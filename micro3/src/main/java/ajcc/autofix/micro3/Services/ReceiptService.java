package ajcc.autofix.micro3.Services;

import ajcc.autofix.micro3.Entities.Bono;
import ajcc.autofix.micro3.Entities.FinalDetails;
import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Entities.RegReparation;
import ajcc.autofix.micro3.Enums.EDiscNRep;
import ajcc.autofix.micro3.Enums.ERecKm;
import ajcc.autofix.micro3.Enums.ERecOld;
import ajcc.autofix.micro3.Models.Detail;
import ajcc.autofix.micro3.Models.Reparation;
import ajcc.autofix.micro3.Models.Vehicle;
import ajcc.autofix.micro3.Repositories.FinalDetailsRepo;
import ajcc.autofix.micro3.Repositories.ReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ajcc.autofix.micro3.Constants.Constants.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReceiptService {
    @Autowired
    ReceiptRepo receiptRepo;

    @Autowired
    RegReparationService regReparationService;

    @Autowired
    FinalDetailsRepo finalDetailsRepo;

    @Autowired
    BonoService bonoService;

    @Autowired
    RestTemplate restTemplate;

    public List<Receipt> getAllReceipts(){
        List<Receipt> receipts = receiptRepo.findAll();
        receipts.forEach( receipt -> {
            Vehicle vehicle = restTemplate.getForObject("http://MICRO1/ByPatente?patente="+ receipt.getPatente(), Vehicle.class);
            receipt.setVehicle(vehicle);

            receipt.getRegReparations().forEach(rep -> {
                Reparation reparation = restTemplate.getForObject("http://MICRO2/"+rep.getReparationId(),Reparation.class);
                rep.setReparation(reparation);
            });
        });

        return receipts;
    }

    public List<Receipt> findAllReceiptsByPatente(String patente){
        return receiptRepo.findAllByPatente(patente);
    }

    public Optional<Receipt> findReceiptById(Long id){
        Receipt receipt = receiptRepo.findById(id).orElse(null);
        if(receipt == null) return Optional.empty();
        Vehicle vehicle = restTemplate.getForObject("http://MICRO1/ByPatente?patente="+ receipt.getPatente(), Vehicle.class);
        receipt.setVehicle(vehicle);

        receipt.getRegReparations().forEach(rep -> {
            Reparation reparation = restTemplate.getForObject("http://MICRO2/"+rep.getReparationId(),Reparation.class);
            rep.setReparation(reparation);
        });
        return Optional.of(receipt);
    }

    public Optional<Receipt> findReceiptUnpaidByPatente(String patente){
        return receiptRepo.findByPatenteAndDeliveredAtIsNull(patente);
    }

    public Receipt createReceiptEmpty(String patente){
        //return receiptRepo.save(receipt);
        return new Receipt(null, null, patente, new ArrayList<>(),null, new ArrayList<>(), null, 0, 0);
    }
    public Receipt saveReceipt(Receipt receipt){
        return receiptRepo.saveAndFlush(receipt);
    }

    public RegReparation regRegReparation(RegReparation regReparation){
        // Veo si existe un recibo sin pagar actual
        Optional<Receipt> optionalReceipt = findReceiptUnpaidByPatente(regReparation.getPatente());
        // En caso que no exista, creo uno
        Receipt receipt = optionalReceipt.orElseGet(() -> createReceiptEmpty(regReparation.getPatente()));

        if(regReparation.getCreatedAt() == null){
            regReparation.setCreatedAt(LocalDateTime.now());
        }
        Vehicle vehicle = restTemplate.getForObject("http://MICRO1/ByPatente?patente="+regReparation.getPatente(), Vehicle.class);
        Reparation reparation = restTemplate.getForObject("http://MICRO2/"+regReparation.getReparationId(), Reparation.class);

        if(vehicle==null || reparation==null) return null;

        switch (vehicle.getMotor()){
            case "gasolina":
                regReparation.setAmount(reparation.getGasValue());
                break;
            case "diesel":
                regReparation.setAmount(reparation.getDieselValue());
                break;
            case "electrico":
                regReparation.setAmount(reparation.getElectricoValue());
                break;
            case "hibrido":
                regReparation.setAmount(reparation.getHibridoValue());
                break;
        }

        receipt.getRegReparations().add(regReparation);
        regReparation.setReceipt(receipt);
        receipt.setVehicle(vehicle);
        receipt.setSumaRep( receipt.getSumaRep()+regReparation.getAmount());

        saveReceipt(receipt);
        return regReparation;
    }

    public Receipt delivered(Long id, List<FinalDetails> details){
        details.forEach(detail -> {
            detail.setReceiptId(id);
        });
        finalDetailsRepo.saveAllAndFlush(details);
        Receipt receipt = findReceiptById(id).orElseThrow();
        receipt.setDeliveredAt(LocalDateTime.now());
        return saveReceipt(receipt);
    }

    public Receipt calculateTotal(Long id, Long id_bono){
        Receipt receipt = findReceiptById(id).orElseThrow();
        if(receipt.getDeliveredAt() != null) return receipt;
        Vehicle vehicle = restTemplate.getForObject("http://MICRO1/ByPatente?patente="+ receipt.getPatente(), Vehicle.class);
        receipt.setVehicle(vehicle);

        receipt.getRegReparations().forEach(rep -> {
            Reparation reparation = restTemplate.getForObject("http://MICRO2/"+rep.getReparationId(),Reparation.class);
            rep.setReparation(reparation);
        });

        if(receipt.getTotal()!=0){
            receipt.setTotal(0);
        }
        int sumaRep = receipt.getSumaRep();

        receipt.getDetails().add(new Detail("Suma Reparaciones", sumaRep, 1));

        int discounts = descRepairs(receipt) + descAttDay(receipt);

        if(id_bono!=null){
            Optional<Bono> bono = bonoService.findBonoById(id_bono);
            if(bono.isPresent() && ( bono.get().equals(receipt.getBono()) || !bono.get().getUsado()) ){
                if(receipt.getBono() != null){
                    bonoService.unUseBono(receipt);
                }
                discounts += bonoService.useBono(bono.get(),receipt);
                receipt.getDetails().add(new Detail("Descuento Bono", -1*bono.get().getAmount(), (-1f*sumaRep)/bono.get().getAmount()));
            }else if (bono.isEmpty()){
                bonoService.unUseBono(receipt);
            }
        } else if (receipt.getBono()!=null) {
            discounts += receipt.getBono().getAmount();
            receipt.getDetails().add(new Detail("Descuento Bono", -1*receipt.getBono().getAmount(), (-1f*sumaRep)/receipt.getBono().getAmount()));
        }

        int recargos = recKm(receipt) + recOld(receipt) + recPark(receipt);

        int costoTotal = (sumaRep - discounts + recargos);
        receipt.getDetails().add(new Detail("IVA", Math.round(costoTotal*IVA),IVA));
        costoTotal = Math.round(costoTotal*(1+IVA));
        receipt.setTotal(costoTotal);
        return saveReceipt(receipt);
    }

    // Zona de descuentos

    private int descRepairs(Receipt receipt){
        int cantidad = regReparationService.countRepairIn12Month(receipt.getVehicle().getPatente());
        if(cantidad==0) return 0;
        float discount = switch (receipt.getVehicle().getMotor()){
            case "gasolina" -> EDiscNRep.GASOLINA.getValues()[cantidad];
            case "diesel" -> EDiscNRep.DIESEL.getValues()[cantidad];
            case "hibrido" -> EDiscNRep.HIBRIDO.getValues()[cantidad];
            case "electrico" -> EDiscNRep.ELECTRICO.getValues()[cantidad];
            default -> throw new IllegalStateException("Vehiculo tiene un tipo de Motor no aceptado: " + receipt.getVehicle().getMotor());
        };
        receipt.getDetails().add(new Detail("Descuento por reparaciones", -1*Math.round(receipt.getSumaRep()*discount) , -1*discount));
        return Math.round(receipt.getSumaRep()*discount);
    }

    private int descAttDay(Receipt receipt){
        Optional<RegReparation> regReparation = regReparationService.findFirstByDay(receipt);
        if(regReparation.isEmpty()) return 0;
        int createdHour = regReparation.get().getCreatedAt().getHour();
        if(regReparation.get().getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY
            || regReparation.get().getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY){
            if(HoraInicioDescuento <= createdHour && createdHour < HoraTerminoDescuento){
                receipt.getDetails().add(new Detail("Descuento por dia de atencion", -1*Math.round(receipt.getSumaRep()*DAYDISCOUNT) , -1*DAYDISCOUNT));
                return Math.round(receipt.getSumaRep()*DAYDISCOUNT);
            }
        }
        return 0;
    }

    // Zona de Recargos
    private int recKm(Receipt receipt){
        int km = receipt.getVehicle().getKm();
        int categoria;
        if(km <= 5000){
            return 0;
        } else if (km <= 12000){
            categoria = 1;
        } else if (km <= 25000){
            categoria = 2;
        } else if (km <= 40000){
            categoria = 3;
        }else {
            categoria = 4;
        }
        float recargo = switch(receipt.getVehicle().getTipo()){
            case "sedan" -> ERecKm.SEDAN.getValues()[categoria];
            case "hatchback" -> ERecKm.HATCHBACK.getValues()[categoria];
            case "suv" -> ERecKm.SUV.getValues()[categoria];
            case "pickup" -> ERecKm.PICKUP.getValues()[categoria];
            case "furgoneta" -> ERecKm.FURGONETA.getValues()[categoria];
            default -> throw new IllegalStateException("Vehiculo tiene un tipo de vehiculo no aceptado:  " + receipt.getVehicle().getTipo());
        };
        receipt.getDetails().add(new Detail("Recargo por Kilometraje", Math.round(receipt.getSumaRep()*recargo) , recargo));
        return Math.round(receipt.getSumaRep()*recargo);
    }

    private int recOld(Receipt receipt){
        int antiguedad = Year.now().minusYears(receipt.getVehicle().getAnofab()).getValue();
        int categoria;
        if(antiguedad <= 5){
            return 0;
        } else if (antiguedad <= 10){
            categoria = 1;
        } else if (antiguedad <= 15){
            categoria = 2;
        } else{
            categoria = 3;
        }
        float recargo = switch(receipt.getVehicle().getTipo()){
            case "sedan" -> ERecOld.SEDAN.getValues()[categoria];
            case "hatchback" -> ERecOld.HATCHBACK.getValues()[categoria];
            case "suv" -> ERecOld.SUV.getValues()[categoria];
            case "pickup" -> ERecOld.PICKUP.getValues()[categoria];
            case "furgoneta" -> ERecOld.FURGONETA.getValues()[categoria];
            default -> throw new IllegalStateException("Vehiculo tiene un tipo de vehiculo no aceptado:  " + receipt.getVehicle().getTipo());
        };
        receipt.getDetails().add(new Detail("Recargo por antiguedad", Math.round(receipt.getSumaRep()*recargo) , recargo));
        return Math.round(receipt.getSumaRep()*recargo);
    }

    public int recPark(Receipt receipt){
        Optional<RegReparation> regReparation = regReparationService.findLastByReceipt(receipt);
        if(regReparation.isEmpty()) return 0;
        int difference = 0;
        if(regReparation.get().getCompletedAt() != null)
            difference = (int) DAYS.between(regReparation.get().getCompletedAt(), LocalDateTime.now());
        if(difference == 0){
            return 0;
        }
        int recargo = Math.round( receipt.getSumaRep() * (difference * 0.05f));
        receipt.getDetails().add(new Detail("Recargo por retraso en retiro", recargo , (difference * 0.05f)));
        return recargo;
    }

}

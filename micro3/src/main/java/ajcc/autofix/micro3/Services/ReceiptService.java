package ajcc.autofix.micro3.Services;

import ajcc.autofix.micro3.Entities.Bono;
import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Entities.RegReparation;
import ajcc.autofix.micro3.Enums.EDiscountsRecharges;
import ajcc.autofix.micro3.Models.Detail;
import ajcc.autofix.micro3.Models.Reparation;
import ajcc.autofix.micro3.Models.Vehicle;
import ajcc.autofix.micro3.Repositories.ReceiptRepo;
import ajcc.autofix.micro3.Repositories.RegReparationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ajcc.autofix.micro3.Constants.Constants.*;

@Service
public class ReceiptService {
    @Autowired
    ReceiptRepo receiptRepo;

    @Autowired
    BonoService bonoService;

    @Autowired
    RestTemplate restTemplate;

    public List<Receipt> getAllReceipts(){
        return receiptRepo.findAll();
    }

    public List<Receipt> findAllReceiptsByPatente(String patente){
        return receiptRepo.findAllByPatente(patente);
    }

    public Optional<Receipt> findReceiptById(Long id){
        return receiptRepo.findById(id);
    }

    public Optional<Receipt> findReceiptUnpaidByPatente(String patente){
        return receiptRepo.findByPatenteAndDeliveredAtIsNotNull(patente);
    }

    public Receipt createReceiptEmpty(String patente){
        Receipt receipt = new Receipt(null, null, patente, null,null, null, null, 0, 0);
        //return receiptRepo.save(receipt);
        return receipt;
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
        receipt.setSumaRep( receipt.getSumaRep()+regReparation.getAmount());

        saveReceipt(receipt);
        return regReparation;
    }

    public Receipt delivered(String patente){
        Receipt receipt = findReceiptUnpaidByPatente(patente).orElseThrow();
        receipt.setDeliveredAt(LocalDateTime.now());
        return saveReceipt(receipt);
    }

    public Receipt calculateTotal(Long id, Long id_bono){
        Receipt receipt = findReceiptById(id).orElseThrow();
        if(receipt.getTotal()!=0){
            receipt.setTotal(0);
        }
        int sumaRep = receipt.getSumaRep();

        receipt.getDetails().add(new Detail("Suma Reparaciones", sumaRep, 1));

        //TODO
        int discounts = 0;

        if(id_bono!=null){
            Optional<Bono> bono = bonoService.findBonoById(id_bono);
            if(bono.isPresent() && !bono.get().getUsado()){
                if(receipt.getBono() != null){
                    bonoService.unUseBono(receipt);
                }
                discounts += bonoService.useBono(bono.get(),receipt);
                receipt.getDetails().add(new Detail("Descuento Bono", -1*bono.get().getAmount(), -1));
            }
        } else if (receipt.getBono()!=null) {
            receipt.getDetails().add(new Detail("Descuento Bono", -1*receipt.getBono().getAmount(), -1));
        }

        int recargos = 0;

        int costoTotal = (sumaRep - discounts + recargos);
        receipt.getDetails().add(new Detail("IVA", Math.round(costoTotal*IVA),IVA));
        costoTotal = Math.round(costoTotal*(1+IVA));
        receipt.setTotal(costoTotal);
        return saveReceipt(receipt);
    }
}

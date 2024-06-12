package ajcc.autofix.micro4.Services;

import ajcc.autofix.micro4.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class RepService {
    @Autowired
    RestTemplate restTemplate;

    //String ip = "http://34.151.221.135:8080/";

    public HashMap<Long, Rep1> generateRep1(int month, int year){
        String url = "http://MICRO3/reparation/byDate?month="+month+"&year="+year;
        //String url = ip+"recibos/reparation/byDate?month="+month+"&year="+year;
        List<RegReparation> reparations = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegReparation>>() {}
        ).getBody();
        if(reparations == null || reparations.isEmpty()) return null;
        HashMap<Long, Rep1> table = new HashMap<>();
        reparations.forEach(rep -> {
            Vehicle vehicle = restTemplate.getForObject("http://MICRO1/ByPatente?patente="+ rep.getPatente(), Vehicle.class);
            //Vehicle vehicle = restTemplate.getForObject(ip+"vehiculos/ByPatente?patente="+ rep.getPatente(), Vehicle.class);
            if(vehicle == null) return;
            if(table.containsKey(rep.getReparationId())){
                Rep1 newRep1 = table.get(rep.getReparationId());
                switch (vehicle.getTipo()) {
                    case "sedan" -> newRep1.getSedan().add(rep.getAmount());
                    case "hatchback" -> newRep1.getHatchback().add(rep.getAmount());
                    case "suv" -> newRep1.getSuv().add(rep.getAmount());
                    case "pickup" -> newRep1.getPickup().add(rep.getAmount());
                    case "furgoneta" -> newRep1.getFurgoneta().add(rep.getAmount());
                    default -> {
                        return ;
                    }
                }
                newRep1.getTotal().add(rep.getAmount());
                table.replace(rep.getReparationId(),newRep1);
            }else{
                Rep1 newRep = switch(vehicle.getTipo()){
                    case "sedan" -> new Rep1( rep.getReparation().getNombre(),
                            new Register(1, rep.getAmount() ),
                            new Register(),
                            new Register(),
                            new Register(),
                            new Register(),
                            new Register(1, rep.getAmount()));
                    case "hatchback" -> new Rep1( rep.getReparation().getNombre(),
                            new Register(),
                            new Register(1, rep.getAmount() ),
                            new Register(),
                            new Register(),
                            new Register(),
                            new Register(1, rep.getAmount()));
                    case "suv" -> new Rep1( rep.getReparation().getNombre(),
                            new Register(),
                            new Register(),
                            new Register(1, rep.getAmount() ),
                            new Register(),
                            new Register(),
                            new Register(1, rep.getAmount()));
                    case "pickup" -> new Rep1( rep.getReparation().getNombre(),
                            new Register(),
                            new Register(),
                            new Register(),
                            new Register(1, rep.getAmount() ),
                            new Register(),
                            new Register(1, rep.getAmount()));
                    case "furgoneta" -> new Rep1( rep.getReparation().getNombre(),
                            new Register(),
                            new Register(),
                            new Register(),
                            new Register(),
                            new Register(1, rep.getAmount() ),
                            new Register(1, rep.getAmount()));
                    default -> null;
                };
                if(newRep == null) return;
                table.put(rep.getReparationId(),
                            newRep);
            }
        });
        return table;
    }

    public HashMap<Long, Rep2> generateRep2(int month, int year) {
        List<Reparation> repsList = restTemplate.exchange(
                "http://MICRO2/",
                //ip + "reparaciones/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Reparation>>() {
                }
        ).getBody();
        HashMap<Long, Rep2> table = new HashMap<>();
        if (repsList == null || repsList.isEmpty()) return null;

        repsList.forEach(reparation -> {
            table.put(reparation.getId(), new Rep2(reparation.getNombre()));
        });

        String url = "http://MICRO3/reparation/byDate?month="+month+"&year="+year;
        //String url = ip + "recibos/reparation/byDate?month=" + month + "&year=" + year;
        try {
            List<RegReparation> reparations = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RegReparation>>() {
                    }
            ).getBody();
            if (reparations == null || reparations.isEmpty()) return null;
            reparations.forEach(regReparation -> {
                table.get(regReparation.getReparationId()).getMonth().add(regReparation.getAmount());
            });
        } catch (Exception e) {
            System.out.println("Excepcion:" + e.getMessage());
        }
        try {
            String url2 = "http://MICRO3/reparation/byDate?month="+(month-1)+"&year="+year;
            //String url2 = ip + "recibos/reparation/byDate?month=" + (month - 1) + "&year=" + year;
            List<RegReparation> reparationsPrev = restTemplate.exchange(
                    url2,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RegReparation>>() {
                    }
            ).getBody();
            if (reparationsPrev == null || reparationsPrev.isEmpty()) return null;
            reparationsPrev.forEach(regReparation -> {
                table.get(regReparation.getReparationId()).getPrevMonth().add(regReparation.getAmount());

            });
        } catch (Exception e) {
            System.out.println("Excepcion:" + e.getMessage());
        }
        String url3 = "http://MICRO3/reparation/byDate?month="+(month-2)+"&year="+year;
        //String url3 = ip + "recibos/reparation/byDate?month=" + (month - 2) + "&year=" + year;
        try{
            List<RegReparation> reparationsPrevPrev = restTemplate.exchange(
                    url3,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RegReparation>>() {
                    }
            ).getBody();
            if (reparationsPrevPrev == null || reparationsPrevPrev.isEmpty()) return null;
            reparationsPrevPrev.forEach(regReparation -> {
                table.get(regReparation.getReparationId()).getPrevPMonth().add(regReparation.getAmount());
            });
        }catch(Exception e){
            System.out.println("Excepcion:"+e.getMessage());
        }
        table.forEach( (key, reparation) -> {
            reparation.calcFirstMonth();
            reparation.calcSectMonth();
        });

        return table;
    }
}

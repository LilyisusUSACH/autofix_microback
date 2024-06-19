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
        List<Reparation> repsList = restTemplate.exchange(
                "http://MICRO2/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Reparation>>() {
                }
        ).getBody();
        HashMap<Long, Rep1> table = new HashMap<>();
        if (repsList == null || repsList.isEmpty()) return null;

        repsList.forEach(reparation -> {
            table.put(reparation.getId(), new Rep1(reparation.getNombre()));
        });

        String url = "http://MICRO3/reparation/byDate?month="+month+"&year="+year;
        List<RegReparation> reparations = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegReparation>>() {}
        ).getBody();
        if(reparations == null || reparations.isEmpty()) return null;

        reparations.forEach(rep -> {
            Vehicle vehicle = restTemplate.getForObject("http://MICRO1/ByPatente?patente="+ rep.getPatente(), Vehicle.class);
            if(vehicle == null) return;
            switch (vehicle.getTipo()) {
                case "sedan" -> table.get(rep.getReparationId()).getSedan().add(rep.getAmount());
                case "hatchback" -> table.get(rep.getReparationId()).getHatchback().add(rep.getAmount());
                case "suv" -> table.get(rep.getReparationId()).getSuv().add(rep.getAmount());
                case "pickup" -> table.get(rep.getReparationId()).getPickup().add(rep.getAmount());
                case "furgoneta" -> table.get(rep.getReparationId()).getFurgoneta().add(rep.getAmount());
                default -> {
                    return ;
                }
            }
            table.get(rep.getReparationId()).getTotal().add(rep.getAmount());
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

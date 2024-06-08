package ajcc.autofix.micro3.Services;

import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Entities.RegReparation;
import ajcc.autofix.micro3.Models.Reparation;
import ajcc.autofix.micro3.Repositories.RegReparationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegReparationService {
    @Autowired
    RegReparationRepo regReparationRepo;

    @Autowired
    RestTemplate restTemplate;
    public List<RegReparation> getAllRegRep(){
        List<RegReparation> regReparations = regReparationRepo.findAll();
        regReparations.forEach(rep -> {
            Reparation reparation = restTemplate.getForObject("http://MICRO2/"+rep.getReparationId(),Reparation.class);
            rep.setReparation(reparation);
        });
        return regReparations;
    }

    public List<RegReparation> getAllRegRepOnWork(){
        List<RegReparation> regReparations = regReparationRepo.findAllByCompletedAtIsNullOrderByCreatedAt();
        regReparations.forEach(rep -> {
            Reparation reparation = restTemplate.getForObject("http://MICRO2/"+rep.getReparationId(),Reparation.class);
            rep.setReparation(reparation);
        });
        return regReparations;
    }

    public Optional<RegReparation> findRegRepById(Long id){
        Optional<RegReparation> regReparation = regReparationRepo.findById(id);
        if(regReparation.isPresent()){
            Reparation reparation = restTemplate.getForObject("http://MICRO2/"+regReparation.get().getReparationId(),Reparation.class);
            regReparation.get().setReparation(reparation);
        }
        return regReparation;
    }

    public List<RegReparation> findRegRepsByPatente(String patente){
        List<RegReparation> regReparations = regReparationRepo.findAllByPatente(patente);
        regReparations.forEach(rep -> {
            Reparation reparation = restTemplate.getForObject("http://MICRO2/"+rep.getReparationId(),Reparation.class);
            rep.setReparation(reparation);
        });
        return regReparations;
    }

    public Optional<RegReparation> findFirstByDay(Receipt receipt, LocalDate date){
        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime finishDay = date.plusDays(1).atStartOfDay();
        return regReparationRepo.findFirstByReceiptAndCreatedAtBetween(receipt,startDay, finishDay);//, date);
    }

    public int countRepairIn12Month(String patente){
        return regReparationRepo.countByPatenteAndCreatedAtBetween(patente, LocalDateTime.now(), LocalDateTime.now().minusMonths(12));
    }

    // TODO: para optimizar, tendria que hacer el reparation embeedable y guardarlo en la db, por tiempo no lo hago
    public RegReparation saveRegRep(RegReparation regReparation){
        return regReparationRepo.save(regReparation);
    }

    public RegReparation completeRegRep(Long id){
        RegReparation regReparation = findRegRepById(id).orElseThrow();
        regReparation.setCompletedAt(LocalDateTime.now());
        return saveRegRep(regReparation);
    }

    public void deleteReparation(Long id){
        regReparationRepo.deleteById(id);
    }
}

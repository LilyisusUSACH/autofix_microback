package ajcc.autofix.micro2.Services;

import ajcc.autofix.micro2.Entities.Reparation;
import ajcc.autofix.micro2.Repositories.ReparationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReparationService {
    @Autowired
    ReparationRepo reparationRepo;
    // Reads
    public List<Reparation> getReparations(){
        return reparationRepo.findAll();
    }

    public Optional<Reparation> findReparationById(Long id){
        return reparationRepo.findById(id);
    }

    // Creates
    public Reparation createReparation(Reparation reparation){
        return  reparationRepo.save(reparation);
    }

    // Updates
    public Reparation updateReparation(Reparation reparation){
        if(reparationRepo.existsById(reparation.getId()))
            return reparationRepo.save(reparation);
        return null; // o throweo?
    }

}

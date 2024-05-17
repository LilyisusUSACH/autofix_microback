package ajcc.autofix.micro2.Controllers;

import ajcc.autofix.micro2.Entities.Reparation;
import ajcc.autofix.micro2.Services.ReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class TestController {
    @Autowired
    ReparationService reparationService;
    @GetMapping("/")
    public ResponseEntity<?> getRepas(){
        List<Reparation> reparations = reparationService.getReparations();
        if(!reparations.isEmpty())
            return new ResponseEntity<>("No hay reparaciones", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reparations, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRepaById(@PathVariable Long id){
        Optional<Reparation> reparation = reparationService.findReparationById(id);
        if(reparation.isPresent())
            return new ResponseEntity<>(reparation.get(), HttpStatus.OK);
        return new ResponseEntity<>("No hay ninguna entidad con ese ID",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepaById(@RequestBody Reparation reparation){
        Reparation reparationResult = reparationService.updateReparation(reparation);
        if(reparationResult!=null)
            return new ResponseEntity<>(reparationResult, HttpStatus.OK);
        return new ResponseEntity<>("No hay ninguna entidad con ese ID",HttpStatus.NOT_FOUND);
    }
}

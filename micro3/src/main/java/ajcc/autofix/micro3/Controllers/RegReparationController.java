package ajcc.autofix.micro3.Controllers;

import ajcc.autofix.micro3.Entities.Bono;
import ajcc.autofix.micro3.Entities.RegReparation;
import ajcc.autofix.micro3.Services.RegReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reparation")
@CrossOrigin("*")
public class RegReparationController{
    @Autowired
    RegReparationService regReparationService;

    @GetMapping("/")
    public ResponseEntity<?> getReparations(){
        List<RegReparation> regReparations = regReparationService.getAllRegRep();
        if(regReparations.isEmpty())
            return new ResponseEntity<>("No hay Reparaciones", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(regReparations, HttpStatus.OK);
    }

    @GetMapping("/onwork")
    public ResponseEntity<?> getRepOnWork(){
        List<RegReparation> regReparations = regReparationService.getAllRegRepOnWork();
        if(regReparations.isEmpty())
            return new ResponseEntity<>("No hay Reparaciones activas en el taller", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(regReparations, HttpStatus.OK);
    }

    @GetMapping("/byPatente")
    public ResponseEntity<?> getRepByPatente(@RequestParam("patente") String patente){
        List<RegReparation> regReparations = regReparationService.findRegRepsByPatente(patente);
        if(regReparations.isEmpty())
            return new ResponseEntity<>("No hay Reparaciones para esa patente", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(regReparations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegRepById(@PathVariable Long id){
        Optional<RegReparation> regReparation = regReparationService.findRegRepById(id);
        if(regReparation.isPresent())
            return new ResponseEntity<>(regReparation, HttpStatus.OK);
        return new ResponseEntity<>("No hay ninguna reparacion con ese ID", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRegRep(@RequestBody RegReparation regReparation){
        return new ResponseEntity<>(regReparationService.saveRegRep(regReparation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> completeRegRep(@PathVariable Long id){
        return new ResponseEntity<>(regReparationService.completeRegRep(id), HttpStatus.OK);
    }
}

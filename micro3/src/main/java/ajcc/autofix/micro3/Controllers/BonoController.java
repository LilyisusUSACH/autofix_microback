package ajcc.autofix.micro3.Controllers;

import ajcc.autofix.micro3.Entities.Bono;
import ajcc.autofix.micro3.Services.BonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bono")
public class BonoController {
    @Autowired
    BonoService bonoService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBonos(){
        List<Bono> bonos = bonoService.getAllBonos();
        if(bonos.isEmpty())
            return new ResponseEntity<>("No hay Bonos", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bonos, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getAllBonosUseds(){
        List<Bono> bonos = bonoService.getAllBonosUsed();
        if(bonos.isEmpty())
            return new ResponseEntity<>("No hay Bonos disponibles", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bonos, HttpStatus.OK);
    }
    @GetMapping("/disp")
    public ResponseEntity<?> getAllBonosDisp(){
        List<Bono> bonos = bonoService.getAllBonosNoUsed();
        if(bonos.isEmpty())
            return new ResponseEntity<>("No hay Bonos disponibles", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bonos, HttpStatus.OK);
    }

    @GetMapping("/marca")
    public ResponseEntity<?> getBonosByMarca(@RequestParam("marca") String marca){
        List<Bono> bonos = bonoService.findBonosByMarca(marca);
        if(bonos.isEmpty())
            return new ResponseEntity<>("No hay Bonos para esta marca", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bonos, HttpStatus.OK);
    }

    @GetMapping("/marcaDisp")
    public ResponseEntity<?> getBonosDispoByMarca(@RequestParam("marca") String marca){
        List<Bono> bonos = bonoService.findBonosNoUsedByMarca(marca);
        if(bonos.isEmpty())
            return new ResponseEntity<>("No hay Bonos Disponibles para esta marca", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bonos, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewBono(@RequestBody Bono bono){
        return new ResponseEntity<>(bonoService.createBono(bono), HttpStatus.CREATED);
    }
}

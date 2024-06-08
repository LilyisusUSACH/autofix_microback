package ajcc.autofix.micro3.Controllers;

import ajcc.autofix.micro3.Entities.Bono;
import ajcc.autofix.micro3.Services.BonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bono")
@CrossOrigin("*")
public class BonoController {
    @Autowired
    BonoService bonoService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBonos(){
        return ResponseEntity.ok( bonoService.getAllBonos() );
    }

    @GetMapping("/disp")
    public ResponseEntity<?> getAllBonosDisp(){
        return ResponseEntity.ok( bonoService.getAllBonosNoUsed() );
    }

    @GetMapping("/marca")
    public ResponseEntity<?> getBonosByMarca(@RequestParam("marca") String marca){
        return ResponseEntity.ok( bonoService.findBonosByMarca(marca) );
    }

    @GetMapping("/marcaDisp")
    public ResponseEntity<?> getBonosDispoByMarca(@RequestParam("marca") String marca){
        return ResponseEntity.ok( bonoService.findBonosNoUsedByMarca(marca) );
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewBono(@RequestBody Bono bono){
        return ResponseEntity.ok(bonoService.createBono(bono));
    }
}

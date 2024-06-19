package ajcc.autofix.micro4.Controllers;

import ajcc.autofix.micro4.Services.RepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class RepController {
    @Autowired
    RepService repService;

    @GetMapping("/1")
    public ResponseEntity<?> getReport1(@RequestParam("year") int year, @RequestParam("month") int month){
        return new ResponseEntity<>(repService.generateRep1(month, year), HttpStatus.OK);
    }

    @GetMapping("/2")
    public ResponseEntity<?> getReport2(@RequestParam("year") int year, @RequestParam("month") int month){
        return new ResponseEntity<>(repService.generateRep2(month, year), HttpStatus.OK);
    }


}

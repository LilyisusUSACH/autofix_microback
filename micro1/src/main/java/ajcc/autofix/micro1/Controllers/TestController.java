package ajcc.autofix.micro1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> a(){
        return ResponseEntity.ok("Correcto");
    }
}

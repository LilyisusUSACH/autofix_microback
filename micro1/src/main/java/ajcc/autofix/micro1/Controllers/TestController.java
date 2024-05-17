package ajcc.autofix.micro1.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin("*")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public ResponseEntity<?> a(){
        String a = restTemplate.getForObject("http://micro2/",String.class);
        return ResponseEntity.ok("Traido desde narnia" + a);
    }
}

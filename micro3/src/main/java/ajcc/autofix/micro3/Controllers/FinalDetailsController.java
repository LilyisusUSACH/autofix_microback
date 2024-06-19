package ajcc.autofix.micro3.Controllers;

import ajcc.autofix.micro3.Entities.FinalDetails;
import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Services.FinalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/details")

public class FinalDetailsController {
    @Autowired
    FinalDetailsService finalDetailsService;
    @GetMapping("/{id}")
    public ResponseEntity<?> findByReceipt(@PathVariable Long id){
        List<FinalDetails> details = finalDetailsService.finalDetailsByReceipt(id);
        if(details.isEmpty())
            return new ResponseEntity<>("No hay Detalles para ese recibo", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}

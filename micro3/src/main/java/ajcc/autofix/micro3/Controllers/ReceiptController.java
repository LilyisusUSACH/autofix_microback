package ajcc.autofix.micro3.Controllers;

import ajcc.autofix.micro3.Entities.FinalDetails;
import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Entities.RegReparation;
import ajcc.autofix.micro3.Models.Detail;
import ajcc.autofix.micro3.Services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;

    @GetMapping("/")
    public ResponseEntity<?> getReceipts(){
        List<Receipt> receipts = receiptService.getAllReceipts();
        if(receipts.isEmpty())
            return new ResponseEntity<>("No hay Recibos", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }
    @GetMapping("/byPatente")
    public ResponseEntity<?> getReceiptsByPatente(@RequestParam("patente") String patente){
        List<Receipt> receipts = receiptService.findAllReceiptsByPatente(patente);
        if(receipts.isEmpty())
            return new ResponseEntity<>("No hay Recibos para esa patente", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }
    @GetMapping("/actualByPatente")
    public ResponseEntity<?> getReceiptUnpaidByPatente(@RequestParam("patente") String patente){
        Optional<Receipt> receipt = receiptService.findReceiptUnpaidByPatente(patente);
        if(receipt.isEmpty())
            return new ResponseEntity<>("No hay Recibos Impagos para esa patente", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceiptByID(@PathVariable Long id){
        Optional<Receipt> receipt = receiptService.findReceiptById(id);
        if(receipt.isEmpty())
            return new ResponseEntity<>("No hay Recibo con el ID entregado", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @PostMapping("/calc/{id}")
    public ResponseEntity<?> calcTotal(@PathVariable Long id, @RequestParam(name = "id_bono", required = false) Long id_bono){
        Receipt receipt = receiptService.calculateTotal(id, id_bono);
        if(receipt == null)
            return new ResponseEntity<>("No hay Recibo con el ID entregado", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @PostMapping("/repair/")
    public ResponseEntity<?> regNewRegRep(@RequestBody RegReparation regReparation){
        RegReparation reparation = receiptService.regRegReparation(regReparation);
        if(reparation == null)
            return new ResponseEntity<>("No se pudo crear la reparacion", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reparation, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> deliver(@PathVariable Long id, @RequestBody List<FinalDetails> finalDetails){
        Receipt receipt = receiptService.delivered(id, finalDetails);
        if(receipt == null)
            return new ResponseEntity<>("No hay Recibo con el ID entregado", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }


}

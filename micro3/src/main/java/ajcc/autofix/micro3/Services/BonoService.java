package ajcc.autofix.micro3.Services;

import ajcc.autofix.micro3.Entities.Bono;
import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Repositories.BonoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonoService {
    @Autowired
    BonoRepo bonoRepo;

    public List<Bono> getAllBonos(){
        return bonoRepo.findAll();
    }

    public List<Bono> getAllBonosUsed(){
        return bonoRepo.findAllByUsadoIsTrue();
    }


    public List<Bono> getAllBonosNoUsed(){
        return bonoRepo.findAllByUsadoIsFalse();
    }

    public List<Bono> findBonosByMarca(String marca){
        return bonoRepo.findAllByMarca(marca);
    }

    public List<Bono> findBonosNoUsedByMarca(String marca){
        return bonoRepo.findAllByMarcaAndUsadoIsFalse(marca);
    }

    public Optional<Bono> findBonoById(Long id){
        return bonoRepo.findById(id);
    }

    public Bono createBono(Bono bono){
        return bonoRepo.save(bono);
    }

    public int useBono(Bono bono, Receipt receipt){
        bono.setReceipt(receipt);
        bono.setUsado(true);
        receipt.setBono(bono);
        return bonoRepo.save(bono).getAmount();
    }

    public void unUseBono(Receipt receipt){
        Bono bono = receipt.getBono();
        if(bono == null) {
            receipt.setBono(null);
            return;
        }
        bono.setReceipt(null);
        bono.setUsado(false);
        receipt.setBono(null);
        bonoRepo.save(bono);
    }
}

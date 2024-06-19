package ajcc.autofix.micro3.Services;

import ajcc.autofix.micro3.Entities.FinalDetails;
import ajcc.autofix.micro3.Repositories.FinalDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinalDetailsService {
    @Autowired
    FinalDetailsRepo finalDetailsRepo;

    public List<FinalDetails> finalDetailsByReceipt(Long id){
        return finalDetailsRepo.findAllByReceiptId(id);
    }
}

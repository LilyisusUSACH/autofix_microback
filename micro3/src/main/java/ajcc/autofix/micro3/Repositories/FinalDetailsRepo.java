package ajcc.autofix.micro3.Repositories;

import ajcc.autofix.micro3.Entities.FinalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalDetailsRepo extends JpaRepository<FinalDetails, Long> {
    List<FinalDetails> findAllByReceiptId(Long id);
}

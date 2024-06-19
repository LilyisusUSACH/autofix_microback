package ajcc.autofix.micro3.Repositories;

import ajcc.autofix.micro3.Entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepo extends JpaRepository<Receipt,Long> {
    List<Receipt> findAllByPatente(String patente);

    Optional<Receipt> findByPatenteAndDeliveredAtIsNull(String patente);


}

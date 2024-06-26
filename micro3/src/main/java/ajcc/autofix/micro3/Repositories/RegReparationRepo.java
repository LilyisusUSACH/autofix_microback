package ajcc.autofix.micro3.Repositories;

import ajcc.autofix.micro3.Entities.Receipt;
import ajcc.autofix.micro3.Entities.RegReparation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegReparationRepo extends JpaRepository<RegReparation, Long> {
    List<RegReparation> findAllByCompletedAtIsNullOrderByCreatedAt();

    List<RegReparation> findAllByPatente(String patente);

    List<RegReparation> findAllByCompletedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<RegReparation> findFirstByReceiptOrderByCreatedAtDesc(Receipt receipt);

    Optional<RegReparation> findFirstByReceiptOrderByCompletedAtDesc(Receipt receipt);

    int countByPatenteAndCreatedAtBetween(String patente, LocalDateTime createdAt, LocalDateTime createdAt2);
}

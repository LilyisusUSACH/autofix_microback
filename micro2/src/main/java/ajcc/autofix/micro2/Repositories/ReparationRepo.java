package ajcc.autofix.micro2.Repositories;

import ajcc.autofix.micro2.Entities.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReparationRepo extends JpaRepository<Reparation, Long> {

}

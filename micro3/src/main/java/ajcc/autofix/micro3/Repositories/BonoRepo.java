package ajcc.autofix.micro3.Repositories;

import ajcc.autofix.micro3.Entities.Bono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BonoRepo extends JpaRepository<Bono, Long> {
    List<Bono> findAllByUsadoIsFalse();
    List<Bono> findAllByUsadoIsTrue();

    List<Bono> findAllByMarca(String marca);

    List<Bono> findAllByMarcaAndUsadoIsFalse(String marca);
}

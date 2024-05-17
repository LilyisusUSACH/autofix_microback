package ajcc.autofix.micro1.Repositories;

import ajcc.autofix.micro1.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Boolean existsVehicleById(Long id);
    Optional<Vehicle> findVehicleByPatente(String patente);
    List<Vehicle> findVehiclesByMarca(String marca);
}

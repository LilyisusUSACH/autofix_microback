package ajcc.autofix.micro1.Services;

import ajcc.autofix.micro1.Entities.Vehicle;
import ajcc.autofix.micro1.Repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    public List<Vehicle> getVehicles(){
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findVehiclesByMarca(String marca){
        return vehicleRepository.findVehiclesByMarca(marca);
    }

    public Optional<Vehicle> findVehicleById(Long id){
        return vehicleRepository.findById(id);
    }
    public Optional<Vehicle> findVehicleByPatente(String patente){
        return vehicleRepository.findVehicleByPatente(patente);
    }

    public Vehicle createVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Vehicle vehicle){
        if( vehicleRepository.existsVehicleById(vehicle.getId()))
            return vehicleRepository.save(vehicle);
        return null;
    }
}

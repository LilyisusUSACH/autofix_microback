package ajcc.autofix.micro1.Controllers;

import ajcc.autofix.micro1.Entities.Vehicle;
import ajcc.autofix.micro1.Services.VehicleService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class VehicleController {
    @Autowired
    VehicleService vehicleService;
    @GetMapping("/")
    public ResponseEntity<?> getVehicles(){
        List<Vehicle> vehicles = vehicleService.getVehicles();
        if(vehicles.isEmpty())
            return new ResponseEntity<>("No hay vehiculos", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id){
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(id);
        if(vehicle.isPresent())
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        return new ResponseEntity<>("No hay ningun vehiculo con ese ID", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ByPatente")
    public ResponseEntity<?> getVehicleByPatente(@RequestParam String patente){
        Optional<Vehicle> vehicle = vehicleService.findVehicleByPatente(patente);
        if(vehicle.isPresent())
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        return new ResponseEntity<>("No hay ningun vehiculo con esa patente", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle){
        return new ResponseEntity<>(vehicleService.createVehicle(vehicle), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicleById(@RequestBody Vehicle vehicle){
        Vehicle vehicleResult = vehicleService.updateVehicle(vehicle);
        if(vehicleResult!=null)
            return new ResponseEntity<>(vehicleResult, HttpStatus.OK);
        return new ResponseEntity<>("No hay ningun vehiculo con ese ID",HttpStatus.NOT_FOUND);
    }
}

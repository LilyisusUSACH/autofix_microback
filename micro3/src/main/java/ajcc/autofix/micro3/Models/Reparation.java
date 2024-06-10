package ajcc.autofix.micro3.Models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reparation{
    private Long id;

    private String nombre;

    private String descripcion;

    private int gasValue;

    private int dieselValue;

    private int hibridoValue;

    private int electricoValue;
}

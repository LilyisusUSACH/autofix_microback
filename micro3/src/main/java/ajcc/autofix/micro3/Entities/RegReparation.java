package ajcc.autofix.micro3.Entities;

import ajcc.autofix.micro3.Models.Reparation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class RegReparation{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patente;
    private Long reparationId;
    private LocalDateTime Fecha;
    private int amount;
}

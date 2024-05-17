package ajcc.autofix.micro1.Entities;

import ajcc.autofix.micro1.Enums.EMotorType;
import ajcc.autofix.micro1.Enums.ETipo;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "vehicle", indexes = {
        @Index(name = "idx_patente", columnList = "patente")
})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String patente;

    private String marca;

    private String modelo;

    private int añofab;

    private int nAsientos;

    private ETipo tipo;

    private EMotorType motor;

}

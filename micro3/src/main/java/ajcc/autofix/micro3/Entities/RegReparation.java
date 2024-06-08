package ajcc.autofix.micro3.Entities;

import ajcc.autofix.micro3.Models.Reparation;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class RegReparation{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patente;

    @ManyToOne()
    @JoinColumn(name = "receipt")
    @JsonIdentityReference(alwaysAsId = true)
    private Receipt receipt;

    private Long reparationId;

    @Transient
    private Reparation reparation;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private int amount;
}

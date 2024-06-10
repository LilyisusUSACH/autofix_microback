package ajcc.autofix.micro4.Models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RegReparation {
    private Long id;
    private String patente;
    private Long receipt;
    private Long reparationId;
    private Reparation reparation;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private int amount;
}

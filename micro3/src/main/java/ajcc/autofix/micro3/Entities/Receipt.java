package ajcc.autofix.micro3.Entities;

import ajcc.autofix.micro3.Models.Detail;
import ajcc.autofix.micro3.Models.Reparation;
import ajcc.autofix.micro3.Models.Vehicle;
import ajcc.autofix.micro3.Repositories.RegReparationRepo;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Vehicle vehicle;

    private String patente; // En vez del vehicle ID

    @ElementCollection
    @Transient
    private List<Detail> details;

    @OneToOne
    @JoinColumn(name = "bono_id")
    private Bono bono;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<RegReparation> regReparations = new ArrayList<>();

    private LocalDateTime deliveredAt;

    private int sumaRep;

    private int total;
}

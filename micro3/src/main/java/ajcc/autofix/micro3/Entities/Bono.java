package ajcc.autofix.micro3.Entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Bono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private Boolean usado;

    private int amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    @JsonIgnore
    private Receipt receipt;

    @JsonGetter
    private Long receiptId(){
        if(receipt != null) return receipt.getId();
        return null;
    }

    public Bono(String marca, int amount) {
        this.marca = marca;
        this.amount = amount;
        this.usado = false;
    }
}

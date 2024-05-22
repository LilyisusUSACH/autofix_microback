package ajcc.autofix.micro3.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    List<Detail> details;


}

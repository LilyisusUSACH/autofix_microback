package ajcc.autofix.micro3.Models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import reactor.util.annotation.NonNull;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vehicle {
    private Long id;

    private String patente;

    private String marca;

    private String modelo;

    private int anofab;

    private int km;

    private int nAsientos;

    private String tipo;

    @NonNull
    private String motor;
}

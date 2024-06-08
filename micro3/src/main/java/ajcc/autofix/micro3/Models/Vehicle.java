package ajcc.autofix.micro3.Models;

import jakarta.persistence.Column;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import reactor.util.annotation.NonNull;

@Data
public class Vehicle {
    private Long id;

    private String patente;

    private String marca;

    private String modelo;

    private int añofab;

    private int nAsientos;

    private String tipo;

    @NonNull
    private String motor;
}

package ajcc.autofix.micro3.Enums;

import lombok.Getter;

@Getter
public enum EDiscNRep {
    GASOLINA(new float[] {0 , 0.05f, 0.05f, 0.10f, 0.10f, 0.10f, 0.15f, 0.15f, 0.15f, 0.15f, 0.20f}),
    DIESEL(new float[] {0 , 0.07f, 0.07f, 0.12f, 0.12f, 0.12f, 0.17f, 0.17f, 0.17f, 0.17f, 0.22f}),
    HIBRIDO(new float[] {0 , 0.10f, 0.10f, 0.15f, 0.15f, 0.15f, 0.20f, 0.20f, 0.20f, 0.20f, 0.25f}),
    ELECTRICO(new float[] {0 , 0.08f, 0.08f, 0.13f, 0.13f, 0.13f, 0.18f, 0.18f, 0.18f, 0.18f, 0.23f});

    private final float[] values;

    private EDiscNRep(float[] values){
        this.values = values;
    }
}

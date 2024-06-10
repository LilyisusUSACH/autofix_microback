package ajcc.autofix.micro3.Enums;

import lombok.Getter;

@Getter
public enum ERecKm {
        SEDAN(new float[] {0f, 0.03f, 0.07f, 0.12f, 0.20f}),
    HATCHBACK(new float[] {0f, 0.03f, 0.07f, 0.12f, 0.20f}),
          SUV(new float[] {0f, 0.05f, 0.09f, 0.12f, 0.20f}),
       PICKUP(new float[] {0f, 0.05f, 0.09f, 0.12f, 0.20f}),
    FURGONETA(new float[] {0f, 0.05f, 0.09f, 0.12f, 0.20f});

    private final float[] values;

    private ERecKm(float[] values){
        this.values = values;
    }
}
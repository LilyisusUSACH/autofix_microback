package ajcc.autofix.micro3.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;


@Embeddable
public class Detail {
    private String description;
    private int value;
    private float percent;

}

package ajcc.autofix.micro4.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rep1 {
    private String name;

    private Register sedan;
    private Register hatchback;
    private Register suv;
    private Register pickup;
    private Register furgoneta;

    private Register total;
}

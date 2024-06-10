package ajcc.autofix.micro4.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Register {
    private int count;
    private int amount;

    public Register() {
        this.count = 0;
        this.amount = 0;
    }

    public void add(int amount){
        count += 1;
        this.amount += amount;
    }
}

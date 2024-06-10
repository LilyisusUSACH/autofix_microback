package ajcc.autofix.micro4.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Rep2 {
    private String name;
    private Register month;
    private float varOneQty;
    private float varOneAmount;
    private Register prevMonth;
    private float varTwoPerc;
    private float varTwoAmount;
    private Register prevPMonth;

    public Rep2(String name) {
        this.name = name;
        month = new Register();
        varOneAmount = 0;
        varOneQty = 0;
        prevMonth = new Register();
        varTwoPerc = 0;
        varTwoAmount = 0;
        prevPMonth = new Register();
    }

    public void calcFirstMonth(){
        varOneQty = (month.getCount()*1.0f)/prevMonth.getCount();
        varOneAmount = (month.getAmount()*1.0f)/prevMonth.getAmount();
    }
    public void calcSectMonth(){
        varOneQty = (prevMonth.getCount()*1.0f)/prevPMonth.getCount();
        varOneAmount = (prevMonth.getAmount()*1.0f)/prevPMonth.getAmount();
    }
}

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
    private float varTwoQty;
    private float varTwoAmount;
    private Register prevPMonth;

    public Rep2(String name) {
        this.name = name;
        month = new Register();
        varOneAmount = 0;
        varOneQty = 0;
        prevMonth = new Register();
        varTwoQty = 0;
        varTwoAmount = 0;
        prevPMonth = new Register();
    }

    public void calcFirstMonth(){
        if (prevMonth.getCount() != 0) {
            varOneQty = (month.getCount() * 1.0f) / prevMonth.getCount();
            varOneAmount = (month.getAmount() * 1.0f) / prevMonth.getAmount();
        }
        else {
            varOneQty = month.getCount() * 1.0f;
            varOneAmount = month.getAmount() * 1.0f;
        }
    }
    public void calcSectMonth(){
        if (prevPMonth.getCount() == 0) {
            varTwoQty = prevMonth.getCount() * 1.0f;
            varTwoAmount = prevMonth.getAmount() * 1.0f;
        } else {
            varTwoQty = (prevMonth.getCount()*1.0f)/prevPMonth.getCount();
            varTwoAmount = (prevMonth.getAmount()*1.0f)/prevPMonth.getAmount();
        }
    }
}

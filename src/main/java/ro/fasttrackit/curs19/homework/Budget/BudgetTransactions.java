package ro.fasttrackit.curs19.homework.Budget;

import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class BudgetTransactions {

    public List<BudgetModel> getTransactions(){
        return List.of(
                new BudgetModel(1,"paine",TransactionType.SELL,4.5),
                new BudgetModel(2,"faina",TransactionType.BUY,2.5),
                new BudgetModel(3,"covrigi",TransactionType.SELL,1.5),
                new BudgetModel(4,"placinte",TransactionType.SELL,3.5),
                new BudgetModel(5,"drojdie",TransactionType.BUY,0.5),
                new BudgetModel(6,"faina",TransactionType.BUY,2.7)
        );
    }
}

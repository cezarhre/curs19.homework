package ro.fasttrackit.curs19.homework.Budget;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transactions")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService){

        this.budgetService = budgetService;
    }

    @GetMapping
    public List<BudgetModel> getAll(
            @RequestParam(defaultValue = "0") int id,
            @RequestParam(required = false) String product,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(defaultValue = "0") double minAmount,
            @RequestParam(defaultValue = "0") double maxAmount
    ){
        return budgetService.getAll(id, product, type, minAmount, maxAmount);
    }

    @GetMapping("/{id}")
    List<BudgetModel> byId(@PathVariable int id){
        return budgetService.byId(id);
    }

    @GetMapping("/reports/product")
    Map<String,List<BudgetModel>> groupByProduct() {
        return budgetService.groupByProduct();
    }

    @GetMapping("/reports/type")
    Map<TransactionType,List<BudgetModel>> groupByType() {
        return budgetService.groupByType();
    }

    @PostMapping
    BudgetModel addTransaction(@RequestBody BudgetModel budgetModel){
        return budgetService.add(budgetModel);
    }

    @PutMapping("{id}")
    BudgetModel replaceTransaction(@PathVariable int id, @RequestBody BudgetModel budgetModel){
        return budgetService.replace(id,budgetModel)
                .orElseThrow(()-> new ResourceNotFoundException("Can't find transaction with id" + id));
    }

    @DeleteMapping("{id}")
    BudgetModel deleteTransaction(@PathVariable int id){
        return budgetService.delete(id)
                .orElse(null);
    }
}
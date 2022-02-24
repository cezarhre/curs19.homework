package ro.fasttrackit.curs19.homework.Budget;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@Service
public class BudgetService {

    private final List<BudgetModel> transactions;

    public BudgetService(BudgetTransactions budgetTransactions) {
        this.transactions = new ArrayList<>(budgetTransactions.getTransactions());
    }

    public List<BudgetModel> getAll(int id, String product, TransactionType type, double minAmount, double maxAmount) {
        return transactions.stream()
                .filter(prod -> id == 0 || prod.id() == id)
                .filter(prod -> product == null || prod.product().equalsIgnoreCase(product))
                .filter(prod -> type == null || prod.type().equals(type))
                .filter(prod -> minAmount == 0 || prod.amount() > minAmount)
                .filter(prod -> maxAmount == 0 || prod.amount() < maxAmount)
                .collect(toList());
    }

    public List<BudgetModel> byId(int id) {
        return transactions.stream()
                .filter(prod -> prod.id() == id)
                .collect(toList());
    }

    public BudgetModel add(BudgetModel budgetModel) {
        BudgetModel newBudgetModel = cloneWithId(this.transactions.size(), budgetModel);
        this.transactions.add(newBudgetModel);
        return newBudgetModel;
    }

    private BudgetModel cloneWithId(int id, BudgetModel budgetModel) {
        return new BudgetModel(
                id,
                budgetModel.product(),
                budgetModel.type(),
                budgetModel.amount()
        );
    }

    public Optional<BudgetModel> replace(int id, BudgetModel budgetModel) {
        return findById(id)
                .map(existing -> replaceExistingTransaction(id, existing, budgetModel));
    }

    private BudgetModel replaceExistingTransaction(int id, BudgetModel existing, BudgetModel budgetModel) {
        this.transactions.remove(existing);
        BudgetModel newTransaction = cloneWithId(id, budgetModel);
        this.transactions.add(id - 1, newTransaction);
        return newTransaction;
    }

    private Optional<BudgetModel> findById(int id) {
        return this.transactions.stream()
                .filter(trans -> trans.id() == id)
                .findFirst();
    }

    public Optional<BudgetModel> delete(int id) {
        Optional<BudgetModel> transactionToDelete = findById(id);
        transactionToDelete.ifPresent(this.transactions::remove);
        return transactionToDelete;
    }

    public Map<TransactionType, List<BudgetModel>> groupByType() {
        return transactions.stream()
                .collect(Collectors.groupingBy(BudgetModel::type));
    }

    public Map<String, List<BudgetModel>> groupByProduct() {
        return transactions.stream()
                .collect(Collectors.groupingBy(BudgetModel::product));
    }
}
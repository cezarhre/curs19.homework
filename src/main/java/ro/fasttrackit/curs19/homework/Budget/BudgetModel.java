package ro.fasttrackit.curs19.homework.Budget;

public record BudgetModel (
        int id,
        String product,
        TransactionType type,
        Double amount) {
}

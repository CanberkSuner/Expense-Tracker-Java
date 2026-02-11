import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    double monthlyBudget;
    List<Expense> expenses;

    public User(String name, double monthlyBudget) {
        this.name = name;
        this.monthlyBudget = monthlyBudget;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense e) {
        expenses.add(e);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public double getTotalSpent() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    public double getRemainingBudget() {
        return monthlyBudget - getTotalSpent();
    }

    public String getName() {
        return name;
    }
}

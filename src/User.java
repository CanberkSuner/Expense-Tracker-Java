import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private double monthlyBudget;
    private List<Expense> expenses;
    private double spentAtUpdate = 0; // Bütçe yenilendiğindeki ofset

    public User(String name, double monthlyBudget) {
        this.name = name;
        this.monthlyBudget = monthlyBudget;
        this.expenses = new ArrayList<>();
        this.spentAtUpdate = 0;
    }


    public void addExpense(Expense e) {
        if (e != null) {
            expenses.add(e);
        }
    }


    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
        this.spentAtUpdate = getTotalSpent();
    }

    public double getRemainingBudget() {

        double spentSinceUpdate = getTotalSpent() - spentAtUpdate;
        return monthlyBudget - spentSinceUpdate;
    }

    public double getTotalSpent() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }


    public void printExpenseSummary() {
        System.out.println("\n--- Expense Summary for " + name + " ---");
        for (Expense e : expenses) {
            System.out.println(" • " + e.toString());
        }
        System.out.println("--------------------------------");
        System.out.printf("Total Spent (All Time): %.2f TL%n", getTotalSpent());
        System.out.printf("Current Budget Limit: %.2f TL%n", monthlyBudget);
        System.out.printf("Remaining Budget: %.2f TL%n", getRemainingBudget());
    }

    public String getName() { return name; }
    public List<Expense> getExpenses() { return expenses; }
}
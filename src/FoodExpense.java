import java.time.LocalDate;

public class FoodExpense extends Expense {
    public FoodExpense(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public String getCategory() {
        return "Food";
    }
}

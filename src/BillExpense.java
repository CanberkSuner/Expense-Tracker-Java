import java.time.LocalDate;

public class BillExpense extends Expense {
    public BillExpense(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public String getCategory() {
        return "Bill";
    }
}

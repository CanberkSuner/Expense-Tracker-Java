import java.time.LocalDate;

public class TransportExpense extends Expense {
    public TransportExpense(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public String getCategory() {
        return "Transport";
    }
}

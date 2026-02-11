import java.io.Serializable;
import java.time.LocalDate;

abstract class Expense implements Serializable {
    String description;
    double amount;
    LocalDate date;

    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public abstract String getCategory();
    public double getAmount() { return amount; }
    public boolean isStarred() { return false; }
    public void setStarred(boolean s) {}

    @Override
    public String toString() {
        return String.format("%-10s | %-15s | %8.2f TL | %s", getCategory(), description, amount, date);
    }
}


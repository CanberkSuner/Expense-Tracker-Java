import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;

abstract class Expense {
    String description;
    double amount;
    LocalDate date;
    boolean starred = false;

    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public abstract String getCategory();

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}

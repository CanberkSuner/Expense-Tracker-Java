import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConsoleApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== EXPENSE TRACKER (CONSOLE MODE) ===");
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        double budget = 0;
        while (true) {
            try {
                System.out.print("Enter your monthly budget (TL): ");
                budget = input.nextDouble();
                if (budget < 0) {
                    System.out.println("Budget cannot be negative.");
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        input.nextLine(); 

        User user = new User(name, budget);

        System.out.print("How many expenses to log? ");
        int count = input.nextInt();
        input.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Expense " + (i + 1) + " ---");
            
            System.out.print("Category (Food/Transport/Bill): ");
            String cat = input.nextLine();
            
            System.out.print("Description: ");
            String desc = input.nextLine();
            
            System.out.print("Amount: ");
            double amount = input.nextDouble();
            input.nextLine();
            
            System.out.print("Date (yyyy-mm-dd): ");
            String dateStr = input.nextLine();
            LocalDate date = LocalDate.parse(dateStr);

            
            Expense e = null;
            if (cat.equalsIgnoreCase("food")) e = new FoodExpense(desc, amount, date);
            else if (cat.equalsIgnoreCase("transport")) e = new TransportExpense(desc, amount, date);
            else if (cat.equalsIgnoreCase("bill")) e = new BillExpense(desc, amount, date);
            else e = new FoodExpense(desc, amount, date); 

            user.addExpense(e);
        }

        System.out.println("\n--------------------------------");
        user.printExpenseSummary();
    }
}
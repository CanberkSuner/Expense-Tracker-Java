import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConsoleApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== EXPENSE TRACKER (BUDGET GUARD MODE) ===");

        System.out.print("Enter your name: ");
        String name = input.nextLine();

        // 1. BÜTÇE GİRİŞİ
        double budget = 0;
        while (true) {
            try {
                System.out.print("Enter your monthly budget (TL): ");
                budget = Double.parseDouble(input.nextLine());
                if (budget < 0) {
                    System.out.println("Budget cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        User user = new User(name, budget);

        // 2. ADET GİRİŞİ
        int count = 0;
        while (true) {
            try {
                System.out.print("How many expenses to log? ");
                count = Integer.parseInt(input.nextLine());
                if (count < 0) {
                    System.out.println("Count cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
            }
        }

        // 3. HARCAMA GİRİŞ DÖNGÜSÜ
        for (int i = 0; i < count; i++) {

            if (user.getRemainingBudget() <= 0) {
                System.out.println("\n!!! NO BUDGET LEFT. You cannot add more expenses !!!");
                break;
            }

            System.out.println("\n--- Expense " + (i + 1) + " (Remaining: " + String.format("%.2f", user.getRemainingBudget()) + " TL) ---");

            // KATEGORİ KONTROLÜ
            String cat = "";
            while (true) {
                System.out.print("Category (Food/Transport/Bill): ");
                cat = input.nextLine().trim();
                if (cat.equalsIgnoreCase("food") || cat.equalsIgnoreCase("transport") || cat.equalsIgnoreCase("bill")) break;
                System.out.println("Invalid category! Must be Food, Transport, or Bill.");
            }

            System.out.print("Description: ");
            String desc = input.nextLine();

            // --- MİKTAR KONTROLÜ (BÜTÇE AŞIMI ENGELLEME) ---
            double amount = 0;
            while (true) {
                try {
                    System.out.print("Amount: ");
                    amount = Double.parseDouble(input.nextLine());

                    if (amount < 0) {
                        System.out.println("Amount cannot be negative. Try again.");
                        continue;
                    }

                    // KONTROL
                    if (amount > user.getRemainingBudget()) {
                        System.out.printf("Insufficient budget! You only have %.2f TL left. Try a smaller amount.%n", user.getRemainingBudget());
                        continue;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a numeric value.");
                }
            }

            // TARİH KONTROLÜ
            LocalDate date = null;
            while (true) {
                try {
                    System.out.print("Date (yyyy-mm-dd) [Enter for Today]: ");
                    String dateStr = input.nextLine();
                    date = dateStr.trim().isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format! Use yyyy-mm-dd.");
                }
            }


            Expense e = switch (cat.toLowerCase()) {
                case "food" -> new FoodExpense(desc, amount, date);
                case "transport" -> new TransportExpense(desc, amount, date);
                case "bill" -> new BillExpense(desc, amount, date);
                default -> new FoodExpense(desc, amount, date);
            };

            user.addExpense(e);
        }

        System.out.println("\n--------------------------------");
        user.printExpenseSummary(); //
        System.out.println("--------------------------------");
    }
}
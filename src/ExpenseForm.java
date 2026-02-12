import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ExpenseForm {
    private static Map<String, DefaultListModel<Expense>> listModels = new HashMap<>();
    private static Map<String, JList<Expense>> expenseLists = new HashMap<>();
    private static JLabel messageLabel = new JLabel();
    private static JLabel summaryLabel;
    private static User user;

    public static void open(User u) {
        user = u;
        JFrame frame = new JFrame("Expense Tracker - Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 650);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // --- HEADER ---
        JPanel header = new JPanel(null);
        header.setBackground(new Color(230, 240, 250));
        header.setBounds(0, 0, 820, 60);

        JLabel logoLabel = new JLabel("Expense Tracker");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoLabel.setBounds(20, 15, 250, 30);
        header.add(logoLabel);

        summaryLabel = new JLabel("", SwingConstants.CENTER);
        summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        summaryLabel.setOpaque(true);
        summaryLabel.setBackground(Color.WHITE);
        summaryLabel.setForeground(new Color(0, 102, 153));
        summaryLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 1));
        summaryLabel.setBounds(470, 10, 320, 40);
        header.add(summaryLabel);
        mainPanel.add(header);

        // --- INPUTS ---
        int y = 75;
        addLabel(mainPanel, "Category:", y);
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food", "Transport", "Bill"});
        categoryBox.setBounds(150, y, 220, 30);
        mainPanel.add(categoryBox);

        addLabel(mainPanel, "Description:", y + 40);
        JTextField descField = new JTextField();
        descField.setBounds(150, y + 40, 220, 30);
        mainPanel.add(descField);

        addLabel(mainPanel, "Amount (TL):", y + 80);
        JTextField amountField = new JTextField();
        amountField.setBounds(150, y + 80, 220, 30);
        mainPanel.add(amountField);

        addLabel(mainPanel, "Date:", y + 120);
        JTextField dateField = new JTextField(LocalDate.now().toString());
        dateField.setBounds(150, y + 120, 220, 30);
        mainPanel.add(dateField);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(30, y + 240, 740, 280);


        JButton addButton = new JButton("Add Expense");
        addButton.setBounds(150, y + 170, 140, 35);
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        mainPanel.add(addButton);

        addButton.addActionListener(e -> {
            try {
                String desc = descField.getText().trim();
                String amountStr = amountField.getText().trim();
                String dateStr = dateField.getText().trim();
                String cat = (String) categoryBox.getSelectedItem();

                if (desc.isEmpty() || amountStr.isEmpty()) {
                    showError("Error: Please fill in all fields!");
                    return;
                }

                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    showError("Error: Amount must be positive!");
                    return;
                }

                // GÜVENLİK: Bütçe Aşımı Kontrolü
                if (amount > user.getRemainingBudget()) {
                    showError("Insufficient Budget! Max: " + String.format("%.2f", user.getRemainingBudget()) + " TL");
                    return;
                }

                LocalDate date = LocalDate.parse(dateStr);
                Expense exp = switch (cat.toLowerCase()) {
                    case "food" -> new FoodExpense(desc, amount, date);
                    case "transport" -> new TransportExpense(desc, amount, date);
                    case "bill" -> new BillExpense(desc, amount, date);
                    default -> null;
                };

                if (exp != null) {
                    user.addExpense(exp);
                    DataManager.saveUser(user);
                    updateTabs();
                    showSuccess("Expense added successfully!");
                    descField.setText("");
                    amountField.setText("");
                }
            } catch (Exception ex) {
                showError("Error: Check Amount or Date format!");
            }
        });

        // --- DELETE BUTTON ---
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBounds(310, y + 170, 160, 35);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        mainPanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex(); // Artık tabbedPane'i tanıyor!
            if (selectedTab != -1) {
                String tabTitle = tabbedPane.getTitleAt(selectedTab);
                List<Expense> selected = expenseLists.get(tabTitle).getSelectedValuesList();
                if (!selected.isEmpty()) {
                    user.getExpenses().removeAll(selected);
                    DataManager.saveUser(user);
                    updateTabs();
                    showSuccess("Selected expenses deleted.");
                }
            }
        });

        messageLabel.setBounds(30, y + 210, 500, 25);
        mainPanel.add(messageLabel);


        String[] cats = {"All", "Food", "Transport", "Bill"};
        for (String c : cats) {
            DefaultListModel<Expense> model = new DefaultListModel<>();
            JList<Expense> list = new JList<>(model);
            list.setCellRenderer(new ExpenseCellRenderer());
            tabbedPane.addTab(c, new JScrollPane(list));
            listModels.put(c, model);
            expenseLists.put(c, list);
        }
        mainPanel.add(tabbedPane);

        updateTabs();
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void addLabel(JPanel p, String t, int y) {
        JLabel l = new JLabel(t);
        l.setBounds(30, y, 120, 25);
        p.add(l);
    }

    private static void showError(String msg) {
        messageLabel.setForeground(Color.RED);
        messageLabel.setText(msg);
    }

    private static void showSuccess(String msg) {
        messageLabel.setForeground(new Color(0, 123, 0));
        messageLabel.setText(msg);
    }

    private static void updateTabs() {
        for (DefaultListModel<Expense> m : listModels.values()) m.clear();
        for (Expense e : user.getExpenses()) {
            listModels.get("All").addElement(e);
            if (listModels.containsKey(e.getCategory())) listModels.get(e.getCategory()).addElement(e);
        }
        summaryLabel.setText(String.format("Spent: %.2f TL | Remaining: %.2f TL", user.getTotalSpent(), user.getRemainingBudget()));
    }

    static class ExpenseCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Expense e) {
                String star = e.isStarred() ? "★ " : "☆ ";
                label.setText(star + e.toString());
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
            return label;
        }
    }
}
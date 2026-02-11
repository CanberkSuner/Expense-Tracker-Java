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
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        user = u;

        JFrame frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 640);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel header = new JPanel(null);
        header.setBackground(new Color(230, 240, 250));
        header.setBounds(0, 0, 800, 60);

        JLabel logoLabel = new JLabel("Expense Tracker");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoLabel.setBounds(20, 15, 250, 30);
        header.add(logoLabel);

        summaryLabel = new JLabel();
        summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        summaryLabel.setOpaque(true);
        summaryLabel.setBackground(new Color(255, 255, 255));
        summaryLabel.setForeground(new Color(0, 102, 153));
        summaryLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        summaryLabel.setBounds(460, 12, 320, 45);
        header.add(summaryLabel);

        mainPanel.add(header);

        // Form Labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        int yBase = 70;

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(labelFont);
        categoryLabel.setBounds(30, yBase, 100, 25);
        mainPanel.add(categoryLabel);

        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food", "Transport", "Bill"});
        categoryBox.setBounds(140, yBase, 220, 30);
        mainPanel.add(categoryBox);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(labelFont);
        descLabel.setBounds(30, yBase + 40, 100, 25);
        mainPanel.add(descLabel);

        JTextField descField = new JTextField();
        descField.setBounds(140, yBase + 40, 220, 30);
        mainPanel.add(descField);

        JLabel amountLabel = new JLabel("Amount (TL):");
        amountLabel.setFont(labelFont);
        amountLabel.setBounds(30, yBase + 80, 100, 25);
        mainPanel.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(140, yBase + 80, 220, 30);
        mainPanel.add(amountField);

        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd):");
        dateLabel.setFont(labelFont);
        dateLabel.setBounds(30, yBase + 120, 150, 25);
        mainPanel.add(dateLabel);

        JTextField dateField = new JTextField();
        dateField.setBounds(180, yBase + 120, 180, 30);
        mainPanel.add(dateField);

        JButton addButton = new JButton("Add Expense");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addButton.setBounds(140, yBase + 170, 150, 35);
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        mainPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteButton.setBounds(310, yBase + 170, 170, 35);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        mainPanel.add(deleteButton);

        messageLabel.setBounds(30, yBase + 215, 720, 25);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mainPanel.add(messageLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabbedPane.setBounds(30, yBase + 250, 720, 270);

        String[] categories = {"All", "Food", "Transport", "Bill"};
        for (String category : categories) {
            DefaultListModel<Expense> model = new DefaultListModel<>();
            JList<Expense> list = new JList<>(model);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setCellRenderer(new ExpenseCellRenderer());
            list.setFixedCellHeight(30);
            list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int index = list.locationToIndex(e.getPoint());
                        if (index >= 0) {
                            Expense exp = model.get(index);
                            exp.setStarred(!exp.isStarred());
                            list.repaint();
                        }
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            tabbedPane.addTab(category, scrollPane);
            listModels.put(category, model);
            expenseLists.put(category, list);
        }

        addButton.addActionListener((ActionEvent e) -> {
            String category = (String) categoryBox.getSelectedItem();
            String desc = descField.getText().trim();
            String amountText = amountField.getText().trim();
            String dateText = dateField.getText().trim();

            double amount;
            LocalDate date;

            if (desc.isEmpty() || amountText.isEmpty() || dateText.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            try {
                amount = Double.parseDouble(amountText);
                if (amount < 0) {
                    messageLabel.setText("Amount must be non-negative.");
                    return;
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid amount.");
                return;
            }

            try {
                date = LocalDate.parse(dateText);
            } catch (Exception ex) {
                messageLabel.setText("Invalid date format.");
                return;
            }

            Expense exp = switch (category.toLowerCase()) {
                case "food" -> new FoodExpense(desc, amount, date);
                case "transport" -> new TransportExpense(desc, amount, date);
                case "bill" -> new BillExpense(desc, amount, date);
                default -> null;
            };

            if (exp != null) {
                if (user.getRemainingBudget() < exp.getAmount()) {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Expense exceeds remaining budget!");
                    return;
                }
                user.addExpense(exp);
                updateTabs();
                messageLabel.setForeground(new Color(0, 102, 0));
                messageLabel.setText("Expense added successfully.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedTab = tabbedPane.getSelectedIndex();
            String tabTitle = tabbedPane.getTitleAt(selectedTab);
            JList<Expense> list = expenseLists.get(tabTitle);
            List<Expense> selected = list.getSelectedValuesList();
            if (!selected.isEmpty()) {
                user.getExpenses().removeAll(selected);
                updateTabs();
                messageLabel.setForeground(new Color(0, 102, 0));
                messageLabel.setText("Selected expenses deleted.");
            }
        });

        updateTabs();

        mainPanel.add(tabbedPane);
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void updateTabs() {
        for (DefaultListModel<Expense> model : listModels.values()) {
            model.clear();
        }

        for (Expense e : user.getExpenses()) {
            listModels.get("All").addElement(e);
            String category = e.getCategory();
            if (listModels.containsKey(category)) {
                listModels.get(category).addElement(e);
            }
        }

        summaryLabel.setText("Spent: " + String.format("%.2f", user.getTotalSpent()) + " TL   |   Remaining: " + String.format("%.2f", user.getRemainingBudget()) + " TL");
    }

    static class ExpenseCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Expense e) {
                String star = e.isStarred() ? "★" : "☆";
                label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                label.setFont(new Font("Dialog", Font.PLAIN, 14));
                String starIcon = e.isStarred() ? "★" : "☆";
                label.setText(starIcon + "  " + e.getCategory() + " | " + e.description + " | " + String.format("%.2f TL", e.amount) + " | " + e.date.toString());
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            }
            return label;
        }
    }
}

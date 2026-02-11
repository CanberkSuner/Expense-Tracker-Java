import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("Expense Tracker - User Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setBounds(30, 30, 80, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 30, 200, 25);
        panel.add(nameField);

        JLabel budgetLabel = new JLabel("Budget (TL):");
        budgetLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        budgetLabel.setBounds(30, 70, 100, 25);
        panel.add(budgetLabel);

        JTextField budgetField = new JTextField();
        budgetField.setBounds(120, 70, 200, 25);
        panel.add(budgetField);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        startButton.setBackground(new Color(0, 123, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBounds(120, 120, 100, 30);
        panel.add(startButton);

        JLabel errorLabel = new JLabel("");
        errorLabel.setBounds(30, 160, 300, 25);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(errorLabel);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String budgetText = budgetField.getText().trim();

                if (name.isEmpty() || budgetText.isEmpty()) {
                    errorLabel.setText("Please enter both name and budget.");
                    return;
                }

                double budget;
                try {
                    budget = Double.parseDouble(budgetText);
                    if (budget < 0) {
                        errorLabel.setText("Budget must be non-negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Invalid number for budget.");
                    return;
                }

                User user = new User(name, budget);
                frame.dispose();
                ExpenseForm.open(user);
            }
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

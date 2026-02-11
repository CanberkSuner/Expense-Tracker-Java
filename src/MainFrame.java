import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame {
    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}


        JFrame loginFrame = new JFrame("Expense Tracker - User Info");
        loginFrame.setSize(450, 320);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);
        loginFrame.getContentPane().setBackground(Color.WHITE);

        // --- NAME FIELD ---
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        loginFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(170, 50, 200, 35);
        loginFrame.add(nameField);

        // --- BUDGET FIELD ---
        JLabel budgetLabel = new JLabel("Budget (TL):");
        budgetLabel.setBounds(50, 110, 120, 30);
        loginFrame.add(budgetLabel);

        JTextField budgetField = new JTextField();
        budgetField.setBounds(170, 110, 200, 35);
        loginFrame.add(budgetField);

        // --- CLICKABLE INFO ICON  ---
        JLabel infoLabel = new JLabel("ⓘ");
        infoLabel.setFont(new Font("Serif", Font.BOLD, 20));
        infoLabel.setForeground(new Color(0, 123, 255)); // Stylish Blue
        infoLabel.setBounds(375, 110, 30, 35);
        infoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor changes on hover
        infoLabel.setToolTipText("Click for budget info");


        infoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(loginFrame,
                        "<html><body style='width: 250px;'>" +
                                "<b>Budget Information:</b><br><br>" +
                                "• <b>Existing Users:</b> Leave empty to continue using your stored budget.<br><br>" +
                                "• <b>Update:</b> Enter a new amount to update your monthly budget.<br><br>" +
                                "• <b>New Users:</b> Entering a budget is required for your first login.</body></html>",
                        "Budget Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        loginFrame.add(infoLabel);

        // --- START BUTTON ---
        JButton startButton = new JButton("Start");
        startButton.setBounds(170, 180, 120, 40);
        startButton.setBackground(new Color(0, 123, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginFrame.add(startButton);


        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String budgetText = budgetField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "Please enter a name!");
                return;
            }


            User loadedUser = DataManager.loadUser(name);
            User user;

            if (loadedUser == null) {

                try {
                    double budget = Double.parseDouble(budgetText);
                    user = new User(name, budget);
                    DataManager.saveUser(user);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginFrame, "A valid budget is required for new users!");
                    return;
                }
            } else {

                user = loadedUser;
                if (!budgetText.isEmpty()) {
                    try {
                        double newBudget = Double.parseDouble(budgetText);
                        user.setMonthlyBudget(newBudget);
                        DataManager.saveUser(user);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid budget format! Continuing with stored budget.");
                    }
                }
            }

            loginFrame.dispose();
            ExpenseForm.open(user);
        });

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }
}
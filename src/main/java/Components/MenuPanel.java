package Components;

import Screens.Employees;
import Screens.Help;
import Screens.RegisterEmployee;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private JFrame parentFrame;

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        String[] tabs = {
                "Employees", "Register Employee", "Attendance", "Leave Management",
                "Payroll", "Reports", "Help", "About"
        };

        // Create grid layout to evenly divide space for buttons
        JPanel tabPanel = new JPanel(new GridLayout(1, tabs.length)); // 1 row, N columns
        tabPanel.setPreferredSize(new Dimension(parentFrame.getWidth(), 60));
        tabPanel.setBackground(new Color(240, 240, 240)); // Light gray

        for (String tab : tabs) {
            JButton button = new JButton(tab);
            styleButton(button);
            addHoverEffect(button);
            button.addActionListener(e -> navigateTo(tab));
            tabPanel.add(button);
        }

        this.add(tabPanel, BorderLayout.NORTH);
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(200, 221, 242)); // Default soft blue
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void addHoverEffect(JButton button) {
        Color originalColor = new Color(200, 221, 242);
        Color hoverColor = new Color(144, 238, 144); // Light green

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
    }

    private void navigateTo(String tab) {
        parentFrame.dispose(); // Close the current window
        switch (tab) {
            case "Employees":
                Employees.main(null);
                break;
            case "Register Employee":
                RegisterEmployee.main(null);
                break;
            case "Help":
                Help.main(null);
                break;
            // Placeholder cases for future implementations
            case "Attendance":
            case "Leave Management":
            case "Payroll":
            case "Reports":
            case "About":
                JOptionPane.showMessageDialog(null, tab + " screen is under development.");
                break;
            default:
                System.out.println("No action defined for tab: " + tab);
                break;
        }
    }
}

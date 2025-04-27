package Components;

import Screens.Employees;
import Screens.Help;
import Screens.RegisterEmployee; // Import RegisterEmployee screen
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    private JFrame parentFrame;

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new GridLayout(1, 9));
        String[] tabs = new String[] { "Employees", "Register Employee", "Attendance", "Leave Management", "Payroll",
                "Reports", "Help", "About", "Settings" };
        for (String tab : tabs) {
            JButton button = new JButton(tab);
            button.setPreferredSize(new Dimension(100, 30));
            button.setFocusable(false);
            button.addActionListener(e -> navigateTo(tab)); // Lambda for navigation
            tabPanel.add(button);
        }

        this.add(tabPanel, BorderLayout.NORTH);
    }

    private void navigateTo(String tab) {
        this.parentFrame.dispose(); // Close the current window
        switch (tab) {
            case "Employees":
                Employees.main(null); // Open Employees screen
                break;
            case "Register Employee":
                RegisterEmployee.main(null); // Open RegisterEmployee screen
                break;
            case "Help":
                Help.main(null); // Open Help screen
                break;
            // Add more cases for other tabs
            // Example cases for other tabs
            case "Attendance":
            case "Leave Management":
            case "Payroll":
            case "Reports":
            case "About":
            case "Settings":
                // Handle other tabs similarly
                break;
            default:
                System.out.println("No action defined for tab: " + tab);
                break;
        }
    }
}

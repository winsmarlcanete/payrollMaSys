package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {

    private JFrame parentFrame;

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new GridLayout(1, 9)); // 9 tabs now
        String[] tabs = {"Employees", "Register Employee", "Attendance", "Leave Management", "Payroll", "Reports", "Help", "About", "Settings"};


        for (String tab : tabs) {
            JButton button = new JButton(tab);
            button.setPreferredSize(new Dimension(100, 30));
            button.setFocusable(false); // Remove focus outline

            // Set up action listeners
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigateTo(tab);
                }
            });

            tabPanel.add(button);
        }

        add(tabPanel, BorderLayout.NORTH);
    }

    private void navigateTo(String tab) {
        parentFrame.dispose(); // Close the current window

        switch (tab) {
            case "Employees":
                Screens.Employees.main(null); // Call Employees screen
                break;
            case "Help":
                Screens.Help.main(null); // Call Help screen
                break;
            // Add other cases here...
            case "Register Employee":
                // Screens.RegisterEmployee.main(null);
                break;
            case "Attendance":
                // Screens.Attendance.main(null);
                break;
            case "Leave Management":
                // Screens.LeaveManagement.main(null);
                break;
            case "Payroll":
                // Screens.Payroll.main(null);
                break;
            case "Reports":
                // Screens.Reports.main(null);
                break;
            case "About":
                // Screens.About.main(null);
                break;
            case "Settings":
                // Screens.Settings.main(null);
                break;
        }
    }
}

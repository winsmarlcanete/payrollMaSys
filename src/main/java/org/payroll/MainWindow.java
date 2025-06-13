package org.payroll;

import javax.swing.*;

// import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import Screens.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Synergy Grafix Corporation PMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1020, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized

        addWindowStateListener(e -> {
            // If restoring from maximized (not from minimized)
            if ((e.getOldState() & Frame.MAXIMIZED_BOTH) != 0 &&
                (e.getNewState() & Frame.MAXIMIZED_BOTH) == 0) {
                
                SwingUtilities.invokeLater(() -> {
                    setSize(1020, 600);            // Set your preferred restored size
                    setLocationRelativeTo(null);  // Center on screen
                });
            }
        });



        // Set the look and feel to FlatLaf (optional)
        // try {
        //     UIManager.setLookAndFeel(new FlatLightLaf());
        // } catch (Exception ex) {
        //     System.err.println("Failed to initialize FlatLaf");
        // }

        // Use CardLayout for switching panels
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Create panels for each section
        Employees employees = new Employees(this);
        RegisterEmployee regemployee = new RegisterEmployee();
        Attendance attendance = new Attendance();
        LeaveManagement leavemanagement = new LeaveManagement();
        Payroll payroll = new Payroll();
        Reports reports = new Reports();
        Help help = new Help();
        About about = new About();

        // Map for button names and panels
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Employees", employees);
        panelMap.put("Register Employee", regemployee);
        panelMap.put("Attendance", attendance);
        panelMap.put("Leave Management", leavemanagement);
        panelMap.put("Payroll", payroll);
        panelMap.put("Reports", reports);
        panelMap.put("Help", help);
        panelMap.put("About", about);

        // Add panels to cardPanel
        for (Map.Entry<String, JPanel> entry : panelMap.entrySet()) {
                    cardPanel.add(entry.getValue(), entry.getKey());
                }

                // Custom button panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
                buttonPanel.setBackground(Color.WHITE);

                Color activeColor = new Color(0, 153, 0);
                Color defaultColor = UIManager.getColor("Button.background");

                // Store buttons for color management
                Map<String, JButton> buttonMap = new LinkedHashMap<>();

                for (String name : panelMap.keySet()) {
                    JButton btn = new JButton(name);
                    btn.setFocusPainted(false);
                    btn.setBackground(defaultColor);
                    btn.setBorderPainted(false); // Hide border, keep padding
                    buttonPanel.add(btn);
                    buttonMap.put(name, btn);

                    btn.addActionListener(e -> {
                        cardLayout.show(cardPanel, name);
                        // Set active color for selected, default for others
                        for (Map.Entry<String, JButton> entry : buttonMap.entrySet()) {
                            if (entry.getKey().equals(name)) {
                                entry.getValue().setBackground(activeColor);
                                entry.getValue().setForeground(Color.WHITE);
                            } else {
                                entry.getValue().setBackground(defaultColor);
                                entry.getValue().setForeground(Color.BLACK);
                            }
                        }
                        // Clear Employees search field when switching to Employees panel
                        if (name.equals("Employees")) {
                            employees.clearSearchField();
                        }
                        // Clear Leave Management search field when switching to Leave Management panel
                        if (name.equals("Leave Management")) {
                            leavemanagement.clearSearchField();
                        }
                    });
                }

        // Set the first button as active
        if (!buttonMap.isEmpty()) {
            JButton firstBtn = buttonMap.values().iterator().next();
            firstBtn.setBackground(activeColor);
            firstBtn.setForeground(Color.WHITE);
        }

        // Add a green separator panel below the buttons
        JPanel greenSeparator = new JPanel();
        greenSeparator.setBackground(activeColor);
        greenSeparator.setPreferredSize(new Dimension(0, 6)); // 6px tall

        // Layout
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(greenSeparator, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
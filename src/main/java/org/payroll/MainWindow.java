package org.payroll;

import javax.swing.*;

// import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import Module.E201File.E201File;
import Module.Payroll.Payroll;
import Screens.*;

public class MainWindow extends JFrame {
    public static Color activeColor = new Color(0, 128, 0);

    private Object[][] employeeTableData;
    public MainWindow() {
        setTitle("Synergy Grafix Corporation PMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1020, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("logo_only.png"));
        setIconImage(windowIcon.getImage());

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

//        try {
//            UIManager.setLookAndFeel(new FlatLightLaf());
//        } catch (Exception ex) {
//            System.err.println("Failed to initialize FlatLaf");
//        }

        // Set the look and feel to FlatLaf (optional)


        // Use CardLayout for switching panels
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Create panels for each section
        employeeTableData = E201File.getEmployeeTableData();
        Employees employees = new Employees(this, employeeTableData);
        RegisterEmployee regemployee = new RegisterEmployee();
        Attendance attendance = new Attendance();
        LeaveManagement leavemanagement = new LeaveManagement();
        PayrollScreen payroll = new PayrollScreen();
        Reports reports = new Reports();
        Help help = new Help();
        About about = new About();

        // Map for button names and panels
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Employees", employees);
        panelMap.put("Register", regemployee);
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

                Color activeColor = new Color(0, 158, 0);
                Color defaultColor = UIManager.getColor("Button.background");

                // Store buttons for color management
                Map<String, JButton> buttonMap = new LinkedHashMap<>();

                for (String name : panelMap.keySet()) {
                    JButton btn = new JButton(name);
                    btn.setFont(new Font("Arial", Font.PLAIN, 16));
                    btn.setFocusPainted(false);
//                    btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, 35));
//                    btn.setMaximumSize(new Dimension(btn.getPreferredSize().width, 35));
                    btn.setPreferredSize(new Dimension(0, 45));
                    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
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
                                entry.getValue().setFont(new Font("Arial", Font.BOLD, 16));
                            } else {
                                entry.getValue().setBackground(defaultColor);
                                entry.getValue().setForeground(Color.BLACK);
                                entry.getValue().setFont(new Font("Arial", Font.PLAIN, 16));
                            }
                        }
                        // Clear Employees search field when switching to Employees panel
                        if (name.equals("Employees")) {
                            employees.clearSearchField();
                            employeeTableData = E201File.getEmployeeTableData();
                            employees.loadEmployeeTabledata();
                        }

                        if (name.equals("Attendance")) {
                            attendance.loadEmployeeTabledata(); // 👈 this refreshes the table
                        }
                        // Clear Leave Management search field when switching to Leave Management panel
                        if (name.equals("Leave Management")) {
                            leavemanagement.clearSearchField();
                            leavemanagement.loadEmployeeTabledata(); // 👈 this refreshes the table

                        }

                        if (name.equals("PayrollScreen")) {
                            Payroll.retrieveAllPayrolls();
                            java.sql.Date periodStart = java.sql.Date.valueOf("2024-10-21"); // Set to 2024-10-21
                            java.sql.Date periodEnd = java.sql.Date.valueOf("2024-11-05");   // Set to 2024-11-05
                            Payroll.loadTimecards(periodStart, periodEnd);
                        }
                    });
                }

        ImageIcon userIcon = new ImageIcon(
                new ImageIcon(getClass().getClassLoader().getResource("User.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton currentUser = new JButton("Human Resources");
        currentUser.setFont(new Font("Arial", Font.PLAIN, 16));
        currentUser.setFocusPainted(false);
        currentUser.setBackground(Color.BLACK);
        currentUser.setForeground(Color.WHITE);
        currentUser.setIconTextGap(10);
        currentUser.setBorderPainted(false);
        currentUser.setIcon(userIcon);
        currentUser.setPreferredSize(new Dimension(200, 45));
        currentUser.setMaximumSize(new Dimension(200, 45));

        buttonPanel.add(currentUser);


        // Set the first button as active
        if (!buttonMap.isEmpty()) {
            JButton firstBtn = buttonMap.values().iterator().next();
            firstBtn.setBackground(activeColor);
            firstBtn.setForeground(Color.WHITE);
            firstBtn.setFont(new Font("Arial", Font.BOLD, 16));
        }

        // Layout
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
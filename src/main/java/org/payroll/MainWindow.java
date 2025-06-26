package org.payroll;

import javax.swing.*;

// import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import Module.E201File.E201File;
import Module.Payroll.Payroll;
import Screens.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {
    public static Color activeColor = new Color(0, 128, 0);
    public static Color grayColor = new Color(217, 217, 217);
    private JPanel overlayPanel;

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
        employees.loadEmployeeTabledata();
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
                    btn.setBackground(new Color(217, 217, 217));
                    btn.setBorderPainted(false); // Hide border, keep padding
                    buttonPanel.add(btn);
                    buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
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
                                entry.getValue().setBackground(new Color(217, 217, 217));
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

//                        if (name.equals("Payroll")) {
//
//                            System.out.println("Loading Payroll screen...");
//
//                            // Create a JOptionPane with a message and progress bar
//                            JOptionPane optionPane = new JOptionPane(
//                                    "Payroll is being updated...",
//                                    JOptionPane.INFORMATION_MESSAGE,
//                                    JOptionPane.DEFAULT_OPTION,
//                                    null,
//                                    new Object[]{}, // No buttons
//                                    null
//                            );
//
//                            JDialog dialog = optionPane.createDialog(payroll, "Updating Payroll");
//                            dialog.setModal(true);
//                            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Disable X button
//
//                            // Run updatePayrollDetails in the background
//                            ExecutorService executorService = Executors.newSingleThreadExecutor();
//                            executorService.submit(() -> {
//                                System.out.println("Running updatePayrollDetails in the background...");
//                                java.sql.Date periodStart = java.sql.Date.valueOf("2024-10-21");
//                                java.sql.Date periodEnd = java.sql.Date.valueOf("2024-11-05");
//                                Payroll.retrieveAllTimecards(periodStart, periodEnd);
//                                System.out.println("updatePayrollDetails completed.");
//
//                                SwingUtilities.invokeLater(() -> {
//                                    System.out.println("Refreshing Payroll UI...");
//                                    Payroll.retrieveAllPayrolls(periodStart,periodEnd);
//
//                                    dialog.dispose(); // Close the dialog
//                                    payroll.repaint();
//                                });
//                            });
//
//                            executorService.shutdown();
//                            dialog.setVisible(true);
//                        }
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

        // Add action listener to currentUser button
        currentUser.addActionListener(e -> {
            // Create popup menu
            JPopupMenu menu = new JPopupMenu();
            menu.setPreferredSize(new Dimension(200, 80));
            menu.setBackground(Color.BLACK);
            menu.setForeground(Color.WHITE);
            menu.setFont(new Font("Arial", Font.PLAIN, 14));

            // Create menu items
            JMenuItem signOut = new JMenuItem("Sign Out");
            JMenuItem cancel = new JMenuItem("Cancel");

            // Style menu items
            Font menuFont = new Font("Arial", Font.PLAIN, 14);
            signOut.setFont(menuFont);
            cancel.setFont(menuFont);

            // Set colors for menu items
            signOut.setBackground(Color.BLACK);
            signOut.setForeground(Color.WHITE);
            cancel.setBackground(Color.BLACK);
            cancel.setForeground(Color.WHITE);

            // Remove menu item borders
            signOut.setBorderPainted(false);
            cancel.setBorderPainted(false);

            // Add action listeners to menu items
            signOut.addActionListener(event -> {
                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to sign out?",
                        "Sign Out",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    dispose(); // Close current window
                    // Start new instance of the application
                    SwingUtilities.invokeLater(() -> {
                        Login loginScreen = new Login();
                        loginScreen.setVisible(true);
                        loginScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    });
                }
            });

            cancel.addActionListener(event -> menu.setVisible(false));

            // Add items to menu
            menu.add(signOut);
            menu.add(cancel);

            // Show popup menu below the button
            menu.show(currentUser, 0, currentUser.getHeight());
        });

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
            MainWindow frame = new MainWindow();
            frame.setVisible(true);

            PayrollScreen payroll = new PayrollScreen();

            // Create a modeless dialog to indicate updating
            JDialog updatingDialog = new JDialog(frame, "Please wait", false);
            updatingDialog.setLayout(new BorderLayout());
            updatingDialog.add(new JLabel("Updating payroll, please wait...", SwingConstants.CENTER), BorderLayout.CENTER);
            updatingDialog.setSize(300, 100);
            updatingDialog.setLocationRelativeTo(frame);

            // Show the dialog
            updatingDialog.setVisible(false);

            // Scheduler to run update every 10 seconds
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Running updatePayrollDetails in the background...");
                Date periodStart = Date.valueOf("2025-04-01");
                Date periodEnd = Date.valueOf("2025-04-15");

                Payroll.retrieveAllTimecards(periodStart, periodEnd);
                System.out.println("updatePayrollDetails completed.");

                SwingUtilities.invokeLater(() -> {
                    System.out.println("Refreshing Payroll UI...");
                    Payroll.retrieveAllPayrolls(periodStart, periodEnd);
                    payroll.repaint();
                    payroll.revalidate();

                    if (!updatingDialog.isVisible()) {
                        updatingDialog.setVisible(false);
                    }
                });
            }, 0, 10, TimeUnit.SECONDS);

            // Shutdown the scheduler when window closes
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    scheduler.shutdown();
                    updatingDialog.dispose();
                }
            });
        });
    }
}
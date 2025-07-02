package org.payroll;

import javax.swing.*;

// import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import Module.E201File.E201File;
import Module.Payroll.Payroll;
import Screens.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame {

    private Component currentPanel;
    public static Color activeColor = new Color(0, 128, 0);
    public static Color grayColor = new Color(217, 217, 217);
    private JPanel overlayPanel;

    private Object[][] employeeTableData;
    public MainWindow(int access_level) {
        setTitle("Synergy Grafix Corporation PMS");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        MainWindow.this,
                        "Are you sure you want to sign out?",
                        "Sign Out",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        Login loginScreen = new Login();
                        loginScreen.setVisible(true);
                        loginScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    });
                }
            }
        });
        setMinimumSize(new Dimension(1020, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized

        PayrollScreen payroll = new PayrollScreen();

        // Create a modeless dialog to indicate updating
        JDialog updatingDialog = new JDialog(this, "Please wait", false);
        updatingDialog.setLayout(new BorderLayout());
        updatingDialog.add(new JLabel("Updating payroll, please wait...", SwingConstants.CENTER), BorderLayout.CENTER);
        updatingDialog.setSize(300, 100);
        updatingDialog.setLocationRelativeTo(this);

        // Scheduler to run update every 10 seconds
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Running updatePayrollDetails in the background...");
            Date periodStart = Date.valueOf("2025-05-01");
            Date periodEnd = Date.valueOf("2025-05-15");

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

        JLabel currentUserLabel = new JLabel();
        if (access_level == 1) {
            currentUserLabel.setText("Admin");
        } else if (access_level == 2) {
            currentUserLabel.setText("Human Resource");
        } else if (access_level == 3) {
            currentUserLabel.setText("Accountant");
        } else {
            currentUserLabel.setText("Unknown");
        }

        Employees employees = new Employees(this, employeeTableData);
        employees.loadEmployeeTabledata();
        RegisterEmployee regemployee = new RegisterEmployee();
        Attendance attendance = new Attendance();
        LeaveManagementScreen leavemanagement = new LeaveManagementScreen();

        Reports reports = new Reports(currentUserLabel.getText());
        Help help = new Help();
        About about = new About();
        UserControl usercontrol = new UserControl();

        // Map for button names and panels
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("E-201 File", employees);
        panelMap.put("Register Employee", regemployee);
        panelMap.put("Attendance", attendance);
        panelMap.put("Leave Management", leavemanagement);
        panelMap.put("Payroll", payroll);
        panelMap.put("Reports", reports);
        panelMap.put("Help", help);
        panelMap.put("About", about);
        panelMap.put("User Control", usercontrol);

        // Add panels to cardPanel
        for (Map.Entry<String, JPanel> entry : panelMap.entrySet()) {
                    cardPanel.add(entry.getValue(), entry.getKey());
                }

                // Custom button panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.gridy = 0;

        // Calculate total label width
                int totalLabelWidth = 0;
                Map<String, Integer> labelWidths = new LinkedHashMap<>();
                Font buttonFont = new Font("Arial", Font.PLAIN, 16);
                for (String name : panelMap.keySet()) {
                    JButton tempBtn = new JButton(name);
                    tempBtn.setFont(buttonFont);
                    FontMetrics metrics = tempBtn.getFontMetrics(buttonFont);
                    int width = metrics.stringWidth(name) + 10; // 15px padding each side
                    labelWidths.put(name, width);
                    totalLabelWidth += width;
                }

        // Add buttons with proportional weightx
                int i = 0;

                Color activeColor = new Color(0, 158, 0);
                Color defaultColor = UIManager.getColor("Button.background");

                // Store buttons for color management
                Map<String, JButton> buttonMap = new LinkedHashMap<>();

                for (String name : panelMap.keySet()) {
                    JButton btn = new JButton(name);
                    btn.setFont(buttonFont);
                    btn.setFocusPainted(false);
                    btn.setBackground(new Color(217, 217, 217));
                    btn.setBorderPainted(false);
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    btn.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            if (btn.getBackground() != activeColor) {
                                btn.setBackground(new Color(200, 200, 200));
                            }
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            if (btn.getBackground() != activeColor) {
                                btn.setBackground(new Color(217, 217, 217));
                            }
                        }
                    });

                    gbc.gridx = i++;
                    gbc.weightx = (double) labelWidths.get(name) / totalLabelWidth;
                    gbc.insets = new Insets(0, 0, 0, 1); // 1px gap between buttons

                    buttonPanel.add(btn, gbc);
                    buttonMap.put(name, btn);

                    btn.addActionListener(e -> {
                        if (currentPanel instanceof Employees) {
                            Employees employeesPanel = (Employees) currentPanel;
                            // Add getter method in Employees class to access hasUnsavedChanges
                            if (employeesPanel.getHasUnsavedChanges()) {
                                int choice = JOptionPane.showConfirmDialog(
                                        MainWindow.this,
                                        "You have unsaved changes. Do you want to continue without saving?",
                                        "Unsaved Changes",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE
                                );

                                if (choice != JOptionPane.YES_OPTION) {
                                    return; // Don't switch if user chooses not to continue
                                }

                                // Reset edit mode if user chooses to continue
                                employeesPanel.resetEditMode();
                            }
                        }

                        cardLayout.show(cardPanel, name);
                        currentPanel = panelMap.get(name);
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

        if (!panelMap.isEmpty()) {
            currentPanel = panelMap.values().iterator().next();
        }

        ImageIcon userIcon = new ImageIcon(
                new ImageIcon(getClass().getClassLoader().getResource("User.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        if (currentUserLabel.getText().equals("Admin")) {
            buttonMap.get("User Control").setVisible(true);
        } else {
            buttonMap.get("User Control").setVisible(false);
        }


        if (currentUserLabel.getText().equals("Accountant")) {
            buttonMap.get("Attendance").setVisible(false);
            buttonMap.get("Leave Management").setVisible(false);
            buttonMap.get("Register Employee").setVisible(false);

        }


        JButton currentUser = new JButton();
        currentUser.setText(currentUserLabel.getText());
        currentUser.setFont(new Font("Arial", Font.PLAIN,16));
        currentUser.setFocusPainted(false);
        currentUser.setBackground(Color.BLACK);
        currentUser.setForeground(Color.WHITE);
        currentUser.setIconTextGap(10);
        currentUser.setBorderPainted(false);
        currentUser.setIcon(userIcon);
        currentUser.setPreferredSize(new Dimension(200, 45));
        currentUser.setMaximumSize(new Dimension(200, 45));
        currentUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        currentUser.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                currentUser.setBackground(new Color(40, 40, 40)); // Slightly lighter black
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                currentUser.setBackground(Color.BLACK);
            }
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


}
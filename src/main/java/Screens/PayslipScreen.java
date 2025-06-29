package Screens;

import Components.BlackRoundedComboBox;
import Components.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import Components.PayslipGenerator;
//import Entity.Payslip;
import Components.RoundedPanel;
import Entity.Payslip;
import Module.E201File.E201File;
import Module.Payroll.Payroll;
import Components.PayslipGenerator;

public class PayslipScreen extends JPanel {
    private BlackRoundedComboBox<String> deptCombo;
    private BlackRoundedComboBox<String> periodCombo;
    private RoundedButton viewButton;
    private RoundedButton downloadPdf;
    private RoundedPanel deptComboPanel;
    private Date startDate;
    private Date endDate;

    private String[] generatePayrollPeriods() {
        List<String> periods = new ArrayList<>();
        periods.add("Select payroll period"); // Add default option

        // Retrieve all periods from Payroll.java
        List<Date[]> retrievedPeriods = Payroll.retrieveAllPeriods();

        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        // Format and add each period to the list
        for (Date[] period : retrievedPeriods) {
            String formattedStartDate = formatter.format(period[0]);
            String formattedEndDate = formatter.format(period[1]);
            periods.add(formattedStartDate + " - " + formattedEndDate);
        }

        return periods.toArray(new String[0]);
    }

    private void updateComponentStates() {
        boolean periodSelected = !periodCombo.getSelectedItem().toString().equals("Select payroll period");
        boolean deptSelected = !deptCombo.getSelectedItem().toString().equals("Choose a department");

        // Define colors
        Color disabledBgColor = new Color(200, 200, 200, 180); // Light gray with transparency
        Color enabledBgColor = Color.BLACK;
        Color disabledFgColor = new Color(128, 128, 128); // Gray text for disabled state
        Color enabledFgColor = Color.WHITE;

        // Update department combo
        deptCombo.setEnabled(periodSelected);
        if (!periodSelected) {
            deptCombo.setBackground(disabledBgColor);
            deptCombo.setForeground(disabledFgColor);
            deptComboPanel.setBackground(disabledBgColor);
        } else {
            deptCombo.setBackground(enabledBgColor);
            deptCombo.setForeground(enabledFgColor);
            deptComboPanel.setBackground(enabledBgColor);
        }

        // Update buttons
        boolean buttonsEnabled = periodSelected && deptSelected;

        viewButton.setEnabled(buttonsEnabled);
        downloadPdf.setEnabled(buttonsEnabled);

        if (!buttonsEnabled) {
            viewButton.setBackground(disabledBgColor);
            viewButton.setForeground(disabledFgColor);
            downloadPdf.setBackground(disabledBgColor);
            downloadPdf.setForeground(disabledFgColor);
        } else {
            viewButton.setBackground(enabledBgColor);
            viewButton.setForeground(enabledFgColor);
            downloadPdf.setBackground(enabledBgColor);
            downloadPdf.setForeground(enabledFgColor);
        }
    }

    public PayslipScreen() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Retrieve departments from E201File
        String[] departments = E201File.retrieveAllDepartments();
        List<String> departmentsList = new ArrayList<>(Arrays.asList(departments));
        departmentsList.add(0, "Choose a department");
        String[] departmentsArray = departmentsList.toArray(new String[0]);

        String[] payrollPeriods = generatePayrollPeriods();
        periodCombo = new BlackRoundedComboBox<>(payrollPeriods) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };
        periodCombo.setSelectedItem("Select payroll period");
        periodCombo.setPreferredSize(new Dimension(250, 40)); // Reduce width, match button height
        periodCombo.setMinimumSize(new Dimension(250, 40));   // Ensure minimum size
        periodCombo.setMaximumSize(new Dimension(250, 40));   // Ensure maximum size
        periodCombo.setFocusable(false);
        periodCombo.setMaximumRowCount(12);
        periodCombo.setFont(new Font("Arial", Font.BOLD, 16));
        ((JLabel) periodCombo.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        periodCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        periodCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                periodCombo.setBackground(new Color(40, 40, 40));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                periodCombo.setBackground(Color.BLACK);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                periodCombo.setBackground(new Color(100, 100, 100));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                periodCombo.setBackground(periodCombo.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        // Use GridBagLayout for vertical centering
        RoundedPanel periodComboPanel = new RoundedPanel(10);
        periodComboPanel.setLayout(new GridBagLayout());
        periodComboPanel.setBackground(Color.BLACK);
        periodComboPanel.setOpaque(false);
        periodComboPanel.setPreferredSize(new Dimension(355, 50)); // Match button height

        periodComboPanel.setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbcCombo = new GridBagConstraints();
        gbcCombo.gridx = 0;
        gbcCombo.gridy = 0;
        gbcCombo.anchor = GridBagConstraints.CENTER;
        gbcCombo.insets = new Insets(0, 10, 0, 10); // Horizontal spacing only

        JLabel sortLabel = new JLabel("Period: ");
        sortLabel.setForeground(Color.WHITE);
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setBorder(new EmptyBorder(0, 13, 0, 0));
        periodComboPanel.add(sortLabel, gbcCombo);

        gbcCombo.gridx = 1;
        periodComboPanel.add(periodCombo, gbcCombo);

        // Dropdown
        deptCombo = new BlackRoundedComboBox<>(departmentsArray) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };
        deptCombo.setSelectedItem("Choose department");
        deptCombo.setPreferredSize(new Dimension(250, 40)); // Reduce width, match button height
        deptCombo.setMinimumSize(new Dimension(250, 40));   // Ensure minimum size
        deptCombo.setMaximumSize(new Dimension(250, 40));   // Ensure maximum size
        deptCombo.setFocusable(false);
        deptCombo.setMaximumRowCount(12);
        deptCombo.setFont(new Font("Arial", Font.BOLD, 16));
        ((JLabel) deptCombo.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        deptCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deptCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                deptCombo.setBackground(new Color(40, 40, 40));
                updateComponentStates();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                deptCombo.setBackground(Color.BLACK);
                updateComponentStates();
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                deptCombo.setBackground(new Color(100, 100, 100));
                if (!deptCombo.isEnabled()) {
                    JOptionPane.showMessageDialog(PayslipScreen.this,
                            "Choose a payroll period first.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                deptCombo.setBackground(deptCombo.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        // Use GridBagLayout for vertical centering
        deptComboPanel = new RoundedPanel(10);
        deptComboPanel.setLayout(new GridBagLayout());
        deptComboPanel.setBackground(Color.BLACK);
        deptComboPanel.setOpaque(false);
        deptComboPanel.setPreferredSize(new Dimension(385, 50)); // Match button height

        deptComboPanel.setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbcCombo2 = new GridBagConstraints();
        gbcCombo2.gridx = 0;
        gbcCombo2.gridy = 0;
        gbcCombo2.anchor = GridBagConstraints.CENTER;
        gbcCombo2.insets = new Insets(0, 10, 0, 10); // Horizontal spacing only

        JLabel deptLabel = new JLabel("Department: ");
        deptLabel.setForeground(Color.WHITE);
        deptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        deptLabel.setBorder(new EmptyBorder(0, 13, 0, 0));
        deptComboPanel.add(deptLabel, gbcCombo2);

        gbcCombo2.gridx = 1;
        deptComboPanel.add(deptCombo, gbcCombo2);

        downloadPdf = new RoundedButton("Download PDF", 10);
        downloadPdf.setPreferredSize(new Dimension(150, 50));
        downloadPdf.setFont(new Font("Arial", Font.BOLD, 16));
        downloadPdf.setForeground(Color.WHITE);
        downloadPdf.setBackground(new Color(0, 0, 0));
        downloadPdf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        downloadPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(new Color(40, 40, 40)); // Slightly lighter black
                updateComponentStates();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(Color.BLACK);
                updateComponentStates();
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(new Color(100, 100, 100)); // Even lighter on click
                if (!downloadPdf.isEnabled()) {
                    JOptionPane.showMessageDialog(PayslipScreen.this,
                            "Choose a payroll period and department first.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(downloadPdf.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        downloadPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String selectedPeriod = (String) periodCombo.getSelectedItem();
                    String filePath = "payslips/payslip_" + selectedPeriod + ".pdf";
                    String[] dates = selectedPeriod.split(" - ");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

                    // Parse the start and end dates
                     startDate = new java.sql.Date(dateFormat.parse(dates[0].trim()).getTime());
                    endDate = new java.sql.Date(dateFormat.parse(dates[1].trim()).getTime());

                    // Retrieve payslips and generate PDF
                    List<Payslip> payslipList = Payroll.retrieveAllPayslip(deptCombo.getSelectedItem().toString(), startDate, endDate);
                    PayslipGenerator.generatePayslip(filePath, payslipList);
                    JOptionPane.showMessageDialog(PayslipScreen.this, "Payslip generated: " + filePath);

                    // Reset selections
                    periodCombo.setSelectedItem("Select payroll period");
                    deptCombo.setSelectedItem("Choose a department");
                    updateComponentStates();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PayslipScreen.this, "Invalid date format selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewButton = new RoundedButton("View PDF", 10);

        viewButton.addActionListener(e -> {
            try {
                String selectedPeriod = (String) periodCombo.getSelectedItem();
                String[] dates = selectedPeriod.split(" - ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

                // Parse the start and end dates
                startDate = new java.sql.Date(dateFormat.parse(dates[0].trim()).getTime());
                endDate = new java.sql.Date(dateFormat.parse(dates[1].trim()).getTime());

                // Retrieve payslips and preview PDF
                List<Payslip> payslipList = Payroll.retrieveAllPayslip(deptCombo.getSelectedItem().toString(), startDate, endDate);
                PayslipGenerator.previewPayslip(payslipList);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(PayslipScreen.this, "Invalid date format selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewButton.setPreferredSize(new Dimension(150, 50));
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(0, 0, 0));
        viewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                viewButton.setBackground(new Color(40, 40, 40));
                updateComponentStates();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                viewButton.setBackground(Color.BLACK);
                updateComponentStates();
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                viewButton.setBackground(new Color(100, 100, 100));
                if (!viewButton.isEnabled()) {
                    JOptionPane.showMessageDialog(PayslipScreen.this,
                            "Choose a payroll period and department first.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                viewButton.setBackground(viewButton.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        deptCombo.setEnabled(false);
        viewButton.setEnabled(false);
        downloadPdf.setEnabled(false);
        updateComponentStates();

        periodCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateComponentStates();
            }
        });

        deptCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateComponentStates();
            }
        });

        // Create a container panel for the three components
        JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(periodComboPanel);
        containerPanel.add(deptComboPanel);
        containerPanel.add(viewButton);
        containerPanel.add(downloadPdf);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(containerPanel, gbc);

        add(topPanel, BorderLayout.NORTH);
    }
}
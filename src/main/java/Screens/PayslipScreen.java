package Screens;

import Components.BlackRoundedComboBox;
import Components.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Components.PayslipGenerator;
import Entity.Payslip;
import Module.E201File.E201File;
import Module.Payroll.Payroll;

public class PayslipScreen extends JPanel {

    public PayslipScreen() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Retrieve departments from E201File
        String[] departments = E201File.retrieveAllDepartments();
        List<String> departmentsList = new ArrayList<>(Arrays.asList(departments));
        String[] departmentsArray = departmentsList.toArray(new String[0]);

        // Dropdown
        BlackRoundedComboBox<String> sortCombo = new BlackRoundedComboBox<>(departmentsArray) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };
        sortCombo.setPreferredSize(new Dimension(250, 50));
        sortCombo.setFocusable(false);
        sortCombo.setMaximumRowCount(12);
        sortCombo.setFont(new Font("Arial", Font.BOLD, 16));
        ((JLabel) sortCombo.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Use GridBagLayout for vertical centering
        JPanel comboPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCombo = new GridBagConstraints();
        gbcCombo.gridx = 0;
        gbcCombo.gridy = 0;
        gbcCombo.anchor = GridBagConstraints.CENTER;
        JLabel sortLabel = new JLabel("Department: ");
        sortLabel.setForeground(Color.WHITE);
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
        comboPanel.add(sortLabel, gbcCombo);

        gbcCombo.gridx = 1;
        comboPanel.add(sortCombo, gbcCombo);
        comboPanel.setOpaque(true);
        comboPanel.setBackground(Color.BLACK);

        RoundedButton downloadPdf = new RoundedButton("Download PDF", 10);
        downloadPdf.setPreferredSize(new Dimension(150, 50));
        downloadPdf.setFont(new Font("Arial", Font.BOLD, 16));
        downloadPdf.setForeground(Color.WHITE);
        downloadPdf.setBackground(new Color(0, 0, 0));
        downloadPdf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        downloadPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(new Color(40, 40, 40)); // Slightly lighter black
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(Color.BLACK);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(new Color(100, 100, 100)); // Even lighter on click
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(downloadPdf.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        downloadPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "payslip.pdf";

                Payslip payslip = Payroll.retrievePayslip( 253, Date.valueOf("2025-05-01"), Date.valueOf("2025-05-15"));
                PayslipGenerator.generatePayslip(filePath, payslip);
                JOptionPane.showMessageDialog(PayslipScreen.this, "Payslip generated: " + filePath);
            }
        });

        JButton viewButton = new JButton("View PDF");
        Payslip payslip = Payroll.retrievePayslip( 253, Date.valueOf("2025-05-01"), Date.valueOf("2025-05-15"));
        viewButton.addActionListener(e -> PayslipGenerator.previewPayslip(payslip));
        viewButton.setPreferredSize(new Dimension(150, 50));
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(0, 0, 0));
        viewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                viewButton.setBackground(new Color(40, 40, 40)); // Slightly lighter black
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                viewButton.setBackground(Color.BLACK);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                viewButton.setBackground(new Color(100, 100, 100)); // Even lighter on click
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                viewButton.setBackground(viewButton.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(comboPanel);
        topPanel.add(Box.createHorizontalStrut(20)); // space between buttons
        topPanel.add(viewButton);
        topPanel.add(Box.createHorizontalStrut(20)); // space between buttons
        topPanel.add(downloadPdf);
        topPanel.add(Box.createHorizontalGlue());

        add(topPanel, BorderLayout.NORTH);
    }
}
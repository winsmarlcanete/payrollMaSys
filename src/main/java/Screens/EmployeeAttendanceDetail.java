package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SpinnerDateModel;

public class EmployeeAttendanceDetail extends JPanel {

    public EmployeeAttendanceDetail(CardLayout cardLayout, JPanel mainContainer, Object[] employeeInfo) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 139, 34)); // Match green background

        // --- Back Button ---
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainContainer, "table"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(new Color(34, 139, 34));
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        backPanel.add(backButton);

        // --- Info Panel ---
        JPanel infoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        infoPanel.setBackground(new Color(34, 139, 34));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        infoPanel.add(createInfoField("Name", employeeInfo[0].toString()));
        infoPanel.add(createInfoField("ID", employeeInfo[1].toString()));
        infoPanel.add(createInfoField("Department", employeeInfo[2].toString()));
        infoPanel.add(createInfoField("Employment", employeeInfo[3].toString()));
        infoPanel.add(createInfoField("Shift start", "9:00"));
        infoPanel.add(createInfoField("Shift end", "17:00"));

        // --- Period Panel ---
        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        periodPanel.setBackground(new Color(34, 139, 34));
        periodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setForeground(Color.WHITE);
        periodLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton fromDate = new JButton("Oct 21, 2024");
        styleDateButton(fromDate);
        fromDate.addActionListener(e -> showDatePickerDialog(fromDate));

        JButton toDate = new JButton("Nov 5, 2024");
        styleDateButton(toDate);
        toDate.addActionListener(e -> showDatePickerDialog(toDate));

        periodPanel.add(periodLabel);
        periodPanel.add(fromDate);
        periodPanel.add(new JLabel(" - "));
        periodPanel.add(toDate);

        // --- Table Section ---
        String[] columns = {
                "Date / Day", "Time In", "Time Out", "Late (mins)",
                "Overtime (hours)", "Undertime (hours)", "Absent"
        };

        Object[][] dummyData = {
                {"Oct 21 Monday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 22 Tuesday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 23 Wednesday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 24 Thursday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 25 Friday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 28 Monday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 29 Tuesday", "9:00", "17:02", 0.083, 0.033, 0, 0},
                {"Oct 30 Wednesday", "9:00", "17:02", 0.083, 0.033, 0, 0},
        };

        JTable table = new JTable(new DefaultTableModel(dummyData, columns));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Table Container
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // --- Combine Layouts ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(34, 139, 34));
        centerPanel.add(infoPanel);
        centerPanel.add(periodPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(tableContainer);

        add(backPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    // --- Style the date button like in your image
    private void styleDateButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    // --- Reusable info field
    private JPanel createInfoField(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(new Color(34, 139, 34));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        JTextField field = new JTextField(value);
        field.setEditable(false);
        field.setPreferredSize(new Dimension(160, 30));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    // --- Custom date picker popup (dialog + JSpinner)
    private void showDatePickerDialog(JButton targetButton) {
        JDialog dialog = new JDialog((Frame) null, "Select Date", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(270, 100);
        dialog.setLocationRelativeTo(targetButton);

        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(model);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MMM dd, yyyy"));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            Date selectedDate = model.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            targetButton.setText(sdf.format(selectedDate));
            dialog.dispose();
        });

        dialog.add(dateSpinner);
        dialog.add(okButton);
        dialog.setVisible(true);
    }
}

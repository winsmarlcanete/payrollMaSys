package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableColumn;

// JCalendar Imports:
import Config.JDBC;
import Module.Payroll.Payroll;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EmployeeAttendanceDetail extends JPanel {

    private JDateChooser fromDateChooser;
    private JDateChooser toDateChooser;
    private Object[][] data;
    private JTable table; // Declare table as a class-level variable
    private Time shiftStart;
    private Time shiftEnd;

    public EmployeeAttendanceDetail(CardLayout cardLayout, JPanel mainContainer, Object[] employeeInfo) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 139, 34)); // Match green background

        // --- Top Panel for Back Button, Info, and Period ---
        JPanel topSectionPanel = new JPanel();
        topSectionPanel.setLayout(new BoxLayout(topSectionPanel, BoxLayout.Y_AXIS));
        topSectionPanel.setBackground(new Color(34, 139, 34));

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
        topSectionPanel.add(backPanel);

        // --- Info Panel ---
        JPanel infoPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        infoPanel.setBackground(new Color(34, 139, 34));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        infoPanel.add(createInfoField("Name", employeeInfo[0].toString()));
        infoPanel.add(createInfoField("ID", employeeInfo[1].toString()));
        infoPanel.add(createInfoField("Department", employeeInfo[2].toString()));
        infoPanel.add(createInfoField("Employment", employeeInfo[3].toString()));
        try {
            int employeeId = Integer.parseInt(employeeInfo[1].toString());
            Connection conn = JDBC.getConnection();
            String sql = "SELECT shift_start, shift_end FROM payrollmsdb.employees WHERE employee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                shiftStart = rs.getTime("shift_start");
                shiftEnd = rs.getTime("shift_end");
            }

            rs.close();
            stmt.close();
            conn.close();

            infoPanel.add(createInfoField("Shift start", shiftStart != null ? shiftStart.toString() : "N/A"));
            infoPanel.add(createInfoField("Shift end", shiftEnd != null ? shiftEnd.toString() : "N/A"));
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving shift times for employee ID: " + employeeInfo[1], e);
        }
        infoPanel.add(new JPanel() {{ setOpaque(false); }});
        infoPanel.add(new JPanel() {{ setOpaque(false); }});
        infoPanel.add(new JPanel() {{ setOpaque(false); }});
        infoPanel.add(new JPanel() {{ setOpaque(false); }});
        infoPanel.add(new JPanel() {{ setOpaque(false); }});
        infoPanel.add(new JPanel() {{ setOpaque(false); }});

        topSectionPanel.add(infoPanel);

        // --- Period Panel (MODIFIED) ---
        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        periodPanel.setBackground(new Color(34, 139, 34));
        periodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setForeground(Color.WHITE);
        periodLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Initialize JDateChooser for "From" date
        fromDateChooser = new JDateChooser(new Date()); // Set initial date to current
        fromDateChooser.setDateFormatString("MMM dd, yyyy"); // Format for display
        styleDateChooser(fromDateChooser); // Apply styling
        fromDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    System.out.println("From Date changed to: " + fromDateChooser.getDate());
                }
            }
        });

        // Initialize JDateChooser for "To" date
        toDateChooser = new JDateChooser(new Date()); // Set initial date to current
        toDateChooser.setDateFormatString("MMM dd, yyyy"); // Format for display
        styleDateChooser(toDateChooser); // Apply styling
        toDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date fromDate = fromDateChooser.getDate();
                    Date toDate = toDateChooser.getDate();

                    if (fromDate != null && toDate != null) {
                        java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
                        java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());

                        // Extract employeeId, shiftStart, and shiftEnd from employeeInfo array
                        int employeeId = Integer.parseInt(employeeInfo[1].toString());

                        // Retrieve updated data for the specific employee
                        Object[][] updatedData = Payroll.retrieveTimecards(employeeId, sqlFromDate, sqlToDate, shiftStart, shiftEnd);

                        // Update the table model
                        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                        tableModel.setDataVector(updatedData, new String[]{
                                "Date / Day", "Time In", "Time Out", "Late (mins)",
                                "Overtime (hours)", "Undertime (hours)", "Absent"
                        });

                        System.out.println("Attendance table updated for employeeId: " + employeeId + " and period: " + fromDate + " to " + toDate);
                    }
                }
            }
        });
        periodPanel.add(periodLabel);
        periodPanel.add(fromDateChooser);
        periodPanel.add(new JLabel(" - "));
        periodPanel.add(toDateChooser);

        topSectionPanel.add(periodPanel);

        add(topSectionPanel, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columns = {
                "Date / Day", "Time In", "Time Out", "Late (mins)",
                "Overtime (hours)", "Undertime (hours)", "Absent"
        };

        table = new JTable(new DefaultTableModel(data, columns)); // Initialize table
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    private void styleDateChooser(JDateChooser dateChooser) {
        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) dateChooser.getDateEditor();
        dateEditor.setBackground(Color.WHITE);
        dateEditor.setForeground(Color.BLACK);
        dateEditor.setOpaque(true);
        dateChooser.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        dateEditor.setBorder(null);
        dateEditor.setFont(new Font("Arial", Font.BOLD, 13));
        dateChooser.setPreferredSize(new Dimension(150, 30));
    }

    private JPanel createInfoField(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(new Color(34, 139, 34));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        JTextField field = new JTextField(value);
        field.setEditable(false);
        field.setColumns(10);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
}
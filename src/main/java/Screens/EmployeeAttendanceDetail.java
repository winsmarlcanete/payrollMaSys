package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableColumn;

// JCalendar Imports:
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class EmployeeAttendanceDetail extends JPanel {

    // Declare JDateChooser instances as class members if you need to access them later
    private JDateChooser fromDateChooser;
    private JDateChooser toDateChooser;


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
        infoPanel.add(createInfoField("Shift start", "9:00"));
        infoPanel.add(createInfoField("Shift end", "17:00"));
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
        // Add a property change listener to react when the date is changed
        fromDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    // Handle date change, e.g., refresh table data
                    System.out.println("From Date changed to: " + fromDateChooser.getDate());
                    // You would call a method here to update your attendance table based on the new date range
                    // updateAttendanceTableData(fromDateChooser.getDate(), toDateChooser.getDate());
                }
            }
        });


        // Initialize JDateChooser for "To" date
        toDateChooser = new JDateChooser(new Date()); // Set initial date to current
        toDateChooser.setDateFormatString("MMM dd, yyyy"); // Format for display
        styleDateChooser(toDateChooser); // Apply styling
        // Add a property change listener
        toDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    // Handle date change
                    System.out.println("To Date changed to: " + toDateChooser.getDate());
                    // updateAttendanceTableData(fromDateChooser.getDate(), toDateChooser.getDate());
                }
            }
        });


        periodPanel.add(periodLabel);
        periodPanel.add(fromDateChooser); // Add the JDateChooser
        periodPanel.add(new JLabel(" - "));
        periodPanel.add(toDateChooser); // Add the JDateChooser

        topSectionPanel.add(periodPanel);

        add(topSectionPanel, BorderLayout.NORTH);


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

    // --- Style the JDateChooser to match your buttons
    private void styleDateChooser(JDateChooser dateChooser) {
        // Set the background and foreground of the text field within the JDateChooser
        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor)dateChooser.getDateEditor();
        dateEditor.setBackground(Color.WHITE);
        dateEditor.setForeground(Color.BLACK);
        dateEditor.setOpaque(true); // Make sure background is painted

        // Style the button next to the text field (the calendar icon button)
        // This is a bit trickier as it's often a BasicArrowButton
        // You might need to iterate through components or use UI delegates for full control.
        // For a simpler approach, let's just make sure the text editor looks good.

        // Also style the border of the entire JDateChooser
        dateChooser.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Matches button padding
        dateEditor.setBorder(null); // Remove default border from internal text field

        // Set the font of the text editor
        dateEditor.setFont(new Font("Arial", Font.BOLD, 13));

        // You might want to adjust its preferred size to match your old buttons
        dateChooser.setPreferredSize(new Dimension(150, 30)); // Adjust width as needed
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
        field.setColumns(10);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    // --- Custom date picker popup (OLD - NO LONGER NEEDED WITH JDateChooser)
    // You can remove this method entirely once you're sure JDateChooser works for you
    // private void showDatePickerDialog(JButton targetButton) {
    //     JDialog dialog = new JDialog((Frame) null, "Select Date", true);
    //     dialog.setLayout(new FlowLayout());
    //     dialog.setSize(270, 100);
    //     dialog.setLocationRelativeTo(targetButton);

    //     SpinnerDateModel model = new SpinnerDateModel();
    //     JSpinner dateSpinner = new JSpinner(model);
    //     dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MMM dd, yyyy"));

    //     JButton okButton = new JButton("OK");
    //     okButton.addActionListener(e -> {
    //         Date selectedDate = model.getDate();
    //         SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    //         targetButton.setText(sdf.format(selectedDate));
    //         dialog.dispose();
    //     });

    //     dialog.add(dateSpinner);
    //     dialog.add(okButton);
    //     dialog.setVisible(true);
    // }
}
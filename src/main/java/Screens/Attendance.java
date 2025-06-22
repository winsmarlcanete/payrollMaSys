package Screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.util.regex.Pattern;


import Components.TableStyler;
import Module.E201File.E201File;

import java.awt.*;
import java.awt.event.*;

public class Attendance extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    private static DefaultTableModel employeeTableModel;
    private static String[] tableViewHeaders = { "Name", "ID", "Department", "Employment Status" };
    private static Object[][] tableViewData;
    private static Object[][] detailsViewData;
    private static String[] columnHeaders = { "Name", "ID", "Department", "Employment Status" };
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public static void loadEmployeeTabledata() {
        Object[][] rawData = E201File.getEmployeeTableData(); // Query your source

        // Map data for table view
        tableViewData = new Object[rawData.length][tableViewHeaders.length];
        for (int i = 0; i < rawData.length; i++) {
            tableViewData[i][0] = rawData[i][0] + ", " + rawData[i][1]; // Combine last name and first name
            tableViewData[i][1] = rawData[i][3]; // ID
            tableViewData[i][2] = rawData[i][4]; // Department
            tableViewData[i][3] = rawData[i][5]; // Employment Status
        }

        // Map data for details view
        detailsViewData = rawData; // Use rawData directly for details view
        employeeTableModel.setDataVector(tableViewData, tableViewHeaders);
    }

    public Attendance() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // --- Table panel ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(34, 139, 34));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(34, 139, 34));

        // Left logo and search
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);

        int targetHeight = 30;
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));
        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (int) ((double) origWidth / origHeight * targetHeight);
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));

        JPanel logoWrapper = new JPanel(new BorderLayout());
        logoWrapper.setBackground(Color.WHITE);
        logoWrapper.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoWrapper.add(logoLabel, BorderLayout.CENTER);
        leftPanel.add(logoWrapper);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setForeground(Color.WHITE);
        leftPanel.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        leftPanel.add(searchField);

        // Right controls
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sortPanel.setBackground(Color.WHITE);
        sortPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel sortLabel = new JLabel("Sort By Department:");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 12));
        sortPanel.add(sortLabel);

        JComboBox<String> departmentDropdown = new JComboBox<>(new String[] {
                "All Departments",
                "Administration",
                "Human Resource",
                "Sales",
                "Production (Pre-Press)",
                "Production (Press)",
                "Production (Post-Press)",
                "Production (Quality Control)"
        });
        departmentDropdown.setPreferredSize(new Dimension(200, 30));
        sortPanel.add(departmentDropdown);



        departmentDropdown.addActionListener(e -> {
            String selected = (String) departmentDropdown.getSelectedItem();
            if (selected.equals("All Departments")) {
                ((TableRowSorter<?>) table.getRowSorter()).setRowFilter(null);
            } else {
                ((TableRowSorter<?>) table.getRowSorter()).setRowFilter(RowFilter.regexFilter("^" + Pattern.quote(selected) + "$", 2));
            }
        });

        rightPanel.add(sortPanel);

        JButton backupButton = new JButton("Attendance Backup");
        backupButton.setPreferredSize(new Dimension(180, 30));
        backupButton.setBackground(Color.BLACK);
        backupButton.setForeground(Color.WHITE);
        backupButton.setFocusPainted(false);
        backupButton.addActionListener(e -> {
            mainContainer.add(new AttendanceBackup(cardLayout, mainContainer), "backup");
            cardLayout.show(mainContainer, "backup");
        });
        rightPanel.add(backupButton);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        Object[][] data = E201File.getEmployeeTableData();
        employeeTableModel = new DefaultTableModel(tableViewData, tableViewHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(employeeTableModel);
        table.getTableHeader().setReorderingAllowed(false);
        TableStyler.styleTable(table);

// ✅ Now safe to initialize rowSorter
        rowSorter = new TableRowSorter<>(employeeTableModel);
        table.setRowSorter(rowSorter);


        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = searchField.getText().trim();
                rowSorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + text, 0));
            }
        });

        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    table.setRowSelectionInterval(row, row);
                } else {
                    table.clearSelection();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    searchField.setText("");
                    int modelRow = table.convertRowIndexToModel(row);
                    Object[] rowData = new Object[table.getColumnCount()];
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        rowData[i] = employeeTableModel.getValueAt(modelRow, i);
                    }
                    EmployeeAttendanceDetail detailPanel = new EmployeeAttendanceDetail(cardLayout, mainContainer, rowData);
                    mainContainer.add(detailPanel, "detail");
                    cardLayout.show(mainContainer, "detail");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.add(topPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainContainer.add(tablePanel, "table");
        add(mainContainer, BorderLayout.CENTER);
    }
}
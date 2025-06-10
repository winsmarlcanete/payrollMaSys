package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LeaveManagement extends JPanel {
    private JTextField searchField;

    public void clearSearchField() {
        searchField.setText("");
    }

    public LeaveManagement() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // CardLayout for switching views
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        // --- Table View ---
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        String[] columnNames = { "Name", "ID", "Department", "Employment Status" };
        Object[][] data = {
                { "Aela Cruz, Juan C.", 1, "Sales", "Regular" },
                { "Bela Cruz, Juan C.", 23, "Sales", "Regular" },
                { "Cela Cruz, Juan C.", 31, "Production(Pre-Press)", "Regular" },
                { "Dela Cruz, Juan C.", 14, "Production(Pre-Press)", "Regular" },
                { "Eela Cruz, Juan C.", 25, "Production(Pre-Press)", "Regular" },
                { "Fela Cruz, Juan C.", 36, "Production (Press)", "Regular" },
                { "Gela Cruz, Juan C.", 15, "Production(Pre-Press)", "Regular" },
                { "Hela Cruz, Juan C.", 4, "Production(Pre-Press)", "Regular" },
                { "Hela Cruz, Juan C.", 8, "Production (Post-Press)", "Regular" },
                { "Iela Cruz, Juan C.", 10, "Production (Post-Press)", "Regular" },
                { "Jela Cruz, Juan C.", 17, "Production (Quality Control)", "Regular" },
                { "Jela Cruz, Juan C.", 22, "Production (Quality Control)", "Regular" },
                { "Kela Cruz, Juan C.", 11, "Sales", "Regular" }
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane tableScrollPane = new JScrollPane(table);

        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        table.setRowHeight(20);
        table.getTableHeader().setFont(font);
        searchField.setFont(font);
        searchButton.setFont(font);

        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    table.setRowSelectionInterval(row, row);
                } else {
                    table.clearSelection();
                }
            }
        });

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // --- Details View ---
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(new Color(34, 177, 76)); // green background
        detailsPanel.setLayout(null);

        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 20, 100, 30);
        detailsPanel.add(backButton);

        Font detailsFont = new Font("Arial", Font.PLAIN, 18);

        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField employmentStatusField = new JTextField();
        JComboBox<String> yearCombo = new JComboBox<>(new String[]{"2024", "2023", "2022"});
        JTextField leavesUsedField = new JTextField();
        JTextField remainingSILField = new JTextField();
        JComboBox<String> typeOfLeave1 = new JComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        JComboBox<String> typeOfLeave2 = new JComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        JComboBox<String> typeOfLeave3 = new JComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        JComboBox<String> typeOfLeave4 = new JComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        JComboBox<String> typeOfLeave5 = new JComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        JTextField dateField1 = new JTextField();
        JTextField dateField2 = new JTextField();
        JTextField dateField3 = new JTextField();
        JTextField dateField4 = new JTextField();
        JTextField dateField5 = new JTextField();

        // Set bounds and add components (adjust as needed for your layout)
        int x = 140, y = 70, w = 220, h = 35, gap = 50;
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(x, y, w, 25);
        nameField.setBounds(x, y + 25, w, h);
        nameField.setFont(detailsFont);
        nameField.setEditable(false);
        detailsPanel.add(nameLabel); detailsPanel.add(nameField);

        JLabel idLabel = new JLabel("ID");
        idLabel.setBounds(x + 260, y, w, 25);
        idField.setBounds(x + 260, y + 25, w, h);
        idField.setFont(detailsFont);
        idField.setEditable(false);
        detailsPanel.add(idLabel); detailsPanel.add(idField);

        JLabel departmentLabel = new JLabel("Department");
        departmentLabel.setBounds(x + 520, y, w, 25);
        departmentField.setBounds(x + 520, y + 25, w, h);
        departmentField.setFont(detailsFont);
        departmentField.setEditable(false);
        detailsPanel.add(departmentLabel); detailsPanel.add(departmentField);

        JLabel employmentStatusLabel = new JLabel("Employment Status");
        employmentStatusLabel.setBounds(x + 780, y, w, 25);
        employmentStatusField.setBounds(x + 780, y + 25, w, h);
        employmentStatusField.setFont(detailsFont);
        employmentStatusField.setEditable(false);
        detailsPanel.add(employmentStatusLabel); detailsPanel.add(employmentStatusField);

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setBounds(x, y + 80, w, 25);
        yearCombo.setBounds(x, y + 105, w, h);
        yearCombo.setFont(detailsFont);
        detailsPanel.add(yearLabel); detailsPanel.add(yearCombo);

        JLabel leavesUsedLabel = new JLabel("Leaves Used");
        leavesUsedLabel.setBounds(x + 260, y + 80, w, 25);
        leavesUsedField.setBounds(x + 260, y + 105, w, h);
        leavesUsedField.setFont(detailsFont);
        leavesUsedField.setEditable(false);
        detailsPanel.add(leavesUsedLabel); detailsPanel.add(leavesUsedField);

        JLabel remainingSILLabel = new JLabel("Remaining SIL");
        remainingSILLabel.setBounds(x + 520, y + 80, w, 25);
        remainingSILField.setBounds(x + 520, y + 105, w, h);
        remainingSILField.setFont(detailsFont);
        remainingSILField.setEditable(false);
        detailsPanel.add(remainingSILLabel); detailsPanel.add(remainingSILField);

        // Type of Leave and Date fields (repeat for up to 5)
        JLabel typeOfLeaveLabel = new JLabel("Type of Leave");
        typeOfLeaveLabel.setBounds(x + 780, y + 80, w, 25);
        detailsPanel.add(typeOfLeaveLabel);

        typeOfLeave1.setBounds(x + 780, y + 105, w, h);
        typeOfLeave2.setBounds(x + 780, y + 105 + gap, w, h);
        typeOfLeave3.setBounds(x + 780, y + 105 + gap * 2, w, h);
        typeOfLeave4.setBounds(x + 780, y + 105 + gap * 3, w, h);
        typeOfLeave5.setBounds(x + 780, y + 105 + gap * 4, w, h);
        detailsPanel.add(typeOfLeave1); detailsPanel.add(typeOfLeave2);
        detailsPanel.add(typeOfLeave3); detailsPanel.add(typeOfLeave4); detailsPanel.add(typeOfLeave5);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(x + 1040, y + 80, w, 25);
        detailsPanel.add(dateLabel);

        dateField1.setBounds(x + 1040, y + 105, w, h);
        dateField2.setBounds(x + 1040, y + 105 + gap, w, h);
        dateField3.setBounds(x + 1040, y + 105 + gap * 2, w, h);
        dateField4.setBounds(x + 1040, y + 105 + gap * 3, w, h);
        dateField5.setBounds(x + 1040, y + 105 + gap * 4, w, h);
        dateField1.setFont(detailsFont); dateField2.setFont(detailsFont);
        dateField3.setFont(detailsFont); dateField4.setFont(detailsFont); dateField5.setFont(detailsFont);
        detailsPanel.add(dateField1); detailsPanel.add(dateField2);
        detailsPanel.add(dateField3); detailsPanel.add(dateField4); detailsPanel.add(dateField5);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(x + 1040, y + 105 + gap * 5 + 20, 120, 40);
        detailsPanel.add(saveButton);

        // --- Add panels to CardLayout ---
        contentPanel.add(tablePanel, "TableView");
        contentPanel.add(detailsPanel, "DetailsView");
        add(contentPanel, BorderLayout.CENTER);

        // --- Table row click event ---
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    nameField.setText(table.getValueAt(row, 0).toString());
                    idField.setText(table.getValueAt(row, 1).toString());
                    departmentField.setText(table.getValueAt(row, 2).toString());
                    employmentStatusField.setText(table.getValueAt(row, 3).toString());
                    // Set default values when opening details
                    leavesUsedField.setText("0");
                    remainingSILField.setText("5 / 5");
                    typeOfLeave1.setSelectedIndex(0);
                    typeOfLeave2.setSelectedIndex(0);
                    typeOfLeave3.setSelectedIndex(0);
                    typeOfLeave4.setSelectedIndex(0);
                    typeOfLeave5.setSelectedIndex(0);
                    dateField1.setText("");
                    dateField2.setText("");
                    dateField3.setText("");
                    dateField4.setText("");
                    dateField5.setText("");
                    cardLayout.show(contentPanel, "DetailsView");
                    nameField.setFocusable(false);
                    idField.setFocusable(false);
                    departmentField.setFocusable(false);
                    employmentStatusField.setFocusable(false);
                    leavesUsedField.setFocusable(false);
                    remainingSILField.setFocusable(false);
                }
            }
        });

        // --- Save button logic ---
        saveButton.addActionListener(e -> {
            int used = 0;
            JComboBox<?>[] leaveBoxes = {typeOfLeave1, typeOfLeave2, typeOfLeave3, typeOfLeave4, typeOfLeave5};
            JTextField[] dateFields = {dateField1, dateField2, dateField3, dateField4, dateField5};
            for (int i = 0; i < 5; i++) {
                if (leaveBoxes[i].getSelectedIndex() > 0 && !dateFields[i].getText().trim().isEmpty()) {
                    used++;
                }
            }
            leavesUsedField.setText(String.valueOf(used));
            remainingSILField.setText((5 - used) + " / 5");
        });

        // --- Back button event ---
        backButton.addActionListener(e -> cardLayout.show(contentPanel, "TableView"));

        // --- Search filter logic ---
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            private void filterTable() {
                String searchText = searchField.getText().toLowerCase();
                model.setRowCount(0);
                if (searchText.isEmpty()) {
                    for (Object[] row : data) model.addRow(row);
                } else {
                    for (Object[] row : data) {
                        boolean matchFound = false;
                        for (Object cell : row) {
                            if (cell != null && cell.toString().toLowerCase().contains(searchText)) {
                                matchFound = true; break;
                            }
                        }
                        if (matchFound) model.addRow(row);
                    }
                }
            }
        });
    }
}
package Screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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

        // ...existing code...
        RoundedTextField nameField = new RoundedTextField(10);
        nameField.setMargin(new java.awt.Insets(0, 5, 0, 0));
        RoundedTextField idField = new RoundedTextField(10);
        idField.setMargin(new java.awt.Insets(0, 5, 0, 0));
        RoundedTextField departmentField = new RoundedTextField(10);
        departmentField.setMargin(new java.awt.Insets(0, 5, 0, 0));
        RoundedTextField employmentStatusField = new RoundedTextField(10);
        employmentStatusField.setMargin(new java.awt.Insets(0, 5, 0, 0));
        RoundedTextField leavesUsedField = new RoundedTextField(10);
        leavesUsedField.setHorizontalAlignment(JTextField.CENTER);
        RoundedTextField remainingSILField = new RoundedTextField(10);
        remainingSILField.setHorizontalAlignment(JTextField.CENTER);
        RoundedTextField dateField1 = new RoundedTextField(10);
        RoundedTextField dateField2 = new RoundedTextField(10);
        RoundedTextField dateField3 = new RoundedTextField(10);
        RoundedTextField dateField4 = new RoundedTextField(10);
        RoundedTextField dateField5 = new RoundedTextField(10);

        RoundedComboBox<String> typeOfLeave1 = new RoundedComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        RoundedComboBox<String> typeOfLeave2 = new RoundedComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        RoundedComboBox<String> typeOfLeave3 = new RoundedComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        RoundedComboBox<String> typeOfLeave4 = new RoundedComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        RoundedComboBox<String> typeOfLeave5 = new RoundedComboBox<>(new String[]{"", "Sick Leave", "Maternity Leave", "Paternity Leave", "Bereavement Leave"});
        // ...existing code...
        // JTextField dateField1 = new JTextField();
        // JTextField dateField2 = new JTextField();
        // JTextField dateField3 = new JTextField();
        // JTextField dateField4 = new JTextField();
        // JTextField dateField5 = new JTextField();

        // Set bounds and add components (adjust as needed for your layout)
        int x = 440, y = 70, w = 220, h = 35, gap = 50;
        JLabel nameLabel = new JLabel("Name");
        int nameW = w + 80;
        nameLabel.setBounds(x, y, nameW, 25);
        nameField.setBounds(x, y + 25, nameW, h);
        nameField.setFont(detailsFont);
        nameLabel.setForeground(Color.WHITE);
        nameField.setEditable(false);
        detailsPanel.add(nameLabel); detailsPanel.add(nameField);

        // ID (a little longer, right of Name)
        JLabel idLabel = new JLabel("ID");
        int idX = x + nameW + 20;
        int idW = 140;
        idLabel.setBounds(idX, y, idW, 25);
        idField.setBounds(idX, y + 25, idW, h);
        idField.setFont(detailsFont);
        idLabel.setForeground(Color.WHITE);
        idField.setEditable(false);
        detailsPanel.add(idLabel); detailsPanel.add(idField);

        // Department (right of ID)
        JLabel departmentLabel = new JLabel("Department");
        int deptX = idX + idW + 20;
        departmentLabel.setBounds(deptX, y, w, 25);
        departmentField.setBounds(deptX, y + 25, w, h);
        departmentField.setFont(detailsFont);
        departmentLabel.setForeground(Color.WHITE);
        departmentField.setEditable(false);
        detailsPanel.add(departmentLabel); detailsPanel.add(departmentField);

        // Employment Status (right of Department)
        JLabel employmentStatusLabel = new JLabel("Employment Status");
        int empStatX = deptX + w + 20;
        employmentStatusLabel.setBounds(empStatX, y, w, 25);
        employmentStatusField.setBounds(empStatX, y + 25, w, h);
        employmentStatusField.setFont(detailsFont);
        employmentStatusLabel.setForeground(Color.WHITE);
        employmentStatusField.setEditable(false);
        detailsPanel.add(employmentStatusLabel); detailsPanel.add(employmentStatusField);

        // Year, Leaves Used, Remaining SIL (below Name+ID)
        int row2Y = y + 25 + h + 20;
        int belowNameIdX = x;
        JLabel yearLabel = new JLabel("Year");
        yearLabel.setBounds(belowNameIdX, row2Y, 80, 25);
        yearLabel.setForeground(Color.WHITE);
        RoundedComboBox<String> yearCombo = new RoundedComboBox<>(new String[]{"2024", "2023", "2022"});
        yearCombo.setBounds(belowNameIdX, row2Y + 25, 140, h);
        yearCombo.setFont(detailsFont);
        ((javax.swing.JLabel)yearCombo.getRenderer()).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detailsPanel.add(yearLabel); detailsPanel.add(yearCombo);

        JLabel leavesUsedLabel = new JLabel("Leaves Used");
        leavesUsedLabel.setBounds(belowNameIdX + 160, row2Y, 120, 25);
        leavesUsedField.setBounds(belowNameIdX + 160, row2Y + 25, 140, h);
        leavesUsedField.setFont(detailsFont);
        leavesUsedLabel.setForeground(Color.WHITE);
        leavesUsedField.setEditable(false);
        detailsPanel.add(leavesUsedLabel); detailsPanel.add(leavesUsedField);

        JLabel remainingSILLabel = new JLabel("Remaining SIL");
        remainingSILLabel.setBounds(belowNameIdX + 320, row2Y, 120, 25);
        remainingSILField.setBounds(belowNameIdX + 320, row2Y + 25, 140, h);
        remainingSILField.setFont(detailsFont);
        remainingSILLabel.setForeground(Color.WHITE);
        remainingSILField.setEditable(false);
        detailsPanel.add(remainingSILLabel); detailsPanel.add(remainingSILField);

        // Type of Leave comboboxes (below Department)
        int typeOfLeaveY = y + 25 + h + 20;
        JLabel typeOfLeaveLabel = new JLabel("Type of Leave");
        typeOfLeaveLabel.setForeground(Color.WHITE);
        typeOfLeaveLabel.setBounds(deptX, typeOfLeaveY, w, 25);
        detailsPanel.add(typeOfLeaveLabel);

        typeOfLeave1.setBounds(deptX, typeOfLeaveY + 25, w, h);
        typeOfLeave2.setBounds(deptX, typeOfLeaveY + 25 + gap, w, h);
        typeOfLeave3.setBounds(deptX, typeOfLeaveY + 25 + gap * 2, w, h);
        typeOfLeave4.setBounds(deptX, typeOfLeaveY + 25 + gap * 3, w, h);
        typeOfLeave5.setBounds(deptX, typeOfLeaveY + 25 + gap * 4, w, h);

        typeOfLeave1.setFont(detailsFont);
        typeOfLeave2.setFont(detailsFont);
        typeOfLeave3.setFont(detailsFont);
        typeOfLeave4.setFont(detailsFont);
        typeOfLeave5.setFont(detailsFont);

        detailsPanel.add(typeOfLeave1); detailsPanel.add(typeOfLeave2);
        detailsPanel.add(typeOfLeave3); detailsPanel.add(typeOfLeave4); detailsPanel.add(typeOfLeave5);

        // Date textfields (below Employment Status)
        int dateY = y + 25 + h + 20;
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(empStatX, dateY, w, 25);
        dateLabel.setForeground(Color.WHITE);
        detailsPanel.add(dateLabel);

        dateField1.setBounds(empStatX, dateY + 25, w, h);
        dateField2.setBounds(empStatX, dateY + 25 + gap, w, h);
        dateField3.setBounds(empStatX, dateY + 25 + gap * 2, w, h);
        dateField4.setBounds(empStatX, dateY + 25 + gap * 3, w, h);
        dateField5.setBounds(empStatX, dateY + 25 + gap * 4, w, h);
        dateField1.setFont(detailsFont); dateField2.setFont(detailsFont);
        dateField3.setFont(detailsFont); dateField4.setFont(detailsFont); dateField5.setFont(detailsFont);
        detailsPanel.add(dateField1); detailsPanel.add(dateField2);
        detailsPanel.add(dateField3); detailsPanel.add(dateField4); detailsPanel.add(dateField5);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(x + 1340, y + 575 + gap * 5 + 20, 120, 40);
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
                    searchField.setText("");

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
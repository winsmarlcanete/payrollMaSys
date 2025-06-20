package Screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.RoundedTextField;
import Components.TableStyler;
import Module.E201File.E201File;

public class LeaveManagement extends JPanel {
    private JTextField searchField;

    private static DefaultTableModel employeeTableModel;
    private static String[] columnHeaders = { "Name", "ID", "Department", "Employment Status" };
    private JTable table;
    public static void loadEmployeeTabledata() {
        Object[][] data = E201File.getEmployeeTableData(); // query your source
        employeeTableModel.setDataVector(data, columnHeaders);
    }
    public void clearSearchField() {
        searchField.setText("");
    }

    public LeaveManagement() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        getCursor();

        // CardLayout for switching views
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        // --- Table View ---
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);


        Object[][] data = E201File.getEmployeeTableData();
        employeeTableModel = new DefaultTableModel(data, columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(employeeTableModel);

        table.getTableHeader().setReorderingAllowed(false);
        TableStyler.styleTable(table);
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
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(new Color(34, 177, 76)); // green background

        // Container panel to group and center the fields
        JPanel groupPanel = new JPanel(null); // We'll use absolute layout for the group, but center the group itself
        groupPanel.setOpaque(false);

        Font detailsFont = new Font("Arial", Font.PLAIN, 18);

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

        int x = 20, y = 20, w = 220, h = 35, gap = 50;
        JLabel nameLabel = new JLabel("Name");
        int nameW = w + 80;
        nameLabel.setBounds(x, y, nameW, 25);
        nameField.setBounds(x, y + 25, nameW, h);
        nameField.setFont(detailsFont);
        nameLabel.setForeground(Color.WHITE);
        nameField.setEditable(false);
        groupPanel.add(nameLabel); groupPanel.add(nameField);

        JLabel idLabel = new JLabel("ID");
        int idX = x + nameW + 20;
        int idW = 140;
        idLabel.setBounds(idX, y, idW, 25);
        idField.setBounds(idX, y + 25, idW, h);
        idField.setFont(detailsFont);
        idLabel.setForeground(Color.WHITE);
        idField.setEditable(false);
        groupPanel.add(idLabel); groupPanel.add(idField);

        JLabel departmentLabel = new JLabel("Department");
        int deptX = idX + idW + 20;
        departmentLabel.setBounds(deptX, y, w, 25);
        departmentField.setBounds(deptX, y + 25, w, h);
        departmentField.setFont(detailsFont);
        departmentLabel.setForeground(Color.WHITE);
        departmentField.setEditable(false);
        groupPanel.add(departmentLabel); groupPanel.add(departmentField);

        JLabel employmentStatusLabel = new JLabel("Employment Status");
        int empStatX = deptX + w + 20;
        employmentStatusLabel.setBounds(empStatX, y, w, 25);
        employmentStatusField.setBounds(empStatX, y + 25, w, h);
        employmentStatusField.setFont(detailsFont);
        employmentStatusLabel.setForeground(Color.WHITE);
        employmentStatusField.setEditable(false);
        groupPanel.add(employmentStatusLabel); groupPanel.add(employmentStatusField);

        // Year, Leaves Used, Remaining SIL (below Name+ID)
        int row2Y = y + 25 + h + 20;
        int belowNameIdX = x;
        JLabel yearLabel = new JLabel("Year");
        yearLabel.setBounds(belowNameIdX, row2Y, 80, 25);
        yearLabel.setForeground(Color.WHITE);
        RoundedComboBox<String> yearCombo = new RoundedComboBox<>(new String[]{"2024", "2023", "2022"});
        yearCombo.setBounds(belowNameIdX, row2Y + 25, 140, h);
        yearCombo.setFont(detailsFont);
        yearCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ((javax.swing.JLabel)yearCombo.getRenderer()).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        groupPanel.add(yearLabel); groupPanel.add(yearCombo);

        JLabel leavesUsedLabel = new JLabel("Leaves Used");
        leavesUsedLabel.setBounds(belowNameIdX + 160, row2Y, 120, 25);
        leavesUsedField.setBounds(belowNameIdX + 160, row2Y + 25, 140, h);
        leavesUsedField.setFont(detailsFont);
        leavesUsedLabel.setForeground(Color.WHITE);
        leavesUsedField.setEditable(false);
        groupPanel.add(leavesUsedLabel); groupPanel.add(leavesUsedField);

        JLabel remainingSILLabel = new JLabel("Remaining SIL");
        remainingSILLabel.setBounds(belowNameIdX + 320, row2Y, 120, 25);
        remainingSILField.setBounds(belowNameIdX + 320, row2Y + 25, 140, h);
        remainingSILField.setFont(detailsFont);
        remainingSILLabel.setForeground(Color.WHITE);
        remainingSILField.setEditable(false);
        groupPanel.add(remainingSILLabel); groupPanel.add(remainingSILField);

        // Type of Leave comboboxes (below Department)
        int typeOfLeaveY = y + 25 + h + 20;
        JLabel typeOfLeaveLabel = new JLabel("Type of Leave");
        typeOfLeaveLabel.setForeground(Color.WHITE);
        typeOfLeaveLabel.setBounds(deptX, typeOfLeaveY, w, 25);
        groupPanel.add(typeOfLeaveLabel);

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

        typeOfLeave1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        typeOfLeave2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        typeOfLeave3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        typeOfLeave4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        typeOfLeave5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set initial enabled/disabled state for typeOfLeave comboboxes and date fields
        typeOfLeave1.setEnabled(true);
        typeOfLeave2.setEnabled(false);
        typeOfLeave3.setEnabled(false);
        typeOfLeave4.setEnabled(false);
        typeOfLeave5.setEnabled(false);

        dateField1.setEnabled(true);
        dateField2.setEnabled(false);
        dateField3.setEnabled(false);
        dateField4.setEnabled(false);
        dateField5.setEnabled(false);

        // Add listeners to enable the next combobox and date field only if the current one is filled
        typeOfLeave1.addActionListener(e -> {
            boolean filled = typeOfLeave1.getSelectedIndex() > 0;
            typeOfLeave2.setEnabled(filled);
            dateField2.setEnabled(filled);
            if (!filled) {
                typeOfLeave2.setSelectedIndex(0);
                typeOfLeave3.setEnabled(false); typeOfLeave3.setSelectedIndex(0);
                typeOfLeave4.setEnabled(false); typeOfLeave4.setSelectedIndex(0);
                typeOfLeave5.setEnabled(false); typeOfLeave5.setSelectedIndex(0);

                dateField2.setText(""); dateField2.setEnabled(false);
                dateField3.setText(""); dateField3.setEnabled(false);
                dateField4.setText(""); dateField4.setEnabled(false);
                dateField5.setText(""); dateField5.setEnabled(false);
            }
        });
        typeOfLeave2.addActionListener(e -> {
            boolean filled = typeOfLeave2.getSelectedIndex() > 0;
            typeOfLeave3.setEnabled(filled);
            dateField3.setEnabled(filled);
            if (!filled) {
                typeOfLeave3.setSelectedIndex(0);
                typeOfLeave4.setEnabled(false); typeOfLeave4.setSelectedIndex(0);
                typeOfLeave5.setEnabled(false); typeOfLeave5.setSelectedIndex(0);

                dateField3.setText(""); dateField3.setEnabled(false);
                dateField4.setText(""); dateField4.setEnabled(false);
                dateField5.setText(""); dateField5.setEnabled(false);
            }
        });
        typeOfLeave3.addActionListener(e -> {
            boolean filled = typeOfLeave3.getSelectedIndex() > 0;
            typeOfLeave4.setEnabled(filled);
            dateField4.setEnabled(filled);
            if (!filled) {
                typeOfLeave4.setSelectedIndex(0);
                typeOfLeave5.setEnabled(false); typeOfLeave5.setSelectedIndex(0);

                dateField4.setText(""); dateField4.setEnabled(false);
                dateField5.setText(""); dateField5.setEnabled(false);
            }
        });
        typeOfLeave4.addActionListener(e -> {
            boolean filled = typeOfLeave4.getSelectedIndex() > 0;
            typeOfLeave5.setEnabled(filled);
            dateField5.setEnabled(filled);
            if (!filled) {
                typeOfLeave5.setSelectedIndex(0);
                dateField5.setText(""); dateField5.setEnabled(false);
            }
        });
        groupPanel.add(typeOfLeave1); groupPanel.add(typeOfLeave2);
        groupPanel.add(typeOfLeave3); groupPanel.add(typeOfLeave4); groupPanel.add(typeOfLeave5);

        // Add hover effect to type of leave and year comboboxes
        Color comboDefaultBg = Color.WHITE;
        Color comboHoverBg = new Color(230, 255, 230); // light greenish

        JComboBox<?>[] comboBoxes = {typeOfLeave1, typeOfLeave2, typeOfLeave3, typeOfLeave4, typeOfLeave5, yearCombo};
        for (JComboBox<?> combo : comboBoxes) {
            combo.setBackground(comboDefaultBg);
            combo.setOpaque(false);
            combo.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (combo.isEnabled()) {
                        combo.setBackground(comboHoverBg);
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    combo.setBackground(comboDefaultBg);
                }
            });
        }

        // Date textfields (below Employment Status)
        int dateY = y + 25 + h + 20;
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(empStatX, dateY, w, 25);
        dateLabel.setForeground(Color.WHITE);
        groupPanel.add(dateLabel);

        dateField1.setBounds(empStatX, dateY + 25, w, h);
        dateField2.setBounds(empStatX, dateY + 25 + gap, w, h);
        dateField3.setBounds(empStatX, dateY + 25 + gap * 2, w, h);
        dateField4.setBounds(empStatX, dateY + 25 + gap * 3, w, h);
        dateField5.setBounds(empStatX, dateY + 25 + gap * 4, w, h);
        dateField1.setFont(detailsFont); dateField2.setFont(detailsFont);
        dateField3.setFont(detailsFont); dateField4.setFont(detailsFont); dateField5.setFont(detailsFont);
        groupPanel.add(dateField1); groupPanel.add(dateField2);
        groupPanel.add(dateField3); groupPanel.add(dateField4); groupPanel.add(dateField5);

        // Set preferred size for the group panel to match its content, not too wide
        groupPanel.setPreferredSize(new java.awt.Dimension(990, 400)); // Adjust width as needed

        // --- Place components in detailsPanel using GridBagLayout ---

        GridBagConstraints gbc = new GridBagConstraints();

        int buttonWidth = 160; // wider if you want
        int buttonHeight = h;  // same as your text fields
        int arc = 20; 

        // Back button (upper left)
        RoundedButton backButton = new RoundedButton("Back", arc);
        backButton.setFont(detailsFont);
        backButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight)); // width increased, height same as text fields
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover and click effect for back button
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                backButton.setBackground(new Color(30, 30, 30)); // Slightly lighter black
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                backButton.setBackground(Color.BLACK);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                backButton.setBackground(new Color(60, 60, 60)); // Even lighter on click
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                backButton.setBackground(backButton.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(25, 30, 0, 0); // Top, Left, Bottom, Right
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(backButton, gbc);

        // Centered groupPanel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(groupPanel, gbc);

        // Save button (bottom right)
        RoundedButton saveButton = new RoundedButton("Save", arc);
        saveButton.setFont(detailsFont);
        saveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight)); // width increased, height same as text fields
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(new Color(0, 153, 0));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover and click effect for save button
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                saveButton.setBackground(new Color(140, 153, 140)); // Light greenish white
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                saveButton.setBackground(Color.WHITE);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                saveButton.setBackground(new Color(200, 240, 200)); // Slightly darker greenish white
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                saveButton.setBackground(saveButton.getBounds().contains(e.getPoint()) ? new Color(230, 255, 230) : Color.WHITE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 30, 35); // Top, Left, Bottom, Right
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(saveButton, gbc);

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
                employeeTableModel.setRowCount(0);
                if (searchText.isEmpty()) {
                    for (Object[] row : data) employeeTableModel.addRow(row);
                } else {
                    for (Object[] row : data) {
                        boolean matchFound = false;
                        for (Object cell : row) {
                            if (cell != null && cell.toString().toLowerCase().contains(searchText)) {
                                matchFound = true; break;
                            }
                        }
                        if (matchFound) employeeTableModel.addRow(row);
                    }
                }
            }
        });
    }
}
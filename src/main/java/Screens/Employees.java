package Screens;

import Components.MenuPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Employees {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Employees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // --- Color Palette ---
        Color primaryColor = new Color(0, 150, 136); // Teal
        Color secondaryColor = new Color(200, 200, 200); // Light Gray
        Color accentColor = new Color(255, 255, 255); // White
        Font defaultFont = new Font("Arial", Font.PLAIN, 16);

        // Create a panel for navigation tabs
        MenuPanel tabPanel = new MenuPanel(frame);
        tabPanel.setBackground(primaryColor);
        for (Component component : tabPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setFont(defaultFont);
                button.setForeground(accentColor);
                button.setBackground(primaryColor);
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                button.setFocusPainted(false);
            }
        }

        // Create a search bar with a rounded appearance
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField();
        searchField.setFont(defaultFont);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(secondaryColor),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchField.putClientProperty("JTextField.variant", "search"); // For macOS search field appearance
        JButton searchButton = new JButton("Search");
        searchButton.setFont(defaultFont);
        searchButton.setBackground(secondaryColor);
        searchButton.setForeground(Color.DARK_GRAY);
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        searchButton.setFocusPainted(false);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Create a table to display employee data
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
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(defaultFont);
        table.setRowHeight(30);
        table.getTableHeader().setFont(defaultFont);
        table.setSelectionBackground(primaryColor.brighter());
        table.setSelectionForeground(accentColor);
        table.setShowGrid(true);
        table.setGridColor(secondaryColor);

        // Center align the table header text
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader()
                .getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(secondaryColor));

        // Adjust layout
        JPanel contentPanel = new JPanel(new CardLayout());

        // Table view panel
        JPanel tableViewPanel = new JPanel(new BorderLayout());
        tableViewPanel.add(searchPanel, BorderLayout.NORTH);
        tableViewPanel.add(tableScrollPane, BorderLayout.CENTER);
        tableViewPanel.setBorder(new EmptyBorder(0, 10, 10, 10)); // Add some padding around the table

        // Employee details view panel
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50)); // Center with horizontal/vertical
                                                                                     // gap
        JPanel detailsContainer = new JPanel(new GridLayout(9, 2, 10, 15)); // 9 rows, 2 columns, horizontal/vertical
                                                                            // gap
        detailsContainer.setBorder(new EmptyBorder(20, 50, 20, 50)); // Padding around details

        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Left align buttons
        JPanel saveButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Right align save

        JButton backButton = new JButton("Back");
        JButton editButton = new JButton("Edit");
        JButton saveButton = new JButton("Save");

        // Style the buttons (assuming defaultFont and colors are already defined)
        backButton.setFont(defaultFont);
        editButton.setFont(defaultFont);
        saveButton.setFont(defaultFont);
        backButton.setBackground(secondaryColor);
        editButton.setBackground(secondaryColor);
        saveButton.setBackground(primaryColor);
        backButton.setForeground(Color.DARK_GRAY);
        editButton.setForeground(Color.DARK_GRAY);
        saveButton.setForeground(accentColor);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        editButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backButton.setFocusPainted(false);
        editButton.setFocusPainted(false);
        saveButton.setFocusPainted(false);

        topButtonPanel.add(backButton);
        topButtonPanel.add(editButton);
        saveButtonPanel.add(saveButton);
        saveButton.setVisible(false);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        nameField.setEditable(false);
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        idField.setEditable(false);
        JLabel departmentLabel = new JLabel("Department:");
        JTextField departmentField = new JTextField();
        departmentField.setEditable(false);
        JLabel employmentStatusLabel = new JLabel("Employment Status:");
        JTextField employmentStatusField = new JTextField();
        employmentStatusField.setEditable(false);
        JLabel rateLabel = new JLabel("Rate / Hour:");
        JTextField rateField = new JTextField("₱ 610.00");
        rateField.setEditable(false);
        JLabel tinLabel = new JLabel("TIN No.:");
        JTextField tinField = new JTextField("000 – 123 – 456 – 001");
        tinField.setEditable(false);
        JLabel pagibigLabel = new JLabel("Pag-Ibig No.:");
        JTextField pagibigField = new JTextField("1234 – 5678 – 9101");
        pagibigField.setEditable(false);
        JLabel sssLabel = new JLabel("SSS No.:");
        JTextField sssField = new JTextField("02 – 1234567 – 9");
        sssField.setEditable(false);
        JLabel philhealthLabel = new JLabel("PhilHealth No.:");
        JTextField philhealthField = new JTextField("02 – 385929672 – 8");
        philhealthField.setEditable(false);

        JLabel[] labels = { nameLabel, idLabel, departmentLabel, employmentStatusLabel, rateLabel, tinLabel,
                pagibigLabel, sssLabel, philhealthLabel };
        JTextField[] fields = { nameField, idField, departmentField, employmentStatusField, rateField, tinField,
                pagibigField, sssField, philhealthField };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(defaultFont);
            fields[i].setFont(defaultFont);
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(secondaryColor),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            detailsContainer.add(labels[i]);
            detailsContainer.add(fields[i]);
        }

        detailsPanel.add(topButtonPanel);
        detailsPanel.add(detailsContainer);
        detailsPanel.add(saveButtonPanel);

        contentPanel.add(tableViewPanel, "TableView");
        contentPanel.add(detailsPanel, "DetailsView");

        frame.setLayout(new BorderLayout());
        frame.add(tabPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);

        // Add action listener to the table for row clicks
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    nameField.setText(table.getValueAt(row, 0).toString());
                    idField.setText(table.getValueAt(row, 1).toString());
                    departmentField.setText(table.getValueAt(row, 2).toString());
                    employmentStatusField.setText(table.getValueAt(row, 3).toString());

                    // Placeholder values for additional fields
                    rateField.setText("₱ 610.00");
                    tinField.setText("000 – 123 – 456 – 001");
                    pagibigField.setText("1234 – 5678 – 9101");
                    sssField.setText("02 – 1234567 – 9");
                    philhealthField.setText("02 – 385929672 – 8");

                    searchPanel.setVisible(false);
                    CardLayout cl = (CardLayout) (contentPanel.getLayout());
                    cl.show(contentPanel, "DetailsView");
                }
            }
        });

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "TableView");
                searchPanel.setVisible(true);
            }
        });

        // Add action listener to the edit button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make fields editable
                for (JTextField field : fields) {
                    field.setEditable(true);
                }

                // Hide edit button and show save button
                saveButton.setVisible(true);
                editButton.setVisible(false);
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();
            }
        });

        // Add action listener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make fields non-editable
                for (JTextField field : fields) {
                    field.setEditable(false);
                }

                // Display updated details (temporary change)
                System.out.println("Updated Details:");
                System.out.println("Name: " + nameField.getText());
                System.out.println("ID: " + idField.getText());
                System.out.println("Department: " + departmentField.getText());
                System.out.println("Employment Status: " + employmentStatusField.getText());
                System.out.println("Rate / Hour: " + rateField.getText());
                System.out.println("TIN No.: " + tinField.getText());
                System.out.println("Pag-Ibig No.: " + pagibigField.getText());
                System.out.println("SSS No.: " + sssField.getText());
                System.out.println("PhilHealth No.: " + philhealthField.getText());

                // Hide save button and show edit button
                saveButton.setVisible(false);
                editButton.setVisible(true);
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();
            }
        });

        // Fix pointer location row highlight feature
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

        // Fix search functionality to filter rows
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }

            private void filterTable() {
                String searchText = searchField.getText().toLowerCase();
                tableModel.setRowCount(0); // Clear the table
                if (searchText.isEmpty()) {
                    for (Object[] row : data) {
                        tableModel.addRow(row);
                    }
                } else {
                    for (Object[] row : data) {
                        boolean matchFound = false;
                        for (Object cell : row) {
                            if (cell != null && cell.toString().toLowerCase().contains(searchText)) {
                                matchFound = true;
                                break;
                            }
                        }
                        if (matchFound) {
                            tableModel.addRow(row);
                        }
                    }
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
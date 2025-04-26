package Screens;

import Components.MenuPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Employees {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Employees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Ensure the frame starts in maximized mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a panel for navigation tabs
        MenuPanel tabPanel = new MenuPanel(frame);


        // Create a search bar
        JPanel searchPanel = new JPanel();
        // Adjust the search text field to occupy the entire width
        searchPanel.setLayout(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchField.setPreferredSize(null); // Allow it to stretch
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Create a table to display employee data
        String[] columnNames = {"Name", "ID", "Department", "Employment Status"};
        Object[][] data = {
                {"Aela Cruz, Juan C.", 1, "Sales", "Regular"},
                {"Bela Cruz, Juan C.", 23, "Sales", "Regular"},
                {"Cela Cruz, Juan C.", 31, "Production(Pre-Press)", "Regular"},
                {"Dela Cruz, Juan C.", 14, "Production(Pre-Press)", "Regular"},
                {"Eela Cruz, Juan C.", 25, "Production(Pre-Press)", "Regular"},
                {"Fela Cruz, Juan C.", 36, "Production (Press)", "Regular"},
                {"Gela Cruz, Juan C.", 15, "Production(Pre-Press)", "Regular"},
                {"Hela Cruz, Juan C.", 4, "Production(Pre-Press)", "Regular"},
                {"Hela Cruz, Juan C.", 8, "Production (Post-Press)", "Regular"},
                {"Iela Cruz, Juan C.", 10, "Production (Post-Press)", "Regular"},
                {"Jela Cruz, Juan C.", 17, "Production (Quality Control)", "Regular"},
                {"Jela Cruz, Juan C.", 22, "Production (Quality Control)", "Regular"},
                {"Kela Cruz, Juan C.", 11, "Sales", "Regular"}
        };
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Increase font size for all text components
        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        table.setRowHeight(20);
        table.getTableHeader().setFont(font);
        searchField.setFont(font);
        searchButton.setFont(font);
        for (Component component : tabPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setFont(font);
            }
        }

        // Adjust layout to always display tabs/menus on top
        JPanel contentPanel = new JPanel(new CardLayout());

        // Table view panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Employee details view panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        
        JPanel topButtonPanel = new JPanel(new BorderLayout());
        JPanel saveButtonPanel = new JPanel(new FlowLayout()); // Panel for the save button

        // Create the buttons
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size for the button
        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size for the button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size for the button

        // Add buttons to opposite sides
        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topButtonPanel.add(backButton, BorderLayout.WEST); // Left side
        topButtonPanel.add(editButton, BorderLayout.EAST); // Right side

        saveButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        saveButtonPanel.add(saveButton);
        saveButtonPanel.setPreferredSize(new Dimension(0, 50)); // Always 50px tall
        saveButton.setVisible(false); // Initially hidden

        // Ensure the back button is properly added and visible
        detailsPanel.add(topButtonPanel, BorderLayout.NORTH);
        detailsPanel.add(saveButtonPanel, BorderLayout.SOUTH); // Add to the frame

        // Add additional fields for employee details
        JPanel combinedDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        nameField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        combinedDetailsPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(nameField, gbc);

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        idField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        combinedDetailsPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(idField, gbc);

        JLabel departmentLabel = new JLabel("Department:");
        JTextField departmentField = new JTextField();
        departmentField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        combinedDetailsPanel.add(departmentLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(departmentField, gbc);

        JLabel employmentStatusLabel = new JLabel("Employment Status:");
        JTextField employmentStatusField = new JTextField();
        employmentStatusField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        combinedDetailsPanel.add(employmentStatusLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(employmentStatusField, gbc);

        JLabel rateLabel = new JLabel("Rate / Hour:");
        JTextField rateField = new JTextField("₱ 610.00");
        rateField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        combinedDetailsPanel.add(rateLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(rateField, gbc);

        JLabel tinLabel = new JLabel("TIN No.:");
        JTextField tinField = new JTextField("000 – 123 – 456 – 001");
        tinField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        combinedDetailsPanel.add(tinLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(tinField, gbc);

        JLabel pagibigLabel = new JLabel("Pag-Ibig No.:");
        JTextField pagibigField = new JTextField("1234 – 5678 – 9101");
        pagibigField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        combinedDetailsPanel.add(pagibigLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(pagibigField, gbc);

        JLabel sssLabel = new JLabel("SSS No.:");
        JTextField sssField = new JTextField("02 – 1234567 – 9");
        sssField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        combinedDetailsPanel.add(sssLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(sssField, gbc);

        JLabel philhealthLabel = new JLabel("PhilHealth No.:");
        JTextField philhealthField = new JTextField("02 – 385929672 – 8");
        philhealthField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 8;
        combinedDetailsPanel.add(philhealthLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(philhealthField, gbc);

        detailsPanel.add(combinedDetailsPanel, BorderLayout.CENTER);

        // Add both panels to the content panel
        contentPanel.add(tablePanel, "TableView");
        contentPanel.add(detailsPanel, "DetailsView");

        // Add tabPanel and contentPanel to the frame
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

                    searchPanel.setVisible(false); // Hide search bar
                    CardLayout cl = (CardLayout)(contentPanel.getLayout());
                    cl.show(contentPanel, "DetailsView");
                }
            }
        });

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(contentPanel.getLayout());
                cl.show(contentPanel, "TableView");
                searchPanel.setVisible(true); // Show search bar
            }
        });

        // Add action listener to the edit button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make fields editable
                nameField.setEditable(true);
                idField.setEditable(true);
                departmentField.setEditable(true);
                employmentStatusField.setEditable(true);
                rateField.setEditable(true);
                tinField.setEditable(true);
                pagibigField.setEditable(true);
                sssField.setEditable(true);
                philhealthField.setEditable(true);

                // Hide edit button and show save button
                saveButton.setVisible(true);
                editButton.setVisible(false);
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();

                // Add action listener to the save button
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Make fields non-editable
                        nameField.setEditable(false);
                        idField.setEditable(false);
                        departmentField.setEditable(false);
                        employmentStatusField.setEditable(false);
                        rateField.setEditable(false);
                        tinField.setEditable(false);
                        pagibigField.setEditable(false);
                        sssField.setEditable(false);
                        philhealthField.setEditable(false);

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
            }
        });

        // Make the table cells non-editable
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing for all cells
            }
        };
        table.setModel(model);

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
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Clear the table
                if (searchText.isEmpty()) {
                    for (Object[] row : data) {
                        model.addRow(row);
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
                            model.addRow(row);
                        }
                    }
                }
            }
        });

        // Set font size of employee details to match the table
        Font detailsFont = new Font("Arial", Font.PLAIN, 16);
        nameLabel.setFont(detailsFont);
        nameField.setFont(detailsFont);
        idLabel.setFont(detailsFont);
        idField.setFont(detailsFont);
        departmentLabel.setFont(detailsFont);
        departmentField.setFont(detailsFont);
        employmentStatusLabel.setFont(detailsFont);
        employmentStatusField.setFont(detailsFont);
        rateLabel.setFont(detailsFont);
        rateField.setFont(detailsFont);
        tinLabel.setFont(detailsFont);
        tinField.setFont(detailsFont);
        pagibigLabel.setFont(detailsFont);
        pagibigField.setFont(detailsFont);
        sssLabel.setFont(detailsFont);
        sssField.setFont(detailsFont);
        philhealthLabel.setFont(detailsFont);
        philhealthField.setFont(detailsFont);

        // Make the frame visible
        frame.setVisible(true);
    }
}
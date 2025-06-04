package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Employees extends JPanel {
    public Employees(JFrame parentFrame) {
        setLayout(new BorderLayout());

        // Create a search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchField.setPreferredSize(null);
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
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Increase font size for all text components
        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        table.setRowHeight(20);
        table.getTableHeader().setFont(font);
        searchField.setFont(font);
        searchButton.setFont(font);

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
        JPanel saveButtonPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(100, 30));
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30));

        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topButtonPanel.add(backButton, BorderLayout.WEST);
        topButtonPanel.add(editButton, BorderLayout.EAST);

        saveButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        saveButtonPanel.add(saveButton);
        saveButtonPanel.setPreferredSize(new Dimension(0, 50));
        saveButton.setVisible(false);

        detailsPanel.add(topButtonPanel, BorderLayout.NORTH);
        detailsPanel.add(saveButtonPanel, BorderLayout.SOUTH);

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

        contentPanel.add(tablePanel, "TableView");
        contentPanel.add(detailsPanel, "DetailsView");

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    nameField.setText(table.getValueAt(row, 0).toString());
                    idField.setText(table.getValueAt(row, 1).toString());
                    departmentField.setText(table.getValueAt(row, 2).toString());
                    employmentStatusField.setText(table.getValueAt(row, 3).toString());

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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "TableView");
                searchPanel.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setEditable(true);
                idField.setEditable(true);
                departmentField.setEditable(true);
                employmentStatusField.setEditable(true);
                rateField.setEditable(true);
                tinField.setEditable(true);
                pagibigField.setEditable(true);
                sssField.setEditable(true);
                philhealthField.setEditable(true);

                saveButton.setVisible(true);
                editButton.setVisible(false);
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nameField.setEditable(false);
                        idField.setEditable(false);
                        departmentField.setEditable(false);
                        employmentStatusField.setEditable(false);
                        rateField.setEditable(false);
                        tinField.setEditable(false);
                        pagibigField.setEditable(false);
                        sssField.setEditable(false);
                        philhealthField.setEditable(false);

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

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

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
                model.setRowCount(0);
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
    }
}
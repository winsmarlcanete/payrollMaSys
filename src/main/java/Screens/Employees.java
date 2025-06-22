package Screens;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import Components.RoundedComboBox;
import Components.TableStyler;
import Module.E201File.E201File;
import jdk.swing.interop.DispatcherWrapper;
import org.payroll.MainWindow;

public class Employees extends JPanel {

    private static DefaultTableModel employeeTableModel;


    private static String[] tableViewHeaders = { "Name", "ID", "Department", "Employment Status" };
    private static String[] detailsViewHeaders = { "Last Name", "First Name", "Middle Name", "Department", "Employment Status", "Rate / Hour", "TIN No.", "Pag-Ibig No.", "SSS No.", "PhilHealth No." };
    private static Object[][] tableViewData;
    private static Object[][] detailsViewData;
    private JTable table;
    private JTextField searchField;

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


    public void clearSearchField() {
        searchField.setText("");
    }



    public Employees(JFrame parentFrame, Object[][] employeeTableData) {
        setLayout(new BorderLayout());

        // Search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(10, 0));
        searchPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 70));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        Font font = new Font("Arial", Font.PLAIN, 16);

        searchField = new JTextField();
        searchField.setFont(font);
        searchField.setBorder(null);
        searchField.setPreferredSize(null);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(font);
        searchButton.setPreferredSize(new Dimension(150, 70));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(null);
        searchButton.setFocusable(false);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.WEST);

        // Dropdown
        RoundedComboBox<String> sortCombo = new RoundedComboBox<>(new String[] {
                "All Departments", "Human Resource", "Administration", "Accounting", "Sales",  "Production", "Production (Pre-Press)", "Production (Press)", "Production (Post-Press)", "Production (Quality Control)"
        }) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };
        sortCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        sortCombo.setPreferredSize(new Dimension(250, 36));
        sortCombo.setBackground(Color.WHITE);
        sortCombo.setFocusable(false);
        sortCombo.setMaximumRowCount(12);
        ((JLabel)sortCombo.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Custom renderer for hover effect in dropdown list
        sortCombo.setRenderer(new DefaultListCellRenderer() {
            private int hoveredIndex = -1;
            {
                // Add mouse motion listener to popup list for hover effect
                sortCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                        JList<?> list = getPopupList();
                        if (list != null) {
                            list.addMouseMotionListener(new MouseMotionAdapter() {
                                @Override
                                public void mouseMoved(MouseEvent e) {
                                    hoveredIndex = list.locationToIndex(e.getPoint());
                                    list.repaint();
                                }
                            });
                            list.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    hoveredIndex = -1;
                                    list.repaint();
                                }
                            });
                        }
                    }
                    @Override public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
                    @Override public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
                    private JList<?> getPopupList() {
                        ComboPopup popup = (ComboPopup) sortCombo.getUI().getAccessibleChild(sortCombo, 0);
                        return popup != null ? popup.getList() : null;
                    }
                });
            }
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index >= 0 && index == hoveredIndex && !isSelected) {
                    c.setBackground(new Color(230, 255, 230)); // light greenish
                }
                return c;
            }
        });

        // Add hover effect (like LeaveManagement)
        Color comboDefaultBg = Color.WHITE;
        Color comboHoverBg = new Color(230, 255, 230); // light greenish
        sortCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (sortCombo.isEnabled()) {
                    sortCombo.setBackground(comboHoverBg);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                sortCombo.setBackground(comboDefaultBg);
            }
        });

        // Use GridBagLayout for vertical centering
        JPanel comboPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCombo = new GridBagConstraints();
        gbcCombo.gridx = 0;
        gbcCombo.gridy = 0;
        gbcCombo.anchor = GridBagConstraints.CENTER;
        JLabel sortLabel = new JLabel("Sort by Department: ");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
        comboPanel.add(sortLabel, gbcCombo);

        gbcCombo.gridx = 1;
        comboPanel.add(sortCombo, gbcCombo);
        comboPanel.setOpaque(true);
        comboPanel.setBackground(Color.WHITE);

        searchPanel.add(comboPanel, BorderLayout.EAST);

        // Table data
        Object[][] data = employeeTableData;
        employeeTableModel = new DefaultTableModel(tableViewData, tableViewHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(employeeTableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getTableHeader().setReorderingAllowed(false);
        TableStyler.styleTable(table);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Set table header background to green and foreground to white
        JTableHeader header = table.getTableHeader();
        header.setBackground(MainWindow.grayColor);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setResizingAllowed(false);

        // Remove the up/down arrow buttons from the vertical scrollbar
        tableScrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                button.setVisible(false);
                return button;
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 177, 76); // green
                this.trackColor = new Color(220, 255, 220); // light green track
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 177, 76)); // green
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(220, 255, 220)); // light green
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });

        // Font

        table.setFont(font);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));



        // CardLayout for switching views
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.RED);



        // Table view panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(MainWindow.activeColor);
        tablePanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Employee details view panel
        JPanel detailsPanel = new JPanel(new BorderLayout());

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

        // Details fields
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        lastNameField.setEditable(false);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        firstNameField.setEditable(false);

        JLabel middleNameLabel = new JLabel("Middle Name:");
        JTextField middleNameField = new JTextField();
        middleNameField.setEditable(false);

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

        // Add fields to panel
        gbc.gridx = 0; gbc.gridy = 0;
        combinedDetailsPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        combinedDetailsPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        combinedDetailsPanel.add(middleNameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(middleNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        combinedDetailsPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        combinedDetailsPanel.add(departmentLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(departmentField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        combinedDetailsPanel.add(employmentStatusLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(employmentStatusField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        combinedDetailsPanel.add(rateLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(rateField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        combinedDetailsPanel.add(tinLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(tinField, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        combinedDetailsPanel.add(pagibigLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(pagibigField, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        combinedDetailsPanel.add(sssLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(sssField, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        combinedDetailsPanel.add(philhealthLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(philhealthField, gbc);

        detailsPanel.add(combinedDetailsPanel, BorderLayout.CENTER);

        contentPanel.add(tablePanel, "TableView");
        contentPanel.add(detailsPanel, "DetailsView");


        add(contentPanel, BorderLayout.CENTER);

        // Store default border and insets for restoration and spacing
        Border defaultBorder = lastNameField.getBorder();
        Insets defaultInsets = defaultBorder.getBorderInsets(lastNameField);

        // Helper to set all fields to "plain text" look but keep spacing
        Runnable setPlainTextLook = () -> {
            Border emptyBorder = BorderFactory.createEmptyBorder(
                defaultInsets.top, defaultInsets.left, defaultInsets.bottom, defaultInsets.right
            );
            lastNameField.setBorder(emptyBorder);
            middleNameField.setBorder(emptyBorder);
            firstNameField.setBorder(emptyBorder);
            idField.setBorder(emptyBorder);
            departmentField.setBorder(emptyBorder);
            employmentStatusField.setBorder(emptyBorder);
            rateField.setBorder(emptyBorder);
            tinField.setBorder(emptyBorder);
            pagibigField.setBorder(emptyBorder);
            sssField.setBorder(emptyBorder);
            philhealthField.setBorder(emptyBorder);
            Color bg = combinedDetailsPanel.getBackground();
            lastNameField.setBackground(bg);
            idField.setBackground(bg);
            departmentField.setBackground(bg);
            employmentStatusField.setBackground(bg);
            rateField.setBackground(bg);
            tinField.setBackground(bg);
            pagibigField.setBackground(bg);
            sssField.setBackground(bg);
            philhealthField.setBackground(bg);
        };

        // Helper to restore default borders
        Runnable setEditableLook = () -> {
            lastNameField.setBorder(defaultBorder);
            middleNameField.setBorder(defaultBorder);
            firstNameField.setBorder(defaultBorder);
            idField.setBorder(defaultBorder);
            departmentField.setBorder(defaultBorder);
            employmentStatusField.setBorder(defaultBorder);
            rateField.setBorder(defaultBorder);
            tinField.setBorder(defaultBorder);
            pagibigField.setBorder(defaultBorder);
            sssField.setBorder(defaultBorder);
            philhealthField.setBorder(defaultBorder);
            Color bg = Color.WHITE;
            lastNameField.setBackground(bg);
            idField.setBackground(bg);
            departmentField.setBackground(bg);
            employmentStatusField.setBackground(bg);
            rateField.setBackground(bg);
            tinField.setBackground(bg);
            pagibigField.setBackground(bg);
            sssField.setBackground(bg);
            philhealthField.setBackground(bg);
        };

        // Set plain text look initially
        setPlainTextLook.run();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    lastNameField.setText(detailsViewData[row][0].toString());
                    firstNameField.setText(detailsViewData[row][1].toString());
                    middleNameField.setText(detailsViewData[row][2].toString());
                    idField.setText(detailsViewData[row][3].toString());
                    departmentField.setText(detailsViewData[row][4].toString());
                    employmentStatusField.setText(detailsViewData[row][5].toString());
                    rateField.setText("₱ " + detailsViewData[row][6].toString());
                    tinField.setText(detailsViewData[row][7].toString());
                    pagibigField.setText(detailsViewData[row][8].toString());
                    sssField.setText(detailsViewData[row][9].toString());
                    philhealthField.setText(detailsViewData[row][10].toString());

                    setPlainTextLook.run();

                    searchPanel.setVisible(false);
                    CardLayout cl = (CardLayout) (contentPanel.getLayout());
                    cl.show(contentPanel, "DetailsView");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reload employee table data
                loadEmployeeTabledata();

                // Switch back to the table view
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "TableView");
                searchPanel.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastNameField.setEditable(true);
                firstNameField.setEditable(true);
                middleNameField.setEditable(true);
                //idField.setEditable(true);
                departmentField.setEditable(true);
                employmentStatusField.setEditable(true);
                rateField.setEditable(true);
                tinField.setEditable(true);
                pagibigField.setEditable(true);
                sssField.setEditable(true);
                philhealthField.setEditable(true);

                setEditableLook.run();

                saveButton.setVisible(true);
                editButton.setVisible(false);
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int employeeId = Integer.parseInt(idField.getText());
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String middleName = middleNameField.getText();
                String department = departmentField.getText();
                String employmentStatus = employmentStatusField.getText();
                double payRate = Double.parseDouble(rateField.getText().replace("₱ ", ""));
                String tinNumber = tinField.getText();
                String pagibigNumber = pagibigField.getText();
                String sssNumber = sssField.getText();
                String philhealthNumber = philhealthField.getText();

                E201File.updateEmployeeData( lastName, firstName, middleName, department,
                        employmentStatus, payRate, tinNumber, pagibigNumber,
                        sssNumber, philhealthNumber,employeeId);

                // Reset fields to non-editable
                lastNameField.setEditable(false);
                firstNameField.setEditable(false);
                middleNameField.setEditable(false);
                idField.setEditable(false);
                departmentField.setEditable(false);
                employmentStatusField.setEditable(false);
                rateField.setEditable(false);
                tinField.setEditable(false);
                pagibigField.setEditable(false);
                sssField.setEditable(false);
                philhealthField.setEditable(false);

                setPlainTextLook.run();

                saveButton.setVisible(false);
                editButton.setVisible(true);
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();
            }
        });



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
        lastNameLabel.setFont(detailsFont);
        lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lastNameField.setFont(detailsFont);

        firstNameLabel.setFont(detailsFont);
        firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        firstNameField.setFont(detailsFont);

        middleNameLabel.setFont(detailsFont);
        middleNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        middleNameField.setFont(detailsFont);

        idLabel.setFont(detailsFont);
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        idField.setFont(detailsFont);

        departmentLabel.setFont(detailsFont);
        departmentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        departmentField.setFont(detailsFont);

        employmentStatusLabel.setFont(detailsFont);
        employmentStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employmentStatusField.setFont(detailsFont);

        rateLabel.setFont(detailsFont);
        rateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rateField.setFont(detailsFont);

        tinLabel.setFont(detailsFont);
        tinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tinField.setFont(detailsFont);

        pagibigLabel.setFont(detailsFont);
        pagibigLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pagibigField.setFont(detailsFont);

        sssLabel.setFont(detailsFont);
        sssLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sssField.setFont(detailsFont);

        philhealthLabel.setFont(detailsFont);
        philhealthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        philhealthField.setFont(detailsFont);
    }



}

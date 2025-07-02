package Screens;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import Algorithms.BinarySearch;
import Components.RoundedComboBox;
import Components.TableStyler;
import Module.E201File.E201File;
import jdk.swing.interop.DispatcherWrapper;
import org.payroll.MainWindow;


public class Employees extends JPanel {
    private final Runnable setPlainTextLook;

    private static DefaultTableModel employeeTableModel;
    private Map<Integer, Integer> filteredToOriginalIndex = new HashMap<>();
    private boolean hasUnsavedChanges = false;

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField idField;
    private JTextField departmentField;
    private JTextField employmentStatusField;
    private JTextField shiftStartField;
    private JTextField shiftEndField;
    private JTextField payRateField;
    private JTextField tinNumberField;
    private JTextField pagibigNumberField;
    private JTextField pagibigPercentageField;
    private JTextField sssNumberField;
    private JTextField sssPercentageField;
    private JTextField philhealthNumberField;
    private JTextField philhealthPercentageField;
    private JTextField efundAmountField;
    private JTextField otherDeductionsField;
    private JTextField salaryAdjPercentageField;
    private JTextField allowanceAmountField;
    private JTextField otherCompAmountField;
    private RoundedButton saveButton;
    private RoundedButton editButton;
    private JPanel topButtonPanel;
    private JPanel saveButtonPanel;

    public boolean getHasUnsavedChanges() {
        return hasUnsavedChanges;
    }


    private final Runnable setEditableLook = () -> {
        Border lineBorder = BorderFactory.createLineBorder(new Color(34, 177, 76), 1);
        Border paddingBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);

        lastNameField.setBorder(compoundBorder);
        middleNameField.setBorder(compoundBorder);
        firstNameField.setBorder(compoundBorder);
        departmentField.setBorder(compoundBorder);
        employmentStatusField.setBorder(compoundBorder);
        shiftStartField.setBorder(compoundBorder);
        shiftEndField.setBorder(compoundBorder);
        payRateField.setBorder(compoundBorder);
        tinNumberField.setBorder(compoundBorder);
        pagibigNumberField.setBorder(compoundBorder);
        pagibigPercentageField.setBorder(compoundBorder);
        sssNumberField.setBorder(compoundBorder);
        sssPercentageField.setBorder(compoundBorder);
        philhealthNumberField.setBorder(compoundBorder);
        philhealthPercentageField.setBorder(compoundBorder);
        efundAmountField.setBorder(compoundBorder);
        otherDeductionsField.setBorder(compoundBorder);
        salaryAdjPercentageField.setBorder(compoundBorder);
        allowanceAmountField.setBorder(compoundBorder);
        otherCompAmountField.setBorder(compoundBorder);

        Color bg = Color.WHITE;
        lastNameField.setBackground(bg);
        middleNameField.setBackground(bg);
        firstNameField.setBackground(bg);
        departmentField.setBackground(bg);
        employmentStatusField.setBackground(bg);
        shiftStartField.setBackground(bg);
        shiftEndField.setBackground(bg);
        payRateField.setBackground(bg);
        tinNumberField.setBackground(bg);
        pagibigNumberField.setBackground(bg);
        pagibigPercentageField.setBackground(bg);
        sssNumberField.setBackground(bg);
        sssPercentageField.setBackground(bg);
        philhealthNumberField.setBackground(bg);
        philhealthPercentageField.setBackground(bg);
        efundAmountField.setBackground(bg);
        otherDeductionsField.setBackground(bg);
        salaryAdjPercentageField.setBackground(bg);
        allowanceAmountField.setBackground(bg);
        otherCompAmountField.setBackground(bg);
    };

    private static void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private static String[] tableViewHeaders = { "Name", "ID", "Department", "Employment Status" };
    private static String[] detailsViewHeaders = {
            "ID", "Last Name", "First Name", "Middle Name", "Department", "Employment Status",
            "Shift Start", "Shift End", "Pay Rate", "TIN Number", "Pag-Ibig Number", "Pag-Ibig Percentage",
            "SSS Number", "SSS Percentage", "PhilHealth Number", "PhilHealth Percentage",
            "E-Fund Amount", "Other Deductions"
    };
    private static Object[][] tableViewData;
    private static Object[][] detailsViewData;
    private static JTable table;
    private JTextField searchField;
    private RoundedComboBox<String> sortCombo;

    public static void loadEmployeeTabledata() {
        Object[][] rawData = E201File.getEmployeeTableData(); // Query your source

        // Map data for table view
        tableViewData = new Object[rawData.length][tableViewHeaders.length];
        for (int i = 0; i < rawData.length; i++) {
            tableViewData[i][0] = rawData[i][1] + ", " + rawData[i][2]; // Combine last name and first name
            tableViewData[i][1] = rawData[i][0]; // ID
            tableViewData[i][2] = rawData[i][4]; // Department
            tableViewData[i][3] = rawData[i][5]; // Employment Status
        }

        // Map data for details view
        detailsViewData = rawData; // Use rawData directly for details view
        employeeTableModel.setDataVector(tableViewData, tableViewHeaders);
        centerTableCells(table);
    }


    public void clearSearchField() {
        searchField.setText("");
    }

    private void initializeFilteredIndices() {
        filteredToOriginalIndex.clear();
        // Map all original indices when table is first loaded
        for (int i = 0; i < tableViewData.length; i++) {
            filteredToOriginalIndex.put(i, i);
        }
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

        searchField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().length() == 0) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.GRAY);
                    g2.setFont(getFont().deriveFont(Font.PLAIN));
                    FontMetrics fm = g2.getFontMetrics();
                    int padding = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString("Search", 15, padding);
                    g2.dispose();
                }
            }
        };
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
        //searchPanel.add(searchButton, BorderLayout.WEST);

        // Dropdown
        sortCombo = new RoundedComboBox<>(E201File.retrieveAllDepartments()) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };

        sortCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        sortCombo.setPreferredSize(new Dimension(250, 50));
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

        sortCombo.addActionListener(e -> {
            String selected = (String) sortCombo.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            filteredToOriginalIndex.clear();

            if (selected == null || selected.equals("All Departments")) {
                // Show all departments
                for (int i = 0; i < tableViewData.length; i++) {
                    model.addRow(tableViewData[i]);
                    filteredToOriginalIndex.put(i, i);
                }
            } else {
                // Filter by selected department
                int filteredIndex = 0;
                for (int i = 0; i < tableViewData.length; i++) {
                    Object[] row = tableViewData[i];
                    String department = row[2].toString(); // Department column
                    if (department.equals(selected)) {
                        model.addRow(row);
                        filteredToOriginalIndex.put(filteredIndex, i);
                        filteredIndex++;
                    }
                }
            }
        });

        sortCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                sortCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                sortCombo.setCursor(Cursor.getDefaultCursor());
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

        tableViewData = employeeTableData;

        employeeTableModel = new DefaultTableModel(tableViewData, tableViewHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(employeeTableModel);
        initializeFilteredIndices();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getTableHeader().setReorderingAllowed(false);;
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

        table.setRowHeight(40);
        table.getTableHeader().setFont(font);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        TableStyler.styleTable(table);


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

        topButtonPanel = new JPanel(new BorderLayout());
        saveButtonPanel = new JPanel(new FlowLayout());

        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setPreferredSize(new Dimension(100, 30));
        editButton = new RoundedButton("Edit", 20);
        editButton.setPreferredSize(new Dimension(100, 30));
        saveButton = new RoundedButton("Save", 20);
        saveButton.setPreferredSize(new Dimension(100, 30));

        topButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topButtonPanel.add(backButton, BorderLayout.WEST);
        topButtonPanel.add(editButton, BorderLayout.EAST);

        saveButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        saveButtonPanel.add(saveButton);
        saveButtonPanel.setPreferredSize(new Dimension(0, 70));
        saveButton.setVisible(false);

        detailsPanel.add(topButtonPanel, BorderLayout.NORTH);
        detailsPanel.add(saveButtonPanel, BorderLayout.SOUTH);

        JPanel combinedDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Details fields
        // Update fields in detailsView
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        lastNameField.setEditable(false);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        firstNameField.setEditable(false);

        JLabel middleNameLabel = new JLabel("Middle Name:");
        middleNameField = new JTextField();
        middleNameField.setEditable(false);

        JLabel departmentLabel = new JLabel("Department:");
        departmentField = new JTextField();
        departmentField.setEditable(false);

        JLabel employmentStatusLabel = new JLabel("Employment Status:");
        employmentStatusField = new JTextField();
        employmentStatusField.setEditable(false);

        JLabel shiftStartLabel = new JLabel("Shift Start:");
        shiftStartField = new JTextField();
        shiftStartField.setEditable(false);

        JLabel shiftEndLabel = new JLabel("Shift End:");
        shiftEndField = new JTextField();
        shiftEndField.setEditable(false);
        JLabel payRateLabel = new JLabel("Pay Rate:");
        payRateField = new JTextField();
        payRateField.setEditable(false);

        JLabel tinNumberLabel = new JLabel("TIN Number:");
        tinNumberField = new JTextField();
        tinNumberField.setEditable(false);

        JLabel pagibigNumberLabel = new JLabel("Pag-Ibig Number:");
        pagibigNumberField = new JTextField();
        pagibigNumberField.setEditable(false);

        JLabel pagibigPercentageLabel = new JLabel("Pag-Ibig Percentage:");
        pagibigPercentageField = new JTextField();
        pagibigPercentageField.setEditable(false);

        JLabel sssNumberLabel = new JLabel("SSS Number:");
        sssNumberField = new JTextField();
        sssNumberField.setEditable(false);

        JLabel sssPercentageLabel = new JLabel("SSS Percentage:");
        sssPercentageField = new JTextField();
        sssPercentageField.setEditable(false);

        JLabel philhealthNumberLabel = new JLabel("PhilHealth Number:");
        philhealthNumberField = new JTextField();
        philhealthNumberField.setEditable(false);

        JLabel philhealthPercentageLabel = new JLabel("PhilHealth Percentage:");
        philhealthPercentageField = new JTextField();
        philhealthPercentageField.setEditable(false);

        JLabel efundAmountLabel = new JLabel("E-Fund Amount:");
        efundAmountField = new JTextField();
        efundAmountField.setEditable(false);

        JLabel otherDeductionsLabel = new JLabel("Other Deductions:");
        otherDeductionsField = new JTextField();
        otherDeductionsField.setEditable(false);

        JLabel salaryAdjPercentageLabel = new JLabel("Salary Adjustment Percentage:");
        salaryAdjPercentageField = new JTextField();
        salaryAdjPercentageField.setEditable(false);

        JLabel allowanceAmountLabel = new JLabel("Allowance Amount:");
        allowanceAmountField = new JTextField();
        allowanceAmountField.setEditable(false);

        JLabel otherCompAmountLabel = new JLabel("Other Compensation Amount:");
        otherCompAmountField = new JTextField();
        otherCompAmountField.setEditable(false);

        setPlainTextLook = () -> {
            Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            lastNameField.setBorder(emptyBorder);
            middleNameField.setBorder(emptyBorder);
            firstNameField.setBorder(emptyBorder);
            idField.setBorder(emptyBorder);
            departmentField.setBorder(emptyBorder);
            employmentStatusField.setBorder(emptyBorder);
            shiftStartField.setBorder(emptyBorder);
            shiftEndField.setBorder(emptyBorder);
            payRateField.setBorder(emptyBorder);
            tinNumberField.setBorder(emptyBorder);
            pagibigNumberField.setBorder(emptyBorder);
            pagibigPercentageField.setBorder(emptyBorder);
            sssNumberField.setBorder(emptyBorder);
            sssPercentageField.setBorder(emptyBorder);
            philhealthNumberField.setBorder(emptyBorder);
            philhealthPercentageField.setBorder(emptyBorder);
            efundAmountField.setBorder(emptyBorder);
            otherDeductionsField.setBorder(emptyBorder);
            salaryAdjPercentageField.setBorder(emptyBorder);
            allowanceAmountField.setBorder(emptyBorder);
            otherCompAmountField.setBorder(emptyBorder);

            Color bg = getBackground();
            lastNameField.setBackground(bg);
            middleNameField.setBackground(bg);
            firstNameField.setBackground(bg);
            idField.setBackground(bg);
            departmentField.setBackground(bg);
            employmentStatusField.setBackground(bg);
            shiftStartField.setBackground(bg);
            shiftEndField.setBackground(bg);
            payRateField.setBackground(bg);
            tinNumberField.setBackground(bg);
            pagibigNumberField.setBackground(bg);
            pagibigPercentageField.setBackground(bg);
            sssNumberField.setBackground(bg);
            sssPercentageField.setBackground(bg);
            philhealthNumberField.setBackground(bg);
            philhealthPercentageField.setBackground(bg);
            efundAmountField.setBackground(bg);
            otherDeductionsField.setBackground(bg);
            salaryAdjPercentageField.setBackground(bg);
            allowanceAmountField.setBackground(bg);
            otherCompAmountField.setBackground(bg);
        };

// Add fields to combinedDetailsPanel
        // First column
        gbc.gridy = 0; // Start at row 0
        gbc.gridx = 0;
        combinedDetailsPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(idField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        combinedDetailsPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(lastNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        combinedDetailsPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(firstNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        combinedDetailsPanel.add(middleNameLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(middleNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        combinedDetailsPanel.add(departmentLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(departmentField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        combinedDetailsPanel.add(employmentStatusLabel, gbc);
        gbc.gridx = 1;
        combinedDetailsPanel.add(employmentStatusField, gbc);

// Second column
        gbc.gridy = 0; // Reset row for second column
        gbc.gridx = 2;
        combinedDetailsPanel.add(shiftStartLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(shiftStartField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(shiftEndLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(shiftEndField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(payRateLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(payRateField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(tinNumberLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(tinNumberField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(pagibigNumberLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(pagibigNumberField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(pagibigPercentageLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(pagibigPercentageField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(sssNumberLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(sssNumberField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(sssPercentageLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(sssPercentageField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(philhealthNumberLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(philhealthNumberField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(philhealthPercentageLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(philhealthPercentageField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(efundAmountLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(efundAmountField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(otherDeductionsLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(otherDeductionsField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(salaryAdjPercentageLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(salaryAdjPercentageField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(allowanceAmountLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(allowanceAmountField, gbc);

        gbc.gridy++;
        gbc.gridx = 2;
        combinedDetailsPanel.add(otherCompAmountLabel, gbc);
        gbc.gridx = 3;
        combinedDetailsPanel.add(otherCompAmountField, gbc);


        detailsPanel.add(combinedDetailsPanel, BorderLayout.CENTER);


        contentPanel.add(tablePanel, "TableView");
        contentPanel.add(detailsPanel, "DetailsView");


        add(contentPanel, BorderLayout.CENTER);

        // Store default border and insets for restoration and spacing
        Border defaultBorder = lastNameField.getBorder();
        Insets defaultInsets = defaultBorder.getBorderInsets(lastNameField);


        // Set plain text look initially
        setPlainTextLook.run();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1) {
                    // Get the original index from our mapping
                    int modelRow = filteredToOriginalIndex.get(viewRow);

                    // Use the correct index to populate fields
                    idField.setText(detailsViewData[modelRow][0].toString());
                    lastNameField.setText(detailsViewData[modelRow][1].toString());
                    firstNameField.setText(detailsViewData[modelRow][2].toString());
                    middleNameField.setText(detailsViewData[modelRow][3].toString());
                    departmentField.setText(detailsViewData[modelRow][4].toString());
                    employmentStatusField.setText(detailsViewData[modelRow][5].toString());
                    shiftStartField.setText(detailsViewData[modelRow][6].toString());
                    shiftEndField.setText(detailsViewData[modelRow][7].toString());
                    payRateField.setText("₱ " + detailsViewData[modelRow][8].toString());
                    tinNumberField.setText(detailsViewData[modelRow][9].toString());
                    pagibigNumberField.setText(detailsViewData[modelRow][10].toString());
                    pagibigPercentageField.setText(detailsViewData[modelRow][11].toString());
                    sssNumberField.setText(detailsViewData[modelRow][12].toString());
                    sssPercentageField.setText(detailsViewData[modelRow][13].toString());
                    philhealthNumberField.setText(detailsViewData[modelRow][14].toString());
                    philhealthPercentageField.setText(detailsViewData[modelRow][15].toString());
                    efundAmountField.setText(detailsViewData[modelRow][16].toString());
                    otherDeductionsField.setText(detailsViewData[modelRow][17].toString());
                    salaryAdjPercentageField.setText(detailsViewData[modelRow][18].toString());
                    allowanceAmountField.setText(detailsViewData[modelRow][19].toString());
                    otherCompAmountField.setText(detailsViewData[modelRow][20].toString());

                    setPlainTextLook.run();

                    searchPanel.setVisible(false);
                    CardLayout cl = (CardLayout) (contentPanel.getLayout());
                    cl.show(contentPanel, "DetailsView");
                }
            }
        });

        Font detailsFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonDetailsFont = new Font("Arial", Font.BOLD, 16);

        backButton.setFont(buttonDetailsFont);
        backButton.setPreferredSize(new Dimension(160, 40)); // width increased, height same as text fields
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        editButton.setFont(buttonDetailsFont);
        editButton.setPreferredSize(new Dimension(160, 40)); // width increased, height same as text fields
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(new Color(0, 158, 0));
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder());
        editButton.setContentAreaFilled(false);
        editButton.setOpaque(false);
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        saveButton.setFont(buttonDetailsFont);
        saveButton.setPreferredSize(new Dimension(160, 40)); // width increased, height same as text fields
        saveButton.setBackground(MainWindow.activeColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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

        // Hover and click effect for save button
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                editButton.setBackground(new Color(140, 153, 140)); // Light greenish white
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                editButton.setBackground(Color.WHITE);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                editButton.setBackground(new Color(200, 240, 200)); // Slightly darker greenish white
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                editButton.setBackground(editButton.getBounds().contains(e.getPoint()) ? new Color(230, 255, 230) : Color.WHITE);
            }
        });

        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                saveButton.setBackground(new Color(50, 180, 50)); // Slightly lighter green
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                saveButton.setBackground(MainWindow.activeColor);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                saveButton.setBackground(new Color(30, 150, 30)); // Darker green on click
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                saveButton.setBackground(saveButton.getBounds().contains(e.getPoint()) ?
                        new Color(50, 180, 50) : MainWindow.activeColor);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasUnsavedChanges) {
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "You have unsaved changes. Do you want to continue without saving?",
                            "Unsaved Changes",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice != JOptionPane.YES_OPTION) {
                        return; // Don't go back if user chooses not to continue
                    }

                    // Reset UI elements if user chooses to discard changes
                    saveButton.setVisible(false);
                    editButton.setVisible(true);

                    // Make all fields non-editable again
                    lastNameField.setEditable(false);
                    firstNameField.setEditable(false);
                    middleNameField.setEditable(false);
                    departmentField.setEditable(false);
                    employmentStatusField.setEditable(false);
                    shiftStartField.setEditable(false);
                    shiftEndField.setEditable(false);
                    payRateField.setEditable(false);
                    tinNumberField.setEditable(false);
                    pagibigNumberField.setEditable(false);
                    pagibigPercentageField.setEditable(false);
                    sssNumberField.setEditable(false);
                    sssPercentageField.setEditable(false);
                    philhealthNumberField.setEditable(false);
                    philhealthPercentageField.setEditable(false);
                    efundAmountField.setEditable(false);
                    otherDeductionsField.setEditable(false);
                    salaryAdjPercentageField.setEditable(false);
                    allowanceAmountField.setEditable(false);
                    otherCompAmountField.setEditable(false);

                    setPlainTextLook.run();
                }

                // Reset the unsaved changes flag
                hasUnsavedChanges = false;

                // Reload employee table data
                loadEmployeeTabledata();
                searchField.setText("");

                // Switch back to the table view
                CardLayout cl = (CardLayout) (contentPanel.getLayout());
                cl.show(contentPanel, "TableView");
                searchPanel.setVisible(true);

                // Refresh the panels
                topButtonPanel.revalidate();
                topButtonPanel.repaint();
                saveButtonPanel.revalidate();
                saveButtonPanel.repaint();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hasUnsavedChanges = true;

                lastNameField.setEditable(true);
                firstNameField.setEditable(true);
                middleNameField.setEditable(true);
                departmentField.setEditable(true);
                employmentStatusField.setEditable(true);
                shiftStartField.setEditable(true);
                shiftEndField.setEditable(true);
                payRateField.setEditable(true);
                tinNumberField.setEditable(true);
                pagibigNumberField.setEditable(true);
                pagibigPercentageField.setEditable(true);
                sssNumberField.setEditable(true);
                sssPercentageField.setEditable(true);
                philhealthNumberField.setEditable(true);
                philhealthPercentageField.setEditable(true);
                efundAmountField.setEditable(true);
                otherDeductionsField.setEditable(true);
                salaryAdjPercentageField.setEditable(true);
                allowanceAmountField.setEditable(true);
                otherCompAmountField.setEditable(true);

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
                try {
                    int employeeId = Integer.parseInt(idField.getText());
                    String lastName = lastNameField.getText();
                    String firstName = firstNameField.getText();
                    String middleName = middleNameField.getText();
                    String department = departmentField.getText();
                    String employmentStatus = employmentStatusField.getText();
                    Time shiftStart = Time.valueOf(shiftStartField.getText());
                    Time shiftEnd = Time.valueOf(shiftEndField.getText());
                    BigDecimal payRate = new BigDecimal(payRateField.getText().replace("₱ ", ""));
                    String tinNumber = tinNumberField.getText();
                    String pagibigNumber = pagibigNumberField.getText();
                    BigDecimal pagibigPercentage = new BigDecimal(pagibigPercentageField.getText());
                    String sssNumber = sssNumberField.getText();
                    BigDecimal sssPercentage = new BigDecimal(sssPercentageField.getText());
                    String philhealthNumber = philhealthNumberField.getText();
                    BigDecimal philhealthPercentage = new BigDecimal(philhealthPercentageField.getText());
                    BigDecimal efundAmount = new BigDecimal(efundAmountField.getText());
                    BigDecimal otherDeductions = new BigDecimal(otherDeductionsField.getText());
                    BigDecimal salaryAdjPercentage = new BigDecimal(salaryAdjPercentageField.getText());
                    BigDecimal allowanceAmount = new BigDecimal(allowanceAmountField.getText());
                    BigDecimal otherCompAmount = new BigDecimal(otherCompAmountField.getText());

                    // Update employee data in the database or data source
                    E201File.updateEmployeeData(employeeId, lastName, firstName, middleName, department,
                            employmentStatus, shiftStart, shiftEnd, payRate, tinNumber, pagibigNumber,
                            pagibigPercentage, sssNumber, sssPercentage, philhealthNumber, philhealthPercentage,
                            efundAmount, otherDeductions, salaryAdjPercentage, allowanceAmount, otherCompAmount);

                    hasUnsavedChanges = false;

                    // Reset fields to non-editable
                    lastNameField.setEditable(false);
                    firstNameField.setEditable(false);
                    middleNameField.setEditable(false);
                    departmentField.setEditable(false);
                    employmentStatusField.setEditable(false);
                    shiftStartField.setEditable(false);
                    shiftEndField.setEditable(false);
                    payRateField.setEditable(false);
                    tinNumberField.setEditable(false);
                    pagibigNumberField.setEditable(false);
                    pagibigPercentageField.setEditable(false);
                    sssNumberField.setEditable(false);
                    sssPercentageField.setEditable(false);
                    philhealthNumberField.setEditable(false);
                    philhealthPercentageField.setEditable(false);
                    efundAmountField.setEditable(false);
                    otherDeductionsField.setEditable(false);
                    salaryAdjPercentageField.setEditable(false);
                    allowanceAmountField.setEditable(false);
                    otherCompAmountField.setEditable(false);

                    setPlainTextLook.run();

                    saveButton.setVisible(false);
                    editButton.setVisible(true);
                    topButtonPanel.revalidate();
                    topButtonPanel.repaint();
                    saveButtonPanel.revalidate();
                    saveButtonPanel.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "An error occurred while saving the employee data. Please check the input values.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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

        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                if (table.rowAtPoint(p) >= 0) {
                    table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    table.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { searchUsingBinarySearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { searchUsingBinarySearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchUsingBinarySearch(); }

            private void searchUsingBinarySearch() {
                String searchText = searchField.getText().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                filteredToOriginalIndex.clear();

                if (searchText.isEmpty()) {
                    // Display all rows if search field is empty
                    for (int i = 0; i < tableViewData.length; i++) {
                        model.addRow(tableViewData[i]);
                        filteredToOriginalIndex.put(i, i);
                    }
                } else {
                    // Linear search for all matching entries
                    int filteredIndex = 0;
                    for (int i = 0; i < tableViewData.length; i++) {
                        Object[] row = tableViewData[i];
                        String name = row[0].toString().toLowerCase();
                        if (name.contains(searchText)) {
                            model.addRow(row);
                            filteredToOriginalIndex.put(filteredIndex, i);
                            filteredIndex++;
                        }
                    }
                }
            }
        });



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

        shiftStartLabel.setFont(detailsFont);
        shiftStartLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shiftStartField.setFont(detailsFont);

        shiftEndLabel.setFont(detailsFont);
        shiftEndLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shiftEndField.setFont(detailsFont);

        payRateLabel.setFont(detailsFont);
        payRateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        payRateField.setFont(detailsFont);

        tinNumberLabel.setFont(detailsFont);
        tinNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tinNumberField.setFont(detailsFont);

        pagibigNumberLabel.setFont(detailsFont);
        pagibigNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pagibigNumberField.setFont(detailsFont);

        pagibigPercentageLabel.setFont(detailsFont);
        pagibigPercentageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pagibigPercentageField.setFont(detailsFont);

        sssNumberLabel.setFont(detailsFont);
        sssNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sssNumberField.setFont(detailsFont);

        sssPercentageLabel.setFont(detailsFont);
        sssPercentageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sssPercentageField.setFont(detailsFont);

        philhealthNumberLabel.setFont(detailsFont);
        philhealthNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        philhealthNumberField.setFont(detailsFont);

        philhealthPercentageLabel.setFont(detailsFont);
        philhealthPercentageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        philhealthPercentageField.setFont(detailsFont);

        efundAmountLabel.setFont(detailsFont);
        efundAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        efundAmountField.setFont(detailsFont);

        otherDeductionsLabel.setFont(detailsFont);
        otherDeductionsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        otherDeductionsField.setFont(detailsFont);

        salaryAdjPercentageLabel.setFont(detailsFont);
        salaryAdjPercentageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        salaryAdjPercentageField.setFont(detailsFont);

        allowanceAmountLabel.setFont(detailsFont);
        allowanceAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        allowanceAmountField.setFont(detailsFont);

        otherCompAmountLabel.setFont(detailsFont);
        otherCompAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        otherCompAmountField.setFont(detailsFont);
    }

    private void setFieldsEditable(boolean editable) {
        lastNameField.setEditable(editable);
        firstNameField.setEditable(editable);
        middleNameField.setEditable(editable);
        departmentField.setEditable(editable);
        employmentStatusField.setEditable(editable);
        shiftStartField.setEditable(editable);
        shiftEndField.setEditable(editable);
        payRateField.setEditable(editable);
        tinNumberField.setEditable(editable);
        pagibigNumberField.setEditable(editable);
        pagibigPercentageField.setEditable(editable);
        sssNumberField.setEditable(editable);
        sssPercentageField.setEditable(editable);
        philhealthNumberField.setEditable(editable);
        philhealthPercentageField.setEditable(editable);
        efundAmountField.setEditable(editable);
        otherDeductionsField.setEditable(editable);
        salaryAdjPercentageField.setEditable(editable);
        allowanceAmountField.setEditable(editable);
        otherCompAmountField.setEditable(editable);
    }

    public void resetEditMode() {
        // Reset UI elements
        saveButton.setVisible(false);
        editButton.setVisible(true);

        // Make all fields non-editable
        setFieldsEditable(false);

        // Reset the look
        setPlainTextLook.run();
        hasUnsavedChanges = false;

        // Refresh the panels
        topButtonPanel.revalidate();
        topButtonPanel.repaint();
        saveButtonPanel.revalidate();
        saveButtonPanel.repaint();
    }



}

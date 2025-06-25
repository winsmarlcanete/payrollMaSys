package Screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.util.regex.Pattern;


import Components.TableStyler;
import Components.RoundedComboBox; // Assuming this class is available
import javax.swing.plaf.basic.BasicScrollBarUI; // Import for scrollbar styling
import javax.swing.plaf.basic.ComboPopup; // Import for ComboPopup
import javax.swing.border.EmptyBorder; // Import for EmptyBorder

import Module.E201File.E201File;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays; // Needed for Arrays.copyOfRange in filterTable
import java.time.LocalDate; // For date handling

public class Attendance extends JPanel {

    private CardLayout cardLayout; // Still useful if you have completely different top-level screens
    private JPanel mainContainer; // This will hold the main Attendance panel OR backup screen

    private static DefaultTableModel employeeTableModel;
    private static String[] tableViewHeaders = { "Name", "ID", "Department", "Employment Status" };
    private static Object[][] tableViewData;
    private static Object[][] detailsViewData; // Raw data used for populating detail view
    private JTable employeeListTable; // Renamed to clarify its purpose
    private TableRowSorter<DefaultTableModel> rowSorter;

    private JComboBox<String> departmentDropdown;
    private JTextField searchField;

    // --- Components for the Employee Detail View (within Attendance panel) ---
    private JPanel employeeListPanel; // Panel holding the employee list table
    private JPanel employeeDetailPanel; // Panel holding the detail view components

    // Detail View Components
    private JLabel detailNameLabel;
    private JLabel detailIdLabel;
    private JLabel detailDepartmentLabel;
    private JLabel detailEmploymentLabel;
    private JLabel detailShiftStartLabel;
    private JLabel detailShiftEndLabel;
    private JButton backButton; // Back button from detail view to list

    // Detail View Daily Attendance Table
    private DefaultTableModel dailyAttendanceTableModel;
    private JTable dailyAttendanceTable;
    private String[] dailyAttendanceHeaders = {"Date / Day", "Time Out", "Late (mins)", "Overtime (hours)", "Undertime (hours)", "Absent"};
    // Add date pickers or period labels if you implement date filtering for the daily table

    /**
     * Loads employee data from E201File and populates the table model.
     * This method is static so it can be called from outside the Attendance class
     * to refresh the employee table data globally.
     */
    public static void loadEmployeeTabledata() {
        // Query raw data from the external source
        Object[][] rawData = E201File.getEmployeeTableData();

        // Map data for table view
        tableViewData = new Object[rawData.length][tableViewHeaders.length];
        for (int i = 0; i < rawData.length; i++) {
            tableViewData[i][0] = rawData[i][1] + ", " + rawData[i][2]; // Combine last name and first name
            tableViewData[i][1] = rawData[i][0]; // ID
            tableViewData[i][2] = rawData[i][4]; // Department
            tableViewData[i][3] = rawData[i][5]; // Employment Status
        }
        // This is crucial: Set the model *before* any filtering is attempted on it.
        if (employeeTableModel != null) {
            employeeTableModel.setDataVector(tableViewData, tableViewHeaders);
        }
        detailsViewData = rawData; // Store raw data directly for details view (if needed later)
    }

    /**
     * Constructor for the Attendance panel.
     * Sets up the UI components, layout, and event listeners.
     */
    public Attendance() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout); // This mainContainer will now hold two primary cards: "main" (Attendance content) and "backup"

        // Set a preferred size for the mainContainer to make the Attendance panel "larger"
        // These dimensions are estimated to make it visibly larger, similar to a common app window.
        // You might need to adjust these values based on your desired final window size.
        mainContainer.setPreferredSize(new Dimension(1200, 800)); // Example large size


        // --- Master Attendance Panel (Holds both list and detail views) ---
        // This panel will switch between the employee list and the detailed view internally.
        // Initially, it will show the employee list.
        JPanel attendanceContentPanel = new JPanel(new CardLayout()); // Internal card layout for list vs detail
        attendanceContentPanel.setBackground(new Color(34, 139, 34)); // Original dark green background

        // --- 1. Employee List Panel (Original table view) ---
        employeeListPanel = new JPanel(new BorderLayout());
        employeeListPanel.setBackground(new Color(34, 139, 34)); // Original dark green background

        // --- Header Area (Container for top and bottom bars) for List View ---
        JPanel headerArea = new JPanel();
        headerArea.setLayout(new BoxLayout(headerArea, BoxLayout.Y_AXIS));
        headerArea.setBackground(new Color(34, 139, 34));

        // Top Bar: Search, Sort By Department
        JPanel topBar = new JPanel(new GridBagLayout());
        topBar.setBackground(new Color(34, 139, 34));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Search Bar Section
        JPanel searchMainPanel = new JPanel(new BorderLayout(10, 0));
        searchMainPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchField.setBackground(Color.WHITE);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setPreferredSize(new Dimension(150, 10));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setBorder(null);
        searchButton.setFocusable(false);

        searchMainPanel.add(searchButton, BorderLayout.WEST);
        searchMainPanel.add(searchField, BorderLayout.CENTER);
        searchMainPanel.setPreferredSize(new Dimension(searchMainPanel.getPreferredSize().width, 45));
        searchMainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 10);
        topBar.add(searchMainPanel, gbc);

        // Sort By Department Section
        JPanel sortFilterContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        sortFilterContainerPanel.setOpaque(false);

        JLabel sortLabel = new JLabel("Sort by Department: ");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setForeground(Color.BLACK);
        sortLabel.setBackground(Color.WHITE);
        sortLabel.setOpaque(true);
        sortLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 16, 15));
        sortFilterContainerPanel.add(sortLabel);

        departmentDropdown = new RoundedComboBox<>(new String[] {
                "All Departments",
                "Human Resource", "Administration", "Accounting", "Sales",
                "Production", "Production (Pre-Press)", "Production (Press)", "Production (Post-Press)", "Production (Quality Control)"
        }) {
            @Override
            protected void paintBorder(Graphics g) { /* Do nothing */ }
        };
        departmentDropdown.setFont(new Font("Arial", Font.BOLD, 16));
        departmentDropdown.setPreferredSize(new Dimension(220, 50));
        departmentDropdown.setBackground(Color.WHITE);
        departmentDropdown.setFocusable(false);
        departmentDropdown.setMaximumRowCount(12);
        ((JLabel)departmentDropdown.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Custom renderer for hover effect (already present)
        departmentDropdown.setRenderer(new DefaultListCellRenderer() {
            private int hoveredIndex = -1;
            {
                departmentDropdown.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
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
                        ComboPopup popup = (ComboPopup) departmentDropdown.getUI().getAccessibleChild(departmentDropdown, 0);
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

        // Add hover effect (already present)
        Color comboDefaultBg = Color.WHITE;
        Color comboHoverBg = new Color(230, 255, 230);
        departmentDropdown.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (departmentDropdown.isEnabled()) {
                    departmentDropdown.setBackground(comboHoverBg);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                departmentDropdown.setBackground(comboDefaultBg);
            }
        });
        sortFilterContainerPanel.add(departmentDropdown);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        topBar.add(sortFilterContainerPanel, gbc);

        headerArea.add(topBar);

        // Bottom Bar: Logo and Attendance Backup Button
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(new Color(34, 139, 34));
        bottomBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel logoArea = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoArea.setOpaque(false);

        int targetHeight = 30;
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));
        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (int) ((double) origWidth / origHeight * targetHeight);
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        JLabel logoAndTextLabel = new JLabel(new ImageIcon(scaledLogo));

        JPanel logoWrapper = new JPanel(new BorderLayout());
        logoWrapper.setBackground(Color.WHITE);
        logoWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        logoWrapper.add(logoAndTextLabel, BorderLayout.CENTER);
        logoArea.add(logoWrapper);
        bottomBar.add(logoArea, BorderLayout.WEST);

        JButton attendanceBackupButton = new JButton("Attendance Backup");
        attendanceBackupButton.setPreferredSize(new Dimension(225, 50));
        attendanceBackupButton.setBackground(Color.WHITE);
        attendanceBackupButton.setForeground(Color.BLACK);
        attendanceBackupButton.setFont(new Font("Arial", Font.BOLD, 16));
        attendanceBackupButton.setFocusPainted(false);
        attendanceBackupButton.setBorder(BorderFactory.createEmptyBorder());
        attendanceBackupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        attendanceBackupButton.addActionListener(e -> {
            mainContainer.add(new AttendanceBackup(cardLayout, mainContainer), "backup");
            cardLayout.show(mainContainer, "backup");
        });
        JPanel backupButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        backupButtonPanel.setOpaque(false);
        backupButtonPanel.add(attendanceBackupButton);
        bottomBar.add(backupButtonPanel, BorderLayout.EAST);

        headerArea.add(bottomBar);
        employeeListPanel.add(headerArea, BorderLayout.NORTH);

        // Employee List Table Setup
        employeeTableModel = new DefaultTableModel(tableViewData, tableViewHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeListTable = new JTable(employeeTableModel); // Use the renamed table variable
        employeeListTable.getTableHeader().setReorderingAllowed(false);
        TableStyler.styleTable(employeeListTable);

        rowSorter = new TableRowSorter<>(employeeTableModel);
        employeeListTable.setRowSorter(rowSorter);

        // Listeners for the employee LIST table (search, sort, hover)
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            public void changedUpdate(DocumentEvent e) { filterTable(); }

            private void filterTable() {
                String text = searchField.getText().trim();
                rowSorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + Pattern.quote(text), 0));
            }
        });

        departmentDropdown.addActionListener(e -> {
            String selected = (String) departmentDropdown.getSelectedItem();
            if (selected != null && selected.equals("All Departments")) {
                rowSorter.setRowFilter(null);
            } else if (selected != null) {
                rowSorter.setRowFilter(RowFilter.regexFilter("^" + Pattern.quote(selected) + "$", 2));
            }
        });

        employeeListTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = employeeListTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    employeeListTable.setRowSelectionInterval(row, row);
                } else {
                    employeeListTable.clearSelection();
                }
            }
        });

        // IMPORTANT: Modify this listener to switch to the detail view WITHIN Attendance
        employeeListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = employeeListTable.getSelectedRow();
                if (row != -1) {
                    searchField.setText(""); // Clear search field
                    int modelRow = employeeListTable.convertRowIndexToModel(row);
                    // Get the raw employee data for the selected row from detailsViewData
                    // The 'ID' column in tableViewData (index 1) corresponds to rawData[i][0]
                    String employeeId = (String) employeeTableModel.getValueAt(modelRow, 1);
                    Object[] selectedRawData = null;
                    for (Object[] rawEmployee : detailsViewData) {
                        if (rawEmployee[0].equals(employeeId)) {
                            selectedRawData = rawEmployee;
                            break;
                        }
                    }
                    if (selectedRawData != null) {
                        populateEmployeeDetailPanel(selectedRawData);
                        ((CardLayout)attendanceContentPanel.getLayout()).show(attendanceContentPanel, "detail");
                    }
                }
            }
        });

        JScrollPane employeeListScrollPane = new JScrollPane(employeeListTable);
        employeeListScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Apply Green Scroll Bar Styling (reused)
        applyGreenScrollbarUI(employeeListScrollPane);

        employeeListPanel.add(employeeListScrollPane, BorderLayout.CENTER);
        attendanceContentPanel.add(employeeListPanel, "list"); // Add list panel to internal card layout

        // --- 2. Employee Detail Panel ---
        employeeDetailPanel = new JPanel(new BorderLayout());
        employeeDetailPanel.setBackground(new Color(34, 139, 34)); // Match background

        // Top Back Button for Detail View
        JPanel detailHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detailHeader.setOpaque(false); // Transparent
        detailHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0)); // Padding

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.BLACK); // Black button as in image
        backButton.setForeground(Color.WHITE); // White text
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            ((CardLayout)attendanceContentPanel.getLayout()).show(attendanceContentPanel, "list"); // Go back to list
            employeeListTable.clearSelection(); // Clear selection when returning to list
        });
        detailHeader.add(backButton);
        employeeDetailPanel.add(detailHeader, BorderLayout.NORTH);


        // Employee Details Section (Name, ID, Department, etc.)
        JPanel employeeInfoPanel = new JPanel(new GridBagLayout());
        employeeInfoPanel.setOpaque(false);
        employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Padding

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.fill = GridBagConstraints.HORIZONTAL;
        gbcInfo.insets = new Insets(5, 5, 5, 5); // Spacing between components

        // Helper to create styled labels
        private JLabel createStyledLabel(String text, Font font, Color background, Color foreground, Insets padding) {
            JLabel label = new JLabel(text);
            label.setFont(font);
            label.setBackground(background);
            label.setForeground(foreground);
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(padding));
            label.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            label.setPreferredSize(new Dimension(label.getPreferredSize().width, 50)); // Consistent height
            return label;
        }

        // --- Name ---
        JLabel nameLabel = createStyledLabel("Name", new Font("Arial", Font.BOLD, 16), new Color(20, 80, 20), Color.WHITE, new Insets(0,15,0,15)); // Darker green
        gbcInfo.gridx = 0; gbcInfo.gridy = 0; gbcInfo.weightx = 0; gbcInfo.anchor = GridBagConstraints.WEST;
        employeeInfoPanel.add(nameLabel, gbcInfo);

        detailNameLabel = createStyledLabel("", new Font("Arial", Font.PLAIN, 16), Color.WHITE, Color.BLACK, new Insets(0,15,0,15));
        gbcInfo.gridx = 1; gbcInfo.gridy = 0; gbcInfo.weightx = 1; // Expands
        employeeInfoPanel.add(detailNameLabel, gbcInfo);

// --- ID ---
        JLabel idLabel = createStyledLabel("ID", new Font("Arial", Font.BOLD, 16), new Color(20, 80, 20), Color.WHITE, new Insets(0,15,0,15));
        gbcInfo.gridx = 2; gbcInfo.gridy = 0; gbcInfo.weightx = 0;
        employeeInfoPanel.add(idLabel, gbcInfo);

        detailIdLabel = createStyledLabel("", new Font("Arial", Font.PLAIN, 16), Color.WHITE, Color.BLACK, new Insets(0,15,0,15));
        gbcInfo.gridx = 3; gbcInfo.gridy = 0; gbcInfo.weightx = 1;
        employeeInfoPanel.add(detailIdLabel, gbcInfo);

        // --- Department ---
        JLabel departmentLabel = createStyledLabel("Department", new Font("Arial", Font.BOLD, 16), new Color(20, 80, 20), Color.WHITE, new Insets(0,15,0,15));
        gbcInfo.gridx = 4; gbcInfo.gridy = 0; gbcInfo.weightx = 0;
        employeeInfoPanel.add(departmentLabel, gbcInfo);

        detailDepartmentLabel = createStyledLabel("", new Font("Arial", Font.PLAIN, 16), Color.WHITE, Color.BLACK, new Insets(0,15,0,15));
        gbcInfo.gridx = 5; gbcInfo.gridy = 0; gbcInfo.weightx = 1;
        employeeInfoPanel.add(detailDepartmentLabel, gbcInfo);

        // --- Employment Status ---
        JLabel employmentLabel = createStyledLabel("Employment", new Font("Arial", Font.BOLD, 16), new Color(20, 80, 20), Color.WHITE, new Insets(0,15,0,15));
        gbcInfo.gridx = 6; gbcInfo.gridy = 0; gbcInfo.weightx = 0;
        employeeInfoPanel.add(employmentLabel, gbcInfo);

        detailEmploymentLabel = createStyledLabel("", new Font("Arial", Font.PLAIN, 16), Color.WHITE, Color.BLACK, new Insets(0,15,0,15));
        gbcInfo.gridx = 7; gbcInfo.gridy = 0; gbcInfo.weightx = 1;
        employeeInfoPanel.add(detailEmploymentLabel, gbcInfo);

        // --- Shift Start ---
        JLabel shiftStartLabel = createStyledLabel("Shift start", new Font("Arial", Font.BOLD, 16), new Color(20, 80, 20), Color.WHITE, new Insets(0,15,0,15));
        gbcInfo.gridx = 8; gbcInfo.gridy = 0; gbcInfo.weightx = 0;
        employeeInfoPanel.add(shiftStartLabel, gbcInfo);

        detailShiftStartLabel = createStyledLabel("", new Font("Arial", Font.PLAIN, 16), Color.WHITE, Color.BLACK, new Insets(0,15,0,15));
        gbcInfo.gridx = 9; gbcInfo.gridy = 0; gbcInfo.weightx = 1;
        employeeInfoPanel.add(detailShiftStartLabel, gbcInfo);

        // --- Shift End ---
        JLabel shiftEndLabel = createStyledLabel("Shift end", new Font("Arial", Font.BOLD, 16), new Color(20, 80, 20), Color.WHITE, new Insets(0,15,0,15));
        gbcInfo.gridx = 10; gbcInfo.gridy = 0; gbcInfo.weightx = 0;
        employeeInfoPanel.add(shiftEndLabel, gbcInfo);

        detailShiftEndLabel = createStyledLabel("", new Font("Arial", Font.PLAIN, 16), Color.WHITE, Color.BLACK, new Insets(0,15,0,15));
        gbcInfo.gridx = 11; gbcInfo.gridy = 0; gbcInfo.weightx = 1;
        employeeInfoPanel.add(detailShiftEndLabel, gbcInfo);


        employeeDetailPanel.add(employeeInfoPanel, BorderLayout.CENTER); // Add info panel to center

        // Period and Daily Attendance Table
        JPanel dailyAttendanceSection = new JPanel(new BorderLayout());
        dailyAttendanceSection.setOpaque(false);
        dailyAttendanceSection.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // Padding

        // Period display (simple labels for now, add date pickers if needed)
        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        periodPanel.setOpaque(false);
        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(new Font("Arial", Font.BOLD, 20));
        periodLabel.setForeground(Color.WHITE); // White text for "Period:"
        periodPanel.add(periodLabel);

        JLabel startDateLabel = createStyledLabel("Oct 21, 2024", new Font("Arial", Font.BOLD, 16), new Color(150, 150, 150), Color.WHITE, new Insets(10,20,10,20)); // Grey background
        periodPanel.add(startDateLabel);
        JLabel dashLabel = createStyledLabel("-", new Font("Arial", Font.BOLD, 16), new Color(34, 139, 34), Color.WHITE, new Insets(10,0,10,0)); // Green background
        periodPanel.add(dashLabel);
        JLabel endDateLabel = createStyledLabel("Nov 5, 2024", new Font("Arial", Font.BOLD, 16), new Color(150, 150, 150), Color.WHITE, new Insets(10,20,10,20)); // Grey background
        periodPanel.add(endDateLabel);

        dailyAttendanceSection.add(periodPanel, BorderLayout.NORTH);


        // Daily Attendance Table
        dailyAttendanceTableModel = new DefaultTableModel(new Object[][]{}, dailyAttendanceHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dailyAttendanceTable = new JTable(dailyAttendanceTableModel);
        TableStyler.styleTable(dailyAttendanceTable); // Apply table styling
        dailyAttendanceTable.getTableHeader().setReorderingAllowed(false);

        // Make the daily attendance table background green behind cells, and headers
        // This requires custom renderer or direct manipulation of table properties if default TableStyler doesn't handle it
        dailyAttendanceTable.setOpaque(false); // Make table transparent
        dailyAttendanceTable.setBackground(new Color(34, 139, 34));
        ((DefaultTableCellRenderer)dailyAttendanceTable.getDefaultRenderer(Object.class)).setOpaque(false);

        JScrollPane dailyAttendanceScrollPane = new JScrollPane(dailyAttendanceTable);
        dailyAttendanceScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Padding below period, no left/right
        dailyAttendanceScrollPane.setOpaque(false);
        dailyAttendanceScrollPane.getViewport().setOpaque(false); // Make viewport transparent

        // Apply Green Scroll Bar Styling to daily attendance table
        applyGreenScrollbarUI(dailyAttendanceScrollPane);

        dailyAttendanceSection.add(dailyAttendanceScrollPane, BorderLayout.CENTER);

        employeeDetailPanel.add(dailyAttendanceSection, BorderLayout.SOUTH); // Add daily attendance section to south

        attendanceContentPanel.add(employeeDetailPanel, "detail"); // Add detail panel to internal card layout

        // Add the attendanceContentPanel (which holds both list and detail) to the mainContainer
        mainContainer.add(attendanceContentPanel, "main");
        add(mainContainer, BorderLayout.CENTER); // Add mainContainer to the Attendance panel

        // Load data into the table after all components are set up
        loadEmployeeTabledata();
    }

    // Helper method to apply scrollbar styling
    private void applyGreenScrollbarUI(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 139, 34);
                this.trackColor = new Color(100, 180, 100);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                button.setVisible(false);
                return button;
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(trackColor);
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });
        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 139, 34);
                this.trackColor = new Color(100, 180, 100);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                button.setVisible(false);
                return button;
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(trackColor);
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });
    }


    /**
     * Populates the detail panel with data from the selected employee.
     * @param rawData The raw data row for the selected employee.
     */
    private void populateEmployeeDetailPanel(Object[] rawData) {
        // rawData: [ID, LastName, FirstName, MiddleName, Department, EmploymentStatus, ShiftStart, ShiftEnd, DailyAttendanceData...]
        // Assuming rawData structure as per loadEmployeeTabledata comments:
        // rawData[0] = ID
        // rawData[1] = LastName
        // rawData[2] = FirstName
        // rawData[3] = MiddleName (Optional)
        // rawData[4] = Department
        // rawData[5] = EmploymentStatus
        // rawData[6] = ShiftStart
        // rawData[7] = ShiftEnd

        detailNameLabel.setText(rawData[1] + ", " + rawData[2]); // LastName, FirstName
        detailIdLabel.setText(rawData[0].toString()); // ID
        detailDepartmentLabel.setText(rawData[4].toString()); // Department
        detailEmploymentLabel.setText(rawData[5].toString()); // Employment Status
        detailShiftStartLabel.setText(rawData[6].toString()); // Shift Start
        detailShiftEndLabel.setText(rawData[7].toString()); // Shift End

        // TODO: Populate dailyAttendanceTableModel with actual daily attendance data
        // This will likely involve another method call to E201File to get specific daily attendance
        // for the selected employee and possibly a date range.
        // For demonstration, let's just clear and add some dummy data.
        dailyAttendanceTableModel.setRowCount(0); // Clear existing data

        // Dummy data for daily attendance, replace with actual data retrieval
        // This should come from your rawData, or another source related to the employee ID
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 21 Monday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 22 Tuesday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 23 Wednesday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 24 Thursday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 25 Friday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 28 Monday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 29 Tuesday", "17:02", 0.083, 0.033, 0, 0});
        dailyAttendanceTableModel.addRow(new Object[]{"Oct 30 Wednesday", "17:02", 0.083, 0.033, 0, 0});
    }
}

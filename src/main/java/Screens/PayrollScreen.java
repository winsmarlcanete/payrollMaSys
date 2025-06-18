package Screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


import java.util.List;

import Components.TableStyler;
import Components.RoundedComboBox;
import Entity.Employee;
import Module.Payroll.Payroll;

public class PayrollScreen extends JPanel {
    private JTextField searchField;

    private Object[][] convertToTableData(List<Employee> employees) {
        Object[][] data = new Object[employees.size()][13]; // 13 fields based on your getters

        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);

            data[i][0] = emp.getEmployee_id();
            data[i][1] = emp.getLast_name();
            data[i][2] = emp.getFirst_name();
            data[i][3] = emp.getMiddle_name();
            data[i][4] = emp.getTin_number();
            data[i][5] = emp.getPhilhealth_number();
            data[i][6] = emp.getPagibig_number();
            data[i][7] = emp.getSss_number();
            data[i][8] = emp.getPay_rate();
            data[i][9] = emp.getEmployment_status();
            data[i][10] = emp.getDepartment();
            data[i][11] = emp.getShift_start();
            data[i][12] = emp.getShift_end();
        }

        return data;
    }

    public PayrollScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 177, 76)); // green

        // --- Search bar (same as Employees.java) ---
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(180, 36));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Add left/right padding
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // --- Top group: logo (left), dropdown + button (right) ---
        JPanel topGroupPanel = new JPanel();
        topGroupPanel.setOpaque(false);
        topGroupPanel.setLayout(new BorderLayout());
        topGroupPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Logo (leftmost)
        int targetHeight = 30;
        // Load and scale the logo image
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));
        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (int) ((double) origWidth / origHeight * targetHeight);
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        // Create a rounded JLabel for the logo
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setPreferredSize(new Dimension(targetWidth + 40, targetHeight)); // Add 20px left/right padding
        logoLabel.setOpaque(true);

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
        sortCombo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // right padding 10
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
        JLabel sortLabel = new JLabel("Sort By Department: ");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
        comboPanel.add(sortLabel, gbcCombo);

        gbcCombo.gridx = 1;
        comboPanel.add(sortCombo, gbcCombo);
        comboPanel.setOpaque(true);
        comboPanel.setBackground(Color.WHITE);

        // Button
        JButton createPeriodBtn = new JButton("Create New Payroll Period");
        createPeriodBtn.setFont(new Font("Arial", Font.BOLD, 16));
        createPeriodBtn.setPreferredSize(new Dimension(250, 36));
        createPeriodBtn.setBackground(Color.WHITE);
        createPeriodBtn.setFocusable(false);
        createPeriodBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Hover and click effect
        Color btnDefaultBg = Color.WHITE;
        Color btnHoverBg = new Color(230, 255, 230); // light greenish
        Color btnPressedBg = new Color(200, 240, 200); // slightly darker green

        createPeriodBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            if (createPeriodBtn.isEnabled()) {
                createPeriodBtn.setBackground(btnHoverBg);
            }
            }
            @Override
            public void mouseExited(MouseEvent e) {
            createPeriodBtn.setBackground(btnDefaultBg);
            }
            @Override
            public void mousePressed(MouseEvent e) {
            if (createPeriodBtn.isEnabled()) {
                createPeriodBtn.setBackground(btnPressedBg);
            }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            if (createPeriodBtn.isEnabled() && createPeriodBtn.getBounds().contains(e.getPoint())) {
                createPeriodBtn.setBackground(btnHoverBg);
            } else {
                createPeriodBtn.setBackground(btnDefaultBg);
            }
            }
        });

        // Use GridBagLayout for vertical centering
        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding   
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 0;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        // Make the button fill vertical space
        gbcBtn.fill = GridBagConstraints.VERTICAL;
        gbcBtn.weighty = 1.0;
        createPeriodBtn.setPreferredSize(new Dimension(220, 36)); // You may adjust width if needed
        btnPanel.add(createPeriodBtn, gbcBtn);

        // Right panel (dropdown + button)
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        rightPanel.add(comboPanel, gbc);

        gbc.gridx = 1;
        rightPanel.add(btnPanel, gbc);

        // Make rightPanel match the height of topGroupPanel
        int topGroupHeight = Math.max(targetHeight, Math.max(sortCombo.getPreferredSize().height, createPeriodBtn.getPreferredSize().height)) + 8;
        comboPanel.setPreferredSize(new Dimension(comboPanel.getPreferredSize().width, topGroupHeight));
        comboPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, topGroupHeight));
        comboPanel.setMinimumSize(new Dimension(0, topGroupHeight));
        btnPanel.setPreferredSize(new Dimension(btnPanel.getPreferredSize().width, topGroupHeight));
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, topGroupHeight));
        btnPanel.setMinimumSize(new Dimension(0, topGroupHeight));
        rightPanel.setPreferredSize(new Dimension(rightPanel.getPreferredSize().width, topGroupHeight));
        rightPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, topGroupHeight));
        rightPanel.setMinimumSize(new Dimension(0, topGroupHeight));

        // Add logo to the left, rightPanel to the right
        topGroupPanel.add(logoLabel, BorderLayout.WEST);
        topGroupPanel.add(rightPanel, BorderLayout.EAST);

        // Set minimum and maximum height to preferred height to prevent stretching
        int prefHeight = Math.max(targetHeight, Math.max(sortCombo.getPreferredSize().height, createPeriodBtn.getPreferredSize().height));
        topGroupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefHeight + 8));
        topGroupPanel.setPreferredSize(new Dimension(topGroupPanel.getPreferredSize().width, prefHeight + 8));

        // --- Period info ---
        JPanel periodPanel = new JPanel();
        periodPanel.setOpaque(false);
        periodPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        periodPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        // Set periodPanel height to fit its components
        periodPanel.setPreferredSize(null);
        periodPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, periodPanel.getPreferredSize().height));

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(new Font("Arial", Font.BOLD, 22));
        periodLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Load calendar icon from resources
        ImageIcon calendarIcon = new ImageIcon(
            new ImageIcon(getClass().getClassLoader().getResource("Calendar.png"))
            .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        // Replace JTextField with JButton for date selection
        JButton fromDateBtn = new JButton("Oct 21, 2024");
        JButton toDateBtn = new JButton("Nov 5,2024"); // Declare toDateBtn before using it
        fromDateBtn.setFont(new Font("Arial", Font.BOLD, 18));
        fromDateBtn.setBackground(Color.BLACK);
        fromDateBtn.setForeground(Color.WHITE);
        fromDateBtn.setFocusPainted(false);
        fromDateBtn.setIcon(calendarIcon);
        fromDateBtn.setHorizontalTextPosition(SwingConstants.LEFT); // Text left, icon right
        fromDateBtn.setIconTextGap(12); // Space between text and icon

        // Add hover effect and hand cursor
        fromDateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fromDateBtn.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        fromDateBtn.addMouseListener(new MouseAdapter() {
            Color normalBg = Color.BLACK;
            Color hoverBg = new Color(34, 177, 76);
            @Override
            public void mouseEntered(MouseEvent e) {
                fromDateBtn.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                fromDateBtn.setBackground(normalBg);
            }
        });

        toDateBtn.setFont(new Font("Arial", Font.BOLD, 18));
        toDateBtn.setBackground(Color.BLACK);
        toDateBtn.setForeground(Color.WHITE);
        toDateBtn.setFocusPainted(false);
        toDateBtn.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        toDateBtn.setIcon(calendarIcon);
        toDateBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        toDateBtn.setIconTextGap(12);
        
        // Add hover effect and hand cursor
        toDateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toDateBtn.addMouseListener(new MouseAdapter() {
            Color normalBg = Color.BLACK;
            Color hoverBg = new Color(34, 177, 76);
            @Override
            public void mouseEntered(MouseEvent e) {
                toDateBtn.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                toDateBtn.setBackground(normalBg);
            }
        });

        final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy");
        final java.util.Date[] fromDate = {null};
        final java.util.Date[] toDate = {null};

        // Show date picker dialog on click
        fromDateBtn.addActionListener(e -> {
            org.jdatepicker.impl.UtilDateModel model = new org.jdatepicker.impl.UtilDateModel();
            java.util.Properties p = new java.util.Properties();
            org.jdatepicker.impl.JDatePanelImpl datePanel = new org.jdatepicker.impl.JDatePanelImpl(model, p);
            org.jdatepicker.impl.JDatePickerImpl picker = new org.jdatepicker.impl.JDatePickerImpl(datePanel, new org.jdatepicker.impl.DateComponentFormatter());

            // Set max selectable date to today using Calendar
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int year = cal.get(java.util.Calendar.YEAR);
            int month = cal.get(java.util.Calendar.MONTH);
            int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
            datePanel.getModel().setSelected(true);
            datePanel.getModel().setDate(year, month, day);
            datePanel.getModel().setSelected(false);

            java.util.Date today = cal.getTime();

            int result = JOptionPane.showConfirmDialog(null, picker, "Select Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                java.util.Date selectedDate = (java.util.Date) picker.getModel().getValue();
                if (selectedDate != null) {
                    if (selectedDate.after(today)) {
                        JOptionPane.showMessageDialog(null, "Start date cannot be in the future.", "Invalid Date", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    fromDate[0] = selectedDate;
                    fromDateBtn.setText(sdf.format(selectedDate));
                    // If toDate is before new fromDate, reset toDate
                    if (toDate[0] != null && toDate[0].before(fromDate[0])) {
                        toDate[0] = null;
                        toDateBtn.setText("Select End Date");
                    }
                }
            }
        });

        JLabel dashLabel = new JLabel(" - ");
        dashLabel.setFont(new Font("Arial", Font.BOLD, 22));
        dashLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Repeat for toDate
        toDateBtn.addActionListener(e -> {
            org.jdatepicker.impl.UtilDateModel model = new org.jdatepicker.impl.UtilDateModel();
            java.util.Properties p = new java.util.Properties();
            org.jdatepicker.impl.JDatePanelImpl datePanel = new org.jdatepicker.impl.JDatePanelImpl(model, p);
            org.jdatepicker.impl.JDatePickerImpl picker = new org.jdatepicker.impl.JDatePickerImpl(datePanel, new org.jdatepicker.impl.DateComponentFormatter());

            java.util.Date today = new java.util.Date();
            java.util.Date minDate = fromDate[0];
            if (minDate == null) {
            JOptionPane.showMessageDialog(null, "Please select a start date first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            // Set picker to minDate by default
            model.setValue(minDate);

            int result = JOptionPane.showConfirmDialog(null, picker, "Select Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
            java.util.Date selectedDate = (java.util.Date) picker.getModel().getValue();
            if (selectedDate != null) {
                if (selectedDate.before(minDate)) {
                JOptionPane.showMessageDialog(null, "End date cannot be before start date.", "Invalid Date", JOptionPane.WARNING_MESSAGE);
                return;
                }
                if (selectedDate.after(today)) {
                JOptionPane.showMessageDialog(null, "End date cannot be in the future.", "Invalid Date", JOptionPane.WARNING_MESSAGE);
                return;
                }
                toDate[0] = selectedDate;
                toDateBtn.setText(sdf.format(selectedDate));
            }
            }
        });

        JLabel adminLabel = new JLabel((String) sortCombo.getSelectedItem());
        // Update adminLabel text when sortCombo selection changes
        sortCombo.addActionListener(e -> adminLabel.setText((String) sortCombo.getSelectedItem()));
        adminLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminLabel.setOpaque(true);
        adminLabel.setBackground(Color.BLACK);
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));

        periodPanel.add(periodLabel);
        periodPanel.add(fromDateBtn);
        periodPanel.add(dashLabel);
        periodPanel.add(toDateBtn);
        periodPanel.add(Box.createHorizontalStrut(10));
        periodPanel.add(adminLabel);



        Payroll payrollLogic = new Payroll();
        List<Employee> employees = payrollLogic.retrieveAllEmployee();
        Object[][] data = convertToTableData(employees);

        Object[][] dest = new Object[data.length][];

        for (int i = 0; i < data.length; i++) {
            dest[i] = new Object[data[i].length]; // allocate each row
            System.arraycopy(data[i], 0, dest[i], 0, data[i].length);
        }


        // --- Table data ---
        String[] columnNames = {
            "Last Name", "First Name", "Rate", "Rate Per Hour", "Days Present", "OT in Hours",
            "Pag-Ibig", "New Deduction", "Total Deduction", "Compensation", "Total Compensation", "Net Pay"
        };


        // --- Frozen columns setup ---
        String[] frozenColumns = Arrays.copyOfRange(columnNames, 0, 2);
        String[] scrollColumns = Arrays.copyOfRange(columnNames, 2, columnNames.length);

        Object[][] frozenData = new Object[data.length][2];
        Object[][] scrollData = new Object[data.length][columnNames.length - 2];
        for (int i = 0; i < data.length; i++) {
            frozenData[i][0] = data[i][0];
            frozenData[i][1] = data[i][1];
            System.arraycopy(data[i], 2, scrollData[i], 0, columnNames.length - 2);
        }

        DefaultTableModel frozenModel = new DefaultTableModel(frozenData, frozenColumns) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        DefaultTableModel scrollModel = new DefaultTableModel(scrollData, scrollColumns) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable frozenTable = new JTable(frozenModel);
        JTable scrollTable = new JTable(scrollModel);
        scrollTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableStyler.styleTable(frozenTable);
        TableStyler.styleTable(scrollTable);

        // Synchronize row selection
        ListSelectionModel selectionModel = frozenTable.getSelectionModel();
        scrollTable.setSelectionModel(selectionModel);

        // Highlight row on mouse hover (like Employees.java)
        frozenTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = frozenTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    frozenTable.setRowSelectionInterval(row, row);
                } else {
                    frozenTable.clearSelection();
                }
            }
        });
        scrollTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = scrollTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    scrollTable.setRowSelectionInterval(row, row);
                } else {
                    scrollTable.clearSelection();
                }
            }
        });

        // Set table header background to green and foreground to white
        JTableHeader frozenHeader = frozenTable.getTableHeader();
        frozenHeader.setBackground(new Color(0, 128, 0));
        frozenHeader.setForeground(Color.WHITE);
        JTableHeader scrollHeader = scrollTable.getTableHeader();
        scrollHeader.setBackground(new Color(0, 128, 0));
        scrollHeader.setForeground(Color.WHITE);

        // Scroll panes for both tables
        JScrollPane frozenScroll = new JScrollPane(frozenTable);
        frozenScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        frozenScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frozenScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 17, 0));
        JScrollPane scrollScroll = new JScrollPane(scrollTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollScroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));

        // Custom scrollbars
        frozenScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 177, 76);
                this.trackColor = new Color(220, 255, 220);
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 177, 76));
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(220, 255, 220));
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });
        scrollScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 177, 76);
                this.trackColor = new Color(220, 255, 220);
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 177, 76));
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(220, 255, 220));
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });
        scrollScroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
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
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 177, 76);
                this.trackColor = new Color(220, 255, 220);
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 177, 76));
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(220, 255, 220));
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });

        // Synchronize vertical scrolling
        frozenScroll.getVerticalScrollBar().setModel(scrollScroll.getVerticalScrollBar().getModel());

        // Place both tables in a panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.add(frozenScroll);
        tablePanel.add(scrollScroll);

        // Place both headers in a panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.add(frozenHeader);
        headerPanel.add(scrollHeader);

        // --- Layout ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(topGroupPanel);

        // Group periodPanel and tablePanel into a white background panel
        JPanel whitePanel = new JPanel();
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setOpaque(true);
        whitePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        whitePanel.add(periodPanel);
        whitePanel.add(Box.createVerticalStrut(10));
        whitePanel.add(headerPanel);
        whitePanel.add(tablePanel);

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(whitePanel);

        add(searchPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // --- Responsive column resizing ---
        // Helper method for column resizing
        Runnable adjustColumnWidths = () -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window == null) return;

            // Helper method to calculate the ideal width of a column based on its content
            java.util.function.BiFunction<JTable, Integer, Integer> getPreferredWidth = (table, colIndex) -> {
                int maxWidth = 50; // A minimum width
                TableCellRenderer headerRenderer = table.getColumnModel().getColumn(colIndex).getHeaderRenderer();
                if (headerRenderer == null) {
                    headerRenderer = table.getTableHeader().getDefaultRenderer();
                }
                Component headerComp = headerRenderer.getTableCellRendererComponent(table, table.getColumnName(colIndex), false, false, 0, colIndex);
                maxWidth = Math.max(headerComp.getPreferredSize().width, maxWidth);

                for (int row = 0; row < table.getRowCount(); row++) {
                    TableCellRenderer cellRenderer = table.getCellRenderer(row, colIndex);
                    Component cellComp = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(row, colIndex), false, false, row, colIndex);
                    maxWidth = Math.max(cellComp.getPreferredSize().width, maxWidth);
                }
                return maxWidth + 20; // Add 20px for padding
            };

            // --- Step 1: Calculate and set the ideal width for the frozen columns AND their container ---
            int frozenCols = frozenTable.getColumnCount();
            int totalFrozenNaturalWidth = 0;
            for (int col = 0; col < frozenCols; col++) {
                int naturalWidth = getPreferredWidth.apply(frozenTable, col);
                frozenTable.getColumnModel().getColumn(col).setPreferredWidth(naturalWidth);
                totalFrozenNaturalWidth += naturalWidth;
            }

            // *** CORRECTED CODE TO FIX THE CONTAINER HEIGHT ***
            // Set the preferred width.
            frozenScroll.setPreferredSize(new Dimension(totalFrozenNaturalWidth, 0));
            // Crucially, only constrain the MAXIMUM WIDTH. Allow the height to be flexible (Integer.MAX_VALUE).
            frozenScroll.setMaximumSize(new Dimension(totalFrozenNaturalWidth, Integer.MAX_VALUE));
            // *************************************************

            // --- Step 2: Calculate the ideal width for the scrollable columns ---
            int scrollCols = scrollTable.getColumnCount();
            int totalScrollNaturalWidth = 0;
            int[] scrollNaturalWidths = new int[scrollCols];
            for (int col = 0; col < scrollCols; col++) {
                int naturalWidth = getPreferredWidth.apply(scrollTable, col);
                scrollNaturalWidths[col] = naturalWidth;
                totalScrollNaturalWidth += naturalWidth;
            }

            // --- Step 3: Distribute remaining space when maximized ---
            boolean maximized = (window instanceof JFrame) && ((((JFrame) window).getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0);
            int availableWidth = tablePanel.getWidth();
            int totalNaturalWidth = frozenScroll.getWidth() + totalScrollNaturalWidth;


            if (maximized && availableWidth > totalNaturalWidth) {
                int extraSpace = availableWidth - totalNaturalWidth;
                if (totalScrollNaturalWidth > 0) { 
                    for (int col = 0; col < scrollCols; col++) {
                        double proportion = (double) scrollNaturalWidths[col] / totalScrollNaturalWidth;
                        int additionalWidth = (int) (extraSpace * proportion);
                        scrollTable.getColumnModel().getColumn(col).setPreferredWidth(scrollNaturalWidths[col] + additionalWidth);
                    }
                }
            } else {
                for (int col = 0; col < scrollCols; col++) {
                    scrollTable.getColumnModel().getColumn(col).setPreferredWidth(scrollNaturalWidths[col]);
                }
            }
        };

        // Listen for window resize/maximize events
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0 && isDisplayable()) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent evt) {
                            adjustColumnWidths.run();
                        }
                        @Override
                        public void componentShown(ComponentEvent evt) {
                            adjustColumnWidths.run();
                        }
                    });
                    // Initial adjustment
                    adjustColumnWidths.run();
                }
            }
        });

        // --- Search functionality (like Employees.java) ---
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            private void filterTable() {
                String searchText = searchField.getText().toLowerCase();
                frozenModel.setRowCount(0);
                scrollModel.setRowCount(0);
                for (Object[] row : data) {
                    boolean matchFound = false;
                    for (Object cell : row) {
                        if (cell != null && cell.toString().toLowerCase().contains(searchText)) {
                            matchFound = true;
                            break;
                        }
                    }
                    if (matchFound) {
                        frozenModel.addRow(new Object[]{row[0], row[1]});
                        scrollModel.addRow(Arrays.copyOfRange(row, 2, row.length));
                    }
                }
                adjustColumnWidths.run();
            }
        });
    }
}
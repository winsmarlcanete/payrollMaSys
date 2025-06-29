package Screens;

import Components.BlackRoundedComboBox;
import Components.RoundedComboBox;
import Components.TableStyler;
import Entity.Employee;
import Entity.PayrollClass;
import Module.Payroll.Payroll;
import org.icepdf.ri.common.views.annotations.ScalableJScrollPane;
import org.payroll.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceReport extends JPanel {

    private JLabel adminLabel;

    private JTable frozenTable1;
    private JTable frozenTable2;
    private JTable scrollTable1;
    private JTable scrollTable2;
    private JPanel tablePanel;
    private DefaultTableModel frozenModel1;
    private DefaultTableModel frozenModel2;
    private DefaultTableModel scrollModel1;

    private BlackRoundedComboBox<String> payrollPeriod;
    private Date startDate;
    private Date endDate;

    private void setupTables() {
        // Create table model with 1 column
         frozenModel1 = new DefaultTableModel(new String[]{"Employee Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

         scrollModel1 = new DefaultTableModel(
                new String[]{"Days Worked", "Overtime (Hours)", "Night Differential (Hours)", "Special Holiday / Sunday (Hours)", "Legal Holiday (Hours)", "Late (Minutes)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

         frozenModel2 = new DefaultTableModel(new String[]{"Employee Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableModel scrollModel2 = new DefaultTableModel(
                new String[]{"Days Worked", "Overtime (Hours)", "Night Differential (Hours)", "Special Holiday / Sunday (Hours)", "Legal Holiday (Hours)", "Late (Minutes)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add 2 rows of data
        frozenModel2.addRow(new Object[]{"Total"});
        frozenModel2.addRow(new Object[]{"Grand Total"});

        // Add sample data
        String[] sampleNames = {
                "John Smith",
                "Mary Johnson",
                "Robert Brown",
                "Patricia Davis",
                "Michael Wilson",
                "Linda Anderson",
                "James Taylor",
                "Elizabeth Thomas",
                "William Martinez",
                "Jennifer Robinson",
                "David Clark",
                "Susan Lewis",
                "John Smith",
                "Mary Johnson",
                "Robert Brown",
                "Patricia Davis",
                "Michael Wilson",
                "Linda Anderson",
                "James Taylor",
                "Elizabeth Thomas",
                "William Martinez",
                "Jennifer Robinson",
                "David Clark",
                "Susan Lewis",
                "John Smith",
                "Mary Johnson",
                "Robert Brown",
                "Patricia Davis",
                "Michael Wilson",
                "Linda Anderson",
                "James Taylor",
                "Elizabeth Thomas",
                "William Martinez",
                "Jennifer Robinson",
                "David Clark",
                "Susan Lewis",
                "John Smith",
                "Mary Johnson",
                "Robert Brown",
                "Patricia Davis",
                "Michael Wilson",
                "Linda Anderson",
                "James Taylor",
                "Elizabeth Thomas",
                "William Martinez",
                "Jennifer Robinson",
                "David Clark",
                "Susan Lewis",
        };

        // Random number generator
        java.util.Random rand = new java.util.Random();
        DecimalFormat df = new DecimalFormat("#,##0.00");

        // Add rows to both tables with random data
        for (String name : sampleNames) {
            frozenModel1.addRow(new Object[]{name});

            // Generate random values for each column
            double basicPay = 30000 + rand.nextDouble() * 40000; // Range: 30,000 - 70,000
            double overtime = rand.nextDouble() * 5000;          // Range: 0 - 5,000
            double holidayPay = rand.nextDouble() * 3000;       // Range: 0 - 3,000
            double thirteenthMonth = basicPay / 12;             // 1/12 of basic pay
            double grossPay = basicPay + overtime + holidayPay + thirteenthMonth;
            double netPay = grossPay * 0.9;                     // Assuming 10% deductions

            scrollModel1.addRow(new Object[]{
                    df.format(basicPay),
                    df.format(overtime),
                    df.format(holidayPay),
                    df.format(thirteenthMonth),
                    df.format(grossPay),
                    df.format(netPay)
            });
        }

        for (int i = 0; i < 2; i++) {
            scrollModel2.addRow(new Object[]{
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00"
            });
        }

        // Create and configure frozenTable1
        frozenTable1 = new JTable(frozenModel1);
        TableStyler.styleTable(frozenTable1);
        frozenTable1.setShowGrid(false);
        frozenTable1.getTableHeader().setReorderingAllowed(false);
        frozenTable1.getTableHeader().setResizingAllowed(false);

        // Configure scrollTable1
        scrollTable1 = new JTable(scrollModel1);
        TableStyler.styleTable(scrollTable1);
        scrollTable1.setShowGrid(false);
        scrollTable1.getTableHeader().setReorderingAllowed(false);
        scrollTable1.getTableHeader().setResizingAllowed(false);

        frozenTable2 = new JTable(frozenModel2);
        TableStyler.styleTable(frozenTable2);
        frozenTable2.setShowGrid(false);
        frozenTable2.setTableHeader(null); // Hide header since it's a summary table

        scrollTable2 = new JTable(scrollModel2);
        TableStyler.styleTable(scrollTable2);
        scrollTable2.setShowGrid(false);
        scrollTable2.setTableHeader(null);

        scrollTable2.setBackground(MainWindow.grayColor);
        frozenTable2.setBackground(MainWindow.grayColor);

        Color lightGray = new Color(240, 240, 240);

        frozenTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(lightGray);
                }
                return c;
            }
        });

        scrollTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(lightGray);
                }
                return c;
            }
        });

        // Synchronize row selection
        ListSelectionModel selectionModel = frozenTable1.getSelectionModel();
        scrollTable1.setSelectionModel(selectionModel);

        // Highlight row on mouse hover (like Employees.java)
        frozenTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = frozenTable1.rowAtPoint(e.getPoint());
                if (row != -1) {
                    frozenTable1.setRowSelectionInterval(row, row);
                } else {
                    frozenTable1.clearSelection();
                }
            }
        });
        scrollTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = scrollTable1.rowAtPoint(e.getPoint());
                if (row != -1) {
                    scrollTable1.setRowSelectionInterval(row, row);
                } else {
                    scrollTable1.clearSelection();
                }
            }
        });


        // Configure column width
        TableColumnModel frozenColumnModel = frozenTable1.getColumnModel();
        TableColumn frozenColumn = frozenColumnModel.getColumn(0);
        frozenColumn.setPreferredWidth(200);

        TableColumnModel scrollColumnModel1 = scrollTable1.getColumnModel();
        for (int i = 0; i < scrollColumnModel1.getColumnCount(); i++) {
            TableColumn column = scrollColumnModel1.getColumn(i);
            column.setPreferredWidth(150);
        }

        // Configure column width
        TableColumnModel frozenColumnModel2 = frozenTable2.getColumnModel();
        TableColumn frozenColumn2 = frozenColumnModel2.getColumn(0);
        frozenColumn2.setPreferredWidth(200);

        TableColumnModel scrollColumnModel2 = scrollTable2.getColumnModel();
        for (int i = 0; i < scrollColumnModel2.getColumnCount(); i++) {
            TableColumn column = scrollColumnModel2.getColumn(i);
            column.setPreferredWidth(150);
        }

        // Create scroll pane for the table
        JScrollPane frozenScrollPane1 = new JScrollPane(frozenTable1);
        frozenScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        frozenScrollPane1.setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollScrollPane1 = new JScrollPane(scrollTable1,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollScrollPane1.setBorder(BorderFactory.createEmptyBorder());


        // Create scroll pane for frozenTable2
        JScrollPane frozenScrollPane2 = new JScrollPane(frozenTable2);
        frozenScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        frozenScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frozenScrollPane2.setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollScrollPane2 = new JScrollPane(scrollTable2);
        scrollScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollScrollPane2.setBorder(BorderFactory.createEmptyBorder());

        // Synchronize vertical scrolling
        scrollScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            frozenScrollPane1.getVerticalScrollBar().setValue(e.getValue());
        });

        // Custom scrollbars
        scrollScrollPane1.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        frozenScrollPane1.addMouseWheelListener(e -> {
            scrollScrollPane1.getVerticalScrollBar().setValue(
                    scrollScrollPane1.getVerticalScrollBar().getValue() + e.getWheelRotation() * e.getScrollAmount() * frozenTable1.getRowHeight()
            );
        });

        JPanel leftBottomTable = new JPanel(new BorderLayout());
        leftBottomTable.add(frozenScrollPane2, BorderLayout.CENTER);

        JPanel centerBottomTable = new JPanel(new BorderLayout());
        centerBottomTable.add(leftBottomTable, BorderLayout.WEST);
        centerBottomTable.add(scrollScrollPane2, BorderLayout.CENTER);

        int bottomTableHeight = (frozenTable2.getRowHeight() * 2) + 2; // 2 rows height + small buffer
        centerBottomTable.setPreferredSize(new Dimension(centerBottomTable.getPreferredSize().width, bottomTableHeight));

        // Create and configure tablePanel
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(frozenScrollPane1, BorderLayout.WEST);
        tablePanel.add(scrollScrollPane1, BorderLayout.CENTER);
        tablePanel.add(centerBottomTable, BorderLayout.SOUTH);
    }

    public static String[] getPeriods() {
        try {
            List<Date[]> periods = Payroll.retrieveAllPeriods();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            String[] formattedPeriods = new String[periods.size()];

            for (int i = 0; i < periods.size(); i++) {
                Date[] period = periods.get(i);
                formattedPeriods[i] = dateFormat.format(period[0]) + " - " + dateFormat.format(period[1]);
            }

            return formattedPeriods;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error retrieving periods"};
        }
    }
    public AttendanceReport() {
        setupTables();

        String[] periods = getPeriods();

        // Create and configure label
        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(new Font("Arial", Font.BOLD, 22));
        periodLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Initialize the class field instead of creating a local variable
        this.payrollPeriod = new BlackRoundedComboBox<>(periods); // Use this.payrollPeriod
        payrollPeriod.setName("payrollPeriod");
        payrollPeriod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payrollPeriod.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        payrollPeriod.setPreferredSize(new Dimension(290, 34));
        payrollPeriod.setBackground(Color.BLACK);
        payrollPeriod.setForeground(Color.WHITE);
        payrollPeriod.setFont(new Font("Arial", Font.PLAIN, 18));
        payrollPeriod.setOpaque(false);
        payrollPeriod.setEditable(false);

        payrollPeriod.addActionListener(e -> {
            System.out.println("" + payrollPeriod.getSelectedItem() + " selected from combo box BURAT");
            String selectedPeriod = (String) payrollPeriod.getSelectedItem();
            if (selectedPeriod != null && !selectedPeriod.isEmpty()) {
                populateTableData(selectedPeriod, adminLabel.getText());
            } else {
                System.err.println("No period selected or invalid selection.");
            }
        });

        // Rest of the constructor code remains the same
        adminLabel = new JLabel("All Departments");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminLabel.setOpaque(true);
        adminLabel.setBackground(Color.BLACK);
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));

        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        periodPanel.setOpaque(false);
        periodPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        periodPanel.add(periodLabel);
        periodPanel.add(payrollPeriod);
        periodPanel.add(Box.createHorizontalStrut(10));
        periodPanel.add(adminLabel);
        periodPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(periodPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);


    }

    public void updateDepartmentLabel(String department) {
        if (adminLabel != null) {
            adminLabel.setText(department);
        }
    }

    private void populateTableData(String selectedPeriod, String department) {
        try {
            System.out.println("Selected Period: " + selectedPeriod);

            String[] dates = selectedPeriod.split(" - ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

            // Parse the start and end dates
            startDate = new java.sql.Date(dateFormat.parse(dates[0].trim()).getTime());
            endDate = new java.sql.Date(dateFormat.parse(dates[1].trim()).getTime());

            System.out.println("Start Date: " + startDate + ", End Date: " + endDate);

            // Retrieve attendance data using the start and end dates
            List<Map<String, Object>> attendanceData = Payroll.retrieveAttendanceData(startDate, endDate, department);
            System.out.println("Retrieved attendance data size: " + attendanceData.size());

            // Clear existing data
            frozenModel1.setRowCount(0);
            scrollModel1.setRowCount(0);

            // Populate tables with new data
            for (Map<String, Object> row : attendanceData) {
                System.out.println("Processing row: " + row);

                frozenModel1.addRow(new Object[]{
                        row.get("name")
                });

                scrollModel1.addRow(new Object[]{
                        row.get("days_worked"),
                        row.get("overtime"),
                        row.get("night_diff"),
                        row.get("special_holiday"),
                        row.get("legal_holiday"),
                        row.get("late")
                });
            }

            System.out.println("Final row count - frozenModel1: " + frozenModel1.getRowCount());
            System.out.println("Final row count - scrollModel1: " + scrollModel1.getRowCount());

        } catch (Exception e) {
            System.err.println("Error in populateTableData: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load attendance data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




}
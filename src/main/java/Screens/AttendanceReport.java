package Screens;

import Components.BlackRoundedComboBox;
import Components.RoundedComboBox;
import Components.TableStyler;
import Entity.Employee;
import Entity.PayrollClass;
import Module.Payroll.Payroll;
import org.icepdf.ri.common.views.annotations.ScalableJScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceReport extends JPanel {

    private JLabel adminLabel;

    private JTable frozenTable1;
    private JTable scrollTable1;
    private JPanel tablePanel;

    private void setupTables() {
        // Create table model with 1 column
        DefaultTableModel frozenModel1 = new DefaultTableModel(new String[]{"Employee Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableModel scrollModel1 = new DefaultTableModel(
                new String[]{"Basic Pay", "Overtime", "Holiday Pay", "13th Month", "Gross Pay", "Net Pay"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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

        // Add rows to both tables with sample data
        for (String name : sampleNames) {
            frozenModel1.addRow(new Object[]{name});
            scrollModel1.addRow(new Object[]{
                    "50000.00",
                    "2500.00",
                    "1000.00",
                    "4166.67",
                    "57666.67",
                    "51900.00"
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

        // Configure column width
        TableColumnModel frozenColumnModel = frozenTable1.getColumnModel();
        TableColumn frozenColumn = frozenColumnModel.getColumn(0);
        frozenColumn.setPreferredWidth(200);

        TableColumnModel scrollColumnModel1 = scrollTable1.getColumnModel();
        for (int i = 0; i < scrollColumnModel1.getColumnCount(); i++) {
            TableColumn column = scrollColumnModel1.getColumn(i);
            column.setPreferredWidth(150);
        }

        // Create scroll pane for the table
        JScrollPane frozenScrollPane1 = new JScrollPane(frozenTable1);
        frozenScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        JScrollPane scrollScrollPane1 = new JScrollPane(scrollTable1);

        // Synchronize vertical scrolling
        scrollScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            frozenScrollPane1.getVerticalScrollBar().setValue(e.getValue());
        });

        frozenScrollPane1.addMouseWheelListener(e -> {
            scrollScrollPane1.getVerticalScrollBar().setValue(
                    scrollScrollPane1.getVerticalScrollBar().getValue() + e.getWheelRotation() * e.getScrollAmount() * frozenTable1.getRowHeight()
            );
        });

        // Create and configure tablePanel
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(frozenScrollPane1, BorderLayout.WEST);
        tablePanel.add(scrollScrollPane1, BorderLayout.CENTER);
    }

    public AttendanceReport() {

        setupTables();

        // Sample data for combobox
        String[] periods = {
                "Jan 01, 2024 - Jan 15, 2024",
                "Jan 16, 2024 - Jan 31, 2024",
                "Feb 01, 2024 - Feb 15, 2024"
        };

        // Create and configure panel

        // Create and configure label
        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(new Font("Arial", Font.BOLD, 22));
        periodLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Create and configure combobox
        BlackRoundedComboBox<String> payrollPeriod = new BlackRoundedComboBox<>(periods);
        payrollPeriod.setName("payrollPeriod");
        payrollPeriod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payrollPeriod.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        payrollPeriod.setPreferredSize(new Dimension(290, 34));
        payrollPeriod.setBackground(Color.BLACK);
        payrollPeriod.setForeground(Color.WHITE);
        payrollPeriod.setFont(new Font("Arial", Font.PLAIN, 18));
        payrollPeriod.setOpaque(false);
        payrollPeriod.setEditable(false);

        adminLabel = new JLabel("All Departments");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminLabel.setOpaque(true);
        adminLabel.setBackground(Color.BLACK);
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));

        // Add components to panel
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
}
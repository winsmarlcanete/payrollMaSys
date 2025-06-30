package Screens;

import Components.TableStyler;
import org.payroll.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Objects; // Added for Objects.requireNonNull

public class ChangeLog extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public ChangeLog() {
        // Set up the main panel layout
        setLayout(new BorderLayout());
        setBackground(MainWindow.activeColor); // MediumSeaGreen background for the main panel
//        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the content

        // Create tabbed pane for Activity Log and Error Log
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); // Allow tabs to wrap if many
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // --- Activity Log Tab ---
        JPanel activityLogPanel = new JPanel(new BorderLayout());
        activityLogPanel.setBackground(Color.WHITE); // White background for the activity log content
//        activityLogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding

        // Create table model and data for Activity Log
        String[] activityColumnNames = {"Name", "Position", "Date", "Time", "Activity"};
        Object[][] activityData = {
                {"Aela Cruz, Juan C.", "HR", "08 / 23 / 24", "8:23 A.M.", "Registered an employee"},
                {"Aela Cruz, Juan C.", "HR", "08 / 23 / 24", "8:23 A.M.", "Registered an employee"},
                {"Aela Cruz, Juan C.", "HR", "08 / 23 / 24", "8:23 A.M.", "Registered an employee"},
                {"Bela Cruz, Juan C.", "Accountant", "08 / 23 / 24", "8:23 A.M.", "Modified payroll"},
                {"Bela Cruz, Juan C.", "Accountant", "08 / 23 / 24", "8:23 A.M.", "Modified payroll"},
                {"Bela Cruz, Juan C.", "Accountant", "08 / 23 / 24", "8:23 A.M.", "Generated payslip"},
                // Add more sample data as needed
        };

        DefaultTableModel activityTableModel = new DefaultTableModel(activityData, activityColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        JTable activityTable = new JTable(activityTableModel);
        activityTable.setFillsViewportHeight(true); // Table fills the height of its scroll pane
        activityTable.setRowHeight(40); // Set row height for better spacing
        TableStyler.styleTable(activityTable); // Apply custom table styling
        activityTable.setSelectionBackground(new Color(220, 220, 220)); // Light grey selection background

        activityTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = activityTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    activityTable.setRowSelectionInterval(row, row);
                } else {
                    activityTable.clearSelection();
                }
            }
        });

        activityTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                if (activityTable.rowAtPoint(p) >= 0) {
                    activityTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    activityTable.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        // Center align activity header text
//        DefaultTableCellRenderer activityHeaderRenderer = new DefaultTableCellRenderer();
//        activityHeaderRenderer.setHorizontalAlignment(JLabel.CENTER);
//        activityTableHeader.setDefaultRenderer(activityHeaderRenderer);

        // Wrap the activity table in a JScrollPane
        JScrollPane activityScrollPane = new JScrollPane(activityTable);
        activityScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
        activityLogPanel.add(activityScrollPane, BorderLayout.CENTER);

        // Add the activity log panel to the tabbed pane
        tabbedPane.addTab("Activity Log", activityLogPanel);


        // --- Error Log Tab ---
        JPanel errorLogPanel = new JPanel(new BorderLayout());
        errorLogPanel.setBackground(Color.WHITE); // White background for the error log content
//        errorLogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding

        // Create table model and data for Error Log
        String[] errorColumnNames = {"Date", "Time", "Error Code", "Error Message", "Activity"};
        Object[][] errorData = {
                {"08 / 23 / 24", "8:23 A.M.", "PAY-002", "Invalid salary amount", "java.lang.NumberFormatException:"},
                {"08 / 23 / 24", "8:23 A.M.", "PAY-003", "Deduct Calc. Failed", "java.lang.NumberFormatExcepitonh"},
                {"08 / 23 / 24", "8:23 A.M.", "PAY-003", "Database connection", "java.lang.SQLException: Error"},
                // Add more sample data as needed
        };

        DefaultTableModel errorTableModel = new DefaultTableModel(errorData, errorColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        JTable errorTable = new JTable(errorTableModel);
        errorTable.setFillsViewportHeight(true);
        errorTable.setRowHeight(40);
        TableStyler.styleTable(errorTable);
        errorTable.setSelectionBackground(new Color(220, 220, 220));

        errorTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                if (errorTable.rowAtPoint(p) >= 0) {
                    errorTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    errorTable.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        errorTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = errorTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    errorTable.setRowSelectionInterval(row, row);
                } else {
                    errorTable.clearSelection();
                }
            }
        });

        // Center align error header text
//        DefaultTableCellRenderer errorHeaderRenderer = new DefaultTableCellRenderer();
//        errorHeaderRenderer.setHorizontalAlignment(JLabel.CENTER);
//        errorTableHeader.setDefaultRenderer(errorHeaderRenderer);

        // Wrap the error table in a JScrollPane
        JScrollPane errorScrollPane = new JScrollPane(errorTable);
        errorScrollPane.setBorder(BorderFactory.createEmptyBorder());
        errorLogPanel.add(errorScrollPane, BorderLayout.CENTER);

        // Add the error log panel to the tabbed pane
        tabbedPane.addTab("Error Log", errorLogPanel);

        // Customize tabbed pane appearance
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font for tab titles
        tabbedPane.setForeground(new Color(50, 50, 50)); // Color for selected tab text
        tabbedPane.setBackground(new Color(240, 240, 240)); // Background of the tab area
        // tabbedPane.setSelectedTabColor(Color.WHITE); // This method doesn't exist directly, but you can achieve it via UI delegates or custom renderers

        // Add the tabbed pane to the main panel
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Example of how to use this ChangeLog screen
        JFrame frame = new JFrame("Change Log");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set initial size
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.add(new ChangeLog());
        frame.setVisible(true);
    }
}

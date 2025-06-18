package Screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import Components.TableStyler; // Assuming this class exists and works as intended
import Module.E201File.E201File;

import java.awt.*;
import java.awt.event.*;

public class Attendance extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    private static DefaultTableModel employeeTableModel;
    private static String[] columnHeaders = { "Name", "ID", "Department", "Employment Status" };
    private JTable table;

    public static void loadEmployeeTabledata() {
        Object[][] data = E201File.getEmployeeTableData(); // query your source
        employeeTableModel.setDataVector(data, columnHeaders);
    }

    public Attendance() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // --- Original table panel (kept for context) ---
        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField();
        JButton backupButton = new JButton("Attendance Backup");
        backupButton.setPreferredSize(new Dimension(180, 30));
        backupButton.setBackground(new Color(0, 100, 0));
        backupButton.setForeground(Color.WHITE);
        backupButton.setFocusPainted(false);

        backupButton.addActionListener(e -> {
            mainContainer.add(new AttendanceBackup(cardLayout, mainContainer), "backup");
            cardLayout.show(mainContainer, "backup");
        });

        searchField.setPreferredSize(new Dimension(300, 30));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(backupButton, BorderLayout.EAST);

        Object[][] data = E201File.getEmployeeTableData();
        employeeTableModel = new DefaultTableModel(data, columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(employeeTableModel);

        table.getTableHeader().setReorderingAllowed(false);
        TableStyler.styleTable(table);

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(employeeTableModel);
        table.setRowSorter(rowSorter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            public void changedUpdate(DocumentEvent e) {
                filter();
            }

            private void filter() {
                String text = searchField.getText().trim();
                if (text.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));
                }
            }
        });

        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    table.setRowSelectionInterval(row, row);
                } else {
                    table.clearSelection();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    searchField.setText("");

                    int modelRow = table.convertRowIndexToModel(row);
                    Object[] rowData = new Object[table.getColumnCount()];
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        rowData[i] = employeeTableModel.getValueAt(modelRow, i);
                    }
                    JPanel detailPanel = createDetailsPanel(rowData);
                    mainContainer.add(detailPanel, "details");
                    cardLayout.show(mainContainer, "details");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0)));

        tablePanel.add(topPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainContainer.add(tablePanel, "table");
        // --- End of original table panel ---

        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createDetailsPanel(Object[] employeeData) {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(34, 139, 34)); // Dark green background for the entire panel

        // --- Top Header Panel ---
        JPanel headerPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout for flexible positioning
        headerPanel.setBackground(new Color(34, 139, 34)); // Green background
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.insets = new Insets(0, 0, 0, 0); // No extra padding for header elements

        // Back button
        gbcHeader.gridx = 0;
        gbcHeader.gridy = 0;
        gbcHeader.anchor = GridBagConstraints.WEST; // Align to the left
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(80, 30)); // Set preferred size for consistency
        headerPanel.add(backButton, gbcHeader);

        backButton.addActionListener(e -> cardLayout.show(mainContainer, "table"));

        // Title centered
        gbcHeader.gridx = 1;
        gbcHeader.weightx = 1.0; // Give extra space to the title column to push it to center
        gbcHeader.anchor = GridBagConstraints.CENTER; // Center horizontally
        JLabel title = new JLabel("Attendance Monitoring", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        headerPanel.add(title, gbcHeader);

        detailsPanel.add(headerPanel, BorderLayout.NORTH);

        // --- Centered Content Panel ---
        JPanel centeredContentWrapper = new JPanel(new GridBagLayout()); // This panel will center its content
        centeredContentWrapper.setOpaque(false); // Make it transparent so the green background shows through
        centeredContentWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // Padding from sides and
                                                                                          // bottom

        GridBagConstraints gbcWrapper = new GridBagConstraints();
        gbcWrapper.gridx = 0;
        gbcWrapper.gridy = 0;
        gbcWrapper.weightx = 1.0; // Allow content to expand horizontally
        gbcWrapper.anchor = GridBagConstraints.CENTER; // Center the wrapped content

        // Inner panel for the employee details and white box
        JPanel innerContentPanel = new JPanel();
        innerContentPanel.setLayout(new BoxLayout(innerContentPanel, BoxLayout.Y_AXIS));
        innerContentPanel.setOpaque(false); // Make it transparent
        // No explicit border here, relying on centeredContentWrapper's padding

        // Employee details fields panel
        JPanel employeeDetailsPanel = new JPanel(new GridBagLayout());
        employeeDetailsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left within their cell

        // Row 1: Name and ID
        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        employeeDetailsPanel.add(createStyledField("Name", (String) employeeData[0], 200, Color.WHITE, Color.BLACK),
                gbc);

        // ID
        gbc.gridx = 1;
        gbc.gridy = 0;
        employeeDetailsPanel.add(createStyledField("ID", String.valueOf(employeeData[1]), 80, Color.WHITE, Color.BLACK), gbc);

        // Add an empty space to push the first row fields to the left (similar to
        // FlowLayout.LEFT behavior)
        gbc.gridx = 2;
        gbc.weightx = 1.0; // This column takes up extra horizontal space
        employeeDetailsPanel.add(Box.createHorizontalGlue(), gbc);
        gbc.weightx = 0; // Reset weight for subsequent components

        // Row 2: Department, Employment, Shift start, Shift end
        String[] labelsRow2 = { "Department", "Employment", "Shift start", "Shift end" };
        String[] valuesRow2 = {
                (String) employeeData[2],
                (String) employeeData[3],
                "9:00", "17:00"
        };
        int[] widthsRow2 = { 160, 100, 80, 80 }; // Custom widths for better fit

        for (int i = 0; i < labelsRow2.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 1;
            employeeDetailsPanel
                    .add(createStyledField(labelsRow2[i], valuesRow2[i], widthsRow2[i], Color.WHITE, Color.BLACK), gbc);
        }

        // Add an empty space to push the second row fields to the left
        gbc.gridx = labelsRow2.length;
        gbc.weightx = 1.0;
        employeeDetailsPanel.add(Box.createHorizontalGlue(), gbc);
        gbc.weightx = 0;

        innerContentPanel.add(employeeDetailsPanel);
        innerContentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between fields and Period

        // Period section
        JPanel periodContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Align left, small horizontal gap
        periodContainer.setOpaque(false);
        periodContainer.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // Left padding to align with fields

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        periodLabel.setForeground(Color.WHITE); // White color for "Period:"
        periodContainer.add(periodLabel);

        JButton startDate = new JButton("Oct 21, 2024");
        JButton endDate = new JButton("Nov 5, 2024");

        for (JButton btn : new JButton[] { startDate, endDate }) {
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false); // Remove button border
            btn.setPreferredSize(new Dimension(100, 30)); // Set preferred size for consistency
            periodContainer.add(btn);
        }
        periodContainer.add(new JLabel(" - ", SwingConstants.CENTER)); // Add the dash
        // No need to adjust individual button sizes using getComponent here if
        // preferredSize is set earlier.

        innerContentPanel.add(periodContainer);
        innerContentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space before white box

        // White content box
        JPanel whiteBox = new JPanel();
        whiteBox.setPreferredSize(new Dimension(740, 350));
        whiteBox.setMaximumSize(new Dimension(740, 350));
        whiteBox.setBackground(Color.WHITE);
        whiteBox.setLayout(null); // Keep null layout if you plan to absolutely position components inside
        whiteBox.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the white box (within BoxLayout context)
        innerContentPanel.add(whiteBox);

        // Add the innerContentPanel to the centeredContentWrapper
        centeredContentWrapper.add(innerContentPanel, gbcWrapper);

        // Add the centeredContentWrapper to the detailsPanel
        detailsPanel.add(centeredContentWrapper, BorderLayout.CENTER);

        return detailsPanel;
    }

    // Helper method to create a styled label and text field group
    private JPanel createStyledField(String labelText, String fieldValue, int width, Color fieldBg, Color fieldFg) {
        JPanel group = new JPanel();
        group.setLayout(new BorderLayout(0, 5)); // Small vertical gap between label and field
        group.setOpaque(false);

        JLabel lbl = new JLabel(labelText, SwingConstants.LEFT); // Align label text to left
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        group.add(lbl, BorderLayout.NORTH);

        JTextField txt = new JTextField(fieldValue);
        txt.setEditable(false);
        txt.setHorizontalAlignment(JTextField.LEFT); // Align field text to left
        txt.setPreferredSize(new Dimension(width, 30));
        txt.setBackground(fieldBg); // Configurable background
        txt.setForeground(fieldFg); // Configurable foreground
        txt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Remove border, add some internal padding
        group.add(txt, BorderLayout.CENTER);

        return group;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Attendance System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new Attendance());
            frame.setVisible(true);
        });
    }
}
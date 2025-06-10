package Screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class Attendance extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private DefaultTableModel model;

    public Attendance() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField();
        JButton backupButton = new JButton("Attendance Backup");
        backupButton.setPreferredSize(new Dimension(180, 30));
        backupButton.setBackground(new Color(0, 100, 0));
        backupButton.setForeground(Color.WHITE);
        // backupButton.setFocusPainted(false); optional

        backupButton.addActionListener(e -> {
            mainContainer.add(new AttendanceBackup(cardLayout, mainContainer), "backup");
            cardLayout.show(mainContainer, "backup");
        });

        searchField.setPreferredSize(new Dimension(300, 30));
        backupButton.setPreferredSize(new Dimension(180, 30));
        backupButton.setBackground(new Color(0, 100, 0));
        backupButton.setForeground(Color.WHITE);
        backupButton.setFocusPainted(false);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(backupButton, BorderLayout.EAST);

        String[] columnNames = { "Name", "ID", "Department", "Employment Status" };
        Object[][] data = {
                { "Aela Cruz, Juan C.", "1", "Sales", "Regular" },
                { "Bela Cruz, Juan C.", "23", "Sales", "Regular" },
                { "Cela Cruz, Juan C.", "31", "Production (Pre-Press)", "Regular" },
                { "Dela Cruz, Juan C.", "14", "Production (Pre-Press)", "Regular" },
                { "Eela Cruz, Juan C.", "25", "Production (Pre-Press)", "Regular" },
                { "Fela Cruz, Juan C.", "36", "Production (Press)", "Regular" },
                { "Gela Cruz, Juan C.", "15", "Production (Press)", "Regular" },
                { "Hela Cruz, Juan C.", "4", "Production (Press)", "Regular" },
                { "Hela Cruz, Juan C.", "8", "Production (Post-Press)", "Regular" },
                { "Iela Cruz, Juan C.", "10", "Production (Post-Press)", "Regular" },
                { "Jela Cruz, Juan C.", "17", "Production (Quality Control)", "Regular" },
                { "Kela Cruz, Juan C.", "22", "Production (Quality Control)", "Regular" },
        };

        model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        styleTable(table);

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(model);
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

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    searchField.setText("");

                    int modelRow = table.convertRowIndexToModel(row);
                    Object[] rowData = new Object[table.getColumnCount()];
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        rowData[i] = model.getValueAt(modelRow, i);
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

        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createDetailsPanel(Object[] employeeData) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        detailsPanel.setBackground(new Color(34, 139, 34));

        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 20, 100, 30);
        detailsPanel.add(backButton);

        backButton.addActionListener(e -> cardLayout.show(mainContainer, "table"));

        String[] labels = { "Name", "ID", "Department", "Employment Status", "Time-in", "Time-out", "Rate / Hour",
                "Remaining SIL", "Date Range", "Total Late", "Absences", "Total OT", "Total Undertime" };
        String[] values = {
                (String) employeeData[0],
                (String) employeeData[1],
                (String) employeeData[2],
                (String) employeeData[3],
                "9:00 A.M.",
                "5:00 P.M.",
                "₱ 610.00",
                "3 / 5",
                "08 / 12 / 24 - 09 / 22 / 24",
                "30 mins",
                "3",
                "20 mins",
                "20 mins"
        };

        int x = 140, y = 60;
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            JTextField txt = new JTextField(values[i]);
            lbl.setBounds(x, y, 200, 25);
            txt.setBounds(x, y + 30, 200, 30);
            txt.setEditable(false);
            detailsPanel.add(lbl);
            detailsPanel.add(txt);
            x += 220;
            if ((i + 1) % 4 == 0) {
                x = 140;
                y += 80;
            }
        }

        return detailsPanel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setGridColor(new Color(0, 128, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 128, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Attendance::new);
    }
}
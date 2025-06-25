package Screens;

import Components.RoundedComboBox;
import javafx.scene.shape.Box;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class LeaveReport extends JPanel {
    public LeaveReport() {
        // Search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(10, 0));
        searchPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 70));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        Font font = new Font("Arial", Font.PLAIN, 16);

        JTextField searchField = new JTextField();
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

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);

        // Leave Type and Date Taken Table
        DefaultTableModel rightTableModel = new DefaultTableModel(
                new Object[][] {
                        {"Sick Leave", "Jan 10, 2024"},
                        {"Sick Leave", "Feb 12, 2024"},
                        {"Maternity Leave", "2023-03-20"},
                        {"Vacation Leave", "2023-04-15"},
                        {"Vacation Leave", "2023-05-10"},
                        {"Vacation Leave", "2023-06-05"},
                        {"Sick Leave", "2023-07-20"},
                        {"Sick Leave", "2023-08-25"},
                        {"Maternity Leave", "2023-09-30"},
                        {"Vacation Leave", "2023-10-15"},
                        {"Vacation Leave", "2023-11-10"},
                        {"Vacation Leave", "2023-12-05"},
                        {"Sick Leave", "2024-01-20"},
                        {"Sick Leave", "2024-02-25"},
                        {"Maternity Leave", "2024-03-30"},
                        {"Vacation Leave", "2024-04-15"},
                        {"Vacation Leave", "2024-05-10"},
                        {"Vacation Leave", "2024-06-05"},
                        {"Sick Leave", "2024-07-20"},
                        {"Sick Leave", "2024-08-25"},
                        {"Maternity Leave", "2024-09-30"},
                        {"Vacation Leave", "2024-10-15"},
                        {"Vacation Leave", "2024-11-10"},
                        {"Vacation Leave", "2024-12-05"},
                        {"Sick Leave", "Jan 10, 2024"},
                        {"Sick Leave", "Feb 12, 2024"},
                        {"Maternity Leave", "2023-03-20"},
                        {"Vacation Leave", "2023-04-15"},
                        {"Vacation Leave", "2023-05-10"},
                        {"Vacation Leave", "2023-06-05"},
                        {"Sick Leave", "2023-07-20"},
                        {"Sick Leave", "2023-08-25"},
                        {"Maternity Leave", "2023-09-30"},
                        {"Vacation Leave", "2023-10-15"},
                        {"Vacation Leave", "2023-11-10"},
                        {"Vacation Leave", "2023-12-05"},
                        {"Sick Leave", "2024-01-20"},
                        {"Sick Leave", "2024-02-25"},
                        {"Maternity Leave", "2024-03-30"},
                        {"Vacation Leave", "2024-04-15"},
                        {"Vacation Leave", "2024-05-10"},
                        {"Vacation Leave", "2024-06-05"},
                        {"Sick Leave", "2024-07-20"},
                        {"Sick Leave", "2024-08-25"},
                        {"Maternity Leave", "2024-09-30"},
                        {"Vacation Leave", "2024-10-15"},
                        {"Vacation Leave", "2024-11-10"},
                        {"Vacation Leave", "2024-12-05"},
                        {"Sick Leave", "Jan 10, 2024"},
                        {"Sick Leave", "Feb 12, 2024"},
                        {"Maternity Leave", "2023-03-20"},
                        {"Vacation Leave", "2023-04-15"},
                        {"Vacation Leave", "2023-05-10"},
                        {"Vacation Leave", "2023-06-05"},
                        {"Sick Leave", "2023-07-20"},
                        {"Sick Leave", "2023-08-25"},
                        {"Maternity Leave", "2023-09-30"},
                        {"Vacation Leave", "2023-10-15"},
                        {"Vacation Leave", "2023-11-10"},
                        {"Vacation Leave", "2023-12-05"},
                        {"Sick Leave", "2024-01-20"},
                        {"Sick Leave", "2024-02-25"},
                        {"Maternity Leave", "2024-03-30"},
                        {"Vacation Leave", "2024-04-15"},
                        {"Vacation Leave", "2024-05-10"},
                        {"Vacation Leave", "2024-06-05"},
                        {"Sick Leave", "2024-07-20"},
                        {"Sick Leave", "2024-08-25"},
                        {"Maternity Leave", "2024-09-30"},
                        {"Vacation Leave", "2024-10-15"},
                        {"Vacation Leave", "2024-11-10"},
                        {"Vacation Leave", "2024-12-05"},
                        {"Sick Leave", "Jan 10, 2024"},
                        {"Sick Leave", "Feb 12, 2024"},
                        {"Maternity Leave", "2023-03-20"},
                        {"Vacation Leave", "2023-04-15"},
                        {"Vacation Leave", "2023-05-10"},
                        {"Vacation Leave", "2023-06-05"},
                        {"Sick Leave", "2023-07-20"},
                        {"Sick Leave", "2023-08-25"},
                        {"Maternity Leave", "2023-09-30"},
                        {"Vacation Leave", "2023-10-15"},
                        {"Vacation Leave", "2023-11-10"},
                        {"Vacation Leave", "2023-12-05"},
                        {"Sick Leave", "2024-01-20"},
                        {"Sick Leave", "2024-02-25"},
                        {"Maternity Leave", "2024-03-30"},
                        {"Vacation Leave", "2024-04-15"},
                        {"Vacation Leave", "2024-05-10"},
                        {"Vacation Leave", "2024-06-05"},
                        {"Sick Leave", "2024-07-20"},
                        {"Sick Leave", "2024-08-25"},
                        {"Maternity Leave", "2024-09-30"},
                        {"Vacation Leave", "2024-10-15"},
                        {"Vacation Leave", "2024-11-10"},
                        {"Vacation Leave", "2024-12-05"},
                        {"Sick Leave", "Jan 10, 2024"},
                        {"Sick Leave", "Feb 12, 2024"},
                        {"Maternity Leave", "2023-03-20"},
                        {"Vacation Leave", "2023-04-15"},
                        {"Vacation Leave", "2023-05-10"},
                        {"Vacation Leave", "2023-06-05"},
                        {"Sick Leave", "2023-07-20"},
                        {"Sick Leave", "2023-08-25"},
                        {"Maternity Leave", "2023-09-30"},
                        {"Vacation Leave", "2023-10-15"},
                        {"Vacation Leave", "2023-11-10"},
                        {"Vacation Leave", "2023-12-05"},
                        {"Sick Leave", "2024-01-20"},
                        {"Sick Leave", "2024-02-25"},
                        {"Maternity Leave", "2024-03-30"},
                        {"Vacation Leave", "2024-04-15"},
                        {"Vacation Leave", "2024-05-10"},
                        {"Vacation Leave", "2024-06-05"},
                        {"Sick Leave", "2024-07-20"},
                        {"Sick Leave", "2024-08-25"},
                        {"Maternity Leave", "2024-09-30"},
                        {"Vacation Leave", "2024-10-15"},
                        {"Vacation Leave", "2024-11-10"},
                        {"Vacation Leave", "2024-12-05"},
                },
                new String[] {"Leave Type", "Date Taken"}
        );

        JTable rightTable = new JTable(rightTableModel);
        rightTable.setFont(new Font("Arial", Font.PLAIN, 16));
        rightTable.setRowHeight(40);
        rightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightTable.setDefaultEditor(Object.class, null); // Make cells non-editable

        JScrollPane rightTableScrollPane = new JScrollPane(rightTable);
        rightTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        rightTableScrollPane.add(rightTable.getTableHeader());

        // Name Column Table
        DefaultTableModel nameTableModel = new DefaultTableModel(
                new Object[][] {
                        {"Supan, Marc"},
                        {"Cañete, Winsmarl"},
                        {"Serrano, Jerwin"},
                        {"Dela Cruz, Adrian"},
                        {"Santos, Bianca"},
                        {"Garcia, Lorenzo"},
                        {"Reyes, Camille"},
                        {"Mendoza, Ethan"},
                        {"Torres, Sofia"},
                        {"Ramos, Miguel"},
                        {"Flores, Alyssa"},
                        {"Gomez, Nathaniel"},
                        {"Castro, Isabelle"},
                        {"Alvarez, Joshua"},
                        {"Gutierrez, Clarisse"},
                        {"Lopez, Samuel"},
                        {"Morales, Kristine"},
                        {"Diaz, Patrick"}
                },
                new String[] {"Name"}
        );

        JTable nameColumn = new JTable(nameTableModel);
        nameColumn.setFont(new Font("Arial", Font.PLAIN, 16));
        nameColumn.setRowHeight(200);
        nameColumn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nameColumn.setDefaultEditor(Object.class, null); // Make cells non-editable

        JScrollPane nameTableScrollPane = new JScrollPane(nameColumn);
        nameTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        nameTableScrollPane.add(nameColumn.getTableHeader());

        // SIL Column Table
        DefaultTableModel leaveTableModel = new DefaultTableModel(
                new Object[][] {
                        {"2/5"},
                        {"4/5"},
                        {"1/5"},
                        {"3/5"},
                        {"0/5"},
                        {"5/5"},
                        {"2/5"},
                        {"1/5"},
                        {"4/5"},
                        {"3/5"},
                        {"2/5"},
                        {"5/5"},
                        {"0/5"}
                },
                new String[] {"Leave Remaining"}
        );

        JTable leaveColumn = new JTable(leaveTableModel);
        leaveColumn.setFont(new Font("Arial", Font.PLAIN, 16));
        leaveColumn.setRowHeight(200);
        leaveColumn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leaveColumn.setDefaultEditor(Object.class, null);

        JScrollPane leaveTableScrollPane = new JScrollPane(leaveColumn);
        leaveTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        leaveTableScrollPane.add(leaveColumn.getTableHeader());

        JPanel leftTablePanel = new JPanel();
        leftTablePanel.setLayout(new BoxLayout(leftTablePanel, BoxLayout.X_AXIS));
        leftTablePanel.add(nameTableScrollPane);
        leftTablePanel.add(leaveTableScrollPane);

        JPanel rightTablePanel = new JPanel(new BorderLayout());
        rightTablePanel.add(rightTableScrollPane, BorderLayout.CENTER);

        nameColumn.getTableHeader().setReorderingAllowed(false);
        leaveColumn.getTableHeader().setReorderingAllowed(false);
        rightTable.getTableHeader().setReorderingAllowed(false);

        // Synchronize vertical scrolling between rightTable and nameColumn
        nameTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        leaveTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        JScrollBar rightVertical = rightTableScrollPane.getVerticalScrollBar();
        JScrollBar nameVertical = nameTableScrollPane.getVerticalScrollBar();
        JScrollBar leaveVertical = leaveTableScrollPane.getVerticalScrollBar();

        int rightRowHeight = rightTable.getRowHeight();
        int nameRowHeight = nameColumn.getRowHeight();
        int leaveRowHeight = leaveColumn.getRowHeight();

        rightVertical.addAdjustmentListener(e -> {
            int rightValue = rightVertical.getValue();
            // Scale the scroll position by the row height ratio
            int nameValue = (int) ((rightValue / (double) rightRowHeight) * nameRowHeight);
            nameVertical.setValue(nameValue);
            int leaveValue = (int) ((rightValue / (double) rightRowHeight) * leaveRowHeight);
            leaveVertical.setValue(leaveValue);
        });

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.add(leftTablePanel);
        tablePanel.add(rightTablePanel);

        add(tablePanel, BorderLayout.CENTER);
    }
}

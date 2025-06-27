package Screens;

import Components.BlackRoundedComboBox;
import Components.RoundedComboBox;
import Components.TableStyler;
import javafx.scene.shape.Box;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.regex.Pattern;

public class LeaveReport extends JPanel {
    private JTable rightTable;
    private JTable leftTable;
    private void selectGroupInRightTable(int row) {
        int groupStart = (row / 5) * 5;
        int groupEnd = groupStart + 4;
        rightTable.getSelectionModel().setSelectionInterval(groupStart, groupEnd);

        int leftRow = row / 5;
        leftTable.getSelectionModel().setSelectionInterval(leftRow, leftRow);
    }

    public LeaveReport() {
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
                },
                new String[] {"Leave Type", "Date Taken"}
        );

        rightTable = new JTable(rightTableModel);
        rightTable.setFont(new Font("Arial", Font.PLAIN, 16));
        rightTable.setRowHeight(40);
        rightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightTable.setDefaultEditor(Object.class, null); // Make cells non-editable
        TableStyler.styleTable(rightTable);

        JScrollPane rightTableScrollPane = new JScrollPane(rightTable);
        rightTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        rightTableScrollPane.add(rightTable.getTableHeader());

        // Name and SIL Table
        DefaultTableModel leftTableModel = new DefaultTableModel(
                new Object[][] {
                        {"Supan, Marc", "2/5"},
                        {"Cañete, Winsmarl", "4/5"},
                        {"Serrano, Jerwin", "1/5"},
                        {"Reyes, Daniel", "3/5"},
                        {"Dela Cruz, Anna", "0/5"},
                        {"Santos, Miguel", "5/5"},
                        {"Lopez, Carla", "2/5"},
                        {"Garcia, Paolo", "1/5"},
                        {"Torres, Jasmine", "4/5"},
                        {"Rivera, John", "3/5"}
                },
                new String[] {"Name", "Remaining Leaves"}
        );

        leftTable = new JTable(leftTableModel);
        leftTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftTable.setDefaultEditor(Object.class, null); // Make cells non-editable
        TableStyler.styleTable(leftTable);
        leftTable.setRowHeight(200);

        // Set selection modes
        rightTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        leftTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

// Mouse hover in rightTable
        rightTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = rightTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    selectGroupInRightTable(row);
                } else {
                    rightTable.clearSelection();
                    leftTable.clearSelection();
                }
            }
        });

// Mouse hover in leftTable
        leftTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = leftTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    leftTable.getSelectionModel().setSelectionInterval(row, row);
                    int groupStart = row * 5;
                    int groupEnd = groupStart + 4;
                    rightTable.getSelectionModel().setSelectionInterval(groupStart, groupEnd);
                } else {
                    leftTable.clearSelection();
                    rightTable.clearSelection();
                }
            }
        });

// Selection listener for clicks in rightTable
        rightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = rightTable.getSelectedRow();
                if (row != -1) {
                    selectGroupInRightTable(row);
                }
            }
        });


        JScrollPane leftTableScrollPane = new JScrollPane(leftTable);
        leftTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        leftTableScrollPane.add(leftTable.getTableHeader());

        leftTableScrollPane.setPreferredSize(new Dimension(0, 0)); // Let layout manager decide size
        rightTableScrollPane.setPreferredSize(new Dimension(0, 0));

        JPanel rightTablePanel = new JPanel(new BorderLayout());
        rightTablePanel.add(rightTableScrollPane, BorderLayout.CENTER);

        JPanel leftTablePanel = new JPanel(new BorderLayout());
        leftTablePanel.add(leftTableScrollPane, BorderLayout.CENTER);

        leftTable.getTableHeader().setReorderingAllowed(false);
        rightTable.getTableHeader().setReorderingAllowed(false);

        // Synchronize vertical scrolling between rightTable and leftTable
        leftTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        JScrollBar rightVertical = rightTableScrollPane.getVerticalScrollBar();
        JScrollBar leftVertical = leftTableScrollPane.getVerticalScrollBar();

        int rightRowHeight = rightTable.getRowHeight();
        int leftRowHeight = leftTable.getRowHeight();

        rightVertical.addAdjustmentListener(e -> {
            leftVertical.setValue(rightVertical.getValue());
        });


        leftVertical.addAdjustmentListener(e -> {
            rightVertical.setValue(leftVertical.getValue());
        });



        // Remove the up/down arrow buttons from the vertical scrollbar
        rightTableScrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
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

//        JPanel tablePanel = new JPanel();
//        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
//        tablePanel.add(leftTablePanel);
//        tablePanel.add(rightTablePanel);


        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(1, 2)); // Change to GridLayout for equal sizing
        tablePanel.add(leftTablePanel);
        tablePanel.add(rightTablePanel);

        // Dropdown
        BlackRoundedComboBox<String> year = new BlackRoundedComboBox<>(new String[] {
                "2023", "2024", "2025"
        }) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };
        year.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                year.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                year.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        year.setPreferredSize(new Dimension(100, 36));
        year.setFocusable(false);
        year.setMaximumRowCount(12);
        year.setFont(new Font("Arial", Font.BOLD, 16));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) year.getRenderer();
        renderer.setFont(new Font("Arial", Font.BOLD, 16));
        ((JLabel)year.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Custom renderer for hover effect in dropdown list
        year.setRenderer(new DefaultListCellRenderer() {
            private int hoveredIndex = -1;
            {
                // Add mouse motion listener to popup list for hover effect
                year.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
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
                        ComboPopup popup = (ComboPopup) year.getUI().getAccessibleChild(year, 0);
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

        // Use GridBagLayout for vertical centering
        JPanel comboPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCombo = new GridBagConstraints();
        gbcCombo.gridx = 0;
        gbcCombo.gridy = 0;
        gbcCombo.anchor = GridBagConstraints.CENTER;
        JLabel yearLabel = new JLabel("Year: ");
        yearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        yearLabel.setForeground(Color.WHITE);
        yearLabel.setOpaque(false);
        yearLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
        comboPanel.add(yearLabel, gbcCombo);

        gbcCombo.gridx = 1;
        comboPanel.add(year, gbcCombo);
        comboPanel.setOpaque(true);
        comboPanel.setBackground(Color.BLACK);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(comboPanel, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 16));

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }
}

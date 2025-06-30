package Screens;

import Components.RoundedButton;
import Components.TableStyler;
import org.payroll.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class UserControl extends JPanel {
    private  JTextField searchField;
    private JTable userTable;
    private DefaultTableModel tableModel;

    private void addSampleData() {
        String[] names = {"John Smith",
                "Emma Davis", "Michael Chen",
                "Sarah Wilson", "David Brown",
                "Lisa Anderson", "James Taylor",
                "Maria Garcia", "Robert Lee",
                "Amanda White", "Taylor Swift",
                "Emma Davis", "Michael Chen",
                "Sarah Wilson", "David Brown",
                "Lisa Anderson", "James Taylor",
                "Maria Garcia", "Robert Lee",
                "Amanda White", "Taylor Swift",
                "Emma Davis", "Michael Chen",
                "Sarah Wilson", "David Brown",
                "Lisa Anderson", "James Taylor",
                "Maria Garcia", "Robert Lee",
                "Amanda White", "Taylor Swift",
                "Emma Davis", "Michael Chen",
                "Sarah Wilson", "David Brown",
                "Lisa Anderson", "James Taylor",
                "Maria Garcia", "Robert Lee",
                "Amanda White", "Taylor Swift"};
        String[] accessLevels = {"Admin", "Human Resources", "Accounting"};
        String[] statuses = {"Active", "Pending"};

        for (int i = 0; i < 41; i++) {
            String name = names[i];
            String userId = "UID" + String.format("%04d", (int)(Math.random() * 10000));
            String email = name.toLowerCase().replace(" ", ".") + "@company.com";
            String accessLevel = accessLevels[(int)(Math.random() * accessLevels.length)];
            String creationDate = String.format("%02d/%02d/2023",
                    (int)(Math.random() * 12) + 1,
                    (int)(Math.random() * 28) + 1);
            String status = statuses[(int)(Math.random() * statuses.length)];

            tableModel.addRow(new Object[]{name, userId, email, accessLevel, creationDate, status, "Edit"});
        }
    }

    public UserControl() {
        setLayout(new BorderLayout());


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(10, 0));
        searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 60));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

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

        searchPanel.add(searchField, BorderLayout.CENTER);

        String[] columnNames = {"Name", "User ID", "Email", "Access Level", "Creation Date", "Account Status", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only Action column is editable
            }
        };

        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.getTableHeader().setReorderingAllowed(false);
        TableStyler.styleTable(userTable);

        class ButtonRenderer implements TableCellRenderer {
            private final JPanel panel;
            private final RoundedButton button;

            public ButtonRenderer() {
                panel = new JPanel(new GridBagLayout());
                panel.setOpaque(false);

                button = new RoundedButton("Approve", 20) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        setModel(getModel()); // Ensure model state is current
                        super.paintComponent(g);
                    }
                };
                button.setOpaque(false);
                button.setBackground(MainWindow.activeColor);
                button.setForeground(Color.WHITE);
                button.setBorderPainted(false);
                button.setPreferredSize(new Dimension(100, 35));
                button.setFont(new Font("Arial", Font.PLAIN, 16));
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.weightx = 1;
                gbc.weighty = 1;
                panel.add(button, gbc);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                // Update button model state based on table row hover
                int mouseRow = table.getRowCount() > 0 ? table.getSelectedRow() : -1;
                button.getModel().setRollover(mouseRow == row);
                return panel;
            }
        }

        class ButtonEditor extends DefaultCellEditor {
            private final JPanel panel;
            private final RoundedButton button;
            private boolean isPushed;

            public ButtonEditor() {
                super(new JCheckBox());
                panel = new JPanel(new GridBagLayout());
                panel.setOpaque(false);

                button = new RoundedButton("Approve", 20);
                button.setOpaque(false);
                button.setBackground(MainWindow.activeColor);
                button.setForeground(Color.WHITE);
                button.setBorderPainted(false);
                button.setPreferredSize(new Dimension(100, 35));
                button.setFont(new Font("Arial", Font.PLAIN, 16));
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);

                button.addActionListener(e -> fireEditingStopped());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.weightx = 1;
                gbc.weighty = 1;
                panel.add(button, gbc);
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                isPushed = true;
                button.getModel().setRollover(true);
                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                isPushed = false;
                button.getModel().setRollover(false);
                return "Approve";
            }

            @Override
            public boolean stopCellEditing() {
                isPushed = false;
                button.getModel().setRollover(false);
                return super.stopCellEditing();
            }
        }

        // Set column widths
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        userTable.getColumnModel().getColumn(0).setPreferredWidth(180); // Name
        userTable.getColumnModel().getColumn(1).setPreferredWidth(70); // User ID
        userTable.getColumnModel().getColumn(2).setPreferredWidth(230); // Email
        userTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Access Level
        userTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Creation Date
        userTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Account Status
        userTable.getColumnModel().getColumn(6).setPreferredWidth(60);
        userTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());// Action

        userTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = userTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    userTable.setRowSelectionInterval(row, row);
                } else {
                    userTable.clearSelection();
                }
            }
        });

        userTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                if (userTable.rowAtPoint(p) >= 0) {
                    userTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    userTable.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(MainWindow.activeColor);
        add(mainPanel, BorderLayout.CENTER);
        addSampleData();
    }
}
package Screens;

import Components.RoundedComboBox;
import org.payroll.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reports extends javax.swing.JPanel {

    private AttendanceReport attendanceReport;
    private LeaveReport leaveReport;

    public Reports() {
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
        sortCombo.setPreferredSize(new Dimension(250, 50));
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

        attendanceReport = new AttendanceReport();
        leaveReport = new LeaveReport();

        sortCombo.addActionListener(e -> {
            String selectedDepartment = (String) sortCombo.getSelectedItem();
            attendanceReport.updateDepartmentLabel(selectedDepartment);
            leaveReport.updateDepartmentLabel(selectedDepartment);
        });

        // Add hover effect (like LeaveManagement)
        Color comboDefaultBg = Color.WHITE;
        Color comboHoverBg = new Color(230, 255, 230); // light greenish
        sortCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (sortCombo.isEnabled()) {
                    sortCombo.setBackground(comboHoverBg);
                    sortCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                sortCombo.setBackground(comboDefaultBg);
                sortCombo.setCursor(Cursor.getDefaultCursor());
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



        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        PayslipScreen payslip = new PayslipScreen();
        ChangeLog changeLog = new ChangeLog();

        // Map for button names and panels
        Map<String, JPanel> panelMap = new LinkedHashMap<>();
        panelMap.put("Attendance Report", attendanceReport);
        panelMap.put("Leave Report", leaveReport);
        panelMap.put("Payslip", payslip);
        panelMap.put("Change Log", changeLog);

// Add panels to cardPanel
        for (Map.Entry<String, JPanel> entry : panelMap.entrySet()) {
            cardPanel.add(entry.getValue(), entry.getKey());
        }

        // Custom button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        Color activeColor = new Color(0, 158, 0);
        Color defaultColor = UIManager.getColor("Button.background");

        // Store buttons for color management
        Map<String, JButton> buttonMap = new LinkedHashMap<>();

        for (String name : panelMap.keySet()) {
            JButton btn = new JButton(name);
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setFocusPainted(false);
//                    btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, 35));
//                    btn.setMaximumSize(new Dimension(btn.getPreferredSize().width, 35));
            btn.setPreferredSize(new Dimension(0, 45));
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setBackground(new Color(217, 217, 217));
            btn.setBorderPainted(false); // Hide border, keep padding
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
            buttonMap.put(name, btn);

            btn.addActionListener(e -> {
                cardLayout.show(cardPanel, name);
                // Set active color for selected, default for others
                for (Map.Entry<String, JButton> entry : buttonMap.entrySet()) {
                    if (entry.getKey().equals(name)) {
                        entry.getValue().setBackground(activeColor);
                        entry.getValue().setForeground(Color.WHITE);
                        entry.getValue().setFont(new Font("Arial", Font.BOLD, 16));
                    } else {
                        entry.getValue().setBackground(new Color(217, 217, 217));
                        entry.getValue().setForeground(Color.BLACK);
                        entry.getValue().setFont(new Font("Arial", Font.PLAIN, 16));
                    }
                }
            });
        }
        // Set the first button as active
        if (!buttonMap.isEmpty()) {
            JButton firstBtn = buttonMap.values().iterator().next();
            firstBtn.setBackground(activeColor);
            firstBtn.setForeground(Color.WHITE);
            firstBtn.setFont(new Font("Arial", Font.BOLD, 16));
        }

        // Layout
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        searchPanel.setOpaque(false);
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        setBackground(MainWindow.activeColor);
//        setOpaque(false);
        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

    }
    
}

package Screens;

import Components.BlackRoundedComboBox;
import Components.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import Components.PayslipGenerator;
import org.payroll.MainWindow;

public class Payslip extends JPanel {
    public Payslip() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Dropdown
        BlackRoundedComboBox<String> sortCombo = new BlackRoundedComboBox<>(new String[] {
                "Human Resource", "Administration", "Accounting", "Sales",  "Production", "Production (Pre-Press)", "Production (Press)", "Production (Post-Press)", "Production (Quality Control)"
        }) {
            @Override
            protected void paintBorder(Graphics g) {
                // Do nothing: no border for this instance
            }
        };
        sortCombo.setPreferredSize(new Dimension(250, 50));
        sortCombo.setFocusable(false);
        sortCombo.setMaximumRowCount(12);
        sortCombo.setFont(new Font("Arial", Font.BOLD, 16));
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

//        // Add hover effect (like LeaveManagement)
//        Color comboDefaultBg = Color.WHITE;
//        Color comboHoverBg = new Color(230, 255, 230); // light greenish
//        sortCombo.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseEntered(java.awt.event.MouseEvent e) {
//                if (sortCombo.isEnabled()) {
//                    sortCombo.setBackground(comboHoverBg);
//                }
//            }
//            @Override
//            public void mouseExited(java.awt.event.MouseEvent e) {
//                sortCombo.setBackground(comboDefaultBg);
//            }
//        });

        // Use GridBagLayout for vertical centering
        JPanel comboPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCombo = new GridBagConstraints();
        gbcCombo.gridx = 0;
        gbcCombo.gridy = 0;
        gbcCombo.anchor = GridBagConstraints.CENTER;
        JLabel sortLabel = new JLabel("Department: ");
        sortLabel.setForeground(Color.WHITE);
        sortLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sortLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
        comboPanel.add(sortLabel, gbcCombo);

        gbcCombo.gridx = 1;
        comboPanel.add(sortCombo, gbcCombo);
        comboPanel.setOpaque(true);
        comboPanel.setBackground(Color.BLACK);

        RoundedButton downloadPdf = new RoundedButton("Download PDF", 10);
        downloadPdf.setPreferredSize(new Dimension(150, 50));
        downloadPdf.setFont(new Font("Arial", Font.BOLD, 16));
        downloadPdf.setForeground(Color.WHITE);
        downloadPdf.setBackground(new Color(0, 0, 0));
        downloadPdf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        downloadPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(new Color(40, 40, 40)); // Slightly lighter black
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(Color.BLACK);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(new Color(100, 100, 100)); // Even lighter on click
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                downloadPdf.setBackground(downloadPdf.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

        downloadPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "payslip.pdf";
                PayslipGenerator.generatePayslip(filePath);
                // Option 1: Show relative to the panel
                JOptionPane.showMessageDialog(Payslip.this, "Payslip generated: " + filePath);

                // Option 2: Use `null` to center on screen
                // JOptionPane.showMessageDialog(null, "Payslip generated: " + filePath);
            }
        });

        JButton viewButton = new JButton("View PDF");
        viewButton.addActionListener(e -> PayslipGenerator.previewPayslip());
        viewButton.setPreferredSize(new Dimension(150, 50));
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(0, 0, 0));
        viewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                viewButton.setBackground(new Color(40, 40, 40)); // Slightly lighter black
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                viewButton.setBackground(Color.BLACK);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                viewButton.setBackground(new Color(100, 100, 100)); // Even lighter on click
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                viewButton.setBackground(viewButton.getBounds().contains(e.getPoint()) ? new Color(30, 30, 30) : Color.BLACK);
            }
        });

//        JPanel topPanel = new JPanel(new BorderLayout());
//        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
//        topPanel.add(comboPanel, BorderLayout.WEST);
//        topPanel.add(viewButton, BorderLayout.CENTER);
//        topPanel.add(downloadPdf, BorderLayout.EAST);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(comboPanel);
        topPanel.add(Box.createHorizontalStrut(20)); // space between buttons
        topPanel.add(viewButton);
        topPanel.add(Box.createHorizontalStrut(20)); // space between buttons
        topPanel.add(downloadPdf);
        topPanel.add(Box.createHorizontalGlue());

        add(topPanel, BorderLayout.NORTH);
    }
}

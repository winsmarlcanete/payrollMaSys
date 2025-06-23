package Components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class RoundedComboBox<E> extends JComboBox<E> {
    private int arc = 20;

    public RoundedComboBox(E[] items) {
        super(items);
        setOpaque(false);
        setBackground(Color.WHITE);
        setUI(new RoundedComboBoxUI());

        setEditable(true);
        setEditor(new BasicComboBoxEditor() {
            private final RoundedTextField editor = new RoundedTextField(10);
            {
                editor.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
                editor.setOpaque(false);
                editor.setBackground(Color.WHITE);
            }
            @Override
            public Component getEditorComponent() {
                return editor;
            }
            @Override
            public void setItem(Object anObject) {
                editor.setText(anObject == null ? "" : anObject.toString());
            }
            @Override
            public Object getItem() {
                return editor.getText();
            }
        });
        setEditable(false);
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(false); // Make renderer transparent
                label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0)); // Optional: padding to match editor
                return label;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
    }

    private class RoundedComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Transparent background
//                    g2.setComposite(AlphaComposite.Clear);
                    g2.setColor(new Color(0, 0, 0, 0));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setComposite(AlphaComposite.SrcOver);

                    // Draw arrow
                    int size = Math.min(getWidth(), getHeight()) / 3;
                    int x = (getWidth() - size) / 2;
                    int y = (getHeight() - size) / 2 + 2;
                    g2.setColor(Color.DARK_GRAY);
                    int[] xPoints = { x, x + size, x + size / 2 };
                    int[] yPoints = { y, y, y + size / 2 };
                    g2.fillPolygon(xPoints, yPoints, 3);
                    g2.dispose();
                }
            };
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            return button;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Do nothing, background is painted by paintComponent
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            // Paint the whole combo box background with rounded corners
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground());
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arc, arc);
            g2.dispose();
            super.paint(g, c);
        }
    }
}
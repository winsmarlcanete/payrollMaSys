package Components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class BlackRoundedComboBox<E> extends JComboBox<E> {
    private int arc = 10;

    public BlackRoundedComboBox(E[] items) {
        super(items);
        setOpaque(false);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setUI(new BlackRoundedComboBoxUI());

        setEditable(true);
        setEditor(new BasicComboBoxEditor() {
            private final RoundedTextField editor = new RoundedTextField(10);
            {
                editor.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
                editor.setOpaque(false);
                editor.setBackground(Color.BLACK);
                editor.setForeground(Color.WHITE);
                editor.setCaretColor(Color.WHITE);
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

                if (isSelected) {
                    label.setBackground(Color.DARK_GRAY); // Or remain black if preferred
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.BLACK);
                    label.setForeground(Color.WHITE);
                }

                label.setOpaque(true); // Set to true to apply background color
                label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
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

    private class BlackRoundedComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 0));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setComposite(AlphaComposite.SrcOver);

                    // Draw white arrow
                    int size = Math.min(getWidth(), getHeight()) / 3;
                    int x = (getWidth() - size) / 2;
                    int y = (getHeight() - size) / 2 + 2;
                    g2.setColor(Color.WHITE);
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
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            Object value = comboBox.getSelectedItem();
            if (value != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = comboBox.getBackground();
                Color fg = comboBox.getForeground();

                // 🔍 Special case for payrollPeriod (force black bg, white text)
                if ("payrollPeriod".equals(comboBox.getName())) {
                    bg = Color.BLACK;
                    fg = Color.WHITE;
                }

                g2.setColor(bg);
                g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 0, 0);

                g2.setColor(fg);
                g2.setFont(comboBox.getFont());

                FontMetrics fm = g2.getFontMetrics();
                String text = value.toString();
                int textY = bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent();

                g2.drawString(text, bounds.x + 10, textY);
                g2.dispose();
            }
        }



        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground());
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arc, arc);
            g2.dispose();
            super.paint(g, c);
        }
    }
}

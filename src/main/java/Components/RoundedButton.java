package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private final int arc;

    public RoundedButton(String text, int arc) {
        super(text);
        this.arc = arc;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Transparent background
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, width, height);

        // Rounded rectangle shape
        Shape roundedRect = new RoundRectangle2D.Float(0, 0, width, height, arc, arc);

        // State-dependent background color
        Color bg = getBackground();
        if (getModel().isPressed()) {
            bg = bg.darker();
        } else if (getModel().isRollover()) {
            bg = bg.brighter();
        }

        g2.setColor(bg);
        g2.fill(roundedRect);

        // ✅ Let JButton draw the label (text/icon)
        super.paintComponent(g2);

        g2.dispose();
    }


    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }
}
package Components; // Ensure this matches your package structure

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedPasswordField extends JPasswordField {
    private int cornerRadius = 15; // You can adjust this value

    public RoundedPasswordField(int columns) {
        super(columns);
        setOpaque(false); // Crucial for custom painting
        // Add some padding inside the text field
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground()); // Use component's background color
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        super.paintComponent(g2); // Paint children (text/echo chars)
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY); // Border color
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.dispose();
    }

    // Override setBorder to prevent default Swing borders from overriding your custom one
    @Override
    public void setBorder(javax.swing.border.Border border) {
        // Allow only EmptyBorder for padding, discard others to maintain custom look
        if (border instanceof EmptyBorder) {
            super.setBorder(border);
        }
    }
}
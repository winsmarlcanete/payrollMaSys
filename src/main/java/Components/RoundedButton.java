package Components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder; // Needed for type checking
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.BasicStroke;

public class RoundedButton extends JButton {
    private final int arc;
    private Border customBorder; // Stores the actual Border object for its insets etc.
    private Color borderColor = Color.BLACK; // Default border color
    private int borderWidth = 1; // Default border width
    private boolean drawCustomBorder = false; // <<< NEW FLAG: Controls if custom border is drawn

    public RoundedButton(String text, int arc) {
        super(text);
        this.arc = arc;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // Set a default, simple border to ensure `super.setBorder()` is called
        // for correct insets. This border won't be drawn by super.paintBorder,
        // and our custom paintBorder will respect `drawCustomBorder` flag.
        super.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Minimal insets
    }

    // --- New Setter to control border visibility ---
    public void setDrawCustomBorder(boolean drawCustomBorder) {
        this.drawCustomBorder = drawCustomBorder;
        repaint(); // Repaint to reflect the change
    }

    // --- Existing Setters for Border Properties ---
    public void setBorderColor(Color color) {
        this.borderColor = color;
        // Automatically enable custom border drawing if color is set
        this.drawCustomBorder = true;
        repaint();
    }

    public void setBorderWidth(int width) {
        if (width < 0) throw new IllegalArgumentException("Border width cannot be negative.");
        this.borderWidth = width;
        // Automatically enable custom border drawing if width is set
        this.drawCustomBorder = true;
        // Adjust default insets if no specific customBorder is set.
        // This helps maintain correct text positioning even without a visible border.
        if (customBorder == null) {
            super.setBorder(BorderFactory.createEmptyBorder(width, width, width, width));
        }
        repaint();
    }

    @Override
    public void setBorder(Border border) {
        this.customBorder = border; // Store the border object
        // Call super.setBorder() to update the button's internal insets correctly.
        // This is crucial for text/icon positioning relative to the border.
        super.setBorder(border);

        // Attempt to extract color and thickness from common border types
        // and automatically enable drawing the custom border
        if (border instanceof LineBorder) {
            LineBorder lb = (LineBorder) border;
            this.borderColor = lb.getLineColor();
            this.borderWidth = lb.getThickness();
            this.drawCustomBorder = true; // Enable drawing if a specific border is set
        } else if (border instanceof CompoundBorder) {
            // For compound, you might need more complex logic to get properties
            // For now, setting drawCustomBorder to true is enough for it to try drawing.
            this.drawCustomBorder = true;
            // You might want to inspect the outer border of the CompoundBorder
            // to get its properties, e.g.:
            Border outer = ((CompoundBorder) border).getOutsideBorder();
            if (outer instanceof LineBorder) {
                LineBorder lb = (LineBorder) outer;
                this.borderColor = lb.getLineColor();
                this.borderWidth = lb.getThickness();
            }
        } else if (border instanceof EmptyBorder) {
            // If an EmptyBorder is explicitly set, assume no visible border
            this.drawCustomBorder = false;
        } else {
            // For other border types (e.g., EtchedBorder, BevelBorder),
            // you might choose to draw your custom rounded border, or
            // let setBorder(true) decide. Defaulting to true here.
            this.drawCustomBorder = true;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Transparent background for the component area
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, width, height);

        // Shape for filling the button (slightly smaller to make space for the border stroke)
        // Only adjust if custom border is actually drawn
        int fillX = 0;
        int fillY = 0;
        int fillWidth = width;
        int fillHeight = height;

        if (drawCustomBorder) {
            fillX = borderWidth / 2;
            fillY = borderWidth / 2;
            fillWidth = width - borderWidth;
            fillHeight = height - borderWidth;
        }

        Shape roundedRectFill = new RoundRectangle2D.Float(fillX, fillY, fillWidth, fillHeight, arc, arc);

        // State-dependent background color
        Color bg = getBackground();
        if (getModel().isPressed()) {
            bg = bg.darker();
        } else if (getModel().isRollover()) {
            bg = bg.brighter();
        }

        g2.setColor(bg);
        g2.fill(roundedRectFill); // Fill the (potentially smaller) rounded rectangle

        // ✅ Let JButton draw the label (text/icon)
        // Super.paintComponent draws the text/icon.
        // It uses the button's insets, which are correctly updated by super.setBorder()
        super.paintComponent(g2);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Only paint the custom rounded border if the flag is true
        if (drawCustomBorder) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Calculate the rectangle for the border stroke.
            int strokeOffset = borderWidth / 2;
            Shape roundedRectBorder = new RoundRectangle2D.Float(strokeOffset, strokeOffset,
                    width - borderWidth, height - borderWidth,
                    arc, arc);

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth));

            // Draw the border path
            g2.draw(roundedRectBorder);

            g2.dispose();
        }
        // Do NOT call super.paintBorder(g) here.
    }
}
package appcode.form;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.RoundRectangle2D;

public class CustomTextArea extends JTextArea {

    private int borderRadius = 15;
    private String placeholder = "";
    private Color placeholderColor = new Color(204, 204, 204);

    public CustomTextArea() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setCaretColor(Color.WHITE);
        setLineWrap(true);
        setWrapStyleWord(true);
        
        // Important to prevent white edges
        setBackground(new Color(255, 255, 255));
        
        // Set viewport to non-opaque for JScrollPane
        setUI(new javax.swing.plaf.basic.BasicTextAreaUI());
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public Color getPlaceholderColor() {
        return placeholderColor;
    }

    public void setPlaceholderColor(Color placeholderColor) {
        this.placeholderColor = placeholderColor;
        repaint();
    }
    
    /**
     * Override to ensure proper behavior when this component is used
     * within a JScrollPane.
     */
    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Create a shape object that represents the rounded rectangle
        Shape roundedRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        
        // Create a copy of the graphics context
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Set clip to rounded rectangle to prevent painting outside
        g2.setClip(roundedRect);
        
        // Paint background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        
        // We use this approach to ensure text is properly rendered within the rounded rectangle
        super.paintComponent(g2);
        
        // Placeholder
        if (getText().isEmpty() && !placeholder.isEmpty()) {
            g2.setColor(placeholderColor);
            FontMetrics fm = g2.getFontMetrics();
            int y = getInsets().top + fm.getAscent();
            int x = getInsets().left;

            // Set font same as text area
            g2.setFont(getFont());
            g2.drawString(placeholder, x, y);
        }
        
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isFocusOwner()) {
            g2.setColor(new Color(4, 88, 167));
        } else {
            g2.setColor(new Color(142, 142, 142));
        }

        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        g2.dispose();
    }
}
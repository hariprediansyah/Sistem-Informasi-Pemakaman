package appcode.form;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.RoundRectangle2D;

public class CustomPasswordField extends JPasswordField {

    private Icon prefixIcon;
    private Icon suffixIcon;
    private int borderRadius = 15;
    private String placeholder = "";
    private Color placeholderColor = new Color(204, 204, 204);

    public CustomPasswordField() {
        setOpaque(false);
        initBorder();
        setCaretColor(Color.WHITE);
    }

    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder();
        repaint();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder();
        repaint();
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

    private void initBorder() {
        int left = 10;
        int right = 10;

        if (prefixIcon != null) {
            left = prefixIcon.getIconWidth() + 10;
        }
        if (suffixIcon != null) {
            right = suffixIcon.getIconWidth() + 10;
        }

        setBorder(BorderFactory.createEmptyBorder(8, left, 8, right));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

        // Text & caret
        super.paintComponent(g2);

        // Placeholder
        if (getPassword().length == 0 && !placeholder.isEmpty()) {
            g2.setColor(placeholderColor);
            FontMetrics fm = g2.getFontMetrics();
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

            int x = getInsets().left;
            if (prefixIcon != null) {
                x = prefixIcon.getIconWidth() + 15;
            }

            g2.setFont(getFont());
            g2.drawString(placeholder, x, y);
        }

        paintIcon(g2);
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

    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) {
            Image prefix = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2;
            g2.drawImage(prefix, 5, y, this);
        }
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int x = getWidth() - suffixIcon.getIconWidth() - 5;
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            g2.drawImage(suffix, x, y, this);
        }
    }
}

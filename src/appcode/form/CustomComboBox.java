package appcode.form;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.geom.RoundRectangle2D;

public class CustomComboBox<E> extends JComboBox<E> {

    private int borderRadius = 15;
    private Icon suffixIcon;
    private Color arrowColor = new Color(142, 142, 142);

    public CustomComboBox() {
        setOpaque(false);
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        setUI(new CustomComboBoxUI());
        
        // For better appearance with rounded corners
        setFocusable(true);
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        repaint();
    }

    public Color getArrowColor() {
        return arrowColor;
    }

    public void setArrowColor(Color arrowColor) {
        this.arrowColor = arrowColor;
        repaint();
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        return shape.contains(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a shape object that represents the rounded rectangle
        Shape roundedRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        
        // Set clip to rounded rectangle to prevent painting outside
        g2.setClip(roundedRect);
        
        // Paint background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        
        // Call parent's paintComponent but without background
        super.paintComponent(g2);
        
        // Paint custom arrow or icon if provided
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int x = getWidth() - suffixIcon.getIconWidth() - 10;
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            g2.drawImage(suffix, x, y, this);
        } else {
            // Draw a custom arrow
            int arrowSize = 8;
            int x = getWidth() - arrowSize - 10;
            int y = getHeight() / 2 - 1;
            
            g2.setColor(arrowColor);
            g2.setStroke(new BasicStroke(1.5f));
            
            // Draw downward arrow
            int[] xPoints = {x - arrowSize/2, x, x + arrowSize/2};
            int[] yPoints = {y - arrowSize/2, y + arrowSize/2, y - arrowSize/2};
            g2.fillPolygon(xPoints, yPoints, 3);
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

    // Custom UI for the ComboBox
    private class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected ComboPopup createPopup() {
            return new CustomComboPopup(comboBox);
        }

        @Override
        protected JButton createArrowButton() {
            // Create an invisible arrow button (we'll draw our own)
            JButton button = new JButton() {
                @Override
                public void paint(Graphics g) {
                    // Don't paint the default button
                }
            };
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setOpaque(false);
            return button;
        }
    }

    // Custom popup for rounded corners
    private class CustomComboPopup extends BasicComboPopup {
        public CustomComboPopup(JComboBox<Object> combo) {
            super(combo);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected JScrollPane createScroller() {
            JScrollPane scroller = new JScrollPane(list,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scroller.setOpaque(false);
            scroller.getViewport().setOpaque(false);
            
            // Create a custom rounded border for the popup
            scroller.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return scroller;
        }

        @Override
        protected void configureList() {
            super.configureList();
            list.setOpaque(false);
            list.setCellRenderer(new CustomListCellRenderer());
        }
    }

    // Custom renderer for list items in the dropdown
    private class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setBorder(new EmptyBorder(5, 10, 5, 10));
                
                if (isSelected) {
                    label.setBackground(new Color(4, 88, 167, 200));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(255, 255, 255));
                    label.setForeground(Color.BLACK);
                }
            }
            
            return c;
        }
    }
}
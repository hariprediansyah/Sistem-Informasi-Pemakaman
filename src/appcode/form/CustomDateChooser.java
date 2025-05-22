package appcode.form;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class CustomDateChooser extends JDateChooser {
    private int borderRadius = 15;
    private String placeholder = "Pilih tanggal...";
    private Color placeholderColor = new Color(204, 204, 204);
    private boolean hasFocus = false;
    
    public CustomDateChooser() {
        super();
        initializeStyle();
    }
    
    public CustomDateChooser(java.util.Date date) {
        super(date);
        initializeStyle();
    }
    
    private void initializeStyle() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(255, 255, 255));
        
        // Delay customization to ensure components are ready
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                customizeComponents();
            }
        });
    }
    
    private void customizeComponents() {
        try {
            // Wait a bit more and try again if components aren't ready
            if (getDateEditor() == null || getDateEditor().getUiComponent() == null) {
                Timer retryTimer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (getDateEditor() != null && getDateEditor().getUiComponent() != null) {
                                setupTextFieldStyle();
                                setupCalendarButtonStyle();
                                ((Timer)e.getSource()).stop();
                            }
                        } catch (Exception ex) {
                            // Ignore and stop trying
                            ((Timer)e.getSource()).stop();
                        }
                    }
                });
                retryTimer.start();
                return;
            }
            
            setupTextFieldStyle();
            setupCalendarButtonStyle();
            
        } catch (Exception e) {
            // Ignore exceptions during initialization
        }
    }
    
    private void setupTextFieldStyle() {
        try {
            Component uiComponent = getDateEditor().getUiComponent();
            if (uiComponent instanceof JTextField) {
                JTextField dateField = (JTextField) uiComponent;
                dateField.setOpaque(false);
                dateField.setBorder(BorderFactory.createEmptyBorder());
                dateField.setCaretColor(Color.WHITE);
                dateField.setBackground(new Color(255, 255, 255));
                dateField.setForeground(Color.WHITE);
                dateField.setDisabledTextColor(Color.WHITE);
                
                // Add focus listeners
                dateField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        hasFocus = true;
                        repaint();
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        hasFocus = false;
                        repaint();
                    }
                });
            }
        } catch (Exception e) {
            // Ignore
        }
    }
    
    private void setupCalendarButtonStyle() {
        try {
            JButton calendarButton = getCalendarButton();
            if (calendarButton != null) {
                calendarButton.setOpaque(false);
                calendarButton.setBorder(BorderFactory.createEmptyBorder());
                calendarButton.setContentAreaFilled(false);
                calendarButton.setFocusPainted(false);
                
                calendarButton.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        hasFocus = true;
                        repaint();
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        hasFocus = false;
                        repaint();
                    }
                });
            }
        } catch (Exception e) {
            // Ignore
        }
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
    
    @Override
public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);

    if (getDateEditor() != null && getDateEditor().getUiComponent() instanceof JTextField) {
        JTextField dateField = (JTextField) getDateEditor().getUiComponent();

        if (enabled) {
            dateField.setForeground(Color.WHITE);
            // agar teks caret juga putih
            dateField.setCaretColor(Color.WHITE);
        } else {
            dateField.setDisabledTextColor(Color.WHITE);
        }
    }

    repaint();
}


    
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
        
        // Paint the component
        super.paintComponent(g2);
        
        // Handle placeholder text
        try {
            if (getDateEditor() != null) {
                Component uiComponent = getDateEditor().getUiComponent();
                if (uiComponent instanceof JTextField) {
                    JTextField dateField = (JTextField) uiComponent;
                    if (dateField.getText() == null || dateField.getText().isEmpty()) {
                        if (!placeholder.isEmpty()) {
                            g2.setColor(placeholderColor);
                            FontMetrics fm = g2.getFontMetrics();
                            int y = getInsets().top + fm.getAscent() + 2;
                            int x = getInsets().left;
                            g2.setFont(getFont());
                            g2.drawString(placeholder, x, y);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Handle any potential exceptions
        }
        
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (hasFocus) {
            g2.setColor(new Color(4, 88, 167));
        } else {
            g2.setColor(new Color(142, 142, 142));
        }
        
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        g2.dispose();
    }
}
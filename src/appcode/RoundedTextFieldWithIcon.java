package appcode;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedTextFieldWithIcon extends JPanel {
    private JTextField textField;
    private JLabel iconLabelLeft;
    private JLabel iconLabelRight;
    private String placeholder;

    public RoundedTextFieldWithIcon(String placeholder, Icon leftIcon, Icon rightIcon, boolean isPassword) {
        this.placeholder = placeholder;

        setLayout(new BorderLayout(8, 0));
        setBackground(new Color(65, 65, 65));
        setBorder(new RoundBorder(15));

        // Ikon kiri
        iconLabelLeft = new JLabel(leftIcon);
        iconLabelLeft.setPreferredSize(new Dimension(30, 30));
        add(iconLabelLeft, BorderLayout.WEST);

        // Text field
        if (isPassword) {
            textField = new JPasswordField(placeholder);
        } else {
            textField = new JTextField(placeholder);
        }
        textField.setForeground(Color.LIGHT_GRAY);
        textField.setBorder(null);
        textField.setBackground(new Color(65, 65, 65));
        add(textField, BorderLayout.CENTER);

        // Placeholder behavior
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        // Ikon kanan (opsional)
        if (rightIcon != null) {
            iconLabelRight = new JLabel(rightIcon);
            iconLabelRight.setPreferredSize(new Dimension(30, 30));
            add(iconLabelRight, BorderLayout.EAST);
        }
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    // Untuk password field
    public JPasswordField getPasswordField() {
        if (textField instanceof JPasswordField) {
            return (JPasswordField) textField;
        }
        return null;
    }
}

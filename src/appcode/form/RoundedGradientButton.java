/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appcode.form;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author MyBook Z Series
 */
public class RoundedGradientButton extends JButton {
    public RoundedGradientButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.BLACK);
        setFont(new Font("SansSerif", Font.BOLD, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient dari kiri ke kanan
        GradientPaint gp = new GradientPaint(0, 0, new Color(255, 214, 165), getWidth(), 0, new Color(168, 208, 213));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // optional: hilangkan border default
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
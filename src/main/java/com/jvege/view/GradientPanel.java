package com.jvege.view;

import com.jvege.util.ResourceBundleUtil;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Construct Gradient JPanel
 * @author Dickson
 */
public class GradientPanel extends JPanel {

    private Color startColor;
    private Color endColor;
    private String direction;

    /**
     * Setup GradientPanel
     * @param startColor
     * @param endColor
     * @param direction 
     */
    public GradientPanel(String startColor, String endColor, String direction) {
        this.startColor = Color.decode(ResourceBundleUtil.getText(startColor));
        this.endColor = Color.decode(ResourceBundleUtil.getText(endColor));
        this.direction = direction;
    }

    public GradientPanel() {
        this.startColor = Color.decode(ResourceBundleUtil.getText("gradient.panel.default.start"));
        this.endColor = Color.decode(ResourceBundleUtil.getText("gradient.panel.default.end")) ;
        this.direction = "V";
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradientPaint;
        if (direction.equals("V") || direction.equals("Vertical")) {
            gradientPaint = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
        } else {
            gradientPaint = new GradientPaint(0, 0, startColor, getWidth(), 0, endColor);
        }
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

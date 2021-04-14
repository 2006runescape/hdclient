package com.runescape;

import java.awt.*;

import javax.swing.JFrame;

public final class RSFrame extends JFrame {

    private final RSApplet applet;

    RSFrame(RSApplet applet, int width, int height) {
        this.applet = applet;
        setTitle("Jagex");
        setResizable(false);
        setVisible(true);
        Insets insets = getInsets();
        setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        requestFocus();
        toFront();
    }

    @Override
    public Graphics getGraphics() {
        final Graphics g = super.getGraphics();
        Insets insets = getInsets();
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(insets != null ? insets.left : 0, insets != null ? insets.top : 0);
        return g;
    }

    @Override
    public void update(Graphics graphics) {
        applet.update(graphics);
    }

    @Override
    public void paint(Graphics graphics) {
        applet.paint(graphics);
    }
}
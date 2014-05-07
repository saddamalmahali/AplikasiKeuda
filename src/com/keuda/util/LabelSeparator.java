/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author client
 */
public class LabelSeparator extends JLabel{
    public static final int OFFSET = 15;

    public LabelSeparator() {
    }
    
    
    public LabelSeparator(String text) {
        super(text);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(), 20);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        g.setColor(getBackground());
        
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getSize();
        int y = (d.height-3)/2;
        g.setColor(Color.white);
        g.drawLine(1, y, d.width-1, y);
        y++;
        g.drawLine(0, y, 1, y);
        
        g.setColor(Color.gray);
        g.drawLine(d.width-1, y, d.width, y);
        y++;
        g.drawLine(1, y, d.width-1, y);
        String text = getText();
        
        if(text.length() == 0)
            return ;
        g.setFont(getFont());
        
        FontMetrics fm = g.getFontMetrics();
        y = (d.height + fm.getAscent())/2;
        int l = fm.stringWidth(text);
        g.setColor(getBackground());
        g.fillRect(OFFSET-5, 0, OFFSET+l, d.height);
        g.setColor(getForeground());
        g.drawString(text, OFFSET, y);
        
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author client
 */
public class BunxuButton extends JButton implements MouseListener{
    String k_tooltiptext = "";
    Icon k_icondefault, k_iconcross, k_iconpressed;
    
    protected int k_w = 7;
    protected int k_h = 7;
    protected Color k_topColor = Color.white;
    protected Color k_bottomColor = Color.gray;
    
    BevelBorder k_raised = new BevelBorder(BevelBorder.RAISED);
    BevelBorder k_lowered = new BevelBorder(BevelBorder.LOWERED);
    EmptyBorder k_inactive = new EmptyBorder(2, 2, 2, 2);
    ButtonModel bmodel;
    boolean k_isBorder = false;
    boolean k_fxDroped = false;

    public BunxuButton(String tooltipText, Icon icondefault, Icon iconcross, Icon iconpressed, boolean fxDropped) {
        k_tooltiptext = tooltipText;
        k_icondefault = icondefault;
        k_iconcross = iconcross;
        k_iconpressed = iconpressed;
        bmodel = getModel();
        setRolloverEnabled(true);
        setToolTipText(tooltipText);
        k_fxDroped = fxDropped;
    }

    public BunxuButton(String tooltiptext, Icon icondefault, boolean fxDroped) {
        k_tooltiptext = tooltiptext;
        k_icondefault = icondefault;
        k_iconpressed = icondefault;
        k_iconcross = icondefault;
        bmodel = getModel();
        setRolloverEnabled(true);
        setToolTipText(tooltiptext);
        k_fxDroped = fxDroped;
    }

    @Override
    protected void paintBorder(Graphics g) {
        Dimension dim = getSize();
        int x = getX();
        int y = getY();
        int w = dim.width-1;
        int h = dim.height -1;
        
        int wimg = 0;
        int himg = 0;
        
        Graphics2D g2 = (Graphics2D) g;
        Color cb = getBackground();
        g.setColor(cb);
        
        if(k_isBorder){
            super.paintBorder(g);
            this.setIcon(k_icondefault);
            this.setRolloverIcon(k_iconcross);
            this.setPressedIcon(k_iconpressed);
            wimg = getIcon().getIconWidth();
            himg = getIcon().getIconHeight();
            setBorderPainted(true);
        }else{
            if(bmodel.isEnabled()){
                g.drawRect(0, 0, getWidth(), getHeight());
                g.drawRect(1, 1, getWidth(), getHeight());
                g.fillRect(0, 0, getWidth(), getHeight());
                int plus = 0;
                if(bmodel.isPressed()){
                    setIcon(k_iconpressed);
                    wimg = getIcon().getIconWidth();
                    wimg = getIcon().getIconHeight();
                    if(k_fxDroped)
                        plus = 1;
                    else
                        plus = 0;                            
                }
                else if(bmodel.isRollover()){
                    setIcon(k_icondefault);
                    wimg = getIcon().getIconWidth();
                    himg = getIcon().getIconHeight();
                    plus = 0;
                }
                else{
                    setIcon(k_icondefault);
                    wimg = getIcon().getIconWidth();
                    himg = getIcon().getIconHeight();
                    plus = 0;
                }
                
                getIcon().paintIcon(this, g, (getWidth()/2-wimg/2)+plus, (getHeight()/2 - himg/2)+plus);
                
                setBorderPainted(true);
                
            }else{
                if(bmodel.isRollover()){
                    setIcon(k_iconcross);
                }else{
                    setIcon(k_icondefault);
                }
                
                this.setOpaque(false);
                wimg = getIcon().getIconWidth();
                himg = getIcon().getIconHeight();
                
                if(wimg <= 0) wimg = 1;
                if(himg <= 0) himg = 1;
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                
                BufferedImage buf = new BufferedImage(wimg, himg, BufferedImage.TYPE_INT_ARGB_PRE);
                super.paintComponent(buf.getGraphics());
                float[] my_kernel = {0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f, 0.10f};
                
                AffineTransform at = new AffineTransform();
                BufferedImage bufd = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
                
                ConvolveOp op = new ConvolveOp(new Kernel(3, 1, my_kernel));
                Image img = op.filter(buf, bufd);
                AffineTransformOp biop = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                g2.drawImage(img, 0, 0, null);
                setBorderPainted(true);
            }
        }
        
        this.setPreferredSize(new Dimension(wimg+4, himg+4));
                
    }
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

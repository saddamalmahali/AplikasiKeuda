/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.model.Fungsi;
import com.keuda.model.SubFungsi;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

/**
 *
 * @author BENDAHARA
 */

public class FungsiTreeRenderer extends JLabel implements TreeCellRenderer{
    static protected ImageIcon open, close, rooticon;
    
    static protected final Color k_selectedBackroundColor = new Color(156, 138, 206);
        
        static{
            try {
                rooticon = new ImageIcon("images/account.gif");
                open = new ImageIcon("images/open_tree.png");
                close = new ImageIcon("images/close_tree.png");
            } catch (Exception e) {
                System.out.println("Tidak dapat mengambil gambar : "+e.toString());
            }
        }
        
        protected boolean k_selected = false;
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            
            TreeModel tmodel = tree.getModel();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            
            Fungsi fungsi  = null;
            SubFungsi subfungsi = null;
            
            String stringvalue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);
            setText(stringvalue);
            setToolTipText(stringvalue);
            
            if(node == tmodel.getRoot())
                setIcon(rooticon);
            
            Object object = node.getUserObject();
            
            if(object instanceof Fungsi){
                fungsi = (Fungsi) object;
                if(expanded){
                    setIcon(open);
                }else
                    setIcon(close);
            }else if(object instanceof SubFungsi){
                subfungsi = (SubFungsi) object;
                setIcon(null);
            }else{
                
            }
            
            if(hasFocus)
                setForeground(new Color(0, 0, 128));
            else
                setForeground(Color.black);
                
            
            
            k_selected = sel;
            
            return this;
            
        }

    @Override
    public void paint(Graphics g) {
        Color bColor;
        Icon currentIcon = getIcon();
        if(k_selected)
            bColor = new Color(206, 206, 255);
        else if(getParent() != null)
            bColor = getParent().getBackground();
        else
            bColor = getBackground();
        
        g.setColor(bColor);
        if(currentIcon != null && getText() != null){
            int offset = (currentIcon.getIconWidth() + getIconTextGap());
            g.fillRect(offset-1, 0, getWidth()-offset+1, getHeight());
        }else
            g.fillRect(0, 0, getWidth(), getHeight());
        
        super.paint(g);
    }
        
        
    
}
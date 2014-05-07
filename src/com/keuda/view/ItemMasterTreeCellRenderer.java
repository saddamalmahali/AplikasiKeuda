/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.ItemMaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

/**
 *
 * @author user
 */
public class ItemMasterTreeCellRenderer extends JLabel implements TreeCellRenderer{
    static protected ImageIcon k_rootIcon, k_mainIcon, k_childIcon, k_mainIcon2, k_childIcon2;
    static protected final Color k_selectiedBackgroundColor = new Color(156, 138, 206);
    
    static {
        try{
            k_rootIcon = new ImageIcon();
            k_mainIcon = new ImageIcon();
            k_childIcon = new ImageIcon();
            
            k_mainIcon2 = new ImageIcon();
            k_childIcon2 = new ImageIcon();
        }catch(Exception e){
            System.out.println("Tidak Dapat Mengambil Gambar : "+e);
        }
    }
    
    
    protected boolean k_selected = false;
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        TreeModel tmodel = tree.getModel();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        
        ItemMaster im = null;
        String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
        
        setText(stringValue);
        
        if(node == tmodel.getRoot()){
            setIcon(k_rootIcon);
        }
        
        Object obj = node.getUserObject();
        if(obj instanceof ItemMaster){
            im = (ItemMaster) obj;
            if(im.getAkun() !=null){
                if(im.isAkunGrup() == ItemMaster.GROUP){
                    setIcon(k_mainIcon2);
                }else{
                    setIcon(k_childIcon2);
                }
                
                setToolTipText("Akun : "+im.getAkun().toString());
            }else{
                if(im.isAkunGrup() == ItemMaster.GROUP){
                    setIcon(k_mainIcon);
                }else{
                    setIcon(k_childIcon);
                }
                setToolTipText(stringValue);
            }
        }
        
        
        if(hasFocus) {
          setForeground(new Color(0, 0, 128));
        }
        else {
          setForeground(Color.black);
        }
        
        k_selected = selected;
        
        return this;
    }

    @Override
    public void paint(Graphics g) {
        Color bColor;
        Icon currentI = getIcon();
        
        if(k_selected) {
          bColor = new Color(206, 206, 255);
        }
        else if(getParent() != null) {
          bColor = getParent().getBackground();
        }
        else {
          bColor = getBackground();
        }

        g.setColor(bColor);
        if(currentI != null && getText() != null) {
            int offset = (currentI.getIconWidth() + getIconTextGap());

        g.fillRect(offset - 1, 0, getWidth() - offset + 1, getHeight());
        }
        else {
          g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paint(g);
    }
    
    
    
}

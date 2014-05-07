/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.Kegiatan;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author user
 */
public class KegiatanListCellRenderer extends JLabel implements ListCellRenderer {
    
    static protected final Color k_selectedBackgroundColor = new Color(156, 138, 206);
    static protected ImageIcon k_kgtIcon;
    
    static{
        try {
            k_kgtIcon = new ImageIcon();
        } catch (Exception e) {
            System.out.println("Tidak Dapat Mengambil Gambar : "+e);
        }
    }
    protected boolean k_selected = false;

    public KegiatanListCellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(JLabel.LEFT);
        setVerticalAlignment(CENTER);
    }
    
    
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, 
                                            boolean isSelected, boolean cellHasFocus) {
        DefaultListModel model = new DefaultListModel();
        
        if(value instanceof Kegiatan){
            Kegiatan obj = (Kegiatan) value;
            setIcon(k_kgtIcon);
        }
        setText(value.toString());
        k_selected = isSelected;
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        return this;
    }
    
}

package com.keuda.util;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author adam
 */
public class ButtonAddDelPanel extends JPanel{
    
    BunxuButton k_btnAdd, k_btnDel, k_btnEdit;
    public boolean k_showEdit = false;
    
    short state = -1;
    
    public static final short NEW_STATE = 0;
    public static final short AFTER_NEW_STATE = 1;
    public static final short DISABLE_ALL = 2;
    
    public ButtonAddDelPanel(boolean showEdit) {
        super(new FlowLayout(FlowLayout.LEFT));
        
        k_showEdit = showEdit;
        
        k_btnAdd = new BunxuButton("Tambah Data", new ImageIcon(getClass().getResource("/com/keuda/images/add.png")), new ImageIcon(getClass().getResource("/com/keuda/images/add.png")), new ImageIcon(getClass().getResource("/com/keuda/images/add.png")), true);
        k_btnDel = new BunxuButton("Hapus Data", new ImageIcon(getClass().getResource("/com/keuda/images/del.png")), new ImageIcon(getClass().getResource("/com/keuda/images/del.png")), new ImageIcon(getClass().getResource("/com/keuda/images/del.png")), true);
        k_btnEdit = new BunxuButton("Edit Data", new ImageIcon(getClass().getResource("/com/keuda/images/edit.png")), new ImageIcon(getClass().getResource("/com/keuda/images/edit.png")), new ImageIcon(getClass().getResource("/com/keuda/images/edit.png")), showEdit);
        
        k_btnAdd.setEnabled(false);
        k_btnDel.setEnabled(false);
        
        add(k_btnAdd);
        if(showEdit){
            add(k_btnEdit);
            k_btnEdit.setEnabled(false);
        }
        add(k_btnDel);
    }   
   
    public void setState(short state){
        if(state == NEW_STATE){
            k_btnAdd.setEnabled(true);
            k_btnEdit.setEnabled(false);
            k_btnDel.setEnabled(false);
        }else if(state == AFTER_NEW_STATE){
            k_btnAdd.setEnabled(true);
            k_btnEdit.setEnabled(true);
            k_btnDel.setEnabled(true);
        }else{
            k_btnAdd.setEnabled(false);
            k_btnEdit.setEnabled(false);
            k_btnDel.setEnabled(false);
        }
        
    }
    
    public BunxuButton getAddButton(){
        return k_btnAdd;
    }
    
    public BunxuButton getDelButton(){
        return k_btnDel;
        
    }
    
    public BunxuButton getEditButton(){
        return k_btnEdit;
    }
    
    
    
    public void setActionPerformed(ActionListener al){
        k_btnAdd.addActionListener(al);
        k_btnDel.addActionListener(al);
        k_btnEdit.addActionListener(al);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.view.KeudaPanel;
import com.keuda.view.MainForm;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class PanelButton extends KeudaPanel{
    public JButton k_btNew = new JButton("Tambah");
    public JButton k_btEdit = new JButton("Ubah");
    public JButton k_btSave = new JButton("Simpan");
    public JButton k_btDelete = new JButton("Hapus");
    public JButton k_btCancel = new JButton("Batal");
    public JButton k_btPrint = new JButton("Cetak");
    
    short k_state = 1;
    public static final short NEW_STATE = 0;
    public static final short AFTER_SAVE_STATE = 1;
    public static final short AFTER_NEW_STATE = 2;
    public static final short ALL_DISABLE_STATE = 3;
    
    boolean isPrint = false;
    boolean isPanelOutcome = false;
    public PanelButton() {
        this(false);
        
    }

    public PanelButton(boolean isPrint) {
        this.isPrint = isPrint;
        construct();
        setBorder(BorderFactory.createEtchedBorder());
    }
    
    void construct(){
        setLayout(new FlowLayout());
        add(k_btNew); k_btNew.setToolTipText("Data Baru"); k_btNew.setMnemonic('N');
        
        add(k_btEdit); k_btEdit.setToolTipText("Ubah Data"); k_btEdit.setMnemonic('E');
        add(k_btSave); k_btSave.setToolTipText("Simpan Data"); k_btSave.setMnemonic('S');
        add(k_btDelete); k_btDelete.setToolTipText("Hapus Data"); k_btDelete.setMnemonic('D');
        add(k_btCancel); k_btCancel.setToolTipText("Batal"); k_btCancel.setMnemonic('C');
        k_btPrint.setToolTipText("Cetak"); k_btPrint.setMnemonic('P');
        if(isPrint)
            add(k_btPrint);
        
    }
    
    public void addButtonListener(ActionListener l){
        k_btNew.addActionListener(l);
        k_btEdit.addActionListener(l);
        k_btSave.addActionListener(l);
        k_btDelete.addActionListener(l);
        k_btCancel.addActionListener(l);
        k_btPrint.addActionListener(l);
    }
    
    public void setButtonEnable(boolean flag){
        k_btNew.setEnabled(flag);
        k_btEdit.setEnabled(flag);
        k_btSave.setEnabled(flag);
        k_btDelete.setEnabled(flag);
        k_btCancel.setEnabled(flag);
        k_btCancel.setEnabled(flag);
    }
    
    public void setButtonState(JTable table){
        if(table.getRowCount()>0){
            setState(PanelButton.AFTER_SAVE_STATE);
        }
        else setState(PanelButton.NEW_STATE);
    }
    
    public void setState(short state){
        k_state = state;
        if(state == NEW_STATE){
            k_btNew.setEnabled(true);
            k_btEdit.setEnabled(false);
            k_btSave.setEnabled(false);
            k_btDelete.setEnabled(false);
            k_btCancel.setEnabled(false);
            k_btPrint.setEnabled(false);
        }
        else if(state == AFTER_SAVE_STATE){
            k_btNew.setEnabled(true);
            k_btEdit.setEnabled(true);
            k_btSave.setEnabled(false);
            k_btDelete.setEnabled(true);
            k_btCancel.setEnabled(false);
            k_btPrint.setEnabled(true);
        }else if(state == AFTER_NEW_STATE){
            k_btNew.setEnabled(false);
            k_btEdit.setEnabled(false);
            k_btSave.setEnabled(true);
            k_btDelete.setEnabled(false);
            k_btCancel.setEnabled(true);
            k_btPrint.setEnabled(false);
        }
        else{
            k_btNew.setEnabled(false);
            k_btEdit.setEnabled(false);
            k_btSave.setEnabled(false);
            k_btDelete.setEnabled(false);
            k_btCancel.setEnabled(false);
            k_btPrint.setEnabled(false);
        }
    }

    public JButton getButtonCancel() {
        return k_btCancel;
    }

    public JButton getButtonDelete() {
        return k_btDelete;
    }

    public JButton getButtonEdit() {
        return k_btEdit;
    }

    public JButton getButtonNew() {
        return k_btNew;
    }

    public JButton getButtonPrint() {
        return k_btPrint;
    }

    public JButton getButtonSave() {
        return k_btSave;
    }   
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.view.KeudaPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class PanelButtonSimple extends KeudaPanel{
    public JButton k_btnNew = new JButton("Tambah");
    public JButton k_btnUpdate = new JButton("Ubah");
    public JButton k_btnDelete = new JButton("Hapus");
    public JButton k_btnView = new JButton("Lihat Detail");
    public JButton k_btnPrint = new JButton("Cetak");
    public JButton k_btnSave = new JButton("Simpan");
    public JButton k_btnCancel = new JButton("Batal");
    short k_state = -1;
    public static final short NEW_STATE = 0;
    public static final short AFTER_SAVE_STATE = 1;
    public static final short AFTER_NEW_STATE = 2;
    public static final short ALL_DISABLE_STATE = 3;
    
    boolean isPrint = false;

    public PanelButtonSimple() {
        this(false);
    }
    
    public PanelButtonSimple(boolean isPrint) {
        this.isPrint = isPrint;
        
        setBorder(BorderFactory.createEtchedBorder());
    }
    
    void construct(){
        setLayout(new FlowLayout());
        add(k_btnView); k_btnView.setToolTipText("Lihat Detail"); k_btnView.setMnemonic('V');
        add(k_btnNew); k_btnNew.setToolTipText("Data Baru"); k_btnNew.setMnemonic('N');
        add(k_btnUpdate); k_btnUpdate.setToolTipText("Ubah Data"); k_btnUpdate.setMnemonic('E');
        add(k_btnDelete); k_btnDelete.setToolTipText("Hapus Data"); k_btnDelete.setMnemonic('D');
        add(k_btnSave); k_btnSave.setToolTipText("Simpan Data"); k_btnSave.setMnemonic('S');
        add(k_btnCancel); k_btnCancel.setToolTipText("Batal"); k_btnCancel.setMnemonic('C');
        k_btnPrint.setToolTipText("Cetak"); k_btnPrint.setMnemonic('P');
        if(isPrint)
            add(k_btnPrint);
        
    }
            
    
    public void addButtonListener(ActionListener l){
        
        k_btnNew.addActionListener(l);
        k_btnUpdate.addActionListener(l);
        k_btnDelete.addActionListener(l);
        k_btnView.addActionListener(l);
        k_btnPrint.addActionListener(l);
        
    }
    
    public void setButtonEnable(boolean flag){
        k_btnNew.setEnabled(flag);
        k_btnUpdate.setEnabled(flag);
        k_btnDelete.setEnabled(flag);
        k_btnView.setEnabled(flag);
        k_btnPrint.setEnabled(flag);
        k_btnSave.setEnabled(flag);
        k_btnCancel.setEnabled(flag);
    }
    
    public void setButtonState(JTable table){
        if(table.getRowCount() > 0){
            
        }
    }

    public void setState(short state) {
        k_state = state;
        if(state == NEW_STATE){
            k_btnView.setEnabled(false);
            k_btnNew.setEnabled(true);
            k_btnUpdate.setEnabled(false);
            k_btnDelete.setEnabled(false);
            k_btnPrint.setEnabled(false);
            k_btnCancel.setEnabled(false);
            k_btnSave.setEnabled(false);
        }else if(state == AFTER_SAVE_STATE){
            k_btnView.setEnabled(true);
            k_btnNew.setEnabled(true);
            k_btnUpdate.setEnabled(true);
            k_btnDelete.setEnabled(true);
            k_btnPrint.setEnabled(false);
            k_btnSave.setEnabled(false);
            k_btnCancel.setEnabled(false);
        }else if(state == AFTER_NEW_STATE){
            k_btnView.setEnabled(false);
            k_btnNew.setEnabled(false);
            k_btnUpdate.setEnabled(false);
            k_btnDelete.setEnabled(false);
            k_btnPrint.setEnabled(false);
            k_btnSave.setEnabled(true);
            k_btnCancel.setEnabled(true);
        }else{
            k_btnView.setEnabled(false);
            k_btnNew.setEnabled(false);
            k_btnUpdate.setEnabled(false);
            k_btnDelete.setEnabled(false);
            k_btnPrint.setEnabled(false);
        }
    }
    
    
    
}

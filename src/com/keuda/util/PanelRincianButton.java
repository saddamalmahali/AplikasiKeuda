/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author adam
 */
public class PanelRincianButton extends KeudaPanel{
    public JButton k_btnNewRincian = new JButton("Tambah Rincian");
    public JButton k_btnNewDetailRincian = new JButton("Tambah Detail Rincian");
    public JButton k_btnNewSubDetailRincian = new JButton("Tambah Sub Rincian");
    public JButton k_update = new JButton("Update Data");
    public JButton k_delete = new JButton("Delete Data");
    public JButton k_view = new JButton("Lihat Rincian");
    
    short k_state = 1;
    
    public static final short NEW_STATE = 0;
    public static final short RINCIAN_STATE = 1;
    public static final short DETAILRINCIAN_STATE = 2;
    public static final short SUBDETAILRINCIAN_STATE = 3;
    public static final short DISABLE_ALL = 4;

    public PanelRincianButton() {
        construct();
        setBorder(BorderFactory.createEtchedBorder());
    }
    
    
    void construct(){
        setLayout(new FlowLayout());
        add(k_btnNewRincian); k_btnNewRincian.setToolTipText("Tambah Rincian");
        add(k_btnNewDetailRincian); k_btnNewDetailRincian.setToolTipText("Tambah Detail Rincian");
        add(k_btnNewSubDetailRincian); k_btnNewSubDetailRincian.setToolTipText("Tambah Sub Detail Rincian");
        add(k_update); k_update.setToolTipText("Edit Data");
        add(k_delete); k_delete.setToolTipText("Hapus Data");
        add(k_view); k_view.setToolTipText("Lihat Rincian");
    }
    public void addButtonListener(ActionListener l){
        k_btnNewRincian.addActionListener(l);
        k_btnNewDetailRincian.addActionListener(l);
        k_btnNewSubDetailRincian.addActionListener(l);
        k_update.addActionListener(l);
        k_delete.addActionListener(l);
        k_view.addActionListener(l);
    }
    
    public void setButtonEnable(boolean flag){
        k_btnNewRincian.setEnabled(flag);
        k_btnNewDetailRincian.setEnabled(flag);
        k_btnNewSubDetailRincian.setEnabled(flag);
        k_update.setEnabled(flag);
        k_delete.setEnabled(flag);
        k_view.setEnabled(flag);
    }
    
    public void setButtonState(JTable table){
        if(table.getRowCount() > 0){
            
        }
    }
    
    public void setState(short state){
        k_state = state;
        if(state == NEW_STATE){
            k_btnNewRincian.setEnabled(true);
            k_btnNewDetailRincian.setEnabled(false);
            k_btnNewSubDetailRincian.setEnabled(false);
            k_update.setEnabled(false);
            k_delete.setEnabled(false);
            k_view.setEnabled(false);
        }else if(state == RINCIAN_STATE){
            k_btnNewRincian.setEnabled(true);
            k_btnNewDetailRincian.setEnabled(true);
            k_btnNewSubDetailRincian.setEnabled(false);
            k_update.setEnabled(false);
            k_delete.setEnabled(true);
            k_view.setEnabled(false);
        }else if(state == DETAILRINCIAN_STATE){
            k_btnNewRincian.setEnabled(true);
            k_btnNewDetailRincian.setEnabled(true);
            k_btnNewSubDetailRincian.setEnabled(true);
            k_update.setEnabled(true);
            k_delete.setEnabled(true);
            k_view.setEnabled(true);
        }else if(state == SUBDETAILRINCIAN_STATE){
            k_btnNewRincian.setEnabled(true);
            k_btnNewDetailRincian.setEnabled(true);
            k_btnNewSubDetailRincian.setEnabled(true);
            k_update.setEnabled(true);
            k_delete.setEnabled(true);
            k_view.setEnabled(true);
        }else if(state == DISABLE_ALL){
            k_btnNewRincian.setEnabled(false);
            k_btnNewDetailRincian.setEnabled(false);
            k_btnNewSubDetailRincian.setEnabled(false);
            k_update.setEnabled(false);
            k_delete.setEnabled(false);
            k_view.setEnabled(false);
        }
    }
    
    public JButton getBtnNewRincian() {
        return k_btnNewRincian;
    }

    public JButton getK_btnNewDetailRincian() {
        return k_btnNewDetailRincian;
    }
    
    public JButton getBtnNewSubDetailRincian() {
        return k_btnNewSubDetailRincian;
    }

    public JButton getUpdate() {
        return k_update;
    }

    public JButton getDelete() {
        return k_delete;
    }

    public JButton getView() {
        return k_view;
    }
    
    
}

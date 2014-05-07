/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.view.MainForm;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public abstract class BunxuCellEditor extends DefaultCellEditor implements ActionListener{
    public MainForm k_owner;
    JButton k_browseBt = new JButton("...");
    public DefaultTableModel k_model = null;
    public int k_row, k_col;
    public Connection conn;
    public long k_sessionId=-1;
    Object k_object = null;
    public JTable k_table;

    public BunxuCellEditor(MainForm owner) {
        super(new JTextField());
        k_owner = owner;
        k_browseBt.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        k_table =table;
        k_model = (DefaultTableModel) table.getModel();
        k_row = row;
        k_col = column;
        k_object = value;
        return k_browseBt;
        
    }
    
    public Component getTableCellEditorComponent(JTable table, int row, int column) {
    k_model = (DefaultTableModel)table.getModel();
    k_row = row;
    k_col = column;
    return k_browseBt;
  }
    
    public Object getObject(){
        return k_object;
    }
    
    public void setObject(Object object){
        k_object = object;
        k_model.setValueAt(k_object, k_row, k_col);
    }
    
    public void setObject(Object object, int col){
        k_object = object;
        k_model.setValueAt(object, k_row, col);
    }
    
    public void setValueAt(Object object, int row, int col){
        k_object = object;
        k_model.setValueAt(k_object, row, col);
    }
    
    public int getRow(){
        return k_row;        
    }
    
    public int getCol(){
        return k_col;
    }
    
    public abstract void onOk();
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_browseBt){
            stopCellEditing();
            onOk();
        }
    }
    
}

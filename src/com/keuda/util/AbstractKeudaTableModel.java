/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.sun.corba.se.pept.transport.Selector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class AbstractKeudaTableModel extends AbstractTableModel{
    protected Object[][] data;
    protected Object[] dataColumnHeader;
    
    public AbstractKeudaTableModel(Object[] columnHeader, Object[][] data) {
        this.data = data;
        this.dataColumnHeader = columnHeader;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public Object[][] getData() {
        return data;
    }

    public void setDataColumnHeader(Object[] dataColumnHeader) {
        this.dataColumnHeader = dataColumnHeader;
    }

    public Object[] getDataColumnHeader() {
        return dataColumnHeader;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (data[0][1]).getClass();
    }      
    
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return dataColumnHeader.length;
    }   
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }    
    
    
    
}

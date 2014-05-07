/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

/**
 *
 * @author user
 */
public class AbstractOutcomeTableModel extends AbstractKeudaTableModel{
    protected Object[][] data;
    protected String[] ColumnString;
    public AbstractOutcomeTableModel(String[] columnHeader, Object[][] data) {
        super(columnHeader, data);
    }

    
    
}

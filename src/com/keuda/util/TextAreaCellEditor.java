/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class TextAreaCellEditor extends AbstractCellEditor implements TableCellEditor{
    
    protected JTextArea k_textArea;
    protected JScrollPane k_scroll;

    public TextAreaCellEditor() {
        k_textArea = new JTextArea();
        k_scroll = new JScrollPane(k_textArea);
        
        k_textArea.setWrapStyleWord(true);
        k_textArea.setLineWrap(true);
//        k_textArea.setOpaque(true);
        k_textArea.setEditable(false);
        k_textArea.setRows(1);
    }
    
    
    
    @Override
    public Object getCellEditorValue() {
        return k_textArea.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        k_textArea.setText(value.toString());
        
        k_textArea.setFont(table.getFont());
        
        if(isSelected){
            k_textArea.setBackground(table.getSelectionBackground());
            k_textArea.setForeground(table.getSelectionForeground());
            k_scroll.setBackground(table.getSelectionBackground());
            k_scroll.setForeground(table.getSelectionForeground());
        }else{
            k_textArea.setBackground(table.getBackground());
            k_textArea.setForeground(table.getForeground());
            k_scroll.setBackground(table.getBackground());
            k_scroll.setForeground(table.getForeground());
        }
        
        return k_scroll;
    }
    
}

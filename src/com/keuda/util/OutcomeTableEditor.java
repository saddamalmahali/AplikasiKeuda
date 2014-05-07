/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.model.OutcomeProgram;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class OutcomeTableEditor extends AbstractCellEditor implements TableCellEditor{
    OutcomeTableComponent editor;

    public OutcomeTableEditor() {
        editor = new OutcomeTableComponent();
    }
    
    
    
    
    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        OutcomeProgram program = (OutcomeProgram) value;
        
        editor.updatePanel(program, isSelected, table);
        
        return editor;
    }
    
}

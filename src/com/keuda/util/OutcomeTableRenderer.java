/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.model.OutcomeProgram;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class OutcomeTableRenderer implements TableCellRenderer{
    OutcomeTableComponent component;
    OutcomeProgram k_outProg;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        k_outProg = (OutcomeProgram) value;
        component = new OutcomeTableComponent();
        
        component.updatePanel(k_outProg, isSelected, table);
        
        return component;
    }
    
}

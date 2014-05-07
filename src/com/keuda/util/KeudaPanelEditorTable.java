/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class KeudaPanelEditorTable extends BasicCellEditor implements TableCellEditor{
    protected Object value;
    protected Color foreground;
    protected Color background;
    protected Font font;
    
    public KeudaPanelEditorTable(JPanel panel) {
        super(panel);
        
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
        editor.setForeground(foreground);
    }

    public void setBackground(Color background) {
        this.background = background;
        editor.setBackground(background);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public Object getCellEditorValue() {
        return value;
    }

    @Override
    public void editingStarted(EventObject event) {
        
    }
    
    
    
    
   

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setForeground(foreground != null ? foreground : table.getForeground());
        editor.setBackground(background !=null ? background : table.getBackground());
        editor.setFont(font != null ? font : table.getFont());
        
        this.value = value;
        
        return editor;
    }
    
    
}

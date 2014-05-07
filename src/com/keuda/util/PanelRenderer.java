/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class PanelRenderer extends JPanel implements TableCellRenderer{
    protected Color foreground;
    protected Color background;
    protected Font font;
    protected Border border;

    public PanelRenderer() {
        this.border = getBorder();
        this.setOpaque(true);
    }

    @Override
    public void setForeground(Color fg) {
        this.foreground = fg;
        super.setForeground(foreground);
    }

    @Override
    public void setBackground(Color bg) {
        this.background = bg;
        super.setBackground(background);
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
        super.setFont(font);
    }

    
    
    
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Color cellForeground = foreground != null ? foreground : table.getForeground();
        Color cellBackground = background != null ? background : table.getBackground();
        
        setFont(font != null ? font : table.getFont());
        
        if(hasFocus){
            setBorder(border);
//            setBorder(UIManager.getBorder("Table.focusCellHiflightBorder"));
//            if(table.isCellEditable(row, column)){
//                cellForeground = UIManager.getColor("Table.focusCellForeGround");
//                cellBackground = UIManager.getColor("Table.focusCellBackground");
//            }
        }else{
            setBorder(border);
        }
        
        table.setCellSelectionEnabled(false);
        this.setBorder(border);
        this.setSize(5, 10);
        super.setForeground(cellForeground);
        super.setBackground(cellBackground);
        
        return this;
    }
    
}

package com.keuda.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import pohaci.gumunda.cgui.DoubleCellRenderer;

/**
 *
 * @author adam
 */

public class RowColorTableRender extends DoubleCellRenderer {

    RowColor[] k_rowColor = null;

    public RowColorTableRender(RowColor[] color) {
        super(JLabel.RIGHT);
        k_rowColor = color;
    }

    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        setHorizontalAlignment(JLabel.LEFT);

        if (value instanceof Double || value instanceof Float) {
            setHorizontalAlignment(JLabel.RIGHT);
        } else if (value instanceof Integer) {
            setHorizontalAlignment(JLabel.CENTER);
        }

        if (k_rowColor != null) {
            for (int i = 0; i < k_rowColor.length; i++) {
                if (k_rowColor[i].getRow() == row) {
                    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    if (k_rowColor[i].getBack() != null) {
                        comp.setBackground(k_rowColor[i].getBack());

                        if (value instanceof Double) {
                            setHorizontalAlignment(JLabel.RIGHT);

                        } else {
                            setHorizontalAlignment(JLabel.LEFT);
                        }
                    }

                    JLabel l = new JLabel();

                    if (k_rowColor[i].isBold() == true) {
                        Font bold = new Font(l.getFont().getName(), Font.BOLD, l.getFont().getSize());
                        comp.setFont(bold);
                    }
                    table.setSelectionForeground(table.getSelectionForeground());
                    table.setSelectionBackground(table.getSelectionBackground());
                    return comp;
                } else {
                    setForeground(Color.black);
                    setBackground(Color.WHITE);
                }
            }

        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}

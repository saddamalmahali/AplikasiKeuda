/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.*;

/**
 *
 * @author user
 */
public class GroupableTableHeaderUI extends BasicTableHeaderUI {

    private final int INSET = 0;

    @Override
    public void paint(Graphics g, JComponent c) {
        g.translate(3, 0);
        g.translate(-3, 0);

        if (!c.isOpaque()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.fillRect(0, 0, c.getWidth(), c.getHeight());

        Rectangle clipBounds = g.getClipBounds();

        if (header.getColumnModel() == null) {
            return;
        }

        int column = 0;
        Dimension size = header.getSize();

        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);

        Hashtable h = new Hashtable();

        Enumeration enumeration = header.getColumnModel().getColumns();

        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = (TableColumn) enumeration.nextElement();

            Enumeration cGroups = ((GroupableTableHeader) header).getColumnGroups(aColumn);

            if (cGroups != null) {
                int groupHeight = 0;
                while (cGroups.hasMoreElements()) {
                    ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);
                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(header.getTable());
                        groupRect.width = d.width;
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }
                    paintCell(g, groupRect, cGroup);
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }
            cellRect.width = aColumn.getWidth();
            if (cellRect.intersects(clipBounds)) {
                paintCell(g, cellRect, column);
            }
            cellRect.x += cellRect.width;
            column++;
        }
        rendererPane.removeAll();
    }

    private void paintCell(Graphics g, Rectangle groupRect, ColumnGroup cGroup) {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        Component component = renderer.getTableCellRendererComponent(header.getTable(), cGroup.getHeaderValue(),
                false, false, -1, -1);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, groupRect.x + INSET,
                groupRect.y + INSET, groupRect.width, groupRect.height, true);


    }

    private int getHeaderHeight() {
        int height = 0;
        TableColumnModel columnModel = header.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); column++) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            Component comp = renderer.getTableCellRendererComponent(header.getTable(),
                    aColumn.getHeaderValue(), false, false, -1, column);

            int cHeight = comp.getPreferredSize().height;
            Enumeration enums = ((GroupableTableHeader) header).getColumnGroups(aColumn);

            if (enums != null) {
                while (enums.hasMoreElements()) {
                    ColumnGroup cGroup = (ColumnGroup) enums.nextElement();
                    cHeight += cGroup.getSize(header.getTable()).height;
                }
            }
            height = Math.max(height, cHeight);

        }
        return height;
    }

    private Dimension createHeaderSize(long width) {
        TableColumnModel columnModel = header.getColumnModel();
        width *= columnModel.getColumnCount();

        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }

        return new Dimension((int) width, getHeaderHeight());
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();

            aColumn.setHeaderRenderer(new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JTableHeader header = table.getTableHeader();

                    if (header != null) {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    
                    setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            });
            
            width = width + aColumn.getWidth();
            
        }
        
        return createHeaderSize(width);
        
    }

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();
        Component component = renderer.getTableCellRendererComponent(
                header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x + INSET, cellRect.y + INSET, cellRect.width,
                cellRect.height, true);
    }
}

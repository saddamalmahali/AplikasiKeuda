/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author user
 */
public class GroupableTableHeader extends JTableHeader {

    private static final String uiClassID = "GroupableTableHeader";
    protected Vector columnGroups = null;

    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        setReorderingAllowed(false);
    }

    @Override
    public void setUI(TableHeaderUI ui) {
        super.setUI(new GroupableTableHeaderUI());
    }

    @Override
    public void setReorderingAllowed(boolean b) {
        reorderingAllowed = false;
    }

    public void addColumnGroup(ColumnGroup g) {
        if (columnGroups == null) {
            columnGroups = new Vector();
        }
        columnGroups.addElement(g);

    }

    public Enumeration getColumnGroups(TableColumn col) {
        if (columnGroups == null) {
            return null;
        }
        Enumeration enums = columnGroups.elements();
        while (enums.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup) enums.nextElement();
            Vector v_ret = cGroup.getColumnGroups(col, new Vector());
            if (v_ret != null) {
                return v_ret.elements();
            }

        }

        return null;

    }
}

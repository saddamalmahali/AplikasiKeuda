/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.services.IDBCConstant;
import java.sql.Connection;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author adam
 */
public class KomponenTree extends KeudaTree{
    short k_level = -1;

    public KomponenTree(Connection conn, long sessionId) {
        super(IDBCConstant.TABLE_KOMPONEN, "Struktur Komponen", IDBCConstant.MODUL_CONFIGURATION, conn, sessionId);
        k_sessionid = sessionId;
        setExpandsSelectedPaths(true);
    }
    
    public void constructComponents(){
        
    }
    
    @Override
    public void init() {
        DefaultTreeModel tmodel = (DefaultTreeModel) getModel();
        
        try {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) tmodel.getRoot();
            BusinessLogic logic = new BusinessLogic(k_conn);
            
        } catch (Exception e) {
        }
        
    }

    @Override
    public void setDefaultTreeCellRenderer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

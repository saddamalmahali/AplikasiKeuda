/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import java.sql.Connection;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author user
 */
public abstract class KeudaTree extends JTree{
    public Connection k_conn;
    public long k_sessionid = -1;
    public String k_rootTitle = "";
    public String k_tablename = "";
    public String k_modul = "";
    public DefaultTreeModel k_treeModel;

    public KeudaTree(String namaTabel, String rootTitle, String modul
           , Connection conn, long sessionID) {
        k_tablename = namaTabel;
        k_modul =  modul;
        k_conn = conn;
        k_sessionid = sessionID;
        k_rootTitle = rootTitle;       
        
    }

    void constructComponents(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(k_rootTitle);
        k_treeModel = new DefaultTreeModel(root);
        setModel(treeModel);
        setDefaultTreeCellRenderer();
        ToolTipManager.sharedInstance().registerComponent(this);
    }
    
    public DefaultMutableTreeNode getRoot(){
        return (DefaultMutableTreeNode) getModel().getRoot();
    }
    
    public void insertNode(Object newObject, DefaultMutableTreeNode superNode){
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        if(superNode == null){
            superNode = (DefaultMutableTreeNode) model.getRoot();
            
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newObject);
        model.insertNodeInto(newNode, superNode, superNode.getChildCount());
        
        scrollPathToVisible(new TreePath(model.getPathToRoot(newNode)));
        }
    }
    
    public void updateNode(Object newObject, DefaultMutableTreeNode node){
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        node.setUserObject(newObject);
        model.nodeChanged(node);
    }
    
    public abstract void init();
    public abstract void setDefaultTreeCellRenderer();
}

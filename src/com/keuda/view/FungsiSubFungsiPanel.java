/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.exception.KeudaException;
import com.keuda.model.Fungsi;
import com.keuda.model.SubFungsi;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import com.keuda.util.FungsiTreeRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author BENDAHARA
 */
public class FungsiSubFungsiPanel extends KeudaPanel{
    Fungsi k_fungsi;
    SubFungsi k_subfungsi;
    
    JTree k_tree;
    DefaultTreeModel k_model;
    DefaultMutableTreeNode k_node;
        
    ImageIcon open, close;

    public FungsiSubFungsiPanel(MainForm mainform) {
        super(mainform);
        
        
        setSize(500, 500);
        constructComponent();
        init();
        
        k_tree.setCellRenderer(new FungsiTreeRenderer());
    }
    
    public void constructComponent(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fungsi/Sub Fungsi");
        k_model = new DefaultTreeModel(root);
        k_tree = new JTree(k_model);        
        
        getContentPane().add(new JScrollPane(k_tree), BorderLayout.CENTER);
    }
    
    public void init(){
        
        Fungsi[] listfungsi = null;        
        try {
             listfungsi = k_logic.getAllFungsi(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        } catch (KeudaException ex) {
            Logger.getLogger(FungsiSubFungsiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(listfungsi != null){
            for(int i = 0; i<listfungsi.length; i++){
                k_fungsi = listfungsi[i];
                DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(k_fungsi);
                SubFungsi[] listsub = null;
                try {
                    listsub = k_logic.getAllSubFungsiByFungsi(k_fungsi.getIdfungsi(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                } catch (KeudaException ex) {
                    Logger.getLogger(FungsiSubFungsiPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(listsub != null){
                    for(int j=0; j<listsub.length; j++){
                        k_subfungsi = listsub[j];
                        DefaultMutableTreeNode sfNode = new DefaultMutableTreeNode(k_subfungsi);
                        k_subfungsi = null;
                        fNode.add(sfNode);
                    }
                }
                
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) k_model.getRoot();
                k_fungsi = null;
                root.add(fNode);
                
            }
        }
    }
    
//    public class FungsiTreeRenderer extends DefaultTreeCellRenderer{
//
//        public FungsiTreeRenderer() {
//            open = new ImageIcon("images/open_tree.png");
//            close = new ImageIcon("images/close_tree.png");
//        }
//        
//        
//        
//        @Override
//        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//            
//            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
//            
//            if(!leaf){
//                if(open != null && close != null){
//                    setOpenIcon(open);
//                    setClosedIcon(close);
//                }else{
//                    setClosedIcon(getDefaultOpenIcon());
//                    setOpenIcon(getDefaultOpenIcon());
//                }
//            }
//            
//            
//            
//            
////            setBackground(Color.WHITE);
////            setForeground(Color.BLACK);
//            return this;
//        }
//        
//    }
        
}

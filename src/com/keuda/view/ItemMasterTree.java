/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.model.ItemMaster;
import com.keuda.services.IDBCConstant;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author user
 */
public class ItemMasterTree extends KeudaTree{
    short k_level = -1;
    ItemMaster[] imasterSuper = null;
    ItemMaster[] imasterSub = null;

    public ItemMasterTree(Connection conn, long sessionId) {
        super(IDBCConstant.TABLE_ITEM_MASTER, "Struktur Akun", IDBCConstant.MODUL_CONFIGURATION, conn, sessionId);
        k_sessionid = sessionId;
        
        
        ItemMasterTreeCellRenderer cell = new ItemMasterTreeCellRenderer();
    }

    public void setK_level(short k_level) {
        this.k_level = k_level;
    }
    
    public void constructComponents(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Struktur Akun");
        DefaultTreeModel model = new DefaultTreeModel(root);
        setModel(treeModel);
        
        ToolTipManager.sharedInstance().registerComponent(this);
    }
    
    public boolean contains(Vector vv, ItemMaster obj){
        boolean result = false;
        if(vv!= null){
            if(!vv.isEmpty()){
                int j=0;
                while(j<vv.size() && result ==false){
                    if(((ItemMaster)vv.elementAt(j)).getAkunIndex() == obj.getAkunIndex()){
                        result = true;
                    }
                    j++;
                }
            }
        }
        return result;
    }
    
    public DefaultMutableTreeNode getNode(DefaultMutableTreeNode root, long a){
        DefaultMutableTreeNode result = null;
        DefaultTreeModel tmodel = (DefaultTreeModel) getModel();
        int count = root.getChildCount();
        boolean find = false;
        int i=0;
        while(result == null && i< count){
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)root.getChildAt(i);
	      if(((ItemMaster)child.getUserObject()).getAkunIndex() == a){
	        result = (DefaultMutableTreeNode)root.getChildAt(i);
	      }
	      else{
	        if( child.getChildCount() > 0 ) result = getSubNode(child, a);
	      }
	      i++;
        }
        return result;
    }
    
    public DefaultMutableTreeNode getSubNode(DefaultMutableTreeNode node, long a){
	    DefaultMutableTreeNode result = null;

	    int count = node.getChildCount();
	    boolean find = false;
	    int i=0;
	    while( result == null && i < count ){
	      DefaultMutableTreeNode child = (DefaultMutableTreeNode)node.getChildAt(i);
	      if(((ItemMaster)child.getUserObject()).getAkunIndex() == a){
	        result = (DefaultMutableTreeNode)node.getChildAt(i);
	      }
	      else{
	        if( child.getChildCount() > 0 ) result = getSubNode(child, a);
	      }
	      i++;
	    }

	    return result;
	  }
    
    @Override
    public void init() {
        DefaultTreeModel tmodel = (DefaultTreeModel)getModel();
	    try{
	      DefaultMutableTreeNode root = (DefaultMutableTreeNode)tmodel.getRoot();
	      BusinessLogic logic = new BusinessLogic(k_conn);
//	      ItemMasterStructureForTree structureItemMasters = (ItemMasterStructureForTree)logic.getItemMasterStructures(m_sessionid, IDBConstants.MODUL_CONFIGURATION);
//              update 08/01/2014
//	      ItemMasterStructureForTree structureItemMasters = (ItemMasterStructureForTree)logic.getItemMasterAndStructure(k_sessionid, IDBCConstant.MODUL_CONFIGURATION);
              
//              imasterSuper = structureItemMasters.getItemSuper();
//              imasterSub = structureItemMasters.getItemSub();
              
              DefaultMutableTreeNode supernode = null;
                for (int i = 0; i < imasterSuper.length; i++) {
                    ItemMaster superNode = imasterSuper[i];
                    supernode = new DefaultMutableTreeNode(superNode);
                    root.add(supernode);
                    
                    subItemMaster(supernode);
                }

//	      ItemMasterStructure[] ims = structureItemMasters.getStructure();
//	      ItemMaster[] supers = structureItemMasters.getSuper();
//	      DefaultMutableTreeNode supernode = null;
//	      for( int i=0; i<supers.length; i++ ){
//	    	  ItemMaster superNode = supers[i];
//	    	  superNode.isViewZeroCode = false;
//	        supernode = new DefaultMutableTreeNode(superNode);
//	        root.add( supernode );
//	      }
//
//	      ItemMasterStructure accstr;
//	      DefaultMutableTreeNode parent = null;
//	      java.util.Vector v = new java.util.Vector();
//	      for (int i = 0; i < ims.length; i++) {
//	        ItemMaster itm = ims[i].getItemMaster();
//	        long parentid = ims[i].getsuperId();
//
//	        if(! v.contains( new Long( itm.getAutoIndex() ) ) ){
//	          v.addElement(new Long( itm.getAutoIndex()));
//	          DefaultMutableTreeNode newnode = new DefaultMutableTreeNode( itm );
//	          DefaultMutableTreeNode pnode = getNode( root, parentid );
//	          if( pnode != null )
//	            pnode.add( newnode );
//	        }
//	      }

	    }catch(Exception ex){
	      ex.printStackTrace();
	      JOptionPane.showMessageDialog(this, ex.getMessage(),
	                                    "Perhatian", JOptionPane.WARNING_MESSAGE);
	      return;
	    }

	    expandPath(new TreePath(tmodel.getPathToRoot((TreeNode)tmodel.getRoot())));
    }

    @Override
    public void setDefaultTreeCellRenderer() {
        ItemMasterTreeCellRenderer cell = new ItemMasterTreeCellRenderer();
	    this.setCellRenderer( cell );
    }
    
    private void subItemMaster(DefaultMutableTreeNode subnode){
              DefaultMutableTreeNode childnode = null;
              
              for (int i = 0; i < imasterSub.length; i++) {
                  if(((ItemMaster)subnode.getUserObject()).getAkunIndex() == imasterSub[i].getK_indexParent()){
                      childnode = new DefaultMutableTreeNode(imasterSub[i]);
                      subnode.add(childnode);
                      
                      if(imasterSub[i].isAkunGrup()){
                          subItemMaster(childnode);
                      }
                  }
              }
          }

	  

	
}

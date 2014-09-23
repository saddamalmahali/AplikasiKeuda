/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.model.Akun;
import com.keuda.model.Akun;
import com.keuda.model.AkunStruktur;
import com.keuda.model.AkunStruktur;
import com.keuda.services.IDBCConstant;
import com.keuda.view.AkunStructureForTree;
import com.keuda.view.AkunTreeCellRenderer;
import com.keuda.view.KeudaTree;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author user
 */
public class AkunTree extends KeudaTree{
    
    short k_level = -1;
    
    public AkunTree(Connection conn, long sessionId){
        super(IDBCConstant.TABLE_AKUN, "Struktur Akun", IDBCConstant.MODUL_CONFIGURATION, conn, sessionId);
        k_sessionid = sessionId;
        setExpandsSelectedPaths(true);
        
        
        constructComponents();
        init();
        
    }
    public void setLevel(short level){
        k_level = level;
    }
    
    public void constructComponents(){
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Bagan Akun Standar");
	    DefaultTreeModel model = new DefaultTreeModel(root);
	    setModel(model);

	    ToolTipManager.sharedInstance().registerComponent(this);
    }
    
    
    public boolean contains(Vector vv, Akun obj){
        boolean result = false;
	    if(vv!= null){
	      if(!vv.isEmpty()){
	        int j = 0;
	        while(j < vv.size() && result==false){
	          if(((Akun)vv.elementAt(j)).getAkunIndex() == obj.getAkunIndex() ){
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
	    DefaultTreeModel tmodel = (DefaultTreeModel)getModel();
	    int count = root.getChildCount();
	    boolean find = false;
	    int i=0;
	    while( result == null && i < count ){
	      DefaultMutableTreeNode child = (DefaultMutableTreeNode)root.getChildAt(i);
	      if(((Akun)child.getUserObject()).getAkunIndex() == a){
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
	      if(((Akun)child.getUserObject()).getAkunIndex() == a){
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
        DefaultTreeModel tmodel = (DefaultTreeModel) getModel();
        
        try {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) tmodel.getRoot();
            BusinessLogic logic = new BusinessLogic(k_conn);
            AkunStructureForTree strukturAkun = logic.getAccountStruktures(k_sessionid, IDBCConstant.MODUL_CONFIGURATION);
            
            AkunStruktur[] aks = strukturAkun.getStruktur();
            Akun[] supers = strukturAkun.getSuper();
            DefaultMutableTreeNode supernode = null;
            for(int i=0; i<supers.length; i++){
               supernode = new DefaultMutableTreeNode(supers[i]);
               root.add(supernode);
            }
            
            AkunStruktur akns;
            DefaultMutableTreeNode parent = null;
            Vector v = new Vector();
            for(int i=0; i<aks.length;i++){
                Akun akn = aks[i].getAkun();
                long parentid = aks[i].getSuperId(); 
                
                //Mulai Parsing
                
                if(!v.contains(new Long(akn.getAkunIndex()))){
                    v.addElement(new Long(akn.getAkunIndex()));
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(akn);
                    DefaultMutableTreeNode pNode = getNode(root, parentid);
                    if(pNode != null)
                        pNode.add(newNode);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Perhatian", JOptionPane.WARNING_MESSAGE);
            return ;
        }
    }

    @Override
    public void setDefaultTreeCellRenderer() {
        AkunTreeCellRenderer cell = new AkunTreeCellRenderer();
        this.setCellRenderer(cell);
    }
    
}

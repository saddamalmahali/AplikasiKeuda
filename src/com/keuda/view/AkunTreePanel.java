/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.model.Akun;
import com.keuda.services.IDBCConstant;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author user
 */
public class AkunTreePanel extends KeudaPanel implements ActionListener, TreeSelectionListener{

    
    
    AkunTree k_tree;
    
    JPopupMenu k_popup = new JPopupMenu();
    JMenuItem mi_add = new JMenuItem("Tambah Akun");
    JMenuItem mi_edit = new JMenuItem("Ubah Akun");
    JMenuItem mi_delete = new JMenuItem("Hapus Akun");
    
    String k_accFormat = ".";
    Akun k_acc = null;
    Akun[] k_accs = new Akun[0];
    
    public AkunTreePanel(MainForm mainForm){
        super(mainForm); 
        construct();
    }
    
    public void construct(){
        k_tree = new AkunTree(k_conn, k_sessionId);
        k_tree.addMouseListener(new AccountMouseAdapter());
        k_tree.addTreeSelectionListener(this);
        k_tree.setCellRenderer(new AkunTreeCellRenderer());
        
        JPanel pbtn = new JPanel();
        
        k_popup.add(mi_add);
        k_popup.add(mi_delete);
        k_popup.addSeparator();
        k_popup.add(mi_edit);
        
        
        mi_add.addActionListener(this);
        mi_delete.addActionListener(this);
        mi_edit.addActionListener(this);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(k_tree), BorderLayout.CENTER);
        
    }
    
    void insertAkun(Akun newAkun, DefaultMutableTreeNode supernode){
        DefaultTreeModel model = (DefaultTreeModel) k_tree.getModel();
        
        if(supernode == null)
            supernode = (DefaultMutableTreeNode) model.getRoot();
            
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newAkun);
        model.insertNodeInto(newNode, supernode, supernode.getChildCount());
        
        k_tree.scrollPathToVisible(new TreePath(model.getPathToRoot(newNode)));       
    }
    
    void updateAkun(Akun akun, DefaultMutableTreeNode node){
        DefaultTreeModel model = (DefaultTreeModel) k_tree.getModel();
        node.setUserObject(akun);
        model.nodeChanged(node);
    }
    
    public DefaultMutableTreeNode getRoot(){
        return (DefaultMutableTreeNode) k_tree.getModel().getRoot();
    }
    
    public void deleteNodeAkunGrup(DefaultMutableTreeNode parentNode){
        while(parentNode.getChildCount() > 0){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) parentNode.getFirstChild();
            if(((Akun)node.getUserObject()).isAkungrup() == Akun.GROUP)
                deleteNodeAkunGrup(node);
            
            deleteNodeAkun(node);
        }
    }
    
    void deleteNodeAkun(DefaultMutableTreeNode node){
        DefaultTreeModel model = (DefaultTreeModel) k_tree.getModel();
        Akun akun = (Akun) node.getUserObject();
        
        try {
            BusinessLogic logic = new BusinessLogic(k_conn);
            logic.deleteAccount(akun.getAkunIndex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            model.removeNodeFromParent(node);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Perhatian",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void getSubAKun(Vector vresult, DefaultMutableTreeNode parent){
        for(int i=0; i<parent.getChildCount(); i++){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getChildAt(i);
            
            Object objsub = node.getUserObject();
            Akun aknSub = null;
            if(objsub instanceof Akun)
                aknSub = ((Akun)objsub);
            
            vresult.addElement(objsub);
            
            getSubAKun(vresult, node);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
              
        if(e.getSource() == mi_add){
            DefaultTreeModel model = (DefaultTreeModel) k_tree.getModel();
            TreePath path = k_tree.getSelectionPath();
            if(path != null){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                if(node==model.getRoot()){
                    AkunAddDialog adddlg = new AkunAddDialog(this, null);
                    adddlg.setTitle("Tambah Golongan Barang");
                    adddlg.setLocationRelativeTo(k_mainForm);
                    adddlg.setVisible(true);
                }else{
                    String title = "";
                    
                    Akun akun = (Akun) node.getUserObject();
                    if(akun.getTipeakun() == Akun.TIPE_1)
                        title = "Penambahan Bidang Akun";
                    else if(akun.getTipeakun() == Akun.TIPE_2)
                        title = "Penambahan Kelompok Akun";
                    else if(akun.getTipeakun() == Akun.TIPE_3)
                        title = "Penambahan Sub Kelompok Akun";
                    else if(akun.getTipeakun() == Akun.TIPE_4)
                        title = "Penambahan Sub Sub Kelompok Barang";
                    else if(akun.getTipeakun() == Akun.TIPE_5)
                        title = "Penambahan Sub Sub Sub Kelompok Barang";
                    
                    
                    AkunAddDialog adddlg = new AkunAddDialog(this, node);
                    
                    adddlg.setTitle(title);
                    adddlg.setLocationRelativeTo(k_mainForm);
                    adddlg.setVisible(true);
                }
            }
        }else if(e.getSource() == mi_edit){
            TreePath path = k_tree.getSelectionPath();
            if(path != null){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                AkunEditDialog editdlg = new AkunEditDialog(this, node);
                editdlg.setLocationRelativeTo(k_mainForm);
                editdlg.setVisible(true);
            }
        }else if(e.getSource() == mi_delete){
            TreePath path = k_tree.getSelectionPath();
            if(path != null){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object[] options = {"Ya", "Tidak"};
                int choise = JOptionPane.showOptionDialog(k_mainForm, "Anda Yakin Menghapus "+node+" ini ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                
                if(choise == JOptionPane.YES_OPTION){
                    if(((Akun)node.getUserObject()).isAkungrup()== Akun.GROUP)
                        deleteNodeAkunGrup(node);
                    
                    deleteNodeAkun(node);
                }
            }
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getNewLeadSelectionPath();
        if(path == null)
            return;
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if(node.getUserObject() instanceof Akun){
            k_acc = (Akun) node.getUserObject();
        }
        
                
    }
    
    class AccountMouseAdapter extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.isPopupTrigger()){
                DefaultTreeModel model = (DefaultTreeModel) k_tree.getModel();
                TreePath path = k_tree.getSelectionPath();
                if(path != null){
                    Rectangle rectangle = k_tree.getPathBounds(path);
                    
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if(node == model.getRoot()){
                        mi_add.setEnabled(true);
                        mi_edit.setEnabled(false);
                        mi_delete.setEnabled(false);
                    }else{
                        Akun akun = (Akun) node.getUserObject();
                        if(akun.isAkungrup() == Akun.GROUP){
                            mi_add.setEnabled(true);
                            mi_edit.setEnabled(true);
                            mi_delete.setEnabled(true);
                        }else{
                            mi_add.setEnabled(false);
                            mi_edit.setEnabled(true);
                            mi_delete.setEnabled(true);
                        }
                    }
                    
                    if(rectangle.contains(e.getPoint()))
                        k_popup.show(k_tree, e.getX(), e.getY());
                }
            }
        }
        
        public void singleMouseReleased(MouseEvent e){
            if(e.isPopupTrigger()){
                DefaultTreeModel model = (DefaultTreeModel) k_tree.getModel();
                
                TreePath path = k_tree.getSelectionPath();
                
                if(path != null){
                    Rectangle rectangle = k_tree.getPathBounds(path);
                    
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if(node.isRoot()){
                        mi_add.setText("Tambah Golongan Akun");
                        mi_edit.setText("Ubah Master Akun");
                        mi_delete.setText("Hapus Master Akun");
                        
                        mi_add.setEnabled(true);
                        mi_edit.setEnabled(false);
                        mi_delete.setEnabled(false);
                        
                    }else{
                        Akun akun = (Akun) node.getUserObject();
                        if(akun.getTipeakun() == Akun.TIPE_1){
                            mi_add.setText("Tambah Bidang Barang");
                            mi_edit.setText("Ubah Golongan  Barang");
                            mi_delete.setText("Hapus Golongan Barang");
							
                        }else if(akun.getTipeakun() == Akun.TIPE_2){
                            mi_add.setText("Tambah Kelompok Akun");
                            mi_edit.setText("Ubah Bidang Akun");
                            mi_delete.setText("Hapus Bidang Akun");
							
                        }else if(akun.getTipeakun() == Akun.TIPE_3){
                            mi_add.setText("Tambah Sub Kelompok Akun");
                            mi_edit.setText("Ubah Kelompok Akun");
                            mi_delete.setText("Hapus Kelompok Akun");
                        }else if(akun.getTipeakun() == Akun.TIPE_4){
                            mi_add.setText("Tambah Sub Sub Kelompok Akun");
                            mi_edit.setText("Ubah Sub Kelompok Akun");
                            mi_delete.setText("Hapus Sub Kelompok Akun");
                        }else if(akun.getTipeakun() == Akun.TIPE_5){
                            mi_add.setText("Tambah Master Akun");
                            mi_edit.setText("Ubah Sub Sub Kelompok Akun");
                            mi_delete.setText("Hapus Sub Sub Kelompok Akun");
                        }
                        
                        if(akun.isAkungrup() == Akun.GROUP){
                            mi_add.setEnabled(true);
                            mi_edit.setEnabled(true);
                            mi_delete.setEnabled(true);
                        }else{
                            mi_add.setEnabled(false);
                            mi_edit.setEnabled(true);
                            mi_delete.setEnabled(true);
                        }
                    }
                    if(rectangle.contains(e.getPoint()))
                        k_popup.show(k_tree, e.getX(), e.getY());
                }
            }
        }
            
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.Kegiatan;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.ProgramDipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.ColumnGroup;
import com.keuda.util.GroupableTableHeader;
import com.keuda.util.KeudaPanelButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author user
 */
public class KegiatanDipaPanel extends KeudaPanelButton implements ListSelectionListener {
    JTable k_table;
    KegiatanDipaTableModel k_tmodel;
    int k_rowIndex = -1;
    KegiatanDipaPanelDetail k_dipaPanelDetail;
    protected KegiatanDipa k_kedip;
    
    ProgramDipa k_prodi;
    Kegiatan k_kegiatan;
    
    
    boolean isNew = false;
    
    public KegiatanDipaPanel(MainForm owner) throws KeudaException {
        super(owner);
        
                
        k_tmodel = new KegiatanDipaTableModel();
        
        k_table = new JTable(k_tmodel){

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
            
        };
        
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();
        TableColumnModel cm = k_table.getColumnModel();
        
        ColumnGroup cg = new ColumnGroup("Program Dipa");
        ColumnGroup cgProgram = new ColumnGroup("Program");
        cgProgram.add(cm.getColumn(0));
        cgProgram.add(cm.getColumn(1));
        cg.add(cgProgram);
        cg.add(cm.getColumn(2));
        header.addColumnGroup(cg);
        
        cg = new ColumnGroup("Kegiatan");
        cg.add(cm.getColumn(3));
        cg.add(cm.getColumn(4));
        header.addColumnGroup(cg);
        
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        
        k_table.getColumnModel().getColumn(1).setMinWidth(300);
        k_table.getColumnModel().getColumn(1).setMaxWidth(300);
        
        k_table.getColumnModel().getColumn(2).setMinWidth(100);
        k_table.getColumnModel().getColumn(2).setMaxWidth(100);
        
        k_table.getColumnModel().getColumn(3).setMinWidth(100);
        k_table.getColumnModel().getColumn(3).setMaxWidth(100);
        k_table.getSelectionModel().addListSelectionListener(this);
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_table.setRowHeight(30);
        k_panelButton.setState(k_panelButton.NEW_STATE);
        
        k_dipaPanelDetail = new KegiatanDipaPanelDetail(k_mainForm);
        init();
        getContentPane().add(k_dipaPanelDetail, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(k_table));
               
    }
    
    void init() throws KeudaException{
        k_tmodel.setRowCount(0);
        k_logic = new BusinessLogic(k_conn);
        KegiatanDipa[] kedip = k_logic.getAllKegiatanDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        
        if(kedip != null){
            for(int i=0; i<kedip.length; i++){
                KegiatanDipa kd = kedip[i];
                Kegiatan kegiatan = kedip[i].getK_kegiatan();
                ProgramDipa prodi = kedip[i].getProgramDipa();
                kd.setView(KegiatanDipa.VIEW_CODE_PROGRAM);
                k_tmodel.addRow(new Object[]{kd, kd.getProgramDipa().getProgram().getProgramName(),
                                            kd.getProgramDipa().getTahunAnggaran(), 
                                            kd.getK_kegiatan().getKegiatanCode(), kd.getK_kegiatan().getKegiatanName()});
            }
        }
    }
    
    
    
    @Override
    public void onNew() {
        k_panelButton.setState(k_panelButton.AFTER_NEW_STATE);
        k_table.clearSelection();
        k_kegiatan = new Kegiatan();
        k_prodi = new ProgramDipa();
        k_dipaPanelDetail.buttonSetEnable(true);
        k_dipaPanelDetail.clearData();
        isNew = true;
    }

    @Override
    public void onEdit() {
        k_panelButton.setState(k_panelButton.AFTER_NEW_STATE);
        isNew = false;
        k_table.setEnabled(false);
        k_dipaPanelDetail.buttonSetEnable(true);
    }

    @Override
    public void onCancel() {
        k_dipaPanelDetail.clearData();
        k_panelButton.setState(k_panelButton.NEW_STATE);
        k_table.setEnabled(true);
        k_dipaPanelDetail.buttonSetEnable(false);
    }

    @Override
    public void onDelete() {
        int config = JOptionPane.showConfirmDialog(this, "Apakah Anda Yakin Akan Menghapus "
                        + "Data Dengan Nama :\n "+k_kedip.getProgramDipa().getProgram()
                                .getProgramName(), "Perhatian", JOptionPane.OK_CANCEL_OPTION);
        if(config == JOptionPane.YES_OPTION){
            k_logic = new BusinessLogic(k_conn);
            try {
                k_logic.deleteKegiatanDipa(k_kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                init();
            } catch (KeudaException ex) {
                Logger.getLogger(KegiatanDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            k_panelButton.setState(k_panelButton.NEW_STATE);
            k_dipaPanelDetail.clearData();
        }
        
    }

    @Override
    public void onSave() {
        if(isNew){
            k_prodi = k_dipaPanelDetail.getK_prodi();
            k_kegiatan = k_dipaPanelDetail.getK_kegiatan();
            System.out.println(k_kegiatan);
            System.out.println(k_prodi);
            k_kedip = new KegiatanDipa(k_prodi, k_kegiatan);
            k_logic = new BusinessLogic(k_conn);
            try {
                k_logic.createKegiatanDipa(k_kedip, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(KegiatanDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            k_panelButton.setState(k_panelButton.NEW_STATE);
            k_table.clearSelection();
            k_table.setEnabled(true);
            k_dipaPanelDetail.clearData();
            isNew = false;
            try {
                init();
            } catch (KeudaException ex) {
                Logger.getLogger(KegiatanDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(!isNew){
            k_prodi = k_dipaPanelDetail.getK_prodi();
            k_kegiatan = k_dipaPanelDetail.getK_kegiatan();
            System.out.println(k_kegiatan);
            System.out.println(k_prodi);
            k_kedip.setK_kegiatan(k_kegiatan);
            k_kedip.setProgramDipa(k_prodi);
            k_logic = new BusinessLogic(k_conn);
            try {
                k_logic.updateKegiatanDipa(k_kedip.getKegiatandipaindex(), k_kedip, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                
            }
            k_panelButton.setState(k_panelButton.NEW_STATE);
            k_table.clearSelection();
            k_table.setEnabled(true);
            k_dipaPanelDetail.clearData();
            isNew = false;
            try {
                init();
            } catch (KeudaException ex) {
                Logger.getLogger(KegiatanDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int rowIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
            
            if(rowIndex > -1){
                k_rowIndex = rowIndex;
                
                Object obj = k_table.getValueAt(rowIndex, 0);
                
                if(obj instanceof KegiatanDipa){
                    k_kedip = (KegiatanDipa) obj;
                    System.out.println(k_kedip);
                    k_prodi = k_kedip.getProgramDipa();
                    System.out.println(k_prodi);
                    k_kegiatan = k_kedip.getK_kegiatan();
                    System.out.println(k_kegiatan);
                    k_dipaPanelDetail.setData(k_prodi, k_kegiatan);
                    k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                }
            }
            
        }
    }
    
    
    
}

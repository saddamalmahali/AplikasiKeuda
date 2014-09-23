/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.MasterVolume;
import com.keuda.services.IDBCConstant;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.PanelButton;
import java.awt.BorderLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author adam
 */
public class MasterVolumePanel extends KeudaPanelButton{
    
    JTable k_table;
    MasterVolumeTableModel k_model;
    MasterVolume k_volume;

    public MasterVolumePanel(MainForm mainFrame) {
        super(mainFrame);
        
        k_model = new MasterVolumeTableModel();
        k_table = new JTable(k_model);
        k_table.getSelectionModel().addListSelectionListener(k_model);
        k_table.getTableHeader().setReorderingAllowed(false);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.setRowHeight(20);
        k_table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField("")));
        k_table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField("")));
        k_table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField("")));
        getContentPane().add(new JScrollPane(k_table), BorderLayout.CENTER);
        
        init();
        
        k_panelButton.setState(PanelButton.NEW_STATE);
    }
    
    
    
    @Override
    public void onNew() {
        k_new = true;
        k_edit = false;
        k_model.addRow(new Object[]{"", "", ""});
        k_selectedRow = k_table.getRowCount()-1;
        k_table.setRowSelectionInterval(k_selectedRow, k_selectedRow);
        k_panelButton.setState(PanelButton.AFTER_NEW_STATE);
    }

    @Override
    public void onEdit() {
        k_selectedRow = k_table.getSelectedRow();
        if((k_selectedRow > -1) && (k_selectedRow < k_table.getRowCount())){
            k_new = false;
            k_edit = true;
            Object obj = k_table.getValueAt(k_selectedRow, 0);
            if(obj instanceof MasterVolume){
                k_volume = (MasterVolume) obj;
                k_panelButton.setState(PanelButton.AFTER_NEW_STATE);
            }else{
                k_volume = null;
            }
                     
        }else{
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan diubah");
        }
    }

    @Override
    public void onCancel() {
        if(k_table.getSelectedRow() > -1 && k_table.getSelectedRow() < k_table.getRowCount()){
            stopCellEditing();
            if(k_new){
                k_model.removeRow(k_selectedRow);
                k_table.clearSelection();
                k_panelButton.setState(PanelButton.NEW_STATE);
            }else if(k_edit){
                k_table.setValueAt(k_volume, k_selectedRow, 0);
                k_table.setValueAt(k_volume.getNamajenis(), k_selectedRow, 1);
                k_table.setValueAt(k_volume.getDescription(), k_selectedRow, 2);
                k_panelButton.setState(PanelButton.AFTER_SAVE_STATE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan dihapus");
        }
    }

    @Override
    public void onDelete() {
        k_selectedRow = k_table.getSelectedRow();
        Object obj = k_table.getValueAt(k_selectedRow, 0);
        if(obj instanceof MasterVolume){
            k_volume = (MasterVolume) obj;
            
        } else{
            k_volume = null;
        }
        
        BusinessLogic logic = new BusinessLogic(k_conn);
        try {
            logic.deleteMasterVolume(k_volume.getMastervolumeid(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            k_model.removeRow(k_selectedRow);
            k_table.clearSelection();
            k_selectedRow = -1;
            k_panelButton.setState(PanelButton.NEW_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSave() {
        stopCellEditing();
        try {
            MasterVolume master = getData();
            BusinessLogic logic = new BusinessLogic(k_conn);

            if(k_new){
                k_volume = logic.createMasterVolume(master, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            }else if(k_edit){
                k_volume = logic.updateMasterVolume(k_volume.getMastervolumeid(), master, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            }
            k_volume.setView(MasterVolume.VIEW_KODE_JENIS);
            k_table.setValueAt(k_volume, k_selectedRow, 0);
            k_table.setValueAt(k_volume.getNamajenis(), k_selectedRow, 1);
            k_table.setValueAt(k_volume.getDescription(), k_selectedRow, 2);
            
            k_new = false;
            k_edit = false;
            k_panelButton.setState(PanelButton.AFTER_SAVE_STATE);
            
        } catch (KeudaException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(k_mainForm, e.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void init(){
        try {
            BusinessLogic logic = new BusinessLogic(k_conn);
            
            MasterVolume[] results = logic.getAllMasterVolume(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            if(results != null){
                for(int i=0; i<results.length; i++){
                    MasterVolume mv = new MasterVolume(results[i]);
                    mv.setView(mv.VIEW_KODE_JENIS);
                    k_model.addRow(new Object[]{mv, mv.getNamajenis(), mv.getDescription()});
                }
            }
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private MasterVolume getData()throws KeudaException{
        short kodeVolume;
        String kode = "";
        String nama = "";
        String desc = "";
        
        kode = k_table.getValueAt(k_selectedRow, 0).toString();
        kodeVolume = Short.valueOf(kode);
        if(kode == null || kode.trim().equals(""))
            throw new KeudaException("Isikan Kode Program.\n");
        
        nama = k_table.getValueAt(k_selectedRow, 1).toString();
        if(nama == null || nama.trim().equals(""))
            throw new KeudaException("Isikan Nama Program");
        
        desc = k_table.getValueAt(k_selectedRow, 2).toString();
        
        return new MasterVolume(kodeVolume, nama, desc);
        
    }
    
    class MasterVolumeTableModel extends DefaultTableModel implements ListSelectionListener{

        public MasterVolumeTableModel() {
            addColumn("Kode Jenis");
            addColumn("Nama Jenis");
            addColumn("Deskripsi");
            
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if((k_new || k_edit) && row == k_selectedRow) return true;
            return false;
        }
        
        
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int iRowIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
                System.out.println(iRowIndex);
                if(k_new || k_edit){
                    if(iRowIndex != k_selectedRow)
                        k_table.getSelectionModel().setSelectionInterval(k_selectedRow, k_selectedRow);
                }
                else if(iRowIndex != -1){
                    k_selectedRow = iRowIndex;
                    Object obj = k_table.getValueAt(k_selectedRow, 0);
                    if(obj instanceof MasterVolume){
                        k_volume = (MasterVolume) obj;
                        k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                    }else{
                        k_volume = null;
                        k_panelButton.setState(k_panelButton.NEW_STATE);
                    }
                }
            }
        }
        
    }
    
    public void stopCellEditing(){
        TableCellEditor editor;
        if((editor = k_table.getCellEditor())!= null)
            editor.stopCellEditing();
    }
}

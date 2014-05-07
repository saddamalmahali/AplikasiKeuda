/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.Kegiatan;
import com.keuda.model.Program;
import com.keuda.services.IDBCConstant;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.PanelButton;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */

public class ProgramPanel extends KeudaPanelButton{
    
    ProgramTableModel k_tmodel;
    JTable k_table;
    Program k_program;

    public ProgramPanel(MainForm mainFrame) throws KeudaException {
        super(mainFrame);
        
        k_tmodel = new ProgramTableModel();
        k_table = new JTable(k_tmodel);
        k_table.getSelectionModel().addListSelectionListener(k_tmodel);
        k_table.getTableHeader().setReorderingAllowed(false);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.setRowHeight(20);
        k_table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField("")));
        k_table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField("")));
        k_table.setRowHeight(30);
        getContentPane().add(new JScrollPane(k_table), BorderLayout.CENTER);
        
        init();
        
        k_panelButton.setState(PanelButton.NEW_STATE);
        
    }
    
    
    
    @Override
    public void onNew() {
        k_new = true;
        k_edit = false;
        k_tmodel.addRow(new Object[]{"", ""});
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
            if(obj instanceof Program){
                k_program = ((Program)obj);
                k_panelButton.setState(PanelButton.AFTER_NEW_STATE);
            }else
                k_program = null;         
            
        }else
        JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan diubah");
        
    }

    
    public void onCancel() {
        if(k_table.getSelectedRow() > -1 && k_table.getSelectedRow() < k_table.getRowCount()){
            stopCellEditing();
            if(k_new){
                k_tmodel.removeRow(k_selectedRow);
                k_table.clearSelection();
                k_panelButton.setState(k_panelButton.NEW_STATE);
            }
            else if(k_edit){
                k_table.setValueAt(k_program, k_selectedRow, 0);
                k_table.setValueAt(k_program.getProgramName(), k_selectedRow, 1);
                k_panelButton.setState(PanelButton.AFTER_SAVE_STATE);
            }
            k_new = false;
            k_edit = false;
            
        }
        else JOptionPane.showMessageDialog(null, "Silahkan Pilih data yang akan dihapus");
    }

    @Override
    public void onDelete() {
        k_selectedRow = k_table.getSelectedRow();
        Object obj = k_table.getValueAt(k_selectedRow, 0);
        if(obj instanceof Program)
            k_program = (Program) obj;
        else
            k_program = null;
        
        BusinessLogic logic = new BusinessLogic(k_conn);
        try {
            logic.deleteProgram(k_program.getProgramIndex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            k_tmodel.removeRow(k_selectedRow);
            k_table.clearSelection();
            k_selectedRow = -1;
            k_panelButton.setState(PanelButton.NEW_STATE);
        } catch (KeudaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSave() {
        try {
            stopCellEditing();
            Program program = getData();
            BusinessLogic logic = new BusinessLogic(k_conn);
            
            if(k_new){
                k_program = logic.createProgram(program, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            }
            else if(k_edit){
                k_program = logic.updateProgram(k_program.getProgramIndex(), program, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            }
            k_program.setView(Program.VIEW_CODE);
            k_table.setValueAt(k_program, k_selectedRow, 0);
            k_table.setValueAt(k_program.getProgramName(), k_selectedRow, 1);
            k_new = false;
            k_edit = false;
            k_panelButton.setState(PanelButton.AFTER_SAVE_STATE);
        } catch (KeudaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(k_mainForm, ex.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
    
    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void stopCellEditing(){
        TableCellEditor editor;
        if((editor = k_table.getCellEditor()) != null)
            editor.stopCellEditing();
    }
    
    
    private void init() throws KeudaException {
        try{
            BusinessLogic logic = new BusinessLogic(k_conn);
            Program[] progs = logic.getAllProgram(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            if(progs != null){
                for(int i=0;  i<progs.length; i++){
                    Program prog = new Program(progs[i]);
                    prog.setView(Program.VIEW_CODE);
                    k_tmodel.addRow(new Object[]{prog, prog.getProgramName()});
                }
                
                
            }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private Program getData() throws KeudaException {
        String code = "";
        String name = "";
        
        code = k_table.getValueAt(k_selectedRow, 0).toString();
        if(code == null || code.trim().equals(""))
            throw new KeudaException("Isikan Kode Program. \n");
        
        name = k_table.getValueAt(k_selectedRow, 1).toString();
        if(name == null || name.trim().equals(""))
            throw new KeudaException("Isikan Nama Program. \n");
        
        return new Program(code, name);
    }
    
    
    class ProgramTableModel extends DefaultTableModel implements ListSelectionListener{

        public ProgramTableModel() {
            addColumn("Kode Program");
            addColumn("Nama Program");
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if((k_new||k_edit) && row == k_selectedRow) return true;
            return false;
        }
        
        
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int iRowIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
                
                if(k_new || k_edit){
                    if(iRowIndex != k_selectedRow)
                        k_table.getSelectionModel().setSelectionInterval(k_selectedRow, k_selectedRow);
                }
                else if(iRowIndex != -1){
                    k_selectedRow = iRowIndex;
                    Object obj = k_table.getValueAt(k_selectedRow, 0);
                    if(obj instanceof Program){
                        k_program = (Program) obj;
                        k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                    }else{
                        k_program = null;
                        k_panelButton.setState(k_panelButton.NEW_STATE);
                    }  
                }
            }
        }
        
    }
}

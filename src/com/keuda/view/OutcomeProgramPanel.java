/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.OutcomeProgram;
import com.keuda.services.IDBCConstant;
import com.keuda.util.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author user
 */
public class OutcomeProgramPanel extends KeudaPanelButton implements ListSelectionListener{
    JTable k_table;
    OutcomeTableModel k_model;
//    OutcomeTableComponent k_component;
//    OutcomeTableRenderer k_tableRender;
//    OutcomeTableEditor k_editor;
    JPanel k_outcomePanel;
    public OutcomeProgram k_outProgram;
    public int k_selectedRow = -1;
//    JButton btn_tambah, btn_keluar, btn_print;
    
    OutcomeProgramDialog k_outProgDialog;
    public static MainForm mainform;
    OutcomeProgramDialog k_dialog;
    public OutcomeProgramPanel(MainForm owner) throws KeudaException {
        
        super(owner);       
        mainform = owner;
        k_outProgram = new OutcomeProgram();
        k_model = new OutcomeTableModel();
        
        
        
//        k_tableRender = new OutcomeTableRenderer();
//        k_editor = new OutcomeTableEditor(); 
        k_table = new JTable(k_model){

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
            
        };
//        k_table.getColumnModel().getColumn(4).setCellEditor(k_editor);
//        k_table.getColumnModel().getColumn(4).setCellRenderer(k_tableRender);
        
//        k_table.getColumnModel().getColumn(4).setMaxWidth(200);
//        k_table.getColumnModel().getColumn(4).setMinWidth(200);
        
//        k_table.getColumnModel().getColumn(4).setCellEditor(new OutcomeTextAreaEditor());
//        k_table.getColumnModel().getColumn(4).setCellRenderer(new OutcomeCellTextAreaRender());
        
        TableColumnModel cm = k_table.getColumnModel();
        
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();
        
        ColumnGroup cg = new ColumnGroup("Program DIPA");
        
        cg.add(cm.getColumn(0));
        cg.add(cm.getColumn(1));
        cg.add(cm.getColumn(2));       
        
        header.addColumnGroup(cg);
        
        k_table.setRowHeight(30);
        k_table.getColumnModel().setColumnSelectionAllowed(false);
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        
        k_table.getColumnModel().getColumn(1).setMinWidth(300);
        k_table.getColumnModel().getColumn(1).setMaxWidth(300);
        
        k_table.getColumnModel().getColumn(3).setMinWidth(650);
        k_table.getColumnModel().getColumn(3).setMaxWidth(650);
        
        k_table.getColumnModel().getColumn(2).setMinWidth(100);
        k_table.getColumnModel().getColumn(2).setMaxWidth(100);
        
        k_table.getColumnModel().getColumn(3).setMinWidth(100);
        k_table.getColumnModel().getColumn(3).setMaxWidth(100);
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_table.getSelectionModel().addListSelectionListener(this);
        getContentPane().add(new JScrollPane(k_table));
        k_table.getTableHeader().setReorderingAllowed(false);
        init();
//        k_panelButton = new JPanel(new FlowLayout());
//        JPanel panelButton = new JPanel(new BorderLayout());
//        btn_tambah = new JButton("Tambah");
//        btn_keluar = new JButton("Keluar");
//        btn_print = new JButton("Print");
//        btn_tambah.addActionListener(this);
//        k_panelButton.add(btn_tambah);
//        k_panelButton.add(btn_print);
//        k_panelButton.add(btn_keluar);
//        panelButton.add(k_panelButton, BorderLayout.EAST);
//        panelButton.setBorder(BorderFactory.createEtchedBorder());
//        getContentPane().add(panelButton, BorderLayout.NORTH);
        constructComponents();
        k_logic = new BusinessLogic(k_conn);
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    
    public void constructComponents(){
        
    }
    
    public void init() throws KeudaException{
        k_model.setRowCount(0);
        k_logic = new BusinessLogic(k_conn);
        OutcomeProgram[] outProg = k_logic.getAllOutcomeProdi(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        for(int i = 0; i<outProg.length; i++){
            OutcomeProgram op = outProg[i];
            
            int noUrut = op.getNmrUrut();
            String noUrutKecil = "";
            
            if(noUrut<10){
                noUrutKecil = "0"+noUrut;
            }else{
                noUrutKecil = ""+noUrut;
            }
            
            op.setView(OutcomeProgram.VIEW_CODE_PROGRAM);
            k_model.addRow(new Object[]{op, op.getProgramDipa().getProgram(), op.getProgramDipa().getTahunAnggaran(), noUrutKecil, op.getOutcome()});
        }
        
    }
    
    public List getData(){
        OutcomeProgram[] data = null;
        List<OutcomeProgram> list = new ArrayList<>();
        data = getDataArray();
        
        for(int i = 0; i < data.length; i++){
            list.add(data[i]);
        }
        
        return list;
    }

    public static MainForm getMainform() {
        return mainform;
    }
    
    
    
    public MainForm getK_mainForm() {
        return k_mainForm;
    }
    
    
    
    public OutcomeProgram[] getDataArray(){
        OutcomeProgram[] data = null;
        
        try {
            data = k_logic.getAllOutcomeProdi(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        } catch (KeudaException ex) {
            Logger.getLogger(OutcomeProgramPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }

    public void setK_outProgram(OutcomeProgram k_outProgram) {
        this.k_outProgram = k_outProgram;
    }

    public OutcomeProgram getK_outProgram() {
        return k_outProgram;
    }
    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        int rowIndex =((ListSelectionModel)e.getSource()).getMinSelectionIndex();
        if(!e.getValueIsAdjusting()){
            if(rowIndex != -1){
                k_selectedRow = rowIndex;
                Object obj = k_table.getValueAt(k_selectedRow, 0);
                
                if(obj instanceof OutcomeProgram){
                    k_outProgram = (OutcomeProgram) obj;
                    k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                }else{
                    System.out.println("Tidak ada objek yang di konvert");
                }
                
                
            }
        }
    }

    

    @Override
    public void onNew() {
       
            k_dialog = new OutcomeProgramDialog(this, k_new);
            k_dialog.setLocationRelativeTo(null);
            k_dialog.setVisible(true);           
        
    }

    @Override
    public void onEdit() {
       
            k_dialog = new OutcomeProgramDialog(this, k_new, k_outProgram);
            k_dialog.setVisible(true);
        
    }

    @Override
    public void onCancel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDelete() {
        int hapus = -1;
        hapus = JOptionPane.showConfirmDialog(k_outcomePanel, "Apakah Anda Akan Menghapus \"\n"+k_outProgram.getOutcome()+" \" ?", "Peringatan", JOptionPane.OK_CANCEL_OPTION);
        if(hapus == JOptionPane.YES_OPTION){
            k_logic = new BusinessLogic(k_conn);
            try {
                k_logic.deleteOutcomeProdi(k_outProgram, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                
                init();
                k_panelButton.setState(k_panelButton.NEW_STATE);
            } catch (KeudaException ex) {
                Logger.getLogger(OutcomeProgramPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else if(hapus == JOptionPane.CANCEL_OPTION){
            return ;
        }
    }
    
    public void save(OutcomeProgram program, boolean add) throws KeudaException{
        if(add){
            k_logic.createOutComeProdi(program, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            init();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }else{
            k_logic.updateOutcomeProdi(program, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            init();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }
        
        
    }
    
    @Override
    public void onSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    
}

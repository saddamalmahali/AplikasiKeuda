/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.IkuProgram;
import com.keuda.model.ProgramDipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.ColumnGroup;
import com.keuda.util.GroupableTableHeader;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.PanelButtonUtil;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import pohaci.gumunda.cgui.DoubleCellEditor;
import pohaci.gumunda.cgui.DoubleCellRenderer;

/**
 *
 * @author user
 */
public class IkuProgramPanel extends KeudaPanelButton implements ListSelectionListener{
    
    MainForm k_mainForm;
    IkuProgram k_iku;
    Connection k_conn;
    long sessionId;
    protected JTable k_table;
    TableIkuProgramModel model;
    IkuProgramDialog k_ikuProgramDialog;  
    PanelButtonUtil k_panelUtil;
    boolean add = false;
    
    public IkuProgramPanel(MainForm mainForm) throws KeudaException {
        super(mainForm);
        
        k_mainForm = mainForm;
        k_conn = k_mainForm.k_conn;
        sessionId = k_mainForm.k_sessionId;    
        model = new TableIkuProgramModel();
        k_table = new JTable(model){
            
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        
        TableColumnModel cm = k_table.getColumnModel();
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();
        ColumnGroup cg = new ColumnGroup("Program DIPA");
        
        cg.add(cm.getColumn(0));
        cg.add(cm.getColumn(1));
        cg.add(cm.getColumn(2));
        
        
        header.addColumnGroup(cg);
        
        
        
        
//        k_table.getTableHeader().setReorderingAllowed(false);
        k_table.getSelectionModel().addListSelectionListener(this);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(1).setMaxWidth(400);
        k_table.getColumnModel().getColumn(1).setMinWidth(400);
        
        k_table.getColumnModel().getColumn(2).setMaxWidth(100);
        k_table.getColumnModel().getColumn(2).setMinWidth(100);
        
        k_table.getColumnModel().getColumn(3).setMaxWidth(100);
        k_table.getColumnModel().getColumn(3).setMinWidth(100);
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        k_table.getColumnModel().getColumn(2).setCellEditor(new DoubleCellEditor());
//        k_table.getColumnModel().getColumn(2).setCellRenderer(new DoubleCellRenderer(JLabel.RIGHT));
        k_table.setRowHeight(30);
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        getContentPane().add(new JScrollPane(k_table));
        
        init();
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    
    
    @Override
    public void onNew() {
        k_ikuProgramDialog = new IkuProgramDialog(this, true);
        k_ikuProgramDialog.setVisible(true);
    }

    @Override
    public void onEdit() {
        k_ikuProgramDialog = new IkuProgramDialog(k_iku, this, true);
        k_ikuProgramDialog.setVisible(true);
    }

    @Override
    public void onCancel() {
        
    }

    @Override
    public void onDelete() {
        int hapus = -1;
        hapus = JOptionPane.showConfirmDialog(this, "Apakah anda yakin akan menghapus \n"+k_iku+" ? ", "Peringatan", JOptionPane.YES_NO_OPTION);
        
        if(hapus == JOptionPane.YES_OPTION){
            try {
                k_logic.deleteIkuProgram(k_iku.getProgramDipa(), k_iku.getNrIku(), sessionId, IDBCConstant.MODUL_CONFIGURATION);
                init();
                k_panelButton.setState(k_panelButton.NEW_STATE);
            } catch (KeudaException ex) {
                Logger.getLogger(IkuProgramPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            
            return ;
            
        }
    }

    @Override
    public void onSave() {
        
    }
    
    public void save(IkuProgram iprog, boolean add) throws KeudaException{
        if(add){
            k_iku = k_logic.createIkuProgram(iprog, sessionId, IDBCConstant.MODUL_CONFIGURATION);
            init();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }else{
            k_iku = k_logic.updateIku(iprog, sessionId, IDBCConstant.MODUL_CONFIGURATION);
            init();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }
    }
    
    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int iRowIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
            IkuProgram iku = new IkuProgram();
            if(iRowIndex != -1){
                Object obj = k_table.getValueAt(iRowIndex, 0);
                if(obj instanceof IkuProgram){
                    iku = (IkuProgram) obj;
                    k_iku = iku;
                    k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                }
                
            }
            
            
        }
    }

    private void init() throws KeudaException {
        model.setRowCount(0);
        k_logic = new BusinessLogic(k_conn);
        
        IkuProgram[] ikus = k_logic.getAllIkuProgram(sessionId, IDBCConstant.MODUL_CONFIGURATION);
        
        for(int i=0; i<ikus.length; i++){
            IkuProgram iprog = ikus[i];
            int noUrut = iprog.getNrIku();
            String noUrutKecil = "";
            iprog.getDipa().setView(ProgramDipa.VIEW_PROGRAM);
            iprog.setView(iprog.VIEW_CODE_PROGRAM);
            if(noUrut < 10){
                noUrutKecil = "0"+noUrut;
            }else{
                noUrutKecil = ""+noUrut;
            }
            model.addRow(new Object[]{iprog, iprog.getDipa().getProgram(), iprog.getDipa().getTahunAnggaran(), noUrutKecil, iprog.getIku()});
        }
        
    }
    
    public class TableIkuProgramModel extends DefaultTableModel{

        public TableIkuProgramModel() {
            addColumn("Kode Program");
            addColumn("Program Dipa");
            addColumn("Tahun");
            addColumn("Nomor Iku");
            addColumn("IKU Program");
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }
    
}

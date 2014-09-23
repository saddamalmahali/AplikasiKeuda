/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.OutputKegiatan;
import com.keuda.services.IDBCConstant;
import com.keuda.util.ColumnGroup;
import com.keuda.util.GroupableTableHeader;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.OutcomeCellTextAreaRender;
import com.keuda.util.OutcomeTableEditor;
import com.keuda.util.OutcomeTextAreaEditor;
import com.keuda.util.TextAreaCellEditor;
import com.keuda.util.TextAreaCellRenderer;
import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author client
 */
public class OutputKegiatanPanel extends KeudaPanelButton implements ListSelectionListener{
    
    OutputKegiatan k_outke;
    JTable k_table;
    private OutputKegiatanTableModel k_tmodel;
    
    int iRowIndex = -1;
    
    OutputKegiatanCreateUpdate k_dialogInsert;
    
    public OutputKegiatanPanel(MainForm mainForm) throws KeudaException {
        super(mainForm);
        
        k_tmodel = new OutputKegiatanTableModel();
        
        k_table = new JTable(k_tmodel){
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        
//        k_table.getColumnModel().getColumn(1).setCellEditor(new OutcomeTextAreaEditor());
//        k_table.getColumnModel().getColumn(1).setCellRenderer(new OutcomeCellTextAreaRender());
        
        TableColumnModel cm = k_table.getColumnModel();
       
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();

        ColumnGroup cg = new ColumnGroup("Kegiatan DIPA");
        ColumnGroup cg2 = new ColumnGroup("Program DIPA");
        ColumnGroup cg3 = new ColumnGroup("Kegiatan");

        cg2.add(cm.getColumn(0));
        cg2.add(cm.getColumn(1));
        cg2.add(cm.getColumn(2));
        cg3.add(cm.getColumn(3));
        cg3.add(cm.getColumn(4));

        cg.add(cg2);
        cg.add(cg3);
       
        header.addColumnGroup(cg);
        
        
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(1).setMaxWidth(200);
        k_table.getColumnModel().getColumn(1).setMinWidth(200);
        k_table.getColumnModel().getColumn(2).setMaxWidth(100);
        k_table.getColumnModel().getColumn(2).setMinWidth(100);
        
        k_table.getColumnModel().getColumn(3).setMaxWidth(100);
        k_table.getColumnModel().getColumn(3).setMinWidth(100);
        k_table.getColumnModel().getColumn(4).setMaxWidth(200);
        k_table.getColumnModel().getColumn(4).setMinWidth(200);
        
        k_table.getColumnModel().getColumn(5).setMaxWidth(100);
        k_table.getColumnModel().getColumn(5).setMinWidth(100);
        
        
        k_table.setColumnSelectionAllowed(false);
//        
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_table.getSelectionModel().addListSelectionListener(this);
        k_table.setRowHeight(30);
        getContentPane().add(new JScrollPane(k_table));
        k_panelButton.setState(k_panelButton.NEW_STATE);
        
        init();
    }
    
    void init() throws KeudaException{
        k_tmodel.setRowCount(0);
        k_logic = new BusinessLogic(k_conn);
        OutputKegiatan[] listOutput = k_logic.getAllOutputKegiatan(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        if(listOutput != null){
            for(int i=0; i<listOutput.length; i++){
                OutputKegiatan ouke = listOutput[i];
                String nrOut = "";
                String urut = "";
                
                if(ouke.getNroutput() < 10 && ouke.getNroutput()>0){
                    urut = "00"+ouke.getNroutput();
                }else if(ouke.getNroutput() >=10 && ouke.getNroutput() < 100){
                    urut = "0"+ouke.getNroutput();
                }
                nrOut = ouke.getK_kedip().getK_kegiatan().getKegiatanCode()+"."+urut;
                ouke.setView(OutputKegiatan.VIEW_KEGIATAN_DIPA_CODE_PROGRAM);
                k_tmodel.addRow(new Object[]{ouke, ouke.getK_kedip().getProgramDipa().getProgram().getProgramName(),
                                ouke.getK_kedip().getProgramDipa().getTahunAnggaran(), ouke.getK_kedip().getK_kegiatan().getKegiatanCode(),
                                ouke.getK_kedip().getK_kegiatan().getKegiatanName(), nrOut, ouke.getOutput()});
            }
        }
    }
    
    @Override
    public void onNew() {
        k_dialogInsert = new OutputKegiatanCreateUpdate(this, true);
        k_table.clearSelection();
        k_dialogInsert.setLocationRelativeTo(null);
        k_dialogInsert.setVisible(true);
    }

    @Override
    public void onEdit() {
        k_dialogInsert = new OutputKegiatanCreateUpdate(this, k_outke);
        k_dialogInsert.setLocationRelativeTo(null);
        k_dialogInsert.setVisible(true);
    }

    @Override
    public void onCancel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda Yakin akan Menghapus Data dengan Kode : "+k_outke.getNroutput(), "Perhatian", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            try {
                k_logic.deleteOutputKegiatan(k_outke.getK_kedip().getKegiatandipaindex(), 
                                    k_outke.getNroutput(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                init();
            } catch (KeudaException ex) {
                Logger.getLogger(OutputKegiatanPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            k_table.clearSelection();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }
    }

    @Override
    public void onSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void save(OutputKegiatan ouke, boolean isNew) throws KeudaException{
        System.out.println(ouke.getK_kedip().getDipaindex());
        System.out.println(isNew);
        if(isNew){
            k_logic.createOutputKegiatan(ouke, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        }else{
            k_logic.updateOutputKegiatan(ouke, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        }
        
        init();
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int rowIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
            if(rowIndex > -1){
                iRowIndex = rowIndex;
                Object obj = k_table.getValueAt(rowIndex, 0);
                if(obj instanceof OutputKegiatan){
                    OutputKegiatan ouke = (OutputKegiatan) obj;
                    k_outke = ouke;
                    k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                }
            }
        }
    }
    
    public class OutputKegiatanTableModel extends DefaultTableModel{

        public OutputKegiatanTableModel() {
            
            addColumn("Kode"); //0
            addColumn("Nama"); //1
            addColumn("Tahun Anggaran"); //2
            addColumn("Kode"); //3
            addColumn("Nama");//4
            addColumn("<html>Nomor<br>Urut</html>"); //5
            addColumn("<html>Output<br>kegiatan</html>"); //6
            
        }
        
    }
    
}

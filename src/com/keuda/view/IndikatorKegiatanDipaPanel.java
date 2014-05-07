/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.IndikatorKegiatan;
import com.keuda.model.KegiatanDipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.*;
import java.awt.*;
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
public class IndikatorKegiatanDipaPanel extends KeudaPanelButton implements ListSelectionListener{
    
    private IndikatorKegiatanDipaPanel k_ike;
    private JTable k_table;
    private JPanel k_headerPanel;
    private JTextField k_txtnrindikator, k_txtProgramDipa, k_txtKegiatanMaster;
    private JTextArea k_txtIndikatorDetail;
    private JButton btn_brwProgramDipa, btn_brwKegiatanDipa;
    private JLabel lbl_programDipa, lbl_kegiatan, lbl_nrIndikator, lbl_indikatorDelail;
    
    
    IndikatorKegiatan k_ikegiatan;
    
    IndikatorKegiatanCreateUpdate k_dialogInsert;
    IndikatorKegiatanDipaTableModel k_tmodel;
    int k_selectedRow = -1;
    int row = 0;
    
    boolean isEditable = false;
    public IndikatorKegiatanDipaPanel(MainForm owner) throws KeudaException {
        super(owner);
       k_tmodel = new IndikatorKegiatanDipaTableModel();
       
       k_table = new JTable(k_tmodel){

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
           
       };
       
       
       k_ikegiatan = new IndikatorKegiatan();
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
        
        getContentPane().add(new JScrollPane(k_table), BorderLayout.CENTER);
        
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
        
        
        k_table.getColumnModel().getColumn(1).setCellEditor(new OutcomeTextAreaEditor());
        k_table.getColumnModel().getColumn(1).setCellRenderer(new OutcomeCellTextAreaRender());
        
        k_table.getColumnModel().getColumn(4).setCellEditor(new OutcomeTextAreaEditor());
        k_table.getColumnModel().getColumn(4).setCellRenderer(new OutcomeCellTextAreaRender());
        
        k_table.getColumnModel().getColumn(6).setCellEditor(new OutcomeTextAreaEditor());
        k_table.getColumnModel().getColumn(6).setCellRenderer(new OutcomeCellTextAreaRender());
        k_table.getTableHeader().setReorderingAllowed(false);
        k_table.setRowHeight(40);
        k_table.getSelectionModel().addListSelectionListener(this);
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        k_table.setCellSelectionEnabled(true);
        constructComponent();
        init();
    }
    
    
    void init() throws KeudaException{
        k_tmodel.setRowCount(0);
        
        k_logic = new BusinessLogic(k_conn);
        System.out.println("Mengambil Data");
        IndikatorKegiatan[] ike = k_logic.getAllIndikatorKegiatan(k_sessionId, TOOL_TIP_TEXT_KEY);
        
        if(ike != null){
            
            for(int i=0; i<ike.length; i++){
                
                IndikatorKegiatan indikator = ike[i];
                indikator.setView(IndikatorKegiatan.VIEW_PROGRAM_CODE);
//                System.out.println(indikator);
//                System.out.println(indikator.getK_kedip().getProgramDipa().getProgram().getProgramName());
//                System.out.println(indikator.getK_kedip().getProgramDipa().getTahunAnggaran());
//                System.out.println(indikator.getK_kedip().getK_kegiatan().getKegiatanCode());
//                System.out.println(indikator.getK_kedip().getK_kegiatan().getKegiatanName());
//                System.out.println(indikator.getNrindikator());
//                System.out.println(indikator.getIndikator());
                
                String nomorUrut ="";
                if(indikator.getNrindikator() < 10 && indikator.getNrindikator() >0){
                    nomorUrut = "00"+indikator.getNrindikator();
                }else if(indikator.getNrindikator() >= 10 && indikator.getNrindikator()<100){
                    nomorUrut = "0"+indikator.getNrindikator();
                }else if(indikator.getNrindikator() > 100){
                    nomorUrut = ""+indikator.getNrindikator();
                }else{
                    nomorUrut = "";
                }
                
                k_tmodel.addRow(new Object[]{indikator, indikator.getK_kedip().getProgramDipa().getProgram().getProgramName(),
                indikator.getK_kedip().getProgramDipa().getTahunAnggaran(), indikator.getK_kedip().getK_kegiatan().getKegiatanCode(),
                indikator.getK_kedip().getK_kegiatan().getKegiatanName(), nomorUrut, indikator.getIndikator()});
            }
        }
        
    }
    
    
    @Override
    public void onNew() {
        
        isEditable = true;
        k_dialogInsert = new IndikatorKegiatanCreateUpdate(this, true);
        k_dialogInsert.setLocationRelativeTo(null);
        k_dialogInsert.setVisible(true);
        
        k_table.getSelectionModel().clearSelection();
        
    }

    @Override
    public void onEdit() {
        k_dialogInsert = new IndikatorKegiatanCreateUpdate(this, k_ikegiatan);
        k_dialogInsert.setLocationRelativeTo(null);
        k_dialogInsert.setVisible(true);
    }

    @Override
    public void onCancel() {
        isEditable = false;
        k_table.setEnabled(true);
//        k_table.getSelectionModel().removeIndexInterval(k_selectedRow, k_selectedRow);
        
//        if(k_selectedRow != -1){
//            k_table.getSelectionModel().removeIndexInterval(k_selectedRow, k_selectedRow);
//        }
        k_table.clearSelection();
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }

    @Override
    public void onDelete() {
        if(k_ikegiatan == null){
            JOptionPane.showMessageDialog(this, "Tidak Ada Data yang Dipilih", "Perhatian", JOptionPane.ERROR_MESSAGE);
        }else{
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin akan menghapus Indikator dengan kode "+k_ikegiatan.getNrindikator(), "Perhatian", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                try {
                    k_logic.deleteIndikatorKegiatan(k_ikegiatan.getK_kedip().getKegiatandipaindex(), k_ikegiatan.getNrindikator(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                    init();
                } catch (KeudaException ex) {
                    Logger.getLogger(IndikatorKegiatanDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                isEditable = false;
                k_table.setEnabled(true);
        //        k_table.getSelectionModel().removeIndexInterval(k_selectedRow, k_selectedRow);

        //        if(k_selectedRow != -1){
        //            k_table.getSelectionModel().removeIndexInterval(k_selectedRow, k_selectedRow);
        //        }
                k_table.clearSelection();
                k_panelButton.setState(k_panelButton.NEW_STATE);
            }
        }
    }
    
    public boolean compare(KegiatanDipa kedip) throws KeudaException{
        boolean compare= false;
        int count = -1;
        
        KegiatanDipa[] objs = k_logic.getAllKegiatanDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        
        for(int i=0; i<objs.length; i++){
            if(objs[i].getDipaindex() != kedip.getDipaindex() && objs[i].getKegiatan() != kedip.getKegiatan()){
                count = 1;
            }else count = -1;
        }
        
        if(count == 1)
            compare = true;
        else
            compare = false;
        
        return compare;
    }
    
    
    @Override
    public void onSave() {
        System.out.println(k_ikegiatan.getKegiatandipa());
        if(k_ikegiatan != null){
            try {
                KegiatanDipa[] kedip = k_logic.getAllKegiatanDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                boolean same = compare(k_ikegiatan.getK_kedip());
                
                System.out.println(same);
                
//                if(same){
                    k_logic.createKegiatanDipa(k_ikegiatan.getK_kedip(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                }
                
                System.out.println(k_ikegiatan.getKegiatandipa());
                k_logic.createIndikatorKegiatan(k_ikegiatan, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                init();
            } catch (KeudaException ex) {
                Logger.getLogger(IndikatorKegiatanDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            isEditable = false;
            k_table.setEnabled(true);
    //        k_table.getSelectionModel().removeIndexInterval(k_selectedRow, k_selectedRow);

    //        if(k_selectedRow != -1){
    //            k_table.getSelectionModel().removeIndexInterval(k_selectedRow, k_selectedRow);
    //        }
            k_table.clearSelection();
            
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }
        
    }

    @Override
    public void onPrint() {
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
        if(!e.getValueIsAdjusting()){
            k_selectedRow = rowIndex;
            if(rowIndex != -1){
             Object obj = k_table.getValueAt(k_selectedRow, 0);
             if(obj instanceof IndikatorKegiatan){
                 k_ikegiatan = (IndikatorKegiatan) obj;
                 k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
             }
             
             
             
            }
        }
    }
    
    public void save(IndikatorKegiatan inke, boolean isNew) throws KeudaException{
        if(isNew){
            System.out.println(inke.getKegiatandipa());
            System.out.println(inke.getNrindikator());
            System.out.println(inke.getIndikator());
            k_logic.createIndikatorKegiatan(inke, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        }else if(!isNew){
            k_logic.updateIndikatorKegiatan(inke, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        }
        init();
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    private void constructComponent() {
        
//        k_txtnrindikator = new JTextField();
//        k_txtIndikatorDetail = new JTextArea();
//        k_txtKegiatanMaster = new JTextField();
//        k_txtProgramDipa = new JTextField();
//        
//        
//        k_txtProgramDipa.setEditable(false);
//        k_txtKegiatanMaster.setEditable(false);
//        k_txtIndikatorDetail.setEditable(false);
//        k_txtnrindikator.setEditable(false);
//        
//        lbl_programDipa = new JLabel("Program DIPA :");
//        lbl_kegiatan = new JLabel("Kegiatan :");
//        lbl_nrIndikator = new JLabel("Nomor Urut :");
//        lbl_indikatorDelail = new JLabel("Detail Indikator :");
//        
//        
//        btn_brwProgramDipa = new JButton("...");
//        btn_brwKegiatanDipa = new JButton("...");
//        
//        k_headerPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints constraint = new GridBagConstraints();
//        constraint.anchor = GridBagConstraints.WEST;
//        
//        Misc.setGridBagConstraints(k_headerPanel, lbl_programDipa, constraint, 0, row, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, new Insets(10, 5, 10, 5));
//        Misc.setGridBagConstraints(k_headerPanel, k_txtProgramDipa, constraint, 1, row, GridBagConstraints.HORIZONTAL, 1, 1, 1.0, 0.0, null);
//        Misc.setGridBagConstraints(k_headerPanel, btn_brwProgramDipa, constraint, 2, row, GridBagConstraints.HORIZONTAL, 1, 1, 0.0, 0.0, null);
//        
//        
//        Misc.setGridBagConstraints(k_headerPanel, lbl_kegiatan, constraint, 3, row, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, null);
//        Misc.setGridBagConstraints(k_headerPanel, k_txtKegiatanMaster, constraint, 4, row, GridBagConstraints.NONE, 1, 1, 1.0, 1.0, null);
//        Misc.setGridBagConstraints(k_headerPanel, btn_brwKegiatanDipa, constraint, 5, row, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, null);
//        
//        k_headerPanel.setBorder(BorderFactory.createTitledBorder("Detail Indikator Kegiatan"));
//        
//        getContentPane().add(k_headerPanel, BorderLayout.NORTH);
        
        k_headerPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout());
        
        
        k_headerPanel.add(panel, BorderLayout.CENTER);
        getContentPane().add(k_headerPanel, BorderLayout.NORTH);
        
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    
    public class IndikatorKegiatanDipaTableModel extends DefaultTableModel{

        public IndikatorKegiatanDipaTableModel() {
            addColumn("Kode");
            addColumn("Nama");
            addColumn("Tahun Anggaran");
            addColumn("Kode");
            addColumn("Nama");
            addColumn("<html>Nomor<br>Urut</html>");
            addColumn("<html>Indikator<br>kegiatan</html>");
            
        }
        
    }
}

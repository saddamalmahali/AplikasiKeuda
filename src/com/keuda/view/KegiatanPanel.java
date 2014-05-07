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
import com.keuda.util.ColumnGroup;
import com.keuda.util.GroupableTableHeader;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.PanelButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author user
 */
public class KegiatanPanel extends KeudaPanelButton{
    
    KegiatanTableModel k_model;
    JTable k_table;
    Program k_program;
    JComboBox k_cboProgram;
    JTextField k_txtProgram;
    JButton k_btnBrw;
    JPanel k_panelList;
    JLabel k_lblListPrg;
    ProgramListDialog k_dialogprg;
    Kegiatan k_kegiatan;
    Program[] program;
    KegiatanSearchPanel k_searchPanel;
    KegiatanCboModel k_cbom;
    KegiatanDialog dialog;
    public KegiatanPanel(MainForm mainForm) throws KeudaException {
        super(mainForm);
        k_searchPanel = new KegiatanSearchPanel();
        k_model = new KegiatanTableModel();
        k_table = new JTable(k_model){

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
            
        };
        
        TableColumnModel cm = k_table.getColumnModel();
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();
        
        
        
        ColumnGroup cg = new ColumnGroup("Program");
        
        cg.add(cm.getColumn(0));
        cg.add(cm.getColumn(1));
        
        header.addColumnGroup(cg);
        
        k_cbom = new KegiatanCboModel();
        
////        k_cboProgram.set
        k_cboProgram = new JComboBox(k_cbom);
        k_cboProgram.addActionListener(k_cbom);
        
        k_table.getSelectionModel().addListSelectionListener(k_model);
        
        k_table.getTableHeader().setReorderingAllowed(false);        
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(1).setMaxWidth(400);
        k_table.getColumnModel().getColumn(1).setMinWidth(400);
        k_table.getColumnModel().getColumn(2).setMaxWidth(80);
        k_table.getColumnModel().getColumn(2).setMinWidth(80);
//        k_table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField("")));
//        k_table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(k_cboProgram));
//        k_table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField("")));
        k_table.setRowHeight(25);
        getContentPane().add(new JScrollPane(k_table), BorderLayout.CENTER);
        
        k_txtProgram = new JTextField("");
        
        k_txtProgram.setOpaque(true);
        k_txtProgram.setEditable(false);
        k_btnBrw = new JButton("...");
        k_btnBrw.setToolTipText("Browse Program");
        k_btnBrw.addActionListener(k_searchPanel);
        k_panelList = new JPanel(new BorderLayout());
        k_lblListPrg = new JLabel("Pilih Program :");
        k_panelList.add(k_lblListPrg, BorderLayout.WEST);
        k_panelList.add(k_txtProgram, BorderLayout.CENTER);
        k_panelList.add(k_btnBrw, BorderLayout.EAST);
        k_panelList.setBorder(BorderFactory.createTitledBorder("Filter"));
        getContentPane().add(k_panelList, BorderLayout.NORTH);
        
        init();
        
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
//    public Object[] ambilDataCbo(){
//            BusinessLogic logic = new BusinessLogic(k_conn);
//            Program[] p = null;
//        try {
//            p = logic.getAllProgram(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//        } catch (KeudaException ex) {
//            Logger.getLogger(KegiatanPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        Object[] obj = new Object[p.length+1];
//        obj[0] = "";
//        for(int i =0; i<p.length; i++){
//            obj[i+1] = p[i].getProgramName();
//        }
//            return obj;
//    }
    
    @Override
    public void onNew() {
            dialog = new KegiatanDialog(this, true);
            dialog.setVisible(true);
        try {
            init();
//        if(!k_txtProgram.getText().trim().equals("")){
//            k_new = true;
//            k_edit = false;
//            k_cboProgram.setSelectedIndex(0);
//            k_cboProgram.updateUI();
//            k_model.addRow(new Object[]{"",  ""});
//            
//            
//            k_selectedRow = k_table.getRowCount()-1;
//            k_table.setRowSelectionInterval(k_selectedRow, k_selectedRow);
//            k_panelButton.setState(PanelButton.AFTER_NEW_STATE);
//        }
        } catch (KeudaException ex) {
            Logger.getLogger(KegiatanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    @Override
    public void onEdit() {
        dialog = new KegiatanDialog(this, k_kegiatan);
        dialog.setVisible(true);
        try {
            init();
        } catch (KeudaException ex) {
            Logger.getLogger(KegiatanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onCancel() {
        if((k_table.getSelectedRow() > -1 && k_table.getSelectedRow() < k_table.getRowCount())){
            stopCellEditing();
            if(k_new){
                k_model.removeRow(k_selectedRow);
                k_table.clearSelection();
                k_panelButton.setState(k_panelButton.NEW_STATE);
            }else if(k_edit){
                k_table.setValueAt(k_program, k_selectedRow, 0);
                k_table.setValueAt(k_kegiatan.getKegiatanName(), k_selectedRow, 1);
                k_panelButton.setState(PanelButton.AFTER_SAVE_STATE);
            }
            
            k_new = false;
            k_edit = false;
        }
    }

    @Override
    public void onDelete() {
        k_selectedRow = k_table.getSelectedRow();
        Object obj = k_table.getValueAt(k_selectedRow, 0);
        
        if(obj instanceof Kegiatan)
            k_kegiatan = (Kegiatan) obj;
        else
            k_kegiatan = null;
        
        if(k_kegiatan != null){
            int konfirm = JOptionPane.showConfirmDialog(this, "Apakah yakin anda akan menghapus data "
                    + "kegiatan dengan kode : "+k_kegiatan.getKegiatanCode()+" ?", "Perhatian", 
                    JOptionPane.YES_NO_OPTION);
            
            if(konfirm == JOptionPane.YES_OPTION){
                BusinessLogic logic = new BusinessLogic(k_conn);
                try {
                    logic.deleteKegiatan(k_kegiatan.getKegiatanIndex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                    k_model.removeRow(k_selectedRow);
                    k_table.clearSelection();
                    k_selectedRow = -1;
                    k_panelButton.setState(k_panelButton.NEW_STATE);
                } catch (KeudaException e) {
                    e.printStackTrace();
                }
            }else if(konfirm == JOptionPane.NO_OPTION) return;
        }
        
        
    }

    @Override
    public void onSave() {
//        try {
//            stopCellEditing();
//            Kegiatan kegiatan = getData();
//            BusinessLogic logic = new BusinessLogic(k_conn);
//            
//            if(k_new){
//                
//            }else if(k_edit){
//                k_kegiatan = logic.updateKegiatan(k_program.getProgramIndex(), kegiatan, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//            }
//            
//            k_kegiatan.setView(Kegiatan.VIEW_CODE);
//            
//            
//        } catch (KeudaException ex) {
//            Logger.getLogger(KegiatanPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private Kegiatan getData()throws KeudaException{
        String kode = "";
        String name = "";
        long programIndex;
        
        kode = k_table.getValueAt(k_selectedRow, 0).toString();
        if(kode == null || kode.trim().equals(""))
            throw new KeudaException("Isikan Kode Kegiatan.");
        
        programIndex = ((Program)k_table.getValueAt(k_selectedRow, 1)).getProgramIndex();
        if(programIndex == -1)
            throw new KeudaException("Pilih Kategori Program");
        name = k_table.getValueAt(k_selectedRow, 2).toString();
        if(name == null || name.trim().equals(""))
            throw new KeudaException("Isikan Nama Kegiatan");
        
        return new Kegiatan(programIndex, kode, name);
        
        
    }
//    public void kosongkanSemuaDataTable(){
//        for(int i=0; i<k_model.getRowCount(); i++){
//            k_model.removeRow(i);
//        }
//    }
    
    public Program[] ambilSemuaDataProgram() throws KeudaException{
        Program[] p = k_logic.getAllProgram(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        return p;
    }

    private void init() throws KeudaException{
        k_model.setRowCount(0);        
            if(k_txtProgram.getText().trim().equals("") || k_txtProgram.getText() == null){
                try {
                    BusinessLogic logic = new BusinessLogic(k_conn);
                    Kegiatan[] kegiatan = logic.getAllKegiatan(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                    
                    if(kegiatan!= null){
                        System.out.println("Perulangan Dimulai");
                        for(int i=0; i<kegiatan.length;i++){
                            Kegiatan k = kegiatan[i];
                            k.setView(Kegiatan.VIEW_CODE_MASTER_PROGRAM);
                            
                            k_model.addRow(new Object[]{k, k.getProgram().getProgramName(), k.getKegiatanCode(), k.getKegiatanName()});
                        }
                    }
                } catch (KeudaException e) {
                }
            }else{
                try {
                    BusinessLogic logic = new BusinessLogic(k_conn);
                    Kegiatan[] kegiatan = logic.getKegiatanByProgram(k_program.getProgramIndex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                    if(kegiatan!=null){
                        System.out.println("Perulangan Dimulai");
                        for(int i=0; i<kegiatan.length;i++){
                             Kegiatan k = kegiatan[i];
                            k.setView(Kegiatan.VIEW_CODE_MASTER_PROGRAM);
                            
                            k_model.addRow(new Object[]{k, k.getProgram().getProgramName(), k.getKegiatanCode(), k.getKegiatanName()});
                        }
                    }
                } catch (KeudaException e) {
                }
            }
    }
    
//    public void removeSemuaData(){
//        for(int i = 1; i<k_table.getRowCount(); i++){
//            k_model.removeRow(i);
//        }
//    }
    
    public void save(Kegiatan kegiatan) throws KeudaException{
        if(kegiatan.getKegiatanIndex() == 0){  
            
            k_logic.createKegiatan(kegiatan, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                                   
        }else if(kegiatan.getKegiatanIndex() != 0){
            
            k_logic.updateKegiatan(k_kegiatan.getKegiatanIndex(), kegiatan, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
        }
        k_panelButton.setState(PanelButton.NEW_STATE);
    }
    public void setData(Program prog){
        k_model.setRowCount(0);
        try{
            final Kegiatan[] keg = k_logic.getKegiatanByProgram(prog.getProgramIndex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i=0; i<keg.length;i++){
                Kegiatan k = keg[i];
                Program p = k_logic.getProgram(k.getProgramIndex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                k.setProgram(p);
                k.setView(Kegiatan.VIEW_CODE);
                k.getProgram().setView(Program.VIEW_NAME);
                k_model.addRow(new Object[]{k, k.getProgram(), k.getKegiatanName()});
                
            }
            
        }catch(KeudaException ex){
            
        }
    }    
    

    public void stopCellEditing(){
        TableCellEditor editor;
        if((editor = k_table.getCellEditor()) != null)
            editor.stopCellEditing();
    }
    
    class KegiatanSearchPanel extends JPanel implements ActionListener{
        
        JPanel panel;
        JLabel lbl_pilih;
        JTextField txt_browse;
        JButton btn_browse;
        
        public KegiatanSearchPanel() {
            panel = new JPanel(new BorderLayout());
            lbl_pilih = new JLabel("Pilih Program : ");
            txt_browse = new JTextField("");
            txt_browse.setEditable(false);
            btn_browse = new JButton("...");
            btn_browse.setToolTipText("Browse");
            btn_browse.addActionListener(this);
            panel.add(lbl_pilih, BorderLayout.WEST);
            panel.add(txt_browse, BorderLayout.CENTER);
            panel.add(btn_browse, BorderLayout.EAST);
            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Firlter"));
            getContentPane().add(panel, BorderLayout.CENTER);
        }
        
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == k_btnBrw){
                k_dialogprg = new ProgramListDialog(k_mainForm);
                k_dialogprg.setVisible(true);
                if(k_dialogprg.k_isOk){
                    k_program = k_dialogprg.getProgram();
                    k_program.setView(Program.VIEW_CODE_AND_NAME);
                    k_txtProgram.setText(k_program.toString());
                    k_panelButton.setState(PanelButton.NEW_STATE);
                    setData(k_program);
                }
            }
        }
        
    }
    
    class KegiatanCboModel extends DefaultComboBoxModel implements ActionListener{
        Program[] prg;
        Vector<Program> v_result;
        
        boolean view = false;
        
        public KegiatanCboModel() throws KeudaException {            
            prg = k_logic.getAllProgram(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            addElement("");
            for(int i=0; i<prg.length; i++){
                Program p =prg[i];
                p.setView(Program.VIEW_NAME);
                addElement(p);
            }
        }

        public void setView(boolean view) {
            this.view = view;
        }

        public boolean isView() {
            return view;
        }       
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().trim().equals("") || e.getSource()==null){
                
            }else{
                Object obj = k_cboProgram.getModel().getSelectedItem();
                System.out.println(obj);
                if(obj instanceof Program){
                    k_program = (Program) obj;
                    k_program.setView(Program.VIEW_NAME);
                    System.out.println(k_program);
                }else{
                    k_program =null;
                    System.out.print(k_program);
                }
                
            }
        }
        
    }
    class KegiatanTableModel extends DefaultTableModel implements ListSelectionListener{

        public KegiatanTableModel() {
            addColumn("Kode");
            addColumn("Nama");
            addColumn("Kode Kegiatan");            
            addColumn("Nama Kegiatan");
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
                
                if(k_new || k_edit){
                    if(iRowIndex != k_selectedRow)
                        k_table.getSelectionModel().setSelectionInterval(k_selectedRow, k_selectedRow);
                }else if(iRowIndex != -1){
                    k_selectedRow = iRowIndex;
                    Object obj = k_table.getValueAt(k_selectedRow, 0);
                    if(obj instanceof Kegiatan){
                        k_kegiatan = ((Kegiatan)obj);
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

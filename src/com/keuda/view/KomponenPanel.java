
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.Komponen;
import com.keuda.model.OutputKegiatan;
import com.keuda.model.SubKomponen;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import com.keuda.util.ButtonAddDelPanel;
import com.keuda.util.GroupableTableHeader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.SelectionModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author adam
 */

public class KomponenPanel extends KeudaPanel implements ListSelectionListener{
    private OutputKegiatan k_outke;
    private OutputKegiatan[] k_listOutke;
    private BunxuList k_list;
    private Komponen k_komponen;
    private SubKomponen k_subKomponen;
    private JTable k_table;
    private JTable k_tableSub;
    private thisModel k_model1;
    private thisSubKomponenModel k_model2;
    private KegiatanDipa k_kedip;
    private JTextField k_txtnoKedip, k_txtKedip,  k_txtNoProgram, k_txtProgram;
    private JLabel lbl_program, lbl_thn, lbl_headerdetail;
    private JButton btn_topbrowse;
    private JComboBox cbo_tahun;
    private KomponenAddDialog komponenAddDialog;
    private SubKomponenDialog subKomponenDialog;
    ButtonAddDelPanel k_adebtn;
    ButtonAddDelPanel k_adebtnSub;
    KegiatanDipaListDialog kegiatanDipaListDialog;
    
    int tableKomponenRowCount = -1;
    int tableSubKomponenRowCount = -1;
    
    short k_tahun = -1;
    public KomponenPanel(MainForm mainForm) {
        super(mainForm);
        k_model1 = new thisModel();
        k_model2 = new thisSubKomponenModel();
        
        k_table = new JTable(k_model1){

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
            
        };
        k_tableSub = new JTable(k_model2){
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        k_list = new BunxuList();
        btn_topbrowse = new JButton("...");
        
        
        k_adebtn = new ButtonAddDelPanel(true);
        k_adebtnSub = new ButtonAddDelPanel(true);
        k_adebtn.getAddButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addBtnKomponenAction(e);
            }
        });
        
        k_adebtn.getDelButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBtnKomponenAction(e);
            }
        });
        
        k_adebtnSub.getDelButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBtnSubKomponenAction(e);
            }
        });
        
        k_adebtn.getEditButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateBtnKomponenAction(e);
            }
        });
        
        k_adebtnSub.getEditButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateBtnSubKomponenAction(e);
            }
        });
        
        k_adebtnSub.getAddButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addBtnSubKomponenAction(e);
            }
        });
        
        cbo_tahun = new JComboBox<Object>(new cboTahunModel());
        cbo_tahun.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                cboTahunAct(e);
            }
        });
        
        btn_topbrowse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnBrwsAct(e);
            }
        });
        k_list.addListSelectionListener(this);
        k_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                valueChangeTableKomponen(e);
            }
        });
        
        k_tableSub.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                valueChangedTableSubKomponen(e);
            }
        });
        
        k_table.getTableHeader().setReorderingAllowed(false);
        k_tableSub.getTableHeader().setReorderingAllowed(false);
        
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_tableSub.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        constructComponent();
    }
    
    public void addBtnKomponenAction(ActionEvent e){
        komponenAddDialog = new KomponenAddDialog(k_outke, this, true);
        komponenAddDialog.setVisible(true);
        
        initTableKomponen(k_outke);
        k_table.clearSelection();
        k_adebtn.setState(k_adebtn.NEW_STATE);
        k_komponen = null;
        initTableSubKomponen();
        k_adebtnSub.setState(k_adebtnSub.DISABLE_ALL);
        
    }
    
    public void addBtnSubKomponenAction(ActionEvent e){
        subKomponenDialog = new SubKomponenDialog(k_komponen, this, true);
        subKomponenDialog.setVisible(true);
        
        initTableSubKomponen();
        k_tableSub.clearSelection();
        k_adebtnSub.setState(k_adebtnSub.NEW_STATE);
        
    }
    
    public void deleteBtnKomponenAction(ActionEvent e){
        k_selectedRow = k_table.getSelectedRow();
        
        Object obj = k_table.getValueAt(k_selectedRow, 0);
        
        if(obj instanceof Komponen){
            k_komponen = (Komponen) obj;
        }else{
            k_komponen = null;
        }
        
        int warning = JOptionPane.showConfirmDialog(this, "Apakah anda yakin akan menghapus "+k_komponen.getKomponenkode()+" dari database?", "Warning", JOptionPane.OK_CANCEL_OPTION);
        
        
        if(warning == JOptionPane.OK_OPTION){
            BusinessLogic logic = new BusinessLogic(k_conn);
        
            try {
                logic.deleteKomponen(k_komponen.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                k_model1.removeRow(k_selectedRow);
                k_table.clearSelection();
                k_selectedRow = -1;
                tableKomponenRowCount = -1;
                k_adebtn.setState(k_adebtn.NEW_STATE);
            } catch (KeudaException ex) {
                Logger.getLogger(KomponenPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else {
            return ;
        }
    }
    
    public void deleteBtnSubKomponenAction(ActionEvent e){
        k_selectedRow = k_tableSub.getSelectedRow();
        Object obj = k_tableSub.getValueAt(k_selectedRow, 0);
        
        if(obj instanceof SubKomponen){
            k_subKomponen = (SubKomponen) obj;
        }else{
            k_subKomponen = null;
        }
        
        int warning = JOptionPane.showConfirmDialog(this, "Apakah anda yakin akan menghapus "+k_subKomponen.getSubkomponenkode()+" dari database?", "Warning", JOptionPane.OK_CANCEL_OPTION);
        
        if(warning == JOptionPane.OK_OPTION){
            BusinessLogic logic = new BusinessLogic(k_conn);
        
            try {
                logic.deleteSubKomponen(k_subKomponen.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                k_model2.removeRow(k_selectedRow);
                k_tableSub.clearSelection();
                k_selectedRow = -1;
                tableSubKomponenRowCount = -1;
                k_adebtnSub.setState(k_adebtn.NEW_STATE);
            } catch (KeudaException ex) {
                Logger.getLogger(KomponenPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public void updateBtnKomponenAction(ActionEvent e){
        komponenAddDialog = new KomponenAddDialog(k_komponen, k_outke, this, true);
        komponenAddDialog.setVisible(true);
        
        initTableKomponen(k_outke);
        
    }
    
    public void updateBtnSubKomponenAction(ActionEvent e){
        subKomponenDialog = new SubKomponenDialog(k_subKomponen, k_komponen, this, true);
        subKomponenDialog.setVisible(true);
        
        System.out.println(k_komponen);
        initTableSubKomponen();
            
        k_tableSub.clearSelection();
        k_adebtnSub.setState(k_adebtnSub.NEW_STATE);
        
    }
    
    public OutputKegiatan getK_outke() {
        return k_outke;
    }

    public void setK_outke(OutputKegiatan k_outke) {
        this.k_outke = k_outke;
    }

    public OutputKegiatan[] getK_listOutke() {
        return k_listOutke;
    }

    public void setK_listOutke(OutputKegiatan[] k_listOutke) {
        this.k_listOutke = k_listOutke;
    }

    public Komponen getK_komponen() {
        return k_komponen;
    }

    public void setK_komponen(Komponen k_komponen) {
        this.k_komponen = k_komponen;
    }

    public KegiatanDipa getK_kedip() {
        return k_kedip;
    }

    public void setK_kedip(KegiatanDipa k_kedip) {
        this.k_kedip = k_kedip;
    }
    
    public void cboTahunAct(ItemEvent e){
        String tahun = (String) cbo_tahun.getSelectedItem();
        
        if(!tahun.trim().equals("")){
            btn_topbrowse.setEnabled(true);
            k_tahun = Short.valueOf(tahun);
            System.out.println(tahun);
        } else{
            k_kedip = null;
            k_outke = null;
            setDataTopPanel(k_kedip);
            initListOutput(k_kedip);
            
            initTableKomponen(k_outke);
            
            k_adebtn.setState(k_adebtn.DISABLE_ALL);
            btn_topbrowse.setEnabled(false);            
        }
        
    }
    
    public void btnBrwsAct(ActionEvent e){
        if(e.getSource() == btn_topbrowse){
            kegiatanDipaListDialog = new KegiatanDipaListDialog(k_tahun, k_mainForm);
            kegiatanDipaListDialog.setVisible(true);
            if(kegiatanDipaListDialog.getKegiatanDipa() != null){
                k_kedip = kegiatanDipaListDialog.getKegiatanDipa();
                k_outke = null;
                
                setDataTopPanel(k_kedip);
                initTableKomponen(k_outke);
                initListOutput(k_kedip);
                
                k_adebtn.setState(k_adebtn.DISABLE_ALL);
                k_adebtnSub.setState(k_adebtnSub.DISABLE_ALL);
            }
        }        
    }
    
    public void setDataTopPanel(KegiatanDipa kedip){
        if(kedip != null){
            k_txtNoProgram.setText(""+kedip.getProgramDipa().getProgram().getProgramCode());
            k_txtProgram.setText(kedip.getProgramDipa().getProgram().getProgramName());
            k_txtnoKedip.setText(kedip.getK_kegiatan().getKegiatanCode());
            k_txtKedip.setText(kedip.getK_kegiatan().getKegiatanName());
        }else{
            k_txtNoProgram.setText("");
            k_txtProgram.setText("");
            k_txtnoKedip.setText("");
            k_txtKedip.setText("");
        }
    }
    
    public void initListOutput(KegiatanDipa kedip){
        if(kedip != null){
            k_list.removeAllElements();
            try {
                k_listOutke = k_logic.getAllOutputKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                for(int i=0; i<k_listOutke.length; i++){
                    OutputKegiatan ouke = k_listOutke[i];
                    ouke.setView(OutputKegiatan.VIEW_NROUTPUT_OUTPUT);
                    k_list.addElement(ouke);
                }
            } catch (Exception e) {
            }
        }else{
            k_list.removeAllElements();
        }
    }
    
    public void constructComponent(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTopPanel(), BorderLayout.NORTH);
//        panel.add(k_list, BorderLayout.WEST);
        JPanel panelTabel = new JPanel(new BorderLayout());
        
        JSplitPane splitTabel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createPanelTabelKomponen(), createPanelTabelSubKomponen());
        splitTabel.setDividerLocation(180);
        panelTabel.add(splitTabel, BorderLayout.CENTER);
//        panelTabel.add(createPanelTabelKomponen(), BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, k_list,panelTabel );
        split.setDividerLocation(200);
        panel.add(split, BorderLayout.CENTER);
        getContentPane().add(panel);
    }
    
    public JPanel createPanelTabelSubKomponen(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        k_tableSub.getColumnModel().getColumn(0).setMinWidth(100);
        k_tableSub.getColumnModel().getColumn(0).setMaxWidth(100);
        JScrollPane sc = new JScrollPane(k_tableSub);
        panel.add(k_adebtnSub, BorderLayout.NORTH);
        panel.add(sc, BorderLayout.CENTER);
        
        return panel;
    }
    
    public JPanel createPanelTabelKomponen(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        JScrollPane sc = new JScrollPane(k_table);
        panel.add(k_adebtn, BorderLayout.NORTH);
        panel.add(sc, BorderLayout.CENTER);
        return panel;
    }
    
    public JPanel createTopPanel(){
        
        Dimension d = new Dimension(80, 20);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(3, 3, 0, 0);
        
        JLabel lbl_thn = new JLabel("Tahun Anggaran :");
        lbl_thn.setHorizontalAlignment(JLabel.RIGHT);
        lbl_thn.setPreferredSize(new Dimension(100, 20));
        panel.add(lbl_thn, gbc);
        
        
        gbc.gridx = 1;
        cbo_tahun.setPreferredSize(new Dimension(60, 20));
        panel.add(cbo_tahun, gbc);
        
        
        gbc.gridx = 1;
        
        
        JLabel lbl_prg = new JLabel("Program :");
        lbl_prg.setPreferredSize(new Dimension(100, 20));
        lbl_prg.setHorizontalAlignment(JLabel.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lbl_prg, gbc);
        
        k_txtNoProgram = new JTextField("");
        k_txtNoProgram.setPreferredSize(new Dimension(60, 20));
        k_txtNoProgram.setEditable(false);
        gbc.gridx = 1;
        panel.add(k_txtNoProgram, gbc);
        
        k_txtProgram = new JTextField("");
        k_txtProgram.setPreferredSize(new Dimension(200, 20));
        k_txtProgram.setEditable(false);
        gbc.gridx =2;
        panel.add(k_txtProgram, gbc);
        
        btn_topbrowse.setEnabled(false);
        gbc.gridx = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        panel.add(btn_topbrowse, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(3, 3, 3, 3);
        JLabel lbl_kgt = new JLabel("Kegiatan :");
        lbl_kgt.setPreferredSize(new Dimension(100, 20));
        lbl_kgt.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(lbl_kgt, gbc);
        
        k_txtnoKedip = new JTextField("");
        k_txtnoKedip.setPreferredSize(new Dimension(60, 20));
        k_txtnoKedip.setEditable(false);
        gbc.gridx = 1;
        panel.add(k_txtnoKedip, gbc);
        
        
        k_txtKedip = new JTextField("");
        k_txtKedip.setPreferredSize(new Dimension(200, 20));
        k_txtKedip.setEditable(false);
        gbc.gridx = 2;
        
        panel.add(k_txtKedip, gbc);
        
        return panel;
    }
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(k_list.getSelectedValue() != null){
            Object obj = k_list.getSelectedValue();
            if(obj instanceof OutputKegiatan){
                OutputKegiatan outke = (OutputKegiatan) obj;
                k_outke = outke;
                
                initTableKomponen(k_outke);
                    
                k_model2.setRowCount(0);
                k_adebtnSub.setState(k_adebtnSub.DISABLE_ALL);
                
                k_adebtn.setState(ButtonAddDelPanel.NEW_STATE);
                
            }
        }
    }
    
    public class thisModel extends DefaultTableModel{

        public thisModel() {
            addColumn("Kode");
            addColumn("Detail Komponen");
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
    
    public class thisSubKomponenModel extends DefaultTableModel{

        public thisSubKomponenModel() {
            addColumn("<html><center>Kode</center>");
            addColumn("<html><center>Detail Sub Komponen</center>");            
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
        
        
        
    }
    
    public class cboTahunModel extends DefaultComboBoxModel<Object>{

        public cboTahunModel() {
            addElement("");
            addElement("2014");
            addElement("2015");
        }
        
    }
    
    public void initTableKomponen(OutputKegiatan outke){
        if(outke != null){
            k_model1.setRowCount(0);
            
            BusinessLogic logic = new BusinessLogic(k_conn);
            Komponen[] komps = null;
            try {
                komps = logic.getAllKomponenByOutputKegiatan(outke.getOutputkegiatanindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if(komps != null){
                for(int i = 0; i<komps.length; i++){
                    Komponen komp = komps[i];
                    komp.setView(Komponen.VIEW_CODE);
                    k_model1.addRow(new Object[]{komp, komp.getKomponenname()});
                }
            }
        }else{
            k_model1.setRowCount(0);
            
        }
    }
    
    public void initTableSubKomponen(){
        
        if(k_komponen != null){ 
//            k_tableSub.clearSelection();
            k_model2.setRowCount(0);
            
            BusinessLogic logic = new BusinessLogic(k_conn);
            SubKomponen[] komps = null;
            try {
                komps = logic.getAllSubKomponenByKomponen(k_komponen.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if(komps.length == 0 || komps ==null){
                return ;
            }else{
                if(komps.length == -1){
                }else{
                    for(int i=0; i<komps.length; i++){
                        SubKomponen subkomp = komps[i];
                        subkomp.setView(subkomp.VIEW_KODE);
                        k_model2.addRow(new Object[]{subkomp, subkomp.getSubkomponenname()});
                    }
                }
                
            }
            
            
            
        }else{
            k_model2.setRowCount(0);
        }
    }
    
    public void valueChangeTableKomponen(ListSelectionEvent e){
        if(!e.getValueIsAdjusting()){
            k_tableSub.clearSelection();
            k_model2.setRowCount(0);
            
            tableKomponenRowCount = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
            if(tableKomponenRowCount != -1){
                Object obj = k_table.getValueAt(tableKomponenRowCount, 0);
                if(obj instanceof Komponen){
                    k_komponen = (Komponen) obj;
                    k_adebtn.setState(k_adebtn.AFTER_NEW_STATE);
                    k_adebtnSub.setState(k_adebtnSub.NEW_STATE);
                    
                    initTableSubKomponen();
                    
                }
            }
        }
        
    }
    
    public void valueChangedTableSubKomponen(ListSelectionEvent e){
        if((!e.getValueIsAdjusting())){
            tableSubKomponenRowCount = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
            if(tableSubKomponenRowCount != -1){
                Object obj = k_tableSub.getValueAt(tableSubKomponenRowCount, 0);
                if(obj instanceof SubKomponen){
                    k_subKomponen = (SubKomponen) obj;
                    k_adebtnSub.setState(k_adebtnSub.AFTER_NEW_STATE);   
                    
                }
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.exception.KeudaException;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.OutputKegiatan;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import com.keuda.util.BunxuListCombo;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.LabelSeparator;
import com.keuda.util.Misc;
import com.keuda.view.MainForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author client
 */
public class OutputKegiatanPanelList extends KeudaPanelButton implements ListSelectionListener{
    JPanel k_panelOutputCenter, k_panelDetailOutput, k_panelList;
    BunxuListCombo k_listOutput;
    MainForm k_mainForm;
    KegiatanDipa[] k_listKedip;
    OutputKegiatan[] k_listOutke;
    OutputKegiatan k_outke;
    KegiatanDipa k_kedip;
    
    JTable k_table;
    int k_selectedIndex = -1;
    JTextField k_txtKategori;
    JButton k_btnBrowse;
    KegiatanDipaListDialog k_dialogKedipList;
    JPanel panel;
    JTextField k_txtnrKegiatan, k_txtnrProdi, k_txtTahun, k_txtnrOutput;
    
    JTextArea k_txtDetailKegiatan, k_txtDetailProdi, k_txtDetailOutput;
    
    
    boolean isNew = false;
    public OutputKegiatanPanelList(MainForm mainForm) {
        super(mainForm);
        k_panelOutputCenter = new JPanel(new BorderLayout());
        
        k_listOutput = new BunxuListCombo();
        k_listOutput.setSize(100, getHeight());
        k_mainForm = mainForm;  
        
        
        constructComponents();
        
        setEditableComponent(false);
        k_listOutput.addListSelectionListener(this);
        k_listOutput.k_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        k_txtDetailKegiatan.setLineWrap(true);
        k_txtDetailKegiatan.setWrapStyleWord(true);
        
        
        k_txtDetailOutput.setWrapStyleWord(true);
        k_txtDetailOutput.setLineWrap(true);
        
        k_txtDetailProdi.setWrapStyleWord(true);
        k_txtDetailProdi.setLineWrap(true);
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    
    public void setEditableComponent(boolean isEditable){
        k_txtDetailOutput.setEditable(isEditable);
        k_txtDetailProdi.setEditable(false);
        k_txtDetailKegiatan.setEditable(false);
        k_txtTahun.setEditable(false);
        k_txtnrOutput.setEditable(isEditable);
        k_txtnrProdi.setEditable(false);
        k_txtnrKegiatan.setEditable(false);
    }
    
    
    @Override
    public void onNew() {
        k_outke = null;
        setData(k_outke);
        
        k_dialogKedipList = new KegiatanDipaListDialog(k_mainForm);
        k_dialogKedipList.setLocationRelativeTo(null);
        k_dialogKedipList.setVisible(true);
        
        if(k_dialogKedipList.getKegiatanDipa() != null){
            k_kedip = k_dialogKedipList.getKegiatanDipa();
            setTextKedip(k_kedip);
            isNew = true;
            setEditableComponent(true);
            k_panelButton.setState(k_panelButton.AFTER_NEW_STATE);
        }else{
            k_panelButton.setState(k_panelButton.NEW_STATE);
            return ;
        }
        
    }

    @Override
    public void onEdit() {
        setData(k_outke);
        setEditableComponent(true);
        k_txtnrOutput.setEditable(false);
        isNew = false;
        k_panelButton.setState(k_panelButton.AFTER_NEW_STATE);
        
    }

    @Override
    public void onCancel() {
        k_outke = null;
        setData(k_outke);
        setEditableComponent(false);
        initList();
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    @Override
    public void onDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah anda yakin akan menghapus data dengan no : "+k_outke.getNroutput(), "Perhatian", JOptionPane.OK_CANCEL_OPTION);
        if(confirm == JOptionPane.OK_OPTION){
            long kegiatanDipa = k_outke.getK_kedip().getKegiatandipaindex();
            int nrOutput = k_outke.getNroutput();
            try {
                k_logic.deleteOutputKegiatan(kegiatanDipa, nrOutput, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(OutputKegiatanPanelList.class.getName()).log(Level.SEVERE, null, ex);
            }
            k_outke = null;
            setData(k_outke);
            initList();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }else{
            return ;
        }
    }
    
    @Override
    public void onSave() {
        if(isNew){
            getData();
            try {
                k_logic.createOutputKegiatan(k_outke, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(OutputKegiatanPanelList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            k_outke = null;
            setData(k_outke);
            initList();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }else{
            updateData();
            try {
                k_logic.updateOutputKegiatan(k_outke, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(OutputKegiatanPanelList.class.getName()).log(Level.SEVERE, null, ex);
            }
            k_panelButton.setState(k_panelButton.NEW_STATE);
            k_outke = null;
            setData(k_outke);
            initList();
        }
    }
    
    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(k_listOutput.getSelectedValue() != null){
             k_selectedIndex = k_listOutput.getSelectedIndex();
             Object obj = k_listOutput.getSelectedValue();
             if(obj instanceof OutputKegiatan){
                 OutputKegiatan outke = (OutputKegiatan) obj;
                 k_outke = outke;
                 
                 setData(k_outke);
                 k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
                 
             }
        }
    }
    
    void getData(){
        if(k_txtnrOutput.getText().trim().equals("") || k_txtnrOutput == null){
            JOptionPane.showMessageDialog(this, "Kolom Nomor Urut Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(k_txtDetailOutput.getText().trim().equals("") || k_txtDetailOutput.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Output Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            int nr = Integer.parseInt(k_txtnrOutput.getText());
            String output = k_txtDetailOutput.getText();
            k_outke = new OutputKegiatan(k_kedip, nr, output);            
        }
    }
    
    void updateData(){
        if(k_txtnrOutput.getText().trim().equals("") || k_txtnrOutput.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Nomor Urut Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(k_txtDetailOutput.getText().trim().equals("") || k_txtDetailOutput.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Output Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            k_outke.setOutput(k_txtDetailOutput.getText());
        }
    }
    
    private void constructComponents() {
        initList();
        
        k_txtKategori = new JTextField();
        k_btnBrowse = new JButton("...");
        k_btnBrowse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                k_dialogKedipList = new KegiatanDipaListDialog(k_mainForm);
                k_dialogKedipList.setLocationRelativeTo(null);
                k_dialogKedipList.setVisible(true);
                if(k_dialogKedipList.getKegiatanDipa()!= null){
                    KegiatanDipa kedip = k_dialogKedipList.getKegiatanDipa();
                    kedip.setView(KegiatanDipa.VIEW_CODE_AND_NAME_KEGIATAN);
                    k_txtKategori.setText(""+kedip);
                    k_kedip = kedip;
                    k_outke = null;
                    setData(k_outke);
                    initList(k_dialogKedipList.getKegiatanDipa());
                    k_panelButton.setState(k_panelButton.NEW_STATE);
                }else{
                    k_listOutput.removeAllElements();
                    k_txtKategori.setText("");
                    initList();
                    k_outke = null;
                    setData(k_outke);
                    k_panelButton.setState(k_panelButton.NEW_STATE);
                }
            }
        });
        
        panel = new JPanel(new FlowLayout());
        
        JPanel panelHeader = new JPanel(new FlowLayout());
        JLabel labelHeader = new JLabel("Rincian Output");
        
        JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
        separator2.setBounds(5, 5, panel.getWidth(), 2);
        labelHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//        labelHeader.setHorizontalAlignment(JLabel.RIGHT);
        labelHeader.setBorder(new EmptyBorder(new Insets(2, panel.getWidth()/2, 10, panel.getWidth()/2)));
        panelHeader.add(labelHeader, "North");
        panelHeader.add(separator2, "Center");
        separator2.setVisible(true);
        panel.add(panelHeader);
        panel.add(constructDetail());
//        panel = constructDetail();
        
        JPanel panelFilter = new JPanel(new BorderLayout());
        panelFilter.add(new LabelSeparator("Pilih Kategori"), BorderLayout.NORTH);
        Dimension d = new Dimension(160, 25);
        k_txtKategori.setPreferredSize(d);
        k_txtKategori.setEditable(false);
        panelFilter.add(k_txtKategori, BorderLayout.WEST);
        panelFilter.add(k_btnBrowse, BorderLayout.EAST);
        JPanel panelList = new JPanel(new BorderLayout());
        panelList.add(panelFilter, BorderLayout.NORTH);
        panelList.add(k_listOutput, BorderLayout.CENTER);
        
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelList, panel);
        split.setDividerLocation(200);
        getContentPane().add(split, BorderLayout.CENTER);
    }
    
    
    void initList(KegiatanDipa kedip){
        k_listOutput.removeAllElements();
        try {
            k_listOutke = k_logic.getAllOutputKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            for(int i=0; i<k_listOutke.length; i++){
                    OutputKegiatan ouke = k_listOutke[i];
                    ouke.setView(OutputKegiatan.VIEW_NROUTPUT_OUTPUT);
                    k_listOutput.addElement(ouke);
            }
        } catch (Exception ex) {
            
        }
        
    }
    
    void initList(){
        try {
            k_listOutput.removeAllElements();
            k_listOutke = k_logic.getAllOutputKegiatan(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            if(k_listOutke != null){
                for(int i=0; i<k_listOutke.length; i++){
                    OutputKegiatan ouke = k_listOutke[i];
                    ouke.setView(OutputKegiatan.VIEW_NROUTPUT_OUTPUT);
                    k_listOutput.addElement(ouke);
                }
            }
            
            
            
        } catch (Exception e) {
        }
    }
    public JPanel constructDetail(){
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(new Insets(2, 5, 2, 10)));
        p.setLayout(new GridBagLayout());
        EmptyBorder border = new EmptyBorder(new Insets(0,
                0, 0, 20));
        EmptyBorder border2 = new EmptyBorder(new Insets(2, 5, 2, 5));
        EmptyBorder border3 = new EmptyBorder(new Insets(0,
                0, 0, 10));
        Dimension d = new Dimension(60, 20);
        Dimension d2 = new Dimension(200, 40);
        Dimension d3 = new Dimension(200, 100);
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 5, 2, 5);
        c.anchor = GridBagConstraints.WEST;
        
//        c.gridwidth = 1;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.gridwidth = 5;
//        c.weightx = 1.0;
//        
//        p.add(separator,c);
        
        JLabel lblKodeProgram = new JLabel("Kode Program");
        lblKodeProgram.setBorder(border);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.0;
        
        c.fill = GridBagConstraints.NONE;
        p.add(lblKodeProgram, c);
        
        k_txtnrProdi = new JTextField("");
        k_txtnrProdi.setPreferredSize(d);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
//        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(k_txtnrProdi,c);
        
        JLabel lblTahunProdi = new JLabel("Tahun");
        lblTahunProdi.setBorder(border2);
//        c.gridy = 2;
        c.gridx = 2;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        p.add(lblTahunProdi, c);
        
        k_txtTahun = new JTextField();
        k_txtTahun.setPreferredSize(d);
        
        c.gridx = 3;
        
        c.gridwidth = 1;
//        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(k_txtTahun, c);
        
        
        JLabel lblDetailProdi = new JLabel("Program");
        lblDetailProdi.setBorder(border);
        
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(lblDetailProdi, c);
        
        k_txtDetailProdi = new JTextArea("");
        JScrollPane scroll = new JScrollPane(k_txtDetailProdi);
        scroll.setPreferredSize(d2);
        
        c.gridx = 1;
        
        c.gridheight =2;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        p.add(scroll, c);
        
        
        
        c.gridy = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridwidth = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.insets = new Insets(15, 5, 5, 5);
//        p.add(new JSeparator(), c);
        
//        c.insets = new Insets(2,5,5,5);
        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        JLabel labelNrKegiatan = new JLabel("Kode Kegiatan");
        labelNrKegiatan.setBorder(border);
        p.add(labelNrKegiatan, c);
        
        c.gridx = 1;
        c.gridwidth = 2;
        k_txtnrKegiatan = new JTextField();
        k_txtnrKegiatan.setPreferredSize(d);
        
        p.add(k_txtnrKegiatan, c);
        
        c.gridy = 6;
        c.gridx = 0;
        c.gridwidth = 1;
        JLabel lblDetailKegiatan = new JLabel("Kegiatan");
        p.add(lblDetailKegiatan, c);
        
        k_txtDetailKegiatan = new JTextArea();
        JScrollPane scrollKegiatan = new  JScrollPane(k_txtDetailKegiatan);
        scrollKegiatan.setPreferredSize(d2);
        
        c.gridx = 1;
        c.gridwidth = 4;
        c.gridheight = 2;
        
        p.add(scrollKegiatan,c);
        
        c.gridy = 8;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.insets = new Insets(10, 5, 1, 5);
//        p.add(new JLabel("Detail Indikator"), c);
        
        c.gridy = 9;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 5, 2, 5);
//        p.add(new JSeparator(), c);
        
        c.insets = new Insets(2, 5, 2, 5);
        c.gridy = 10;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        JLabel lblNrOutput = new JLabel("Nomor Urut");
        lblNrOutput.setBorder(border3);
        p.add(lblNrOutput, c);
        
        c.gridx = 1;
        c.gridwidth = 2;
        k_txtnrOutput = new JTextField();
        k_txtnrOutput.setPreferredSize(d);
        p.add(k_txtnrOutput, c);
        
        
        c.gridy = 11;
        c.gridx = 0;
        c.gridwidth = 1;
        JLabel lblDetailOutput = new JLabel("Output");
        lblDetailOutput.setBorder(border);
        p.add(lblDetailOutput, c);
        
        c.gridx = 1;
        c.gridheight = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        k_txtDetailOutput = new JTextArea();
        
        JScrollPane scrollOutput= new JScrollPane(k_txtDetailOutput);
        scrollOutput.setPreferredSize(d3);
        
        p.add(scrollOutput, c);
//        c.insets = new Insets(0, 0, 0, 0);
//        c.gridy = 9;
//        c.gridx = 0;
//        c.gridwidth = 4;
//        c.gridheight = 4;
//        c.weightx = 1.0;
//        c.weighty = 1.0;
//        
//        
//        JPanel panel2  = new JPanel(new BorderLayout());
//        panel2.setLayout(new GridBagLayout());
//        panel2.setBorder(BorderFactory.createTitledBorder("Detail Indikator"));
//        p.add(panel2, c);
//        c.anchor = GridBagConstraints.WEST;
//        c.insets = new Insets(5, 5, 5, 5);
//        c.fill = GridBagConstraints.NONE;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.gridwidth = 1;
//        c.gridheight = 0;
//        c.weightx = 1.0;
//        c.weighty = 0.0;
//        JLabel lblNrOutput = new JLabel("Nomor Urut");
//        lblNrOutput.setBorder(border3);
//        panel2.add(lblNrOutput, c);
//        
//        k_txtnrOutput = new JTextField();
//        k_txtnrOutput.setPreferredSize(d);
//        c.gridx = 1;
//        c.gridwidth = 1;
////        c.gridheight = 0;
////        c.weightx = 0.0;
//        panel2.add(k_txtnrOutput, c);
//        
//        JLabel lblDetailOutput = new JLabel("Output");
//        lblDetailOutput.setBorder(border3);
//        c.gridy = 2;
//        c.gridx = 0;
//        c.gridwidth = 1;
//        c.fill = GridBagConstraints.NONE;
//        panel2.add(lblDetailOutput, c);
//        
////        k_txtDetailOutput = new JTextArea();
////        JScrollPane scrollOutput= new JScrollPane(k_txtDetailOutput);
////        
////        scrollOutput.setPreferredSize(d2);
////        c.gridx = 1;
////        c.gridwidth = 3;
////        c.gridheight =2;
////        panel2.add(scrollOutput,c);
                
        
        return p;
    }

    private void setData(OutputKegiatan outke) {
        if(k_outke != null){
            k_txtnrProdi.setText(outke.getK_kedip().getProgramDipa().getProgram().getProgramCode());
            k_txtDetailProdi.setText(outke.getK_kedip().getProgramDipa().getProgram().getProgramName());
            k_txtTahun.setText(""+outke.getK_kedip().getProgramDipa().getTahunAnggaran());
            
            k_txtnrKegiatan.setText(outke.getK_kedip().getK_kegiatan().getKegiatanCode());
            k_txtDetailKegiatan.setText(outke.getK_kedip().getK_kegiatan().getKegiatanName());
            String nr = "";
            if(outke.getNroutput() < 10 && outke.getNroutput() > 0){
                nr = "00"+outke.getNroutput();
            }else if(outke.getNroutput() >=10 && outke.getNroutput() < 100){
                nr= "0"+outke.getNroutput();
            }else{
                nr = ""+outke.getNroutput();
            }
            k_txtnrOutput.setText(nr);
            k_txtDetailOutput.setText(outke.getOutput());
            
        }else{
            k_txtnrProdi.setText("");
            k_txtDetailProdi.setText("");
            k_txtTahun.setText("");
            
            k_txtnrKegiatan.setText("");
            k_txtDetailKegiatan.setText("");
//            String nr = "";
//            if(outke.getNroutput() < 10 && outke.getNroutput() > 0){
//                nr = "00"+outke.getNroutput();
//            }else if(outke.getNroutput() >=10 && outke.getNroutput() < 100){
//                nr= "0"+outke.getNroutput();
//            }else{
//                nr = ""+outke.getNroutput();
//            }
            k_txtnrOutput.setText("");
            k_txtDetailOutput.setText("");
        }
    }
    
    
    void setTextKedip(KegiatanDipa kedip){
        if(kedip!= null){
            k_txtnrProdi.setText(kedip.getProgramDipa().getProgram().getProgramCode());
            k_txtTahun.setText(""+kedip.getProgramDipa().getTahunAnggaran());
            k_txtDetailProdi.setText(kedip.getProgramDipa().getProgram().getProgramName());
            
            k_txtnrKegiatan.setText(kedip.getK_kegiatan().getKegiatanCode());
            k_txtDetailKegiatan.setText(kedip.getK_kegiatan().getKegiatanName());
        }else{
            k_txtnrProdi.setText("");
            k_txtTahun.setText("");
            k_txtDetailProdi.setText("");
            
            k_txtnrKegiatan.setText("");
            k_txtDetailKegiatan.setText("");
        }
    }
}

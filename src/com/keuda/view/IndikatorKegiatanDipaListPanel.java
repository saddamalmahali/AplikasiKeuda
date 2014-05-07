/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.IndikatorKegiatan;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.OutputKegiatan;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuListCombo;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.LabelSeparator;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author client
 */
public class IndikatorKegiatanDipaListPanel extends KeudaPanelButton implements ListSelectionListener{
    JPanel k_panelIndikatorCenter, k_panelDetailIndikator, k_panelList;
    BunxuListCombo k_listIndikator;
    MainForm k_mainForm;
    KegiatanDipa[] k_listKedip;
    IndikatorKegiatan[] k_listInkeu;
    IndikatorKegiatan k_inkeu;
    KegiatanDipa k_kedip;
    
    
    JTable k_table;
    int k_selectedIndex = -1;
    JTextField k_txtKategori;
    JButton k_btnBrowse;
    KegiatanDipaListDialog k_dialogKedipList;
    JPanel k_panel;
    JTextField k_txtnrKegiatan, k_txtnrProdi, k_txtTahun, k_txtnrIndikator;
    
    JTextArea k_txtDetailKegiatan, k_txtDetailProdi, k_txtDetailIndikator;
    
    
    boolean isNew = false;

    public IndikatorKegiatanDipaListPanel(MainForm mainForm) {
        super(mainForm);
        k_panelIndikatorCenter = new JPanel(new BorderLayout());
        
        k_listIndikator = new BunxuListCombo();
        k_listIndikator.setSize(100, getHeight());
        k_mainForm = mainForm;
        
        constructComponents();
        
        setEditableComponent(false);
        k_listIndikator.addListSelectionListener(this);
        k_listIndikator.k_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        k_txtDetailIndikator.setLineWrap(true);
        k_txtDetailIndikator.setWrapStyleWord(true);
        
        k_txtDetailKegiatan.setLineWrap(true);
        k_txtDetailKegiatan.setWrapStyleWord(true);
        
        k_txtDetailProdi.setLineWrap(true);
        k_txtDetailProdi.setWrapStyleWord(true);
        
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    
    
    @Override
    public void onNew() {
        k_inkeu = null;
        setData(k_inkeu);
        
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
            return;
        }
    }

    @Override
    public void onEdit() {
        setData(k_inkeu);
        setEditableComponent(true);
        k_txtnrIndikator.setEditable(false);
        isNew = false;
        k_panelButton.setState(k_panelButton.AFTER_NEW_STATE);
                
    }

    @Override
    public void onCancel() {
        k_inkeu = null;
        setData(k_inkeu);
        setEditableComponent(false);
        initList();
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }

    @Override
    public void onDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah anda yakin akan menghapus data dengan no : "+k_inkeu.getNrindikator(), "Perhatian", JOptionPane.OK_CANCEL_OPTION);
        if(confirm == JOptionPane.OK_OPTION){
            long kegiatanDipa = k_inkeu.getK_kedip().getKegiatandipaindex();
            int nrIndikator = k_inkeu.getNrindikator();
            try {
                k_logic.deleteIndikatorKegiatan(kegiatanDipa, nrIndikator, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (Exception e) {
            }
            
            k_inkeu = null;
            setData(k_inkeu);
            initList();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }else {
            return ;
        }
    }

    @Override
    public void onSave() {
        if(isNew){
            getData();
            try {
                k_logic.createIndikatorKegiatan(k_inkeu, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (Exception e) {
                
            }
            
            k_inkeu = null;
            setData(k_inkeu);
            initList();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }else{
            updateData();
            try {
                k_logic.updateIndikatorKegiatan(k_inkeu, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (Exception e) {
                
            }
            
            k_inkeu = null;
            setData(k_inkeu);
            initList();
            k_panelButton.setState(k_panelButton.NEW_STATE);
        }
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(k_listIndikator.getSelectedValue() != null){
            k_selectedIndex = k_listIndikator.getSelectedIndex();
            Object obj = k_listIndikator.getSelectedValue();
            if(obj instanceof IndikatorKegiatan){
                IndikatorKegiatan inkeu = (IndikatorKegiatan) obj;
                k_inkeu = inkeu;
                
                
                setData(k_inkeu);
                k_panelButton.setState(k_panelButton.AFTER_SAVE_STATE);
            }
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
                if(k_dialogKedipList.getKegiatanDipa() != null){
                    KegiatanDipa kedip = k_dialogKedipList.getKegiatanDipa();
                    kedip.setView(KegiatanDipa.VIEW_CODE_AND_NAME_KEGIATAN);
                    k_txtKategori.setText(""+kedip);
                    
                    k_kedip = kedip;
                    k_inkeu = null;
                    setData(k_inkeu);
                    initList(k_dialogKedipList.getKegiatanDipa());
                    k_panelButton.setState(k_panelButton.NEW_STATE);
                }else{
                    k_listIndikator.removeAllElements();
                    k_txtKategori.setText("");
                    initList();
                    k_inkeu = null;
                    setData(k_inkeu);
                    k_panelButton.setState(k_panelButton.NEW_STATE);
                }
            }
        });
        
        k_panel = new JPanel(new FlowLayout());
        JPanel panelHeader = new JPanel(new FlowLayout());
        JLabel labelHeader = new JLabel("Rincian Indikator");
        
        JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
        separator2.setBounds(5, 5, k_panel.getWidth(), 2);
        labelHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        
        labelHeader.setBorder(new EmptyBorder(new Insets(2, k_panel.getWidth()/2, 10, k_panel.getWidth()/2)));
        panelHeader.add(labelHeader, "North");
        panelHeader.add(separator2, "Center");
        separator2.setVisible(true);
        k_panel.add(panelHeader);
        k_panel.add(constructDetail());
        
        
        JPanel panelFilter = new JPanel(new BorderLayout());
        panelFilter.add(new LabelSeparator("Pilih Kategori"), BorderLayout.NORTH);
        Dimension d = new Dimension(160, 25);
        k_txtKategori.setPreferredSize(d);
        k_txtKategori.setEditable(false);
        panelFilter.add(k_txtKategori, BorderLayout.WEST);
        panelFilter.add(k_btnBrowse, BorderLayout.EAST);
//        k_panel.add(constructDetail());
        JPanel panelList = new JPanel(new BorderLayout());
        panelList.add(panelFilter, BorderLayout.NORTH);
        panelList.add(k_listIndikator, BorderLayout.CENTER);
        
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelList, k_panel);
        split.setDividerLocation(200);
        getContentPane().add(split, BorderLayout.CENTER);
                
    }
    void initList(KegiatanDipa kedip){
        k_listIndikator.removeAllElements();
        try {
            k_listInkeu = k_logic.getAllIndikatorKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i = 0; i<k_listInkeu.length; i++){
                IndikatorKegiatan inkeu = k_listInkeu[i];
                inkeu.setView(IndikatorKegiatan.VIEW_NR_AND_OUTPUT);
                k_listIndikator.addElement(inkeu);
            }
        } catch (Exception e) {
            
        }
    }
    void initList(){
        try {
            k_listIndikator.removeAllElements();
            k_listInkeu = k_logic.getAllIndikatorKegiatan(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            if(k_listInkeu != null){
                for(int i=0; i<k_listInkeu.length; i++){
                    IndikatorKegiatan inkeu = k_listInkeu[i];
                    inkeu.setView(inkeu.VIEW_NR_AND_OUTPUT);
                    k_listIndikator.addElement(inkeu);
                }
            }
        } catch (Exception e) {
        }
    }
    
    void setData(IndikatorKegiatan inkeu){
        if(k_inkeu != null){
            k_txtnrProdi.setText(inkeu.getK_kedip().getProgramDipa().getProgram().getProgramCode());
            k_txtDetailProdi.setText(inkeu.getK_kedip().getProgramDipa().getProgram().getProgramName());
            k_txtTahun.setText(""+inkeu.getK_kedip().getProgramDipa().getTahunAnggaran());
            
            k_txtnrKegiatan.setText(inkeu.getK_kedip().getK_kegiatan().getKegiatanCode());
            k_txtDetailKegiatan.setText(inkeu.getK_kedip().getK_kegiatan().getKegiatanName());
            String nr = "";
            if(inkeu.getNrindikator() < 10 && inkeu.getNrindikator() > 0){
                nr = "00"+inkeu.getNrindikator();
            }else if(inkeu.getNrindikator() >=10 && inkeu.getNrindikator() < 100){
                nr= "0"+inkeu.getNrindikator();
            }else{
                nr = ""+inkeu.getNrindikator();
            }
            k_txtnrIndikator.setText(nr);
            k_txtDetailIndikator.setText(inkeu.getIndikator());
            
        }else{
            k_txtnrProdi.setText("");
            k_txtDetailProdi.setText("");
            k_txtTahun.setText("");
            
            k_txtnrKegiatan.setText("");
            k_txtDetailKegiatan.setText("");
            k_txtnrIndikator.setText("");
            k_txtDetailIndikator.setText("");
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
    
    public JPanel constructDetail(){
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(new Insets(2, 5, 2, 10)));
        p.setLayout(new GridBagLayout());
        
        EmptyBorder border = new EmptyBorder(new Insets(0, 0, 0, 20));
        EmptyBorder border2 = new EmptyBorder(new Insets(2, 5, 2, 5));
        EmptyBorder border3 = new EmptyBorder(new Insets(0, 0, 0, 10));
        
        Dimension d = new Dimension(60, 20);
        Dimension d2 = new Dimension(200, 40);
        Dimension d3 = new Dimension(200, 100);
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 5, 2, 5);
        c.anchor = GridBagConstraints.WEST;
        
        JLabel lblKodeProgram = new JLabel("Kode");
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
        p.add(k_txtnrProdi, c);
        
        JLabel lblTahunProdi = new JLabel("Tahun");
        lblTahunProdi.setBorder(border2);
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
        p.add(k_txtTahun, c);
        
        JLabel lblDetailProdi = new JLabel("Program");
        lblDetailProdi.setBorder(border);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.weighty  = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(lblDetailProdi, c);
        
        k_txtDetailProdi = new JTextArea("");
        JScrollPane scroll = new JScrollPane(k_txtDetailProdi);
        scroll.setPreferredSize(d2);
        c.gridx = 1;
        c.gridheight = 2;
        c.gridwidth = 3;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        p.add(scroll, c);
        
        c.gridy = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridwidth = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(15,5,5,5);
        //p.add(new JSeparator(),c);
        
        //c.insets = new Insets(2,5,5,5);
        c.gridy =5;
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
        JLabel lblDetailKeigatan = new JLabel("Kegiatan");
        p.add(lblDetailKeigatan, c);
        
        k_txtDetailKegiatan = new JTextArea();
        JScrollPane scrollKegiatan = new JScrollPane(k_txtDetailKegiatan);
        scrollKegiatan.setPreferredSize(d2);
        
        c.gridx = 1;
        c.gridwidth = 4;
        c.gridheight = 2;
        p.add(scrollKegiatan, c);
                
        c.gridy = 8;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.insets = new Insets(10, 5, 1, 5);
        //p.add(new JLabel("Detail Indikator"), c);
        c.gridy = 9;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 5, 2, 5);
        //p.add(new JSeparator(), c);
        
        c.insets = new Insets(2, 5, 2, 5);
        c.gridy = 10;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        JLabel lblNrIndikator = new JLabel("Nomor Urut");
        lblNrIndikator.setBorder(border3);
        p.add(lblNrIndikator, c);
        
        c.gridx = 1;
        c.gridwidth = 2;
        k_txtnrIndikator = new JTextField();
        k_txtnrIndikator.setPreferredSize(d);
        p.add(k_txtnrIndikator, c);
        
        c.gridy = 11;
        c.gridx = 0;
        c.gridwidth = 1;
        JLabel lblDetailIndikator = new JLabel("Indikator");
        lblDetailIndikator.setBorder(border);
        p.add(lblDetailIndikator, c);
        
        c.gridx = 1;
        c.gridheight = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        k_txtDetailIndikator = new JTextArea();
        
        JScrollPane scrollIndikator = new JScrollPane(k_txtDetailIndikator);
        scrollIndikator.setPreferredSize(d3);
        
        p.add(scrollIndikator, c);
                
        return p;
    }
    
    void setEditableComponent(boolean isEditable){
        k_txtDetailIndikator.setEditable(isEditable);
        k_txtDetailKegiatan.setEditable(false);
        k_txtDetailProdi.setEditable(false);
        k_txtTahun.setEditable(false);
        k_txtnrIndikator.setEditable(isEditable);
        k_txtnrKegiatan.setEditable(false);
        k_txtnrProdi.setEditable(false);
    }
    
    public void getData(){
        if(k_txtnrIndikator.getText().trim().equals("") || k_txtnrIndikator.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Nomor Urut Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(k_txtDetailIndikator.getText().trim().equals("") || k_txtDetailIndikator.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Indikator Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            int nr = Integer.parseInt(k_txtnrIndikator.getText());
            String indikator = k_txtDetailIndikator.getText();
            k_inkeu = new IndikatorKegiatan(k_kedip, nr, indikator);
        }
    }
    
    public void updateData(){
        if(k_txtnrIndikator.getText().trim().equals("") || k_txtnrIndikator.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Nomor Urut Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(k_txtDetailIndikator.getText().trim().equals("") || k_txtDetailIndikator.getText() == null){
            JOptionPane.showMessageDialog(this, "Kolom Indikator Tidak Boleh Kosong", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            k_inkeu.setIndikator(k_txtDetailIndikator.getText());
        }
    }
           
}

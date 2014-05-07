/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.Kegiatan;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.ProgramDipa;
import com.keuda.util.Misc;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author client
 */
public class KegiatanDipaPanelDetail extends KeudaPanel{
    private Kegiatan k_kegiatan;
    private ProgramDipa k_prodi;
    private KegiatanDipa k_kedip;
    
    private MainForm k_MainForm;
    
    private JTextField k_txtprogramDipa_programCode,
                        k_txtprogramDipa_tahunAnggaran,
                        k_txtkegiatan_kegiatanKode;
                        
    private JLabel lbl_lblDetailProgram,
                    lbl_programCode,
                    lbl_tahunAnggaran,
                    lbl_kegiatanKode,
                    lbl_detailKegiatan;
    
    private JTextArea k_txtProgramDipa_detailProgram,
                        k_txtKegiatan_kegiatanDetail;
    
    private JTextField txtProdi, 
                        txtKegiatan;
    private JLabel lblProdi, lblKegiatan;
    
    private JButton btnBrwProdi,
                    btnBrwKegiatan;
    
    
    private KegiatanListDialog k_keglistDialog;
    
    private ProgramDipaListDialog k_prodiListDialog;
    
    public KegiatanDipaPanelDetail(MainForm mainForm) {
        super(mainForm);
        k_prodi = new ProgramDipa();
        k_kegiatan = new Kegiatan();
        
        constructElement();
    }

    private void constructElement() {
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        
        
        Dimension shortDimensi = new Dimension(30, 25);
        Dimension mediumDimensi = new Dimension(80, 25);
        Dimension longDimensi = new Dimension(240, 25);
        Dimension hugeDimensi = new Dimension(240, 40);
        
        
        EmptyBorder border = new EmptyBorder(new Insets(0, 0, 0, 10));
        EmptyBorder border2 = new EmptyBorder(new Insets(0, 10, 0, 10));
        EmptyBorder border3 = new EmptyBorder(new Insets(0, 0, 0, 0));
        GridBagConstraints c = new GridBagConstraints();
        
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = GridBagConstraints.WEST;
        lblProdi = new JLabel("Program DIPA");
        lblProdi.setBorder(border);
        panel.add(lblProdi, c);
        
        
        txtProdi = new JTextField("");
        
        c.gridx = 1;
        
        c.weightx = 1.0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        txtProdi.setPreferredSize(shortDimensi);
        panel.add(txtProdi, c);
        
        btnBrwProdi = new JButton("...");
        c.gridx = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
//        c.fill = GridBagConstraints.BOTH;
        btnBrwProdi.setPreferredSize(shortDimensi);
        btnBrwProdi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                k_prodiListDialog = new ProgramDipaListDialog(k_mainForm);
                k_prodiListDialog.setVisible(true);
                
                if(k_prodiListDialog.getProgramDipa() != null){
                    k_prodi = k_prodiListDialog.getProgramDipa();
                    txtProdi.setText(k_prodi.getProgram().getProgramName());
                    btnBrwKegiatan.setEnabled(true);
                }
                
                
                
            }
        });
        panel.add(btnBrwProdi, c);
        
        lblKegiatan = new JLabel("Kegiatan");
        c.gridx =6;
        c.gridwidth = 1;
        c.gridheight = 1;
//        c.weightx = 0.0;
//        c.weighty = 0.0;
        lblKegiatan.setBorder(border2);
        panel.add(lblKegiatan, c);
        
        txtKegiatan = new JTextField("");
        txtKegiatan.setPreferredSize(mediumDimensi);
        c.gridx = 7;        
        c.gridwidth = 3;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtKegiatan, c);    
        
        btnBrwKegiatan = new JButton("...");
        btnBrwKegiatan.setPreferredSize(shortDimensi);
        btnBrwKegiatan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                k_keglistDialog = new KegiatanListDialog(k_mainForm);
                k_keglistDialog.setVisible(true);
                
                if(k_keglistDialog.getKegiatan() != null){
                    k_kegiatan = k_keglistDialog.getKegiatan();
                    txtKegiatan.setText(k_kegiatan.getKegiatanName());
                    
                }
                
            }
        });
        c.gridx  = 10;
        c.weightx = 0.0;
        panel.add(btnBrwKegiatan, c);
        
        JPanel panelDetailProdi = new JPanel(new GridBagLayout());
        panelDetailProdi.setBorder(BorderFactory.createTitledBorder("Detail Program DIPA"));
//        panelDetailProdi.setBorder(BorderFactory.createTitledBorder("Detail Program DIPA"));
        
//        lbl_programCode = new JLabel("Kode Program");
//        lbl_programCode.setBorder(border2);
//        panelDetailProdi.add(lbl_programCode, BorderLayout.WEST);
//        k_txtprogramDipa_programCode = new JTextField();
//        k_txtprogramDipa_programCode.setPreferredSize(mediumDimensi);
//        
//        
//        panelDetailProdi.add(k_txtprogramDipa_programCode, BorderLayout.CENTER);
//        JLabel label = new JLabel("");
//        label.setBorder(border);
//        panelDetailProdi.add(label, BorderLayout.EAST);
        
        /**
         * Construct Panel Program DIPA Detail
         */
        
//        GridBagConstraints gc = new GridBagConstraints();
//        
//        gc.insets = new Insets(1, 1, 1, 1);
//        gc.anchor = GridBagConstraints.WEST;
//        gc.gridwidth = 1;
//        gc.weightx = 0.0;
//        gc.weighty = 0.0;
//        gc.fill = GridBagConstraints.NONE;
//        lbl_programCode = new JLabel("Kode Program");
//        
//        lbl_programCode.setBorder(border);
//        gc.gridwidth = 1;
//        gc.fill = GridBagConstraints.NONE;
//        panelDetailProdi.add(lbl_programCode, gc);
//        
//        k_txtprogramDipa_programCode = new JTextField();
//        k_txtprogramDipa_programCode.setPreferredSize(mediumDimensi);
//        gc.gridx = 1;
//        gc.gridwidth = 1;
//        gc.weightx = 1.0;
//        
//        
//        
//        panelDetailProdi.add(k_txtprogramDipa_programCode, gc);
//        
//        lbl_tahunAnggaran = new JLabel("Tahun Anggaran ");
//        lbl_tahunAnggaran.setBorder(border);
//        gc.gridx = 0;
//        gc.gridy = 1;
//        gc.gridwidth = 1;
//        
//        panelDetailProdi.add(lbl_tahunAnggaran, gc);
//        
//        k_txtprogramDipa_tahunAnggaran = new JTextField();
//        k_txtprogramDipa_tahunAnggaran.setPreferredSize(mediumDimensi);
//        
//        gc.gridx = 1;
//        gc.fill = GridBagConstraints.NONE;
//        panelDetailProdi.add(k_txtprogramDipa_tahunAnggaran, gc);
//        
//        lbl_lblDetailProgram = new JLabel("Detail Program ");
//        lbl_lblDetailProgram.setBorder(border);
//        gc.gridx = 0;
//        gc.gridy = 2;
//        gc.gridwidth = 1;
//        
//        panelDetailProdi.add(lbl_lblDetailProgram, gc);
//        
//        k_txtProgramDipa_detailProgram = new JTextArea();
//        JScrollPane scroll = new JScrollPane(k_txtProgramDipa_detailProgram);
//        scroll.setPreferredSize(hugeDimensi);
//        gc.gridheight = 2;
//        gc.gridwidth = 3;
//        gc.fill = GridBagConstraints.HORIZONTAL;
//        gc.gridx = 1;
//        panelDetailProdi.add(scroll, gc);
//        /**
//         * End of Construct Panel Program DIPA Detail
//         */
//        
//        c.gridy = 1;
//        c.gridx = 0;
//        c.gridwidth = 6;
//        c.weightx = 0.0;
//        c.weighty = 0.0;
//        panel.add(panelDetailProdi, c);
//        
//        
//        /**
//         * Construct Panel Detail Kegiatan
//         */
//        
//        JPanel panelDetailKegiatan = new JPanel(new GridBagLayout());
//                
//        panelDetailKegiatan.setBorder(BorderFactory.createTitledBorder("Detail Kegiatan"));
//        
//        gc.gridx = 0;
//        gc.gridy = 0;
//        gc.weightx = 0.0;
//        gc.weighty = 0.0;
//        
//        lbl_kegiatanKode = new JLabel("Kode Kegiatan");
//        lbl_kegiatanKode.setBorder(border);
//        panelDetailKegiatan.add(lbl_kegiatanKode, gc);
//        
//        /**
//         * End of Construct Panel Detail Kegiatan
//         */
//        
//        c.gridx = 7;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        panel.add(panelDetailKegiatan, c);
        setTextEnable(false);
        buttonSetEnable(false);
        
        getContentPane().add(panel);
        
    }
    
    
    void setData(ProgramDipa prodi, Kegiatan kegiatan){
        k_kegiatan = kegiatan;
        k_prodi = prodi;
            txtKegiatan.setText(""+k_kegiatan.getKegiatanName());
            
            txtProdi.setText(""+k_prodi.getProgram().getProgramName());
        
    }
    
    void clearData(){
        txtKegiatan.setText("");
        txtProdi.setText("");
        k_kegiatan = new Kegiatan();
        k_prodi = new ProgramDipa();
    }
    
    void kosongkanData(){
        txtKegiatan.setText("");
        txtProdi.setText("");
    }
    
    public Kegiatan getKegiatan(){
        return k_kegiatan;
    }
    
    public ProgramDipa getProgramDipa(){
        return k_prodi;
    }
    
    void setTextEnable(boolean isEnable){
        txtProdi.setEditable(isEnable);
        txtKegiatan.setEditable(isEnable);
    }
    
    void buttonSetEnable(boolean isEnable){
        btnBrwProdi.setEnabled(isEnable);
        if(!isEnable){            
            btnBrwKegiatan.setEnabled(isEnable);
        }
    }

    public Kegiatan getK_kegiatan() {
        return k_kegiatan;
    }

    public ProgramDipa getK_prodi() {
        return k_prodi;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.IndikatorKegiatan;
import com.keuda.model.KegiatanDipa;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
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
public class IndikatorKegiatanDipaFormDialog extends KeudaDialog{
    
    JPanel k_panel;
    JTextField txt_nrIndikator, txt_kegiatanDipa;
    JTextArea txt_indikator;
    JButton btn_brw;
    KegiatanDipaListDialog k_listDialog;
    KegiatanDipa k_kedip;
    IndikatorKegiatan k_inke;
    
    boolean isNew = false;
    
    public IndikatorKegiatanDipaFormDialog(IndikatorKegiatanDipaPanel mainForm) {
        this(mainForm, null);
        
    }

    public IndikatorKegiatanDipaFormDialog(IndikatorKegiatanDipaPanel mainForm, IndikatorKegiatan inke) {
        super(mainForm.k_mainForm, "Form Isian Indikator Kegiatan", 300, 300);

        
    }
    
    
    
    @Override
    public JComponent constructCenterPanel() {
        
        k_panel = new JPanel(new GridBagLayout());
        k_panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        GridBagConstraints c = new GridBagConstraints();
        k_inke = new IndikatorKegiatan();
        
        Dimension shortField = new Dimension(40, 20);
        Dimension mediumField = new Dimension(120, 20);
        Dimension longField = new Dimension(240, 20);
        Dimension hugeField = new Dimension(150, 20);
        
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.insets = new Insets(2, 2, 2, 2);
        EmptyBorder border = new EmptyBorder(new Insets(0, 5, 0, 10));
        
        JLabel label = new JLabel("Kediatan DIPA ");
        label.setBorder(border);
        k_panel.add(label, c);
        
        txt_kegiatanDipa = new JTextField("");
        txt_kegiatanDipa.setPreferredSize(longField);
        
        txt_kegiatanDipa.setEditable(false);
        c.weightx = 1.0;
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        k_panel.add(txt_kegiatanDipa, c);
        
        btn_brw = new JButton("...");
        btn_brw.setPreferredSize(shortField);
        btn_brw.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                k_listDialog = new KegiatanDipaListDialog(k_mainForm);
                k_listDialog.setVisible(true);
                if(k_listDialog.getKegiatanDipa() != null){
                    k_kedip = k_listDialog.getKegiatanDipa();
                    k_kedip.setView(KegiatanDipa.VIEW_PROGRAM);
                    txt_kegiatanDipa.setText(""+k_kedip);
                }
                
            }
        });
        c.gridx = 2;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        k_panel.add(btn_brw, c);
        
        label = new JLabel("Nomor Urut");
        label.setBorder(border);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        k_panel.add(label, c);
        
        txt_nrIndikator = new JTextField("");
        txt_nrIndikator.setPreferredSize(mediumField);
        
        c.gridx = 1;
        c.gridwidth = 2;
                
        c.fill = GridBagConstraints.HORIZONTAL;
        k_panel.add(txt_nrIndikator, c);
        
        label = new JLabel("Indikator");
        label.setBorder(border);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        k_panel.add(label, c);
        
        txt_indikator = new JTextArea();
        JScrollPane scroll = new JScrollPane(txt_indikator);
        scroll.setPreferredSize(hugeField);
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        k_panel.add(scroll, c);
        
        return k_panel;
    }
    
    
    public IndikatorKegiatan getIndikatorKegiatan(){
        
        
        return k_inke;
    }
    
    void setData(){
        
        int nrIndikator = Integer.parseInt(txt_nrIndikator.getText());
        String indikator = txt_indikator.getText();
        k_inke = new IndikatorKegiatan(k_kedip, nrIndikator, indikator);
    }
    
    @Override
    public void onOk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

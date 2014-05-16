/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.Dipa;
import com.keuda.model.Rincian;
import com.keuda.model.RincianDipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import com.keuda.util.KeudaPanelButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author client
 */
public class DipaComplete extends KeudaPanelButton implements ListSelectionListener{
    
    Dipa k_dipa;
    Rincian k_rincian;
    RincianDipa k_rdipa;
    JTable k_Table;
    BunxuList k_list;
    Rincian[] k_listRincian;
    Dipa[] k_listDipa;
    RincianDipa[] k_listRDipa;
    JPanel panel;
    JPanel k_panelTopDetail;
    public DipaComplete(MainForm mainForm) {
        super(mainForm);
        
        k_list = new BunxuList();
        
        constructComponents();
    }
    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        
    }

    @Override
    public void onNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCancel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void initList(){
        try {
            k_list.removeAllElements();
            k_listDipa = k_logic.getAllDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            k_listRDipa = k_logic.getAllRincianDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            k_listRincian = k_logic.getAllRincian(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            if(k_listDipa != null){
                for(int i=0; i<k_listRDipa.length; i++){
                    RincianDipa rdipa = k_listRDipa[i];
                    rdipa.setView(rdipa.VIEW_KODEREKENING_NAMAREKENING);
                    k_list.addElement(rdipa);
                }
            }
        } catch (Exception e) {
        }
    }

    private void constructComponents() {
        initList();
        
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        k_panelTopDetail = new JPanel(new FlowLayout());
        k_panelTopDetail.setBorder(BorderFactory.createEtchedBorder());
        k_panelTopDetail.add(constructTopDetail());
        JPanel panel_center_detail = new JPanel(new FlowLayout());
        panel_center_detail.add(constructDetail());
        
        panel.add(panel_center_detail, BorderLayout.CENTER);
        panel.add(k_panelTopDetail, BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, k_list, panel);
        split.setDividerLocation(200);
        getContentPane().add(split, BorderLayout.CENTER);
        k_panelButton.setState(k_panelButton.NEW_STATE);
    }
    
    JPanel constructTopDetail(){
        
        EmptyBorder border = new EmptyBorder(new Insets(0, 0, 0, 10));
        EmptyBorder border2 = new EmptyBorder(new Insets(2, 5, 2, 5));
        EmptyBorder border3 = new EmptyBorder(new Insets(0, 0, 0, 10));
        
        Dimension d = new Dimension(60, 20);
        Dimension d_panjang = new Dimension(100, 20);
        Dimension d2 = new Dimension(200, 40);
        Dimension d3 = new Dimension(200, 100);
        
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = GridBagConstraints.CENTER;
        
        JLabel lbl_Tahun = new JLabel("Tahun");
        lbl_Tahun.setBorder(border);
        c.gridx = 0;
        
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        p.add(lbl_Tahun, c);
        
        JTextField txt_tahun = new JTextField("");
        txt_tahun.setPreferredSize(d);
        c.gridx = 1;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
        p.add(txt_tahun, c);
        
        JLabel lbl_nomor = new JLabel("Nomor DIPA");
        lbl_nomor.setBorder(border2);
        c.gridx = 4;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 1.0;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        p.add(lbl_nomor, c);
        
        JTextArea txt_nomor = new JTextArea("");
        JScrollPane scroll = new JScrollPane(txt_nomor);
        scroll.setPreferredSize(d_panjang);
//        JTextField txt_nomor = new JTextField("");
//        txt_nomor.setPreferredSize(d_panjang);
        c.gridx = 5;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.BOTH;
        p.add(scroll, c);
        
        
        return p;
    }
    JPanel constructDetail(){
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        p.setLayout(new GridBagLayout());
        
//        EmptyBorder border = new EmptyBorder(new Insets(0, 0, 0, 10));
//        EmptyBorder border2 = new EmptyBorder(new Insets(2, 5, 2, 5));
//        EmptyBorder border3 = new EmptyBorder(new Insets(0, 0, 0, 10));
//        
//        Dimension d = new Dimension(60, 20);
//        Dimension d_panjang = new Dimension(100, 40);
//        Dimension d2 = new Dimension(200, 40);
//        Dimension d3 = new Dimension(200, 100);
//        
//        GridBagConstraints c = new GridBagConstraints();
//        c.insets = new Insets(5, 5, 5, 5);
//        c.anchor = GridBagConstraints.WEST;
//        
//        
//        
//                
//        JLabel lbl_kegiatan = new JLabel("Kegiatan");
//        
//        lbl_kegiatan.setBorder(border);
//        c.gridx = 0;
//        c.gridy = 1;
//        c.gridwidth = 1;
//        c.weightx = 1.0;
//        c.insets = new Insets(20, 5, 2, 5);
//        p.add(lbl_kegiatan, c);
//        
//        
//        c.gridx = 0;
//        c.gridy = 2;
//        c.gridwidth = 2;
//        c.insets = new Insets(2, 5, 2, 5);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        p.add(new JSeparator(), c);
//        
////        JLabel lbl_
//        
//        c.insets = new Insets(2, 5, 5, 5);
//        c.gridy = 3;
//        c.gridwidth = 1;
//        c.fill = GridBagConstraints.NONE;
//        JLabel lbl_kode_prg = new JLabel("Kode");
//        lbl_kode_prg.setBorder(border);
//        p.add(lbl_kode_prg, c);
//        
//        c.gridx = 1;
//        c.weightx = 0.0;
//        JTextField txt_kodeprg = new JTextField("");
//        txt_kodeprg.setPreferredSize(d);
//        p.add(txt_kodeprg, c);
//        
//        JLabel lbl_kdkegiatan = new JLabel("Kode");
//        lbl_kdkegiatan.setBorder(new EmptyBorder(0, 10, 0, 0));
//        c.gridx = 4;
//        
//        p.add(lbl_kdkegiatan, c);
//        
//        JTextField txt_kdkegiatan = new JTextField("");
//        txt_kdkegiatan.setPreferredSize(d);
//        c.gridx = 5;
//        c.gridwidth = 1;
//        p.add(txt_kdkegiatan, c);
        
        return p;
    }
}

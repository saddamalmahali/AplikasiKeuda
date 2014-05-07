/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.model.Akun;
import com.keuda.services.IDBCConstant;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author user
 */
public class AkunAddDialog extends JDialog implements ActionListener{
    JFrame k_owner;
    AkunTreePanel k_akunPanel;
    AkunTree akunTree;
    
    
    JLabel k_superLabel = new JLabel(" ");
    JLabel k_kodeLabel = new JLabel("", SwingConstants.RIGHT);
    JTextField k_kodeText = new JTextField();
    JTextField k_nameText = new JTextField();
    
    JRadioButton k_grupRadio = new JRadioButton("Grup                    ");
    JRadioButton k_leafRadio = new JRadioButton("Bukan Grup");
    
    JComboBox k_LevelCombo = new JComboBox(
            new Object[]{"",
                Akun.STR_LEVEL_1, 
                Akun.STR_LEVEL_2,
                Akun.STR_LEVEL_3,
                Akun.STR_LEVEL_4,
                Akun.STR_LEVEL_5,
                Akun.STR_LEVEL_6
            });
    
    JComboBox k_tipeCombo = new JComboBox(
            new Object[]{
                Akun.STR_TIPE_1,
                Akun.STR_TIPE_2,
                Akun.STR_TIPE_3,
                Akun.STR_TIPE_4,
                Akun.STR_TIPE_5,
                Akun.STR_TIPE_6
            });
    
    JButton k_okBt = new JButton("Ok");
    JButton k_cancelBt = new JButton("Batal");
    
    Connection k_conn;
    long k_sessionid = -1;
    
    DefaultMutableTreeNode k_supernode;

     public AkunAddDialog(AkunTreePanel akunPanel, DefaultMutableTreeNode supernode) {
        super(akunPanel.k_mainForm, "Penambahan Akun", true);
        setResizable(false);
        setSize(400,300);
        
        k_owner = akunPanel.k_mainForm;
        k_akunPanel = akunPanel;
        k_supernode = supernode;
        
        k_conn = akunPanel.k_conn;
        k_sessionid = akunPanel.k_sessionId;
        
        
        constructComponents();
        init();
    }

    public AkunAddDialog(AkunTreePanel akunPanel, String title, DefaultMutableTreeNode parentNode, Akun obj) {
        super(akunPanel.k_mainForm, title, true);
        setResizable(false);
        setSize(400, 270);
        
        k_owner = akunPanel.k_mainForm;
        k_akunPanel = akunPanel;
        k_supernode = parentNode;
        
        k_conn = akunPanel.k_conn;
        k_sessionid = akunPanel.k_sessionId;
        
    }
    
    
    
    void constructComponents(){
        JPanel inputPanel = new JPanel();
        JPanel akunPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        akunPanel.setLayout(gbl);
        
        JLabel lsuper = new JLabel("Akun Induk : ");
        gbc.fill = gbc.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbl.setConstraints(lsuper, gbc);
        akunPanel.add(lsuper);
        
        gbc.gridx =1;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(k_superLabel, gbc);
        akunPanel.add(k_superLabel);
        
        JLabel lcode = new JLabel(" Kode Akun: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbl.setConstraints(lcode, gbc);
        akunPanel.add(lcode);
        
        gbc.gridx = 1;
        gbl.setConstraints(k_kodeLabel, gbc);
        akunPanel.add(k_kodeLabel);
        
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(k_kodeText, gbc);
        akunPanel.add(k_kodeText);
        
        JLabel lname = new JLabel(" Nama Akun : ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbl.setConstraints(lname, gbc);
        akunPanel.add(lname);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(k_nameText, gbc);
        akunPanel.add(k_nameText);
        
        JLabel lLevel = new JLabel("Kelompok Akun : ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbl.setConstraints(lLevel, gbc);
        akunPanel.add(lLevel);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(k_LevelCombo, gbc);
        akunPanel.add(k_LevelCombo);
        
        JLabel lTipe = new JLabel("Jenis Akun : ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbl.setConstraints(lTipe, gbc);
        akunPanel.add(lTipe);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(k_tipeCombo, gbc);
        akunPanel.add(k_tipeCombo);
        
        JPanel groupPanel = new JPanel();
        groupPanel.add(k_grupRadio, BorderLayout.WEST);
        groupPanel.add(k_leafRadio, BorderLayout.EAST);
        groupPanel.setBorder(new TitledBorder(new EtchedBorder(), "Tipe Akun"));
        
        ButtonGroup bgg = new ButtonGroup();
        bgg.add(k_grupRadio);
        bgg.add(k_leafRadio);
        k_grupRadio.setSelected(true);
        k_grupRadio.setEnabled(true);
        k_leafRadio.setEnabled(true);
        
        JPanel pnCenter = new JPanel();
        pnCenter.setLayout(new BorderLayout());
        pnCenter.add(BorderLayout.CENTER, akunPanel);
        pnCenter.add(BorderLayout.SOUTH, groupPanel);
        
        JPanel controlPanel = new JPanel();
        controlPanel.add(k_okBt);
        controlPanel.add(k_cancelBt);
        k_okBt.addActionListener(this);
        k_cancelBt.addActionListener(this);
        controlPanel.setBorder(new EtchedBorder());
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnCenter, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);        
    }
    
    
    void init(){
        if(k_supernode != null){
            Akun akun = (Akun) k_supernode.getUserObject();
            if(akun != null){
                k_superLabel.setText(akun.toString());
                k_kodeLabel.setText(akun.getKodeAkun()+ k_akunPanel.k_accFormat);
                
                k_tipeCombo.setSelectedIndex(akun.getTipeakun());
                k_LevelCombo.setSelectedItem(akun.getLevelAsString());
                if(akun.getTipeakun() <= Akun.TIPE_3){
                    k_grupRadio.setSelected(true);
                    k_leafRadio.setSelected(false);
                }else{
                    k_grupRadio.setSelected(false);
                    k_leafRadio.setSelected(true);
                }
                
                k_LevelCombo.setEnabled(true);
                
                k_tipeCombo.setEnabled(false);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_cancelBt){
            dispose();
        }else if(e.getSource() == k_okBt){
            
            
            String kode = k_kodeText.getText().trim();
            if(kode.equals("")){
                JOptionPane.showMessageDialog(this, "Kode akun tidak boleh kosong");
                return;
            }
            
            String nama = k_nameText.getText().trim();
            if(nama.equals("")){
                JOptionPane.showMessageDialog(this, "Nama akun tidak boleh kosong");
                return;
            }
            
            String stracctipe = (String) k_tipeCombo.getSelectedItem();
            if(stracctipe.equals("")){
                JOptionPane.showMessageDialog(this, "Jenis akun perlu didefinisikan");
                return;
            }
            short tipeAkun = Akun.tipeFromStringToID(stracctipe);
            
            String straklevel = (String) k_LevelCombo.getSelectedItem();
            
            if(straklevel.equals("")){
                JOptionPane.showMessageDialog(this, "Kategori akun perlu di definisikan");
                return;
            }
            short akunLevel = Akun.LevelFromStringToId(straklevel);
            
            boolean tipeGrup;
            if(k_grupRadio.isSelected())
                tipeGrup = Akun.GROUP;
            else
                tipeGrup = Akun.NOTGROUP;
            
            String superkode = "";
            long indexsupercode = -1;
            Akun superAkun = null;
            
            if(k_supernode != null){
                superAkun = (Akun) k_supernode.getUserObject();
                superkode = superAkun.getKodeAkun();
                indexsupercode = superAkun.getAkunIndex();
                kode = superkode+k_akunPanel.k_accFormat + kode;
            }
            
            Akun akun = new Akun(kode, nama, tipeAkun, akunLevel, tipeGrup);
            akun.setParent(superAkun);
            
            BusinessLogic logic = new BusinessLogic(k_conn);
            
            try {
                Akun akk = (Akun) logic.createAkun(akun, k_sessionid, IDBCConstant.MODUL_CONFIGURATION);
                k_akunPanel.insertAkun(akun, k_supernode);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Perhatian", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
                
                k_kodeText.setText("");
                k_nameText.setText("");
                if(k_supernode == null){
                    k_tipeCombo.setSelectedItem("");
                    k_LevelCombo.setSelectedItem("");
                }
                init();
                dispose();
        }
    }
    
}

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

/**
 *
 * @author user
 */
public class AkunEditDialog extends JDialog implements ActionListener{
    JFrame k_owner;
    AkunTreePanel k_akunPanel;
    
    JLabel k_superLabel = new JLabel(" ");
    JLabel k_kodeLabel = new JLabel("");
    JTextField k_kodeText = new JTextField();
    JTextField k_nameText = new JTextField();
    
    JRadioButton k_grupRadio = new JRadioButton("Grup                    ");
    JRadioButton k_leafRadio = new JRadioButton("Bukan Grup");
    
    JComboBox k_LevelCombo = new JComboBox(
            new Object[]{
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
                Akun.STR_TIPE_5
            });
    
    JButton k_okBt = new JButton("Ok");
    JButton k_cancelBt = new JButton("Batal");
    
    Connection k_conn;
    long k_sessionid = -1;
    
    DefaultMutableTreeNode k_node;
    String k_kode;
    long k_index;
    boolean k_tipe;
    boolean k_grup;

    public AkunEditDialog(AkunTreePanel akunPanel, DefaultMutableTreeNode node) {
        super(akunPanel.k_mainForm, "Perubahan Akun", true);
        setResizable(false);
        setSize(400, 300);
        
        k_owner = akunPanel.k_mainForm;
        k_akunPanel = akunPanel;
        k_node = node;
        
        k_conn = akunPanel.k_conn;
        k_sessionid = akunPanel.k_sessionId;
        
        constructComponents();
        init();
        
    }
    
    void constructComponents(){
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
        
        JPanel groupPanel = new JPanel();
        groupPanel.add(k_grupRadio, BorderLayout.WEST);
        groupPanel.add(k_leafRadio, BorderLayout.EAST);
        groupPanel.setBorder(new TitledBorder(new EtchedBorder(), "Tipe Akun"));
        
        ButtonGroup bgg = new ButtonGroup();
        bgg.add(k_grupRadio);
        bgg.add(k_leafRadio);
        k_grupRadio.setSelected(true);
        k_grupRadio.setEnabled(false);
        k_leafRadio.setEnabled(false);
        
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
        DefaultMutableTreeNode supernode = (DefaultMutableTreeNode) k_node.getParent();
        Akun akun = (Akun) k_node.getUserObject();
        
        System.out.println(k_node);
        
        k_kode = akun.getKodeAkun();
        k_index = akun.getAkunIndex();
        
        if(supernode != k_akunPanel.getRoot()){
            System.out.println(" : "+supernode);
            
            Object obj = supernode.getUserObject();
            if(obj instanceof Akun){
                akun.setParent((Akun)obj);
            }
            
            k_superLabel.setText(akun.getParent().toString());
        }
        
        k_kodeLabel.setText(k_kode);
        k_nameText.setText(akun.getNamaAkun());
        k_tipeCombo.setSelectedItem(akun.getTipeAsString());
        k_LevelCombo.setSelectedItem(akun.getLevelAsString());
        if(akun.isAkungrup() == Akun.GROUP)
            k_grupRadio.setSelected(true);
        else
            k_leafRadio.setSelected(true);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_cancelBt){
            k_node = null;
            dispose();
        }else if(e.getSource()==k_okBt){
            String nama = k_nameText.getText().trim();
            
            if(nama.equals("")){
                JOptionPane.showMessageDialog(this, "Nama Rekening tidak boleh kosong");
                return;
            }
            
            String straktipe = (String) k_tipeCombo.getSelectedItem();
            if(straktipe.equals("")){
                JOptionPane.showMessageDialog(this, "Jenis Akun Perlu Didefinisikan");
                return;
                
            }
            
            short akunTipe = Akun.tipeFromStringToID(straktipe);
            
            String strakLevel = (String) k_LevelCombo.getSelectedItem();
            if(strakLevel.equals("")){
                JOptionPane.showMessageDialog(this, "Level akun perlu didefinisikan");
                return;
            }
            short akunLevel = Akun.LevelFromStringToId(strakLevel);
            
            boolean tipegrup;
            if(k_grupRadio.isSelected())
                tipegrup = Akun.GROUP;
            else
                tipegrup = Akun.NOTGROUP;
            
            Akun akun = new Akun(k_index, k_kode, nama, akunTipe, akunLevel, tipegrup);
            
            BusinessLogic logic = new BusinessLogic(k_conn);
            try {
                logic.updateAkun(k_index, akun, k_sessionid, IDBCConstant.MODUL_CONFIGURATION);
                k_akunPanel.updateAkun(akun, k_node);
                
                dispose();
            } catch (Exception ex) {                
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Perhatian", JOptionPane.WARNING_MESSAGE);
            }
            
            k_node = null;
        }
        
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author BENDAHARA
 * 
 * 
 * 1.   bendaharaindex
 * 2.   satkerid
 * 3.   jenisbendahara
 * 4.   nip
 * 5.   alamat
 * 6.   email
 * 7.   kodebank
 * 8.   rekening
 * 9.   saldo
 * 10.  npwp
 * 11.  tanggal
 * 12.  bankid
 * 13.  nama
 * 14.  kodebendahara
 * 15.  telepon
 * 
 * 
 */
public class SupplierDialog extends KeudaDialog{
    JTextField txt_alamat, txt_email, txt_rekening, txt_saldo, txt_npwp, txt_nama, txt_telepon;
    JComboBox cbo_satker, cbo_jenisbendahara, cbo_kodebank, cbo_bank;
    
    
    
    public SupplierDialog(MainForm mainForm) {
        super(mainForm, "");
        
        
        
    }

    
    
    @Override
    public JComponent constructCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JPanel panelcenter = new JPanel(new GridBagLayout());
        panelcenter.setBorder(BorderFactory.createEtchedBorder());
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;
        
        
        
        return panel;
    }

    @Override
    public void onOk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.Kegiatan;
import com.keuda.model.Program;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author user
 */
public class KegiatanAddEditDialog extends JDialog implements ActionListener {
    JButton btn_saveedit, btn_btl;
    JLabel lbl_programid, lbl_kegiatancode, lbl_kegiatanname;
    JComboBox cbo_program;
    JTextField txt_kegiatancode, txt_kegiatanname;
    Program[] program;
    Kegiatan kgt;
    Program prg;
    
    public static final long VIEW_SAVE_DIALOG = 0;
    public static final long VIEW_EDIT_DIALOG = 1;
    public long view = -1;
    
    public final String[] viewState = new String[]{
       "Save", "Edit" 
    };
    
    public KegiatanAddEditDialog(KegiatanPanel panel, String title, Program[] program,Kegiatan kgt, Program prg) {
        super(panel.k_mainForm, title);
        setResizable(false);
        setSize(400, 300);
        this.program = program;
        this.prg = prg;
        if(kgt != null)
            this.kgt = kgt;
        else
            this.kgt = new Kegiatan();
        
        if(prg != null)
            this.prg = prg;
        else 
            this.prg = new Program();
        
        
        comboKegiatanModel model = new comboKegiatanModel();
        btn_saveedit = new JButton("");
        btn_btl = new JButton("Batal");
        cbo_program = new JComboBox(model);
        txt_kegiatancode = new JTextField("");
        txt_kegiatanname = new JTextField("");
        lbl_programid = new JLabel("Program ID :");
        lbl_kegiatancode = new JLabel("Kode Kegiatan :");
        lbl_kegiatanname = new JLabel("Nama Kegiatan : ");
        constructComponens();
    }
    
    public void setView(long viewState){
        if(viewState == VIEW_SAVE_DIALOG){
            txt_kegiatanname.setText("");
            txt_kegiatancode.setText("");
            cbo_program.setSelectedIndex(0);
        }else if(viewState == VIEW_EDIT_DIALOG){
            cbo_program.setSelectedItem(cbo_program.getSelectedItem().equals(prg.getProgramName()));
            txt_kegiatancode.setText(kgt.getKegiatanCode());
            txt_kegiatanname.setText(kgt.getKegiatanName());
        }else{
            return ;
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet...");
    }

    private void constructComponens() {
        JPanel button_panel = new JPanel();
        JPanel label_panel = new JPanel();
        JPanel detail_panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        detail_panel.setLayout(gbl);
        
        gbc.fill = gbc.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbl.setConstraints(lbl_programid, gbc);
        detail_panel.add(lbl_programid);
        
        gbc.gridx =1;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(cbo_program, gbc);
        detail_panel.add(cbo_program);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.gridwidth =1;
        gbl.setConstraints(lbl_kegiatancode, gbc);
        detail_panel.add(lbl_kegiatancode);
        
        gbc.gridx =1;
        gbc.weightx =1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(txt_kegiatancode, gbc);
        detail_panel.add(txt_kegiatancode);
        
        gbc.gridx =0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbl.setConstraints(lbl_kegiatanname, gbc);
        detail_panel.add(lbl_kegiatanname);
        
        gbc.gridx =1;
        gbc.weightx = 1.0;
        gbc.gridwidth = gbc.REMAINDER;
        gbl.setConstraints(txt_kegiatanname, gbc);
        detail_panel.add(txt_kegiatanname);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(detail_panel, BorderLayout.CENTER);
        
                
    }
    
    class comboKegiatanModel extends DefaultComboBoxModel implements ActionListener{

        public comboKegiatanModel() {     
            addElement("");
            for(int i=0; i<program.length; i++){
                addElement(program[i]);
            }
            
        }

        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println();
        }
        
    }
}

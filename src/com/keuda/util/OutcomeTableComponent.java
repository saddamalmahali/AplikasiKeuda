/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.model.OutcomeProgram;
import com.keuda.view.MainForm;
import com.keuda.view.OutcomeProgramDialog;
import com.keuda.view.OutcomeProgramPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author user
 */
public class OutcomeTableComponent extends JPanel implements ActionListener{
    protected JButton btn_view, btn_edit, btn_delete;
    protected JPanel panel;
    protected OutcomeProgram k_outprogram;
    protected JTable k_table;
    protected OutcomeProgramDialog outProg;
    protected MainForm k_mainForm;
    public OutcomeTableComponent() {
        
        setLayout(new BorderLayout());
        setOpaque(true);
        panel = new JPanel(new FlowLayout());
        btn_view = new JButton("View");
        btn_view.setSize(10, 15);
        btn_view.setEnabled(true);
        btn_view.addActionListener(this);
        
        btn_edit = new JButton("Edit");
        btn_edit.setSize(10, 15);
        btn_edit.setEnabled(true);
        btn_edit.addActionListener(this);
        
        btn_delete = new JButton("Hapus");
        btn_delete.setSize(10, 15);
        btn_delete.setEnabled(true);
        btn_delete.addActionListener(this);
        
        panel.add(btn_view);
        panel.add(btn_edit);
        panel.add(btn_delete);
        
        add(panel, BorderLayout.CENTER);
    }

    public void setK_outprogram(OutcomeProgram k_outprogram) {
        this.k_outprogram = k_outprogram;
    }
    
    public OutcomeProgram getK_outprogram() {
        return k_outprogram;
    }

    public void setK_mainForm(MainForm k_mainForm) {
        this.k_mainForm = k_mainForm;
    }

    
    
    
    
    
    public void updatePanel(OutcomeProgram program, boolean isSelected, JTable table){
        this.k_outprogram = program;
        
        if(isSelected){
            setBackground(table.getSelectionBackground());
        }else{
            setForeground(table.getSelectionForeground());
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_view){
            
        }else if(e.getSource() == btn_edit){
//            k_outprogram = OutcomeProgramPanel.getK_outProgram();
//            outProg = new OutcomeProgramDialog(this, true, k_outprogram);
        }else if(e.getSource() == btn_delete){
            
        }
    }
    
}

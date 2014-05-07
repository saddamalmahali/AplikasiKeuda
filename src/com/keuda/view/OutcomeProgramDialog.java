/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.OutcomeProgram;
import com.keuda.model.ProgramDipa;
import com.keuda.services.IDBCConstant;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class OutcomeProgramDialog extends javax.swing.JDialog {

    /**
     * Creates new form OutcomeProgramDialog
     */
    protected OutcomeProgram k_outProg;
    protected MainForm k_mainform;
    protected long sessionId = -1;
    protected Connection k_conn;
    protected BusinessLogic k_logic;
    protected ProgramDipa k_prodi;
    protected ProgramDipaListDialog k_listDialog;
    OutcomeProgramPanel panel;
    protected boolean add = false;
    
    public OutcomeProgramDialog(OutcomeProgramPanel panel, boolean modal) {
        super(panel.k_mainForm, modal);
        initComponents();
        this.panel = panel;
        k_mainform = panel.k_mainForm;
        sessionId = panel.k_mainForm.k_sessionId;
        k_conn = panel.k_conn;
        k_prodi = new ProgramDipa();
        txtProram.setLineWrap(true);
        txtProram.setWrapStyleWord(true);
        
        setLocationRelativeTo(null);
        add = true;
    }
    public OutcomeProgramDialog(OutcomeProgramPanel panel, boolean modal, OutcomeProgram outProg) {
        super(panel.k_mainForm, modal);
        
        initComponents();
        this.panel = panel;
        k_mainform = panel.k_mainForm;
        sessionId = panel.k_mainForm.k_sessionId;
        k_conn = panel.k_conn;
        k_outProg = outProg;
        k_prodi = k_outProg.getProgramDipa();
        txtProgramDipa.setText(""+k_outProg.getProgramDipa());
        txtNoOutcome.setText(""+k_outProg.getNmrUrut());
        txtProram.setText(""+k_outProg.getOutcome());
        txtProgramDipa.setEditable(false);
        txtNoOutcome.setEditable(false);
        setLocationRelativeTo(null);
        txtProram.setLineWrap(true);
        txtProram.setWrapStyleWord(true);
        add = false;
    }
    
    public void ambilData(){
        k_outProg = new OutcomeProgram(Integer.valueOf(txtNoOutcome.getText()), txtProram.getText(), k_prodi);
    }
    public void updateData(){        
        k_outProg.setOutcome(txtProram.getText());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProgramDipa = new javax.swing.JTextField();
        btn_browse = new javax.swing.JButton();
        txtNoOutcome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtProram = new javax.swing.JTextArea();
        btn_batal = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Outcome"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Program DIPA :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nomor :");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Outcome Program :");

        txtProgramDipa.setEditable(false);

        btn_browse.setText("..");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });

        txtProram.setColumns(20);
        txtProram.setRows(5);
        jScrollPane1.setViewportView(txtProram);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtProgramDipa, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_browse, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNoOutcome, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtProgramDipa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_browse)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNoOutcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_simpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batal)
                    .addComponent(btn_simpan))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        if(add){
            if(txtProgramDipa.getText().trim().equals("") || txtProgramDipa.getText() == null){
                JOptionPane.showMessageDialog(this, "Kolom Program DIPA tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }else if(txtNoOutcome.getText().trim().equals("") || txtNoOutcome.getText()==null){
                JOptionPane.showMessageDialog(this, "Kolom No Urut tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }else if(txtProram.getText().trim().equals("") || txtProram.getText() == null){
                JOptionPane.showMessageDialog(this, "Kolom Output Program tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }else{
                ambilData();
                try {
                    panel.save(k_outProg, add);
                } catch (KeudaException ex) {
                    Logger.getLogger(OutcomeProgramDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            dispose();
        }else{
            if(txtProgramDipa.getText().trim().equals("") || txtProgramDipa.getText() == null){
                JOptionPane.showMessageDialog(this, "Kolom Program DIPA tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }else if(txtNoOutcome.getText().trim().equals("") || txtNoOutcome.getText()==null){
                JOptionPane.showMessageDialog(this, "Kolom No Urut tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }else if(txtProram.getText().trim().equals("") || txtProram.getText() == null){
                JOptionPane.showMessageDialog(this, "Kolom Output Program tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }else{
                
                updateData();
                try {
                    panel.save(k_outProg, add);
                } catch (KeudaException ex) {
                    Logger.getLogger(OutcomeProgramDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                dispose();
            }
        }
        
            
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        k_listDialog = new ProgramDipaListDialog(k_mainform);
        k_listDialog.setVisible(true);
        if(k_listDialog.getProgramDipa() != null){
            
        k_prodi = k_listDialog.getProgramDipa();
        }
        k_prodi.setView(ProgramDipa.VIEW_PROGRAM);
        
        txtProgramDipa.setText(""+k_prodi);
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_batalActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtNoOutcome;
    private javax.swing.JTextField txtProgramDipa;
    private javax.swing.JTextArea txtProram;
    // End of variables declaration//GEN-END:variables

    
}

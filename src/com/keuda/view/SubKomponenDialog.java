
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.model.Komponen;
import com.keuda.model.SubKomponen;
import com.keuda.services.IDBCConstant;
import javax.swing.JOptionPane;

/**
 *
 * @author adam
 */
public class SubKomponenDialog extends javax.swing.JDialog {
    private Komponen k_komponen;
    private SubKomponen k_subkomp;
    private MainForm k_mainForm;

    public Komponen getK_komponen() {
        return k_komponen;
    }

    public void setK_komponen(Komponen k_komponen) {
        this.k_komponen = k_komponen;
    }

    public SubKomponen getK_subkomp() {
        return k_subkomp;
    }

    public void setK_subkomp(SubKomponen k_subkomp) {
        this.k_subkomp = k_subkomp;
    }
    long sessionId = -1;
    boolean add = false;
    public SubKomponenDialog(Komponen komp, KomponenPanel panel, boolean modal) {
        super(panel.k_mainForm, modal);
        k_mainForm = panel.k_mainForm;
        k_komponen = komp;
        sessionId = k_mainForm.k_sessionId;
        initComponents();
        add =true;
        setLocationRelativeTo(null);
    }
    
    public SubKomponenDialog(SubKomponen subkomp, Komponen komp, KomponenPanel panel, boolean modal) {
        super(panel.k_mainForm, modal);
        k_mainForm = panel.k_mainForm;
        k_komponen = komp;
        sessionId = k_mainForm.k_sessionId;
        k_subkomp = subkomp;
        initComponents();
        add =false;
        initData(subkomp);
        setLocationRelativeTo(null);
    }
    
    public void initData(SubKomponen subkomp){
        txt_kode.setText(subkomp.getSubkomponenkode());
        txt_detile.setText(subkomp.getSubkomponenname());
    }
    
    public void getData(){
        String kode = txt_kode.getText();
        String detile = txt_detile.getText();
        
        if(kode.trim().equals("")|| kode == null){
            JOptionPane.showMessageDialog(this, "Kolom Kode tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else if(detile.trim().equals("") || detile == null){
            JOptionPane.showMessageDialog(this, "Kolom Detile tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }
        
        k_subkomp = new SubKomponen(k_komponen, kode, detile);
    }
    
    public void getDataUpdate(){
        String kode = txt_kode.getText();
        String detile = txt_detile.getText();
        
        if(kode.trim().equals("")|| kode == null){
            JOptionPane.showMessageDialog(this, "Kolom Kode tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else if(detile.trim().equals("") || detile == null){
            JOptionPane.showMessageDialog(this, "Kolom Detile tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }
        
        k_subkomp.setSubkomponenkode(kode);
        k_subkomp.setSubkomponenname(detile);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_kode = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_detile = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Isian Sub Komponen");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Kode :");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Detile :");

        txt_kode.setPreferredSize(new java.awt.Dimension(60, 20));

        txt_detile.setColumns(20);
        txt_detile.setRows(5);
        jScrollPane1.setViewportView(txt_detile);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_simpan.setText("Simpan");
        btn_simpan.setPreferredSize(new java.awt.Dimension(70, 23));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan);

        btn_batal.setText("Batal");
        btn_batal.setPreferredSize(new java.awt.Dimension(70, 23));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_batal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        if(add){
            getData();
            try {
                BusinessLogic logic = new BusinessLogic(k_mainForm.getK_conn());
                logic.createSubKomponen(k_subkomp, sessionId, IDBCConstant.MODUL_CONFIGURATION);
                JOptionPane.showMessageDialog(this, "Berhasil Menambahkan Sub Komponen kedalam database", "Sukses", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal Dalam Menambahkan Sub Komponen kedalam database. dengan alasan :\n"+e.toString());
            }
            dispose();
        }else{
            getDataUpdate();
            try {
                BusinessLogic logic = new BusinessLogic(k_mainForm.getK_conn());
                logic.updateSubKomponen(k_subkomp.getKomponenindex(), k_subkomp, sessionId, IDBCConstant.MODUL_CONFIGURATION);
                
                JOptionPane.showMessageDialog(this, "Berhasil Memutakhirkan Sub Komponen kedalam database", "Sukses", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal Dalam Memutakhirkan Sub Komponen kedalam database. dengan alasan :\n"+e.toString());
            }
            dispose();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        dispose();
    }//GEN-LAST:event_btn_batalActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txt_detile;
    private javax.swing.JTextField txt_kode;
    // End of variables declaration//GEN-END:variables
}

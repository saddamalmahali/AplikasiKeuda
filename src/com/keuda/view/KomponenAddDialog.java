package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.model.Komponen;
import com.keuda.model.OutputKegiatan;
import com.keuda.services.IDBCConstant;
import javax.swing.JOptionPane;

/**
 *
 * @author adam
 */
public class KomponenAddDialog extends javax.swing.JDialog {

    private MainForm k_mainForm;
    private long sessionId;
    private OutputKegiatan k_outke;
    private Komponen k_komponen;
    
    boolean add = true;
    public KomponenAddDialog(OutputKegiatan outke, KomponenPanel panel, boolean modal) {
        super(panel.k_mainForm, modal);
        k_mainForm = panel.k_mainForm;
        initComponents();
        this.k_outke = outke;
        initData(outke);
        setLocationRelativeTo(null);
        add = true;
    }
    
    public KomponenAddDialog(Komponen komp, OutputKegiatan outke, KomponenPanel panel, boolean modal) {
        super(panel.k_mainForm, modal);
        k_komponen = komp;
        k_mainForm = panel.k_mainForm;
        initComponents();
        this.k_outke = outke;
        initData(outke);
        setLocationRelativeTo(null);
        add = false;
    }
    
    public void initData(OutputKegiatan outke){
        if(outke != null){
            txt_thnanggaran.setText(""+outke.getK_kedip().getProgramDipa().getTahunAnggaran());
            
            txt_kodeprogram.setText(outke.getK_kedip().getProgramDipa().getProgram().getProgramCode());
            txt_program.setText(outke.getK_kedip().getProgramDipa().getProgram().getProgramName());
            txt_kodekegiatan.setText(outke.getK_kedip().getK_kegiatan().getKegiatanCode());
            txt_kegiatan.setText(outke.getK_kedip().getK_kegiatan().getKegiatanName());
            String urut = "";
            int nmrurt = outke.getNroutput();
            String jml = "";
            if(nmrurt > 0 && nmrurt < 10){
                urut = "00"+nmrurt;
                jml = outke.getK_kedip().getK_kegiatan().getKegiatanCode()+"."+urut;
            }else if(nmrurt >9 && nmrurt <100 ){
                urut = "0"+nmrurt;
                jml = outke.getK_kedip().getK_kegiatan().getKegiatanCode()+"."+urut;
            }else{
                urut = ""+nmrurt;
                jml = outke.getK_kedip().getK_kegiatan().getKegiatanCode()+"."+urut;
            }
            
            txt_nourut.setText(jml);
            txt_outke.setText(outke.getOutput());
            
            if(k_komponen != null){
                txt_kodekomponen.setText(k_komponen.getKomponenkode());
                txt_detilekomponen.setText(k_komponen.getKomponenname());
            }
        }else{
            txt_thnanggaran.setText("");
            txt_kodeprogram.setText("");
            txt_program.setText("");
            txt_kodekegiatan.setText("");
            txt_kegiatan.setText("");
            txt_outke.setText("");
        }
    }
    
    
    public void getData(){
        String kode = txt_kodekomponen.getText();
        
        
        String detil = txt_detilekomponen.getText();
        
        if(kode.trim().equals("") || kode == null){
            JOptionPane.showMessageDialog(this, "Kolom Kode tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else if(detil.trim().equals("") || detil == null){
            JOptionPane.showMessageDialog(this, "Kolom Detile Komponen tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }
        
        k_komponen = new Komponen(k_outke, kode, detil);
    }
    public void getDataUpdate(){
        String kode = txt_kodekomponen.getText();
        
        String detil = txt_detilekomponen.getText();
        
        if(kode.trim().equals("") || kode == null){
            JOptionPane.showMessageDialog(this, "Kolom Kode tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }else if(detil.trim().equals("") || detil == null){
            JOptionPane.showMessageDialog(this, "Kolom Detile Komponen tidak boleh kosong", "Peringatan", JOptionPane.ERROR_MESSAGE);
        }
        
        k_komponen.setKomponenkode(kode);
        k_komponen.setKomponenname(detil);
                
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_outke = new javax.swing.JTextField();
        txt_kodekomponen = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_detilekomponen = new javax.swing.JTextArea();
        txt_nourut = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_kodeprogram = new javax.swing.JTextField();
        txt_thnanggaran = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_program = new javax.swing.JTextField();
        txt_kodekegiatan = new javax.swing.JTextField();
        txt_kegiatan = new javax.swing.JTextField();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Isian Komponen");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Output Program :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Kode Komponen :");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Detile Komponen :");

        txt_outke.setEnabled(false);

        txt_detilekomponen.setColumns(20);
        txt_detilekomponen.setRows(5);
        jScrollPane1.setViewportView(txt_detilekomponen);

        txt_nourut.setEditable(false);
        txt_nourut.setPreferredSize(new java.awt.Dimension(6, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_kodekomponen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(txt_nourut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_outke, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_outke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nourut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_kodekomponen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 139, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_simpan.setText("Simpan");
        btn_simpan.setPreferredSize(new java.awt.Dimension(70, 23));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel2.add(btn_simpan);

        btn_batal.setText("Batal");
        btn_batal.setPreferredSize(new java.awt.Dimension(70, 23));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel2.add(btn_batal);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Program : ");

        txt_kodeprogram.setEditable(false);

        txt_thnanggaran.setEditable(false);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Kegiatan :");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Tahun Anggaran :");

        txt_program.setEditable(false);

        txt_kodekegiatan.setEditable(false);

        txt_kegiatan.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_kodeprogram, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(txt_thnanggaran))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_program, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_kodekegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kegiatan)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_thnanggaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_kodeprogram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_program, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_kodekegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_kegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(127, 127, 127))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        
        
        if(add){
            getData();
            try {
                BusinessLogic logic = new BusinessLogic(k_mainForm.getK_conn());
                logic.createKomponen(k_komponen, sessionId, IDBCConstant.MODUL_CONFIGURATION);
                JOptionPane.showMessageDialog(this, "Berhasil Menambahkan Komponen kedalam Database... ", "Sukses", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal Dalam Menambahkan Komponen kedalam database. dengan alasan :\n"+e.toString());
            }
            dispose();
        }else{
            getDataUpdate();
            try {
                BusinessLogic logic = new BusinessLogic(k_mainForm.getK_conn());
                logic.updateKomponen(k_komponen.getKomponenindex(), k_komponen, sessionId, IDBCConstant.MODUL_CONFIGURATION);
                JOptionPane.showMessageDialog(this, "Berhasil Memutakhirkan Komponen kedalam Database... ", "Sukses", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal Dalam Memutakhirkan Komponen kedalam database. dengan alasan :\n"+e.toString());
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txt_detilekomponen;
    private javax.swing.JTextField txt_kegiatan;
    private javax.swing.JTextField txt_kodekegiatan;
    private javax.swing.JTextField txt_kodekomponen;
    private javax.swing.JTextField txt_kodeprogram;
    private javax.swing.JTextField txt_nourut;
    private javax.swing.JTextField txt_outke;
    private javax.swing.JTextField txt_program;
    private javax.swing.JTextField txt_thnanggaran;
    // End of variables declaration//GEN-END:variables
}

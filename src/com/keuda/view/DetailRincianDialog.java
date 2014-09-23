package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.DetailRincian;
import com.keuda.model.Rincian;
import com.keuda.model.SubKomponen;
import com.keuda.services.IDBCConstant;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author adam
 */
public class DetailRincianDialog extends javax.swing.JDialog {
    private Rincian k_rincian;
    private DetailRincian k_detail, parent;
    private MainForm k_mainform;
    
    ComboGroupModel model;
    ComboJenisVolumeModel modeljenis;
    ComboLokasiModel lokasimodel;
    ComboKppnModel kppnmodel;
    ComboCaperModel capermodel;
    
    boolean grup = false;
    boolean update = false;
    
    public DetailRincianDialog(MainForm mainForm, Rincian rincian, boolean modal) {
        super(mainForm,"Form Isian Belanja", modal);
        
        k_rincian = rincian;
        k_mainform = mainForm;
        
        initComponents();
        
        model = new ComboGroupModel();
        modeljenis = new ComboJenisVolumeModel();
        lokasimodel = new ComboLokasiModel();
        kppnmodel = new ComboKppnModel();
        capermodel = new ComboCaperModel();
        
        
        cboGrup.setModel(model);
        cbojenisvolume.setModel(modeljenis);
        cbolokasi.setModel(lokasimodel);
        cbokppn.setModel(kppnmodel);
        cbocaper.setModel(capermodel);
        
        initData(k_rincian.getK_subkomponen());
        setLocationRelativeTo(null);
    }
    
    public DetailRincianDialog(MainForm mainForm, Rincian rincian, DetailRincian parent, boolean modal) {
        super(mainForm,"Form Isian Belanja", modal);
        
        k_rincian = rincian;
        k_mainform = mainForm;
        initComponents();
        
        model = new ComboGroupModel();
        modeljenis = new ComboJenisVolumeModel();
        lokasimodel = new ComboLokasiModel();
        kppnmodel = new ComboKppnModel();
        capermodel = new ComboCaperModel();
        this.parent = parent;
        
        cboGrup.setModel(model);
        cbojenisvolume.setModel(modeljenis);
        cbolokasi.setModel(lokasimodel);
        cbokppn.setModel(kppnmodel);
        cbocaper.setModel(capermodel);
        
        initData(k_rincian.getK_subkomponen());
        setLocationRelativeTo(null);
    }
    
    public DetailRincianDialog(MainForm mainForm, Rincian rincian, DetailRincian parent, boolean update, boolean modal) {
        super(mainForm,"Form Isian Belanja", modal);
        
        k_rincian = rincian;
        k_mainform = mainForm;
        
        this.update = true;
        System.out.println(update);
        this.k_detail = parent;
        
        initComponents();
        
        model = new ComboGroupModel();
        modeljenis = new ComboJenisVolumeModel();
        lokasimodel = new ComboLokasiModel();
        kppnmodel = new ComboKppnModel();
        capermodel = new ComboCaperModel();
        this.parent = parent;
        
        cboGrup.setModel(model);
        cbojenisvolume.setModel(modeljenis);
        cbolokasi.setModel(lokasimodel);
        cbokppn.setModel(kppnmodel);
        cbocaper.setModel(capermodel);
        
        
        setdata(k_rincian, k_detail);
        setLocationRelativeTo(null);
    }
    
    void initData(SubKomponen sub){
        if(sub != null){
            txtkomponen.setText(sub.getSubkomponenname());
        }
    }
    
    void setdata(Rincian rincian, DetailRincian detailRincian){
        txtkomponen.setText(rincian.getK_subkomponen().getSubkomponenname());
        txtdetailrincian.setText(detailRincian.getDetailrincian());
        txtvolume.setText(""+detailRincian.getVolume());
        String jumlah1 = StringUtils.removeEnd(String.valueOf(detailRincian.getJumlahdana()).trim(), ",00");
        String jumlah = StringUtils.remove(jumlah1, ',');
        String jumlah2 = StringUtils.removeEnd(jumlah, ".0");
        txtjumlah.setText(jumlah2);
        if(detailRincian.getJumlahdana() != 0.0){            
            cboGrup.setSelectedIndex(2);
        }else{
            cboGrup.setSelectedIndex(1);
        }
        cbocaper.setSelectedItem(detailRincian.getCarapenarikan());
        cbojenisvolume.setSelectedItem(detailRincian.getJenisvolume());
        cbolokasi.setSelectedItem(detailRincian.getLokasi());
        cbokppn.setSelectedItem(detailRincian.getKppn());
    }
    
    public class ComboJenisVolumeModel extends DefaultComboBoxModel{

        public ComboJenisVolumeModel() {
            addElement("");
            addElement("OB");
            addElement("OJ");
            addElement("OH");
            addElement("OT");
            addElement("OK");
            addElement("BK");
            addElement("BH");
            addElement("KL");
            addElement("LAP");
            addElement("HAL");
            addElement("EXP");
            addElement("PKT");            
        }
        
    }
    
    public void getData(){
        if(grup){
            String detaildata = txtdetailrincian.getText();
//            String lokasi = cbolokasi.getSelectedItem().toString();
//            String kppn = cbokppn.getSelectedItem().toString();
//            String cara = cbocaper.getSelectedItem().toString();
            
            if(detaildata.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Detail Data Tidak Boleh Kosong", "Perhatian", JOptionPane.ERROR_MESSAGE);
//            }else if(lokasi.trim().equals("")){
//                JOptionPane.showMessageDialog(this, "Silahkan Pilih Lokasi", "Perhatian", JOptionPane.ERROR_MESSAGE);
//            }else if(kppn.trim().equals("")){
//                JOptionPane.showMessageDialog(this, "Silahkan Pilih KPPN", "Perhatian", JOptionPane.ERROR_MESSAGE);
//            }else if(cara.trim().equals("")){
//                JOptionPane.showMessageDialog(this, "Silahkan Pilih Cara Penarikan", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else{
                k_detail = new DetailRincian(k_rincian, detaildata);
                
            }
        }else{
            String detaildata = txtdetailrincian.getText();
            String lokasi = cbolokasi.getSelectedItem().toString();
            String kppn = cbokppn.getSelectedItem().toString();
            String cara = cbocaper.getSelectedItem().toString();
            
            
            String tipe = cbojenisvolume.getSelectedItem().toString();
            if(detaildata.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Detail Data Tidak Boleh Kosong", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else if(lokasi.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Lokasi", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else if(kppn.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Silahkan Pilih KPPN", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else if(cara.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Cara Penarikan", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else if(txtvolume.getText().trim().equals("")){
                JOptionPane.showMessageDialog(this, "Volume Tidak Boleh Kosong", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else if(tipe.trim().equals("")){
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Tipe Volume", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else if(txtjumlah.getText().trim().equals("")){
                JOptionPane.showMessageDialog(this, "Jumlah Tidak Boleh Kosong", "Perhatian", JOptionPane.ERROR_MESSAGE);
            }else{
                int volume = Integer.valueOf(txtvolume.getText());
                double jumlah = Double.valueOf(txtjumlah.getText());
                
                if(update){
                    k_detail.setRincian(k_rincian);
                    k_detail.setDetailrincian(detaildata);
                    k_detail.setVolume(volume);
                    k_detail.setJenisvolume(tipe);
                    k_detail.setJumlahdana(jumlah);
                }else{
                    k_detail = new DetailRincian(k_rincian, detaildata, lokasi, kppn, cara, volume, tipe, jumlah);
                    if(parent != null){
                        k_detail.setParent(parent);
                    }
                }
            }
        }
    }
    
    public class ComboGroupModel extends DefaultComboBoxModel{

        public ComboGroupModel() {
            addElement("");
            addElement("Grup");
            addElement("Bukan Grup");
        }
        
    }
    
    public class ComboLokasiModel extends DefaultComboBoxModel{

        public ComboLokasiModel() {
            addElement("");
            addElement("01");
        }
        
    }
    
    public class ComboKppnModel extends DefaultComboBoxModel{

        public ComboKppnModel() {
            addElement("");
            addElement("133");
        }        
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    public class ComboCaperModel extends DefaultComboBoxModel{

        public ComboCaperModel() {
            addElement("");
            addElement("0");
        }
        
    }
    //</editor-fold>
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtkomponen = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdetailrincian = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtvolume = new javax.swing.JTextField();
        cbojenisvolume = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboGrup = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbolokasi = new javax.swing.JComboBox();
        cbokppn = new javax.swing.JComboBox();
        cbocaper = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        btnsimpan = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Form Isian Belanja");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Komponen/Sub Komponen :");

        txtkomponen.setEditable(false);
        txtkomponen.setColumns(20);
        txtkomponen.setLineWrap(true);
        txtkomponen.setRows(5);
        txtkomponen.setTabSize(2);
        txtkomponen.setToolTipText("");
        txtkomponen.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtkomponen);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Detail Rincian :");

        txtdetailrincian.setEditable(false);
        txtdetailrincian.setColumns(20);
        txtdetailrincian.setLineWrap(true);
        txtdetailrincian.setRows(5);
        txtdetailrincian.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtdetailrincian);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Volume :");

        txtvolume.setEditable(false);

        cbojenisvolume.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbojenisvolume.setEnabled(false);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Satuan Biaya (Rp.) :");

        txtjumlah.setEditable(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Grup :");

        cboGrup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cboGrup.setPreferredSize(new java.awt.Dimension(200, 22));
        cboGrup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGrupActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Lokasi :");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("KPPN :");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Cara Penarikan :");

        cbolokasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbolokasi.setEnabled(false);

        cbokppn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbokppn.setEnabled(false);

        cbocaper.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbocaper.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtvolume, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbojenisvolume, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboGrup, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbocaper, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbokppn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbolokasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboGrup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbolokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbokppn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbocaper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtvolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbojenisvolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/save.gif"))); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.setPreferredSize(new java.awt.Dimension(110, 25));
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });
        jPanel2.add(btnsimpan);

        btnbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/cancel.gif"))); // NOI18N
        btnbatal.setText("Batal");
        btnbatal.setPreferredSize(new java.awt.Dimension(110, 25));
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });
        jPanel2.add(btnbatal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboGrupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGrupActionPerformed
        if(!cboGrup.getSelectedItem().toString().trim().equals("")){
            if(cboGrup.getSelectedItem().toString().equals("Grup")){
                txtvolume.setEditable(false);
                cbojenisvolume.setEnabled(false);
                txtjumlah.setEditable(false);
                txtdetailrincian.setEditable(true);
                cbolokasi.setEnabled(false);
                cbokppn.setEnabled(false);
                cbocaper.setEnabled(false);
                grup = true;
            }else if(cboGrup.getSelectedItem().toString().equals("Bukan Grup")){
                
                txtvolume.setEditable(true);
                cbojenisvolume.setEnabled(true);
                txtjumlah.setEditable(true);
                txtdetailrincian.setEditable(true);
                cbolokasi.setEnabled(true);
                cbokppn.setEnabled(true);
                cbocaper.setEnabled(true);
                grup = false;
            }
        }else{
            txtvolume.setEditable(false);
            cbojenisvolume.setEnabled(false);
            txtjumlah.setEditable(false);
            txtdetailrincian.setEditable(false);
            cbolokasi.setEnabled(false);
            cbokppn.setEnabled(false);
            cbocaper.setEnabled(false);
        }
    }//GEN-LAST:event_cboGrupActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        BusinessLogic logic = new BusinessLogic(k_mainform.getK_conn());
        
        try {
            if(update){
                getData();
                System.out.println(update);
                logic.updateDetailRincian(k_detail.getDetailrincianindex(), k_detail, k_mainform.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                
            }else{
                getData();
                System.out.println(update);
                logic.createDetailRincian(k_detail, k_mainform.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            }
        } catch (KeudaException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Gagal", JOptionPane.ERROR_MESSAGE);
        }
        
        dispose();
        
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        dispose();
    }//GEN-LAST:event_btnbatalActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JComboBox cboGrup;
    private javax.swing.JComboBox cbocaper;
    private javax.swing.JComboBox cbojenisvolume;
    private javax.swing.JComboBox cbokppn;
    private javax.swing.JComboBox cbolokasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtdetailrincian;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextArea txtkomponen;
    private javax.swing.JTextField txtvolume;
    // End of variables declaration//GEN-END:variables
}

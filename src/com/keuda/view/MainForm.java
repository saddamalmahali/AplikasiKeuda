                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;
import com.keuda.exception.KeudaException;
import com.keuda.util.KoneksiAplikasi;
import de.muntjak.tinylookandfeel.Theme;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author user
 */

public class MainForm extends javax.swing.JFrame {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public Connection k_conn = null;
    public long k_sessionId = -1;
    AkunTreePanel akunPanel;
    KoneksiAplikasi koneksi_master;
    ProgramPanel programPanel;
    KegiatanPanel kegiatanPanel;
    ProgramDipaPanel programDipaPanel;
    IndikatorKegiatanDipaPanel indikatorKegiatanDipaPanel;
    DipaComplete dipaCompletePanel;
    protected OutcomeProgramPanel outcomeProgramPanel;
    protected final int x = getWidth();
    protected final int y = getHeight();
    protected final Toolkit toolkit = Toolkit.getDefaultToolkit();
    protected final int width = toolkit.getScreenSize().width;
    protected final int height = toolkit.getScreenSize().height;
    IkuProgramPanel ikuPanel;
    KegiatanDipaPanel kegiatanDipaPanel;
    OutputKegiatanPanel outputKegiatanPanel;
    OutputKegiatanPanelList outputKegiatanPanelListBeta;
    IndikatorKegiatanDipaListPanel indikatorKegiatanPanelListBeta;
    /**
     * Creates new form MainForm
     */
    public MainForm() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        
        initComponents();
        koneksi_master = new KoneksiAplikasi();
        k_conn = koneksi_master.getConnection();
        
//        try {
//            Class.forName("org.postgresql.Driver");
//            k_conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplikasi_keuda", "postgres", "Bangga");
//        } catch (ClassNotFoundException | SQLException ex) {
//            System.err.println("Gagal...");
//        }
        
        setLocationRelativeTo(null);
        setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 8));
        
        setIconImage(new ImageIcon(getClass().getResource("/com/keuda/images/icon_frame.png")).getImage());
    }

    public Connection getK_conn() {
        return k_conn;
    }

    public void setK_conn(Connection k_conn) {
        this.k_conn = k_conn;
    }

    public long getK_sessionId() {
        return k_sessionId;
    }

    public void setK_sessionId(long k_sessionId) {
        this.k_sessionId = k_sessionId;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_grupPengguna = new javax.swing.JMenuItem();
        mi_pengguna = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mi_logout = new javax.swing.JMenuItem();
        mi_exit = new javax.swing.JMenuItem();
        mnuDataMaster = new javax.swing.JMenu();
        mi_master_akun = new javax.swing.JMenuItem();
        mnuProgramKegiatan = new javax.swing.JMenu();
        mni_Program = new javax.swing.JMenuItem();
        mni_kegiatan = new javax.swing.JMenuItem();
        mnu_dipa = new javax.swing.JMenu();
        mnuProgramDipa = new javax.swing.JMenuItem();
        mni_outcome = new javax.swing.JMenuItem();
        mni_IkuProgram = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mni_kegiatanDipa = new javax.swing.JMenuItem();
        mni_indikatorKegiatan = new javax.swing.JMenuItem();
        mniIndikatorKegiatanBeta = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mniOutputKegiatan = new javax.swing.JMenuItem();
        mni_outke_beta = new javax.swing.JMenuItem();
        mniDIPA = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Keuangan Daerah");

        jDesktopPane1.setBackground(new java.awt.Color(241, 241, 241));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jDesktopPane1.add(jPanel1);
        jPanel1.setBounds(180, 0, 100, 100);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mnu_aplikasi.png"))); // NOI18N
        jMenu1.setToolTipText("aplikasi");

        mi_grupPengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_ugroup.png"))); // NOI18N
        mi_grupPengguna.setText("Grup Pengguna");
        mi_grupPengguna.setToolTipText("grup pengguna");
        jMenu1.add(mi_grupPengguna);

        mi_pengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_account.png"))); // NOI18N
        mi_pengguna.setText("Pengguna");
        mi_pengguna.setToolTipText("pengguna");
        jMenu1.add(mi_pengguna);
        jMenu1.add(jSeparator1);

        mi_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_logout.png"))); // NOI18N
        mi_logout.setText("Logout");
        mi_logout.setToolTipText("logout");
        jMenu1.add(mi_logout);

        mi_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_close.png"))); // NOI18N
        mi_exit.setText("Exit");
        mi_exit.setToolTipText("Exit");
        mi_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_exitActionPerformed(evt);
            }
        });
        jMenu1.add(mi_exit);

        jMenuBar1.add(jMenu1);

        mnuDataMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mnu_data_master.png"))); // NOI18N
        mnuDataMaster.setToolTipText("data master");

        mi_master_akun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/akun_image.png"))); // NOI18N
        mi_master_akun.setText("Master Akun");
        mi_master_akun.setToolTipText("Master Akun");
        mi_master_akun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mi_master_akunMouseClicked(evt);
            }
        });
        mi_master_akun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_master_akunActionPerformed(evt);
            }
        });
        mnuDataMaster.add(mi_master_akun);

        mnuProgramKegiatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/program_kegiatan_18.png"))); // NOI18N
        mnuProgramKegiatan.setText("Program & Kegiatan");

        mni_Program.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/program_18.png"))); // NOI18N
        mni_Program.setText("Program");
        mni_Program.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_ProgramActionPerformed(evt);
            }
        });
        mnuProgramKegiatan.add(mni_Program);

        mni_kegiatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/activity_18.png"))); // NOI18N
        mni_kegiatan.setText("Kegiatan");
        mni_kegiatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_kegiatanActionPerformed(evt);
            }
        });
        mnuProgramKegiatan.add(mni_kegiatan);

        mnuDataMaster.add(mnuProgramKegiatan);

        jMenuBar1.add(mnuDataMaster);

        mnu_dipa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mnu_dipa_32x32.png"))); // NOI18N

        mnuProgramDipa.setText("Program DIPA");
        mnuProgramDipa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProgramDipaActionPerformed(evt);
            }
        });
        mnu_dipa.add(mnuProgramDipa);

        mni_outcome.setText("Outcome Program");
        mni_outcome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_outcomeActionPerformed(evt);
            }
        });
        mnu_dipa.add(mni_outcome);

        mni_IkuProgram.setText("IKU Program");
        mni_IkuProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_IkuProgramActionPerformed(evt);
            }
        });
        mnu_dipa.add(mni_IkuProgram);
        mnu_dipa.add(jSeparator2);

        mni_kegiatanDipa.setText("Kegiatan DIPA");
        mni_kegiatanDipa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_kegiatanDipaActionPerformed(evt);
            }
        });
        mnu_dipa.add(mni_kegiatanDipa);

        mni_indikatorKegiatan.setText("Indikator Kegiatan");
        mni_indikatorKegiatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_indikatorKegiatanActionPerformed(evt);
            }
        });
        mnu_dipa.add(mni_indikatorKegiatan);

        mniIndikatorKegiatanBeta.setText("Indikator Kegiatan (Beta)");
        mniIndikatorKegiatanBeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniIndikatorKegiatanBetaActionPerformed(evt);
            }
        });
        mnu_dipa.add(mniIndikatorKegiatanBeta);
        mnu_dipa.add(jSeparator3);

        mniOutputKegiatan.setText("Output Kegiatan");
        mniOutputKegiatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniOutputKegiatanActionPerformed(evt);
            }
        });
        mnu_dipa.add(mniOutputKegiatan);

        mni_outke_beta.setText("Output Kegiatan (Beta)");
        mni_outke_beta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_outke_betaActionPerformed(evt);
            }
        });
        mnu_dipa.add(mni_outke_beta);

        mniDIPA.setText("DIPA");
        mniDIPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDIPAActionPerformed(evt);
            }
        });
        mnu_dipa.add(mniDIPA);

        jMenuBar1.add(mnu_dipa);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mi_master_akunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_master_akunActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
        akunPanel = new AkunTreePanel(this);
        
        kif.setContentPane(akunPanel);
        akunPanel.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/master_account_small.png")));
        kif.setResizable(false);
        kif.setMaximizable(false);
        kif.setTitle("Master Akun");
        kif.setVisible(true);
        
    }//GEN-LAST:event_mi_master_akunActionPerformed

    private void mi_master_akunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mi_master_akunMouseClicked
                
    }//GEN-LAST:event_mi_master_akunMouseClicked

    private void mi_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mi_exitActionPerformed

    private void mni_ProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_ProgramActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {
            programPanel = new ProgramPanel(this);
            
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            kif.setContentPane(programPanel);
            programPanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(false);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png")));
            kif.setTitle("Master Program");
            kif.setSize(500, 400);
            kif.setVisible(true);
        
    }//GEN-LAST:event_mni_ProgramActionPerformed

    private void mni_kegiatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_kegiatanActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {
            kegiatanPanel = new KegiatanPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
            kif.setContentPane(kegiatanPanel);
            kegiatanPanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(false);
            kif.setSize(800, 300);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/activity_18.png")));
            kif.setTitle("Master Kegiatan");
            kif.setVisible(true);
    }//GEN-LAST:event_mni_kegiatanActionPerformed

    private void mni_outcomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_outcomeActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {
            outcomeProgramPanel = new OutcomeProgramPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kif.setContentPane(outcomeProgramPanel);
        kif.setTitle("Outcome Program");
        outcomeProgramPanel.setVisible(true);
        jDesktopPane1.add(kif);
        try {
            kif.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        kif.setVisible(true);
        
        
    }//GEN-LAST:event_mni_outcomeActionPerformed

    private void mnuProgramDipaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProgramDipaActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {
            programDipaPanel = new ProgramDipaPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kif.setContentPane(programDipaPanel);
        programDipaPanel.setVisible(true);
        kif.setTitle("Program DIPA");
        kif.setSize(600, 400);
        kif.setResizable(false);
        
        kif.setMaximizable(false);
        
        kif.setVisible(true);
        jDesktopPane1.add(kif);
    }//GEN-LAST:event_mnuProgramDipaActionPerformed

    private void mni_IkuProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_IkuProgramActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {
            ikuPanel = new IkuProgramPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        kif.setContentPane(ikuPanel);
        kif.setTitle("IKU Program");
        ikuPanel.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setResizable(false);
        kif.setMaximizable(false);
        try {
            kif.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        kif.setVisible(true);
        
    }//GEN-LAST:event_mni_IkuProgramActionPerformed

    private void mni_indikatorKegiatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_indikatorKegiatanActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
        
        try {
            indikatorKegiatanDipaPanel = new IndikatorKegiatanDipaPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kif.setContentPane(indikatorKegiatanDipaPanel);
        kif.setTitle("Indikator Kegiatan");
        indikatorKegiatanDipaPanel.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setResizable(false);
        kif.setMaximizable(false);
        try {
            kif.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        kif.setVisible(true);
        
    }//GEN-LAST:event_mni_indikatorKegiatanActionPerformed

    private void mni_kegiatanDipaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_kegiatanDipaActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {        
            kegiatanDipaPanel= new KegiatanDipaPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kif.setContentPane(kegiatanDipaPanel);
        kif.setTitle("Kegiatan DIPA");
        kegiatanDipaPanel.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setResizable(false);
        kif.setMaximizable(false);
        try {
            kif.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        kif.setVisible(true);
        
    }//GEN-LAST:event_mni_kegiatanDipaActionPerformed

    private void mniOutputKegiatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniOutputKegiatanActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
        try {        
            outputKegiatanPanel = new OutputKegiatanPanel(this);
        } catch (KeudaException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kif.setContentPane(outputKegiatanPanel);
        kif.setTitle("Kegiatan DIPA");
        outputKegiatanPanel.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setResizable(false);
        kif.setMaximizable(false);
        try {
            kif.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        kif.setVisible(true);
        
    }//GEN-LAST:event_mniOutputKegiatanActionPerformed

    private void mni_outke_betaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_outke_betaActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
//        try {        
            outputKegiatanPanelListBeta = new OutputKegiatanPanelList(this);
//        } catch (KeudaException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        kif.setContentPane(outputKegiatanPanelListBeta);
        kif.setTitle("Output Kegiatan DIPA");
        outputKegiatanPanelListBeta.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setSize(600, 450);
        kif.setResizable(false);
        kif.setMaximizable(false);
//        try {
//            kif.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        kif.setVisible(true);
        
    }//GEN-LAST:event_mni_outke_betaActionPerformed

    private void mniIndikatorKegiatanBetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniIndikatorKegiatanBetaActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
//        try {        
            indikatorKegiatanPanelListBeta = new IndikatorKegiatanDipaListPanel(this);
//        } catch (KeudaException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        kif.setContentPane(indikatorKegiatanPanelListBeta);
        kif.setTitle("Indikator Kegiatan DIPA");
        indikatorKegiatanPanelListBeta.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setSize(650, 450);
        kif.setResizable(false);
        kif.setMaximizable(false);
        
//        try {
//            kif.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        kif.setVisible(true);
    }//GEN-LAST:event_mniIndikatorKegiatanBetaActionPerformed

    private void mniDIPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDIPAActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
//        try {        
            dipaCompletePanel = new DipaComplete(this);
//        } catch (KeudaException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        kif.setContentPane(dipaCompletePanel);
        kif.setTitle("Indikator Kegiatan DIPA");
        dipaCompletePanel.setVisible(true);
        jDesktopPane1.add(kif);
        kif.setSize(650, 450);
//        kif.setResizable(false);
//        kif.setMaximizable(false);
        
//        try {
//            kif.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        kif.setVisible(true);
    }//GEN-LAST:event_mniDIPAActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {            
            
            public void run() {
                try {
                    Toolkit.getDefaultToolkit().setDynamicLayout(true);
                    System.setProperty("sun.awt.noerasebackground", "true");
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(false);
                    try {
//                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                        
                        
//                    Theme.loadTheme(new File(System.getProperty("user.dir"), "theme/Default.theme").toURI().toURL());
//                    UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                     
                    } catch (Exception e) {
                    System.out.println("Unsuported Look And feel: " + e.getMessage());
                    }
//                    Theme.loadTheme(new File(System.getProperty("user.dir"), "theme/Default.theme").toURI().toURL());
//                    UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                
                try {
                    new MainForm().setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem mi_exit;
    private javax.swing.JMenuItem mi_grupPengguna;
    private javax.swing.JMenuItem mi_logout;
    private javax.swing.JMenuItem mi_master_akun;
    private javax.swing.JMenuItem mi_pengguna;
    private javax.swing.JMenuItem mniDIPA;
    private javax.swing.JMenuItem mniIndikatorKegiatanBeta;
    private javax.swing.JMenuItem mniOutputKegiatan;
    private javax.swing.JMenuItem mni_IkuProgram;
    private javax.swing.JMenuItem mni_Program;
    private javax.swing.JMenuItem mni_indikatorKegiatan;
    private javax.swing.JMenuItem mni_kegiatan;
    private javax.swing.JMenuItem mni_kegiatanDipa;
    private javax.swing.JMenuItem mni_outcome;
    private javax.swing.JMenuItem mni_outke_beta;
    private javax.swing.JMenu mnuDataMaster;
    private javax.swing.JMenuItem mnuProgramDipa;
    private javax.swing.JMenu mnuProgramKegiatan;
    private javax.swing.JMenu mnu_dipa;
    // End of variables declaration//GEN-END:variables
}

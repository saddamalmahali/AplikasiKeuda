
package com.keuda.view;


import com.keuda.exception.KeudaException;
import com.keuda.util.KoneksiAplikasi;
import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.JOutlookBar;
import de.muntjak.tinylookandfeel.Theme;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingManager;
import org.flexdock.util.SwingUtility;
import org.flexdock.view.View;
import org.flexdock.view.Viewport;

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
    RincianDipaPanel dipaCompletePanel;
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
    RincianAkunPanel rincianAkunPanel;
    MasterVolumePanel masterVolumePanel;
    private KomponenPanel komponenPanel;
    RincianKertasKerjaSatker rkkjpanel;
    SPPPanel spppanel;
    BankPanel bankpanel;
    
    Font font = null;
    
    /**
     * Creates new form MainForm
     */
    
    public MainForm() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        
        initComponents();
        createMenus();
        
        koneksi_master = new KoneksiAplikasi();
        k_conn = koneksi_master.getConnection();
        
//        try {
//            Class.forName("org.postgresql.Driver");
//            k_conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/aplikasi_keuda", "postgres", "Bangga");
//        } catch (ClassNotFoundException | SQLException ex) {
//            System.err.println("Gagal...");
//        }
        
        font = new Font("Arial", Font.BOLD, 14);
        setLocationRelativeTo(null);
        setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 8));
        
        setIconImage(new ImageIcon(getClass().getResource("/com/keuda/images/icon_frame.png")).getImage());
        mni_indikatorKegiatan.setEnabled(false);
        mniOutputKegiatan.setEnabled(false);
        mi_pengguna.setEnabled(false);
        mi_grupPengguna.setEnabled(false);
        mi_logout.setEnabled(false);
        
    }
    
    public void createMenus(){
        
        SwingUtility.setPlaf("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        DockingManager.setFloatingEnabled(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        Viewport viewPort = new Viewport();
        panel.add(viewPort, BorderLayout.CENTER);
        
        View desktopView = new View("desktop", "");
        desktopView.setTerritoryBlocked(DockingConstants.CENTER_REGION, true);
        desktopView.setTitlebar(null);
        JPanel pDesktop = new JPanel(new BorderLayout());
        pDesktop.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        viewPort.dock(desktopView);
        View vLeft = new View("navigator", "Menu");
        
        vLeft.addAction(DockingConstants.PIN_ACTION);
        desktopView.dock(vLeft, DockingConstants.WEST_REGION, .1f);
        
//        try{
//            SwingUtility.setPlaf("de.muntjak.tinylookandfeel.TinyLookAndFeel");
//        }catch(Exception ex){
//           
//        }
        
//        jDesktopPane1 = new JDesktopPane(){
//            ImageIcon icon = new ImageIcon(getClass().getResource("/com/keuda/images/background-form.jpg"));
//            Image image = icon.getImage();
//            Image newImage = image.getScaledInstance(1500, 1000, Image.SCALE_SMOOTH);
//                
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                g.drawImage(newImage, 0, 0, getSize().width, getSize().height, this);
//            }
//           
//        };
        
        vLeft.setContentPane(createMenuBar());
        jDesktopPane1.setBorder(new BevelBorder(BevelBorder.LOWERED));
        desktopView.setContentPane(jDesktopPane1);
        
        setContentPane(panel);
        
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_grupPengguna = new javax.swing.JMenuItem();
        mi_pengguna = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mi_logout = new javax.swing.JMenuItem();
        mi_exit = new javax.swing.JMenuItem();
        mniDataMaster = new javax.swing.JMenu();
        mi_master_akun = new javax.swing.JMenuItem();
        mni_satuanvolume = new javax.swing.JMenuItem();
        mni_bank = new javax.swing.JMenuItem();
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
        mniKomponen = new javax.swing.JMenuItem();
        mniDIPA = new javax.swing.JMenuItem();
        mni_rkkj = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Keuangan Daerah");

        jDesktopPane1.setBackground(new java.awt.Color(241, 241, 241));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mnu_aplikasi.png"))); // NOI18N
        jMenu1.setToolTipText("aplikasi");

        mi_grupPengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_ugroup.png"))); // NOI18N
        mi_grupPengguna.setText("Grup Pengguna");
        mi_grupPengguna.setToolTipText("grup pengguna");
        mi_grupPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_grupPenggunaActionPerformed(evt);
            }
        });
        jMenu1.add(mi_grupPengguna);

        mi_pengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_account.png"))); // NOI18N
        mi_pengguna.setText("Pengguna");
        mi_pengguna.setToolTipText("pengguna");
        mi_pengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_penggunaActionPerformed(evt);
            }
        });
        jMenu1.add(mi_pengguna);
        jMenu1.add(jSeparator1);

        mi_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mni_logout.png"))); // NOI18N
        mi_logout.setText("Logout");
        mi_logout.setToolTipText("logout");
        mi_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_logoutActionPerformed(evt);
            }
        });
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

        mniDataMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mnu_data_master.png"))); // NOI18N
        mniDataMaster.setToolTipText("data master");
        mniDataMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDataMasterActionPerformed(evt);
            }
        });

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
        mniDataMaster.add(mi_master_akun);

        mni_satuanvolume.setText("Satuan Volume");
        mni_satuanvolume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_satuanvolumeActionPerformed(evt);
            }
        });
        mniDataMaster.add(mni_satuanvolume);

        mni_bank.setText("Bank");
        mni_bank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_bankActionPerformed(evt);
            }
        });
        mniDataMaster.add(mni_bank);

        mnuProgramKegiatan.setText("Program & Kegiatan");

        mni_Program.setText("Program");
        mni_Program.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_ProgramActionPerformed(evt);
            }
        });
        mnuProgramKegiatan.add(mni_Program);

        mni_kegiatan.setText("Kegiatan");
        mni_kegiatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_kegiatanActionPerformed(evt);
            }
        });
        mnuProgramKegiatan.add(mni_kegiatan);

        mniDataMaster.add(mnuProgramKegiatan);

        jMenuBar1.add(mniDataMaster);

        mnu_dipa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/keuda/images/mnu_dipa_32x32.png"))); // NOI18N
        mnu_dipa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_dipaActionPerformed(evt);
            }
        });

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

        mniKomponen.setText("Komponen Kegiatan");
        mniKomponen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKomponenActionPerformed(evt);
            }
        });
        mnu_dipa.add(mniKomponen);

        mniDIPA.setText("Rincian Anggaran Keuangan");
        mniDIPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDIPAActionPerformed(evt);
            }
        });
        mnu_dipa.add(mniDIPA);

        mni_rkkj.setText("Tayang Rincian Satker");
        mni_rkkj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_rkkjActionPerformed(evt);
            }
        });
        mnu_dipa.add(mni_rkkj);

        jMenuBar1.add(mnu_dipa);

        jMenu2.setText("SPP");

        jMenuItem1.setText("RUH SPP");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Cetak SPP");
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

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
        kif.setSize(600, 700);
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
            dipaCompletePanel = new RincianDipaPanel(this);
//        } catch (KeudaException ex) {
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        kif.setContentPane(dipaCompletePanel);
        kif.setTitle("Form Rincian Akun");
        dipaCompletePanel.setVisible(true);
        jDesktopPane1.add(kif);
//        kif.setSize(700, 500);
        kif.setResizable(false);
        kif.setMaximizable(false);
        try {
            kif.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        kif.setVisible(true);
        
    }//GEN-LAST:event_mniDIPAActionPerformed

    private void mni_satuanvolumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_satuanvolumeActionPerformed
        
        KeudaInternalFrame kif = new KeudaInternalFrame();
        
            masterVolumePanel = new MasterVolumePanel(this);
                 
        
            kif.setContentPane(masterVolumePanel);
            masterVolumePanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(false);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png")));
            kif.setTitle("Satuan Volume");
            kif.setSize(500, 400);
            kif.setVisible(true);
    }//GEN-LAST:event_mni_satuanvolumeActionPerformed

    private void mniKomponenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKomponenActionPerformed
            
        KeudaInternalFrame kif = new KeudaInternalFrame();
        
            komponenPanel = new KomponenPanel(this);
                 
        
            kif.setContentPane(komponenPanel);
            komponenPanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(true);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png")));
            kif.setTitle("Komponen/Sub Komponen");
            kif.setSize(900, 500);
            kif.setVisible(true);
            
    }//GEN-LAST:event_mniKomponenActionPerformed

    private void mi_grupPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_grupPenggunaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mi_grupPenggunaActionPerformed

    private void mi_penggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_penggunaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mi_penggunaActionPerformed

    private void mi_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_logoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mi_logoutActionPerformed

    private void mnu_dipaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_dipaActionPerformed
           
    }//GEN-LAST:event_mnu_dipaActionPerformed

    private void mni_rkkjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_rkkjActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
            rkkjpanel = new RincianKertasKerjaSatker(this);
            kif.setContentPane(rkkjpanel);
            rkkjpanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(true);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png")));
            kif.setTitle("Rincian Kertas Kerja Satker TA. 2014");
            kif.setSize(900, 500);
            kif.setMaximizable(false);
            try {
                kif.setMaximum(true);
            } catch (PropertyVetoException ex){

            }
            kif.setVisible(true);
    }//GEN-LAST:event_mni_rkkjActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
            spppanel = new SPPPanel(this);
            kif.setContentPane(spppanel);
            spppanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(true);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png")));
            kif.setTitle("Rincian Kertas Kerja Satker TA. 2014");
            kif.setSize(800, 500);
            kif.setMaximizable(false);
            
            kif.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mniDataMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDataMasterActionPerformed
        
    }//GEN-LAST:event_mniDataMasterActionPerformed

    private void mni_bankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_bankActionPerformed
        KeudaInternalFrame kif = new KeudaInternalFrame();
            bankpanel = new BankPanel(this);
            kif.setContentPane(bankpanel);
            bankpanel.setVisible(true);
            jDesktopPane1.add(kif);
            kif.setResizable(true);
            kif.setFrameIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png")));
            kif.setTitle("Rincian Kertas Kerja Satker TA. 2014");
            kif.setSize(1114, 500);
//            kif.setMaximizable(false);
            
            kif.setVisible(true);
    }//GEN-LAST:event_mni_bankActionPerformed

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
                    Theme.loadTheme(new File(System.getProperty("user.dir"), "theme/Default.theme").toURI().toURL());
                    UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
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
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem mi_exit;
    private javax.swing.JMenuItem mi_grupPengguna;
    private javax.swing.JMenuItem mi_logout;
    private javax.swing.JMenuItem mi_master_akun;
    private javax.swing.JMenuItem mi_pengguna;
    private javax.swing.JMenuItem mniDIPA;
    private javax.swing.JMenu mniDataMaster;
    private javax.swing.JMenuItem mniIndikatorKegiatanBeta;
    private javax.swing.JMenuItem mniKomponen;
    private javax.swing.JMenuItem mniOutputKegiatan;
    private javax.swing.JMenuItem mni_IkuProgram;
    private javax.swing.JMenuItem mni_Program;
    private javax.swing.JMenuItem mni_bank;
    private javax.swing.JMenuItem mni_indikatorKegiatan;
    private javax.swing.JMenuItem mni_kegiatan;
    private javax.swing.JMenuItem mni_kegiatanDipa;
    private javax.swing.JMenuItem mni_outcome;
    private javax.swing.JMenuItem mni_outke_beta;
    private javax.swing.JMenuItem mni_rkkj;
    private javax.swing.JMenuItem mni_satuanvolume;
    private javax.swing.JMenuItem mnuProgramDipa;
    private javax.swing.JMenu mnuProgramKegiatan;
    private javax.swing.JMenu mnu_dipa;
    // End of variables declaration//GEN-END:variables

    private Container createMenuBar() {
        JOutlookBar bar = new JOutlookBar();
        JButtonBar btn = new JButtonBar();
        
        bar.addTab("Master Data", createMasterData());
        bar.addTab("DIPA", createDIPA());
        
        return bar;
    }

    private Component createMasterData() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton k_btnmasterdata = createButton("Master Data", null, null, "master data");
        k_btnmasterdata.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actionMenuEvt(e);
            }
        });
        
        panel.add(k_btnmasterdata);
        
        return panel;
    }
    
    private Component createDIPA(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton k_btnmasterdata = createButton("DIPA", null, null, "master data");
        k_btnmasterdata.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actionMenuEvt(e);
            }
        });
        
        JButton k_btnIKUProgram = createButton("IKU Program", null, null, "iku program");
        k_btnIKUProgram.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actionMenuEvt(e);
            }
        });
        panel.add(k_btnmasterdata);
        panel.add(k_btnIKUProgram);
        return panel;
    }
    
    private JButton createButton(String text, Icon icon, Icon overicon, String tooltip){
        JButton btn = new JButton(text, icon);
        btn.setRolloverIcon(overicon);
        btn.setPressedIcon(overicon);
        btn.setFont(font);
        btn.setToolTipText(tooltip);
        btn.setOpaque(false);
        btn.setVerticalTextPosition(JButton.BOTTOM);
        btn.setVerticalTextPosition(JButton.CENTER);
        btn.setRolloverEnabled(true);
        
        return btn;
    }
    public void actionMenuEvt(ActionEvent ev){
        
    }
}

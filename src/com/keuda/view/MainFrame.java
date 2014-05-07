/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.util.KoneksiAplikasi;
import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.JOutlookBar;
import de.muntjak.tinylookandfeel.Theme;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 *
 * @author client
 */
public class MainFrame extends KeudaMainFrame implements ActionListener{
    protected Font font;
    
    JMenuItem mi_account, mi_accountGroup, mi_exit, mi_logout;
    
    JMenuItem mi_masterAkun, mi_programKeg, mi_program, mi_kegiatan;
    
    JMenuItem mi_prodi, mi_outProg, mi_iprog, mi_kedip, mi_inkeu, mi_outkeu;
    
    public MainFrame() {
        super("Sistem Informasi Keuangan Daerah");
        setIconImage(new ImageIcon(getClass().getResource("/com/keuda/images/icon_frame.png")).getImage());
        createMenu();
        font = new Font("Arial", Font.BOLD, 14);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        
        setVisible(true);
        
    }
    
    private void createMenu(){
        JMenu menu = new JMenu();
        menu.setIcon(new ImageIcon(getClass().getResource("/com/keuda/images/mnu_aplikasi.png")));
        addMenu(menu);
        
        menu.add(mi_account = createMenuItem("Akun", new ImageIcon(getClass().getResource("/com/keuda/images/mni_account.png"))));
        menu.add(mi_accountGroup = createMenuItem("Akun Grup", new ImageIcon(getClass().getResource("/com/keuda/images/mni_ugroup.png"))));
        menu.add(mi_logout = createMenuItem("Logout", new ImageIcon(getClass().getResource("/com/keuda/images/mni_logout.png"))));
        menu.add(mi_exit = createMenuItem("Exit", new ImageIcon(getClass().getResource("/com/keuda/images/mni_close.png"))));
        
        menu = new JMenu();
        menu.setIcon(new ImageIcon(getClass().getResource("/com/keuda/images/mnu_data_master.png")));
        addMenu(menu);
        menu.add(mi_masterAkun = createMenuItem("Master Akun", new ImageIcon(getClass().getResource("/com/keuda/images/master_account_small.png"))));
        JMenu tempMenu = new JMenu("Program & Kegiatan");
        tempMenu.setIcon(new ImageIcon(getClass().getResource("/com/keuda/images/program_kegiatan_18.png")));
        menu.add(tempMenu);
        tempMenu.add(mi_program = createMenuItem("Program", new ImageIcon(getClass().getResource("/com/keuda/images/program_18.png"))));
        tempMenu.add(mi_kegiatan = createMenuItem("Kegiatan", new ImageIcon(getClass().getResource("/com/keuda/images/activity_18.png"))));
    }
    
    private JMenuItem createMenuItem(String title, ImageIcon icon){
        JMenuItem mi = new JMenuItem(title, icon);
        mi.addActionListener(this);
        return mi;
    }
    @Override
    public void init() {
        try {
            KoneksiAplikasi koneksi_master = new KoneksiAplikasi();
            conn = koneksi_master.getConnection();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deinit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void login() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public JComponent createMenuBar() {
//        JOutlookBar bar = new JOutlookBar();
//        
//        JButtonBar brn = new JButtonBar();
//        
//        bar.addTab("Persediaan", create);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mi_masterAkun){
            AkunTreePanel  panel = new AkunTreePanel(null);
        }
    }

    @Override
    public JComponent createMenuBar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
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
                    //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    Theme.loadTheme(new File(System.getProperty("user.dir"), "theme/Default.theme").toURI().toURL());
                    UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                    } catch (Exception e) {
                    System.out.println("Unsuported Look And feel: " + e.getMessage());
                    }
//                    Theme.loadTheme(new File(System.getProperty("user.dir"), "theme/Default.theme").toURI().toURL());
//                    UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                    new MainFrame();
               
            }
        });
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author user
 */
public abstract class KeudaMainFrame extends JFrame {

    static KeudaMainFrame k_owner;
    public KeudaDesktopPane k_dektopPane;
    JMenuBar k_menubar;
    JToolBar k_toolbar;
    public Connection conn = null;
    public long k_sessionid = -1;
    String title = "";

    public KeudaMainFrame(String title){
        super(title);
        this.title = title;
        k_owner = this;
        adjustPosition();
        constructComponent();
        closingFrame();
    }

    private void adjustPosition() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width, dim.height - 25);
    }

    private void constructComponent() {
        k_menubar = new JMenuBar();
        setJMenuBar(k_menubar);
        

        k_toolbar = new JToolBar();
        
        
    }

    private void closingFrame() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    public abstract void init();
    
    public abstract void deinit();
    
    public abstract void login();
    
    public abstract void logout();
    
    public abstract JComponent createMenuBar();

    public JInternalFrame addInternalFrame(JPanel panel, String title, ImageIcon icon, int width, int height) {
        k_dektopPane.setLayout(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        if (icon != null) {
            frame.setFrameIcon(icon);
        }
        frame.setContentPane(panel);
        frame.setSize(width, height);
        k_dektopPane.add(frame);

        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (Exception ex) {
            Logger.getLogger(KeudaMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        return frame;
    }

    public JInternalFrame addInternalFrame(JPanel panel, String title, ImageIcon icon, int width, int height, int x, int y) {
        k_dektopPane.setLayout(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        if (icon != null) {
            frame.setFrameIcon(icon);
        }
        frame.setContentPane(panel);
        frame.setSize(width, height);
        frame.setLocation(x, y);
        k_dektopPane.add(frame);

        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (Exception ex) {
            Logger.getLogger(KeudaMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        return frame;
    }
    
    public void addMenu(JMenu menu){
        k_menubar.add(menu);
    }
    
    public void addButtonBar(JButton button){
        k_toolbar.add(button);
    }
    
    public static KeudaMainFrame getMainFrame(){
        return k_owner;
    }
    
    public JDesktopPane getDesktopPane(){
        return k_dektopPane;
    }
    
    
}

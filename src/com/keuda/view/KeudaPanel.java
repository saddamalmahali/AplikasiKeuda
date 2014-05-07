/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class KeudaPanel extends JPanel{
    public MainForm k_mainForm;
    public long k_sessionId = -1;
    public Connection k_conn;
    public BusinessLogic k_logic;
    JPanel k_pane = new JPanel(new BorderLayout());
    public Object k_object;
    public boolean k_new = false, k_edit = false;
    public int k_selectedRow = -1;
    
    public SimpleDateFormat sdf;

    public KeudaPanel() {
        setLayout(new BorderLayout());
        constructs();
    }

    public KeudaPanel(MainForm owner) {
        k_mainForm = owner;
        sdf = k_mainForm.sdf;
        k_logic = new BusinessLogic(k_mainForm.getK_conn());
        k_sessionId = k_mainForm.getK_sessionId();
        k_conn = k_mainForm.getK_conn();
        setLayout(new BorderLayout());
        constructs();
    }
    
    
    
    protected void constructs(){
        JPanel panel = new JPanel();
        BoxLayout box = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(box);
        
        JPanel p1 = new JPanel(new BorderLayout());
        
        panel.add(new JPanel());
        panel.add(p1);
        panel.add(new JPanel());
        
        box = new BoxLayout(p1, BoxLayout.Y_AXIS);
        p1.setLayout(box);
        
        JPanel p2 = new JPanel(new BorderLayout());
        p1.add(new JPanel());
        p1.add(p2);
        p1.add(new JPanel());
        
        p2.add(k_pane, "Center");
        
        k_pane.setBorder(BorderFactory.createEtchedBorder());
        add(panel, "Center");
        
    }
    public JPanel getContentPane(){
        return k_pane;
    }
}

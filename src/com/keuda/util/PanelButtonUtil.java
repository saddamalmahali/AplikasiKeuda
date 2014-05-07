/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class PanelButtonUtil extends JPanel implements ActionListener{
    JButton btn_view, btn_print;
    JPanel k_panel;
    Object[] obj;
    
    public PanelButtonUtil(Object[] obj) {
        
        btn_view = new JButton("View");
        btn_print = new JButton("Print");
        this.obj = obj;
        k_panel = new JPanel(new FlowLayout());
        
        k_panel.add(btn_view);
        k_panel.add(btn_print);
        
        add(k_panel, BorderLayout.EAST);
        
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

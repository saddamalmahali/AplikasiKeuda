/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author user
 */
public abstract class KeudaDialogCombo extends JDialog implements ActionListener{
    public MainForm k_mainForm;
    public BusinessLogic k_logic;
    public JButton k_btnOk, k_btnCancel;
    boolean k_isOk = false;

    public KeudaDialogCombo(MainForm mainForm, String title) {
        super(mainForm, title, true);
        k_mainForm = mainForm;
    }
    public void construct(){
        k_logic = new BusinessLogic(k_mainForm.getK_conn());
        k_btnOk = new JButton("Ok");
        k_btnOk.addActionListener(this);
        k_btnCancel = new JButton("Batal");
        k_btnCancel.addActionListener(this);
        JPanel pbtn = new JPanel();
        pbtn.add(k_btnOk);
        pbtn.add(k_btnCancel);
        pbtn.setBorder(BorderFactory.createEtchedBorder());
        
        getContentPane().setLayout(new BorderLayout());
        JComponent comp = constructCenterPanel();
        if(comp != null)
            getContentPane().add(comp, BorderLayout.CENTER);
        
        getContentPane().add(pbtn, BorderLayout.SOUTH);
        
    }

    @Override
    public void setVisible(boolean b) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width/2)-(getWidth()/2), (dim.height/2)-(getHeight()/2));
        super.setVisible(true);
    }

    public boolean isOk() {
        return k_isOk;
    }
    
    
    
    public abstract JComponent constructCenterPanel();
    public abstract void onOk();
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_btnOk){
            onOk();
            k_isOk = true;
        }
        else if(e.getSource() == k_btnCancel){
            k_isOk = false;
            dispose();
        }
    }
    
}

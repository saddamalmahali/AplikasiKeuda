/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.util;

import com.keuda.view.KeudaPanel;
import com.keuda.view.MainForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author adam
 */
public abstract class KeudaRincianButton extends KeudaPanel implements ActionListener{
    
    public PanelRincianButton k_panelButton;
    private JPanel k_contentPane = new JPanel(new BorderLayout());

    public KeudaRincianButton() {
        this(null);
    }
    
    public KeudaRincianButton(MainForm owner) {
        super(owner);
        constructsElement();
    }
    
    public void constructsElement(){
        k_panelButton = new PanelRincianButton();
        super.getContentPane().add(k_panelButton, "South");
        super.getContentPane().add(k_contentPane, "Center");
        k_panelButton.addButtonListener(this);
    }
    
    @Override
    public JPanel getContentPane(){
        return k_contentPane;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_panelButton.k_btnNewRincian){onNewRincian();}
        else if(e.getSource() == k_panelButton.k_btnNewDetailRincian){onNewDetailRincian();}
        else if(e.getSource() == k_panelButton.k_btnNewSubDetailRincian){onNewSubDetailRincian();}
        else if(e.getSource() == k_panelButton.k_update){onUpdate();}
        else if(e.getSource() == k_panelButton.k_delete){onDelete();}
        else if(e.getSource() == k_panelButton.k_view){onView();}
    }
    
    public abstract void onNewRincian();
    public abstract void onNewDetailRincian();
    public abstract void onNewSubDetailRincian();
    public abstract void onUpdate();
    public abstract void onDelete();
    public abstract void onView();
    
    public PanelRincianButton getPanelRincianButton(){
        return k_panelButton;
    }
    
}

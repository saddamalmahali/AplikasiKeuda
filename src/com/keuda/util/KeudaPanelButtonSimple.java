package com.keuda.util;

import com.keuda.view.KeudaPanel;
import com.keuda.view.MainForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author BENDAHARA
 * 
 */
public abstract class KeudaPanelButtonSimple extends KeudaPanel implements ActionListener{
    
    private PanelButtonSimple k_panelButton;
    private JPanel k_contentPane = new JPanel(new BorderLayout());

    public KeudaPanelButtonSimple() {
        
        this(null);
        
    }
    
    public KeudaPanelButtonSimple(MainForm owner){
        super(owner);
        constructElement();
    }
    
    public void constructElement(){
        k_panelButton = new PanelButtonSimple();
        super.getContentPane().add(k_panelButton, "South");
        super.getContentPane().add(k_contentPane, "Center");
        k_panelButton.addButtonListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_panelButton.k_btnNew){ onNew();}
        else if(e.getSource() == k_panelButton.k_btnUpdate){onEdit();}
        else if(e.getSource() == k_panelButton.k_btnDelete){onDelete();}
        else if(e.getSource() == k_panelButton.k_btnView){onView();}
    }

    @Override
    public JPanel getContentPane() {
        return k_contentPane;
    }
    
    
    
    public abstract void onNew();
    public abstract void onEdit();
    public abstract void onDelete();
    public abstract void onView();
    
    public PanelButtonSimple getPanelButton(){
        return k_panelButton;
    }
    
}

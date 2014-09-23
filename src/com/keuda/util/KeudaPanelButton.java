package com.keuda.util;

import com.keuda.view.KeudaPanel;
import com.keuda.view.MainForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public abstract class KeudaPanelButton extends KeudaPanel implements ActionListener{
    public PanelButton k_panelButton;
    private JPanel k_contentPane = new JPanel(new BorderLayout());
    
    boolean isPrint = false;

    public KeudaPanelButton() {    
        this(null, false);
    }

    public KeudaPanelButton(MainForm owner) {
        this(owner, false);
    }

    public KeudaPanelButton(MainForm owner, boolean isPrint) {
        super(owner);
        this.isPrint = isPrint;
        constructsElement();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == k_panelButton.k_btNew){ onNew();}
        else if(e.getSource() == k_panelButton.k_btEdit ){onEdit();}
        else if(e.getSource() == k_panelButton.k_btDelete ){onDelete();}
        else if(e.getSource() == k_panelButton.k_btCancel ){onCancel();}
        else if(e.getSource() == k_panelButton.k_btSave ){onSave();}
        else if(e.getSource() == k_panelButton.k_btPrint ){onPrint();}
    }

    private void constructsElement() {
        k_panelButton = new PanelButton(isPrint);
        super.getContentPane().add(k_panelButton, "South");
        super.getContentPane().add(k_contentPane, "Center");
        k_panelButton.addButtonListener(this);
    }

    @Override
    public JPanel getContentPane() {
        return k_contentPane;
    }
    
    public abstract void onNew();
    public abstract void onEdit();
    public abstract void onCancel();
    public abstract void onDelete();
    public abstract void onSave();
    public abstract void onPrint();

    public PanelButton getpanelButton() {
        return k_panelButton;
    }
    
    
}

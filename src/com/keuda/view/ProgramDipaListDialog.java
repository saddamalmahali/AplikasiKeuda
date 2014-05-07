/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.ProgramDipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class ProgramDipaListDialog extends KeudaDialog{
    
    BunxuList k_list;
    ProgramDipa k_obj;

    public ProgramDipaListDialog(MainForm mainForm) {
        super(mainForm, "Pemilihan Program DIPA", 400, 500);
    }
    
    
    
    @Override
    public JPanel constructCenterPanel() {
        k_list = new BunxuList();
        
        try {
            ProgramDipa[] objs = k_logic.getAllProgramDipa(k_mainForm.getK_sessionId(), IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i=0; i<objs.length; i++){
                ProgramDipa obj = objs[i];
                obj.setView(ProgramDipa.VIEW_PROGRAM);
                k_list.addElement(obj);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        return k_list;
    }

    @Override
    public void onOk() {
        Object obj = k_list.getSelectedValue();
        if(obj instanceof ProgramDipa){
            k_obj = (ProgramDipa) obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Program DIPA Belum dipilih", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    
    public ProgramDipa getProgramDipa(){
        return k_obj;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.exception.KeudaException;
import com.keuda.model.Program;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class ProgramListDialog extends KeudaDialog{
    
    BunxuList k_list;
    Program k_obj = null;

    public ProgramListDialog(MainForm mainForm) {
        super(mainForm, "Pemilihan Program", 400, 500);
    }
    
    
    public JPanel constructCenterPanel(){
        k_list = new BunxuList();
        try{
            Program[] objs = k_logic.getAllProgram(k_mainForm.getK_sessionId(), IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i=0; i<objs.length; i++){
                Program obj = objs[i];
                obj.setView(Program.VIEW_CODE_AND_NAME);
                k_list.addElement(obj);                
            }
        }catch(KeudaException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return k_list;
    }
    
    public Program getProgram(){
        return k_obj;
    }

    @Override
    public void onOk() {
        Object obj = k_list.getSelectedValue();
        if(obj instanceof Program){
            k_obj = (Program) obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Organisasi Belum Dipilih.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    
}

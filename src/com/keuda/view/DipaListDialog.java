/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.Dipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author client
 */
public class DipaListDialog extends KeudaDialog{
    
    BunxuList k_list;
    Dipa k_obj;
    String k_thn = "";
    
    public DipaListDialog(MainForm mainForm, String thn) {
        
        super(mainForm, "Pemilihan DIPA", 400, 500);
        k_thn = thn;
    }
    
    
    
    @Override
    public JComponent constructCenterPanel() {
        k_list = new BunxuList();
        
        try {
            Dipa[] objs = k_logic.getAllDipa(k_mainForm.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i=0; i<objs.length; i++){
                Dipa obj = objs[i];
                obj.setView(obj.VIEW_NOMOR_DIPA);
                k_list.addElement(obj);
            }
            
        } catch (Exception e) {
        }
        
        return k_list;
    }

    @Override
    public void onOk() {
        Object obj = k_list.getSelectedValue();
        if(obj instanceof Dipa){
            k_obj = (Dipa) obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "DIPA Belum Dipilih", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
}

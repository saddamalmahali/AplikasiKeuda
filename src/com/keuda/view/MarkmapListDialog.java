/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.Markmap;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author adam
 */
public class MarkmapListDialog extends KeudaDialog{
    BunxuList k_list;
    Markmap k_obj;

    public MarkmapListDialog(MainForm mainForm) {
        super(mainForm, "Pemilihan Akun", 400, 500);
    }
    
    
    
    @Override
    public JPanel constructCenterPanel() {
        k_list = new BunxuList();
        try {
            Markmap[] objs = k_logic.getAllMarkmap(k_mainForm.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            for(int i=0; i<objs.length; i++){
                Markmap obj = objs[i];
                obj.setView(Markmap.VIEW_KODE_NAMA_REK);
                k_list.addElement(obj);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return k_list;
    }

    @Override
    public void onOk() {
        Object obj = k_list.getSelectedValue();
        if(obj instanceof Markmap){
            k_obj = (Markmap) obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Akun Belum Dipilih.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    public Markmap getMarkmap(){
        return k_obj;
    }
    
}

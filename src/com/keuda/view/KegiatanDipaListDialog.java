/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.KegiatanDipa;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author client
 */
public class KegiatanDipaListDialog extends KeudaDialog{
    
    BunxuList k_list;
    KegiatanDipa k_obj;

    public KegiatanDipaListDialog(MainForm mainForm) {
        super(mainForm, "Lis Kegiatan DIPA", 400, 500);
        
    }
    
    
    @Override
    public JComponent constructCenterPanel() {
        k_list = new BunxuList();
        
        try {
            KegiatanDipa[] objs = k_logic.getAllKegiatanDipa(k_mainForm.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i=0; i<objs.length; i++){
                KegiatanDipa obj = objs[i];
                obj.setView(KegiatanDipa.VIEW_CODE_AND_NAME_KEGIATAN);
                k_list.addElement(obj);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan Mengambil Data", JOptionPane.ERROR_MESSAGE);
        }
        
        return k_list;
    }

    @Override
    public void onOk() {
        Object obj = k_list.getSelectedValue();
        if(obj instanceof KegiatanDipa){
            k_obj = (KegiatanDipa) obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Kegiatan Belum dipilih", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    public KegiatanDipa getKegiatanDipa(){
        return k_obj;
    }
    
}

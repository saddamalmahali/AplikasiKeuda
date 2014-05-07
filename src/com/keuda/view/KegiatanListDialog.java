/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.Kegiatan;
import com.keuda.model.Program;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class KegiatanListDialog extends KeudaDialog{
    
    BunxuList k_list;
    Kegiatan k_obj;
    Program k_program;

    public KegiatanListDialog(MainForm mainForm) {
        super(mainForm, "Pemilihan Kegiatan", 400,500);
    }
    
    
    
    @Override
    public JPanel constructCenterPanel() {
        k_list = new BunxuList();
        
        try {
            Kegiatan[] objs = k_logic.getAllKegiatan(k_mainForm.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
                for(int i = 0; i<objs.length; i++){
                    Kegiatan obj = objs[i];
                    obj.setView(Kegiatan.VIEW_CODE_AND_NAME);
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
        if(obj instanceof Kegiatan){
            k_obj = (Kegiatan)obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Kegiatan Belum dipilih", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    
    public Kegiatan getKegiatan(){
        return k_obj;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.exception.KeudaException;
import com.keuda.model.OutputKegiatan;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author client
 */
public class OutputKegiatanListDialog extends KeudaDialog{
    BunxuList k_list;
    OutputKegiatan k_outke;
    String k_thn;
    public OutputKegiatanListDialog(MainForm mainForm, String thn) {
        super(mainForm, "Pemilihan Output Kegiatan DIPA", 400, 500);
        k_thn = thn;
    }

    @Override
    public JComponent constructCenterPanel() {
        k_list = new BunxuList();
        try {           
            OutputKegiatan[] objs = k_logic.getAllOutputKegiatan(k_mainForm.k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            for(int i=0; i<objs.length; i++){
                OutputKegiatan obj = objs[i];
                obj.setView(obj.VIEW_NROUTPUT_OUTPUT);
                k_list.addElement(obj);
            }
            
        } catch (KeudaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Kesalahan Mengambil Data", JOptionPane.ERROR_MESSAGE);
        }
        return k_list;
    }

    @Override
    public void onOk() {
        Object obj = k_list.getSelectedValue();
        if(obj instanceof OutputKegiatan){
            k_outke = (OutputKegiatan) obj;
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Kegiatan Belum Dipilih", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    public OutputKegiatan getOutputKegiatan(){
        return k_outke;
    }
    
}

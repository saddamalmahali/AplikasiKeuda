/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class KegiatanDipaTableModel extends DefaultTableModel{

    public KegiatanDipaTableModel() {
        
        addColumn("Kode Program");//0
        addColumn("Program");//1
        addColumn("Tahun Anggaran");//2
        addColumn("Kode Kegiatan");//3
        addColumn("Nama Kegiatan");   //4     
    
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

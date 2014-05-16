/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.Dipa;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author client
 */
public class DipaPanel extends KeudaPanel implements ListSelectionListener{
    
    Dipa k_dipa;
    JTable k_table;
    MainForm k_mainForm;
    
    
    public DipaPanel() {
        
    }
    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class DipaTableModel extends DefaultTableModel{
        public DipaTableModel(){
            addColumn("Tahun Anggaran");
        }
    }
}

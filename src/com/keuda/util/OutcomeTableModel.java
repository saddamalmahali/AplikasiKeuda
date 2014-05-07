/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.model.OutcomeProgram;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class OutcomeTableModel extends DefaultTableModel{

    public OutcomeTableModel() {
    }
    
//    List<OutcomeProgram> list;
//
//    public OutcomeTableModel(List<OutcomeProgram> list) {
//        this.list = list;    
//    }
//    
//    
//    public void setList(List<OutcomeProgram> list) {
//        this.list = list;
//    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return OutcomeProgram.class;
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            
            case 0 : return "Program Dipa";
            case 1 : return "Tahun";
            case 2 : return "Nomor Urut";
            case 3 : return "Outcome";
//            case 4 : return "Keterangan";
            default : return null;
                
        }
    }
    
    
    
//    @Override
//    public int getRowCount() {
//        return (list.size()==-1) ? -1 : list.size();
//    }    
    
    
    @Override
    public int getColumnCount() {
        return 4;
    }

//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {            
//            switch(columnIndex){
//                case 0 : return list.get(rowIndex);
//                case 1 : return list.get(rowIndex).getProgramDipa().getTahunAnggaran();
//                case 2 : return list.get(rowIndex).getNmrUrut();
//                case 3 : return list.get(rowIndex).getOutcome();
//                default : return null;
//            }        
//    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
//        if(columnIndex == 4)
//            return true;
//        else 
            if(columnIndex == 0)
            return true;
        else
            return false;
                    
    }
    
    
    
}

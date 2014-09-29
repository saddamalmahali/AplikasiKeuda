/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.BankRef;
import com.keuda.model.Kota;
import com.keuda.services.IDBCConstant;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author BENDAHARA
 */
public class BankPanel extends KeudaPanel{
    BankRef k_bank;    
    JTable k_table;
    BankTableModel k_model;
    JComboBox<Object> cboBank;
    ComboBankModel cbobankmodel;
    
    BankRef[] listbank;
    Vector<Kota> kotavector = new Vector<>();
    Kota kota;
    Kota[] listkota;
    public BankPanel(MainForm mainform) {
        super(mainform);
        
        k_bank = new BankRef();
        constructComponent();
        initData("");
    }    
    
    public void constructComponent(){
        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(BorderFactory.createEtchedBorder());
        JPanel toppanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        cbobankmodel = new ComboBankModel();
        
        cboBank = new JComboBox<Object>(cbobankmodel);
        cboBank.addItemListener(cbobankmodel);
        toppanel.add(new JLabel("Berdasarkan Bank :"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        toppanel.add(cboBank, gbc);
        
        panel.add(toppanel, BorderLayout.NORTH);
                
        
        k_model = new BankTableModel();
        k_table = new JTable(k_model);
        
        k_table.getColumnModel().getColumn(0).setMinWidth(50);
        k_table.getColumnModel().getColumn(0).setMaxWidth(50);
        k_table.getColumnModel().getColumn(1).setMinWidth(100);
        k_table.getColumnModel().getColumn(1).setMaxWidth(100);
        k_table.getColumnModel().getColumn(2).setMinWidth(400);
//        k_table.getColumnModel().getColumn(2).setMaxWidth(400);
        k_table.getColumnModel().getColumn(3).setMinWidth(100);
        k_table.getColumnModel().getColumn(3).setMaxWidth(100);
        k_table.getColumnModel().getColumn(4).setMinWidth(400);
//        k_table.getColumnModel().getColumn(4).setMaxWidth(400);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        k_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        k_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        k_table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        k_table.getTableHeader().setDefaultRenderer(new thisTablerRenderer(k_table));
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_table.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(k_table), BorderLayout.CENTER);
        
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void initData(String wilayah) {
        BusinessLogic logic = new BusinessLogic(k_conn);
        if(wilayah.equals("") || wilayah == ""){
            
            try {
                listbank = logic.getAllBankRef(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(BankPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(listbank != null){
                long id = 1;
                k_model.setRowCount(0);
                for(int i = 0; i<listbank.length; i++){

                    String idstring = id+".";
                    k_bank = listbank[i];
                    k_bank.setView(k_bank.VIEW_SANDI);
                    k_model.addRow(new Object[]{ idstring, k_bank, k_bank.getNamabank(), k_bank.getWilayah(), k_bank.getAlamat()});                
                    id++;
                }


            }
        }else{
            
            try {
                listbank = logic.getAllBankRefByWilayah(wilayah, k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(BankPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(listbank != null){
                long id = 1;
                k_model.setRowCount(0);
                for(int i = 0; i<listbank.length; i++){
                    String idstring = id+".";
                    k_bank = listbank[i];
                    k_bank.setView(k_bank.VIEW_SANDI);
                    k_model.addRow(new Object[]{idstring, k_bank, k_bank.getNamabank(), k_bank.getWilayah(), k_bank.getAlamat()});                
                    id++;
                }


            }
        }
        
    }
    
    public class BankTableModel extends DefaultTableModel{
        
        public BankTableModel() {
            
            addColumn("No");
            addColumn("Sandi");
            addColumn("Nama Bank");
            addColumn("Wilayah");
            addColumn("Alamat");         
            
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
    
    public class ComboBankModel extends DefaultComboBoxModel<Object> implements ItemListener{
        BankRef[] bankresult ;
        
        public ComboBankModel() {
            addElement("");
            try {
                bankresult = k_logic.getAllBankRefSortByWilayah(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(BankPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            long id = 0;
            String oldwilayah = bankresult[0].getWilayah();
            
            kota = new Kota(id, oldwilayah);
            kotavector.removeAllElements();
            kotavector.addElement(kota);
            for(int i =0; i<bankresult.length; i++){
                if(!bankresult[i].getWilayah().equals(oldwilayah)){
                    
                    id++;
                    kota = new Kota(id, bankresult[i].getWilayah());
                    
                    kotavector.addElement(kota);
                    oldwilayah = kota.getNama();
                }else{
                    
                }
            }
            
            listkota = new Kota[kotavector.size()];
            kotavector.copyInto(listkota);
            
            for(int i=0; i<listkota.length; i++){
                addElement(listkota[i].getNama());
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            String wilayah= getSelectedItem().toString().trim();
            initData(wilayah);
        }
        
    }
    
    public class thisTablerRenderer implements TableCellRenderer{
        
        DefaultTableCellRenderer renderer;
        
        public thisTablerRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            renderer.setVerticalAlignment(JLabel.CENTER);
        }       
        
        
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            
                return lbl;
            
        }
        
    }
    
}

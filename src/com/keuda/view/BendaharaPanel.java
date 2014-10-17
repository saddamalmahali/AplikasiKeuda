/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.exception.KeudaException;
import com.keuda.model.Bendahara;
import com.keuda.services.IDBCConstant;
import com.keuda.util.KeudaPanelButtonSimple;
import com.keuda.util.PanelButton;
import com.keuda.util.PanelButtonSimple;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author BENDAHARA
 */
public class BendaharaPanel extends KeudaPanelButtonSimple{
    private JTable k_table;
    private BendaharaTableModel k_model;
    private PanelButtonSimple panelbutton;
    
    
    public BendaharaPanel(MainForm mainForm) {
        super(mainForm);
        
        
        
        constructComponent();
        initData();
    }
    
    protected void constructComponent(){
        
        JPanel panel = new JPanel(new BorderLayout());
        
        k_model = new BendaharaTableModel();
        k_table = new JTable(k_model);
        
        
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        k_table.getTableHeader().setReorderingAllowed(false);
        k_table.setRowHeight(40);
        
        JTableHeader header = k_table.getTableHeader();
        
        header.setDefaultRenderer(new thisTableRenderer(k_table));
        
        JScrollPane scrol = new JScrollPane(k_table);
        
        panel.add(scrol, BorderLayout.CENTER);
        
        panelbutton = new PanelButtonSimple();
        
        
        getContentPane().add(panel, BorderLayout.CENTER);
    }
    
    protected void initData(){
        if(k_logic != null){
            k_model.setRowCount(0);
            Bendahara[] list  = null;
            try {
                list= k_logic.getAllBendahara(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(BendaharaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            long id = 1;
            
            if(list != null){
                for(int i = 0; i<list.length; i++){
                    k_model.addRow(new Object[]{id, list[i].getKodebendahara(), list[i].getNama(), list[i].getNpwp(), list[i].getAlamat(), list[i].getEmail(), "", ""});
                }
            }
        }
    }

    @Override
    public void onNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class BendaharaTableModel extends DefaultTableModel implements ListSelectionListener{

        public BendaharaTableModel() {
            addColumn("No");
            addColumn("Kode Bendahara");
            addColumn("Nama Bendahara");
            addColumn("NPWP");
            addColumn("Alamat");
            addColumn("Email");
            addColumn("Telepon");
            addColumn("Keterangan");           
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class thisTableRenderer implements TableCellRenderer{
        DefaultTableCellRenderer renderer;
        public thisTableRenderer(JTable table) {
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

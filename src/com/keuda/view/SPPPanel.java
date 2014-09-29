package com.keuda.view;

import com.keuda.util.KeudaPanelButton;
import com.keuda.util.KeudaPanelButtonSimple;
import datechooser.beans.DateChooserCombo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

public class SPPPanel extends KeudaPanel{
    
    MainForm k_mainform;
    JTable k_table;
    thisModel k_model;
    DateChooserCombo tanggal;
    DateChooserCombo tanggalakhir;
    
    comboSatkerModel cbosatkermodel;
    comboBendaharaModel cbobendaharamodel;
    
    JComboBox cbosatker, cbobendahara;
    
    KeudaPanelButtonSimple panelbutton;
    
    
    public SPPPanel(MainForm mainform) {
        super(mainform);
        tanggal = new DateChooserCombo();
        tanggalakhir = new DateChooserCombo();
        constructComponents();
    }
    
    
    public void constructComponents(){
        //top panel
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelmasa = new JPanel(new FlowLayout());
        JPanel panelrinciansatker = new JPanel(new GridBagLayout());
        panelrinciansatker.setBorder(BorderFactory.createTitledBorder("Satker"));
        
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panelrinciansatker.add(new JLabel("Satker : "), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbosatkermodel = new comboSatkerModel();
        cbosatker = new JComboBox(cbosatkermodel);
        
        panelrinciansatker.add(cbosatker, gbc);
        
        //combo bendahara
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panelrinciansatker.add(new JLabel("Bendahara : "), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cbobendaharamodel = new comboBendaharaModel();
        cbobendahara = new JComboBox(cbobendaharamodel);
        cbobendahara.setEnabled(false);
        panelrinciansatker.add(cbobendahara, gbc);
        
        
        panel.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        
        //add panel masa tanggal
        panelmasa.add(new JLabel("Periode : "));
        tanggal.setEnabled(false);
        panelmasa.add(tanggal);
        panelmasa.add(new JLabel("Sampai : "));
        tanggalakhir.setEnabled(false);
        panelmasa.add(tanggalakhir);
        
        //add to content panel
        panel.add(panelmasa, BorderLayout.SOUTH);
        panel.add(panelrinciansatker, BorderLayout.NORTH);
        //inisialisasi tabel model 
        k_model = new thisModel();
        k_table = new JTable(k_model);
       
        
        k_table.getColumnModel().getColumn(0).setMinWidth(50);
        k_table.getColumnModel().getColumn(0).setMaxWidth(50);
        k_table.getColumnModel().getColumn(1).setMinWidth(150);
        k_table.getColumnModel().getColumn(1).setMaxWidth(150);
        k_table.getColumnModel().getColumn(2).setMinWidth(450);
        k_table.getColumnModel().getColumn(2).setMaxWidth(450);
        
        
        k_table.getTableHeader().setReorderingAllowed(false);
        
        k_table.setRowHeight(30);
        
        JTableHeader header = k_table.getTableHeader();
        
        header.setDefaultRenderer(new thisTablerRenderer(k_table));
        
        panelbutton = new KeudaPanelButtonSimple(k_mainForm) {
            
            @Override
            public void onNew() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            @Override
            public void onEdit() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onDelete() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onView() {
                throw new UnsupportedOperationException("Not supported yet.");
            }           
            
        };
        
        getContentPane().add(new JScrollPane(k_table), BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(panelbutton, BorderLayout.SOUTH);
        
    }
    
    public class thisModel extends DefaultTableModel implements ListSelectionListener{

        public thisModel() {
            
            addColumn("No");
            addColumn("Tanggal");
            addColumn("No SPP");
            addColumn("Status");
            
            addRow(new Object[]{"", "", "", ""});
            
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        
        

        @Override
        public void valueChanged(ListSelectionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    public JPanel createPanelBottom(){
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        return panel;
    }
    
    public class comboSatkerModel extends DefaultComboBoxModel implements ItemListener{

        public comboSatkerModel() {
            
        }
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); 
        }

    }
    
    public class comboBendaharaModel extends DefaultComboBoxModel implements ItemListener{

        public comboBendaharaModel() {
            
        }        
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); 
        }
        
    }
    
}

package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.DetailRincian;
import com.keuda.model.DetailRincianStruktur;
import com.keuda.model.Rincian;
import com.keuda.model.SubKomponen;
import com.keuda.services.IDBCConstant;
import com.keuda.util.ColumnGroup;
import com.keuda.util.GroupableTableHeader;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.KeudaRincianButton;
import com.keuda.util.RowColor;
import com.keuda.util.RowColorTableRender;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import pohaci.gumunda.cgui.DoubleCellRenderer;

/**
 *
 * @author adam
 */
public class RincianDipaDetailPanel extends KeudaRincianButton implements ListSelectionListener{
    private JTable k_table;
    private thisModel k_model;
    private JLabel lbl_headerdetail;
    private DetailRincian detail;
    private Rincian k_rincian;
    private DetailRincian k_detail;
    Vector vRowColor = new Vector();
    private SubKomponen subkomp;
    RincianAkunDialog rincianAkunDialog;
    DetailRincianStruktur detailrincianstruktur;
    DetailRincian detailrincian;
    DetailRincianDialog detailrinciandialog;
    
    boolean isrincian;
    boolean isdetail;
    boolean issubdetail;
                        
        public RincianDipaDetailPanel(MainForm mainForm) {            
            super(mainForm);
            
            k_model = new thisModel();
            k_table = new JTable(k_model){

                @Override
                protected JTableHeader createDefaultTableHeader() {
                    return new GroupableTableHeader(columnModel);
                }
                
            };
            
        TableColumnModel cm = k_table.getColumnModel();
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();
        
        ColumnGroup cg = new ColumnGroup("Perhitungan Anggaran");
        
        cg.add(cm.getColumn(3));
        cg.add(cm.getColumn(4));
        cg.add(cm.getColumn(5));
        header.addColumnGroup(cg);
        
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        k_table.getColumnModel().getColumn(0).setMinWidth(30);
        k_table.getColumnModel().getColumn(0).setMaxWidth(30);
        k_table.getColumnModel().getColumn(1).setMinWidth(70);
        k_table.getColumnModel().getColumn(1).setMaxWidth(70);
        k_table.getColumnModel().getColumn(2).setMinWidth(400);
        k_table.getColumnModel().getColumn(2).setMaxWidth(400);
        k_table.getColumnModel().getColumn(3).setMinWidth(50);
        k_table.getColumnModel().getColumn(3).setMaxWidth(50);
        k_table.getColumnModel().getColumn(4).setMinWidth(100);
//        k_table.getColumnModel().getColumn(5).setMaxWidth(250);
        k_table.getColumnModel().getColumn(5).setMinWidth(150);
//        k_table.getColumnModel().getColumn(6).setMaxWidth(150);
        
        k_table.getColumnModel().getColumn(4).setCellRenderer(new DoubleCellRenderer(JLabel.CENTER));
        k_table.getColumnModel().getColumn(5).setCellRenderer(new DoubleCellRenderer(JLabel.RIGHT));
        k_table.setRowHeight(20);
        
        k_table.getSelectionModel().addListSelectionListener(this);
        
            constructComponents();
        }
        
        
        public void initTable(SubKomponen sub) {
            k_model.setRowCount(0);
            double total = 0.0;
            
            if(sub != null){
                
                Rincian[] listrincian = null;
                DetailRincian[] detail = null;
                DetailRincianStruktur[] detailstruktur = null;
                
                try {
                    
                    listrincian = k_logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                    
                } catch (KeudaException ex) {
                    Logger.getLogger(RincianDipaDetailPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                int no = 1;
                if(listrincian != null || listrincian.length != -1){
                    for(int i = 0; i<listrincian.length; i++){
                        
                        if(listrincian[i] != null){
                            listrincian[i].setView(Rincian.VIEW_NOMORDIPA_KODEKEGIATAN);
                            try {
                                detailstruktur = k_logic.getAllDetailRincianStruktur(listrincian[i].getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                            } catch (KeudaException ex) {
                                
                            }
                            
                            double jumlahakun = 0.0;
                            for(int j = 0; j<detailstruktur.length; j++){
                                if(detailstruktur[j].getDetail() != null){
                                    double jumlah = 0.0;
                                    if(detailstruktur[j].getDetail().getJumlahdana() != 0.0){                                        
                                        jumlah = detailstruktur[j].getDetail().getJumlahdana()*detailstruktur[j].getDetail().getVolume();

                                        jumlahakun += jumlah;
                                        
                                    }else{
                                        
                                    }


                                }

                                DetailRincian[] subs = detailstruktur[j].getSubs();

                                if(subs != null){
                                    for(int k=0; k<subs.length; k++){


                                        
                                        double jumlah = subs[k].getJumlahdana()*subs[k].getVolume();
                                        
                                        jumlahakun += jumlah;
                                        
                                    }
                                }
                            }
                            k_model.addRow(new Object[]{no, listrincian[i], listrincian[i].getNamarekening(),
                                        "", "", new Double(jumlahakun)});
                            vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.cyan, Color.BLACK));

                            

                            for(int j = 0; j<detailstruktur.length; j++){
                                
                                if(detailstruktur[j].getDetail() != null){
                                    
                                    double jumlah = 0.0;
                                    String jumlahvolume ="";
                                    if(detailstruktur[j].getDetail().getJumlahdana() != 0.0){
                                        jumlahvolume = detailstruktur[j].getDetail().getVolume()+" "+detailstruktur[j].getDetail().getJenisvolume().toUpperCase();
                                        jumlah = detailstruktur[j].getDetail().getJumlahdana()*detailstruktur[j].getDetail().getVolume();
                                        
                                        total += jumlah;
                                        detailstruktur[j].setView(DetailRincianStruktur.VIEW_NONE);
                                        k_model.addRow(new Object[]{"", detailstruktur[j], ">   "+detailstruktur[j].getDetail().getDetailrincian(), 
                                            jumlahvolume, new Double(detailstruktur[j].getDetail().getJumlahdana()), new Double(jumlah)});
                                        
                                    }else{
                                        
                                        detailstruktur[j].setView(DetailRincianStruktur.VIEW_NONE);
                                        k_model.addRow(new Object[]{"", detailstruktur[j],  ">   "+detailstruktur[j].getDetail().getDetailrincian(), "", "", ""});
                                        
                                    }


                                }

                                DetailRincian[] subs = detailstruktur[j].getSubs();

                                if(subs != null){
                                    
                                    for(int k=0; k<subs.length; k++){


                                        String jumlahvolume = subs[k].getVolume()+" "+subs[k].getJenisvolume().toUpperCase();
                                        double jumlah = subs[k].getJumlahdana()*subs[k].getVolume();
                                        subs[k].setView(DetailRincian.VIEW_NONE);
                                        total += jumlah;
                                        k_model.addRow(new Object[]{"", subs[k], "    -   "+subs[k].getDetailrincian(), jumlahvolume, 
                                            new Double(subs[k].getJumlahdana()), new Double(jumlah)});
                                        
                                    }
                                    
                                }
                                
                            }

                        }

                        k_model.addRow(new Object[]{"",  "", "", "", "", ""});
                        no++;
                    }

                    k_model.addRow(new Object[]{"", "Total", "", "", "", new Double(total)});
                    vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.lightGray, Color.BLACK));

                    setTableRenderer(k_table);
                    if(vRowColor.isEmpty() == false){
                        vRowColor.clear();
                    }
                }
                
                subkomp = sub;
            }else{
                k_model.addRow(new Object[]{"", "Total", "", "", "", new Double(total)});
                vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.lightGray, Color.BLACK));

                setTableRenderer(k_table);
                if(vRowColor.isEmpty() == false){
                    vRowColor.clear();
                }
            }
            
        }
        
        public void setTableRenderer(JTable table){
            RowColor[] rowColors = null;
            if(vRowColor.size() > 0){
                rowColors = new RowColor[vRowColor.size()];
                vRowColor.copyInto(rowColors);
            }
            for(int j=0; j<table.getColumnCount(); j++){
                table.getColumnModel().getColumn(j).setCellRenderer(new RowColorTableRender(rowColors));
            }
        }
        
        public void constructComponents(){
            
            Font font = new Font("Tabel Akun/Detile", Font.BOLD, 20);
            lbl_headerdetail = new JLabel("Tabel Akun/Detil");
            lbl_headerdetail.setHorizontalAlignment(JLabel.CENTER);
            lbl_headerdetail.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            getContentPane().add(lbl_headerdetail, BorderLayout.NORTH);
            JScrollPane scroll = new JScrollPane(k_table);
            
            getContentPane().add(scroll, BorderLayout.CENTER);
        }
        
        

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(e.getValueIsAdjusting()){
                int rowIndex = k_table.getSelectedRow();
                Object obj = k_table.getValueAt(rowIndex, 1);
                if(obj instanceof Rincian){
                    k_rincian = (Rincian) obj;
                    
                    
                    k_panelButton.setState(k_panelButton.RINCIAN_STATE);
                    isrincian = true;
                    isdetail = false;
                    issubdetail = false;
                }else if(obj instanceof DetailRincianStruktur){
                    k_panelButton.setState(k_panelButton.DETAILRINCIAN_STATE);
                    detailrincianstruktur = (DetailRincianStruktur) obj;
                    k_rincian = detailrincianstruktur.getDetail().getRincian();
                    detailrincian = detailrincianstruktur.getDetail();
                    isrincian = false;
                    isdetail = true;
                    issubdetail = false;
                }else if(obj instanceof DetailRincian){
                    k_panelButton.setState(k_panelButton.SUBDETAILRINCIAN_STATE);
                    detailrincian = (DetailRincian) obj;
                    
                    isrincian = false;
                    isdetail = false;
                    issubdetail = true;
                }else{
                    k_panelButton.setState(k_panelButton.DISABLE_ALL);
                }
            }
        }

    @Override
    public void onNewRincian() {
        rincianAkunDialog = new RincianAkunDialog(k_mainForm, subkomp,  true);
        rincianAkunDialog.setVisible(true);        
        initTable(subkomp);
    }

    @Override
    public void onNewDetailRincian() {
        detailrinciandialog = new DetailRincianDialog(k_mainForm, k_rincian, true);
        detailrinciandialog.setVisible(true);
        initTable(subkomp);
    }

    @Override
    public void onNewSubDetailRincian() {
        detailrinciandialog = new DetailRincianDialog(k_mainForm, k_rincian, detailrincian, true);
        detailrinciandialog.setVisible(true);
        initTable(subkomp);
    }

    @Override
    public void onUpdate() {
        detailrinciandialog = new DetailRincianDialog(k_mainForm, k_rincian, detailrincian, true, true);
        detailrinciandialog.setVisible(true);
        initTable(subkomp);
//        JOptionPane.showMessageDialog(k_mainForm, "Bagian ini Masih Dalam Pengembangan.", "Perhatian", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void onDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda Yakin Akan Menghapus Data ini ?", "Peringatan", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        
        if(confirm == JOptionPane.YES_OPTION){
            BusinessLogic logic = new BusinessLogic(k_conn);
            if(isrincian){
                try {
                    logic.deleteRincian(k_rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                } catch (KeudaException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                initTable(subkomp);
                k_panelButton.setState(k_panelButton.NEW_STATE);
            }else if(isdetail){
                try {
                    logic.deleteDetailRincian(detailrincianstruktur.getDetail().getDetailrincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                } catch (KeudaException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                initTable(subkomp);
                k_panelButton.setState(k_panelButton.NEW_STATE);
            }else if(issubdetail){
                try {
                    System.out.println("Menghapus data dengan id : "+detailrincian.getDetailrincianindex());
                    logic.deleteDetailRincian(detailrincian.getDetailrincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                } catch (KeudaException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                initTable(subkomp);
                k_panelButton.setState(k_panelButton.NEW_STATE);
            }
        }
        
    }

    @Override
    public void onView() {
        
    }
        
        public class thisModel extends DefaultTableModel{
            private JLabel lbl_jml;
        
            public thisModel() {
                lbl_jml = new JLabel("Rp. 0,-");
                addColumn("No");
                addColumn("Kode");
                addColumn("Akun/Detil");
                addColumn("Volume");
                addColumn("Harga Satuan");
                addColumn("Jumlah Biaya");
            }

            public JLabel getLbl_jml() {
                return lbl_jml;
            }

            public void setLbl_jml(JLabel lbl_jml) {
                this.lbl_jml = lbl_jml;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            
            
        }
}

package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.DetailRincian;
import com.keuda.model.DetailRincianStruktur;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.Komponen;
import com.keuda.model.OutputKegiatan;
import com.keuda.model.ProgramDipa;
import com.keuda.model.Rincian;
import com.keuda.model.SubKomponen;
import com.keuda.services.IDBCConstant;
import com.keuda.util.ColumnGroup;
import com.keuda.util.GroupableTableHeader;
import com.keuda.util.RowColor;
import com.keuda.util.RowColorTableRender;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import pohaci.gumunda.cgui.DoubleCellRenderer;

/**
 *
 * @author adam
 */

public class RincianKertasKerjaSatker extends KeudaPanel{
    
    private MainForm k_mainForm;
    private DetailRincian k_detairincian;
    private DetailRincianStruktur k_detailrincianstruktur;
    private JTable k_table;
    private ThisModel k_model;
    Rincian[] listrincian;
    SubKomponen[] listSub;
    Komponen[] listkomponent;
    OutputKegiatan[] listoutputkegiatan;
    KegiatanDipa[] listkedip;
    DetailRincian[] listdetailrincian;
    DetailRincianStruktur[] listdetailrincianstruktur;
    Vector vRowColor = new Vector();
    
    
    public RincianKertasKerjaSatker(MainForm mainForm) {
        
        super(mainForm);
               
        constructComponents();
        
        k_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(1).setMinWidth(1200);
        k_table.getColumnModel().getColumn(1).setMaxWidth(1200);
        k_table.getColumnModel().getColumn(2).setMinWidth(80);
        k_table.getColumnModel().getColumn(2).setMaxWidth(80);
        k_table.getColumnModel().getColumn(5).setMinWidth(50);
        k_table.getColumnModel().getColumn(5).setMaxWidth(50);
        
        
        k_table.setRowHeight(20);
//        k_table.getColumnModel().getColumn(3).setCellRenderer(new DoubleCellRenderer(JLabel.CENTER));
//        k_table.getColumnModel().getColumn(4).setCellRenderer(new DoubleCellRenderer(JLabel.RIGHT));
    }
    
    private void constructComponents(){
        
        JPanel panel = new JPanel(new BorderLayout());
        
        k_model = new ThisModel();
        k_table = new JTable(k_model){
            
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
            
        };
        
        TableColumnModel cm = k_table.getColumnModel();
        
        GroupableTableHeader header = (GroupableTableHeader) k_table.getTableHeader();
        ColumnGroup cg = new ColumnGroup("Perhitungan Tahun 2014");
        cg.add(cm.getColumn(2));
        cg.add(cm.getColumn(3));
        cg.add(cm.getColumn(4));
        
        header.addColumnGroup(cg);
        
        JScrollPane scroll = new JScrollPane(k_table);
        
        panel.add(scroll);
        getContentPane().add(panel, BorderLayout.CENTER);
        initData();
    }
    
    public void initData(){
        
        double totalseluruh = 0.0;
        double totalrincian = 0.0;
        double totalsubkomp = 0.0;
        double totalkomp = 0.0;
        double totaloutputkegiatan = 0.0;
        double totalkegiatan = 0.0;
        double totalprogram = 0.0;
        
        BusinessLogic logic = new BusinessLogic(k_conn);
        
        try {
            ProgramDipa[] programdipalist = logic.getAllProgramDipa2(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            
            if(programdipalist != null){
                for(int i=0; i<programdipalist.length; i++){
                    
                    if(programdipalist[i].getKegiatandipa() != null){
                        for(int j = 0; j<programdipalist[i].getKegiatandipa().length; j++){
                            KegiatanDipa kegiatandipa = programdipalist[i].getKegiatandipa()[j];
                            totalkegiatan = 0.0;
                            for(int k = 0; k<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan().length; k++){
                                OutputKegiatan outke = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k];                               
                                
                                totaloutputkegiatan = 0.0;
                                for(int l=0; l<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen().length; l++){
                                    Komponen komponen = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l];
                                    
                                    totalkomp = 0.0;
                                    for(int m = 0; m<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen().length; m++){
                                        totalsubkomp = 0.0;
                                        for(int n =0; n< programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian().length; n++){
                                            totalrincian = 0.0;
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                totalrincian += jumlah;
                                                
                                            }
                                            totalsubkomp += totalrincian;
                                        }
                                        totalkomp += totalsubkomp;
                                    }
                                    totaloutputkegiatan += totalkomp;
                                }
                                totalkegiatan+=totaloutputkegiatan;
                            }
                            totalseluruh += totalkegiatan;
                        }
                    }
                    
                    k_model.addRow(new Object[]{programdipalist[i].getProgram().getProgramCode(), programdipalist[i].getProgram().getProgramName(), "", "", new Double(totalseluruh), ""});
                    vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.BLUE, Color.BLACK));
                    if(programdipalist[i].getKegiatandipa() != null){
                        for(int j = 0; j<programdipalist[i].getKegiatandipa().length; j++){
                            KegiatanDipa kegiatandipa = programdipalist[i].getKegiatandipa()[j];
                            totalkegiatan = 0.0;
                            for(int k = 0; k<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan().length; k++){
                                OutputKegiatan outke = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k];                               
                                
                                totaloutputkegiatan = 0.0;
                                for(int l=0; l<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen().length; l++){
                                    Komponen komponen = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l];
                                    
                                    totalkomp = 0.0;
                                    for(int m = 0; m<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen().length; m++){
                                        totalsubkomp = 0.0;
                                        for(int n =0; n< programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian().length; n++){
                                            totalrincian = 0.0;
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                totalrincian += jumlah;
                                                
                                            }
                                            totalsubkomp += totalrincian;
                                        }
                                        totalkomp += totalsubkomp;
                                    }
                                    totaloutputkegiatan += totalkomp;
                                }
                                totalkegiatan+=totaloutputkegiatan;
                            }
                            
                            k_model.addRow(new Object[]{kegiatandipa.getK_kegiatan().getKegiatanCode(), kegiatandipa.getK_kegiatan().getKegiatanName(), "", "", new Double(totalkegiatan), ""});
                            vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.GREEN, Color.BLACK));
                            for(int k = 0; k<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan().length; k++){
                                OutputKegiatan outke = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k];
                                int kode = outke.getNroutput();
                                String kode2 = "";
                                if(kode<10 && kode >0){
                                    kode2 = "00"+kode;
                                }else if(kode>9 && kode <100){
                                    kode2 = "0"+kode;
                                }else{
                                    kode2 = ""+kode;
                                }
                                
                                String outkekode = programdipalist[i].getKegiatandipa()[j].getK_kegiatan().getKegiatanCode()+"."+kode2;
                                totaloutputkegiatan = 0.0;
                                for(int l=0; l<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen().length; l++){
                                    Komponen komponen = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l];
                                    
                                    totalkomp = 0.0;
                                    for(int m = 0; m<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen().length; m++){
                                        totalsubkomp = 0.0;
                                        for(int n =0; n< programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian().length; n++){
                                            totalrincian = 0.0;
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                totalrincian += jumlah;
                                                
                                            }
                                            totalsubkomp += totalrincian;
                                        }
                                        totalkomp += totalsubkomp;
                                    }
                                    totaloutputkegiatan += totalkomp;
                                }
                                
                                k_model.addRow(new Object[]{outkekode, outke.getOutput(), "", "", new Double(totaloutputkegiatan), ""});
                                vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.MAGENTA, Color.BLACK));
                                
                                for(int l=0; l<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen().length; l++){
                                    Komponen komponen = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l];
                                    int kodekomponen3 = Integer.valueOf(komponen.getKomponenkode());
                                    String kodekomponen = "";
                                    if(kodekomponen3 > 0 && kodekomponen3<10){
                                        kodekomponen = "00"+kodekomponen3;
                                    }else if(kodekomponen3 >9 && kodekomponen3<100){
                                        kodekomponen = "0"+kodekomponen3;
                                    }else{
                                        kodekomponen= ""+kodekomponen3;
                                    }
                                    totalkomp = 0.0;
                                    for(int m = 0; m<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen().length; m++){
                                        totalsubkomp = 0.0;
                                        for(int n =0; n< programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian().length; n++){
                                            totalrincian = 0.0;
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                totalrincian += jumlah;
                                                
                                            }
                                            totalsubkomp += totalrincian;
                                        }
                                        totalkomp += totalsubkomp;
                                    }
                                    
                                    k_model.addRow(new Object[]{outkekode+"."+kodekomponen, komponen.getKomponenname(), "", "", new Double(totalkomp), ""});
                                    vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.ORANGE, Color.BLACK));
                                    
                                    for(int m = 0; m<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen().length; m++){
                                        totalsubkomp = 0.0;
                                        for(int n =0; n< programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian().length; n++){
                                            totalrincian = 0.0;
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                totalrincian += jumlah;
                                                
                                            }
                                            totalsubkomp += totalrincian;
                                        }
                                        
                                        SubKomponen subkomponen = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m];
                                        k_model.addRow(new Object[]{subkomponen.getSubkomponenkode(), subkomponen.getSubkomponenname(), "", "", new Double(totalsubkomp), ""});
                                        for(int n =0; n< programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian().length; n++){
                                            totalrincian = 0.0;
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                totalrincian += jumlah;
                                                
                                            }
                                            
                                            Rincian rincian =  programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n];
                                            k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalrincian), ""});
                                            
                                            for(int o = 0; o<programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian().length; o++){
                                                DetailRincian detailrincian = programdipalist[i].getKegiatandipa()[j].getOutputkegiatan()[k].getKomponen()[l].getSubkomponen()[m].getRincian()[n].getDetailrincian()[o];
                                                String satuan = detailrincian.getVolume()+"  "+detailrincian.getJenisvolume();
                                                
                                                double jumlah = detailrincian.getJumlahdana()*detailrincian.getVolume();
                                                if(detailrincian.getJumlahdana() == 0.0){
                                                    k_model.addRow(new Object[]{"", ">    "+detailrincian.getDetailrincian(), "", "", "", ""});
                                                }else{
                                                    k_model.addRow(new Object[]{"", ">    "+detailrincian.getDetailrincian(), satuan, new Double(detailrincian.getJumlahdana()), new Double(jumlah), ""});
                                                }
                                            }
                                            //End of DetailRincian
                                                    
                                        }//End of Rincian
                                    }//End of Subkomponen
                                }//End of Komponen
                                
                            }//End of OutputKegiatan
                        }//End of KegiatanDipa
                    }
                }//End of ProgramDipa
                setTableRenderer(k_table);
                if(vRowColor.isEmpty() == false){
                    vRowColor.clear();
                }
            }
            
        } catch (Exception e) {
            System.err.println(e.toString());
            JOptionPane.showMessageDialog(k_mainForm, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
//        try {
//            /**
//             * retrieve data
//             */
//            
//            listkedip = logic.getAllKegiatanDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//            
//            if(listkedip != null){
////                for(int i=0; i<listkedip.length; i++){
////                    KegiatanDipa kedip = listkedip[i];
////                    
////                    listoutputkegiatan = logic.getAllOutputKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
////                    
////                    if(listoutputkegiatan != null){
////                        for(int j=0; j<listoutputkegiatan.length; j++){
////                            OutputKegiatan outke = listoutputkegiatan[j];
////                            
////                            listkomponent = logic.getAllKomponenByOutputKegiatan(outke.getOutputkegiatanindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
////                            
////                            if(listkomponent != null){
////                                for(int k=0; k<listkomponent.length; k++){
////                                    Komponen komp = listkomponent[k];
////                                    
////                                    listSub = logic.getAllSubKomponenByKomponen(komp.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
////                                    
////                                    if(listSub != null){
////                                        for(int l=0; l<listSub.length; l++){
////                                            SubKomponen sub = listSub[l];
////                                            
////                                            if(sub != null){
////                                                listrincian = logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
////                                                if(listrincian != null){
////                                                    for(int m=0; m< listrincian.length; m++){
////                                                        Rincian rincian = listrincian[m];
////                                                        if(rincian!= null){
////                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
////                                                            if(listdetailrincianstruktur != null){
////                                                                listdetailrincianstruktur = logic.getAllDetailRincianStruktur(listrincian[m].getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
////                                                                
////                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
////                                                                    
////                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
////                                                                        
////                                                                        double jumlah1 = 0.0;
////                                                                        jumlahakun = 0.0;
////                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
////                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
////                                                                            
////                                                                            jumlahakun += jumlah1;
////                                                                            //jumlah
////                                                                        }else{
////                                                                            
////                                                                        }
////                                                                    }
////
////                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
////
////                                                                    if(subs != null){
////                                                                        double jumlah1 = 0.0;
////                                                                        for(int o=0; o<subs.length; o++){
////                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
////                                                                            jumlahakun += jumlah1;
////                                                                            //jumlah
////                                                                        }
////                                                                    }
////                                                                    
////                                                                }
////                                                            }
////                                                        }
////                                                    }
////                                                }
////                                            }                                            
////                                        }
////                                    }
////                                }
////                            }                            
////                        }
////                    }
////                    
////                }
//                
//                //Parsing kedalam tabel
//                long proses = 0;
//                System.out.println("Kalkulasi Total Pagu Anggaran");
//                for(int i=0; i<listkedip.length; i++){
//                    KegiatanDipa kedip = listkedip[i];
//                    
////                    setTableRenderer(k_table);
////                    if(vRowColor.isEmpty() == false){
////                        vRowColor.clear();
////                    }
//                    listoutputkegiatan = logic.getAllOutputKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                    
//                    if(listoutputkegiatan != null){
//                        double totalkegiatan = 0.0;
//                        for(int j=0; j<listoutputkegiatan.length; j++){
//                            OutputKegiatan outke = listoutputkegiatan[j];
//                            String nr = "";
//                            if(outke.getNroutput()>0 && outke.getNroutput()<10){
//                                nr = "00"+outke.getNroutput();
//                                
//                            }else if(outke.getNroutput()>9 && outke.getNroutput() <100 ){
//                                nr = "0"+outke.getNroutput();
//                            }else{
//                                nr = ""+outke.getNroutput();
//                            }
//                            
//                            String kodeoutput = kedip.getK_kegiatan().getKegiatanCode()+"."+nr;
//                            
//                            listkomponent = logic.getAllKomponenByOutputKegiatan(outke.getOutputkegiatanindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                            
//                            if(listkomponent!= null){
//                                double totaloutput = 0.0;
//                                for(int k=0; k<listkomponent.length; k++){
//                                    Komponen komp = listkomponent[k];
//                                    String kodekomponen = kodeoutput+"."+komp.getKomponenkode();
//                                    
//                                    listSub = logic.getAllSubKomponenByKomponen(komp.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                    
//                                    if(listSub != null){
//                                        double totalkomp = 0.0;
//                                        for(int l=0; l<listSub.length; l++){
//                                            SubKomponen sub = listSub[l];
//                                            if(sub != null){
//                                                double totalsub = 0.0;                                      
//                                                
//                                                listrincian = logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                
//                                                
//                                                if(listrincian != null){
//                                                    
//                                                    for(int m=0; m< listrincian.length; m++){
//                                                        Rincian rincian = listrincian[m];
//                                                        
//                                                        
//                                                        if(rincian!= null){
//                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                            if(listdetailrincianstruktur != null){                                                                
//                                                                double totalakun = 0.0;
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        
//                                                                        double jumlah1 = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }else{
//                                                                            
//                                                                        }
//                                                                    }
//                                                                    proses++;        
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//                                                                    
//                                                                    if(subs != null){
//                                                                        double jumlah1 = 0.0;
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            totalakun += jumlah1;
//                                                                            
//                                                                            //jumlah
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
////                                                                k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalakun),""});                                            
//                                                                
//                                                                totalsub +=totalakun;
//                                                            }
//                                                        }
//                                                    }
//                                                    
////                                                    k_model.addRow(new Object[]{sub.getSubkomponenkode(), sub.getSubkomponenname(), "", "", totalsub, ""});
//                                                    totalkomp +=totalsub;
//                                                }
//                                            }                                            
//                                        }
//                                        totaloutput+=totalkomp;
//                                    }
//                                }
//                                totalkegiatan += totaloutput;
//                            }
//                            
//                        }
//                        
////                        k_model.addRow(new Object[]{kedip.getK_kegiatan().getKegiatanCode(), kedip.getK_kegiatan().getKegiatanName(), "", "", new Double(totalkegiatan), ""});
////                        vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.GREEN, Color.BLACK));
//                        
//                        totalseluruh += totalkegiatan;
//                    }
//                }
//                System.out.println("Total Proses : "+proses);
//                System.out.println("Proses : Parsing kedalam tabel");
//                k_model.addRow(new Object[]{listkedip[0].getProgramDipa().getProgram().getProgramCode(), listkedip[0].getProgramDipa().getProgram().getProgramName(), "", "", totalseluruh, ""});
//                vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.blue, Color.BLACK));
//                
//                
//                for(int i=0; i<listkedip.length; i++){
//                    KegiatanDipa kedip = listkedip[i];
//                    
////                    setTableRenderer(k_table);
////                    if(vRowColor.isEmpty() == false){
////                        vRowColor.clear();
////                    }
//                    listoutputkegiatan = logic.getAllOutputKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                    
//                    if(listoutputkegiatan != null){
//                        
//                        
//                        double totalkegiatan = 0.0;
//                        long proseskegiatan = 0;
//                        System.out.println("Kalkulasi Pagu Kegiatan");
//                        for(int j=0; j<listoutputkegiatan.length; j++){
//                            OutputKegiatan outke = listoutputkegiatan[j];
//                            String nr = "";
//                            if(outke.getNroutput()>0 && outke.getNroutput()<10){
//                                nr = "00"+outke.getNroutput();
//                                
//                            }else if(outke.getNroutput()>9 && outke.getNroutput() <100 ){
//                                nr = "0"+outke.getNroutput();
//                            }else{
//                                nr = ""+outke.getNroutput();
//                            }
//                            
//                            String kodeoutput = kedip.getK_kegiatan().getKegiatanCode()+"."+nr;
//                            
//                            listkomponent = logic.getAllKomponenByOutputKegiatan(outke.getOutputkegiatanindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                            
//                            if(listkomponent!= null){
//                                double totaloutput = 0.0;
//                                for(int k=0; k<listkomponent.length; k++){
//                                    Komponen komp = listkomponent[k];
//                                    String kodekomponen = kodeoutput+"."+komp.getKomponenkode();
//                                    
//                                    listSub = logic.getAllSubKomponenByKomponen(komp.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                    
//                                    if(listSub != null){
//                                        double totalkomp = 0.0;
//                                        for(int l=0; l<listSub.length; l++){
//                                            SubKomponen sub = listSub[l];
//                                            if(sub != null){
//                                                double totalsub = 0.0;                                      
//                                                
//                                                listrincian = logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                
//                                                
//                                                if(listrincian != null){
//                                                    
//                                                    for(int m=0; m< listrincian.length; m++){
//                                                        Rincian rincian = listrincian[m];
//                                                        
//                                                        
//                                                        if(rincian!= null){
//                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                            if(listdetailrincianstruktur != null){                                                                
//                                                                double totalakun = 0.0;
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        
//                                                                        double jumlah1 = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }else{
//                                                                            
//                                                                        }
//                                                                    }
//                                                                    proseskegiatan++;
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//
//                                                                    if(subs != null){
//                                                                        double jumlah1 = 0.0;
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
////                                                                k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalakun),""});                                            
//                                                                
//                                                                totalsub +=totalakun;
//                                                            }
//                                                        }
//                                                    }
//                                                    
////                                                    k_model.addRow(new Object[]{sub.getSubkomponenkode(), sub.getSubkomponenname(), "", "", totalsub, ""});
//                                                    totalkomp +=totalsub;
//                                                }
//                                            }                                            
//                                        }
//                                        totaloutput+=totalkomp;
//                                    }
//                                }
//                                totalkegiatan += totaloutput;
//                            }
//                            
//                        }
//                        
//                        System.out.println("Total Proses Pagu Anggaran : "+proseskegiatan);
//                        System.out.println("Parsing Kegiatan kedalam tabel.");
//                        k_model.addRow(new Object[]{kedip.getK_kegiatan().getKegiatanCode(), kedip.getK_kegiatan().getKegiatanName(), "", "", new Double(totalkegiatan), ""});
//                        vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.GREEN, Color.BLACK));
//                        
//                        
//                        for(int j=0; j<listoutputkegiatan.length; j++){
//                            OutputKegiatan outke = listoutputkegiatan[j];
//                            String nr = "";
//                            if(outke.getNroutput()>0 && outke.getNroutput()<10){
//                                nr = "00"+outke.getNroutput();
//                                
//                            }else if(outke.getNroutput()>9 && outke.getNroutput() <100 ){
//                                nr = "0"+outke.getNroutput();
//                            }else{
//                                nr = ""+outke.getNroutput();
//                            }
//                            
//                            String kodeoutput = kedip.getK_kegiatan().getKegiatanCode()+"."+nr;
//                            
//                            listkomponent = logic.getAllKomponenByOutputKegiatan(outke.getOutputkegiatanindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                            
//                            if(listkomponent != null){
//                                double totaloutput = 0.0;
//                                for(int k=0; k<listkomponent.length; k++){
//                                    Komponen komp = listkomponent[k];
//                                    String kodekomponen = kodeoutput+"."+komp.getKomponenkode();
//                                    
//                                    listSub = logic.getAllSubKomponenByKomponen(komp.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                    
//                                    if(listSub != null){
//                                        double totalkomp = 0.0;
//                                        for(int l=0; l<listSub.length; l++){
//                                            SubKomponen sub = listSub[l];
//                                            if(sub != null){
//                                                double totalsub = 0.0;                                      
//                                                
//                                                listrincian = logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                
//                                                
//                                                if(listrincian != null){
//                                                    
//                                                    for(int m=0; m< listrincian.length; m++){
//                                                        Rincian rincian = listrincian[m];
//                                                        
//                                                        
//                                                        if(rincian!= null){
//                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                            if(listdetailrincianstruktur != null){                                                                
//                                                                double totalakun = 0.0;
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        
//                                                                        double jumlah1 = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }else{
//                                                                            
//                                                                        }
//                                                                    }
//
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//
//                                                                    if(subs != null){
//                                                                        double jumlah1 = 0.0;
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
////                                                                k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalakun),""});                                            
//                                                                
//                                                                totalsub +=totalakun;
//                                                            }
//                                                        }
//                                                    }
//                                                    
////                                                    k_model.addRow(new Object[]{sub.getSubkomponenkode(), sub.getSubkomponenname(), "", "", totalsub, ""});
//                                                    totalkomp +=totalsub;
//                                                }
//                                            }                                            
//                                        }
//                                        totaloutput+=totalkomp;
//                                    }
//                                }
//                                k_model.addRow(new Object[]{kodeoutput, outke.getOutput(), "", "", totaloutput, ""});
//                                vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.MAGENTA, Color.BLACK));
//                                
//                                for(int k=0; k<listkomponent.length; k++){
//                                    Komponen komp = listkomponent[k];
//                                    String kodekomponen = kodeoutput+"."+komp.getKomponenkode();
//                                    
//                                    listSub = logic.getAllSubKomponenByKomponen(komp.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                    
//                                    if(listSub != null){
//                                        double totalkomp = 0.0;
//                                        for(int l=0; l<listSub.length; l++){
//                                            SubKomponen sub = listSub[l];
//                                            if(sub != null){
//                                                double totalsub = 0.0;                                      
//                                                
//                                                listrincian = logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                
//                                                
//                                                if(listrincian != null){
//                                                    
//                                                    for(int m=0; m< listrincian.length; m++){
//                                                        Rincian rincian = listrincian[m];
//                                                        
//                                                        
//                                                        if(rincian!= null){
//                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                            if(listdetailrincianstruktur != null){                                                                
//                                                                double totalakun = 0.0;
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        
//                                                                        double jumlah1 = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }else{
//                                                                            
//                                                                        }
//                                                                    }
//
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//
//                                                                    if(subs != null){
//                                                                        double jumlah1 = 0.0;
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
////                                                                k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalakun),""});                                            
//                                                                
//                                                                totalsub +=totalakun;
//                                                            }
//                                                        }
//                                                    }
//                                                    
////                                                    k_model.addRow(new Object[]{sub.getSubkomponenkode(), sub.getSubkomponenname(), "", "", totalsub, ""});
//                                                    totalkomp +=totalsub;
//                                                }
//                                            }                                            
//                                        }
//                                        
//                                        k_model.addRow(new Object[]{kodekomponen, komp.getKomponenname(), "", "", new Double(totalkomp), ""});
//                                        vRowColor.addElement(new RowColor(k_table.getRowCount()-1, true, Color.ORANGE, Color.BLACK));
//                                        
//                                        for(int l=0; l<listSub.length; l++){
//                                            SubKomponen sub = listSub[l];
//                                            if(sub != null){
//                                                double totalsub = 0.0;                                      
//                                                
//                                                listrincian = logic.getRincianBySubkomp(sub.getSubkomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                
//                                                
//                                                if(listrincian != null){
//                                                    
//                                                    for(int m=0; m< listrincian.length; m++){
//                                                        Rincian rincian = listrincian[m];
//                                                        
//                                                        
//                                                        if(rincian!= null){
//                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                            if(listdetailrincianstruktur != null){                                                                
//                                                                double totalakun = 0.0;
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        
//                                                                        double jumlah1 = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }else{
//                                                                            
//                                                                        }
//                                                                    }
//
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//
//                                                                    if(subs != null){
//                                                                        double jumlah1 = 0.0;
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
////                                                                k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalakun),""});                                            
//                                                                
//                                                                totalsub +=totalakun;
//                                                            }
//                                                        }
//                                                    }
//                                                    
//                                                    k_model.addRow(new Object[]{sub.getSubkomponenkode(), sub.getSubkomponenname(), "", "", totalsub, ""});
//                                                    
//                                                    for(int m=0; m< listrincian.length; m++){
//                                                        Rincian rincian = listrincian[m];
//                                                        
//                                                        
//                                                        if(rincian!= null){
//                                                            listdetailrincianstruktur = logic.getAllDetailRincianStruktur(rincian.getRincianindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
//                                                            if(listdetailrincianstruktur != null){                                                                
//                                                                double totalakun = 0.0;
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        
//                                                                        double jumlah1 = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlah1 = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }else{
//                                                                            
//                                                                        }
//                                                                    }
//
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//
//                                                                    if(subs != null){
//                                                                        double jumlah1 = 0.0;
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            jumlah1 = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            totalakun += jumlah1;
//                                                                            //jumlah
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
//                                                                k_model.addRow(new Object[]{rincian.getKoderekening(), rincian.getNamarekening(), "", "", new Double(totalakun),"RM"});
//                                                                k_model.addRow(new Object[]{"", "(KPPN.133-JAKARTA IV)", "", "", "", ""});
//                                                                
//                                                                
//                                                                for(int n=0; n< listdetailrincianstruktur.length; n++){
//                                                                                                                                       
//                                                                    
//                                                                    if(listdetailrincianstruktur[n].getDetail() != null){
//                                                                        String jumlahvolume = "";
//                                                                        double jumlah = 0.0;
//                                                                        if(listdetailrincianstruktur[n].getDetail().getJumlahdana() != 0.0){
//                                                                            jumlahvolume = listdetailrincianstruktur[n].getDetail().getVolume()+" "+listdetailrincianstruktur[n].getDetail().getJenisvolume().toUpperCase();
//                                                                            jumlah = listdetailrincianstruktur[n].getDetail().getJumlahdana()*listdetailrincianstruktur[n].getDetail().getVolume();
//                                                                            
//                                                                            k_model.addRow(new Object[]{"", ">    "+listdetailrincianstruktur[n].getDetail().getDetailrincian(), jumlahvolume, new Double(listdetailrincianstruktur[n].getDetail().getJumlahdana()), new Double(jumlah), ""});
//                                                                        }else{
//                                                                            k_model.addRow(new Object[]{"", ">    "+listdetailrincianstruktur[n].getDetail().getDetailrincian(), "", "", "", ""});
//                                                                        }
//                                                                    }
//
//                                                                    DetailRincian[] subs = listdetailrincianstruktur[n].getSubs();
//
//                                                                    if(subs != null){
//                                                                        for(int o=0; o<subs.length; o++){
//                                                                            String jumlahvolume = subs[o].getVolume()+" "+subs[o].getJenisvolume().toUpperCase();
//                                                                            double jumlah = subs[o].getJumlahdana()*subs[o].getVolume();
//                                                                            subs[o].setView(DetailRincian.VIEW_NONE);
//                                                                            
//                                                                            k_model.addRow(new Object[]{"",  "    -   "+subs[k].getDetailrincian(), jumlahvolume, 
//                                                                                new Double(subs[k].getJumlahdana()), new Double(jumlah), ""});
//                                                                        }
//                                                                    }
//                                                                    
//                                                                }
//                                                                
//                                                                k_model.addRow(new Object[]{"", "", "", "", "", ""});
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }                                            
//                                        }
//                                    }
//                                }
//                            }                            
//                        }
//                    }
//                    
//                }
//                setTableRenderer(k_table);
//                if(vRowColor.isEmpty() == false){
//                    vRowColor.clear();
//                }
//            }
//            
//            
//            
//        } catch (KeudaException ex) {
//            Logger.getLogger(RincianKertasKerjaSatker.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
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
    
    class ThisModel extends DefaultTableModel{
        
        public ThisModel() {
            
            addColumn("KODE");//0
            addColumn("PROGRAM/KEGIATAN/OUTPUT/SUBOUTPUT/KOMPONEN/SUBKOMP/AKUN/DETIL");//1
            addColumn("VOLUME");//2
            addColumn("HARGA SATUAN");//3
            addColumn("JUMLAH BIAYA");//4
            addColumn("SD/CP");//5
            
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
        
        
        
    }
    
}

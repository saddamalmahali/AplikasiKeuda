
package com.keuda.view;

import com.keuda.Logic.BusinessLogic;
import com.keuda.exception.KeudaException;
import com.keuda.model.Dipa;
import com.keuda.model.KegiatanDipa;
import com.keuda.model.Komponen;
import com.keuda.model.OutputKegiatan;
import com.keuda.model.Rincian;
import com.keuda.model.RincianDipaKodrek;
import com.keuda.model.SubKomponen;
import com.keuda.services.IDBCConstant;
import com.keuda.util.BunxuList;
import com.keuda.util.ButtonAddDelPanel;
import com.keuda.util.PanelRincianButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author adam
 */

public class RincianDipaPanel extends KeudaPanel{
    
    private RincianDipaKodrek k_rincianDipaKodrek;
    private Rincian k_rincian;
    private KegiatanDipa k_kedip;
    private OutputKegiatan k_outke;
    private Komponen k_komponen;
    private SubKomponen k_subkomp;    
    private Dipa k_dipa;
    private SubKomponen k_subKomponen;
    
    private JTextField txt_thnanggaran, txt_kodeprogram, txt_kodekegiatan, txt;
    private JComboBox cbo_kedip, cbo_outke, cbo_komponen;
    private JLabel lbl_headerdetail;
    private JTextArea txt_program, txt_kegiatan;
    private JButton btn_topbrowse;
    private JPanel panel;
    private BunxuList k_listright;
    private OutputKegiatanDipaComboModel outkeCboModel;
    private KomponenComboModel komponenCboModel;
    private RincianDipaDetailPanel k_panelrincian;
    ButtonAddDelPanel k_adebtn;
    
    public RincianDipaPanel(MainForm mainForm) {
        super(mainForm);
        
        constructComponents();
        
        
        
    }
    
    public void constructComponents(){
        panel = new JPanel(new BorderLayout());
        panel.add(constructTopPanel(), BorderLayout.NORTH);
        panel.add(constructCenterPanel(), BorderLayout.CENTER);
        
        
        cbo_kedip.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                comboKegiatanDipaItemAct(e);
            }
        });
        cbo_outke.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                comboOutputKegiatanAct(e);
            }
        });
        
        cbo_komponen.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                comboKomponenItemAct(e);
            }
        });
        
        getContentPane().setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        getContentPane().add(panel);
        k_panelrincian.k_panelButton.setState(PanelRincianButton.DISABLE_ALL);
    }
    
    public JPanel constructTopPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 2, 5);
        c.anchor = GridBagConstraints.WEST;
        
        Dimension d_label_short = new Dimension(120, 20);
        
        Dimension d_txtFieldShort = new Dimension(60, 20);
        Dimension d_txtFieldLong = new Dimension(450, 20);
        Dimension d_txtFieldBigger = new Dimension(250, 60);
        
        EmptyBorder border = new EmptyBorder(new Insets(2, 2, 2, 2));
        
        
        JLabel lbl_thn  = new JLabel("Tahun Anggaran :");
//        lbl_thn.setHorizontalAlignment(JLabel.RIGHT);
//        lbl_thn.setPreferredSize(d_label_short);
        lbl_thn.setBorder(border);
        panel.add(lbl_thn, c);
        
        txt_thnanggaran = new JTextField("");
        txt_thnanggaran.setPreferredSize(d_txtFieldShort);
        txt_thnanggaran.setEditable(false);
        c.gridx = 1;
        panel.add(txt_thnanggaran, c);
        
        JLabel lbl_program = new JLabel("Program :");
//        lbl_program.setPreferredSize(d_label_short);
        lbl_program.setBorder(border);
//        lbl_program.setHorizontalAlignment(JLabel.RIGHT);
        c.gridy = 1;
        c.gridx = 0;
        c.insets = new Insets(0, 5, 5, 5);
        panel.add(lbl_program, c);
        
        c.insets = new Insets(0, 5, 5, 0);
        txt_kodeprogram = new JTextField("");
        txt_kodeprogram.setPreferredSize(d_txtFieldShort);
        txt_kodeprogram.setEditable(false);
        c.gridx = 1;
        panel.add(txt_kodeprogram, c);
        
        JLabel lbl_kegiatan = new JLabel("Keigatan ");
        lbl_kegiatan.setBorder(border);
        c.gridx = 6;
        c.fill = GridBagConstraints.NONE;
        panel.add(lbl_kegiatan, c);
        
        txt_kodekegiatan = new JTextField("");
        txt_kodekegiatan.setPreferredSize(d_txtFieldShort);
        txt_kodekegiatan.setEditable(false);
        c.gridx = 7;
        panel.add(txt_kodekegiatan, c);
        
        txt_kegiatan = new JTextArea("");
        txt_kegiatan.setPreferredSize(d_txtFieldBigger);
        txt_kegiatan.setWrapStyleWord(true);
        txt_kegiatan.setLineWrap(true);
        JScrollPane scroll_kegiatan = new JScrollPane(txt_kegiatan);
        txt_kegiatan.setEditable(false);
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 2;
        c.gridx = 7;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scroll_kegiatan, c);
        
        
        txt_program = new JTextArea("");
        txt_program.setPreferredSize(d_txtFieldBigger);
        JScrollPane scrol_program = new JScrollPane(txt_program);
        txt_program.setEditable(false);
        txt_program.setWrapStyleWord(true);
        txt_program.setLineWrap(true);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(scrol_program, c);
        
        JLabel lbl_kegiatandipa = new JLabel("Kegiatan DIPA");
        lbl_kegiatandipa.setBorder(border);
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 0;
        c.gridheight = 1;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        
        panel.add(lbl_kegiatandipa, c);
        
        cbo_kedip = new JComboBox(new KegiatanDipaComboModel());
        cbo_kedip.setPreferredSize(d_txtFieldLong);
        c.gridx = 1;
        panel.add(cbo_kedip, c);
        
        JLabel lbl_output = new JLabel("Output");
        lbl_output.setBorder(border);
        c.gridx = 0;
        c.gridy = 5;
        c.fill = GridBagConstraints.NONE;
        panel.add(lbl_output, c);
        
        outkeCboModel = new OutputKegiatanDipaComboModel(k_kedip);
        cbo_outke = new JComboBox(outkeCboModel);
        cbo_outke.setEnabled(false);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(cbo_outke, c);
        
        return panel;        
    }
    
    public JPanel constructCenterPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel panelcenter = new JPanel(new BorderLayout());
        JPanel panelright = new JPanel(new BorderLayout());
        
        k_listright = new BunxuList();
        k_listright.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                listRigthAction(e);
            }
        });
        komponenCboModel = new KomponenComboModel();
        
        cbo_komponen = new JComboBox(komponenCboModel);
        cbo_komponen.setPreferredSize(new Dimension(170, 20));
        cbo_komponen.setEnabled(false);
        
        
        JPanel panelkomponen = new JPanel(new FlowLayout());
        panelkomponen.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(), "Komponen"));
        panelkomponen.add(cbo_komponen);
        k_adebtn = new ButtonAddDelPanel(true);
        
        panelright.add(panelkomponen, BorderLayout.NORTH);
        JPanel paneladd = new JPanel(new BorderLayout());
        paneladd.add(k_adebtn, BorderLayout.EAST);
        panelright.add(paneladd, BorderLayout.SOUTH);
        panelright.add(k_listright, BorderLayout.CENTER);
        
        
        k_panelrincian = new RincianDipaDetailPanel(k_mainForm);
        
        
//        panelcenter.add(lbl_headerdetail, BorderLayout.NORTH);
        panelcenter.add(k_panelrincian, BorderLayout.CENTER);
        
        JSplitPane splitcenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelright, panelcenter);
        splitcenter.setDividerLocation(200);
        panel.add(splitcenter, BorderLayout.CENTER);
        return panel;
    }
    
    
    
    private void setDataTop(KegiatanDipa kedip){
        if(kedip != null){
            txt_thnanggaran.setText(""+kedip.getProgramDipa().getTahunAnggaran());
            txt_kodeprogram.setText(kedip.getProgramDipa().getProgram().getProgramCode());
            txt_program.setText(kedip.getProgramDipa().getProgram().getProgramName());
            txt_kodekegiatan.setText(kedip.getK_kegiatan().getKegiatanCode());
            txt_kegiatan.setText(kedip.getK_kegiatan().getKegiatanName());
        }else{
            txt_thnanggaran.setText("");
            txt_kodeprogram.setText("");
            txt_program.setText("");
            txt_kodekegiatan.setText("");
            txt_kegiatan.setText("");
        }
    }
    
    private void setDataListRight(Komponen komponen){
        if(komponen != null){
            k_listright.removeAllElements();
            BusinessLogic logic = new BusinessLogic(k_conn);
            SubKomponen[] results = null;
            try {
                results = logic.getAllSubKomponenByKomponen(komponen.getKomponenindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(RincianDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(results != null){
                for(int i=0; i<results.length; i++){
                    SubKomponen subkomp = results[i];
                    subkomp.setView(SubKomponen.VIEW_KODE_AND_NAME);
                    k_listright.addElement(subkomp);
                }
            }
            
        }else{
            k_listright.removeAllElements();
        }
    }
    
    public class KegiatanDipaComboModel extends  DefaultComboBoxModel<Object>{

        public KegiatanDipaComboModel() {
            
            addElement("");
            
            BusinessLogic logic = new BusinessLogic(k_conn);
             KegiatanDipa[] list_kedip = null;
            try {
                list_kedip = logic.getAllKegiatanDipa(k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
            } catch (KeudaException ex) {
                Logger.getLogger(RincianDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(list_kedip != null){
                for(int i=0; i<list_kedip.length; i++){
                    KegiatanDipa kedip = list_kedip[i];
                    kedip.setView(kedip.VIEW_CODE_AND_NAME_KEGIATAN);
                    addElement(kedip);
                }
            }
        }
    }
    
    public class OutputKegiatanDipaComboModel extends DefaultComboBoxModel<Object>{
        
        public OutputKegiatanDipaComboModel(KegiatanDipa kedip) {
            addElement("");
        }
        
        public void setData(KegiatanDipa kedip){
            if(kedip != null){
                removeAllElements();
                addElement("");
                OutputKegiatan[] listoutke = null;
                BusinessLogic logic = new BusinessLogic(k_conn);
                try {
                    listoutke = logic.getAllOutputKegiatanByKegiatan(kedip.getKegiatandipaindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                } catch (KeudaException ex) {
                    Logger.getLogger(RincianDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(listoutke != null){
                    for(int i=0; i<listoutke.length; i++){
                        OutputKegiatan outke = listoutke[i];
                        outke.setView(outke.VIEW_NROUTPUT_OUTPUT);
                        addElement(outke);
                    }
                }
            }else{
                removeAllElements();
                addElement("");
            }            
        }
        
    }
    
    public void comboKegiatanDipaItemAct(ItemEvent e){
        String kedip = cbo_kedip.getSelectedItem().toString();
        if(!kedip.trim().equals("")){
            outkeCboModel.removeAllElements();
            Object obj = cbo_kedip.getSelectedItem();
            if(obj instanceof KegiatanDipa){
                k_kedip = (KegiatanDipa) obj;
                setDataTop(k_kedip);
                outkeCboModel.setData(k_kedip);
                cbo_outke.setEnabled(true);            
                
            }
        }else{
            k_kedip = null;
            k_outke = null;
            k_komponen = null;
            k_subKomponen = null;
            
            setDataTop(k_kedip);
            outkeCboModel.removeAllElements();
            komponenCboModel.setData(k_outke);
            setDataListRight(k_komponen);
            
            k_panelrincian.initTable(k_subKomponen);
            cbo_outke.setEnabled(false);
            cbo_komponen.setEnabled(false);
            k_panelrincian.k_panelButton.setState(PanelRincianButton.DISABLE_ALL);
        }
    }
    
    public void comboKomponenItemAct(ItemEvent e){
        if(cbo_komponen.getSelectedItem() == null){
            
        }else{
            String objstring = cbo_komponen.getSelectedItem().toString();
        
            if(!objstring.trim().equals("")){
                k_listright.removeAllElements();
                Object obj = cbo_komponen.getSelectedItem();
                if(obj instanceof Komponen){
                    k_komponen = (Komponen) obj;
                    k_subKomponen = null;
                    
                    setDataListRight(k_komponen);
                    k_panelrincian.initTable(k_subKomponen);
                }
            }else{
                k_komponen = null;
                k_subKomponen = null;
                
                setDataListRight(k_komponen);
                k_panelrincian.initTable(k_subKomponen);
                k_panelrincian.k_panelButton.setState(PanelRincianButton.DISABLE_ALL);
            }
        }
    }
    
    public void comboOutputKegiatanAct(ItemEvent e){
        if(cbo_outke.getSelectedItem() == null){
            
        }else{
            String outke = cbo_outke.getSelectedItem().toString();
            if(!outke.trim().equals("")){
                Object obj = cbo_outke.getSelectedItem();

                if(obj instanceof OutputKegiatan){
                    k_outke = (OutputKegiatan) obj;
                    k_outke.setView(k_outke.VIEW_NROUTPUT_OUTPUT);
                    komponenCboModel.setData(k_outke);
                    
                    cbo_komponen.setEnabled(true);
                }
            }else{
                k_outke = null;
                k_komponen = null;
                komponenCboModel.setData(k_outke);
                setDataListRight(k_komponen);
                cbo_komponen.setEnabled(false);
            }
        }
    }
    
    public class KomponenComboModel extends DefaultComboBoxModel<Object>{

        public KomponenComboModel() {
            addElement("");
        }
        
        public void setData(OutputKegiatan outke){
            if(outke != null){
                removeAllElements();
                addElement("");
                BusinessLogic logic = new BusinessLogic(k_conn);
                Komponen[] results = null;
                
                try {
                    results = logic.getAllKomponenByOutputKegiatan(outke.getOutputkegiatanindex(), k_sessionId, IDBCConstant.MODUL_CONFIGURATION);
                } catch (KeudaException ex) {
                    Logger.getLogger(RincianDipaPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(results!=null){
                    for(int i =0; i<results.length; i++){
                        Komponen komp = results[i];
                        komp.setView(komp.VIEW_CODE_AND_NAME);
                        addElement(komp);
                    }
                }else{
                    removeAllElements();
                }
                
            }else{
                removeAllElements();
                addElement("");
            }
        }
    }
    
    public void listRigthAction(ListSelectionEvent e){
        Object obj = k_listright.getSelectedValue();
        
        if(obj instanceof SubKomponen){
            k_subKomponen = (SubKomponen) obj;
                
            k_panelrincian.initTable(k_subKomponen);
            k_panelrincian.k_panelButton.setState(PanelRincianButton.NEW_STATE);
            
        }else{
            
            k_panelrincian.k_panelButton.setState(PanelRincianButton.DISABLE_ALL);
        }
    }
    
}

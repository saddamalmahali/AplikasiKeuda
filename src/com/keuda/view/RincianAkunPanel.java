package com.keuda.view;

import com.keuda.model.KodrekRincian;
import com.keuda.model.RincianDipaKodrek;
import com.keuda.util.BunxuList;
import com.keuda.util.ButtonAddDelPanel;
import com.keuda.util.KeudaPanelButton;
import com.keuda.util.Misc;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author client
 */
public class RincianAkunPanel extends KeudaPanelButton implements ListSelectionListener {

    private MainForm k_mainForm;
    private RincianDipaKodrek k_rdipakodrek;

    TabelRincianAkunModel k_model;
    BunxuList k_list;

    JComboBox cbo_tahun;

    int selectRowRincian = -1;

    JTextField k_txtnodip, k_txtkodeprogram, k_txtkodekegiatan, k_txtkoderekening, k_txtlokasi, k_txtkppn, k_txtcarapenarikan;

    JTextArea k_txtnamarekening;

    thisTable k_table;

    ButtonAddDelPanel k_adebtn;

    public RincianAkunPanel(MainForm mainForm) {
        super(mainForm, false);
        this.k_mainForm = mainForm;

        k_list = new BunxuList();
        construct();
    }

    private void construct() {
        cbo_tahun = new JComboBox(new ComboDetailAkun());
        k_adebtn = new ButtonAddDelPanel(true);
        k_adebtn.getAddButton().setEnabled(true);
        JPanel panelTopDetail = new JPanel(new FlowLayout());
        panelTopDetail.setBorder(BorderFactory.createEtchedBorder());
        JLabel lbl_tahun = new JLabel("Tahun Anggaran : ");

        panelTopDetail.add(lbl_tahun);
        panelTopDetail.add(cbo_tahun);

        JPanel panelCenter = new JPanel(new BorderLayout(10, 10));
        JPanel panelDetail = createCenterPanel();
        panelCenter.setBorder(BorderFactory.createEtchedBorder());
        panelCenter.add(createPanelTable(), BorderLayout.CENTER);
        panelCenter.add(createCenterPanel(), BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, k_list, panelCenter);
        split.setDividerLocation(200);

        getContentPane().add(split, BorderLayout.CENTER);
        getContentPane().add(panelTopDetail, BorderLayout.NORTH);
        
    }
    
    public void setComponent(boolean isActive){
        
    }
    
    private JPanel createCenterPanel() {

        EmptyBorder border1 = new EmptyBorder(new Insets(0, 0, 0, 20));

        Dimension dimensi = new Dimension(40, 20);
        Dimension dimensi2 = new Dimension(200, 40);
        Dimension dimensi3 = new Dimension(200, 100);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.WEST;

        k_txtnodip = new JTextField("");
        k_txtnodip.setEditable(false);
        k_txtnodip.setPreferredSize(new Dimension(200, 20));
        Misc.setGridBagConstraints(panel, new JLabel("Nomor DIPA"), gc, 0, 0, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, new Insets(5, 5, 0, 5));
        Misc.setGridBagConstraints(panel, k_txtnodip, gc, 1, 0, GridBagConstraints.HORIZONTAL, 3, 1, 1.0, 0.0, null);

        k_txtkodeprogram = new JTextField("");
        k_txtkodeprogram.setEditable(false);
        k_txtkodeprogram.setPreferredSize(new Dimension(80, 20));
        Misc.setGridBagConstraints(panel, new JLabel("Program"), gc, 0, 1, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, null);
        Misc.setGridBagConstraints(panel, k_txtkodeprogram, gc, 1, 1, GridBagConstraints.NONE, 1, 1, 1.0, 0.0, null);

        k_txtkodekegiatan = new JTextField("");
        k_txtkodekegiatan.setEditable(false);
        k_txtkodekegiatan.setPreferredSize(new Dimension(80, 20));
        Misc.setGridBagConstraints(panel, new JLabel("Kegiatan"), gc, 2, 1, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, new Insets(5, 5, 5, 5));
        Misc.setGridBagConstraints(panel, k_txtkodekegiatan, gc, 3, 1, GridBagConstraints.NONE, 1, 1, 1.0, 0.0, null);

        k_txtkoderekening = new JTextField("");
        k_txtkoderekening.setPreferredSize(new Dimension(80, 20));
        k_txtkoderekening.setEditable(false);
        Misc.setGridBagConstraints(panel, new JLabel("Kode Akun"), gc, 0, 2, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, null);
        Misc.setGridBagConstraints(panel, k_txtkoderekening, gc, 1, 2, GridBagConstraints.NONE, 1, 1, 1.0, 0.0, null);

        k_txtnamarekening = new JTextArea(3, 3);
        k_txtnamarekening.setEditable(false);
        k_txtnamarekening.setLineWrap(true);
        k_txtnamarekening.setWrapStyleWord(true);
        JScrollPane scrollrekening = new JScrollPane(k_txtnamarekening);
        scrollrekening.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gc.anchor = GridBagConstraints.NORTHWEST;
        Misc.setGridBagConstraints(panel, new JLabel("Akun"), gc, 0, 3, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, null);
        Misc.setGridBagConstraints(panel, scrollrekening, gc, 1, 3, GridBagConstraints.HORIZONTAL, 4, 1, 1.0, 0.0, null);

        k_txtlokasi = new JTextField("");
        k_txtlokasi.setPreferredSize(new Dimension(80, 20));
        k_txtlokasi.setEditable(false);
        Misc.setGridBagConstraints(panel, new JLabel("Lokasi"), gc, 0, 4, GridBagConstraints.NONE, 1, 1, 0.0, 0.0, null);
        Misc.setGridBagConstraints(panel, k_txtlokasi, gc, 1, 4, GridBagConstraints.NONE, 1, 1, 1.0, 0.0, null);

        return panel;
    }

    private void constructTopPanel() {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

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
    public void onCancel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class ComboDetailAkun extends DefaultComboBoxModel {

        public ComboDetailAkun() {
            addElement("2014");
            addElement("2015");
            addElement("2016");
        }

    }

    private JPanel createPanelTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Detail Rincian", TitledBorder.CENTER, TitledBorder.CENTER));

        k_table = new thisTable();

        k_model = (TabelRincianAkunModel) k_table.getModel();
        k_table.getSelectionModel().addListSelectionListener(k_model);
        
        k_table.getColumnModel().getColumn(0).setMinWidth(100);
        k_table.getColumnModel().getColumn(0).setMaxWidth(100);
        k_table.getColumnModel().getColumn(1).setMinWidth(250);
        k_table.getColumnModel().getColumn(1).setMaxWidth(250);
        k_table.getColumnModel().getColumn(2).setMinWidth(100);
        k_table.getColumnModel().getColumn(2).setMaxWidth(100);
        
        
        JScrollPane sc = new JScrollPane(k_table);
        panel.add(k_adebtn, BorderLayout.NORTH);
        panel.add(sc, BorderLayout.CENTER);
        
        return panel;
    }

    class thisTable extends JTable {

        TabelRincianAkunModel model;

        public thisTable() {
            model = new TabelRincianAkunModel();
            setModel(model);
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            getSelectionModel().addListSelectionListener(model);
        }

        public void setData(KodrekRincian[] objs) {
            model.setRowCount(0);
            int i = 1;
            for (KodrekRincian obj : objs) {
                model.addRow(new Object[]{String.valueOf(i), obj, obj.getNilaiitem()});
                i++;
            }
        }

        public void setData(KodrekRincian obj) {
            model.addRow(new Object[]{model.getRowCount()+1, obj, obj.getNilaiitem()});
        }

        public void updateData(KodrekRincian obj) {
            model.setValueAt(obj, selectRowRincian, 1);
            model.setValueAt(new Double(obj.getNilaiitem()), selectRowRincian, 2);
            
        }

    }

    public class TabelRincianAkunModel extends DefaultTableModel implements ListSelectionListener {

        public TabelRincianAkunModel() {
            addColumn("No Urut");
            addColumn("Rincian Item");
            addColumn("Nilai Item");
            
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}

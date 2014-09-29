/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import com.keuda.view.KeudaPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class PanelButtonSimple extends KeudaPanel {

    public JButton k_btnNew = new JButton("Tambah");
    public JButton k_btnUpdate = new JButton("Ubah");
    public JButton k_btnDelete = new JButton("Hapus");
    public JButton k_btnView = new JButton("Lihat Detail");

    short k_state = -1;
    public static final short NEW_STATE = 0;
    public static final short AFTER_SAVE_STATE = 1;
    public static final short AFTER_NEW_STATE = 2;
    public static final short ALL_DISABLE_STATE = 3;

    boolean isPrint = false;

    public PanelButtonSimple() {

        construct();
        setBorder(BorderFactory.createEtchedBorder());
    }

    void construct() {

        setLayout(new FlowLayout());

        add(k_btnNew);
        k_btnNew.setToolTipText("Data Baru");
        k_btnNew.setMnemonic('N');
        add(k_btnUpdate);
        k_btnUpdate.setToolTipText("Ubah Data");
        k_btnUpdate.setMnemonic('E');
        add(k_btnDelete);
        k_btnDelete.setToolTipText("Hapus Data");
        k_btnDelete.setMnemonic('D');
        add(k_btnView);
        k_btnView.setToolTipText("Lihat Detail");
        k_btnView.setMnemonic('V');

    }

    public void addButtonListener(ActionListener l) {

        k_btnNew.addActionListener(l);
        k_btnUpdate.addActionListener(l);
        k_btnDelete.addActionListener(l);
        k_btnView.addActionListener(l);

    }

    public void setButtonEnable(boolean flag) {
        k_btnNew.setEnabled(flag);
        k_btnUpdate.setEnabled(flag);
        k_btnDelete.setEnabled(flag);
        k_btnView.setEnabled(flag);

    }

    public void setButtonState(JTable table) {
        if (table.getRowCount() > 0) {

        }
    }

    public void setState(short state) {
        k_state = state;
        if (state == NEW_STATE) {
            k_btnView.setEnabled(false);
            k_btnNew.setEnabled(true);
            k_btnUpdate.setEnabled(false);
            k_btnDelete.setEnabled(false);

        } else if (state == AFTER_SAVE_STATE) {
            k_btnView.setEnabled(true);
            k_btnNew.setEnabled(true);
            k_btnUpdate.setEnabled(true);
            k_btnDelete.setEnabled(true);

        } else if (state == AFTER_NEW_STATE) {
            k_btnView.setEnabled(false);
            k_btnNew.setEnabled(false);
            k_btnUpdate.setEnabled(false);
            k_btnDelete.setEnabled(false);

        } else {
            k_btnView.setEnabled(false);
            k_btnNew.setEnabled(false);
            k_btnUpdate.setEnabled(false);
            k_btnDelete.setEnabled(false);

        }
    }

}

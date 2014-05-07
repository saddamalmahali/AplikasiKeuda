/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class UserPanel extends JPanel implements ActionListener{
    MainForm k_mainFrame;
    Connection k_conn;
    long k_sessionid = -1;
    
    JButton k_addBt = new JButton("Tambah");
    JButton k_deleteBt = new JButton("Hapus");
    UserTable k_table = new UserTable();

    public UserPanel(MainForm mainFrame) {
        k_mainFrame = mainFrame;
        k_conn = mainFrame.getK_conn();
        k_sessionid = mainFrame.getK_sessionId();
    }
    
    void constructComponents(){
        JPanel btnpanel = new JPanel();
        btnpanel.add(k_addBt);
        btnpanel.add(k_deleteBt);
        
        k_addBt.addActionListener(this);
        k_deleteBt.addActionListener(this);
        
        setLayout(new BorderLayout());
        add(new JScrollPane(k_table),BorderLayout.CENTER);
        add(btnpanel, BorderLayout.SOUTH);
    }
    
    void init(){
        //Authorization Business Logic
    }
    
    public void actionPerformed(ActionEvent e){
        
    }
    
    class UserTable extends JTable{

        public UserTable() {
            UserTableModel model = new UserTableModel();
            model.addColumn("Nama");
            model.addColumn("Grup");
            model.addColumn("Keterangan");

            setModel(model);
            setRowHeight(18);

        }    
    }


    class UserTableModel extends DefaultTableModel{

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }
}


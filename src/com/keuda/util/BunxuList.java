/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class BunxuList extends JPanel {

    public DefaultListModel k_model = new DefaultListModel();
    public JList k_list = new JList(k_model);
    Vector k_v = new Vector();
    Object[] k_obj = new Object[0];
    public FilterField k_filterField = new FilterField("");
    
    public BunxuList() {
        constructComponent();
    }

    public void addElement(Object obj) {
        k_model.addElement(obj);
        k_v.addElement(obj);
    }

    public void setElementAt(Object obj, int index) {
        k_model.setElementAt(obj, index);
        k_v.setElementAt(obj, index);
    }

    public void addElements(Object[] objs) {
        k_obj = new Object[objs.length];
        k_obj = objs;
        for (int i = 0; i < objs.length; i++) {
            addElement(objs[i]);
        }
    }

    public void clearSelection() {
        k_list.clearSelection();
    }

    public int getSelectedIndex() {
        return k_list.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        k_list.setSelectedIndex(index);

    }

    public int[] getSelectedIndices() {
        return k_list.getSelectedIndices();
    }

    public Object getSelectedValue() {
        return k_list.getSelectedValue();
    }

    public Object[] getSelectedValues() {
        return k_list.getSelectedValues();
    }

    public void insertElementAt(Object obj, int index) {
        k_model.insertElementAt(obj, index);
    }

    public void addListDataListener(ListDataListener l) {
        k_model.addListDataListener(l);
    }

    public void addListSelectionListener(ListSelectionListener l) {
        k_list.addListSelectionListener(l);
    }

    public void copyInto(Object[] anArray) {
        k_model.copyInto(anArray);
    }

    public boolean contains(Object elem) {
        return k_model.contains(elem);
    }

    public boolean isEmpty() {
        return k_model.isEmpty();
    }

    public void removeRange(int fromIndex, int toIndex) {
        k_model.removeRange(fromIndex, toIndex);
    }

    public void removeAllElements() {
        k_obj = new Object[0];
        k_v.clear();
        k_model.removeAllElements();
    }

    public void removeElementAt(int index) {
        k_model.removeElementAt(index);
    }

    public int listSize() {
        return k_model.size();
    }

    public void clear() {
        k_model.clear();
    }

    public void removeElement(Object obj) {
        k_model.removeElement(obj);
    }

    public FilterField getfilterField() {
        return k_filterField;
    }

    public void clearTextSearch() {
        k_filterField.requestFocus();
        k_filterField.setText("");
    }

    public JList getList() {
        return k_list;
    }

    public DefaultListModel getListModel() {
        return k_model;
    }

    public void setEnableComponent(boolean enable) {
        k_filterField.setEnabled(enable);
        k_list.setEnabled(enable);
    }

    private void search() {
        String term = getfilterField().getText();
        if (!term.trim().equals("")) {
            if (k_obj.length == 0) {
                k_obj = new Object[k_v.size()];
                k_v.copyInto(k_obj);
            }
            k_model.removeAllElements();

            for (int j = 0; j < k_obj.length; j++) {
                Object obj = k_obj[j];
                String item = obj.toString();
                if (item.toUpperCase().indexOf(term.toUpperCase(), 0) != -1) {
                    k_model.addElement(obj);
                }

                if (k_model.size() > 0) {
                    k_list.setSelectedIndex(0);
                }

            }
        } else {
            k_model.removeAllElements();
            for (int j = 0; j < k_obj.length; j++) {
                Object obj = k_obj[j];
                String item = obj.toString();
                if (item.toUpperCase().indexOf(term.toUpperCase(), 0) != -1) {
                    k_model.addElement(obj);
                }
                if (k_model.size() > 0) {
                    k_list.setSelectedIndex(0);
                }
            }
        }
    }

    private void constructComponent() {
        setLayout(new BorderLayout());
        add(k_filterField, "North");
        add(new JScrollPane(k_list), "Center");
    }

    class FilterField extends JTextField implements DocumentListener, KeyListener {

        public FilterField(String text) {
            super(text);
            getDocument().addDocumentListener(this);
            addKeyListener(this);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                setVisible(false);
            }
        }
    }
}

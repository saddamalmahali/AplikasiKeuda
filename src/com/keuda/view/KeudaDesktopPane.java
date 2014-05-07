/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JDesktopPane;
import pohaci.gumunda.util.DefaultPreferences;

/**
 *
 * @author user
 */
public class KeudaDesktopPane extends JDesktopPane{
    String title = "Sistem Informasi Manajemen";
    
    public String m_companyType = "", m_companyName = "", m_company = "";

    public KeudaDesktopPane(String title) {
        this.title = title;
        
        try {
            DefaultPreferences prop = new DefaultPreferences("keuda", null);
            prop.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int w = getWidth();
        int h = getHeight();
        
        int per = w/5;
        
        int x = w-per;
        
    }
    
    
}

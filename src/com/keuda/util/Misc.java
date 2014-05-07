/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.GregorianCalendar;
import javax.swing.JComponent;

/**
 *
 * @author user
 */
public class Misc {

    public Misc() {
    }
    
    public static void setGridBagConstraints(JComponent container, JComponent component, GridBagConstraints gridBagConstraints,
            int gridx, int gridy, int fill, int gridWidth, int gridHeight, 
            double weightx, double weighty, Insets insets){
        
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        
        if(fill > -1)
            gridBagConstraints.fill = fill;
        
        gridBagConstraints.gridwidth = gridWidth;
        gridBagConstraints.gridheight = gridHeight;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        
        if(insets != null)
            gridBagConstraints.insets = insets;
        
        container.add(component, gridBagConstraints);
        
    }
    
}

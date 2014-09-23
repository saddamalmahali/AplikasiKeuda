/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.util;

import java.awt.Color;

/**
 *
 * @author adam
 */
public class RowColor {
    Color k_foreground = Color.BLACK, k_back = null;
    
    int k_row = -1;
    boolean k_bold = false;

    public RowColor() {
    }
    
    public RowColor(Color foreground, int row){
        k_foreground = foreground;
        k_row = row;
        k_back = null;
    }
    
    public RowColor(Color foreground, int row, boolean isBold){
        k_foreground = foreground;
        k_row = row;
        k_bold = isBold;
        k_back = null;
    }
    
    public RowColor(int row, boolean isBold, Color background, Color foreground){
        k_row = row;
        k_bold = isBold;
        k_back = background;
        k_foreground = foreground;
    }

    public Color getForeground() {
        return k_foreground;
    }

    public Color getBack() {
        return k_back;
    }

    public int getRow() {
        return k_row;
    }

    public boolean isBold() {
        return k_bold;
    }
    
    
    
}

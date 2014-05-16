/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.model;

/**
 *
 * @author client
 */
public class Dipa {
    private long dipaindex;
    private int thnanggaran;
    private String nomordipa;
    
    public static final long VIEW_DIPA = 0;
    public static final long VIEW_NOMOR_DIPA = 1;
    
    long view = -1;
    
    
    public Dipa() {
    }

    public Dipa(long dipaindex, int thnanggaran, String nomordipa) {
        this.dipaindex = dipaindex;
        this.thnanggaran = thnanggaran;
        this.nomordipa = nomordipa;
    }

    public Dipa(int thnanggaran, String nomordipa) {
        this.thnanggaran = thnanggaran;
        this.nomordipa = nomordipa;
    }
    
    
    
    public long getDipaindex() {
        return dipaindex;
    }

    public void setDipaindex(long dipaindex) {
        this.dipaindex = dipaindex;
    }

    public int getThnanggaran() {
        return thnanggaran;
    }

    public void setThnanggaran(int thnanggaran) {
        this.thnanggaran = thnanggaran;
    }

    public String getNomordipa() {
        return nomordipa;
    }

    public void setNomordipa(String nomordipa) {
        this.nomordipa = nomordipa;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view ==  VIEW_DIPA)
            return thnanggaran +"  "+ nomordipa;
        else if(view == VIEW_NOMOR_DIPA)
            return nomordipa;
        else
            return ""+dipaindex;
    }
    
    
    
    
}

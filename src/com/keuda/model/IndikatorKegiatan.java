/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class IndikatorKegiatan implements Serializable{
    private long kegiatandipa;
    private int nrindikator;
    private String indikator;
    private KegiatanDipa k_kedip;
    private String nr = "";
    private long view = -1;
    
    public static final long VIEW_KEGIATAN_DIPA = 0;
    public static final long VIEW_PROGRAM_CODE = 1;
    public static final long VIEW_NR_AND_OUTPUT = 2;

    public IndikatorKegiatan() {
    
    }
    
    
    
    public IndikatorKegiatan(KegiatanDipa kedip, int nrindikator, String indikator) {
        k_kedip = kedip;
        kegiatandipa = k_kedip.getDipaindex();
        this.nrindikator = nrindikator;
        this.indikator = indikator;
    }
    
    public IndikatorKegiatan(long kegiatandipa, int nrindikator, String indikator){
        this.indikator = indikator;
        this.nrindikator = nrindikator;
        this.kegiatandipa = kegiatandipa;
    }

    public String getIndikator() {
        return indikator;
    }

    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    public KegiatanDipa getK_kedip() {
        return k_kedip;
    }

    public void setK_kedip(KegiatanDipa k_kedip) {
        this.k_kedip = k_kedip;
    }

    public long getKegiatandipa() {
        return kegiatandipa;
    }

    public void setKegiatandipa(long kegiatandipa) {
        this.kegiatandipa = kegiatandipa;
    }

    public int getNrindikator() {
        return nrindikator;
    }

    public void setNrindikator(int nrindikator) {
        this.nrindikator = nrindikator;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_KEGIATAN_DIPA)
            return ""+k_kedip.getK_kegiatan().getKegiatanCode();
        else if(view == VIEW_PROGRAM_CODE)
            return ""+k_kedip.getProgramDipa().getProgram().programCode;
        else if(view == VIEW_NR_AND_OUTPUT){
            if(nrindikator < 10 && nrindikator > 0){
                nr = "00"+nrindikator;
            }else if(nrindikator >=10 && nrindikator < 100){
                nr= "0"+nrindikator;
            }else{
                nr = ""+nrindikator;
            }
            return nr + "  "+indikator;
        }
        else            
            return null;
    }
    
    
    
}

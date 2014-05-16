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
public class Rincian {
    private long rincianindex;
    private long dipaindex;
    private long kegiatan;
    private KegiatanDipa k_kedip;
    private Dipa k_dipa;
    
    long view = -1;
    
    public static final long VIEW_NOMORDIPA_KODEKEGIATAN = 0;
    public static final long VIEW_NOMORDIPA_NAMAKEGIATAN = 1;
    public static final long VIEW_NOMORDIPA_TAHUN_NAMAKEGIATAN = 2;

    public Rincian() {
    
    }
    
    
    
    public Rincian(long rincianindex, KegiatanDipa kedip, Dipa dipa) {
        this.rincianindex = rincianindex;
        this.dipaindex = dipa.getDipaindex();
        this.kegiatan = kedip.getKegiatandipaindex();
        this.k_kedip = kedip;
        this.k_dipa = dipa;
    }

    public Rincian(KegiatanDipa kedip, Dipa dipa) {
        this.dipaindex = dipa.getDipaindex();
        this.kegiatan = kedip.getKegiatandipaindex();
        this.k_kedip = kedip;
        this.k_dipa = dipa;
    }
    
    
    public long getRincianindex() {
        return rincianindex;
    }

    public void setRincianindex(long rincianindex) {
        this.rincianindex = rincianindex;
    }

    public long getDipaindex() {
        return dipaindex;
    }

    public void setDipaindex(long dipaindex) {
        this.dipaindex = dipaindex;
    }

    public long getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(long kegiatan) {
        this.kegiatan = kegiatan;
    }

    public KegiatanDipa getK_kedip() {
        return k_kedip;
    }

    public void setK_kedip(KegiatanDipa k_kedip) {
        this.k_kedip = k_kedip;
    }

    public Dipa getK_dipa() {
        return k_dipa;
    }

    public void setK_dipa(Dipa k_dipa) {
        this.k_dipa = k_dipa;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_NOMORDIPA_KODEKEGIATAN)
            return getK_dipa().getNomordipa()+"  "+getK_kedip().getK_kegiatan().getKegiatanCode();
        else if(view == VIEW_NOMORDIPA_NAMAKEGIATAN)
            return getK_dipa().getNomordipa()+"  "+getK_kedip().getK_kegiatan().getKegiatanName();
        else if(view == VIEW_NOMORDIPA_TAHUN_NAMAKEGIATAN)
            return getK_dipa().getNomordipa()+"  "+getK_dipa().getThnanggaran()+"  "+getK_kedip().getK_kegiatan().getKegiatanName();
        else return ""+rincianindex;
    }
    
    
    
}

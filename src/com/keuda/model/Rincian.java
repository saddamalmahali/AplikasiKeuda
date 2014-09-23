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
    private long subkomponen;
    private String koderekening;
    private String namarekening;
    
    private SubKomponen k_subkomponen;
    private Dipa k_dipa;
    
    long view = -1;
    
    public static final long VIEW_NOMORDIPA_KODEKEGIATAN = 0;
    public static final long VIEW_NOMORDIPA_NAMAKEGIATAN = 1;
    public static final long VIEW_NOMORDIPA_TAHUN_NAMAKEGIATAN = 2;
    public static final long VIEW_NOMORDIPA = 3;
    DetailRincian[] detailrincian;
    public Rincian() {
    
    }
    
    
    
    public Rincian(long rincianindex, SubKomponen subkomponen, Dipa dipa) {
        this.rincianindex = rincianindex;
        this.dipaindex = dipa.getDipaindex();
        this.subkomponen = subkomponen.getSubkomponenindex();
        this.k_subkomponen = subkomponen;
        this.k_dipa = dipa;
    }
    
    public Rincian(long rincianindex, SubKomponen subkomponen, Dipa dipa, String koderekening, String namarekening) {
        this.rincianindex = rincianindex;
        this.dipaindex = dipa.getDipaindex();
        this.subkomponen = subkomponen.getSubkomponenindex();
        this.k_subkomponen = subkomponen;
        this.k_dipa = dipa;
        this.koderekening = koderekening;
        this.namarekening = namarekening;
    }
    
    public Rincian(long rincianindex, SubKomponen subkomponen, Dipa dipa, String koderekening, String namarekening, DetailRincian[] detailrincian) {
        this.rincianindex = rincianindex;
        this.dipaindex = dipa.getDipaindex();
        this.subkomponen = subkomponen.getSubkomponenindex();
        this.k_subkomponen = subkomponen;
        this.k_dipa = dipa;
        this.koderekening = koderekening;
        this.namarekening = namarekening;
        this.detailrincian = detailrincian;
    }
    
    public Rincian( SubKomponen subkomponen, Dipa dipa, String koderekening, String namarekening) {
        
        this.dipaindex = dipa.getDipaindex();
        this.subkomponen = subkomponen.getSubkomponenindex();
        this.k_subkomponen = subkomponen;
        this.k_dipa = dipa;
        this.koderekening = koderekening;
        this.namarekening = namarekening;
    }

    public Rincian(SubKomponen subkomponen, Dipa dipa) {
        this.dipaindex = dipa.getDipaindex();
        this.subkomponen = subkomponen.getSubkomponenindex();
        this.k_subkomponen = subkomponen;
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

    public long getSubkomponen() {
        return subkomponen;
    }

    public void setSubkomponen(long subkomponen) {
        this.subkomponen = subkomponen;
    }

    public SubKomponen getK_subkomponen() {
        return k_subkomponen;
    }

    public void setK_subkomponen(SubKomponen k_subkomponen) {
        this.k_subkomponen = k_subkomponen;
    }

    public String getKoderekening() {
        return koderekening;
    }

    public void setKoderekening(String koderekening) {
        this.koderekening = koderekening;
    }

    public String getNamarekening() {
        return namarekening;
    }

    public void setNamarekening(String namarekening) {
        this.namarekening = namarekening;
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

    public void setDetailrincian(DetailRincian[] detailrincian) {
        this.detailrincian = detailrincian;
    }

    public DetailRincian[] getDetailrincian() {
        return detailrincian;
    }
    
    

    @Override
    public String toString() {
        if(view == VIEW_NOMORDIPA){
            return getK_dipa().getNomordipa();
        }else if(view == VIEW_NOMORDIPA_KODEKEGIATAN){
            return getKoderekening();
        }else{
            return super.toString();
        }
    }
    
    
    
}

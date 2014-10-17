package com.keuda.model;

public class Fungsi {
    
    long idfungsi;
    String kodefungsi;
    String namafungsi;
    
    SubFungsi[] subfungsi;
    
    public Fungsi(long idfungsi, String kodefungsi, String namafungsi) {
        this.idfungsi = idfungsi;
        this.kodefungsi = kodefungsi;
        this.namafungsi = namafungsi;
    }    
    
    public Fungsi(String kodefungsi, String namafungsi) {
        
        this.kodefungsi = kodefungsi;
        this.namafungsi = namafungsi;
    }

    public Fungsi() {
    }

    public long getIdfungsi() {
        return idfungsi;
    }

    public String getKodefungsi() {
        return kodefungsi;
    }

    public String getNamafungsi() {
        return namafungsi;
    }

    public void setIdfungsi(long idfungsi) {
        this.idfungsi = idfungsi;
    }

    public void setKodefungsi(String kodefungsi) {
        this.kodefungsi = kodefungsi;
    }

    public void setNamafungsi(String namafungsi) {
        this.namafungsi = namafungsi;
    }

    public void setSubfungsi(SubFungsi[] subfungsi) {
        this.subfungsi = subfungsi;
    }

    public SubFungsi[] getSubfungsi() {
        return subfungsi;
    }    
    

    @Override
    public String toString() {
        return kodefungsi+"   "+namafungsi; 
    }
    
    
    
}

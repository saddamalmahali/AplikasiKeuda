package com.keuda.model;

public class SubFungsi {
    long idsubfungsi;
    long idfungsi;
    String kodesubfungsi;
    String namasubfungsi;
    
    Fungsi fungsi;

    public SubFungsi() {
    }
    
    public SubFungsi(Fungsi fungsi, String kodesubfungsi, String namasubfungsi){
        this.fungsi = fungsi;
        this.idfungsi = fungsi.getIdfungsi();
        this.kodesubfungsi = kodesubfungsi;
        this.namasubfungsi = namasubfungsi;
    }
    public SubFungsi(long idsubfungsi, Fungsi fungsi, String kodesubfungsi, String namasubfungsi){
        this.idsubfungsi = idsubfungsi;
        this.fungsi = fungsi;
        this.idfungsi = fungsi.getIdfungsi();
        this.kodesubfungsi = kodesubfungsi;
        this.namasubfungsi = namasubfungsi;
    }

    public Fungsi getFungsi() {
        return fungsi;
    }

    public long getIdfungsi() {
        return idfungsi;
    }

    public long getIdsubfungsi() {
        return idsubfungsi;
    }

    public String getKodesubfungsi() {
        return kodesubfungsi;
    }

    public String getNamasubfungsi() {
        return namasubfungsi;
    }

    public void setFungsi(Fungsi fungsi) {
        this.fungsi = fungsi;
    }

    public void setIdfungsi(long idfungsi) {
        this.idfungsi = idfungsi;
    }

    public void setIdsubfungsi(long idsubfungsi) {
        this.idsubfungsi = idsubfungsi;
    }

    public void setKodesubfungsi(String kodesubfungsi) {
        this.kodesubfungsi = kodesubfungsi;
    }

    public void setNamasubfungsi(String namasubfungsi) {
        this.namasubfungsi = namasubfungsi;
    }

    @Override
    public String toString() {
        return kodesubfungsi+" "+namasubfungsi; 
    }
    
    
}

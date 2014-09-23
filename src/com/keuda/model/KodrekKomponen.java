package com.keuda.model;


public class KodrekKomponen {
    private long rinciandipakodrek;
    private long komponen;
    private RincianDipaKodrek k_rinciandipakodrek;
    private Komponen k_komponen;

    public KodrekKomponen() {
        
    }

    public long getRinciandipakodrek() {
        return rinciandipakodrek;
    }

    public void setRinciandipakodrek(long rinciandipakodrek) {
        this.rinciandipakodrek = rinciandipakodrek;
    }

    public long getKomponen() {
        return komponen;
    }

    public void setKomponen(long komponen) {
        this.komponen = komponen;
    }

    public RincianDipaKodrek getK_rinciandipakodrek() {
        return k_rinciandipakodrek;
    }

    public void setK_rinciandipakodrek(RincianDipaKodrek k_rinciandipakodrek) {
        this.k_rinciandipakodrek = k_rinciandipakodrek;
    }

    public Komponen getK_komponen() {
        return k_komponen;
    }

    public void setK_komponen(Komponen k_komponen) {
        this.k_komponen = k_komponen;
    }
    
    
}

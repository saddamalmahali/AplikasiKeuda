
package com.keuda.model;


public class KodrekSubKomponen {
    
    private long rinciandipakodrek;
    private long subkomponen;
    private RincianDipaKodrek k_rinciandipakodrek;
    private SubKomponen k_subkomponen;

    public KodrekSubKomponen() {
    }

    public KodrekSubKomponen(long rinciandipakodrek, long subkomponen, RincianDipaKodrek k_rinciandipakodrek, SubKomponen k_subkomponen) {
        this.rinciandipakodrek = rinciandipakodrek;
        this.subkomponen = subkomponen;
        this.k_rinciandipakodrek = k_rinciandipakodrek;
        this.k_subkomponen = k_subkomponen;
    }

    public KodrekSubKomponen(long subkomponen, RincianDipaKodrek k_rinciandipakodrek, SubKomponen k_subkomponen) {
        this.subkomponen = subkomponen;
        this.k_rinciandipakodrek = k_rinciandipakodrek;
        this.k_subkomponen = k_subkomponen;
    }
    
    public long getRinciandipakodrek() {
        return rinciandipakodrek;
    }

    public void setRinciandipakodrek(long rinciandipakodrek) {
        this.rinciandipakodrek = rinciandipakodrek;
    }

    public long getSubkomponen() {
        return subkomponen;
    }

    public void setSubkomponen(long subkomponen) {
        this.subkomponen = subkomponen;
    }

    public RincianDipaKodrek getK_rinciandipakodrek() {
        return k_rinciandipakodrek;
    }

    public void setK_rinciandipakodrek(RincianDipaKodrek k_rinciandipakodrek) {
        this.k_rinciandipakodrek = k_rinciandipakodrek;
    }
    
    
}

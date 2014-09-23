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
public class KodrekRincian {
    private long kodrekrincianindex;
    private long kodrek;
    private String rincianitem;
    private double nilaiitem;
    private RincianDipaKodrek rinciandipakodrek;
    
    private long view = -1;
    
    public static final long VIEW_COMPLETE_KODREK_RINCIAN = 0;
    
    public KodrekRincian(long kodrekrincianindex, RincianDipaKodrek rinciandipakodrek, String rincianitem, double nilaiitem) {
        this.kodrekrincianindex = kodrekrincianindex;
        this.kodrek = rinciandipakodrek.getRinciapdipakodrekindex();
        this.rincianitem = rincianitem;
        this.nilaiitem = nilaiitem;
    }

    public KodrekRincian(RincianDipaKodrek rinciandipakodrek, String rincianitem, double nilaiitem) {
        this.rinciandipakodrek = rinciandipakodrek;
        this.kodrek = rinciandipakodrek.getRinciapdipakodrekindex();
        this.rincianitem = rincianitem;
        this.nilaiitem = nilaiitem;    
    }

    public KodrekRincian(long kodrek, String rincianitem, double nilaiitem) {
        this.kodrek = kodrek;
        this.rincianitem = rincianitem;
        this.nilaiitem = nilaiitem;
    }
    
    
    
    public KodrekRincian() {
        
    }
    
    public long getKodrekrincianindex() {
        return kodrekrincianindex;
    }

    public void setKodrekrincianindex(long kodrekrincianindex) {
        this.kodrekrincianindex = kodrekrincianindex;
    }

    public long getKodrek() {
        return kodrek;
    }

    public void setKodrek(long kodrek) {
        this.kodrek = kodrek;
    }

    public String getRincianitem() {
        return rincianitem;
    }

    public void setRincianitem(String rincianitem) {
        this.rincianitem = rincianitem;
    }

    public double getNilaiitem() {
        return nilaiitem;
    }

    public void setNilaiitem(double nilaiitem) {
        this.nilaiitem = nilaiitem;
    }

    public RincianDipaKodrek getRinciandipakodrek() {
        return rinciandipakodrek;
    }

    public void setRinciandipakodrek(RincianDipaKodrek rinciandipakodrek) {
        this.rinciandipakodrek = rinciandipakodrek;
    }

    @Override
    public String toString() {
        return rinciandipakodrek.getNamarekening()+" " + rincianitem+" " + nilaiitem;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.model;

/**
 *
 * @author BENDAHARA
 */
public class Satker {
    long satkerid;
    String kodedepartemen;
    String namadepartemen;
    String kodeunitdepartemen;
    String unitdepartemen;    
    String kodesatker;
    String namasatker;

    public Satker(long satkerid, String kodedepartemen, String namadepartemen, String kodeunitdepartemen, String unitdepartemen, String kodesatker, String namasatker) {
        this.satkerid = satkerid;
        this.kodedepartemen = kodedepartemen;
        this.namadepartemen = namadepartemen;
        this.kodeunitdepartemen = kodeunitdepartemen;
        this.unitdepartemen = unitdepartemen;
        this.kodesatker = kodesatker;
        this.namasatker = namasatker;
    }
    
    public Satker(String kodedepartemen, String namadepartemen, String kodeunitdepartemen, String unitdepartemen, String kodesatker, String namasatker) {
        this.satkerid = satkerid;
        this.kodedepartemen = kodedepartemen;
        this.namadepartemen = namadepartemen;
        this.kodeunitdepartemen = kodeunitdepartemen;
        this.unitdepartemen = unitdepartemen;
        this.kodesatker = kodesatker;
        this.namasatker = namasatker;
    }

    public long getSatkerid() {
        return satkerid;
    }

    public void setSatkerid(long satkerid) {
        this.satkerid = satkerid;
    }

    public String getKodedepartemen() {
        return kodedepartemen;
    }

    public void setKodedepartemen(String kodedepartemen) {
        this.kodedepartemen = kodedepartemen;
    }

    public String getNamadepartemen() {
        return namadepartemen;
    }

    public void setNamadepartemen(String namadepartemen) {
        this.namadepartemen = namadepartemen;
    }

    public String getKodeunitdepartemen() {
        return kodeunitdepartemen;
    }

    public void setKodeunitdepartemen(String kodeunitdepartemen) {
        this.kodeunitdepartemen = kodeunitdepartemen;
    }

    public String getUnitdepartemen() {
        return unitdepartemen;
    }

    public void setUnitdepartemen(String unitdepartemen) {
        this.unitdepartemen = unitdepartemen;
    }

    

    

    public String getKodesatker() {
        return kodesatker;
    }

    public void setKodesatker(String kodesatker) {
        this.kodesatker = kodesatker;
    }

    public String getNamasatker() {
        return namasatker;
    }

    public void setNamasatker(String namasatker) {
        this.namasatker = namasatker;
    }
    
}

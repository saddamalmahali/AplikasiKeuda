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
public class FungsiSPM {
    String kdfungsi;
    String nmfungsi;

    public FungsiSPM() {
    }

    public FungsiSPM(String kdfungsi, String nmfungsi) {
        this.kdfungsi = kdfungsi;
        this.nmfungsi = nmfungsi;
    }

    public String getKdfungsi() {
        return kdfungsi;
    }

    public String getNmfungsi() {
        return nmfungsi;
    }

    public void setKdfungsi(String kdfungsi) {
        this.kdfungsi = kdfungsi;
    }

    public void setNmfungsi(String nmfungsi) {
        this.nmfungsi = nmfungsi;
    }
    
    
}

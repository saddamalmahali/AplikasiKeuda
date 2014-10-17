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
public class SubFungsiSPM {
    String kdfungsi;
    String kdsfung;
    String nmsfung;

    public SubFungsiSPM() {
    }

    public SubFungsiSPM(String kdfungsi, String kdsfung, String nmsfung) {
        this.kdfungsi = kdfungsi;
        this.kdsfung = kdsfung;
        this.nmsfung = nmsfung;
    }

    public String getKdfungsi() {
        return kdfungsi;
    }

    public String getKdsfung() {
        return kdsfung;
    }

    public String getNmsfung() {
        return nmsfung;
    }

    public void setKdfungsi(String kdfungsi) {
        this.kdfungsi = kdfungsi;
    }

    public void setKdsfung(String kdsfung) {
        this.kdsfung = kdsfung;
    }

    public void setNmsfung(String nmsfung) {
        this.nmsfung = nmsfung;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.model;

import java.util.Date;

/**
 *
 * @author BENDAHARA
 */
public class Bendahara {
    
    long bendaharaindex;
    long satkerid;
    String jenisbendahara;
    String nip;
    String alamat;
    String email;
    String kodebank;
    String rekening;
    double saldo;
    String npwp;
    Date tanggal;
    
    Satker satker;
    
    public Bendahara() {
    }

    public Bendahara(long bendaharaindex, Satker satker, String jenisbendahara, String nip, String alamat, String email, String kodebank, String rekening, double saldo, String npwp, Date tanggal) {
        this.bendaharaindex = bendaharaindex;
        this.satkerid = satker.getSatkerid();
        this.jenisbendahara = jenisbendahara;
        this.nip = nip;
        this.alamat = alamat;
        this.email = email;
        this.kodebank = kodebank;
        this.rekening = rekening;
        this.saldo = saldo;
        this.npwp = npwp;
        this.tanggal = tanggal;
        this.satker = satker;
        this.satker = satker;
    }
    

    public long getBendaharaindex() {
        return bendaharaindex;
    }

    public void setBendaharaindex(long bendaharaindex) {
        this.bendaharaindex = bendaharaindex;
    }

    public String getJenisbendahara() {
        return jenisbendahara;
    }

    public void setJenisbendahara(String jenisbendahara) {
        this.jenisbendahara = jenisbendahara;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getKodebank() {
        return kodebank;
    }

    public void setKodebank(String kodebank) {
        this.kodebank = kodebank;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }    

    public long getSatkerid() {
        return satkerid;
    }

    public void setSatkerid(long satkerid) {
        this.satkerid = satkerid;
    }

    public Satker getSatker() {
        return satker;
    }

    public void setSatker(Satker satker) {
        this.satker = satker;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }
    
    
    
}

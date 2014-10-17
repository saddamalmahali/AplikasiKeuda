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
    long bankid;
    long satkerid;
    String nama;
    String jenisbendahara;
    String nip;
    String alamat;
    String email;
    String kodebank;
    String rekening;
    String kodebendahara;
    double saldo;
    String npwp;
    Date tanggal;
    
    Satker satker;
    BankRef bank;
    
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
    
    public Bendahara(long bendaharaindex, BankRef bank, Satker satker, String kodebendahara, String nama, String jenisbendahara, String nip, String alamat, String email, String kodebank, String rekening, double saldo, String npwp, Date tanggal) {
        this.bendaharaindex = bendaharaindex;
        this.bank = bank;
        this.bankid = bank.getId();
        this.satkerid = satker.getSatkerid();
        this.kodebendahara = kodebendahara;
        this.nama = nama;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public BankRef getBank() {
        return bank;
    }

    public long getBankid() {
        return bankid;
    }

    public void setBank(BankRef bank) {
        this.bank = bank;
    }

    public void setBankid(long bankid) {
        this.bankid = bankid;
    }

    public String getKodebendahara() {
        return kodebendahara;
    }

    public void setKodebendahara(String kodebendahara) {
        this.kodebendahara = kodebendahara;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

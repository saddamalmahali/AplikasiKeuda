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
public class SPP {
    
    long sppid;
    long outputkegiatanid;
    long satkerid;
    long bendaharaid;
    long bankid;
    String lokasi;
    String kewenangan;
    String jenisbelanja;
    String atasnama;
    String alamat;
    String koderekening;
    String nospk;
    double jumlahspk;
    String keterangan;
    double jumlah;
    
    Date tanggalspp;
    Date tanggalvalid;
    
    
    OutputKegiatan outputkegiatan;
    BankRef bank;
    Satker satker;
    Bendahara bendahara;
    public SPP() {
        
    }

    public SPP(long sppid, long outputkegiatan, long satkerid, String lokasi, String kewenangan, String jenisbelanja, String atasnama, String alamat, String koderekening, String nospk, double jumlahspk, String keterangan, double jumlah) {
        this.sppid = sppid;
        this.outputkegiatanid = outputkegiatan;
        this.satkerid = satkerid;
        this.lokasi = lokasi;
        this.kewenangan = kewenangan;
        this.jenisbelanja = jenisbelanja;
        this.atasnama = atasnama;
        this.alamat = alamat;
        this.koderekening = koderekening;
        this.nospk = nospk;
        this.jumlahspk = jumlahspk;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }

    public SPP(long outputkegiatan, long satkerid, String lokasi, String kewenangan, String jenisbelanja, String atasnama, String alamat, String koderekening, String nospk, double jumlahspk, String keterangan, double jumlah) {
        this.outputkegiatanid = outputkegiatan;
        this.satkerid = satkerid;
        
        this.lokasi = lokasi;
        this.kewenangan = kewenangan;
        this.jenisbelanja = jenisbelanja;
        this.atasnama = atasnama;
        this.alamat = alamat;
        this.koderekening = koderekening;
        this.nospk = nospk;
        this.jumlahspk = jumlahspk;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }

    
    public SPP(OutputKegiatan outputkegiatan, Satker satker, Bendahara bendahara, String lokasi, String kewenangan, String jenisbelanja, String atasnama, String alamat, String koderekening, String nospk, double jumlahspk, String keterangan, double jumlah) {
        this.outputkegiatanid = outputkegiatan.getOutputkegiatanindex();
        this.outputkegiatan = outputkegiatan;
        this.bendahara  = bendahara;
        this.bendaharaid = bendahara.getBendaharaindex();
        this.satkerid = satker.getSatkerid();
        this.satker = satker;
        this.lokasi = lokasi;
        this.kewenangan = kewenangan;
        this.jenisbelanja = jenisbelanja;
        this.atasnama = atasnama;
        this.alamat = alamat;
        this.koderekening = koderekening;
        this.nospk = nospk;
        this.jumlahspk = jumlahspk;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }
    
    public SPP(OutputKegiatan outputkegiatan, Satker satker, Bendahara bendahara, BankRef bank, 
            String lokasi, String kewenangan, String jenisbelanja, String atasnama, 
            String alamat, String koderekening,String nospk, double jumlahspk, String keterangan, double jumlah, 
            Date tanggalspp, Date tanggalvalid) {
        this.outputkegiatanid = outputkegiatan.getOutputkegiatanindex();
        this.outputkegiatan = outputkegiatan;
        this.bendahara  = bendahara;
        this.bendaharaid = bendahara.getBendaharaindex();
        this.satkerid = satker.getSatkerid();
        this.satker = satker;
        this.lokasi = lokasi;
        this.kewenangan = kewenangan;
        this.jenisbelanja = jenisbelanja;
        this.atasnama = atasnama;
        this.alamat = alamat;
        this.koderekening = koderekening;
        
        
        this.nospk = nospk;
        this.jumlahspk = jumlahspk;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
        this.tanggalspp = tanggalspp;
        this.tanggalvalid = tanggalvalid;
    }
    
    public long getSppid() {
        return sppid;
    }

    public void setSppid(long sppid) {
        this.sppid = sppid;
    }

    public long getOutputkegiatanId() {
        return outputkegiatanid;
    }

    public void setOutputkegiatanId(long outputkegiatan) {
        this.outputkegiatanid = outputkegiatan;
    }

    public long getSatkerId() {
        return satkerid;
    }

    public void setSatkerId(long satkerid) {
        this.satkerid = satkerid;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKewenangan() {
        return kewenangan;
    }

    public void setKewenangan(String kewenangan) {
        this.kewenangan = kewenangan;
    }

    public String getJenisbelanja() {
        return jenisbelanja;
    }

    public void setJenisbelanja(String jenisbelanja) {
        this.jenisbelanja = jenisbelanja;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public void setAtasnama(String atasnama) {
        this.atasnama = atasnama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKoderekening() {
        return koderekening;
    }

    public void setKoderekening(String koderekening) {
        this.koderekening = koderekening;
    }

    public String getNospk() {
        return nospk;
    }

    public void setNospk(String nospk) {
        this.nospk = nospk;
    }

    public double getJumlahspk() {
        return jumlahspk;
    }

    public void setJumlahspk(double jumlahspk) {
        this.jumlahspk = jumlahspk;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public OutputKegiatan getOutputkegiatan() {
        return outputkegiatan;
    }

    public void setOutputkegiatan(OutputKegiatan outputkegiatan) {
        this.outputkegiatan = outputkegiatan;
    }

    public Satker getSatker() {
        return satker;
    }

    public void setSatker(Satker satker) {
        this.satker = satker;
    }

    public long getBendaharaid() {
        return bendaharaid;
    }

    public void setBendaharaid(long bendaharaid) {
        this.bendaharaid = bendaharaid;
    }

    public Bendahara getBendahara() {
        return bendahara;
    }

    public void setBendahara(Bendahara bendahara) {
        this.bendahara = bendahara;
    }    

    public long getOutputkegiatanid() {
        return outputkegiatanid;
    }

    public void setOutputkegiatanid(long outputkegiatanid) {
        this.outputkegiatanid = outputkegiatanid;
    }

    public long getSatkerid() {
        return satkerid;
    }

    public void setSatkerid(long satkerid) {
        this.satkerid = satkerid;
    }

    public Date getTanggalspp() {
        return tanggalspp;
    }

    public void setTanggalspp(Date tanggalspp) {
        this.tanggalspp = tanggalspp;
    }

    public Date getTanggalvalid() {
        return tanggalvalid;
    }

    public void setTanggalvalid(Date tanggalvalid) {
        this.tanggalvalid = tanggalvalid;
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
    
    
    
    

    @Override
    public String toString() {
        return super.toString();
    }  
    
}

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
public class RincianDipa {
    long rincianindex;
    String koderekening;
    String namarekening;
    String lokasi;
    String kppn;
    String jenisvolume;
    int volume;
    String carapenarikan;
    double jumlahdana;
    Rincian k_rincian;
    long view = -1;
    
    
    public final long VIEW_KODEREKENING_NAMAREKENING = 0;

    public RincianDipa(Rincian rincian, String koderekening, String namarekening, String lokasi,
                        String kppn, String jenisvolume, int volume,String carapemakaian, double jumlahdana) {
        this.k_rincian = rincian;
        this.rincianindex = rincian.getRincianindex();
        this.koderekening = koderekening;
        this.namarekening = namarekening;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.jenisvolume = jenisvolume;
        this.volume = volume;
        this.carapenarikan = carapemakaian;
        this.jumlahdana = jumlahdana;
    }

    
    
    public long getRincianindex() {
        return rincianindex;
    }

    public void setRincianindex(long rincianindex) {
        this.rincianindex = rincianindex;
    }

    public String getKoderekening() {
        return koderekening;
    }

    public Rincian getK_rincian() {
        return k_rincian;
    }
    
    
    
    public void setKoderekening(String koderekening) {
        this.koderekening = koderekening;
    }

    public String getNamarekening() {
        return namarekening;
    }

    public void setNamarekening(String namarekening) {
        this.namarekening = namarekening;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKppn() {
        return kppn;
    }

    public void setKppn(String kppn) {
        this.kppn = kppn;
    }

    public String getCarapenarikan() {
        return carapenarikan;
    }

    public void setCarapenarikan(String carapenarikan) {
        this.carapenarikan = carapenarikan;
    }

    public double getJumlahdana() {
        return jumlahdana;
    }

    public void setJumlahdana(double jumlahdana) {
        this.jumlahdana = jumlahdana;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_KODEREKENING_NAMAREKENING )
            return koderekening + "  "+namarekening;
        else
            return koderekening;
    }
    
    
    
}

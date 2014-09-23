/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.model;

/**
 *
 * @author adam
 */
public class SubRincianDipa {
    long subrincianindex;
    String koderekening;
    String namaRekening;
    String lokasi;
    String kppn;
    String jenisvolume;
    int volume;
    String carapenarikan;
    double jumlahdana;
    RincianDipa rinciandipa;
    long view = -1;

    public SubRincianDipa(RincianDipa rinciandipa, String koderekening, String namaRekening, String lokasi, String kppn, String jenisvolume, int volume, String carapenarikan, double jumlahdana) {
        
        this.rinciandipa = rinciandipa;
        this.koderekening = koderekening;
        this.namaRekening = namaRekening;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.jenisvolume = jenisvolume;
        this.volume = volume;
        this.carapenarikan = carapenarikan;
        this.jumlahdana = jumlahdana;
        
    }
    
    public long getSubrincianindex() {
        return subrincianindex;
    }
    
    public void setSubrincianindex(long subrincianindex) {
        this.subrincianindex = subrincianindex;
    }

    public String getKoderekening() {
        return koderekening;
    }

    public void setKoderekening(String koderekening) {
        this.koderekening = koderekening;
    }

    public String getNamaRekening() {
        return namaRekening;
    }

    public void setNamaRekening(String namaRekening) {
        this.namaRekening = namaRekening;
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

    public String getJenisvolume() {
        return jenisvolume;
    }

    public void setJenisvolume(String jenisvolume) {
        this.jenisvolume = jenisvolume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
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

    public RincianDipa getRinciandipa() {
        return rinciandipa;
    }

    public void setRinciandipa(RincianDipa rinciandipa) {
        this.rinciandipa = rinciandipa;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}

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
public class RincianDipaKodrek {
    
    private long rinciapdipakodrekindex;
    private long rincianindex;
    private String koderekening;
    private String namarekening;
    private String lokasi;
    private String kppn;
    private String carapenarikan;
    private double jumlahdana;
    
    private Rincian rincian;
    
    private long view = -1;
    
    public static final long VIEW_COMPLETE = 0;

    public RincianDipaKodrek() {
    }

    public RincianDipaKodrek(long rinciapdipakodrekindex, long rincianindex, String koderekening, String namarekening, String lokasi, String kppn, String carapenarikan, double jumlahdana, Rincian rincian) {
        this.rinciapdipakodrekindex = rinciapdipakodrekindex;
        this.rincianindex = rincianindex;
        this.koderekening = koderekening;
        this.namarekening = namarekening;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.jumlahdana = jumlahdana;
        this.rincian = rincian;
    }
    
    public RincianDipaKodrek(long rinciapdipakodrekindex, Rincian rincian, String koderekening, String namarekening, String lokasi, String kppn, String carapenarikan, double jumlahdana) {
        this.rinciapdipakodrekindex = rinciapdipakodrekindex;
        this.rincianindex = rincian.getRincianindex();
        this.rincian = rincian;
        this.koderekening = koderekening;
        this.namarekening = namarekening;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.jumlahdana = jumlahdana;
        this.rincian = rincian;
    }
    
    public long getRinciapdipakodrekindex() {
        return rinciapdipakodrekindex;
    }

    public void setRinciapdipakodrekindex(long rinciapdipakodrekindex) {
        this.rinciapdipakodrekindex = rinciapdipakodrekindex;
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

    public Rincian getRincian() {
        return rincian;
    }

    public void setRincian(Rincian rincian) {
        this.rincian = rincian;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}

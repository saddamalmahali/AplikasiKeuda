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
public class DetailRincian {
    private long detailrincianindex;
    private long rincianindex;
    private String detailrincian;
    private String lokasi;
    private String kppn;
    private String carapenarikan;
    private int volume;
    private String jenisvolume;
    private double jumlahdana;
    Rincian rincian;
    DetailRincian k_parent;
    long view = -1;
    
    public static final long VIEW_NONE = 0;
    public DetailRincian() {
    
    }

    public DetailRincian(long rincianindex, String detailrincian, String lokasi, String kppn, String carapenarikan, int volume, String jenisvolume, double jumlahdana) {
        this.rincianindex = rincianindex;
        this.detailrincian = detailrincian;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.volume = volume;
        this.jenisvolume = jenisvolume;
        this.jumlahdana = jumlahdana;
    }

    public DetailRincian(long detailrincianindex, long rincianindex, String detailrincian, String lokasi, String kppn, String carapenarikan, int volume, String jenisvolume, double jumlahdana) {
        this.detailrincianindex = detailrincianindex;
        this.rincianindex = rincianindex;
        this.detailrincian = detailrincian;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.volume = volume;
        this.jenisvolume = jenisvolume;
        this.jumlahdana = jumlahdana;
    }
    
    public DetailRincian(long detailrincianindex, Rincian rincian, String detailrincian, String lokasi, String kppn, String carapenarikan, int volume, String jenisvolume, double jumlahdana) {
        this.detailrincianindex = detailrincianindex;
        this.rincian = rincian;
        this.rincianindex = rincian.getRincianindex();
        this.detailrincian = detailrincian;
        this.lokasi = lokasi;
        
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.volume = volume;
        this.jenisvolume = jenisvolume;
        this.jumlahdana = jumlahdana;
    }
    public DetailRincian(Rincian rincian, String detailrincian, String lokasi, String kppn, String carapenarikan, int volume, String jenisvolume, double jumlahdana) {
        
        this.rincian = rincian;
        this.rincianindex = rincian.getRincianindex();
        this.detailrincian = detailrincian;
        this.lokasi = lokasi;
        
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.volume = volume;
        this.jenisvolume = jenisvolume;
        this.jumlahdana = jumlahdana;
    }
    
    public DetailRincian(Rincian rincian, String detailrincian) {
        
        this.rincian = rincian;
        this.rincianindex = rincian.getRincianindex();
        this.detailrincian = detailrincian;
        this.lokasi = "";
        
        this.kppn = "";
        this.carapenarikan = "";
        this.volume = 0;
        this.jenisvolume = "";
        this.jumlahdana = 0.0;
    }
    
    public DetailRincian(long detailrincianindex, long rincianindex, String detailrincian, String lokasi, String kppn, String carapenarikan, int volume, String jenisvolume, double jumlahdana, DetailRincian k_parent) {
        this.detailrincianindex = detailrincianindex;
        this.rincianindex = rincianindex;
        this.detailrincian = detailrincian;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.volume = volume;
        this.jenisvolume = jenisvolume;
        this.jumlahdana = jumlahdana;
        this.k_parent = k_parent;
    }
    
    
    
    public long getDetailrincianindex() {
        return detailrincianindex;
    }

    public void setDetailrincianindex(long detailrincianindex) {
        this.detailrincianindex = detailrincianindex;
    }

    public long getRincianindex() {
        return rincianindex;
    }

    public void setRincianindex(long rincianindex) {
        this.rincianindex = rincianindex;
    }

    public String getDetailrincian() {
        return detailrincian;
    }

    public void setDetailrincian(String detailrincian) {
        this.detailrincian = detailrincian;
    }

    public Rincian getRincian() {
        return rincian;
    }

    public void setRincian(Rincian rincian) {
        this.rincian = rincian;
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getJenisvolume() {
        return jenisvolume;
    }

    public void setJenisvolume(String jenisvolume) {
        this.jenisvolume = jenisvolume;
    }

    public double getJumlahdana() {
        return jumlahdana;
    }

    public void setJumlahdana(double jumlahdana) {
        this.jumlahdana = jumlahdana;
    }
    
    public DetailRincian getParent(){
        return k_parent;
    }
    
    public void setParent(DetailRincian k_parent){
        this.k_parent = k_parent;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_NONE){
            return "";
        }else{
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
}

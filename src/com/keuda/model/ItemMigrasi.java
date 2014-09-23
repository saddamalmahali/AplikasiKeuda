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
public class ItemMigrasi {
    short tahunanggaran;
    String kegiatankode;
    String outputkegiatankode;
    String komponenkode;
    String subkomponenkode;
    String kodeakun;
    String lokasi;
    String kppn;
    String carapenarikan;
    String detailrincian;
    double jumlahdana;
    int volume;
    String jenisvolume;

    public ItemMigrasi(short tahunanggaran, String kegiatankode, String outputkegiatankode, String komponenkode, String subkomponenkode, String kodeakun, String lokasi, String kppn, String carapenarikan, String detailrincian, double jumlahdana, int volume, String jenisvolume) {
        this.tahunanggaran = tahunanggaran;
        this.kegiatankode = kegiatankode;
        this.outputkegiatankode = outputkegiatankode;
        this.komponenkode = komponenkode;
        this.subkomponenkode = subkomponenkode;
        this.kodeakun = kodeakun;
        this.lokasi = lokasi;
        this.kppn = kppn;
        this.carapenarikan = carapenarikan;
        this.detailrincian = detailrincian;
        this.jumlahdana = jumlahdana;
        this.volume = volume;
        this.jenisvolume = jenisvolume;
    }   
    
    public short getTahunanggaran() {
        return tahunanggaran;
    }

    public void setTahunanggaran(short tahunanggaran) {
        this.tahunanggaran = tahunanggaran;
    }

    public String getKegiatankode() {
        return kegiatankode;
    }

    public void setKegiatankode(String kegiatankode) {
        this.kegiatankode = kegiatankode;
    }

    public String getOutputkegiatankode() {
        return outputkegiatankode;
    }

    public void setOutputkegiatankode(String outputkegiatankode) {
        this.outputkegiatankode = outputkegiatankode;
    }

    public String getKomponenkode() {
        return komponenkode;
    }

    public void setKomponenkode(String komponenkode) {
        this.komponenkode = komponenkode;
    }

    public String getSubkomponenkode() {
        return subkomponenkode;
    }

    public void setSubkomponenkode(String subkomponenkode) {
        this.subkomponenkode = subkomponenkode;
    }

    public String getKodeakun() {
        return kodeakun;
    }

    public void setKodeakun(String kodeakun) {
        this.kodeakun = kodeakun;
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

    public String getDetailrincian() {
        return detailrincian;
    }

    public void setDetailrincian(String detailrincian) {
        this.detailrincian = detailrincian;
    }

    public double getJumlahdana() {
        return jumlahdana;
    }

    public void setJumlahdana(double jumlahdana) {
        this.jumlahdana = jumlahdana;
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
    
    
}

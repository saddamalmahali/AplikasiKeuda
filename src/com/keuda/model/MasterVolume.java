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
public class MasterVolume {
    private long mastervolumeid;
    private short kodevolume;
    private String namajenis;
    private String description;
    
    public long view = -1;
    
    public static long VIEW_KODE_JENIS = 0;
    
    public MasterVolume(short kodevolume, String namajenis, String description) {
        this.kodevolume = kodevolume;
        this.namajenis = namajenis;
        this.description = description;        
    }

    public MasterVolume(long mastervolumeid, short kodevolume, String namajenis, String description) {
        this.mastervolumeid = mastervolumeid;
        this.kodevolume = kodevolume;
        this.namajenis = namajenis;
        this.description = description;
    }
    
    public MasterVolume(MasterVolume masterVolume) {
        this.mastervolumeid = masterVolume.mastervolumeid;
        this.kodevolume = masterVolume.getKodevolume();
        this.namajenis = masterVolume.getNamajenis();
        this.description = masterVolume.getDescription();
        
    }

    public MasterVolume() {
    }
    
    public long getMastervolumeid() {
        return mastervolumeid;
    }

    public void setMastervolumeid(long mastervolumeid) {
        this.mastervolumeid = mastervolumeid;
    }

    public short getKodevolume() {
        return kodevolume;
    }

    public void setKodevolume(short kodevolume) {
        this.kodevolume = kodevolume;
    }

    public String getNamajenis() {
        return namajenis;
    }

    public void setNamajenis(String namajenis) {
        this.namajenis = namajenis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setView(long view) {
        this.view = view;
    }
    
    
    
    @Override
    public String toString() {
        if(view == VIEW_KODE_JENIS)
            return ""+kodevolume;
        else return null;
    }   
    
    
    
    
    
}

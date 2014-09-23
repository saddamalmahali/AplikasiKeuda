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
public class Komponen {
    
    private long komponenindex;
    private long outputkegiatanindex;
    private String komponenkode;
    private String komponenname;
    private OutputKegiatan outke;
    
    private SubKomponen[] subkomponen;
    long view = -1;
    
    
    public static final long VIEW_CODE = 0;
    public static final long VIEW_CODE_AND_NAME = 1;
    
    public Komponen(OutputKegiatan outke, String komponenkode, String komponenname) {
        this.outke = outke;
        this.outputkegiatanindex = outke.getOutputkegiatanindex();
        this.komponenkode = komponenkode;
        this.komponenname = komponenname;
    }

    public Komponen(long komponenindex, OutputKegiatan outke, String komponenkode, String komponenname) {
        this.komponenindex = komponenindex;
        this.outke = outke;
        this.outputkegiatanindex = outke.getOutputkegiatanindex();
        this.komponenkode = komponenkode;
        this.komponenname = komponenname;
    }
    
    public Komponen(long komponenindex, OutputKegiatan outke, String komponenkode, String komponenname, SubKomponen[] subkomponen) {
        this.komponenindex = komponenindex;
        this.outke = outke;
        this.outputkegiatanindex = outke.getOutputkegiatanindex();
        this.komponenkode = komponenkode;
        this.komponenname = komponenname;
        this.subkomponen  = subkomponen;
    }

    public Komponen(long komponenindex, long outputkegiatan, String komponenkode, String komponenname) {
        this.komponenindex = komponenindex;
        this.outputkegiatanindex = outputkegiatanindex;
        this.komponenkode = komponenkode;
        this.komponenname = komponenname;
    }

    public Komponen(long outputkegiatanindex, String komponenkode, String komponennama) {
        this.outputkegiatanindex = outputkegiatanindex;
        this.komponenkode = komponenkode;
        this.komponenname = komponennama;
    }
    
    
    
    public long getKomponenindex() {
        return komponenindex;
    }

    public void setKomponenindex(long komponenindex) {
        this.komponenindex = komponenindex;
    }

    public long getOutputkegiatanindex() {
        return outputkegiatanindex;
    }

    public void setOutputkegiatanindex(long outputkegiatanindex) {
        this.outputkegiatanindex = outputkegiatanindex;
    }

    public String getKomponenkode() {
        return komponenkode;
    }

    public void setKomponenkode(String komponenkode) {
        this.komponenkode = komponenkode;
    }

    public String getKomponenname() {
        return komponenname;
    }

    public void setKomponenname(String komponenname) {
        this.komponenname = komponenname;
    }

    public OutputKegiatan getOutke() {
        return outke;
    }

    public void setOutke(OutputKegiatan outke) {
        this.outke = outke;
    }
    
    public void setView(long view){
        this.view = view;
    }

    public SubKomponen[] getSubkomponen() {
        return subkomponen;
    }

    public void setSubkomponen(SubKomponen[] subkomponen) {
        this.subkomponen = subkomponen;
    }
    
    
    
    @Override
    public String toString() {
        if(view == VIEW_CODE){
            return ""+komponenkode;
        }else if(view == VIEW_CODE_AND_NAME){
            return komponenkode+"   "+komponenname;
        }else{
            return super.toString();
        }
    }
    
}

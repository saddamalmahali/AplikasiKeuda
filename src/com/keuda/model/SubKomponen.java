

package com.keuda.model;

/**
 *
 * @author client
 */
public class SubKomponen {
    
    private long subkomponenindex;
    private long komponenindex;
    private String subkomponenkode;
    private String subkomponenname;
    private Komponen komponen;
    
    long view = -1;
    
    public static final long VIEW_KODE = 0;
    public static final long VIEW_KODE_AND_NAME = 1;
    
    Rincian[] rincian;
            
    
    public SubKomponen(Komponen komponen, String subkomponenkode, String subkomponenname) {
        this.komponen = komponen;
        this.komponenindex = komponen.getKomponenindex();
        this.subkomponenkode = subkomponenkode;
        this.subkomponenname = subkomponenname;
        
    }

    public SubKomponen(long subkomponenindex, Komponen komponen, String subkomponenkode, String subkomponenname) {
        this.subkomponenindex = subkomponenindex;
        this.komponen = komponen;
        this.komponenindex = komponen.getKomponenindex();
        this.subkomponenkode = subkomponenkode;
        this.subkomponenname = subkomponenname;
    }
    
    public SubKomponen(long subkomponenindex, Komponen komponen, String subkomponenkode, String subkomponenname, Rincian[] rincian) {
        this.subkomponenindex = subkomponenindex;
        this.komponen = komponen;
        this.komponenindex = komponen.getKomponenindex();
        this.subkomponenkode = subkomponenkode;
        this.subkomponenname = subkomponenname;
        this.rincian = rincian;
    }

    public SubKomponen(long komponenindex, String subkomponenkode, String subkomponenname) {
        this.komponenindex = komponenindex;
        this.subkomponenkode = subkomponenkode;
        this.subkomponenname = subkomponenname;
    }
    
    
    
    public long getSubkomponenindex() {
        return subkomponenindex;
    }

    public void setSubkomponenindex(long subkomponenindex) {
        this.subkomponenindex = subkomponenindex;
    }

    public long getKomponenindex() {
        return komponenindex;
    }

    public void setKomponenindex(long komponenindex) {
        this.komponenindex = komponenindex;
    }

    public String getSubkomponenkode() {
        return subkomponenkode;
    }

    public void setSubkomponenkode(String subkomponenkode) {
        this.subkomponenkode = subkomponenkode;
    }

    public String getSubkomponenname() {
        return subkomponenname;
    }

    public void setSubkomponenname(String subkomponenname) {
        this.subkomponenname = subkomponenname;
    }

    public void setView(long view) {
        this.view = view;
    }

    public Rincian[] getRincian() {
        return rincian;
    }

    public void setRincian(Rincian[] rincian) {
        this.rincian = rincian;
    }
    
    
    
    @Override
    public String toString() {
        
        if(view == VIEW_KODE){
            return subkomponenkode;
            
        }else if(view == VIEW_KODE_AND_NAME){
            return subkomponenkode+"   "+subkomponenname;
        }else{
            return subkomponenkode+"  "+subkomponenname;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author user
 */
public class GeneralObject implements Serializable{
    
    private long akunIndex;
    private String kodeAkun = "";
    private String namaAkun = "";
    
    protected short view = 0;
    
    public static final short VIEW_CODENAME = 0;
    public static final short VIEW_CODE = 1;
    public static final short VIEW_NAME = 2;

    public GeneralObject() {
    }
    
    private void readObject(ObjectInputStream ois)throws ClassNotFoundException, IOException{
        ois.defaultReadObject();
    }
    
    private void writeObject(ObjectOutputStream oos)throws IOException{
        oos.defaultWriteObject();
    }

    public GeneralObject(long akunIndex, String kodeAkun, String namaAkun) {
        this.akunIndex = akunIndex;
        this.kodeAkun = kodeAkun;
        this.namaAkun = namaAkun;
    }
    
    public GeneralObject(String kodeAkun, String namaAkun){
         this.kodeAkun = kodeAkun;
        this.namaAkun = namaAkun;
    }
    
    public GeneralObject(GeneralObject go){
         this.akunIndex = go.getAkunIndex();
        this.kodeAkun = go.getKodeAkun();
        this.namaAkun = go.getNamaAkun();
    }

    public long getAkunIndex() {
        return akunIndex;
    }

    public void setAkunIndex(long akunIndex) {
        this.akunIndex = akunIndex;
    }

    public String getKodeAkun() {
        return kodeAkun;
    }

    public void setKodeAkun(String kodeAkun) {
        this.kodeAkun = kodeAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public void setView(short view) {
        if(view >=0 && view <=2) this.view = view;
        else this.view = 0;
    }

    @Override
    public String toString() {
        if(view == VIEW_CODE)
            return this.kodeAkun;
        else if(view == this.VIEW_NAME)
            return this.namaAkun;
        return this.kodeAkun+" "+this.namaAkun;
    }
    
    
}

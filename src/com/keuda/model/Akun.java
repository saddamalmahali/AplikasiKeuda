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
public class Akun extends GeneralObject implements Serializable{

   
    private short tipeakun;
    private short levelakun;
    private boolean akungrup;
    
    public static final boolean NOTGROUP = false;
    public static final boolean GROUP = true;
    
    private Akun k_parent = null;
    private Akun[] k_subs = null;
    
    public static final short LEVEL_1 = 1;
    public static final short LEVEL_2 = 2;
    public static final short LEVEL_3 = 3;
    public static final short LEVEL_4 = 4;
    public static final short LEVEL_5 = 5;
    public static final short LEVEL_6 = 6;
    public static final short LEVEL_7 = 7;
    
    public static final String STR_LEVEL_1 = "BAGAN AKUN STANDAR 1";
    public static final String STR_LEVEL_2 = "BAGAN AKUN STANDAR 2";
    public static final String STR_LEVEL_3 = "BAGAN AKUN STANDAR 3";
    public static final String STR_LEVEL_4 = "BAGAN AKUN STANDAR 4";
    public static final String STR_LEVEL_5 = "BAGAN AKUN STANDAR 5";
    public static final String STR_LEVEL_6 = "BAGAN AKUN STANDAR 6";
    
    public static String[] k_level = new String[]{
      "", STR_LEVEL_1, STR_LEVEL_2, STR_LEVEL_3,
    STR_LEVEL_4, STR_LEVEL_5, STR_LEVEL_6 
    };
    
    public static final short TIPE_1 = 1;
    public static final short TIPE_2 = 2;
    public static final short TIPE_3 = 3;
    public static final short TIPE_4 = 4;
    public static final short TIPE_5 = 5;
    public static final short TIPE_6 = 6;
    
    public static final String STR_TIPE_1 = "AKUN UTAMA";
    public static final String STR_TIPE_2 = "KELOMPOK";
    public static final String STR_TIPE_3 = "JENIS";
    public static final String STR_TIPE_4 = "OBJEK";
    public static final String STR_TIPE_5 = "SUB OBJEK";
    public static final String STR_TIPE_6 = "RINCIAN";
    
    public static String[] k_tipe = new String[]{
        "" , STR_TIPE_1, STR_TIPE_2, STR_TIPE_3,
        STR_TIPE_4, STR_TIPE_5, STR_TIPE_6
    };
       
    public Akun(long akunIndex, String kodeAkun, String namaAkun){
        super.setAkunIndex(akunIndex);
        super.setNamaAkun(namaAkun);
        super.setKodeAkun(kodeAkun);
    }

    public Akun(String kodeAkun, String namaAkun){
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
    }
    
    public Akun(long akunIndex, String kodeAkun, String namaAkun, short tipe, short levelAkun, boolean group){
        super.setAkunIndex(akunIndex);
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.tipeakun = tipe;
        this.levelakun = levelAkun;
        this.akungrup = group;
    }
    
    public Akun(String kodeAkun, String namaAkun, short tipeAkun, short levelAkun, boolean akunGrup){
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.tipeakun = tipeAkun;
        this.levelakun = levelAkun;
        this.akungrup = akunGrup;
    }

    public Akun(long akunIndex, Akun akun) {
        super.setAkunIndex(akunIndex);
        super.setKodeAkun(akun.getKodeAkun());
        super.setNamaAkun(akun.getNamaAkun());
        this.tipeakun = akun.getTipeakun();
        this.levelakun = akun.getLevelakun();
        this.akungrup = akun.isAkungrup();
    }
    
    public void setAkunIndex(long akunIndex){
        super.setAkunIndex(akunIndex);
    }
    
    public long getAkunIndex(){
        return super.getAkunIndex();
    }

    @Override
    public void setKodeAkun(String kodeAkun) {
        super.setKodeAkun(kodeAkun);
    }

    @Override
    public String getKodeAkun() {
        return super.getKodeAkun();
    }

    @Override
    public void setNamaAkun(String namaAkun) {
        super.setNamaAkun(namaAkun);
    }

    @Override
    public String getNamaAkun() {
        return super.getNamaAkun();
    }
    
    
    
    public boolean isAkungrup() {
        return akungrup;
    }
    
    public void setAkungrup(boolean akungrup) {
        this.akungrup = akungrup;
    }   

    public short getLevelakun() {
        return levelakun;
    }

    public void setLevelakun(short levelakun) {
        this.levelakun = levelakun;
    }

    public short getTipeakun() {
        return tipeakun;
    }

    public void setTipeakun(short tipeakun) {
        this.tipeakun = tipeakun;
    }
    
    private void readObject(ObjectInputStream ois)throws ClassNotFoundException, IOException{
        ois.defaultReadObject();
    }
    
    private void writeObjeck(ObjectOutputStream oos)throws IOException{
        oos.defaultWriteObject();
    }
    
    public String getLevelAsString(){
        if(this.levelakun < 0 || this.levelakun >= k_level.length)
            return "";
        else
            return k_level[this.levelakun];
    }
    
    public static short LevelFromStringToId(String akunLevel){
        short len = (short) k_level.length;
        for(short i=0; i<len; i++){
            if(k_level[i].equals(akunLevel))
                return i;
        }
        return -1;
    }
    
    public String getTipeAsString(){
        if(this.tipeakun < 0 || this.tipeakun >= k_tipe.length)
            return "";
        else
            return k_tipe[this.tipeakun];
    }
    
    public static short tipeFromStringToID(String tipeAkun){
        short len = (short) k_tipe.length;
        for(short i=0; i<len; i++){
            if(k_tipe[i].equals(tipeAkun))
                return i;
        }
        return -1;
    }
    
    public String toString(){
        return super.toString();
    }
    
    public Akun getParent(){
        return k_parent;
    }
    
    public void setParent(Akun parent){
        k_parent = parent;
    }
    
    public void setSub(Akun[] subs){
        k_subs = subs;
    }
    
    public Akun[] getSub(){
        return k_subs;
    }
}

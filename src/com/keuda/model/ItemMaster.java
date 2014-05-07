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
public class ItemMaster extends GeneralObject implements Serializable {
    public static final short LEVEL_1 = 1;
    public static final short LEVEL_2 = 2;
    public static final short LEVEL_3 = 3;
    public static final short LEVEL_4 = 4;
    public static final short LEVEL_99 = 9;
    
    public static final String STR_LEVEL_1 = "BARANG TIDAK BERGERAK";
    public static final String STR_LEVEL_2 = "BARANG BERGERAK";
    public static final String STR_LEVEL_3 = "HEWAN, IKAN DAN TANAMAN";
    public static final String STR_LEVEL_4 = "BARANG PERSEDIAAN";
    public static final String STR_LEVEL_99 = "KONTRUKSI DALAM PENGERJAAN";
    
    public static final String[] k_level = new String[]{
      "", STR_LEVEL_1, STR_LEVEL_2, STR_LEVEL_3, STR_LEVEL_4, STR_LEVEL_99  
    };
    
    public static final short TIPE_GOLONGAN = 1;
    public static final short TIPE_BIDANG = 2;
    public static final short TIPE_KELOMPOK = 3;
    public static final short TIPE_SUBKELOMPOK = 4;
    public static final short TIPE_SUBSUBKELOMPOK = 5;
    
    public static final String STR_TIPE_GOLONGAN = "GOLONGAN";
    public static final String STR_TIPE_BIDANG = "BIDANG";
    public static final String STR_TIPE_KELOMPOK = "KELOMPOK";
    public static final String STR_TIPE_SUBKELOMPOK = "SUB KELOMPOK";
    public static final String STR_TIPE_SUBSUBKELOMPOK = "SUB SUB KELOMPOK";
    
    public static String[] k_tipe = new String[]{
      "", STR_TIPE_GOLONGAN, STR_TIPE_BIDANG, STR_TIPE_KELOMPOK, STR_TIPE_SUBKELOMPOK
            , STR_TIPE_SUBSUBKELOMPOK
    };
    
    public static final boolean NOTGROUP = false;
    public static final boolean GROUP = true;
    
    private short tipeAkun;
    private boolean akunGrup =false;
    private short levelAkun;
    
    private ItemMaster k_parent = null;
    private Akun akun = null;
    
    private long k_indexParent = 0;
    private String unit;
    
    public static boolean isViewZeroCode = false;

    public ItemMaster(long akunIndex, String kodeAkun, String namaAkun) {
        super.setAkunIndex(akunIndex);
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
       
    }

    public ItemMaster(long akunIndex, String kodeAkun, String namaAkun, short levelAkun) {
        super.setAkunIndex(akunIndex);
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.levelAkun = levelAkun;
        
    }

    public ItemMaster(long akunIndex, String kodeAkun, String namaAkun, short levelAkun
            , short tipeAkun, boolean akunGrup) {
        super.setAkunIndex(akunIndex);
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.tipeAkun = tipeAkun;
        this.levelAkun = levelAkun;
        this.akunGrup = akunGrup;
    }

    public ItemMaster(String kodeAkun, String namaAkun, short tipeAkun, short levelAkun,
            boolean akunGrup) {
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.tipeAkun = tipeAkun;
        this.levelAkun = levelAkun;
        this.akunGrup = akunGrup;
    }

    public ItemMaster(long akunIndex, ItemMaster im) {
        super.setAkunIndex(im.getAkunIndex());
        super.setKodeAkun(im.getKodeAkun());
        super.setNamaAkun(im.getNamaAkun());
        this.tipeAkun = im.getTipeAkun();
        this.levelAkun = im.getLevelAkun();
        this.akunGrup = im.isAkunGrup();
    }

    public ItemMaster(long akunIndex, String kodeAkun, String namaAkun, boolean akunGrup) {
        super.setAkunIndex(akunIndex);
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.akunGrup = akunGrup;
    }

    public ItemMaster(String kodeAkun, String namaAkun, boolean akunGrup) {
        super.setKodeAkun(kodeAkun);
        super.setNamaAkun(namaAkun);
        this.akunGrup = akunGrup;
    }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        ois.defaultReadObject();
    }
    
    private void writeObject(ObjectOutputStream oos)throws IOException{
        oos.defaultWriteObject();
    }
    
    public boolean isAkunGrup() {
        return akunGrup;
    }

    @Override
    public void setAkunIndex(long akunIndex) {
        super.setAkunIndex(akunIndex);
    }   
    
    @Override
    public long getAkunIndex() {
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
    
     
    public void setAkunGrup(boolean akunGrup) {
        this.akunGrup = akunGrup;
    }

    public short getLevelAkun() {
        return levelAkun;
    }

    public void setLevelAkun(short levelAkun) {
        this.levelAkun = levelAkun;
    }

    public short getTipeAkun() {
        return tipeAkun;
    }

    public void setTipeAkun(short tipeAkun) {
        this.tipeAkun = tipeAkun;
    }
    
    public String getLevelAsString(){
        if(this.levelAkun < 0 || this.levelAkun >= k_level.length)
            return "";
        else
            return k_level[this.levelAkun];                   
    }
    
    public static short levelFromStringToID(String levelAkun){
        short len = (short) k_level.length;
        for(short i =0; i<len; i++){
            if(k_level[i].equals(levelAkun))
                return i;
        }
        return -1;
    }
    
    public String getTipeAsString(){
        if(this.tipeAkun <0 || this.tipeAkun >= k_tipe.length)
            return "";
        else
            return k_tipe[this.tipeAkun];
    }
    
    public static short tipeFromStringToID(String tipeAkun){
        short len = (short) k_tipe.length;
        for(short i=0; i<len; i++){
            if(k_tipe[i].equals(tipeAkun))
                return i;
                       
        }
        return -1;
    }

    public ItemMaster getK_parent() {
        return k_parent;
    }

    public void setK_parent(ItemMaster k_parent) {
        this.k_parent = k_parent;
    }
    
    public void setView(short view){
        super.setView(view);
    }
    
    public String getCastingCode(){
        String result = super.getKodeAkun();
        if (tipeAkun == TIPE_GOLONGAN)
		result += ".00.00.00.000";
	else if (tipeAkun == TIPE_BIDANG)
		result += ".00.00.000";
	else if (tipeAkun == TIPE_KELOMPOK)
		result += ".00.000";
	else if (tipeAkun == TIPE_SUBKELOMPOK)
		result += ".000";        
	return result;
    }
    
    public String toString(){
        String kodeAkun = super.getKodeAkun();
        if(isViewZeroCode){
            kodeAkun = getCastingCode();
        }
        if(view == GeneralObject.VIEW_CODENAME)
            return kodeAkun +" "+super.getNamaAkun();
        else if(view == GeneralObject.VIEW_NAME)
            return super.getNamaAkun();
        else
            return kodeAkun;        
    }

    public void setAkun(Akun akun) {
        this.akun = akun;
    }

    public Akun getAkun() {
        return akun;
    }

    public void setK_indexParent(long k_indexParent) {
        this.k_indexParent = k_indexParent;
    }

    public long getK_indexParent() {
        return k_indexParent;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }    
    
}

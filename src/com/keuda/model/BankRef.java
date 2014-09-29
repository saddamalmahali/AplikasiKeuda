/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.model;

/**
 *
 * @author BENDAHARA
 */
public class BankRef {
    long id;
    String sandi;
    String namabank;
    String wilayah;
    String alamat;
    
    public static final long VIEW_NO = 0;
    public static final long VIEW_SANDI = 1;
    
    long view;
    
    public BankRef() {
        
    }

    public BankRef(String sandi, String namabank, String wilayah, String alamat) {
        
        this.sandi = sandi;
        this.namabank = namabank;
        this.wilayah = wilayah;
        this.alamat = alamat;
    }
    
    public BankRef(long id, String sandi, String namabank, String wilayah, String alamat) {
        this.id = id;
        this.sandi = sandi;
        this.namabank = namabank;
        this.wilayah = wilayah;
        this.alamat = alamat;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSandi() {
        return sandi;
    }

    public void setSandi(String sandi) {
        this.sandi = sandi;
    }

    public String getNamabank() {
        return namabank;
    }

    public void setNamabank(String namabank) {
        this.namabank = namabank;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setView(long view) {
        this.view = view;
    }
    
    

    @Override
    public String toString() {
         //To change body of generated methods, choose Tools | Templates.
        
        if(view == VIEW_NO){
            return ""+id;
        }else if(view == VIEW_SANDI){
            return sandi;
        }else{
            return namabank+" KC "+wilayah+" "+alamat;
        }
    }
    
    
}

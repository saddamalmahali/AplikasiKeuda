/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class AkunStruktur {
    
    private Akun akun;
    private long superId;
    private long subId;
    
    short[] tipe;

    public AkunStruktur() {
        
    }
    
    
    public AkunStruktur(long superId, long subId) {
        this.superId = superId;
        this.subId = subId;
    }

    public AkunStruktur(Akun akun, long superId, long subId) {
        this.akun = akun;
        this.superId = superId;
        this.subId = subId;
    }
    
    
    public Akun getAkun(){
        return akun;
    }
    
    public long getSuperId() {
        return superId;
    }

    public void setSuperId(long superId) {
        this.superId = superId;
    }

    public long getSubId() {
        return subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }
}

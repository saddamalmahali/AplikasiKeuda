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
public class JenisSPP {
    long idspp;
    String namajenis;

    public JenisSPP() {
    }

    public JenisSPP(String namajenis) {
        this.namajenis = namajenis;
    }

    public long getIdspp() {
        return idspp;
    }

    public void setIdspp(long idspp) {
        this.idspp = idspp;
    }

    public String getNamajenis() {
        return namajenis;
    }

    public void setNamajenis(String namajenis) {
        this.namajenis = namajenis;
    }

    @Override
    public String toString() {
        return namajenis; //To change body of generated methods, choose Tools | Templates.
    }   
    
}

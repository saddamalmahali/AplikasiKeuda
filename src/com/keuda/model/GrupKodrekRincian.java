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
public class GrupKodrekRincian {
    
    private long indexgrup;
    private long rincianindex;

    public GrupKodrekRincian() {
        
    }

    public GrupKodrekRincian(long indexgrup, long rincianindex) {
        this.indexgrup = indexgrup;
        this.rincianindex = rincianindex;
    }
    
    public long getIndexgrup() {
        return indexgrup;
    }

    public void setIndexgrup(long indexgrup) {
        this.indexgrup = indexgrup;
    }

    public long getRincianindex() {
        return rincianindex;
    }

    public void setRincianindex(long rincianindex) {
        this.rincianindex = rincianindex;
    }
    
    
}

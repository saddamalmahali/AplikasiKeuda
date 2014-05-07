/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class ItemMasterStructure {
    ItemMaster im;
    long superId;
    long subId;
    short[] types;

    public ItemMasterStructure() {
    }

    public ItemMasterStructure(ItemMaster acc, long superacc, long subacc) {
        this.im = acc;
        this.superId = superacc;
        this.subId = subacc;
    }

    public void setType(short[] types) {
        this.types = types;
    }

    public short[] getType() {
        return types;
    }

    public ItemMaster getItemMaster() {
        return im;
    }

    public long getsubId() {
        return subId;
    }

    public long getsuperId() {
        return superId;
    }
}

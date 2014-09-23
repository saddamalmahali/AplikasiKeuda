/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.view;

import com.keuda.model.Komponen;
import com.keuda.model.SubKomponen;

/**
 *
 * @author adam
 */
public class KomponenStructureForTree {
    Komponen[] parent;
    SubKomponen[] sub;

    public KomponenStructureForTree(Komponen[] parent, SubKomponen[] sub) {
        this.parent = parent;
        this.sub = sub;
    }

    public Komponen[] getParent() {
        return parent;
    }

    public void setParent(Komponen[] parent) {
        this.parent = parent;
    }

    public SubKomponen[] getSub() {
        return sub;
    }

    public void setSub(SubKomponen[] sub) {
        this.sub = sub;
    }
    
    
    
}

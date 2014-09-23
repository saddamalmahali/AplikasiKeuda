/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.Akun;
import com.keuda.model.AkunStruktur;
import java.util.Vector;

/**
 *
 * @author user
 */
public class AkunStructureForTree {
    AkunStruktur[] sr;
    Vector ak;
    Akun[] akuns;

    public AkunStructureForTree(AkunStruktur[] sr, Vector ak) {
        this.sr = sr;
        this.ak = ak;
    }

    public Vector getAkun() {
        return ak;
    }  
    
    public Vector getAkunID() {
        return ak;
    }

    public Akun[] getSuper() {
        return akuns;
    }

    public void setSuper(Akun[] akuns) {
        this.akuns = akuns;
    }

    public AkunStruktur[] getStruktur() {
        return sr;
    }

    public void setSr(AkunStruktur[] sr) {
        this.sr = sr;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.model;

/**
 *
 * @author adam
 */
public class DetailRincianStruktur {
    
    DetailRincian detail;
    private long parentdetail;
    private long subdetail;
    DetailRincian[] subs;
    
    long view = -1;
    
    public static final long VIEW_NONE = 0;
    
    public DetailRincianStruktur() {
    }
    
    public DetailRincianStruktur(long parentdetail, long subdetail) {
        this.parentdetail = parentdetail;
        this.subdetail = subdetail;
    }

    public DetailRincianStruktur(DetailRincian detail, long parentdetail, long subdetail) {
        this.detail = detail;
        this.parentdetail = parentdetail;
        this.subdetail = subdetail;
    }

    public DetailRincianStruktur(DetailRincian detail, DetailRincian[] subs) {
        this.detail = detail;
        this.subs = subs;
    }
    
    
    
    public DetailRincian getDetail() {
        return detail;
    }

    public void setDetail(DetailRincian detail) {
        this.detail = detail;
    }

    public long getParentdetail() {
        return parentdetail;
    }

    public void setParentdetail(long parentdetail) {
        this.parentdetail = parentdetail;
    }

    public long getSubdetail() {
        return subdetail;
    }

    public void setSubdetail(long subdetail) {
        this.subdetail = subdetail;
    }

    public DetailRincian[] getSubs() {
        return subs;
    }

    public void setSubs(DetailRincian[] subs) {
        this.subs = subs;
    }

    public void setView(long view) {
        this.view = view;
    }
    
    

    @Override
    public String toString() {
        if(view == VIEW_NONE){
            return "";
        }else{
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    
}

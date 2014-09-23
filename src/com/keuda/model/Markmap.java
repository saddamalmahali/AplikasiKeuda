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
public class Markmap {
    public long id;
    public String kdrekening;
    public String nmrekening;
    
    long view = -1;
    
    public static final long VIEW_KODE_NAMA_REK = 0;
    
    public Markmap() {
    }

    public Markmap(long id, String kdrekening, String nmrekening) {
        this.id = id;
        this.kdrekening = kdrekening;
        this.nmrekening = nmrekening;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKdrekening() {
        return kdrekening;
    }

    public void setKdrekening(String kdrekening) {
        this.kdrekening = kdrekening;
    }

    public String getNmrekening() {
        return nmrekening;
    }

    public void setNmrekening(String nmrekening) {
        this.nmrekening = nmrekening;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_KODE_NAMA_REK){
            return kdrekening + "   "+nmrekening;
        }else
            return super.toString();
    }
    
    
    
}

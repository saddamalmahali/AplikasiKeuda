/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class OutputKegiatan implements Serializable{
    private long kegiatanDipa;
    private int nroutput;
    private String output;
    private KegiatanDipa k_kedip;
    private long view = -1;
    
    public static final long VIEW_OUTPUT_KEGIATAN = 0;
    
    public static final long VIEW_KEGIATAN_DIPA = 1;
    public static final long VIEW_KEGIATAN_DIPA_CODE_PROGRAM = 2;
    public static final long VIEW_NROUTPUT_OUTPUT = 3;

    public OutputKegiatan(long kegiatanDipa, int nroutput, String output) {
        this.kegiatanDipa = kegiatanDipa;
        this.nroutput = nroutput;
        this.output = output;
//        k_kedip = new KegiatanDipa();
    }

    public OutputKegiatan(KegiatanDipa k_kedip, int nroutput, String output) {
        this.k_kedip = k_kedip;
        kegiatanDipa = k_kedip.getDipaindex();
        this.nroutput = nroutput;
        this.output = output;
    }

    public KegiatanDipa getK_kedip() {
        return k_kedip;
    }

    public void setK_kedip(KegiatanDipa k_kedip) {
        this.k_kedip = k_kedip;
    }

    public long getKegiatanDipa() {
        return kegiatanDipa;
    }

    public void setKegiatanDipa(long kegiatanDipa) {
        this.kegiatanDipa = kegiatanDipa;
    }

    public int getNroutput() {
        return nroutput;
    }

    public void setNroutput(int nroutput) {
        this.nroutput = nroutput;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_KEGIATAN_DIPA)
            return k_kedip.getK_kegiatan().getKegiatanName();
        else if(view == VIEW_OUTPUT_KEGIATAN)
            return output;
        else if(view == VIEW_KEGIATAN_DIPA_CODE_PROGRAM){
            return k_kedip.getProgramDipa().getProgram().getProgramCode();
        }else if(view == VIEW_NROUTPUT_OUTPUT){
            String nr = "";
            if(nroutput>0 && nroutput< 10){
                nr = "00"+nroutput;
            }else if(nroutput>=10 && nroutput<100){
                nr = "0"+nroutput;
            }else{
                nr = ""+nroutput;
            }
            return nr+"  "+output;
        }
        else 
            return ""+k_kedip;
    } 
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class IkuProgram {

    private long programDipa;
    private int nrIku;
    private String iku;
    private ProgramDipa dipa;
    
    public static long view =-1;
    public static final long VIEW_ID_NOMOR_IKU = 0;
    public static final long VIEW_PROGRAM_NOMOR =1;
    public static final long VIEW_NOMOR_IKU = 2;
    public static final long VIEW_IKU = 3;
    public static final long VIEW_IKU_PROGRAM = 4;
    public static final long VIEW_CODE_PROGRAM = 5;
    
    public IkuProgram(IkuProgram ip) {
        this.programDipa = ip.getProgramDipa();
        this.nrIku = ip.getNrIku();
        this.iku = ip.getIku();
        this.dipa = ip.getDipa();
    }
    
    public IkuProgram(long programDipa, int nrIku, String iku) {
        this.programDipa = programDipa;
        this.nrIku = nrIku;
        this.iku = iku;
    }
    
    public IkuProgram(ProgramDipa prodi, int nriku, String iku){
        this.dipa = prodi;
        this.nrIku = nriku;
        this.iku = iku;
        this.programDipa = prodi.getDipaIndex();
    }
    
    public IkuProgram() {
    } 

    public ProgramDipa getDipa() {
        return dipa;
    }

    public void setDipa(ProgramDipa dipa) {
        this.dipa = dipa;
        this.programDipa = dipa.getDipaIndex();
    }

    public String getIku() {
        return iku;
    }

    public void setIku(String iku) {
        this.iku = iku;
    }

    public int getNrIku() {
        return nrIku;
    }

    public void setNrIku(int nrIku) {
        this.nrIku = nrIku;
    }

    public long getProgramDipa() {
        return programDipa;
    }

    public void setProgramDipa(long programDipa) {
        this.programDipa = programDipa;
    }

    public static void setView(long view) {
        IkuProgram.view = view;
    }
    
    

    @Override
    public String toString() {
        if(view == VIEW_ID_NOMOR_IKU)
            return programDipa+ nrIku+iku;
        else if(view == VIEW_NOMOR_IKU)
            return nrIku+iku;
        else if(view == VIEW_PROGRAM_NOMOR)
            return ""+programDipa+nrIku;
        else if(view == VIEW_IKU)
            return iku;
        else if(view == VIEW_IKU_PROGRAM)
            return ""+dipa;
        else if(view == VIEW_CODE_PROGRAM)
            return ""+dipa.getProgram().getProgramCode();
        else
            return dipa+iku;
    }
    
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class ProgramDipa {
    private long dipaIndex;
    private int tahunAnggaran;
    private long programindex;
    private Program program;
    
    public long view = -1;
    
    public static final long VIEW_CODE = 0;
    public static final long VIEW_PROGRAM = 1;
    public static final long VIEW_TAHUN_ANGGARAN = 2;
    
    
    public ProgramDipa(long dipaIndex, int tahunAnggaran, Program program) {
        this.dipaIndex = dipaIndex;
        this.tahunAnggaran = tahunAnggaran;
        this.programindex = program.getProgramIndex();
        this.program = program;
    }

    public ProgramDipa() {
    }
    
    
    
    public ProgramDipa(int tahunAnggaran, long programindex) {
        this.tahunAnggaran = tahunAnggaran;
        this.programindex = programindex;
    }
    
    
    public ProgramDipa(long dipaIndex, int tahunAnggaran, long programindex, Program program) {
        this.dipaIndex = dipaIndex;
        this.tahunAnggaran = tahunAnggaran;
        this.programindex = programindex;
        this.program = program;
    }

    public ProgramDipa(ProgramDipa dipa) {
        this.dipaIndex = dipa.getDipaIndex();
        this.tahunAnggaran = dipa.getTahunAnggaran();
        this.program = dipa.getProgram();
        this.programindex = dipa.getProgramindex();
    }

    public ProgramDipa(long dipaIndex, int tahunAnggaran, long programindex) {
        this.dipaIndex = dipaIndex;
        this.tahunAnggaran = tahunAnggaran;
        this.programindex = programindex;
    }
    
    
    
    public long getDipaIndex() {
        return dipaIndex;
    }

    public void setDipaIndex(long dipaIndex) {
        this.dipaIndex = dipaIndex;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public long getProgramindex() {
        return programindex;
    }

    public void setProgramindex(long programindex) {
        this.programindex = programindex;
    }

    public int getTahunAnggaran() {
        return tahunAnggaran;
    }

    public void setTahunAnggaran(int tahunAnggaran) {
        this.tahunAnggaran = tahunAnggaran;
    }

    public void setView(long view) {
        this.view = view;
    }
    
    
    
    @Override
    public String toString() {
        if(view == VIEW_CODE){
            return ""+dipaIndex;
        }else if(view == VIEW_PROGRAM){
            return ""+ program;
        }else if(view == VIEW_TAHUN_ANGGARAN){
            return ""+tahunAnggaran;
        }else{
            return ""+dipaIndex+" "+tahunAnggaran+" "+program;
        }
    }
    
    
    
}

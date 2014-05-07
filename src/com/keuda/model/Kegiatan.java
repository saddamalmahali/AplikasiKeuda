/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class Kegiatan {
    private long kegiatanIndex;
    private long programIndex;
    private String kegiatanCode;
    private String kegiatanName;
    private Program program;
    
    long view = -1;
    
    public static long VIEW_CODE = 0;
    public static long VIEW_NAME = 1;
    public static long VIEW_CODE_AND_NAME = 2;
    public static long VIEW_CODE_MASTER_PROGRAM = 3;
    
    public Kegiatan(long kegiatanIndex, long programIndex, String kegiatanCode, String kegiatanName, Program program) {
        this.kegiatanIndex = kegiatanIndex;
        this.programIndex = programIndex;
        this.kegiatanCode = kegiatanCode;
        this.kegiatanName = kegiatanName;
        this.program = program;
    }
    
    
    public Kegiatan(long kegiatanIndex, String kegiatanCode, String KegiatanName, Program program){
        this.program = program;
        this.programIndex = program.getProgramIndex();
        this.kegiatanCode = kegiatanCode;
        this.kegiatanIndex = kegiatanIndex;
        this.kegiatanName = KegiatanName;
    }
    public Kegiatan() {
    }
    
    public Kegiatan(Kegiatan k){
        this.kegiatanIndex = k.getKegiatanIndex();
        this.programIndex = k.getProgramIndex();
        this.kegiatanCode = k.getKegiatanCode();
        this.kegiatanName = k.getKegiatanName();
    }
    
    public Kegiatan(long programIndex, String kegiatanKode, String kegiatanName){
        this.programIndex = programIndex;
        this.kegiatanName = kegiatanName;
        this.kegiatanCode = kegiatanKode;
    }
    
    public Kegiatan(long kegiatanIndex, Program program, String kegiatanCode, String kegiatanName){
        this.kegiatanIndex = kegiatanIndex;
        
        this.program = program;
        this.programIndex = program.getProgramIndex();
        this.kegiatanCode = kegiatanCode;
        this.kegiatanName = kegiatanName;
    }
    
    public Kegiatan(long kegiatanIndex, long programIndex, String kegiatanCode, String kegiatanName) {
        this.kegiatanIndex = kegiatanIndex;
        this.programIndex = programIndex;
        this.kegiatanCode = kegiatanCode;
        this.kegiatanName = kegiatanName;
    }

    public String getKegiatanCode() {
        return kegiatanCode;
    }

    public void setKegiatanCode(String kegiatanCode) {
        this.kegiatanCode = kegiatanCode;
    }

    public long getKegiatanIndex() {
        return kegiatanIndex;
    }

    public void setKegiatanIndex(long kegiatanIndex) {
        this.kegiatanIndex = kegiatanIndex;
    }

    public String getKegiatanName() {
        return kegiatanName;
    }

    public void setKegiatanName(String kegiatanName) {
        this.kegiatanName = kegiatanName;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public long getProgramIndex() {
        return programIndex;
    }

    public void setProgramIndex(long programIndex) {
        this.programIndex = programIndex;
    }

    public void setView(long view) {
        this.view = view;
    }
    
    

    @Override
    public String toString() {
        if(view == VIEW_CODE){
            return kegiatanCode;
        }else if(view == VIEW_NAME){
            return kegiatanName;
        }else if(view == VIEW_CODE_AND_NAME){
            return kegiatanCode+" "+kegiatanName; 
        }else if(view == VIEW_CODE_MASTER_PROGRAM){
            return ""+getProgram().getProgramCode();
        }else{
            return kegiatanIndex+" "+programIndex+" "+kegiatanCode+" "+kegiatanName;
        }
    }
    
}

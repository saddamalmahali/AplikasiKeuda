package com.keuda.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class KegiatanDipa implements Serializable{
    private long kegiatandipaindex;
    private long dipaindex;
    private long kegiatan;
    private ProgramDipa k_prodi;
    private Kegiatan k_kegiatan;
    private OutputKegiatan[] outputkegiatan;
    public long view = -1;
    
    public static final long VIEW_CODE_PROGRAM = 0;
    public static final long VIEW_CODE_AND_NAME = 1;
    public static final long VIEW_PROGRAM = 2;
    public static final long VIEW_TAHUN = 3;
    public static final long VIEW_CODE_AND_NAME_KEGIATAN = 4;
    
    
    public KegiatanDipa(long kegiatandipaindex, ProgramDipa k_prodi, Kegiatan k_kegiatan) {
        this.kegiatandipaindex = kegiatandipaindex;
        this.k_prodi = k_prodi;
        this.k_kegiatan = k_kegiatan;
        this.dipaindex = k_prodi.getDipaIndex();
        this.kegiatan = k_kegiatan.getKegiatanIndex();
    }
    
    public KegiatanDipa(long kegiatandipaindex, ProgramDipa k_prodi, Kegiatan k_kegiatan, OutputKegiatan[] outputkegiatan) {
        this.kegiatandipaindex = kegiatandipaindex;
        this.k_prodi = k_prodi;
        this.k_kegiatan = k_kegiatan;
        this.dipaindex = k_prodi.getDipaIndex();
        this.kegiatan = k_kegiatan.getKegiatanIndex();
        this.outputkegiatan = outputkegiatan;
    }
    
    public KegiatanDipa(ProgramDipa k_prodi, Kegiatan k_kegiatan){
        this.k_prodi = k_prodi;
        this.k_kegiatan = k_kegiatan;
        this.dipaindex = k_prodi.getDipaIndex();
        this.kegiatan = k_kegiatan.getKegiatanIndex();
    }
    
    public KegiatanDipa(long kegiatandipaindex, long dipaindex, long kegiatan){
        this.kegiatandipaindex = kegiatandipaindex;
        this.dipaindex = dipaindex;
        this.kegiatan = kegiatan;
    }
    
    public KegiatanDipa(long kegiatandipaindex, ProgramDipa k_prodi, long kegiatan){
        this.kegiatandipaindex = kegiatandipaindex;
        this.k_prodi = k_prodi;
        this.kegiatan = kegiatan;
        this.dipaindex = k_prodi.getDipaIndex();
    }
    
    public KegiatanDipa(long kegiatandipaindex, long dipaindex, Kegiatan k_kegiatan){
        this.kegiatandipaindex = kegiatandipaindex;
        this.dipaindex = dipaindex;
        this.kegiatan = k_kegiatan.getKegiatanIndex();
        this.k_kegiatan = k_kegiatan;
    }
    
    public long getDipaindex() {
        return dipaindex;
    }

    public long getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(long kegiatan) {
        this.kegiatan = kegiatan;
    }
    
    
    
    public void setDipaindex(long dipaindex) {
        this.dipaindex = dipaindex;
    }

    public void setK_kegiatan(Kegiatan k_kegitan){
        this.k_kegiatan = k_kegitan;
        this.kegiatan = k_kegitan.getKegiatanIndex();
    }

    public Kegiatan getK_kegiatan(){
        return k_kegiatan;
    }
    
    public void setProgramDipa(ProgramDipa k_prodi){
        this.k_prodi = k_prodi;
        this.dipaindex = k_prodi.getDipaIndex();
    }
    
    public ProgramDipa getProgramDipa(){
        return k_prodi;
    }

    public void setView(long view) {
        this.view = view;
    }

    public long getKegiatandipaindex() {
        return kegiatandipaindex;
    }

    public void setKegiatandipaindex(long kegiatandipaindex) {
        this.kegiatandipaindex = kegiatandipaindex;
    }

    public void setOutputkegiatan(OutputKegiatan[] outputkegiatan) {
        this.outputkegiatan = outputkegiatan;
    }

    public OutputKegiatan[] getOutputkegiatan() {
        return outputkegiatan;
    }
       
    
    
    @Override
    public String toString() {
        if(view == VIEW_CODE_PROGRAM)
            return k_prodi.getProgram().getProgramCode();
        else if(view == VIEW_PROGRAM)
            return k_prodi.getProgram().getProgramName();
        else if(view == VIEW_CODE_AND_NAME_KEGIATAN){
            return ""+k_kegiatan.getKegiatanCode()+"   "+k_kegiatan.getKegiatanName();
        }else if(view == VIEW_CODE_AND_NAME){
            return k_prodi.getProgram().getProgramCode()+"   "+k_prodi.getProgram().getProgramName();
        }else if(view ==  VIEW_TAHUN)
            return ""+k_prodi.getTahunAnggaran();
        else
            return ""+dipaindex;
    }
    
}

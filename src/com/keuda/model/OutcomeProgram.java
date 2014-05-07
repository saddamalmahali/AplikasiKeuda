/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class OutcomeProgram {
    private long dipaIndex;
    private int nmrUrut;
    private String outcome;
    private ProgramDipa programDipa;
    
    private long view = -1;
    public static final long VIEW_NOMOR_URUT =0;
    public static final long VIEW_OUTCOME =1;
    public static final long VIEW_POGRAM_DIPA = 2;
    public static final long VIEW_CODE_PROGRAM = 3;
    
    public OutcomeProgram() {
    }

    public OutcomeProgram(int nmrUrut, String outcome) {
        this.nmrUrut = nmrUrut;
        this.outcome = outcome;
    }

    public OutcomeProgram(int nmrUrut, String outcome, ProgramDipa programDipa) {
        this.dipaIndex = programDipa.getDipaIndex();
        this.nmrUrut = nmrUrut;
        this.outcome = outcome;
        this.programDipa = programDipa;
    }

    public OutcomeProgram(long dipaIndex, int nmrUrut, String outcome) {
        this.dipaIndex = dipaIndex;
        this.nmrUrut = nmrUrut;
        this.outcome = outcome;
    }
    
    public OutcomeProgram(OutcomeProgram op) {
        this.dipaIndex = op.getDipaIndex();
        this.nmrUrut = op.getNmrUrut();
        this.outcome = op.getOutcome();
        this.programDipa = op.getProgramDipa();
    }

    public long getDipaIndex() {
        return dipaIndex;
    }

    public void setDipaIndex(long dipaIndex) {
        this.dipaIndex = dipaIndex;
    }

    public int getNmrUrut() {
        return nmrUrut;
    }

    public void setNmrUrut(int nmrUrut) {
        this.nmrUrut = nmrUrut;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public ProgramDipa getProgramDipa() {
        programDipa.setView(ProgramDipa.VIEW_PROGRAM);
        return programDipa;
    }

    public void setProgramDipa(ProgramDipa programDipa) {
        this.programDipa = programDipa;
    }

    public void setView(long view) {
        this.view = view;
    }

    @Override
    public String toString() {
        if(view == VIEW_OUTCOME)
            return outcome;
        else if(view == VIEW_NOMOR_URUT)
            return ""+nmrUrut;
        else if(view == VIEW_POGRAM_DIPA)
            return ""+programDipa;
        else if(view == VIEW_CODE_PROGRAM)
            return programDipa.getProgram().getProgramCode();
        else
            return "";
    }
    
    
    
    
}

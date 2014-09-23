package com.keuda.model;


public class KodrekOutput {
    private long rinciandipakodrek;
    private long output;
    private RincianDipaKodrek k_rinciandipakodrek;
    private OutputKegiatan k_output;

    public KodrekOutput() {
        
    }

    public KodrekOutput(long rinciandipakodrek, long output, RincianDipaKodrek k_rinciandipakodrek, OutputKegiatan k_output) {
        this.rinciandipakodrek = rinciandipakodrek;
        this.output = output;
        this.k_rinciandipakodrek = k_rinciandipakodrek;
        this.k_output = k_output;
    }
    
    public long getRinciandipakodrek() {
        return rinciandipakodrek;
    }

    public void setRinciandipakodrek(long rinciandipakodrek) {
        this.rinciandipakodrek = rinciandipakodrek;
    }

    public long getOutput() {
        return output;
    }

    public void setOutput(long output) {
        this.output = output;
    }

    public RincianDipaKodrek getK_rinciandipakodrek() {
        return k_rinciandipakodrek;
    }

    public void setK_rinciandipakodrek(RincianDipaKodrek k_rinciandipakodrek) {
        this.k_rinciandipakodrek = k_rinciandipakodrek;
    }

    public OutputKegiatan getK_output() {
        return k_output;
    }

    public void setK_output(OutputKegiatan k_output) {
        this.k_output = k_output;
    }
    
    
    
}

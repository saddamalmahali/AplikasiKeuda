
package com.keuda.model;


public class GrupItem {
    
    private long grupindex;
    private String grupname;
    
    public GrupItem(long grupindex, String grupname) {
        this.grupindex = grupindex;
        this.grupname = grupname;
    }

    public GrupItem() {
    }
    
    public long getGrupindex() {
        return grupindex;
    }

    public void setGrupindex(long grupindex) {
        this.grupindex = grupindex;
    }

    public String getGrupname() {
        return grupname;
    }

    public void setGrupname(String grupname) {
        this.grupname = grupname;
    }    
    
}

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
public class Program implements Serializable{
    
    
    long programIndex;
    String programCode = "";
    String programName = "";
    long view = -1;
    
    public static long VIEW_CODE = 0;
    public static long VIEW_NAME = 1;
    public static long VIEW_CODE_AND_NAME = 2;
    
    public Program(long programIndex, String programCode, String programName) {
        this.programIndex = programIndex;
        this.programCode = programCode;
        this.programName = programName;
    }
    
    public Program() {
    }
    
    public Program(Program p){
        this.programIndex = p.getProgramIndex();
        this.programCode = p.getProgramCode();
        this.programName = p.getProgramName();
    }
    
    public Program(String programCode, String programName){
        this.programCode = programCode;
        this.programName = programName;
    }
    
    public String getProgramCode() {
        return programCode;
    }

    public long getProgramIndex() {
        return programIndex;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public void setProgramIndex(long programIndex) {
        this.programIndex = programIndex;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public void setView(long view) {
        this.view = view;
    }
    
    
    @Override
    public String toString() {
        if(view == VIEW_CODE){
            return programCode;
        }else if(view == VIEW_NAME){
            return programName;
        }else if(view == VIEW_CODE_AND_NAME){
            return programCode+" "+programName; 
        }else{
            return programIndex+" "+programCode+" "+programName;
        }
    }
    
    
}

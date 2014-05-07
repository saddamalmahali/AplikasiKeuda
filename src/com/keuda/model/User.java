/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.model;

/**
 *
 * @author user
 */
public class User {
    protected String k_username = "";
    protected String k_groups[];
    protected String k_note;
    
    public User(String username, String groups[], String note){
        this.k_username = username;
        this.k_groups = groups;
        this.k_note = note;
    }

    public String[] getK_groups() {
        return k_groups;
    }

    public void setK_groups(String[] k_groups) {
        this.k_groups = k_groups;
    }

    public String getK_note() {
        return k_note;
    }

    public void setK_note(String k_note) {
        this.k_note = k_note;
    }

    public String getK_username() {
        return k_username;
    }

    public void setK_username(String k_username) {
        this.k_username = k_username;
    }    
    
}

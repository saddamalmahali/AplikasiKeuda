/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author user
 */
public class KoneksiAplikasi {
    
    private Connection conn;
    private Properties props;
    private File file;
    private FileInputStream inputDataKoneksi;
    private String username;
    private String driver;
    private String url;
    private String pass;
    
    public KoneksiAplikasi() throws FileNotFoundException, ClassNotFoundException {
        file = new File("./koneksi.properties");
        inputDataKoneksi = new FileInputStream(file);
        props = new Properties();
        
        
    }
    
    public Connection getConnection() throws IOException, SQLException, ClassNotFoundException{
        props.load(inputDataKoneksi);
        driver = props.getProperty("jdbc.driver");
        Class.forName(driver);
        
        url = props.getProperty("jdbc.url");
        pass = props.getProperty("jdbc.password");
        username = props.getProperty("jdbc.user");
        
        conn = DriverManager.getConnection(url, username, pass);
        
        
        return conn;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}

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
    private Connection conn2;
    private Properties props;
    private Properties props2;
    private File file;
    private File file2;
    private FileInputStream inputDataKoneksi;
    private FileInputStream inputDataKoneksi2;
    private String username;
    private String driver;
    private String url;
    private String pass;
    
    public KoneksiAplikasi() throws FileNotFoundException, ClassNotFoundException {
        file = new File("./koneksi.properties");
        inputDataKoneksi = new FileInputStream(file);
        props = new Properties();
        
        file2 = new File("./koneksi2.properties");
        inputDataKoneksi2 = new FileInputStream(file2);
        props2 = new Properties();
        
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
    
    public Connection getConnection2() throws IOException, SQLException, ClassNotFoundException{
        
        props2.load(inputDataKoneksi2);
        driver = props2.getProperty("jdbc.driver");
        Class.forName(driver);
        
        url = props2.getProperty("jdbc.url");
        pass = props2.getProperty("jdbc.password");
        username = props2.getProperty("jdbc.user");
        
        conn2 = DriverManager.getConnection(url, username, pass);
        
        
        return conn2;
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

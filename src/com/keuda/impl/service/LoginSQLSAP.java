/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.impl.service;

import com.keuda.services.IDBCConstant;
import com.keuda.services.ILoginSQL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author client
 */
public class LoginSQLSAP implements ILoginSQL{

    @Override
    public long login(String username, byte[] password, String app, Connection conn) throws SQLException {
        Statement stm = null;
        byte[] dbpasswd = null;
        
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select "+IDBCConstant.ATT_PASSWD + " from "
                                            +IDBCConstant.TABLE_USERACCOUNT+" WHERE "
                                            + IDBCConstant.ATT_USERNAME+"='"+username+"'");
            
            if(rs.next()){
                dbpasswd = rs.getBytes(IDBCConstant.ATT_PASSWD);
                if(!java.util.Arrays.equals(dbpasswd, password))
                    return -1;
                
            }else
                return -1;
            
            //Periksa apakah grup termasuk administrator
            //administrator bisa mengakses semua modul dan aplikasi
            rs = stm.executeQuery("SELECT "+IDBCConstant.ATT_UGROUP+" FROM "
                                    +IDBCConstant.TABLE_USERTYPE+" WHERE "
                                    +IDBCConstant.TABLE_USERTYPE+"."+IDBCConstant.ATT_USERNAME+"='"+username+"'");
            while(rs.next()){
                String group = rs.getString(IDBCConstant.ATT_UGROUP).trim();
                if(group.equals(IDBCConstant.UGROUP_ADMINISTRATORS)){
                    long id= System.currentTimeMillis();
                    stm.executeUpdate("insert into "+IDBCConstant.TABLE_USERID+" values ('"
                                        +username+"' , "+id+")");
                    
                    return id;
                }
            }
            
            //user bukan admin, cek hak menjalankan aplikasi
            rs = stm.executeQuery("select "+IDBCConstant.ATT_APP+" from "
                                    +IDBCConstant.TABLE_APPLICATION_RIGHT+","
                                    +IDBCConstant.TABLE_USERTYPE+","
                                    +IDBCConstant.TABLE_USERGROUP+" where "
                                    +IDBCConstant.TABLE_USERTYPE+"."+IDBCConstant.ATT_USERNAME+"='"+username+"' AND "
                                    +IDBCConstant.TABLE_USERTYPE+"."+IDBCConstant.ATT_UGROUP+"="
                                    +IDBCConstant.TABLE_USERGROUP+"."+IDBCConstant.ATT_NAME+" and "
                                    +IDBCConstant.TABLE_USERGROUP+"."+IDBCConstant.ATT_NAME+"="
                                    +IDBCConstant.TABLE_APPLICATION_RIGHT+"."+IDBCConstant.ATT_UGROUP+" and "
                                    +IDBCConstant.TABLE_APPLICATION_RIGHT+"."+IDBCConstant.ATT_APP+"='"+app+"'");
            
            if(rs.next()){
                long id = System.currentTimeMillis();
                stm.executeUpdate("INSERT INTO "+IDBCConstant.TABLE_USERID+" values ('"
                                    +username+"', "+id+")");
                
                return id;
            }
            
            return -1;
        } catch (Exception e) {
            throw new SQLException("Gagal dalam proses login \n"+e.toString());
        }finally{
            if(stm != null){
                stm.close();
            }
        }
    }

    @Override
    public void logout(long sessionId, Connection conn) throws SQLException {
        Statement stm = null;
        try {
            stm = conn.createStatement();
            stm.executeUpdate("delete from "+IDBCConstant.TABLE_USERID+" where "
                                +IDBCConstant.ATT_SESSIONID+"="+sessionId);
        } catch (Exception e) {
            throw new SQLException("Gagal dalam proses logout \n"+e.getMessage());
        }finally{
            if(stm != null){
                stm.close();
            }
        }
    }
    
}

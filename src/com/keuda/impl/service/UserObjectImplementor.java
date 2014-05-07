/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.impl.service;

import com.keuda.exception.UserException;
import com.keuda.model.User;
import com.keuda.services.UserServices;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class UserObjectImplementor implements UserServices {

    private Connection conn;

    public UserObjectImplementor(Connection conn) {
        this.conn = conn;
    }

    public UserObjectImplementor() {
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public void addUser(User user) {
        if (user != null) {
            try {
                conn.setAutoCommit(false);
                PreparedStatement pstat = conn.prepareStatement(QUERY_INSERT);

//                pstat.setInt(1, user.getIdUser());
//                pstat.setString(2, user.getFullName());
//                pstat.setString(3, user.getUserName());
//                pstat.setString(4, user.getPass());
//                pstat.executeUpdate();

                conn.commit();
                System.err.println("query : "+QUERY_INSERT);
            } catch (SQLException ex) {
                new UserException(ex.getMessage()).getMessage();
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            return;
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            conn.setAutoCommit(false);
            
//            PreparedStatement pstat = conn.prepareStatement(QUERY_UPDATE);
//            pstat.setString(1, user.getFullName());
//            pstat.setString(2, user.getUserName());
//            pstat.setString(3, user.getPass());
//            pstat.setInt(4, user.getIdUser());
            
//            pstat.executeUpdate();
            
            
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Gagal Update User dengan pesan : "+ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void deleteUser(int idUser) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstat = conn.prepareStatement(QUERY_DELETE);
            pstat.setInt(1, idUser);
            
            pstat.executeUpdate();
            
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Gagal menghapus User dengan pesan : "+ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    @Override
    public List<User> getAllUser(){
        
        List<User> listUser = new ArrayList<>();
        Statement s;
        
        try {
            
            s = conn.createStatement();            
            ResultSet rs = s.executeQuery(QUERY_GET_ALL);
            
            while(rs.next()){
//                User user = new User();
//                user.setIdUser(rs.getInt("id_user"));
//                user.setFullName(rs.getString("full_nama_user"));
//                user.setUserName("nama_user");
//                user.setPass("pass_user");
//                listUser.add(user);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return listUser;
        
    }

    @Override
    public User getUserFromId(int idUser) {
        System.out.println(""+idUser);
        User user = null;
        try {
            PreparedStatement pstat = conn.prepareStatement(QUERY_GET_BYID);  
            pstat.setInt(1, idUser);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()){
//                user = new User();
//                user.setIdUser(rs.getInt("id_user"));
//                user.setFullName(rs.getString("full_nama_user"));
//                user.setUserName(rs.getString("nama_user"));
//                user.setPass(rs.getString("pass_user"));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserObjectImplementor.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return user;     
                
    }

    @Override
    public User getUserFromName(String namaUser) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

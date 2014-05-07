/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.services;

import com.keuda.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public interface IAuthorizationSQL {
    boolean isAuthorized(long sessionid, String modul, String right, Connection conn)throws SQLException;
    String[] getAllUserGroup(Connection conn)throws SQLException;
    User[] getAllUser(Connection conn)throws SQLException;
    String[] getAllApplication(Connection conn)throws SQLException;
    String[] getAllModul(Connection conn)throws SQLException;
    String[] getApplicationByUserGroup(String ugroup, Connection conn)throws SQLException;
    String[] getModulByUserGroup(String ugroup, Connection conn)throws SQLException;
    boolean hasRightRead(String ugroup, String modul, Connection conn)throws SQLException;
    boolean hasRightWrite(String ugroup, String modul, Connection conn)throws SQLException;
    void createUserGroup(String ugroup, Connection conn)throws SQLException;
    void createUser(String username, byte[] password, String note, Connection conn)throws SQLException;
    void createApplicationAuthorization(String ugroup, String[] apps, Connection conn)throws SQLException;
    void createModulAuthorization(String ugroup, String[] moduls, boolean[] breads, boolean[] bwrites,
            Connection conn)throws SQLException;
    void deleteUserGroup(String ugroup, Connection conn)throws SQLException;
    void deleteApplicationAuthorization(String ugroup, Connection conn)throws SQLException;
    void deleteModulAuthorization(String ugroup, Connection conn)throws SQLException;
    void linkUserToUserGroup(String username, String usergroup, Connection conn)throws SQLException;
    String[] getGroupByUser(String username, String usergroup, Connection conn)throws SQLException;
    void createModul(String modul, Connection conn)throws SQLException;
    void deleteModul(String modul, Connection conn)throws SQLException;
    void createApplication(String appname, Connection conn)throws SQLException;
    void deleteApplication(String appname, Connection conn)throws SQLException;
    String getUsername(long sessionId, Connection conn)throws SQLException;
    Statement stm = null;
    void updatePassword(String username, byte oldPasswd[], byte[] newpassword, Connection conn)throws SQLException;
    void deleteUser(String username, Connection conn)throws SQLException;
    
    
}

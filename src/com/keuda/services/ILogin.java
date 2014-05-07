/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.keuda.services;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author client
 */
public interface ILogin {
    long login(String username, byte[] password, String app, Connection conn)throws SQLException;
    void logout(long sessionId, Connection conn)throws SQLException;
}

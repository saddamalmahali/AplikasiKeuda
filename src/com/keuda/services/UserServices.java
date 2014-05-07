/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.services;

import com.keuda.exception.UserException;
import com.keuda.model.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author user
 */
public interface UserServices {
    
    final String QUERY_INSERT = "insert into \"user\".\"user\" (id_user, full_nama_user, nama_user, pass_user) "
            + "values(?,?,?,?)";
    final String QUERY_UPDATE ="update from \"user\".\"user\" set full_nama_user=?, nama_user=?, pass_user=? where id_user=?";
    final String QUERY_DELETE = "delete from \"user\".\"user\" where id_user=?";
    final String QUERY_GET_ALL = "select * from \"user\".\"user\"";
    final String QUERY_GET_BYID = "select * from \"user\".\"user\" where id_user=?";
    final String QUERY_BET_BYNAME = "select * from \"user\".\"user\" where nama_user=?";
    
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int idUser);
    public List<User> getAllUser();
    public User getUserFromId(int idUser);
    public User getUserFromName(String namaUser);
}

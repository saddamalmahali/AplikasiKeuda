/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.exception;

/**
 *
 * @author user
 */
public class UserException extends Exception {

    /**
     * Creates a new instance of
     * <code>UserException</code> without detail message.
     */
    public UserException() {
    }

    /**
     * Constructs an instance of
     * <code>UserException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UserException(String msg) {
        super(msg);
    }
}

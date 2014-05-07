/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.exception;

/**
 *
 * @author user
 */
public class AuthorizationException extends Exception {

    /**
     * Creates a new instance of
     * <code>AuthorizationException</code> without detail message.
     */
    public AuthorizationException() {
    }

    /**
     * Constructs an instance of
     * <code>AuthorizationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AuthorizationException(String msg) {
        super(msg);
    }
}

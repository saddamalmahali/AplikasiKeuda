/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.exception;

/**
 *
 * @author user
 */
public class KeudaException extends Exception {

    /**
     * Creates a new instance of
     * <code>KeudaException</code> without detail message.
     */
    public KeudaException() {
    }

    /**
     * Constructs an instance of
     * <code>KeudaException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public KeudaException(String msg) {
        super(msg);
    }
}

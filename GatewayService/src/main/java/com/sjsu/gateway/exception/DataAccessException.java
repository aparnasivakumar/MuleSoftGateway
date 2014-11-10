package com.sjsu.gateway.exception;

/**
 * <p>
 * Exception thrown when a service failed to do a particular operation
 * </p>
 * 
 */

public class DataAccessException extends Exception {


	private static final long serialVersionUID = -5429777284073680343L;

/**
    * <p>
    * Constructs a new <code>DataAccessException</code> without specified detail message.
    * </p>
    */
   public DataAccessException() {
      super();
   }

   /**
    * <p>
    * Constructs a new <code>DataAccessException</code> with specified detail message.
    * </p>
    * 
    * @param msg The error message.
    */
   public DataAccessException(String msg) {
      super(msg);
   }

   /**
    * <p>
    * Constructs a new <code>DataAccessException</code> with specified nested <code>Throwable</code>.
    * </p>
    * 
    * @param cause The <code>Exception</code> or <code>Error</code> that caused this exception to be
    *        thrown.
    */
   public DataAccessException(Throwable cause) {
      super(cause);
   }

   /**
    * <p>
    * Constructs a new <code>DataAccessException</code> with specified detail message and nested
    * <code>Throwable</code>.
    * </p>
    * 
    * @param msg The error message.
    * @param cause The <code>Exception</code> or <code>Error</code> that caused this exception to be
    *        thrown.
    */
   public DataAccessException(String msg, Throwable cause) {
      super(msg, cause);
   }
}

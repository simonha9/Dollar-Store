package com.b07.exceptions;

public class UnauthenticatedException extends Exception {

  /**
   * Serial ID for unauthenticated exception.
   */
  private static final long serialVersionUID = -4586334918058406875L;

  /**
   * Sets error exception message.
   */
  public UnauthenticatedException(String message) {
    super(message);
  }
}

package com.b07.exceptions;

public class InsufficientItemException extends Exception {

  /**
   * Serial ID for insufficient item exception.
   */
  private static final long serialVersionUID = -6304648339309618348L;

  /**
   * Sets error exception message.
   */
  public InsufficientItemException(String message) {
    super(message);
  }
}

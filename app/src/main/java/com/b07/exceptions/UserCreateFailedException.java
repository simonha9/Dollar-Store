package com.b07.exceptions;

public class UserCreateFailedException extends Exception {

  /**
   * Serial ID for user create failed exception.
   */
  private static final long serialVersionUID = 1224492872065301743L;

  /**
   * Sets error exception message.
   */
  public UserCreateFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}

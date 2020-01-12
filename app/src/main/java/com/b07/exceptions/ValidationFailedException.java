package com.b07.exceptions;

public class ValidationFailedException extends Exception {

  /**
   * Serial ID for validation failed exception.
   */
  private static final long serialVersionUID = 8972384030469765364L;

  /**
   * Sets error exception message.
   */
  public ValidationFailedException(String message) {
    super(message);
  }
}

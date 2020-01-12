package com.b07.exceptions;

public class AccountCreateFailedException extends Exception {

  /**
   * Generated Serial version uid
   */
  private static final long serialVersionUID = 4452101383801655763L;

  /**
   * Sets error exception message.
   */
  public AccountCreateFailedException(String message) {
    super(message);
  }
}

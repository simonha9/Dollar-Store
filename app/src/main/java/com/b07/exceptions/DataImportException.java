package com.b07.exceptions;

public class DataImportException extends Exception {


  private static final long serialVersionUID = -630464833934418348L;

  /**
   * Sets error exception message.
   */
  public DataImportException(String message) {
    super(message);
  }
}

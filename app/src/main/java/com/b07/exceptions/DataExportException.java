package com.b07.exceptions;

public class DataExportException extends Exception {

  private static final long serialVersionUID = -630664833934418348L;

  /**
   * Sets error exception message.
   */
  public DataExportException(String message) {
    super(message);
  }
}

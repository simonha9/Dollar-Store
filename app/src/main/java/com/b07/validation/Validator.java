package com.b07.validation;

import com.b07.exceptions.ValidationFailedException;

public interface Validator {

  /**
   * Interface for Validate Using Liskov because all implementations of these methods have
   * consistent behaviour.
   */
  void validate() throws ValidationFailedException;
}

package com.b07.validation;

import com.b07.exceptions.ValidationFailedException;

public class AddressValidator implements Validator {

  private String address;

  /**
   * Initializes address for AddressValidator.
   */
  public AddressValidator(String address) {
    this.address = address;
  }

  /**
   * Validates address.
   */
  @Override
  public void validate() throws ValidationFailedException {
    if (address != null && address.length() > 100) {
      throw new ValidationFailedException("Address length exceeds 100.");
    }
  }

}

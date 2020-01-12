package com.b07.validation;

import com.b07.exceptions.ValidationFailedException;

public class ItemNameValidator implements Validator {

  private String name;

  /**
   * Initializes name for ItemNameValidator.
   */
  public ItemNameValidator(String name) {
    this.name = name;
  }

  /**
   * Validates name of item.
   */
  @Override
  public void validate() throws ValidationFailedException {
    StringBuilder sb = new StringBuilder();
    if (name == null || name.length() > 63) {
      sb.append("Item name cannot be null and length must be less than 64 characters.");
    }
    if (sb.length() > 0) {
      throw new ValidationFailedException(sb.toString());

    }
  }

}

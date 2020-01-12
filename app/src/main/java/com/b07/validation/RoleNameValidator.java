package com.b07.validation;

import com.b07.enums.Roles;
import com.b07.exceptions.ValidationFailedException;

public class RoleNameValidator implements Validator {

  private String roleName;

  /**
   * Initializes roleName for RoleNameValidator.
   */
  public RoleNameValidator(String roleName) {
    this.roleName = roleName;
  }

  /**
   * Validates roleName.
   */
  @Override
  public void validate() throws ValidationFailedException {
    try {
      Roles.valueOf(roleName);
    } catch (IllegalArgumentException e) {
      throw new ValidationFailedException(roleName + " is not valid role name.");
    }
  }

}

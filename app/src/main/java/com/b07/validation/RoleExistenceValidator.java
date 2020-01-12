package com.b07.validation;


import android.database.SQLException;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.ValidationFailedException;

public class RoleExistenceValidator extends AbstractValidator implements Validator {

  private int roleId;

  /**
   * Initializes roleId for RoleExistenceValidator.
   */
  public RoleExistenceValidator(DatabaseDriverAndroid dbDriver, int roleId) {
    super(dbDriver);
    this.roleId = roleId;
  }

  /**
   * Validates roleId is from database.
   */
  @Override
  public void validate() throws ValidationFailedException {
    try {
      String roleName = DatabaseSelectHelper.getInstance(getDbDriver()).getRoleName(roleId);
      if (roleName == null) {
        throw new ValidationFailedException("Role id, " + roleId + " not found. ");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ValidationFailedException(e.getMessage());
    }

  }

}

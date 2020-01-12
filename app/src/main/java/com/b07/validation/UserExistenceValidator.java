package com.b07.validation;

import android.database.SQLException;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.ValidationFailedException;

public class UserExistenceValidator extends AbstractValidator implements Validator {

  private int userId;

  /**
   * Initializes userId for UserExistenceValidator.
   */
  public UserExistenceValidator(DatabaseDriverAndroid dbDriver, int userId) {
    super(dbDriver);
    this.userId = userId;
  }

  /**
   * Validates userId is from database.
   */
  @Override
  public void validate() throws ValidationFailedException {
    try {
      String name = DatabaseSelectHelper.getInstance(getDbDriver()).getUserName(userId);
      if (name == null) {
        throw new ValidationFailedException("User id, " + userId + " not found. ");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ValidationFailedException(e.getMessage());
    }

  }

}

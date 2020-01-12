package com.b07.validation;

import android.database.SQLException;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.ValidationFailedException;

public class AccountExistenceValidator extends AbstractValidator implements Validator {

  private int accountId;

  /**
   * Initializes userId for UserExistenceValidator.
   */
  public AccountExistenceValidator(DatabaseDriverAndroid dbDriver, int accountId) {
    super((dbDriver));
    this.accountId = accountId;
  }

  /**
   * Validates userId is from database.
   */
  @Override
  public void validate() throws ValidationFailedException {
    try {
      boolean exists = DatabaseSelectHelper.getInstance(getDbDriver()).accountExists(accountId);
      if (!exists) {
        throw new ValidationFailedException("Account id, " + accountId + " not found.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ValidationFailedException(e.getMessage());
    }
  }

}

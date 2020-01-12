package com.b07.security;

import android.content.Context;
import android.database.SQLException;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.User;

public class Authenticator {

  private Context context;

  public Authenticator(Context context) {
    this.context = context;
  }

  /**
   * Attempts to authenticate the user using the unhashed password with the hashed password stored
   * in the database.
   *
   * @param userId the user id of the user to authenticate.
   * @param password the password to compare the hashed password with.
   * @return true if password matched in the database, false otherwise.
   */
  public final boolean authenticate(int userId, String password) throws ValidationFailedException {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(context)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      User user = helper.getUserDetails(userId);
      if (user == null) {
        throw new ValidationFailedException("User does not exist.");
      }
      String dbPassword = DatabaseSelectHelper.getInstance(dbDriver).getPassword(userId);
      return PasswordHelpers.comparePassword(dbPassword, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

}

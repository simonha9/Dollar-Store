package com.b07.ui.login;

import android.content.Context;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.ValidationFailedException;
import com.b07.security.Authenticator;
import com.b07.users.User;

public class LoginModel {

  private Context context;

  public LoginModel(Context context) {
    this.context = context;
  }

  public boolean authenticate(int userId, String password) throws ValidationFailedException {
    return new Authenticator(context).authenticate(userId, password);
  }

  public User getUserDetail(int userId) {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(context)) {
      return DatabaseSelectHelper.getInstance(dbDriver).getUserDetails(userId);
    }
  }

}

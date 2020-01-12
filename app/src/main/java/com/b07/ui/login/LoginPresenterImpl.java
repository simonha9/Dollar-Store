package com.b07.ui.login;

import com.b07.exceptions.ValidationFailedException;
import com.b07.users.User;

public class LoginPresenterImpl implements LoginPresenter {

  private LoginView view;
  private LoginModel model;

  public LoginPresenterImpl(LoginView view) {
    this.view = view;
    model = new LoginModel(view.getApplicationContext());
  }

  @Override
  public User login(int userId, String password) {
    User user = null;
    try {
      if (model.authenticate(userId, password)) {
        user = model.getUserDetail(userId);
      } else {
        view.showError(
            "Either your user id or password is incorrect.");  // It is up to view on how to show errors.
      }
    } catch (ValidationFailedException e) {
      view.showError("Please enter a valid user id.");
    }
    return user;
  }
}

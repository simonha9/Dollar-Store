package com.b07.ui.login;

import com.b07.users.User;

public interface LoginPresenter {

  User login(int userId, String password);

}

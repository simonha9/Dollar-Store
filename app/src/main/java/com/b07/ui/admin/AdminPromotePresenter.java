package com.b07.ui.admin;

import com.b07.users.User;
import java.util.List;

public interface AdminPromotePresenter {

  List<User> getEmployeeList();

  boolean promoteEmployee(User employee);

}

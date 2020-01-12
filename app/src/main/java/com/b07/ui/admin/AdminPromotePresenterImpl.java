package com.b07.ui.admin;

import com.b07.users.User;
import java.util.List;

public class AdminPromotePresenterImpl implements AdminPromotePresenter {

  private AdminView view;
  private AdminPromoteModel model;

  public AdminPromotePresenterImpl(AdminView view) {
    this.view = view;
    model = new AdminPromoteModel(view.getApplicationContext());
  }

  @Override
  public List<User> getEmployeeList() {
    return model.getAllEmployees();
  }

  @Override
  public boolean promoteEmployee(User employee) {
      if (!model.promoteEmployee(employee)) {
      view.showError("There was a problem promoting the employee. Please try again.");
      return false;
    }
    return true;
  }
}

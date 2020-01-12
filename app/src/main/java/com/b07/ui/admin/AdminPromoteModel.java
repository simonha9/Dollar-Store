package com.b07.ui.admin;

import android.content.Context;
import com.b07.services.AdminInterface;
import com.b07.users.Employee;
import com.b07.users.User;
import java.util.List;

public class AdminPromoteModel {

  private Context context;

  public AdminPromoteModel(Context context) {
    this.context = context;
  }

  public List<User> getAllEmployees() {
    AdminInterface adminInterface = new AdminInterface(context);
    return adminInterface.getAllEmployees();
  }

  public boolean promoteEmployee(User employee) {
    AdminInterface adminInterface = new AdminInterface(context);
    return adminInterface.promoteEmployee((Employee) employee);
  }
}

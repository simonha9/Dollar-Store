package com.b07.ui.db;


import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.enums.Roles;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.UserCreateFailedException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.services.AdminInterface;
import com.b07.services.InventoryInterface;

public class DbPresenterImpl implements DbPresenter {

  private DbActivityView view;

  public DbPresenterImpl(DbActivityView view) {
    this.view = view;

  }

  @Override
  public boolean initializeDbRequired() {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(view.getApplicationContext())) {
      return (DatabaseSelectHelper.getInstance(dbDriver).getRoleIds().size() < 3);
    }
  }

  @Override
  public void initializeDb() {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(view.getApplicationContext());
        AdminInterface adminInterface = new AdminInterface(view.getApplicationContext());
        InventoryInterface inventoryInterface = new InventoryInterface(
            view.getApplicationContext())) {
      DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
      DatabaseInsertHelper helper = DatabaseInsertHelper.getInstance(dbDriver);
      int id = 0;
      if (selectHelper.getRoleIdByName(Roles.ADMIN.toString()) < 0) {
        id = helper.insertRole(Roles.ADMIN.toString());
        view.showMessage("ADMIN Role created, assigned RoleId=" + id);
      }
      if (selectHelper.getRoleIdByName(Roles.CUSTOMER.toString()) < 0) {
        id = helper.insertRole(Roles.CUSTOMER.toString());
        view.showMessage("CUSTOMER Role created, assigned RoleId=" + id);
      }
      if (selectHelper.getRoleIdByName(Roles.EMPLOYEE.toString()) < 0) {
        id = helper.insertRole(Roles.EMPLOYEE.toString());
        view.showMessage("EMPLOYEE Role created, assigned RoleId=" + id);
      }

      id = adminInterface.createAdmin("admin", 19, "Admin Address", "admin");
      view.showMessage("Admin User created, assigned id=" + id);
      id = adminInterface.createEmployee("Simon", 19, "Simon's Address", "simon");
      view.showMessage("First user: simon created, assigned id=" + id);

      inventoryInterface.initializeInventory();
      view.showMessage("BB has been initialize with 3 Roles, Admin and First user and Items.");

    } catch (ValidationFailedException | DatabaseInsertException | UserCreateFailedException e) {
      e.printStackTrace();
      view.showMessage(e.getMessage());
    }
  }

  @Override
  public void importDb(String filename) {

  }

}

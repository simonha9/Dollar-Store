package com.b07.services;

import android.content.Context;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Inventory;
import com.b07.domain.Item;
import com.b07.enums.Roles;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.UserCreateFailedException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.Employee;
import com.b07.users.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeInterface extends BaseService {

  private Employee currentEmployee;
  private Inventory inventory;

  public EmployeeInterface(Context appContext) {
    super(appContext);
  }

  /**
   * Initializes employee and inventory for EmployeeInterface.
   */
  public EmployeeInterface(Context appContext, Employee employee, Inventory inventory) {
    super(appContext);
    setCurrentEmployee(employee);
    this.inventory = inventory;
  }

  /**
   * Initializes only inventory for EmployeeInterface.
   */
  public EmployeeInterface(Context appContext, Inventory inventory) {
    super(appContext);
    this.inventory = inventory;
  }

  public static int getInputId() throws IOException, IllegalArgumentException {
    System.out.println("Select an id from the menu.");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String inputId = bufferedReader.readLine();
    try {
      int id = Integer.parseInt(inputId);
      if (id <= 0) {
        throw new NumberFormatException();
      }
      return id;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("ID must be a number > 0.");
    }
  }

  /**
   * Checks id EmployeeInterface has employee.
   *
   * @return true if EmployeeInterface has employee, else false
   */
  public boolean hasCurrentEmployee() {
    return currentEmployee != null;
  }

  public void setCurrentEmployee(Employee currentEmployee) {
    if (currentEmployee.isAuthenticated()) {
      this.currentEmployee = currentEmployee;
    }
  }

  /**
   * Restocks quantity of an item.
   *
   * @return boolean
   */
  public boolean restockInventory(Item item, int quantity) {
    try {
      getDatabaseUpdateHelper().updateInventoryQuantity(quantity, item.getId());
      return true;
    } catch (ValidationFailedException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Creates customer.
   *
   * @return userId
   */
  public int createCustomer(String name, int age, String address, String password)
      throws UserCreateFailedException {
    try {
      int userId = getDatabaseInsertHelper().insertNewUser(name, age, address, password);
      int roleId = getDatabaseSelectHelper().getRoleIdByName(Roles.CUSTOMER.toString());
      getDatabaseInsertHelper().insertUserRole(userId, roleId);
      return userId;
    } catch (DatabaseInsertException | ValidationFailedException e) {
      throw new UserCreateFailedException(e.getMessage(), e);
    }
  }

  /**
   * Creates employee.
   *
   * @return userId
   */
  public int createEmployee(String name, int age, String address, String password)
      throws UserCreateFailedException {
    try {
      int userId = getDatabaseInsertHelper().insertNewUser(name, age, address, password);
      int roleId = getDatabaseSelectHelper().getRoleIdByName(Roles.EMPLOYEE.toString());
      getDatabaseInsertHelper().insertUserRole(userId, roleId);
      return userId;
    } catch (DatabaseInsertException | ValidationFailedException e) {
      throw new UserCreateFailedException(e.getMessage(), e);
    }
  }

  public void displayCurrentCustomers() throws SQLException {
    DatabaseSelectHelper helper = getDatabaseSelectHelper();
    int roleId = helper.getRoleIdByName(Roles.CUSTOMER.toString());
    List<Integer> customerIds = helper.getUsersByRole(roleId);
    List<User> customers = new ArrayList<>();
    System.out.println("ID\tName\tAge\tAddress");
    for (int custId : customerIds) {
      User customer = helper.getUserDetails(custId);
      customers.add(customer);
      System.out.println(customer.getId() + "\t" + customer.getName() + "\t" + customer.getAge()
          + "\t" + customer.getAddress());
    }
  }

}

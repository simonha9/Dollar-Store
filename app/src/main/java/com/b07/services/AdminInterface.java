package com.b07.services;

import android.content.Context;
import android.util.Log;

import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Item;
import com.b07.domain.Sale;
import com.b07.domain.SalesLog;
import com.b07.enums.Roles;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.UnauthenticatedException;
import com.b07.exceptions.UserCreateFailedException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.Admin;
import com.b07.users.Employee;
import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminInterface extends BaseService {

  private Admin admin;

  public AdminInterface(Context appContext) {
    super(appContext);
  }

  /**
   * Initializes admin for AdminInterface.
   */
  public AdminInterface(Context appContext, Admin admin) throws UnauthenticatedException {
    super(appContext);
    setAdmin(admin);
  }

  /**
   * Gets admin.
   *
   * @return the admin
   */
  public Admin getAdmin() {
    return admin;
  }

  /**
   * Sets admin.
   */
  public void setAdmin(Admin admin) throws UnauthenticatedException {
    if (admin.isAuthenticated()) {
      this.admin = admin;
    } else {
      throw new UnauthenticatedException("Admin User is not logged in");

    }
  }

  /**
   * Creates admin.
   *
   * @return userId
   */
  public int createAdmin(String name, int age, String address, String password)
      throws UserCreateFailedException {
    try {
      int userId = getDatabaseInsertHelper().insertNewUser(name, age, address, password);
      int roleId = getDatabaseSelectHelper().getRoleIdByName(Roles.ADMIN.toString());
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

  /**
   * Takes user who is currently an employee and promote to admin.
   *
   * @return boolean
   */
  public boolean promoteEmployee(Employee employee) {
    try {
      int roleId = getDatabaseSelectHelper().getRoleIdByName(Roles.ADMIN.toString());
      getDatabaseUpdateHelper().updateUserRole(roleId, employee.getId());
      return true;
    } catch (ValidationFailedException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void viewBooks() throws SQLException {
    System.out.println("\nRecords:");
    SalesLog log = getDatabaseSelectHelper().getItemizedSales();
    List<Sale> sales = log.getSales();
    showCustomerLog(sales);
    showItemRecords(sales);
    System.out.println("End of records.\n");
  }

  private void showItemRecords(List<Sale> sales) {
    Map<Item, Integer> itemMap = new HashMap<>();
    BigDecimal total = new BigDecimal("0");
    for (Sale sale : sales) {
      Map<Item, Integer> saleItemMap = sale.getItemMap();
      for (Item item : saleItemMap.keySet()) {
        int quantity = saleItemMap.get(item);
        if (itemMap.containsKey(item)) {
          quantity = itemMap.get(item) + quantity;
        }
        itemMap.put(item, quantity);
      }
      total = total.add(sale.getTotalPrice());
    }
    showItemLog(itemMap);
    System.out.println("TOTAL SALES: " + total.toString());
  }

  private void showItemLog(Map<Item, Integer> itemMap) {
    for (Item item : itemMap.keySet()) {
      System.out.println("Number " + item.getName() + " sold: " + itemMap.get(item));
    }
  }

  private void showCustomerLog(List<Sale> sales) {
    for (Sale sale : sales) {
      System.out.println("Customer: " + sale.getUser().getName());
      System.out.println("Purchase Number: " + sale.getId());
      System.out.println("Total Purchase Price: " + sale.getTotalPrice());

      Map<Item, Integer> saleItemMap = sale.getItemMap();
      System.out.print("Itemized Breakdown: ");
      for (Item item : saleItemMap.keySet()) {
        System.out.println(item.getName() + ": " + saleItemMap.get(item));
        System.out.print("\t\t");
      }
      System.out.println();
      System.out.println("-------------------------------------------------------------");
    }
  }

  public void displayInactiveAccounts(int custId) throws SQLException {
    List<Integer> inactiveAccounts = getDatabaseSelectHelper().getInactiveAccounts(custId);
    System.out.println("List of inactive accounts:");
    System.out.println(inactiveAccounts.toString());
  }

  public void displayActiveAccounts(int custId) throws SQLException {
    List<Integer> activeAccounts = getDatabaseSelectHelper().getActiveAccounts(custId);
    System.out.println("List of active accounts:");
    System.out.println(activeAccounts.toString());
  }

  public List<User> getAllEmployees() {
    List<User> users = new ArrayList<>();
    DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance((getDbDriver()));
    int roleId = selectHelper.getRoleIdByName(Roles.EMPLOYEE.toString());
    List<Integer> employeeIds = selectHelper.getUsersByRole(roleId);
    for (int id : employeeIds) {
      users.add(selectHelper.getUserDetails(id));
    }
    return users;
  }

  public SalesLog getSales() {
    DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance((getDbDriver()));
    return selectHelper.getItemizedSales();
  }

  public List<Integer> getInactiveAccounts(int custId) {
    return getDatabaseSelectHelper().getInactiveAccounts(custId);
  }

  public List<Integer> getActiveAccounts(int custId) {
    return getDatabaseSelectHelper().getActiveAccounts(custId);
  }
}

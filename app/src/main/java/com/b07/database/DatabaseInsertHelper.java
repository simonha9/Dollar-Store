package com.b07.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.validation.AccountExistenceValidator;
import com.b07.validation.AddressValidator;
import com.b07.validation.InventoryValidator;
import com.b07.validation.ItemValidator;
import com.b07.validation.ItemizedSalesValidator;
import com.b07.validation.RoleExistenceValidator;
import com.b07.validation.RoleNameValidator;
import com.b07.validation.SalesValidator;
import com.b07.validation.UserExistenceValidator;
import com.b07.validation.Validator;
import java.math.BigDecimal;

public class DatabaseInsertHelper extends DatabaseHelper {

  public DatabaseInsertHelper(DatabaseDriverAndroid dbDriver) {
    super(dbDriver);
  }

  public static DatabaseInsertHelper getInstance(DatabaseDriverAndroid dbDriver) {
    return new DatabaseInsertHelper(dbDriver);
  }

  /**
   * @param name - the new role to be added.
   * @return roleId
   */
  public int insertRole(String name)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new RoleNameValidator(name);
    validator.validate();
    return Math.toIntExact(dbDriver.insertRole(name));
  }

  /**
   * Inserts a user into the database.
   *
   * @return userId
   */
  public int insertNewUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new AddressValidator(address);
    validator.validate();
    int userId = Math.toIntExact(dbDriver.insertNewUser(name, age, address, password));
    return userId;
  }

  /**
   * Inserts a user into the database.
   *
   * @return userId
   */
  public int insertNewUserWithHashedPassword(String name, int age, String address,
      String hashedPassword)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new AddressValidator(address);
    validator.validate();
    int userId = Math.toIntExact(dbDriver.insertUser(name, age, address));
    insertHashedPassword(hashedPassword, userId);
    return userId;
  }

  private void insertHashedPassword(String hashedPassword, int userId) {
    SQLiteDatabase sqLiteDatabase = dbDriver.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("USERID", userId);
    contentValues.put("PASSWORD", hashedPassword);
    sqLiteDatabase.insert("USERPW", null, contentValues);
    sqLiteDatabase.close();
  }

  /**
   * Inserts a user role into the database.
   *
   * @return userRoleId
   */
  public int insertUserRole(int userId, int roleId)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator userValidator = new UserExistenceValidator(dbDriver, userId);
    userValidator.validate();
    Validator roleValidator = new RoleExistenceValidator(dbDriver, roleId);
    roleValidator.validate();

    int userRoleId = Math.toIntExact(dbDriver.insertUserRole(userId, roleId));
    return userRoleId;
  }

  /**
   * Inserts a user role into the database.
   *
   * @return userRoleId
   */
  public int insertUserRole_initialize(int userId, int roleId)
          throws DatabaseInsertException, SQLException {
    int userRoleId = Math.toIntExact(dbDriver.insertUserRole(userId, roleId));
    return userRoleId;
  }

  /**
   * Inserts an item into the database.
   *
   * @return item
   */
  public int insertItem(String name, BigDecimal price)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new ItemValidator(name, price);
    validator.validate();

    int itemId = Math.toIntExact(dbDriver.insertItem(name, price));
    return itemId;
  }

  /**
   * Inserts an inventory into the database.
   *
   * @return inventoryId
   */
  public int insertInventory(int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new InventoryValidator(dbDriver, itemId, quantity);
    validator.validate();
    int inventoryId = Math.toIntExact(dbDriver.insertInventory(itemId, quantity));
    return inventoryId;
  }

  /**
   * Inserts a sale into the database.
   *
   * @return saleId
   */
  public int insertSale(int userId, BigDecimal totalPrice)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new SalesValidator(dbDriver, userId, totalPrice);
    validator.validate();
    int saleId = Math.toIntExact(dbDriver.insertSale(userId, totalPrice));
    return saleId;
  }

  /**
   * Inserts an itemized sale into the database.
   *
   * @return itemizedId
   */
  public int insertItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new ItemizedSalesValidator(dbDriver, saleId, itemId, quantity);
    validator.validate();
    int itemizedId = Math.toIntExact(dbDriver.insertItemizedSale(saleId, itemId, quantity));
    return itemizedId;
  }

  /**
   * Inserts account record with given userId into db.
   */
  public int insertAccount(int userId)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new UserExistenceValidator(dbDriver, userId);
    validator.validate();
    return Math.toIntExact(dbDriver.insertAccount(userId, true));
  }

  /**
   * Inserts account line with given account id, item id and quantity.
   */
  public int insertAccountLine(int accountId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ValidationFailedException {
    Validator validator = new AccountExistenceValidator(dbDriver, accountId);
    validator.validate();
    int accountLine = Math.toIntExact(dbDriver.insertAccountLine(accountId, itemId, quantity));
    return accountLine;
  }
}

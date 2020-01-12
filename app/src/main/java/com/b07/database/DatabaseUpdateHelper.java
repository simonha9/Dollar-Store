package com.b07.database;

import android.database.SQLException;
import com.b07.domain.Item;
import com.b07.exceptions.ValidationFailedException;
import com.b07.validation.AccountExistenceValidator;
import com.b07.validation.AddressValidator;
import com.b07.validation.InventoryValidator;
import com.b07.validation.ItemExistenceValidator;
import com.b07.validation.ItemNameValidator;
import com.b07.validation.ItemPriceValidator;
import com.b07.validation.RoleExistenceValidator;
import com.b07.validation.RoleNameValidator;
import com.b07.validation.UserExistenceValidator;
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.util.Map;

public class DatabaseUpdateHelper extends DatabaseHelper {

  public DatabaseUpdateHelper(DatabaseDriverAndroid dbDriver) {
    super(dbDriver);
  }

  public static DatabaseUpdateHelper getInstance(DatabaseDriverAndroid dbDriver) {
    return new DatabaseUpdateHelper(dbDriver);
  }

  /**
   * Updates role name in database associated with id.
   *
   * @return complete
   */
  public boolean updateRoleName(String name, int id) throws SQLException,
      ValidationFailedException {
    Validator roleValidator = new RoleExistenceValidator(dbDriver, id);
    roleValidator.validate();
    Validator nameValidator = new RoleNameValidator(name);
    nameValidator.validate();

    boolean complete = dbDriver.updateRoleName(name, id);
    return complete;
  }

  /**
   * Updates name in database associated with user id.
   *
   * @return complete
   */
  public boolean updateUserName(String name, int userId) throws SQLException,
      ValidationFailedException {
    Validator validator = new UserExistenceValidator(dbDriver, userId);
    validator.validate();
    boolean complete = dbDriver.updateUserName(name, userId);
    return complete;
  }

  /**
   * Updates age in database associated with user id.
   *
   * @return complete
   */
  public boolean updateUserAge(int age, int userId) throws SQLException,
      ValidationFailedException {
    Validator validator = new UserExistenceValidator(dbDriver, userId);
    validator.validate();
    boolean complete = dbDriver.updateUserAge(age, userId);
    return complete;
  }

  /**
   * Updates address in database associated with user id.
   *
   * @return complete
   */
  public boolean updateUserAddress(String address, int userId)
      throws SQLException, ValidationFailedException {
    Validator userValidator = new UserExistenceValidator(dbDriver, userId);
    userValidator.validate();
    Validator addressValidator = new AddressValidator(address);
    addressValidator.validate();
    boolean complete = dbDriver.updateUserAddress(address, userId);
    return complete;
  }

  /**
   * Updates role id in database associated with user id.
   *
   * @return complete
   */
  public boolean updateUserRole(int roleId, int userId)
      throws SQLException, ValidationFailedException {
    Validator userValidator = new UserExistenceValidator(dbDriver, userId);
    userValidator.validate();
    Validator roleValidator = new RoleExistenceValidator(dbDriver, roleId);
    roleValidator.validate();

    boolean complete = dbDriver.updateUserRole(roleId, userId);
    return complete;
  }

  /**
   * Updates name in database associated with item id.
   *
   * @return complete
   */
  public boolean updateItemName(String name, int itemId)
      throws SQLException, ValidationFailedException {
    Validator itemValidator = new ItemExistenceValidator(dbDriver, itemId);
    itemValidator.validate();
    Validator nameValidator = new ItemNameValidator(name);
    nameValidator.validate();

    boolean complete = dbDriver.updateItemName(name, itemId);
    return complete;
  }

  /**
   * Updates price in database associated with item id.
   *
   * @return complete
   */
  public boolean updateItemPrice(BigDecimal price, int itemId) throws SQLException,
      ValidationFailedException {
    Validator itemValidator = new ItemExistenceValidator(dbDriver, itemId);
    itemValidator.validate();
    Validator priceValidator = new ItemPriceValidator(price);
    priceValidator.validate();

    boolean complete = dbDriver.updateItemPrice(price, itemId);
    return complete;
  }

  /**
   * Updates quantity in database associated with item id.
   *
   * @return complete
   */
  public boolean updateInventoryQuantity(int quantity, int itemId)
      throws SQLException, ValidationFailedException {
    Validator validator = new InventoryValidator(dbDriver, itemId, quantity);
    validator.validate();

    boolean complete = dbDriver.updateInventoryQuantity(quantity, itemId);
    return complete;
  }

  public void updateAccountStatus(int accountId) throws SQLException, ValidationFailedException {
    Validator validator = new AccountExistenceValidator(dbDriver, accountId);
    validator.validate();
    Map<Item, Integer> cartMap = DatabaseSelectHelper.getInstance(dbDriver)
        .getAccountDetails(accountId);
    if (cartMap.isEmpty()) {
      System.out.println("Account summary does not exist.");
      throw new ValidationFailedException("Account summary does not exist.");
    }
    dbDriver.updateAccountStatus(accountId, false);
  }
}

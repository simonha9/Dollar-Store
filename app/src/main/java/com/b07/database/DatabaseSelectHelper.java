package com.b07.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.b07.domain.Inventory;
import com.b07.domain.Item;
import com.b07.domain.Sale;
import com.b07.domain.SalesLog;
import com.b07.domain.impl.InventoryImpl;
import com.b07.domain.impl.ItemImpl;
import com.b07.domain.impl.SaleImpl;
import com.b07.domain.impl.SalesLogImpl;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.User;
import com.b07.users.UserFactory;
import com.b07.validation.UserExistenceValidator;
import com.b07.validation.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseSelectHelper extends DatabaseHelper {

  public DatabaseSelectHelper(DatabaseDriverAndroid dbDriver) {
    super(dbDriver);
  }

  public static DatabaseSelectHelper getInstance(DatabaseDriverAndroid dbDriver) {
    return new DatabaseSelectHelper(dbDriver);
  }

  public static Item lookupItemByName(List<Item> items, String itemName) {
    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Gets all role ids.
   *
   * @return ids
   */
  public List<Integer> getRoleIds() throws SQLException {
    List<Integer> ids = new ArrayList<>();
    try (Cursor cursor = dbDriver.getRoles()) {
      while (cursor.moveToNext()) {
        ids.add(cursor.getInt(cursor.getColumnIndex("ID")));
      }
    }
    return ids;
  }

  /**
   * Gets role id associated with name.
   *
   * @return results.getInt(" ID ")
   */
  public Integer getRoleIdByName(String name) throws SQLException {
    try (Cursor cursor = dbDriver.getRoles()) {
      while (cursor.moveToNext()) {
        String roleName = cursor.getString(cursor.getColumnIndex("NAME"));
        if (name.equalsIgnoreCase(roleName)) {
          return cursor.getInt(cursor.getColumnIndex("ID"));
        }
      }
    }
    return -1;
  }

  /**
   * Gets role name associated with role id.
   *
   * @return role
   */
  public String getRoleName(int roleId) throws SQLException {
    String role = dbDriver.getRole(roleId);
    return role;
  }

  public int getUserRoleId(int userId) throws SQLException {
    int roleId = dbDriver.getUserRole(userId);
    return roleId;
  }

  /**
   * Gets list of user ids associated with role id.
   *
   * @return userIds
   */
  public List<Integer> getUsersByRole(int roleId) throws SQLException {
    List<Integer> userIds = new ArrayList<>();
    try (Cursor cursor = dbDriver.getUsersByRole(roleId)) {
      while (cursor.moveToNext()) {
        userIds.add(cursor.getInt(cursor.getColumnIndex("USERID")));
      }
    }
    return userIds;
  }

  /**
   * Gets users in database.
   *
   * @return users
   */
  public List<User> getUsersDetails() throws SQLException {
    List<User> users = new ArrayList<>();
    try (Cursor cursor = dbDriver.getUsersDetails()) {
      while (cursor.moveToNext()) {
        User user = mapResultSetToUser(cursor);
        users.add(user);
      }
    }
    return users;
  }

  /**
   * Gets user associated with Result Set.
   *
   * @return user
   */
  private User mapResultSetToUser(Cursor cursor) throws SQLException {
    int id = cursor.getInt(cursor.getColumnIndex("ID"));
    int roleId = getUserRoleId(id);
    String roleName = getRoleName(roleId);
    User user = UserFactory.buildUser(roleName);
    user.setAddress(cursor.getString(cursor.getColumnIndex("ADDRESS")));
    user.setAge(cursor.getInt(cursor.getColumnIndex("AGE")));
    user.setId(id);
    user.setRoleId(roleId);
    user.setAuthenticated(false);
    user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
    return user;
  }

  /**
   * Gets user associated with user id.
   *
   * @return mapResultSetToUser(results)
   */
  public User getUserDetails(int userId) throws SQLException {
    try (Cursor cursor = dbDriver.getUserDetails(userId)) {
      while (cursor.moveToNext()) {
        return mapResultSetToUser(cursor);
      }
    }
    return null;
  }

  /**
   * Gets user name associated with user id.
   *
   * @return results.getString(" NAME ")
   */
  public String getUserName(int userId) throws SQLException {

    try (Cursor cursor = dbDriver.getUserDetails(userId)) {
      while (cursor.moveToNext()) {
        return cursor.getString(cursor.getColumnIndex("NAME"));
      }
    }
    return null;
  }

  /**
   * Gets password associated with user id.
   *
   * @return password
   */
  public String getPassword(int userId) throws SQLException {
    String password = dbDriver.getPassword(userId);
    return password;
  }

  /**
   * Gets all items.
   *
   * @return iems
   */
  public List<Item> getAllItems() throws SQLException {

    try (Cursor cursor = dbDriver.getAllItems()) {
      List<Item> items = new ArrayList<>();
      while (cursor.moveToNext()) {
        Item item = new ItemImpl();
        mapResultSetToItem(cursor, item);
        items.add(item);
      }
      return items;
    }
  }

  /**
   * Sets item details associated with result set.
   */
  private void mapResultSetToItem(Cursor cursor, Item item) throws SQLException {

    item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
    item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
    item.setPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
  }

  /**
   * Gets item associated with item id.
   *
   * @return item
   */
  public Item getItem(int itemId) throws SQLException {
    try (Cursor cursor = dbDriver.getItem(itemId)) {
      while (cursor.moveToNext()) {
        Item item = new ItemImpl();
        mapResultSetToItem(cursor, item);
        return item;
      }
    }
    return null;
  }

  /**
   * Gets inventory.
   *
   * @return inventory
   */
  public Inventory getInventory() throws SQLException {
    Inventory inventory = new InventoryImpl();
    try (Cursor cursor = dbDriver.getInventory()) {
      while (cursor.moveToNext()) {
        Item item = getItem(cursor.getInt(cursor.getColumnIndex("ITEMID")));
        int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
        inventory.updateMap(item, quantity);
      }
    }
    return inventory;
  }

  /**
   * Gets inventory quantity associated with item id.
   *
   * @return quantity
   */
  public int getInventoryQuantity(int itemId) {
    int quantity = dbDriver.getInventoryQuantity(itemId);
    return quantity;
  }

  /**
   * Gets log of sales.
   *
   * @return salesLog
   */
  public SalesLog getSales() throws SQLException {
    SalesLog salesLog = new SalesLogImpl();
    try (Cursor cursor = dbDriver.getSales()) {
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl();
        mapResultSetToSale(cursor, sale);
        salesLog.addSale(sale);
      }
      return salesLog;
    }
  }

  /**
   * Gets sale associated with sale id.
   *
   * @return sale
   */
  public Sale getSaleById(int saleId) throws SQLException {

    try (Cursor cursor = dbDriver.getSaleById(saleId)) {
      Sale sale = null;
      while (cursor.moveToNext()) {
        sale = new SaleImpl();
        mapResultSetToSale(cursor, sale);
      }
      return sale;
    }
  }

  /**
   * Sets sale information associated with result set.
   */
  private void mapResultSetToSale(Cursor cursor, Sale sale) throws SQLException {
    sale.setId(cursor.getInt(cursor.getColumnIndex("ID")));
    User user = getUserDetails(cursor.getInt(cursor.getColumnIndex("USERID")));
    sale.setUser(user);
    sale.setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
  }

  /**
   * Gets all sales associated with user id.
   *
   * @return sales
   */
  public List<Sale> getSalesToUser(int userId) throws SQLException {
    try (Cursor cursor = dbDriver.getSalesToUser(userId)) {
      List<Sale> sales = new ArrayList<>();
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl();
        mapResultSetToSale(cursor, sale);
        sales.add(sale);
      }
      return sales;
    }
  }

  /**
   * Gets itemized sale associated with sale id.
   *
   * @return sale
   */
  public Sale getItemizedSaleById(int saleId) throws SQLException {
    try (Cursor cursor = dbDriver.getItemizedSaleById(saleId)) {
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl();
        mapResultSetToItemizedSale(cursor, sale);
        return sale;
      }
      return null;
    }
  }

  /**
   * Sets sale information associated with result set.
   */
  private void mapResultSetToItemizedSale(Cursor cursor, Sale sale) throws SQLException {
    sale.setId(cursor.getInt(cursor.getColumnIndex("SALEID")));
    Item item = getItem(cursor.getInt(cursor.getColumnIndex("ITEMID")));
    Integer quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
    sale.getItemMap().put(item, quantity);
  }

  /**
   * Gets log of itemized sales.
   *
   * @return salesLog
   */
  public SalesLog getItemizedSales() throws SQLException {
    try (Cursor cursor = dbDriver.getItemizedSales()) {
      SalesLog salesLog = new SalesLogImpl();
      ArrayList<Integer> saleIdList = new ArrayList<>();
      Sale sale = null;
      while (cursor.moveToNext()) {
        int newSaleId = cursor.getInt(cursor.getColumnIndex("SALEID"));
        if (!saleIdList.contains(newSaleId)) {
          sale = getSaleById(newSaleId);
          salesLog.addSale(sale);
          saleIdList.add(newSaleId);
        }
        mapResultSetToItemizedSale(cursor, sale);
      }
      return salesLog;
    }
  }

  /**
   * Gets map of restored cart associated with account id.
   *
   * @return cartHistory
   */
  public Map<Item, Integer> getAccountDetails(int accountId) throws SQLException {
    try (Cursor cursor = dbDriver.getAccountDetails(accountId)) {
      Map<Item, Integer> cartHistory = new HashMap<>();
      while (cursor.moveToNext()) {
        int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
        Item item = getItem(itemId);
        int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
        cartHistory.put(item, quantity);
      }
      return cartHistory;
    }
  }

  /**
   * @return Cursor resultset
   */
  protected Cursor getAccount(int accountId) {
    SQLiteDatabase sqLiteDatabase = dbDriver.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ACCOUNT WHERE ID = ?;",
        new String[]{String.valueOf(accountId)});
    return cursor;
  }

  /**
   * Checks whether given account id exists in ACCOUNT table.
   */
  public boolean accountExists(int accountId) throws SQLException {
    try (Cursor cursor = getAccount(accountId)) {
      return cursor.moveToFirst();
    }
  }

  /**
   * Gets list of active accounts associated with given user id.
   */
  public List<Integer> getActiveAccounts(int userId) throws SQLException {
    List<Integer> accounts = new ArrayList<>();
    try (Cursor cursor = dbDriver.getUserActiveAccounts(userId)) {
      while (cursor.moveToNext()) {
        int accountId = cursor.getInt(cursor.getColumnIndex("ID"));
        accounts.add(accountId);
      }
      return accounts;
    }
  }

  /**
   * Gets account id associated with given user id.
   */
  public List<Integer> getInactiveAccounts(int userId) throws SQLException {
    List<Integer> accounts = new ArrayList<>();
    try (Cursor cursor = dbDriver.getUserInactiveAccounts(userId)) {
      while (cursor.moveToNext()) {
        int accountId = cursor.getInt(cursor.getColumnIndex("ID"));
        accounts.add(accountId);
      }
      return accounts;
    }
  }

}

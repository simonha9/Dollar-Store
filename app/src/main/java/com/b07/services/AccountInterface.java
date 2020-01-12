package com.b07.services;

import android.content.Context;
import android.database.SQLException;
import com.b07.database.DatabaseInsertHelper;
import com.b07.domain.Item;
import com.b07.exceptions.AccountCreateFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.Customer;
import java.util.List;
import java.util.Map;

public class AccountInterface extends BaseService {

  private Customer customer;

  public AccountInterface(Context appContext, Customer customer) {
    super(appContext);
    this.customer = customer;
  }

  public int createAccount() throws AccountCreateFailedException {
    try {
      return getDatabaseInsertHelper().insertAccount(customer.getId());
    } catch (DatabaseInsertException | SQLException | ValidationFailedException e) {
      throw new AccountCreateFailedException(
        "The requested account could not be created: " + e.getMessage());
    }
  }

  public Map<Item, Integer> getAccountSummary() throws SQLException {
    return getDatabaseSelectHelper().getAccountDetails(customer.getAccountId());
  }

  public List<Integer> getAccounts(int userId) throws SQLException {
    return getDatabaseSelectHelper().getActiveAccounts(userId);
  }

  public void updateAccountSummary(Map<Item, Integer> cartItemMap)
    throws DatabaseInsertException, SQLException, ValidationFailedException {
    int acctId = customer.getAccountId();
    clearAccountSummary(acctId);
    DatabaseInsertHelper helper = getDatabaseInsertHelper();
    for (Item item : cartItemMap.keySet()) {
      helper.insertAccountLine(acctId, item.getId(), cartItemMap.get(item));
    }
  }

  private void clearAccountSummary(int accountId) throws SQLException {
    //check if the account line exists - if it does exist then update the row
    Map<Item, Integer> existingCartMap = getDatabaseSelectHelper().getAccountDetails(accountId);
    if (!existingCartMap.isEmpty()) {
      getDatabaseDeleteHelper().deleteAccountSummaries(accountId);
    }
  }

  public boolean deactivateAccount(int accountId) {
    try {
      getDatabaseUpdateHelper().updateAccountStatus(accountId);
      return true;
    } catch (SQLException | ValidationFailedException e) {
      System.out.println("Could not update Account: " + e.getMessage());
      return false;
    }
  }
}

package com.b07.ui.customer;

import android.content.Context;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Item;
import java.util.List;
import java.util.Map;

public class CustomerExistingAcctModel {

  private Context context;

  public CustomerExistingAcctModel(Context context) {
    this.context = context;
  }

  public List<Integer> getActiveAccounts(int userId) {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(context)) {
      return DatabaseSelectHelper.getInstance(dbDriver).getActiveAccounts(userId);
    }
  }

  public Map<Item, Integer> getSelectedAccount(int accountId) {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(context)) {
      return DatabaseSelectHelper.getInstance(dbDriver).getAccountDetails(accountId);
    }
  }
}

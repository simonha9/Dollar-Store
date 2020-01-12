package com.b07.exporter;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Account;
import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.users.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountExporter implements DbRecordExporter<Account> {

  private Context appContext;

  public AccountExporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public List<Account> exportRecords() throws DataExportException {
    List<Account> accounts = new ArrayList<>();
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      for (User user : helper.getUsersDetails()) {
        Account account = new Account();
        account.setUserId(user.getId());
        boolean hasAcount = false;
        for (Integer acctId : helper.getActiveAccounts(user.getId())) {
          Map<Item, Integer> acctSummary = helper.getAccountDetails(acctId);
          account.setActive(true);
          account.setItemMap(acctSummary);
          hasAcount = true;
        }
        for (Integer acctId : helper.getInactiveAccounts(user.getId())) {
          Map<Item, Integer> acctSummary = helper.getAccountDetails(acctId);
          account.setActive(false);
          account.setItemMap(acctSummary);
          hasAcount = true;
        }
        if (hasAcount) {
          accounts.add(account);
        }
      }

    }
    Log.d("AccountExporter", "exported:" + accounts.size());
    return accounts;
  }
}

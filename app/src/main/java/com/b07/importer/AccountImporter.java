package com.b07.importer;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDeleteHelper;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Account;
import com.b07.domain.Item;
import com.b07.exceptions.DataImportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import java.util.List;

public class AccountImporter implements DbRecordImporter {

  private Context appContext;

  public AccountImporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public void importRecords(List<Object> data) throws DataImportException {
    Log.d("AccountImporter", "Starting");
    int count = 0;
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
      DatabaseInsertHelper insertHelper = DatabaseInsertHelper.getInstance(dbDriver);
      DatabaseDeleteHelper deleteHelper = DatabaseDeleteHelper.getInstance(dbDriver);
      List<Item> existingItems = selectHelper.getAllItems();
      for (Object entity : data) {
        if (entity instanceof Account) {
          Account inAcct = (Account) entity;
          count++;
          boolean acctExists = selectHelper.accountExists(inAcct.getId());
          int acctIdToUse = -1;
          if (!acctExists) {
            acctIdToUse = insertHelper.insertAccount(inAcct.getUserId());
          } else {
            acctIdToUse = inAcct.getId();
            deleteHelper.deleteAccountSummaries(acctIdToUse); // no update of Acct Summary
          }
          for (Item inItem : inAcct.getItemMap().keySet()) {
            Item existItem = DatabaseSelectHelper.lookupItemByName(existingItems, inItem.getName());
            int itemId = -1;
            if (existItem == null) { // should not happen
              itemId = insertHelper.insertItem(inItem.getName(), inItem.getPrice());
            } else {
              itemId = existItem.getId();
            }
            insertHelper.insertAccountLine(acctIdToUse, itemId, inAcct.getItemMap().get(inItem));
          }
        }
      }
    } catch (ValidationFailedException | DatabaseInsertException e) {
      throw new DataImportException("Cannot import Account: " + e.getMessage());
    }
    Log.d("AccountImporter", "Finished import " + count + " records");
  }
}

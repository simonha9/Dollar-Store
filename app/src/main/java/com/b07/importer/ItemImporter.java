package com.b07.importer;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.domain.Item;
import com.b07.exceptions.DataImportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import java.util.List;

public class ItemImporter implements DbRecordImporter {

  private Context appContext;

  public ItemImporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public void importRecords(List<Object> data) throws DataImportException {
    Log.d("ItemImporter", "Starting");
    int count = 0;
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
      DatabaseInsertHelper insertHelper = DatabaseInsertHelper.getInstance(dbDriver);
      DatabaseUpdateHelper updateHelper = DatabaseUpdateHelper.getInstance(dbDriver);
      List<Item> existingItems = selectHelper.getAllItems();
      for (Object entity : data) {
        if (entity instanceof Item) {
          Item inItem = (Item) entity;
          count++;
          Item existItem = DatabaseSelectHelper.lookupItemByName(existingItems, inItem.getName());
          if (existItem == null) {
            insertHelper.insertItem(inItem.getName(), inItem.getPrice());
          } else if (!inItem.getPrice().equals(existItem.getPrice())) {
            updateHelper.updateItemPrice(inItem.getPrice(), existItem.getId());
          }
        }
      }
    } catch (ValidationFailedException | DatabaseInsertException e) {
      throw new DataImportException("Cannot import Item: " + e.getMessage());
    }
    Log.d("ItemImporter", "Finished import " + count + " records");
  }

}

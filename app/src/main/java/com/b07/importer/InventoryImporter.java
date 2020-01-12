package com.b07.importer;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.domain.Inventory;
import com.b07.domain.Item;
import com.b07.exceptions.DataImportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryImporter implements DbRecordImporter {

  private Context appContext;

  public InventoryImporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public void importRecords(List<Object> data) throws DataImportException {
    Log.d("InventoryImporter", "Starting");
    int count = 0;
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
      DatabaseInsertHelper insertHelper = DatabaseInsertHelper.getInstance(dbDriver);
      DatabaseUpdateHelper updateHelper = DatabaseUpdateHelper.getInstance(dbDriver);
      List<Item> existingItems = selectHelper.getAllItems();
      for (Object entity : data) {
        if (entity instanceof Inventory) {
          Inventory inInventory = (Inventory) entity;
          count++;
          Inventory existInv = selectHelper.getInventory();
          if (inInventory.getItemMap() == null) {
            inInventory.setItemMap(new HashMap<Item, Integer>());
          }
          if (existInv == null) {
            for (Item inItem : inInventory.getItemMap().keySet()) {
              Item itemDefinition = DatabaseSelectHelper
                  .lookupItemByName(existingItems, inItem.getName());
              int itemId = -1;
              if (itemDefinition == null) { // should not happen
                itemId = insertHelper.insertItem(inItem.getName(), inItem.getPrice());
              } else {
                itemId = itemDefinition.getId();
              }
              insertHelper.insertInventory(itemId, inInventory.getItemMap().get(inItem));
            }
          } else {
            List<Item> existInvItems = new ArrayList<>(inInventory.getItemMap().keySet());
            for (Item inItem : inInventory.getItemMap().keySet()) {
              Item existItem = DatabaseSelectHelper
                  .lookupItemByName(existInvItems, inItem.getName());
              int itemId = -1;
              if (existItem == null) {
                Item itemDefinition = DatabaseSelectHelper
                    .lookupItemByName(existingItems, inItem.getName());
                itemId = itemDefinition.getId();
              } else {
                itemId = existItem.getId();
              }
              updateHelper.updateInventoryQuantity(inInventory.getItemMap().get(inItem), itemId);
            }
          }
        }
      }
    } catch (ValidationFailedException | DatabaseInsertException e) {
      throw new DataImportException("Cannot import Inventory: " + e.getMessage());
    }
    Log.d("InventoryImporter", "Finished import " + count + " records");
  }
}

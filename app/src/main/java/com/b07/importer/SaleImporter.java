package com.b07.importer;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.domain.Item;
import com.b07.domain.Sale;
import com.b07.exceptions.DataImportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import java.util.ArrayList;
import java.util.List;

public class SaleImporter implements DbRecordImporter {

  private Context appContext;

  public SaleImporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public void importRecords(List<Object> data) throws DataImportException {
    Log.d("SaleImporter", "Starting");
    int count = 0;
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
      DatabaseInsertHelper insertHelper = DatabaseInsertHelper.getInstance(dbDriver);
      DatabaseUpdateHelper updateHelper = DatabaseUpdateHelper.getInstance(dbDriver);
      List<Item> existingItems = selectHelper.getAllItems();
      for (Object entity : data) {
        if (entity instanceof Sale) {
          Sale inSale = (Sale) entity;
          count++;
          Sale existSale = selectHelper.getItemizedSaleById(inSale.getId());
          List<Item> itemsToUse = existingItems;
          int saleIdToUse = -1;
          if (existSale == null) {
            saleIdToUse = insertHelper.insertSale(inSale.getId(), inSale.getTotalPrice());

          } else {
            itemsToUse = new ArrayList<>(existSale.getItemMap().keySet());
            saleIdToUse = inSale.getId();
          }
// No upddate of ItemizedSale
          for (Item inItem : inSale.getItemMap().keySet()) {
            Item existItem = DatabaseSelectHelper.lookupItemByName(itemsToUse, inItem.getName());
            int itemId = -1;
            if (existItem == null) {
              Item itemDefinition = DatabaseSelectHelper
                  .lookupItemByName(existingItems, inItem.getName());
              itemId = itemDefinition.getId();
            } else {
              itemId = existItem.getId();
            }
            insertHelper.insertItemizedSale(saleIdToUse, itemId, inSale.getItemMap().get(inItem));
          }
        }
      }
    } catch (ValidationFailedException | DatabaseInsertException e) {
      throw new DataImportException("Cannot import Sale: " + e.getMessage());
    }
    Log.d("SaleImporter", "Finished import " + count + " records");
  }
}

package com.b07.exporter;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Inventory;
import com.b07.exceptions.DataExportException;
import java.util.ArrayList;
import java.util.List;

public class InventoryExporter implements DbRecordExporter<Inventory> {

  private Context appContext;

  public InventoryExporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public List<Inventory> exportRecords() throws DataExportException {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      List<Inventory> result = new ArrayList<>();
      Inventory inventory = helper.getInventory();
      if (inventory.getItemMap().size() > 0) {
        result.add(inventory);
      }
      Log.d("InventoryExporter", "exported:" + result.size() + " item count="
          + inventory.getItemMap().size());
      return result;

    }
  }
}

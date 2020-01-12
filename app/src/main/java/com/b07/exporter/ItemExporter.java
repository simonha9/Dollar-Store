package com.b07.exporter;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import java.util.List;

public class ItemExporter implements DbRecordExporter<Item> {

  private Context appContext;

  public ItemExporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public List<Item> exportRecords() throws DataExportException {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      List<Item> result = helper.getAllItems();
      Log.d("ItemExporter", "exported:" + result.size());
      return result;
    }
  }
}

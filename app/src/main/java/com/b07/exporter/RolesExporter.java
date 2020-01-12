package com.b07.exporter;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.DataExportException;
import java.util.ArrayList;
import java.util.List;

public class RolesExporter implements DbRecordExporter<String> {

  private Context appContext;

  public RolesExporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public List<String> exportRecords() throws DataExportException {
    List<String> entities = new ArrayList<>();
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      for (Integer roleId : helper.getRoleIds()) {
        entities.add(helper.getRoleName(roleId));
      }
    }
    Log.d("RolesExporter", "exported:" + entities.size());
    return entities;
  }
}

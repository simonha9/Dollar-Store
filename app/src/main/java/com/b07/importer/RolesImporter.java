package com.b07.importer;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.DataImportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import java.util.List;

public class RolesImporter implements DbRecordImporter {

  private Context appContext;

  public RolesImporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public void importRecords(List<Object> data) throws DataImportException {
    Log.d("RolesImporter", "Starting");
    int count = 0;
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
      for (Object entity : data) {
        if (entity instanceof String) {
          count++;
          int id = selectHelper.getRoleIdByName(entity.toString());
          if (id < 0) {
            DatabaseInsertHelper.getInstance(dbDriver).insertRole(entity.toString());
          }
        }
      }

    } catch (ValidationFailedException | DatabaseInsertException e) {
      throw new DataImportException("Cannot import Role :" + e.getMessage());
    }
    Log.d("RolesImporter", "Finished import " + count + " records");
  }
}

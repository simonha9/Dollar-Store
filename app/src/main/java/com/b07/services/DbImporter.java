package com.b07.services;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.DataImportException;
import com.b07.importer.DbRecordImporter;
import com.b07.importer.DbRecordImporterFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class DbImporter extends BaseService {

  private static final String DATABASE_BACKUP_NAME = "inventorymgmt_bk.ser";

  public DbImporter(Context appContext) {
    super(appContext);
  }

  public Object deserialize(String filename) throws IOException, ClassNotFoundException {
    try (FileInputStream fis = getAppContext().openFileInput(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fis)) {
      return objectInputStream.readObject();
    }
  }

  public void backup() throws DataExportException {
    Log.d("DbImporter", "Backup");
    DbExporter exporter = new DbExporter(getAppContext());
    exporter.exportData(DATABASE_BACKUP_NAME);
  }

  public void restore() throws DataImportException {
    Log.d("DbImporter", "restore");
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(getAppContext())) {
      dbDriver.onUpgrade(dbDriver.getWritableDatabase(), 0, 1);
    }
    try {
      List<Object> data = (List<Object>) deserialize(DATABASE_BACKUP_NAME);
      List<DbRecordImporter> importers = DbRecordImporterFactory
          .getDbRecordImporters(getAppContext());
      for (DbRecordImporter importer : importers) {
        importer.importRecords(data);
      }
    } catch (IOException | ClassNotFoundException e) {
      throw new DataImportException("Cannot restore data:" + e.getMessage());
    }
  }

  public void importData(String filename) throws DataImportException {
    try {
      List<Object> data = (List<Object>) deserialize(filename);
      Log.d("importData " + filename, "Finished deserialize" + data.size() + " Records");
      List<DbRecordImporter> importers = DbRecordImporterFactory
          .getDbRecordImporters(getAppContext());
      backup();
      for (DbRecordImporter importer : importers) {
        try {
          importer.importRecords(data);
        } catch (DataImportException e) {
          restore();
          throw e;
        }
      }
    } catch (IOException | ClassNotFoundException e) {
      throw new DataImportException("Cannot import data:" + e.getMessage());
    } catch (DataExportException e) {
      throw new DataImportException("Cannot backup data:" + e.getMessage());
    }
    Log.d("importData " + filename, "Finished");
  }
}

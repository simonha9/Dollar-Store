package com.b07.services;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseHelper;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.exporter.DbRecordExporter;
import com.b07.exporter.DbRecordExporterFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DbExporter extends BaseService {


  public DbExporter(Context appContext) {
    super(appContext);
  }

  public void serialize(Object data, String filename) throws IOException {
    try (FileOutputStream outputStream = getAppContext()
        .openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
      out.writeObject(data);
      out.flush();
    }
  }


  public void exportData(String filename) throws DataExportException {
    try {
      Log.d("Export filename=" + filename, "Started");
      List<DbRecordExporter> exporterList = DbRecordExporterFactory
          .getDbRecordExporters(getAppContext());
      List<Serializable> data = new ArrayList<>();
      for (DbRecordExporter recordExporter : exporterList) {
        data.addAll(recordExporter.exportRecords());
      }
      serialize(data, DatabaseHelper.SERIALIZED_DATABASE_NAME);
      Log.d("Export filename=" + filename, "Finished export" + data.size() + " Records");
    } catch (IOException e) {
      throw new DataExportException("Cannot export data:" + e.getMessage());
    }
  }
}

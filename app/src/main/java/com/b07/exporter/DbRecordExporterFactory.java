package com.b07.exporter;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DbRecordExporterFactory {

  public static List<DbRecordExporter> getDbRecordExporters(Context context) {
    List<DbRecordExporter> exporterList = new ArrayList<>();
    exporterList.add(new RolesExporter(context));
    exporterList.add(new UserExporter(context));
    exporterList.add(new ItemExporter(context));
    exporterList.add(new InventoryExporter(context));
    exporterList.add(new AccountExporter(context));
    exporterList.add(new SaleExporter(context));

    return exporterList;
  }
}

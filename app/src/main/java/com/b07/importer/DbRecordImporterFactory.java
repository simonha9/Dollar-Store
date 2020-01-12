package com.b07.importer;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DbRecordImporterFactory {

  public static List<DbRecordImporter> getDbRecordImporters(Context context) {
    List<DbRecordImporter> importers = new ArrayList<>();
    importers.add(new RolesImporter(context));
    importers.add(new UserImporter(context));
    importers.add(new ItemImporter(context));
    importers.add(new InventoryImporter(context));
    importers.add(new AccountImporter(context));
    importers.add(new SaleImporter(context));

    return importers;
  }
}

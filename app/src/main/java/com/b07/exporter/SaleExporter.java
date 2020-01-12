package com.b07.exporter;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Sale;
import com.b07.domain.SalesLog;
import com.b07.exceptions.DataExportException;
import java.util.List;

public class SaleExporter implements DbRecordExporter<Sale> {

  private Context appContext;

  public SaleExporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public List<Sale> exportRecords() throws DataExportException {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      SalesLog salesLog = helper.getSales();
      List<Sale> sales = salesLog.getSales();
      for (Sale sale : sales) {
        Sale saleItems = helper.getItemizedSaleById(sale.getId());
        sale.setItemMap(saleItems.getItemMap());
      }
      Log.d("SaleExporter", "exported:" + sales.size());
      return sales;
    }
  }
}

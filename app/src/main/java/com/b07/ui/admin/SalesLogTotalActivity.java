package com.b07.ui.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.domain.Item;
import com.b07.salesapplication.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesLogTotalActivity extends AppCompatActivity {

  private ListView lstTotalBreakdown;

  @Override
  public void onCreate(Bundle savedBundleInstance) {
    super.onCreate(savedBundleInstance);
    setContentView(R.layout.admin_sales_log_breakdown);

    lstTotalBreakdown = findViewById(R.id.lstTotalBreakdown);
    setupAdaptors();
  }

  private void setupAdaptors() {
    Map<Item, Integer> totalSalesMap = (HashMap<Item, Integer>) getIntent()
        .getSerializableExtra("totalItemizedSales");
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, mapItemizedSalesToString(totalSalesMap));
    lstTotalBreakdown.setAdapter(arrayAdapter);
  }

  private List<String> mapItemizedSalesToString(Map<Item, Integer> totalSalesMap) {
    List<String> totalItemizedList = new ArrayList<>();
    for (Map.Entry<Item, Integer> entry : totalSalesMap.entrySet()) {
      totalItemizedList.add("Total " + entry.getKey().getName() + " sold: " + entry.getValue());
    }
    return totalItemizedList;
  }

}

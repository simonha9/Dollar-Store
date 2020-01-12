package com.b07.ui.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.domain.Item;
import com.b07.salesapplication.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesLogDetailActivity extends AppCompatActivity {

  private TextView txtCustomerName;
  private TextView txtSaleId;
  private TextView txtTotalPrice;
  private ListView lstItemBreakdown;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_sales_log_details);

    txtCustomerName = findViewById(R.id.detailedCustomerName);
    txtSaleId = findViewById(R.id.detailedSaleId);
    txtTotalPrice = findViewById(R.id.detailedTotalPrice);
    lstItemBreakdown = findViewById(R.id.detailedItemBreakdown);

    setupAdapters();
    setText();
  }

  private void setupAdapters() {
    Map<Item, Integer> itemMap = (HashMap<Item, Integer>) getIntent()
        .getSerializableExtra("itemMap");
    ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
        mapItemToString(itemMap));
    lstItemBreakdown.setAdapter(arrayAdapter);
  }

  private void setText() {
    String customerNameString = "Customer: " + getIntent().getStringExtra("customerName");
    txtCustomerName.setText(customerNameString);
    String saleNumberString = "Purchase Number: " + getIntent().getIntExtra("saleNumber", 0);
    txtSaleId.setText(saleNumberString);
    String totalPriceString =
        "Total Purchase Price: " + getIntent().getStringExtra("totalPrice");
    txtTotalPrice.setText(totalPriceString);

  }

  private List<String> mapItemToString(Map<Item, Integer> itemMap) {
    List<String> itemSaleList = new ArrayList<>();
    for (Map.Entry<Item, Integer> entry : itemMap.entrySet()) {
      String itemString = entry.getKey().getName() + ": " + entry.getValue();
      itemSaleList.add(itemString);
    }
    return itemSaleList;
  }
}

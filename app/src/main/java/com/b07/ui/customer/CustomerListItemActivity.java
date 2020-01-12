package com.b07.ui.customer;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.b07.domain.Item;
import com.b07.salesapplication.R;

import java.util.HashMap;

public class CustomerListItemActivity extends AppCompatActivity {

  public static String CART_TAG = "itemCartMap";
  public static String ID_TAG = "id";
  int id;
  HashMap<Item, Integer> itemMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_list_cart);

    Bundle bundle = getIntent().getExtras();
    id = bundle.getInt(ID_TAG);
    itemMap = (HashMap<Item, Integer>) getIntent().getSerializableExtra(CART_TAG);

    ListView lv = findViewById(android.R.id.list);
    TextView emptyText = findViewById(android.R.id.empty);
    lv.setEmptyView(emptyText);

    BaseAdapter adapter = new CustomItemAdapter(this, R.layout.customer_list_cart_row,
      itemMap);
    lv.setAdapter(adapter);

  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id == android.R.id.home ){
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

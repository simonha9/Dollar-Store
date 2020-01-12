package com.b07.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.domain.Item;
import com.b07.salesapplication.R;
import com.b07.ui.BaseView;
import java.util.HashMap;

public class CustomerExistingAcctActivity extends AppCompatActivity implements BaseView {

  private ListView lstExistingAccts;
  private Button btnLoadAcct;
  private int userId;
  private int selectedAcct;
  private CustomerExistingAcctPresenter presenter;
  private String name;

  @Override
  public void onCreate(Bundle savedBundleInstance) {
    super.onCreate(savedBundleInstance);
    setContentView(R.layout.customer_existing_accounts);

    Bundle bundle = getIntent().getExtras();
    userId = bundle.getInt("userId", 0);
    name = bundle.getString("userName");

    lstExistingAccts = findViewById(R.id.lstExistingAccts);
    btnLoadAcct = findViewById(R.id.btnLoadAcct);
    presenter = new CustomerExistingAcctPresenterImpl(this);
    setupAdapters();
    setupListeners();
  }

  private void setupAdapters() {
    ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, presenter.getActiveAccounts(userId));
    lstExistingAccts.setAdapter(arrayAdapter);
  }

  private void setupListeners() {
    lstExistingAccts.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedAcct = (int) parent.getItemAtPosition(position);
      }
    });

    btnLoadAcct.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (selectedAcct > 0) {
          Intent intent = new Intent(CustomerExistingAcctActivity.this,
            CustomerMainActivity.class);
          intent.putExtra("itemCartMap",
              (HashMap<Item, Integer>) presenter.getSelectedAccount(selectedAcct));
          intent.putExtra("userId", userId);
          intent.putExtra("userName", name);
          intent.putExtra("accountNum", selectedAcct);
          startActivity(intent);
          finish();
        }
      }
    });
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

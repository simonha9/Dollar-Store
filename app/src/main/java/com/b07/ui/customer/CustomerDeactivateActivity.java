package com.b07.ui.customer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;
import com.b07.services.AccountInterface;
import com.b07.ui.BaseView;
import com.b07.ui.login.LoginModel;
import com.b07.users.Customer;
import com.b07.users.User;

public class CustomerDeactivateActivity extends AppCompatActivity implements BaseView {
  private ListView lstAccts;
  private Button btnLoadAcct;
  private int userId;
  private int selectedAcct;
  private CustomerExistingAcctPresenter presenter;

  @Override
  public void onCreate(Bundle savedBundleInstance) {
    super.onCreate(savedBundleInstance);
    setContentView(R.layout.customer_deactivate);

    userId = getIntent().getIntExtra("userId", 0);

    lstAccts = findViewById(android.R.id.list);
    TextView emptyText = findViewById(android.R.id.empty);
    lstAccts.setEmptyView(emptyText);

    btnLoadAcct = findViewById(R.id.btnLoadAcct);
    presenter = new CustomerExistingAcctPresenterImpl(this);
    setupAdapters();
    setupListeners();
  }

  private void setupAdapters() {
    ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,
      android.R.layout.simple_list_item_1, presenter.getActiveAccounts(userId));
    lstAccts.setAdapter(arrayAdapter);
  }

  private void setupListeners() {
    lstAccts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedAcct = (int) parent.getItemAtPosition(position);
      }
    });

    btnLoadAcct.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (selectedAcct > 0) {
          User customer;
          LoginModel login = new LoginModel(CustomerDeactivateActivity.this);
          customer = login.getUserDetail(userId);

          AccountInterface account = new AccountInterface(CustomerDeactivateActivity.this,
            (Customer) customer);
          try {
            account.deactivateAccount(selectedAcct);
            if (!account.deactivateAccount(selectedAcct)) {
              Toast.makeText(CustomerDeactivateActivity.this, "Account could not be " +
                "deactivated" + ". No items in cart.", Toast.LENGTH_LONG).show();
            } else {
              Toast.makeText(CustomerDeactivateActivity.this, "Account " + selectedAcct
                + " is deactivated" + ". ", Toast.LENGTH_LONG).show();
            }
          } catch (Exception e) {
            Toast.makeText(CustomerDeactivateActivity.this, "Account could not be " +
              "deactivated" + ".  Database error.", Toast.LENGTH_LONG).show();
          }
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

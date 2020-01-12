package com.b07.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.UnauthenticatedException;
import com.b07.salesapplication.R;
import com.b07.services.ShoppingCart;
import com.b07.users.Customer;

import java.sql.SQLException;
import java.util.HashMap;

public class CustomerRestoreActivity extends AppCompatActivity {

  public static String CART_TAG = "itemCartMap";
  public static String ID_TAG = "id";
  public static String ACCOUNT_TAG = "account";
  int id;
  int accountNum;
  HashMap<Item, Integer> itemMap;
  ShoppingCart cart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_restore);

    Bundle bundle = getIntent().getExtras();
    id = bundle.getInt(ID_TAG);
    accountNum = bundle.getInt(ACCOUNT_TAG);
    itemMap = (HashMap<Item, Integer>) getIntent().getSerializableExtra(CART_TAG);

    if (accountNum != 0) {
      final CustomerItemHelper itemHelper = new
        CustomerItemHelper(CustomerRestoreActivity.this);
      Customer customer = null;
      try {
        customer = itemHelper.getUserById(id);
        customer.setAuthenticated(true);
        customer.setAccountId(accountNum);
        cart = new ShoppingCart(CustomerRestoreActivity.this, customer);
        cart.reloadShoppingCart();
        itemMap = cart.getCartItemMap();

      } catch (DataExportException e) {
        Toast.makeText(CustomerRestoreActivity.this, "Data export failed!",
          Toast.LENGTH_LONG).show();
      } catch (UnauthenticatedException e) {
        Toast.makeText(CustomerRestoreActivity.this, "Unauthenticated user!",
          Toast.LENGTH_LONG).show();
      } catch (SQLException e) {
        Toast.makeText(CustomerRestoreActivity.this, "SQL failed!",
          Toast.LENGTH_LONG).show();
      }

      Button restoreButton = findViewById(R.id.restoreButton);
      restoreButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
          Toast.makeText(CustomerRestoreActivity.this, "Restored cart!",
            Toast.LENGTH_LONG).show();
          Intent newIntent = new Intent();
          newIntent.putExtra(CART_TAG, cart.getCartItemMap());
          setResult(RESULT_OK, newIntent);
          finish();
        }
      });
    }

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

package com.b07.ui.customer;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.UnauthenticatedException;
import com.b07.salesapplication.R;
import com.b07.services.ShoppingCart;
import com.b07.users.Customer;

import java.math.BigDecimal;
import java.util.HashMap;

public class CustomerPriceActivity extends AppCompatActivity {

  public static String CART_TAG = "itemCartMap";
  public static String ID_TAG = "id";
  String priceString;
  BigDecimal price;
  int id;
  HashMap<Item, Integer> itemMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_check_price);

    Bundle bundle = getIntent().getExtras();
    id = bundle.getInt(ID_TAG);
    itemMap = (HashMap<Item, Integer>) getIntent().getSerializableExtra(CART_TAG);

    final CustomerItemHelper itemHelper = new CustomerItemHelper(CustomerPriceActivity.this);
    Customer customer ;
    try {
      customer = itemHelper.getUserById(id);
      customer.setAuthenticated(true);

      ShoppingCart cart = new ShoppingCart(CustomerPriceActivity.this, customer);
      cart.setCartItemMap(itemMap);
      cart.updateTotal();
      price = cart.getTotal();
      priceString = String.valueOf(price);

      TextView priceText = findViewById(R.id.priceText);
      priceText.setText("Total Price of Cart: $" + priceString);
    } catch (DataExportException e) {
      Toast.makeText(CustomerPriceActivity.this,
        "Export failed!",
        Toast.LENGTH_LONG).show();
    } catch (UnauthenticatedException e) {
      Toast.makeText(CustomerPriceActivity.this,
        "User is unauthenticated!",
        Toast.LENGTH_LONG).show();
    }
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

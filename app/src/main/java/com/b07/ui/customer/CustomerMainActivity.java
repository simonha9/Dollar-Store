package com.b07.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.UnauthenticatedException;
import com.b07.salesapplication.R;
import com.b07.services.ShoppingCart;
import com.b07.ui.login.LogoutActivity;
import com.b07.users.Customer;
import java.io.Serializable;
import java.util.HashMap;

public class CustomerMainActivity extends LogoutActivity implements Serializable {

  private TextView txtWelcome;
  public static String CART_TAG = "itemCartMap";
  public static String ID_TAG = "id";
  public static String ACCOUNT_TAG = "account";
  int id;
  int accountNum;
  ShoppingCart cart;
  HashMap<Item, Integer> itemMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_main);

    Bundle bundle = getIntent().getExtras();
    id = bundle.getInt("userId", 0);
    accountNum = bundle.getInt("accountNum", 0);

    final CustomerItemHelper itemHelper = new CustomerItemHelper(CustomerMainActivity.this);
    Customer customer;
    try {
      customer = itemHelper.getUserById(id);
      customer.setAuthenticated(true);
      cart = new ShoppingCart(CustomerMainActivity.this, customer);
    } catch (DataExportException e) {
      Toast.makeText(CustomerMainActivity.this, "Data export failed!",
        Toast.LENGTH_LONG).show();
    } catch (UnauthenticatedException e) {
      Toast.makeText(CustomerMainActivity.this, "Unauthenticated user!",
        Toast.LENGTH_LONG).show();
    }

    txtWelcome = findViewById(R.id.txtCustomerWelcome);
    setWelcomeText();

    Button addButton = findViewById(R.id.buttonAdd);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CustomerMainActivity.this,
            CustomerAddActivity.class);
        intent.putExtra(CART_TAG, cart.getCartItemMap());
        intent.putExtra(ID_TAG, id);
        intent.putExtra(ACCOUNT_TAG, accountNum);
        startActivityForResult(intent, 1);
      }
    });

    Button removeButton = findViewById(R.id.buttonRemove);
    removeButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CustomerMainActivity.this,
            CustomerRemoveActivity.class);
        intent.putExtra(CART_TAG, cart.getCartItemMap());
        intent.putExtra(ID_TAG, id);
        intent.putExtra(ACCOUNT_TAG, accountNum);
        startActivityForResult(intent, 1);
      }
    });

    Button priceButton = findViewById(R.id.buttonPrice);
    priceButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CustomerMainActivity.this,
            CustomerPriceActivity.class);
        intent.putExtra(CART_TAG, cart.getCartItemMap());
        intent.putExtra(ID_TAG, id);
        startActivity(intent);
      }
    });

    Button checkoutButton = findViewById(R.id.buttonCheckout);
    checkoutButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CustomerMainActivity.this,
            CustomerCheckoutActivity.class);
        intent.putExtra(CART_TAG, cart.getCartItemMap());
        intent.putExtra(ID_TAG, id);
        intent.putExtra(ACCOUNT_TAG, accountNum);
        startActivityForResult(intent, 1);
      }
    });

    Button listButton = findViewById(R.id.buttonList);
    listButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CustomerMainActivity.this,
            CustomerListItemActivity.class);
        intent.putExtra(CART_TAG, cart.getCartItemMap());
        intent.putExtra(ID_TAG, id);
        startActivityForResult(intent, 1);
      }
    });

    Button restoreButton = findViewById(R.id.buttonRestore);
    restoreButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CustomerMainActivity.this,
            CustomerRestoreActivity.class);
        intent.putExtra(CART_TAG, cart.getCartItemMap());
        intent.putExtra(ID_TAG, id);
        intent.putExtra(ACCOUNT_TAG, accountNum);
        startActivityForResult(intent, 1);
      }
    });

    setupLogoutButton();
  }

  private void setWelcomeText() {
    String name = getIntent().getStringExtra("userName").split("\\s+")[0];
    if (name != null && !name.isEmpty()) {
      String welcomeName = "Welcome, " + name + "!";
      txtWelcome.setText(welcomeName);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case 1:
        if (resultCode == RESULT_OK) {
          itemMap = (HashMap<Item, Integer>) data.getSerializableExtra(CART_TAG);
          cart.setCartItemMap(itemMap);
        }
        break;
    }
  }
}
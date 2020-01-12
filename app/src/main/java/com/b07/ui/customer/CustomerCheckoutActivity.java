package com.b07.ui.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InsufficientItemException;
import com.b07.exceptions.UnauthenticatedException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.salesapplication.R;
import com.b07.services.ShoppingCart;
import com.b07.ui.login.LoginActivity;
import com.b07.ui.login.LogoutActivity;
import com.b07.users.Customer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

public class CustomerCheckoutActivity extends AppCompatActivity {
  public static String CART_TAG = "itemCartMap";
  public static String ID_TAG = "id";
  public static String ACCOUNT_TAG = "account";
  String priceString;
  BigDecimal price;
  int id;
  HashMap<Item, Integer> itemMap;
  ShoppingCart cart = null;
  int accountNum;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_checkout);

    Bundle bundle = getIntent().getExtras();
    id = bundle.getInt(ID_TAG);
    accountNum = bundle.getInt(ACCOUNT_TAG);
    itemMap = (HashMap<Item, Integer>) getIntent().getSerializableExtra(CART_TAG);

    final CustomerItemHelper itemHelper = new
      CustomerItemHelper(CustomerCheckoutActivity.this);
    Customer customer;
    try {
      customer = itemHelper.getUserById(id);
      customer.setAuthenticated(true);
      cart = new ShoppingCart(CustomerCheckoutActivity.this, customer);

      if (accountNum != 0) {
        customer.setAccountId(accountNum);
      }

    } catch (UnauthenticatedException e) {
      Toast.makeText(CustomerCheckoutActivity.this,
        "User unauthenticated!",
        Toast.LENGTH_LONG).show();
    } catch (DataExportException e) {
      Toast.makeText(CustomerCheckoutActivity.this,
        "Export failed!",
        Toast.LENGTH_LONG).show();
    }

    cart.setCartItemMap(itemMap);
    cart.updateTotal();
    BigDecimal priceTax = cart.calcTotalAfterTax();
    price = cart.getTotal();
    priceString = String.valueOf(priceTax);

    TextView priceText = findViewById(R.id.priceCart);
    priceText.setText("Total Price of Cart: $" + priceString);

    Button payButton = findViewById(R.id.payButton);
    payButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        try {
          cart.setTotal(price);
          cart.setCartItemMap(itemMap);
          boolean checkout = cart.checkOut();

          if (accountNum != 0) {
            try {
              cart.save();
            } catch (DatabaseInsertException e) {
              Toast.makeText(CustomerCheckoutActivity.this, "Insert to database failed!",
                Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
              Toast.makeText(CustomerCheckoutActivity.this, "SQL failed!",
                Toast.LENGTH_LONG).show();
            } catch (ValidationFailedException e) {
              Toast.makeText(CustomerCheckoutActivity.this, "Validation failed!",
                Toast.LENGTH_LONG).show();
            }
          }

          if (checkout) {
            Toast.makeText(CustomerCheckoutActivity.this,
              "Checkout successful! Thank you for shopping with us!",
              Toast.LENGTH_LONG).show();
          }
        } catch (InsufficientItemException e) {
          Toast.makeText(CustomerCheckoutActivity.this,
            "Insufficient items! Checkout failed!",
            Toast.LENGTH_LONG).show();
        } catch (ValidationFailedException e) {
          Toast.makeText(CustomerCheckoutActivity.this,
            "Validation error!",
            Toast.LENGTH_LONG).show();
        }

      }
    });

    Button yesButton = findViewById(R.id.yesButton);
    yesButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent newIntent = new Intent(CustomerCheckoutActivity.this,
          CustomerMainActivity.class);
        newIntent.putExtra(CART_TAG, cart.getCartItemMap());
        setResult(RESULT_OK, newIntent);
        finish();
      }
    });

    Button noButton = findViewById(R.id.noButton);
    noButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        new AlertDialog.Builder(CustomerCheckoutActivity.this)
          .setIcon(R.drawable.ic_exit_to_app_black_24dp)
          .setTitle("Log out")
          .setMessage("Are you sure you want to log out?")
          .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                  Intent intent = new Intent(CustomerCheckoutActivity.this,
                    LoginActivity.class);
                  finish();
                  startActivity(intent);
                }
              })
              .setNegativeButton("Cancel", null)
              .show();
          }
        });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id== android.R.id.home ){
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

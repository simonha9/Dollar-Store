package com.b07.ui.customer;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.UnauthenticatedException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.salesapplication.R;
import com.b07.services.ShoppingCart;
import com.b07.users.Customer;

import java.sql.SQLException;
import java.util.HashMap;

public class CustomerRemoveActivity extends AppCompatActivity {
  public static String CART_TAG = "itemCartMap";
  public static String ID_TAG = "id";
  public static String ACCOUNT_TAG = "account";
  HashMap<Item, Integer> itemMap;
  int id;
  int accountNum;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer_remove);

    final CustomerItemHelper itemHelper = new
      CustomerItemHelper(CustomerRemoveActivity.this);

    Bundle bundle = getIntent().getExtras();
    id = bundle.getInt(ID_TAG);
    accountNum = bundle.getInt(ACCOUNT_TAG);
    itemMap = (HashMap<Item, Integer>) getIntent().getSerializableExtra(CART_TAG);

    final EditText removeQuantity = findViewById(R.id.quantityText);

    final Spinner itemSpinner = findViewById(R.id.itemSpinner);
    Button enterButton = findViewById(R.id.enterButton);
    enterButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        String itemChoice = itemSpinner.getSelectedItem().toString();
        String itemTypeChoice = itemHelper.convertName(itemChoice);
        String quantityString = removeQuantity.getText().toString();

        if (!quantityString.equals("")) {
          int quantity = Integer.valueOf(quantityString);
          try {
            Customer customer = itemHelper.getUserById(id);
            customer.setAuthenticated(true);
            ShoppingCart cart = new ShoppingCart(CustomerRemoveActivity.this, customer);
            cart.setCartItemMap(itemMap);

            Item item = itemHelper.getItemByName(itemTypeChoice);
            cart.removeItem(item, quantity);

            if (accountNum != 0) {
              try {
                customer.setAccountId(accountNum);
                cart.save();
              } catch (DatabaseInsertException e) {
                Toast.makeText(CustomerRemoveActivity.this,
                  "Insert to database failed!", Toast.LENGTH_LONG).show();
              } catch (SQLException e) {
                Toast.makeText(CustomerRemoveActivity.this, "SQL failed!",
                  Toast.LENGTH_LONG).show();
              } catch (ValidationFailedException e) {
                Toast.makeText(CustomerRemoveActivity.this,
                  "Account validation failed!", Toast.LENGTH_LONG).show();
              }
            }

            Toast.makeText(CustomerRemoveActivity.this,
              "Removed " + quantity + " of " + itemChoice, Toast.LENGTH_LONG).show();

            Intent newIntent = new Intent();
            newIntent.putExtra(CART_TAG, cart.getCartItemMap());
            setResult(RESULT_OK, newIntent);
            finish();
          } catch (DataExportException e) {
            Toast.makeText(CustomerRemoveActivity.this, "Export failed!",
              Toast.LENGTH_LONG).show();
          } catch (UnauthenticatedException e) {
            Toast.makeText(CustomerRemoveActivity.this, "User unauthenticated!",
              Toast.LENGTH_LONG).show();
          }
        } else {
          Toast.makeText(CustomerRemoveActivity.this, "Please enter a quantity!",
            Toast.LENGTH_LONG).show();
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

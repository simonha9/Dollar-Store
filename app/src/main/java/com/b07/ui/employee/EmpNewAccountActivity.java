package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.exceptions.AccountCreateFailedException;
import com.b07.salesapplication.R;
import com.b07.services.AccountInterface;
import com.b07.ui.login.LoginActivity;
import com.b07.ui.login.LoginModel;
import com.b07.users.Customer;
import com.b07.users.User;

public class EmpNewAccountActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.emp_new_user_layout);

    Button enterButton = findViewById(R.id.enterButton);
    enterButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        EditText customerIdEditText = findViewById(R.id.customerId);
        String customerIdString = customerIdEditText.getText().toString();

        if (!customerIdString.equals("")) {
          int customerId = Integer.valueOf(customerIdEditText.getText().toString());
          User customer;
          try {
            LoginModel login = new LoginModel(EmpNewAccountActivity.this);
            customer = login.getUserDetail(customerId);
            AccountInterface account = new AccountInterface(EmpNewAccountActivity.this,
              (Customer) customer);
            try {
              int id = account.createAccount();
//              ((Customer) customer).getAccounts().add(id);
              Toast.makeText(EmpNewAccountActivity.this, "Account made with ID " + id,
                Toast.LENGTH_LONG).show();
              Toast.makeText(EmpNewAccountActivity.this, "Navigating to customer login",
                Toast.LENGTH_LONG).show();
              Intent intent = new Intent(EmpNewAccountActivity.this,
                LoginActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
            } catch (AccountCreateFailedException e) {
              Toast.makeText(EmpNewAccountActivity.this, "Account could not be made. ",
                Toast.LENGTH_LONG).show();
            }
          } catch (Exception e) {
            Toast.makeText(EmpNewAccountActivity.this, "Account could not be made. " +
                "Invalid ID.",
              Toast.LENGTH_LONG).show();
          }
        } else {
          Toast.makeText(EmpNewAccountActivity.this, "Please enter an id!",
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

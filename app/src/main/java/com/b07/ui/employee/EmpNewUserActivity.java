package com.b07.ui.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.b07.exceptions.UserCreateFailedException;
import com.b07.salesapplication.R;
import com.b07.services.EmployeeInterface;

public class EmpNewUserActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.account_layout);
    Intent intent = getIntent();
    String name = intent.getExtras().getString("name");
    int age = intent.getExtras().getInt("age");
    String address = intent.getExtras().getString("address");
    String password = intent.getExtras().getString("password");
    String user = intent.getExtras().getString("userToMake");

    EmployeeInterface eInt = new EmployeeInterface(EmpNewUserActivity.this);
    if (user.equals("employee")) {
      try {
        int id = eInt.createEmployee(name, age, address, password);
        Toast.makeText(EmpNewUserActivity.this,
          "Made employee with id: " + id,
          Toast.LENGTH_LONG).show();
      } catch (UserCreateFailedException e) {
        Toast.makeText(EmpNewUserActivity.this,
          "Could not make new employee!",
          Toast.LENGTH_LONG).show();

      }
    } else if (user.equals("customer")) {
      try {
        int id = eInt.createCustomer(name, age, address, password);
        Toast.makeText(EmpNewUserActivity.this,
          "Made customer with id: " + id,
          Toast.LENGTH_LONG).show();
      } catch (UserCreateFailedException e) {
        Toast.makeText(EmpNewUserActivity.this,
          "Could not make new customer!",
          Toast.LENGTH_LONG).show();

      }
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

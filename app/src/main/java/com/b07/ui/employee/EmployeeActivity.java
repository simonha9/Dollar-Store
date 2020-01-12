package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.b07.salesapplication.R;
import com.b07.ui.login.LogoutActivity;

public class EmployeeActivity extends LogoutActivity {

  private TextView txtWelcome;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employee_main);

    txtWelcome = findViewById(R.id.txtEmployeeWelcome);
    setWelcomeText();

    Button authButton = findViewById(R.id.authEmpButton);
    authButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(EmployeeActivity.this, EmpAuthActivity.class);
        startActivity(intent);
      }

    });

    Button newEmpButton = findViewById(R.id.newEmpButton);
    newEmpButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(EmployeeActivity.this, AccountActivity.class);
        intent.putExtra("user", "employee");
        startActivity(intent);
      }

    });

    Button newAccButton = findViewById(R.id.newAccButton);
    newAccButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(EmployeeActivity.this, EmpNewAccountActivity.class);
        startActivity(intent);
      }
    });

    Button newUserButton = findViewById(R.id.newUserButton);
    newUserButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Intent intent = new Intent(EmployeeActivity.this, AccountActivity.class);
        intent.putExtra("user", "customer");
        startActivity(intent);
      }

    });

    Button restockButton = findViewById(R.id.restockInvButton);
    restockButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        final int id = getIntent().getIntExtra("userId", 0);
        Intent intent = new Intent(EmployeeActivity.this, EmpRestockActivity.class);
        intent.putExtra("userId", id);
        startActivity(intent);
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
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}

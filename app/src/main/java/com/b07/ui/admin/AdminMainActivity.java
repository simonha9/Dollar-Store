package com.b07.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.b07.salesapplication.R;
import com.b07.ui.login.LogoutActivity;

public class AdminMainActivity extends LogoutActivity {

  private TextView txtWelcome;
  private Button btnPromoteEmployee;
  private Button btnViewSalesLog;
  private Button btnViewCustomerAccts;
  private Button btnManageDatabase;

  @Override
  public void onCreate(Bundle savedBundleState) {
    super.onCreate(savedBundleState);
    setContentView(R.layout.admin_main);

    txtWelcome = findViewById(R.id.txtAdminWelcome);
    btnPromoteEmployee = findViewById(R.id.buttonPromote);
    btnViewSalesLog = findViewById(R.id.buttonSales);
    btnViewCustomerAccts = findViewById(R.id.buttonAccounts);
    btnManageDatabase = findViewById(R.id.buttonDatabase);
    setWelcomeText();
    setupListeners();
    setupLogoutButton();
  }

  private void setWelcomeText() {
    String name = getIntent().getStringExtra("userName").split("\\s+")[0];
    if (name != null && !name.isEmpty()) {
      String welcomeName = "Welcome, " + name + "!";
      txtWelcome.setText(welcomeName);
    }
  }

  private void setupListeners() {
    btnPromoteEmployee.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(AdminMainActivity.this, AdminPromoteActivity.class);
        startActivity(intent);
      }
    });

    btnViewSalesLog.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(AdminMainActivity.this, SalesLogActivity.class);
        startActivity(intent);
      }
    });

    btnViewCustomerAccts.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(AdminMainActivity.this, AdminViewAcctsActivity.class);
        startActivity(intent);
      }
    });

    btnManageDatabase.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(AdminMainActivity.this, AdminDatabaseActivity.class);
        startActivity(intent);
      }
    });
  }

}

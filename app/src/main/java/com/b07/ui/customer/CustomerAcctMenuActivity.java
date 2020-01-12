package com.b07.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.b07.salesapplication.R;
import com.b07.ui.login.LogoutActivity;

public class CustomerAcctMenuActivity extends LogoutActivity {

  private TextView txtWelcome;
  private Button btnRegisterAcct;
  private Button btnShopWoAcct;
  private Button btnChooseAcct;
  private Button btnDeactivateAcct;
  private int userId;
  private String name;

  @Override
  public void onCreate(Bundle savedBundleInstance) {
    super.onCreate(savedBundleInstance);
    setContentView(R.layout.customer_account_menu);

    userId = getIntent().getIntExtra("userId", 0);

    txtWelcome = findViewById(R.id.txtCustomerWelcome);
    btnRegisterAcct = findViewById(R.id.btnRegisterAcct);
    btnShopWoAcct = findViewById(R.id.btnShopWoAcct);
    btnChooseAcct = findViewById(R.id.btnChooseAcct);
    btnDeactivateAcct = findViewById(R.id.btnDeactivateAcct);
    setWelcomeText();
    setupListeners();
    setupLogoutButton();
  }

  private void setWelcomeText() {
    name = getIntent().getStringExtra("userName").split("\\s+")[0];
    if (name != null && !name.isEmpty()) {
      String welcomeName = "Welcome, " + name + "!";
      txtWelcome.setText(welcomeName);
    }
  }

  private void setupListeners() {
    btnRegisterAcct.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(CustomerAcctMenuActivity.this,
          CustomerNewAccount.class);
        startActivity(intent);
      }
    });

    btnShopWoAcct.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(CustomerAcctMenuActivity.this,
            "Please register for an account if you wish to save your cart!", Toast.LENGTH_LONG)
            .show();
        Intent intent = new Intent(CustomerAcctMenuActivity.this,
          CustomerMainActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("accountNum", 0);
        intent.putExtra("userName", name);
        startActivity(intent);
      }
    });

    btnChooseAcct.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(CustomerAcctMenuActivity.this,
            CustomerExistingAcctActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userName", name);
        startActivity(intent);
      }
    });

    btnDeactivateAcct.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(CustomerAcctMenuActivity.this,
            CustomerDeactivateActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
      }
    });
  }
}

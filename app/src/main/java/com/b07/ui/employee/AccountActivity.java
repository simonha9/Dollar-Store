package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;

public class AccountActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.account_layout);

    Intent intent = getIntent();
    final String user = intent.getStringExtra("user");

    final EditText nameEditText = findViewById(R.id.accountName);
    final EditText ageEditText = findViewById(R.id.accountAge);
    final EditText addressEditText = findViewById(R.id.accountAddress);
    final EditText passwordEditText = findViewById(R.id.accountPassword);

    Button enterButton = findViewById(R.id.enterDetails);
    enterButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Intent intent = new Intent(AccountActivity.this, EmpNewUserActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        intent.putExtra("address", address);
        intent.putExtra("password", password);
        intent.putExtra("userToMake", user);
        startActivity(intent);
        finish();

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

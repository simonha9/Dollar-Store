package com.b07.ui.employee;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;
import com.b07.ui.login.LoginModel;


public class EmpAuthActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_main);

    Button enterButton = findViewById(R.id.enterButton);
    enterButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        EditText userEditText = findViewById(R.id.idUserText);
        String userId;
        userId = userEditText.getText().toString();

        EditText passwordEditText = findViewById(R.id.idPasswordText);
        String password;
        password = passwordEditText.getText().toString();

        LoginModel auth = new LoginModel(EmpAuthActivity.this);
        try {
          if (auth.authenticate(Integer.valueOf(userId), password)) {
            Toast.makeText(EmpAuthActivity.this, "Employee is authenticated",
              Toast.LENGTH_LONG).show();
            finish();
          }
        } catch (Exception e) {
          Toast.makeText(EmpAuthActivity.this, "User Id or password is incorrect. " +
              "Please try again.",
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

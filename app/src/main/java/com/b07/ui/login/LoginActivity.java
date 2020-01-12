package com.b07.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;
import com.b07.ui.admin.AdminMainActivity;
import com.b07.ui.customer.CustomerAcctMenuActivity;
import com.b07.ui.employee.EmployeeActivity;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;

public class LoginActivity extends AppCompatActivity implements LoginView {

  private EditText usernameEditText;
  private EditText passwordEditText;
  private Button loginButton;
  private LoginPresenter presenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_main);
    usernameEditText = findViewById(R.id.idUserText);
    usernameEditText.getText().clear();
    passwordEditText = findViewById(R.id.idPasswordText);
    passwordEditText.getText().clear();
    loginButton = findViewById(R.id.enterButton);
    presenter = new LoginPresenterImpl(this);
    setupListeners();
  }

  private void setupListeners() {
    usernameEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        updateLoginButtonState();
      }
    });
    passwordEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        updateLoginButtonState();
      }
    });
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("Login", "Login clicked");
        nextView(v);
      }
    });

  }

  private void updateLoginButtonState() {
    boolean enabled = (usernameEditText.getText().length() > 0 &&
        passwordEditText.getText().length() > 0);
    loginButton.setClickable(enabled);
    loginButton.setEnabled(enabled);
  }

  @Override
  public void showError(String error) {
    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
  }

  /**
   * Called when the user taps the Send button
   */
  public void nextView(View view) {
    final User user = presenter.login(Integer.parseInt(usernameEditText.getText().toString()),
        passwordEditText.getText().toString());
    Log.d("Login", "nextView: " + usernameEditText.getText().toString());
    if (user != null) {
      Intent intent;
      if (user instanceof Admin) {
        intent = new Intent(this, AdminMainActivity.class);
        intent.putExtra("userId", user.getId());
        intent.putExtra("userName", user.getName());
        startActivity(intent);
      } else if (user instanceof Employee) {
        intent = new Intent(this, EmployeeActivity.class);
        intent.putExtra("userId", user.getId());
        intent.putExtra("userName", user.getName());
        startActivity(intent);
      } else if (user instanceof Customer) {
        intent = new Intent(this, CustomerAcctMenuActivity.class);
        intent.putExtra("userId", user.getId());
        intent.putExtra("userName", user.getName());
        startActivity(intent);
      }
      finish();
    }
  }

}

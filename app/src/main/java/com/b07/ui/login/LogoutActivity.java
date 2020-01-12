package com.b07.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;

public class LogoutActivity extends AppCompatActivity {

  public void setupLogoutButton() {
    Button btnLogout = findViewById((R.id.exitButton));
    btnLogout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        new AlertDialog.Builder(LogoutActivity.this)
            .setIcon(R.drawable.ic_exit_to_app_black_24dp)
            .setTitle("Log out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
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
  public void onDestroy() {
    String name = getIntent().getStringExtra("userName");
    String toodles;
    if (name != null && !name.isEmpty()) {
      toodles = "Toodles, " + name + "!";
    } else {
      toodles = "Toodles!";
    }
    Toast.makeText(this, toodles, Toast.LENGTH_LONG).show();
    super.onDestroy();
  }

}

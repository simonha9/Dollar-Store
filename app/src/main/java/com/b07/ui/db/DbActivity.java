package com.b07.ui.db;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;
import com.b07.ui.login.LoginActivity;

public class DbActivity extends AppCompatActivity implements DbActivityView {

  private Button btnInitdb;
  private Button btnImport;
  private Button btnDone;
  private EditText txtFilename;
  private ProgressBar progressBar;

  private DbPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new DbPresenterImpl(this);
    if (!presenter.initializeDbRequired()) {
      nextView();
    }
    setContentView(R.layout.activity_db);
    TextView txt_folder = findViewById(R.id.txtSerializeDbFolder);
    txt_folder.setText(getApplicationContext().getFilesDir().getAbsolutePath());
    btnInitdb = findViewById(R.id.btnInitDb);
    txtFilename = findViewById(R.id.txtFilename);
    btnImport = findViewById(R.id.btnInitDbImport);
    btnDone = findViewById(R.id.btnInitDbDone);
    progressBar = findViewById(R.id.dbProgressBar);
    progressBar.setVisibility(View.INVISIBLE);

    addListeners();
  }

  private void nextView() {
    Intent intent = new Intent(this, LoginActivity.class);
    finish();
    startActivity(intent);
  }

  private void addListeners() {
    btnInitdb.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("Login", "btnInitdb clicked");
        presenter.initializeDb();

      }
    });
    btnImport.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("Login", "btnImport clicked");
        presenter.importDb(txtFilename.getText().toString());
      }
    });
    btnDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("Login", "btnDone clicked");
        nextView();
      }
    });
  }


  @Override
  public void showProgressBar(boolean flag) {
    progressBar.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
  }

  @Override
  public void showMessage(String msg) {
    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
  }
}

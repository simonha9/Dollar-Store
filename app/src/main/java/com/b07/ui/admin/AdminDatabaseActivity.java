package com.b07.ui.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.exceptions.DataExportException;
import com.b07.exceptions.DataImportException;
import com.b07.salesapplication.R;
import com.b07.services.DbExporter;
import com.b07.services.DbImporter;

import static com.b07.database.DatabaseHelper.SERIALIZED_DATABASE_NAME;
import static com.b07.database.DatabaseHelper.DATABASE_NAME;

public class AdminDatabaseActivity  extends AppCompatActivity {

  private Button btnImportDatabase;
  private Button btnExportDatabase;

  @Override
  public void onCreate(Bundle savedBundleState) {
    super.onCreate(savedBundleState);
    setContentView(R.layout.admin_database);

    btnImportDatabase = findViewById(R.id.buttonImport);
    btnExportDatabase = findViewById(R.id.buttonExport);
    setupListeners();

  }

  private void setupListeners() {

    btnImportDatabase.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DbImporter importer = new DbImporter(AdminDatabaseActivity.this);
        try {
          importer.importData(SERIALIZED_DATABASE_NAME);
          Toast.makeText(AdminDatabaseActivity.this, "New database imported!",
            Toast.LENGTH_LONG).show();
        } catch (DataImportException e) {
          Toast.makeText(AdminDatabaseActivity.this, "Data import failed!",
            Toast.LENGTH_LONG).show();
        }
      }
    });

    btnExportDatabase.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DbExporter exporter = new DbExporter(AdminDatabaseActivity.this);
        try {
          exporter.exportData(SERIALIZED_DATABASE_NAME);
          Toast.makeText(AdminDatabaseActivity.this, "Current database exported!",
            Toast.LENGTH_LONG).show();
        } catch (DataExportException e) {
          Toast.makeText(AdminDatabaseActivity.this, "Data export failed!",
            Toast.LENGTH_LONG).show();
        }
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home ){
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

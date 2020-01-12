package com.b07.ui.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;
import com.b07.users.User;

public class AdminPromoteActivity extends AppCompatActivity implements AdminView {

  private ListView lstEmployeeList;
  private Button btnPromote;
  private User selectedEmployee = null;
  private AdminPromotePresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_promote);

    lstEmployeeList = findViewById(R.id.employeeList);
    btnPromote = findViewById(R.id.promoteButton);

    presenter = new AdminPromotePresenterImpl(this);
    setupAdapters();
    setupListeners();
  }

  private void setupAdapters() {
    ArrayAdapter arrayAdapter = new EmployeeListAdapter(this,
        android.R.layout.simple_list_item_1, presenter.getEmployeeList());
    lstEmployeeList.setAdapter(arrayAdapter);
  }

  private void setupListeners() {
    lstEmployeeList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedEmployee = (User) parent.getItemAtPosition(position);
      }
    });

    btnPromote.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (selectedEmployee != null) {
          if (presenter.promoteEmployee(selectedEmployee)) {
            Toast.makeText(AdminPromoteActivity.this,
                "Successfully promoted " + selectedEmployee.getName() + " to an admin!",
                Toast.LENGTH_LONG).show();
            finish();
          }
        }
      }
    });
  }

  @Override
  public void showError(String error) {
    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id== android.R.id.home ){
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

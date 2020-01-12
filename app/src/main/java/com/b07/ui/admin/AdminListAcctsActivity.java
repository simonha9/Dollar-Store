package com.b07.ui.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.salesapplication.R;
import java.util.ArrayList;
import java.util.List;

public class AdminListAcctsActivity extends AppCompatActivity {

  TextView accountListTitle;
  ListView activeAccountList;
  ListView inactiveAccountList;

  @Override
  public void onCreate(Bundle savedBundleInstance) {
    super.onCreate(savedBundleInstance);
    setContentView(R.layout.admin_view_accounts);

    accountListTitle = findViewById(R.id.accountListTitle);
    activeAccountList = findViewById(R.id.activeAccountList);
    inactiveAccountList = findViewById(R.id.inactiveAccountList);

    setText();
    setupAdapters();
  }

  private void setText() {
    String text = "Viewing accounts for customer number " + getIntent().getStringExtra("custId");
    accountListTitle.setText(text);
  }

  private void setupAdapters() {
    List<Integer> activeAccounts = (ArrayList<Integer>) getIntent()
        .getSerializableExtra("activeAccounts");
    ArrayAdapter<Integer> activeAccountsAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, activeAccounts);
    activeAccountList.setAdapter(activeAccountsAdapter);

    List<Integer> inactiveAccounts = (ArrayList<Integer>) getIntent()
        .getSerializableExtra("inactiveAccounts");
    ArrayAdapter<Integer> inactiveAccountsAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, inactiveAccounts);
    inactiveAccountList.setAdapter(inactiveAccountsAdapter);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id == android.R.id.home){
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

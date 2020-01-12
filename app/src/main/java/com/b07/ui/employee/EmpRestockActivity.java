package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.domain.Inventory;
import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exporter.InventoryExporter;
import com.b07.exporter.ItemExporter;
import com.b07.salesapplication.R;
import com.b07.services.EmployeeInterface;
import com.b07.ui.login.LoginModel;
import com.b07.users.Employee;
import com.b07.users.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmpRestockActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.emp_restock_layout);

    Intent intent = getIntent();
    final int id = intent.getIntExtra("userId", 0);

    Button enterButton = findViewById(R.id.enterButton);
    enterButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Spinner itemSpinner = findViewById(R.id.itemSpinner);
        String itemChoice = itemSpinner.getSelectedItem().toString();
        int itemId = 0;
        if (itemChoice.equals("Fishing Rod")) {
          itemId = 1;
        } else if (itemChoice.equals("Hockey Stick")) {
          itemId = 2;
        } else if (itemChoice.equals("Skates")) {
          itemId = 3;
        } else if (itemChoice.equals("Running Shoes")) {
          itemId = 4;
        } else {
          itemId = 5;
        }

        EditText quantityEditText = findViewById(R.id.quantityButton);
        String quantityString = quantityEditText.getText().toString();
        if (quantityString.matches("")) {
          invalidQuantityShowPopupWindow(view);

        } else {
          int quantity = Integer.valueOf(quantityEditText.getText().toString());
          LoginModel login = new LoginModel(EmpRestockActivity.this);
          User emp;
          emp = login.getUserDetail(id);

          EmployeeInterface eInt = new EmployeeInterface(EmpRestockActivity.this);
          eInt.setCurrentEmployee((Employee) emp);

          List<Item> itemList;
          ItemExporter items = new ItemExporter(EmpRestockActivity.this);
          List<Inventory> invList;
          InventoryExporter inventory = new InventoryExporter(EmpRestockActivity.this);
          int total = 0;
          try {
            itemList = items.exportRecords();
            invList = inventory.exportRecords();
            Item item = null;
            for (int i = 0; i < itemList.size(); i++) {
              if (itemList.get(i).getId() == itemId) {
                item = itemList.get(i);
              }
            }
            for (int j = 0; j < invList.size(); j++) {
              total = total + invList.get(j).getItemMap().get(item);
            }

            eInt.restockInventory(item, total + quantity);
          } catch (DataExportException e) {
            Toast.makeText(EmpRestockActivity.this, "Unable to retrieve items",
                Toast.LENGTH_LONG).show();
          }

          onButtonShowPopupWindowClickSuccess(view);
        }
      }
    });
  }


  public void onButtonShowPopupWindowClickSuccess(View view) {
    LayoutInflater inflater = (LayoutInflater)
        getSystemService(LAYOUT_INFLATER_SERVICE);
    View popupView = inflater.inflate(R.layout.emp_restock_popup, null);

    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    boolean focusable = true;
    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    popupView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        popupWindow.dismiss();
        return true;
      }
    });
  }


  public void invalidQuantityShowPopupWindow(View view) {
    LayoutInflater inflater = (LayoutInflater)
        getSystemService(LAYOUT_INFLATER_SERVICE);
    View popupView = inflater.inflate(R.layout.invalid_int_popup, null);

    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    boolean focusable = true;
    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    popupView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        popupWindow.dismiss();
        return true;
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

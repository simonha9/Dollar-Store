package com.b07.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import com.b07.domain.Item;
import com.b07.domain.Sale;
import com.b07.salesapplication.R;
import java.util.HashMap;

public class SalesLogActivity extends AppCompatActivity implements AdminView {

  private TextView txtTotalSales;
  private Button btnTotalSales;
  private ListView lstSalesLog;
  private SalesLogPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_sales_log);

    txtTotalSales = findViewById(R.id.txtTotalSales);
    btnTotalSales = findViewById(R.id.btnTotalSales);
    lstSalesLog = findViewById(R.id.lstSalesLog);
    presenter = new SalesLogPresenterImpl(this);
    setupAdapters();
    setupListeners();
    setText();
  }

  private void setupAdapters() {
    lstSalesLog.setAdapter(new SalesLogAdapter(this, R.layout.admin_sales_log_list_row,
        presenter.getSalesList()));
  }

  private void setupListeners() {
    btnTotalSales.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(SalesLogActivity.this, SalesLogTotalActivity.class);
        intent.putExtra("totalItemizedSales",
            (HashMap<Item, Integer>) presenter.getTotalItemizedSales());
        startActivity(intent);
      }
    });

    lstSalesLog.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Sale sale = (Sale) parent.getItemAtPosition(position);

        Intent intent = new Intent(SalesLogActivity.this, SalesLogDetailActivity.class);
        intent.putExtra("customerName", sale.getUser().getName());
        intent.putExtra("saleNumber", sale.getId());
        intent.putExtra("totalPrice", sale.getTotalPrice().toString());
        intent.putExtra("itemMap", sale.getItemMap());

        startActivity(intent, ActivityOptionsCompat
            .makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight()).toBundle());
      }
    });
  }

  private void setText() {
    String totalPrice = "Total Sales: $" + presenter.getTotalPrice();
    txtTotalSales.setText(totalPrice);
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

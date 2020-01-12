package com.b07.ui.admin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.b07.domain.Sale;
import com.b07.salesapplication.R;
import java.util.List;

public class SalesLogAdapter extends ArrayAdapter<Sale> {

  private int layoutResource;

  public SalesLogAdapter(Context context, int layoutResource, List<Sale> salesList) {
    super(context, layoutResource, salesList);
    this.layoutResource = layoutResource;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
      convertView = layoutInflater.inflate(layoutResource, null);
    }

    Sale sale = getItem(position);

    TextView saleIdView = convertView.findViewById(R.id.saleId);
    TextView totalPriceView = convertView.findViewById(R.id.totalPrice);
    TextView customerNameView = convertView.findViewById(R.id.customerName);

    if (sale != null) {
      String saleIdText = "Purchase Number: " + sale.getId();
      saleIdView.setText(saleIdText);

      String totalPriceText = "Total Purchase Price: $" + sale.getTotalPrice().toString();
      totalPriceView.setText(totalPriceText);

      String customerNameText = "Customer: " + sale.getUser().getName();
      customerNameView.setText(customerNameText);
    }
    return convertView;
  }
}

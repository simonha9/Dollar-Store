package com.b07.ui.customer;

import androidx.annotation.NonNull;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b07.domain.Item;
import com.b07.domain.impl.ItemImpl;
import com.b07.exceptions.DataExportException;
import com.b07.exporter.ItemExporter;
import com.b07.salesapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.BaseAdapter;

public class CustomItemAdapter extends BaseAdapter {

  private int layoutResource;
  private HashMap<Item, Integer> itemMap;
  private Item[] mapKeys;
  private Context context;

  public CustomItemAdapter(@NonNull Context context, int layoutResource, HashMap<Item, Integer>
    itemMap) {
    this.context = context;
    this.itemMap = itemMap;
    this.layoutResource = layoutResource;
    this.mapKeys = itemMap.keySet().toArray(new Item [itemMap.size()]);
  }

  @Override
  public int getCount() {
    return itemMap.size();
  }

  @Override
  public Integer getItem(int position) {
    return itemMap.get(mapKeys[position]);
  }

  public int getFromList(List<Item> itemList, String name) {
    for (int i = 0; i < itemList.size(); i++) {
      if (name.equals(itemList.get(i).getName())) {
        return i;
      }
    }
    return -1;
  }

  public String getObject(int position) {
    Item item;
    item = mapKeys[position];
    return item.getName();
  }

  @Override
  public long getItemId(int arg0) {
    return arg0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    View view = convertView;

    if (view == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_cart_row,
        parent, false);
    }

    ItemExporter itemEx = new ItemExporter(parent.getContext());
    List<Item> itemList = new ArrayList<>();
    try {
      itemList = itemEx.exportRecords();
    } catch (DataExportException e) {
    }

    Integer quantity = getItem(position);

    TextView nameView = view.findViewById(R.id.itemName);
    TextView quantityView = view.findViewById(R.id.itemQuantity);
    TextView priceView = view.findViewById(R.id.itemPrice);

    String itemName = null;

    if (nameView != null) {
      itemName = getObject(position);
      nameView.setText(itemName);
    }
    if (quantityView != null) {
      String quantityText = "Quantity is " + quantity;
      quantityView.setText(quantityText);
    }
    if (priceView != null) {
      String priceText = "Price is " + itemList.get(getFromList(itemList, itemName)).getPrice();
      priceView.setText(priceText);
    }

    return view;
  }
}

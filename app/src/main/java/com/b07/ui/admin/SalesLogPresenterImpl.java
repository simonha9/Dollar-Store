package com.b07.ui.admin;

import com.b07.domain.Item;
import com.b07.domain.Sale;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesLogPresenterImpl implements SalesLogPresenter {

  private AdminView view;
  private SalesLogModel model;

  public SalesLogPresenterImpl(AdminView view) {
    this.view = view;
    model = new SalesLogModel(view.getApplicationContext());
  }

  @Override
  public List<Sale> getSalesList() {
    return model.getSales().getSales();
  }

  @Override
  public String getTotalPrice() {
    BigDecimal totalPrice = new BigDecimal("0");
    for (Sale sale : getSalesList()) {
      totalPrice = totalPrice.add(sale.getTotalPrice());
    }
    return totalPrice.toString();
  }

  @Override
  public Map<Item, Integer> getTotalItemizedSales() {
    Map<Item, Integer> totalItemMap = new HashMap<>();
    for (Sale sale : getSalesList()) {
      Map<Item, Integer> saleItemMap = sale.getItemMap();
      for (Item item : saleItemMap.keySet()) {
        totalItemMap.put(item, totalItemMap.getOrDefault(item, 0) + saleItemMap.get(item));
      }
    }
    return totalItemMap;
  }

}

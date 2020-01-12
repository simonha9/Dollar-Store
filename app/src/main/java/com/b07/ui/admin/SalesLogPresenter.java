package com.b07.ui.admin;

import com.b07.domain.Item;
import com.b07.domain.Sale;
import java.util.List;
import java.util.Map;

public interface SalesLogPresenter {

  List<Sale> getSalesList();

  String getTotalPrice();

  Map<Item, Integer> getTotalItemizedSales();
}

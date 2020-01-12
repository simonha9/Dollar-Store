package com.b07.domain.impl;

import com.b07.domain.Inventory;
import com.b07.domain.Item;
import java.util.HashMap;
import java.util.Map;

public class InventoryImpl implements Inventory {

  /**
   *
   */
  private static final long serialVersionUID = -5044295221452732865L;

  private Map<Item, Integer> itemMap = new HashMap<>();
  private transient int totalItems;

  public Map<Item, Integer> getItemMap() {
    return itemMap;
  }

  public void setItemMap(Map<Item, Integer> itemMap) {
    this.itemMap = itemMap;
    updateTotalItems();
  }

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  @Override
  public void updateMap(Item item, Integer quantity) {
    itemMap.put(item, quantity);
    totalItems = totalItems + quantity;
  }

  private void updateTotalItems() {
    totalItems = 0;
    for (Item item : itemMap.keySet()) {
      totalItems = totalItems + itemMap.get(item);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((itemMap == null) ? 0 : itemMap.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    InventoryImpl other = (InventoryImpl) obj;
    if (itemMap == null) {
      return other.itemMap == null;
    } else
      return itemMap.equals(other.itemMap);
  }


}

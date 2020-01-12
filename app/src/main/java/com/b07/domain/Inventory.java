package com.b07.domain;

import java.io.Serializable;
import java.util.Map;

public interface Inventory extends Serializable {

  /**
   * Returns the map of all the items and the quantity of each item in stock.
   *
   * @return the itemMap
   */
  Map<Item, Integer> getItemMap();

  /**
   * Sets the map of all the items and the quantity of each item in stock.
   *
   * @param itemMap the map of all the items and the quantity of each item in stock.
   */
  void setItemMap(Map<Item, Integer> itemMap);

  /**
   * Updates the quantity of the specified item with the specified value, or adds the item if it
   * does not exist in the map prior.
   *
   * @param item the item to update.
   * @param value the number of the item in stock.
   */
  void updateMap(Item item, Integer value);

  /**
   * Returns the total number of items in stock.
   *
   * @return the total number of items in stock.
   */
  int getTotalItems();

  /**
   * Sets the total number of items in stock.
   *
   * @param total the total number of items in stock.
   */
  void setTotalItems(int total);

}

package com.b07.domain;

import java.math.BigDecimal;

public interface Item extends PrimaryKey {

  /**
   * Returns the name of the item.
   *
   * @return the name of the item.
   */
  String getName();

  /**
   * Sets the name of the item.
   *
   * @param name the name to assign to the item.
   */
  void setName(String name);

  /**
   * Returns the price of the item.
   *
   * @return the price of the item.
   */
  BigDecimal getPrice();

  /**
   * Sets the price of the item.
   *
   * @param price the price to assign to the item.
   */
  void setPrice(BigDecimal price);

}

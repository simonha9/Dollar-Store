package com.b07.domain.impl;

import com.b07.domain.Item;
import com.b07.domain.PrimaryKeyObject;
import java.io.Serializable;
import java.math.BigDecimal;

public class ItemImpl extends PrimaryKeyObject implements Item, Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -4117634167202643720L;

  private String name;
  private BigDecimal price;

  public ItemImpl(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public ItemImpl(String name) {
    this.name = name;
  }

  public ItemImpl() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}

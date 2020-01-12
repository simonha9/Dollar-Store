package com.b07.domain.impl;

import com.b07.domain.Item;
import com.b07.domain.PrimaryKeyObject;
import com.b07.domain.Sale;
import com.b07.users.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public class SaleImpl extends PrimaryKeyObject implements Sale, Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -4670484324164784780L;

  private User user;
  private BigDecimal totalPrice;
  private HashMap<Item, Integer> itemMap = new HashMap<>();

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public HashMap<Item, Integer> getItemMap() {
    return itemMap;
  }

  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.itemMap = itemMap;
  }


}

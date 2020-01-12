package com.b07.users;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

  /**
   * 
   */
  private static final long serialVersionUID = 7806427654311147614L;
  
  private transient int accountId;
  
  public Customer() {};
  
  public Customer(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }

  public Customer(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  public boolean hasAccount() {
    return accountId > 0;
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  
  
  
  
}

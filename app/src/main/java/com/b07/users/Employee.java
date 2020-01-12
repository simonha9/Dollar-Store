package com.b07.users;

public class Employee extends User {

  /**
   *
   */
  private static final long serialVersionUID = -1508583445440080227L;

  public Employee() {
  }

  public Employee(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  public Employee(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }


}

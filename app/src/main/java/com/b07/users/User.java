package com.b07.users;

import com.b07.domain.PrimaryKeyObject;

public abstract class User extends PrimaryKeyObject {

  /**
   *
   */
  private static final long serialVersionUID = 9064775294451761508L;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private transient boolean authenticated;
  private String hashedPassword;

  public User() {
  }

  public User(int id, String name, int age, String address) {
    setId(id);
    this.name = name;
    this.age = age;
    this.address = address;
  }

  public User(int id, String name, int age, String address, boolean authenticated) {
    this(id, name, age, address);
    this.authenticated = authenticated;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  @Override
  public String toString() {
    return "User [name=" + name + ", age=" + age + ", address=" + address + ", roleId=" + roleId
        + ", authenticated=" + authenticated + "]";
  }

  // Moved to Authenticator to comply with Single Responsibility
  // Also Database helper methods should not be in domain class
  /*
   * public static final boolean authenticate(int userId, String password) { try { String dbPassword
   * = DatabaseSelectHelper.getPassword(userId); PasswordHelpers.comparePassword(dbPassword,
   * password); return true; } catch (SQLException e) { e.printStackTrace(); } return false; }
   */


}

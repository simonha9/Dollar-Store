package com.b07.users;

public class Admin extends User {


  /**
   *
   */
  private static final long serialVersionUID = -2926675042840457107L;

  public Admin() {
  }

  public Admin(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  public Admin(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }

  // Moved to AdminInterface so does not violate Single Responsibility
  // Database helper methods should not be in domain class
  /*
   * public boolean promoteEmployee(Employee employee) { try { int roleId =
   * DatabaseSelectHelper.getRoleIdByName(Roles.ADMIN.toString());
   * DatabaseUpdateHelper.updateUserRole(roleId, employee.getId()); return true; } catch
   * (SQLException | ValidationFailedException e) { e.printStackTrace(); } return false; }
   */

}

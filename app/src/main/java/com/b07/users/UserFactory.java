package com.b07.users;

import com.b07.enums.Roles;
import java.util.HashMap;
import java.util.Map;

public class UserFactory {

  private static final Map<Roles, Class<?>> roleUserMap = new HashMap<>();

  static {
    roleUserMap.put(Roles.ADMIN, Admin.class);
    roleUserMap.put(Roles.CUSTOMER, Customer.class);
    roleUserMap.put(Roles.EMPLOYEE, Employee.class);
  }

  public static User buildUser(String roleName) {
    Class<?> userClass = roleUserMap.get(Roles.valueOf(roleName));
    try {
      return (User) userClass.newInstance();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}

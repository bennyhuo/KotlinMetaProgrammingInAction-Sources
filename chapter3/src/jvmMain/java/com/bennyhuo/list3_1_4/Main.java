package com.bennyhuo.list3_1_4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
  public static void main(String[] args) throws Exception {
    // 3-1
    for (Field field : User.class.getDeclaredFields()) {
      System.out.println(field.getName() + ", ");
    }

    // 3-2
    User user = new User();
    Field nameField = User.class.getDeclaredField("name");
    nameField.set(user, "bennyhuo");
    System.out.println(nameField.get(user));

    // 3-3
    Method getIdMethod = User.class.getDeclaredMethod("getId");
    long id = (long) getIdMethod.invoke(user);
    System.out.println(id == user.getId());

    // 3-4
    Field idField = User.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(user, 1000);
    System.out.println(idField.get(user));
  }
}

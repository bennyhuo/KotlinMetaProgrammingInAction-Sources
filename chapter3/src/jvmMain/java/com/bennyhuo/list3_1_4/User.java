package com.bennyhuo.list3_1_4;

import java.util.Random;

public class User {
  private long id = new Random().nextLong();
  public String name;
  public int age;

  public long getId() {
    return id;
  }
}
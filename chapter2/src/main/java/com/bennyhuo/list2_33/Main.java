package com.bennyhuo.list2_33;

import com.bennyhuo.lib.CollectionsKt;

import java.util.ArrayList;

/**
 * Created by benny.
 */
public class Main {
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    list.add("Hello");
    list.add("World");
    if(CollectionsKt.isNotNullAndNotEmpty(list)) {
      list.forEach(System.out::println);
    }
  }
}

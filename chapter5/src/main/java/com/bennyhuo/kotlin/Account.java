package com.bennyhuo.kotlin;

import com.bennyhuo.kotlin.annotations.Export;

/**
 * Created by benny.
 */
@Export
public class Account {
  private final String id;

  public Account(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}

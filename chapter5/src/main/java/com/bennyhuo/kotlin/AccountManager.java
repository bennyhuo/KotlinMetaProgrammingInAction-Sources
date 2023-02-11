package com.bennyhuo.kotlin;

import com.bennyhuo.kotlin.annotations.Export;

/**
 * Created by benny.
 */
@Export
public class AccountManager {

  @Export
  public static class Holder {
    private static AccountManager instance = new AccountManager();
  }

  public static AccountManager getInstance() {
    return Holder.instance;
  }

}

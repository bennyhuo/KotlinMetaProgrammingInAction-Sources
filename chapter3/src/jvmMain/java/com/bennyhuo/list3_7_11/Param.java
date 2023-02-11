package com.bennyhuo.list3_7_11;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 3-9
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
  String value();
}

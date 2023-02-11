package com.bennyhuo.kotlin.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

/**
 * Created by benny.
 */
public class ObjectUtils {
  @NotNull
  public static <T> String join(@NotNull List<T> $receiver, @NotNull String separator) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<T> iterator = $receiver.iterator();
    if (iterator.hasNext()) {
      stringBuilder.append(iterator.next());
    }
    while (iterator.hasNext()) {
      stringBuilder.append(separator).append(iterator.next());
    }

    return stringBuilder.toString();
  }

//  public static <P1 extends Number, P2 extends Number, R extends Number & Comparable<Number>> R call(P1 p1, P2 p2) {
//    throw new UnsupportedOperationException();
//  }
}
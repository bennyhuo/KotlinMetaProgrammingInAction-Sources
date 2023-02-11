package com.bennyhuo.kotlin.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

// 4-19
public class StringUtils {
  @NotNull
  public static String join(@NotNull List<String> $receiver, @NotNull String separator) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<String> iterator = $receiver.iterator();
    if (iterator.hasNext()) {
      stringBuilder.append(iterator.next());
    }
    while (iterator.hasNext()) {
      stringBuilder.append(separator).append(iterator.next());
    }

    return stringBuilder.toString();
  }

  @Nullable
  public static String toStringOrNull(@Nullable Object $receiver) {
    return $receiver == null ? null : $receiver.toString();
  }
}
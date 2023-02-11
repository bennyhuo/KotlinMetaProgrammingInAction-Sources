package com.bennyhuo.kotlin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 4-28
public class DateUtils {

  public static Calendar toCalendar(Date $receiver) {
    Calendar cal = Calendar.getInstance();
    cal.setTime($receiver);
    return cal;
  }

  public static String format(Date date, String format) {
    return new SimpleDateFormat(format).format(date);
  }

  public static Date next(Date $receiver) {
    return plus($receiver, 1);
  }

  public static Date prev(Date $receiver) {
    return minus($receiver, 1);
  }

  public static Date plus(Date $receiver, int days) {
    Calendar calendar = (Calendar) Calendar.getInstance().clone();
    calendar.setTime($receiver);
    calendar.add(Calendar.DAY_OF_YEAR, days);
    return calendar.getTime();
  }

  public static Date minus(Date $receiver, int days) {
    return plus($receiver, -days);
  }
}

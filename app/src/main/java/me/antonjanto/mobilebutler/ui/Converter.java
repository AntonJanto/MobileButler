package me.antonjanto.mobilebutler.ui;

import java.util.Locale;

public class Converter
{
     public static String toDecimal(Double d)
     {
          return String.format(Locale.ENGLISH, "%.2f", d);
     }

     public static String toInteger(Long l)
     {
          return String.valueOf(l);
     }

     public static String toInteger(Integer i)
     {
          return String.valueOf(i);
     }
}

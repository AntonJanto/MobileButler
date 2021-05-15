package me.antonjanto.mobilebutler.ui;

public class Converter
{
     public static String toDecimal(Double d)
     {
          return String.format("%.2f", d);
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

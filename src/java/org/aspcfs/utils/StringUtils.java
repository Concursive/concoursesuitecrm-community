package com.darkhorseventures.utils;

import java.util.zip.*;
import java.io.*;

public class StringUtils {

  public static String allowed = "-0123456789.";

  public static int getIntegerNumber(String in) {
    return Integer.parseInt(getNumber(in)); 
  }

  public static double getDoubleNumber(String in) {
    return Double.parseDouble(getNumber(in)); 
  }
  
  private static String getNumber(String in) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
      if (allowed.indexOf(in.charAt(i)) > -1) {
        sb.append(in.charAt(i));
      }
    }
    return sb.toString();
  }

}

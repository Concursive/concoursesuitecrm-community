package com.darkhorseventures.utils;

public class HTTPUtils {
  
  public static String toHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = StringHelper.replace(htmlReady, "\"", "&quot;");
      htmlReady = StringHelper.replace(htmlReady, "<", "&lt;");
      htmlReady = StringHelper.replace(htmlReady, ">", "&gt;");
      htmlReady = StringHelper.replace(htmlReady, "\r\n", "<br>");
      htmlReady = StringHelper.replace(htmlReady, "\n\r", "<br>");
      htmlReady = StringHelper.replace(htmlReady, "\n", "<br>");
      htmlReady = StringHelper.replace(htmlReady, "\r", "<br>");
      return(htmlReady);
    } else {
      return("");
    }
  }
  
}

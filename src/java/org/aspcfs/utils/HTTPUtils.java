package com.darkhorseventures.utils;

import java.io.*;
import java.net.*;

/**
 *  Utilities for working with HTTP
 *
 *@author     matt rajkowski
 *@created    August 29, 2002
 *@version    $Id$
 */
public class HTTPUtils {

  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
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
      return (htmlReady);
    } else {
      return ("");
    }
  }


  /**
   *  Sends a string to the specified URL
   *
   *@param  address                  Description of the Parameter
   *@param  xmlPacket                Description of the Parameter
   *@return                          Description of the Return Value
   *@exception  java.io.IOException  Description of the Exception
   */
  public static String sendPacket(String address, String xmlPacket) throws java.io.IOException {
    Exception errorMessage = null;
    try {
      URL url = new URL(address);
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod("POST");
      http.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
      http.setDoOutput(true);
      PrintWriter outStream = new PrintWriter(http.getOutputStream());
      outStream.println(xmlPacket);
      outStream.close();
      return (retrieveHtml(http));
    } catch (java.net.MalformedURLException e) {
      errorMessage = e;
    } catch (java.io.IOException e) {
      errorMessage = e;
    }
    if (errorMessage != null) {
      throw new java.io.IOException(errorMessage.toString());
    }
    return null;
  }


  /**
   *  Returns the text received from a web post
   *
   *@param  http                     Description of the Parameter
   *@return                          Description of the Return Value
   *@exception  java.io.IOException  Description of the Exception
   */
  public static String retrieveHtml(HttpURLConnection http) throws java.io.IOException {
    StringBuffer htmlOutput = new StringBuffer();
    InputStreamReader input = new InputStreamReader(http.getInputStream());
    BufferedReader inStream = new BufferedReader(input);
    String inputLine;
    while ((inputLine = inStream.readLine()) != null) {
      htmlOutput.append(inputLine + System.getProperty("line.separator"));
    }
    inStream.close();
    return htmlOutput.toString();
  }

}


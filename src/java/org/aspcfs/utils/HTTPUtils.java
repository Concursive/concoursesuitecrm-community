package org.aspcfs.utils;

import java.io.*;
import java.net.*;

/**
 *  Utilities for working with HTTP
 *
 *@author     matt rajkowski
 *@created    August 29, 2002
 *@version    $Id: HTTPUtils.java,v 1.2.20.1 2002/12/06 21:37:00 mrajkowski Exp
 *      $
 */
public class HTTPUtils {

  /**
   *  Generates acceptable default html text when using an input field on an
   *  html form
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


  /**
   *  Downloads a URL into a postscript file. Currently uses html2ps, but for
   *  Windows compatibility may need to use htmldoc after testing.
   *
   *@param  url           Description of the Parameter
   *@param  baseFilename  Description of the Parameter
   *@return               Description of the Return Value
   */
  public static int convertUrlToPostscriptFile(String url, String baseFilename) {
    Process process;
    Runtime runtime;
    java.io.InputStream input;
    byte buffer[];
    int bytes;
    String command[] = null;

    File osCheckFile = new File("/bin/sh");
    if (osCheckFile.exists()) {
      //Linux
      command = new String[]{"/bin/sh", "-c", "/usr/bin/htmldoc --quiet " +
          "-f " + baseFilename + ".ps " +
          "--webpage " +
          "-t ps " +
          "--header ... " +
          "--footer ... " +
          url};
    } else {
      //Windows
      command = new String[]{"htmldoc", "--quiet " +
          "-f " + baseFilename + ".ps " +
          "--webpage " +
          "-t ps3 " +
          "--header ... " +
          "--footer ... " +
          url};
    }
    runtime = Runtime.getRuntime();
    try {
      process = runtime.exec(command);
      return (process.waitFor());
    } catch (Exception e) {
      System.err.println("HTTPUtils-> urlToPostscriptFile error: " + e.toString());
      return (1);
    }
  }

}


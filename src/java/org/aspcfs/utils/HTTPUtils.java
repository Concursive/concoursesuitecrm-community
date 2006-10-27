/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Utilities for working with HTTP
 *
 * @author matt rajkowski
 * @version $Id: HTTPUtils.java,v 1.2.20.1 2002/12/06 21:37:00 mrajkowski Exp
 *          $
 * @created August 29, 2002
 */
public class HTTPUtils {
  /**
   * Generates acceptable html text when displaying in HTML, especially useful
   * within a table cell because a cell should not be left empty
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toHtml(String s) {
    if (s != null) {
      if (s.trim().equals("")) {
        return ("&nbsp;");
      } else {
        return toHtmlValue(s);
      }
    } else {
      return ("&nbsp;");
    }
  }


  /**
   * Generates acceptable default html text when using an input field on an
   * html form
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = StringUtils.replace(htmlReady, "\"", "&quot;");
      htmlReady = StringUtils.replace(htmlReady, "<", "&lt;");
      htmlReady = StringUtils.replace(htmlReady, ">", "&gt;");
      htmlReady = StringUtils.replace(htmlReady, "\r\n", "<br>");
      htmlReady = StringUtils.replace(htmlReady, "\n\r", "<br>");
      htmlReady = StringUtils.replace(htmlReady, "\n", "<br>");
      htmlReady = StringUtils.replace(htmlReady, "\r", "<br>");
      return (htmlReady);
    } else {
      return ("");
    }
  }


  /**
   * Description of the Method
   *
   * @param address   Description of the Parameter
   * @param xmlPacket Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException Description of the Exception
   */
  public static String sendPacket(String address, String xmlPacket) throws java.io.IOException {
    return sendPacket(address, xmlPacket, null);
  }


  /**
   * Sends a string to the specified URL, intended for communicating with web
   * servers. Use the SSLMessage for secure communication with a server
   * application.
   *
   * @param address   Description of the Parameter
   * @param xmlPacket Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException Description of the Exception
   */
  public static String sendPacket(String address, String xmlPacket, HashMap headers) throws java.io.IOException {
    Exception errorMessage = null;
    try {
      //The default factory requires a trusted certificate
      //Accept any certificate using the custom TrustManager
      X509TrustManager xtm = new HttpsTrustManager();
      TrustManager mytm[] = {xtm};
      SSLContext ctx = SSLContext.getInstance("SSL");
      ctx.init(null, mytm, null);
      SSLSocketFactory factory = ctx.getSocketFactory();
      //Get a connection
      URL url = new URL(address);
      URLConnection conn = url.openConnection();
      //Override the default certificates
      if (conn instanceof HttpsURLConnection) {
        ((HttpsURLConnection) conn).setSSLSocketFactory(factory);
        ((HttpsURLConnection) conn).setHostnameVerifier(
            new HttpsHostnameVerifier());
      }
      //Backwards compatible if something sets the old system property
      if (conn instanceof com.sun.net.ssl.HttpsURLConnection) {
        ((com.sun.net.ssl.HttpsURLConnection) conn).setSSLSocketFactory(
            factory);
        ((com.sun.net.ssl.HttpsURLConnection) conn).setHostnameVerifier(
            new HttpsHostnameVerifierDeprecated());
      }
      ((HttpURLConnection) conn).setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
      if (headers.size() > 0) {
        Iterator i = headers.keySet().iterator();
        while (i.hasNext()) {
          String header = (String) i.next();
          String value = (String) headers.get(header);
          conn.setRequestProperty(header, value);
        }
      }
      conn.setDoInput(true);
      conn.setDoOutput(true);
      OutputStreamWriter out = new OutputStreamWriter(
          conn.getOutputStream(), "UTF8");
      out.write(xmlPacket);
      out.close();
      return (retrieveHtml(conn));
    } catch (java.net.MalformedURLException e) {
      errorMessage = e;
    } catch (java.io.IOException e) {
      errorMessage = e;
    } catch (java.security.KeyManagementException e) {
      errorMessage = e;
    } catch (java.security.NoSuchAlgorithmException e) {
      errorMessage = e;
    }
    if (errorMessage != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("HTTPUtils-> sendPacket error: " + errorMessage.getMessage());
      }
      throw new java.io.IOException(errorMessage.toString());
    }
    return null;
  }


  /**
   * Returns the text received from a web post
   *
   * @param http Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException Description of the Exception
   */
  public static String retrieveHtml(URLConnection http) throws java.io.IOException {
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
   * Downloads a URL into a postscript file. Currently uses html2ps, but for
   * Windows compatibility may need to use htmldoc after testing.
   *
   * @param url          Description of the Parameter
   * @param baseFilename Description of the Parameter
   * @return Description of the Return Value
   */
  public static int convertUrlToPostscriptFile(String url, String baseFilename) {
    Process process;
    Runtime runtime;
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
      System.err.println(
          "HTTPUtils-> urlToPostscriptFile error: " + e.toString());
      return (1);
    }
  }


  /**
   * Connects to a web server and gets the Server header field
   *
   * @param address Description of the Parameter
   * @return The serverName value
   */
  public static String getServerName(String address) {
    try {
      //The default factory requires a trusted certificate
      //Accept any certificate using the custom TrustManager
      X509TrustManager xtm = new HttpsTrustManager();
      TrustManager mytm[] = {xtm};
      SSLContext ctx = SSLContext.getInstance("SSL");
      ctx.init(null, mytm, null);
      SSLSocketFactory factory = ctx.getSocketFactory();
      //Get a connection
      URL url = new URL(address);
      URLConnection conn = url.openConnection();
      //Override the default certificates
      if (conn instanceof HttpsURLConnection) {
        ((HttpsURLConnection) conn).setSSLSocketFactory(factory);
        ((HttpsURLConnection) conn).setHostnameVerifier(
            new HttpsHostnameVerifier());
      }
      //Backwards compatible if something sets the old system property
      if (conn instanceof com.sun.net.ssl.HttpsURLConnection) {
        ((com.sun.net.ssl.HttpsURLConnection) conn).setSSLSocketFactory(
            factory);
        ((com.sun.net.ssl.HttpsURLConnection) conn).setHostnameVerifier(
            new HttpsHostnameVerifierDeprecated());
      }
      if (conn instanceof HttpURLConnection) {
        HttpURLConnection httpConnection = (HttpURLConnection) conn;
        httpConnection.setRequestMethod("HEAD");
        httpConnection.connect();
        return httpConnection.getHeaderField("Server");
      }
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("HTTPUtils-> getServerName error: " + e.getMessage());
        e.printStackTrace(System.out);
      }
    }
    return null;
  }
}


package org.aspcfs.utils;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import javax.servlet.http.*;
import java.security.cert.CertificateFactory;
import javax.security.cert.*;
import java.security.*;

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
   *  Generates acceptable html text when displaying in HTML, especially useful
   *  within a table cell because a cell should not be left empty
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
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
   *  Sends a string to the specified URL, intended for communicating with web
   *  servers. Use the SSLMessage for secure communication with a server
   *  application.
   *
   *@param  address                  Description of the Parameter
   *@param  xmlPacket                Description of the Parameter
   *@return                          Description of the Return Value
   *@exception  java.io.IOException  Description of the Exception
   */
  public static String sendPacket(String address, String xmlPacket) throws java.io.IOException {
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
        ((HttpsURLConnection) conn).setHostnameVerifier(new HttpsHostnameVerifier());
      }
      //Backwards compatible if something sets the old system property
      if (conn instanceof com.sun.net.ssl.HttpsURLConnection) {
        ((com.sun.net.ssl.HttpsURLConnection) conn).setSSLSocketFactory(factory);
        ((com.sun.net.ssl.HttpsURLConnection) conn).setHostnameVerifier(new HttpsHostnameVerifierDeprecated());
      }
      ((HttpURLConnection) conn).setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
      conn.setDoInput(true);
      conn.setDoOutput(true);
      PrintWriter outStream = new PrintWriter(conn.getOutputStream());
      //Make the socket connection
      outStream.println(xmlPacket);
      outStream.close();
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


  /**
   *  Adds a feature to the LinkParams attribute of the HTTPUtils class
   *
   *@param  request  The feature to be added to the LinkParams attribute
   *@param  tmp      The feature to be added to the LinkParams attribute
   *@return          Description of the Return Value
   */
  public static String addLinkParams(HttpServletRequest request, String tmp) {
    String params = "";
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()) {
      String param = tokens.nextToken();
      if (request.getParameter(param) != null) {
        params += ("&" + param + "=" + request.getParameter(param));
      }
    }
    return params;
  }


  /**
   *  Returns the server's url that was specified in the request, excluding the
   *  scheme
   *
   *@param  request  Description of the Parameter
   *@return          The serverURL value
   */
  public static String getServerUrl(HttpServletRequest request) {
    int port = request.getServerPort();
    return (request.getServerName() + (port != 80 && port != 443 ? ":" + String.valueOf(port) : "") + request.getContextPath());
  }


  /**
   *  Connects to a web server and gets the Server header field
   *
   *@param  address  Description of the Parameter
   *@return          The serverName value
   */
  public static String getServerName(String address) {
    try {
      URL url = new URL(address);
      URLConnection conn = url.openConnection();
      if (conn instanceof HttpURLConnection) {
        HttpURLConnection httpConnection = (HttpURLConnection) conn;
        httpConnection.setRequestMethod("HEAD");
        httpConnection.connect();
        return httpConnection.getHeaderField("Server");
      }
    } catch (Exception e) {
    }
    return null;
  }
}


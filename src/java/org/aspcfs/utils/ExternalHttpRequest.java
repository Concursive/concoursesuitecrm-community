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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *  A utility to retrieve HTML from a website and store it in a string. Can
 *  retrieve from the specified link with or without specifying request
 *  parameters.
 *
 *@author     mrajkowski
 *@created    August 3, 2001
 *@version    $Id: ExternalHttpRequest.java,v 1.1 2001/08/03 17:12:39 mrajkowski
 *      Exp $
 */
public class ExternalHttpRequest {

  StringBuffer htmlOutput = new StringBuffer();
  int errorCode = 0;
  String errorMessage = "";


  /**
   *  Constructor for the ExternalHttpRequest object... does nothing.
   *
   *@since    1.0
   */
  public ExternalHttpRequest() { }


  /**
   *  Constructor for the ExternalHttpRequest object. Retrieves the specified
   *  url and stores in a string.
   *
   *@param  link  Description of Parameter
   *@since        1.0
   */
  public ExternalHttpRequest(String link) {
    try {
      URL url = new URL(link);
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      retrieveHtml(http);
    } catch (java.net.MalformedURLException e) {
      errorCode = 1;
      errorMessage = e.toString();
    } catch (java.io.IOException e) {
      errorCode = 2;
      errorMessage = e.toString();
    }
  }


  /**
   *  Constructor for the ExternalHttpRequest object. Retrieves the specified
   *  url and stores in a string, but also passes any parameters in the current
   *  HttpServletRequest to the url before getting the results. <p>
   *
   *  The 'command' parameter is ignored because that is used by the servlet
   *  controller.
   *
   *@param  request   Description of Parameter
   *@param  link      Description of Parameter
   *@param  username  Description of the Parameter
   *@since            1.0
   */
  public ExternalHttpRequest(HttpServletRequest request, String link, String username) {

    //Process all request parameters to be used when retrieving the requested link
    Enumeration parameters = request.getParameterNames();
    StringBuffer parameterList = new StringBuffer();

    if (username != null) {
      parameterList.append("ckname=" + username);
    }

    while (parameters.hasMoreElements()) {
      String param = (String) parameters.nextElement();
      if (param.equals("command")) {
        //Throw it away because the servlet controller uses this only
      } else {
        parameterList.append(((parameterList.length() == 0) ? "" : "&") + param + "=" + request.getParameter(param));
      }
    }

    //Forward the parameters to the request Http page using the
    //appropriate request type
    try {

      URL url;
      HttpURLConnection http;

      if (request.getMethod().equals("POST")) {
        //Post to the page
        url = new URL(link);
        http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        PrintWriter outStream = new PrintWriter(http.getOutputStream());
        outStream.println(parameterList.toString());
        outStream.close();
      } else {
        //Get the page
        if (parameterList.length() > 0) {
          url = new URL(link + "?" + parameterList.toString());
        } else {
          url = new URL(link);
        }
        http = (HttpURLConnection) url.openConnection();
      }

      retrieveHtml(http);

    } catch (java.net.MalformedURLException e) {
      errorCode = 1;
      errorMessage = e.toString();
    } catch (java.io.IOException e) {
      errorCode = 2;
      errorMessage = e.toString();
    }
  }


  /**
   *  Gets the HtmlOutput attribute of the ExternalHttpRequest object
   *
   *@return    The HtmlOutput value
   *@since     1.0
   */
  public String getHtmlOutput() {
    return htmlOutput.toString();
  }


  /**
   *  Gets the ErrorCode attribute of the ExternalHttpRequest object. A result
   *  equal to 0 means no error.
   *
   *@return    The ErrorCode value
   *@since     1.0
   */
  public int getErrorCode() {
    return errorCode;
  }


  /**
   *  Gets the ErrorMessage attribute of the ExternalHttpRequest object
   *
   *@return    The ErrorMessage value
   *@since     1.0
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Method to retrieve the HTML.
   *
   *@param  http                     Description of Parameter
   *@exception  java.io.IOException  Description of Exception
   *@since                           1.0
   */
  private void retrieveHtml(HttpURLConnection http) throws java.io.IOException {
    //Get the requested page
    InputStreamReader input = new InputStreamReader(http.getInputStream());
    BufferedReader inStream = new BufferedReader(input);
    String inputLine;
    while ((inputLine = inStream.readLine()) != null) {
      htmlOutput.append(inputLine + System.getProperty("line.separator"));
    }
    inStream.close();
  }

}


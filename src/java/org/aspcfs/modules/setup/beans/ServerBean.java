//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.setup.beans;

import com.darkhorseventures.framework.beans.*;
import java.util.StringTokenizer;

/**
 *  Bean to encapsulate the Configure Server HTML form
 *
 *@author     mrajkowski
 *@created    August 26, 2003
 *@version    $Id: ServerBean.java,v 1.1.2.1 2003/08/26 21:16:19 mrajkowski Exp
 *      $
 */
public class ServerBean extends GenericBean {

  private int configured = -1;
  private String url = null;
  private String email = null;
  private String emailAddress = null;
  private String fax = null;


  /**
   *  Sets the configured attribute of the ServerBean object
   *
   *@param  tmp  The new configured value
   */
  public void setConfigured(int tmp) {
    this.configured = tmp;
  }


  /**
   *  Sets the configured attribute of the ServerBean object
   *
   *@param  tmp  The new configured value
   */
  public void setConfigured(String tmp) {
    this.configured = Integer.parseInt(tmp);
  }


  /**
   *  Sets the url attribute of the ServerBean object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the email attribute of the ServerBean object
   *
   *@param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the emailAddress attribute of the ServerBean object
   *
   *@param  tmp  The new emailAddress value
   */
  public void setEmailAddress(String tmp) {
    this.emailAddress = tmp;
  }


  /**
   *  Sets the fax attribute of the ServerBean object
   *
   *@param  tmp  The new fax value
   */
  public void setFax(String tmp) {
    this.fax = tmp;
  }


  /**
   *  Gets the configured attribute of the ServerBean object
   *
   *@return    The configured value
   */
  public int getConfigured() {
    return configured;
  }


  /**
   *  Gets the url attribute of the ServerBean object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the email attribute of the ServerBean object
   *
   *@return    The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   *  Gets the emailAddress attribute of the ServerBean object
   *
   *@return    The emailAddress value
   */
  public String getEmailAddress() {
    return emailAddress;
  }


  /**
   *  Gets the fax attribute of the ServerBean object
   *
   *@return    The fax value
   */
  public String getFax() {
    return fax;
  }


  /**
   *  Gets the valid attribute of the ServerBean object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    errors.clear();
    if (url == null || "".equals(url.trim())) {
      errors.put("urlError", "URL is a required field");
    }
    if (email == null || "".equals(email.trim())) {
      errors.put("emailError", "Email server is a required field");
    }
    if (emailAddress == null || "".equals(emailAddress.trim())) {
      errors.put("emailAddressError", "Email address is a required field");
    }
    return (!hasErrors());
  }


  /**
   *  Sets the serverInfo attribute of the ServerBean object
   *
   *@param  tmp  The new serverInfo value
   */
  public void setServerInfo(String tmp) {
    StringTokenizer st = new StringTokenizer(tmp, "|");
    while (st.hasMoreTokens()) {
      String pair = st.nextToken();
      String param = pair.substring(0, pair.indexOf("="));
      String value = pair.substring(pair.indexOf("=") + 1);
      if ("email".equals(param)) {
        email = value;
      } else if ("addr".equals(param)) {
        emailAddress = value;
      } else if ("fax".equals(param)) {
        fax = value;
      } else if ("url".equals(param)) {
        url = value;
      }
    }
  }


  /**
   *  Gets the serverInfo attribute of the ServerBean object
   *
   *@return    The serverInfo value
   */
  public String getServerInfo() {
    return "email=" + email + "|" +
        "addr=" + emailAddress + "|" +
        "fax=" + fax + "|" +
        "url=" + url;
  }
}


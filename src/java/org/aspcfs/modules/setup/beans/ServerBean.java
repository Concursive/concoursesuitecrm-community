//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.setup.beans;

import com.darkhorseventures.framework.beans.*;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.text.NumberFormat;

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
  private String timeZone = null;
  private String currency = null;
  private String language = null;
  private String country = null;


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
   *  Sets the timeZone attribute of the ServerBean object
   *
   *@param  tmp  The new timeZone value
   */
  public void setTimeZone(String tmp) {
    this.timeZone = tmp;
  }


  /**
   *  Gets the currency attribute of the ServerBean object
   *
   *@return    The currency value
   */
  public String getCurrency() {
    return currency;
  }


  /**
   *  Sets the currency attribute of the ServerBean object
   *
   *@param  tmp  The new currency value
   */
  public void setCurrency(String tmp) {
    this.currency = tmp;
  }


  /**
   *  Gets the language attribute of the ServerBean object
   *
   *@return    The language value
   */
  public String getLanguage() {
    return language;
  }


  /**
   *  Sets the language attribute of the ServerBean object
   *
   *@param  tmp  The new language value
   */
  public void setLanguage(String tmp) {
    this.language = tmp;
  }


  /**
   *  Gets the country attribute of the ServerBean object
   *
   *@return    The country value
   */
  public String getCountry() {
    return country;
  }


  /**
   *  Sets the country attribute of the ServerBean object
   *
   *@param  tmp  The new country value
   */
  public void setCountry(String tmp) {
    this.country = tmp;
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
   *  Gets the timeZone attribute of the ServerBean object
   *
   *@return    The timeZone value
   */
  public String getTimeZone() {
    return timeZone;
  }


  /**
   *  Gets the timeZoneDefault attribute of the ServerBean object
   *
   *@return    The timeZoneDefault value
   */
  public String getTimeZoneDefault() {
    if (timeZone != null) {
      return timeZone;
    } else {
      return TimeZone.getDefault().getID();
    }
  }


  /**
   *  Gets the currencyDefault attribute of the ServerBean object
   *
   *@return    The currencyDefault value
   */
  public String getCurrencyDefault() {
    if (currency != null) {
      return currency;
    } else {
      return NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
    }
  }


  /**
   *  Gets the languageDefault attribute of the ServerBean object
   *
   *@return    The languageDefault value
   */
  public String getLanguageDefault() {
    if (language != null) {
      return language;
    } else {
      return "en_US";
    }
  }


  /**
   *  Gets the countryDefault attribute of the ServerBean object
   *
   *@return    The countryDefault value
   */
  public String getCountryDefault() {
    if (country != null) {
      return country;
    } else {
      return "UNITED STATES";
    }
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
    if (timeZone == null || "".equals(timeZone.trim()) || "-1".equals(timeZone)) {
      errors.put("timeZoneError", "Time Zone is a required field");
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
      } else if ("timezone".equals(param)) {
        timeZone = value;
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
        "url=" + url + "|" +
        "timezone=" + timeZone;
  }
}


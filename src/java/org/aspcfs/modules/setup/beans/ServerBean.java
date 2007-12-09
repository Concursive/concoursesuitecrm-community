/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.setup.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.DatabaseUtils;

import java.text.NumberFormat;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * Bean to encapsulate the Configure Server HTML form
 *
 * @author mrajkowski
 * @version $Id: ServerBean.java,v 1.1.2.1 2003/08/26 21:16:19 mrajkowski Exp
 *          $
 * @created August 26, 2003
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
  private String asteriskUrl = null;
  private String asteriskUsername = null;
  private String asteriskPassword = null;
  private String asteriskContext = null;
  private boolean asteriskInbound = false;
  private boolean asteriskOutbound = false;
  private String xmppUrl = null;
  private int xmppPort = 5222;
  private boolean xmppSSL = false;
  private String xmppUsername = null;
  private String xmppPassword = null;
  private boolean xmppEnabled = false;
  private String ldapCentricCRMField = null;
  private String ldapFactory = null;
  private String ldapUrl = null;
  private boolean ldapSearchByAttribute = true;
  private String ldapSearchUsername = null;
  private String ldapSearchPassword = null;
  private String ldapSearchContainer = null;
  private String ldapSearchOrgPerson = null;
  private boolean ldapSearchSubtree = true;
  private String ldapSearchAttribute = null;
  private String ldapSearchPrefix = null;
  private String ldapSearchPostfix = null;
  private boolean ldapEnabled = false;

  /**
   * Sets the configured attribute of the ServerBean object
   *
   * @param tmp The new configured value
   */
  public void setConfigured(int tmp) {
    this.configured = tmp;
  }


  /**
   * Sets the configured attribute of the ServerBean object
   *
   * @param tmp The new configured value
   */
  public void setConfigured(String tmp) {
    this.configured = Integer.parseInt(tmp);
  }


  /**
   * Sets the url attribute of the ServerBean object
   *
   * @param tmp The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   * Sets the email attribute of the ServerBean object
   *
   * @param tmp The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   * Sets the emailAddress attribute of the ServerBean object
   *
   * @param tmp The new emailAddress value
   */
  public void setEmailAddress(String tmp) {
    this.emailAddress = tmp;
  }


  /**
   * Sets the fax attribute of the ServerBean object
   *
   * @param tmp The new fax value
   */
  public void setFax(String tmp) {
    this.fax = tmp;
  }


  /**
   * Sets the timeZone attribute of the ServerBean object
   *
   * @param tmp The new timeZone value
   */
  public void setTimeZone(String tmp) {
    this.timeZone = tmp;
  }


  /**
   * Gets the currency attribute of the ServerBean object
   *
   * @return The currency value
   */
  public String getCurrency() {
    return currency;
  }


  /**
   * Sets the currency attribute of the ServerBean object
   *
   * @param tmp The new currency value
   */
  public void setCurrency(String tmp) {
    this.currency = tmp;
  }


  /**
   * Gets the language attribute of the ServerBean object
   *
   * @return The language value
   */
  public String getLanguage() {
    return language;
  }


  /**
   * Sets the language attribute of the ServerBean object
   *
   * @param tmp The new language value
   */
  public void setLanguage(String tmp) {
    this.language = tmp;
  }


  /**
   * Gets the country attribute of the ServerBean object
   *
   * @return The country value
   */
  public String getCountry() {
    return country;
  }


  /**
   * Sets the country attribute of the ServerBean object
   *
   * @param tmp The new country value
   */
  public void setCountry(String tmp) {
    this.country = tmp;
  }


  /**
   * Gets the configured attribute of the ServerBean object
   *
   * @return The configured value
   */
  public int getConfigured() {
    return configured;
  }


  /**
   * Gets the url attribute of the ServerBean object
   *
   * @return The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   * Gets the email attribute of the ServerBean object
   *
   * @return The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   * Gets the emailAddress attribute of the ServerBean object
   *
   * @return The emailAddress value
   */
  public String getEmailAddress() {
    return emailAddress;
  }


  /**
   * Gets the fax attribute of the ServerBean object
   *
   * @return The fax value
   */
  public String getFax() {
    return fax;
  }


  /**
   * Gets the timeZone attribute of the ServerBean object
   *
   * @return The timeZone value
   */
  public String getTimeZone() {
    return timeZone;
  }


  /**
   * Gets the timeZoneDefault attribute of the ServerBean object
   *
   * @return The timeZoneDefault value
   */
  public String getTimeZoneDefault() {
    if (timeZone != null) {
      return timeZone;
    } else {
      return TimeZone.getDefault().getID();
    }
  }


  /**
   * Gets the currencyDefault attribute of the ServerBean object
   *
   * @return The currencyDefault value
   */
  public String getCurrencyDefault() {
    if (currency != null) {
      return currency;
    } else {
      return NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
    }
  }


  /**
   * Gets the languageDefault attribute of the ServerBean object
   *
   * @return The languageDefault value
   */
  public String getLanguageDefault() {
    if (language != null) {
      return language;
    } else {
      return "en_US";
    }
  }


  /**
   * Gets the countryDefault attribute of the ServerBean object
   *
   * @return The countryDefault value
   */
  public String getCountryDefault() {
    if (country != null) {
      return country;
    } else {
      return "UNITED STATES";
    }
  }

  public String getAsteriskUrl() {
    return asteriskUrl;
  }

  public void setAsteriskUrl(String asteriskUrl) {
    this.asteriskUrl = asteriskUrl;
  }

  public String getAsteriskUsername() {
    return asteriskUsername;
  }

  public void setAsteriskUsername(String asteriskUsername) {
    this.asteriskUsername = asteriskUsername;
  }

  public String getAsteriskPassword() {
    return asteriskPassword;
  }

  public void setAsteriskPassword(String asteriskPassword) {
    this.asteriskPassword = asteriskPassword;
  }

  public String getAsteriskContext() {
    return asteriskContext;
  }

  public String getAsteriskContextDefault() {
    if (asteriskContext != null) {
      return asteriskContext;
    } else {
      return "from-internal";
    }
  }


  public void setAsteriskContext(String asteriskContext) {
    this.asteriskContext = asteriskContext;
  }

  public boolean getAsteriskInbound() {
    return asteriskInbound;
  }

  public void setAsteriskInbound(boolean asteriskInbound) {
    this.asteriskInbound = asteriskInbound;
  }

  public void setAsteriskInbound(String tmp) {
    asteriskInbound = DatabaseUtils.parseBoolean(tmp);
  }

  public boolean getAsteriskOutbound() {
    return asteriskOutbound;
  }

  public void setAsteriskOutbound(boolean asteriskOutbound) {
    this.asteriskOutbound = asteriskOutbound;
  }

  public void setAsteriskOutbound(String tmp) {
    asteriskOutbound = DatabaseUtils.parseBoolean(tmp);
  }

  public String getXmppUrl() {
    return xmppUrl;
  }

  public void setXmppUrl(String xmppUrl) {
    this.xmppUrl = xmppUrl;
  }

  public int getXmppPort() {
    return xmppPort;
  }

  public void setXmppPort(String xmppPort) {
    this.xmppPort = Integer.parseInt(xmppPort);
  }

  public void setXmppPort(int xmppPort) {
    this.xmppPort = xmppPort;
  }

  public String getXmppUsername() {
    return xmppUsername;
  }

  public void setXmppUsername(String xmppUsername) {
    this.xmppUsername = xmppUsername;
  }

  public String getXmppPassword() {
    return xmppPassword;
  }

  public void setXmppPassword(String xmppPassword) {
    this.xmppPassword = xmppPassword;
  }

  public boolean getXmppEnabled() {
    return xmppEnabled;
  }

  public void setXmppEnabled(boolean xmppEnabled) {
    this.xmppEnabled = xmppEnabled;
  }

  public void setXmppEnabled(String tmp) {
    xmppEnabled = DatabaseUtils.parseBoolean(tmp);
  }

  public boolean getXmppSSL() {
    return xmppSSL;
  }

  public void setXmppSSL(boolean xmppSSL) {
    this.xmppSSL = xmppSSL;
  }

  public void setXmppSSL(String tmp) {
    xmppSSL = DatabaseUtils.parseBoolean(tmp);
  }

  public String getLdapCentricCRMField() {
    if (ldapCentricCRMField == null) {
      return "username";
    }
    return ldapCentricCRMField;
  }

  public void setLdapCentricCRMField(String ldapCentricCRMField) {
    this.ldapCentricCRMField = ldapCentricCRMField;
  }

  public String getLdapFactory() {
    if (ldapFactory == null) {
      return "com.sun.jndi.ldap.LdapCtxFactory";
    } else {
      return ldapFactory;
    }
  }

  public void setLdapFactory(String ldapFactory) {
    this.ldapFactory = ldapFactory;
  }

  public String getLdapUrl() {
    if (ldapUrl == null) {
      return "ldap://127.0.0.1:389";
    } else {
      return ldapUrl;
    }
  }

  public void setLdapUrl(String ldapUrl) {
    this.ldapUrl = ldapUrl;
  }

  public String getLdapSearchUsername() {
    if (ldapSearchUsername == null) {
      return "cn=admin,ou=IT,o=COMPANY";
    } else {
      return ldapSearchUsername;
    }
  }

  public void setLdapSearchUsername(String ldapSearchUsername) {
    this.ldapSearchUsername = ldapSearchUsername;
  }

  public String getLdapSearchPassword() {
    return ldapSearchPassword;
  }

  public void setLdapSearchPassword(String ldapSearchPassword) {
    this.ldapSearchPassword = ldapSearchPassword;
  }

  public String getLdapSearchContainer() {
    if (ldapSearchContainer == null) {
      return "o=COMPANY";
    } else {
      return ldapSearchContainer;
    }
  }

  public void setLdapSearchContainer(String ldapSearchContainer) {
    this.ldapSearchContainer = ldapSearchContainer;
  }

  public String getLdapSearchOrgPerson() {
    if (ldapSearchOrgPerson == null) {
      return "inetOrgPerson";
    } else {
      return ldapSearchOrgPerson;
    }
  }

  public void setLdapSearchOrgPerson(String ldapSearchOrgPerson) {
    this.ldapSearchOrgPerson = ldapSearchOrgPerson;
  }

  public boolean getLdapSearchSubtree() {
    return ldapSearchSubtree;
  }

  public void setLdapSearchSubtree(boolean ldapSearchSubtree) {
    this.ldapSearchSubtree = ldapSearchSubtree;
  }

  public void setLdapSearchSubtree(String tmp) {
    ldapSearchSubtree = DatabaseUtils.parseBoolean(tmp);
  }

  public String getLdapSearchAttribute() {
    if (ldapSearchAttribute == null) {
      return "mail";
    } else {
      return ldapSearchAttribute;
    }
  }

  public void setLdapSearchAttribute(String ldapSearchAttribute) {
    this.ldapSearchAttribute = ldapSearchAttribute;
  }

  public String getLdapSearchPrefix() {
    if (ldapSearchPrefix == null) {
      return "cn=";
    }
    return ldapSearchPrefix;
  }

  public void setLdapSearchPrefix(String ldapSearchPrefix) {
    this.ldapSearchPrefix = ldapSearchPrefix;
  }

  public String getLdapSearchPostfix() {
    if (ldapSearchPostfix == null) {
      return ",o=COMPANY";
    }
    return ldapSearchPostfix;
  }

  public void setLdapSearchPostfix(String ldapSearchPostfix) {
    this.ldapSearchPostfix = ldapSearchPostfix;
  }

  public boolean getLdapSearchByAttribute() {
    return ldapSearchByAttribute;
  }

  public void setLdapSearchByAttribute(boolean ldapSearchByAttribute) {
    this.ldapSearchByAttribute = ldapSearchByAttribute;
  }

  public void setLdapSearchByAttribute(String tmp) {
    ldapSearchByAttribute = DatabaseUtils.parseBoolean(tmp);
  }

  public boolean getLdapEnabled() {
    return ldapEnabled;
  }

  public void setLdapEnabled(boolean ldapEnabled) {
    this.ldapEnabled = ldapEnabled;
  }

  public void setLdapEnabled(String tmp) {
    this.ldapEnabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the serverInfo attribute of the ServerBean object
   *
   * @param tmp The new serverInfo value
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

  public void setServerInfo(ApplicationPrefs prefs) {
    if (prefs.has("SYSTEM.CURRENCY")) {
      currency = prefs.get("SYSTEM.CURRENCY");
    }
    if (prefs.has("SYSTEM.LANGUAGE")) {
      language = prefs.get("SYSTEM.LANGUAGE");
    }
    if (prefs.has("SYSTEM.COUNTRY")) {
      country = prefs.get("SYSTEM.COUNTRY");
    }
    if (prefs.has("ASTERISK.URL")) {
      asteriskUrl = prefs.get("ASTERISK.URL");
    }
    if (prefs.has("ASTERISK.USERNAME")) {
      asteriskUsername = prefs.get("ASTERISK.USERNAME");
    }
    if (prefs.has("ASTERISK.PASSWORD")) {
      asteriskPassword = prefs.get("ASTERISK.PASSWORD");
    }
    if (prefs.has("ASTERISK.CONTEXT")) {
      asteriskContext = prefs.get("ASTERISK.CONTEXT");
    }
    if (prefs.has("ASTERISK.OUTBOUND.ENABLED")) {
      asteriskInbound = "true".equals(prefs.get("ASTERISK.OUTBOUND.ENABLED"));
    }
    if (prefs.has("ASTERISK.INBOUND.ENABLED")) {
      asteriskOutbound = "true".equals(prefs.get("ASTERISK.INBOUND.ENABLED"));
    }
    if (prefs.has("XMPP.CONNECTION.URL")) {
      xmppUrl = prefs.get("XMPP.CONNECTION.URL");
    }
    if (prefs.has("XMPP.CONNECTION.PORT")) {
      xmppPort = Integer.parseInt(prefs.get("XMPP.CONNECTION.PORT"));
    }
    if (prefs.has("XMPP.CONNECTION.SSL")) {
      xmppSSL = "true".equals(prefs.get("XMPP.CONNECTION.SSL"));
    }
    if (prefs.has("XMPP.MANAGER.USERNAME")) {
      xmppUsername = prefs.get("XMPP.MANAGER.USERNAME");
    }
    if (prefs.has("XMPP.MANAGER.PASSWORD")) {
      xmppPassword = prefs.get("XMPP.MANAGER.PASSWORD");
    }
    if (prefs.has("XMPP.ENABLED")) {
      xmppEnabled = "true".equals(prefs.get("XMPP.ENABLED"));
    }
    if (prefs.has("LDAP.CENTRIC_CRM.FIELD")) {
      ldapCentricCRMField = prefs.get("LDAP.CENTRIC_CRM.FIELD");
    }
    if (prefs.has("LDAP.FACTORY")) {
      ldapFactory = prefs.get("LDAP.FACTORY");
    }
    if (prefs.has("LDAP.SERVER")) {
      ldapUrl = prefs.get("LDAP.SERVER");
    }
    if (prefs.has("LDAP.SEARCH.BY_ATTRIBUTE")) {
      ldapSearchByAttribute = "true".equals(prefs.get("LDAP.SEARCH.BY_ATTRIBUTE"));
    }
    if (prefs.has("LDAP.SEARCH.USERNAME")) {
      ldapSearchUsername = prefs.get("LDAP.SEARCH.USERNAME");
    }
    if (prefs.has("LDAP.SEARCH.PASSWORD")) {
      ldapSearchPassword = prefs.get("LDAP.SEARCH.PASSWORD");
    }
    if (prefs.has("LDAP.SEARCH.CONTAINER")) {
      ldapSearchContainer = prefs.get("LDAP.SEARCH.CONTAINER");
    }
    if (prefs.has("LDAP.SEARCH.ORGPERSON")) {
      ldapSearchOrgPerson = prefs.get("LDAP.SEARCH.ORGPERSON");
    }
    if (prefs.has("LDAP.SEARCH.SUBTREE")) {
      ldapSearchSubtree = "true".equals(prefs.get("LDAP.SEARCH.SUBTREE"));
    }
    if (prefs.has("LDAP.SEARCH.ATTRIBUTE")) {
      ldapSearchAttribute = prefs.get("LDAP.SEARCH.ATTRIBUTE");
    }
    if (prefs.has("LDAP.SEARCH.PREFIX")) {
      ldapSearchPrefix = prefs.get("LDAP.SEARCH.PREFIX");
    }
    if (prefs.has("LDAP.SEARCH.POSTFIX")) {
      ldapSearchPostfix = prefs.get("LDAP.SEARCH.POSTFIX");
    }
    if (prefs.has("LDAP.ENABLED")) {
      ldapEnabled = "true".equals(prefs.get("LDAP.ENABLED"));
    }
  }


  /**
   * Gets the serverInfo attribute of the ServerBean object
   *
   * @return The serverInfo value
   */
  public String getServerInfo() {
    return "email=" + email + "|" +
        "addr=" + emailAddress + "|" +
        "fax=" + fax + "|" +
        "url=" + url + "|" +
        "timezone=" + timeZone;
  }
}


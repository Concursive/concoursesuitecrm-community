//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.setup.beans;

import com.darkhorseventures.framework.beans.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;

/**
 *  Bean to encapsulate the Setup HTML form
 *
 *@author     mrajkowski
 *@created    August 13, 2003
 *@version    $Id: RegistrationBean.java,v 1.1.2.1 2003/08/13 15:28:42
 *      mrajkowski Exp $
 */
public class RegistrationBean extends GenericBean {

  private int configured = -1;
  private String nameFirst = null;
  private String nameLast = null;
  private String company = null;
  private String email = null;
  private boolean ssl = true;
  private String zlib = null;
  private String text = null;
  private String profile = null;
  private String webserver = null;


  /**
   *  Sets the configured attribute of the RegistrationBean object
   *
   *@param  tmp  The new configured value
   */
  public void setConfigured(int tmp) {
    this.configured = tmp;
  }


  /**
   *  Sets the configured attribute of the RegistrationBean object
   *
   *@param  tmp  The new configured value
   */
  public void setConfigured(String tmp) {
    this.configured = Integer.parseInt(tmp);
  }


  /**
   *  Sets the nameFirst attribute of the RegistrationBean object
   *
   *@param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameLast attribute of the RegistrationBean object
   *
   *@param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the company attribute of the RegistrationBean object
   *
   *@param  tmp  The new company value
   */
  public void setCompany(String tmp) {
    this.company = tmp;
  }


  /**
   *  Sets the email attribute of the RegistrationBean object
   *
   *@param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the ssl attribute of the RegistrationBean object
   *
   *@param  tmp  The new ssl value
   */
  public void setSsl(boolean tmp) {
    this.ssl = tmp;
  }


  /**
   *  Sets the ssl attribute of the RegistrationBean object
   *
   *@param  tmp  The new ssl value
   */
  public void setSsl(String tmp) {
    this.ssl = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the zlib attribute of the RegistrationBean object
   *
   *@param  tmp  The new zlib value
   */
  public void setZlib(String tmp) {
    this.zlib = tmp;
  }


  /**
   *  Sets the text attribute of the RegistrationBean object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   *  Sets the profile attribute of the RegistrationBean object
   *
   *@param  tmp  The new profile value
   */
  public void setProfile(String tmp) {
    this.profile = tmp;
  }


  /**
   *  Sets the webserver attribute of the RegistrationBean object
   *
   *@param  tmp  The new webserver value
   */
  public void setWebserver(String tmp) {
    this.webserver = tmp;
  }


  /**
   *  Gets the configured attribute of the RegistrationBean object
   *
   *@return    The configured value
   */
  public int getConfigured() {
    return configured;
  }


  /**
   *  Gets the nameFirst attribute of the RegistrationBean object
   *
   *@return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameLast attribute of the RegistrationBean object
   *
   *@return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the company attribute of the RegistrationBean object
   *
   *@return    The company value
   */
  public String getCompany() {
    return company;
  }


  /**
   *  Gets the email attribute of the RegistrationBean object
   *
   *@return    The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   *  Gets the ssl attribute of the RegistrationBean object
   *
   *@return    The ssl value
   */
  public boolean getSsl() {
    return ssl;
  }


  /**
   *  Gets the profile attribute of the RegistrationBean object
   *
   *@return    The profile value
   */
  public String getProfile() {
    return profile;
  }


  /**
   *  Gets the webserver attribute of the RegistrationBean object
   *
   *@return    The webserver value
   */
  public String getWebserver() {
    return webserver;
  }


  /**
   *  Gets the valid attribute of the RegistrationBean object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    errors.clear();
    if (profile == null || "".equals(profile.trim())) {
      errors.put("profileError", "Profile is a required field");
    }
    if (nameFirst == null || "".equals(nameFirst.trim())) {
      errors.put("nameFirstError", "First name is a required field");
    }
    if (nameLast == null || "".equals(nameLast.trim())) {
      errors.put("nameLastError", "Last name is a required field");
    }
    if (company == null || "".equals(company.trim())) {
      errors.put("companyError", "Company is a required field");
    }
    if (email == null || "".equals(email.trim())) {
      errors.put("emailError", "Email is a required field");
    }
    return (!hasErrors());
  }


  /**
   *  Description of the Method
   *
   *@return                                                     Description of
   *      the Return Value
   *@exception  javax.xml.parsers.ParserConfigurationException  Description of
   *      the Exception
   */
  public String toXmlString() throws javax.xml.parsers.ParserConfigurationException {
    //Build an XML document needed for request
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dbf.newDocumentBuilder();
    Document document = builder.newDocument();
    //Root element
    Element rootElement = document.createElement("crmRegistration");
    document.appendChild(rootElement);
    //First name
    Element nameFirstElement = document.createElement("nameFirst");
    nameFirstElement.appendChild(document.createTextNode(nameFirst));
    rootElement.appendChild(nameFirstElement);
    //Last name
    Element nameLastElement = document.createElement("nameLast");
    nameLastElement.appendChild(document.createTextNode(nameLast));
    rootElement.appendChild(nameLastElement);
    //Company name
    Element companyElement = document.createElement("company");
    companyElement.appendChild(document.createTextNode(company));
    rootElement.appendChild(companyElement);
    //Email address
    Element emailElement = document.createElement("email");
    emailElement.appendChild(document.createTextNode(email));
    rootElement.appendChild(emailElement);
    //Profile
    Element profileElement = document.createElement("profile");
    profileElement.appendChild(document.createTextNode(profile));
    rootElement.appendChild(profileElement);
    //OS
    String os = System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version");
    Element osElement = document.createElement("os");
    osElement.appendChild(document.createTextNode(os));
    rootElement.appendChild(osElement);
    //java
    String java = System.getProperty("java.version");
    Element javaElement = document.createElement("java");
    javaElement.appendChild(document.createTextNode(java));
    rootElement.appendChild(javaElement);
    //Web Server type
    Element webElement = document.createElement("webserver");
    webElement.appendChild(document.createTextNode(webserver));
    rootElement.appendChild(webElement);
    //Key
    if (zlib != null) {
      Element zlibElement = document.createElement("zlib");
      zlibElement.appendChild(document.createTextNode(zlib));
      rootElement.appendChild(zlibElement);
    }
    if (text != null) {
      Element textElement = document.createElement("text");
      textElement.appendChild(document.createTextNode(text));
      rootElement.appendChild(textElement);
    }
    return XMLUtils.toString(rootElement);
  }

}


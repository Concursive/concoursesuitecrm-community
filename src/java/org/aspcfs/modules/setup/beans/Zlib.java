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
package org.aspcfs.modules.setup.beans;

import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.StringUtils;
import java.io.*;
import java.security.*;
import com.sun.crypto.provider.*;
import sun.misc.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 *  Handles the information that appears in the license
 *
 *@author     matt rajkowski
 *@created    September 9, 2003
 *@version    $Id$
 */
public class Zlib {
  public final static String CRLF = System.getProperty("line.separator");

  private Key key = null;
  private String keyText = null;
  private String nameFirst = null;
  private String nameLast = null;
  private String company = null;
  private String email = null;
  private String edition = null;
  private String text = null;
  private String text2 = null;
  private String profile = null;
  private String mailError = null;
  private String os = null;
  private String java = null;
  private String webserver = null;


  /**
   *  Constructor for the Zlib object
   */
  public Zlib() { }


  /**
   *  Takes a license that has been serialized, decodes it and performs a test
   *
   *@param  xml  Description of the Parameter
   */
  public Zlib(XMLUtils xml) {
    try {
      //Process the properties
      keyText = XMLUtils.getNodeText(xml.getFirstChild("zlib"));
      nameFirst = XMLUtils.getNodeText(xml.getFirstChild("nameFirst"));
      nameLast = XMLUtils.getNodeText(xml.getFirstChild("nameLast"));
      company = XMLUtils.getNodeText(xml.getFirstChild("company"));
      email = XMLUtils.getNodeText(xml.getFirstChild("email"));
      profile = XMLUtils.getNodeText(xml.getFirstChild("profile"));
      os = XMLUtils.getNodeText(xml.getFirstChild("os"));
      java = XMLUtils.getNodeText(xml.getFirstChild("java"));
      webserver = XMLUtils.getNodeText(xml.getFirstChild("webserver"));
      text2 = XMLUtils.getNodeText(xml.getFirstChild("text2"));
      //Extract the key
      BASE64Decoder decoder = new BASE64Decoder();
      key = (Key) ObjectUtils.toObject(decoder.decodeBuffer(keyText));
      //Test the key
      text = PrivateString.decrypt(key, XMLUtils.getNodeText(xml.getFirstChild("text")));
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the key attribute of the Zlib object
   *
   *@param  tmp  The new key value
   */
  public void setKey(Key tmp) {
    this.key = tmp;
  }


  /**
   *  Sets the keyText attribute of the Zlib object
   *
   *@param  tmp  The new keyText value
   */
  public void setKeyText(String tmp) {
    this.keyText = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Zlib object
   *
   *@param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Zlib object
   *
   *@param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the company attribute of the Zlib object
   *
   *@param  tmp  The new company value
   */
  public void setCompany(String tmp) {
    this.company = tmp;
  }


  /**
   *  Sets the email attribute of the Zlib object
   *
   *@param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the edition attribute of the Zlib object
   *
   *@param  tmp  The new edition value
   */
  public void setEdition(String tmp) {
    this.edition = tmp;
  }


  /**
   *  Sets the text attribute of the Zlib object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   *  Sets the text2 attribute of the Zlib object
   *
   *@param  tmp  The new text2 value
   */
  public void setText2(String tmp) {
    this.text2 = tmp;
  }


  /**
   *  Sets the profile attribute of the Zlib object
   *
   *@param  tmp  The new profile value
   */
  public void setProfile(String tmp) {
    this.profile = tmp;
  }


  /**
   *  Sets the mailError attribute of the Zlib object
   *
   *@param  tmp  The new mailError value
   */
  public void setMailError(String tmp) {
    this.mailError = tmp;
  }


  /**
   *  Sets the os attribute of the Zlib object
   *
   *@param  tmp  The new os value
   */
  public void setOs(String tmp) {
    this.os = tmp;
  }


  /**
   *  Sets the java attribute of the Zlib object
   *
   *@param  tmp  The new java value
   */
  public void setJava(String tmp) {
    this.java = tmp;
  }


  /**
   *  Sets the webserver attribute of the Zlib object
   *
   *@param  tmp  The new webserver value
   */
  public void setWebserver(String tmp) {
    this.webserver = tmp;
  }


  /**
   *  Gets the key attribute of the Zlib object
   *
   *@return    The key value
   */
  public Key getKey() {
    return key;
  }


  /**
   *  Gets the keyText attribute of the Zlib object
   *
   *@return    The keyText value
   */
  public String getKeyText() {
    return keyText;
  }


  /**
   *  Gets the nameFirst attribute of the Zlib object
   *
   *@return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameLast attribute of the Zlib object
   *
   *@return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the company attribute of the Zlib object
   *
   *@return    The company value
   */
  public String getCompany() {
    return company;
  }


  /**
   *  Gets the email attribute of the Zlib object
   *
   *@return    The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   *  Gets the edition attribute of the Zlib object
   *
   *@return    The edition value
   */
  public String getEdition() {
    return edition;
  }


  /**
   *  Gets the text attribute of the Zlib object
   *
   *@return    The text value
   */
  public String getText() {
    return text;
  }


  /**
   *  Gets the text2 attribute of the Zlib object
   *
   *@return    The text2 value
   */
  public String getText2() {
    return text2;
  }


  /**
   *  Gets the profile attribute of the Zlib object
   *
   *@return    The profile value
   */
  public String getProfile() {
    return profile;
  }


  /**
   *  Gets the mailError attribute of the Zlib object
   *
   *@return    The mailError value
   */
  public String getMailError() {
    return mailError;
  }


  /**
   *  Gets the os attribute of the Zlib object
   *
   *@return    The os value
   */
  public String getOs() {
    return os;
  }


  /**
   *  Gets the java attribute of the Zlib object
   *
   *@return    The java value
   */
  public String getJava() {
    return java;
  }


  /**
   *  Gets the webserver attribute of the Zlib object
   *
   *@return    The webserver value
   */
  public String getWebserver() {
    return webserver;
  }


  /**
   *  Gets the valid attribute of the Zlib object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    try {
      if (text.startsWith("5USERBINARY") || text.startsWith("ENTERPRISE") || text.startsWith("UPGRADE")) {
        return true;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }


  /**
   *  Used for encrypting and sending the license back to the requesting user
   *
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public boolean sendEmailRegistration() throws Exception {
    String theLicense = "<license>" + getCode() + "</license>" + CRLF;
    SMTPMessage mail = new SMTPMessage();
    mail.setHost("127.0.0.1");
    mail.setType("text/plain");
    mail.addTo(email);
    mail.setFrom("Centric CRM Registration <registration@centriccrm.com>");
    mail.addReplyTo("registration@centriccrm.com");
    mail.setSubject("Centric CRM Registration");
    mail.setBody(
        "Thank you for registering Centric CRM." + CRLF +
        CRLF +
        "Paste the complete registation code from the attached file " +
        "(including the <license> tags) into the Centric CRM license validation field." + CRLF +
        CRLF +
        "Some mail programs may have a problem with the attachment, if so please report " +
        "the client mail application name and the mail server software name to Centric CRM." + CRLF +
        CRLF +
        "The Centric CRM Team"
        );
    mail.addByteArrayAttachment("license.txt", theLicense, "text/plain");
    if (mail.send() == 2) {
      mailError = mail.getErrorMsg();
      return false;
    }
    return true;
  }


  /**
   *  Gets the code attribute of the Zlib object
   *
   *@return                                                     The code value
   *@exception  javax.xml.parsers.ParserConfigurationException  Description of
   *      the Exception
   */
  public String getCode() throws javax.xml.parsers.ParserConfigurationException {
    //Build an XML document needed for license
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dbf.newDocumentBuilder();
    Document document = builder.newDocument();
    //Root element
    Element rootElement = document.createElement("crmLicense");
    document.appendChild(rootElement);
    //First name
    Element nameFirstElement = document.createElement("nameFirst");
    nameFirstElement.appendChild(document.createTextNode(StringUtils.toString(nameFirst)));
    rootElement.appendChild(nameFirstElement);
    //Last name
    Element nameLastElement = document.createElement("nameLast");
    nameLastElement.appendChild(document.createTextNode(StringUtils.toString(nameLast)));
    rootElement.appendChild(nameLastElement);
    //Company name
    Element companyElement = document.createElement("company");
    companyElement.appendChild(document.createTextNode(StringUtils.toString(company)));
    rootElement.appendChild(companyElement);
    //Email address
    Element emailElement = document.createElement("email");
    emailElement.appendChild(document.createTextNode(StringUtils.toString(email)));
    rootElement.appendChild(emailElement);
    //Profile
    Element profileElement = document.createElement("profile");
    profileElement.appendChild(document.createTextNode(StringUtils.toString(profile)));
    rootElement.appendChild(profileElement);
    //License
    Element licenseElement = document.createElement("license");
    licenseElement.appendChild(document.createTextNode(StringUtils.toString(text)));
    rootElement.appendChild(licenseElement);
    //License name
    Element editionElement = document.createElement("edition");
    editionElement.appendChild(document.createTextNode(StringUtils.toString(edition)));
    rootElement.appendChild(editionElement);
    //crc
    Element crcElement = document.createElement("text2");
    crcElement.appendChild(document.createTextNode(StringUtils.toString(text2)));
    rootElement.appendChild(crcElement);
    //Date and time approved
    Element dateTimeElement = document.createElement("entered");
    dateTimeElement.appendChild(document.createTextNode(String.valueOf(new java.util.Date())));
    rootElement.appendChild(dateTimeElement);
    //Encrypt it
    return PrivateString.encrypt(key, XMLUtils.toString(rootElement));
  }
}


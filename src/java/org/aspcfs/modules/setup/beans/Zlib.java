//Copyright 2001-2003 Dark Horse Ventures
package org.aspcfs.modules.setup.beans;

import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.SMTPMessage;
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

  private Key key = null;
  private String nameFirst = null;
  private String nameLast = null;
  private String company = null;
  private String email = null;
  private String text = null;
  private String profile = null;
  private String mailError = null;


  /**
   *  Takes a license that has been serialized, decodes it and performs a test
   *
   *@param  xml  Description of the Parameter
   */
  public Zlib(XMLUtils xml) {
    try {
      //Extract the key and the properties
      BASE64Decoder decoder = new BASE64Decoder();
      key = (Key) ObjectUtils.toObject(
          decoder.decodeBuffer(
          XMLUtils.getNodeText(xml.getFirstChild("zlib"))));
      nameFirst = XMLUtils.getNodeText(xml.getFirstChild("nameFirst"));
      nameLast = XMLUtils.getNodeText(xml.getFirstChild("nameLast"));
      company = XMLUtils.getNodeText(xml.getFirstChild("company"));
      email = XMLUtils.getNodeText(xml.getFirstChild("email"));
      profile = XMLUtils.getNodeText(xml.getFirstChild("profile"));
      //Test the key
      text = PrivateString.decrypt(key, XMLUtils.getNodeText(xml.getFirstChild("text")));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
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
   *  Gets the valid attribute of the Zlib object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    try {
      if ("5USERBINARY-1.0".equals(text)) {
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
    String theCode = getCode();
    SMTPMessage mail = new SMTPMessage();
    mail.setHost("127.0.0.1");
    mail.setType("text/html");
    mail.addTo(email);
    mail.setFrom("Dark Horse CRM Registration <info@darkhorseventures.com>");
    mail.addReplyTo("info@darkhorseventures.com");
    mail.setSubject("Dark Horse CRM Registration");
    mail.setBody(
        "Thanks for your interest in Dark Horse CRM.<br>" +
        "<br>" +
        "Paste the complete registation code that follows into the Dark Horse CRM validation field:<br>" +
        "<br>" +
        "&lt;license&gt;" + theCode + "&lt;/license&gt;");
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
  private String getCode() throws javax.xml.parsers.ParserConfigurationException {
    //Build an XML document needed for license
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dbf.newDocumentBuilder();
    Document document = builder.newDocument();
    //Root element
    Element rootElement = document.createElement("cfsLicense");
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
    //License
    Element licenseElement = document.createElement("license");
    licenseElement.appendChild(document.createTextNode(text));
    rootElement.appendChild(licenseElement);
    //Date and time approved
    Element dateTimeElement = document.createElement("entered");
    dateTimeElement.appendChild(document.createTextNode(String.valueOf(new java.util.Date())));
    rootElement.appendChild(dateTimeElement);
    //Encrypt it
    return PrivateString.encrypt(key, XMLUtils.toString(rootElement));
  }
}


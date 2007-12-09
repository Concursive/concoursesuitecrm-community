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
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Bean to encapsulate the Update action
 *
 * @author mrajkowski
 * @version $Id$
 * @created November 26, 2003
 */
public class UpdateBean extends GenericBean {

  private String zlib = null;
  private String zlib2 = null;
  private String email = null;
  private String profile = null;
  private String text = null;
  private String text2 = null;
  private boolean ssl = true;


  /**
   * Sets the zlib attribute of the UpdateBean object
   *
   * @param tmp The new zlib value
   */
  public void setZlib(String tmp) {
    this.zlib = tmp;
  }


  public void setZlib2(String zlib2) {
    this.zlib2 = zlib2;
  }

  /**
   * Sets the email attribute of the UpdateBean object
   *
   * @param tmp The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   * Sets the profile attribute of the UpdateBean object
   *
   * @param tmp The new profile value
   */
  public void setProfile(String tmp) {
    this.profile = tmp;
  }


  /**
   * Sets the text attribute of the UpdateBean object
   *
   * @param tmp The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   * Sets the text2 attribute of the UpdateBean object
   *
   * @param tmp The new text2 value
   */
  public void setText2(String tmp) {
    this.text2 = tmp;
  }


  /**
   * Sets the ssl attribute of the UpdateBean object
   *
   * @param tmp The new ssl value
   */
  public void setSsl(boolean tmp) {
    this.ssl = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of
   *         the Return Value
   * @throws javax.xml.parsers.ParserConfigurationException
   *          Description of
   *          the Exception
   */
  public String toXmlString() throws javax.xml.parsers.ParserConfigurationException {
    //Build an XML document needed for request
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dbf.newDocumentBuilder();
    Document document = builder.newDocument();
    //Root element
    Element rootElement = document.createElement("crmUpdate");
    document.appendChild(rootElement);
    //Email address
    Element emailElement = document.createElement("email");
    emailElement.appendChild(document.createTextNode(email));
    rootElement.appendChild(emailElement);
    //Profile
    Element profileElement = document.createElement("profile");
    profileElement.appendChild(document.createTextNode(profile));
    rootElement.appendChild(profileElement);
    //Text
    Element textElement = document.createElement("text");
    textElement.appendChild(document.createTextNode(text));
    rootElement.appendChild(textElement);
    //CRC
    Element text2Element = document.createElement("text2");
    text2Element.appendChild(document.createTextNode(text2));
    rootElement.appendChild(text2Element);
    //Key
    if (zlib != null) {
      Element zlibElement = document.createElement("zlib");
      zlibElement.appendChild(document.createTextNode(zlib));
      rootElement.appendChild(zlibElement);
    }
    if (zlib2 != null) {
      Element zlibElement = document.createElement("zlib2");
      zlibElement.appendChild(document.createTextNode(zlib2));
      rootElement.appendChild(zlibElement);
    }
    return XMLUtils.toString(rootElement);
  }

}


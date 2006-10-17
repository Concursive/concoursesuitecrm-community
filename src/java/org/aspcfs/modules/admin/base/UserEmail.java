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
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;

/**
 * Description of the Class
 *
 * @author partha
 * @created June 12, 2006
 */
public class UserEmail {
  private String subject = null;
  private String body = null;


  /**
   * Gets the subject attribute of the UserEmail object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Sets the subject attribute of the UserEmail object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Gets the body attribute of the UserEmail object
   *
   * @return The body value
   */
  public String getBody() {
    return body;
  }


  /**
   * Sets the body attribute of the UserEmail object
   *
   * @param tmp The new body value
   */
  public void setBody(String tmp) {
    this.body = tmp;
  }


  /**
   * Constructor for the UserEmail object
   *
   * @param context      Description of the Parameter
   * @param thisUser     Description of the Parameter
   * @param templateFile Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public UserEmail(ActionContext context, User thisUser, String name, String password, String url, String templateFile) throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(mappings, "map", "id", "newuser.alert.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(
                mappings, "map", "id", "newuser.alert.email.body")));
    messageBody.addParseElement("${user.username}", thisUser.getUsername());
    messageBody.addParseElement("${user.password}", password);
    if (name != null) {
      messageBody.addParseElement("${modUserName}", name);
    } else {
      messageBody.addParseElement("${modUserName}", "the administrator");
    }
    messageBody.addParseElement("${url}", url);
    messageBody.addParseElement("${endUrl}", "</a>");
    body = messageBody.getParsedText();
  }
}


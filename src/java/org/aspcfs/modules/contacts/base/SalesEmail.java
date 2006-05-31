/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.web.RequestUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;
import java.sql.*;

/**
 *  Generates a custom contact email contents for use with actions that send
 *  email
 *
 * @author     partha
 * @created    August 23, 2005
 * @version    $Id$
 */
public class SalesEmail {

  private String subject = null;
  private String body = null;


  /**
   *  Constructor for the SalesEmail object
   *
   * @param  templateFile   Description of the Parameter
   * @param  contact        Description of the Parameter
   * @param  context        Description of the Parameter
   * @param  type           Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public SalesEmail(String templateFile, Contact contact, ActionContext context, String type) throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", type + ".email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    messageSubject.addParseElement("${" + type + ".email.subject}", "Centric CRM: Lead Assigned");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(XMLUtils.getNodeText(XMLUtils.getElement(
        mappings, "map", "id", "" + type + ".email.body")));
    messageBody.addParseElement("${contact.name}", contact.getNameFull());
    if (contact.getCompany() != null && !"".equals(contact.getCompany().trim())) {
      messageBody.addParseElement("${contact.company}", "/ " + contact.getCompany());
    } else {
      messageBody.addParseElement("${contact.company}", "");
    }
    messageBody.addParseElement("${link}", RequestUtils.getLink(
        context, "Sales.do?command=Details&contactId=" + contact.getId() + "&from=dashboard"));
    body = messageBody.getParsedText();
  }


  /**
   *  Constructor for the SalesEmail object
   *
   * @param  templateFile   Description of the Parameter
   * @param  contact        Description of the Parameter
   * @param  context        Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public SalesEmail(String templateFile, Contact contact, ActionContext context) throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "leads.worked.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    messageSubject.addParseElement("${leads.worked.email.subject}", "Centric CRM: Prospect Assigned");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(XMLUtils.getNodeText(XMLUtils.getElement(
        mappings, "map", "id", "leads.worked.email.body")));
    if (contact.getNameLastFirst() == null || "".equals(contact.getNameLastFirst())) {
      messageBody.addParseElement("${contact.name}", contact.getNameFull());
      messageBody.addParseElement("${contact.company}", "");
    } else {
      messageBody.addParseElement("${contact.name}", contact.getNameFull());
      if (contact.getCompany() != null && !"".equals(contact.getCompany().trim())) {
        messageBody.addParseElement("${contact.company}", "/ " + contact.getCompany());
      } else {
        messageBody.addParseElement("${contact.company}", "");
      }
    }
    if (contact.getOrgId() == -1) {
      messageBody.addParseElement("${link}", RequestUtils.getLink(
          context, "ExternalContacts.do?command=Details&id=" + contact.getId()));
    } else {
      messageBody.addParseElement("${link}", RequestUtils.getLink(
          context, "Accounts.do?command=Details&orgId=" + contact.getOrgId()));
    }
    body = messageBody.getParsedText();
  }


  /**
   *  Gets the subject attribute of the SalesEmail object
   *
   * @return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the body attribute of the SalesEmail object
   *
   * @return    The body value
   */
  public String getBody() {
    return body;
  }
}


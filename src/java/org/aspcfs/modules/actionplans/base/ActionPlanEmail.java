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
package org.aspcfs.modules.actionplans.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.web.RequestUtils;
import org.w3c.dom.Element;

import java.io.File;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    September 8, 2005
 */
public class ActionPlanEmail {
  private String subject = null;
  private String body = null;


  /**
   *  Gets the subject attribute of the ActionPlanEmail object
   *
   * @return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Sets the subject attribute of the ActionPlanEmail object
   *
   * @param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Gets the body attribute of the ActionPlanEmail object
   *
   * @return    The body value
   */
  public String getBody() {
    return body;
  }


  /**
   *  Sets the body attribute of the ActionPlanEmail object
   *
   * @param  tmp  The new body value
   */
  public void setBody(String tmp) {
    this.body = tmp;
  }


  /**
   *  Constructor for the ActionPlanEmail object
   *
   * @param  templateFile   Description of the Parameter
   * @param  context        Description of the Parameter
   * @param  actionPlan     Description of the Parameter
   * @param  assigned       Description of the Parameter
   * @param  manager        Description of the Parameter
   * @param  orgName        Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public ActionPlanEmail(String templateFile, ActionPlanWork actionPlan, Contact assigned, Contact manager, String orgName, ActionContext context)
       throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionplan.assign.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionplan.assign.email.body")));
    messageBody.addParseElement("${actionplan.name}", actionPlan.getPlanName());
    messageBody.addParseElement("${actionplan.manager}", manager.getNameFull());
    messageBody.addParseElement("${actionplan.assignedTo}", assigned.getNameFull());
    messageBody.addParseElement("${organization.name}", orgName);
    messageBody.addParseElement("${link}", RequestUtils.getLink(
        context, "MyActionPlans.do?command=Details&actionPlanId=" + actionPlan.getId()));
    body = messageBody.getParsedText();
  }


  /**
   *  Constructor for the ActionPlanEmail object
   *
   * @param  templateFile   Description of the Parameter
   * @param  actionPlan     Description of the Parameter
   * @param  previous       Description of the Parameter
   * @param  assigned       Description of the Parameter
   * @param  manager        Description of the Parameter
   * @param  orgName        Description of the Parameter
   * @param  context        Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public ActionPlanEmail(String templateFile, ActionPlanWork actionPlan, Contact previous, Contact assigned, Contact manager, String orgName, ActionContext context)
       throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionplan.reassign.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionplan.reassign.email.body")));
    messageBody.addParseElement("${actionplan.name}", actionPlan.getPlanName());
    messageBody.addParseElement("${actionplan.manager}", manager.getNameFull());
    messageBody.addParseElement("${actionplan.assignedFrom}", previous.getNameFull());
    messageBody.addParseElement("${actionplan.assignedTo}", assigned.getNameFull());
    messageBody.addParseElement("${organization.name}", orgName);
    messageBody.addParseElement("${link}", RequestUtils.getLink(
        context, "MyActionPlans.do?command=Details&actionPlanId=" + actionPlan.getId()));
    body = messageBody.getParsedText();
  }


  /**
   *  Constructor for the ActionPlanEmail object
   *
   * @param  templateFile   Description of the Parameter
   * @param  actionPlan     Description of the Parameter
   * @param  next           Description of the Parameter
   * @param  stepName       Description of the Parameter
   * @param  context        Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public ActionPlanEmail(String templateFile, ActionPlanWork actionPlan, Contact next, String stepName, ActionContext context)
       throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionstep.alert.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionstep.alert.email.body")));
    if (actionPlan.getOrganization() != null) {
      messageBody.addParseElement("${organization.name}", actionPlan.getOrganization().getName());
    }
    messageBody.addParseElement("${actionstep.description}", stepName);
    messageBody.addParseElement("${actionstep.owner}", next.getNameFull());
    messageBody.addParseElement("${link}", RequestUtils.getLink(
        context, "MyActionPlans.do?command=Details&actionPlanId=" + actionPlan.getId()));
    body = messageBody.getParsedText();
  }

  public ActionPlanEmail(String templateFile, ActionPlanWork actionPlan, Contact next, String stepName, ActionContext context, String link)
       throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionstep.stepCompletion.alert.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
        XMLUtils.getElement(
        mappings, "map", "id", "actionstep.stepCompletion.alert.email.body")));
    if (actionPlan.getOrganization() != null) {
      messageBody.addParseElement("${organization.name}", actionPlan.getOrganization().getName());
    }
    messageBody.addParseElement("${actionstep.description}", stepName);
    messageBody.addParseElement("${actionstep.owner}", next.getNameFull());
    messageBody.addParseElement("${link}", RequestUtils.getLink(
        context, link + actionPlan.getId()));
    body = messageBody.getParsedText();
  }
}


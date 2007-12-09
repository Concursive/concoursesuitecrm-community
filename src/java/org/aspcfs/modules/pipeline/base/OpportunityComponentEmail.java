/*
 *  Copyright(c) 2005 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.pipeline.base;

import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.StringUtils;
import org.w3c.dom.Element;

import java.io.File;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Sep 14, 2005
 */

public class OpportunityComponentEmail {

  private String subject = null;
  private String body = null;

  private String relationshipType = null;
  private String relationshipName = null;
  private OpportunityHeader opportunity = null;
  private OpportunityComponent component = null;
  private String url = null;

  public OpportunityComponentEmail() throws Exception {

  }

  public void render(String templateFile) throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(
                mappings, "map", "id", "notifier.opportunity.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    messageSubject.addParseElement("${opportunity.relationshipName}", StringUtils.toHtml(relationshipName));
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(
                mappings, "map", "id", "notifier.opportunity.body")));
    messageBody.addParseElement("${opportunity.relationshipType}", StringUtils.toHtml(relationshipType));
    messageBody.addParseElement("${opportunity.relationshipName}", StringUtils.toHtml(relationshipName));
    messageBody.addParseElement("${opportunity.description}", StringUtils.toHtml(opportunity.getDescription()));
    messageBody.addParseElement("${component.description}", StringUtils.toHtml(component.getDescription()));
    messageBody.addParseElement("${component.alertText}", StringUtils.toHtml(component.getAlertText()));
    messageBody.addParseElement("${component.notes}", StringUtils.toHtml(component.getNotes()));
    messageBody.addParseElement("${link}", url);
    body = messageBody.getParsedText();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("OpportunityComponentEmail-> Subject: " + subject);
      System.out.println("OpportunityComponentEmail-> Body: " + body);
    }
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  public void setRelationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
  }

  public void setRelationshipName(String relationshipName) {
    this.relationshipName = relationshipName;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setOpportunity(OpportunityHeader opportunity) {
    this.opportunity = opportunity;
  }

  public void setComponent(OpportunityComponent component) {
    this.component = component;
  }
}


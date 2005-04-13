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
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;

/**
 * Generates news article email contents for use with actions that send email
 *
 * @author matt rajkowski
 * @version $Id$
 * @created February 16, 2005
 */
public class NewsArticleEmail {

  private String subject = null;
  private String body = null;

  public NewsArticleEmail(String templateFile, NewsArticle thisArticle, ActionContext context) throws Exception {
    // Load the templates
    File configFile = new File(templateFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");
    // Construct the subject
    Template messageSubject = new Template();
    messageSubject.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(
                mappings, "map", "id", "projects.news.email.subject")));
    messageSubject.addParseElement("\r\n", "");
    messageSubject.addParseElement("\r", "");
    messageSubject.addParseElement("\n", "");
    messageSubject.addParseElement(
        "${news.subject}", thisArticle.getSubject());
    subject = messageSubject.getParsedText();
    // Construct the body
    Template messageBody = new Template();
    messageBody.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(
                mappings, "map", "id", "projects.news.email.body")));
    messageBody.addParseElement("${news.intro}", thisArticle.getIntro());
    messageBody.addParseElement(
        "${link}", HTTPUtils.getLink(
            context, "ProjectManagementNews.do?command=Details&pid=" + thisArticle.getProjectId() + "&id=" + thisArticle.getId()));
    String param1 = messageBody.getValue("news.continued");
    if (thisArticle.hasMessage()) {
      messageBody.addParseElement("${news.continued=" + param1 + "}", param1);
    } else {
      messageBody.addParseElement("${news.continued=" + param1 + "}", "");
    }
    body = messageBody.getParsedText();
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }
}


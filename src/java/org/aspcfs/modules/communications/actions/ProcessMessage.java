/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.utils.*;

public final class ProcessMessage extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String code = context.getRequest().getParameter("code");
    String messageId = context.getRequest().getParameter("messageId");
    String contactId = context.getRequest().getParameter("contactId");

    try {
      AuthenticationItem auth = new AuthenticationItem();
      auth.setId(context.getRequest().getServerName());
      auth.setCode(code);
      db = auth.getConnection(context);

      Message thisMessage = new Message(db, messageId);
      if (contactId != null && !"".equals(contactId)) {
        Contact thisContact = new Contact(db, Integer.parseInt(contactId));
        Template template = new Template();
        template.setText(thisMessage.getMessageText());
        String value = template.getValue("surveyId");
        if (value != null) {
          template.addParseElement("${surveyId=" + value + "}", "- Survey not available by fax -");
        }
        //NOTE: The following items are the same as the Notifier.java items
        template.addParseElement("${name}", StringUtils.toHtml(thisContact.getNameFirstLast()));
        template.addParseElement("${firstname}", StringUtils.toHtml(thisContact.getNameFirst()));
        template.addParseElement("${lastname}", StringUtils.toHtml(thisContact.getNameLast()));
        template.addParseElement("${company}", StringUtils.toHtml(thisContact.getCompany()));
        template.addParseElement("${department}", StringUtils.toHtml(thisContact.getDepartmentName()));
        thisMessage.setMessageText(template.getParsedText());
      }
      context.getRequest().setAttribute("Message", thisMessage);

    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage != null) {
      return ("SystemError");
    } else {
      return ("PreviewOK");
    }
  }
}


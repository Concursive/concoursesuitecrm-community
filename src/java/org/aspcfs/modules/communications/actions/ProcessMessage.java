/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.base.AuthenticationItem;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public final class ProcessMessage extends CFSModule {

  /**
   *  This action is used for displaying a message to a logged in user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
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
      System.out.println(e.getMessage());
      return ("SystemError");
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    return ("PreviewOK");
  }
}


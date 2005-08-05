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
package org.aspcfs.modules.communications.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.communications.base.Message;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: ProcessMessage.java,v 1.8 2004/03/23 14:04:47 mrajkowski Exp
 *          $
 * @created January 15, 2003
 */
public final class ProcessMessage extends CFSModule {

  /**
   * This action is used for displaying a message to a logged in user
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
          template.addParseElement(
              "${surveyId=" + value + "}", "- Survey not available by fax -");
        }
        //NOTE: The following items are the same as the Notifier.java items
        template.addParseElement(
            "${name}", StringUtils.toHtml(thisContact.getNameFirstLast()));
        template.addParseElement(
            "${firstname}", StringUtils.toHtml(thisContact.getNameFirst()));
        template.addParseElement(
            "${lastname}", StringUtils.toHtml(thisContact.getNameLast()));
        template.addParseElement(
            "${company}", StringUtils.toHtml(thisContact.getCompany()));
        template.addParseElement(
            "${department}", StringUtils.toHtml(
                thisContact.getDepartmentName()));
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


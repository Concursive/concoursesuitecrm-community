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
import org.aspcfs.modules.communications.base.ScheduledRecipient;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 15, 2005
 */
public final class ProcessCampaignConfirmation extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Connection db = null;
    ScheduledRecipient recipient = null;
    String codedId = context.getRequest().getParameter("id");
    if (codedId != null && codedId.startsWith("${campaignId")) {
      return "InvalidRequestError";
    }
    try {
      // Get a database connection based on the request
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      // Load the survey key which decodes the url
      String dbName = auth.getConnectionElement(context).getDbName();
      String filename = getPath(context) + dbName + fs + "keys" + fs + "survey.key";
      String uncodedId = PrivateString.decrypt(filename, codedId);
      int contactId = -1;
      int campaignId = -1;
      StringTokenizer st = new StringTokenizer(uncodedId, ",");
      while (st.hasMoreTokens()) {
        String pair = (st.nextToken());
        StringTokenizer stPair = new StringTokenizer(pair, "=");
        String param = stPair.nextToken();
        String value = stPair.nextToken();
        if ("campaignId".equals(param)) {
          campaignId = Integer.parseInt(value);
        } else if ("cid".equals(param)) {
          contactId = Integer.parseInt(value);
        }
      }
      int recordcount = -1;
      recipient = new ScheduledRecipient(db, campaignId, contactId);
      if (recipient.getReplyDate() == null) {
        recipient.setReplyDate(
          DateUtils.roundUpToNextFive(System.currentTimeMillis()));
        recordcount = recipient.update(db);
      }
      if (recordcount > 0) {
        context.getRequest().setAttribute("status", "OK");
      } else {
        context.getRequest().setAttribute("status", "EXISTS");
      }

    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("NotFoundError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recipient != null) {
      context.getRequest().setAttribute("scheduledRecipient", recipient);
      return ("ConfirmationOK");
    } else {
      context.getRequest().setAttribute("Error", "No Survey Found.");
      return ("NotFoundError");
    }
  }
}


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
import org.aspcfs.modules.communications.base.ActiveSurvey;
import org.aspcfs.modules.communications.base.SurveyResponse;
import org.aspcfs.modules.communications.base.ScheduledRecipient;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.util.StringTokenizer;
import java.security.Key;

/**
 * Allows respondants to take part in a survey in which they were invited to
 *
 * @author chris price
 * @version $Id: ProcessSurvey.java,v 1.13 2004/03/23 14:04:47 mrajkowski Exp
 *          $
 * @created August 7, 2002
 */
public final class ProcessSurvey extends CFSModule {

  /**
   * Generates the survey for presentation, decodes URL for a non-user of this
   * system.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    ActiveSurvey thisSurvey = null;
    Connection db = null;
    String codedId = context.getRequest().getParameter("id");
    //return alert message if someone tried clicking link from the campaign dashboard's message tab
    if (codedId != null && codedId.startsWith("${surveyId")) {
      return "InvalidRequestError";
    }
    try {
      // Get a database connection based on the request
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      // Load the survey key which decodes the url
      String dbName = auth.getConnectionElement(context).getDbName();
      String filename = getPath(context) + dbName + fs + "keys" + fs + "survey2.key";
      Key key = PrivateString.loadEncodedKey(filename);
      String uncodedId = PrivateString.decrypt(key, codedId);
      int surveyId = -1;
      int contactId = -1;
      StringTokenizer st = new StringTokenizer(uncodedId, ",");
      while (st.hasMoreTokens()) {
        String pair = (st.nextToken());
        StringTokenizer stPair = new StringTokenizer(pair, "=");
        String param = stPair.nextToken();
        String value = stPair.nextToken();
        if ("id".equals(param)) {
          surveyId = Integer.parseInt(value);
        } else if ("cid".equals(param)) {
          contactId = Integer.parseInt(value);
        }
      }
      thisSurvey = new ActiveSurvey(db, surveyId);
      
      if (contactId != -1) {
        //mark the recipient to have received the survey
        ScheduledRecipient recipient = new ScheduledRecipient(db, thisSurvey.getCampaignId(), contactId);
        if (recipient.getReplyDate() == null) {
          recipient.setReplyDate(
            DateUtils.roundUpToNextFive(System.currentTimeMillis()));
          recipient.update(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("NotFoundError");
    } finally {
      this.freeConnection(context, db);
    }
    if (thisSurvey != null) {
      context.getRequest().setAttribute("ActiveSurvey", thisSurvey);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", "No Survey Found.");
      return ("NotFoundError");
    }
  }


  /**
   * Processes the user's answers and inserts them
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    Connection db = null;
    ActiveSurvey thisSurvey = null;
    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      // Load the survey key which decodes the url
      String dbName = auth.getConnectionElement(context).getDbName();
      String filename = getPath(context) + dbName + fs + "keys" + fs + "survey2.key";
      String codedId = context.getRequest().getParameter("id");
      Key key = PrivateString.loadEncodedKey(filename);
      String uncodedId = PrivateString.decrypt(key, codedId);
      int surveyId = -1;
      int contactId = -1;
      StringTokenizer st = new StringTokenizer(uncodedId, ",");
      while (st.hasMoreTokens()) {
        String pair = (st.nextToken());
        StringTokenizer stPair = new StringTokenizer(pair, "=");
        String param = stPair.nextToken();
        String value = stPair.nextToken();
        if ("id".equals(param)) {
          surveyId = Integer.parseInt(value);
        } else if ("cid".equals(param)) {
          contactId = Integer.parseInt(value);
        }
      }
      SurveyResponse thisResponse = new SurveyResponse(context);
      thisResponse.setActiveSurveyId(surveyId);
      thisResponse.setContactId(contactId);
      thisResponse.setUniqueCode(codedId);
      thisResponse.insert(db);
      thisSurvey = new ActiveSurvey(db, surveyId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ThankYouText", thisSurvey.getOutro());
    return ("InsertOK");
  }
}


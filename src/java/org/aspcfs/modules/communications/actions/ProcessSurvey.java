/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.ConnectionPool;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.utils.web.CustomForm;
import org.aspcfs.modules.login.base.AuthenticationItem;


/**
 *  Allows respondants to take part in a survey in which they were invited to
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id$
 */
public final class ProcessSurvey extends CFSModule {

  /**
   *  Generates the survey for presentation
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    ActiveSurvey thisSurvey = null;
    ConnectionPool sqlDriver = null;
    Connection db = null;

    try {
      //CustomForm thisForm = getDynamicForm(context, "surveyview");
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);

      String dbName = auth.getConnectionElement(context).getDbName();
      String filename = getPath(context) + dbName + fs + "keys" + fs + "survey.key";
      String codedId = context.getRequest().getParameter("id");
      String uncodedId = PrivateString.decrypt(filename, codedId);
      int surveyId = -1;
      
      //return alert message if someone tried clicking link from the campaign dashboard's message tab
      if(codedId != null && codedId.startsWith("${surveyId")){
          return "InvalidRequestError";
      }
      StringTokenizer st = new StringTokenizer(uncodedId, ",");
      while (st.hasMoreTokens()) {
        String pair = (st.nextToken());
        StringTokenizer stPair = new StringTokenizer(pair, "=");
        String param = stPair.nextToken();
        String value = stPair.nextToken();
        if ("id".equals(param)) {
          surveyId = Integer.parseInt(value);
        }
      }
      thisSurvey = new ActiveSurvey(db, surveyId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (thisSurvey != null) {
        context.getRequest().setAttribute("ActiveSurvey", thisSurvey);
        return ("ViewOK");
      } else {
        context.getRequest().setAttribute("Error", "No Survey Found.");
        return ("NotFoundError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("NotFoundError");
    }
  }


  /**
   *  Processes the user's answers and inserts them
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    ActiveSurvey thisSurvey = null;

    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);

      String dbName = auth.getConnectionElement(context).getDbName();
      String filename = getPath(context) + dbName + fs + "keys" + fs + "survey.key";
      String codedId = context.getRequest().getParameter("id");
      String uncodedId = PrivateString.decrypt(filename, codedId);

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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ThankYouText", thisSurvey.getOutro());
      return ("InsertOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}


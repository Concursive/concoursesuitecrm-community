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
import com.darkhorseventures.controller.CustomForm;
import com.darkhorseventures.utils.*;

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
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Survey thisSurvey = null;
    ConnectionPool sqlDriver = null;
    Connection db = null;

    CustomForm thisForm = getDynamicForm(context, "surveyview");
    String passedId = context.getRequest().getParameter("id");

    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      thisSurvey = new Survey(db, Integer.parseInt(passedId));
      thisForm.populate(thisSurvey);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (thisSurvey != null) {
        context.getRequest().setAttribute("Survey", thisSurvey);
        context.getRequest().setAttribute("CustomFormInfo", thisForm);
        return ("ViewOK");
      } else {
        context.getRequest().setAttribute("Error", "No Survey Found.");
        return ("SystemError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Processes the user's answers and inserts them
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    ConnectionPool sqlDriver = null;
    Connection db = null;

    Survey thisSurvey = new Survey();
    thisSurvey.setAnswerItems(context.getRequest());

    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);

      //Insert the answers
      Iterator ans = thisSurvey.getAnswers().iterator();
      while (ans.hasNext()) {
        SurveyAnswer thisAnswer = (SurveyAnswer) ans.next();
        thisAnswer.insert(db, -1, Integer.parseInt(context.getRequest().getParameter("id")));
      }
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("InsertOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}


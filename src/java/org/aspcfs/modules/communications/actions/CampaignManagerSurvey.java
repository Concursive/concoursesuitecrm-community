package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.controller.CustomFormList;
import com.darkhorseventures.controller.CustomForm;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id$
 */
public final class CampaignManagerSurvey extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandView(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    PagedListInfo surveyInfo = this.getPagedListInfo(context, "CampaignSurveyListInfo");
    surveyInfo.setLink("/CampaignManagerSurvey.do?command=View");
    Connection db = null;
    SurveyList surveyList = new SurveyList();

    try {
      db = this.getConnection(context);
      surveyList.setPagedListInfo(surveyInfo);
      if ("all".equals(surveyInfo.getListView())) {
        surveyList.setEnteredByIdRange(this.getUserRange(context));
      } else {
        surveyList.setEnteredByIdRange(this.getUserId(context) + "");
      }
      surveyList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "View Surveys");

    if (errorMessage == null) {
      context.getRequest().setAttribute("SurveyList", surveyList);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add"))) {
      return ("PermissionError");
    }

    int pg = 0;
    int updateResult = 0;

    if (context.getRequest().getParameter("pg") != null) {
      pg = Integer.parseInt(context.getRequest().getParameter("pg"));
    }

    CustomForm thisForm = getDynamicForm(context, "survey");
    thisForm.setSelectedTabId(pg);

    Survey thisSurvey = (Survey) context.getFormBean();

    thisSurvey.setRequestItems(context.getRequest());
    updateResult = thisForm.populate(thisSurvey);

    String submenu = context.getRequest().getParameter("submenu");

    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }

    context.getRequest().setAttribute("submenu", submenu);
    context.getRequest().setAttribute("Survey", thisSurvey);
    context.getRequest().setAttribute("CustomFormInfo", thisForm);
    addModuleBean(context, submenu, "Add Survey");

    return ("AddOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandDetails(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    Survey thisSurvey = null;
    Connection db = null;

    CustomForm thisForm = getDynamicForm(context, "surveydetails");

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      thisForm.populate(thisSurvey);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    String submenu = context.getRequest().getParameter("submenu");

    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("submenu", submenu);
      addModuleBean(context, submenu, "Add Surveys");

      if (thisSurvey != null) {
        context.getRequest().setAttribute("Survey", thisSurvey);
        context.getRequest().setAttribute("CustomFormInfo", thisForm);
        return ("DetailsOK");
      } else {
        processErrors(context, thisSurvey.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandDelete(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "campaign-campaigns-surveys-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Survey thisSurvey = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      recordDeleted = thisSurvey.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

/*     String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }
 */
    if (errorMessage == null) {
      //context.getRequest().setAttribute("submenu", submenu);
      //addModuleBean(context, submenu, "View Surveys");

      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        processErrors(context, thisSurvey.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandModify(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    Survey thisSurvey = null;
    Connection db = null;

    CustomForm thisForm = getDynamicForm(context, "survey");

    try {
      db = this.getConnection(context);

      if (context.getRequest().getParameter("id") != null) {
        thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      }

      thisForm.populate(thisSurvey);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    String submenu = context.getRequest().getParameter("submenu");

    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("submenu", submenu);
      addModuleBean(context, submenu, "Add Surveys");

      if (thisSurvey != null) {
        context.getRequest().setAttribute("Survey", thisSurvey);
        context.getRequest().setAttribute("CustomFormInfo", thisForm);
        return ("ModifyOK");
      } else {
        processErrors(context, thisSurvey.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandPreview(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Survey thisSurvey = null;
    Connection db = null;

    CustomForm thisForm = getDynamicForm(context, "surveypreview");
    thisSurvey = (Survey) context.getFormBean();
    thisSurvey.setRequestItems(context.getRequest());

    try {
      db = this.getConnection(context);
      thisForm.populate(thisSurvey);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    String submenu = context.getRequest().getParameter("submenu");

    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("submenu", submenu);
      addModuleBean(context, submenu, "Add Surveys");

      if (thisSurvey != null) {
        context.getRequest().setAttribute("Survey", thisSurvey);
        context.getRequest().setAttribute("CustomFormInfo", thisForm);
        return ("PreviewOK");
      } else {
        processErrors(context, thisSurvey.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    int recordsModified = 0;

    Survey newSurvey = (Survey) context.getFormBean();
    int surveyId = -1;

    if (context.getRequest().getParameter("id") != null && !(context.getRequest().getParameter("id").equals(""))) {
      surveyId = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    if (surveyId == -1) {
      newSurvey.setEnteredBy(getUserId(context));
    }

    newSurvey.setModifiedBy(getUserId(context));
    newSurvey.setRequestItems(context.getRequest());
    Connection db = null;

    System.out.println("Survey ID is: " + surveyId);

    try {
      db = this.getConnection(context);

      if (surveyId == -1) {
        recordInserted = newSurvey.insert(db);
      } else {
        recordsModified = newSurvey.update(db);
      }

      System.out.println("records modified: " + recordsModified);

      if (recordInserted || recordsModified > 0) {
        //newSurvey = new Survey(db, "" + newSurvey.getId());
        //newSurvey = new Survey();
        context.getRequest().setAttribute("SurveyDetails", newSurvey);
      } else {
        processErrors(context, newSurvey.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted || recordsModified > 0) {
        return ("InsertOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd2(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-surveys-add"))) {
      return ("PermissionError");
    }

    Survey thisSurvey = (Survey) context.getFormBean();

    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }
    context.getRequest().setAttribute("submenu", submenu);
    context.getRequest().setAttribute("Survey", thisSurvey);
    addModuleBean(context, submenu, "Add Survey");
    return ("Add2OK");
  }

}


package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.web.CustomFormList;
import org.aspcfs.utils.web.CustomForm;
import java.sql.*;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id: CampaignManagerSurvey.java,v 1.8 2002/09/30 14:34:08 chris
 *      Exp $
 */
public final class CampaignManagerSurvey extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    PagedListInfo surveyInfo = this.getPagedListInfo(context, "CampaignSurveyListInfo");
    surveyInfo.setLink("CampaignManagerSurvey.do?command=View");
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
    if (!(hasPermission(context, "campaign-campaigns-surveys-add") || hasPermission(context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }
    try {
      int pg = 0;
      int updateResult = 0;
      Exception errorMessage = null;
      Connection db = null;
      if (context.getRequest().getParameter("pg") != null) {
        pg = Integer.parseInt(context.getRequest().getParameter("pg"));
      }

      try {
        db = this.getConnection(context);
        CustomForm thisForm = getDynamicForm(context, "survey");
        thisForm.setSelectedTabId(pg);
        Survey thisSurvey = (Survey) context.getFormBean();
        thisSurvey.setRequestItems(context.getRequest());
        thisForm.setContext(context);
        updateResult = thisForm.populate(db, thisSurvey);
        context.getRequest().setAttribute("Survey", thisSurvey);
        context.getRequest().setAttribute("CustomFormInfo", thisForm);
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
      addModuleBean(context, submenu, "Add Survey");

      return ("AddOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Items for a SurveyQuestion given the questionId
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewItems(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    int questionId = -1;
    if (context.getRequest().getParameter("questionid") != null) {
      if (!context.getRequest().getParameter("questionid").equals("")) {
        questionId = Integer.parseInt(context.getRequest().getParameter("questionid"));
      }
    }

    try {
      db = this.getConnection(context);
      ItemList itemList = new ItemList();
      itemList.setQuestionId(questionId);
      itemList.buildList(db);
      context.getRequest().setAttribute("ItemList", itemList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ViewItemsOK";
    }
    return "SystemError";
  }


  /**
   *  Modify the Survey depending on the page "pg" given QuestionId is needed
   *  for modifying a question
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }

    int pg = 0;
    int questionId = -1;
    Exception errorMessage = null;
    Survey thisSurvey = null;
    Connection db = null;
    CustomForm thisForm = getDynamicForm(context, "survey");
    if (context.getRequest().getParameter("pg") != null) {
      pg = Integer.parseInt(context.getRequest().getParameter("pg"));
    }
    if (context.getRequest().getParameter("questionid") != null) {
      questionId = Integer.parseInt(context.getRequest().getParameter("questionid"));
    }

    try {
      db = this.getConnection(context);
      if (context.getRequest().getParameter("id") != null) {
        if (Integer.parseInt(context.getRequest().getParameter("id")) != -1) {
          thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
        }
      }
      thisSurvey.getQuestions().setQuestionId(questionId);
      thisForm.setSelectedTabId(pg);
      thisForm.setContext(context);
      thisForm.populate(db, thisSurvey);
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

      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("submenu", submenu);
      addModuleBean(context, submenu, "Add Surveys");

      if (thisSurvey != null) {
        if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
          return ("PermissionError");
        }
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Survey thisSurvey = null;

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      context.getRequest().setAttribute("Survey", thisSurvey);
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

    if (errorMessage == null) {

      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }

      addModuleBean(context, submenu, "Add Surveys");
      return ("DetailsOK");
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
  public String executeCommandViewReturn(ActionContext context) {
    String returnType = context.getRequest().getParameter("return");
    if (!"list".equals(returnType)) {
      String id = context.getRequest().getParameter("id");
      if (id != null && !"-1".equals(id)) {
        return executeCommandDetails(context);
      }
    }
    return executeCommandView(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
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
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }
      recordDeleted = thisSurvey.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "CampaignManagerSurvey.do?command=View");
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
   *  Deletes a SurveyQuestion given the surveyId & questionId
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteQuestion(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Survey thisSurvey = null;
    Connection db = null;
    int surveyId = Integer.parseInt(context.getRequest().getParameter("id"));
    int questionId = Integer.parseInt(context.getRequest().getParameter("questionid"));

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, surveyId);
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }
      SurveyQuestionList thisList = thisSurvey.getQuestions();
      recordDeleted = thisList.getQuestion(questionId).delete(db, surveyId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        return executeCommandModify(context);
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
   *  Preview of the Survey
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPreview(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Survey thisSurvey = null;
    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      context.getRequest().setAttribute("Survey", thisSurvey);
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
      return ("PreviewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Insert or update a Survey
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add") || hasPermission(context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    int recordsModified = 0;
    Connection db = null;
    try {
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
      db = this.getConnection(context);
      if (surveyId == -1) {
        recordInserted = newSurvey.insert(db);
      } else {
        recordsModified = newSurvey.update(db);
      }
      if (recordInserted || recordsModified > 0) {
        context.getRequest().setAttribute("SurveyDetails", newSurvey);
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CampaignManagerSurvey -- > Insert Errors " + newSurvey.getErrors());
        }
        processErrors(context, newSurvey.getErrors());
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted || recordsModified > 0) {
      return ("InsertOK");
    } else if (recordsModified == 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    } else {
      return (executeCommandAdd(context));
    }
  }



  /**
   *  Save and Add a SurveyQuestion ... could be used for Save and Add any page
   *  of Survey
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertAndAdd(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add") || hasPermission(context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }

    int pg = 0;
    Exception errorMessage = null;
    Survey thisSurvey = null;
    Connection db = null;
    //Insert Survey
    executeCommandInsert(context);

    //create CustomForm for adding
    CustomForm thisForm = getDynamicForm(context, "survey");
    if (context.getRequest().getParameter("pg") != null) {
      pg = Integer.parseInt(context.getRequest().getParameter("pg"));
    }

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, ((Survey) context.getRequest().getAttribute("SurveyDetails")).getId());
      thisForm.setSelectedTabId(pg);
      thisForm.setContext(context);
      thisForm.populate(db, thisSurvey);
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
        return ("InsertAndAddOK");
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
   *  Show Dependencies if any before deleting Survey
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Survey thisSurvey = null;

    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;

    if (!(hasPermission(context, "campaign-campaigns-surveys-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, id);
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }
      htmlDialog.setTitle("CFS: Campaign Manager");

      DependencyList dependencies = thisSurvey.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());

      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='CampaignManagerSurvey.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setHeader("This survey cannot be deleted because at least one campaign is using it.");
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
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
  public String executeCommandViewInteractiveOptions(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    } else {
      return ("InteractiveOptionsOK");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMoveQuestion(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Survey thisSurvey = null;
    int pg = 0;
    String direction = context.getRequest().getParameter("direction");
    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }
      thisSurvey.getQuestions().updateOrder(db, Integer.parseInt(context.getRequest().getParameter("questionid")), direction);
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
      return executeCommandModify(context);
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
  public String executeCommandMockInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Survey thisSurvey = null;

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(db, Integer.parseInt(context.getRequest().getParameter("id")));
      context.getRequest().setAttribute("ThankYouText", thisSurvey.getOutro());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("MockInsertOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}



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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.ItemList;
import org.aspcfs.modules.communications.base.Survey;
import org.aspcfs.modules.communications.base.SurveyList;
import org.aspcfs.modules.communications.base.SurveyQuestionList;
import org.aspcfs.modules.communications.base.SurveyQuestion;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.CustomForm;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.*;

/**
 * Description of the Class
 *
 * @author chris price
 * @version $Id: CampaignManagerSurvey.java,v 1.8 2002/09/30 14:34:08 chris
 *          Exp $
 * @created August 7, 2002
 */
public final class CampaignManagerSurvey extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    PagedListInfo surveyInfo = this.getPagedListInfo(
        context, "CampaignSurveyListInfo");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add") || hasPermission(
        context, "campaign-campaigns-surveys-edit"))) {
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
        context.getRequest().setAttribute(
            "systemStatus", this.getSystemStatus(context));
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
   * Items for a SurveyQuestion given the questionId
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
        questionId = Integer.parseInt(
            context.getRequest().getParameter("questionid"));
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
   * Modify the Survey depending on the page "pg" given QuestionId is needed
   * for modifying a question
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      questionId = Integer.parseInt(
          context.getRequest().getParameter("questionid"));
    }

    try {
      db = this.getConnection(context);
      if (context.getRequest().getParameter("id") != null) {
        if (Integer.parseInt(context.getRequest().getParameter("id")) != -1) {
          thisSurvey = new Survey(
              db, Integer.parseInt(context.getRequest().getParameter("id")));
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
        context.getRequest().setAttribute(
            "systemStatus", this.getSystemStatus(context));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      thisSurvey = new Survey(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Survey thisSurvey = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }
      recordDeleted = thisSurvey.delete(db);
      if (!recordDeleted) {
        HashMap map = new HashMap();
        map.put(
            "${thisSurvey.inactiveCount}", "" + thisSurvey.getInactiveCount());
        map.put(
            "${thisSurvey.campaign}", (thisSurvey.getInactiveCount() == 1 ? "campaign is" : "campaigns are"));
        map.put(
            "${thisSurvey.use}", (thisSurvey.getInactiveCount() == 1 ? "uses" : "use"));
        Template template = new Template(
            systemStatus.getLabel(
                "object.validation.actionError.canNotDeleteSurvey"));
        template.setParseElements(map);
        thisSurvey.getErrors().put("actionError", template.getParsedText());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute(
            "refreshUrl", "CampaignManagerSurvey.do?command=View");
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
   * Deletes a SurveyQuestion given the surveyId & questionId
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
    int questionId = Integer.parseInt(
        context.getRequest().getParameter("questionid"));

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
   * Preview of the Survey
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      thisSurvey = new Survey(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
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
   * Insert or update a Survey
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add") || hasPermission(
        context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    int recordsModified = -1;
    boolean isValid = false;
    Connection db = null;
    try {
      Survey newSurvey = (Survey) context.getFormBean();
      int surveyId = -1;
      if (context.getRequest().getParameter("id") != null && !(context.getRequest().getParameter(
          "id").equals(""))) {
        surveyId = Integer.parseInt(context.getRequest().getParameter("id"));
      }
      if (surveyId == -1) {
        newSurvey.setEnteredBy(getUserId(context));
      }
      newSurvey.setModifiedBy(getUserId(context));
      newSurvey.setRequestItems(context.getRequest());
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, newSurvey);
      Iterator iter = (Iterator) newSurvey.getQuestions().iterator();
      while (iter.hasNext()) {
        SurveyQuestion question = (SurveyQuestion) iter.next();
        isValid = this.validateObject(context, db, question) && isValid;
      }
      if (isValid) {
        if (surveyId == -1) {
          recordInserted = newSurvey.insert(db);
        } else {
          recordsModified = newSurvey.update(db);
        }
      }
      if ((recordInserted || recordsModified > 0) && isValid) {
        context.getRequest().setAttribute("SurveyDetails", newSurvey);
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "CampaignManagerSurvey-> Insert Errors " + newSurvey.getErrors());
        }
        processErrors(context, newSurvey.getErrors());
      }
      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
    } catch (Exception errorMessage) {
      errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ((recordInserted || recordsModified > 0) && isValid) {
      return ("InsertOK");
    } else if (recordsModified == 0 && isValid) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   * Save and Add a SurveyQuestion ... could be used for Save and Add any page
   * of Survey
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertAndAdd(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-add") || hasPermission(
        context, "campaign-campaigns-surveys-edit"))) {
      return ("PermissionError");
    }

    int pg = 0;
    Exception errorMessage = null;
    Survey thisSurvey = null;
    Connection db = null;
    //Insert Survey
    context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
    String result = executeCommandInsert(context);
    if (result.equals("AddOK")) {
      if (context.getRequest().getParameter("pg") != null) {
        if ("1".equals((String) context.getRequest().getParameter("pg"))) {
          context.getRequest().setAttribute("pg", "0");
        }
      }
      return "AddOK";
    }

    //create CustomForm for adding
    CustomForm thisForm = getDynamicForm(context, "survey");
    if (context.getRequest().getParameter("pg") != null) {
      pg = Integer.parseInt(context.getRequest().getParameter("pg"));
    }

    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(
          db, ((Survey) context.getRequest().getAttribute("SurveyDetails")).getId());
      thisForm.setSelectedTabId(pg);
      thisForm.setContext(context);
      thisForm.populate(db, thisSurvey);
      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
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
   * Show Dependencies if any before deleting Survey
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisSurvey = new Survey(db, id);
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }

      DependencyList dependencies = thisSurvey.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));

      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='CampaignManagerSurvey.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.surveyCampaignHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewInteractiveOptions(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    } else {
      return ("InteractiveOptionsOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      thisSurvey = new Survey(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      if (!hasAuthority(context, thisSurvey.getEnteredBy())) {
        return ("PermissionError");
      }
      thisSurvey.getQuestions().updateOrder(
          db, Integer.parseInt(
              context.getRequest().getParameter("questionid")), direction);
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMockInsert(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Survey thisSurvey = null;
    try {
      db = this.getConnection(context);
      thisSurvey = new Survey(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      context.getRequest().setAttribute("ThankYouText", thisSurvey.getOutro());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("MockInsertOK");
  }
}


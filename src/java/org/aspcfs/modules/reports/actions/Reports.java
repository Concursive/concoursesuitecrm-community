package org.aspcfs.modules.reports.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.admin.base.PermissionCategoryList;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.reports.base.*;
import dori.jasper.engine.*;
import dori.jasper.engine.util.*;
import java.io.*;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.LookupList;

/**
 *  Actions for working with Pipeline Management reports. Code originally from
 *  Leads.java.
 *
 *@author     matt rajkowski
 *@created    September 12, 2003
 *@version    $Id$
 */
public final class Reports extends CFSModule {

  /**
   *  Action to forward to another action
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandViewQueue(context);
  }


  /**
   *  Action for showing the report queue
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewQueue(ActionContext context) {
    addModuleBean(context, "reports.queue", "View Queue");
    Connection db = null;
    try {
      db = getConnection(context);
      //Build completed list
      ReportQueueList completed = new ReportQueueList();
      completed.setEnteredBy(getUserId(context));
      completed.setProcessedOnly(true);
      completed.setBuildResources(true);
      completed.buildList(db);
      context.getRequest().setAttribute("completedQueue", completed);
      //Build pending list
      ReportQueueList pending = new ReportQueueList();
      pending.setEnteredBy(getUserId(context));
      pending.setInQueueOnly(true);
      pending.setSortAscending(true);
      pending.setBuildResources(true);
      pending.buildList(db);
      context.getRequest().setAttribute("pendingQueue", pending);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "ReportQueueOK";
  }


  /**
   *  Action to show the available module report categories
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRunReport(ActionContext context) {
    addModuleBean(context, "reports.run", "Run Report");
    Connection db = null;
    try {
      db = getConnection(context);
      //Get list of modules with reports
      PermissionCategoryList categories = new PermissionCategoryList();
      categories.setModulesWithReportsOnly(true);
      categories.buildList(db);
      context.getRequest().setAttribute("categories", categories);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "RunReportOK";
  }


  /**
   *  Action to show the list of reports for the specified module category
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListReports(ActionContext context) {
    addModuleBean(context, "reports.run", "Run Report");
    Connection db = null;
    //Process parameters
    String categoryId = context.getRequest().getParameter("categoryId");
    try {
      db = getConnection(context);
      //Load the selected category
      PermissionCategory thisCategory = new PermissionCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("category", thisCategory);
      //Load the report list
      ReportList reports = new ReportList();
      reports.setCategoryId(Integer.parseInt(categoryId));
      reports.buildList(db);
      context.getRequest().setAttribute("reports", reports);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "ListReportsOK";
  }


  /**
   *  Action to show the user's previously saved criteria for the specified
   *  report
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCriteriaList(ActionContext context) {
    addModuleBean(context, "reports.run", "Run Report");
    Connection db = null;
    //Process parameters
    String categoryId = context.getRequest().getParameter("categoryId");
    String reportId = context.getRequest().getParameter("reportId");
    try {
      db = getConnection(context);
      //Load the category
      PermissionCategory thisCategory = new PermissionCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("category", thisCategory);
      //Load the specific report
      Report report = new Report(db, Integer.parseInt(reportId));
      context.getRequest().setAttribute("report", report);
      //Load the user's saved criteria list for this report
      CriteriaList criteriaList = new CriteriaList();
      criteriaList.setReportId(report.getId());
      criteriaList.setOwner(getUserId(context));
      criteriaList.buildList(db);
      context.getRequest().setAttribute("criteriaList", criteriaList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "CriteriaListOK";
  }


  /**
   *  Action to delete the user's previously saved criteria
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteCriteria(ActionContext context) {
    Connection db = null;
    //Process parameters
    String criteriaId = context.getRequest().getParameter("criteriaId");
    String reportId = context.getRequest().getParameter("reportId");
    String categoryId = context.getRequest().getParameter("categoryId");
    try {
      db = getConnection(context);
      CriteriaList.delete(db, Integer.parseInt(criteriaId));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "DeleteCriteriaOK";
  }


  /**
   *  Action to show the available parameters for the specified report. Merges
   *  previously saved criteria if specified by the user.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandParameterList(ActionContext context) {
    addModuleBean(context, "reports.run", "Run Report");
    Connection db = null;
    //Process parameters
    String categoryId = context.getRequest().getParameter("categoryId");
    String reportId = context.getRequest().getParameter("reportId");
    String criteriaId = context.getRequest().getParameter("criteriaId");
    try {
      db = getConnection(context);
      //Load the category
      PermissionCategory thisCategory = new PermissionCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("category", thisCategory);
      //Load the specific report
      Report report = new Report(db, Integer.parseInt(reportId));
      context.getRequest().setAttribute("report", report);
      //Read in the Jasper Report and return the parameters to the user
      String reportPath = getPath(context) + "reports" + fs;
      JasperReport jasperReport = JasperReportUtils.getReport(reportPath + report.getFilename());
      //Generate the allowable parameter list
      ParameterList params = new ParameterList();
      params.setParameters(jasperReport);
      //Load the criteria if the user selected to base on existing criteria
      if (criteriaId != null && !criteriaId.equals("-1")) {
        Criteria criteria = new Criteria(db, Integer.parseInt(criteriaId));
        criteria.buildResources(db);
        params.setParameters(criteria);
        context.getRequest().setAttribute("criteria", criteria);
      }
      //Auto-populate some defaults to show the user
      Iterator i = params.iterator();
      while (i.hasNext()) {
        Parameter param = (Parameter) i.next();
        if (param.getIsForPrompting()) {
          //All of the configurability appears in the Parameter class
          param.prepareContext(context.getRequest(), db);
        }
      }
      context.getRequest().setAttribute("parameterList", params);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "ParameterListOK";
  }


  /**
   *  Adds the specified report and criteria to the report queue to be processed
   *  by the ReportRunner.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandGenerateReport(ActionContext context) {
    addModuleBean(context, "reports.run", "Run Report");
    Connection db = null;
    //Process parameters
    String categoryId = context.getRequest().getParameter("categoryId");
    String reportId = context.getRequest().getParameter("reportId");
    //String criteriaId = context.getRequest().getParameter("criteriaId");
    try {
      db = getConnection(context);
      //Load the category
      PermissionCategory thisCategory = new PermissionCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("category", thisCategory);
      //Load the specific report
      Report report = new Report(db, Integer.parseInt(reportId));
      context.getRequest().setAttribute("report", report);
      //Read in the Jasper Report and set the parameters
      String reportPath = getPath(context) + "reports" + fs;
      JasperReport jasperReport = JasperReportUtils.getReport(reportPath + report.getFilename());
      //Determine the parameters that need to be saved from the jasper report
      ParameterList params = new ParameterList();
      params.setParameters(jasperReport);
      //Set the user supplied parameters from the request
      params.setParameters(context.getRequest());
      //Set the system generated parameters
      //TODO: Move this into ParameterList.java
      params.addParam("user_name", (getUser(context, getUserId(context))).getContact().getNameFirstLast());
      //Populate a criteria record which will be used in the report
      Criteria thisCriteria = new Criteria();
      thisCriteria.setReportId(report.getId());
      thisCriteria.setOwner(getUserId(context));
      thisCriteria.setEnteredBy(getUserId(context));
      thisCriteria.setModifiedBy(getUserId(context));
      thisCriteria.setSubject(context.getRequest().getParameter("criteria_subject"));
      thisCriteria.setParameters(params);
      //Determine if criteria should be saved
      thisCriteria.setSave(context.getRequest().getParameter("save"));
      String saveType = context.getRequest().getParameter("saveType");
      if ("overwrite".equals(saveType)) {
        thisCriteria.setId(Integer.parseInt(context.getRequest().getParameter("criteriaId")));
        thisCriteria.setOverwrite(true);
      } else if ("save".equals(saveType)) {
        thisCriteria.setSave(true);
      }
      //Save the user's criteria for future use
      boolean result = thisCriteria.save(db);
      //Insert the report into the queue
      int position = ReportQueue.insert(db, thisCriteria);
      context.getRequest().setAttribute("queuePosition", String.valueOf(position));
      //JasperRunManager.runReportToPdfFile(reportPath + report.getFilename() + ".jasper", reportPath + report.getFilename() + ".pdf", parameters, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "GenerateReportOK";
  }


  /**
   *  Action to stream the specified report PDF to the user.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandStreamReport(ActionContext context) {
    Connection db = null;
    //Process parameters
    String id = context.getRequest().getParameter("id");
    ReportQueue queue = null;
    try {
      db = getConnection(context);
      //Load the queued report
      queue = new ReportQueue(db, Integer.parseInt(id));
      queue.buildReport(db);
    } catch (Exception e) {
    } finally {
      freeConnection(context, db);
    }
    //Stream the report
    if (queue != null) {
      try {
        FileDownload download = new FileDownload();
        download.setFullPath(this.getPath(context, "reports-queue") + this.getDatePath(queue.getEntered()) + queue.getFilename());
        download.setDisplayName(queue.getReport().getFilename() + ".pdf");
        download.streamContent(context);
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return "-none-";
  }


  /**
   *  Action to download the specified report PDF to the user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDownloadReport(ActionContext context) {
    Connection db = null;
    //Process parameters
    String id = context.getRequest().getParameter("id");
    ReportQueue queue = null;
    try {
      db = getConnection(context);
      //Load the queued report
      queue = new ReportQueue(db, Integer.parseInt(id));
      queue.buildReport(db);
    } catch (Exception e) {
    } finally {
      freeConnection(context, db);
    }
    //Send the report
    if (queue != null) {
      try {
        FileDownload download = new FileDownload();
        download.setFullPath(this.getPath(context, "reports-queue") + this.getDatePath(queue.getEntered()) + queue.getFilename());
        download.setDisplayName(queue.getReport().getFilename() + ".pdf");
        download.sendFile(context);
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return "-none-";
  }


  /**
   *  Action to delete the specified report that was previously processed. The
   *  attached file is also deleted.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    Connection db = null;
    //Process parameters
    String id = context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the queued report
      ReportQueue queue = new ReportQueue(db, Integer.parseInt(id));
      if (queue.getProcessed() != null) {
        queue.delete(db, this.getPath(context, "reports-queue") + this.getDatePath(queue.getEntered()) + queue.getFilename());
      } else {
        context.getRequest().setAttribute("actionError", "Report could not be deleted because it has not been processed");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "DeleteReportOK";
  }


  /**
   *  Action to cancel a queued report. If the report is already processing then
   *  it cannot be cancelled.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCancelReport(ActionContext context) {
    Connection db = null;
    //Process parameters
    String id = context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the queued report
      ReportQueue queue = new ReportQueue(db, Integer.parseInt(id));
      if (queue.getProcessed() == null) {
        queue.delete(db, this.getPath(context, "reports-queue") + this.getDatePath(queue.getEntered()) + queue.getFilename());
      } else {
        context.getRequest().setAttribute("actionError", "Report could not be cancelled because processing has already started");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "CancelReportOK";
  }
}


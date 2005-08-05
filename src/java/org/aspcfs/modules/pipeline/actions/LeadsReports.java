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
package org.aspcfs.modules.pipeline.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.ViewpointInfo;

import java.sql.Connection;

/**
 * Actions for working with Pipeline Management reports. Code originally from
 * Leads.java.
 *
 * @author matt rajkowski
 * @version $Id: LeadsReports.java,v 1.1.2.2 2003/09/12 15:27:16 mrajkowski
 *          Exp $
 * @created September 12, 2003
 */
public final class LeadsReports extends CFSModule {

  /**
   * Action to proceed to default report page
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandExportList(context);
  }


  /**
   * Action to display a list of exported data files
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandExportList(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Check the pagedlistinfo for which reports to filter
    PagedListInfo rptListInfo = this.getPagedListInfo(
        context, "LeadRptListInfo");
    rptListInfo.setLink("LeadsReports.do?command=ExportList");
    //Get a list of the exported files to show to user
    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_LEADS_REPORTS);
    files.setLinkItemId(-1);
    files.setPagedListInfo(rptListInfo);
    try {
      db = this.getConnection(context);
      if ("all".equals(rptListInfo.getListView())) {
        files.setOwnerIdRange(this.getUserRange(context, userId));
      } else {
        files.setOwner(userId);
      }
      files.buildList(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "ViewReports");
    context.getRequest().setAttribute("FileList", files);
    return ("ExportListOK");
  }


  /**
   * Action to prepare the export data form
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandExportForm(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Reports", "Generate new");
    return ("ExportFormOK");
  }


  /**
   * Action to export the data based on form data
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandExport(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    boolean recordInserted = false;
    boolean isValid = false;
    //Process the parameters
    String subject = context.getRequest().getParameter("subject");
    String ownerCriteria = context.getRequest().getParameter("criteria1");
    //Prepare the filepath to store the file
    String filePath = this.getPath(context, "lead-reports") + DateUtils.getDatePath(
        new java.util.Date());
    //Prepare the exported data report
    OpportunityReport oppReport = new OpportunityReport();
    oppReport.setCriteria(
        context.getRequest().getParameterValues("selectedList"));
    oppReport.setFilePath(filePath);
    oppReport.setSubject(subject);
    //Apply the pagedlist info
    PagedListInfo thisInfo = new PagedListInfo();
    thisInfo.setColumnToSortBy(context.getRequest().getParameter("sort"));
    thisInfo.setItemsPerPage(0);
    oppReport.setPagedListInfo(thisInfo);
    //Apply the filters
    if (ownerCriteria.equals("my")) {
      oppReport.setOwner(userId);
    } else if (ownerCriteria.equals("all")) {
      oppReport.setOwnerIdRange(this.getUserRange(context, userId));
    }
    try {
      db = this.getConnection(context);
      //Generate the report
      oppReport.buildReportFull(db, context);
      oppReport.setEnteredBy(getUserId(context));
      oppReport.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, oppReport);
      if (isValid) {
        oppReport.saveAndInsert(db);
        return "ExportOK";
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ExportFormOK";
  }


  /**
   * Action to display previously exported data, as an HTML file
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandShowExportHtml(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Process the paramters
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Retrieve the exported data to display
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_LEADS_REPORTS);
      String filePath = this.getPath(context, "lead-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = this.includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception e) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("ExportHtmlOK");
  }


  /**
   * Action that downloads an exported CSV file
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownloadCSVExport(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    FileItem thisItem = null;
    Connection db = null;
    //Process the parameter
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_LEADS_REPORTS);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      FileItem itemToDownload = null;
      itemToDownload = thisItem;
      String filePath = this.getPath(context, "lead-reports") + getDatePath(
          itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("PMF-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }


  /**
   * Action to delete exported data
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteCSVExport(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Connection db = null;
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Process the parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_LEADS_REPORTS);
      if (!hasViewpointAuthority(
          db, context, "pipeline", thisItem.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      recordDeleted = thisItem.delete(
          db, this.getPath(context, "lead-reports"));
      //Delete any related files
      String filePath1 = this.getPath(context, "lead-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".csv";
      java.io.File fileToDelete1 = new java.io.File(filePath1);
      if (!fileToDelete1.delete()) {
        System.err.println("FileItem-> Tried to delete csv: " + filePath1);
      }
      String filePath2 = this.getPath(context, "lead-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      java.io.File fileToDelete2 = new java.io.File(filePath2);
      if (!fileToDelete2.delete()) {
        System.err.println("FileItem-> Tried to delete html: " + filePath2);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports del");
    if (recordDeleted) {
      return ("DeleteExportOK");
    } else {
      return ("DeleteExportERROR");
    }
  }
}


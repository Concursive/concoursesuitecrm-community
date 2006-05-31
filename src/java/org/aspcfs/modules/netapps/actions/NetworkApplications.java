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
package org.aspcfs.modules.netapps.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.netapps.base.ContractExpiration;
import org.aspcfs.modules.netapps.base.ContractExpirationList;
import org.aspcfs.modules.netapps.base.ContractExpirationLogList;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    September 16, 2004
 *@version    $Id: NetworkApplications.java,v 1.1.2.3 2004/09/20 17:42:25
 *      kbhoopal Exp $
 */
public final class NetworkApplications extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-view"))) {
      return ("PermissionError");
    }

    return executeCommandSearchForm(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-view"))) {
      return ("PermissionError");
    }

    return getReturn(context, "SearchForm");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-view"))) {
      return ("PermissionError");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ExpirationContractListInfo");
    pagedListInfo.setLink("NetworkApplications.do?command=Search");
    Connection db = null;
    ContractExpirationList thisList = null;
    try {
      db = this.getConnection(context);
      thisList = new ContractExpirationList();
      thisList.setPagedListInfo(pagedListInfo);
      thisList.setExcludeUnapprovedContracts(true);
      pagedListInfo.setSearchCriteria(thisList, context);
      thisList.buildList(db);
      context.getRequest().setAttribute("ImportResults", thisList);
      context.getRequest().setAttribute("approved", "approved");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return getReturn(context, "Search");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ExpirationContractListInfo");
    pagedListInfo.setLink("NetworkApplications.do?command=List");
    try {
      db = this.getConnection(context);
      ContractExpirationList thisList = new ContractExpirationList();
      thisList.setPagedListInfo(pagedListInfo);
      thisList.setExcludeUnapprovedContracts(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("ImportResults", thisList);
      context.getRequest().setAttribute("approved", "approved");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "List");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String expirationId = context.getRequest().getParameter("expirationId");
    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = new ContractExpiration(db, Integer.parseInt(expirationId));
      context.getRequest().setAttribute("contractExpiration", thisContractExpiration);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ContractExpirationDetails");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String expirationId = context.getRequest().getParameter("expirationId");
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ExpirationContractHistoryListInfo");
    pagedListInfo.setLink("NetworkApplications.do?command=ViewHistory&expirationId=" + expirationId);
    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = new ContractExpiration(db, Integer.parseInt(expirationId));
      context.getRequest().setAttribute("contractExpiration", thisContractExpiration);

      ContractExpirationLogList thisList = new ContractExpirationLogList();
      thisList.setPagedListInfo(pagedListInfo);
      thisList.setExpirationId(expirationId);
      thisList.buildList(context.getRequest(), db);
      context.getRequest().setAttribute("contractExpirationLogList", thisList);
      thisList.setPagedListInfo(pagedListInfo);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ContractExpirationHistory");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String expirationId = context.getRequest().getParameter("expirationId");
    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = new ContractExpiration(db, Integer.parseInt(expirationId));
      context.getRequest().setAttribute("contractExpiration", thisContractExpiration);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateQuote(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = (ContractExpiration) context.getFormBean();
      thisContractExpiration.setModifiedBy(getUserId(context));
      resultCount = thisContractExpiration.updateQuoteInformation(db);
      context.getRequest().setAttribute("contractExpiration", thisContractExpiration);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("list".equals(context.getRequest().getParameter("return"))) {
      return executeCommandSearch(context);
    } else {
      return executeCommandView(context);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-delete"))) {
      return ("PermissionError");
    }

    return "ConfirmDeleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts-delete"))) {
      return ("PermissionError");
    }
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-delete"))) {
      return ("PermissionError");
    }

    Connection db = null;
    boolean recordDeleted = false;
    String expirationId = context.getRequest().getParameter("expirationId");
    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = new ContractExpiration(db, Integer.parseInt(expirationId));
      if (thisContractExpiration.getStatusId() == Import.PROCESSED_APPROVED) {
        recordDeleted = thisContractExpiration.delete(context.getRequest(), db);
        if (!recordDeleted) {
          context.getRequest().setAttribute("ContractExpirationDetails", thisContractExpiration);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Delete");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrintReport(ActionContext context) {
    if (!hasPermission(context, "netapps_expiration_contracts-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String endDateStart = context.getRequest().getParameter("endDateStart");
      String endDateEnd = context.getRequest().getParameter("endDateEnd");

      //Temporary fix, setting start and end dates to current dates/
      //the long term solution is to change the report query based on the parameters supplied
      Calendar calendar = Calendar.getInstance();
      if ("".equals(endDateStart)) {
        endDateStart = calendar.getTimeInMillis() + "";
      }

      if ("".equals(endDateEnd)) {
        endDateEnd = calendar.getTimeInMillis() + "";
      }

      Timestamp tmpTimestampStart = new Timestamp(Long.parseLong(endDateStart));
      Timestamp tmpTimestampEnd = new Timestamp(Long.parseLong(endDateEnd));

      HashMap map = new HashMap();
      map.put("date_start", tmpTimestampStart);
      map.put("date_end", tmpTimestampEnd);
      map.put("language", UserUtils.getUserLocale(context.getRequest()).getLanguage());
      map.put("country", UserUtils.getUserLocale(context.getRequest()).getCountry());
      map.put("currency", UserUtils.getUserCurrency(context.getRequest()));
      String reportPath = getWebInfPath(context, "reports");
      map.put("path", reportPath);
      String filename = "dataline_contract_renewal.xml";
      byte[] bytes = JasperReportUtils.getReportAsBytes(reportPath + filename, map, db);
      if (bytes != null) {
        FileDownload fileDownload = new FileDownload();
        fileDownload.setDisplayName("ExpirationContracts.pdf");
        fileDownload.sendFile(context, bytes, "application/pdf");
      } else {
        return ("SystemError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }

}


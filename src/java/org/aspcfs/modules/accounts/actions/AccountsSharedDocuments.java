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
package org.aspcfs.modules.accounts.actions;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.documents.base.AccountDocument;
import org.aspcfs.modules.documents.base.AccountDocumentList;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * @author Achyutha
 * @version $Id: $
 * @created June 20, 2006
 */
public class AccountsSharedDocuments extends CFSModule {

  public String executeCommandList(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!hasPermission(context, "accounts-accounts-shareddocuments-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      Organization thisOrganization = setOrganization(context, db);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisOrganization.getOrgId())) {
        return ("PermissionError");
      }
      //PagedList Info
      AccountDocumentList accountDocumentList = new AccountDocumentList();
      PagedListInfo accountDocumentListInfo = this.getPagedListInfo(
          context, "accountsSharedDocumentsInfo");
      accountDocumentListInfo.setLink(
          "AccountsSharedDocuments.do?command=List&orgId=" + thisOrganization.getId());
      accountDocumentListInfo.setItemsPerPage(0);
      accountDocumentList.setPagedListInfo(accountDocumentListInfo);
      accountDocumentList.setOrgId(thisOrganization.getId());
      accountDocumentList.setBuildPortalRecords(
      		isPortalUser(context) ? Constants.TRUE : Constants.UNDEFINED);
      accountDocumentList.setUserId(getUserId(context));
      accountDocumentList.buildList(db);

  		context.getRequest().setAttribute("accountDocumentList", accountDocumentList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "List");
  }

  private static Organization setOrganization(ActionContext context, Connection db) throws SQLException {
    Organization thisOrganization = null;
    String orgId = context.getRequest().getParameter("orgId");
    thisOrganization = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("orgDetails", thisOrganization);
    return thisOrganization;
  }
	
}

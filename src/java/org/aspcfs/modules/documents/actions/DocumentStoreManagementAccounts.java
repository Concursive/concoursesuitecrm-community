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
package org.aspcfs.modules.documents.actions;

import java.sql.Connection;
import java.util.Iterator;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.documents.base.AccountDocument;
import org.aspcfs.modules.documents.base.AccountDocumentList;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * @author Achyutha
 * @version $Id: $
 * @created June 19, 2006
 */
public class DocumentStoreManagementAccounts extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "documents-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String documentStoreId = (String) context.getRequest().getParameter("documentStoreId");
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(context, db, thisDocumentStore, "accounts-accounts-documentstore-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("accounts").toLowerCase());
      // Setup a paged list
      PagedListInfo accountDocumentInfo = this.getPagedListInfo(
          context, "documentStoreAccountInfo");
      accountDocumentInfo.setLink(
          "DocumentStoreManagementAccounts.do?command=View&section=Accounts&documentStoreId=" + thisDocumentStore.getId());
      accountDocumentInfo.setItemsPerPage(0);
      // Load the list of Accounts
      AccountDocumentList accountDocumentList = new AccountDocumentList();
      accountDocumentList.setPagedListInfo(accountDocumentInfo);
      accountDocumentList.setDocumentStoreId(thisDocumentStore.getId());
      accountDocumentList.buildList(db);
      context.getRequest().setAttribute("accountDocumentList", accountDocumentList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      return ("DocumentStorePopupOK");
    } else {
      return ("DocumentStoreOK");
    }
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandLinkAccount(ActionContext context) {
    if (!hasPermission(context, "documents-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String documentStoreId = (String) context.getRequest().getParameter("documentStoreId");
    String orgId = (String) context.getRequest().getParameter("orgId");
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(db, Integer.parseInt(documentStoreId));      
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "accounts-accounts-documentstore-add")) {
        return "PermissionError";
      }
      AccountDocumentList accountDocumentList = new AccountDocumentList();
      accountDocumentList.setDocumentStoreId(Integer.parseInt(documentStoreId));
      accountDocumentList.setOrgId(Integer.parseInt(orgId));
      accountDocumentList.buildList(db);
      
      if (accountDocumentList.size() == 0) {
        AccountDocument thisAccountDocument = new AccountDocument();
        thisAccountDocument.setDocumentStoreId(Integer.parseInt(documentStoreId));
        thisAccountDocument.setOrgId(Integer.parseInt(orgId));
        thisAccountDocument.insert(db);
      }
      
    } catch (Exception e) {
    	e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DocumentStoreAddOK");
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveAccount(ActionContext context) {
    if (!hasPermission(context, "documents-view")) {
      return ("PermissionError");
    }
    Connection db = null;

    String documentStoreId = (String) context.getRequest().getParameter("documentStoreId");
    String orgId = (String) context.getRequest().getParameter("orgId");
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(db, Integer.parseInt(documentStoreId));      
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "accounts-accounts-documentstore-delete")) {
        return "PermissionError";
      }
      AccountDocumentList accountDocumentList = new AccountDocumentList();
      accountDocumentList.setDocumentStoreId(Integer.parseInt(documentStoreId));
      accountDocumentList.setOrgId(Integer.parseInt(orgId));
      accountDocumentList.buildList(db);
      
      System.out.println("account document list size "+accountDocumentList.size());
      Iterator thisIterator = accountDocumentList.iterator();
      while(thisIterator.hasNext()) {
      	AccountDocument thisAccountDocument = (AccountDocument) thisIterator.next();
      	thisAccountDocument.delete(db);
      }
      
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DocumentStoreRemoveOK");
  }

	
}

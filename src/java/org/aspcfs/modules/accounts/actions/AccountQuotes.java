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

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: AccountQuotes.java,v 1.4.16.3 2004/11/04 20:44:49 partha Exp
 *          $
 * @created April 23, 2004
 */
public final class AccountQuotes extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Quotes", "View Quote Details");
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }
    String headerId = context.getRequest().getParameter("headerId");
    if (headerId == null) {
      headerId = (String) context.getRequest().getAttribute("headerId");
    }
    PagedListInfo quoteListInfo = this.getPagedListInfo(
        context, "accountQuoteListInfo", "qe.group_id", "desc");
    quoteListInfo.setLink(
        "AccountQuotes.do?command=View&orgId=" + orgid +
            RequestUtils.addLinkParams(context.getRequest(), "version|popup|popupType"));
    Connection db = null;
    QuoteList quoteList = new QuoteList();
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgid))) {
        return ("PermissionError");
      }

      if (thisOrganization.isTrashed()) {
        quoteList.setIncludeOnlyTrashed(true);
      }
      if (version != null && !"".equals(version)) {
        Quote quote = new Quote(db, Integer.parseInt(version));
        quoteList.setBuildCompleteVersionList(true);
        quoteList.setId(quote.getRootQuote(db, quote.getParentId()));
        quoteList.setPagedListInfo(quoteListInfo);
        quoteListInfo.setSearchCriteria(quoteList, context);
        if (thisOrganization.isTrashed()) {
          quoteList.setIncludeOnlyTrashed(true);
        }
        quoteList.buildList(db);
        context.getRequest().setAttribute("quoteList", quoteList);
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
        return ("ListOK");
      }
      quoteList.setPagedListInfo(quoteListInfo);
      quoteList.setOrgId(Integer.parseInt(orgid));
      if ((headerId != null) && !"".equals(headerId)) {
        quoteList.setHeaderId(Integer.parseInt(headerId));
      }
      quoteList.buildList(db);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("quoteList", quoteList);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return getReturn(context, "List");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Quotes", "View Quote Details");
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String printQuote = (String) context.getRequest().getParameter("canPrint");
    if (printQuote != null && !"".equals(printQuote)) {
      context.getRequest().setAttribute("canPrint", printQuote);
    }
    String quoteId = (String) context.getRequest().getAttribute("quoteId");
    if (quoteId == null || "".equals(quoteId)) {
      quoteId = (String) context.getRequest().getParameter("quoteId");
    }
    Quote quote = null;
    QuoteProductList quoteProducts = null;
    ProductCatalogList productList = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList valuesList = null;
    Organization thisOrganization = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (quoteId == null || "".equals(quoteId)) {
      try {
        //check for the product id & ticket id.. is it a request to create a new quote?
        int productId = Integer.parseInt(
            (String) context.getRequest().getParameter("productId"));
        int ticketId = Integer.parseInt(
            (String) context.getRequest().getParameter("ticketId"));
      } catch (Exception e) {
        //if the product id is null, then the incomplete form was submitted, set error message
        context.getRequest().setAttribute(
            "actionError",
            systemStatus.getLabel(
                "object.validation.actionError.invalidCriteria"));
        return "SearchCriteriaError";
      }
      // as product id is not null, create a new quote
      return executeCommandAddQuote(context);
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the quote
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      quote.retrieveTicket(db);

      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }

      thisOrganization = new Organization(db, quote.getOrgId());

      optionList = new ProductOptionList();
      optionList.setBuildConfigDetails(true);
      optionList.buildList(db);
      context.getRequest().setAttribute("optionList", optionList);

      productList = new ProductCatalogList();
      quoteProducts = quote.getProductList();
      Iterator iterator = quoteProducts.iterator();
      while (iterator.hasNext()) {
        QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
        quoteProduct.buildProductOptions(db);
        quoteProduct.queryRecord(db, quoteProduct.getId());
        ProductCatalog product = new ProductCatalog();
        product.setBuildOptions(true);
        product.queryRecord(db, quoteProduct.getProductId());
        productList.add(product);
      }
      context.getRequest().setAttribute("quoteProductList", quoteProducts);
      context.getRequest().setAttribute("productList", productList);

      int orderId = quote.getOrderId(db);
      if (orderId != -1) {
        Order order = new Order(db, orderId);
        context.getRequest().setAttribute("order", order);
      }

      int headerId = quote.getHeaderId();
      if (headerId != -1) {
        OpportunityHeader opportunity = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunity", opportunity);
      }

      Quote quoteBean = new Quote();
      context.getRequest().setAttribute("quoteBean", quoteBean);

      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      if (quote.getLogoFileId() > 0) {
        FileItem thisItem = new FileItem(
            db, quote.getLogoFileId(), Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
        context.getRequest().setAttribute("fileItem", thisItem);
      }
    } catch (Exception errorMessage) {
      errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("quote", quote);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return getReturn(context, "Details");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveProduct(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-edit"))) {
      return ("PermissionError");
    }
    String quoteName = (String) context.getRequest().getParameter("quoteId");
    String productName = (String) context.getRequest().getParameter(
        "productId");
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = Integer.parseInt(quoteName);
    int productId = Integer.parseInt(productName);
    QuoteProduct quoteProduct = null;
    ProductCatalog product = null;
    Connection db = null;
    try {
      db = getConnection(context);
      quoteProduct = new QuoteProduct(db, productId);

      Quote previousQuote = new Quote();
      previousQuote.setBuildProducts(true);
      previousQuote.queryRecord(db, quoteProduct.getQuoteId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, previousQuote.getOrgId())) {
        return ("PermissionError");
      }
      // Delete the requested product
      product = new ProductCatalog(db, quoteProduct.getProductId());
      quoteProduct.delete(db);
      // Load the final quote
      Quote quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteProduct.getQuoteId());
      processUpdateHook(context, previousQuote, quote);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandDetails(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveNotes(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-view"))) {
      return ("PermissionError");
    }
    int quoteId = -1;
    boolean isValid = false;
    int recordCount = -1;
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    QuoteNote quoteNote = null;
    Quote quote = null;
    Quote previousQuote = null;
    Quote notes = (Quote) context.getFormBean();
    if (notes == null) {
      notes = new Quote();
    }
    Organization thisOrganization = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);

      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);
      previousQuote = new Quote(db, quoteId);

      thisOrganization = new Organization(db, quote.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      //Check to see for any new notes to save them as a new quote note entry
      if (notes.getNotes() != null) {
        quoteNote = new QuoteNote();
        quoteNote.setQuoteId(quote.getId());
        quoteNote.setEnteredBy(this.getUserId(context));
        quoteNote.setModifiedBy(this.getUserId(context));
        quoteNote.setNotes(notes.getNotes());
        isValid = this.validateObject(context, db, quoteNote);
        if (isValid) {
          quoteNote.insert(db);
        }
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);
      if (quote.getStatusId() == list.getIdFromValue("Rejected by customer")) {
        quote.setStatusId(list.getIdFromValue("Pending customer acceptance"));
        Timestamp currentTimestamp = new Timestamp(
            Calendar.getInstance().getTimeInMillis());
        quote.setIssuedDate(currentTimestamp);
        quote.setStatusDate(currentTimestamp);
        //update the quote
        isValid = isValid && this.validateObject(context, db, quote);
        if (isValid) {
          recordCount = quote.update(db);
        }
        if (recordCount == 1) {
          this.processUpdateHook(context, previousQuote, quote);
        }
      }

      //rebuild the quote notes list to display the updated set of notes to the user
      QuoteNoteList noteList = new QuoteNoteList();
      noteList.setQuoteId(quote.getId());
      noteList.buildList(db);
      context.getRequest().setAttribute("quoteNoteList", noteList);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "SaveNotes");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSubmit(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-edit"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);
      quote = new Quote();
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SubmitOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-delete"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = Integer.parseInt(
        (String) context.getRequest().getParameter("quoteId"));
    int orgId = Integer.parseInt(
        (String) context.getRequest().getParameter("orgId"));
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      quote.delete(db);
      processDeleteHook(context, quote);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inline = (context.getRequest().getParameter("popupType") != null
        && "inline".equals(context.getRequest().getParameter("popupType")));
    context.getRequest().setAttribute(
        "refreshUrl", "AccountQuotes.do?command=View&orgId=" + orgId + (inline ? "&popup=true" : ""));
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Quote quote = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String quoteId = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    if (context.getRequest().getParameter("quoteId") != null) {
      quoteId = context.getRequest().getParameter("quoteId");
    }

    try {
      db = this.getConnection(context);
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.setBuildTicket(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }

      htmlDialog.setTitle("Centric CRM: Quote Management");
      DependencyList dependencies = quote.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      //htmlDialog.addMessage("Please review carefully...\n" + dependencies.getHtmlString());
      if (quote.getOrderId(db) != -1) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(
            systemStatus.getLabel("quotes.deleteRelatedOrdersFirst"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountQuotes.do?command=Delete&quoteId=" + quote.getId() + "&orgId=" + quote.getOrgId()
            + RequestUtils.addLinkParams(context.getRequest(), "version|popup|popupType") + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-edit"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    Quote previousQuote = null;
    int recordCount = -1;
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = Integer.parseInt(
        (String) context.getRequest().getParameter("quoteId"));
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Quote quoteBean = (Quote) context.getFormBean();

      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      previousQuote = new Quote(db, quoteId);

      if (quoteBean.getNotes() != null) {
        quote.setNotes(quoteBean.getNotes());
      }
      if (quoteBean.getExpirationDate() != null) {
        quote.setExpirationDate(quoteBean.getExpirationDate());
      }
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        recordCount = quote.update(db);
      }
      if (recordCount == 1) {
        this.processUpdateHook(context, previousQuote, quote);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SetSearchOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyForm(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-edit"))) {
      return ("PermissionError");
    }
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String quoteIdString = (String) context.getRequest().getAttribute(
        "quoteId");
    if (quoteIdString == null || "".equals(quoteIdString)) {
      quoteIdString = (String) context.getRequest().getParameter("quoteId");
    }
    Quote quote = null;
    Connection db = null;

    try {
      db = this.getConnection(context);

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      list2.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      //Create a new instance of Quote
      quote = new Quote(db, Integer.parseInt(quoteIdString));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quoteBean", quote);

      int headerId = quote.getHeaderId();
      if (headerId != -1) {
        OpportunityHeader opportunity = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunity", opportunity);
      }

      Organization orgDetails = new Organization(db, quote.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      ContactList contactList = new ContactList();
      contactList.setOrgId(quote.getOrgId());
      contactList.setLeadsOnly(Constants.FALSE);
      contactList.setEmployeesOnly(Constants.FALSE);
      contactList.setDefaultContactId(quote.getContactId());
      contactList.buildList(db);
      context.getRequest().setAttribute("contactList", contactList);

      //Create a list for selection of a logo file
      FileItemList itemList = new FileItemList();
      itemList.setLinkModuleId(Constants.DOCUMENTS_QUOTE_LOGO);
      itemList.setLinkItemId(Constants.QUOTES);
      itemList.buildList(db);
      context.getRequest().setAttribute("fileItemList", itemList);
      context.getRequest().setAttribute("systemStatus", systemStatus);
    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ModifyForm");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-edit"))) {
      return ("PermissionError");
    }
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    String version = (String) context.getRequest().getParameter("versionId");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String orgIdString = (String) context.getRequest().getParameter("orgId");
    int orgId = -1;
    if (orgIdString != null && !"".equals(orgIdString)) {
      orgId = Integer.parseInt(orgIdString);
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    int quoteId = -1;
    if (quoteIdString != null && !"".equals(quoteIdString)) {
      quoteId = Integer.parseInt(quoteIdString);
    }
    boolean isValid = false;
    int recordCount = -1;
    Quote quote = null;
    Quote previousQuote = null;
    Quote quoteBean = (Quote) context.getFormBean();
    User user = this.getUser(context, this.getUserId(context));
    String printQuote = (String) context.getRequest().getAttribute("canPrint");
    if (printQuote != null && !"".equals(printQuote)) {
      context.getRequest().setAttribute("canPrint", printQuote);
    } else {
      printQuote = quoteBean.getCanPrint();
      context.getRequest().setAttribute("canPrint", printQuote);
    }

    Connection db = null;

    try {
      db = this.getConnection(context);

      //Create a new instance of Quote
      quote = new Quote(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      previousQuote = new Quote(db, quoteId);

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      //Retrieve the contact id from the bean
      int contactId = quoteBean.getContactId();
      if (quoteBean.getContactId() != -1) {
        quote.setContactId(quoteBean.getContactId());
      }
      quote.setShortDescription(quoteBean.getShortDescription());
      quote.setExpirationDate(quoteBean.getExpirationDate());
      quote.setIssuedDate(quoteBean.getIssuedDate());
      quote.setNotes(quoteBean.getNotes());
      quote.setStatusId(quoteBean.getStatusId());
      quote.setHeaderId(quoteBean.getHeaderId());
      quote.setDeliveryId(quoteBean.getDeliveryId());
      quote.setEmailAddress(quoteBean.getEmailAddress());
      quote.setFaxNumber(quoteBean.getFaxNumber());
      quote.setPhoneNumber(quoteBean.getPhoneNumber());
      quote.setAddress(quoteBean.getAddress());
      quote.setCloseIt(quoteBean.getCloseIt());
      quote.setClosed(quoteBean.getClosed());
      quote.setSubmitAction(quoteBean.getSubmitAction());
      quote.setLogoFileId(quoteBean.getLogoFileId());
      quote.setModifiedBy(user.getId());
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        recordCount = quote.update(db);
      }
      if (recordCount == 1) {
        this.processUpdateHook(context, previousQuote, quote);
      }
      Organization orgDetails = new Organization(db, orgId);
      context.getRequest().setAttribute("OrgDetails", orgDetails);
    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordCount == -1 || !isValid) {
      return executeCommandModifyForm(context);
    }
    boolean isAddVersion = (context.getRequest().getParameter("newVersion") != null && "true".equals(context.getRequest().getParameter("newVersion")));
    if (isAddVersion) {
      return "ModifyAddVersionOK";
    }
    return getReturn(context, "Modify");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddQuote(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-add"))) {
      return ("PermissionError");
    }
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    boolean isValid = false;
    boolean recordInserted = false;
    String ticketIdString = (String) context.getRequest().getParameter(
        "ticketId");
    int ticketId = -1;
    String productIdString = (String) context.getRequest().getParameter(
        "productId");
    int productId = -1;
    String orgIdString = (String) context.getRequest().getParameter("orgId");
    int orgId = -1;
    if (ticketIdString != null && !"".equals(ticketIdString)) {
      ticketId = Integer.parseInt(ticketIdString);
    }
    if (productIdString != null && !"".equals(productIdString)) {
      productId = Integer.parseInt(productIdString);
    }
    if (orgIdString != null && !"".equals(orgIdString)) {
      orgId = Integer.parseInt(orgIdString);
    }

    Quote quote = null;
    Ticket ticket = null;
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;

    try {
      db = this.getConnection(context);

      //Create a new instance of Quote
      quote = (Quote) context.getFormBean();
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      //Retrieve the ticket
      if (ticketId != -1) {
        ticket = new Ticket(db, ticketId);
        ProductCatalog product = new ProductCatalog(db, productId);
        quote.setProductId(product.getId());
        product.determineCategory(db);
        quote.setShortDescription(product.getCategoryName() + ", " + product.getName() + ": ");
        //if the ticket is not for a new ad design, retrieve the customer product from the database
        if (!ticket.getProblem().equals("New Ad Design Request") && ticket.getCustomerProductId() != -1) {
          CustomerProduct customerProduct = new CustomerProduct(
              db, ticket.getCustomerProductId());
          quote.setCustomerProductId(customerProduct.getId());
        }
        quote.setOrgId(ticket.getOrgId());
        quote.setContactId(ticket.getContactId());
        quote.setTicketId(ticket.getId());
      }
      int contactId = quote.getContactId();
      if (contactId != -1) {
        Organization orgDetails = new Organization(db, quote.getOrgId());
        context.getRequest().setAttribute("OrgDetails", orgDetails);
      } else {
        quote.setStatusId(list.getIdFromValue("Incomplete"));
      }
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
      quote.createNewGroup(db);
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        quote.setVersion(quote.getNewVersion());
        recordInserted = quote.insert(db);
        String quoteId = "" + quote.getId();
        context.getRequest().setAttribute("quoteId", quoteId);
      }
      if (recordInserted) {
        this.processInsertHook(context, quote);
      }

      if (ticketId != -1) {
        QuoteNote quoteNote = new QuoteNote();
        quoteNote.setQuoteId(quote.getId());
        quoteNote.setNotes(ticket.getProblem().trim());
        quoteNote.setModifiedBy(user.getId());
        quoteNote.setEnteredBy(user.getId());
        isValid = isValid && this.validateObject(context, db, quoteNote);
        if (isValid) {
          quoteNote.insert(db);
        }
      }

    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!isValid) {
      return executeCommandAddQuoteForm(context);
    }
    return "SaveOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddQuoteForm(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-add"))) {
      return ("PermissionError");
    }
    int orgId = -1;
    String orgIdString = (String) context.getRequest().getParameter("orgId");
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    if (orgIdString != null && !"".equals(orgIdString)) {
      orgId = Integer.parseInt(orgIdString);
    }
    Quote quote = (Quote) context.getFormBean();
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;

    try {
      db = this.getConnection(context);

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      list2.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteDeliveryList", list2);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, orgId)) {
        return ("PermissionError");
      }
      Organization orgDetails = new Organization(db, orgId);
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      int headerId = quote.getHeaderId();
      if (headerId != -1) {
        OpportunityHeader opportunity = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunity", opportunity);
      }
      //Create the list of contacts
      ContactList contactList = new ContactList();
      contactList.setOrgId(orgId);
      contactList.setLeadsOnly(Constants.FALSE);
      contactList.setEmployeesOnly(Constants.FALSE);
      contactList.buildList(db);
      context.getRequest().setAttribute("contactList", contactList);

      //Create a new instance of Quote
      if (quote == null) {
        quote = new Quote();
      }
      quote.setOrgId(orgId);
      quote.setName(orgDetails.getName());
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
//      quote.setStatusId(list.getIdFromValue("Incomplete"));
      context.getRequest().setAttribute("quoteBean", quote);

      FileItemList itemList = new FileItemList();
      itemList.setLinkModuleId(Constants.DOCUMENTS_QUOTE_LOGO);
      itemList.setLinkItemId(Constants.QUOTES);
      itemList.buildList(db);
      context.getRequest().setAttribute("fileItemList", itemList);
    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return getReturn(context, "AddQuoteForm");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-add"))) {
      return ("PermissionError");
    }
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Quote oldQuote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (quoteId != null && !"".equals(quoteId)) {
        oldQuote = new Quote();
        oldQuote.setBuildProducts(true);
        oldQuote.queryRecord(db, Integer.parseInt(quoteId));
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, oldQuote.getOrgId())) {
        return ("PermissionError");
      }
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      //Clone a new instance of Quote
      quote = new Quote();
      quote.setStatusId(list.getIdFromValue("Incomplete"));
      quote = oldQuote.addVersion(db, quote);
      quote.queryRecord(db, quote.getId());
      this.processUpdateHook(context, oldQuote, quote);
      String quoteIdString = String.valueOf(quote.getId());
      context.getRequest().setAttribute("quoteId", quoteIdString);
    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandModifyForm(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!(hasPermission(context, "accounts-quotes-view"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Connection db = null;
    Quote quote = null;
    String quoteId = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      quoteId = (String) context.getRequest().getParameter("quoteId");
      db = this.getConnection(context);
      quote = new Quote();
      quote.setBuildHistory(true);
      quote.setSystemStatus(systemStatus);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);
      Organization orgDetails = new Organization(db, quote.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);
      //Retrieve the lookup list for the quote status
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Quotes", "View Quote Details");
    return getReturn(context, "ViewHistory");
  }
}


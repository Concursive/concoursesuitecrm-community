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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCatalogList;
import org.aspcfs.modules.products.base.ProductOptionList;
import org.aspcfs.modules.products.base.ProductOptionValuesList;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.utils.web.*;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    November 19, 2004
 *@version    $Id$
 */
public final class LeadsQuotes extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandQuoteList(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    int headerId = -1;
    OpportunityHeader thisHeader = null;
    addModuleBean(context, "View Opportunities", "View Opportunity Details");
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Check parameters
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt((String) context.getRequest().getAttribute("headerId"));
    }
    Connection db = null;
    QuoteList quotes = new QuoteList();
    PagedListInfo quoteListInfo = this.getPagedListInfo(context, "LeadsQuoteListInfo", "qe.group_id", "desc");
    quoteListInfo.setLink("LeadsQuotes.do?command=QuoteList&headerId=" + headerId + RequestUtils.addLinkParams(context.getRequest(), "viewSource"));
    quotes.setPagedListInfo(quoteListInfo);
    try {
      db = this.getConnection(context);
      //Generate the opportunity header
      thisHeader = new OpportunityHeader();
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      //Generate the quote list
      quoteListInfo.setSearchCriteria(quotes, context);
      quotes.setHeaderId(headerId);
      quotes.buildList(db);
      context.getRequest().setAttribute("quoteList", quotes);
      addRecentItem(context, thisHeader);
      //build the lookup lists
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList statusSelect = systemStatus.getLookupList(db, "lookup_quote_status");
      statusSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteStatusList", statusSelect);
      return ("QuoteListOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Opportunity Quotes", "View Opportunity Quote Details");
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
    Connection db = null;
    Quote quote = null;
    QuoteProductList quoteProducts = null;
    ProductCatalogList productList = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList valuesList = null;
    OpportunityHeader thisHeader = null;
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      //build the quote
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      quote.retrieveTicket(db);
      if (quote.getHeaderId() == -1) {
        context.getRequest().setAttribute("actionError",
            "Quote unrelated to the current Opportunity");
        return "UserError";
      }
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, quote.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", thisHeader);

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

      Quote quoteBean = new Quote();
      context.getRequest().setAttribute("quoteBean", quoteBean);

      valuesList = new ProductOptionValuesList();
      valuesList.buildList(db);
      context.getRequest().setAttribute("productOptionValuesList", valuesList);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      if (quote.getLogoFileId() > 0) {
				FileItem thisItem = new FileItem(db, quote.getLogoFileId() , Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
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
    return "DetailsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRemoveProduct(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String quoteName = (String) context.getRequest().getParameter("quoteId");
    String productName = (String) context.getRequest().getParameter("productId");
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
      product = new ProductCatalog(db, quoteProduct.getProductId());
      quoteProduct.delete(db);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveNotes(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-view"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    int quoteId = -1;
    OpportunityHeader header = null;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    StringBuffer oldNotes = new StringBuffer("");
    User user = this.getUser(context, this.getUserId(context));
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int orgId = user.getContact().getOrgId();
    QuoteNote quoteNote = null;
    Quote quote = null;
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
      context.getRequest().setAttribute("quote", quote);
      
      header = new OpportunityHeader();
      header.queryRecord(db, quote.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", header);

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
        processInsertHook(context, quoteNote);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);
      if (quote.getStatusId() == list.getIdFromValue("Rejected by customer")) {
        quote.setStatusId(list.getIdFromValue("Pending customer acceptance"));
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        quote.setIssuedDate(currentTimestamp);
        quote.setStatusDate(currentTimestamp);
        //update the quote
        isValid = isValid && this.validateObject(context, db, quote);
        if (isValid) {
          quote.update(db);
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
    return "SaveNotesOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSubmit(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    Quote quoteBean = (Quote) context.getFormBean();
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);
      quote = new Quote();
      quote.queryRecord(db, quoteId);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Quote quote = null;
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    Connection db = null;
    try {
      db = getConnection(context);
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      context.getRequest().setAttribute("refreshUrl", "LeadsQuotes.do?command=QuoteList&headerId=" + quote.getHeaderId());
      quote.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }
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

      htmlDialog.setTitle("Centric CRM: Quote Management");
      DependencyList dependencies = quote.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (quote.getOrderId(db) != -1) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.deleteRelatedOrdersFirst"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.dependencies"));
        htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='LeadsQuotes.do?command=Delete&quoteId=" + quote.getId() + "'");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyForm(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String quoteIdString = (String) context.getRequest().getAttribute("quoteId");
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
      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      list2.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      //Create a new instance of Quote
      quote = new Quote(db, Integer.parseInt(quoteIdString));
      context.getRequest().setAttribute("quoteBean", quote);

      int headerId = quote.getHeaderId();
      if (headerId != -1) {
        OpportunityHeader opportunityHeader = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunityHeader", opportunityHeader);
      }
      ContactList contactList = new ContactList();
      contactList.setOrgId(quote.getOrgId());
      contactList.buildList(db);
      context.getRequest().setAttribute("contactList", contactList);

      Organization orgDetails = new Organization(db, quote.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

			//Create a list for selection of a logo file
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
    return "ModifyFormOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    boolean isValid = false;
    String version = (String) context.getRequest().getParameter("versionId");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String orgIdString = (String) context.getRequest().getParameter("orgId");
    int orgId = -1;
    if (orgIdString != null && !"".equals(orgIdString)) {
      orgId = Integer.parseInt(orgIdString);
    }
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    int quoteId = -1;
    if (quoteIdString != null && !"".equals(quoteIdString)) {
      quoteId = Integer.parseInt(quoteIdString);
    }
    int resultCount = -1;
    Quote quote = null;
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

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
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
			quote.setShowTotal(quoteBean.getShowTotal());
			quote.setLogoFileId(quoteBean.getLogoFileId());
      quote.setModifiedBy(user.getId());
      isValid = this.validateObject(context, db, quoteBean);
      if (isValid) {
        resultCount = quote.update(db);
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
    if (resultCount == -1 || !isValid) {
      return executeCommandModifyForm(context);
    }
    return getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddQuote(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    String orgIdString = (String) context.getRequest().getParameter("orgId");
    int orgId = -1;
    if (orgIdString != null && !"".equals(orgIdString)) {
      orgId = Integer.parseInt(orgIdString);
    }

    Quote quote = null;
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;

    try {
      db = this.getConnection(context);

      //Create a new instance of Quote
      quote = (Quote) context.getFormBean();

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      int contactId = quote.getContactId();
      if (contactId != -1) {
        Organization orgDetails = new Organization(db, quote.getOrgId());
        context.getRequest().setAttribute("OrgDetails", orgDetails);
      } else {
        quote.setStatusId(list.getIdFromValue("Incomplete"));
      }
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
      quote.setVersion(quote.getNewVersion());
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        quote.createNewGroup(db);
        quote.insert(db);
        String quoteId = "" + quote.getId();
        context.getRequest().setAttribute("quoteId", quoteId);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddQuoteForm(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    int headerId = -1;
    String headerIdString = (String) context.getRequest().getParameter("headerId");
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    if (headerIdString != null && !"".equals(headerIdString)) {
      headerId = Integer.parseInt(headerIdString);
    }
    Quote quote = (Quote) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      list2.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      OpportunityHeader opportunityHeader = new OpportunityHeader();
      opportunityHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", opportunityHeader);

      //Create the list of contacts
      ContactList contactList = new ContactList();
      if (opportunityHeader.getAccountLink() != -1) {
        contactList.setOrgId(opportunityHeader.getAccountLink());
      } else {
        Contact oppContact = new Contact(db, opportunityHeader.getContactLink());
        if (oppContact.getOrgId() != -1) {
          contactList.setOrgId(oppContact.getOrgId());
        }
      }
      if (contactList.getOrgId() != -1) {
        contactList.buildList(db);
      } else {
        HashMap errors = new HashMap();
        errors.put("actionError", systemStatus.getLabel("object.validation.opportunities.quotes.noQuotesForGeneralContacts"));
        processErrors(context, errors);
      }
      context.getRequest().setAttribute("contactList", contactList);

      //Create a new instance of Quote
      if (quote == null) {
        quote = new Quote();
      }
      if (opportunityHeader.getAccountLink() != -1) {
        quote.setOrgId(opportunityHeader.getAccountLink());
        quote.setName(opportunityHeader.getAccountName());
      } else {
        Contact oppContact = new Contact(db, opportunityHeader.getContactLink());
        if (oppContact.getOrgId() != -1) {
          quote.setOrgId(oppContact.getOrgId());
          quote.setName(oppContact.getOrgName());
          quote.setContactId(oppContact.getId());
        }
      }
      quote.setEnteredBy(userId);
      quote.setModifiedBy(userId);
      quote.setHeaderId(headerId);
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
    return "AddQuoteFormOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String orgIdString = (String) context.getRequest().getParameter("orgId");
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
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      //Clone a new instance of Quote
      quote = new Quote();
      quote.setStatusId(list.getIdFromValue("Incomplete"));
      quote = oldQuote.addVersion(db, quote);
      quote.queryRecord(db, quote.getId());
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-view"))) {
      return ("PermissionError");
    }
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Connection db = null;
    Quote quote = null;
    String quoteId = null;
    try {
      quoteId = (String) context.getRequest().getParameter("quoteId");
      db = this.getConnection(context);
      quote = new Quote();
      quote.setBuildHistory(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      context.getRequest().setAttribute("quote", quote);

      OpportunityHeader header = new OpportunityHeader();
      header.queryRecord(db, quote.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", header);
      
      Organization orgDetails = new Organization(db, quote.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Quotes", "View Quote Details");
    return ("ViewHistoryOK");
  }
}


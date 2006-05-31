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
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author
 * @version $Id$
 * @created
 */
public final class AccountContactsOppQuotes extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-view"))) {
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

    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    String headerId = context.getRequest().getParameter("headerId");
    if (headerId == null) {
      headerId = (String) context.getRequest().getAttribute("headerId");
    }

    PagedListInfo quoteListInfo = this.getPagedListInfo(context, "accountQuoteListInfo", "qe.group_id", "desc");
    quoteListInfo.setLink("AccountContactsOppQuotes.do?command=View&orgId=" + orgid + (headerId != null ? "&headerId=" + headerId : "") + "&version=" + (version != null ? version : ""));
    Connection db = null;
    QuoteList quoteList = new QuoteList();
    Organization thisOrganization = null;
    Contact thisContact = null;
    OpportunityHeader header = null;
    try {
      db = this.getConnection(context);

      header = new OpportunityHeader(db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunity", header);

      thisContact = new Contact(db, header.getContactLink());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));

      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      context.getRequest().setAttribute("ContactDetails", thisContact);

      quoteList.setPagedListInfo(quoteListInfo);
      quoteList.setOrgId(Integer.parseInt(orgid));
      if ((headerId != null) && !"".equals(headerId)) {
        quoteList.setHeaderId(Integer.parseInt(headerId));
      }
      if (version != null && !"".equals(version)) {
        Quote quote = new Quote(db, Integer.parseInt(version));
        quoteList.setBuildCompleteVersionList(true);
        quoteList.setId(quote.getRootQuote(db, quote.getParentId()));
        quoteListInfo.setSearchCriteria(quoteList, context);
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
    return ("ListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-view"))) {
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
    String quoteId = context.getRequest().getParameter("quoteId");
    if (quoteId == null || "".equals(quoteId)) {
      quoteId = (String) context.getRequest().getAttribute("quoteId");
    }
    context.getRequest().setAttribute("quoteId", quoteId);
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    Connection db = null;
    Quote quote = null;
    QuoteProductList quoteProducts = null;
    ProductCatalogList productList = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList valuesList = null;
    Organization thisOrganization = null;
    OpportunityHeader header = null;
    Contact thisContact = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (quoteId == null || "".equals(quoteId)) {
        try {
          //check for the product id & ticket id.. is it a request to create a new quote?
          int productId = Integer.parseInt((String) context.getRequest().getParameter("productId"));
          int ticketId = Integer.parseInt((String) context.getRequest().getParameter("ticketId"));
        } catch (Exception e) {
          //if the product id is null, then the incomplete form was submitted, set error message
          context.getRequest().setAttribute(
              "actionError",
              systemStatus.getLabel("object.validation.actionError.invalidCriteria"));
          return "SearchCriteriaError";
        }
        // as product id is not null, create a new quote
        return executeCommandAddQuote(context);
      }
      //build the quote
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      quote.retrieveTicket(db);

      thisOrganization = new Organization(db, quote.getOrgId());
      if (contactId == null) {
        thisContact = new Contact(db, quote.getContactId());
      } else {
        thisContact = new Contact(db, Integer.parseInt(contactId));
      }

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
      if (headerId == -1) {
        context.getRequest().setAttribute("quote", quote);
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
        return "OpenAccountQuotesOK";
      } else {
        header = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunity", header);
      }

      Quote quoteBean = new Quote();
      context.getRequest().setAttribute("quoteBean", quoteBean);

      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      if (quote.getLogoFileId() > 0) {
        FileItem thisItem = new FileItem(db, quote.getLogoFileId(), Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
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
    context.getRequest().setAttribute("ContactDetails", thisContact);
    return ("DetailsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveProduct(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-edit"))) {
      return ("PermissionError");
    }
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
  public String executeCommandSubmit(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-edit"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    boolean foundTicket = false;
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
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-delete"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    int orgId = Integer.parseInt((String) context.getRequest().getParameter("orgId"));
    int headerId = Integer.parseInt((String) context.getRequest().getParameter("headerId"));
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

      //update opportunity related information
      processUpdateHook(context, quote, null);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "AccountContactsOppQuotes.do?command=View&orgId=" + orgId + "&contactId=" + quote.getContactId() + "&headerId=" + quote.getHeaderId());
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-delete"))) {
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
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      //htmlDialog.addMessage("Please review carefully...\n" + dependencies.getHtmlString());
      if (quote.getOrderId(db) != -1) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.deleteRelatedOrdersFirst"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.dependencies"));
        htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountContactsOppQuotes.do?command=Delete&quoteId=" + quote.getId() + "&orgId=" + quote.getOrgId() + "&contactId=" + quote.getContactId() + "&headerId=" + quote.getHeaderId() + "'");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-edit"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    Quote previousQuote = null;
    int recordCount = -1;
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
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
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-edit"))) {
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
    String quoteIdString = (String) context.getRequest().getAttribute("quoteId");
    if (quoteIdString == null || "".equals(quoteIdString)) {
      quoteIdString = (String) context.getRequest().getParameter("quoteId");
    }
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    Quote quote = null;
    Contact thisContact = null;
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quoteBean", quote);

      Organization orgDetails = new Organization(db, quote.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      int headerId = quote.getHeaderId();
      if (headerId != -1) {
        OpportunityHeader opportunity = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunity", opportunity);
      }

      ContactList contactList = new ContactList();
      contactList.setOrgId(quote.getOrgId());
      contactList.setLeadsOnly(Constants.FALSE);
      contactList.setEmployeesOnly(Constants.FALSE);
      contactList.setDefaultContactId(quote.getContactId());
      contactList.buildList(db);
      context.getRequest().setAttribute("contactList", contactList);


      thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);

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
    return "ModifyFormOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-edit"))) {
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
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    int quoteId = -1;
    if (quoteIdString != null && !"".equals(quoteIdString)) {
      quoteId = Integer.parseInt(quoteIdString);
    }
    String contactIdString = context.getRequest().getParameter("contactId");
    if (contactIdString == null) {
      contactIdString = (String) context.getRequest().getAttribute("contactId");
    }
    boolean isValid = false;
    int resultCount = -1;
    Quote quote = null;
    Quote oldQuote = null;
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
      oldQuote = new Quote(db, quoteId);

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
      quote.setLogoFileId(quoteBean.getLogoFileId());
      quote.setModifiedBy(user.getId());
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        resultCount = quote.update(db);
      }
      //Update opportunity related information
      if (resultCount == 1) {
        processUpdateHook(context, oldQuote, quote);
      }
      Organization orgDetails = new Organization(db, orgId);
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      if (quoteBean.getContactId() == -1) {
        contactId = Integer.parseInt(contactIdString);
      }
      Contact thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddQuote(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-add"))) {
      return ("PermissionError");
    }
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

    boolean isValid = false;
    boolean recordInserted = false;
    String ticketIdString = (String) context.getRequest().getParameter("ticketId");
    int ticketId = -1;
    String productIdString = (String) context.getRequest().getParameter("productId");
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

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      //Retrieve the ticket
      if (ticketId != -1) {
        ticket = new Ticket(db, ticketId);
        ProductCatalog product = new ProductCatalog(db, productId);
        quote.setProductId(product.getId());
        quote.setShortDescription(product.getCategoryName() + ", " + product.getName() + ": ");
        //if the ticket is not for a new ad design, retrieve the customer product from the database
        if (!ticket.getProblem().equals("New Ad Design Request") && ticket.getCustomerProductId() != -1) {
          CustomerProduct customerProduct = new CustomerProduct(db, ticket.getCustomerProductId());
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

        Contact thisContact = new Contact(db, contactId);
        context.getRequest().setAttribute("ContactDetails", thisContact);
      } else {
        quote.setStatusId(list.getIdFromValue("Incomplete"));
      }
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
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
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-add"))) {
      return ("PermissionError");
    }
    int orgId = -1;
    String orgIdString = context.getRequest().getParameter("orgId");
    if (orgIdString == null) {
      orgIdString = (String) context.getRequest().getAttribute("orgId");
    }
    if (orgIdString != null && !"".equals(orgIdString)) {
      orgId = Integer.parseInt(orgIdString);
    }
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    Contact thisContact = null;
    String headerIdString = context.getRequest().getParameter("headerId");
    if (headerIdString == null) {
      headerIdString = (String) context.getRequest().getAttribute("headerId");
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
      LookupList list2 = systemStatus.getLookupList(db, "lookup_quote_delivery");
      list2.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      Organization orgDetails = new Organization(db, orgId);
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);

      OpportunityHeader opportunity = null;
      if ((headerIdString != null) && (!"".equals(headerIdString))) {
        opportunity = new OpportunityHeader(db, Integer.parseInt(headerIdString));
        context.getRequest().setAttribute("opportunity", opportunity);
      }

      //Create the list of contacts
      ContactList contactList = new ContactList();
      contactList.setOrgId(orgId);
      contactList.setLeadsOnly(Constants.FALSE);
      contactList.setEmployeesOnly(Constants.FALSE);
      contactList.buildList(db);
      context.getRequest().setAttribute("contactList", contactList);

      //fetch logos
      FileItemList itemList = new FileItemList();
      itemList.setLinkModuleId(Constants.DOCUMENTS_QUOTE_LOGO);
      itemList.setLinkItemId(Constants.QUOTES);
      itemList.buildList(db);
      context.getRequest().setAttribute("fileItemList", itemList);

      if (quote == null) {
        quote = new Quote();
      }
      quote.setOrgId(orgId);
      quote.setContactId(contactId);
      quote.setName(orgDetails.getName());
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      if ((headerIdString != null) && (!"".equals(headerIdString))) {
        quote.setHeaderId(Integer.parseInt(headerIdString));
        quote.setShortDescription(opportunity.getDescription());
      }
      context.getRequest().setAttribute("quoteBean", quote);

    } catch (Exception e) {
      // Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return "AddQuoteFormOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opportunities-quotes-add"))) {
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
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      //Clone a new instance of Quote
      quote = new Quote();
      quote.setStatusId(list.getIdFromValue("Incomplete"));
      quote = oldQuote.addVersion(db, quote);
      quote.queryRecord(db, quote.getId());
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
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
}


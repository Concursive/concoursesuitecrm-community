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
package org.aspcfs.modules.quotes.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

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
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Action Class for handling Quotes
 *
 * @author ananth
 * @version $Id$
 * @created April 20, 2004
 */
public final class Quotes extends CFSModule {

  /**
   * Default method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    return executeCommandSearchForm(context);
  }


  /**
   * This method displays the list of quotes
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Quotes", "View Quote Details");

    //find record permissions for portal users
    /*
     *  if (!isRecordAccessPermitted(context, Integer.parseInt(orgid))) {
     *  return ("PermissionError");
     *  }
     */
    PagedListInfo quoteListInfo = this.getPagedListInfo(
        context, "quoteListInfo");
    quoteListInfo.setLink("Quotes.do?command=View");
    Connection db = null;
    QuoteList quoteList = new QuoteList();
    try {
      db = this.getConnection(context);

      //build the quote list
      quoteList.setPagedListInfo(quoteListInfo);
      quoteList.setIncludeAllSites(false);
      quoteList.setSiteId(this.getUser(context, this.getUserId(context)).getSiteId());
      quoteList.setExclusiveToSite(true);
      quoteList.setBuildResources(true);
      quoteList.buildList(db);

      //retrieve the lookuplist from the SystemStatus
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

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
   * This method displays a search form for searching quotes
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }

    Connection db = null;
    try {
      db = getConnection(context);
      //Account type lookup

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_quote_status");

      statusSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("statusSelect", statusSelect);
//Category lookup
//      LookupList list = new LookupList(db, "lookup_product_category_type");
//      ProductCategoryList categoryList = new ProductCategoryList();
//      categoryList.buildList(db);
//      HtmlSelect select = categoryList.getHtmlSelect(list.getIdFromValue("Publication"));
//      context.getRequest().setAttribute("categorySelect", select);

      //sites lookup
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      context.getRequest().setAttribute("SiteList", siteList);


      //reset the offset and current letter of the paged list in order to make sure we search ALL quotes
      PagedListInfo quoteListInfo = this.getPagedListInfo(
          context, "quoteListInfo");
      quoteListInfo.setCurrentLetter("");
      quoteListInfo.setCurrentOffset(0);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Search Quotes", "Quotes Search");
    return ("SearchOK");
  }


  /**
   * This method displays the list of quotes resulting from the search
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    // See if a valid quoteId was specified
    int quoteId = -1;
    try {
      quoteId = Integer.parseInt(
          context.getRequest().getParameter("searchId"));
    } catch (Exception e) {
    }
    String source = (String) context.getRequest().getParameter("source");
    QuoteList quoteList = new QuoteList();
    addModuleBean(context, "View Quotes", "Search Results");
    User user = this.getUser(context, this.getUserId(context));
    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "quoteListInfo", "group_id", "desc");
    searchListInfo.setLink(
        "Quotes.do?command=Search&version=" + ((version != null) ? version : ""));
    //Need to reset any sub PagedListInfos since this is a new acccount
    Connection db = null;
    try {
      db = this.getConnection(context);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      list.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("quoteStatusList", list);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
      if (isPortalUser(context)) {
        source = "searchForm";
      }

      //Build the quote list
      if (version != null && !"".equals(version)) {
        Quote quote = new Quote(db, Integer.parseInt(version));
        quoteList = new QuoteList();
        quoteList.setBuildCompleteVersionList(true);
        quoteList.setId(quote.getRootQuote(db, quote.getParentId()));
        quoteList.setPagedListInfo(searchListInfo);
        searchListInfo.setSearchCriteria(quoteList, context);
        quoteList.buildList(db);
        context.getRequest().setAttribute("quoteList", quoteList);
        return ("ListOK");
      }
      if (quoteId > -1) {
        quoteList.setId(quoteId);
      }
      quoteList.setPagedListInfo(searchListInfo);
      quoteList.setStatusId(searchListInfo.getFilterKey("listFilter1"));
//      quoteList.setCategoryId(searchListInfo.getFilterKey("listFilter1"));
      searchListInfo.setSearchCriteria(quoteList, context);
      if (isPortalUser(context)) {
        quoteList.setOrgId(getPortalUserPermittedOrgId(context));
      }
      if (quoteList.getSiteId() == Constants.INVALID_SITE) {
        quoteList.setIncludeAllSites(true);
        quoteList.setSiteId(user.getSiteId());
      } else {
        quoteList.setIncludeAllSites(false);
        quoteList.setExclusiveToSite(true);
      }
      quoteList.buildList(db);
      context.getRequest().setAttribute("quoteList", quoteList);
    } catch (Exception e) {
      //Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListOK");
  }


  /**
   * This method displays the quotes details.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    String result = "DetailsOK";
    int quoteId = -1;

    String quoteIdString = (String) context.getRequest().getAttribute(
        "quoteId");
    if (quoteIdString == null || "".equals(quoteIdString)) {
      quoteIdString = (String) context.getRequest().getParameter("quoteId");
    }
    String printQuote = (String) context.getRequest().getParameter("canPrint");
    if (printQuote != null && !"".equals(printQuote)) {
      context.getRequest().setAttribute("canPrint", printQuote);
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Quote quote = null;
    QuoteProductList quoteProducts = null;
    ProductCatalogList productList = null;
    ProductOptionList optionList = null;
    //check if the quote id is null
    if (quoteIdString == null || "".equals(quoteIdString)) {
      try {
        // check for the product id & ticket id.. is it a request to create a new quote?
        // this can be done through the ticket module which generates a quote
        int productId = Integer.parseInt(
            (String) context.getRequest().getParameter("productId"));
        int ticketId = Integer.parseInt(
            (String) context.getRequest().getParameter("ticketId"));
      } catch (Exception e) {
        //if the product id is null, then the incomplete form was submitted, set error message
        context.getRequest().setAttribute(
            "actionError",
            "Invalid criteria, please review and make necessary changes before submitting");
        return "SearchCriteriaError";
      }
      // as product id is not null, create a new quote
      // NOTE: The following method must not be executed from within another
      // action method.  If a database connection is obtained and this method
      // is called, a deadlock will occur.
      return executeCommandAddQuote(context);
    } else {
      try {
        quoteId = Integer.parseInt(quoteIdString);
      } catch (Exception e) {
        //Syntax error in entering the quote id
        context.getRequest().setAttribute(
            "actionError",
            "Invalid criteria, please review and make necessary changes before submitting");
        return "SearchCriteriaError";
      }
    }
    // Note: Do not move this before the previous logic
    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the quote
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      //create an empty product list
      productList = new ProductCatalogList();

      //create the product option list
      optionList = new ProductOptionList();
      optionList.setBuildConfigDetails(true);
      optionList.buildList(db);
      context.getRequest().setAttribute("optionList", optionList);

      // for each quote product, add the related product to the product list
      quoteProducts = quote.getProductList();
      Iterator iterator = quoteProducts.iterator();
      while (iterator.hasNext()) {
        QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
        quoteProduct.setBuildProductOptions(true);
        quoteProduct.queryRecord(db, quoteProduct.getId());
        ProductCatalog product = new ProductCatalog();
        product.setBuildOptions(true);
        product.queryRecord(db, quoteProduct.getProductId());
        productList.add(product);
      }

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

      context.getRequest().setAttribute("quoteProductList", quoteProducts);
      context.getRequest().setAttribute("productList", productList);

      /*
       *  Quote quoteBean = new Quote();
       *  context.getRequest().setAttribute("quoteBean", quoteBean);
       */
      //retrieve the lookuplist from the SystemStatus
      SystemStatus systemStatus = this.getSystemStatus(context);
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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }


  /**
   * This method is used to display the quote to the customer
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCustomerDisplay(ActionContext context) {
    if (!hasPermission(context, "products-view")) {
      return ("PermissionError");
    }
    String result = "CustomerDisplayOK";
    int quoteId = -1;
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    Quote quote = null;
    QuoteProductList quoteProducts = null;
    Quote quoteBean = null;
    ProductCatalogList productList = null;
    ProductOptionList optionList = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

      quoteId = Integer.parseInt(quoteIdString);

      //retrieve the quote from the database
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      //Check user for permissions to access the quote
      if (isPortalUser(context)) {
        User user = getUser(context, getUserId(context));
        int userOrgId = user.getContact().getOrgId();
        if (quote.getOrgId() != userOrgId) {
          Exception error = new Exception("Unauthorized Access");
          context.getRequest().setAttribute("Error", error);
          return "SystemError";
        }
      }

      //build the quote note list
      QuoteNoteList noteList = new QuoteNoteList();
      noteList.setQuoteId(quote.getId());
      noteList.buildList(db);
      context.getRequest().setAttribute("quoteNoteList", noteList);

      //create the quote bean and set the notes
      quoteBean = new Quote();
      context.getRequest().setAttribute("quoteBean", quoteBean);

      productList = new ProductCatalogList();

      optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("optionList", optionList);

      quoteProducts = quote.getProductList();
      Iterator iterator = quoteProducts.iterator();
      while (iterator.hasNext()) {
        QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
        quoteProduct.setBuildProductOptions(true);
        quoteProduct.queryRecord(db, quoteProduct.getId());
        ProductCatalog product = new ProductCatalog();
        product.setBuildOptions(true);
        product.queryRecord(db, quoteProduct.getProductId());
        productList.add(product);
      }

      ProductOptionValuesList values = new ProductOptionValuesList();
      values.buildList(db);
      context.getRequest().setAttribute("productOptionValuesList", values);

      context.getRequest().setAttribute("quoteProductList", quoteProducts);
      context.getRequest().setAttribute("productList", productList);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      context.getRequest().setAttribute("systemStatus", systemStatus);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSubmit(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit"))) {
      return ("PermissionError");
    }
    int quoteId = -1;
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);
      //retrieve the quote from the database
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
   * This method saves the quote notes
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveNotes(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    int quoteId = -1;
    boolean isValid = false;
    int recordCount = -1;
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    QuoteNote quoteNote = null;
    Quote quote = null;
    Quote previousQuote = null;
    Quote notes = (Quote) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);

      //retrieve the quote from the database
      previousQuote = new Quote(db, quoteId);
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);

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
        isValid = this.validateObject(context, db, quote) && isValid;
        if (isValid) {
          recordCount = quote.update(db);
        }
        if (recordCount > 0) {
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
    return "SaveNotesOK";
  }


  /**
   * This method removes the specified quote product from the quote
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveProduct(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    String productName = (String) context.getRequest().getParameter(
        "productId");
    int productId = Integer.parseInt(productName.trim());
    QuoteProduct quoteProduct = null;
    Connection db = null;
    try {
      db = getConnection(context);
      quoteProduct = new QuoteProduct(db, productId);
      Quote previousQuote = new Quote();
      previousQuote.queryRecord(db, quoteProduct.getQuoteId());
      previousQuote.buildProducts(db);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, previousQuote.getOrgId())) {
        return ("PermissionError");
      }
      quoteProduct.delete(db);

      Quote quote = new Quote();
      quote.queryRecord(db, quoteProduct.getQuoteId());
      quote.buildProducts(db);
      processUpdateHook(context, previousQuote, quote);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "RemoveProductOK";
  }


  /**
   * This method deletes the selected quote
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-delete"))) {
      return ("PermissionError");
    }
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
      //retrieve the quote from the database
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      //delete the quote
      quote.delete(db);
      processDeleteHook(context, quote);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "Quotes.do?command=Search");
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-delete"))) {
      return ("PermissionError");
    }
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
      //retrieve the quote from the database
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      //delete the quote
      quote.updateStatus(db, true, this.getUserId(context));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "Quotes.do?command=Search");
    return "DeleteOK";
    //return executeCommandDetails(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestore(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-delete"))) {
      return ("PermissionError");
    }
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

      //retrieve the quote from the database
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      //restore the quote
      quote.updateStatus(db, false, this.getUserId(context));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //context.getRequest().setAttribute("refreshUrl", "Quotes.do?command=Search");
    //return "DeleteOK";
    return executeCommandDetails(context);
  }


  /**
   * This method saves the current entries to a quote
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes_edit"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    int quoteId = Integer.parseInt(
        (String) context.getRequest().getParameter("quoteId"));
    boolean flag = false;
    boolean isValid = false;
    int recordCount = -1;
    Quote quote = null;
    Quote previousQuote = null;
    Connection db = null;
    try {

      db = getConnection(context);
      Quote quoteBean = (Quote) context.getFormBean();

      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      previousQuote = new Quote(db, quoteId);

      //check for any new quote notes
      if (quoteBean.getNotes() != null) {
        if (!quote.getNotes().equals(quoteBean.getNotes())) {
          quote.setNotes(quoteBean.getNotes());
          flag = true;
        }
      }

      //check for an expiration date entry
      if (quoteBean.getExpirationDate() != null) {
        if (quote.getExpirationDate() != quoteBean.getExpirationDate()) {
          quote.setExpirationDate(quoteBean.getExpirationDate());
          flag = true;
        }
      }

      //update the quote only if it has been changed
      if (flag) {
        isValid = this.validateObject(context, db, quote);
        if (isValid) {
          //Check access permission to organization record
          if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
            return ("PermissionError");
          }
          recordCount = quote.update(db);
        }
        if (recordCount > 0) {
          this.processUpdateHook(context, previousQuote, quote);
        }
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
  public String executeCommandConfirmDelete(ActionContext context) {

    if (!(hasPermission(context, "quotes-quotes-delete"))) {
      return ("PermissionError");
    }

    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Connection db = null;
    Quote quote = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String quoteId = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
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
      DependencyList dependencies = quote.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
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
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='Quotes.do?command=Trash&quoteId=" + quote.getId() + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyForm(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit"))) {
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
    User user = this.getUser(context, this.getUserId(context));
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

      ContactList contactList = new ContactList();
      if (quote.getOrgId() != -1) {
        contactList.setOrgId(quote.getOrgId());
      } else if (user.getSiteId() == -1) {
        contactList.setIncludeAllSites(true);
      }
      contactList.setSiteId(user.getSiteId());
      contactList.setExclusiveToSite(true);
      contactList.setBuildDetails(false);
      contactList.setBuildTypes(false);
      contactList.setIncludeEnabled(Constants.UNDEFINED);
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
    return "ModifyFormOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    String returnValue = (String) context.getRequest().getParameter("return");
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
    int resultCount = -1;
    Quote quote = null;
    Quote quoteBean = (Quote) context.getFormBean();
    Quote previousQuote = null;
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

      if (quoteBean.getOrgId() != -1) {
        quote.setOrgId(quoteBean.getOrgId());
      }
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
      quote.setShowTotal(quoteBean.getShowTotal());
      quote.setShowSubtotal(quoteBean.getShowSubtotal());
      quote.setModifiedBy(user.getId());
      isValid = this.validateObject(context, db, quote);
      if (isValid){
        if ("submit".equals(context.getRequest().getParameter("return"))){
          Quote tmpQuote = new Quote();
          tmpQuote.setBuildProducts(true);
          tmpQuote.queryRecord(db, quote.getId());
          isValid = this.validateObject(context, db, tmpQuote.getProductList());
        }
      }
      if (isValid) {
        resultCount = quote.update(db);
      }
      if (resultCount > 0) {
        this.processUpdateHook(context, previousQuote, quote);
      }
    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1 || !isValid) {
      if (returnValue == null || "".equals(returnValue.trim())) {
        return executeCommandModifyForm(context);
      } else if (returnValue.equals("clone")) {
        return executeCommandCloneForm(context);
      } else if (returnValue.equals("submit")) {
        return executeCommandSubmit(context);
      } else if (returnValue.equals("close")) {
        return executeCommandClose(context);
      } else {
        return "UserError";
      }
    }
    return getReturn(context, "Modify");
  }


  /**
   * Adds a feature to the Quote attribute of the Quotes object
   *
   * @param context The feature to be added to the Quote attribute
   * @return Description of the Return Value
   */
  public String executeCommandAddQuote(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-add"))) {
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

    if (ticketIdString != null && !"".equals(ticketIdString)) {
      ticketId = Integer.parseInt(ticketIdString);
    }
    if (productIdString != null && !"".equals(productIdString)) {
      productId = Integer.parseInt(productIdString);
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

      LookupList list2 = systemStatus.getLookupList(
          db, "lookup_quote_delivery");
      context.getRequest().setAttribute("quoteDeliveryList", list2);

      //Retrieve the ticket
      if (ticketId != -1) {
        ticket = new Ticket(db, ticketId);
        ProductCatalog product = new ProductCatalog(db, productId);
        quote.setProductId(product.getId());
        quote.setShortDescription(product.getName() + ": ");
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
      quote.setVersion(quote.getNewVersion());
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        //Check access permission to organization record
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
        quote.createNewGroup(db);
        recordInserted = quote.insert(db);
      }
      if (recordInserted) {
        this.processInsertHook(context, quote);
      }
      String quoteIdString = null;
      if (isValid) {
        quoteIdString = "" + quote.getId();
        context.getRequest().setAttribute("quoteId", quoteIdString);
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
    if (!(hasPermission(context, "quotes-quotes-add"))) {
      return ("PermissionError");
    }
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);

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

      //Create a new instance of Quote
      if (quote == null) {
        quote = new Quote();
      }
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
      //quote.setStatusId(list.getIdFromValue("Incomplete"));

      //Create a list for selection of a logo file
      FileItemList itemList = new FileItemList();
      itemList.setLinkModuleId(Constants.DOCUMENTS_QUOTE_LOGO);
      itemList.setLinkItemId(Constants.QUOTES);
      itemList.buildList(db);
      context.getRequest().setAttribute("fileItemList", itemList);

      int headerId = quote.getHeaderId();
      if (headerId != -1) {
        OpportunityHeader opportunity = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunity", opportunity);
      }
      if (quote.getOrgId() != -1) {
        Organization orgDetails = new Organization(db, quote.getOrgId());
        quote.setName(orgDetails.getName());
        context.getRequest().setAttribute("OrgDetails", orgDetails);
        //Create the list of contacts
        ContactList contactList = new ContactList();
        contactList.setOrgId(quote.getOrgId());
        contactList.setLeadsOnly(Constants.FALSE);
        contactList.setEmployeesOnly(Constants.FALSE);
        contactList.buildList(db);
        context.getRequest().setAttribute("contactList", contactList);
      }
      context.getRequest().setAttribute("quoteBean", quote);
      context.getRequest().setAttribute("systemStatus", systemStatus);
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
   * This method decides the workflow after the user's decision on the quote
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCustomerQuoteDecision(ActionContext context) {
    if (!hasPermission(context, "products-view")) {
      return ("PermissionError");
    }
    QuoteNote quoteNote = null;
    Connection db = null;
    boolean isValid = false;
    int recordCount = -1;
    Quote previousQuote = null;
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    String value = (String) context.getRequest().getParameter("value");
    User user = getUser(context, getUserId(context));
    int userOrgId = user.getContact().getOrgId();
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");

      Quote quoteBean = (Quote) context.getFormBean();
      Timestamp currentTimestamp = new Timestamp(
          Calendar.getInstance().getTimeInMillis());
      Quote quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteIdString));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      previousQuote = new Quote(db, Integer.parseInt(quoteIdString));

      //check the user for the necessary permissions
      if (isPortalUser(context)) {
        if (quote.getOrgId() != userOrgId) {
          Exception error = new Exception("Unauthorized Access");
          context.getRequest().setAttribute("Error", error);
          return "SystemError";
        }
      }
      //check for any new notes from the user
      if (quoteBean.getNotes() != null && !"".equals(
          quoteBean.getNotes().trim())) {
        quoteNote = new QuoteNote();
        quoteNote.setQuoteId(Integer.parseInt(quoteIdString));
        quoteNote.setNotes(quoteBean.getNotes());
        quoteNote.setModifiedBy(user.getId());
        quoteNote.setEnteredBy(user.getId());
        isValid = this.validateObject(context, db, quoteNote);
        if (isValid) {
          quoteNote.insert(db);
        }
      }

      //if the user rejects the quote, set the quote status details
      if (value.equals("REJECT") || value.equals("NOTES")) {
        if (value.equals("REJECT")) {
          quote.setStatusId(list.getIdFromValue("Rejected by customer"));
        }
        if (quoteNote != null) {
          processInsertHook(context, quoteNote);
        }
      }
      quote.setStatusDate(currentTimestamp);

      //update the quote
      isValid = this.validateObject(context, db, quote) && isValid;
      if (isValid) {
        recordCount = quote.update(db);
      }
      if (recordCount > 0) {
        this.processUpdateHook(context, previousQuote, quote);
      }
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteIdString));
      context.getRequest().setAttribute("quote", quote);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (value.equals("REJECT") || value.equals("NOTES") || !isValid) {
      return "QuoteRejectedOK";
    }
    return "CustomerQuoteDecisionOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandOrganizationJSList(ActionContext context) {
    Connection db = null;
    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      ContactList contactList = new ContactList();
      if (orgId != null && !"-1".equals(orgId)) {
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setLeadsOnly(Constants.FALSE);
        contactList.setEmployeesOnly(Constants.FALSE);
        contactList.setOrgId(Integer.parseInt(orgId));
        contactList.buildList(db);
      }
      context.getRequest().setAttribute("ContactList", contactList);
    } catch (Exception errorMessage) {

    } finally {
      this.freeConnection(context, db);
    }
    return "OrganizationJSListOK";
  }


  /**
   * Build the quote to be cloned and set it to the clone form.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCloneForm(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-add") || hasPermission(
        context, "accounts-quotes-add")
        || hasPermission(context, "leads-opportunities-add"))) {
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
    Quote oldQuote = null;
    User user = this.getUser(context, this.getUserId(context));
    ContactList contactList = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      oldQuote = new Quote();
      oldQuote.setBuildProducts(true);
      oldQuote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, oldQuote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", oldQuote);

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      contactList = new ContactList();
      if (oldQuote.getOrgId() != -1) {
        contactList.setOrgId(oldQuote.getOrgId());
      } else if (user.getSiteId() == -1) {
        contactList.setIncludeAllSites(true);
      }
      contactList.setSiteId(user.getSiteId());
      contactList.setExclusiveToSite(true);
      contactList.setBuildDetails(false);
      contactList.setBuildTypes(false);
      contactList.buildList(db);
      context.getRequest().setAttribute("contactList", contactList);
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
    return "CloneFormOK";
  }


  /**
   * Clone the quote and return to the requested details page
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-add") || hasPermission(
        context, "accounts-quotes-add")
        || hasPermission(context, "leads-opportunities-add"))) {
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
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String returnValue = (String) context.getRequest().getParameter("return");

    Quote quote = (Quote) context.getFormBean();
    Quote oldQuote = null;
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;

    try {
      db = this.getConnection(context);
      //Build the old quote
      oldQuote = new Quote();
      oldQuote.setBuildProducts(true);
      oldQuote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, oldQuote.getOrgId())) {
        return ("PermissionError");
      }

      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

      //Clone a new instance of Quote
      quote.setId(-1);
      quote.setCanNotCopyExpirationDate(true);
      quote.setEnteredBy(user.getId());
      quote.setModifiedBy(user.getId());
      quote = oldQuote.clone(db, quote);
      this.processInsertHook(context, quote);
      if (returnValue != null && !"".equals(returnValue)) {
        if (returnValue.equals("clone")) {
          context.getRequest().setAttribute(
              "id", "" + quote.getId() + "&orgId=" + quote.getOrgId());
        } else if (returnValue.equals("old")) {
          context.getRequest().setAttribute(
              "id", "" + oldQuote.getId() + "&version=" + (version != null ? version : "") + "&orgId=" + oldQuote.getOrgId());
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
    return "CloneOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-add"))) {
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
      quote = new Quote(db, quote.getId());
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
  public String executeCommandDetailsByGroup(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
      return ("PermissionError");
    }
    int quoteId = -1;
    int groupId = -1;
    String groupIdString = (String) context.getRequest().getParameter(
        "quoteId");
    try {
      groupId = Integer.parseInt(groupIdString);
      if (groupId <= 0) {
        throw new Exception("Invalid number selection");
      }
    } catch (Exception e) {
      //Syntax error in entering the quote id
      context.getRequest().setAttribute("groupIdError", "Invalid quote ID");
      context.getRequest().setAttribute(
          "actionError",
          "Invalid criteria, please review and make necessary changes before submitting");
      return "SearchCriteriaError";
    }
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      //check if the quote id is null
      QuoteList quotes = new QuoteList();
      quotes.setGroupId(groupIdString);
      quotes.setTopOnly(Constants.TRUE);
      quotes.buildList(db);
      if (quotes.size() == 0) {
        context.getRequest().setAttribute(
            "actionError",
            "No quotes with the given Id could be found.");
        return "SearchCriteriaError";
      } else {
        quoteId = ((Quote) quotes.get(0)).getId();
      }
      String quoteIdString = "" + quoteId;
      context.getRequest().setAttribute("quoteId", quoteIdString);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute(
          "actionError",
          "The specified quote could not be found");
      return "SearchCriteriaError";
    } finally {
      this.freeConnection(context, db);
    }
    return "GroupOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!(hasPermission(context, "quotes-view"))) {
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
      quote.setBuildProducts(true);
      quote.setBuildResources(true);
      quote.setBuildConditions(true);
      quote.setSystemStatus(systemStatus);
      quote.setBuildRemarks(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);
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
    return ("ViewHistoryOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClose(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Connection db = null;
    Quote quote = null;
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    try {
      db = this.getConnection(context);
      quote = new Quote();
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Quotes", "View Quote Details");
    return ("CloseFormOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEmail(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String version = (String) context.getRequest().getParameter("version");
    if (version != null && !"".equals(version)) {
      context.getRequest().setAttribute("version", version);
    }
    Connection db = null;
    Quote quote = null;
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    User user = this.getUser(context, this.getUserId(context));
    Contact userContact = user.getContact();
    try {
      db = this.getConnection(context);
      quote = new Quote();
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);
      //Build the user's contact information
      userContact.setBuildDetails(true);
      userContact.queryRecord(db, userContact.getId());
      context.getRequest().setAttribute("userContact", userContact);
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Quotes", "View Quote Details");
    return "EmailFormOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrintQuote(ActionContext context) {
    if (!(hasPermission(context, "quotes-view") || hasPermission(
        context, "accounts-quotes-view")
        || hasPermission(context, "leads-opportunities-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String id = (String) context.getRequest().getParameter("id");
      Quote quote = new Quote(db, Integer.parseInt(id));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }

      HashMap map = new HashMap();
      map.put("quote_id", new Integer(id));
      String reportPath = getWebInfPath(context, "reports");
      map.put("path", reportPath);
      String displayTotal = (String) context.getRequest().getParameter(
          "display");
      map.put("displaytotal", new Boolean(displayTotal));
      String displaySubTotal = (String) context.getRequest().getParameter(
          "subTotal");
      map.put("displaysubtotal", new Boolean(displaySubTotal));
      if (quote.getLogoFileId() > 0) {
        FileItem thisItem = new FileItem(
            db, quote.getLogoFileId(), Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
        String logoFilePath = this.getPath(context, "quotes") + getDatePath(
            thisItem.getModified()) + thisItem.getFilename();
        map.put("logopath", logoFilePath);
      }
      //provide the language, currency and country information
      map.put(
          "language", this.getSystemStatus(context).getLanguage());
      map.put(
          "currency", this.getSystemStatus(context).getApplicationPrefs().get(
              "SYSTEM.CURRENCY"));
      map.put(
          "country", this.getSystemStatus(context).getApplicationPrefs().get(
              "SYSTEM.COUNTRY"));
      //provide the dictionary as a parameter to the quote report
      map.put(
          "CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
      String filename = "quote.xml";

      //provide a seperate database connection for the subreports
      Connection scriptdb = this.getConnection(context);
      map.put("SCRIPT_DB_CONNECTION", scriptdb);

      //Replace the font based on the system language to support i18n chars
      String fontPath = getWebInfPath(context, "fonts");
      String reportDir = getWebInfPath(context, "reports");
      JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
      String language = getPref(context, "SYSTEM.LANGUAGE");

      JasperReportUtils.modifyFontProperties(
          jasperReport, reportDir, fontPath, language);

      byte[] bytes = JasperRunManager.runReportToPdf(
        jasperReport, map, db);

      if (bytes != null) {
        FileDownload fileDownload = new FileDownload();
        fileDownload.setDisplayName(
            "Quote_" + quote.getGroupId() + "v" + quote.getVersionNumber() + ".pdf");
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
    return ("--none--");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendEmail(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    HashMap errors = new HashMap();
    String fromEmailAddress = null;
    String emailToAddress = null;
    String subject = null;
    String emailMeString = null;
    boolean emailMe = false;
    String body = null;
    HashMap sentEmailAddresses = new HashMap();
    String displayGrandTotal = (String) context.getRequest().getParameter(
        "displayGrandTotal");
    String displaySubTotal = (String) context.getRequest().getParameter(
        "displaySubTotal");
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    emailToAddress = (String) context.getRequest().getParameter(
        "emailToAddress");
    if (emailToAddress == null || "".equals(emailToAddress.trim())) {
      errors.put(
          "emailToAddressError", systemStatus.getLabel(
              "object.validation.required"));
    } else {
      sentEmailAddresses.put("to", emailToAddress);
    }
    fromEmailAddress = (String) context.getRequest().getParameter(
        "fromEmailAddress");
    if (fromEmailAddress == null || "".equals(fromEmailAddress.trim())) {
      fromEmailAddress = getPref(context, "EMAILADDRESS");
      if (fromEmailAddress == null || "".equals(fromEmailAddress.trim())) {
        errors.put(
            "fromEmailAddressError", systemStatus.getLabel(
                "object.validation.required"));
      }
    }
    subject = context.getRequest().getParameter("subject");
    if (subject == null || "".equals(subject.trim())) {
      errors.put(
          "subjectError", systemStatus.getLabel("object.validation.required"));
    }
    body = context.getRequest().getParameter("body");
    if (body == null || "".equals(body.trim())) {
      errors.put(
          "bodyError", systemStatus.getLabel("object.validation.required"));
    }
    emailMeString = context.getRequest().getParameter("emailMe");
    if (emailMeString != null && !"".equals(emailMeString)) {
      emailMe = new Boolean(emailMeString).booleanValue();
      sentEmailAddresses.put("from", fromEmailAddress);
    }
    if (sentEmailAddresses.size() > 0) {
      context.getRequest().setAttribute(
          "sentEmailAddresses", sentEmailAddresses);
    }
    User user = this.getUser(context, this.getUserId(context));
    Contact userContact = user.getContact();
    try {
      db = this.getConnection(context);
      //Build the quote.
      quote = new Quote();
      quote.setBuildResources(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("quote", quote);
      //Retrieve the lookup list for the quote status
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
      //Build the user's contact information
      userContact.setBuildDetails(true);
      userContact.queryRecord(db, userContact.getId());
      context.getRequest().setAttribute("userContact", userContact);

      //Create the file to be emailed.
      HashMap map = new HashMap();
      map.put("quote_id", new Integer(quoteId));
      String reportPath = getWebInfPath(context, "reports");
      map.put("path", reportPath);
      if (displayGrandTotal != null && !"".equals(displayGrandTotal)) {
        map.put("displaytotal", new Boolean(displayGrandTotal));
      } else {
        map.put("displaytotal", new Boolean("false"));
      }
      if (displaySubTotal != null && !"".equals(displaySubTotal)) {
        map.put("displaysubtotal", new Boolean(displaySubTotal));
      } else {
        map.put("displaysubtotal", new Boolean("false"));
      }

      if (quote.getLogoFileId() > 0) {
        FileItem thisItem = new FileItem(
            db, quote.getLogoFileId(), Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
        String logoFilePath = this.getPath(context, "quotes") + getDatePath(
            thisItem.getModified()) + thisItem.getFilename();
        map.put("logopath", logoFilePath);
      }
      //provide the language, currency and country information
      map.put(
          "language", this.getSystemStatus(context).getLanguage());
      map.put(
          "currency", this.getSystemStatus(context).getApplicationPrefs().get(
              "SYSTEM.CURRENCY"));
      map.put(
          "country", this.getSystemStatus(context).getApplicationPrefs().get(
              "SYSTEM.COUNTRY"));
      //provide the dictionary as a parameter to the quote report
      map.put(
          "CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
      //provide a seperate database connection for the subreports
      Connection scriptdb = this.getConnection(context);
      map.put("SCRIPT_DB_CONNECTION", scriptdb);

      String filename = "quote.xml";

      //Replace the font based on the system language to support i18n chars
      String fontPath = getWebInfPath(context, "fonts");
      JasperReport jasperReport = JasperReportUtils.getReport(reportPath + filename);
      String language = getPref(context, "SYSTEM.LANGUAGE");

      JasperReportUtils.modifyFontProperties(jasperReport, reportPath, fontPath, language);

      byte[] attachment = JasperRunManager.runReportToPdf(jasperReport, map, db);

      //Send the email
      if (errors.size() == 0) {
        SMTPMessage mail = new SMTPMessage();
        mail.setHost(getPref(context, "MAILSERVER"));
        mail.setFrom(fromEmailAddress);
        mail.setTo(emailToAddress);
        if (emailMe) {
          mail.setBcc(fromEmailAddress);
        }
        mail.setType("text/plain");
        mail.setSubject(subject);
        mail.addByteArrayAttachment(
            "Quote_" + quote.getGroupId() + "v" + quote.getVersionNumber() + ".pdf", attachment, "application/pdf");
        mail.setBody(body + "\r\n");
        if (mail.send() == 2) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "Quotes-> Send error: " + mail.getErrorMsg() + "<br><br>");
          }
          System.err.println(mail.getErrorMsg());
          errors.put(
              "actionError", systemStatus.getLabel("mail.quoteErrorMessage") + mail.getErrorMsg());
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (errors.size() > 0) {
      processErrors(context, errors);
      return "EmailFormOK";
    }
    return "EmailSentOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandChangeShowTotal(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Quote quote = null;
    Quote previousQuote = null;
    int recordCount = -1;
    boolean isValid = false;
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String showTotal = (String) context.getRequest().getParameter("showTotal");
    try {
      db = this.getConnection(context);
      quote = new Quote();
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      previousQuote = new Quote(db, Integer.parseInt(quoteId));
      boolean canShowTotal = new Boolean(showTotal).booleanValue();
      quote.setShowTotal(canShowTotal);
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        recordCount = quote.update(db);
      }
      if (recordCount > 0) {
        this.processUpdateHook(context, previousQuote, quote);
      }
      context.getRequest().setAttribute(
          "quoteShowTotal", "" + quote.getShowTotal());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SetShowTotalOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandChangeShowSubtotal(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Quote quote = null;
    Quote previousQuote = null;
    int recordCount = -1;
    boolean isValid = false;
    String quoteId = (String) context.getRequest().getParameter("quoteId");
    String showSubtotal = (String) context.getRequest().getParameter(
        "showSubtotal");
    try {
      db = this.getConnection(context);
      quote = new Quote();
      quote.queryRecord(db, Integer.parseInt(quoteId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      previousQuote = new Quote(db, Integer.parseInt(quoteId));
      boolean canShowSubtotal = new Boolean(showSubtotal).booleanValue();
      quote.setShowSubtotal(canShowSubtotal);
      isValid = this.validateObject(context, db, quote);
      if (isValid) {
        recordCount = quote.update(db);
      }
      if (recordCount > 0) {
        this.processUpdateHook(context, previousQuote, quote);
      }
      context.getRequest().setAttribute(
          "quoteShowSubtotal", "" + quote.getShowSubtotal());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SetShowSubtotalOK";
  }
}


package org.aspcfs.modules.quotes.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.orders.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.quotes.beans.*;
/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    April 20, 2004
 *@version    $Id$
 */
public final class Quotes extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandSearchForm(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "accounts-accounts-quotes-view"))) {
     *  return ("PermissionError");
     *  }
     */
    addModuleBean(context, "View Quotes", "View Quote Details");
    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }

    System.out.println("orgid : " + orgid);
    //find record permissions for portal users
    /*
     *  if (!isRecordAccessPermitted(context, Integer.parseInt(orgid))) {
     *  return ("PermissionError");
     *  }
     */
    PagedListInfo quoteListInfo = this.getPagedListInfo(context, "QuoteListInfo");
    quoteListInfo.setLink("Quotes.do?command=View&orgId=" + orgid);
    Connection db = null;
    QuoteList quoteList = new QuoteList();
    Organization thisOrganization = null;
    this.resetPagedListInfo(context);
    try {
      db = this.getConnection(context);
      quoteList.setPagedListInfo(quoteListInfo);
      quoteList.setOrgId(Integer.parseInt(orgid));
      //quoteList.setBuildDetails(true);
      //quoteList.setBuildTypes(false);
      System.out.println("building quote list");
      quoteList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
    } catch (Exception errorMessage) {
      errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("QuoteList", quoteList);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return ("ListOK");
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    /*
     *  TODO: uncomment this code when the permission is created
     *  if (!(hasPermission(context, "quotes-quotes-view"))) {
     *  return ("PermissionError");
     *  }
     */
    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }

    Connection db = null;
    try {
      db = getConnection(context);
      //Account type lookup
      LookupList typeSelect = new LookupList(db, "lookup_quote_type");
      typeSelect.addItem(0, "All Types");
      context.getRequest().setAttribute("typeSelect", typeSelect);
      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
      PagedListInfo quoteListInfo = this.getPagedListInfo(context, "searchQuoteListInfo");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    /*
     *  if (!hasPermission(context, "quotes-quotes-view")) {
     *  return ("PermissionError");
     *  }
     */
    String source = (String) context.getRequest().getParameter("source");
    QuoteList quoteList = new QuoteList();
    addModuleBean(context, "View Quotes", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(context, "searchQuoteListInfo");
    searchListInfo.setLink("Quotes.do?command=Search");
    //Need to reset any sub PagedListInfos since this is a new acccount
    //TODO: uncomment this
    //this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
      if (isPortalUser(context)) {
        source = "searchForm";
      }
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }

      //Build the quote list
      quoteList.setPagedListInfo(searchListInfo);
      quoteList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
      searchListInfo.setSearchCriteria(quoteList,UserUtils.getUserLocale(context.getRequest()));
      /*
       *  if ("my".equals(searchListInfo.getListView())) {
       *  quoteList.setOwnerId(this.getUserId(context));
       *  }
       */
      /*
       *  if ("disabled".equals(searchListInfo.getListView())) {
       *  quoteList.setIncludeEnabled(0);
       *  }
       *  if ("all".equals(searchListInfo.getListView())) {
       *  quoteList.setIncludeEnabled(-1);
       *  }
       */
      if (isPortalUser(context)) {
        quoteList.setOrgId(getPortalUserPermittedOrgId(context));
      }
      quoteList.buildList(db);
      context.getRequest().setAttribute("quoteList", quoteList);
      return ("ListOK");
    } catch (Exception e) {
      //Go through the SystemError process
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
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
  public String executeCommandList(ActionContext context) {
    User user = this.getUser(context, this.getUserId(context));
    int orgId = user.getContact().getOrgId();

    Connection db = null;
    QuoteList quoteList = new QuoteList();
    try {
      db = this.getConnection(context);
      quoteList.buildList(db);
      context.getRequest().setAttribute("quoteList", quoteList);

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
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return "ListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDisplay(ActionContext context) {
    String result = "DisplayOK";
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (quoteIdString == null || "".equals(quoteIdString)) {
        quoteId = addQuote(context);
      } else {
        quoteId = Integer.parseInt(quoteIdString);
      }
//      System.out.println("Quote Id in the Display method is -> "+quoteId);
      quote = new Quote(db, quoteId);
      quote.buildProducts(db);
      System.out.println("This is the quote-productList size in Display -> " + quote.getProductList().size());
      quote.retrieveTicket(db);
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
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCustomerDisplay(ActionContext context) {
    String result = "CustomerDisplayOK";
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (quoteIdString == null || "".equals(quoteIdString)) {
        quoteId = addQuote(context);
      } else {
        quoteId = Integer.parseInt(quoteIdString);
      }
//      System.out.println("Quote Id in the Display method is -> "+quoteId);
      quote = new Quote(db, quoteId);
      quote.buildProducts(db);
      System.out.println("This is the quote-productList size in Display -> " + quote.getProductList().size());
      quote.retrieveTicket(db);
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
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSubmit(ActionContext context) {
    String result = "ListOK";
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);
//      System.out.println("Quote Id in the Display method is -> "+quoteId);
      quote = new Quote(db, quoteId);
      quote.buildProducts(db);
      quote.setModifiedBy(this.getUserId(context));

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");

      quote.setStatusId(list.getIdFromValue("Pending customer acceptance"));
      System.out.println("The new status ID is " + list.getIdFromValue("Pending customer acceptance"));
      quote.update(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandList(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
/*  public String executeCommandSaveNotes(ActionContext context) {
    int quoteId = -1;
    String quoteIdString = (String) context.getRequest().getParameter("quoteId");
    String customerString = (String) context.getRequest().getParameter("customer");
    User user = this.getUser(context, this.getUserId(context));
    int orgId = user.getContact().getOrgId();
    Quote quote = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      quoteId = Integer.parseInt(quoteIdString);
      System.out.println("Quote Id in the SaveNotes method is -> " + quoteId);
      quote = new Quote(db, quoteId);
      quote.buildProducts(db);
      quote.setModifiedBy(this.getUserId(context));

      QuoteNotesBean notes = (QuoteNotesBean) context.getFormBean();
      java.util.Date currentTime = Calendar.getInstance().getTime();

      StringBuffer oldNotes = new StringBuffer("");
      oldNotes.append(quote.getNotes() + "\r\n\r\n");
      oldNotes.append("On ->" + currentTime.toString() + " Modified By ->" + user.getContact().getNameFirstLast() + "\r\n");
      oldNotes.append(notes.getNotes());
      quote.setNotes(oldNotes.toString());
      System.out.println("This is the new quote Notes in the SaveNotes action method \n" + oldNotes.toString());
      quote.update(db);
      if ("yes".equals(customerString)) {
        return executeCommandCustomerDisplay(context);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandDisplay(context);
  }
*/

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddProduct(ActionContext context) {
    String quoteName = (String) context.getRequest().getParameter("quoteId");
    String productName = (String) context.getRequest().getParameter("productId");
    String qtyName = (String) context.getRequest().getParameter("qty");
//    System.out.println("The quote Id is -> "+quoteName);
//    System.out.println("The productId is -> "+productName);
    int quoteId = Integer.parseInt(quoteName);
    int productId = Integer.parseInt(productName);
    int qty = Integer.parseInt(qtyName);
//    System.out.println("The quote Id in AddProduct is -> "+quoteId);
//    System.out.println("The productId in AddProduct is -> "+productId);
    Quote quote = null;
    QuoteProduct quoteProduct = null;
    ProductCatalog product = null;
    Connection db = null;
    try {
      db = getConnection(context);

      product = new ProductCatalog(db, productId);

      quoteProduct = new QuoteProduct();
      quoteProduct.setQuoteId(quoteId);
      quoteProduct.setProductId(productId);
      quoteProduct.setQuantity(qty);
      quoteProduct.setPriceAmount(product.getPriceAmount());
//      quoteProduct.setRecurringAmount(0.0);
      quoteProduct.setTotalPrice(product.getPriceAmount());
      quoteProduct.insert(db);

      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
//      System.out.println("This is the size of productList in Add Product -> "+quote.getProductList().size());
      quote.retrieveTicket(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandDisplay(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRemoveProduct(ActionContext context) {
    String quoteName = (String) context.getRequest().getParameter("quoteId");
    String productName = (String) context.getRequest().getParameter("productId");
    int quoteId = Integer.parseInt(quoteName);
    int productId = Integer.parseInt(productName);
//    System.out.println("The quote Id in RemoveProduct is -> "+quoteId);
//    System.out.println("The productId in RemoveProduct is -> "+productId);
    Quote quote = null;
    QuoteProduct quoteProduct = null;
    ProductCatalog product = null;
    Connection db = null;
    try {
      db = getConnection(context);

      quoteProduct = new QuoteProduct(db, productId);
      quoteProduct.delete(db);

      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);

//      System.out.println("This is the size of productList in Add Product -> "+quote.getProductList().size());
      quote.retrieveTicket(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandDisplay(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);

      quote = new Quote(db, quoteId);
      quote.delete(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandList(context);
  }

  
  /**
   *  Adds a feature to the Quote attribute of the Quotes object
   *
   *@param  context  The feature to be added to the Quote attribute
   *@return          Description of the Return Value
   */
  public int addQuote(ActionContext context) {
    int result = -1;
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    StringBuffer quoteDetails = new StringBuffer("");
    int ticketId = Integer.parseInt((String) context.getRequest().getParameter("ticketId"));
    int productId = Integer.parseInt((String) context.getRequest().getParameter("productId"));
    int moduleId = Constants.FOLDERS_TICKETS;
//    System.out.println("After the id are deciphered -> "+ticketId +" & adId -> "+productId);
    String categoryName = new String("New Ad Design Request Questionnaire");
    Quote quote = null;
    Ticket ticket = null;
    CustomFieldRecord record = null;
    CustomFieldCategory questionnaire = null;
    User user = this.getUser(context, this.getUserId(context));
    int orgId = user.getContact().getOrgId();
    Connection db = null;

    try {
      db = this.getConnection(context);
      //Retrieve the ticket
      ticket = new Ticket(db, ticketId);
      if(!ticket.getProblem().equals("New Ad Design Request") && ticket.getCustomerProductId() != -1){
        CustomerProduct customerProduct = new CustomerProduct(db, ticket.getCustomerProductId());
        ProductCatalog product = new ProductCatalog(db, productId);
        quoteDetails.append("Ad ID selected : "+customerProduct.getId()+"\r\n");
        quoteDetails.append("Ad Description : "+customerProduct.getDescription()+"\r\n");
        quoteDetails.append("Product ID selected : "+product.getId()+"\r\n");
        quoteDetails.append("Product Dimensions : "+product.getShortDescription()+"\r\n\r\n");
        quoteDetails.append(ticket.getProblem()+"\r\n");
      }
      //Retrieve the lookup list for the quote status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");

      //Create a new instance of Quote
      quote = new Quote();
      quote.setOrgId(ticket.getOrgId());
      System.out.println("This is the quote Id "+quote.getQuoteId());
      quote.setContactId(ticket.getContactId());
      System.out.println("This is the contactId "+quote.getContactId());
      quote.setEnteredBy(ticket.getEnteredBy());
      quote.setModifiedBy(user.getId());

//      System.out.println("After the ticket has been created "+ticket.getId());
//      context.getRequest().setAttribute("ticket", ticket);
      // Retrieve the folder data and convert it to a StringBuffer
      if(ticket.getProblem().equals("New Ad Design Request")){
        int categoryId = CustomFieldCategory.getIdFromName(db, moduleId, categoryName);
        CustomFieldRecordList recordList = new CustomFieldRecordList();
        recordList.setLinkModuleId(Constants.FOLDERS_TICKETS);
        recordList.setLinkItemId(ticketId);
        recordList.setCategoryId(categoryId);
        recordList.buildList(db);
        System.out.println("After the record list has been created with size" + recordList.size());
        if (recordList.size() > 0) {
          record = (CustomFieldRecord) recordList.get(0);
  //        System.out.println("Inside the if statement... checking the records..--> record ID ->"+record.getId());
          // Query the field description and resulting data
          questionnaire = new CustomFieldCategory(db, categoryId);
          questionnaire.setLinkModuleId(Constants.FOLDERS_TICKETS);
          questionnaire.setLinkItemId(ticket.getId());
          questionnaire.setRecordId(record.getId());
          questionnaire.setIncludeEnabled(Constants.TRUE);
          questionnaire.setBuildResources(true);
          questionnaire.buildResources(db);
          //System.out.println("The category is "+questionnaire);
          System.out.println("The questionnaire has been found ..." + questionnaire.getId());
        }
        Iterator groups = questionnaire.iterator();
        while (groups.hasNext()) {
          System.out.println("Inside the while loop for groups " + groups.hasNext());
          CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
          if (!thisGroup.getName().equals("Questionnaire")) {
            quoteDetails.append("\r\n" + thisGroup.getName() + "\r\n");
            System.out.println("The questionnaire group " + thisGroup.getId());
          } else {
            quoteDetails.append("\r\n");
          }
          Iterator fields = thisGroup.iterator();
          while (fields.hasNext()) {
            CustomField thisField = (CustomField) fields.next();
            System.out.println("Inside the while loop for fields.." + thisField.getId());
            if (thisField.getType() == CustomField.CHECKBOX) {
              if (thisField.getValueHtml().equals("Yes")) {
                System.out.println("Name-> " + thisField.getNameHtml() + "\n" + " Value-> " + thisField.getEnteredValue());
                quoteDetails.append("Your Selection -> " + thisField.getNameHtml() + "\r\n");
                /*
                 *  if(!thisField.getAdditionalText().equals("") || thisField.getAdditionalText() != null){
                 *  System.out.println("The additional text is "+thisField.getAdditionalText());
                 *  quoteDetails.append(thisField.getAdditionalText()+"\r\n");
                 *  }
                 */
              }
            } else {
              if (thisField.getEnteredValue() != null && !"".equals(thisField.getEnteredValue())) {
                System.out.println("Name-> " + thisField.getNameHtml() + "\n" + " Value-> " + thisField.getEnteredValue());
                if (thisField.getNameHtml().equals("Headline/Sale/Unique Selling Feature")) {
                  quote.setName(thisField.getEnteredValue() + "\r\n");
                  quoteDetails.append(thisField.getEnteredValue() + "\r\n");
                } else {
                  quoteDetails.append(thisField.getNameHtml() + "\r\n");
                  quoteDetails.append(thisField.getEnteredValue() + "\r\n");
                  if (thisField.getAdditionalText() != null && !"".equals(thisField.getAdditionalText())) {
                    quoteDetails.append(thisField.getAdditionalText() + "\r\n");
                  }
                }
              }
            }
          }
        }
      }
      System.out.println("This is the final quoteDetails \n" + quoteDetails.toString());
      quote.setNotes(quoteDetails.toString());
      quote.setTicketId(ticket.getId());
      quote.setStatusId(list.getIdFromValue("Incomplete"));
      quote.insert(db);
      result = quote.getId();
    } catch (Exception e) {
      // Go through the SystemError process
      e.printStackTrace();
    } finally {
//      this.freeConnection(context, db);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "ContactListInfo");
    this.deletePagedListInfo(context, "AccountFolderInfo");
    this.deletePagedListInfo(context, "AccountTicketInfo");
    this.deletePagedListInfo(context, "AccountDocumentInfo");
    this.deletePagedListInfo(context, "OrderListInfo");
  }
}


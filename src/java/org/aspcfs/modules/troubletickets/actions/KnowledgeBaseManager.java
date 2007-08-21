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
package org.aspcfs.modules.troubletickets.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.*;
import com.isavvix.tools.*;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.troubletickets.base.KnowledgeBase;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Calendar;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    October 6, 2005
 * @version    $Id$
 */
public final class KnowledgeBaseManager extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "tickets-knowledge-base-view"))) {
      return ("DefaultError");
    }
    return (this.executeCommandSearch(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!(hasPermission(context, "tickets-knowledge-base-view"))) {
      return ("PermissionError");
    }
    String popupString = context.getRequest().getParameter("popup");
    boolean popup = (popupString != null && !"".equals(popupString.trim()));
    PagedListInfo kbListInfo = this.getPagedListInfo(context, (popup?"kbListInfoPopup":"kbListInfo"));
    kbListInfo.setLink("KnowledgeBaseManager.do?command=Search"+(popup?"&popup=true":""));
    KnowledgeBaseList kbList = new KnowledgeBaseList();
    String siteId = kbListInfo.getSearchOptionValue("searchcodeSiteId");
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (siteId == null || "".equals(siteId)) {
        siteId = String.valueOf(user.getSiteId());
      } else {
        int siteIdInt = Integer.parseInt(siteId);
        if (user.getSiteId() != -1 && siteIdInt != user.getSiteId()) {
          return "PermissionError";
        }
      }
      context.getRequest().setAttribute("siteId", siteId);
      kbList.setBuildResources(true);
      kbList.setPagedListInfo(kbListInfo);
      kbListInfo.setSearchCriteria(kbList, context);
      if (kbList.getCatCode() == 0 && kbList.getSubCat1() == 0 && kbList.getSubCat2() == 0 && kbList.getSubCat3() == 0) {
        kbList.setCategoryId(0);
      }
      kbList.buildList(db);
      context.getRequest().setAttribute("kbList", kbList);

      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);

      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setSiteId(siteId);
      categoryList.setExclusiveToSite(true);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("CategoryList", categoryList);

      TicketCategoryList subList1 = new TicketCategoryList();
      subList1.setCatLevel(1);
      subList1.setParentCode(kbList.getCatCode());
      subList1.setSiteId(siteId);
      subList1.setExclusiveToSite(true);
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList1", subList1);

      TicketCategoryList subList2 = new TicketCategoryList();
      subList2.setCatLevel(2);
      subList2.setParentCode(kbList.getSubCat1());
      subList2.setSiteId(siteId);
      subList2.setExclusiveToSite(true);
      subList2.getCatListSelect().setDefaultKey(kbList.getSubCat2());
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
      subList2.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList2", subList2);

      TicketCategoryList subList3 = new TicketCategoryList();
      subList3.setCatLevel(3);
      subList3.setParentCode(kbList.getSubCat2());
      subList3.setSiteId(siteId);
      subList3.setExclusiveToSite(true);
      subList3.getCatListSelect().setDefaultKey(kbList.getSubCat3());
      subList3.setHtmlJsEvent("onChange=\"javascript:updateSubList4();\"");
      subList3.buildList(db);
      subList3.getCatListSelect().addItem(0, "Undetermined");
      context.getRequest().setAttribute("SubList3", subList3);
      boolean canAddCategory = true;
      if (kbList.getCategoryId() > 0) {
        TicketCategory category = new TicketCategory(db, kbList.getCategoryId());
        canAddCategory = category.getEnabled();
      }
      context.getRequest().setAttribute("canAddCategory", String.valueOf(canAddCategory));
    } catch (Exception e) {
      e.printStackTrace();
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      String siteId = context.getRequest().getParameter("siteId");
      String reset = context.getRequest().getParameter("reset");
      String catCode = context.getRequest().getParameter("catCode");
      String subCat1 = context.getRequest().getParameter("subCat1");
      String subCat2 = context.getRequest().getParameter("subCat2");
      String subCat3 = context.getRequest().getParameter("subCat3");
      User user = this.getUser(context, this.getUserId(context));
      if (!isSiteAccessPermitted(context, siteId)) {
        return ("PermissionError");
      }

      db = this.getConnection(context);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);

/*      if (siteId != null && !"".equals(siteId.trim())) {
        if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
          return ("PermissionError");
        }
      }
*/
      if (siteId == null || "".equals(siteId.trim())) {
        siteId = String.valueOf(user.getSiteId());
      }
      if (reset != null && "true".equals(reset.trim())) {
        TicketCategoryList catList = new TicketCategoryList();
        catList.setCatLevel(0);
        catList.setSiteId(siteId);
        catList.setExclusiveToSite(true);
        catList.setParentCode(0);
        catList.buildList(db);
        context.getRequest().setAttribute("CategoryList", catList);
      } else if (catCode != null) {
        TicketCategoryList subList1 = new TicketCategoryList();
        subList1.setCatLevel(1);
        subList1.setSiteId(siteId);
        subList1.setParentCode(Integer.parseInt(catCode));
        subList1.buildList(db);
        context.getRequest().setAttribute("SubList1", subList1);
      } else if (subCat1 != null) {
        TicketCategoryList subList2 = new TicketCategoryList();
        subList2.setCatLevel(2);
        subList2.setSiteId(siteId);
        subList2.setParentCode(Integer.parseInt(subCat1));
        subList2.buildList(db);
        context.getRequest().setAttribute("SubList2", subList2);
      } else if (subCat2 != null) {
        TicketCategoryList subList3 = new TicketCategoryList();
        subList3.setCatLevel(3);
        subList3.setSiteId(siteId);
        subList3.setParentCode(Integer.parseInt(subCat2));
        subList3.buildList(db);
        context.getRequest().setAttribute("SubList3", subList3);
      }
    } catch (Exception errorMessage) {
    } finally {
      this.freeConnection(context, db);
    }
    return ("CategoryJSListOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandAdd(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
    
  private String executeCommandAdd(ActionContext context,Connection db) throws NumberFormatException, SQLException {
    if (!(hasPermission(context, "tickets-knowledge-base-add"))) {
      return ("PermissionError");
    }
    String categoryId = context.getRequest().getParameter("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = (String) context.getRequest().getAttribute("categoryId");
    }
    String title = (String) context.getRequest().getAttribute("title");
    String description = (String) context.getRequest().getAttribute("description");
    KnowledgeBase kb = new KnowledgeBase();
    kb.setTitle(title);
    kb.setDescription(description);
    kb.setCategoryId(categoryId);
      TicketCategory category = new TicketCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("thisCategory", category);
      context.getRequest().setAttribute("kb", kb);
    return getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandModify(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
  
  private String executeCommandModify(ActionContext context,Connection db) throws NumberFormatException, SQLException {  
    if (!(hasPermission(context, "tickets-knowledge-base-view"))) {
      return ("PermissionError");
    }
    String kbId = context.getRequest().getParameter("kbId");
    if (kbId == null || "".equals(kbId)) {
      kbId = (String) context.getRequest().getAttribute("kbId");
    }
    String title = (String) context.getRequest().getAttribute("title");
    String description = (String) context.getRequest().getAttribute("description");
    String categoryId = (String) context.getRequest().getAttribute("categoryId");

    KnowledgeBase kb = new KnowledgeBase();
      if (kb.getId() == -1) {
        kb = new KnowledgeBase();
        kb.setBuildResources(true);
        kb.queryRecord(db, Integer.parseInt(kbId));
      } else {
        kb.setTitle(title);
        kb.setDescription(description);
        kb.setCategoryId(categoryId);
      }
      if (kb.getItemId() != -1) {
        FileItem thisFile = new FileItem(db, kb.getItemId(), kb.getId(), Constants.DOCUMENTS_KNOWLEDGEBASE);
        context.getRequest().setAttribute("fileItem", thisFile);
      }
      TicketCategory category = new TicketCategory(db, kb.getCategoryId());
      context.getRequest().setAttribute("thisCategory", category);
      context.getRequest().setAttribute("kb", kb);
    return getReturn(context,"Modify");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "tickets-knowledge-base-view"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    boolean recordInserted = false;
    boolean fileInserted = false;
    int resultCount = -1;
    KnowledgeBase kb = new KnowledgeBase();
    FileItem thisItem = null;
    Connection db = null;
    try {
      String filePath = this.getPath(context, "knowledgebase");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String kbId = (String) parts.get("id");
      kb.setId(kbId);
      db = this.getConnection(context);
      if (kb.getId() != -1) {
        kb.setBuildResources(true);
        kb.queryRecord(db, kb.getId());
      }
      String fileText = (String) parts.get("fileText");
      String title = (String) parts.get("title");
      kb.setTitle(title);
      String description = (String) parts.get("description");
      kb.setDescription(description);
      String categoryId = (String) parts.get("categoryId");
      kb.setCategoryId(categoryId);
      String itemId = (String) parts.get("fid");
      kb.setItemId(itemId);
      String versionId = (String) parts.get("versionId");
      kb.setModifiedBy(this.getUserId(context));
      kb.printKb();
      isValid = this.validateObject(context, db, kb);
      if (!((Object) parts.get("itemId1") instanceof FileInfo)) {
        if (fileText != null && !"".equals(fileText.trim())) {
          isValid = false;
        }
      }
      if (isValid) {
        if (kb.getId() == -1) {
          recordInserted = kb.insert(db);
        } else {
          resultCount = kb.update(db);
        }
        if ((Object) parts.get("itemId1") instanceof FileInfo) {
          //parts.get("itemId1") != null &&
          //Update the database with the resulting file
          FileInfo newFileInfo = (FileInfo) parts.get("itemId1");
          //Insert a file description record into the database
          thisItem = new FileItem();
          thisItem.setLinkModuleId(Constants.DOCUMENTS_KNOWLEDGEBASE);
          thisItem.setLinkItemId(kb.getId());
          thisItem.setEnteredBy(getUserId(context));
          thisItem.setModifiedBy(getUserId(context));
          thisItem.setFolderId(-1);
          thisItem.setId(kb.getItemId());
          thisItem.setSubject(title);
          thisItem.setClientFilename(newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename());
          thisItem.setVersion(Double.parseDouble(versionId));
          thisItem.setSize(newFileInfo.getSize());
          isValid = this.validateObject(context, db, thisItem);
          if (isValid && (recordInserted || resultCount > -1)) {
            if (thisItem.getId() != -1) {
              recordInserted = thisItem.insertVersion(db);
            } else {
              fileInserted = thisItem.insert(db);
            }
            if (fileInserted) {
              kb = new KnowledgeBase(db, kb.getId());
              kb.setItemId(thisItem.getId());
              resultCount = kb.update(db);
              kb.printKb();
            }
          }
        }
      }
      HashMap errors = new HashMap();
      SystemStatus systemStatus = this.getSystemStatus(context);
      if ((Object) parts.get("itemId1") instanceof FileInfo) {
        //parts.get("itemIdFile") != null &&
        if (!fileInserted) {
          //Catch the errors and set it to the add/modify page
          errors.put("actionError", systemStatus.getLabel("object.validation.incorrectFileName"));
          processErrors(context, errors);
        }
      } else if (fileText != null && !"".equals(fileText.trim())) {
        //Catch the errors and set it to the add/modify page
        errors.put("actionError", systemStatus.getLabel("object.validation.incorrectFileName"));
        processErrors(context, errors);
      }
      context.getRequest().setAttribute("kbId", String.valueOf(kb.getId()));
      if (!isValid || (!recordInserted && resultCount == -1)) {
        context.getRequest().setAttribute("kb", kb);
        if (kb.getId() == -1) {
          context.getRequest().setAttribute("categoryId", String.valueOf(kb.getCategoryId()));
          if (kb.getTitle() != null) {
            context.getRequest().setAttribute("title", kb.getTitle());
          }
          if (kb.getDescription() != null) {
            context.getRequest().setAttribute("description", kb.getDescription());
          }
          return executeCommandAdd(context,db);
        } else {
          return executeCommandModify(context,db);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Save");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "tickets-knowledge-base-view"))) {
      return ("PermissionError");
    }
    String kbId = context.getRequest().getParameter("kbId");
    if (kbId == null || "".equals(kbId)) {
      kbId = (String) context.getRequest().getAttribute("kbId");
    }
    KnowledgeBase kb = new KnowledgeBase();
    Connection db = null;
    try {
      db = this.getConnection(context);
      kb.setBuildResources(true);
      kb.queryRecord(db, Integer.parseInt(kbId));
      TicketCategory category = new TicketCategory(db, kb.getCategoryId());
      context.getRequest().setAttribute("thisCategory", category);
      context.getRequest().setAttribute("kb", kb);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Details");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {
    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String kbId = (String) context.getRequest().getParameter("kbId");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisItem = null;
    Connection db = null;
    KnowledgeBase thisKb = null;

    try {
      db = getConnection(context);
      thisKb = new KnowledgeBase(db, Integer.parseInt(kbId));
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(kbId), Constants.DOCUMENTS_KNOWLEDGEBASE);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      if (version == null) {
        FileItem itemToDownload = thisItem;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "knowledgebase") +
            getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(thisItem.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "KnowledgeBaseManager -> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", "The requested download no longer exists on the system");
          return (executeCommandSearch(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "knowledgebase") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(itemToDownload.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "KnowledgeBaseManager -> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", "The requested download no longer exists on the system");
          return (executeCommandSearch(context));
        }
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        se.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    return ("-none-");
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public String executeCommandConfirmDelete(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "tickets-knowledge-base-delete"))) {
      return ("PermissionError");
    }
    String isSourcePopup = context.getRequest().getParameter("isSourcePopup");
    String kbId = context.getRequest().getParameter("kbId");
    if (kbId == null || "".equals(kbId)) {
      kbId = (String) context.getRequest().getAttribute("kbId");
    }
    Connection db = null;
    KnowledgeBase kb = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      kb = new KnowledgeBase();
      kb.setBuildResources(true);
      kb.queryRecord(db, Integer.parseInt(kbId));

      DependencyList dependencies = kb.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("knowledgebase.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"),
            "javascript:window.location.href='KnowledgeBaseManager.do?command=Delete&kbId=" +
            kb.getId() +(isSourcePopup != null?"&isSourcePopup="+isSourcePopup:"")+ "';");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close();");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close();");
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
   * @param  context           Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public String executeCommandDelete(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "tickets-knowledge-base-delete"))) {
      return ("PermissionError");
    }
    String isSourcePopup = context.getRequest().getParameter("isSourcePopup");
    String kbId = (String) context.getRequest().getParameter("kbId");
    if (kbId != null && !"".equals(kbId)) {
      context.getRequest().setAttribute("kbId", kbId);
    }
    KnowledgeBase kb = null;
    Connection db = null;
    try {
      db = getConnection(context);
      kb = new KnowledgeBase();
      kb.setBuildResources(true);
      kb.queryRecord(db, Integer.parseInt(kbId));
      //delete the kb
      kb.delete(db, getDbNamePath(context));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "KnowledgeBaseManager.do?command=Search"+(isSourcePopup != null?"&popup="+isSourcePopup:""));
    return "DeleteOK";
  }
}


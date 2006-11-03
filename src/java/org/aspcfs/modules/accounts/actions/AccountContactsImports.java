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

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.apps.transfer.reader.mapreader.PropertyMap;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.ImportList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactImport;
import org.aspcfs.modules.contacts.base.ContactImportValidate;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Web Actions for Accounts -> Imports
 *
 * @author Mathur
 * @version $Id$
 * @created June 21, 2004
 */
public final class AccountContactsImports extends CFSModule {

  /**
   * Default Action: View Imports
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return (this.executeCommandView(context));
  }


  /**
   * View Imports
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "AccountContactsImportListInfo");
    pagedListInfo.setLink("AccountContactsImports.do?command=View");
    pagedListInfo.setDefaultListView("my");
    try {
      db = this.getConnection(context);

      //get mananger
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      //build list
      ImportList thisList = new ImportList();
      thisList.setType(Constants.IMPORT_ACCOUNT_CONTACTS);
      thisList.setPagedListInfo(pagedListInfo);
      if ("my".equals(pagedListInfo.getListView())) {
        thisList.setEnteredBy(this.getUserId(context));
      } else {
        thisList.setEnteredIdRange(this.getUserRange(context));
      }
      thisList.setManager(manager);
      thisList.setSystemStatus(systemStatus);
      thisList.buildList(db);

      //update record counts of running imports
      thisList.updateRecordCounts();

      context.getRequest().setAttribute("ImportList", thisList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "View");
  }


  /**
   * Create new import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandNew(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      
      buildFormElements(db, context);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "New");
  }


  /**
   * Save import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-add"))) {
      return ("PermissionError");
    }
    HashMap errors = new HashMap();
    Connection db = null;
    boolean contactRecordInserted = false;
    boolean isValid = false;
    boolean fileRecordInserted = false;

    SystemStatus systemStatus = this.getSystemStatus(context);
    ContactImport thisImport = (ContactImport) context.getFormBean();
    try {
      db = getConnection(context);

      String filePath = this.getPath(context, "accounts");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String subject = (String) parts.get("name");
      String description = (String) parts.get("description");
      String sourceType = (String) parts.get("sourceType");
      String rating = (String) parts.get("rating");
      String comments = (String) parts.get("comments");
      String siteId = (String) parts.get("siteId");

      //set import properties
      thisImport.setEnteredBy(this.getUserId(context));
      thisImport.setModifiedBy(this.getUserId(context));
      thisImport.setType(Constants.IMPORT_ACCOUNT_CONTACTS);
      thisImport.setName(subject);
      thisImport.setDescription(description);
      thisImport.setSourceType(sourceType);
      thisImport.setRating(rating);
      thisImport.setComments(comments);
      thisImport.setSiteId(siteId);
      if (!((Object) parts.get("id") instanceof FileInfo)) {
        fileRecordInserted = false;
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        processErrors(context, errors);
        isValid = this.validateObject(context, db, thisImport);
        context.getRequest().setAttribute("name", subject);
        isValid = false;
      } else {
        isValid = this.validateObject(context, db, thisImport);
        if (isValid) {
          contactRecordInserted = thisImport.insert(db);
          if (contactRecordInserted) {
            if ((Object) parts.get("id") instanceof FileInfo) {
              //Update the database with the resulting file
              FileInfo newFileInfo = (FileInfo) parts.get("id");
              //Insert a file description record into the database
              FileItem thisItem = new FileItem();
              thisItem.setLinkModuleId(Constants.IMPORT_ACCOUNT_CONTACTS);
              thisItem.setLinkItemId(thisImport.getId());
              thisItem.setEnteredBy(getUserId(context));
              thisItem.setModifiedBy(getUserId(context));
              thisItem.setSubject(subject);
              thisItem.setClientFilename(newFileInfo.getClientFileName());
              thisItem.setFilename(newFileInfo.getRealFilename());
              thisItem.setVersion(Import.IMPORT_FILE_VERSION);
              thisItem.setSize(newFileInfo.getSize());
              isValid = this.validateObject(context, db, thisItem);
              if (isValid) {
                fileRecordInserted = thisItem.insert(db);
              }
            }
          }
        }
      }
      if (contactRecordInserted && fileRecordInserted) {
        thisImport = new ContactImport(db, thisImport.getId());
        thisImport.buildFileDetails(db);
        thisImport.setSystemStatus(systemStatus);
      } else if (contactRecordInserted) {
        thisImport.delete(db);
        thisImport.setId(-1);
      }
      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }

    if (fileRecordInserted && contactRecordInserted) {
      return getReturn(context, "Save");
    }
    return executeCommandNew(context);
  }


  /**
   * Import Details
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      thisImport.buildFileDetails(db);
      thisImport.setSystemStatus(systemStatus);

      //get mananger
      ImportManager manager = systemStatus.getImportManager(context);
      Object activeImport = manager.getImport(thisImport.getId());

      //update record count if import is running
      if (thisImport.isRunning() && activeImport != null) {
        thisImport.updateRecordCounts(manager);
      } else if (thisImport.isRunning() && activeImport == null) {
        thisImport.updateStatus(db, Import.FAILED);
      }

      buildFormElements(db, context);

      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Details");
  }


  /**
   * Start import validation
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInitValidate(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-add"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      thisImport.buildFileDetails(db);
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty(
          "file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "accountContact");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      //get path to import file
      String filePath = this.getPath(context, "accounts") + getDatePath(
          importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ContactImportValidate validator = new ContactImportValidate();
        validator.setContactImport(thisImport);
        validator.setFilePath(filePath);
        validator.setPropertyMap(thisMap);
        validator.initialize();
        context.getRequest().setAttribute("ImportValidator", validator);
      } else {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.importFileDoesNotExist"));
      }

      //build form elements
      buildValidateFormElements(db, context, thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "InitValidate");
  }


  /**
   * Validate import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandValidate(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-add"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      thisImport.buildFileDetails(db);
      thisImport.setProperties(context.getRequest());
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty(
          "file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "accountContact");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      String filePath = this.getPath(context, "accounts") + getDatePath(
          importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ContactImportValidate validator = new ContactImportValidate();
        validator.setContactImport(thisImport);
        validator.setFilePath(filePath);
        validator.setPropertyMap(thisMap);
        validator.validate(context.getRequest());
        context.getRequest().setAttribute("ImportValidator", validator);

        if (validator.getErrors().size() == 0) {
          return executeCommandProcess(context);
        }
      } else {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.importFileDoesNotExist"));
      }

      buildValidateFormElements(db, context, thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Validate");
  }


  /**
   * Run the import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProcess(ActionContext context) {
    try {
      //validate has already built the import and maps, use those
      ContactImportValidate validator = (ContactImportValidate) context.getRequest().getAttribute(
          "ImportValidator");
      ContactImport thisImport = (ContactImport) context.getRequest().getAttribute(
          "ImportDetails");
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      FileItem thisItem = (FileItem) context.getRequest().getAttribute(
          "FileItem");

      //get the database elements
      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
          "ConnectionElement");

      //get manager
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      Object activeImport = manager.getImport(thisImport.getId());
      if (activeImport == null) {
        //run
        thisImport.setSystemStatus(systemStatus);
        thisImport.setPropertyMap(validator.getPropertyMap());
        thisImport.setLookupAccount(true);
        thisImport.setFilePath(validator.getFilePath());
        thisImport.setUserId(this.getUserId(context));
        thisImport.setConnectionElement(ce);
        thisImport.setFileItem(thisItem);
        //queue the import
        manager.add(thisImport);
      } else {
        HashMap map = new HashMap();
        map.put("${thisImport.name}", thisImport.getName());
        Template template = new Template(
            systemStatus.getLabel(
                "object.validation.actionError.importAlreadyRunning"));
        template.setParseElements(map);
        context.getRequest().setAttribute(
            "actionError", template.getParsedText());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return getReturn(context, "Process");
  }


  /**
   * Cancel the import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-add"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String importId = context.getRequest().getParameter("importId");
    try {
      //get mananger
      ImportManager manager = systemStatus.getImportManager(context);

      //cancel import
      if (!manager.cancel(Integer.parseInt(importId))) {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.importNotRunning"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    if ("list".equals(context.getRequest().getParameter("return"))) {
      return getReturn(context, "Cancel");
    }
    return getReturn(context, "CancelDetails");
  }


  /**
   * Confirm deletion of import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    if (!(hasPermission(context, "accounts-accounts-contacts-imports-delete"))) {
      return ("PermissionError");
    }

    String importId = context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      DependencyList dependencies = thisImport.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='AccountContactsImports.do?command=Delete&importId=" + importId + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountContactsImports.do?command=Delete&importId=" + importId + "'");
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
    return "ConfirmDeleteOK";
  }


  /**
   * Delete import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-delete"))) {
      return ("PermissionError");
    }

    String importId = (String) context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      int recordDeleted = thisImport.updateStatus(db, Import.DELETED);

      if (recordDeleted > 0) {
        //delete the files
        FileItem importFile = new FileItem(
            db, thisImport.getId(), Constants.IMPORT_ACCOUNT_CONTACTS);
        importFile.delete(db, this.getPath(context, "accounts"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "AccountContactsImports.do?command=View");
    return getReturn(context, "Delete");
  }


  /**
   * Download import file(could be error file)
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-imports-view"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String importId = (String) context.getRequest().getParameter("importId");
    String stream = (String) context.getRequest().getParameter("stream");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    ContactImport thisImport = null;

    Connection db = null;
    try {
      db = getConnection(context);
      thisImport = new ContactImport(db, Integer.parseInt(importId));
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(importId), Constants.IMPORT_ACCOUNT_CONTACTS);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download

    try {

      if (version == null) {
        FileItem itemToDownload = thisItem;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "accounts") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if ("true".equals(stream)) {
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
              "AccountContactsImports-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          context.getRequest().setAttribute("ImportDetails", thisImport);
          context.getRequest().setAttribute("FileItem", itemToDownload);
          return getReturn(context, "Details");
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "accounts") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context);
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "AccountContactsImports-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandView(context));
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println(se.toString());
      }
    } catch (Exception e) {
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
   * Approve the import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandApprove(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-edit"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    String importId = context.getRequest().getParameter("importId");

    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }

      if (thisImport.canApprove()) {
        thisImport.updateStatus(db, Import.PROCESSED_APPROVED);
      } else {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.importNotSuccessful"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Approve");
  }


  /**
   * View Results
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewResults(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String importId = context.getRequest().getParameter("importId");
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "AccountContactsImportResultsInfo");
    pagedListInfo.setLink(
        "AccountContactsImports.do?command=ViewResults&importId=" + importId);

    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(
          db, Integer.parseInt(importId));
      if (!isRecordAccessPermitted(context, thisImport)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ImportDetails", thisImport);

      ContactList thisList = new ContactList();
      thisList.setPagedListInfo(pagedListInfo);
      pagedListInfo.setSearchCriteria(thisList, context);
      thisList.setImportId(Integer.parseInt(importId));
      thisList.setIncludeAllSites(true);
      thisList.setExcludeUnapprovedContacts(false);
      thisList.setBuildDetails(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("ImportResults", thisList);
      
      pagedListInfo.setColumnToSortBy("");
      pagedListInfo.setSortOrder("");
      OrganizationList orgList = new OrganizationList();
      orgList.setPagedListInfo(pagedListInfo);
      pagedListInfo.setSearchCriteria(orgList, context);
      orgList.setImportId(Integer.parseInt(importId));
      orgList.setIncludeAllSites(true);
      orgList.setExcludeUnapprovedAccounts(false);
      orgList.buildList(db);
      context.getRequest().setAttribute("AccountImportResults", orgList);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ViewResults");
  }


  /**
   * View details of a contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandContactDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String contactId = context.getRequest().getParameter("contactId");

    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(true);
      thisContact.queryRecord(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, thisContact)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      Import thisImport = new Import(db, thisContact.getImportId());
      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ContactDetails");
  }


  /**
   * View details of a contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAccountDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String orgId = context.getRequest().getParameter("orgId");

    try {
      db = this.getConnection(context);
      Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
      if (!isRecordAccessPermitted(context, thisOrg)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", thisOrg);

      Import thisImport = new Import(db, thisOrg.getImportId());
      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "OrgDetails");
  }


  /**
   * Delete a contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteContact(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, thisContact)){
        return ("PermissionError");
      }
      if (thisContact.getStatusId() != Import.PROCESSED_APPROVED) {
        recordDeleted = thisContact.delete(
            db, this.getPath(context, "contacts"));
        processErrors(context, thisContact.getErrors());
        Organization thisOrg = new Organization(db, thisContact.getOrgId());
        if (thisOrg.getImportId() > 0 && !thisOrg.hasContacts(db)) {
          thisOrg.delete(db, context, getDbNamePath(context));
        }
        if (!recordDeleted) {
          context.getRequest().setAttribute("ContactDetails", thisContact);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      return getReturn(context, "DeleteContact");
    }

    if ("list".equals(context.getRequest().getParameter("return"))) {
      return getReturn(context, "DeleteContactFailedList");
    }
    return getReturn(context, "DeleteContactFailedDetails");
  }
  /**
   * Delete a contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteOrganization(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-imports-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    String orgId = context.getRequest().getParameter("orgId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
      if (!isRecordAccessPermitted(context, thisOrg)){
        return ("PermissionError");
      } 
      if (thisOrg.getStatusId() != Import.PROCESSED_APPROVED) {
          thisOrg.setContactDelete(true);
          thisOrg.setRevenueDelete(true);
          thisOrg.setDocumentDelete(true);
    	recordDeleted = thisOrg.delete(db, context, getDbNamePath(context));
        processErrors(context, thisOrg.getErrors());
        if (!recordDeleted) {
          context.getRequest().setAttribute("OrgDetails", thisOrg);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      return getReturn(context, "DeleteContact");
    }

    if ("list".equals(context.getRequest().getParameter("return"))) {
      return getReturn(context, "DeleteContactFailedList");
    }
    return getReturn(context, "DeleteContactFailedDetails");
  }


  /**
   * Description of the Method
   *
   * @param context    Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisImport Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildValidateFormElements(Connection db, ActionContext context, ContactImport thisImport) throws SQLException {
    SystemStatus systemStatus = getSystemStatus(context);

    //access type
    AccessTypeList accessTypeList = null;
    if (thisImport.getType() == Constants.IMPORT_ACCOUNT_CONTACTS) {
      accessTypeList = systemStatus.getAccessTypeList(
          db, AccessType.ACCOUNT_CONTACTS);
    } else {
      accessTypeList = systemStatus.getAccessTypeList(
          db, AccessType.GENERAL_CONTACTS);
    }
    context.getRequest().setAttribute("AccessTypeList", accessTypeList);

    //Email types
    LookupList emailTypeList = systemStatus.getLookupList(
        db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    //Phone types
    LookupList phoneTypeList = systemStatus.getLookupList(
        db, "lookup_contactphone_types");
    context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

    //Address types
    LookupList addressTypeList = systemStatus.getLookupList(
        db, "lookup_contactaddress_types");
    context.getRequest().setAttribute(
        "ContactAddressTypeList", addressTypeList);
  }
  
  
  private void buildFormElements(Connection db, ActionContext context) throws SQLException {
    SystemStatus systemStatus = getSystemStatus(context);

    LookupList sourceList = new LookupList(db, "lookup_contact_source");
    sourceList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SourceTypeList", sourceList);

    LookupList ratings = new LookupList(db, "lookup_contact_rating");
    ratings.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("RatingList", ratings);

    LookupList siteList = new LookupList(db, "lookup_site_id");
    siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteList", siteList);
  }
}


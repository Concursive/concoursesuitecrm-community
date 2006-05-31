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
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.ImportList;
import org.aspcfs.modules.netapps.base.ContractExpiration;
import org.aspcfs.modules.netapps.base.ContractExpirationImport;
import org.aspcfs.modules.netapps.base.ContractExpirationImportValidate;
import org.aspcfs.modules.netapps.base.ContractExpirationList;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    September 16, 2004
 *@version    $Id: NetworkApplicationsImports.java,v 1.1.6.1 2005/10/24 20:30:40
 *      mrajkowski Exp $
 */
public final class NetworkApplicationsImports extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {

    return (this.executeCommandView(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "NetworkApplicationsImportListInfo");
    pagedListInfo.setLink("NetworkApplicationsImports.do?command=View");
    try {
      db = this.getConnection(context);

      //get mananger
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      //build list
      ImportList thisList = new ImportList();
      thisList.setType(Constants.IMPORT_NETAPP_EXPIRATION);
      thisList.setPagedListInfo(pagedListInfo);
      if ("my".equals(pagedListInfo.getListView())) {
        thisList.setEnteredIdRange(this.getUserRange(context));
      } else {
        thisList.setEnteredBy(this.getUserId(context));
      }
      thisList.setManager(manager);
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
   *  Create new import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandNew(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-add"))) {
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    boolean contractRecordInserted = false;
    boolean fileRecordInserted = false;

    ContractExpirationImport thisImport = (ContractExpirationImport) context.getFormBean();
    try {
      db = getConnection(context);

      String filePath = this.getPath(context, "netapps");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String subject = (String) parts.get("name");
      String description = (String) parts.get("description");

      //set import properties
      thisImport.setEnteredBy(this.getUserId(context));
      thisImport.setModifiedBy(this.getUserId(context));
      thisImport.setType(Constants.IMPORT_NETAPP_EXPIRATION);
      thisImport.setName(subject);
      thisImport.setDescription(description);
      contractRecordInserted = thisImport.insert(db);

      if (contractRecordInserted) {
        if ((Object) parts.get("id") instanceof FileInfo) {
          //Update the database with the resulting file
          FileInfo newFileInfo = (FileInfo) parts.get("id");
          //Insert a file description record into the database
          FileItem thisItem = new FileItem();
          thisItem.setLinkModuleId(Constants.IMPORT_NETAPP_EXPIRATION);
          thisItem.setLinkItemId(thisImport.getId());
          thisItem.setEnteredBy(getUserId(context));
          thisItem.setModifiedBy(getUserId(context));
          thisItem.setSubject(subject);
          thisItem.setClientFilename(newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename());
          thisItem.setVersion(Import.IMPORT_FILE_VERSION);
          thisItem.setSize(newFileInfo.getSize());
          fileRecordInserted = thisItem.insert(db);
          if (!fileRecordInserted) {
            processErrors(context, thisItem.getErrors());
          }
        } else {
          fileRecordInserted = false;
          HashMap errors = new HashMap();
          errors.put("actionError", "The file could not be sent by your computer, make sure the file exists");
          processErrors(context, errors);
          context.getRequest().setAttribute("name", subject);
        }
      }
      if (contractRecordInserted && fileRecordInserted) {
        thisImport = new ContractExpirationImport(db, thisImport.getId());
        thisImport.buildFileDetails(db);
      } else if (contractRecordInserted) {
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

    if (fileRecordInserted && contractRecordInserted) {
      return getReturn(context, "Save");
    }
    return getReturn(context, "New");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;

    try {
      db = this.getConnection(context);

      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);

      //get mananger
      SystemStatus systemStatus = this.getSystemStatus(context);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInitValidate(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty("file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "netAppContractExpiration");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      //get path to import file
      String filePath = this.getPath(context, "netapps") + getDatePath(importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ContractExpirationImportValidate validator = new ContractExpirationImportValidate();
        validator.setContractExpirationImport(thisImport);
        validator.setFilePath(filePath);
        validator.setPropertyMap(thisMap);
        validator.initialize();
        context.getRequest().setAttribute("ImportValidator", validator);
      } else {
        context.getRequest().setAttribute("actionError", "Import file does not exist.");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandValidate(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      thisImport.setProperties(context.getRequest());
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty("file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "netAppContractExpiration");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      String filePath = this.getPath(context, "netapps") + getDatePath(importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ContractExpirationImportValidate validator = new ContractExpirationImportValidate();
        validator.setContractExpirationImport(thisImport);
        validator.setFilePath(filePath);
        validator.setPropertyMap(thisMap);
        validator.validate(context.getRequest());
        context.getRequest().setAttribute("ImportValidator", validator);

        if (validator.getErrors().size() == 0) {
          return executeCommandProcess(context);
        }
      } else {
        context.getRequest().setAttribute("actionError", "Import file does not exist.");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcess(ActionContext context) {
    try {
      //validate has already built the import and maps, use those
      ContractExpirationImportValidate validator = (ContractExpirationImportValidate) context.getRequest().getAttribute("ImportValidator");
      ContractExpirationImport thisImport = (ContractExpirationImport) context.getRequest().getAttribute("ImportDetails");
      FileItem thisItem = (FileItem) context.getRequest().getAttribute("FileItem");

      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");

      //get manager
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      Object activeImport = manager.getImport(thisImport.getId());
      if (activeImport == null) {
        //run
        thisImport.setPropertyMap(validator.getPropertyMap());
        thisImport.setFilePath(validator.getFilePath());
        thisImport.setUserId(this.getUserId(context));
        thisImport.setConnectionElement(ce);
        thisImport.setFileItem(thisItem);
        //queue the import
        manager.add(thisImport);
      } else {
        context.getRequest().setAttribute("actionError", "The import \"" + thisImport.getName() + "\" is already running");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return getReturn(context, "Process");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-add"))) {
      return ("PermissionError");
    }

    String importId = context.getRequest().getParameter("importId");
    try {
      //get mananger
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      //cancel import
      if (!manager.cancel(Integer.parseInt(importId))) {
        context.getRequest().setAttribute("actionError", "Cancel Failed: Import is no longer running");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    if (!(hasPermission(context, "netapps_expiration_contracts_imports-delete"))) {
      return ("PermissionError");
    }

    String importId = context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      DependencyList dependencies = thisImport.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());

      if (dependencies.size() == 0) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='NetworkApplicationsImports.do?command=Delete&importId=" + importId + "'");
      } else {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setHeader("Are you sure you want to delete this item:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='NetworkApplicationsImports.do?command=Delete&importId=" + importId + "'");
        htmlDialog.addButton("No", "javascript:parent.window.close()");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-delete"))) {
      return ("PermissionError");
    }

    String importId = (String) context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      int recordDeleted = thisImport.updateStatus(db, Import.DELETED);
      ContractExpirationImport.deleteImportedRecords(db, thisImport.getId());
      if (recordDeleted > 0) {
        //delete the files
        FileItem importFile = new FileItem(db, thisImport.getId(), Constants.IMPORT_NETAPP_EXPIRATION);
        importFile.delete(db, this.getPath(context, "netapps"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "NetworkApplicationsImports.do?command=View");
    return getReturn(context, "Delete");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {

    if (!(hasPermission(context, "netapps_expiration_contracts_imports-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String importId = (String) context.getRequest().getParameter("importId");
    String stream = (String) context.getRequest().getParameter("stream");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    ContractExpirationImport thisImport = null;

    Connection db = null;
    try {
      db = getConnection(context);
      thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(importId), Constants.IMPORT_NETAPP_EXPIRATION);
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
        String filePath = this.getPath(context, "netapps") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if ("true".equals(stream)) {
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println("NetworkApplicationsImports-> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          context.getRequest().setAttribute("ImportDetails", thisImport);
          context.getRequest().setAttribute("FileItem", itemToDownload);
          return getReturn(context, "Details");
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "netapps") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          System.err.println("NetworkApplicationsImports-> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandApprove(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-edit"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String importId = context.getRequest().getParameter("importId");

    try {
      db = this.getConnection(context);
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));

      if (thisImport.canApprove()) {
        thisImport.updateStatus(db, Import.PROCESSED_APPROVED);
      } else {
        context.getRequest().setAttribute("actionError", "Import has not yet been successfully processed");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewResults(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String importId = context.getRequest().getParameter("importId");
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "NetworkApplicationsImportResultsInfo");
    pagedListInfo.setLink("NetworkApplicationsImports.do?command=ViewResults&importId=" + importId);

    try {
      db = this.getConnection(context);
      ContractExpirationImport thisImport = new ContractExpirationImport(db, Integer.parseInt(importId));
      context.getRequest().setAttribute("ImportDetails", thisImport);

      ContractExpirationList thisList = new ContractExpirationList();
      thisList.setPagedListInfo(pagedListInfo);
      thisList.setImportId(Integer.parseInt(importId));
      thisList.setExcludeUnapprovedContracts(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("ImportResults", thisList);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ViewResults");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandContractExpirationDetails(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String expirationId = context.getRequest().getParameter("expirationId");

    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = new ContractExpiration(db, Integer.parseInt(expirationId));
      context.getRequest().setAttribute("contractExpiration", thisContractExpiration);

      Import thisImport = new Import(db, thisContractExpiration.getImportId());
      context.getRequest().setAttribute("ImportDetails", thisImport);
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
  public String executeCommandDeleteContractExpiration(ActionContext context) {
    if (!(hasPermission(context, "netapps_expiration_contracts_imports-edit"))) {
      return ("PermissionError");
    }

    Connection db = null;
    boolean recordDeleted = false;
    String expirationId = context.getRequest().getParameter("expirationId");
    try {
      db = this.getConnection(context);
      ContractExpiration thisContractExpiration = new ContractExpiration(db, Integer.parseInt(expirationId));
      if (thisContractExpiration.getStatusId() != Import.PROCESSED_APPROVED) {
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
    if (recordDeleted) {
      return getReturn(context, "DeleteContractExpiration");
    }

    if ("list".equals(context.getRequest().getParameter("return"))) {
      return getReturn(context, "DeleteContractExpirationListFailed");
    }
    return getReturn(context, "DeleteContractExpirationDetailsFailed");
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  thisImport        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildValidateFormElements(Connection db, ActionContext context, ContractExpirationImport thisImport) throws SQLException {
    SystemStatus systemStatus = getSystemStatus(context);

    //access type
    AccessTypeList accessTypeList = null;
    if (thisImport.getType() == Constants.IMPORT_NETAPP_EXPIRATION) {
      accessTypeList = systemStatus.getAccessTypeList(db, AccessType.EMPLOYEES);
    }
    context.getRequest().setAttribute("AccessTypeList", accessTypeList);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildFormElements(Connection db, ActionContext context) throws SQLException {
    SystemStatus systemStatus = getSystemStatus(context);

    LookupList sourceList = new LookupList(db, "lookup_contact_source");
    sourceList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SourceTypeList", sourceList);

    LookupList siteList = new LookupList(db, "lookup_site_id");
    siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
    context.getRequest().setAttribute("SiteList", siteList);
  }

}


package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.controller.SystemStatus;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.apps.transfer.reader.mapreader.*;

/**
 *  Web Actions for Contacts -> Imports
 *
 *@author     Mathur
 *@created    March 30, 2004
 *@version    $id:exp$
 */
public final class ExternalContactsImports extends CFSModule {

  /**
   *  Default Action: View Imports
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return (this.executeCommandView(context));
  }


  /**
   *  View Imports
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ExternalContactsImportListInfo");
    pagedListInfo.setLink("ExternalContactsImports.do?command=View");
    try {
      db = this.getConnection(context);

      //get the database pool
      ConnectionPool cPool = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

      //get mananger
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      //build list
      ImportList thisList = new ImportList();
      thisList.setType(Constants.IMPORT_CONTACTS);
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
    return this.getReturn(context, "View");
  }


  /**
   *  Create new import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandNew(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-add"))) {
      return ("PermissionError");
    }

    return this.getReturn(context, "New");
  }


  /**
   *  Save import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    boolean contactRecordInserted = false;
    boolean fileRecordInserted = false;
    
    ContactImport thisImport = (ContactImport) context.getFormBean();
    try {
      db = getConnection(context);

      String filePath = this.getPath(context, "contacts");
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
      thisImport.setType(Constants.IMPORT_CONTACTS);
      thisImport.setName(subject);
      thisImport.setDescription(description);
      contactRecordInserted = thisImport.insert(db);

      if (contactRecordInserted) {
        if ((Object) parts.get("id") instanceof FileInfo) {
          //Update the database with the resulting file
          FileInfo newFileInfo = (FileInfo) parts.get("id");
          //Insert a file description record into the database
          FileItem thisItem = new FileItem();
          thisItem.setLinkModuleId(Constants.IMPORT_CONTACTS);
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
      if (contactRecordInserted && fileRecordInserted) {
        thisImport = new ContactImport(db, thisImport.getId());
        thisImport.buildFileDetails(db);
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
      return this.getReturn(context, "Save");
    }
    return this.getReturn(context, "New");
  }


  /**
   *  Import Details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;

    try {
      db = this.getConnection(context);

      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);

      //get the database elements
      ConnectionPool cPool = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

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
      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Details");
  }


  /**
   *  Start import validation
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInitValidate(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty("file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "generalContact");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      //get path to import file
      String filePath = this.getPath(context, "contacts") + getDatePath(importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ContactImportValidate validator = new ContactImportValidate();
        validator.setContactImport(thisImport);
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
    return this.getReturn(context, "InitValidate");
  }


  /**
   *  Validate import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandValidate(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-add"))) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      thisImport.setProperties(context.getRequest());
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty("file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "generalContact");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      String filePath = this.getPath(context, "contacts") + getDatePath(importFile.getModified()) + importFile.getFilename();

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
        context.getRequest().setAttribute("actionError", "Import file does not exist.");
      }

      buildValidateFormElements(db, context, thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Validate");
  }


  /**
   *  Run the import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcess(ActionContext context) {
    try {
      //validate has already built the import and maps, use those
      ContactImportValidate validator = (ContactImportValidate) context.getRequest().getAttribute("ImportValidator");
      ContactImport thisImport = (ContactImport) context.getRequest().getAttribute("ImportDetails");
      FileItem thisItem = (FileItem) context.getRequest().getAttribute("FileItem");

      //get the database elements
      ConnectionPool cPool = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
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
    return this.getReturn(context, "Process");
  }


  /**
   *  Cancel the import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-add"))) {
      return ("PermissionError");
    }

    String importId = context.getRequest().getParameter("importId");
    try {
      //get the pool
      ConnectionPool cPool = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

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
      return this.getReturn(context, "Cancel");
    }
    return this.getReturn(context, "CancelDetails");
  }


  /**
   *  Confirm deletion of import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    if (!(hasPermission(context, "contacts-external_contacts-imports-delete"))) {
      return ("PermissionError");
    }

    String importId = context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));
      DependencyList dependencies = thisImport.processDependencies();
      htmlDialog.addMessage(dependencies.getHtmlString());

      if (dependencies.size() == 0) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='ExternalContactsImports.do?command=Delete&importId=" + importId + "'");
      } else {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setHeader("Are you sure you want to delete this item:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='ExternalContactsImports.do?command=Delete&importId=" + importId + "'");
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
   *  Delete import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    if (!(hasPermission(context, "contacts-external_contacts-imports-delete"))) {
      return ("PermissionError");
    }

    String importId = (String) context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));
      int recordDeleted = thisImport.updateStatus(db, Import.DELETED);

      if (recordDeleted > 0) {
        //delete the files
        FileItem importFile = new FileItem(db, thisImport.getId(), Constants.IMPORT_CONTACTS);
        importFile.delete(db, this.getPath(context, "contacts"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "ExternalContactsImports.do?command=View");
    return this.getReturn(context, "Delete");
  }


  /**
   *  Download import file(could be error file)
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-imports-view"))) {
      return ("PermissionError");
    }

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
      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(importId), Constants.IMPORT_CONTACTS);
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
        String filePath = this.getPath(context, "contacts") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          System.err.println("ExternalContactsImports-> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          context.getRequest().setAttribute("ImportDetails", thisImport);
          context.getRequest().setAttribute("FileItem", itemToDownload);
          return this.getReturn(context, "Details");
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "contacts") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          System.err.println("ExternalContactsImports-> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          return (executeCommandView(context));
        }
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
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
   *  Approve the import
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandApprove(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-edit"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String importId = context.getRequest().getParameter("importId");

    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));

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
    return this.getReturn(context, "Approve");
  }


  /**
   *  View Results
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewResults(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String importId = context.getRequest().getParameter("importId");
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ExternalContactsImportResultsInfo");
    pagedListInfo.setLink("ExternalContactsImports.do?command=ViewResults&importId=" + importId);

    try {
      db = this.getConnection(context);
      ContactImport thisImport = new ContactImport(db, Integer.parseInt(importId));
      context.getRequest().setAttribute("ImportDetails", thisImport);

      ContactList thisList = new ContactList();
      thisList.setPagedListInfo(pagedListInfo);
      thisList.setImportId(Integer.parseInt(importId));
      thisList.setExcludeUnapprovedContacts(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("ImportResults", thisList);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ViewResults");
  }


  /**
   *  View details of a contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandContactDetails(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String contactId = context.getRequest().getParameter("contactId");

    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);

      Import thisImport = new Import(db, thisContact.getImportId());
      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ContactDetails");
  }


  /**
   *  Delete a contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteContact(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-imports-edit"))) {
      return ("PermissionError");
    }

    Connection db = null;
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");

    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      if (thisContact.getStatusId() != Import.PROCESSED_APPROVED) {
        recordDeleted = thisContact.delete(db);
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
      return this.getReturn(context, "DeleteContact");
    }

    if ("list".equals(context.getRequest().getParameter("return"))) {
      return this.getReturn(context, "DeleteContactFailedList");
    }
    return this.getReturn(context, "DeleteContactFailedDetails");
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  thisImport        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildValidateFormElements(Connection db, ActionContext context, ContactImport thisImport) throws SQLException {
    SystemStatus systemStatus = getSystemStatus(context);

    //access type
    AccessTypeList accessTypeList = null;
    if (thisImport.getType() == Constants.IMPORT_ACCOUNT_CONTACTS) {
      accessTypeList = systemStatus.getAccessTypeList(db, AccessType.ACCOUNT_CONTACTS);
    } else {
      accessTypeList = systemStatus.getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
    }
    context.getRequest().setAttribute("AccessTypeList", accessTypeList);

    //Email types
    LookupList emailTypeList = systemStatus.getLookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    //Phone types
    LookupList phoneTypeList = systemStatus.getLookupList(db, "lookup_contactphone_types");
    context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

    //Address types
    LookupList addressTypeList = systemStatus.getLookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
  }
}


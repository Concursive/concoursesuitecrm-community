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
package org.aspcfs.modules.products.actions;

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
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.ImportList;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * A generic Product Catalog belongs to a Product Category and contains several
 * Product Options
 *
 * @author Olga
 * @version $Id:
 *          $
 * @created May 18, 2006
 */

public class ProductCatalogImports extends CFSModule {


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
    if (!(hasPermission(context, "product-catalog-product-imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "ProductCatalogImportListInfo");
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      pagedListInfo.setLink("ProductCatalogImports.do?command=View&moduleId=" + moduleId);
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      //get mananger
      SystemStatus systemStatus = this.getSystemStatus(context);
      ImportManager manager = systemStatus.getImportManager(context);

      //build list
      ImportList thisList = new ImportList();
      thisList.setType(Constants.IMPORT_PRODUCT_CATALOG);
      thisList.setPagedListInfo(pagedListInfo);
      if ("my".equals(pagedListInfo.getListView())) {
        thisList.setEnteredBy(this.getUserId(context));
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
    if (!(hasPermission(context, "product-catalog-product-imports-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
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
    if (!(hasPermission(context, "product-catalog-product-imports-add"))) {
      return ("PermissionError");
    }
    HashMap errors = new HashMap();
    HashMap warnings = new HashMap();
    Connection db = null;
    boolean productCatalogRecordInserted = false;
    boolean isValid = false;
    boolean fileRecordInserted = false;
    boolean zipFileRecordInserted = false;
    boolean zipFileErrors = false;

    SystemStatus systemStatus = this.getSystemStatus(context);
    ProductCatalogImport thisImport = (ProductCatalogImport) context.getFormBean();
    String filePath = this.getPath(context, "products");
    try {
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String subject = (String) parts.get("newImport");
      String description = (String) parts.get("description");
      String comments = (String) parts.get("comments");

      // Now that the data has been streamed, validate and import
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      //set import properties
      thisImport.setEnteredBy(this.getUserId(context));
      thisImport.setModifiedBy(this.getUserId(context));
      thisImport.setType(Constants.IMPORT_PRODUCT_CATALOG);
      thisImport.setName(subject);
      thisImport.setDescription(description);
      thisImport.setComments(comments);

      if (!((Object) parts.get("id") instanceof FileInfo) || (((FileInfo) parts.get("id")).getClientFileName().length() > 250)) {
        fileRecordInserted = false;
        if (!((Object) parts.get("id") instanceof FileInfo)) {
          errors.put("fileError", systemStatus.getLabel("object.validation.incorrectFileName"));
        } else {
          errors.put("fileError", systemStatus.getLabel("object.validation.longFileName"));
        }
        processErrors(context, errors);
        isValid = this.validateObject(context, db, thisImport);
        context.getRequest().setAttribute("name", subject);
        isValid = false;
      } else {
        isValid = this.validateObject(context, db, thisImport);
        if (isValid) {
          productCatalogRecordInserted = thisImport.insert(db);
          if (productCatalogRecordInserted) {
            if ((Object) parts.get("id") instanceof FileInfo) {
              //Update the database with the resulting file
              FileInfo newFileInfo = (FileInfo) parts.get("id");
              //Insert a file description record into the database
              FileItem thisItem = new FileItem();
              thisItem.setLinkModuleId(Constants.IMPORT_PRODUCT_CATALOG);
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
      // Image zip file
      isValid = false;
      if (!((Object) parts.get("imageId") instanceof FileInfo)) {
        zipFileRecordInserted = false;
      } else {
        isValid = this.validateObject(context, db, thisImport);
        if (isValid) {
          if (productCatalogRecordInserted) {
            if ((Object) parts.get("imageId") instanceof FileInfo) {
              // Update the database with the resulting file
              FileInfo newFileInfo = (FileInfo) parts.get("imageId");
              // Insert a file description record into the database
              FileItem thisItem = new FileItem();
              thisItem.setLinkModuleId(Constants.IMPORT_PRODUCT_CATALOG);
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
                zipFileRecordInserted = thisItem.insert(db);
              }
            }
          }
        }
      }

      if (productCatalogRecordInserted && fileRecordInserted) {
        thisImport = new ProductCatalogImport(db, thisImport.getId());
        try {
          thisImport.buildFileDetails(db);
        } catch (SQLException e) {
          thisImport.delete(db);
          thisImport.setId(-1);
          errors.put("fileError", systemStatus.getLabel("object.validation.badFileFormat"));
          processErrors(context, errors);
        }
        if (zipFileRecordInserted) {
          try {
            thisImport.buildZipFileDetails(db);
          } catch (SQLException e) {
            thisImport.delete(db);
            thisImport.setId(-1);
            zipFileErrors = true;
            errors.put("imagesError", systemStatus.getLabel("object.validation.badImagesFileFormat"));
            processErrors(context, errors);
          }
        }
        thisImport.setSystemStatus(systemStatus);
      } else if (productCatalogRecordInserted) {
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
    if (fileRecordInserted && productCatalogRecordInserted && !zipFileErrors) {
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
    if (!(hasPermission(context, "product-catalog-product-imports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      SystemStatus systemStatus = this.getSystemStatus(context);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ProductCatalogImport thisImport = new ProductCatalogImport(
          db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      if (FileItem.fileExists(db, -1, "%.zip", thisImport.getId(), thisImport.getType()) > 0) {
        thisImport.buildZipFileDetails(db);
      }
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
    ProductCatalogImages imagesFileItem;
    if (!(hasPermission(context, "product-catalog-product-imports-add"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ProductCatalogImport thisImport = new ProductCatalogImport(db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      if (FileItem.fileExists(db, -1, "%.zip", thisImport.getId(), thisImport.getType()) > 0) {
        thisImport.buildZipFileDetails(db);
      }
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty(
          "file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "productCatalog");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      //load the zip file item
      if (FileItem.fileExists(db, -1, "%.zip", thisImport.getId(), thisImport.getType()) > 0) {
        FileItem importZipFile = thisImport.getZipFileItem();
        context.getRequest().setAttribute("ZipFileItem", importZipFile);
        String zipFilePath = this.getPath(context, "products") + getDatePath(importZipFile.getModified()) + importZipFile.getFilename();
      }

      //get path to import file
      String filePath = this.getPath(context, "products") + getDatePath(importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ProductImportValidate validator = new ProductImportValidate();
        validator.setProductCatalogImport(thisImport);
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
    if (!(hasPermission(context, "product-catalog-product-imports-add"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {

      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      //build the import
      String importId = context.getRequest().getParameter("importId");
      ProductCatalogImport thisImport = new ProductCatalogImport(
          db, Integer.parseInt(importId));
      thisImport.buildFileDetails(db);
      if (FileItem.fileExists(db, -1, "%.zip", thisImport.getId(), thisImport.getType()) > 0) {
        thisImport.buildZipFileDetails(db);
      }
      thisImport.setProperties(context.getRequest());
      context.getRequest().setAttribute("ImportDetails", thisImport);

      //load the property map
      String propertyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty(
          "file.separator") + "cfs-import-properties.xml";
      PropertyMap thisMap = new PropertyMap(propertyFile, "productCatalog");

      //load the file item
      FileItem importFile = thisImport.getFile();
      context.getRequest().setAttribute("FileItem", importFile);

      //load the zip file item
      if (FileItem.fileExists(db, -1, "%.zip", thisImport.getId(), thisImport.getType()) > 0) {
        FileItem importZipFile = thisImport.getZipFileItem();
        context.getRequest().setAttribute("ZipFileItem", importZipFile);
        ProductCatalogImages imagesFileItem = ProductCatalogImages.processZipFile(thisImport.getZipFileItem(), this.getPath(context, "products") + getDatePath(importZipFile.getModified()));
        thisImport.setImageList(imagesFileItem);
      }
      String filePath = this.getPath(context, "products") + getDatePath(
          importFile.getModified()) + importFile.getFilename();

      if (FileUtils.fileExists(filePath)) {
        //Run the validator(only maps fields to properties in this iteration)
        ProductImportValidate validator = new ProductImportValidate();
        validator.setProductCatalogImport(thisImport);
        validator.setFilePath(filePath);
        validator.setPropertyMap(thisMap);
        validator.validate(context.getRequest());
        context.getRequest().setAttribute("ImportValidator", validator);

        if (validator.getErrors().size() == 0) {
          return executeCommandProcess(context,db);
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
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandProcess(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
    
  private String executeCommandProcess(ActionContext context,Connection db) throws SQLException {
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      //validate has already built the import and maps, use those
      ProductImportValidate validator = (ProductImportValidate) context.getRequest().getAttribute(
          "ImportValidator");
      ProductCatalogImport thisImport = (ProductCatalogImport) context.getRequest().getAttribute(
          "ImportDetails");
      FileItem thisItem = (FileItem) context.getRequest().getAttribute("FileItem");

      //get the database elements
      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");

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
    return getReturn(context, "Process");
  }


  /**
   * Cancel the import
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!(hasPermission(context, "product-catalog-product-imports-add"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String importId = context.getRequest().getParameter("importId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
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

    if (!(hasPermission(context, "product-catalog-product-imports-delete"))) {
      return ("PermissionError");
    }

    String importId = context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      SystemStatus systemStatus = this.getSystemStatus(context);
      ProductCatalogImport thisImport = new ProductCatalogImport(
          db, Integer.parseInt(importId));
      DependencyList dependencies = thisImport.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='ProductCatalogImports.do?command=Delete&moduleId=" + moduleId + "&importId=" + importId + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='ProductCatalogImports.do?command=Delete&moduleId=" + moduleId + "&importId=" + importId + "'");
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
    if (!(hasPermission(context, "product-catalog-product-imports-delete"))) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String importId = (String) context.getRequest().getParameter("importId");
    try {
      db = this.getConnection(context);
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      ProductCatalogImport thisImport = new ProductCatalogImport(
          db, Integer.parseInt(importId));
      int recordDeleted = thisImport.updateStatus(db, Import.DELETED);

      if (recordDeleted > 0) {
        //delete the files
        FileItem importFile = new FileItem(
            db, thisImport.getId(), Constants.IMPORT_PRODUCT_CATALOG);
        importFile.delete(db, this.getPath(context, "products"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "ProductCatalogImports.do?command=View&moduleId=" + moduleId);
    return getReturn(context, "Delete");
  }


  /**
   * Download import file(could be error file)
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {

    if (!(hasPermission(context, "product-catalog-product-imports-view"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String importId = (String) context.getRequest().getParameter("importId");
    String stream = (String) context.getRequest().getParameter("stream");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    ProductCatalogImport thisImport = null;

    Connection db = null;
    try {
      db = getConnection(context);
      thisImport = new ProductCatalogImport(db, Integer.parseInt(importId));
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(importId), Constants.IMPORT_PRODUCT_CATALOG);
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
        String filePath = this.getPath(context, "products") + getDatePath(
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
              "ProductCatalogImports-> Trying to send a file that does not exist");
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
        String filePath = this.getPath(context, "products") + getDatePath(
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
              "ProductCatalogImports-> Trying to send a file that does not exist");
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
    if (!(hasPermission(context, "product-catalog-product-imports-edit"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    String importId = context.getRequest().getParameter("importId");

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      ProductCatalogImport thisImport = new ProductCatalogImport(
          db, Integer.parseInt(importId));

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
    if (!(hasPermission(context, "product-catalog-product-imports-view"))) {
      return ("PermissionError");
    }


    Connection db = null;
    String importId = context.getRequest().getParameter("importId");
    String moduleId = context.getRequest().getParameter("moduleId");
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "ProductCatalogImportResultsInfo");
    pagedListInfo.setLink(
        "ProductCatalogImports.do?command=ViewResults&importId=" + importId + "&moduleId=" + moduleId);

    try {
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      ProductCatalogImport thisImport = new ProductCatalogImport(
          db, Integer.parseInt(importId));
      context.getRequest().setAttribute("ImportDetails", thisImport);

      ProductCatalogList thisList = new ProductCatalogList();
      thisList.setDetermineCategory(true);
      thisList.setPagedListInfo(pagedListInfo);
      pagedListInfo.setSearchCriteria(thisList, context);
      thisList.setImportId(Integer.parseInt(importId));
      thisList.setExcludeUnapprovedProducts(false);
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
   * View details of a Product Catalog
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProductDetails(ActionContext context) {
    if (!(hasPermission(context, "product-catalog-product-imports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    String productId = context.getRequest().getParameter("productId");

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      ProductCatalog thisProductCatalog = new ProductCatalog();
      thisProductCatalog.setBuildCategories(true);
      thisProductCatalog.setBuildPriceList(true);
      thisProductCatalog.queryRecord(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("ProductDetails", thisProductCatalog);
      FileItem thumbnail = null;
      FileItem smallImage = null;
      FileItem largeImage = null;
      if (thisProductCatalog.getThumbnailImageId() != -1) {
        thumbnail = new FileItem(db, thisProductCatalog.getThumbnailImageId());
      }
      if (thisProductCatalog.getSmallImageId() != -1) {
        smallImage = new FileItem(db, thisProductCatalog.getSmallImageId());
      }
      if (thisProductCatalog.getLargeImageId() != -1) {
        largeImage = new FileItem(db, thisProductCatalog.getLargeImageId());
      }
      context.getRequest().setAttribute("ThumbnailImage", thumbnail);
      context.getRequest().setAttribute("SmallImage", smallImage);
      context.getRequest().setAttribute("LargeImage", largeImage);
      Import thisImport = new Import(db, thisProductCatalog.getImportId());
      context.getRequest().setAttribute("ImportDetails", thisImport);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ProductDetails");
  }


  /**
   * Delete a Product
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteProduct(ActionContext context) {
    if (!(hasPermission(context, "product-catalog-product-imports-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    String productId = context.getRequest().getParameter("productId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId == null || "-1".equals(moduleId)) {
        moduleId = Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));
      }
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      ProductCatalog thisProductCatalog = new ProductCatalog(db, Integer.parseInt(productId));
      if (thisProductCatalog.getStatusId() != Import.PROCESSED_APPROVED) {
        recordDeleted = thisProductCatalog.delete(
            db, this.getPath(context, "products"));
        processErrors(context, thisProductCatalog.getErrors());

        if (!recordDeleted) {
          context.getRequest().setAttribute("ProductDetails", thisProductCatalog);
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
      return getReturn(context, "DeleteProductFailedList");
    }
    return getReturn(context, "DeleteProductFailedDetails");
  }


  /**
   * Description of the Method
   *
   * @param context    Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisImport Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildValidateFormElements(Connection db, ActionContext context, ProductCatalogImport thisImport) throws SQLException {
  }


  private void buildFormElements(Connection db, ActionContext context) throws SQLException {
    SystemStatus systemStatus = getSystemStatus(context);

    LookupList siteList = new LookupList(db, "lookup_site_id");
    siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteList", siteList);
  }
}




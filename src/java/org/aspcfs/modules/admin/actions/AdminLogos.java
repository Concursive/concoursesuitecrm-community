/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.Thumbnail;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.utils.ImageUtils;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author ananth
 * @created January 26, 2005
 */
public final class AdminLogos extends CFSModule {
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-logos-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    PermissionCategory permCat = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    if (moduleId == null) {
      moduleId = (String) context.getRequest().getAttribute("moduleId");
    }
    try {
      db = this.getConnection(context);
      permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);

      //build the list of logos available
      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.DOCUMENTS_QUOTE_LOGO);
      files.setLinkItemId(Constants.QUOTES);
      files.buildList(db);
      context.getRequest().setAttribute("FileItemList", files);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Admin", "View Documents");
    return ("ViewOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddLogo(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-logos-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    PermissionCategory permCat = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    if (moduleId == null) {
      moduleId = (String) context.getRequest().getAttribute("moduleId");
    }
    try {
      db = this.getConnection(context);
      permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Admin", "Add Documents");
    return ("AddLogoOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUploadLogo(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-logos-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;

    try {
      String filePath = this.getPath(context, "quotes");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String id = (String) parts.get("id");
      String subject = (String) parts.get("subject");

      db = getConnection(context);
      String defaultLogo = (String) parts.get("defaultLogo");
      String moduleId = (String) parts.get("moduleId");
      context.getRequest().setAttribute("moduleId", moduleId);

      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
        if (!newFileInfo.getClientFileName().toLowerCase().endsWith(".jpg")
            && !newFileInfo.getClientFileName().toLowerCase().endsWith(
                ".jpeg")
            && !newFileInfo.getClientFileName().toLowerCase().endsWith(".gif")
            && !newFileInfo.getClientFileName().toLowerCase().endsWith(".png")
            && !newFileInfo.getClientFileName().toLowerCase().endsWith(".bmp")
            && !newFileInfo.getClientFileName().toLowerCase().endsWith(".wmf")
            && !newFileInfo.getClientFileName().toLowerCase().endsWith(
                ".tiff")) {
          recordInserted = false;
          HashMap errors = new HashMap();
          SystemStatus systemStatus = this.getSystemStatus(context);
          errors.put(
              "actionError", systemStatus.getLabel(
                  "object.validation.unacceptableImageFileFormat"));
          processErrors(context, errors);
          context.getRequest().setAttribute("subject", subject);
        } else {
//Insert a file description record into the database
          FileItem thisItem = new FileItem();
          thisItem.setLinkModuleId(Constants.DOCUMENTS_QUOTE_LOGO);
          thisItem.setLinkItemId(Constants.QUOTES);
          thisItem.setEnteredBy(getUserId(context));
          thisItem.setModifiedBy(getUserId(context));
          thisItem.setSubject(subject);
          thisItem.setClientFilename(newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename());
          thisItem.setVersion(1.0);
          thisItem.setSize(newFileInfo.getSize());
          thisItem.setEnabled(true);
          if (subject != null) {
            context.getRequest().setAttribute("subject", subject);
          }
//Process if logo needs to be made default
          if (defaultLogo != null && !"".equals(defaultLogo)) {
            if (Integer.parseInt(defaultLogo) == 1) {
//Make this logo the default logo
              thisItem.setDefaultFile(true);
            }
          }
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insert(db);
          }
          if (recordInserted) {
            if (thisItem.isImageFormat()) {
//Create a thumbnail if this is an image
              File thumbnailFile = new File(
                  newFileInfo.getLocalFile().getPath() + "TH");
              ImageUtils.saveThumbnail(
                  newFileInfo.getLocalFile(), thumbnailFile, 133d, 133d);
//Store thumbnail in database
              Thumbnail thumbnail = new Thumbnail();
              thumbnail.setId(thisItem.getId());
              thumbnail.setFilename(newFileInfo.getRealFilename() + "TH");
              thumbnail.setVersion(thisItem.getVersion());
              thumbnail.setSize((int) thumbnailFile.length());
              thumbnail.setEnteredBy(thisItem.getEnteredBy());
              thumbnail.setModifiedBy(thisItem.getModifiedBy());
              recordInserted = thumbnail.insert(db);
            }
          }
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        processErrors(context, errors);
        context.getRequest().setAttribute("subject", subject);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }

    if (recordInserted) {
      return (executeCommandView(context));
    }
    return (executeCommandAddLogo(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-logos-edit"))) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = this.getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
      recordEnabled = thisItem.enable(db);
      if (!recordEnabled) {
        this.validateObject(context, db, thisItem);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //addModuleBean(context, "Accounts", "Delete Account");
    return (executeCommandView(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-logos-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), Constants.QUOTES, Constants.DOCUMENTS_QUOTE_LOGO);
      if (context.getRequest().getParameter("action") != null) {
        if (((String) context.getRequest().getParameter("action")).equals(
            "delete")) {
          //check to see if a quote refer's to this image
          QuoteList quotes = new QuoteList();
          quotes.setLogoFileId(thisItem.getId());
          quotes.buildList(db);
          quotes.removeLogoLink(db);
          recordDeleted = thisItem.delete(db, this.getPath(context, "quotes"));
        } else if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          recordDeleted = thisItem.disable(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Quote Logos", "Delete Logo");
    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        return ("DeleteERROR");
      }
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
  public String executeCommandMarkDefault(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-logos-edit"))) {
      return ("PermissionError");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = this.getConnection(context);
      FileItem.updateDefaultRecord(
          db, Constants.DOCUMENTS_QUOTE_LOGO, Constants.QUOTES, Integer.parseInt(
              itemId));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandView(context));
  }
}


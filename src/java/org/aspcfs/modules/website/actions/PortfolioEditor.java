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
package org.aspcfs.modules.website.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.iteam.base.Thumbnail;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.utils.ImageUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: Exp$
 * @created February 28, 2006
 */
public final class PortfolioEditor extends CFSModule {
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-view")) {
      return ("PermissionError");
    }
    return executeCommandList(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-view")) {
      return ("PermissionError");
    }
    String categoryId = (String) context.getRequest().getAttribute("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = context.getRequest().getParameter("categoryId");
    }
    Connection db = null;
    PortfolioCategoryList categoryList = new PortfolioCategoryList();
    PortfolioItemList itemList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (categoryId != null && !"".equals(categoryId.trim()) && !"null".equals(categoryId.trim()) && !"-1".equals(categoryId.trim()))
      {
        categoryList.setParentId(categoryId);
      }
      categoryList.setBuildResources(true);
      categoryList.buildList(db);
//System.out.println("PortfolioEditor::List the categoryList is " + categoryList);
      categoryList = categoryList.reorder();
      context.getRequest().setAttribute("categoryList", categoryList);
      if (categoryList.getParentId() > 0) {
        PortfolioCategory parentCategory = new PortfolioCategory(db, categoryList.getParentId());
        context.getRequest().setAttribute("parentCategory", parentCategory);
        PortfolioEditor.buildHierarchy(db, context);
      }
      itemList = new PortfolioItemList();
      itemList.setCategoryId(categoryList.getParentId());
      itemList.buildList(db);
      itemList = itemList.reorder();
      context.getRequest().setAttribute("itemList", itemList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddCategory(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-add")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    if (from != null && !"".equals(from)) {
      context.getRequest().setAttribute("return", from);
    }
    String parentId = (String) context.getRequest().getAttribute("parentId");
    if (parentId == null || "".equals(parentId.trim())) {
      parentId = context.getRequest().getParameter("parentId");
    }
    String positionId = (String) context.getRequest().getParameter("positionId");
    String doSubmit = context.getRequest().getParameter("dosubmit");
    Connection db = null;
    PortfolioCategory category = (PortfolioCategory) context.getFormBean();
    if (category.getPositionId() == -1 && positionId != null && !"".equals(positionId.trim()))
    {
      category.setPositionId(positionId);
    }
    if (doSubmit == null || "".equals(doSubmit.trim())) {
      category.setEnabled(true);
    }
    PortfolioCategory parentCategory = new PortfolioCategory();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //build the parent category
      if (parentId != null && !"".equals(parentId.trim()) && !"null".equals(parentId.trim()))
      {
        category.setParentId(parentId);
        if (category.getParentId() != -1) {
          context.getRequest().setAttribute("categoryId", String.valueOf(category.getParentId()));
          PortfolioEditor.buildHierarchy(db, context);
          parentCategory.queryRecord(db, category.getParentId());
          context.getRequest().setAttribute("parentCategory", parentCategory);
        }
      }
      context.getRequest().setAttribute("category", category);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddCategoryOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyCategory(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-edit")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    if (from != null && !"".equals(from)) {
      context.getRequest().setAttribute("return", from);
    }
    String categoryId = context.getRequest().getParameter("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = (String) context.getRequest().getAttribute("categoryId");
    }
    PortfolioCategory category = (PortfolioCategory) context.getFormBean();
    PortfolioCategory parentCategory = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (category.getId() == -1) {
        category.queryRecord(db, Integer.parseInt(categoryId));
      }
      if (category.getParentId() != -1) {
        PortfolioEditor.buildHierarchy(db, context);
        parentCategory = new PortfolioCategory(db, category.getParentId());
        context.getRequest().setAttribute("parentCategory", parentCategory);
      }
      context.getRequest().setAttribute("category", category);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyCategoryOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveCategory(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-edit")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    Connection db = null;
    boolean isValid = false;
    boolean recordInserted = false;
    int resultCount = -1;
    PortfolioCategory category = (PortfolioCategory) context.getFormBean();
    PortfolioCategory oldCategory = null;
    PortfolioCategory parentCategory = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (category.getId() > 0) {
        oldCategory = new PortfolioCategory(db, category.getId());
      }
      category.setModifiedBy(this.getUserId(context));
//System.out.println("PortfolioEditor::SaveCategory the category position is " + category.getPositionId());
      isValid = this.validateObject(context, db, category);
      if (isValid) {
        if (category.getId() > -1) {
          resultCount = category.update(db);
          processUpdateHook(context, oldCategory, category);
        } else {
          recordInserted = category.insert(db);
          processInsertHook(context, category);
        }
      }
      if (recordInserted || resultCount > 0) {
        context.getRequest().setAttribute("categoryId", String.valueOf(category.getId()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!isValid || (resultCount < 1 && !recordInserted)) {
      if (category.getId() > -1) {
        return executeCommandModifyCategory(context);
      } else {
        return executeCommandAddCategory(context);
      }
    }
    if (from != null && "list".equals(from)) {
      context.getRequest().setAttribute("categoryId", String.valueOf(category.getParentId()));
      return executeCommandList(context);
    }
    return "SaveCategoryOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDeleteCategory(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-delete")) {
      return ("PermissionError");
    }
    String categoryId = context.getRequest().getParameter("categoryId");
    if (categoryId == null || "".equals(categoryId)) {
      categoryId = (String) context.getRequest().getAttribute("categoryId");
    }
    Connection db = null;
    PortfolioCategory category = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      category = new PortfolioCategory();
      category.queryRecord(db, Integer.parseInt(categoryId));

      DependencyList dependencies = category.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
        systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("website.portfolio.category.dependencies"));
        htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='PortfolioEditor.do?command=DeleteCategory&categoryId=" + category.getId() + "';");
        htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
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
  public String executeCommandDeleteCategory(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-delete")) {
      return ("PermissionError");
    }
    String categoryId = (String) context.getRequest().getParameter("categoryId");
    if (categoryId != null && !"".equals(categoryId)) {
      context.getRequest().setAttribute("categoryId", categoryId);
    }
    PortfolioCategory category = null;
    Connection db = null;
    try {
      db = getConnection(context);
      category = new PortfolioCategory();
      category.queryRecord(db, Integer.parseInt(categoryId));
      //delete the category
      category.delete(db, getDbNamePath(context));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "PortfolioEditor.do?command=List&categoryId=" + category.getParentId());
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddItem(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-add")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    if (from != null && !"".equals(from)) {
      context.getRequest().setAttribute("return", from);
    }
    String categoryId = (String) context.getRequest().getAttribute("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = context.getRequest().getParameter("categoryId");
    }
    String name = (String) context.getRequest().getParameter("name");
    String description = (String) context.getRequest().getParameter("description");
    String caption = context.getRequest().getParameter("caption");
    String enabled = context.getRequest().getParameter("enabled");
    String positionId = context.getRequest().getParameter("positionId");
    String doSubmit = context.getRequest().getParameter("dosubmit");
    Connection db = null;
    PortfolioCategory category = null;
    PortfolioItem item = new PortfolioItem();
    if (item.getCategoryId() == -1) {
      item.setCategoryId(categoryId);
    }
    if (doSubmit == null || "".equals(doSubmit.trim())) {
      item.setEnabled(true);
    }
    item.setName(name);
    item.setDescription(description);
    item.setPositionId(positionId);
    item.setCaption(caption);
    if (enabled != null && !"".equals(enabled.trim())) {
      item.setEnabled(enabled);
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (item.getCategoryId() != -1) {
        category = new PortfolioCategory(db, item.getCategoryId());
        context.getRequest().setAttribute("category", category);
      }
      PortfolioEditor.buildHierarchy(db, context);
      context.getRequest().setAttribute("item", item);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddItemOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyItem(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-edit")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    if (from != null && !"".equals(from)) {
      context.getRequest().setAttribute("return", from);
    }
    String itemId = (String) context.getRequest().getAttribute("itemId");
    if (itemId == null || "".equals(itemId.trim())) {
      itemId = context.getRequest().getParameter("itemId");
    }
    String name = context.getRequest().getParameter("name");
    String description = context.getRequest().getParameter("description");
    String categoryId = context.getRequest().getParameter("categoryId");
    String caption = context.getRequest().getParameter("caption");
    String enabled = context.getRequest().getParameter("enabled");
    String positionId = context.getRequest().getParameter("positionId");
    Connection db = null;
    PortfolioCategory category = null;
    PortfolioItem item = new PortfolioItem();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (item.getId() == -1) {
        item = new PortfolioItem();
        item.setBuildResources(true);
        item.queryRecord(db, Integer.parseInt(itemId));
      } else {
        item.setName(name);
        item.setDescription(description);
        item.setCaption(caption);
        item.setPositionId(positionId);
        if (enabled != null && !"".equals(enabled.trim())) {
          item.setEnabled(enabled);
        } else {
          item.setEnabled(false);
        }
        if (categoryId != null && !"".equals(categoryId)) {
          item.setCategoryId(categoryId);
        }
      }
      if (item.getImageId() != -1) {
        FileItem thisFile = new FileItem(db, item.getImageId(), item.getId(), Constants.DOCUMENTS_PORTFOLIO_ITEM);
        context.getRequest().setAttribute("fileItem", thisFile);
      }
      context.getRequest().setAttribute("categoryId", String.valueOf(item.getCategoryId()));
      PortfolioEditor.buildHierarchy(db, context);
      if (item.getCategoryId() != -1) {
        category = new PortfolioCategory(db, item.getCategoryId());
        context.getRequest().setAttribute("category", category);
      }
      context.getRequest().setAttribute("item", item);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyItemOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveItem(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-edit")) {
      return ("PermissionError");
    }
    String from = context.getRequest().getParameter("return");
    boolean isValid = false;
    boolean recordInserted = false;
    boolean fileInserted = false;
    int resultCount = -1;
    PortfolioCategory category = null;
    PortfolioItem item = new PortfolioItem();
    PortfolioItem oldItem = null;
    FileItem thisFileItem = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      String filePath = this.getPath(context, "portfolioitem");
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String itemId = (String) parts.get("id");
      item.setId(itemId);
      db = this.getConnection(context);
      if (item.getId() > 0) {
        item.setBuildResources(true);
        item.queryRecord(db, item.getId());
      }
      String fileText = (String) parts.get("fileText");
      String name = (String) parts.get("name");
      String description = (String) parts.get("description");
      String caption = (String) parts.get("caption");
      String enabled = (String) parts.get("enabled");
      String categoryId = (String) parts.get("categoryId");
      String positionId = (String) parts.get("positionId");
      String imageId = (String) parts.get("imageId");
      String versionId = (String) parts.get("versionId");
      if (from == null || "".equals(from.trim())) {
        from = (String) parts.get("return");
      }
      item.setModifiedBy(this.getUserId(context));
      item.setName(name);
      item.setDescription(description);
      item.setCaption(caption);
      item.setPositionId(positionId);
      if (enabled != null && !"".equals(enabled.trim())) {
        item.setEnabled(enabled);
      }
      if (categoryId != null && !"".equals(categoryId)) {
        item.setCategoryId(categoryId);
      }
      if (imageId != null || !"".equals(imageId.trim())) {
        item.setImageId(imageId);
      }
      isValid = this.validateObject(context, db, item);
      if (!((Object) parts.get("imageId1") instanceof FileInfo)) {
        if (fileText != null && !"".equals(fileText.trim())) {
          isValid = false;
        }
      }
      if (isValid) {
        if (item.getId() == -1) {
          recordInserted = item.insert(db);
        } else {
          resultCount = item.update(db);
        }
        if ((Object) parts.get("imageId1") instanceof FileInfo) {
          //Update the database with the resulting file
          FileInfo newFileInfo = (FileInfo) parts.get("imageId1");
          //Insert a file description record into the database
          thisFileItem = new FileItem();
          thisFileItem.setLinkModuleId(Constants.DOCUMENTS_PORTFOLIO_ITEM);
          thisFileItem.setLinkItemId(item.getId());
          thisFileItem.setEnteredBy(getUserId(context));
          thisFileItem.setModifiedBy(getUserId(context));
          thisFileItem.setFolderId(-1);
          thisFileItem.setId(item.getImageId());
          thisFileItem.setSubject((caption != null && !"".equals(caption.trim()) ? caption : name));
          thisFileItem.setClientFilename(newFileInfo.getClientFileName());
          thisFileItem.setFilename(newFileInfo.getRealFilename());
          thisFileItem.setVersion(Double.parseDouble(versionId));
          thisFileItem.setSize(newFileInfo.getSize());
          isValid = this.validateObject(context, db, thisFileItem);
          if (isValid && (recordInserted || resultCount > -1)) {
            if (thisFileItem.getId() != -1) {
              recordInserted = thisFileItem.insertVersion(db);
            } else {
              fileInserted = thisFileItem.insert(db);
            }
            thisFileItem.setDirectory(filePath);
            if (thisFileItem.isImageFormat()) {
              //Create a thumbnail if this is an image
              File thumbnailFile = new File(
                newFileInfo.getLocalFile().getPath() + "TH");
              ImageUtils.saveThumbnail(
                newFileInfo.getLocalFile(), thumbnailFile, 133d, 133d);
              //Store thumbnail in database
              Thumbnail thumbnail = new Thumbnail();
              thumbnail.setId(thisFileItem.getId());
              thumbnail.setFilename(newFileInfo.getRealFilename() + "TH");
              thumbnail.setVersion(thisFileItem.getVersion());
              thumbnail.setSize((int) thumbnailFile.length());
              thumbnail.setEnteredBy(thisFileItem.getEnteredBy());
              thumbnail.setModifiedBy(thisFileItem.getModifiedBy());
              thumbnail.insert(db);
            }
            if (fileInserted) {
              item = new PortfolioItem(db, item.getId());
              item.setImageId(thisFileItem.getId());
              resultCount = item.update(db);
            }
          }
        }
      }
      HashMap errors = new HashMap();
      if ((Object) parts.get("imageId1") instanceof FileInfo) {
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
      context.getRequest().setAttribute("itemId", String.valueOf(item.getId()));
      if (!isValid || (!recordInserted && resultCount == -1)) {
        context.getRequest().setAttribute("item", item);
        if (item.getId() == -1) {
          context.getRequest().setAttribute("categoryId", String.valueOf(item.getCategoryId()));
          if (item.getName() != null) {
            context.getRequest().setAttribute("name", item.getName());
          }
          if (item.getDescription() != null) {
            context.getRequest().setAttribute("description", item.getDescription());
          }
          if (item.getCaption() != null) {
            context.getRequest().setAttribute("caption", item.getCaption());
          }
          if (item.getEnabled()) {
            context.getRequest().setAttribute("enabled", "true");
          }
          return executeCommandAddItem(context);
        } else {
          return executeCommandModifyItem(context);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (from != null && "list".equals(from)) {
      context.getRequest().setAttribute("categoryId", String.valueOf(item.getCategoryId()));
      return "SaveItemAndListOK";
    }
    return "SaveItemOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandItemDetails(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-view")) {
      return ("PermissionError");
    }
    String itemId = context.getRequest().getParameter("itemId");
    if (itemId == null || "".equals(itemId)) {
      itemId = (String) context.getRequest().getAttribute("itemId");
    }
    PortfolioItem item = new PortfolioItem();
    Connection db = null;
    try {
      db = this.getConnection(context);
      item.setBuildResources(true);
      item.queryRecord(db, Integer.parseInt(itemId));
      if (item.getCategoryId() != -1) {
        PortfolioCategory category = new PortfolioCategory(db, item.getCategoryId());
        context.getRequest().setAttribute("categoryId", String.valueOf(item.getCategoryId()));
        context.getRequest().setAttribute("category", category);
      }
      PortfolioEditor.buildHierarchy(db, context);
      context.getRequest().setAttribute("item", item);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ItemDetailsOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {
    Exception errorMessage = null;

    String imageId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String categoryId = (String) context.getRequest().getParameter("categoryId");
    String itemId = (String) context.getRequest().getParameter("itemId");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisImage = null;
    Connection db = null;
    PortfolioItem item = null;

    try {
      db = getConnection(context);
      item = new PortfolioItem(db, Integer.parseInt(itemId));
      thisImage = new FileItem(db, Integer.parseInt(imageId), Integer.parseInt(itemId), Constants.DOCUMENTS_PORTFOLIO_ITEM);
      if (version != null) {
        thisImage.buildVersionList(db);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      if (version == null) {
        FileItem itemToDownload = thisImage;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "portfolioitem") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println("PortfolioItemEditor -> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          return (executeCommandItemDetails(context));
        }
      } else {
        FileItemVersion itemToDownload = thisImage.getVersion(
          Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "portfolioitem") + getDatePath(
          itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println("PortfolioItemEditor -> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          return (executeCommandItemDetails(context));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDeleteItem(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-delete")) {
      return ("PermissionError");
    }
    String itemId = context.getRequest().getParameter("itemId");
    if (itemId == null || "".equals(itemId)) {
      itemId = (String) context.getRequest().getAttribute("itemId");
    }
    Connection db = null;
    PortfolioItem item = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      item = new PortfolioItem(db, Integer.parseInt(itemId));

      DependencyList dependencies = item.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
        systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("website.portfolio.item.dependencies"));
        htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='PortfolioItemEditor.do?command=DeleteItem&categoryId=" + item.getCategoryId() + "&itemId=" + item.getId() + "';");
        htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
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
  public String executeCommandDeleteItem(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-delete")) {
      return ("PermissionError");
    }
    String itemId = (String) context.getRequest().getParameter("itemId");
    if (itemId != null && !"".equals(itemId)) {
      context.getRequest().setAttribute("itemId", itemId);
    }
    PortfolioItem item = null;
    Connection db = null;
    try {
      db = getConnection(context);
      item = new PortfolioItem(db, Integer.parseInt(itemId));
      //delete the item
      item.delete(db, getDbNamePath(context));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "PortfolioEditor.do?command=List&categoryId=" + item.getCategoryId());
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public final static void buildHierarchy(Connection db, ActionContext context) throws SQLException {
    String categoryId = (String) context.getRequest().getAttribute("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = context.getRequest().getParameter("categoryId");
    }
    if (categoryId != null && !"-1".equals(categoryId) && !"0".equals(
      categoryId)) {
      LinkedHashMap categoryLevels = new LinkedHashMap();
      PortfolioCategory.buildHierarchy(db, categoryLevels, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("categoryLevels", categoryLevels);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReorder(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-view")) {
      return ("PermissionError");
    }
    String categoryId = (String) context.getRequest().getAttribute("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = context.getRequest().getParameter("categoryId");
    }
    Connection db = null;
    PortfolioCategoryList categoryList = new PortfolioCategoryList();
    PortfolioItemList itemList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      if (categoryId != null && !"".equals(categoryId.trim()) && !"null".equals(categoryId.trim()) && !"-1".equals(categoryId.trim()))
      {
        categoryList.setParentId(categoryId);
      }
      categoryList.setBuildResources(true);
      categoryList.buildList(db);
      categoryList.reset(context, db);
      context.getRequest().setAttribute("categoryList", categoryList);
      if (categoryList.getParentId() > 0) {
        PortfolioCategory parentCategory = new PortfolioCategory(db, categoryList.getParentId());
        context.getRequest().setAttribute("parentCategory", parentCategory);
      }
      PortfolioEditor.buildHierarchy(db, context);
      itemList = new PortfolioItemList();
      itemList.setCategoryId(categoryList.getParentId());
      itemList.buildList(db);
      itemList.reset(context, db);
      context.getRequest().setAttribute("itemList", itemList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ReorderOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-edit")) {
      return ("PermissionError");
    }
    String itemId = (String) context.getRequest().getParameter("itemId");
    if (itemId != null && !"".equals(itemId)) {
      context.getRequest().setAttribute("itemId", itemId);
    }
    Connection db = null;
    PortfolioItem item = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      item = new PortfolioItem(db, Integer.parseInt(itemId));
      PortfolioCategoryHierarchy hierarchy = new PortfolioCategoryHierarchy();
      hierarchy.build(db);
      context.getRequest().setAttribute("categoryHierarchy", hierarchy);
      context.getRequest().setAttribute("item", item);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "MoveOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateMove(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-edit")) {
      return ("PermissionError");
    }
    String categoryId = (String) context.getRequest().getAttribute("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = context.getRequest().getParameter("categoryId");
    }
    String itemId = (String) context.getRequest().getParameter("itemId");
    if (itemId != null && !"".equals(itemId)) {
      context.getRequest().setAttribute("itemId", itemId);
    }
    Connection db = null;
    PortfolioItem item = null;
    PortfolioCategory category = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      item = new PortfolioItem(db, Integer.parseInt(itemId));
      if (categoryId != null && !"".equals(categoryId)) {
        item.setCategoryId(categoryId);
      }
      item.updateCategory(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "UpdateMoveOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPopupSingleSelector(ActionContext context) {
    Connection db = null;
    PortfolioCategoryList categoryList = null;
    String categoryId = (String) context.getRequest().getAttribute("categoryId");
    if (categoryId == null || "".equals(categoryId.trim())) {
      categoryId = context.getRequest().getParameter("categoryId");
    }
    try {
      db = this.getConnection(context);
      String displayFieldId = (String) context.getRequest().getParameter("displayFieldId");
      String hiddenFieldId = (String) context.getRequest().getParameter("hiddenFieldId");

      PagedListInfo categoryPagedInfo = this.getPagedListInfo(context, "portfolioCategoryInfo");
      categoryPagedInfo.setLink("PortfolioEditor.do?command=PopupSingleSelector&hiddenFieldId=" + hiddenFieldId + "&displayFieldId=" + displayFieldId);

      PortfolioEditor.buildHierarchy(db, context);
      categoryList = new PortfolioCategoryList();
      categoryList.setPagedListInfo(categoryPagedInfo);
      if (categoryId != null && !"".equals(categoryId.trim()) && !"null".equals(categoryId.trim()) && !"-1".equals(categoryId.trim()))
      {
        categoryList.setParentId(categoryId);
      }
      categoryList.setEnabledOnly(Constants.TRUE);
      categoryList.setBuildResources(true);
      categoryList.buildList(db);
      categoryList = categoryList.reorder();
      context.getRequest().setAttribute("categoryList", categoryList);
      if (categoryList.getParentId() > 0) {
        PortfolioCategory parentCategory = new PortfolioCategory(db, categoryList.getParentId());
        context.getRequest().setAttribute("parentCategory", parentCategory);
        PortfolioEditor.buildHierarchy(db, context);
      }

      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
      context.getRequest().setAttribute("categoryList", categoryList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListSingleOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandXXX(ActionContext context) {
    if (!hasPermission(context, "website-portfolio-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "XXXOK";
  }
}


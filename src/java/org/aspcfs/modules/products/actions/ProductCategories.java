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

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.base.ProductCategory;
import org.aspcfs.modules.products.base.ProductCategoryList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created August 3, 2004
 */
public final class ProductCategories extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      LookupList typeSelect = new LookupList(
          db, "lookup_product_category_type");
      typeSelect.addItem(-1, "All Types");
      context.getRequest().setAttribute("TypeSelect", typeSelect);

      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
      PagedListInfo categoryListInfo = this.getPagedListInfo(
          context, "SearchProductCategoryListInfo");
      categoryListInfo.setCurrentLetter("");
      categoryListInfo.setCurrentOffset(0);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("SearchOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    ProductCategoryList categoryList = new ProductCategoryList();

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchProductCategoryListInfo");

    //Need to reset any sub PagedListInfos since this is a new search
    //this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);
      searchListInfo.setLink(
          "ProductCategories.do?command=Search&moduleId=" + moduleId);
      categoryList.setPagedListInfo(searchListInfo);
      categoryList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
      searchListInfo.setSearchCriteria(categoryList, context);
      if ("all".equals(searchListInfo.getListView())) {
        categoryList.setEnabled(-1);
      }
      if ("enabled".equals(searchListInfo.getListView())) {
        categoryList.setEnabled(1);
      }
      if ("disabled".equals(searchListInfo.getListView())) {
        categoryList.setEnabled(0);
      }
      categoryList.buildList(db);
      context.getRequest().setAttribute("CategoryList", categoryList);
      return "ListOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "product-catalog-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_product_category_type");
      typeSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CategoryTypeList", typeSelect);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        this.buildHierarchy(db, context);
        context.getRequest().setAttribute("parentCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSubCategoryList(ActionContext context) {
    ProductCategoryList categoryList = new ProductCategoryList();
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String categoryId = context.getRequest().getParameter("categoryId");
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("ProductCategory", category);

      PagedListInfo subCatListInfo = this.getPagedListInfo(
          context, "SubCategoryListInfo");
      categoryList = new ProductCategoryList();
      categoryList.setParentId(categoryId);
      categoryList.setPagedListInfo(subCatListInfo);
      categoryList.buildList(db);
      context.getRequest().setAttribute("CategoryList", categoryList);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "SubCategoryListOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "product-catalog-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    ProductCategory insCategory = null;
    ProductCategory newCategory = (ProductCategory) context.getFormBean();
    newCategory.setEnteredBy(getUserId(context));
    newCategory.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);
      isValid = this.validateObject(context, db, newCategory);
      if (isValid) {
        recordInserted = newCategory.insert(db);
      }
      if (recordInserted && isValid) {
        insCategory = new ProductCategory(db, newCategory.getId());
        context.getRequest().setAttribute("ProductCategory", insCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return ("InsertOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "product-catalog-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_product_category_type");
      typeSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CategoryTypeList", typeSelect);

      String catId = context.getRequest().getParameter("catId");
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(catId));
      this.buildHierarchy(db, context);
      context.getRequest().setAttribute("productCategory", category);

      /*
       *  ProductCategoryList childList = new ProductCategoryList();
       *  childList.setParentId(categoryId);
       *  childList.buildList(db);
       *  context.getRequest().setAttribute("ChildList", childList);
       */
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "product-catalog-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    int recordInserted = -1;
    ProductCategory insCategory = null;
    ProductCategory newCategory = (ProductCategory) context.getFormBean();
    newCategory.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      isValid = this.validateObject(context, db, newCategory);
      if (isValid) {
        recordInserted = newCategory.update(db);
      }
      if (recordInserted > -1 && isValid) {
        insCategory = new ProductCategory(db, newCategory.getId());
        context.getRequest().setAttribute("productCategory", insCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted > -1 && isValid) {
      return "UpdateOK";
    }
    return (executeCommandModify(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "product-catalog-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCategory category = null;
    Exception errorMessage = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      String categoryId = context.getRequest().getParameter("categoryId");
      category = new ProductCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("productCategory", category);

      ProductCategoryList categoryList = new ProductCategoryList();
      categoryList.setTopOnly(Constants.TRUE);
      categoryList.buildList(db);
      categoryList.setLevel(1);
      
      //populate the hierarchy with initial level 1
      ProductCategoryList.buildHierarchy(db, categoryList);
      categoryList.buildCompleteHierarchy();
      context.getRequest().setAttribute("categoryHierarchy", categoryList);
      context.getRequest().setAttribute("action", "moveCategory");
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "MoveOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveMove(ActionContext context) {
    if (!hasPermission(context, "product-catalog-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    String id = (String) context.getRequest().getParameter("id");
    String categoryId = (String) context.getRequest().getParameter(
        "categoryId");
    try {
      db = this.getConnection(context);
      ProductCategory thisCategory = new ProductCategory(
          db, Integer.parseInt(id));
      ProductCategory category = thisCategory.getChild(
          db, Integer.parseInt(categoryId));
      if (category != null) {
        category.updateParent(db, thisCategory.getParentId());
      }
      thisCategory.updateParent(db, Integer.parseInt(categoryId));
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "product-catalog-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCategory category = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String parentId = context.getRequest().getParameter("parentId");
    HtmlDialog htmlDialog = new HtmlDialog();
    Exception errorMessage = null;
    String returnUrl = context.getRequest().getParameter("return");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      category = new ProductCategory(db, Integer.parseInt(categoryId));
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      DependencyList dependencies = category.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("productCategory.deleteMsg") + "\n\n" +
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='ProductCategories.do?command=Delete&action=delete&return=" + returnUrl + "&moduleId=" + (moduleId != null ? moduleId : "") + "&categoryId=" + (parentId != null ? parentId : "") + "&catId=" + category.getId() + "'");
      //htmlDialog.addButton("button.disableOnly"), "javascript:window.location.href='ProductCategories.do?command=Delete&action=disable&categoryId=" + category.getId() + "&return=" + returnUrl + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return "ConfirmDeleteOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "product-catalog-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    ProductCategory thisCategory = null;
    Connection db = null;
    String returnUrl = context.getRequest().getParameter("return");
    String moduleId = context.getRequest().getParameter("moduleId");
    String parentId = context.getRequest().getParameter("parentId");
    returnUrl += "&moduleId=" + moduleId + "&categoryId=" + (parentId != null ? parentId : "");
    try {
      db = this.getConnection(context);
      String categoryId = context.getRequest().getParameter("catId");
      thisCategory = new ProductCategory(db, Integer.parseInt(categoryId));
      if (context.getRequest().getParameter("action") != null) {
        if (((String) context.getRequest().getParameter("action")).equals(
            "delete")) {
          recordDeleted = thisCategory.delete(
              db, this.getPath(context, "products"));
        } else if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          //recordDeleted = thisCategory.disable(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", returnUrl);
        return "DeleteOK";
      } else {
        processErrors(context, thisCategory.getErrors());
        return "SystemError";
      }
    } else {
      context.getRequest().setAttribute(
          "actionError", this.getSystemStatus(context).getLabel(
              "object.validation.actionError.categoryDeletion"));
      context.getRequest().setAttribute("refreshUrl", returnUrl);
      return ("DeleteError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "product-catalog-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCategory newCategory = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      int categoryId = Integer.parseInt(
          context.getRequest().getParameter("categoryId"));
      newCategory = new ProductCategory(db, categoryId);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ProductCategory", newCategory);
      return "DetailsOK";
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
  public String executeCommandImageList(ActionContext context) {
    Connection db = null;
    ProductCategory category = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String categoryId = context.getRequest().getParameter("categoryId");
      category = new ProductCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("ProductCategory", category);

      FileItem thumbnail = null;
      FileItem smallImage = null;
      FileItem largeImage = null;
      if (category.getThumbnailImageId() != -1) {
        thumbnail = new FileItem(db, category.getThumbnailImageId());
      }
      if (category.getSmallImageId() != -1) {
        smallImage = new FileItem(db, category.getSmallImageId());
      }
      if (category.getLargeImageId() != -1) {
        largeImage = new FileItem(db, category.getLargeImageId());
      }
      context.getRequest().setAttribute("ThumbnailImage", thumbnail);
      context.getRequest().setAttribute("SmallImage", smallImage);
      context.getRequest().setAttribute("LargeImage", largeImage);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "ImageListOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownloadFile(ActionContext context) {
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String categoryId = (String) context.getRequest().getParameter(
        "categoryId");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(categoryId));
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(categoryId), Constants.DOCUMENTS_PRODUCT_CATEGORY);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      FileItem itemToDownload = thisItem;
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
            "ProductCategories-> Trying to send a file that does not exist");
        context.getRequest().setAttribute(
            "actionError", "The requested download no longer exists on the system");
        return (executeCommandImageList(context));
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println(se.toString());
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage == null) {
      return ("-none-");
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
  public String executeCommandRemoveFile(ActionContext context) {
    Connection db = null;
    try {
      String itemId = (String) context.getRequest().getParameter("fid");
      String categoryId = (String) context.getRequest().getParameter(
          "categoryId");
      db = getConnection(context);
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(categoryId));
      String imageType = context.getRequest().getParameter("imageType");
      category.removeFileItem(
          db, Integer.parseInt(itemId), imageType, this.getPath(
              context, "products"));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandImageList(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUploadFile(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    Exception errorMessage = null;
    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "products");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String categoryId = context.getRequest().getParameter("categoryId");
      String subject = "Attachment";
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("ProductCategory", category);

      if ((Object) parts.get("id" + categoryId) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + categoryId);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATEGORY);
        thisItem.setLinkItemId(category.getId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(-1);
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
        }
        if (recordInserted && isValid) {
          String imageType = (String) parts.get("imageType");
          if ("thumbnail".equals(imageType)) {
            category.setThumbnailImageId(thisItem.getId());
          } else if ("small".equals(imageType)) {
            category.setSmallImageId(thisItem.getId());
          } else if ("large".equals(imageType)) {
            category.setLargeImageId(thisItem.getId());
          }
          isValid = this.validateObject(context, db, category);
          if (isValid) {
            category.update(db);
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
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandImageList(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMappingList(ActionContext context) {
    Connection db = null;
    ProductCategory category = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      int categoryId = Integer.parseInt(
          context.getRequest().getParameter("categoryId"));
      category = new ProductCategory(db, categoryId);
      ProductCategoryList mappings = new ProductCategoryList();
      mappings.setMasterCategoryId(category.getId());
      mappings.buildList(db);
      context.getRequest().setAttribute("MappingList", mappings);

      LookupList typeSelect = new LookupList(
          db, "lookup_product_category_type");
      context.getRequest().setAttribute("CategoryTypeList", typeSelect);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ProductCategory", category);
      return "MappingListOK";
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
  public String executeCommandAddMapping(ActionContext context) {
    Connection db = null;
    ProductCategory category = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);
      int categoryId = Integer.parseInt(
          context.getRequest().getParameter("categoryId"));
      category = new ProductCategory(db, categoryId);
      context.getRequest().setAttribute("ProductCategory", category);
      // build the product categories not already mapped
      ProductCategoryList mappings = new ProductCategoryList();
      mappings.setMasterCategoryId(category.getId());
      mappings.setExcludeMappedCategories(true);
      mappings.buildList(db);
      context.getRequest().setAttribute("MappingList", mappings);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return "AddMappingOK";
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
  public String executeCommandRemoveMapping(ActionContext context) {
    Connection db = null;
    ProductCategory category1 = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      int cat1 = Integer.parseInt(
          context.getRequest().getParameter("categoryId"));
      category1 = new ProductCategory(db, cat1);
      context.getRequest().setAttribute("ProductCategory", category1);
      //remove both mappings cat1 -> cat2 and cat2 -> cat1
      int cat2 = Integer.parseInt(context.getRequest().getParameter("cat2"));
      category1.removeCategoryMapping(db, cat2);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return (executeCommandMappingList(context));
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
  public String executeCommandInsertMapping(ActionContext context) {
    Connection db = null;
    ProductCategory category = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      /*
       *  String moduleId = context.getRequest().getParameter("moduleId");
       *  PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
       *  context.getRequest().setAttribute("PermissionCategory", permissionCategory);
       */
      int categoryId = Integer.parseInt(
          context.getRequest().getParameter("categoryId"));
      category = new ProductCategory(db, categoryId);

      String mapcategory = context.getRequest().getParameter("mapcategory");
      if (mapcategory != null) {
        category.addCategory(db, Integer.parseInt(mapcategory));
      }
      context.getRequest().setAttribute("ProductCategory", category);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return (executeCommandMappingList(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    //this.deletePagedListInfo(context, "");
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public final static void buildHierarchy(Connection db, ActionContext context) throws SQLException {
    String categoryId = context.getRequest().getParameter("categoryId");
    if (categoryId != null && !"-1".equals(categoryId) && !"0".equals(
        categoryId)) {
      LinkedHashMap categoryLevels = new LinkedHashMap();
      ProductCategory.buildHierarchy(
          db, categoryLevels, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("categoryLevels", categoryLevels);
    }
  }
}


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
import com.zeroio.iteam.base.Thumbnail;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ImageUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: ProductCatalogs.java,v 1.1.4.7 2005/02/24 19:20:11 mrajkowski
 *          Exp $
 * @created August 20, 2004
 */
public final class ProductCatalogs extends CFSModule {

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
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      LookupList typeSelect = new LookupList(db, "lookup_product_type");
      typeSelect.addItem(-1, "All Types");
      context.getRequest().setAttribute("TypeSelect", typeSelect);

      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
      PagedListInfo catalogListInfo = this.getPagedListInfo(
          context, "SearchProductCatalogListInfo");
      catalogListInfo.setCurrentLetter("");
      catalogListInfo.setCurrentOffset(0);

      if (!"".equals(
          catalogListInfo.getSearchOptionValue("searchcodeCategoryId")) && !"-1".equals(
          catalogListInfo.getSearchOptionValue("searchcodeCategoryId"))) {
        String categoryId = catalogListInfo.getSearchOptionValue(
            "searchcodeCategoryId");
        ProductCategory category = new ProductCategory(
            db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("ProductCategory", category);
      }
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
    Connection db = null;
    ProductCatalogList catalogList = new ProductCatalogList();

    // Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchProductCatalogListInfo");
    this.resetPagedListInfo(context);
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      searchListInfo.setLink(
          "ProductCatalogs.do?command=Search&moduleId=" + moduleId);
      catalogList.setPagedListInfo(searchListInfo);
      catalogList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
      catalogList.setCategoryId(searchListInfo.getFilterKey("listFilter2"));
      searchListInfo.setSearchCriteria(catalogList, context);
      if ("all".equals(searchListInfo.getListView())) {
        catalogList.setActive(Constants.UNDEFINED);
      }
      if ("enabled".equals(searchListInfo.getListView())) {
        catalogList.setActive(Constants.TRUE);
      }
      if ("disabled".equals(searchListInfo.getListView())) {
        catalogList.setActive(Constants.FALSE);
      }
      catalogList.buildList(db);
      context.getRequest().setAttribute("CatalogList", catalogList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
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
  public String executeCommandAddCategoryMappings(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(
          db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      // populate the existing list of category mappings for this product
      ProductCategoryList oldList = new ProductCategoryList();
      oldList.setProductId(catalogId);
      oldList.buildList(db);

      // populate the list of categories that need to be mapped
      ProductCategoryList newList = new ProductCategoryList();
      String finalElements = context.getRequest().getParameter(
          "finalElements");
      if (finalElements != null) {
        StringTokenizer st = new StringTokenizer(finalElements, ",");
        while (st.hasMoreTokens()) {
          String id = String.valueOf(st.nextToken());
          newList.add(new ProductCategory(db, Integer.parseInt(id)));
        }
      }
      newList.addProductMapping(db, oldList, catalog.getId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandCategoryList(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRemoveCategoryMapping(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(
          db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String categoryId = context.getRequest().getParameter("categoryId");
      catalog.removeCategoryMapping(db, Integer.parseInt(categoryId));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandCategoryList(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCategoryList(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(db,
          Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory",
          permissionCategory);

      String catalogId = context.getRequest().getParameter("productId");
      String categoryId = context.getRequest().getParameter("categoryId");
      ProductCatalog catalog = new ProductCatalog(db, Integer
          .parseInt(catalogId));
      catalog.setCategoryId(categoryId);
      context.getRequest().setAttribute("productCatalog", catalog);

      // Generate a list of categories that have this product
      PagedListInfo categoryListInfo = this.getPagedListInfo(context,
          "CategoryListInfo");
      ProductCategoryList categoryList = new ProductCategoryList();
      categoryList.setProductId(catalogId);
      categoryList.setPagedListInfo(categoryListInfo);
      categoryList.buildList(db);

      Iterator iter = (Iterator) categoryList.iterator();
      while (iter.hasNext()) {
        ProductCategory ctgry = (ProductCategory) iter.next();
        ProductCategoryList fullName = new ProductCategoryList();
        fullName = ctgry.buildFullName(db, fullName, ctgry.getId(), true);
        ctgry.setFullPath(fullName);
        ctgry.buildProductList(db);
      }
      context.getRequest().setAttribute("CategoryList", categoryList);

      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
      ProductCategoryList completeList = new ProductCategoryList();
      completeList.setProductId(catalogId);
      completeList.buildList(db);
      context.getRequest().setAttribute("CompleteList", completeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "CategoryListOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_product_type");
      typeSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogTypeList", typeSelect);

      LookupList formatSelect = systemStatus.getLookupList(
          db, "lookup_product_format");
      formatSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogFormatList", formatSelect);

      LookupList shippingSelect = systemStatus.getLookupList(
          db, "lookup_product_shipping");
      shippingSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogShippingList", shippingSelect);

      LookupList shipTimeSelect = systemStatus.getLookupList(
          db, "lookup_product_ship_time");
      shipTimeSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogShipTimeList", shipTimeSelect);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
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
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    ProductCatalog insCatalog = null;
    ProductCatalog newCatalog = (ProductCatalog) context.getFormBean();
    newCatalog.setEnteredBy(getUserId(context));
    newCatalog.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      if (newCatalog.getActivePrice() != null && newCatalog.getActivePrice().getPriceAmount() > 0) {
        newCatalog.getActivePrice().setEnabled(newCatalog.getEnabled());
        newCatalog.getActivePrice().setEnteredBy(this.getUserId(context));
        newCatalog.getActivePrice().setModifiedBy(this.getUserId(context));
        newCatalog.getActivePrice().setStartDate(newCatalog.getStartDate());
        newCatalog.getActivePrice().setExpirationDate(newCatalog.getExpirationDate());
      }
      isValid = this.validateObject(context, db, newCatalog);
      if (newCatalog.getActivePrice() != null && newCatalog.getActivePrice().getPriceAmount() > 0) {
        isValid = this.validateObject(context, db, newCatalog.getActivePrice()) && isValid;
      }
      if (isValid) {
        recordInserted = newCatalog.insert(db);
        if (recordInserted && newCatalog.getActivePrice() != null && newCatalog.getActivePrice().getPriceAmount() > 0) {
          insCatalog = new ProductCatalog(db, newCatalog.getId());
          newCatalog.getActivePrice().setProductId(insCatalog.getId());
          recordInserted = newCatalog.getActivePrice().insert(db);
        }
      }
      if (recordInserted && isValid) {
        if (insCatalog == null || insCatalog.getId() == -1) {
          insCatalog = new ProductCatalog(db, newCatalog.getId());
        }
        context.getRequest().setAttribute("productCatalog", insCatalog);
        String categoryId = context.getRequest().getParameter("categoryId");
        if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
            categoryId) != -1) {
          ProductCategory parentCategory = new ProductCategory(
              db, Integer.parseInt(categoryId));
          ProductCategories.buildHierarchy(db, context);
          context.getRequest().setAttribute("productCategory", parentCategory);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!recordInserted || !isValid) {
      context.getRequest().setAttribute("productCatalog", newCatalog);
      return (executeCommandAdd(context));
    }
    if (recordInserted && isValid) {
      return ("InsertOK");
    } else {
      return "SystemError";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      if (context.getRequest().getAttribute("productCatalog") == null) {
        String productId = context.getRequest().getParameter("productId");
        ProductCatalog cloneProduct = new ProductCatalog(
            db, Integer.parseInt(productId));
        cloneProduct.resetBaseInfo();
        context.getRequest().setAttribute("productCatalog", cloneProduct);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_product_type");
      typeSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogTypeList", typeSelect);

      LookupList formatSelect = systemStatus.getLookupList(
          db, "lookup_product_format");
      formatSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogFormatList", formatSelect);

      LookupList shippingSelect = systemStatus.getLookupList(
          db, "lookup_product_shipping");
      shippingSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogShippingList", shippingSelect);

      LookupList shipTimeSelect = systemStatus.getLookupList(
          db, "lookup_product_ship_time");
      shipTimeSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogShipTimeList", shipTimeSelect);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("parentCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
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
  public String executeCommandSaveClone(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    ProductCatalog insCatalog = null;
    ProductCatalog newCatalog = (ProductCatalog) context.getFormBean();
    newCatalog.setEnteredBy(getUserId(context));
    newCatalog.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      //Build the product that is being cloned
      String cloneSourceId = context.getRequest().getParameter("productId");
      ProductCatalog cloneSource = new ProductCatalog();
      cloneSource.setBuildOptions(true);
      cloneSource.setBuildPriceList(true);
      cloneSource.queryRecord(db, Integer.parseInt(cloneSourceId));
      if (newCatalog.getActivePrice() != null && newCatalog.getActivePrice().getPriceAmount() > 0) {
        newCatalog.getActivePrice().setEnabled(newCatalog.getEnabled());
        newCatalog.getActivePrice().setEnteredBy(this.getUserId(context));
        newCatalog.getActivePrice().setModifiedBy(this.getUserId(context));
        newCatalog.getActivePrice().setStartDate(newCatalog.getStartDate());
        newCatalog.getActivePrice().setExpirationDate(newCatalog.getExpirationDate());
      }
      isValid = this.validateObject(context, db, newCatalog);
      if (newCatalog.getActivePrice() != null && newCatalog.getActivePrice().getPriceAmount() > 0) {
        isValid = this.validateObject(context, db, newCatalog.getActivePrice()) && isValid;
      }
      if (isValid) {
        recordInserted = newCatalog.insert(db);
        if (recordInserted && newCatalog.getActivePrice() != null && newCatalog.getActivePrice().getPriceAmount() > 0) {
          insCatalog = new ProductCatalog(db, newCatalog.getId());
          newCatalog.getActivePrice().setProductId(insCatalog.getId());
          recordInserted = newCatalog.getActivePrice().insert(db);
        }
      }
      if (recordInserted && isValid) {
        if (insCatalog == null || insCatalog.getId() == -1) {
          insCatalog = new ProductCatalog(db, newCatalog.getId());
        }
        context.getRequest().setAttribute("productCatalog", insCatalog);

        String categoryId = context.getRequest().getParameter("categoryId");
        if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
            categoryId) != -1) {
          ProductCategory parentCategory = new ProductCategory(
              db, Integer.parseInt(categoryId));
          ProductCategories.buildHierarchy(db, context);
          context.getRequest().setAttribute("productCategory", parentCategory);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!recordInserted || !isValid) {
      context.getRequest().setAttribute("productCatalog", newCatalog);
      return (executeCommandClone(context));
    }
    if (recordInserted && isValid) {
      return ("SaveCloneOK");
    } else {
      return "SystemError";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog product = null;
    ProductCategory category = null;
    Exception errorMessage = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      String returnAction = context.getRequest().getParameter("returnAction");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);
      ProductCategoryList selectedList = new ProductCategoryList();
      String productId = context.getRequest().getParameter("productId");
      String parentId = context.getRequest().getParameter("parentId") != null
          && !"".equals(context.getRequest().getParameter("parentId"))
          ? context.getRequest().getParameter("parentId") : "-1";
      if (Integer.parseInt(productId) != -1) {
        product = new ProductCatalog(db, Integer.parseInt(productId));
        product.buildCategories(db);
        context.getRequest().setAttribute("productCatalog", product);
        selectedList = product.getCategoryList();
        context.getRequest().setAttribute("checkedList", product.getCategoryList());
      } else {
        String selected = context.getRequest().getParameter("selected");
        selectedList = new ProductCategoryList();
        if (selected != null && !"".equals(selected)) {
          selectedList = selectedList.buildListFromIds(db, selected);
          context.getRequest().setAttribute("checkedList", selectedList);
        }

      }

      String categoryId = context.getRequest().getParameter("categoryId");
      if (Integer.parseInt(categoryId) != -1) {
        category = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("productCategory", category);
      }

      ProductCategoryList categoryList = new ProductCategoryList();
      categoryList.setParentId(parentId);
      if ("-1".equals(parentId)) {
        categoryList.setTopOnly(Constants.TRUE);
      }
      categoryList.setBuildChildCount(true);
      categoryList.buildList(db);
      if ("-1".equals(parentId)) {
        ProductCategoryList tmpList = new ProductCategoryList();
        Iterator i = selectedList.iterator();
        while (i.hasNext()) {
          ProductCategory thisCategory = (ProductCategory) i.next();
          ProductCategoryList parentList = new ProductCategoryList();
          parentList = ProductCategory.buildFullName(db, parentList, thisCategory.getId(), false);
          tmpList.addAll(parentList);
        }
        ProductCategoryList.buildHierarchyFromSelectedIds(db, categoryList, tmpList);
      }
      context.getRequest().setAttribute("categoryHierarchy", categoryList);
      context.getRequest().setAttribute("action", "moveProduct");
      context.getRequest().setAttribute("returnAction", returnAction);
      context.getRequest().setAttribute("parentId", parentId);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (context.getRequest().getParameter("mode") != null)
        return "MoveChunkOK";
      else
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
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    String id = (String) context.getRequest().getParameter("id");
//    String categoryId = (String) context.getRequest().getParameter(
//        "categoryId");
    String returnAction = (String) context.getRequest().getParameter(
        "returnAction");
    String[] category = context.getRequest().getParameterValues(
        "categoryElt");

    try {
      db = this.getConnection(context);
      String ids = "";
      if (category != null) {
        for (int i = 0; i < category.length; i++) {
          ids += category[i] + "|";
        }
      }
      ProductCategoryList newList = new ProductCategoryList();
      newList = newList.buildListFromIds(db, ids);
      if (!"set".equals(returnAction)) {
        ProductCatalog thisProduct = new ProductCatalog(db, Integer
            .parseInt(id));
        thisProduct.buildCategories(db);
        newList.addProductMapping(db, thisProduct.getCategoryList(),
            thisProduct.getId());
        return "PopupCloseOK";
      }
      context.getRequest().setAttribute("categoryList", newList);
      return "MultiselectCloseOK";
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
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog thisProduct = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));

      thisProduct = new ProductCatalog(db, productId);
      thisProduct.buildCategories(db);
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("productCatalog", thisProduct);
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
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_product_type");
      typeSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogTypeList", typeSelect);

      LookupList formatSelect = systemStatus.getLookupList(
          db, "lookup_product_format");
      formatSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogFormatList", formatSelect);

      LookupList shippingSelect = systemStatus.getLookupList(
          db, "lookup_product_shipping");
      shippingSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogShippingList", shippingSelect);

      LookupList shipTimeSelect = systemStatus.getLookupList(
          db, "lookup_product_ship_time");
      shipTimeSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CatalogShipTimeList", shipTimeSelect);

      if (context.getRequest().getAttribute("productCatalog") == null) {
        int productId = Integer.parseInt(
            context.getRequest().getParameter("productId"));
        catalog = new ProductCatalog(db, productId);
        catalog.buildCategories(db);
        context.getRequest().setAttribute("productCatalog", catalog);
      }

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return "ModifyOK";
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
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    int recordUpdated = -1;
    ProductCatalog updatedCatalog = null;
    ProductCatalog catalog = (ProductCatalog) context.getFormBean();
    catalog.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      catalog.buildActivePrice(db);
      isValid = this.validateObject(context, db, catalog);
      if (isValid) {
        recordUpdated = catalog.update(db);
      }
      if (recordUpdated > 0 && isValid) {

        updatedCatalog = new ProductCatalog(db, catalog.getId());
        updatedCatalog.buildCategories(db);
        context.getRequest().setAttribute("productCatalog", updatedCatalog);

        String categoryId = context.getRequest().getParameter("categoryId");
        if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
            categoryId) != -1) {
          ProductCategory parentCategory = new ProductCategory(
              db, Integer.parseInt(categoryId));
          ProductCategories.buildHierarchy(db, context);
          context.getRequest().setAttribute("productCategory", parentCategory);
        }
      } else {
        context.getRequest().setAttribute("productCatalog", catalog);
        return (executeCommandModify(context));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated > 0 && isValid) {
      if (context.getRequest().getParameter("return") != null) {
        if ("list".equals(context.getRequest().getParameter("return"))) {
          return "UpdateOK";
        }
      } else {
        return (executeCommandDetails(context));
      }
    }
    return "SystemError";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog product = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String productId = context.getRequest().getParameter("productId");

    HtmlDialog htmlDialog = new HtmlDialog();
    Exception errorMessage = null;
    String returnUrl = context.getRequest().getParameter("return");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      product = new ProductCatalog(db, Integer.parseInt(productId));
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      DependencyList dependencies = product.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      if (dependencies.canDelete()) {
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='ProductCatalogs.do?command=Trash&productId=" + productId + "&action=delete&return=" + returnUrl + "&moduleId=" + (moduleId != null ? moduleId : "") + "&categoryId=" + (categoryId != null ? categoryId : "") + "'");
      } else {
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='ProductCatalogs.do?command=Trash&productId=" + productId + "&action=disable&return=" + returnUrl + "&moduleId=" + (moduleId != null ? moduleId : "") + "&categoryId=" + (categoryId != null ? categoryId : "") + "&forceDelete=true" + "'");
      }
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
    if (!hasPermission(context, "product-catalog-product-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    ProductCatalog thisProduct = null;
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String returnUrl = context.getRequest().getParameter("return");
    try {
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      returnUrl += "&moduleId=" + moduleId + "&categoryId=" + (categoryId != null ? categoryId : "");
      db = this.getConnection(context);
      String productId = context.getRequest().getParameter("productId");
      thisProduct = new ProductCatalog(db, Integer.parseInt(productId));
      if (context.getRequest().getParameter("action") != null) {
        if (((String) context.getRequest().getParameter("action")).equals(
            "delete")) {
          recordDeleted = thisProduct.delete(db, this.getDbNamePath(context));
        } else
        if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          thisProduct.setEnabled(false);
          int resultCount = thisProduct.update(db);
          if (resultCount == 1) {
            recordDeleted = true;
          }
        }
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(errorMessage.getMessage());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", returnUrl);
        return "DeleteOK";
      } else {
        processErrors(context, thisProduct.getErrors());
        return "SystemError";
      }
    } else {
      context.getRequest().setAttribute(
          "actionError", this.getSystemStatus(context).getLabel(
          "object.validation.actionError.productDeletion"));
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
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordUpdated = false;
    ProductCatalog thisProduct = null;
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String returnUrl = context.getRequest().getParameter("return");
    try {
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      returnUrl += "&moduleId=" + moduleId + "&categoryId=" + (categoryId != null ? categoryId : "");
      db = this.getConnection(context);
      String productId = context.getRequest().getParameter("productId");
      thisProduct = new ProductCatalog(db, Integer.parseInt(productId));
      if (context.getRequest().getParameter("action").equals("disable")) {
        thisProduct.setEnabled(false);
        int resultCount = thisProduct.update(db);
        if (resultCount == 1) {
          recordUpdated = true;
        }
      } else {
        recordUpdated = thisProduct.updateStatus(
            db, true, this.getUserId(context));
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(errorMessage.getMessage());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordUpdated) {
        context.getRequest().setAttribute("refreshUrl", returnUrl);
        return "DeleteOK";
      } else {
        processErrors(context, thisProduct.getErrors());
        return "SystemError";
      }
    } else {
      context.getRequest().setAttribute(
          "actionError", this.getSystemStatus(context).getLabel(
          "object.validation.actionError.productDeletion"));
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
  public String executeCommandRestore(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordUpdated = false;
    ProductCatalog thisProduct = null;
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    try {
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      db = this.getConnection(context);
      String productId = context.getRequest().getParameter("productId");
      thisProduct = new ProductCatalog(db, Integer.parseInt(productId));
      recordUpdated = thisProduct.updateStatus(
          db, false, this.getUserId(context));
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(errorMessage.getMessage());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordUpdated) {
        return (executeCommandDetails(context));
      } else {
        processErrors(context, thisProduct.getErrors());
        return "SystemError";
      }
    } else {
      context.getRequest().setAttribute(
          "actionError", this.getSystemStatus(context).getLabel(
          "object.validation.actionError.productDeletion"));
      return ("DeleteError");
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
    ProductCatalog catalog = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId) && !"-1".equals(categoryId))
      {
        ProductCategory category = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("productCategory", category);
      }
      ProductCategories.buildHierarchy(db, context);

      String productId = context.getRequest().getParameter("productId");
      catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      FileItem thumbnail = null;
      FileItem smallImage = null;
      FileItem largeImage = null;
      if (catalog.getThumbnailImageId() != -1) {
        thumbnail = new FileItem(db, catalog.getThumbnailImageId());
      }
      if (catalog.getSmallImageId() != -1) {
        smallImage = new FileItem(db, catalog.getSmallImageId());
      }
      if (catalog.getLargeImageId() != -1) {
        largeImage = new FileItem(db, catalog.getLargeImageId());
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
  public String executeCommandUploadFile(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
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
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      String subject = "Attachment";
      ProductCatalog catalog = new ProductCatalog(
          db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      if ((Object) parts.get("id" + productId) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + productId);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATALOG);
        thisItem.setLinkItemId(catalog.getId());
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
        thisItem.setDirectory(filePath);
        if (thisItem.isImageFormat()) {
          //Create a thumbnail if this is an image
          File thumbnailFile = new File(
              newFileInfo.getLocalFile().getPath() + "TH");
          ImageUtils.saveThumbnail(newFileInfo.getLocalFile(), thumbnailFile, 133d, 133d);
          //Store thumbnail in database
          Thumbnail thumbnail = new Thumbnail();
          thumbnail.setId(thisItem.getId());
          thumbnail.setFilename(newFileInfo.getRealFilename() + "TH");
          thumbnail.setVersion(thisItem.getVersion());
          thumbnail.setSize((int) thumbnailFile.length());
          thumbnail.setEnteredBy(thisItem.getEnteredBy());
          thumbnail.setModifiedBy(thisItem.getModifiedBy());
          thumbnail.insert(db);
        }
        if (recordInserted && isValid) {
          String imageType = (String) parts.get("imageType");
          if ("thumbnail".equals(imageType)) {
            catalog.setThumbnailImageId(thisItem.getId());
          } else if ("small".equals(imageType)) {
            catalog.setSmallImageId(thisItem.getId());
          } else if ("large".equals(imageType)) {
            catalog.setLargeImageId(thisItem.getId());
          }
          catalog.updateImages(db);
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
      e.printStackTrace(System.out);
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
  public String executeCommandRemoveFile(ActionContext context) {
    Connection db = null;
    try {
      String itemId = (String) context.getRequest().getParameter("fid");
      String productId = (String) context.getRequest().getParameter("productId");
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId) && !"-1".equals(categoryId))
      {
        ProductCategory category = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("productCategory", category);
      }
      db = getConnection(context);
      ProductCatalog catalog = new ProductCatalog(
          db, Integer.parseInt(productId));
      String imageType = context.getRequest().getParameter("imageType");
      catalog.removeFileItem(
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
  public String executeCommandDownloadFile(ActionContext context) {
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String productId = (String) context.getRequest().getParameter("productId");
    String categoryId = context.getRequest().getParameter("categoryId");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (categoryId != null && !"".equals(categoryId) && !"-1".equals(categoryId))
      {
        ProductCategory category = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("productCategory", category);
      }
      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(productId), Constants.DOCUMENTS_PRODUCT_CATALOG);
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
            "ProductCatalogs-> Trying to send a file that does not exist");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return Description of the Return Value
   */
  /*
   *  public String executeCommandViewMappings(ActionContext context) {
   *  Connection db = null;
   *  ProductCatalog catalog = null;
   *  Exception errorMessage = null;
   *  try {
   *  db = this.getConnection(context);
   *  String moduleId = context.getRequest().getParameter("moduleId");
   *  PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
   *  context.getRequest().setAttribute("PermissionCategory", permissionCategory);
   *  int productId = Integer.parseInt(context.getRequest().getParameter("productId"));
   *  catalog = new ProductCatalog(db, productId);
   *  ProductCatalogList mappings = new ProductCatalogList();
   *  mappings.setMasterCategoryId(catalog.getId());
   *  mappings.buildList(db);
   *  context.getRequest().setAttribute("MappingList", mappings);
   *  } catch (Exception e) {
   *  errorMessage = e;
   *  e.printStackTrace();
   *  } finally {
   *  this.freeConnection(context, db);
   *  }
   *  if (errorMessage == null) {
   *  context.getRequest().setAttribute("ProductCatalog", category);
   *  return "ViewMappingsOK";
   *  } else {
   *  context.getRequest().setAttribute("Error", errorMessage);
   *  return ("SystemError");
   *  }
   *  }
   */

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPricingList(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);

      // Active Prices
      ProductCatalogPricingList activePriceList = new ProductCatalogPricingList();
      activePriceList.setProductId(catalog.getId());
      activePriceList.setEnabled(Constants.TRUE);
      activePriceList.buildList(db);
      context.getRequest().setAttribute("ActivePriceList", activePriceList);
      // Inactive Prices
      ProductCatalogPricingList inactivePriceList = new ProductCatalogPricingList();
      inactivePriceList.setProductId(catalog.getId());
      inactivePriceList.setEnabled(Constants.FALSE);
      inactivePriceList.buildList(db);
      context.getRequest().setAttribute(
          "InactivePriceList", inactivePriceList);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ProductCatalog", catalog);
      return "PricingListOK";
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
  public String executeCommandAddPricing(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList taxSelect = systemStatus.getLookupList(
          db, "lookup_product_tax");
      taxSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TaxSelect", taxSelect);

      LookupList currencySelect = systemStatus.getLookupList(
          db, "lookup_currency");
      currencySelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CurrencySelect", currencySelect);

      LookupList recurringTypeSelect = systemStatus.getLookupList(
          db, "lookup_recurring_type");
      recurringTypeSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute(
          "RecurringTypeSelect", recurringTypeSelect);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ProductCatalog", catalog);
      return "AddPricingOK";
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
  public String executeCommandModifyPricing(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);

      if (context.getRequest().getAttribute("Pricing") == null) {
        int pricingId = Integer.parseInt(
            context.getRequest().getParameter("pricingId"));
        ProductCatalogPricing pricing = new ProductCatalogPricing(
            db, pricingId);
        context.getRequest().setAttribute("Pricing", pricing);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList taxSelect = systemStatus.getLookupList(
          db, "lookup_product_tax");
      taxSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TaxSelect", taxSelect);

      LookupList currencySelect = systemStatus.getLookupList(
          db, "lookup_currency");
      currencySelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CurrencySelect", currencySelect);

      LookupList recurringTypeSelect = systemStatus.getLookupList(
          db, "lookup_recurring_type");
      recurringTypeSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute(
          "RecurringTypeSelect", recurringTypeSelect);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ProductCatalog", catalog);
      return "PricingModifyOK";
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
  public String executeCommandInsertPricing(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    ProductCatalog catalog = null;
    ProductCatalogPricing pricing = (ProductCatalogPricing) context.getFormBean();
    pricing.setEnteredBy(getUserId(context));
    pricing.setModifiedBy(getUserId(context));
    boolean recordInserted = false;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);
      pricing.setProductId(productId);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }

      isValid = this.validateObject(context, db, pricing);
      if (isValid) {
        recordInserted = pricing.insert(db);
      }
      if (!recordInserted || !isValid) {
        context.getRequest().setAttribute("Pricing", pricing);
        return (executeCommandAddPricing(context));
      } else {
        pricing = new ProductCatalogPricing(db, pricing.getId());
        pricing.updatePriceStatus(db, true);
        context.getRequest().setAttribute("Pricing", pricing);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return (executeCommandPricingList(context));
    }
    return "SystemError";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdatePricing(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    ProductCatalog catalog = null;
    Exception errorMessage = null;
    ProductCatalogPricing updatePricing = null;
    ProductCatalogPricing pricing = (ProductCatalogPricing) context.getFormBean();
    pricing.setModifiedBy(getUserId(context));
    int recordUpdated = 0;

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);
      pricing.setProductId(productId);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }

      isValid = this.validateObject(context, db, pricing);
      if (isValid) {
        recordUpdated = pricing.update(db);
      }
      if (recordUpdated == 1 && isValid) {
        updatePricing = new ProductCatalogPricing(db, pricing.getId());
        updatePricing.updatePriceStatus(db, true);
        context.getRequest().setAttribute("Pricing", updatePricing);
      } else {
        context.getRequest().setAttribute("Pricing", pricing);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated == 1 && isValid) {
      return (executeCommandPricingDetails(context));
    }
    return (executeCommandModifyPricing(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPricingDetails(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    ProductCatalogPricing pricing = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);

      int pricingId = Integer.parseInt(
          context.getRequest().getParameter("pricingId"));
      pricing = new ProductCatalogPricing(db, pricingId);
      context.getRequest().setAttribute("Pricing", pricing);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "PricingDetailsOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDisablePricing(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    ProductCatalogPricing pricing = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "permissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("productCatalog", catalog);

      int pricingId = Integer.parseInt(
          context.getRequest().getParameter("pricingId"));
      pricing = new ProductCatalogPricing(db, pricingId);
      context.getRequest().setAttribute("Pricing", pricing);

      pricing.updatePriceStatus(db, false);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (context.getRequest().getParameter("return") != null && "details".equals(
        context.getRequest().getParameter("return"))) {
      return (executeCommandPricingDetails(context));
    } else {
      return (executeCommandPricingList(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEnablePricing(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog catalog = null;
    ProductCatalogPricing pricing = null;
    boolean status = false;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      int productId = Integer.parseInt(
          context.getRequest().getParameter("productId"));
      catalog = new ProductCatalog(db, productId);
      context.getRequest().setAttribute("ProductCatalog", catalog);

      int pricingId = Integer.parseInt(
          context.getRequest().getParameter("pricingId"));
      pricing = new ProductCatalogPricing(db, pricingId);
      context.getRequest().setAttribute("Pricing", pricing);
      status = pricing.updatePriceStatus(db, true);
      if (!status) {
        context.getRequest().setAttribute(
            "actionError", this.getSystemStatus(context).getLabel(
            "product.priceNotActivated"));
      }

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(
            db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (context.getRequest().getParameter("return") != null && "details".equals(
        context.getRequest().getParameter("return"))) {
      return (executeCommandPricingDetails(context));
    } else {
      return (executeCommandPricingList(context));
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
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEnableProduct(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    ProductCatalog catalog = null;
    boolean status = false;
    String returnLocation = null;
    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId==null || "-1".equals(moduleId))
      {moduleId =Integer.toString(PermissionCategory.lookupId(db, PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG));}
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      int catalogId = Integer.parseInt(
          context.getRequest().getParameter("catalogId"));
      catalog = new ProductCatalog(db, catalogId);
      context.getRequest().setAttribute("ProductCatalog", catalog);
      status = DatabaseUtils.parseBoolean(
          (String) context.getRequest().getParameter("status").trim());
      catalog.setEnabled(status);
      isValid = this.validateObject(context, db, catalog);
      if (isValid) {
        catalog.update(db);
      }
      returnLocation = (String) context.getRequest().getParameter("return");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (returnLocation.equals("list")) {
      return (executeCommandSearch(context));
    } else if (returnLocation.equals("details")) {
      return (executeCommandDetails(context));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return "UserError";
    }
  }

  public String executeCommandViewCategories(ActionContext context) {
    Connection db = null;
    ProductCategoryList list = new ProductCategoryList();
    try {
      String ctgId = context.getRequest().getParameter("categoryId");
      if (ctgId == null || "".equals(ctgId)) {
        ctgId = (String) context.getRequest().getAttribute("categoryId");
      }
      String categories = context.getRequest().getParameter("categories");
      db = this.getConnection(context);
      if (categories != null && !"".equals(categories)) {
        list = list.buildListFromIds(db, categories);
      }
      Iterator iter = (Iterator) list.iterator();
      while (iter.hasNext()) {
        ProductCategory ctgry = (ProductCategory) iter.next();
        ProductCategoryList fullName = new ProductCategoryList();
        fullName = ctgry.buildFullName(db, fullName, ctgry.getId(), true);
        ctgry.setFullPath(fullName);
        ctgry.buildProductList(db);
      }
      context.getRequest().setAttribute("categoryList", list);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewCategoriesOK");
  }
}


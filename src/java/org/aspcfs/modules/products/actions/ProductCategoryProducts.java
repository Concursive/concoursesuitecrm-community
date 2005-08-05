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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCatalogList;
import org.aspcfs.modules.products.base.ProductCategory;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created August 31, 2004
 */
public final class ProductCategoryProducts extends CFSModule {
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    Connection db = null;
    ProductCategory category = null;
    ProductCatalogList catalogList = null;
    PagedListInfo catalogListInfo = this.getPagedListInfo(
        context, "ProductCatalogListInfo");
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
      catalogListInfo.setLink(
          "ProductCategoryProducts.do?command=List&moduleId=" + moduleId + "&categoryId=" + categoryId);

      catalogList = new ProductCatalogList();
      catalogList.setPagedListInfo(catalogListInfo);
      catalogList.setCategoryId(categoryId);
      catalogList.buildList(db);
      context.getRequest().setAttribute("ProductList", catalogList);

      ProductCatalogList completeList = new ProductCatalogList();
      completeList.setCategoryId(categoryId);
      completeList.buildList(db);
      context.getRequest().setAttribute("CompleteList", completeList);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "SystemError";
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
  public String executeCommandAdd(ActionContext context) {
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
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "SystemError";
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
  public String executeCommandOptionSource(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String option = context.getRequest().getParameter("choice");
      if (option != null) {
        if ("old".equals(option.trim())) {
          String categoryId = context.getRequest().getParameter("categoryId");
          // selectedElements into the session
          HashMap selectedElements = new HashMap();
          if (categoryId != null) {
            ProductCatalogList productList = new ProductCatalogList();
            productList.setCategoryId(categoryId);
            productList.buildList(db);
            Iterator i = productList.iterator();
            while (i.hasNext()) {
              ProductCatalog thisCatalog = (ProductCatalog) i.next();
              selectedElements.put(new Integer(thisCatalog.getId()), "");
            }
            context.getSession().setAttribute(
                "selectedElements", selectedElements);
          }
          return (executeCommandProductList(context));
        } else {
          return (executeCommandAddProduct(context));
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "SystemError";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProductList(ActionContext context) {
    Connection db = null;
    PagedListInfo catalogListInfo = this.getPagedListInfo(
        context, "ProductCatalogListInfo");
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

      HashMap selectedList = new HashMap();
      HashMap finalElementList = (HashMap) context.getSession().getAttribute(
          "finalElements");
      if (context.getRequest().getParameter("previousSelection") != null) {
        StringTokenizer st = new StringTokenizer(
            context.getRequest().getParameter("previousSelection"), "|");
        while (st.hasMoreTokens()) {
          selectedList.put(new Integer(st.nextToken()), "");
        }
      } else {
        // get the selected list from the session
        selectedList = (HashMap) context.getSession().getAttribute(
            "selectedElements");
      }
      int rowCount = 1;
      while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
        int elementId = 0;
        elementId = Integer.parseInt(
            context.getRequest().getParameter("hiddenelementid" + rowCount));
        if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
          if (selectedList.get(new Integer(elementId)) == null) {
            selectedList.put(new Integer(elementId), "");
          }
        } else {
          selectedList.remove(new Integer(elementId));
        }
        rowCount++;
      }
      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase(
            "true")) {
          finalElementList = (HashMap) selectedList.clone();
          System.out.println("finalElements : " + finalElementList);
          context.getSession().setAttribute("finalElements", finalElementList);
          return (executeCommandAddCategoryMappings(context));
        }
      }
      // build the option list
      ProductCatalogList catalogList = new ProductCatalogList();
      catalogListInfo.setLink(
          "ProductCategoryProducts.do?command=ProductList&moduleId=" + moduleId + "&categoryId=" + categoryId);
      catalogList.setPagedListInfo(catalogListInfo);
      catalogList.buildList(db);
      context.getRequest().setAttribute("ProductList", catalogList);
      context.getSession().setAttribute("selectedElements", selectedList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ProductListOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddProduct(ActionContext context) {
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
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "AddProductOK";
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
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String categoryId = context.getRequest().getParameter("categoryId");
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("ProductCategory", category);

      // remove the existing list of options for this product
      ProductCatalogList oldList = new ProductCatalogList();
      oldList.setCategoryId(categoryId);
      oldList.buildList(db);

      // add new product option mappings
      ProductCatalogList newList = new ProductCatalogList();
      String finalElements = context.getRequest().getParameter(
          "finalElements");
      if (finalElements != null) {
        StringTokenizer st = new StringTokenizer(finalElements, ",");
        while (st.hasMoreTokens()) {
          String id = String.valueOf(st.nextToken());
          newList.add(new ProductCatalog(db, Integer.parseInt(id)));
        }
      }

      newList.addCategoryMapping(db, oldList, category.getId());

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandList(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    ProductCategory category = null;
    ProductCatalog insCatalog = null;
    ProductCatalog newCatalog = (ProductCatalog) context.getFormBean();
    newCatalog.setEnteredBy(getUserId(context));
    newCatalog.setModifiedBy(getUserId(context));

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
      isValid = this.validateObject(context, db, newCatalog);
      if (isValid) {
        recordInserted = newCatalog.insert(db);
      }
      if (recordInserted && isValid) {
        insCatalog = new ProductCatalog(db, newCatalog.getId());
        category.addCatalog(db, insCatalog.getId());
        context.getRequest().setAttribute("ProductCatalog", insCatalog);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
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
  public String executeCommandDetails(ActionContext context) {
    Connection db = null;
    ProductCategory category = null;
    ProductCatalog catalog = null;
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

      String catalogId = context.getRequest().getParameter("catalogId");
      catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "DetailsOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    ProductCatalog catalog = null;
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

      int catalogId = Integer.parseInt(
          context.getRequest().getParameter("catalogId"));
      catalog = new ProductCatalog(db, catalogId);
      context.getRequest().setAttribute("ProductCatalog", catalog);
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
    Connection db = null;
    int recordUpdated = -1;
    boolean isValid = false;
    ProductCatalog updatedCatalog = null;
    ProductCatalog catalog = (ProductCatalog) context.getFormBean();
    catalog.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String categoryId = context.getRequest().getParameter("categoryId");
      ProductCategory category = new ProductCategory(
          db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("ProductCategory", category);
      isValid = this.validateObject(context, db, catalog);
      if (isValid) {
        recordUpdated = catalog.update(db);
      }
      if (recordUpdated > 0 && isValid) {
        updatedCatalog = new ProductCatalog(db, catalog.getId());
        context.getRequest().setAttribute("ProductCatalog", updatedCatalog);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated > 0 && isValid) {
      return (executeCommandDetails(context));
    }
    return (executeCommandModify(context));
  }
}


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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.products.base.ProductCategory;
import org.aspcfs.modules.products.base.ProductCategoryList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Creates a List of Product Categories for display within a popup <br>
 * Can be used in two variants: Single/Multiple<br>
 * Single and Multiple define if multiple Categories can be selected or just a
 * single one
 *
 * @author ananth
 * @version $Id$
 * @created October 11, 2004
 */
public final class ProductCategorySelector extends CFSModule {
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListProductCategories(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    String listType = context.getRequest().getParameter("listType");
    ProductCategory root = null;
    ProductCategoryList categoryList = null;
    ProductCategoryList finalCategories = null;
    ArrayList trailIds = new ArrayList();
    ArrayList trailValues = new ArrayList();
    ArrayList ignoredList = new ArrayList();
    ArrayList selectedList = new ArrayList();

    String prevSelection = context.getRequest().getParameter(
        "previousSelection");
    if (prevSelection != null) {
      StringTokenizer st = new StringTokenizer(prevSelection, "|");
      while (st.hasMoreTokens()) {
        selectedList.add(String.valueOf(st.nextToken()));
      }
    }

    String ignoreIds = context.getRequest().getParameter("ignoreIds");
    if (ignoreIds != null) {
      StringTokenizer st = new StringTokenizer(ignoreIds, "|");
      while (st.hasMoreElements()) {
        ignoredList.add(String.valueOf(st.nextToken()));
      }
    }

    try {
      db = this.getConnection(context);
      int rowCount = 1;
      String params = (String) context.getRequest().getParameter("params");
      String categoryId = (String) context.getRequest().getParameter(
          "categoryId");
      String catName = (String) context.getRequest().getParameter("catName");
      String catMaster = (String) context.getRequest().getParameter(
          "catMaster");

      PagedListInfo categoryListInfo = this.getPagedListInfo(
          context, "ProductCategoryListInfo");
      categoryListInfo.setLink("ProductCategories.do?command=CategoryList");

      categoryList = new ProductCategoryList();

      if ("list".equals(listType)) {
        while (context.getRequest().getParameter(
            "hiddenCategoryId" + rowCount) != null) {
          int catId = Integer.parseInt(
              context.getRequest().getParameter("hiddenCategoryId" + rowCount));
          if (context.getRequest().getParameter("category" + rowCount) != null) {
            if (!selectedList.contains(String.valueOf(catId))) {
              selectedList.add(String.valueOf(catId));
            }
          } else {
            selectedList.remove(String.valueOf(catId));
          }
          rowCount++;
        }
      }

      if ("true".equals(
          (String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(
              context.getRequest().getParameter("rowcount"));
          int catId = Integer.parseInt(
              context.getRequest().getParameter("hiddenCategoryId" + rowCount));
          selectedList.clear();
          selectedList.add(String.valueOf(catId));
        }
        listDone = true;
        if (finalCategories == null) {
          finalCategories = new ProductCategoryList();
        }
        for (int i = 0; i < selectedList.size(); i++) {
          int catId = Integer.parseInt((String) selectedList.get(i));
          finalCategories.add(new ProductCategory(db, catId));
        }
      }

      categoryList.setPagedListInfo(categoryListInfo);
      if (catMaster != null && !"".equals(catMaster.trim()) && Integer.parseInt(
          catMaster) > -1) {
        // will not include this category in the resulting category list
        categoryList.setInclude(false);
        categoryList.setId(catMaster);
      }
      categoryList.setBuildChildList(true);
      categoryList.setTypeId(categoryListInfo.getFilterKey("listFilter1"));
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(
          categoryId) != -1) {
        root = new ProductCategory(db, Integer.parseInt(categoryId));
        categoryList.setParentId(Integer.parseInt(categoryId));

        // Category trails
        ProductCategory cat = new ProductCategory(
            db, Integer.parseInt(categoryId));
        trailIds.add(0, String.valueOf(cat.getId()));
        trailValues.add(0, String.valueOf(cat.getName()));
        while (cat.getParentId() != -1) {
          cat = new ProductCategory(db, cat.getParentId());
          trailIds.add(0, String.valueOf(cat.getId()));
          trailValues.add(0, String.valueOf(cat.getName()));
        }
      } else {
        categoryList.setTopOnly(Constants.TRUE);
      }
      categoryList.buildList(db);
      // remove the categories in the ignoreList
      Iterator s = ignoredList.iterator();
      while (s.hasNext()) {
        String id = (String) s.next();
        Iterator t = categoryList.iterator();
        while (t.hasNext()) {
          ProductCategory thisCategory = (ProductCategory) t.next();
          if (thisCategory.getId() == Integer.parseInt(id)) {
            t.remove();
          }
        }
      }

      context.getRequest().setAttribute("TrailIds", trailIds);
      context.getRequest().setAttribute("TrailValues", trailValues);
      context.getRequest().setAttribute("ParentCategory", root);
      context.getRequest().setAttribute("selected", categoryId);

      LookupList typeSelect = new LookupList(
          db, "lookup_product_category_type");
      typeSelect.addItem(-1, " -- All --");
      typeSelect.setJsEvent(
          "onChange=\"document.categoryListView.submit();\"");
      context.getRequest().setAttribute("CategoryTypeList", typeSelect);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("CategoryList", categoryList);
      context.getRequest().setAttribute("SelectedCategories", selectedList);
      context.getRequest().setAttribute("IgnoredCategories", ignoredList);
      if (listDone) {
        context.getRequest().setAttribute("FinalCategories", finalCategories);
      }
      return "ListCategoriesOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }
}


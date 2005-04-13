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

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;

import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.admin.base.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.products.base.*;
/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    August 3, 2004
 *@version    $Id: ProductCatalogEditor.java,v 1.1.4.1 2004/10/18 19:56:27
 *      mrajkowski Exp $
 */
public final class ProductCatalogEditor extends CFSModule {
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return "DefaultOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandOptions(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "OptionsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    Connection db = null;
    ProductCategory thisCategory = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      ProductCategoryList categoryList = new ProductCategoryList();
      ProductCatalogList productList = new ProductCatalogList();
      
      String categoryId = context.getRequest().getParameter("categoryId");
      
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        categoryList.setParentId(Integer.parseInt(categoryId));
        productList.setCategoryId(Integer.parseInt(categoryId));
        
        thisCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("parentCategory", thisCategory);
        // Category Trails
        ProductCategories.buildHierarchy(db, context);
      } else {
        // build top level category list and products without a category 
        categoryList.setTopOnly(Constants.TRUE);
        productList.setHasCategories(Constants.FALSE);
      }
      categoryList.buildList(db);
      
      productList.setBuildActivePrice(true);
      productList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("productList", productList);
    } catch (Exception e) {
      // TODO: set the appropriate error message
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }
}


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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.modules.quotes.base.QuoteProductList;
import org.aspcfs.modules.quotes.base.QuoteProductOption;
import org.aspcfs.modules.quotes.base.QuoteProductOptionList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.products.configurator.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    May 5, 2004
 *@version    $Id: ProductsCatalog.java,v 1.2 2004/05/04 15:52:27 mrajkowski Exp
 *      $
 */
public final class ProductsCatalog extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandCategories(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCategoryList(ActionContext context) {
    ProductCategoryList categoryList = null;
    Connection db = null;
    try {
      db = getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId != null && !"".equals(moduleId)) {
        PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
        context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      }
      String displayFieldId = (String) context.getRequest().getParameter("displayFieldId");
      String hiddenFieldId = (String) context.getRequest().getParameter("hiddenFieldId");
      categoryList = new ProductCategoryList();
      categoryList.setTypeName("Publication");
      categoryList.setEnabled(Constants.TRUE);
      categoryList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProductList(ActionContext context) {
    int categoryId = Integer.parseInt((String) context.getRequest().getParameter("id"));
    String displayFieldId = (String) context.getRequest().getParameter("displayFieldId");
    String hiddenFieldId = (String) context.getRequest().getParameter("hiddenFieldId");
    ProductCategoryList trails = new ProductCategoryList();
    ProductCategory category = null;
    Connection db = null;
    try {
      db = getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      if (moduleId != null && !"".equals(moduleId)) {
        PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
        context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      }

      category = new ProductCategory();
      category.setBuildChildList(true);
      category.setBuildProductList(true);
      category.setBuildEnabledProducts(Constants.TRUE);
      category.queryRecord(db, categoryId);

      int parentId = category.getParentId();
      while (parentId != -1) {
        ProductCategory parent = new ProductCategory(db, parentId);
        trails.add(0, parent);
        parentId = parent.getParentId();
      }
      context.getRequest().setAttribute("category", category);
      context.getRequest().setAttribute("trails", trails);
      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ProductListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCategories(ActionContext context) {
    Quote quote = null;
    ProductCategory thisCategory = null;
    Connection db = null;
    try {
      db = getConnection(context);
      ProductCategoryList categoryList = new ProductCategoryList();
      ProductCatalogList productList = new ProductCatalogList();
      
      String quoteId = context.getRequest().getParameter("quoteId");
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
      if (categoryList.size() > 0) {
        categoryList.removeNonProductCategories(db);
      }
      productList.setEnabled(Constants.TRUE);
      productList.setBuildResources(true);
      productList.setBuildActivePrice(true);
      productList.setBuildEnabledProductsOnly(Constants.TRUE);
      productList.buildList(db);
      
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("productList", productList);
        
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      context.getRequest().setAttribute("quote", quote);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "CategoriesOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddProducts(ActionContext context) {
    Quote quote = null;
    QuoteProductList quoteProductList = null;
    Connection db = null;
    try {
      db = getConnection(context);
      String quoteId = context.getRequest().getParameter("quoteId");
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      quoteProductList = new QuoteProductList();
      quoteProductList.populate(db, context);
      context.getRequest().setAttribute("quoteProductList", quoteProductList);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory thisCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("parentCategory", thisCategory);
        // Category Trails
        ProductCategories.buildHierarchy(db, context);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddProductsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddOptions(ActionContext context) {
    String categoryIdString = (String) context.getRequest().getParameter("categoryId");
    Quote quote = null;
    boolean isValid = false;
    QuoteProduct quoteProduct = null;
    QuoteProductOption quoteProductOption = null;
    ProductCategory category = null;
    Connection db = null;
    try {
      db = getConnection(context);
      String quoteId = context.getRequest().getParameter("quoteId");
      quote = new Quote(db, Integer.parseInt(quoteId));
      for (int i = 1; ; i++) {
        String productIdString = (String) context.getRequest().getParameter("product_" + i);
        if (productIdString == null || "".equals(productIdString)) {
          break;
        } else {
          String quantityString = (String) context.getRequest().getParameter("qty_" + i);
          if (quantityString != null && !"".equals(quantityString)) {
            ProductCatalog product = new ProductCatalog(db, Integer.parseInt(productIdString));
            product.buildActivePrice(db);
            quoteProduct = new QuoteProduct();
            quoteProduct.setProductCatalog(product);
            quoteProduct.setProductId(product.getId());
            quoteProduct.setQuantity(quantityString);
            quoteProduct.setQuoteId(quote.getId());
            // Build the quoteProduct price from the product price
            quoteProduct.buildPricing(db);
            isValid = this.validateObject(context, db, quoteProduct);
            if (isValid) {
              QuoteProductOptionList optionList = new QuoteProductOptionList();
              for (int j = 1; ; j++) {
                String optionIdString = (String) context.getRequest().getParameter("option_" + productIdString + "_" + j);
                if (optionIdString != null && !"".equals(optionIdString)) {
                  ProductOption option = new ProductOption(db, Integer.parseInt(optionIdString.trim()));
                  quoteProductOption = new QuoteProductOption();
                  //Load the option configurator and verify users input and ask the
                  //configurator to determine the price for this option
                  OptionConfigurator configurator =
                                (OptionConfigurator) ProductOptionConfigurator.getConfigurator(db, option.getConfiguratorId());
                  
                  configurator.queryProperties(db, option.getId(), false);
                  if (configurator.validateUserInput(context.getRequest())) {
                    double priceAdjust = configurator.computePriceAdjust(context.getRequest());
                    quoteProductOption.setProductOptionId(option.getProductOptionId(db, product.getId()));
                    quoteProductOption.setQuantity(1);
                    quoteProductOption.setPriceAmount(priceAdjust);
                    quoteProductOption.setTotalPrice(priceAdjust);
                    isValid = this.validateObject(context, db, quoteProductOption);
                    if (isValid) {
                      quoteProductOption.setOptionId(option.getId());
                      quoteProductOption.setConfiguratorId(option.getConfiguratorId());
                      optionList.add(quoteProductOption);
                    } else {
                      break;
                    }
                  } else {
                    //check to see if user input is empty. If not empty
                    //return back to form
                    if (configurator.hasUserInput(context.getRequest())) {
                      SystemStatus systemStatus = this.getSystemStatus(context);
                      context.getRequest().setAttribute("actionError", systemStatus.getLabel("quoteProduct.option.validation.actionError"));
                      return (executeCommandAddProducts(context));
                    }
                  }
                } else {
                  break;
                }
              }
              //both quote product and its options are valid. Insert them both
              quoteProduct.insert(db, optionList, context.getRequest());
            }
          }
        }
      }
      quote.setBuildProducts(true);
      quote.queryRecord(db, Integer.parseInt(quoteId));
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      if (categoryIdString != null && !"".equals(categoryIdString) && !"-1".equals(categoryIdString)) {
        category = new ProductCategory(db, Integer.parseInt(categoryIdString));
        context.getRequest().setAttribute("category", category);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (isValid) {
      return "AddOptionsOK";
    }
    return executeCommandCategories(context);
  }


  /**
   *  Lists all ProductCatalogs
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListAllProducts(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-view")) {
    if (!hasPermission(context, "admin-sysconfig-products-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    ProductCatalogList productList = null;
    PagedListInfo productCatalogListInfo = this.getPagedListInfo(context, "productCatalogListInfo");
    productCatalogListInfo.setLink("ProductsCatalog.do?command=ListAllProducts&moduleId=" + moduleId);
    Connection db = null;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      productList = new ProductCatalogList();
      productList.setPagedListInfo(productCatalogListInfo);
      productList.setBuildResources(true);
      productList.buildList(db);
      context.getRequest().setAttribute("productList", productList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewProducts", "View Products");
    return "AllProductsOK";
  }


  /**
   *  View ProductCatalog details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewProductDetails(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-view")) {
    if (!hasPermission(context, "admin-sysconfig-products-view")) {
      return ("PermissionError");
    }
    ProductCategoryList categoryList = null;
    ProductCatalog productDetails = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      int id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);

      categoryList = new ProductCategoryList();
      categoryList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("productDetails", productDetails);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ViewProductOK";
  }


  /**
   *  Prepares the add ProductCatalog Screen
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddProduct(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-add")) {
    if (!hasPermission(context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    ProductCategoryList categoryList = null;
    ProductCatalog productDetails = new ProductCatalog();
    Connection db = null;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      categoryList = new ProductCategoryList();
      categoryList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("productDetails", productDetails);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "AddProduct", "Add Product");
    return "AddProductOK";
  }


  /**
   *  Saves a ProductCatalog details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveProduct(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-add")) {
    if (!hasPermission(context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    boolean inserted = false;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      ProductCatalog thisProduct = (ProductCatalog) context.getFormBean();
      thisProduct.setEnteredBy(getUserId(context));
      thisProduct.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, thisProduct);
      if (isValid) {
        // TODO: check if product inserted?
        inserted = thisProduct.insert(db);
      }
      thisProduct.addCategoryMapping(db, Integer.parseInt((String) context.getRequest().getParameter("categoryId")));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandListAllProducts(context);
  }


  /**
   *  Retrieves ProductCatalog details for modification
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyProduct(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-edit")) {
    if (!hasPermission(context, "admin-sysconfig-products-edit")) {
      return ("PermissionError");
    }
    ProductCategoryList categoryList = null;
    ProductCatalog productDetails = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      int id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);

      categoryList = new ProductCategoryList();
      categoryList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("productDetails", productDetails);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyProductOK";
  }


  /**
   *  Updates ProductCatalog details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateProduct(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-edit")) {
    if (!hasPermission(context, "admin-sysconfig-products-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    int inserted = -1;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      ProductCatalog thisProduct = (ProductCatalog) context.getFormBean();
      thisProduct.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, thisProduct);
      if (isValid) {
        inserted = thisProduct.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (isValid && inserted > -1) {
      return executeCommandListAllProducts(context);
    }
    return executeCommandModifyProduct(context);
  }


  /**
   *  Confirm deletion of product
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDeleteProduct(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-delete")) {
    if (!hasPermission(context, "admin-sysconfig-products-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog productDetails = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      SystemStatus systemStatus = this.getSystemStatus(context);
      int id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);

      htmlDialog.setTitle("Centric CRM: Products - Product");
      DependencyList dependencies = productDetails.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header3"));
        htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='ProductsCatalog.do?command=DeleteProduct&action=delete&productId=" + id + "&moduleId=" + moduleId + "'");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Delete ProductCatalog
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteProduct(ActionContext context) {
    //if (!hasPermission(context, "product-catalog-product-delete")) {
    if (!hasPermission(context, "admin-sysconfig-products-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    ProductCatalog productDetails = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    int id = -1;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);
      recordDeleted = productDetails.delete(db, "");

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      context.getRequest().setAttribute("actionError", systemStatus.getLabel("object.validation.actionError.productDeletion"));
      context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ViewProduct&productId=" + id + "&moduleId=" + moduleId);
      return ("DeleteError");
    } finally {
      this.freeConnection(context, db);
    }

    if (recordDeleted) {
      context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ListAllProducts&moduleId=" + moduleId);
      return getReturn(context, "Delete");
    }
    context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ViewProduct&productId=" + id + "&moduleId=" + moduleId);
    return getReturn(context, "Delete");
  }

  /**
   *  Prepares a multi select popup for labor categories
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    Connection db = null;
    String displayFieldId = null;
    ProductCatalogList productList = null;
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ProductCatalogSelectorInfo");
    }
    HashMap selectedList = new HashMap();
    
    String previousSelection = context.getRequest().getParameter("previousSelection");
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(context, "ProductCatalogSelectorInfo");
    if (previousSelection != null) {
      StringTokenizer st = new StringTokenizer(previousSelection, "|");
      StringTokenizer st1 = new StringTokenizer(context.getRequest().getParameter("previousSelectionDisplay"), "|");
      while (st.hasMoreTokens() && st1.hasMoreTokens()) {
        selectedList.put(new Integer(st.nextToken()), st1.nextToken());
      }
    }
    if (context.getRequest().getParameter("displayFieldId") != null) {
      displayFieldId = context.getRequest().getParameter("displayFieldId");
    }
    //Flush the selectedList if its a new selection
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      if (context.getSession().getAttribute("finalElements") != null && context.getRequest().getParameter("previousSelection") == null) {
        selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalElements")).clone();
      }
    }
    int rowCount = 1;
    while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
      int elementId = 0;
      String elementValue = "";
      elementId = Integer.parseInt(context.getRequest().getParameter("hiddenelementid" + rowCount));
      if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
        if (context.getRequest().getParameter("elementvalue" + rowCount) != null) {
          elementValue = context.getRequest().getParameter("elementvalue" + rowCount);
        }

        if (selectedList.get(new Integer(elementId)) == null) {
          selectedList.put(new Integer(elementId), elementValue);
        } else {
          selectedList.remove(new Integer(elementId));
          selectedList.put(new Integer(elementId), elementValue);
        }
      } else {
        selectedList.remove(new Integer(elementId));
      }
      rowCount++;
    }
    try {
      db = this.getConnection(context);
      productList = new ProductCatalogList();
      ProductCategoryList categoryList = new ProductCategoryList();
      
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        categoryList.setParentId(Integer.parseInt(categoryId));
        productList.setCategoryId(Integer.parseInt(categoryId));
        
        ProductCategory thisCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("parentCategory", thisCategory);
        // Category Trails
        ProductCategories.buildHierarchy(db, context);
      } else {
        // build top level category list and products without a category 
        categoryList.setTopOnly(Constants.TRUE);
        productList.setHasCategories(Constants.FALSE);
      }
      productList.setPagedListInfo(lookupSelectorInfo);
      productList.setBuildResources(false);
      productList.setSelectedItems(selectedList);
      
      String listType = context.getRequest().getParameter("listType");
      if (context.getRequest().getParameter("contractId") != null && !"".equals(context.getRequest().getParameter("contractId"))) {
        if ("single".equals(listType)) {
          categoryList.setServiceContractId(context.getRequest().getParameter("contractId"));
          productList.setServiceContractId(context.getRequest().getParameter("contractId"));
        }
      }
      productList.buildList(db);
      categoryList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      context.getRequest().setAttribute("productList", productList);
      
      if ("true".equals((String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
          int elementId = Integer.parseInt(context.getRequest().getParameter("hiddenelementid" + rowCount));
          String elementValue = context.getRequest().getParameter("elementvalue" + rowCount);
          selectedList.clear();
          selectedList.put(new Integer(elementId), elementValue);
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("selectedElements", selectedList);
    context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
    return ("PopupProductListOK");
  }

}


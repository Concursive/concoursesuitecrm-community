package org.aspcfs.modules.products.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.orders.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.quotes.base.*;
/**
 *  Description of the Class
 *
 *@author
 *@created
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
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      
      String params = (String) context.getRequest().getParameter("params");
      String displayFieldId = (String) context.getRequest().getParameter("displayFieldId");
      String hiddenFieldId = (String) context.getRequest().getParameter("hiddenFieldId");
      categoryList = new ProductCategoryList();
      categoryList.setHasProducts(Constants.TRUE);
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
    ProductCatalogList productList = null;
    ProductCategory category = null;
    Connection db = null;
    try {
      db = getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      
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
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    String categoryIdString = (String) context.getRequest().getParameter("categoryId");
    int categoryId = -1;

    Quote quote = null;
    ProductCategory category = null;
    ProductCategoryList categoryList = null;
    ProductCategoryList trailCategories = null;
    ProductCategory parentCategory = null;
    ProductCatalogList productList = null;

    Connection db = null;
    try {
      db = getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      
      if (categoryIdString != null && !"".equals(categoryIdString)) {

        categoryId = Integer.parseInt(categoryIdString);
        category = new ProductCategory(db, categoryId);
        context.getRequest().setAttribute("category", category);

        int parentId = category.getParentId();
        trailCategories = new ProductCategoryList();
        while (parentId != -1) {
          parentCategory = new ProductCategory(db, parentId);
          trailCategories.add(0, parentCategory);
          parentId = parentCategory.getParentId();
        }
        if (trailCategories != null) {
          context.getRequest().setAttribute("trailCategories", trailCategories);
        }

        categoryList = new ProductCategoryList();
        categoryList.setParentId(categoryId);
        categoryList.buildList(db);
        if(categoryList.size() > 0 ){
          categoryList.removeNonProductCategories(db);
        }

        productList = new ProductCatalogList();
        productList.setCategoryId(categoryId);
        productList.setEnabled(Constants.TRUE);
        productList.setBuildResources(true);
        productList.buildList(db);
      } else {
        //Starting case where we start with the Top level categories
        productList = new ProductCatalogList();
        productList.setHasCategories(Constants.FALSE);
        productList.setEnabled(Constants.TRUE);
        productList.setBuildResources(true);
        productList.buildList(db);
        
        categoryList = new ProductCategoryList();
        categoryList.setTopOnly(Constants.TRUE);
        categoryList.buildList(db);
        if(categoryList.size() > 0 ){
          categoryList.removeNonProductCategories(db);
        }
      }
      context.getRequest().setAttribute("productList", productList);
      context.getRequest().setAttribute("categoryList", categoryList);

      quote = new Quote(db, quoteId);
      quote.retrieveTicket(db);
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
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    int categoryId = Integer.parseInt((String) context.getRequest().getParameter("categoryId"));
    boolean displayOptions = false;
    Quote quote = null;
    ProductCategoryList trailCategories = null;
    ProductCategory parentCategory = null;
    ProductCategory category = null;
    QuoteProductList quoteProductList = null;
    Connection db = null;
    try {
      db = getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      quoteProductList = new QuoteProductList();
      quoteProductList.populate(db, context);
      context.getRequest().setAttribute("quoteProductList", quoteProductList);

      category = new ProductCategory(db, categoryId);
      context.getRequest().setAttribute("category", category);

      trailCategories = new ProductCategoryList();
      int parentId = category.getParentId();
      while (parentId != -1) {
        parentCategory = new ProductCategory(db, parentId);
        trailCategories.add(0, parentCategory);
        parentId = parentCategory.getParentId();
      }
      if (trailCategories != null) {
        context.getRequest().setAttribute("trailCategories", trailCategories);
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
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    int categoryId = Integer.parseInt((String) context.getRequest().getParameter("categoryId"));
    StringBuffer abbreviation = new StringBuffer("");
    Quote quote = null;
    QuoteProduct quoteProduct = null;
    QuoteProductList quoteProducts = null;
    QuoteProductOption quoteProductOption = null;
    ProductCategory category = null;
    ProductCatalogList productList = null;
    Connection db = null;
    try {
      db = getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      
      quote = new Quote(db, quoteId);
      if (quote.getShortDescription() != null) {
        abbreviation.append(quote.getShortDescription());
      }
      for (int i = 1; ; i++) {
        String productIdString = (String) context.getRequest().getParameter("product_" + i);
        if (productIdString == null || "".equals(productIdString)) {
          break;
        } else {
          String quantityString = (String) context.getRequest().getParameter("qty_" + i);
          if (quantityString != null && !"".equals(quantityString)) {
            ProductCatalog product = new ProductCatalog(db, Integer.parseInt(productIdString));
            quoteProduct = new QuoteProduct();
            quoteProduct.setProductCatalog(product);
            quoteProduct.setProductId(product.getId());
            quoteProduct.setQuantity(quantityString);
            quoteProduct.setQuoteId(quote.getId());
            quoteProduct.setPriceAmount(product.getPriceAmount());
            quoteProduct.setTotalPrice(product.getPriceAmount());
            quoteProduct.insert(db);
            abbreviation.append(product.getAbbreviation());
            String optionIdString = (String) context.getRequest().getParameter("option_" + productIdString);
            if (optionIdString != null && !"".equals(optionIdString)) {
              String valueString = (String) context.getRequest().getParameter("value_" + productIdString + "_" + optionIdString);
              if (valueString != null && !"".equals(valueString)) {
                String optionSelection = (String) context.getRequest().getParameter("selection_" + productIdString + "_" + optionIdString);
                if (!"".equals(optionSelection) && optionSelection != null) {
                  ProductOption option = new ProductOption(db, Integer.parseInt(optionIdString.trim()));
                  ProductOptionValues value = new ProductOptionValues(db, Integer.parseInt(optionSelection));
                  quoteProductOption = new QuoteProductOption();
                  quoteProductOption.setItemId(quoteProduct.getId());
                  quoteProductOption.setProductOptionId(option.getId());
                  quoteProductOption.setQuantity(1);
                  quoteProductOption.setPriceAmount(value.getPriceAmount());
                  quoteProductOption.setTotalPrice(value.getPriceAmount());
                  quoteProductOption.setIntegerValue(value.getId());
                  quoteProductOption.insert(db);
                } else {
                }
              }
            }
          }
        }
      }
      if (abbreviation != null && !"".equals(abbreviation.toString())) {
        quote.setShortDescription(abbreviation.toString());
        quote.update(db);
      }
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      category = new ProductCategory(db, categoryId);
      context.getRequest().setAttribute("category", category);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddOptionsOK";
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
      inserted = thisProduct.insert(db);

      int thisProductId = thisProduct.getId();
      thisProduct.addCategory(db, Integer.parseInt((String) context.getRequest().getParameter("categoryId")));
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
    int inserted = -1;
    try {
      db = this.getConnection(context);
      // Populate the permission category for trails
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);
      
      ProductCatalog thisProduct = (ProductCatalog) context.getFormBean();
      thisProduct.setModifiedBy(getUserId(context));
      inserted = thisProduct.update(db);
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
      
      int id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);

      htmlDialog.setTitle("Dark Horse CRM: Products - Product");
      DependencyList dependencies = productDetails.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      if (dependencies.canDelete()){
        htmlDialog.setHeader("The product you are requesting to delete has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='ProductsCatalog.do?command=DeleteProduct&action=delete&productId=" + id + "&moduleId=" + moduleId + "'");
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      }else{
        htmlDialog.setHeader("This product cannot be deleted because it has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
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
      context.getRequest().setAttribute("actionError", "Product could not be deleted because of referential integrity .");
      context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ViewProduct&productId=" + id + "&moduleId=" + moduleId);
      return ("DeleteError");
    } finally {
      this.freeConnection(context, db);
    }

    if (recordDeleted) {
      context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ListAllProducts&moduleId=" + moduleId);
      return this.getReturn(context, "Delete");
    }
    processErrors(context, productDetails.getErrors());
    context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ViewProduct&productId=" + id + "&moduleId=" + moduleId);
    return this.getReturn(context, "Delete");
  }


  /**
   *  Prepares a multi select popup for labor categories
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {

    Connection db = null;
    boolean listDone = false;
    String displayFieldId = null;
    ProductCatalogList productList = null;

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ProductCatalogSelectorInfo");
    }

    HashMap selectedList = new HashMap();

    String previousSelection = context.getRequest().getParameter("previousSelection");
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(context, "ProductCatalogSelectorInfo");
    lookupSelectorInfo.setLink("ProductsCatalog.do?command=PopupSelector");

    if (previousSelection != null) {
      StringTokenizer st = new StringTokenizer(previousSelection, "|");
      StringTokenizer st1 = new StringTokenizer(context.getRequest().getParameter("previousSelectionDisplay"), "|");
      while (st.hasMoreTokens()) {
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
      productList.setPagedListInfo(lookupSelectorInfo);
      productList.setBuildResources(true);
      productList.setSelectedItems(selectedList);
      productList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      context.getRequest().setAttribute("productList", productList);
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("selectedElements", selectedList);
    context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
    return ("PopupProductListOK");
  }

}


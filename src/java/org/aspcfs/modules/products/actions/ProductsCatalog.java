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
      String params = (String) context.getRequest().getParameter("params");
      String displayFieldId = (String) context.getRequest().getParameter("displayFieldId");
      String hiddenFieldId = (String) context.getRequest().getParameter("hiddenFieldId");

      System.out.println("hiddenFieldId : " + hiddenFieldId);
      System.out.println("displayFieldId : " + displayFieldId);

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

    ProductCatalogList productList = null;
    ProductCategory category = null;
    Connection db = null;
    try {
      db = getConnection(context);

      category = new ProductCategory(db, categoryId);
      context.getRequest().setAttribute("category", category);

      productList = new ProductCatalogList();
      productList.setPublicationId(categoryId);
      productList.setBuildResources(true);
      productList.buildList(db);

      context.getRequest().setAttribute("productList", productList);
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

    Quote quote = null;
    ProductCategoryList categoryList = null;
    Connection db = null;
    try {
      db = getConnection(context);

      categoryList = new ProductCategoryList();
      categoryList.setHasProducts(Constants.TRUE);
      categoryList.buildList(db);
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
  public String executeCommandProducts(ActionContext context) {
    int quoteId = Integer.parseInt((String) context.getRequest().getParameter("quoteId"));
    int categoryId = Integer.parseInt((String) context.getRequest().getParameter("categoryId"));
    Quote quote = null;
    ProductCatalogList productList = null;
    ProductCategory category = null;
    Connection db = null;
    try {
      db = getConnection(context);

      category = new ProductCategory(db, categoryId);
      context.getRequest().setAttribute("category", category);

      productList = new ProductCatalogList();
      productList.setPublicationId(categoryId);
      productList.setBuildResources(true);
      productList.buildList(db);
      context.getRequest().setAttribute("productList", productList);

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
    return "ProductsOK";
  }


  /**
   *  Lists all ProductCatalogs
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListAllProducts(ActionContext context) {
    if (!hasPermission(context, "product-catalog-product-view")) {
      return ("PermissionError");
    }
    ProductCatalogList productList = null;
    PagedListInfo productCatalogListInfo = this.getPagedListInfo(context, "productCatalogListInfo");
    productCatalogListInfo.setLink("ProductsCatalog.do?command=ListAllProducts");
    Connection db = null;
    try {
      db = this.getConnection(context);
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
    if (!hasPermission(context, "product-catalog-product-view")) {
      return ("PermissionError");
    }
    ProductCategoryList categoryList = null;
    ProductCatalog productDetails = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
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
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    ProductCategoryList categoryList = null;
    ProductCatalog productDetails = new ProductCatalog();
    Connection db = null;
    try {
      db = this.getConnection(context);
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
    if (!hasPermission(context, "product-catalog-product-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean inserted = false;
    try {
      db = this.getConnection(context);
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
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    ProductCategoryList categoryList = null;
    ProductCatalog productDetails = null;
    Connection db = null;
    try {
      db = this.getConnection(context);

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
    if (!hasPermission(context, "product-catalog-product-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int inserted = -1;
    try {
      db = this.getConnection(context);
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
    if (!hasPermission(context, "product-catalog-product-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductCatalog productDetails = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    try {
      db = this.getConnection(context);

      int id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);

      htmlDialog.setTitle("Dark Horse CRM: Products - Product");
      DependencyList dependencies = productDetails.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      if (dependencies.canDelete()){
        htmlDialog.setHeader("The product you are requesting to delete has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='ProductsCatalog.do?command=DeleteProduct&action=delete&productId=" + id + "'");
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
    if (!hasPermission(context, "product-catalog-product-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    ProductCatalog productDetails = null;
    int id = -1;
    try {
      db = this.getConnection(context);

      id = Integer.parseInt((String) context.getRequest().getParameter("productId"));
      productDetails = new ProductCatalog(db, id);
      recordDeleted = productDetails.delete(db, "");

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      context.getRequest().setAttribute("actionError", "Product could not be deleted because of referential integrity .");
      context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ViewProduct&productId=" + id);
      return ("DeleteError");
    } finally {
      this.freeConnection(context, db);
    }

    if (recordDeleted) {
      context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ListAllProducts");
      return this.getReturn(context, "Delete");
    }
    processErrors(context, productDetails.getErrors());
    context.getRequest().setAttribute("refreshUrl", "ProductsCatalog.do?command=ViewProduct&productId=" + id);
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


    try {
      db = this.getConnection(context);
      if (previousSelection != null) {
        int j = 0;
        StringTokenizer st = new StringTokenizer(previousSelection, "|");
        while (st.hasMoreTokens()) {
          int id = Integer.parseInt(st.nextToken());
          ProductCatalog pc = new ProductCatalog(db,id);
          selectedList.put(new Integer(id), pc.getSku());
          j++;
        }
      }
    }catch(Exception errorMessage){
      context.getRequest().setAttribute("Error", errorMessage);
    return ("SystemError");
    }finally{
      this.freeConnection(context, db);
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


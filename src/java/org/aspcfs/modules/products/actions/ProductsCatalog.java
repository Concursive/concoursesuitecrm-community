package org.aspcfs.modules.products.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.tasks.base.TaskList;
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
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.quotes.base.*;
/**
 *  Description of the Class
 *
 *@author     
 *@created    
 *@version    $Id$
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
}


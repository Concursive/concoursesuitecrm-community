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
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.modules.products.configurator.OptionProperty;
import org.aspcfs.modules.products.configurator.OptionPropertyList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.modules.base.DependencyList;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    September 10, 2004
 *@version    $Id: ProductCatalogOptions.java,v 1.1.4.3.2.1 2005/03/01 04:51:08
 *      ananth Exp $
 */
public final class ProductCatalogOptions extends CFSModule {
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOptionList optionList = new ProductOptionList();

    PagedListInfo optionListInfo = this.getPagedListInfo(context, "ProductCatalogOptionListInfo");
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
      
      optionListInfo.setLink("ProductCatalogOptions.do?command=List&moduleId=" + moduleId + "&productId=" + productId + "&categoryId=" + categoryId);
      optionList.setPagedListInfo(optionListInfo);
      optionList.setProductId(productId);
      optionList.setBuildConfigDetails(true);
      optionList.buildList(db);
      context.getRequest().setAttribute("OptionList", optionList);

      ProductOptionList completeList = new ProductOptionList();
      completeList.setProductId(productId);
      completeList.buildList(db);
      context.getRequest().setAttribute("CompleteList", completeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOptionConfiguratorList configList = new ProductOptionConfiguratorList();
    ProductOptionList optionList = new ProductOptionList();
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      configList.buildList(db);
      context.getRequest().setAttribute("configuratorList", configList);

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "AddOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption();
      option.setBuildConfigDetails(true);
      option.queryRecord(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("productOption", option);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(db, option.getConfiguratorId());
      // query the properties
      configurator.queryProperties(db, option.getId(), true);
      OptionPropertyList propertyList = configurator.getPropertyList();
      context.getRequest().setAttribute("propertyList", propertyList);
      String configName = configurator.getName();
      context.getRequest().setAttribute("configName", configName);
      boolean allowMultiplePrices = configurator.getAllowMultiplePrices();
      context.getRequest().setAttribute("allowMultiplePrices", new String(allowMultiplePrices ? "true" : "false"));

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "DetailsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandOptionSource(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String option = context.getRequest().getParameter("choice");
      if (option != null) {
        if ("old".equals(option.trim())) {
          String catalogId = context.getRequest().getParameter("catalogId");
          // selectedElements into the session
          HashMap selectedElements = new HashMap();
          if (catalogId != null) {
            ProductOptionList optionList = new ProductOptionList();
            optionList.setProductId(catalogId);
            optionList.buildList(db);
            Iterator i = optionList.iterator();
            while (i.hasNext()) {
              ProductOption thisOption = (ProductOption) i.next();
              selectedElements.put(new Integer(thisOption.getId()), "");
            }
            context.getSession().setAttribute("selectedElements", selectedElements);
          }
          return (executeCommandOptionList(context));
        } else {
          return (executeCommandLoadConfigurator(context));
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandOptionList(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    PagedListInfo optionListInfo = this.getPagedListInfo(context, "ProductCatalogOptionListInfo");
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String configId = context.getRequest().getParameter("oldConfigId");
      context.getRequest().setAttribute("configId", configId);

      HashMap selectedList = new HashMap();
      HashMap finalElementList = (HashMap) context.getSession().getAttribute("finalElements");
      if (context.getRequest().getParameter("previousSelection") != null) {
        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
        while (st.hasMoreTokens()) {
          selectedList.put(new Integer(st.nextToken()), "");
        }
      } else {
        // get the selected list from the session
        selectedList = (HashMap) context.getSession().getAttribute("selectedElements");
      }
      int rowCount = 1;
      while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
        int elementId = 0;
        elementId = Integer.parseInt(context.getRequest().getParameter("hiddenelementid" + rowCount));
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
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
          finalElementList = (HashMap) selectedList.clone();
          System.out.println("finalElements : " + finalElementList);
          context.getSession().setAttribute("finalElements", finalElementList);
          return (executeCommandAddOptionMappings(context));
        }
      }
      // build the option list
      ProductOptionList optionList = new ProductOptionList();
      optionListInfo.setLink("ProductCatalogOptions.do?command=OptionList&moduleId=" + moduleId + "&catalogId=" + catalogId + "&configId=" + configId);
      optionList.setPagedListInfo(optionListInfo);
      optionList.setConfiguratorId(configId);
      optionList.buildList(db);
      context.getRequest().setAttribute("OptionList", optionList);
      context.getSession().setAttribute("selectedElements", selectedList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "OptionListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddOptionMappings(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      // remove the existing list of options for this product
      ProductOptionList oldList = new ProductOptionList();
      oldList.setProductId(catalogId);
      oldList.buildList(db);

      // add new product option mappings
      ProductOptionList newList = new ProductOptionList();
      String finalElements = context.getRequest().getParameter("finalElements");
      if (finalElements != null) {
        StringTokenizer st = new StringTokenizer(finalElements, ",");
        while (st.hasMoreTokens()) {
          String id = String.valueOf(st.nextToken());
          newList.add(new ProductOption(db, Integer.parseInt(id)));
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
    return (executeCommandList(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRemoveMapping(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));

      option.removeProductMapping(db, Integer.parseInt(productId));

      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOption option = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String productId = context.getRequest().getParameter("productId");
    String optionId = context.getRequest().getParameter("optionId");

    HtmlDialog htmlDialog = new HtmlDialog();
    Exception errorMessage = null;
    String returnUrl = context.getRequest().getParameter("return");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      option = new ProductOption(db, Integer.parseInt(optionId));
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      DependencyList dependencies = option.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      if (dependencies.canDelete()) {
        htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='ProductCatalogOptions.do?command=Delete&optionId=" + optionId + "&action=delete&return=" + returnUrl + "&moduleId=" + (moduleId != null ? moduleId : "") + "&categoryId=" + (categoryId != null ? categoryId : "") + "&productId=" + (productId != null ? productId : "") + "'");
      }
      htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String productId = context.getRequest().getParameter("productId");
    String returnUrl = context.getRequest().getParameter("return");
    returnUrl += "&moduleId=" + moduleId + "&categoryId=" + (categoryId != null ? categoryId : "") + "&productId=" + (productId != null ? productId : "");
    try {
      db = getConnection(context);
      //delete the option
      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      recordDeleted = option.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", returnUrl);
        return "DeleteOK";
      } else {
        return "SystemError";
      }
    } else {
      context.getRequest().setAttribute("actionError", this.getSystemStatus(context).getLabel("object.validation.actionError.productOptionDeletion"));
      context.getRequest().setAttribute("refreshUrl", returnUrl);
      return ("DeleteError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddNew(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOptionConfiguratorList configList = new ProductOptionConfiguratorList();

    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      configList.buildList(db);
      context.getRequest().setAttribute("ConfiguratorList", configList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "AddNewOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandLoadConfigurator(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOption productOption = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      String configId = context.getRequest().getParameter("configId");
      context.getRequest().setAttribute("configId", configId);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(db, Integer.parseInt(configId));

      if (context.getRequest().getParameter("action") != null) {
        //Update action on an existing option
        String optionId = context.getRequest().getParameter("optionId");
        configurator.queryProperties(db, Integer.parseInt(optionId), false);
        if (context.getRequest().getAttribute("productOption") == null) {
          productOption = new ProductOption(db, Integer.parseInt(optionId));
        } else {
          productOption = (ProductOption) context.getRequest().getAttribute("productOption");
        }
        productOption.setLabel(configurator.getLabel());
        context.getRequest().setAttribute("productOption", productOption);
      }
      // get the property list
      OptionPropertyList propertyList = null;
      if (context.getRequest().getAttribute("PropertyList") != null) {
        propertyList = (OptionPropertyList) context.getRequest().getAttribute("PropertyList");
      } else {
        propertyList = configurator.getPropertyList();
      }
      Iterator i = propertyList.iterator();
      while (i.hasNext()) {
        OptionProperty thisProperty = (OptionProperty) i.next();
        thisProperty.prepareContext(db, context.getRequest());
      }
      context.getRequest().setAttribute("PropertyList", propertyList);
      String configName = configurator.getName();
      context.getRequest().setAttribute("configName", configName);
      boolean allowMultiplePrices = configurator.getAllowMultiplePrices();
      context.getRequest().setAttribute("allowMultiplePrices", new String(allowMultiplePrices ? "true" : "false"));
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "LoadConfiguratorOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertOption(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOption option = (ProductOption) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean isValid = false;
    try {
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      String configId = context.getRequest().getParameter("configId");
      context.getRequest().setAttribute("configId", configId);
      option.setConfiguratorId(configId);

      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(db, Integer.parseInt(configId));
      // set the properties from values obtained in the request
      try {
        configurator.setProperties(context.getRequest());
      } catch (Exception exp) {
        //Dont do anything, configurator anyway checks to see if the property list is valid and these errors will be noted
      }
      boolean allowMultiplePrices = configurator.getAllowMultiplePrices();
      context.getRequest().setAttribute("allowMultiplePrices", new String(allowMultiplePrices ? "true" : "false"));

      if (!configurator.getPropertyList().isValid(systemStatus)) {
        context.getRequest().setAttribute("PropertyList", configurator.getPropertyList());
        context.getRequest().setAttribute("productOption", option);
        context.getRequest().setAttribute("actionError", systemStatus.getLabel("object.validation.genericActionError"));
        return (executeCommandLoadConfigurator(context));
      }
      //validate and save the properties
      if (this.validateObject(context, db, option)) {
        if (this.validateObject(context, db, configurator)) {
          isValid = true;
        }
      }
      if (isValid) {
        configurator.saveProperties(db, option);
        option.setLabel(configurator.getLabel());
        context.getRequest().setAttribute("productOption", option);
        context.getRequest().setAttribute("propertyList", configurator.getPropertyList());
      } else {
        context.getRequest().setAttribute("PropertyList", configurator.getPropertyList());
        context.getRequest().setAttribute("productOption", option);
        return (executeCommandLoadConfigurator(context));
      }
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "InsertOptionOK";
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateOption(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-products-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    ProductOption option = (ProductOption) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean isValid = false;
    try {
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String productId = context.getRequest().getParameter("productId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(productId));
      context.getRequest().setAttribute("productCatalog", catalog);

      String configId = context.getRequest().getParameter("configId");
      context.getRequest().setAttribute("configId", configId);
      option.setConfiguratorId(configId);

      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(db, Integer.parseInt(configId));
      // set the properties from values obtained in the request
      try {
        configurator.setProperties(context.getRequest());
      } catch (Exception exp) {
        //Dont do anything, configurator anyway checks to see if the property list is valid and these errors will be noted
      }
      boolean allowMultiplePrices = configurator.getAllowMultiplePrices();
      context.getRequest().setAttribute("allowMultiplePrices", new String(allowMultiplePrices ? "true" : "false"));

      if (!configurator.getPropertyList().isValid(systemStatus)) {
        context.getRequest().setAttribute("PropertyList", configurator.getPropertyList());
        context.getRequest().setAttribute("productOption", option);
        context.getRequest().setAttribute("actionError", systemStatus.getLabel("object.validation.genericActionError"));
        return (executeCommandLoadConfigurator(context));
      }

      //validate and save the properties
      if (this.validateObject(context, db, option)) {
        if (this.validateObject(context, db, configurator)) {
          isValid = true;
        }
      }
      if (isValid) {
        configurator.updateProperties(db, option);
        option.setLabel(configurator.getLabel());
        context.getRequest().setAttribute("productOption", option);
        context.getRequest().setAttribute("propertyList", configurator.getPropertyList());
      } else {
        context.getRequest().setAttribute("PropertyList", configurator.getPropertyList());
        context.getRequest().setAttribute("productOption", option);
        return (executeCommandLoadConfigurator(context));
      }
      String categoryId = context.getRequest().getParameter("categoryId");
      if (categoryId != null && !"".equals(categoryId.trim()) && Integer.parseInt(categoryId) != -1) {
        ProductCategory parentCategory = new ProductCategory(db, Integer.parseInt(categoryId));
        ProductCategories.buildHierarchy(db, context);
        context.getRequest().setAttribute("productCategory", parentCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "UpdateOptionOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPricingList(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(db, option.getConfiguratorId());
      // query the properties for this option
      configurator.queryProperties(db, option.getId(), false);
      // retrieve the result labels array

      // populate the pricing list
      ProductOptionValuesList pricingList = new ProductOptionValuesList();
      pricingList.setOptionId(optionId);
      pricingList.buildList(db);
      context.getRequest().setAttribute("PricingList", pricingList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "PricingListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandValueList(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      String resultId = context.getRequest().getParameter("resultId");
      String displayValue = context.getRequest().getParameter("displayValue");

      // populate the pricing list
      ProductOptionValuesList pricingList = new ProductOptionValuesList();
      if (optionId != null && resultId != null) {
        pricingList.setOptionId(optionId);
        pricingList.setResultId(resultId);
        pricingList.buildList(db);
      }
      context.getRequest().setAttribute("resultId", resultId);
      context.getRequest().setAttribute("DisplayText", displayValue);
      context.getRequest().setAttribute("PricingList", pricingList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "ValueListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddPricing(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      int nextRangeMin = ProductOption.getNextRangeMin(db, option.getId());
      context.getRequest().setAttribute("nextRangeMin", String.valueOf(nextRangeMin));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "AddPricingOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertPricing(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    try {
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permissionCategory);

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      ProductOptionValues value = (ProductOptionValues) context.getFormBean();
      isValid = this.validateObject(context, db, value);
      if (isValid) {
        value.insert(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (isValid) {
      return (executeCommandPricingList(context));
    }
    return executeCommandAddPricing(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddMapping(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    try {
      db = getConnection(context);

      String moduleId = context.getRequest().getParameter("moduleId");

      String catalogId = context.getRequest().getParameter("catalogId");
      ProductCatalog catalog = new ProductCatalog(db, Integer.parseInt(catalogId));
      context.getRequest().setAttribute("ProductCatalog", catalog);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      String valueId = context.getRequest().getParameter("valueId");
      if (valueId != null) {
        // update product_option_mapping table
        //option.addProductValue(db, catalog.getId(), Integer.parseInt(resultId), Integer.parseInt(valueId));
        String refreshUrl = "ProductCatalogOptionPricings.do?command=PricingList&catalogId=" + catalogId + "&moduleId=" + moduleId + "&optionId=" + optionId;
        context.getRequest().setAttribute("refreshUrl", refreshUrl);
      } else {
        // Insert a new product option value and also update the
        // product_option_mapping table
        ProductOptionValues value = (ProductOptionValues) context.getFormBean();
        isValid = this.validateObject(context, db, value);
        if (isValid) {
          value.insert(db);
        }
        //option.addProductValue(db, catalog.getId(), Integer.parseInt(resultId), value.getId());
        String refreshUrl = "ProductCatalogOptionPricings.do?command=PricingList&catalogId=" + catalogId + "&moduleId=" + moduleId + "&optionId=" + optionId;
        context.getRequest().setAttribute("refreshUrl", refreshUrl);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "AddMappingOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRemovePricing(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandPricingList(context));
  }
}


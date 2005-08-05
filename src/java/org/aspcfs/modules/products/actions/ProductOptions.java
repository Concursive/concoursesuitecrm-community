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
import org.aspcfs.modules.products.base.ProductOption;
import org.aspcfs.modules.products.base.ProductOptionConfigurator;
import org.aspcfs.modules.products.base.ProductOptionConfiguratorList;
import org.aspcfs.modules.products.base.ProductOptionList;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.modules.products.configurator.OptionProperty;
import org.aspcfs.modules.products.configurator.OptionPropertyList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created September 2, 2004
 */
public final class ProductOptions extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    Connection db = null;
    ProductOptionConfiguratorList configList = new ProductOptionConfiguratorList();

    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      configList.buildList(db);
      HtmlSelect configSelect = configList.getHtmlSelect();
      configSelect.addItem(-1, "All Types", 0);
      context.getRequest().setAttribute("ConfigSelect", configSelect);
      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
      PagedListInfo optionListInfo = this.getPagedListInfo(
          context, "SearchProductOptionListInfo");
      optionListInfo.setCurrentLetter("");
      optionListInfo.setCurrentOffset(0);
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
    ProductOptionList optionList = new ProductOptionList();

    // Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchProductOptionListInfo");
    this.resetPagedListInfo(context);
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      searchListInfo.setLink(
          "ProductOptions.do?command=Search&moduleId=" + moduleId);
      optionList.setPagedListInfo(searchListInfo);
      optionList.setConfiguratorId(searchListInfo.getFilterKey("listFilter1"));
      searchListInfo.setSearchCriteria(optionList, context);
      if ("all".equals(searchListInfo.getListView())) {
        optionList.setEnabled(-1);
      }
      if ("enabled".equals(searchListInfo.getListView())) {
        optionList.setEnabled(1);
      }
      if ("disabled".equals(searchListInfo.getListView())) {
        optionList.setEnabled(0);
      }
      optionList.buildList(db);
      context.getRequest().setAttribute("OptionList", optionList);
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
  public String executeCommandAdd(ActionContext context) {
    Connection db = null;
    ProductOptionConfiguratorList configList = new ProductOptionConfiguratorList();
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      configList.buildList(db);
      context.getRequest().setAttribute("ConfiguratorList", configList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Add");
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
    ProductOption insOption = null;
    ProductOption newOption = (ProductOption) context.getFormBean();
    //newOption.setEnteredBy(getUserId(context));
    //newOption.setModifiedBy(getUserId(context));

    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      recordInserted = newOption.insert(db);
      if (recordInserted) {
        insOption = new ProductOption(db, newOption.getId());
        context.getRequest().setAttribute("ProductOption", insOption);
      } else {
        processErrors(context, newOption.getErrors());
        return (executeCommandAdd(context));
      }

      if (context.getRequest().getParameter("popup") != null) {
        String popup = context.getRequest().getParameter("popup");
        if ("true".equals(popup)) {
          String catalogId = context.getRequest().getParameter("catalogId");
          String url = context.getRequest().getParameter("action");
          String params = "&moduleId=" + moduleId + "&catalogId=" + catalogId;
          url += params;
          context.getRequest().setAttribute("refreshUrl", url);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Insert");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmEnable(ActionContext context) {
    Connection db = null;
    String returnUrl = context.getRequest().getParameter("return");
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, option.getConfiguratorId());
      HtmlDialog htmlDialog = new HtmlDialog();
      htmlDialog.setTitle("Centric CRM: Product Catalog Editor");
      if (configurator.enableOption(db, option.getId())) {
        htmlDialog.addMessage(
            "The option can be successfully enabled...<br><br>");
        htmlDialog.addButton(
            "Enable", "javascript:window.location.href='ProductOptions.do?command=Enable&optionId=" + optionId + "&return=" + returnUrl + "';");
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
        context.getRequest().setAttribute("refreshUrl", returnUrl);
      } else {
        HashMap errors = configurator.getErrors();
        htmlDialog.addMessage(
            "The option could not be enabled because of the following errors:<br><br>");
        Iterator i = errors.values().iterator();
        while (i.hasNext()) {
          String error = (String) i.next();
          htmlDialog.addMessage(error);
        }
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      }
      context.getSession().setAttribute("Dialog", htmlDialog);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "ConfirmEnableOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    Connection db = null;
    String returnUrl = context.getRequest().getParameter("return");
    String moduleId = context.getRequest().getParameter("moduleId");
    returnUrl += "&moduleId=" + moduleId;
    try {
      db = this.getConnection(context);
      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      option.setEnabled(true);
      option.update(db);
      context.getRequest().setAttribute("refreshUrl", returnUrl);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "EnableOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, option.getConfiguratorId());

      // query the properties for this option
      configurator.queryProperties(db, Integer.parseInt(optionId), false);
      OptionPropertyList propertyList = null;
      if (context.getRequest().getAttribute("PropertyList") != null) {
        propertyList = (OptionPropertyList) context.getRequest().getAttribute(
            "PropertyList");
      } else {
        propertyList = configurator.getPropertyList();
      }
      Iterator i = propertyList.iterator();
      while (i.hasNext()) {
        OptionProperty thisProperty = (OptionProperty) i.next();
        thisProperty.prepareContext(db, context.getRequest());
      }
      context.getRequest().setAttribute("PropertyList", propertyList);
      String name = configurator.getName();
      context.getRequest().setAttribute("configName", name);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
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
  public String executeCommandLoadConfigurator(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String configId = context.getRequest().getParameter("configId");
      context.getRequest().setAttribute("configId", configId);

      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, Integer.parseInt(configId));
      OptionPropertyList propertyList = null;
      if (context.getRequest().getAttribute("PropertyList") != null) {
        propertyList = (OptionPropertyList) context.getRequest().getAttribute(
            "PropertyList");
      } else {
        propertyList = configurator.getPropertyList();
      }
      Iterator i = propertyList.iterator();
      while (i.hasNext()) {
        OptionProperty thisProperty = (OptionProperty) i.next();
        thisProperty.prepareContext(db, context.getRequest());
      }
      context.getRequest().setAttribute("PropertyList", propertyList);
      String name = configurator.getName();
      context.getRequest().setAttribute("configName", name);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "LoadConfigurator");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertOption(ActionContext context) {
    Connection db = null;
    ProductOption option = (ProductOption) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean isValid = false;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String configId = context.getRequest().getParameter("configId");
      option.setConfiguratorId(configId);
      context.getRequest().setAttribute("ProductOption", option);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, Integer.parseInt(configId));
      // set the properties with values available in the request
      configurator.setProperties(context.getRequest());
      if (!configurator.getPropertyList().isValid(systemStatus)) {
        context.getRequest().setAttribute(
            "PropertyList", configurator.getPropertyList());
        return (executeCommandLoadConfigurator(context));
      }
      // save the properties
      isValid = this.validateObject(context, db, option);
      isValid = this.validateObject(context, db, configurator) && isValid;
      if (isValid) {
        configurator.saveProperties(db, option);
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ProductOptions-> Unable to save properties");
        }
        context.getRequest().setAttribute(
            "PropertyList", configurator.getPropertyList());
        return (executeCommandLoadConfigurator(context));
      }
      String html = configurator.getHtml();
      context.getRequest().setAttribute("HtmlString", html);

      if (context.getRequest().getParameter("popup") != null) {
        String popup = context.getRequest().getParameter("popup");
        if ("true".equals(popup)) {
          String catalogId = context.getRequest().getParameter("catalogId");
          String url = context.getRequest().getParameter("action");
          String params = "&moduleId=" + moduleId + "&catalogId=" + catalogId;
          url += params;
          context.getRequest().setAttribute("refreshUrl", url);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "InsertOption");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    Connection db = null;
    ProductOption option = (ProductOption) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean isValid = false;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String configId = context.getRequest().getParameter("configId");
      context.getRequest().setAttribute("configId", configId);

      option.setConfiguratorId(configId);
      context.getRequest().setAttribute("ProductOption", option);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, Integer.parseInt(configId));
      // set the properties with values available in the request
      configurator.setProperties(context.getRequest());
      if (!configurator.getPropertyList().isValid(systemStatus)) {
        context.getRequest().setAttribute(
            "PropertyList", configurator.getPropertyList());
        return (executeCommandModify(context));
      }
      // save the properties
      isValid = this.validateObject(context, db, option);
      isValid = this.validateObject(context, db, configurator) && isValid;
      if (isValid) {
        configurator.updateProperties(db, option);
      } else {
        context.getRequest().setAttribute(
            "PropertyList", configurator.getPropertyList());
//      processErrors(context, option.getErrors());
        return (executeCommandModify(context));
      }
      String html = configurator.getHtml();
      context.getRequest().setAttribute("HtmlString", html);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandDetails(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      String moduleId = context.getRequest().getParameter("moduleId");
      PermissionCategory permissionCategory = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute(
          "PermissionCategory", permissionCategory);

      String optionId = context.getRequest().getParameter("optionId");
      ProductOption option = new ProductOption(db, Integer.parseInt(optionId));
      context.getRequest().setAttribute("ProductOption", option);

      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, option.getConfiguratorId());

      // query the properties for this option
      configurator.queryProperties(db, Integer.parseInt(optionId), true);

      // prepare the html widget
      String html = configurator.getHtml();
      context.getRequest().setAttribute("HtmlString", html);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return "DetailsOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    //this.deletePagedListInfo(context, "");
  }
}


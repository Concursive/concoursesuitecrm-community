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
import org.aspcfs.modules.products.base.ProductOption;
import org.aspcfs.modules.products.base.ProductOptionConfigurator;
import org.aspcfs.modules.products.base.ProductOptionValues;
import org.aspcfs.modules.products.base.ProductOptionValuesList;
import org.aspcfs.modules.products.configurator.OptionConfigurator;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created September 7, 2004
 */
public final class ProductOptionPricings extends CFSModule {
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
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
      configurator.queryProperties(db, option.getId(), false);

      // populate the pricing list
      ProductOptionValuesList pricingList = new ProductOptionValuesList();
      pricingList.setOptionId(Integer.parseInt(optionId));
      pricingList.buildList(db);
      context.getRequest().setAttribute("PricingList", pricingList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
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

      int nextRangeMin = ProductOption.getNextRangeMin(db, option.getId());
      context.getRequest().setAttribute(
          "nextRangeMin", String.valueOf(nextRangeMin));
      // load the configurator
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, option.getConfiguratorId());
      // query the properties for this option
      configurator.queryProperties(db, option.getId(), false);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
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
    Connection db = null;
    ProductOptionValues value = (ProductOptionValues) context.getFormBean();
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

      value.insert(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandList(context));
  }
}


/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCategory;
import org.aspcfs.modules.products.base.ProductCategoryList;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.modules.website.framework.IceletManager;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Actions for the Portal module
 *
 * @author mrajkowski
 * @version $Id: Exp $
 * @created May 5, 2006
 */
public final class Portal extends CFSModule {

  public String executeCommandCheckPortal(ActionContext context) {
    // Will need to use the following objects in some way...
    Connection db = null;
    try {
      db = this.getConnection(context);
      // If an active website exists, redirect to the portal
      org.aspcfs.modules.website.base.SiteList websiteList = new org.aspcfs.modules.website.base.SiteList();
      websiteList.setEnabled(Constants.TRUE);
      websiteList.buildList(db);
      if (websiteList.size() > 0) {
        context.getRequest().setAttribute("site", websiteList.get(0));
        return "WebsiteFoundOK";
      }
    } catch (Exception e) {
      System.out.println("Portal-> Default error: " + e.getMessage());
    } finally {
      freeConnection(context, db);
    }
    // No default, go to login
    return "WebsiteNotFoundOK";
  }


  public String executeCommandDefault(ActionContext context) {
    // Request items
    int tabId = -1;
    String tabValue = context.getRequest().getParameter("tabId");
    int pageId = -1;
    String pageValue = context.getRequest().getParameter("pageId");
    // Show the site...
    Site site = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("url", systemStatus.getUrl());
    ArrayList rowColumnList = null;
    try {
      // Load the site
      site = (Site) context.getRequest().getAttribute("site");
      db = this.getConnection(context);
      if (site == null) {
        SiteList websiteList = new SiteList();
        websiteList.setEnabled(Constants.TRUE);
        websiteList.buildList(db);
        if (websiteList.size() > 0) {
          site = (Site) websiteList.get(0);
        }
      }
      if (site == null) {
        return "NoActiveSiteError";
      }
      // Show all of the tabs for this site
      site.buildTabList(db, Constants.TRUE);
      // If the user selected a tab, make sure it is actually enabled for viewing
      if (StringUtils.hasText(tabValue)) {
        tabId = Integer.parseInt(tabValue);
        if (!TabList.queryEnabled(db, tabId)) {
          return "TabNotEnabledError";
        }
      } else {
        tabId = TabList.queryDefault(db, site.getId(), Site.PORTAL_MODE);
      }
      // If the user selected a page, make sure it is actually enabled for viewing
      if (StringUtils.hasText(pageValue)) {
        pageId = Integer.parseInt(pageValue);
      } else {
        pageId = PageList.queryDefault(db, tabId, Site.PORTAL_MODE);
      }
      context.getRequest().setAttribute("tabId", String.valueOf(tabId));
      context.getRequest().setAttribute("pageId", String.valueOf(pageId));

      //Add to website access log
      UserBean userBean = (UserBean) context.getRequest().getSession().getAttribute("User");
      if (userBean != null && userBean.getUserId() == -2) {
        WebPageAccessLog webPageAccessLog = new WebPageAccessLog();
        webPageAccessLog.setSiteLogId(Integer.parseInt(userBean.getSessionId()));
        webPageAccessLog.setPageId(pageId);
        addLogItem(context, "webPageAccessLog", webPageAccessLog);
      }

      //Building site for display
      site.buildResources(db, tabId, pageId, Site.PORTAL_MODE);
      rowColumnList = new ArrayList();
      site.buildRowsColumns(rowColumnList);
      context.getRequest().setAttribute("rowsColumns", rowColumnList);
      context.getRequest().setAttribute("site", site);
      // Prepare any data required by the icelets using the icelet cache
      IceletManager manager = IceletManager.getManager(context);
      boolean actionMode = manager.prepare(context, site, tabId, pageId, db);
      // Load the layout
      if (site.getLayoutId() > -1) {
        Layout layout = new Layout(db, site.getLayoutId());
        context.getRequest().setAttribute("layout", layout);
      } else {
        Layout layout = new Layout();
        layout.setJsp("layout_2006051814_top_right.jsp");
        context.getRequest().setAttribute("layout", layout);
      }
      // Load the stylesheet
      if (site.getStyleId() > -1) {
        Style style = new Style(db, site.getStyleId());
        context.getRequest().setAttribute("style", style);
      } else {
        Style style = new Style();
        style.setCss("style_2006051811_blue.css");
        context.getRequest().setAttribute("style", style);
      }
      if (actionMode) {
        return "-none-";
      }
      context.getRequest().setAttribute("portal", "true");
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("PortalError");
    } finally {
      this.freeConnection(context, db);
    }
    return "PortalOK";
  }

  public String executeCommandListProductMultiCategories(ActionContext context) {
    Connection db = null;
    ProductCatalog product = null;
    ProductCategory category = null;
    Exception errorMessage = null;
    try {
      db = this.getConnection(context);
//      String moduleId = context.getRequest().getParameter("moduleId");
      String returnAction = context.getRequest().getParameter("returnAction");
//      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
//      context.getRequest().setAttribute("permissionCategory", permissionCategory);

      String parentId = context.getRequest().getParameter("parentId") != null &&
          !"".equals(context.getRequest().getParameter("parentId"))
          ? context.getRequest().getParameter("parentId") : "-1";
      String selected = context.getRequest().getParameter("selected");
      ProductCategoryList list = new ProductCategoryList();
      if (selected != null && !"".equals(selected)) {
        list = list.buildListFromIds(db, selected);
        context.getRequest().setAttribute("checkedList", list);
      }


      String categoryId = context.getRequest().getParameter("categoryId");
      if (Integer.parseInt(categoryId) != -1) {
        category = new ProductCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("productCategory", category);
      }

      ProductCategoryList categoryList = new ProductCategoryList();
      categoryList.setParentId(parentId);
      if ("-1".equals(parentId)) {
        categoryList.setTopOnly(Constants.TRUE);
      }
      categoryList.setBuildChildCount(true);
      categoryList.buildList(db);
      categoryList.setLevel(1);
      //populate the hierarchy with initial level 1
      //ProductCategoryList.buildHierarchy(db, categoryList);
      //categoryList.buildCompleteHierarchy();
      context.getRequest().setAttribute("categoryHierarchy", categoryList);
      context.getRequest().setAttribute("action", "moveProduct");
      context.getRequest().setAttribute("returnAction", returnAction);
      context.getRequest().setAttribute("parentId", parentId);
      context.getRequest().setAttribute("categoryListIds", "-1");
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (context.getRequest().getParameter("mode") != null)
        return "MoveChunkOK";
      else
        return "MoveOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    }
  }

  public String executeCommandSaveListProductMultiCategories(ActionContext context) {
    String[] category = context.getRequest().getParameterValues("categoryElt");
    Connection db = null;

    try {
      db = this.getConnection(context);
      String ids = "";
      if (category != null) {
        for (String s : category) {
          ids += s + "|";
        }
      }
      ProductCategoryList newList = new ProductCategoryList();
      newList = newList.buildListFromIds(db, ids);
      String categoryNames = null;
      if (!newList.isEmpty()) {
        Iterator i = newList.iterator();
        int count = 0;
        while (i.hasNext() && count < 3) {
          count++;
          ProductCategory thisCategory = (ProductCategory) i.next();
          categoryNames = categoryNames == null ? thisCategory.getName() : categoryNames + ", " + thisCategory.getName();
        }
        if (count >= 3) {
          categoryNames = categoryNames + "...";
        }
      }
      //context.getRequest().getSession().setAttribute("searchcodeCategoryList",newList);
      context.getRequest().setAttribute("categoryNames", categoryNames);
      context.getRequest().setAttribute("categoryListIds", ids);
      return "MultiselectCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }

}


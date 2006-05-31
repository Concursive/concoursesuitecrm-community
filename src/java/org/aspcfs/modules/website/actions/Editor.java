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
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.website.base.SiteList;

import java.sql.Connection;

/**
 * Actions for the WebSite module
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 21, 2006 $Id: Exp $
 */
public final class Editor extends CFSModule {

  /**
   * Displays a list of sites to the user (if any), otherwise goes straight
   * to the welcome page.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    // TODO: Permissions before each action command
    Connection db = null;
    try {
      db = this.getConnection(context);
      SiteList siteList = new SiteList();
      siteList.buildList(db);
      if (siteList.size() > 0) {
        context.getRequest().setAttribute("siteList", siteList);
        return "SiteListOK";
      } else {
        return "GotoWelcomeOK";
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
  }


  /**
   * Display a list of template sites to choose from, or allow them to create
   * an empty site.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String excuteCommandWelcome(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      // TODO: Build list of default templates to display

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "WelcomeOK";
  }
  /**
   * Display a list of template sites to choose from, or allow them to create
   * an empty site.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String excuteCommandCreateFromTemplate(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      // TODO: Create a new site from template

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "CreateSiteOK";
  }

}


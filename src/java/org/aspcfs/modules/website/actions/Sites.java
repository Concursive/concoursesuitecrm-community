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
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.modules.website.framework.IceletManager;
import org.aspcfs.modules.website.utils.SiteImporter;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Actions for the Sites module
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 10, 2006
 */
public final class Sites extends CFSModule {

  /**
   * Renders the site for the given or default tabId, and the given or default
   * pageId
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "site-editor-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    ArrayList rowColumnList = new ArrayList();
    try {
      // Request items
      int siteId = Integer.parseInt(context.getRequest().getParameter("siteId"));
      int tabId = -1;
      String tabValue = context.getRequest().getParameter("tabId");
      int pageId = -1;
      String pageValue = context.getRequest().getParameter("pageId");
      // Build the site data
      db = this.getConnection(context);
      Site site = new Site(db, siteId);
      if (StringUtils.hasText(tabValue)) {
        tabId = Integer.parseInt(tabValue);
      } else {
        tabId = TabList.queryDefault(db, site.getId(), Site.EDIT_MODE);
      }
      if (StringUtils.hasText(pageValue)) {
        pageId = Integer.parseInt(pageValue);
      } else {
        pageId = PageList.queryDefault(db, tabId, Site.EDIT_MODE);
      }
      context.getRequest().setAttribute("tabId", String.valueOf(tabId));
      context.getRequest().setAttribute("pageId", String.valueOf(pageId));
      site.buildResources(db, tabId, pageId, Site.EDIT_MODE);
      site.buildRowsColumns(rowColumnList);
      context.getRequest().setAttribute("rowsColumns", rowColumnList);
      context.getRequest().setAttribute("site", site);
      // Prepare any data required by the icelets using the icelet cache
      IceletManager manager = IceletManager.getManager(context);
      boolean actionMode = manager.prepare(context, site, tabId, pageId, db);
      if (actionMode) {
        return "-none-";
      }
      return "SiteOK";
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "site-editor-view"))) {
      return ("PermissionError");
    }
    SiteList sites = new SiteList();
    Connection db = null;
    PagedListInfo siteListInfo = this.getPagedListInfo(context, "siteListInfo");
    siteListInfo.setLink("Sites.do?command=List&popup=true");
    try {
      db = this.getConnection(context);
      sites.setPagedListInfo(siteListInfo);
      siteListInfo.setSearchCriteria(sites, context);
      sites.buildList(db);
      context.getRequest().setAttribute("sites", sites);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (sites.size() == 0) {
      return getReturn(context, "GotoTemplateList");
//        return executeCommandTemplateList(context);
    }
    return getReturn(context, "List");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTemplateList(ActionContext context) {
    if (!(hasPermission(context, "site-editor-add"))) {
      return ("PermissionError");
    }
    String fromList = context.getRequest().getParameter("fromList");
    if (fromList != null && "true".equals(fromList.trim())) {
      context.getRequest().setAttribute("fromList", fromList);
    }
    try {
      // Build the list of templates from the directory
      TemplateList templateList = new TemplateList();
      templateList.buildList(getWebInfPath(context, "portal_templates"), getSystemStatus(context).getLanguage());
      context.getRequest().setAttribute("templateList", templateList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return getReturn(context, "TemplateList");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "site-editor-add"))) {
      return ("PermissionError");
    }
    String fromList = context.getRequest().getParameter("fromList");
    if (fromList != null && "true".equals(fromList.trim())) {
      context.getRequest().setAttribute("fromList", fromList);
    }
    Site site = (Site) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);
      context.getRequest().setAttribute("site", site);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
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
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    Site site = (Site) context.getFormBean();
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (site.getId() == -1) {
        site = new Site(db, Integer.parseInt(siteId));
      }
      context.getRequest().setAttribute("site", site);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Modify");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "site-editor-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Site site = (Site) context.getFormBean();
    int recordCount = -1;
    boolean isValid = false;
    boolean recordInserted = false;
    try {
      db = this.getConnection(context);
      site.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, site);
      if (isValid) {
        if (site.getId() > -1) {
          // Updating an existing site
          recordCount = site.update(db);
        } else {
          // Inserting a new site
          String templateFile = context.getRequest().getParameter("template");
          TemplateList templateList = new TemplateList();
          templateList.setTemplate(templateFile);
          templateList.buildList(getWebInfPath(context, "portal_templates"), getSystemStatus(context).getLanguage());
          if (templateList.size() == 1) {
            Template thisTemplate = (Template) templateList.get(0);
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Sites-> Importing template: " + templateFile);
            }
            String zipFile = templateList.getZipPath() + System.getProperty("file.separator") + thisTemplate.getFilename() + ".zip";
            SiteImporter importer = new SiteImporter(zipFile, null, db, this.getUserId(context));
            Site newSite = importer.getSite();
            newSite.setName(site.getName());
            newSite.setEnabled(site.getEnabled());
            newSite.setInternalDescription(site.getInternalDescription());
            newSite.setNotes(site.getNotes());
            newSite.update(db);
            context.getRequest().setAttribute("site", newSite);
            recordInserted = (newSite.getId() > 0);
						site.setId(newSite.getId());
          } else {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Sites-> None found..." + templateFile);
            }
          }
        }
      }
      if (isValid && (recordInserted || recordCount != -1)) {
        context.getRequest().setAttribute("siteId", String.valueOf(site.getId()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!isValid && !(recordInserted || recordCount != -1)) {
      if (site.getId() > -1) {
        return executeCommandModify(context);
      } else {
        return executeCommandAdd(context);
      }
    }
    return executeCommandDetails(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandActivate(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Site site = null;
    String siteId = context.getRequest().getParameter("siteId");
    String flag = context.getRequest().getParameter("enable");
    int recordCount = -1;
    try {
      db = this.getConnection(context);
      site = new Site(db, Integer.parseInt(siteId));
      if (flag != null && !"".equals(flag.trim())) {
        site.setEnabled(flag);
        if (site.getEnabled()) {
          SiteList.disableOtherSites(db);
        }
        recordCount = site.update(db);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandList(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
	 public String executeCommandUpdateLogo(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    String logoImageId = context.getRequest().getParameter("logoImageId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Site site = new Site(db, Integer.parseInt(siteId));
			site.updateLogoImageId(db, Integer.parseInt(logoImageId));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
		return executeCommandDetails(context);
	}
	
	
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    int tabId = -1;
    String tabValue = context.getRequest().getParameter("tabId");
    int pageId = -1;
    String pageValue = context.getRequest().getParameter("pageId");
    String siteId = context.getRequest().getParameter("siteId");
    if (siteId == null || "".equals(siteId.trim())) {
      siteId = (String) context.getRequest().getAttribute("siteId");
    }
    ArrayList rowColumnList = null;
    Site site = null;
    SystemStatus systemStatus =  this.getSystemStatus(context);
    if (systemStatus != null) {
      context.getRequest().setAttribute("url", systemStatus.getUrl());
    } else {
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      context.getRequest().setAttribute("url", prefs.get("WEBSERVER.URL"));
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      site = new Site(db, Integer.parseInt(siteId));
      site.setBuildTabList(true);
      site.buildTabList(db);
      if (site.getTabList().size() == 0) {
        return getReturn(context, "GotoSiteList");
      }
      if (StringUtils.hasText(tabValue) && !"-1".equals(tabValue)) {
        tabId = Integer.parseInt(tabValue);
      } else {
        tabId = TabList.queryDefault(db, site.getId(), Site.EDIT_MODE);
      }
      if (StringUtils.hasText(pageValue) && !"-1".equals(pageValue)) {
        pageId = Integer.parseInt(pageValue);
      } else {
        pageId = PageList.queryDefault(db, tabId, Site.EDIT_MODE);
      }
      context.getRequest().setAttribute("tabId", String.valueOf(tabId));
      context.getRequest().setAttribute("pageId", String.valueOf(pageId));
      //Build the site tabs, pages, groups etc..
      site.buildResources(db, tabId, pageId, Site.EDIT_MODE);
      rowColumnList = new ArrayList();
      site.buildRowsColumns(rowColumnList);
      context.getRequest().setAttribute("rowsColumns", rowColumnList);
      // Prepare any data required by the icelets using the icelet cache
      IceletManager manager = IceletManager.getManager(context);
      manager.prepare(context, site, tabId, pageId, db);
      context.getRequest().setAttribute("site", site);
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
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Details");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "site-editor-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    String siteId = context.getRequest().getParameter("siteId");
    try {
      db = this.getConnection(context);
      Site site = new Site(db, Integer.parseInt(siteId));
      DependencyList dependencies = site.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
        systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (!dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.deleteRelatedOrdersFirst"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title", "Title"));
        htmlDialog.setHeader(systemStatus.getLabel("quotes.dependencies", "Delete this Site"));
        htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='Sites.do?command=Delete&siteId=" + site.getId() + "&popup=true'");
        htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close();");
      }
      context.getSession().setAttribute("Dialog", htmlDialog);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ConfirmDelete");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "site-editor-delete"))) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Site site = new Site(db, Integer.parseInt(siteId));
      site.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "Sites.do?command=List&popup=true");
    return getReturn(context, "Delete");
  }


  /**
   * This action streams a portal template thumnbnail image from an HTML image tag
   * @param context
   * @return Action result
   */
  public String executeCommandTemplateThumbnail(ActionContext context) {
    if (!(hasPermission(context, "site-editor-view"))) {
      return ("PermissionError");
    }
    String template = context.getRequest().getParameter("template");
    try {
      // Verify the template exists
      TemplateList templateList = new TemplateList();
      templateList.setTemplate(template);
      templateList.buildList(getWebInfPath(context, "portal_templates"), getSystemStatus(context).getLanguage());
      // Stream the image from the template archive
      if (templateList.size() == 1) {
        Template thisTemplate = (Template) templateList.get(0);
        FileDownload fileDownload = new FileDownload();
        fileDownload.setDisplayName(thisTemplate.getName());
        fileDownload.sendFileFromZip(context, templateList.getZipPath() + System.getProperty("file.separator") + thisTemplate.getFilename() + ".zip", "website/thumbnail.gif");
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return ("-none-");
  }

  public String executeCommandViewLayouts(ActionContext context) {
    if (!(hasPermission(context, "site-editor-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      LayoutList layoutList = new LayoutList();
      layoutList.buildList(db);
      context.getRequest().setAttribute("layoutList", layoutList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ViewLayoutsOK";
  }

  public String executeCommandUpdateLayout(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      String siteId = context.getRequest().getParameter("siteId");
      String layoutId = context.getRequest().getParameter("layoutId");
      db = this.getConnection(context);
      Site thisSite = new Site(db, Integer.parseInt(siteId));
      thisSite.updateLayoutId(db, Integer.parseInt(layoutId));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "UpdateLayoutOK";
  }

  public String executeCommandViewStyles(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      StyleList styleList = new StyleList();
      styleList.buildList(db);
      context.getRequest().setAttribute("styleList", styleList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ViewStylesOK";
  }

  public String executeCommandUpdateStyle(ActionContext context) {
    if (!(hasPermission(context, "site-editor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      String siteId = context.getRequest().getParameter("siteId");
      String styleId = context.getRequest().getParameter("styleId");
      db = this.getConnection(context);
      Site thisSite = new Site(db, Integer.parseInt(siteId));
      thisSite.updateStyleId(db, Integer.parseInt(styleId));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "UpdateStyleOK";
  }
}


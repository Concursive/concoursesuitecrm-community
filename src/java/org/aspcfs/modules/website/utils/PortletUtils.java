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
package org.aspcfs.modules.website.utils;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.PagedListInfo;
import org.quartz.Scheduler;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id: Exp $
 * @created May 11, 2006 $Id: Exp $
 */
public class PortletUtils {

  public final static String fs = System.getProperty("file.separator");

  /**
   * Constructor for the PortletUtils object
   */
  public PortletUtils() {
  }


  /**
   * Gets the connection attribute of the PortletUtils class
   *
   * @param request Description of the Parameter
   * @return The connection value
   */
  public static Connection getConnection(PortletRequest request) {
    return (Connection) request.getAttribute("connection");
  }


  public static synchronized boolean addLogItem(PortletRequest request, String type, Object item) throws IOException {
    if (item == null) {
      return false;
    }
    Scheduler scheduler = (Scheduler) request.getAttribute("scheduler");
    try {
      ((Vector) scheduler.getContext().get(type)).add(item);
      // Force the job right now, otherwise wait for timer
      //scheduler.triggerJob("logger", Scheduler.DEFAULT_GROUP);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("PortletUtils-> Scheduler failed: " + e.getMessage());
    }
    return true;
  }


  /**
   * Gets the systemStatus attribute of the PortletUtils class
   *
   * @param request Description of the Parameter
   * @return The systemStatus value
   */
  public static SystemStatus getSystemStatus(PortletRequest request) {
    return (SystemStatus) request.getAttribute("systemStatus");
  }


  public static ApplicationPrefs getApplicationPrefs(PortletRequest request) {
    ApplicationPrefs prefs = (ApplicationPrefs) request.getAttribute("applicationPrefs");
    if (prefs != null) {
      return prefs;
    } else {
      return null;
    }
  }

  /**
   * Gets the application prefs for the specified string
   *
   * @param request Description of the Parameter
   * @param param   Description of the Parameter
   * @return The string value
   */
  public static String getApplicationPrefs(PortletRequest request, String param) {
    ApplicationPrefs prefs = PortletUtils.getApplicationPrefs(request);
    if (prefs != null) {
      return prefs.get(param);
    } else {
      return null;
    }
  }


  /**
   * Gets the file path with the database name for the specified string
   *
   * @param request Description of the Parameter
   * @return The string value
   */
  public static String getDbNamePath(PortletRequest request) {
    String fileLibraryPath = PortletUtils.getApplicationPrefs(request, "FILELIBRARY");
    if (fileLibraryPath != null) {
      return (fileLibraryPath + getDbName(request) + fs);
    } else {
      return null;
    }
  }


  /**
   * Gets the database name for the specified string
   *
   * @param request Description of the Parameter
   * @return The string value
   */
  public static String getDbName(PortletRequest request) {
    ConnectionElement ce = (ConnectionElement) request.getAttribute("connectionElement");
    if (ce != null) {
      return ce.getDbName();
    } else {
      return null;
    }
  }


  /**
   * Gets the pagedListInfo attribute of the PortletUtils class
   *
   * @param request  Description of the Parameter
   * @param viewName Description of the Parameter
   * @return The pagedListInfo value
   */
  public static PagedListInfo getPagedListInfo(PortletRequest request, RenderResponse response, String viewName) {
    return PortletUtils.getPagedListInfo(request, response, viewName, true);
  }


  /**
   * Gets the pagedListInfo attribute of the PortletUtils class
   *
   * @param request   Description of the Parameter
   * @param viewName  Description of the Parameter
   * @param setParams Description of the Parameter
   * @return The pagedListInfo value
   */
  public static PagedListInfo getPagedListInfo(PortletRequest request, RenderResponse response, String viewName, boolean setParams) {
    PagedListInfo tmpInfo = (PagedListInfo) request.getPortletSession().getAttribute(viewName);
    if (tmpInfo == null || tmpInfo.getId() == null) {
      tmpInfo = new PagedListInfo();
      tmpInfo.setId(viewName);
      tmpInfo.setNamespace(response.getNamespace());
      request.getPortletSession().setAttribute(viewName, tmpInfo);
    }
    if (setParams) {
      tmpInfo.setParameters((HttpServletRequest) request);
    }
    return tmpInfo;
  }


  public static UserBean getUser(PortletRequest request) {
    return (UserBean) request.getAttribute("userBean");
  }
}


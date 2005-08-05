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
package org.aspcfs.utils;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.apps.common.ReportConstants;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/**
 * Utilities to work with the sites that have been installed on the system
 *
 * @author matt rajkowski
 * @version $Id$
 * @created October 3, 2003
 */
public class SiteUtils {

  /**
   * Generates a list of sites depending on the configuration parameters
   * specified, generally a build.properties file config
   *
   * @return The siteList value
   */
  public static SiteList getSiteList(ApplicationPrefs prefs, ConnectionPool cp) {
    return SiteUtils.getSiteList(prefs.getPrefs(), cp);
  }

  /**
   * Generates a list of sites depending on the configuration parameters
   * specified, generally a build.properties file config
   *
   * @param config Description of the Parameter
   * @return The siteList value
   */
  public static SiteList getSiteList(Map config) {
    return getSiteList(config, null);
  }

  public static SiteList getSiteList(Map config, ConnectionPool cp) {
    Connection dbSites = null;
    SiteList siteList = new SiteList();
    String appCode = (String) config.get("GATEKEEPER.APPCODE");
    String baseName = (String) config.get("GATEKEEPER.URL");
    String dbUser = (String) config.get("GATEKEEPER.USER");
    String dbPass = (String) config.get("GATEKEEPER.PASSWORD");
    String driver = (String) config.get("GATEKEEPER.DRIVER");
    try {
      if ("true".equals((String) config.get("WEBSERVER.ASPMODE"))) {
        if (cp != null) {
          ConnectionElement ce = new ConnectionElement(
              baseName, dbUser, dbPass);
          ce.setDriver(driver);
          dbSites = cp.getConnection(ce);
        } else {
          //Build list of sites to process
          Class.forName(driver);
          dbSites = DriverManager.getConnection(baseName, dbUser, dbPass);
        }
        siteList.setSiteCode(appCode);
        siteList.setEnabled(ReportConstants.TRUE);
        siteList.buildList(dbSites);
        dbSites.close();
      } else {
        //This setup only allows one site so process it
        Site thisSite = new Site();
        thisSite.setDatabaseDriver((String) config.get("GATEKEEPER.DRIVER"));
        thisSite.setDatabaseUrl((String) config.get("GATEKEEPER.URL"));
        thisSite.setDatabaseName((String) config.get("GATEKEEPER.DATABASE"));
        thisSite.setDatabaseUsername((String) config.get("GATEKEEPER.USER"));
        thisSite.setDatabasePassword(
            (String) config.get("GATEKEEPER.PASSWORD"));
        thisSite.setSiteCode((String) config.get("GATEKEEPER.APPCODE"));
        thisSite.setVirtualHost((String) config.get("WEBSERVER.URL"));
        thisSite.setLanguage((String) config.get("SYSTEM.LANGUAGE"));
        siteList.add(thisSite);
      }
    } catch (Exception e) {
      return null;
    } finally {
      if (cp != null) {
        cp.free(dbSites);
      }
    }
    return siteList;
  }
}


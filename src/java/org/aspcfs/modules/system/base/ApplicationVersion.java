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
package org.aspcfs.modules.system.base;

import org.aspcfs.controller.ApplicationPrefs;
import com.darkhorseventures.framework.hooks.CustomHook;

/**
 * Class for reading the application version information
 *
 * @author matt rajkowski
 * @version $Id: ApplicationVersion.java,v 1.15 2004/06/15 14:30:04 mrajkowski
 *          Exp $
 * @created July 31, 2003
 */
public class ApplicationVersion {
  public final static String VERSION = "Centric CRM 4.1 Beta 14 (2007-01-16)";
  public final static String APP_VERSION = "2007-01-16";
  public final static String DB_VERSION = "2007-01-16";
  public final static String RELEASE = "4.1";


  /**
   * Gets the outOfDate attribute of the ApplicationVersion class
   *
   * @param prefs Description of the Parameter
   * @return The outOfDate value
   */
  public static boolean isOutOfDate(ApplicationPrefs prefs) {
    return CustomHook.isOutOfDate(prefs);
  }


  /**
   * Gets the installedVersion attribute of the ApplicationVersion class
   *
   * @param prefs Description of the Parameter
   * @return The installedVersion value
   */
  public static String getInstalledVersion(ApplicationPrefs prefs) {
    String installedVersion = prefs.get("VERSION");
    if (installedVersion == null || "".equals(installedVersion)) {
      // Return the first version that had an integrated upgrade utility
      return "2.8 (2004-03-16)";
    } else {
      return installedVersion;
    }
  }

}

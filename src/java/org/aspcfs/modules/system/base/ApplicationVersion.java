package org.aspcfs.modules.system.base;

/**
 *  Class for reading the application version information
 *
 *@author     matt rajkowski
 *@created    July 31, 2003
 *@version    $Id: ApplicationVersion.java,v 1.15 2004/06/15 14:30:04 mrajkowski
 *      Exp $
 */
public class ApplicationVersion {
  public final static String VERSION = "2.9 beta 3 (2004-08-30)";


  /**
   *  returns just the date of the version
   *
   *@return    The versionDate value
   */
  public static String getVersionDate() {
    return VERSION.substring(VERSION.indexOf("(") + 1, VERSION.indexOf(")"));
  }


  /**
   *  Gets the versionName attribute of the ApplicationVersion class
   *
   *@return    The versionName value
   */
  public static String getVersionName() {
    return VERSION.substring(0, VERSION.indexOf("(") - 1);
  }
}


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

/**
 *  Class for reading the application version information
 *
 *@author     matt rajkowski
 *@created    July 31, 2003
 *@version    $Id: ApplicationVersion.java,v 1.15 2004/06/15 14:30:04 mrajkowski
 *      Exp $
 */
public class ApplicationVersion {
  public final static String VERSION = "2.9 rc 2 (2004-09-17)";


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


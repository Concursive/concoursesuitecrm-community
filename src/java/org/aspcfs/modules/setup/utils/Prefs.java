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
package org.aspcfs.modules.setup.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.prefs.*;

/**
 *  A class for loading CFS preferences, only called when the initHook does not
 *  find any prefs in web.xml. Used for the CFS initial setup code.
 *
 *@author     matt rajkowski
 *@created    August 12, 2003
 *@version    $Id$
 */
public class Prefs {

  /**
   *  Saves the specified preference name and value to the store
   *
   *@param  name   Description of the Parameter
   *@param  value  Description of the Parameter
   *@return        Description of the Return Value
   */
  public static boolean savePref(String name, String value) {
    try {
      Preferences prefs = Preferences.userNodeForPackage(Prefs.class);
      prefs.put(name, value);
      prefs.flush();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}


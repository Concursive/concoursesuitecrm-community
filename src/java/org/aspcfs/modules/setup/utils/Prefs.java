package org.aspcfs.modules.setup.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.prefs.*;

/**
 *  A class for loading CFS preferences, only called when the initHook
 *  does not find any prefs in web.xml.  Used for the CFS initial setup
 *  code.
 *
 *@author     matt rajkowski
 *@created    August 12, 2003
 *@version    $Id$
 */
public class Prefs {

  /**
   *  Loads the default prefs from the store
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public static boolean loadPrefs(ServletContext context) {
    try {
      Preferences prefs = Preferences.userNodeForPackage(Prefs.class);
      addAttribute(context, "SiteCode", prefs.get("cfs.gatekeeper.sitecode", "cfs"));
      addAttribute(context, "FileLibrary", prefs.get("cfs.fileLibrary", null));
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  /**
   *  Adds a feature to the Attribute attribute of the Prefs class
   *
   *@param  context        The feature to be added to the Attribute attribute
   *@param  attributeName  The feature to be added to the Attribute attribute
   *@param  paramName      The feature to be added to the Attribute attribute
   */
  private static void addAttribute(ServletContext context, String param, String value) {
    if (param != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Prefs-> addAttribute: " + param + "=" + value);
      }
      context.setAttribute(param, value);
    }
  }


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


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


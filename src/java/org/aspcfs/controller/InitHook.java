package org.aspcfs.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.servlets.ControllerInitHook;
import com.darkhorseventures.database.*;
import org.aspcfs.utils.web.CustomFormList;
import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;
import org.aspcfs.utils.StringUtils;
import java.io.File;

/**
 *  Code that is initialized when the ServletController starts.
 *
 *@author     mrajkowski
 *@created    July 10, 2001
 *@version    $Id: InitHook.java,v 1.24.18.1 2003/08/12 21:12:56 mrajkowski Exp
 *      $
 */
public class InitHook implements ControllerInitHook {
  public final static String fs = System.getProperty("file.separator");


  /**
   *  When the ServletController is initialized, this code maps init-params to
   *  the Context or Application scope.
   *
   *@param  config  Description of Parameter
   *@return         Description of the Returned Value
   *@since          1.1
   */
  public String executeControllerInit(ServletConfig config) {
    System.out.println("InitHook-> Executing");
    ServletContext context = config.getServletContext();
    //Load the system prefs from the Java registry to determine the file library path
    org.aspcfs.modules.setup.utils.Prefs.loadPrefs(context);
    //Load the build.properties file to set the rest of the prefs
    File propertyFile = null;
    if (context.getAttribute("FileLibrary") != null) {
      //The value was loaded from the binary distribution
      propertyFile = new File((String) context.getAttribute("FileLibrary") + "build.properties");
    } else {
      propertyFile = new File(context.getRealPath("/") + "WEB-INF" + fs + "fileLibrary" + fs + "build.properties");
      if (propertyFile.exists()) {
        //The value can be set since it wasn't required during the enterprise build process
        context.setAttribute("FileLibrary", context.getRealPath("/") + "WEB-INF" + fs + "fileLibrary" + fs);
      }
    }
    //Load prefs from file
    ApplicationPrefs prefs = new ApplicationPrefs();
    if (propertyFile != null && propertyFile.exists()) {
      prefs.load(propertyFile.getPath());
      if (prefs.has("CONTROL")) {
        context.setAttribute("cfs.setup", "true");
      } else {
        prefs.clear();
      }
    }
    context.setAttribute("APPLICATION.PREFS", prefs);
    prefs.populateContext(context);
    //Define the keystore, to be used by tasks that require SSL certificates
    this.addAttribute(config, context, "ClientSSLKeystore", "ClientSSLKeystore");
    this.addAttribute(config, context, "ClientSSLKeystorePassword", "ClientSSLKeystorePassword");
    //Read in the default module settings for CFS
    this.addAttribute(config, context, "ContainerMenuConfig", "ContainerMenuConfig");
    if (config.getInitParameter("DynamicFormConfig") != null) {
      context.setAttribute("DynamicFormConfig", config.getInitParameter("DynamicFormConfig"));
      CustomFormList forms = new CustomFormList(context, config.getInitParameter("DynamicFormConfig"));
      context.setAttribute("DynamicFormList", forms);
    }
    return null;
  }
  
  /**
   *  Adds a feature to the Attribute attribute of the InitHook class
   *
   *@param  config         The feature to be added to the Attribute attribute
   *@param  context        The feature to be added to the Attribute attribute
   *@param  attributeName  The feature to be added to the Attribute attribute
   *@param  paramName      The feature to be added to the Attribute attribute
   */
  private static void addAttribute(ServletConfig config, ServletContext context, String attributeName, String paramName) {
    if (config.getInitParameter(paramName) != null) {
      context.setAttribute(attributeName, config.getInitParameter(paramName));
    }
  }
}


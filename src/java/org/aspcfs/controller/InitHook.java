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
    //Determine the file library path and load the prefs if found
    ApplicationPrefs prefs = new ApplicationPrefs(context);
    context.setAttribute("applicationPrefs", prefs);
    //Define the keystore, to be used by tasks that require SSL certificates
    this.addAttribute(config, context, "ClientSSLKeystore", "ClientSSLKeystore");
    this.addAttribute(config, context, "ClientSSLKeystorePassword", "ClientSSLKeystorePassword");
    //Read in the default module settings
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


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
package org.aspcfs.controller;

import com.darkhorseventures.framework.servlets.ControllerInitHook;
import org.aspcfs.utils.web.CustomFormList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Code that is initialized when the ServletController starts.
 *
 * @author mrajkowski
 * @version $Id: InitHook.java,v 1.24.18.1 2003/08/12 21:12:56 mrajkowski Exp
 *          $
 * @created July 10, 2001
 */
public class InitHook implements ControllerInitHook {
  public final static String fs = System.getProperty("file.separator");


  /**
   * When the ServletController is initialized, this code maps init-params to
   * the Context or Application scope.
   *
   * @param config Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeControllerInit(ServletConfig config) {
    System.out.println("InitHook-> Executing");
    ServletContext context = config.getServletContext();
    //Determine the file library path and load the prefs if found
    ApplicationPrefs prefs = new ApplicationPrefs(context);
    context.setAttribute("applicationPrefs", prefs);
    //Define the keystore, to be used by tasks that require SSL certificates
    addAttribute(config, context, "ClientSSLKeystore", "ClientSSLKeystore");
    addAttribute(
        config, context, "ClientSSLKeystorePassword", "ClientSSLKeystorePassword");
    //Read in the default module settings
    addAttribute(
        config, context, "ContainerMenuConfig", "ContainerMenuConfig");
    if (config.getInitParameter("DynamicFormConfig") != null) {
      context.setAttribute(
          "DynamicFormConfig", config.getInitParameter("DynamicFormConfig"));
      CustomFormList forms = new CustomFormList(
          context, config.getInitParameter("DynamicFormConfig"));
      context.setAttribute("DynamicFormList", forms);
    }
    SyncHook.initSync(context);
    return null;
  }


  /**
   * Adds a feature to the Attribute attribute of the InitHook class
   *
   * @param config        The feature to be added to the Attribute attribute
   * @param context       The feature to be added to the Attribute attribute
   * @param attributeName The feature to be added to the Attribute attribute
   * @param paramName     The feature to be added to the Attribute attribute
   */
  private static void addAttribute(ServletConfig config, ServletContext context, String attributeName, String paramName) {
    if (config.getInitParameter(paramName) != null) {
      context.setAttribute(attributeName, config.getInitParameter(paramName));
    }
  }
}


package org.aspcfs.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.servlets.ControllerInitHook;
import com.darkhorseventures.database.*;
import org.aspcfs.utils.web.CustomFormList;
import java.sql.*;
import java.util.Hashtable;

/**
 *  Code that is initialized when the ServletController starts.
 *
 *@author     mrajkowski
 *@created    July 10, 2001
 *@version    $Id$
 */
public class InitHook implements ControllerInitHook {

  /**
   *  When the ServletController is initialized, this code creates a
   *  ConnectionPool and maps it to the Application scope.
   *
   *@param  config  Description of Parameter
   *@return         Description of the Returned Value
   *@since          1.1
   */
  public String executeControllerInit(ServletConfig config) {
    ServletContext context = config.getServletContext();

    InitHook.addAttribute(config, context, "SiteCode", "GATEKEEPER.APPCODE");
    InitHook.addAttribute(config, context, "GKDRIVER", "GATEKEEPER.DRIVER");
    InitHook.addAttribute(config, context, "GKHOST", "GATEKEEPER.URL");
    InitHook.addAttribute(config, context, "GKUSER", "GATEKEEPER.USER");
    InitHook.addAttribute(config, context, "GKPUSERPW", "GATEKEEPER.PASSWORD");

    if (config.getInitParameter("ForceSSL") != null) {
      if ("true".equals(config.getInitParameter("ForceSSL"))) {
        context.setAttribute("ForceSSL", "true");
      } else {
        context.setAttribute("ForceSSL", "false");
      }
    }

    if (config.getInitParameter("GlobalPWInfo") != null) {
      context.setAttribute("GlobalPWInfo",
          config.getInitParameter("GlobalPWInfo"));
    } else {
      context.setAttribute("GlobalPWInfo", "#notspecified");
    }

    InitHook.addAttribute(config, context, "ClientSSLKeystore", "ClientSSLKeystore");
    InitHook.addAttribute(config, context, "ClientSSLKeystorePassword", "ClientSSLKeystorePassword");
    InitHook.addAttribute(config, context, "ContainerMenuConfig", "ContainerMenuConfig");

    if (config.getInitParameter("DynamicFormConfig") != null) {
      context.setAttribute("DynamicFormConfig", config.getInitParameter("DynamicFormConfig"));
      CustomFormList forms = new CustomFormList(context, config.getInitParameter("DynamicFormConfig"));
      context.setAttribute("DynamicFormList", forms);
    }

    if (config.getInitParameter("MailServer") != null) {
      context.setAttribute("MailServer",
          config.getInitParameter("MailServer"));
      System.setProperty("MailServer", String.valueOf(config.getInitParameter("MailServer")));
    } else {
      context.setAttribute("MailServer", "127.0.0.1");
      System.setProperty("MailServer", "127.0.0.1");
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


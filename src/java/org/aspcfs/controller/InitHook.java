package com.darkhorseventures.framework.hooks;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.servlets.ControllerInitHook;
import com.darkhorseventures.database.*;
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
    if (config.getInitParameter("SiteCode") != null) {
      context.setAttribute("SiteCode", config.getInitParameter("SiteCode"));
    }

    if (config.getInitParameter("GKDRIVER") != null) {
      context.setAttribute("GKDRIVER", config.getInitParameter("GKDRIVER"));
    } else {
      context.setAttribute("GKDRIVER", "org.postgresql.Driver");
    }

    if (config.getInitParameter("GKHOST") != null) {
      context.setAttribute("GKHOST", config.getInitParameter("GKHOST"));
    }
    if (config.getInitParameter("GKUSER") != null) {
      context.setAttribute("GKUSER", config.getInitParameter("GKUSER"));
    }
    if (config.getInitParameter("GKUSERPW") != null) {
      context.setAttribute("GKUSERPW", config.getInitParameter("GKUSERPW"));
    }

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

    if (config.getInitParameter("ClientSSLKeystore") != null) {
      context.setAttribute("ClientSSLKeystore",
          config.getInitParameter("ClientSSLKeystore"));
    }

    if (config.getInitParameter("ClientSSLKeystorePassword") != null) {
      context.setAttribute("ClientSSLKeystorePassword",
          config.getInitParameter("ClientSSLKeystorePassword"));
    }

    if (config.getInitParameter("ContainerMenuConfig") != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("InitHook-> Setting the ContainerMenuConfig property: " +
            config.getInitParameter("ContainerMenuConfig"));
      }
      context.setAttribute("ContainerMenuConfig",
          config.getInitParameter("ContainerMenuConfig"));
    }

    if (config.getInitParameter("DynamicFormConfig") != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("InitHook-> Setting the DynamicFormConfig property: " +
            config.getInitParameter("DynamicFormConfig"));
      }
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

}


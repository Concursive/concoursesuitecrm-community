package org.aspcfs.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.servlets.ControllerInitHook;
import com.darkhorseventures.database.*;
import org.aspcfs.utils.web.CustomFormList;
import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;
import org.jcrontab.Crontab;
import org.aspcfs.utils.StringUtils;

/**
 *  Code that is initialized when the ServletController starts.
 *
 *@author     mrajkowski
 *@created    July 10, 2001
 *@version    $Id$
 */
public class InitHook implements ControllerInitHook {
  public static final String fs = System.getProperty("file.separator");
  
  /**
   *  When the ServletController is initialized, this code maps init-params
   *  to the Context or Application scope.
   *
   *@param  config  Description of Parameter
   *@return         Description of the Returned Value
   *@since          1.1
   */
  public String executeControllerInit(ServletConfig config) {
    System.out.println("InitHook-> Executing");
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
    
    
    //Start the cron last
    if ("true".equals(config.getInitParameter("CRON.ENABLED"))) {
      try {
        System.out.println("InitHook-> Starting CRON");
        Class cl = Class.forName("org.aspcfs.apps.notifier.Notifier");
        Crontab crontab = Crontab.getInstance();
        Properties jcronProperties = new Properties();
        //jcron settings
        jcronProperties.setProperty("org.jcrontab.Crontab.refreshFrequency", "1");
/*
        //jcron datasource for cron entries
        jcronProperties.setProperty("org.jcrontab.data.datasource", "org.jcrontab.data.FileSource");
        jcronProperties.setProperty("org.jcrontab.data.file", context.getRealPath("/") + "WEB-INF" + fs + "crontab");
*/
        //Use a database instead...
        jcronProperties.setProperty("org.jcrontab.data.datasource", "org.jcrontab.data.GenericSQLSource");
        jcronProperties.setProperty("org.jcrontab.data.GenericSQLSource.driver", StringUtils.toString((String)context.getAttribute("GKDRIVER")));
        jcronProperties.setProperty("org.jcrontab.data.GenericSQLSource.url", StringUtils.toString((String)context.getAttribute("GKHOST")));
        jcronProperties.setProperty("org.jcrontab.data.GenericSQLSource.username", StringUtils.toString((String)context.getAttribute("GKUSER")));
        jcronProperties.setProperty("org.jcrontab.data.GenericSQLSource.password", StringUtils.toString((String)context.getAttribute("GKUSERPW")));
        
        //jcron logger -- TODO: implement a database logger
        jcronProperties.setProperty("org.jcrontab.log.Logger", "org.jcrontab.log.DebugLogger");
        crontab.init(jcronProperties);
        context.setAttribute("Crontab", crontab);
      } catch (Exception e) {
        System.err.println(e.toString());
      }
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


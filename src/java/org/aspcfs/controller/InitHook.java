package org.aspcfs.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.servlets.ControllerInitHook;
import com.darkhorseventures.database.*;
import org.aspcfs.utils.web.CustomFormList;
import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;
import org.jcrontab.*;
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
    //Add the gatekeeper info to the context
    InitHook.addAttribute(config, context, "SiteCode", "GATEKEEPER.APPCODE");
    InitHook.addAttribute(config, context, "GKDRIVER", "GATEKEEPER.DRIVER");
    InitHook.addAttribute(config, context, "GKHOST", "GATEKEEPER.URL");
    InitHook.addAttribute(config, context, "GKUSER", "GATEKEEPER.USER");
    InitHook.addAttribute(config, context, "GKUSERPW", "GATEKEEPER.PASSWORD");
    
    //Define the ConnectionPool, else defaults from the ContextListener will be used
    ConnectionPool cp = (ConnectionPool) context.getAttribute("ConnectionPool");
    if (cp != null) {
      if (config.getInitParameter("CONNECTION_POOL.DEBUG") != null) {
        cp.setDebug(config.getInitParameter("CONNECTION_POOL.DEBUG"));
      }
      if (config.getInitParameter("CONNECTION_POOL.TEST_CONNECTIONS") != null) {
        cp.setTestConnections(config.getInitParameter("CONNECTION_POOL.TEST_CONNECTIONS"));
      }
      if (config.getInitParameter("CONNECTION_POOL.ALLOW_SHRINKING") != null) {
        cp.setAllowShrinking(config.getInitParameter("CONNECTION_POOL.ALLOW_SHRINKING"));
      }
      if (config.getInitParameter("CONNECTION_POOL.MAX_CONNECTIONS") != null) {
        cp.setMaxConnections(config.getInitParameter("CONNECTION_POOL.MAX_CONNECTIONS"));
      }
      if (config.getInitParameter("CONNECTION_POOL.MAX_IDLE_TIME.SECONDS") != null) {
        cp.setMaxIdleTimeSeconds(config.getInitParameter("CONNECTION_POOL.MAX_IDLE_TIME.SECONDS"));
      }
      if (config.getInitParameter("CONNECTION_POOL.MAX_DEAD_TIME.SECONDS") != null) {
        cp.setMaxDeadTimeSeconds(config.getInitParameter("CONNECTION_POOL.MAX_DEAD_TIME.SECONDS"));
      }
    }
    
    
    //Define whether the app requires SSL for browser clients
    if (config.getInitParameter("ForceSSL") != null) {
      if ("true".equals(config.getInitParameter("ForceSSL"))) {
        context.setAttribute("ForceSSL", "true");
      } else {
        context.setAttribute("ForceSSL", "false");
      }
    }
    //Define the developer's debug code
    if (config.getInitParameter("GlobalPWInfo") != null) {
      context.setAttribute("GlobalPWInfo",
          config.getInitParameter("GlobalPWInfo"));
    } else {
      context.setAttribute("GlobalPWInfo", "#notspecified");
    }
    //Define the keystore, to be used by tasks that require SSL certificates
    InitHook.addAttribute(config, context, "ClientSSLKeystore", "ClientSSLKeystore");
    InitHook.addAttribute(config, context, "ClientSSLKeystorePassword", "ClientSSLKeystorePassword");
    //Read in the default module settings for CFS
    InitHook.addAttribute(config, context, "ContainerMenuConfig", "ContainerMenuConfig");
    if (config.getInitParameter("DynamicFormConfig") != null) {
      context.setAttribute("DynamicFormConfig", config.getInitParameter("DynamicFormConfig"));
      CustomFormList forms = new CustomFormList(context, config.getInitParameter("DynamicFormConfig"));
      context.setAttribute("DynamicFormList", forms);
    }
    //Define the mail server to be used within CFS
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
        Crontab crontab = Crontab.getInstance();
        Properties jcronProperties = new Properties();
        jcronProperties.setProperty("org.jcrontab.Crontab.refreshFrequency", "3");
        //Specify the cron items are in the gatekeeper database
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


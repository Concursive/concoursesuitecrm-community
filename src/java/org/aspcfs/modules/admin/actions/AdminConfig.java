package org.aspcfs.modules.admin.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.controller.ApplicationPrefs;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 9, 2003
 *@version    $Id$
 */
public final class AdminConfig extends CFSModule {

  /**
   *  Action that prepares a list of the editable global parameters
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListGlobalParams(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    //get the session timeout
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    int sessionTimeout = ((SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl())).getSessionTimeout();
    context.getRequest().setAttribute("Timeout", String.valueOf(sessionTimeout / 60));
    return ("GlobalParamsOK");
  }


  /**
   *  Action that routes to the modify timeout page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyTimeout(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    return ("ModifyTimeoutOK");
  }


  /**
   *  Action that updates the global session timeout from form data
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateTimeout(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    int timeout = Integer.parseInt(context.getRequest().getParameter("timeout"));
    addModuleBean(context, "Configuration", "Configuration");
    //get the session timeout and update
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    thisSystem.setSessionTimeout(timeout * 60);
    return "UpdateOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    String module = context.getRequest().getParameter("param");
    //Allowable params configured
    if (module == null) {
      return "ModifyError";
    }
    if ("MAILSERVER".equals(module)) {
      return "ModifyEmailOK";
    }
    if ("EMAILADDRESS".equals(module)) {
      return "ModifyEmailAddressOK";
    }
    if ("FAXSERVER".equals(module)) {
      return "ModifyFaxOK";
    }
    if ("WEBSERVER.URL".equals(module)) {
      return "ModifyUrlOK";
    }
    return "ModifyError";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    //Prepare to change the prefs
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("APPLICATION.PREFS");
    if (prefs == null) {
      return "ModifyError";
    }
    //Process the request
    String email = context.getRequest().getParameter("email");
    if (email != null) {
      prefs.add("MAILSERVER", email);
    }
    //Process the request
    String emailAddress = context.getRequest().getParameter("emailAddress");
    if (emailAddress != null) {
      prefs.add("EMAILADDRESS", emailAddress);
    }
    //Process the request
    String fax = context.getRequest().getParameter("fax");
    if (fax != null) {
      prefs.add("FAXSERVER", fax);
    }
    //Process the request
    String url = context.getRequest().getParameter("url");
    if (url != null) {
      prefs.add("WEBSERVER.URL", url);
    }
    prefs.save();
    prefs.populateContext(context.getServletContext());
    return "UpdateOK";
  }
}


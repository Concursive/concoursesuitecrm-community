package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public final class CampaignManagerAttachment extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-messages-edit"))) {
      return ("PermissionError");
    } else {
      String submenu = context.getRequest().getParameter("submenu");
      if (submenu == null) {
        submenu = (String) context.getRequest().getAttribute("submenu");
      }
      if (submenu == null) {
        submenu = "ManageSurveys";
      }

      context.getRequest().setAttribute("submenu", submenu);
      addModuleBean(context, submenu, "View Surveys");

      return ("DefaultOK");
    }
  }
}


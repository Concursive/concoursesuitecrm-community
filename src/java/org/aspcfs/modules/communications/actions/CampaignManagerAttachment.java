package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import org.theseus.actions.*;

public final class CampaignManagerAttachment extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
	  
	if (!(hasPermission(context, "campaign-campaigns-messages-edit"))) {
	    	return ("PermissionError");
    	} else {
		String submenu = context.getRequest().getParameter("submenu");
		if (submenu == null) {
			submenu = (String)context.getRequest().getAttribute("submenu");
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


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
package org.aspcfs.modules.communications.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: CampaignManagerAttachment.java,v 1.2 2003/01/15 21:16:05
 *          akhi_m Exp $
 * @created January 15, 2003
 */
public final class CampaignManagerAttachment extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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


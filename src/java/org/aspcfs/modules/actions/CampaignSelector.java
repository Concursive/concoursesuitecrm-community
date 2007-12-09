/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.Constants;

import java.sql.Connection;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 10, 2006
 * @version    $Id: Exp$
 */
public final class CampaignSelector extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    Connection db = null;
    CampaignList campaignList = null;
    String siteId = context.getRequest().getParameter("siteId");
    try {
      db = this.getConnection(context);
      String orgId = (String) context.getRequest().getParameter("orgId");
      String displayFieldId = (String) context.getRequest().getParameter(
          "displayFieldId");
      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      String hiddenFieldId = (String) context.getRequest().getParameter(
          "hiddenFieldId");
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);

      PagedListInfo campaignPagedInfo = this.getPagedListInfo(
          context, "campaignSelectorListInfo");
      campaignPagedInfo.setLink("CampaignSelector.do?command=List&hiddenFieldId=" + hiddenFieldId + "&displayFieldId=" + displayFieldId + (siteId != null && !"".equals(siteId.trim()) ? "&siteId=" + siteId : ""));

      campaignList = new CampaignList();
      campaignList.setPagedListInfo(campaignPagedInfo);
      if (siteId != null && !"".equals(siteId)) {
        campaignList.setIncludeAllSites(false);
        campaignList.setSiteId(siteId);
        campaignList.setExclusiveToSite(true);
      }
      campaignList.setCompleteOnly(true);
      campaignList.setType(Campaign.GENERAL);
      campaignList.buildList(db);
      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
      context.getRequest().setAttribute("campaignList", campaignList);
      if (siteId != null && !"".equals(siteId.trim())) {
        context.getRequest().setAttribute("siteId", siteId);
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListOK");
  }
}


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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.Constants;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created July 19, 2004
 */
public final class OpportunitySelector extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    Connection db = null;
    OpportunityHeaderList oppList = null;

    try {
      db = this.getConnection(context);
      String orgId = (String) context.getRequest().getParameter("orgId");
      String displayFieldId = (String) context.getRequest().getParameter(
          "displayFieldId");
      String hiddenFieldId = (String) context.getRequest().getParameter(
          "hiddenFieldId");

      PagedListInfo oppPagedInfo = this.getPagedListInfo(
          context, "opportunityListInfo");
      oppPagedInfo.setLink(
          "OpportunitySelector.do?command=List&orgId=" + orgId + "&hiddenFieldId=" + hiddenFieldId + "&displayFieldId=" + displayFieldId);

      oppList = new OpportunityHeaderList();
      oppList.setPagedListInfo(oppPagedInfo);
      oppList.setOrgId(orgId);
      oppList.setControlledHierarchy(Constants.TRUE, this.getUserRange(context));
      oppList.setQueryOpenOnly(true);
      oppList.setPagedListInfo(oppPagedInfo);
      oppList.buildList(db);
      
      // build the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      
      // bump off the opportunities that the user can not modify or delete.
      Iterator iterator = (Iterator) oppList.iterator();
      while (iterator.hasNext()) {
        OpportunityHeader header = (OpportunityHeader) iterator.next();
        header.buildManagerOwnerIdRange(db, accessTypeList, this.getUserRange(context));
        if (!checkAuthority(context, header.getManagerOwnerIdRange())) {
          iterator.remove();
        }
      }

      Organization orgDetails = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", orgDetails);

      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
      context.getRequest().setAttribute("orgId", orgId);

      context.getRequest().setAttribute("oppList", oppList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListOK");
  }
  
  public boolean checkAuthority(ActionContext context, String owner) {
    SystemStatus systemStatus = this.getSystemStatus(context);
    int userId = this.getUserId(context);
    String[] owners = owner.split(",");
    for (int i = 0; i < owners.length; i++) {
      String oneOwner = owners[i];
      if (userId == Integer.parseInt(oneOwner)) {
        return true;
      }
      User userRecord = systemStatus.getUser(userId);
      User childRecord = null;
      if (oneOwner != null && !"".equals(oneOwner.trim())) {
        childRecord = userRecord.getChild(Integer.parseInt(oneOwner));
      }
      if (childRecord != null) {
        return true;
      }
    }
    return false;
  }
}


package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

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
      oppList.setOwnerIdRange(this.getUserRange(context));
      oppList.setQueryOpenOnly(true);
      oppList.setPagedListInfo(oppPagedInfo);
      oppList.buildList(db);

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
}


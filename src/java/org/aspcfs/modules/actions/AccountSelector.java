package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.base.Filter;
import org.aspcfs.modules.base.Constants;

/**
 *  Creates a Accounts List for the Account popup with two levels of filters. <br>
 *  Can be used in two variants : Single/Multiple<br>
 *  Single and mutiple define if multiple accounts can be selected or just
 *  single.
 *
 *@author     Mathur
 *@created    March 17, 2003
 *@version    $Id: AccountSelector.java,v 1.1.2.1 2003/03/18 23:36:24 akhi_m Exp
 *      $
 */
public final class AccountSelector extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListAccounts(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    String listType = context.getRequest().getParameter("listType");
    OrganizationList acctList = null;
    OrganizationList finalAccounts = null;
    ArrayList selectedList = (ArrayList) context.getSession().getAttribute("SelectedAccounts");

    if (selectedList == null || "true".equals(context.getRequest().getParameter("reset"))) {
      selectedList = new ArrayList();
    }
    if (context.getRequest().getParameter("previousSelection") != null) {
      StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
      while (st.hasMoreTokens()) {
        selectedList.add(String.valueOf(st.nextToken()));
      }
    }
    
    try {
      db = this.getConnection(context);
      int rowCount = 1;
      acctList = new OrganizationList();

      if ("list".equals(listType)) {
        while (context.getRequest().getParameter("hiddenAccountId" + rowCount) != null) {
          int acctId = Integer.parseInt(context.getRequest().getParameter("hiddenAccountId" + rowCount));
          if (context.getRequest().getParameter("account" + rowCount) != null) {
            if (!selectedList.contains(String.valueOf(acctId))) {
              selectedList.add(String.valueOf(acctId));
            }
          } else {
            selectedList.remove(String.valueOf(acctId));
          }
          rowCount++;
        }
      }

      if ("true".equals((String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
          int acctId = Integer.parseInt(context.getRequest().getParameter("hiddenAccountId" + rowCount));
          selectedList.clear();
          selectedList.add(String.valueOf(acctId));
        }
        listDone = true;
        if (finalAccounts == null) {
          finalAccounts = new OrganizationList();
        }
        for (int i = 0; i < selectedList.size(); i++) {
          int orgId = Integer.parseInt((String) selectedList.get(i));
          finalAccounts.add(new Organization(db, orgId));
        }
      }

      LookupList typeSelect = new LookupList(db, "lookup_account_types");
      typeSelect.addItem(0, "All Types");
      context.getRequest().setAttribute("TypeSelect", typeSelect);

      //Set OrganizationList Parameters and build the list
      setParameters(acctList, context);
      acctList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("AccountList", acctList);
      context.getSession().setAttribute("SelectedAccounts", selectedList);
      if (listDone) {
        context.getRequest().setAttribute("FinalAccounts", finalAccounts);
      }
      return ("ListAccountsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Sets the parameters attribute of the AccountSelector object
   *
   *@param  context   The new parameters value
   *@param  acctList  The new parameters value
   */
  private void setParameters(OrganizationList acctList, ActionContext context) {

    boolean showMyCompany = "true".equals((String) context.getRequest().getParameter("showMyCompany"));
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("AccountListInfo");
    }

    PagedListInfo acctListInfo = this.getPagedListInfo(context, "AccountListInfo");
    //filter for departments & project teams
    if (!acctListInfo.hasListFilters()) {
      acctListInfo.addFilter(1, "0");
    }
    
    //add filters
    FilterList filters = new FilterList();
    filters.setSource(Constants.ACCOUNTS);
    filters.build(context.getRequest());
    context.getRequest().setAttribute("Filters", filters);
    
    //  set Filter for retrieving addresses depending on typeOfContact
    String firstFilter = filters.getFirstFilter(acctListInfo.getListView());
    
    acctList.setPagedListInfo(acctListInfo);
    acctList.setMinerOnly(false);
    acctList.setTypeId(acctListInfo.getFilterKey("listFilter1"));
    acctList.setShowMyCompany(showMyCompany);

    if ("my".equals(firstFilter)) {
      acctList.setOwnerId(this.getUserId(context));
    } else if ("disabled".equals(firstFilter)) {
      acctList.setIncludeEnabled(0);
    }
  }
}



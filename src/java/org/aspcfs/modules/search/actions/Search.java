package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import java.sql.*;

/**
 *  The Search module.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 */
public final class Search extends CFSModule {

  /**
   *  Currently does nothing important.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandSiteSearch(ActionContext context) {
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");

    //Populate the SearchBean
    //SearchResultsBean thisBean = new SearchResultsBean();
    //thisBean.setFirstName(thisUser.getFirstName());
    //thisBean.setLastName(thisUser.getLastName());

    //Put it in the request
    //context.getRequest().setAttribute("SearchResultsBean", thisBean);

    addModuleBean(context, "Search", "Search Results");
    return ("SearchOK");
  }

}


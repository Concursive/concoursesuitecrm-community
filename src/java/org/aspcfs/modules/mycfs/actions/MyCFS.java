package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import java.sql.*;
import java.util.Vector;
import com.darkhorseventures.webutils.*;
import java.util.Enumeration;

/**
 *  The MyCFS module.
 *
 *@author     chris
 *@created    July 3, 2001
 *@version    $Id$
 */
public final class MyCFS extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return ("IncludeOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDeleteHeadline(ActionContext context) {
    //Process all parameters
    Enumeration parameters = context.getRequest().getParameterNames();
    Exception errorMessage = null;

    int orgId = -1;
    Connection db = null;

    try {
      db = this.getConnection(context);
      while (parameters.hasMoreElements()) {
        String param = (String)parameters.nextElement();

        if (context.getRequest().getParameter(param).equals("on")) {
          orgId = Integer.parseInt(param);
          Organization newOrg = new Organization();
          newOrg.setOrgId(orgId);
          newOrg.deleteMinerOnly(db);
          System.out.println(param + ": " + context.getRequest().getParameter(param) + "<br>");
        }
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "", "Headline Delete OK");
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Takes a look at the User Session Object and prepares the MyCFSBean for the
   *  JSP. The bean will contain all the information that the JSP can see.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandHeadline(ActionContext context) {
    addModuleBean(context, "Customize Headlines", "Customize Headlines");
    Exception errorMessage = null;

    PagedListInfo orgListInfo = this.getPagedListInfo(context, "HeadlineListInfo");
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
    orgListInfo.setLink("/MyCFS.do?command=Headline");

    Connection db = null;
    OrganizationList organizationList = new OrganizationList();

    try {
      db = this.getConnection(context);
      organizationList.setPagedListInfo(orgListInfo);
      organizationList.setMinerOnly(true);
      organizationList.setEnteredBy(thisUser.getUserId());
      organizationList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OrgList", organizationList);
      return ("HeadlineOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsertHeadline(ActionContext context) {
    addModuleBean(context, "Customize Headlines", "");
    int headlines = 0;
    Exception errorMessage = null;

    Connection db = null;

    String name = new String();
    name = context.getRequest().getParameter("name");

    Organization newOrg = (Organization)new Organization();
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");

    try {
      newOrg.setName(name);
      newOrg.setIndustry("1");
      newOrg.setEnteredBy(thisUser.getUserId());
      newOrg.setMiner_only(true);

      db = this.getConnection(context);
      newOrg.insert(db);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OrgDetails", newOrg);
      return ("GoHomeOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Takes a look at the User Session Object and prepares the MyCFSBean for the
   *  JSP. The bean will contain all the information that the JSP can see.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandHome(ActionContext context) {
    addModuleBean(context, "Home", "");
    int headlines = 0;
    Exception errorMessage = null;
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");

    int myId = thisUser.getUserId();

    String industryCheck = new String();
    String whereClause = new String();
    String dateRange = new String();
    String reportType = new String();

    Vector alertsList = new Vector();
    HtmlSelect alertsListSelect = new HtmlSelect();

    Vector newsList = new Vector();
    HtmlSelect industrySelect = new HtmlSelect();
    industrySelect.setSelectName("industry");

    HtmlSelect dateRangeSelect = new HtmlSelect();
    dateRangeSelect.setSelectName("dateRange");

    dateRangeSelect.setJsEvent("onChange=\"document.forms[0].submit();\"");

    HtmlSelect reportTypeSelect = new HtmlSelect();
    reportTypeSelect.setSelectName("reportType");
    reportTypeSelect.setJsEvent("onChange=\"document.forms[0].submit();\"");

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;
    ResultSet headline_rs = null;

    OpportunityList alertOpps = new OpportunityList();

    try {
      StringBuffer sql = new StringBuffer();
      StringBuffer sql_industry = new StringBuffer();

      //used to check the number of customized headlines
      PreparedStatement pst = null;
      StringBuffer headlineCount = new StringBuffer();
      headlineCount.append("SELECT COUNT(org_id) AS headlinecount " +
          "FROM organization " +
          "WHERE miner_only = 't' and industry_temp_code = 1 and enteredby = " + myId + " ");

      industryCheck = context.getRequest().getParameter("industry");
      dateRange = context.getRequest().getParameter("dateRange");
      reportType = context.getRequest().getParameter("reportType");

      if (dateRange == null) {
        dateRange = "0";
      }

      if (reportType == null) {
        reportType = "0";
      }

      sql.append("SELECT * from news ");

      sql_industry.append("SELECT code,name from industry_temp order by code ");

      db = this.getConnection(context);

      pst = db.prepareStatement(headlineCount.toString());
      headline_rs = pst.executeQuery();
      if (headline_rs.next()) {
        headlines = headline_rs.getInt("headlinecount");
      }
      pst.close();
      headline_rs.close();

      if (industryCheck != null) {
        industrySelect.setDefaultKey(industryCheck);
      } else if (industryCheck == null && headlines > 0) {
        industryCheck = "1";
        industrySelect.setDefaultKey(1);
      }

      if (industryCheck != null && !(industryCheck.equals("0"))) {
        whereClause = " WHERE organization.industry_temp_code = " + Integer.parseInt(industryCheck) +
            " AND organization.org_id = news.org_id ";

        if (industryCheck.equals("1")) {
          whereClause = whereClause + "AND organization.enteredby = "
               + thisUser.getUserId() + "AND organization.miner_only = 't' ";
        }

        sql.append(whereClause);
      } else if (industryCheck == null || industryCheck.equals("0")) {
        sql.append("WHERE organization.miner_only = 'f' AND organization.org_id = news.org_id ");
      }

      sql.append(" ORDER BY dateentered desc limit 10 ");

      st = db.createStatement();
      rs = st.executeQuery(sql_industry.toString());

      if (dateRange != null) {
        dateRangeSelect.setDefaultKey(dateRange);
      }

      if (reportType != null) {
        reportTypeSelect.setDefaultKey(reportType);
      }

      industrySelect.addItem(0, "Latest News");

      if (headlines > 0) {
        industrySelect.addItem(1, "My News");
      }

      industrySelect.addItems(rs, "code", "int", "name", "String");

      rs.close();

      dateRangeSelect.addItem(0, "Last Week");
      dateRangeSelect.addItem(1, "Last Month");
      dateRangeSelect.addItem(2, "Last 6 Months");
      dateRangeSelect.build();

      reportTypeSelect.addItem(0, "Milestones");
      reportTypeSelect.addItem(1, "&nbsp;&nbsp;Leads");
      reportTypeSelect.addItem(2, "&nbsp;&nbsp;Prospects");
      reportTypeSelect.addItem(3, "&nbsp;&nbsp;Hot Prospects");
      reportTypeSelect.addItem(4, "Activities");
      reportTypeSelect.addItem(5, "&nbsp;&nbsp;Calls Made");
      reportTypeSelect.addItem(6, "&nbsp;&nbsp;Contacts Completed");
      reportTypeSelect.addItem(7, "&nbsp;&nbsp;Presentations");
      reportTypeSelect.addItem(8, "&nbsp;&nbsp;Proposals");
      reportTypeSelect.addItem(9, "&nbsp;&nbsp;Contracts");

      reportTypeSelect.build();

      //Execute the main query
      rs = st.executeQuery(sql.toString());
      while (rs.next()) {
        NewsArticle thisNews = new NewsArticle(rs);
        newsList.addElement(thisNews);
      }
      rs.close();

      //	***
      //	Alerts Selection
      //
      alertsListSelect.setSelectName("alerts");
      alertsListSelect.addItem(0, "Opportunities");
      alertsListSelect.addItem(1, "Leads Contacted");
      alertsListSelect.addItem(2, "Presentations");
      alertsListSelect.addItem(3, "Proposals");
      alertsListSelect.addItem(4, "Contracts Signed");

      String alertsRequest = (String)context.getRequest().getParameter("alerts");
      if (alertsRequest == null) {
        alertsRequest = "0";
      }
      int i_alerts = Integer.parseInt(alertsRequest);
      alertsListSelect.setDefaultKey(i_alerts);
      alertsListSelect.build();

      //	***
      //	Alerts
      //
      Alert alert = null;
      UserBean user = (UserBean)context.getSession().getAttribute("User");
      switch (i_alerts) {
        case 0:
          sql = new StringBuffer();
          sql.append("SELECT ticketid,problem,modified,closed,pri_code,scode FROM ticket"
               + " WHERE ( assigned_to is null OR assigned_to = -1 or assigned_to = '" + getUserId(context) + "')"
               + " AND closed is null "
               + " ORDER BY scode DESC, pri_code DESC, modified");
          rs = st.executeQuery(sql.toString());
          while (rs.next()) {
            int ticketid = rs.getInt("ticketid");
            int pri_code = rs.getInt("pri_code");
            int scode = rs.getInt("scode");
            String status = "S" + scode + "/" + (rs.getDate("closed") == null ? "O" : "C");
            String color;
            String email = "";
            String desc = rs.getString("problem");

            if (desc.length() > 23) {
              desc = desc.substring(0, 20) + "...";
            }

            alert = new Alert(desc, status, email);
            alert.setId("" + ticketid);
            switch (pri_code) {
              case 0:
                color = "lightgreen";
                break;
              case 1:
                color = "yellow";
                break;
              case 2:
                color = "red";
                break;
              default:
                color = "red";
            }
            alert.setColor(color);

            alertsList.addElement(alert);
          }
          rs.close();
          break;
        case 1:
          alertsList.addElement(new Alert("Farm Fresh...", "O", "none"));
          alertsList.addElement(new Alert("Casey Isuzu...", "O", "none"));
          alertsList.addElement(new Alert("Great Bridge", "O", "none"));
          break;
        case 2:
          alertsList.addElement(new Alert("presentation due...", "", ""));
          break;
        case 3:
          alertsList.addElement(new Alert("proposal at...", "unknown", "none"));
          break;
        case 4:
          alertsList.addElement(new Alert("contract signing...", "unknown", "none"));
          break;
        default:
          alertsList.addElement(new Alert("other...", "unknown", "none"));
      }

      //
      //	***

      //Make sure the statement is closed
      st.close();

      //I'm just going to put the alertOpps here

      PagedListInfo alertOppsPaged = new PagedListInfo();
      alertOppsPaged.setMaxRecords(5);
      alertOppsPaged.setColumnToSortBy("alertdate");

      alertOpps.setPagedListInfo(alertOppsPaged);
      alertOpps.setEnteredBy(myId);
      alertOpps.setHasAlertDate(true);
      alertOpps.buildList(db);


    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("NewsList", newsList);
      context.getRequest().setAttribute("IndustryList", industrySelect);
      context.getRequest().setAttribute("dateRangeList", dateRangeSelect);
      context.getRequest().setAttribute("reportTypeList", reportTypeSelect);
      context.getRequest().setAttribute("alertsList", alertsList);
      context.getRequest().setAttribute("AlertsListSelection", alertsListSelect);
      context.getRequest().setAttribute("graphRange", dateRange);
      context.getRequest().setAttribute("rptType", reportType);
      context.getRequest().setAttribute("AlertOppsList", alertOpps);
      return ("HomeOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Displays a list of profile items the user can select to modify
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyProfile(ActionContext context) {
    addModuleBean(context, "MyProfile", "");
    return ("MyProfileOK");
  }


  /**
   *  The user wants to modify their name, etc.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyCFSProfile(ActionContext context) {
    addModuleBean(context, "MyProfile", "");
    return ("ProfileOK");
  }


  /**
   *  The user's name was modified
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdateProfile(ActionContext context) {
    return ("UpdateProfileOK");
  }


  /**
   *  The user wants to change the password
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMyCFSPassword(ActionContext context) {
    addModuleBean(context, "MyProfile", "");
    return ("PasswordOK");
  }


  /**
   *  The password was modified
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdatePassword(ActionContext context) {
    return ("UpdatePasswordOK");
  }
  
  
  public String executeCommandMyCFSSettings(ActionContext context) {
    addModuleBean(context, "MyProfile", "");
    return ("SettingsOK");
  }

  public String executeCommandUpdateSettings(ActionContext context) {
    return ("UpdateSettingsOK");
  }
}


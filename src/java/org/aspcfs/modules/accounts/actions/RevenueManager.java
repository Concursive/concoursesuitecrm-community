package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.utils.web.*;
import java.text.*;
import java.io.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.base.GraphSummaryList;
import com.jrefinery.chart.*;
import com.jrefinery.chart.data.*;
import com.jrefinery.chart.ui.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.entity.StandardEntityCollection;
import com.jrefinery.chart.tooltips.TimeSeriesToolTipGenerator;
import java.awt.Color;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    November, 2002
 *@version    $Id: RevenueManager.java,v 1.24 2003/01/09 18:10:27 mrajkowski Exp
 *      $
 */
public final class RevenueManager extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-revenue-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Revenue", "Revenue");
    String errorMessage = null;

    //Prepare the user id to base all data on
    int idToUse = 0;
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = null;
    //Check if a specific user was selected
    int overrideId = StringUtils.parseInt(context.getRequest().getParameter("oid"), -1);
    //Check if the list is being reset
    if (context.getRequest().getParameter("reset") != null) {
      overrideId = -1;
      context.getSession().removeAttribute("revenueoverride");
      context.getSession().removeAttribute("revenueothername");
      context.getSession().removeAttribute("revenuepreviousId");
    }
    //Determine the user whose data is being shown, by default it's the current user
    if (overrideId > -1) {
      if (overrideId == getUserId(context)) {
        context.getSession().removeAttribute("revenueoverride");
        context.getSession().removeAttribute("revenueothername");
        context.getSession().removeAttribute("revenuepreviousId");
      }
    } else if (context.getSession().getAttribute("revenueoverride") != null) {
      overrideId = StringUtils.parseInt((String) context.getSession().getAttribute("revenueoverride"), -1);
    } else {
      overrideId = thisUser.getUserId();
    }

    //Check that the user hasAuthority for this oid
    if (hasAuthority(context, overrideId)) {
      idToUse = overrideId;
    } else {
      idToUse = thisUser.getUserId();
    }
    thisRec = this.getUser(context, idToUse);

    //Track the id in the request and the session
    if (idToUse > -1 && idToUse != getUserId(context)) {
      context.getSession().setAttribute("revenueoverride", String.valueOf(overrideId));
      context.getSession().setAttribute("revenueothername", thisRec.getContact().getNameFull());
      context.getSession().setAttribute("revenuepreviousId", String.valueOf(thisRec.getManagerId()));
    }

    //Check the cache and see if the current graph exists and is valid
    //TODO: This line invalidates the data no matter what... need to fix
    thisRec.setRevenueIsValid(false, true);
    String checkFileName = null;
    if (thisRec.getRevenueIsValid()) {
      checkFileName = thisRec.getRevenue().getLastFileName();
    }

    //Determine the year to use
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    String yearParam = context.getRequest().getParameter("year");
    if (yearParam == null) {
      yearParam = (String) context.getSession().getAttribute("revenueyear");
    }
    if (yearParam != null) {
      year = Integer.parseInt(yearParam);
      cal.set(Calendar.YEAR, year);
      context.getSession().setAttribute("revenueyear", yearParam);
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("RevenueManager-> YEAR: " + year);
    }

    //Determine the type to show
    String revenueType = context.getRequest().getParameter("type");
    if (revenueType == null) {
      revenueType = (String) context.getSession().getAttribute("revenuetype");
    }

    UserList fullChildList = new UserList();
    UserList shortChildList = new UserList();
    shortChildList = thisRec.getShortChildList();
    shortChildList.setRevenueYear(year);
    RevenueList realFullRevList = new RevenueList();
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Information about the org currently selected, etc.
      buildFormElements(context, db);

      //Build the revenueTypeList combo box
      RevenueTypeList rtl = new RevenueTypeList(db);
      rtl.addItem(0, "All Types");
      rtl.setJsEvent("onChange=\"document.forms[0].submit();\"");
      context.getRequest().setAttribute("RevenueTypeList", rtl);

      //Generate the account pagedList for the idToUse
      PagedListInfo revenueInfo = this.getPagedListInfo(context, "DBRevenueListInfo");
      revenueInfo.setLink("RevenueManager.do?command=Dashboard");
      OrganizationList displayList = new OrganizationList();
      if (revenueType != null) {
        shortChildList.setRevenueType(Integer.parseInt(revenueType));
        realFullRevList.setType(Integer.parseInt(revenueType));
        displayList.setRevenueType(Integer.parseInt(revenueType));
        context.getSession().setAttribute("revenuetype", revenueType);
      }
      displayList.setRevenueYear(year);
      displayList.setBuildRevenueYTD(true);
      displayList.setRevenueOwnerId(idToUse);
      displayList.buildList(db);
      Comparator comparator = new OrganizationYTDComparator();
      java.util.Collections.sort(displayList, comparator);
      context.getRequest().setAttribute("MyRevList", displayList);

      //FullChildList is the complete user hierarchy for the selected user and
      //is needed for the graph
      if (checkFileName == null) {
        fullChildList = thisRec.getFullChildList(shortChildList, new UserList());
        String range = fullChildList.getUserListIds(idToUse);
        //All of the revenue that make up this graph calculation
        realFullRevList.setYear(year);
        realFullRevList.setOwnerIdRange(range);
        realFullRevList.buildList(db);
      }

      //Build the YTD revenue for each child to display in list under graph
      shortChildList.buildRevenueYTD(db);
    } catch (Exception e) {
      errorMessage = e.toString();
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    if (checkFileName != null) {
      //Existing graph is good
      if (System.getProperty("DEBUG") != null) {
        System.out.println("RevenueManager-> Using cached chart");
      }
      context.getRequest().setAttribute("GraphFileName", checkFileName);
    } else {
      //Need to generate a new graph
      if (System.getProperty("DEBUG") != null) {
        System.out.println("RevenueManager-> Preparing the chart");
      }
      //Filter out the selected user for graph
      RevenueList tempRevList = new RevenueList();
      Iterator z = realFullRevList.iterator();
      while (z.hasNext()) {
        Revenue tempRev = (Revenue) (z.next());
        if (tempRev.getOwner() == idToUse) {
          tempRevList.addElement(tempRev);
        }
      }
      //add up all revenue for children line on graph
      UserList tempUserList = new UserList();
      Iterator n = fullChildList.iterator();
      while (n.hasNext()) {
        User thisRecord = (User) n.next();
        thisRecord.setRevenueIsValid(false, true);
        tempUserList = prepareLines(thisRecord, realFullRevList, tempUserList, year);
      }
      UserList linesToDraw = new UserList();
      linesToDraw = calculateLine(tempUserList, linesToDraw, year);
      //set my own, on top of the children line
      tempUserList = prepareLines(thisRec, tempRevList, tempUserList, year);
      linesToDraw = calculateLine(thisRec, linesToDraw, year);
      //Store the data in the collection
      XYSeriesCollection categoryData = createCategoryDataset(linesToDraw, year);
      //Vertical Axis characteristics
      NumberAxis vnAxis = new VerticalNumberAxis("");
      vnAxis.setAutoRangeIncludesZero(true);
      vnAxis.setTickMarksVisible(true);
      //Horizontal Axis characteristics
      HorizontalDateAxis hnAxis = new HorizontalDateAxis("");
      XYPlot bPlot = new XYPlot(categoryData, hnAxis, vnAxis);
      SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");
      TimeSeriesToolTipGenerator ttg = new TimeSeriesToolTipGenerator(
          sdf, NumberFormat.getInstance());
      StandardXYItemRenderer sxyir = new StandardXYItemRenderer(
          StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES,
          ttg);
      sxyir.setDefaultShapeFilled(false);
      bPlot.setRenderer(sxyir);
      //Draw the chart and save to file
      JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, bPlot, false);
      chart.setBackgroundPaint(Color.white);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("RevenueManager-> Drawing the chart");
      }
      //Output the chart
      int width = 275;
      int height = 200;
      try {
        String realPath = context.getServletContext().getRealPath("/");
        String filePath = realPath + "graphs" + fs;
        java.util.Date testDate = new java.util.Date();
        String fileName = String.valueOf(idToUse) + String.valueOf(testDate.getTime()) + String.valueOf(context.getSession().getCreationTime());

        // Write the chart image
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        File imageFile = new File(filePath + fileName + ".jpg");
        ChartUtilities.saveChartAsJPEG(imageFile, 1.0f, chart, width, height, info);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath + fileName + ".map")));
        ChartUtilities.writeImageMap(pw, fileName, info);
        pw.flush();
        pw.close();

        //Update the cached filename
        thisRec.getRevenue().setLastFileName(fileName);
        context.getRequest().setAttribute("GraphFileName", fileName);
      } catch (IOException e) {
      }
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ShortChildList", shortChildList);
      return ("DashboardOK");
    } else {
      //A System Error occurred
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-revenue-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Add Revenue to Account");

    Exception errorMessage = null;
    Connection db = null;

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-revenue-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "View Revenue List");
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");
    PagedListInfo revenueInfo = this.getPagedListInfo(context, "RevenueListInfo");
    revenueInfo.setLink("RevenueManager.do?command=View&orgId=" + orgid);

    Connection db = null;
    RevenueList revenueList = new RevenueList();
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      revenueList.setPagedListInfo(revenueInfo);
      revenueList.setOrgId(Integer.parseInt(orgid));
      if ("all".equals(revenueInfo.getListView())) {
        revenueList.setOwnerIdRange(this.getUserRange(context));
      } else {
        revenueList.setOwner(this.getUserId(context));
      }
      revenueList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("RevenueList", revenueList);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-revenue-delete")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    Revenue thisRevenue = null;
    Organization thisOrganization = null;

    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;

    try {
      db = this.getConnection(context);
      thisRevenue = new Revenue(db, context.getRequest().getParameter("id"));
      thisOrganization = new Organization(db, Integer.parseInt(orgId));

      if (!hasAuthority(context, thisRevenue.getOwner())) {
        return "PermissionError";
      }
      recordDeleted = thisRevenue.delete(db, context);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Delete Revenue");

    if (errorMessage == null) {
      //context.getRequest().setAttribute("orgId", orgId);
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        processErrors(context, thisRevenue.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-revenue-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Insert Revenue");
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;

    Revenue thisRevenue = null;
    Revenue newRevenue = null;

    thisRevenue = (Revenue) context.getFormBean();
    thisRevenue.setEnteredBy(getUserId(context));
    thisRevenue.setModifiedBy(getUserId(context));
    thisRevenue.setOwner(getUserId(context));

    try {
      db = this.getConnection(context);
      recordInserted = thisRevenue.insert(db, context);
      if (recordInserted) {
        newRevenue = new Revenue(db, String.valueOf(thisRevenue.getId()));
        context.getRequest().setAttribute("Revenue", newRevenue);
      } else {
        processErrors(context, thisRevenue.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("InsertOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-revenue-edit")) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Modify Revenue");
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");
    String passedId = context.getRequest().getParameter("id");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.
    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    //end

    Connection db = null;
    Revenue thisRevenue = null;
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      thisRevenue = new Revenue(db, "" + passedId);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
      buildFormElements(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, thisRevenue.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Revenue", thisRevenue);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-revenue-view")) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "View Revenue Details");
    Exception errorMessage = null;

    String revenueId = context.getRequest().getParameter("id");

    Connection db = null;
    Revenue newRevenue = null;
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      newRevenue = new Revenue(db, revenueId);

      //check whether or not the owner is an active User
      newRevenue.checkEnabledOwnerAccount(db);

      thisOrganization = new Organization(db, newRevenue.getOrgId());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, newRevenue.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Revenue", newRevenue);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-revenue-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;

    Revenue newRevenue = (Revenue) context.getFormBean();

    Organization thisOrganization = null;
    String orgid = context.getRequest().getParameter("orgId");

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newRevenue.setModifiedBy(getUserId(context));
      if (!hasAuthority(context, newRevenue.getOwner())) {
        return "PermissionError";
      }
      resultCount = newRevenue.update(db, context);
      if (resultCount == -1) {
        processErrors(context, newRevenue.getErrors());
        buildFormElements(context, db);
        thisOrganization = new Organization(db, Integer.parseInt(orgid));
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
        return (executeCommandModify(context));
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandView(context));
        } else {
          return ("UpdateOK");
        }
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  y  Description of the Parameter
   *@param  m  Description of the Parameter
   *@param  d  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static java.util.Date createDate(int y, int m, int d) {
    GregorianCalendar calendar = new GregorianCalendar(y, m, d, 0, 0, 0);
    return calendar.getTime();
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    RevenueTypeList rtl = new RevenueTypeList(db);
    rtl.addItem(0, "--None--");
    context.getRequest().setAttribute("RevenueTypeList", rtl);

    HtmlSelect monthList = new HtmlSelect();
    monthList.setTypeUSMonths();
    monthList.setSelectName("month");
    context.getRequest().setAttribute("MonthList", monthList);

    HtmlSelect yearList = new HtmlSelect();
    yearList.setTypeYears(1990);
    yearList.setSelectName("year");
    context.getRequest().setAttribute("YearList", yearList);
  }


  /**
   *  Description of the Method
   *
   *@param  pertainsTo    Description of the Parameter
   *@param  revList       Description of the Parameter
   *@param  usersToGraph  Description of the Parameter
   *@param  year          Description of the Parameter
   *@return               Description of the Return Value
   */
  private UserList prepareLines(User pertainsTo, RevenueList revList, UserList usersToGraph, int year) {
    if (!pertainsTo.getRevenueIsValid()) {
      pertainsTo.doRevenueLock();
      if (!pertainsTo.getRevenueIsValid()) {
        try {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("RevenueManager-> (RE)BUILDING REVENUE DATA FOR " + pertainsTo.getId());
          }
          pertainsTo.setRevenue(new GraphSummaryList());
          Iterator revIterator = revList.iterator();
          while (revIterator.hasNext()) {
            Revenue tempRev = (Revenue) revIterator.next();
            if (tempRev.getOwner() == pertainsTo.getId()) {
              int passedDay = 0;
              int passedYear = tempRev.getYear();
              if (passedYear == year) {
                int passedMonth = (tempRev.getMonth() - 1);
                String valKey = String.valueOf(passedYear) + String.valueOf(passedMonth);
                Double revenueAddTerm = new Double(tempRev.getAmount());
                pertainsTo.setRevenueGraphValues(valKey, revenueAddTerm);
              }
            }
          }
          pertainsTo.setRevenueIsValid(true, true);
        } catch (Exception e) {
          System.err.println("Revenue Manager-> Unwanted exception occurred: " + e.toString());
        } finally {
        }
      }
      pertainsTo.doRevenueUnlock();
    }
    usersToGraph.addElement(pertainsTo);
    if (revList.size() == 0) {
      return new UserList();
    } else {
      return usersToGraph;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  primaryNode   Description of the Parameter
   *@param  currentLines  Description of the Parameter
   *@param  year          Description of the Parameter
   *@return               Description of the Return Value
   */
  private UserList calculateLine(User primaryNode, UserList currentLines, int year) {
    if (currentLines.size() == 0) {
      currentLines.addElement(primaryNode);
      return currentLines;
    }
    User thisLine = new User();
    String[] valKeys = thisLine.getRevenue().getYearRange(12, year);
    Iterator x = currentLines.iterator();
    User addToMe = (User) x.next();
    for (int count = 0; count < 12; count++) {
      thisLine.getRevenue().setValue(valKeys[count], new Double(primaryNode.getRevenue().getValue(valKeys[count]).doubleValue() + (addToMe.getRevenue().getValue(valKeys[count])).doubleValue()));
    }
    currentLines.addElement(thisLine);
    return currentLines;
  }


  /**
   *  Description of the Method
   *
   *@param  toRollUp      Description of the Parameter
   *@param  currentLines  Description of the Parameter
   *@param  year          Description of the Parameter
   *@return               Description of the Return Value
   */
  private UserList calculateLine(UserList toRollUp, UserList currentLines, int year) {
    if (toRollUp.size() == 0) {
      return new UserList();
    }
    User thisLine = new User();
    String[] valKeys = thisLine.getRevenue().getYearRange(12, year);
    Iterator x = toRollUp.iterator();
    while (x.hasNext()) {
      User thisUser = (User) x.next();
      for (int count = 0; count < 12; count++) {
        thisLine.getRevenue().setValue(valKeys[count], thisUser.getRevenue().getValue(valKeys[count]));
      }
    }
    currentLines.addElement(thisLine);
    return currentLines;
  }


  /**
   *  Description of the Method
   *
   *@param  year         Description of the Parameter
   *@param  linesToDraw  Description of the Parameter
   *@return              Description of the Return Value
   */
  private XYSeriesCollection createCategoryDataset(UserList linesToDraw, int year) {
    XYSeriesCollection xyDataset = new XYSeriesCollection();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("RevenueManager-> Lines to draw: " + linesToDraw.size());
    }
    if (linesToDraw.size() == 0) {
      return xyDataset;
    }
    Calendar iteratorDate = Calendar.getInstance();
    iteratorDate.set(Calendar.YEAR, year);
    Iterator users = linesToDraw.iterator();
    while (users.hasNext()) {
      User thisUser = (User) users.next();
      XYSeries dataSeries = new XYSeries(null);
      String[] valKeys = thisUser.getRevenue().getYearRange(12, year);
      for (int count = 0; count < 12; count++) {
        java.util.Date dateValue = createDate(iteratorDate.get(Calendar.YEAR), count, 1);
        Double itemValue = thisUser.getRevenue().getValue(valKeys[count]);
        dataSeries.add(dateValue.getTime(), itemValue);
      }
      xyDataset.addSeries(dataSeries);
    }
    return xyDataset;
  }

}


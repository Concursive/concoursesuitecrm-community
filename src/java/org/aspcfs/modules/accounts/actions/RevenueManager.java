package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import java.text.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import com.sun.image.codec.jpeg.*;

import com.jrefinery.chart.*;
import com.jrefinery.chart.data.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.ui.*;

public final class RevenueManager extends CFSModule {
	
  public String executeCommandDashboard(ActionContext context) {
	if (!(hasPermission(context, "accounts-accounts-revenue-view"))) {
	    	return ("PermissionError");
    	}

    addModuleBean(context, "Revenue", "Revenue");
    
    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    int errorCode = 0;
    int idToUse = 0;
    
    java.util.Date d = new java.util.Date();
    int y = d.getYear() + 1900;
    
    String errorMessage = "";
    String fileName = "";
    StringBuffer sql = new StringBuffer();
    String checkFileName = "";
    
    //String graphString = context.getRequest().getParameter("type");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    String overrideId = null;
    
    if (context.getRequest().getParameter("oid") != null) {
	    System.out.println("Based on req");
	    overrideId = context.getRequest().getParameter("oid");
	    if (Integer.parseInt(overrideId) == getUserId(context)) {
		context.getSession().setAttribute("override", null);
		context.getSession().setAttribute("othername", null);
		context.getSession().setAttribute("previousId", null);
      	    }
    } else if (context.getSession().getAttribute("override") != null) {
	    overrideId = (String)context.getSession().getAttribute("override");
    } 
    
    User thisRec = null;

    UserList shortChildList = new UserList();
    UserList fullChildList = new UserList();
    UserList tempUserList = new UserList();
    UserList linesToDraw = new UserList();

    RevenueList tempRevList = new RevenueList();
    RevenueList realFullRevList = new RevenueList();
    
    OrganizationList displayList = new OrganizationList();

    XYDataset categoryData = null;
    
    if (context.getRequest().getParameter("reset") != null) {
      overrideId = null;
      context.getSession().setAttribute("override", null);
      context.getSession().setAttribute("othername", null);
      context.getSession().setAttribute("previousId", null);
    }

    if (overrideId != null && !(overrideId.equals("null")) && !(Integer.parseInt(overrideId) == getUserId(context))) {
      idToUse = Integer.parseInt(overrideId);
      thisRec = thisUser.getUserRecord().getChild(idToUse);
      context.getSession().setAttribute("override", overrideId);
      context.getSession().setAttribute("othername", thisRec.getContact().getNameFull());
      context.getSession().setAttribute("previousId", "" + thisRec.getManagerId());
    } else {
      idToUse = thisUser.getUserId();
      thisRec = thisUser.getUserRecord();
    }

    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
      
      //graph type select
      RevenueTypeList rtl = new RevenueTypeList(db);
      rtl.addItem(0, "--All--");
      rtl.setJsEvent("onChange=\"document.forms[0].submit();\"");
      context.getRequest().setAttribute("RevenueTypeList", rtl);
      //end
      
      PagedListInfo revenueInfo = this.getPagedListInfo(context, "DBRevenueListInfo");
      revenueInfo.setLink("/RevenueManager.do?command=Dashboard");
      
      //read in the year
      if (context.getRequest().getParameter("year") != null) {
	      y = Integer.parseInt(context.getRequest().getParameter("year"));
	      d.setYear(y - 1900);
	      context.getSession().setAttribute("year", context.getRequest().getParameter("year"));
      } else if (context.getSession().getAttribute("year") != null) {
	      System.out.println("Year is read as : " + (String)context.getSession().getAttribute("year"));
	      y = Integer.parseInt((String)context.getSession().getAttribute("year"));
	      d.setYear(y - 1900);
	      context.getSession().setAttribute("year", (String)context.getSession().getAttribute("year"));
      } 
      //end 
      
      shortChildList = thisRec.getShortChildList();
      shortChildList.setRevenueYear(y);
      
      if (context.getRequest().getParameter("type") != null) {
	      shortChildList.setRevenueType(Integer.parseInt(context.getRequest().getParameter("type")));
	      realFullRevList.setType(Integer.parseInt(context.getRequest().getParameter("type")));
	      
	      displayList.setRevenueType(Integer.parseInt(context.getRequest().getParameter("type")));
	      context.getSession().setAttribute("type", context.getRequest().getParameter("type"));
      }  else if (context.getSession().getAttribute("type") != null) {
	      shortChildList.setRevenueType(Integer.parseInt((String)context.getSession().getAttribute("type")));
	      realFullRevList.setType(Integer.parseInt((String)context.getSession().getAttribute("type")));
	      
	      displayList.setRevenueType(Integer.parseInt((String)context.getSession().getAttribute("type")));
      }
      
      fullChildList = thisRec.getFullChildList(shortChildList, new UserList());
      String range = fullChildList.getUserListIds(idToUse);
      
      //set the revenue YTD for each child in shortchildlist will use full-reporting range
      shortChildList.buildRevenueYTD(db);
      context.getRequest().setAttribute("ShortChildList", shortChildList);
      
      //might need this
      //thisRec.setRevenueIsValid(false, true);
      
      realFullRevList.setYear(y);
      realFullRevList.setOwnerIdRange(range);
      
      System.out.println("The type of realfullrevlist is: " + realFullRevList.getType());
      
      realFullRevList.buildList(db);
      
      displayList.setRevenueYear(y);
      displayList.setBuildRevenueYTD(true);
      
      //System.out.println("here is the session variable: " + context.getSession().getAttribute("type"));

      //filter out my revenue for displaying on page
      Iterator z = realFullRevList.iterator();

      while (z.hasNext()) {
        Revenue tempRev = (Revenue) (z.next());
        //tempRevList is MY (or user drilled-to) Revs
        if (tempRev.getOwner() == idToUse) {
          tempRevList.addElement(tempRev);
        }
      }
      
      displayList.setRevenueOwnerId(idToUse);
      //displayList.setPagedListInfo(revenueInfo);
      displayList.buildList(db);
      
      Comparator comparator = null;
      comparator = new OrganizationYTDComparator();
      
      java.util.Collections.sort(displayList, comparator);

      context.getRequest().setAttribute("MyRevList", displayList);
      
    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e.toString();
    } finally {
      this.freeConnection(context, db);
    }
    
    //invalidating...??
    thisRec.setRevenueIsValid(false, true);

    if (thisRec.getRevenueIsValid() == true) {
        checkFileName = thisRec.getRevenue().getLastFileName();
    }
    
    if (checkFileName.equals("")) {

      System.out.println("Revenue-> Preparing the chart");

      //add up all stuff for children

      Iterator n = fullChildList.iterator();

      while (n.hasNext()) {
        User thisRecord = (User) n.next();
	thisRecord.setRevenueIsValid(false, true);
        tempUserList = prepareLines(thisRecord, realFullRevList, tempUserList, y);
      }

      linesToDraw = calculateLine(tempUserList, linesToDraw, y);
      
      //set my own

      tempUserList = prepareLines(thisRec, tempRevList, tempUserList, y);

      //add me up -- keep this
      linesToDraw = calculateLine(thisRec, linesToDraw, y);

      categoryData = createCategoryDataset(linesToDraw, y);
      
      JFreeChart chart = ChartFactory.createXYChart("", "", "", categoryData, false);

      chart.setBackgroundPaint(Color.white);
      
      XYPlot bPlot = chart.getXYPlot();

      VerticalNumberAxis vnAxis = (VerticalNumberAxis) chart.getXYPlot().getVerticalAxis();
      vnAxis.setAutoRangeIncludesZero(true);
      vnAxis.setTickMarksVisible(true);
      bPlot.setRangeAxis(vnAxis);
      
      HorizontalNumberAxis hnAxis = (HorizontalNumberAxis) chart.getXYPlot().getHorizontalAxis();
      
      hnAxis.setAutoRangeIncludesZero(false);
      hnAxis.setAutoTickUnitSelection(false);
      hnAxis.setVerticalTickLabels(true);
      hnAxis.setTickMarksVisible(true);
      hnAxis.setAutoRange(false);

      Stroke gridStroke = new BasicStroke(0.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0.0f, new float[]{2.0f, 2.0f}, 0.0f);
      Paint gridPaint = Color.gray;
      
      ValueAxis myHorizontalDateAxis = new HorizontalDateAxis(hnAxis.getLabel(), hnAxis.getLabelFont(),
        hnAxis.getLabelPaint(), hnAxis.getLabelInsets(), true, hnAxis.getTickLabelFont(),
        hnAxis.getTickLabelPaint(), hnAxis.getTickLabelInsets(), true, true, hnAxis.getTickMarkStroke(),
        true, new Integer(0), new Range((d.getYear() + 1900), (d.getYear() + 1901)), false, new DateUnit(Calendar.MONTH, 1),
        new SimpleDateFormat("MMM ' ' yy"), true, gridStroke, gridPaint, false, null, null, null);
      
      myHorizontalDateAxis.setTickMarksVisible(true);
      
      try{
              bPlot.setDomainAxis(myHorizontalDateAxis);
      } catch (AxisNotCompatibleException err1) {
        System.out.println("AxisNotCompatibleException error!");
      }
      
      
      //define the chart
      int width = 275;
      int height = 200;

      System.out.println("Revenue-> Drawing the chart");
      BufferedImage img = draw(chart, width, height);

      //Output the chart
      try {
        String fs = System.getProperty("file.separator");

        String realPath = context.getServletContext().getRealPath("/");
        String filePath = realPath + "graphs" + fs;

        java.util.Date testDate = new java.util.Date();
        java.util.Calendar testCal = java.util.Calendar.getInstance();
        testCal.setTime(testDate);
        testCal.add(java.util.Calendar.MONTH, +1);

        fileName = new String(idToUse + testDate.getTime() + context.getSession().getCreationTime() + ".jpg");
        
	thisRec.getRevenue().setLastFileName(fileName);
        
	context.getRequest().setAttribute("GraphFileName", fileName);
        FileOutputStream foutstream = new FileOutputStream(filePath + fileName);

        JPEGImageEncoder encoder =
            JPEGCodec.createJPEGEncoder(foutstream);
        JPEGEncodeParam param =
            encoder.getDefaultJPEGEncodeParam(img);
        param.setQuality(1.0f, true);
        encoder.encode(img, param);
        foutstream.close();
      } catch (IOException e) {
      }

    } else {
      System.out.println("This file is valid, and cached: " + checkFileName);
      context.getRequest().setAttribute("GraphFileName", checkFileName);
    }

    if (errorCode == 0) {
      context.getRequest().setAttribute("UserInfo", thisRec);
      context.getRequest().setAttribute("FullChildList", fullChildList);
      context.getRequest().setAttribute("FullRevList", realFullRevList);
      //context.getRequest().setAttribute("GraphTypeList", graphTypeSelect);

      return ("DashboardOK");
    } else {
      //A System Error occurred
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }
	
	
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
  
  public String executeCommandView(ActionContext context) {
	if (!(hasPermission(context, "accounts-accounts-revenue-view"))) {
	    	return ("PermissionError");
    	}
    addModuleBean(context, "View Accounts", "View Revenue List");
    
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");

    PagedListInfo revenueInfo = this.getPagedListInfo(context, "RevenueListInfo");
    revenueInfo.setLink("/RevenueManager.do?command=View&orgId=" + orgid);

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
  
  public String executeCommandDelete(ActionContext context) {
	
	if (!(hasPermission(context, "accounts-accounts-revenue-delete"))) {
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
      recordDeleted = thisRevenue.delete(db, context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
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
  
   public String executeCommandInsert(ActionContext context) {
	
	if (!(hasPermission(context, "accounts-accounts-revenue-add"))) {
	    	return ("PermissionError");
    	}
	
    addModuleBean(context, "View Accounts", "Insert Revenue");
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;
    
    Revenue thisRevenue = null;
    Revenue newRevenue = null;
    Organization thisOrganization = null;
    
    thisRevenue = (Revenue)context.getFormBean();
    thisRevenue.setEnteredBy(getUserId(context));
    thisRevenue.setOwner(getUserId(context));
    
    try {
      db = this.getConnection(context);
      recordInserted = thisRevenue.insert(db, context);
      if (recordInserted) {
        newRevenue = new Revenue(db, "" + thisRevenue.getId());
        context.getRequest().setAttribute("Revenue", newRevenue);
        thisOrganization = new Organization(db, newRevenue.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
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
        //return ("DetailsOK");
	return("InsertOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandModify(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-revenue-edit"))) {
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
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
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
      context.getRequest().setAttribute("Revenue", thisRevenue);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandDetails(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-revenue-view"))) {
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
      thisOrganization = new Organization(db, newRevenue.getOrgId());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("Revenue", newRevenue);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  

  
  public String executeCommandUpdate(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-revenue-edit"))) {
	    	return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Revenue newRevenue = (Revenue)context.getFormBean();
    
    Organization thisOrganization = null;
    String orgid = context.getRequest().getParameter("orgId");
    
    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newRevenue.setModifiedBy(getUserId(context));
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
  
  protected BufferedImage draw(JFreeChart chart, int width, int height) {
    BufferedImage img =
        new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = img.createGraphics();

    chart.draw(g2, new Rectangle2D.Double((-21), 0, width + 21, height));

    g2.dispose();
    return img;
  }
  
  public static java.util.Date createDate(int y, int m, int d) {
    GregorianCalendar calendar = new GregorianCalendar(y, m, d, 0, 0, 0);
    return calendar.getTime();
  }
  
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    RevenueTypeList rtl = new RevenueTypeList(db);
    rtl.addItem(0, "--None--");
    context.getRequest().setAttribute("RevenueTypeList", rtl);

    //LookupList monthList = new LookupList(db, "lookup_months");
    //context.getRequest().setAttribute("MonthList", monthList);
    
    HtmlSelect monthList = new HtmlSelect();
    monthList.setTypeUSMonths();
    monthList.setSelectName("month");
    context.getRequest().setAttribute("MonthList", monthList);
    
    HtmlSelect yearList = new HtmlSelect();
    yearList.setTypeYears(1990);
    yearList.setSelectName("year");
    context.getRequest().setAttribute("YearList", yearList);
    
    //LookupList yearList = new LookupList(db, "lookup_years");
    //context.getRequest().setAttribute("YearList", yearList);

/**
    LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
*/
  }
  
  private XYDataset createEmptyCategoryDataset() {

    Object[][][] data;

    data = new Object[][][]{
        {
        {createDate(2001, 12, 20), new Integer(0)},
        {createDate(2002, 1, 18), new Integer(45)},
        {createDate(2002, 2, 18), new Integer(3)},
        {createDate(2002, 3, 18), new Integer(3)},
        {createDate(2002, 4, 18), new Integer(5)},
        {createDate(2002, 5, 18), new Integer(56)}
        }
        };

    return new DefaultXYDataset(data);
  }
  
  private UserList prepareLines(User pertainsTo, RevenueList revList, UserList usersToGraph, int y) {
    java.util.Date d = new java.util.Date();
    java.util.Calendar rightNow = java.util.Calendar.getInstance();
    d.setDate(1);
    d.setYear(y-1900);
    rightNow.setTime(d);
    
    int passedDay = 0;
    int passedYear = 0;
    int passedMonth = 0;

    Double revenueAddTerm = new Double(0.0);

    String valKey = "";

    //rightNow.add(java.util.Calendar.MONTH, +1);

    if (pertainsTo.getRevenueIsValid() == false) {
      pertainsTo.doRevenueLock();
      if (pertainsTo.getRevenueIsValid() == false) {
        try {
          System.out.println("(RE)BUILDING REVENUE DATA FOR " + pertainsTo.getId());

          pertainsTo.setRevenue(new GraphSummaryList());

          Iterator revIterator = revList.iterator();
          while (revIterator.hasNext()) {

            Revenue tempRev = (Revenue) revIterator.next();

            if (tempRev.getOwner() == pertainsTo.getId()) {

	      passedDay = 0;
              passedYear = tempRev.getYear();
              passedMonth = (tempRev.getMonth()-1);

              valKey = ("" + passedYear) + ("" + passedMonth);

              //get the individual graph values
              revenueAddTerm = new Double(tempRev.getAmount());
              //done

	      //System.out.println(passedYear + " " + rightNow.get(java.util.Calendar.YEAR));
	      
              //case: amount date within 12 month range
              if ( passedYear == rightNow.get(java.util.Calendar.YEAR) ) {
		//System.out.println("adding in " + revenueAddTerm + " at " + valKey);
                pertainsTo.setRevenueGraphValues(valKey, revenueAddTerm);
              }

              //more terms
              /**
	      	if ((java.lang.Math.round(tempOpp.getTerms())) > 1) {

                for (x = 1; x < (java.lang.Math.round(tempOpp.getTerms())); x++) {
                  readDate.add(java.util.Calendar.MONTH, +1);
                  if (((rightNow.before(readDate) || rightNowAdjusted.before(readDate)) && twelveMonths.after(readDate)) || rightNow.equals(readDate) || twelveMonths.equals(readDate)) {
                    valKey = ("" + readDate.get(java.util.Calendar.YEAR)) + ("" + readDate.get(java.util.Calendar.MONTH));

                    if (!(adjustTerms)) {
                      pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
                    }
                  }

                  adjustTerms = false;
                }
              }
	      */
	      
            }
          }
          pertainsTo.setRevenueIsValid(true, true);
        } catch (Exception e) {
          System.err.println("Revenue Manager-> Unwanted exception occurred: " + e.toString());
        } finally {
          pertainsTo.doRevenueUnlock();
        }
      } else {
        pertainsTo.doRevenueUnlock();
      }
    }

    usersToGraph.addElement(pertainsTo);

    if (revList.size() == 0) {
      return new UserList();
    } else {
      return usersToGraph;
    }
  }
  
  private UserList calculateLine(User primaryNode, UserList currentLines, int y) {
    if (currentLines.size() == 0) {
      currentLines.addElement(primaryNode);
      return currentLines;
    }

    User thisLine = new User();
    String[] valKeys = thisLine.getRevenue().getYearRange(12, y);

    Iterator x = currentLines.iterator();
    User addToMe = (User) x.next();

    int count = 0;

    for (count = 0; count < 12; count++) {
      thisLine.getRevenue().setValue(valKeys[count], new Double(primaryNode.getRevenue().getValue(valKeys[count]).doubleValue() + (addToMe.getRevenue().getValue(valKeys[count])).doubleValue()));
      //System.out.println("VK: " + valKeys[count]);
    }

    currentLines.addElement(thisLine);
    return currentLines;
  }
  
  private UserList calculateLine(UserList toRollUp, UserList currentLines, int y) {
    if (toRollUp.size() == 0) {
      return new UserList();
    }

    User thisLine = new User();
    String[] valKeys = thisLine.getRevenue().getYearRange(12, y);

    int count = 0;

    Iterator x = toRollUp.iterator();

    while (x.hasNext()) {
      User thisUser = (User) x.next();

      for (count = 0; count < 12; count++) {
        thisLine.getRevenue().setValue(valKeys[count], thisUser.getRevenue().getValue(valKeys[count]));
	//System.out.println("VK: " + valKeys[count]);
      }
    }

    currentLines.addElement(thisLine);
    return currentLines;
  }
  
  private XYDataset createCategoryDataset(UserList passedList, int y) {

    if (passedList.size() == 0) {
      return createEmptyCategoryDataset();
    }

    Object[][][] data;

    java.util.Date d = new java.util.Date();
    d.setYear(y-1900);
    
    java.util.Calendar iteratorDate = java.util.Calendar.getInstance();

    data = new Object[passedList.size()][12][2];
    int count = 0;
    int x = 0;

    Iterator n = passedList.iterator();

    while (n.hasNext()) {
      User thisUser = (User) n.next();

      String[] valKeys = thisUser.getRevenue().getYearRange(12, y);

      iteratorDate.setTime(d);

      for (count = 0; count < 12; count++) {
              data[x][count][0] = createDate(iteratorDate.get(java.util.Calendar.YEAR), count, 1);
              data[x][count][1] = thisUser.getRevenue().getValue(valKeys[count]);
      }

      x++;
    }

    return new DefaultXYDataset(data);
  }
 
}


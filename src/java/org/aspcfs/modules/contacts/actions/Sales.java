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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddress;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumber;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWork;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.GraphSummaryList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 4, 2005
 * @version    $Id: Sales.java 14202 2006-02-07 15:05:58 -0500 (Tue, 07 Feb
 *      2006) partha@darkhorseventures.com $
 */
public final class Sales extends CFSModule {
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandDashboard(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "sales-dashboard-view")) {
      if (!(hasPermission(context, "sales-view"))) {
        return ("PermissionError");
      }
      //Bypass dashboard to the search form
      return (executeCommandSearchForm(context));
    }
    //Prepare the user id to base all data on
    int idToUse = 0;
    User thisRec = null;
    int userId = this.getUserId(context);
    addModuleBean(context, "Dashboard", "Dashboard");

    //Check if a specific user was selected
    int overrideId = StringUtils.parseInt(
        context.getRequest().getParameter("oid"), -1);

    //Check if the list is being reset
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      overrideId = -1;
      context.getSession().removeAttribute("salesoverride");
      context.getSession().removeAttribute("salesothername");
      context.getSession().removeAttribute("salespreviousId");
    }
    //Determine the user whose data is being shown, by default it's the current user
    if (overrideId < 0) {
      if (context.getSession().getAttribute("salesoverride") != null) {
        overrideId = StringUtils.parseInt(
            (String) context.getSession().getAttribute("salesoverride"), -1);
      } else {
        overrideId = userId;
      }
    }

    String myLeads = (String) context.getRequest().getParameter("myLeads");
    String savedValue = (String) context.getSession().getAttribute("myLeads");
    if (myLeads != null && savedValue != null
         && !"".equals(myLeads) && !"".equals(savedValue)
         && !savedValue.equals(myLeads)) {
      context.getSession().removeAttribute("myLeads");
      context.getSession().setAttribute("myLeads", myLeads);
    }
    if (myLeads == null || "".equals(myLeads)) {
      if (savedValue != null && !"".equals(savedValue)) {
        myLeads = savedValue;
      }
    }
    if (savedValue == null || "".equals(savedValue)) {
      if (myLeads != null && !"".equals(myLeads)) {
        context.getSession().setAttribute("myLeads", myLeads);
      }
    }
    String checkFileName = null;
    Connection db = null;

    UserList fullChildList = new UserList();
    UserList shortChildList = new UserList();
    ContactList realFullLeadList = new ContactList();

    //Determine the graph type to generate
    String graphString = "lccr";
    Locale locale = getUser(context, getUserId(context)).getLocale();

    SystemStatus systemStatus = this.getSystemStatus(context);

    try {
      db = getConnection(context);
      //Check that the user hasAuthority for this oid
      if (hasAuthority(context, overrideId)) {
        idToUse = overrideId;
      } else {
        idToUse = this.getUserId(context);
      }
      thisRec = this.getUser(context, idToUse);
      shortChildList = thisRec.getShortChildList();
      shortChildList = UserList.sortEnabledUsers(
          shortChildList, new UserList());

      //Track the id in the request and the session
      if (context.getRequest().getParameter("oid") != null && !"true".equals(
          (String) context.getRequest().getParameter("reset"))) {
        context.getRequest().setAttribute("override", String.valueOf(idToUse));
        context.getRequest().setAttribute(
            "othername", thisRec.getContact().getNameFull());
        context.getRequest().setAttribute(
            "previousId", String.valueOf(thisRec.getManagerId()));
        context.getSession().setAttribute(
            "salesoverride", String.valueOf(overrideId));
        context.getSession().setAttribute(
            "salesothername", thisRec.getContact().getNameFull());
        context.getSession().setAttribute(
            "salespreviousId", String.valueOf(thisRec.getManagerId()));
      }

      //Check the cache and see if the current graph exists and is valid
      if (thisRec.getIsValidLead() == true) {
        if (graphString.equals("lccr")) {
          checkFileName = thisRec.getLccr().getLastFileName();
        }
      }

      // See if the file exists, otherwise reset the user and the checkFileName
      if (checkFileName != null) {
        File checkFile = new File(context.getServletContext().getRealPath("/") + "graphs" + fs + checkFileName + ".jpg");
        if (!checkFile.exists()) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Sales-> Invalidating data, file not found: " + context.getServletContext().getRealPath("/") + "graphs" + fs + checkFileName + ".jpg");
          }
          thisRec.setIsValidLead(false, true);
          checkFileName = null;
        }
      }
      //Generate the leads pagedList for the currentId, right of graph
      PagedListInfo dashboardListInfo = this.getPagedListInfo(
          context, "SalesDashboardListInfo");
      dashboardListInfo.setLink("Sales.do?command=Dashboard");
      dashboardListInfo.setColumnToSortBy("c.entered DESC");
      ContactList leads = new ContactList();
      leads.setPagedListInfo(dashboardListInfo);
      dashboardListInfo.setSearchCriteria(leads, context);
      leads.setExcludeUnapprovedContacts(true);
      leads.setOldestFirst(Constants.FALSE);
      leads.setSiteId(thisRec.getSiteId());
      leads.setExclusiveToSite(true);
      if (thisRec.getSiteId() == -1) {
        leads.setIncludeAllSites(true);
      }
      if (myLeads != null && "true".equals(myLeads)) {
        leads.setOwnerOrReader(true);
        leads.setLeadsOnly(Constants.TRUE);
        leads.setZipCodeAscPotentialDesc(true);
        leads.setReadBy(idToUse);
        leads.setOwner(idToUse);
        context.getRequest().setAttribute("myLeads", "" + false);
      } else {
        leads.setZipCodeAscPotentialDesc(true);
        leads.setLeadStatusExists(Constants.TRUE);
        context.getRequest().setAttribute("myLeads", "" + true);
      }
      leads.buildList(db);
      context.getRequest().setAttribute("contactList", leads);

      //Build the leads for the graph
      //FullChildList is the complete user hierarchy for the selected user and
      //is needed for the graph
      if (checkFileName == null) {
        fullChildList = thisRec.getFullChildList(
            shortChildList, new UserList());
        fullChildList = UserList.sortEnabledUsers(
            fullChildList, new UserList());
        String range = fullChildList.getUserListIds(idToUse);
        //All of the opportunities that make up this graph calculation
        //TODO: Set a max date for less records
        realFullLeadList.setOwnerIdRange(range);
        realFullLeadList.setLeadsOnly(Constants.FALSE);
        realFullLeadList.setEmployeesOnly(Constants.FALSE);
        realFullLeadList.setHasConversionDate(Constants.TRUE);
        realFullLeadList.setSiteId(thisRec.getSiteId());
        realFullLeadList.setExclusiveToSite(true);
        if (thisRec.getSiteId() == -1) {
          realFullLeadList.setIncludeAllSites(true);
        }
        realFullLeadList.buildList(db);
      }

      //Lookup Lists in the dashboard
      LookupList sources = new LookupList(db, "lookup_contact_source");
      sources.addItem(-1, systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("SourceList", sources);
      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      ratings.addItem(-1, systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("RatingList", ratings);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //TODO:: Graph related code
    try {
      //Determine if a graph has to be generated
      if (checkFileName != null) {
        //Existing graph is good
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Sales-> Using cached chart");
        }
        context.getRequest().setAttribute("GraphFileName", checkFileName);
      } else {
        //Need to generate a new graph
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Sales-> Preparing the chart");
        }

        //Prepare the values for the graph
        ContactList tempLeadList = new ContactList();
        Iterator z = realFullLeadList.iterator();
        while (z.hasNext()) {
          Contact tempLead = (Contact) z.next();
          if (tempLead.getOwner() == idToUse) {
            tempLeadList.add(tempLead);
          }
        }
        //add up all leads for children line on graph
        UserList tempUserList = new UserList();
        Iterator n = fullChildList.iterator();
        while (n.hasNext()) {
          User thisRecord = (User) n.next();
          tempUserList = prepareLines(
              thisRecord, realFullLeadList, tempUserList);
        }
        UserList linesToDraw = new UserList();
        linesToDraw = calculateLine(tempUserList, linesToDraw);
        //set my own, on top of the children line
        tempUserList = prepareLines(thisRec, tempLeadList, tempUserList);
        linesToDraw = calculateLine(thisRec, linesToDraw);

        if (System.getProperty("DEBUG") != null) {
          System.out.println("Sales-> Drawing the chart");
        }
        //Store the data in the collection
        XYSeriesCollection categoryData = createCategoryDataset(
            linesToDraw, graphString);
        JFreeChart chart =
            ChartFactory.createTimeSeriesChart(
            "",
            "",
            "",
            categoryData,
            false,
            true,
            false);
        chart.setBackgroundPaint(Color.white);
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
            DateFormat.SHORT, locale);
        // X and Y axis
        XYPlot plot = chart.getXYPlot();
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setTickMarksVisible(true);
        yAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));
        // Tell the chart how we would like dates to read
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(sdf);
        // Display data points
        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer) {
          StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
          rr.setPlotShapes(true);
          rr.setShapesFilled(false);
          rr.setItemLabelsVisible(true);
          // Tool tip formatting using locale {1} = Date, {2} = Amount
          StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
              "{2} ({1})", sdf, NumberFormat.getInstance(locale));
          rr.setToolTipGenerator(ttg);
        }
        //Output the chart
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Sales-> Drawing the chart");
        }
        int width = 275;
        int height = 200;
        String filePath = context.getServletContext().getRealPath("/") + "graphs" + fs;
        // Make sure the path exists before writing the image
        File graphDirectory = new File(filePath);
        if (!graphDirectory.exists()) {
          graphDirectory.mkdirs();
        }
        java.util.Date testDate = new java.util.Date();
        String fileName = "leads" + String.valueOf(testDate.getTime()) + String.valueOf(
            context.getSession().getCreationTime());

        ChartRenderingInfo info = new ChartRenderingInfo(
            new StandardEntityCollection());
        File imageFile = new File(filePath + fileName + ".jpg");
        ChartUtilities.saveChartAsJPEG(
            imageFile, 1.0f, chart, width, height, info);
        PrintWriter pw = new PrintWriter(
            new BufferedWriter(new FileWriter(filePath + fileName + ".map")), true);
        ChartUtilities.writeImageMap(pw, fileName, info, false);
        //Update the cached filename
        if (graphString.equals("lccr")) {
          thisRec.getLccr().setLastFileName(fileName);
        }
        context.getRequest().setAttribute("GraphFileName", fileName);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      System.out.println("Sales-> GraphicsError exception occurred here");
      return ("GraphicsError");
    }
    context.getRequest().setAttribute("ShortChildList", shortChildList);
    return "DashboardOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "sales-view"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      //Create the paged list info to store the search form
      PagedListInfo salesListInfo = this.getPagedListInfo(
          context, "SalesListInfo");
      salesListInfo.setCurrentLetter("");
      salesListInfo.setCurrentOffset(0);
//      salesListInfo.setItemsPerPage(100);

      //Lookup Lists in the dashboard
      LookupList sources = new LookupList(db, "lookup_contact_source");
      if (sources.size() > 0) {
        sources.addItem(-1, systemStatus.getLabel("pipeline.any"));
      }
      context.getRequest().setAttribute("SourceList", sources);
      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      if (ratings.size() > 0) {
        ratings.addItem(-1, systemStatus.getLabel("pipeline.any"));
      }
      context.getRequest().setAttribute("RatingList", ratings);
      CountrySelect countrySelect = new CountrySelect(
          systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("countrySelect", countrySelect);
      context.getRequest().setAttribute("listForm", "" + true);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      if (siteList.size() > 0) {
        siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
        siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      }
      context.getRequest().setAttribute("SiteIdList", siteList);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "SearchOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    String addAnother = (String) context.getRequest().getAttribute(
        "addAnother");
    if (addAnother == null || "".equals(addAnother.trim())) {
      thisContact = (Contact) context.getFormBean();
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = getConnection(context);
      //prepare userList for reassigning owner
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      User thisRec = thisUser.getUserRecord();
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(
          shortChildList, new UserList());
      userList.setMyId(getUserId(context));
      userList.setMyValue(thisUser.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
//      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      LookupList industryList = new LookupList(db, "lookup_industry");
      industryList.addItem(-1, systemStatus.getLabel("accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("IndustryList", industryList);
      context.getRequest().setAttribute("UserList", userList);
      if (thisContact != null) {
        context.getRequest().setAttribute("ContactDetails", thisContact);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "PrepareOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String reset = context.getRequest().getParameter("reset");
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null || "".equals(contactId.trim())) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    Contact thisContact = (Contact) context.getFormBean();
    String addAnother = (String) context.getRequest().getAttribute(
        "addAnother");
    thisContact = (Contact) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = getConnection(context);

      if (reset == null || !"true".equals(reset)) {
        thisContact = new Contact();
        thisContact.setBuildDetails(true);
        thisContact.queryRecord(db, Integer.parseInt(contactId));
      }
      //prepare userList for reassigning owner
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      User thisRec = thisUser.getUserRecord();
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(
          shortChildList, new UserList());
      userList.setMyId(getUserId(context));
      userList.setMyValue(thisUser.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      LookupList industryList = new LookupList(db, "lookup_industry");
      industryList.addItem(-1, systemStatus.getLabel("accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("IndustryList", industryList);
      context.getRequest().setAttribute("UserList", userList);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "PrepareOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    //check for appropriate permissions
    if (!hasPermission(context, "sales-leads-add")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    int resultCount = -1;
    boolean isValid = false;
    Contact oldContact = null;
    Contact thisContact = (Contact) context.getFormBean();
    thisContact.setRequestItems(context);
    if (thisContact.getId() == -1) {
      thisContact.setEnteredBy(getUserId(context));
    }
    thisContact.setModifiedBy(getUserId(context));
    thisContact.setIsLead(true);
    thisContact.setLeadStatus(Contact.LEAD_UNPROCESSED);
    Connection db = null;
    try {
      db = getConnection(context);
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(
          db, AccessType.GENERAL_CONTACTS);
      thisContact.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
      // trying to insert a contact
      isValid = validateObject(context, db, thisContact);
      if (isValid) {
        if (thisContact.getId() > 0) {
          oldContact = new Contact(db, thisContact.getId());
          resultCount = thisContact.update(db);
        } else {
          recordInserted = thisContact.insert(db);
        }
      }
      if (isValid && (recordInserted || resultCount != -1)) {
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        context.getRequest().setAttribute(
            "contactId", "" + thisContact.getId());
        if (resultCount != -1) {
          ActionPlanWork actionPlanWork = ActionPlanWork.getActionPlanWork(db, ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS), thisContact.getId());
          if (oldContact.getSiteId() != thisContact.getSiteId() && actionPlanWork != null && actionPlanWork.getId() > 0) {
            actionPlanWork.setManagerId(this.getUserId(context));
            actionPlanWork.update(db);
          }
          context.getRequest().setAttribute("actionPlanWork", actionPlanWork);
        }
      } else {
        if (thisContact.getId() > -1) {
          return executeCommandModify(context);
        } else {
          return executeCommandAdd(context);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Add a Lead");
    // decide what happend with the processing
    if (recordInserted) {
      if ("true".equals(
          (String) context.getRequest().getParameter("saveAndNew"))) {
        context.getRequest().removeAttribute("ContactDetails");
        context.getRequest().setAttribute("addAnother", "" + true);
        return executeCommandAdd(context);
      } else if ("true".equals(
          (String) context.getRequest().getParameter("saveAndClone"))) {
        return "SaveOK";
      }
    } else if (resultCount != -1) {
      return "SaveOK";
    }
    return executeCommandDashboard(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-view"))) {
      return ("PermissionError");
    }
    String nextValue = (String) context.getRequest().getAttribute("nextValue");
    if (nextValue == null || "".equals(nextValue)) {
      nextValue = (String) context.getRequest().getParameter("nextValue");
    }
    context.getRequest().setAttribute("nextValue", nextValue);
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    boolean isValid = false;
    boolean fetchedList = false;
    User user = this.getUser(context, this.getUserId(context));
    ContactList contacts = new ContactList();
    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SalesListInfo");
    searchListInfo.setLink(
        "Sales.do?command=List&listForm=" + (listForm != null ? listForm : ""));
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      if (searchListInfo.getSearchOptionValue("searchcodeCountry") != null) {
        StateSelect stateSelect = new StateSelect(systemStatus, searchListInfo.getSearchOptionValue("searchcodeCountry"));
        HashMap map = new HashMap();
        map.put((String) searchListInfo.getSearchOptionValue("searchcodeOtherState"), (String)searchListInfo.getSearchOptionValue("searchcodeOtherState"));
        stateSelect.setPreviousStates(map);
        context.getRequest().setAttribute("stateSelect", stateSelect);
      } else {
        StateSelect stateSelect = new StateSelect(systemStatus);
        context.getRequest().setAttribute("stateSelect", stateSelect);
      }

      LookupList sources = systemStatus.getLookupList(
          db, "lookup_contact_source");
      sources.addItem(-1, systemStatus.getLabel("quotes.all"));
      context.getRequest().setAttribute("sourceList", sources);

      LookupList ratings = systemStatus.getLookupList(
          db, "lookup_contact_rating");
      ratings.addItem(-1, systemStatus.getLabel("quotes.all"));
      context.getRequest().setAttribute("ratingList", ratings);
      LookupList segments = systemStatus.getLookupList(
          db, "lookup_industry");
      segments.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("segmentList", segments);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);

      contacts.setPagedListInfo(searchListInfo);
      searchListInfo.setIsValid(true);
      searchListInfo.setSearchCriteria(contacts, context);
      if (contacts.getSiteId() == Constants.INVALID_SITE) {
        contacts.setSiteId(user.getSiteId());
        contacts.setIncludeAllSites(true);
      }
      if (contacts.getOwner() == -1) {
        contacts.setControlledHierarchyOnly(true, this.getUserRange(context));
      }
      if (contacts.getLeadStatus() == Contact.LEAD_WORKED) {
        contacts.setLeadsOnly(Constants.FALSE);
        contacts.setLeadStatus(Constants.UNDEFINED);
        contacts.setHasConversionDate(Constants.TRUE);
      } else if (contacts.getLeadStatus() == Contact.LEAD_UNREAD) {
        contacts.setLeadsOnly(Constants.TRUE);
      } else if (contacts.getLeadStatus() == Contact.LEAD_ASSIGNED) {
        contacts.setLeadsOnly(Constants.TRUE);
      } else if (contacts.getLeadStatus() == -1) {
        contacts.setLeadStatus(-1);
        contacts.setLeadsOnly(Constants.UNDEFINED);
      }
      contacts.setLeadStatusExists(Constants.TRUE);
      contacts.setBuildDetails(false);
      contacts.setBuildTypes(false);
      isValid = searchListInfo.getIsValid();
      if (isValid) {
        fetchedList = contacts.buildList(db);
      }
      context.getRequest().setAttribute("contacts", contacts);

      //Make the CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      CountrySelect countrySelect = new CountrySelect(systemStatus);
      context.getRequest().setAttribute("countrySelect", countrySelect);
      context.getRequest().setAttribute("systemStatus", systemStatus);
      if (!fetchedList) {
        processErrors(context, contacts.getErrors());
        return executeCommandSearchForm(context);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Search Results");
    if (nextValue != null && !"".equals(nextValue) && contacts.size() > 0) {
      return executeCommandDetails(context);
    }
    return "ListOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-view"))) {
      return ("PermissionError");
    }
    User user = this.getUser(context, this.getUserId(context));
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    int readStatus = -1;
    String nextValue = (String) context.getRequest().getAttribute("nextValue");
    if (nextValue == null || "".equals(nextValue)) {
      nextValue = (String) context.getRequest().getParameter("nextValue");
    }
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String readStatusString = (String) context.getRequest().getAttribute(
        "readStatus");
    String contactId = (String) context.getRequest().getAttribute("contactId");
    if (contactId == null || "".equals(contactId)) {
      contactId = (String) context.getRequest().getParameter("contactId");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    ContactList contacts = new ContactList();
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SalesListInfo", false);
    Contact contact = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (nextValue != null && !"".equals(nextValue.trim())) {
        contacts.setPagedListInfo(searchListInfo);
        searchListInfo.setSearchCriteria(contacts, context);
        if (contacts.getSiteId() == Constants.INVALID_SITE) {
          contacts.setSiteId(user.getSiteId());
          if (user.getSiteId() == -1) {
            contacts.setIncludeAllSites(true);
          } else {
            contacts.setExclusiveToSite(true);
          }
        }
        if (contacts.getLeadStatus() == Contact.LEAD_WORKED) {
          contacts.setLeadsOnly(Constants.FALSE);
          contacts.setLeadStatus(-1);
          contacts.setHasConversionDate(Constants.TRUE);
        } else if (contacts.getLeadStatus() == Contact.LEAD_UNREAD) {
          contacts.setLeadsOnly(Constants.TRUE);
        } else if (contacts.getLeadStatus() == -1) {
          contacts.setLeadStatus(-1);
          contacts.setLeadsOnly(Constants.UNDEFINED);
        }
        contactId = "" + LeadUtils.getNextLead(
            db, Integer.parseInt(contactId), contacts, contacts.getSiteId(), (contacts.getSiteId() == Constants.INVALID_SITE));
      }
//    context.getRequest().setAttribute("nextValue", ""+true);

      LookupList sources = new LookupList(db, "lookup_contact_source");
      sources.addItem(
          -1, systemStatus.getLabel(
          "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("SourceList", sources);

      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      ratings.addItem(
          -1, systemStatus.getLabel(
          "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("RatingList", ratings);

      LookupList industryList = new LookupList(db, "lookup_industry");
      industryList.addItem(
          -1, systemStatus.getLabel(
          "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("IndustryList", industryList);

      if (readStatusString == null || "".equals(readStatusString.trim())) {
        if (hasPermission(context, "sales-leads-edit")) {
          readStatus = LeadUtils.setReadStatus(
              db, Integer.parseInt(contactId), this.getUserId(context));
        }
      } else {
        try {
          readStatus = Integer.parseInt(readStatusString);
        } catch (Exception error1) {
        }
      }
      context.getRequest().setAttribute("readStatus", "" + readStatus);
      if (contactId != null && !"".equals(contactId) && Integer.parseInt(
          contactId) != -1) {
        contact = new Contact(db, contactId);
        if (!isRecordAccessPermitted(context, contact)) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("ContactDetails", contact);

        //build a list of "Action Plans" mapped to "Accounts"
        ActionPlanList actionPlanList = new ActionPlanList();
        actionPlanList.setIncludeOnlyApproved(Constants.TRUE);
        actionPlanList.setEnabled(Constants.TRUE);
        actionPlanList.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
        actionPlanList.setSiteId(contact.getSiteId());
        actionPlanList.buildList(db);
        HtmlSelect actionPlanSelect = actionPlanList.getHtmlSelectObj();
        actionPlanSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes", "-- None --"));
        context.getRequest().setAttribute("actionPlanSelect", actionPlanSelect);

        ActionPlanWork actionPlanWork =
            ActionPlanWork.getActionPlanWork(db, ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS), contact.getId());
        context.getRequest().setAttribute("actionPlanWork", actionPlanWork);
      } else {
        HashMap errors = new HashMap();
        errors.put(
            "actionError", systemStatus.getLabel(
            "object.validation.leadsSearchActionError"));
        processErrors(context, errors);
        return executeCommandSearchForm(context);
      }
      //build the import details of the import for this contact
      if (contact.getImportId() != -1) {
        ContactImport thisImport = new ContactImport(
            db, contact.getImportId());
        context.getRequest().setAttribute("ImportDetails", thisImport);
      }

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      context.getRequest().setAttribute("SiteIdList", siteList);

      boolean hasGcPermission = hasPermission(
          context, "contacts-external_contacts-view");
      boolean hasAcPermission = hasPermission(
          context, "accounts-accounts-contacts-view");
      boolean hasAccountPermission = hasPermission(
          context, "accounts-accounts-view");
      if (hasGcPermission || hasAcPermission) {
        context.getRequest().setAttribute(
            "hasDuplicateLastName", "" + ContactUtils.hasDuplicateLastName(
            db, contact.getNameLast(), user.getIdRange(), hasGcPermission, hasAcPermission));
        if (contact.getEmailAddressList().size() > 0) {
          context.getRequest().setAttribute(
              "hasDuplicateEmailAddress", ContactUtils.hasDuplicateEmailAddresses(
              db, contact.getEmailAddressList().getConcatenatedList(), user.getIdRange(), hasGcPermission, hasAcPermission));
        }
      }
      if ((hasGcPermission || hasAccountPermission) && contact.getCompany() != null && !"".equals(
          contact.getCompany().trim())) {
        context.getRequest().setAttribute(
            "hasDuplicateCompany", ContactUtils.hasDuplicateCompany(
            db, contact.getCompany(), user.getIdRange(), hasGcPermission, hasAccountPermission));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "DetailsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAssignLead(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-edit"))) {
      return ("PermissionError");
    }
    int readStatus = -1;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String nextValue = (String) context.getRequest().getAttribute("nextValue");
    if (nextValue == null || "".equals(nextValue)) {
      nextValue = (String) context.getRequest().getParameter("nextValue");
    }
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String readStatusString = (String) context.getRequest().getAttribute(
        "readStatus");
    String contactId = (String) context.getRequest().getAttribute("contactId");
    if (contactId == null || "".equals(contactId)) {
      contactId = (String) context.getRequest().getParameter("contactId");
    }
    ContactList contacts = new ContactList();
    Contact contact = null;
    Connection db = null;
    try {
      db = getConnection(context);

      if (nextValue != null && !"".equals(nextValue.trim())) {
        contactId = "" + LeadUtils.getNextLead(
            db, Integer.parseInt(contactId), contacts, this.getUserSiteId(context), true);
      }

      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      context.getRequest().setAttribute("ratingList", ratings);

      if (readStatusString == null || "".equals(readStatusString.trim())) {
        if (hasPermission(context, "sales-leads-edit")) {
          readStatus = LeadUtils.setReadStatus(
              db, Integer.parseInt(contactId), this.getUserId(context));
        }
      } else {
        try {
          readStatus = Integer.parseInt(readStatusString);
        } catch (Exception error1) {
        }
      }
      context.getRequest().setAttribute("readStatus", "" + readStatus);

      if (contactId != null && !"".equals(contactId) && Integer.parseInt(
          contactId) != -1) {
        contact = new Contact(db, contactId);
        context.getRequest().setAttribute("contactDetails", contact);

        //build a list of "Action Plans" mapped to "Accounts"
        ActionPlanList actionPlanList = new ActionPlanList();
        actionPlanList.setIncludeOnlyApproved(Constants.TRUE);
        actionPlanList.setEnabled(Constants.TRUE);
        actionPlanList.setSiteId(contact.getSiteId());
        actionPlanList.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
        actionPlanList.buildList(db);
        HtmlSelect actionPlanSelect = actionPlanList.getHtmlSelectObj();
        actionPlanSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes", "-- None --"));
        context.getRequest().setAttribute("actionPlanSelect", actionPlanSelect);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "AssignLeadOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCheckAssignStatus(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-edit"))) {
      return ("PermissionError");
    }
    String from = (String) context.getRequest().getAttribute("from");
    if (from == null || "".equals(from)) {
      from = (String) context.getRequest().getParameter("from");
    }
    String nextValue = (String) context.getRequest().getAttribute("nextValue");
    if (nextValue == null || "".equals(nextValue)) {
      nextValue = (String) context.getRequest().getParameter("nextValue");
    }
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String contactId = (String) context.getRequest().getParameter("contactId");
    context.getRequest().setAttribute("contactId", contactId);
    String next = (String) context.getRequest().getParameter("next");
    String owner = (String) context.getRequest().getParameter("owner");
    context.getRequest().setAttribute("next", next);
    if (next == null || contactId == null) {
      return ("PermissionError");
    }
    boolean assignStatus = false;
    boolean skippedStatus = false;
    int userId = this.getUserId(context);
    Connection db = null;
    try {
      db = getConnection(context);
      if (!"skip".equals(next)) {
        if (owner != null && !"".equals(owner)) {
          assignStatus = LeadUtils.tryToAssignLead(
              db, Integer.parseInt(contactId), userId, Integer.parseInt(owner));
        } else {
          assignStatus = LeadUtils.tryToAssignLead(
              db, Integer.parseInt(contactId), userId);
        }
        if (assignStatus) {
          context.getRequest().setAttribute("assignStatus", "assigned");
        } else {
          context.getRequest().setAttribute("assignStatus", "notAssigned");
        }
      } else {
        skippedStatus = LeadUtils.skipLead(
            db, Integer.parseInt(contactId), userId);
        context.getRequest().setAttribute("assignStatus", "skipped");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "CheckAssignStatusOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandWorkLead(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Add Contact", "Add Contact to Account");
    String contactId = (String) context.getRequest().getParameter("id");
    String rating = (String) context.getRequest().getParameter("rating");
    String comments = (String) context.getRequest().getParameter("comments");

    try {
      db = getConnection(context);

      Contact contact = new Contact(db, contactId);
      Contact oldContact = new Contact(db, contactId);
      if (contact.getOwner() == -1) {
        contact.setOwner(this.getUserId(context));
      }
      if (contact.getLeadStatus() != Contact.LEAD_ASSIGNED) {
        contact.setLeadStatus(Contact.LEAD_ASSIGNED);
      }
      contact.setIsLead(false);
      contact.setConversionDate(
          new Timestamp(Calendar.getInstance().getTimeInMillis()));
      if (rating != null && !"".equals(rating)) {
        contact.setRating(Integer.parseInt(rating));
      }
      contact.setComments(comments);
      contact.update(db, context);
      processUpdateHook(context, oldContact, contact);
      int size = LeadUtils.cleanUpContact(
          db, contact.getId(), this.getUserId(context));
      this.sendEmail(context,db, contact, contact.getOwner(), (String) null);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "WorkLeadOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandWorkAccount(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Add Account", "Add Account");
    String contactId = (String) context.getRequest().getParameter("id");
    String rating = (String) context.getRequest().getParameter("rating");
    String comments = (String) context.getRequest().getParameter("comments");
    int orgId = -1;
    User assigned = null;
    User manager = null;
    ActionPlanWork actionPlanWork = new ActionPlanWork();
    Organization thisOrg = null;
    try {
      db = getConnection(context);

      //insert an account if company name exists
      boolean status = false;
      int resultCount = -1;
      Contact contact = new Contact(db, contactId);
      Contact oldContact = new Contact(db, contactId);
      if (contact.getOwner() == -1) {
        contact.setOwner(this.getUserId(context));
      }
      if (contact.getLeadStatus() != Contact.LEAD_ASSIGNED) {
        contact.setLeadStatus(Contact.LEAD_ASSIGNED);
      }

      if (contact.getCompany() != null && !"".equals(contact.getCompany().trim())) {
        orgId = Organization.lookupAccount(db, contact.getCompany(), -1, contact.getSiteId());
      } else {
        orgId = Organization.lookupAccount(db, contact.getNameFirst(), contact.getNameMiddle(), contact.getNameLast(), -1, contact.getSiteId());
      }
       {
        if (orgId > 0) {
          thisOrg = new Organization(db, orgId);
        }
        if (orgId < 0) {
          thisOrg = new Organization();
          thisOrg.setEnteredBy(this.getUserId(context));
          if (contact.getCompany() == null || "".equals(contact.getCompany().trim())) {
            contact.setPrimaryContact(true);
            thisOrg.setNameFirst(contact.getNameFirst());
            thisOrg.setNameLast(contact.getNameLast());
            thisOrg.setNameMiddle(contact.getNameMiddle());
            thisOrg.setName(thisOrg.getNameLastFirstMiddle());
            thisOrg.setInsertPrimaryContact(false);
          } else {
            thisOrg.setName(contact.getCompany());
          }
        }
        thisOrg.setSiteId(contact.getSiteId());
        thisOrg.setModifiedBy(this.getUserId(context));
        if ((thisOrg.getId() > 0 && thisOrg.getAccountNumber() != null &&
            contact.getAccountNumber() != null &&
            !contact.getAccountNumber().equals(thisOrg.getAccountNumber()))
             || thisOrg.getOrgId() == -1) {
          thisOrg.setAccountNumber(contact.getAccountNumber());
        }
        if ((thisOrg.getId() > 0 && contact.getOwner() != thisOrg.getOwner())
             || thisOrg.getOrgId() == -1) {
          thisOrg.setOwner(contact.getOwner());
        }
        if ((thisOrg.getId() > 0 && contact.getPotential() != thisOrg.getPotential())
             || thisOrg.getOrgId() == -1) {
          thisOrg.setPotential(contact.getPotential());
        }
        if ((thisOrg.getId() > 0 && contact.getIndustryTempCode() != thisOrg.getIndustry())
             || thisOrg.getOrgId() == -1) {
          thisOrg.setIndustry(contact.getIndustryTempCode());
        }
        if ((thisOrg.getId() > 0 && contact.getSource() != thisOrg.getSource())
             || thisOrg.getOrgId() == -1) {
          thisOrg.setSource(contact.getSource());
        }
        if ((thisOrg.getId() > 0 && contact.getRating() != thisOrg.getRating())
             || thisOrg.getOrgId() == -1) {
          thisOrg.setRating(contact.getRating());
        }

        //Copy postal address from contact to organization
        ContactAddressList contactAddressList = new ContactAddressList();
        contactAddressList.setContactId(contact.getId());
        contactAddressList.buildList(db);
        Iterator addressIterator = contactAddressList.iterator();
        while (addressIterator.hasNext()) {
          //DEVELOPER NOTE: probably needs to be in  the OrganizationEmailAddress Constructor
          ContactAddress contactAddress = (ContactAddress) addressIterator.next();
          OrganizationAddress organizationAddress = new OrganizationAddress();
          organizationAddress.setStreetAddressLine1(contactAddress.getStreetAddressLine1());
          organizationAddress.setStreetAddressLine2(contactAddress.getStreetAddressLine2());
          organizationAddress.setStreetAddressLine3(contactAddress.getStreetAddressLine3());
          organizationAddress.setCity(contactAddress.getCity());
          organizationAddress.setState(contactAddress.getState());
          organizationAddress.setZip(contactAddress.getZip());
          organizationAddress.setCountry(contactAddress.getCountry());

          LookupList contactAddressTypeList = new LookupList(db, "lookup_contactaddress_types");
          LookupList orgAddressTypeList = null;
          String contactAddressType = contactAddressTypeList.getValueFromId(contactAddress.getType());
          int orgAddressType = -1;
          if (contactAddress.getType() != -1) {
            if (contactAddressType.indexOf("Business") > -1) {
              orgAddressTypeList = new LookupList(db, "lookup_orgaddress_types");
              orgAddressType = orgAddressTypeList.getIdFromValue("Primary");
            } else {
              orgAddressTypeList = new LookupList(db, "lookup_orgaddress_types");
              orgAddressType = orgAddressTypeList.getIdFromValue("Auxiliary");
            }
            organizationAddress.setType(orgAddressType);
          }
          organizationAddress.setPrimaryAddress(contactAddress.getPrimaryAddress());
          thisOrg.getAddressList().add(organizationAddress);
        }
        //Copy email address from contact to organization
        ContactEmailAddressList contactEmailAddressList = new ContactEmailAddressList();
        contactEmailAddressList.setContactId(contact.getId());
        contactEmailAddressList.buildList(db);
        Iterator emailAddressIterator = contactEmailAddressList.iterator();
        while (emailAddressIterator.hasNext()) {
          //DEVELOPER NOTE: probably needs to be in  the OrganizationEmailAddress Constructor
          ContactEmailAddress contactEmailAddress = (ContactEmailAddress) emailAddressIterator.next();
          OrganizationEmailAddress organizationEmailAddress = new OrganizationEmailAddress();
          organizationEmailAddress.setEmail(contactEmailAddress.getEmail());

          LookupList contactEmailAddressTypeList = new LookupList(db, "lookup_contactemail_types");
          LookupList orgEmailAddressTypeList = null;
          String contactEmailAddressType = contactEmailAddressTypeList.getValueFromId(contactEmailAddress.getType());
          int orgEmailAddressType = -1;
          if (contactEmailAddressType.indexOf("Business") > -1) {
            orgEmailAddressTypeList = new LookupList(db, "lookup_orgemail_types");
            orgEmailAddressType = orgEmailAddressTypeList.getIdFromValue("Primary");
          } else {
            orgEmailAddressTypeList = new LookupList(db, "lookup_orgemail_types");
            orgEmailAddressType = orgEmailAddressTypeList.getIdFromValue("Auxiliary");
          }

          organizationEmailAddress.setType(orgEmailAddressType);
          organizationEmailAddress.setPrimaryEmail(contactEmailAddress.getPrimaryEmail());
          thisOrg.getEmailAddressList().add(organizationEmailAddress);
        }
        //Copy phone numbers from contact to organization
        ContactPhoneNumberList contactPhoneNumberList = new ContactPhoneNumberList();
        contactPhoneNumberList.setContactId(contact.getId());
        contactPhoneNumberList.buildList(db);
        Iterator phoneNumberIterator = contactPhoneNumberList.iterator();
        while (phoneNumberIterator.hasNext()) {
          //DEVELOPER NOTE: probably needs to be in  the OrganizationPhoneNumber Constructor
          ContactPhoneNumber contactPhoneNumber = (ContactPhoneNumber) phoneNumberIterator.next();
          OrganizationPhoneNumber organizationPhoneNumber = new OrganizationPhoneNumber();
          organizationPhoneNumber.setNumber(contactPhoneNumber.getNumber());
          organizationPhoneNumber.setExtension(contactPhoneNumber.getExtension());

          LookupList contactPhoneNumberTypeList = new LookupList(db, "lookup_contactphone_types");
          LookupList orgPhoneNumberTypeList = null;
          String contactPhoneNumberType = contactPhoneNumberTypeList.getValueFromId(contactPhoneNumber.getType());
          int orgPhoneNumberType = -1;
          if (contactPhoneNumberType.indexOf("Fax") > -1) {
            orgPhoneNumberTypeList = new LookupList(db, "lookup_orgphone_types");
            orgPhoneNumberType = orgPhoneNumberTypeList.getIdFromValue("Fax");
          } else {
            orgPhoneNumberTypeList = new LookupList(db, "lookup_orgphone_types");
            orgPhoneNumberType = orgPhoneNumberTypeList.getIdFromValue("Main");
          }

          organizationPhoneNumber.setType(orgPhoneNumberType);
          organizationPhoneNumber.setPrimaryNumber(contactPhoneNumber.getPrimaryNumber());
          thisOrg.getPhoneNumberList().add(organizationPhoneNumber);
        }

        if (thisOrg.getOrgId() > 0) {
          status = (thisOrg.update(db) == 1);
        } else {
          status = thisOrg.insert(db);
        }
        if (status) {
          this.addRecentItem(context, thisOrg);
          context.getRequest().setAttribute("orgId", String.valueOf(thisOrg.getOrgId()));
        }
        contact.setOrgId(thisOrg.getOrgId());
        contact.setOrgName(thisOrg.getName());
      }
      if (status) {
        contact.setIsLead(false);
        contact.setConversionDate(
            new Timestamp(Calendar.getInstance().getTimeInMillis()));
        if (rating != null && !"".equals(rating)) {
          contact.setRating(Integer.parseInt(rating));
        }
        contact.setComments(comments);
        resultCount = contact.update(db, context);
        if ((contact.getNameLast() == null) || "".equals(contact.getNameLast())) {
          contact.disable(db);
        }
        processUpdateHook(context, oldContact, contact);
        int size = LeadUtils.cleanUpContact(
            db, contact.getId(), this.getUserId(context));
      }
      //Record a corressponding Action Plan Work Item
      if (status && resultCount > 0) {
        String actionPlanId = context.getRequest().getParameter("actionPlan");
        String managerId = context.getRequest().getParameter("manager");
        String owner = (String) context.getRequest().getParameter("owner");
        if (actionPlanId != null && !"".equals(actionPlanId.trim()) && !"-1".equals(actionPlanId)) {
          ActionPlan actionPlan = new ActionPlan();
          actionPlan.setBuildPhases(true);
          actionPlan.setBuildSteps(true);
          actionPlan.queryRecord(db, Integer.parseInt(actionPlanId));

          actionPlanWork.setActionPlanId(actionPlanId);
          actionPlanWork.setManagerId(managerId);
          actionPlanWork.setAssignedTo(owner);
          actionPlanWork.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
          actionPlanWork.setLinkItemId(thisOrg.getOrgId());
          actionPlanWork.setEnteredBy(this.getUserId(context));
          actionPlanWork.setModifiedBy(this.getUserId(context));
          actionPlanWork.insert(db, actionPlan);
          
          assigned = this.getUser(context, actionPlanWork.getAssignedTo());
          assigned.setBuildContact(true);
          assigned.setBuildContactDetails(true);
          assigned.buildResources(db);
          manager = this.getUser(context, actionPlanWork.getManagerId());
          manager.setBuildContact(true);
          manager.setBuildContactDetails(true);
          manager.buildResources(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Process Emails
    if (actionPlanWork.getId() > -1) {
      try {
        String templateFile = getDbNamePath(context) + "templates_" + getUserLanguage(context) + ".xml";
        if (!FileUtils.fileExists(templateFile)) {
          templateFile = getDbNamePath(context) + "templates_en_US.xml";
        }
        //Send an email to the Action Plan "owner" & Action Plan "manager"
        actionPlanWork.sendEmail(
            context,
            assigned.getContact(),
            manager.getContact(),
            thisOrg.getName(),
            templateFile);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      }
    }
    addModuleBean(context, "Leads", "Leads");
    if ("true".equals(context.getRequest().getParameter("popup"))) {
      context.getRequest().setAttribute(
          "refreshUrl", "Sales.do?command=List" + RequestUtils.addLinkParams(
          context.getRequest(), "actionId|listForm|from"));
      return "CloseAndReloadOK";
    }
    return "WorkAccountOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "sales-leads-edit")) {
      return ("PermissionError");
    }
    int resultCount = 0;
    boolean isValid = false;
    String from = (String) context.getRequest().getAttribute("from");
    if (from == null || "".equals(from)) {
      from = (String) context.getRequest().getParameter("from");
    }
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String nextValue = (String) context.getRequest().getAttribute("nextValue");
    if (nextValue == null || "".equals(nextValue)) {
      nextValue = (String) context.getRequest().getParameter("nextValue");
    }
    context.getRequest().setAttribute(
        "nextValue", nextValue != null ? nextValue : "");
    String contactId = context.getRequest().getParameter("contactId");
    String owner = (String) context.getRequest().getParameter("owner");
    String leadStatus = (String) context.getRequest().getParameter(
        "leadStatus");
    String comments = (String) context.getRequest().getParameter("comments");
    String rating = (String) context.getRequest().getParameter("rating");
    Contact thisContact = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      Contact oldContact = new Contact(db, contactId);
      thisContact.setModifiedBy(getUserId(context));
      thisContact.setIsLead(true);
      if (owner != null && !"".equals(owner)) {
        thisContact.setOwner(Integer.parseInt(owner));
      }
      if (leadStatus != null && !"".equals(leadStatus)) {
        thisContact.setLeadStatus(Integer.parseInt(leadStatus));
      }
      if (comments != null && !"".equals(comments)) {
        thisContact.setComments(comments);
      }
      if (rating != null && !"".equals(rating)) {
        thisContact.setRating(Integer.parseInt(rating));
      }
      isValid = validateObject(context, db, thisContact);
      if (isValid) {
        resultCount = thisContact.update(db, context);
      }
      if (isValid && resultCount == 1) {
        if (oldContact.getOwner() != thisContact.getOwner()) {
          this.sendEmail(context, db, thisContact, thisContact.getOwner(), "leads.assigned");
        }
        processUpdateHook(context, oldContact, thisContact);
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        context.getRequest().setAttribute("contactId", "" + thisContact.getId());

        String next = (String) context.getRequest().getParameter("next");
        if ("assignaccount".equals(next)) {
          // TODO: The following should not be executed in this Action because now
          // this request is using an additional database connection without closing the first!
          // it could be moved down below to fix this
          String retVal = executeCommandWorkAccount(context);
          if (from != null && !"list".equals(from)) {
            context.getRequest().setAttribute("refreshUrl", "Sales.do?command=Dashboard" + RequestUtils.addLinkParams(context.getRequest(), "actionId"));
          } else {
            context.getRequest().setAttribute("refreshUrl", "Sales.do?command=List" + RequestUtils.addLinkParams(context.getRequest(), "actionId|listForm|from"));
          }
          if ("CloseAndReloadOK".equals(retVal)) {
            return "CloseAndReloadOK";
          }
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Update Lead");
    // decide what happend with the processing
    if (from != null && "dashboard".equals(from)) {
      return executeCommandDashboard(context);
    } else {
      return executeCommandList(context);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-delete"))) {
      return ("PermissionError");
    }
    String contactId = (String) context.getRequest().getParameter("contactId");
    Contact contact = null;
    Connection db = null;
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = getConnection(context);
      contact = new Contact(db, contactId);
      if (!isRecordAccessPermitted(context, contact)) {
        return ("PermissionError");
      }

      DependencyList dependencies = contact.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='Sales.do?command=Delete&contactId=" + contactId + "&listForm=" + (listForm != null ? listForm : "") + RequestUtils.addLinkParams(
            context.getRequest(), "from|nextValue") + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header2"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='Sales.do?command=Trash&contactId=" + contactId + "&listForm=" + (listForm != null ? listForm : "") + RequestUtils.addLinkParams(
            context.getRequest(), "popup|from|nextValue") + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    context.getSession().setAttribute("Dialog", htmlDialog);
    return "ConfirmDeleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-delete"))) {
      return ("PermissionError");
    }
    String contactId = (String) context.getRequest().getParameter("contactId");
    String listForm = (String) context.getRequest().getParameter("listForm");
    Contact contact = null;
    Connection db = null;
    try {
      db = getConnection(context);
      contact = new Contact(db, contactId);
      int size = LeadUtils.cleanUpContact(
          db, contact.getId(), this.getUserId(context));
      if (!hasAuthority(context, contact.getOwner())) {
        return "PermissionError";
      }
      contact.updateStatus(db, context, true, this.getUserId(context));
      String returnType = (String) context.getRequest().getParameter("from");
      if (returnType != null && !"list".equals(returnType)) {
        context.getRequest().setAttribute(
            "refreshUrl", "Sales.do?command=Dashboard" + RequestUtils.addLinkParams(
            context.getRequest(), "actionId"));
      } else {
        context.getRequest().setAttribute(
            "refreshUrl", "Sales.do?command=List" + RequestUtils.addLinkParams(
            context.getRequest(), "actionId|listForm|from"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-delete"))) {
      return ("PermissionError");
    }
    String contactId = (String) context.getRequest().getParameter("contactId");
    String listForm = (String) context.getRequest().getParameter("listForm");
    Contact contact = null;
    Connection db = null;
    try {
      db = getConnection(context);
      contact = new Contact(db, contactId);
      int size = LeadUtils.cleanUpContact(
          db, contact.getId(), this.getUserId(context));
      if (!hasAuthority(context, contact.getOwner())) {
        return "PermissionError";
      }
      contact.delete(db, context, getDbNamePath(context));
      String returnType = (String) context.getRequest().getParameter("from");
      if (returnType != null && !"list".equals(returnType)) {
        context.getRequest().setAttribute(
            "refreshUrl", "Sales.do?command=Dashboard" + RequestUtils.addLinkParams(
            context.getRequest(), "actionId"));
      } else {
        context.getRequest().setAttribute(
            "refreshUrl", "Sales.do?command=List" + RequestUtils.addLinkParams(
            context.getRequest(), "actionId|listForm|from"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Leads");
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandContactList(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-view")) {
      return ("PermissionError");
    }
    PagedListInfo searchContactsInfo = this.getPagedListInfo(
        context, "SalesContactListInfo");
    searchContactsInfo.setLink("Sales.do?command=ContactList");
    String source = (String) context.getRequest().getParameter("source");
    addModuleBean(context, "Sales Contacts", "Search Results");
    ContactList contactList = new ContactList();
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;
    try {
      db = this.getConnection(context);
      //set the searchcriteria
      searchContactsInfo.setSearchCriteria(contactList, context);
      //make sure user has access to view account contacts
      if (!(hasPermission(context, "accounts-accounts-contacts-view"))) {
        contactList.setExcludeAccountContacts(true);
      }
      //set properties on contact list
      contactList.setPagedListInfo(searchContactsInfo);
      contactList.setBuildDetails(true);
      contactList.setBuildTypes(false);
      contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
      contactList.setControlledHierarchyOnly(false, this.getUserRange(context));
      contactList.setAllContacts(true);
      contactList.setPersonalId(this.getUserId(context));
      contactList.setSiteId(user.getSiteId());
      contactList.setExclusiveToSite(true);
      if (user.getSiteId() == -1) {
        contactList.setIncludeAllSites(true);
      }
      contactList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactList", contactList);
    return ("ContactListOK");
  }


  /**
   *  Description of the Method
   *
   *@param  pertainsTo    Description of the Parameter
   *@param  leadsList     Description of the Parameter
   *@param  usersToGraph  Description of the Parameter
   *@return               Description of the Return Value
   */
  private UserList prepareLines(User pertainsTo, ContactList leadsList, UserList usersToGraph) {
    java.util.Date myDate = null;
    Calendar readDate = Calendar.getInstance();
    Calendar readDateAdjusted = Calendar.getInstance();
    Double lccrAddTerm = new Double(0.0);
    String valKey = "";
    Calendar rightNow = Calendar.getInstance();
    rightNow.set(Calendar.DAY_OF_MONTH, 1);
    Calendar rightNowAdjusted = Calendar.getInstance();
    rightNowAdjusted.set(Calendar.DAY_OF_MONTH, 1);
    rightNowAdjusted.add(Calendar.DATE, -1);
    //two months ago
    Calendar twoMonthsAgo = Calendar.getInstance();
    twoMonthsAgo.set(Calendar.DAY_OF_MONTH, 1);
    twoMonthsAgo.add(Calendar.MONTH, -3);
    if (pertainsTo.getIsValidLead() == false) {
      pertainsTo.doLeadsLock();
      if (pertainsTo.getIsValidLead() == false) {
        try {
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "Sales-> (RE)BUILDING LEADS DATA FOR " + pertainsTo.getId());
          }
          pertainsTo.setLccr(
              new GraphSummaryList(8, GraphSummaryList.WEEK_RANGE, false));
          Iterator leadsIterator = leadsList.iterator();
          while (leadsIterator.hasNext()) {
            Contact tempLead = (Contact) leadsIterator.next();
            if ((tempLead.getOwner() == pertainsTo.getId())) {
              myDate = new java.util.Date(
                  tempLead.getConversionDate().getTime());
              readDate.setTime(myDate);
              readDateAdjusted.setTime(myDate);
              readDate.add(
                  Calendar.DAY_OF_WEEK, -(readDate.get(Calendar.DAY_OF_WEEK) - readDate.getFirstDayOfWeek()));
              //Fix this readDate as it should show a lot more intervals of readDate
              valKey = String.valueOf(readDate.get(java.util.Calendar.YEAR)) + String.valueOf(
                  readDate.get(java.util.Calendar.MONTH)) + String.valueOf(
                  readDate.get(java.util.Calendar.DATE));
              //get the individual graph values
              lccrAddTerm = new Double(
                  (tempLead.getConversionDate() != null) ? 1.0 : 0.0);
              //case: conversion date within 0-2m range
//if (((rightNow.after(readDate) || rightNowAdjusted.after(readDate)) && twoMonthsAgo.before(readDate)) || rightNow.equals(readDate) || twoMonthsAgo.equals(readDate)) {
              pertainsTo.setGraphValuesLeads(valKey, lccrAddTerm);
//}
            }
          }
          pertainsTo.setIsValidLead(true, true);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println(
              "Sales-> Unwanted exception occurred: " + e.toString());
        }
      }
      pertainsTo.doLeadsUnlock();
    }
    usersToGraph.add(pertainsTo);
    if (leadsList.size() == 0) {
      return new UserList();
    } else {
      return usersToGraph;
    }
  }


  /**
   *  This method takes a userlist, then for each user the specified graph data
   *  is pulled out by date range and put in an XYSeries, and then the XYSeries
   *  is added to an XYSeriesCollection.
   *
   *@param  linesToDraw  Description of Parameter
   *@param  whichGraph   Description of Parameter
   *@return              Description of the Returned Value
   */
  private XYSeriesCollection createCategoryDataset(UserList linesToDraw, String whichGraph) {
    XYSeriesCollection xyDataset = new XYSeriesCollection();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Sales-> Lines to draw: " + linesToDraw.size());
    }
    if (linesToDraw.size() == 0) {
      return xyDataset;
    }
    Iterator users = linesToDraw.iterator();
    while (users.hasNext()) {
      User thisUser = (User) users.next();
      XYSeries dataSeries = new XYSeries("");
      String[] valKeys = thisUser.getLccr().getWeekRange(8);
      Calendar iteratorDate = Calendar.getInstance();
      for (int count = 0; count < 8; count++) {
        java.util.Date dateValue = createDate(
            iteratorDate.get(Calendar.YEAR), iteratorDate.get(Calendar.MONTH), iteratorDate.get(
            Calendar.DATE));
        Double itemValue = new Double(0);
        if (whichGraph.equals("lccr")) {
          itemValue = thisUser.getLccr().getValue(valKeys[count]);
        }
        iteratorDate.add(Calendar.WEEK_OF_YEAR, -1);
        dataSeries.add(dateValue.getTime(), itemValue);
      }
      xyDataset.addSeries(dataSeries);
    }
    return xyDataset;
  }


  /**
   *  Create the x and y data for a chart for a single user, on top of another
   *  list of user's data.. Each year, month and date combo for the past 2
   *  months from the beginning of this month are stored under a new single User
   *  object (hmph) for drawing a line on the graph to show cumulative data. @
   *  param primaryNode Description of Parameter @ param currentLines
   *  Description of Parameter @ return Description of the Returned Value
   *
   *@param  primaryNode   Description of the Parameter
   *@param  currentLines  Description of the Parameter
   *@return               Description of the Return Value
   */
  private UserList calculateLine(User primaryNode, UserList currentLines) {
    if (currentLines.size() == 0) {
      currentLines.add(primaryNode);
      return currentLines;
    }
    User thisLine = new User();
    thisLine.getLccr().setIsFutureDateRange(false);
    String[] valKeys = thisLine.getLccr().getWeekRange(8);
    Iterator x = currentLines.iterator();
    while (x.hasNext()) {
      User addToMe = (User) x.next();
      for (int count = 0; count < 8; count++) {
        thisLine.getLccr().setValue(
            valKeys[count], new Double(
            primaryNode.getLccr().getValue(valKeys[count]).doubleValue() + (addToMe.getLccr().getValue(
            valKeys[count])).doubleValue()));
      }
    }
    currentLines.add(thisLine);
    return currentLines;
  }


  /**
   *  Create the x and y data for a chart for a list of users. Each year, month
   *  and year combo for the past 2 months from the beginning of this month are
   *  added together for each user and stored under a new single User object
   *  (hmph) for drawing a line on the graph. @ param toRollUp Description of
   *  Parameter @ param currentLines Description of Parameter @ return
   *  Description of the Returned Value
   *
   *@param  toRollUp      Description of the Parameter
   *@param  currentLines  Description of the Parameter
   *@return               Description of the Return Value
   */
  private UserList calculateLine(UserList toRollUp, UserList currentLines) {
    if (toRollUp.size() == 0) {
      return new UserList();
    }
    User thisLine = new User();
    thisLine.getLccr().setIsFutureDateRange(false);
    String[] valKeys = thisLine.getLccr().getWeekRange(8);
    Iterator x = toRollUp.iterator();
    while (x.hasNext()) {
      User thisUser = (User) x.next();
      for (int count = 0; count < 8; count++) {
        thisLine.getLccr().setValue(
            valKeys[count], thisUser.getLccr().getValue(valKeys[count]));
      }
    }
    currentLines.add(thisLine);
    return currentLines;
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
   *@param  context         Description of the Parameter
   *@param  currentContact  Description of the Parameter
   *@param  owner           Description of the Parameter
   *@param  type            Description of the Parameter
   *@return                 Description of the Return Value
   *@exception  Exception   Description of the Exception
   */
  public boolean sendEmail(ActionContext context, Connection db, Contact currentContact, int owner, String type) throws Exception {
    User user = this.getUser(context, owner);
    user.setBuildContact(true);
    user.setBuildContactDetails(true);
    user.buildResources(db);
    if (type != null && !"".equals(type) && user.getId() == this.getUserId(context)) {
      return true;
    }
    String templateFile = getDbNamePath(context) + "templates_" + getUserLanguage(
        context) + ".xml";
    if (!FileUtils.fileExists(templateFile)) {
      templateFile = getDbNamePath(context) + "templates_en_US.xml";
    }
    SalesEmail salesEmail = null;
    if (type != null && !"".equals(type)) {
      salesEmail = new SalesEmail(templateFile, currentContact, context, type);
    } else {
      salesEmail = new SalesEmail(templateFile, currentContact, context);
    }
    // Prepare the email
    SMTPMessage mail = new SMTPMessage();
    mail.setHost(getPref(context, "MAILSERVER"));
    mail.setFrom(getPref(context, "EMAILADDRESS"));
    mail.addReplyTo(user.getContact().getPrimaryEmailAddress(), user.getContact().getNameFirstLast());
    mail.setType("text/html");
    mail.setSubject(salesEmail.getSubject());
    mail.setBody(salesEmail.getBody());
    if (user.getContact().getPrimaryEmailAddress() != null && !"".equals(user.getContact().getPrimaryEmailAddress())) {
      mail.addTo(user.getContact().getPrimaryEmailAddress());
      mail.send();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReports(ActionContext context) {
    if (!(hasPermission(context, "sales-reports-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_SALES_REPORTS);
    files.setLinkItemId(-1);

    PagedListInfo rptListInfo = this.getPagedListInfo(
        context, "salesRptListInfo");
    rptListInfo.setLink("Sales.do?command=Reports");
    files.setPagedListInfo(rptListInfo);

    if ("all".equals(rptListInfo.getListView())) {
      files.setOwnerIdRange(this.getUserRange(context));
    } else {
      files.setOwner(this.getUserId(context));
    }
    try {
      db = this.getConnection(context);
      files.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "ViewReports");
    context.getRequest().setAttribute("fileList", files);
    return ("ReportsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandGenerateForm(ActionContext context) {
    if (!hasPermission(context, "sales-reports-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);

      LookupList industryList = systemStatus.getLookupList(db, "lookup_industry");
      industryList.addItem(-1, systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("industryList", industryList);

      LookupList sources = systemStatus.getLookupList(db, "lookup_contact_source");
      sources.addItem(-1, systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("sourceList", sources);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandExportReport(ActionContext context) {
    if (!hasPermission(context, "sales-reports-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    String subject = context.getRequest().getParameter("subject");
    String leadStatus = context.getRequest().getParameter("leadStatus");
    String sourceCode = context.getRequest().getParameter("sourceCode");
    String industryCode = context.getRequest().getParameter("industryCode");

    //setup file stuff
    String filePath = this.getPath(context, "sales-reports");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    filePath += datePathToUse1 + fs + datePathToUse2 + fs;
    //Prepare the report
    ContactReport leadReport = new ContactReport();
    leadReport.setType(Contact.LEAD_TYPE);

    leadReport.setCriteria(
        context.getRequest().
        getParameterValues("selectedList"));

    leadReport.setFilePath(filePath);
    leadReport.setSubject(subject);
    leadReport.setUserId(this.getUserId(context));
    
    //build only leads
    leadReport.setLeadStatus(leadStatus);
    if (Integer.parseInt(leadStatus) == Contact.LEAD_WORKED) {
      leadReport.setLeadsOnly(Constants.FALSE);
      leadReport.setLeadStatus(-1);
      leadReport.setHasConversionDate(Constants.TRUE);
    } else if (Integer.parseInt(leadStatus) == Contact.LEAD_UNREAD) {
      leadReport.setLeadsOnly(Constants.TRUE);
    } else if (Integer.parseInt(leadStatus) == -1) {
      leadReport.setLeadStatus(-1);
      leadReport.setLeadsOnly(Constants.UNDEFINED);
    }
    leadReport.setLeadStatusExists(Constants.TRUE);

    //Prepare the pagedList to provide sorting
    PagedListInfo thisInfo = new PagedListInfo();
    thisInfo.setColumnToSortBy(context.getRequest().getParameter("sort"));
    thisInfo.setItemsPerPage(0);
    leadReport.setPagedListInfo(thisInfo);
    leadReport.setSiteId(this.getUserSiteId(context));
    leadReport.setExclusiveToSite(true);
    if (this.getUserSiteId(context) == -1) {
      leadReport.setIncludeAllSites(true);
    }
    //Check the form selections and criteria
    if (sourceCode != null) {
      leadReport.setSource(sourceCode);
    }
    if (industryCode != null) {
      leadReport.setIndustry(industryCode);
    }
    leadReport.setControlledHierarchyOnly(true, this.getUserRange(context));
    
    try {
      db = this.getConnection(context);
      //builds list also
      leadReport.buildReportFull(db, this.getUserTable(context));
      leadReport.setEnteredBy(getUserId(context));
      leadReport.setModifiedBy(getUserId(context));
      //TODO: set owner, enteredby, and modified names
      leadReport.saveAndInsert(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandReports(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShowReportHtml(ActionContext context) {
    if (!hasPermission(context, "sales-reports-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    FileItem thisItem = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId));
      String filePath = this.getPath(context, "sales-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!hasAuthority(context, thisItem.getEnteredBy())) {
      return ("PermissionError");
    }
    return ("ReportHtmlOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDownloadCSVReport(ActionContext context) {
    if (!(hasPermission(context, "sales-reports-view"))) {
      return ("PermissionError");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_SALES_REPORTS);
      if (!hasAuthority(context, thisItem.getEnteredBy())) {
        return ("PermissionError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      FileItem itemToDownload = null;
      itemToDownload = thisItem;
      //itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "sales-reports") + getDatePath(
          itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("PMF-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "sales-reports-delete"))) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_SALES_REPORTS);
      if (!hasAuthority(context, thisItem.getEnteredBy())) {
        return ("PermissionError");
      }
      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(
            db, this.getPath(context, "sales-reports"));

        String filePath1 = this.getPath(context, "sales-reports") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename() + ".csv";
        java.io.File fileToDelete1 = new java.io.File(filePath1);
        if (!fileToDelete1.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath1);
        }

        String filePath2 = this.getPath(context, "sales-reports") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename() + ".html";
        java.io.File fileToDelete2 = new java.io.File(filePath2);
        if (!fileToDelete2.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath2);
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports del");
    if (recordDeleted) {
      return ("DeleteReportOK");
    } else {
      return ("DeleteReportERROR");
    }
  }
}


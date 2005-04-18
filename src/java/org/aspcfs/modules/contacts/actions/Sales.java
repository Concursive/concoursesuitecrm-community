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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.GraphSummaryList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.ContactUtils;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.LeadUtils;
import org.aspcfs.utils.StringUtils;
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
 * @version    $Id$
 */
public final class Sales extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandDashboard(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-view"))) {
      return ("PermissionError");
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

      //Generate the leads pagedList for the currentId, right of graph
      PagedListInfo dashboardListInfo = this.getPagedListInfo(context, "SalesDashboardListInfo");
      dashboardListInfo.setLink("Sales.do?command=Dashboard");
      dashboardListInfo.setColumnToSortBy("c.entered");
      ContactList leads = new ContactList();
      leads.setPagedListInfo(dashboardListInfo);
      dashboardListInfo.setSearchCriteria(leads, context);
      leads.setExcludeUnapprovedContacts(true);
      leads.setOldestFirst(Constants.FALSE);  
      if (myLeads != null && "true".equals(myLeads)) {
        leads.setOwnerOrReader(true);
        leads.setLeadsOnly(Constants.TRUE);
        leads.setReadBy(idToUse);
        leads.setOwner(idToUse);
        context.getRequest().setAttribute("myLeads", "" + false);
      } else {
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
        XYSeriesCollection categoryData = createCategoryDataset(linesToDraw, graphString);
        JFreeChart chart =
            ChartFactory.createTimeSeriesChart(
            "",
        // chart title
        "",
        // xlabel
        "",
        // ylabel
        categoryData,
            false,
        // legend
        true,
        // tooltips
        false
        // urls
        );
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
        String fileName = "leads" + String.valueOf(testDate.getTime()) + String.valueOf(context.getSession().getCreationTime());

        ChartRenderingInfo info = new ChartRenderingInfo(
            new StandardEntityCollection());
        File imageFile = new File(filePath + fileName + ".jpg");
        ChartUtilities.saveChartAsJPEG(imageFile, 1.0f, chart, width, height, info);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath + fileName + ".map")), true);
        ChartUtilities.writeImageMap(pw, fileName, info);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-view"))) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      //Create the paged list info to store the search form
      PagedListInfo salesListInfo = this.getPagedListInfo(context, "SalesListInfo");
      salesListInfo.setCurrentLetter("");
      salesListInfo.setCurrentOffset(0);

      //Lookup Lists in the dashboard
      LookupList sources = new LookupList(db, "lookup_contact_source");
      sources.addItem(-1, systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("SourceList", sources);
      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      ratings.addItem(-1, systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("RatingList", ratings);
      CountrySelect countrySelect = new CountrySelect(systemStatus.getLabel("pipeline.any"));
      context.getRequest().setAttribute("countrySelect", countrySelect);
      context.getRequest().setAttribute("listForm", ""+true);

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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    String addAnother = (String) context.getRequest().getAttribute("addAnother");
    if (addAnother == null || "".equals(addAnother.trim())) {
      thisContact = (Contact) context.getFormBean();
    }
    try {
      db = getConnection(context);
      //prepare userList for reassigning owner
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      User thisRec = thisUser.getUserRecord();
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
      userList.setMyId(getUserId(context));
      userList.setMyValue(thisUser.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    //check for appropriate permissions
    if (!hasPermission(context, "sales-leads-add")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    Contact thisContact = (Contact) context.getFormBean();
    thisContact.setRequestItems(context);
    thisContact.setEnteredBy(getUserId(context));
    thisContact.setModifiedBy(getUserId(context));
    thisContact.setIsLead(true);
    thisContact.setLeadStatus(Contact.LEAD_UNPROCESSED);
    Connection db = null;
    try {
      db = getConnection(context);
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
      thisContact.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
      // trying to insert a contact
      isValid = validateObject(context, db, thisContact);
      if (isValid) {
        recordInserted = thisContact.insert(db);
      }
      if (isValid && recordInserted) {
        processInsertHook(context, thisContact);
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        context.getRequest().setAttribute("contactId", "" + thisContact.getId());
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
      if ("true".equals((String) context.getRequest().getParameter("saveAndNew"))) {
        context.getRequest().removeAttribute("ContactDetails");
        context.getRequest().setAttribute("addAnother", "" + true);
        return executeCommandAdd(context);
      } else if ("true".equals((String) context.getRequest().getParameter("saveAndClone"))) {
        return "SaveOK";
      }
    } else if (!isValid && !recordInserted) {
      return executeCommandAdd(context);
    }
    return executeCommandDashboard(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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

    ContactList contacts = new ContactList();
    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(context, "SalesListInfo");
    searchListInfo.setLink("Sales.do?command=List&listForm="+(listForm != null ? listForm : ""));

    Connection db = null;
    try {
      db = getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList sources = systemStatus.getLookupList(db, "lookup_contact_source");
      sources.addItem(-1, systemStatus.getLabel("quotes.all"));
      context.getRequest().setAttribute("sourceList", sources);

      LookupList ratings = systemStatus.getLookupList(db, "lookup_contact_rating");
      ratings.addItem(-1, systemStatus.getLabel("quotes.all"));
      context.getRequest().setAttribute("ratingList", ratings);

      contacts.setPagedListInfo(searchListInfo);
      searchListInfo.setSearchCriteria(contacts, context);
      contacts.buildList(db);
      context.getRequest().setAttribute("contacts", contacts);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-view"))) {
      return ("PermissionError");
    }
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
    String readStatusString = (String) context.getRequest().getAttribute("readStatus");
    String contactId = (String) context.getRequest().getAttribute("contactId");
    if (contactId == null || "".equals(contactId)) {
      contactId = (String) context.getRequest().getParameter("contactId");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    ContactList contacts = new ContactList();
    PagedListInfo searchListInfo = this.getPagedListInfo(context, "SalesListInfo",false);
    Contact contact = null;
    Connection db = null;
    try {
      db = getConnection(context);
      if (nextValue != null && !"".equals(nextValue.trim())) {
        contacts.setPagedListInfo(searchListInfo);
        searchListInfo.setSearchCriteria(contacts, context);
        contactId = "" + LeadUtils.getNextLead(db, Integer.parseInt(contactId), contacts);
      }
//    context.getRequest().setAttribute("nextValue", ""+true);

      LookupList sources = new LookupList(db, "lookup_contact_source");
      sources.addItem(-1, systemStatus.getLabel("accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("SourceList", sources);

      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      ratings.addItem(-1, systemStatus.getLabel("accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("RatingList", ratings);

      if (readStatusString == null || "".equals(readStatusString.trim())) {
        if (hasPermission(context, "sales-leads-edit")) {
          readStatus = LeadUtils.setReadStatus(db, Integer.parseInt(contactId), this.getUserId(context));
        }
      } else {
        try {
          readStatus = Integer.parseInt(readStatusString);
        } catch (Exception error1) {
        }
      }
      context.getRequest().setAttribute("readStatus", "" + readStatus);
      if (contactId != null && !"".equals(contactId) && Integer.parseInt(contactId) != -1) {
        contact = new Contact(db, contactId);
        context.getRequest().setAttribute("ContactDetails", contact);
      } else {
        HashMap errors = new HashMap();
        errors.put("actionError", systemStatus.getLabel("object.validation.leadsSearchActionError"));//End of search range reached
        processErrors(context, errors);
        return executeCommandSearchForm(context);
      }
      context.getRequest().setAttribute("foundDuplicateLastName", ContactUtils.foundDuplicateLastName(db, contact.getNameLast()));
      context.getRequest().setAttribute("foundDuplicateCompany", ContactUtils.foundDuplicateCompany(db, contact.getCompany()));
      context.getRequest().setAttribute("foundDuplicateEmailAddress", ContactUtils.foundDuplicateEmailAddresses(db, contact.getEmailAddressList()));
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandCheckAssignStatus(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-edit"))) {
      return ("PermissionError");
    }
    String from = (String) context.getRequest().getAttribute("from");
    if (from == null || "".equals(from)) {
      from = (String) context.getRequest().getParameter("from");
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
          assignStatus = LeadUtils.tryToAssignLead(db, Integer.parseInt(contactId), userId, Integer.parseInt(owner));
        } else {
          assignStatus = LeadUtils.tryToAssignLead(db, Integer.parseInt(contactId), userId);
        }
        if (assignStatus) {
          context.getRequest().setAttribute("assignStatus", "assigned");
        } else {
          context.getRequest().setAttribute("assignStatus", "notAssigned");
        }
      } else {
        skippedStatus = LeadUtils.skipLead(db, Integer.parseInt(contactId), userId);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      contact.setIsLead(false);
      contact.setConversionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
      if (rating != null && !"".equals(rating)) {
        contact.setRating(Integer.parseInt(rating));
      }
      contact.setComments(comments);
      contact.update(db, context);
      processUpdateHook(context, oldContact, contact);
      int size = LeadUtils.cleanUpContact(db, contact.getId(), this.getUserId(context));
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    context.getRequest().setAttribute("nextValue", nextValue != null ? nextValue : "");
    String contactId = context.getRequest().getParameter("contactId");
    String owner = (String) context.getRequest().getParameter("owner");
    String leadStatus = (String) context.getRequest().getParameter("leadStatus");
    String comments = (String) context.getRequest().getParameter("comments");
    String rating = (String) context.getRequest().getParameter("rating");
    SystemStatus systemStatus = this.getSystemStatus(context);
    Contact thisContact = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisContact = new Contact(db, contactId);
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
        processUpdateHook(context, oldContact, thisContact);
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        context.getRequest().setAttribute("contactId", "" + thisContact.getId());
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
    if (from != null && !"".equals(from) && "dashboard".equals(from)) {
      return executeCommandDashboard(context);
    } else {
      return executeCommandList(context);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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

      DependencyList dependencies = contact.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='Sales.do?command=Delete&contactId=" + contactId + "&listForm="+ (listForm != null ? listForm : "") + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header2"));
        htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='Sales.do?command=Delete&contactId=" + contactId + "&listForm="+ (listForm != null ? listForm : "") + RequestUtils.addLinkParams(context.getRequest(), "popup|from") + "'");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      int size = LeadUtils.cleanUpContact(db, contact.getId(), this.getUserId(context));
      if (!hasAuthority(context, contact.getOwner())) {
        return "PermissionError";
      }
      contact.delete(db, context);
      String returnType = (String) context.getRequest().getParameter("return");
      if (returnType != null && !"list".equals(returnType)) {
        context.getRequest().setAttribute("refreshUrl", "Sales.do?command=Dashboard" + RequestUtils.addLinkParams(context.getRequest(), "actionId"));
      } else {
        context.getRequest().setAttribute("refreshUrl", "Sales.do?command=List" + RequestUtils.addLinkParams(context.getRequest(), "actionId|listForm|from"));
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
   * @param  pertainsTo    Description of the Parameter
   * @param  leadsList     Description of the Parameter
   * @param  usersToGraph  Description of the Parameter
   * @return               Description of the Return Value
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
            System.out.println("Sales-> (RE)BUILDING LEADS DATA FOR " + pertainsTo.getId());
          }
          pertainsTo.setLccr(new GraphSummaryList(8, GraphSummaryList.WEEK_RANGE, false));
          Iterator leadsIterator = leadsList.iterator();
          while (leadsIterator.hasNext()) {
            Contact tempLead = (Contact) leadsIterator.next();
            if ((tempLead.getOwner() == pertainsTo.getId())) {
              myDate = new java.util.Date(tempLead.getConversionDate().getTime());
              readDate.setTime(myDate);
              readDateAdjusted.setTime(myDate);
              readDate.add(Calendar.DAY_OF_WEEK, -(readDate.get(Calendar.DAY_OF_WEEK) - readDate.getFirstDayOfWeek()));
              //Fix this readDate as it should show a lot more intervals of readDate
              valKey = String.valueOf(readDate.get(java.util.Calendar.YEAR)) + String.valueOf(readDate.get(java.util.Calendar.MONTH)) + String.valueOf(readDate.get(java.util.Calendar.DATE));
              //get the individual graph values
              lccrAddTerm = new Double((tempLead.getConversionDate() != null) ? 1.0 : 0.0);
              //case: conversion date within 0-2m range
//if (((rightNow.after(readDate) || rightNowAdjusted.after(readDate)) && twoMonthsAgo.before(readDate)) || rightNow.equals(readDate) || twoMonthsAgo.equals(readDate)) {
              pertainsTo.setGraphValuesLeads(valKey, lccrAddTerm);
//}
            }
          }
          pertainsTo.setIsValidLead(true, true);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("Sales-> Unwanted exception occurred: " + e.toString());
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
   * @param  linesToDraw  Description of Parameter
   * @param  whichGraph   Description of Parameter
   * @return              Description of the Returned Value
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
        java.util.Date dateValue = createDate(iteratorDate.get(Calendar.YEAR), iteratorDate.get(Calendar.MONTH), iteratorDate.get(Calendar.DATE));
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
   * @param  primaryNode   Description of the Parameter
   * @param  currentLines  Description of the Parameter
   * @return               Description of the Return Value
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
        thisLine.getLccr().setValue(valKeys[count], new Double(primaryNode.getLccr().getValue(valKeys[count]).doubleValue() + (addToMe.getLccr().getValue(valKeys[count])).doubleValue()));
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
   * @param  toRollUp      Description of the Parameter
   * @param  currentLines  Description of the Parameter
   * @return               Description of the Return Value
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
        thisLine.getLccr().setValue(valKeys[count], thisUser.getLccr().getValue(valKeys[count]));
      }
    }
    currentLines.add(thisLine);
    return currentLines;
  }


  /**
   *  Description of the Method
   *
   * @param  y  Description of the Parameter
   * @param  m  Description of the Parameter
   * @param  d  Description of the Parameter
   * @return    Description of the Return Value
   */
  public static java.util.Date createDate(int y, int m, int d) {
    GregorianCalendar calendar = new GregorianCalendar(y, m, d, 0, 0, 0);
    return calendar.getTime();
  }

}


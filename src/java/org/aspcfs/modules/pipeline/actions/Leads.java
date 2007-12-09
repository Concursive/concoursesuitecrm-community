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
package org.aspcfs.modules.pipeline.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.GraphSummaryList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.UserUtils;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

/**
 * Core actions for Pipeline Management module
 *
 * @author chris
 * @version $Id$
 * @created October 17, 2001
 */
public final class Leads extends CFSModule {

  /**
   * Description of the Method
   *
   * @param y Description of Parameter
   * @param m Description of Parameter
   * @param d Description of Parameter
   * @return Description of the Returned Value
   */
  public static java.util.Date createDate(int y, int m, int d) {
    GregorianCalendar calendar = new GregorianCalendar(y, m, d, 0, 0, 0);
    return calendar.getTime();
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandDashboard(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Generate user list
    User thisRec = this.getUser(context, userId);
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userList = UserList.sortEnabledUsers(userList, new UserList());
    userList.setMyId(userId);
    String tempString = "";
    if (!thisRec.getEnabled() || (thisRec.getExpires() != null && thisRec.getExpires().before(
        new Timestamp(Calendar.getInstance().getTimeInMillis())))) {
      tempString = " *";
    }
    userList.setMyValue(thisRec.getContact().getNameLastFirst() + tempString);
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);
    //Parameters
    String id = context.getRequest().getParameter("headerId");
    if (id != null && !"-1".equals(id)) {
      if (!hasPermission(context, "pipeline-opportunities-add")) {
        return ("PermissionError");
      }
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      //check if a header needs to be built.
      if (id != null && !"-1".equals(id)) {
        //Build the container items
        OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(id));
        context.getRequest().setAttribute("opportunityHeader", oppHeader);
      }
      if ("list".equals(context.getRequest().getParameter("source"))) {
        addModuleBean(context, "View Opportunities", "Add Opportunity");
      } else {
        addModuleBean(context, "Add Opportunity", "Add Opportunity");
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      //add access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      if (context.getRequest().getParameter("actionSource") != null) {
        return getReturn(context, "AddPipelineLeads");
      }

      return "PrepareOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveComponent(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = 0;
    String componentId = context.getRequest().getParameter("id");
    String permission = "pipeline-opportunities-add";
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    OpportunityBean bean = new OpportunityBean();
    OpportunityHeader header = null;
    OpportunityComponent oldComponent = null;
    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    if (!systemStatus.hasField("opportunity.componentTypes")) {
      newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
    }
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));
    String action = (newComponent.getId() > 0 ? "modify" : "insert");
    Connection db = null;
    if ("modify".equals(action)) {
      permission = "pipeline-opportunities-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    try {
      db = this.getConnection(context);
      header = new OpportunityHeader(db, newComponent.getHeaderId());
      newComponent.setContactId(header.getContactLink());
      newComponent.setOrgId(header.getAccountLink());
      bean.setHeader(header);
      if (newComponent.getId() > 0) {
        oldComponent = new OpportunityComponent(
            db, Integer.parseInt(componentId));
        if (!(hasViewpointAuthority(db, context, "pipeline", oldComponent.getOwner(), userId) ||
            hasViewpointAuthority(db, context, "pipeline", header.getManager(), userId))) {
          return "PermissionError";
        }
        newComponent.setModifiedBy(getUserId(context));
        bean.setComponent(newComponent);
        isValid = this.validateObject(context, db, bean);
        isValid = this.validateObject(context, db, newComponent) && isValid;
        if (isValid) {
          resultCount = newComponent.update(db, context);
        }
        if (resultCount == 1) {
          newComponent.queryRecord(db, newComponent.getId());
          this.processUpdateHook(context, oldComponent, newComponent);
        }
      } else {
        if (!hasViewpointAuthority(db, context, "pipeline", header.getManager(), userId)) {
          return "PermissionError";
        }
        bean.setComponent(newComponent);
        isValid = this.validateObject(context, db, bean);
        isValid = this.validateObject(context, db, newComponent) && isValid;
        if (isValid) {
          recordInserted = newComponent.insert(db, context);
        }
      }
      if (recordInserted) {
        this.processInsertHook(context, newComponent);
        addRecentItem(context, newComponent);
      } else if ("modify".equals(action) && resultCount == -1) {
        UserBean thisUser = (UserBean) context.getSession().getAttribute(
            "User");
        User thisRec = thisUser.getUserRecord();
        UserList shortChildList = thisRec.getShortChildList();
        UserList userList = thisRec.getFullChildList(
            shortChildList, new UserList());
        userList.setMyId(getUserId(context));
        userList.setMyValue(thisUser.getContact().getNameLastFirst());
        userList.setIncludeMe(true);
        userList.setExcludeDisabledIfUnselected(true);
        userList.setExcludeExpiredIfUnselected(true);
        context.getRequest().setAttribute("UserList", userList);
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute(
            "TypeList", newComponent.getTypeList());
      } else if ("insert".equals(action) && !recordInserted) {
        //rebuild the form
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute(
            "TypeList", newComponent.getTypeList());
      }
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      if (!systemStatus.hasField("opportunity.componentTypes")) {
        newComponent.setTypeListToTypes(db);
      }
      context.getRequest().setAttribute("ComponentDetails", newComponent);
      //add access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("insert".equals(action)) {
      if (recordInserted) {
        return (executeCommandDetailsOpp(context));
      }
    } else {
      if (resultCount == 1) {
        if ("list".equals(context.getRequest().getParameter("return"))) {
          return (executeCommandSearch(context));
        } else if ("details".equals(
            context.getRequest().getParameter("return"))) {
          return (executeCommandDetailsComponent(context));
        } else if (context.getRequest().getParameter("popup") != null) {
          return "PopupCloseOK";
        }
        return (executeCommandDetailsComponent(context));
      }
    }
    return (executeCommandPrepare(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-add")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    SystemStatus systemStatus = this.getSystemStatus(context);
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute(
        "OppDetails");
    //String association = context.getRequest().getParameter("opp_type");
    Contact linkedContact = null;
    if (!systemStatus.hasField("opportunity.componentTypes")) {
      //set types
      newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    }
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    if (newOpp.getHeader().getAccountLink() > -1) {
      newOpp.getHeader().setContactLink("-1");
    } else if (newOpp.getHeader().getContactLink() > -1) {
      newOpp.getHeader().setAccountLink("-1");
    }
    context.getRequest().setAttribute("oppDetails", newOpp);
    Connection db = null;
    try {
      db = this.getConnection(context);
      //set the name for displaying on the form in case there are any errors
      if (newOpp.getHeader().getContactLink() > -1) {
        linkedContact = new Contact(db, newOpp.getHeader().getContactLink());
        newOpp.getHeader().setContactName(linkedContact.getNameLastFirst());
      }
      // newOpp only validates the owner of the component to
      //the accessType and manager in the header
      isValid = this.validateObject(context, db, newOpp);
      isValid = this.validateObject(context, db, newOpp.getHeader()) && isValid;
      isValid = this.validateObject(context, db, newOpp.getComponent()) && isValid;
      if (isValid) {
        recordInserted = newOpp.insert(db, context);
      }
      if (recordInserted) {
        newOpp.getComponent().setContactId(
            newOpp.getHeader().getContactLink());
        newOpp.getComponent().setOrgId(newOpp.getHeader().getAccountLink());
        this.processInsertHook(context, newOpp.getComponent());
      } else {
        LookupList environmentSelect = systemStatus.getLookupList(
            db, "lookup_opportunity_environment");
        context.getRequest().setAttribute(
            "environmentSelect", environmentSelect);
        LookupList competitorsSelect = systemStatus.getLookupList(
            db, "lookup_opportunity_competitors");
        context.getRequest().setAttribute(
            "competitorsSelect", competitorsSelect);
        LookupList compellingEventSelect = systemStatus.getLookupList(
            db, "lookup_opportunity_event_compelling");
        context.getRequest().setAttribute(
            "compellingEventSelect", compellingEventSelect);
        LookupList budgetSelect = systemStatus.getLookupList(
            db, "lookup_opportunity_budget");
        context.getRequest().setAttribute("budgetSelect", budgetSelect);
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute(
            "TypeList", newOpp.getComponent().getTypeList());
        //add access types
        AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
        context.getRequest().setAttribute("accessTypeList", accessTypeList);
        if (newOpp.getHeader().getAccountLink() > -1) {
          Organization thisOrg = new Organization(
              db, newOpp.getHeader().getAccountLink());
          newOpp.getHeader().setAccountName(thisOrg.getName());
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      addRecentItem(context, newOpp.getHeader());
      if (context.getRequest().getParameter("popup") != null) {
          if(context.getRequest().getParameter("fromPop").equals("true")){
           context.getRequest().setAttribute("fromPop", "true");
          }
        return ("CloseInsertOppPopup");
      }
      context.getRequest().setAttribute(
          "headerId", String.valueOf(newOpp.getHeader().getId()));
      return (executeCommandDetailsOpp(context));
    }
    return (executeCommandPrepare(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetailsOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    int headerId = -1;
    PagedListInfo searchOppListInfo = this.getPagedListInfo(context, "SearchOppListInfo", false);
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;
    addModuleBean(context, "View Opportunities", "View Opportunity Details");
    String fromQuoteDetails = (String) context.getRequest().getParameter(
        "fromQuoteDetails");
    String viewSource = context.getRequest().getParameter("viewSource");
    if (fromQuoteDetails != null && "true".equals(fromQuoteDetails)) {
      context.getSession().removeAttribute("LeadsComponentListInfo");
      context.getSession().removeAttribute("PipelineViewpointInfo");
    }
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Check parameters
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt(
          (String) context.getRequest().getAttribute("headerId"));
    }
    Connection db = null;
    //Configure the paged list info
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("LeadsComponentListInfo");
    }
    PagedListInfo componentListInfo = this.getPagedListInfo(
        context, "LeadsComponentListInfo");
    componentListInfo.setLink(
        "Leads.do?command=DetailsOpp&headerId=" + headerId + RequestUtils.addLinkParams(
            context.getRequest(), "viewSource"));
    try {
      db = this.getConnection(context);
      //Generate the opportunity header
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      //add access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      //Generate the list of components
      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setAccessType(thisHeader.getAccessType());
      String accessType = searchOppListInfo.getSearchOptionValue("searchcodeAccessType");
      if (viewSource == null || !"dashboard".equals(viewSource)) {
        if (accessType != null && !"".equals(accessType.trim()) && AccessType.CONTROLLED_HIERARCHY == accessTypeList.getRuleId(Integer.parseInt(accessType))) {
          if (searchOppListInfo.getFilterKey("listFilter2") != -1) {
            componentList.setControlledHierarchy(Constants.TRUE, this.getUserRange(context, searchOppListInfo.getFilterKey("listFilter2")));
          } else {
            componentList.setControlledHierarchy(Constants.TRUE, this.getUserRange(context));
          }
        } else if (accessType != null && !"".equals(accessType.trim()) && AccessType.PUBLIC == accessTypeList.getRuleId(Integer.parseInt(accessType))) {
          if (searchOppListInfo.getFilterKey("listFilter2") != -1) {
            componentList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context, searchOppListInfo.getFilterKey("listFilter2")));
          } else {
            componentList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
          }
        } else {
          componentList.setOwnerIdRange(this.getUserRange(context));
          componentList.setControlledHierarchyOnly(Constants.UNDEFINED);
        }
      } else {
        componentList.setOwnerIdRange(this.getUserRange(context));
        componentList.setControlledHierarchyOnly(Constants.UNDEFINED);
      }
      componentList.setHeaderId(thisHeader.getId());
      componentList.setIncludeOnlyTrashed((thisHeader.isTrashed()));
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);
      if (!allowMultiple(context) && (componentList.size() > 0)) {
        //Load the opportunity header for display
        OpportunityComponent thisComponent = (OpportunityComponent) componentList.get(0);
        context.getRequest().setAttribute("LeadsComponentDetails", thisComponent);
      }
      addRecentItem(context, thisHeader);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!allowMultiple(context) && (componentList.size() > 0)) {
      return this.getReturn(context, "DetailsComponent");
    }
    return ("OppDetailsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  
  
  public String executeCommandSearchForm(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandSearchForm(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }
      
  private String executeCommandSearchForm(ActionContext context,Connection db) throws NumberFormatException, SQLException {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    PagedListInfo searchOppListInfo = this.getPagedListInfo(
        context, "SearchOppListInfo");
    //Prepare viewpoints
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int vpUserId = viewpointInfo.getVpUserId(this.getUserId(context));
    int userId = this.getUserId(context);
    if (vpUserId != -1 && vpUserId != userId) {
      userId = vpUserId;
    }
      //Opportunity types drop-down menu
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
      typeSelect.addItem(0, systemStatus.getLabel("accounts.allTypes","All Types"));
      context.getRequest().setAttribute("TypeSelect", typeSelect);
      //add access types
      AccessTypeList accessTypeList = null;
      accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      //Generate user list
      User thisRec = this.getUser(context, userId);
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(
          shortChildList, new UserList());
      userList = UserList.sortEnabledUsers(userList, new UserList());
      userList.setMyId(userId);
      userList.setMyValue(thisRec.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      context.getRequest().setAttribute("UserList", userList);
      //check if account/contact is already selected, if so build it
      if (!"".equals(
          searchOppListInfo.getSearchOptionValue("searchcodeOrgId")) && !"-1".equals(
          searchOppListInfo.getSearchOptionValue("searchcodeOrgId"))) {
        String orgId = searchOppListInfo.getSearchOptionValue(
            "searchcodeOrgId");
        Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrg);
      }
      if (!"".equals(
          searchOppListInfo.getSearchOptionValue("searchcodeContactId")) && !"-1".equals(
          searchOppListInfo.getSearchOptionValue("searchcodeContactId"))) {
        String id = searchOppListInfo.getSearchOptionValue(
            "searchcodeContactId");
        Contact thisContact = new Contact(db, Integer.parseInt(id));
        context.getRequest().setAttribute("ContactDetails", thisContact);
      }
      //stage
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      stageSelect.addItem(-1, systemStatus.getLabel("pipeline.allstages","All Stages"));
      context.getRequest().setAttribute("StageList", stageSelect);

      //sites lookup
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      siteList.setJsEvent("onChange=\"javascript:resetSiteData(this.form);\"");
      context.getRequest().setAttribute("SiteIdList", siteList);
    addModuleBean(context, "Search Opportunities", "Search Opportunities");
    return ("SearchOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    OpportunityHeader thisOpp = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;

    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisOpp = new OpportunityHeader(db, id);
      DependencyList dependencies = thisOpp.processDependencies(db, allowMultiple(context));
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='Leads.do?command=DeleteOpp&id=" + id + RequestUtils.addLinkParams(
                context.getRequest(), "viewSource") + "'");
      } else if (dependencies.canDelete()) {
        dependencies.setSystemStatus(systemStatus);
        htmlDialog.addMessage(
            systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='Leads.do?command=TrashOpp&id=" + id + RequestUtils.addLinkParams(
            context.getRequest(), "viewSource") + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader(
            systemStatus.getLabel(
                "confirmdelete.opportunity.cannotDeleteHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   * Action method to generate dashboard graphs, opportunities, and hierarchy
   * gross pipeline.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "pipeline-dashboard-view")) {
      if (!hasPermission(context, "pipeline-opportunities-view")) {
        return ("PermissionError");
      }
      return (executeCommandSearch(context));
    }
    addModuleBean(context, "Dashboard", "Dashboard");

    //Prepare the user id to base all data on
    int idToUse = 0;

    //Check to see if a Viewpoint is selected else default to user who's logged in
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    User thisRec = null;

    //Check if a specific user was selected
    int overrideId = StringUtils.parseInt(
        context.getRequest().getParameter("oid"), -1);

    //Check if the list is being reset
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      overrideId = -1;
      context.getSession().removeAttribute("leadsoverride");
      context.getSession().removeAttribute("leadsothername");
      context.getSession().removeAttribute("leadspreviousId");
    }

    //Determine the user whose data is being shown, by default it's the current user
    if (overrideId < 0) {
      if (context.getSession().getAttribute("leadsoverride") != null) {
        overrideId = StringUtils.parseInt(
            (String) context.getSession().getAttribute("leadsoverride"), -1);
      } else {
        overrideId = userId;
      }
    }
    String checkFileName = null;
    Connection db = null;

    UserList fullChildList = new UserList();
    UserList shortChildList = new UserList();
    OpportunityComponentList realFullOppList = new OpportunityComponentList();
    HtmlSelect graphTypeSelect = new HtmlSelect();

    //Determine the graph type to generate
    String graphString = null;
    if (context.getRequest().getParameter("whichGraph") != null) {
      graphString = context.getRequest().getParameter("whichGraph");
      context.getSession().setAttribute("pipelineGraph", graphString);
    } else if ((String) context.getRequest().getSession().getAttribute(
        "pipelineGraph") != null) {
      graphString = (String) context.getRequest().getSession().getAttribute(
          "pipelineGraph");
    } else {
      graphString = "gmr";
    }
    Locale locale = getUser(context, getUserId(context)).getLocale();

    try {
      db = this.getConnection(context);

      //Add Viewpoints
      UserList vpUserList = this.addViewpoints(db, context, "pipeline");
      viewpointInfo.setVpUserName(vpUserList);

      //Check that the user hasAuthority for this oid
      if (hasViewpointAuthority(db, context, "pipeline", overrideId, userId)) {
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
            "leadsoverride", String.valueOf(overrideId));
        context.getSession().setAttribute(
            "leadsothername", thisRec.getContact().getNameFull());
        context.getSession().setAttribute(
            "leadspreviousId", String.valueOf(thisRec.getManagerId()));
      }

      //Build the html graph combo box
      SystemStatus systemStatus = this.getSystemStatus(context);
      graphTypeSelect.setSelectName("whichGraph");
      graphTypeSelect.setJsEvent("onChange=\"document.Dashboard.submit();\"");
      graphTypeSelect.addItem(
          "gmr", systemStatus.getLabel("pipeline.graphTypeSelect.item.GMR"));
      graphTypeSelect.addItem(
          "ramr", systemStatus.getLabel("pipeline.graphTypeSelect.item.RAMR"));
      graphTypeSelect.addItem(
          "cgmr", systemStatus.getLabel("pipeline.graphTypeSelect.item.CGMR"));
      graphTypeSelect.addItem(
          "cramr", systemStatus.getLabel(
          "pipeline.graphTypeSelect.item.CRAMR"));
      graphTypeSelect.setDefaultKey(graphString);

      //Generate the opportunities pagedList for the idToUse (builds component count and value of idToUse only), right of graph
      PagedListInfo dashboardListInfo = this.getPagedListInfo(
          context, "DashboardListInfo");
      dashboardListInfo.setLink("Leads.do?command=Dashboard");
      dashboardListInfo.setColumnToSortBy("x.description");
      OpportunityHeaderList headerList = new OpportunityHeaderList();
      headerList.setPagedListInfo(dashboardListInfo);
      headerList.setOwner(idToUse);
      headerList.setQueryOpenOnly(true);
      headerList.setComponentsOwnedByUser(idToUse);
      headerList.setBuildTotalValues(true);
      headerList.setBuildActionPlans(true);
      headerList.buildList(db);
      context.getRequest().setAttribute("oppList", headerList);

      //Check the cache and see if the current graph exists and is valid
      if (thisRec.getIsValid()) {
        if (graphString.equals("gmr")) {
          checkFileName = thisRec.getGmr().getLastFileName();
        } else if (graphString.equals("ramr")) {
          checkFileName = thisRec.getRamr().getLastFileName();
        } else if (graphString.equals("cgmr")) {
          checkFileName = thisRec.getCgmr().getLastFileName();
        } else if (graphString.equals("cramr")) {
          checkFileName = thisRec.getCramr().getLastFileName();
        }
      }

      // See if the file exists, otherwise reset the user and the checkFileName
      if (checkFileName != null) {
        File checkFile = new File(context.getServletContext().getRealPath("/") + "graphs" + fs + checkFileName + ".jpg");
        if (!checkFile.exists()) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Leads-> Invalidating data, file not found: " + context.getServletContext().getRealPath("/") + "graphs" + fs + checkFileName + ".jpg");
          }
          thisRec.setIsValid(false, true);
          checkFileName = null;
        }
      }

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
//        realFullOppList.setUnits("M");
        realFullOppList.setOwnerIdRange(range);
        realFullOppList.setQueryOpenOnly(true);
        //ignores components not relevant for the graph
        realFullOppList.setIncludeOnlyForGraph(true);
        if (getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null) {
          realFullOppList.setDefaultTerms(getPref(context, "OPPORTUNITY.DEFAULT_TERM"));
        }
        if (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null) {
          realFullOppList.setDefaultUnits(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"));
        }
        Calendar calendar = Calendar.getInstance(
            getUserTimeZone(context), UserUtils.getUserLocale(
            context.getRequest()));
        realFullOppList.setCloseDateEnd(
            new java.sql.Timestamp(
                calendar.getTimeInMillis() + ((long) 1000 * 60 * 60 * 24 * 395)));
        realFullOppList.buildList(db);
      }

      //ShortChildList is used for showing user list, under graph
      shortChildList.buildPipelineValues(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    try {
      //Determine if a graph has to be generated
      if (checkFileName != null) {
        //Existing graph is good
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Leads-> Using cached chart");
        }
        context.getRequest().setAttribute("GraphFileName", checkFileName);
      } else {
        //Need to generate a new graph
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Leads-> Preparing the chart");
        }
        //Filter out the selected user for graph
        OpportunityComponentList tempOppList = new OpportunityComponentList();
        Iterator z = realFullOppList.iterator();
        while (z.hasNext()) {
          OpportunityComponent tempOppComponent = (OpportunityComponent) (z.next());
          if (tempOppComponent.getOwner() == idToUse) {
            tempOppList.add(tempOppComponent);
          }
        }
        //add up all opportunities for children line on graph
        UserList tempUserList = new UserList();
        Iterator n = fullChildList.iterator();
        while (n.hasNext()) {
          User thisRecord = (User) n.next();
          tempUserList = prepareLines(
              context, thisRecord, realFullOppList, tempUserList);
        }
        UserList linesToDraw = new UserList();
        linesToDraw = calculateLine(tempUserList, linesToDraw);
        //set my own, on top of the children line
        tempUserList = prepareLines(context, thisRec, tempOppList, tempUserList);
        linesToDraw = calculateLine(thisRec, linesToDraw);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Leads-> Drawing the chart");
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
          System.out.println("Leads-> Drawing the chart");
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
        String fileName = String.valueOf(idToUse) + String.valueOf(
            testDate.getTime()) + String.valueOf(
            context.getSession().getCreationTime());

        // Write the chart image
        ChartRenderingInfo info = new ChartRenderingInfo(
            new StandardEntityCollection());
        File imageFile = new File(filePath + fileName + ".jpg");
        ChartUtilities.saveChartAsJPEG(
            imageFile, 1.0f, chart, width, height, info);
        PrintWriter pw = new PrintWriter(
            new BufferedWriter(new FileWriter(filePath + fileName + ".map")));
        ChartUtilities.writeImageMap(pw, fileName, info, false);
        pw.flush();
        pw.close();

        //Update the cached filename
        if (graphString.equals("gmr")) {
          thisRec.getGmr().setLastFileName(fileName);
        } else if (graphString.equals("ramr")) {
          thisRec.getRamr().setLastFileName(fileName);
        } else if (graphString.equals("cgmr")) {
          thisRec.getCgmr().setLastFileName(fileName);
        } else if (graphString.equals("cramr")) {
          thisRec.getCramr().setLastFileName(fileName);
        }
        context.getRequest().setAttribute("GraphFileName", fileName);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      System.out.println("Leads-> GraphicsError exception occurred here");
      return ("GraphicsError");
    }
    context.getRequest().setAttribute("ShortChildList", shortChildList);
    context.getRequest().setAttribute("GraphTypeList", graphTypeSelect);
    return ("DashboardOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }
    //get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    boolean recordDeleted = false;
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOpp = new OpportunityHeader(
          db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", newOpp.getManager(), userId)) {
        return "PermissionError";
      }
      recordDeleted = newOpp.delete(db, context, getDbNamePath(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Opportunities", "Delete an opportunity");
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl", "Leads.do?command=Search");
      deleteRecentItem(context, newOpp);
      return ("OppDeleteOK");
    } else {
      processErrors(context, newOpp.getErrors());
      return (executeCommandSearch(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrashOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-delete")) {
      return ("PermissionError");
    }

    //get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    boolean recordUpdated = false;
    OpportunityHeader newOpp = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      newOpp = new OpportunityHeader(
          db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", newOpp.getManager(), userId)) {
        return "PermissionError";
      }
      recordUpdated = newOpp.updateStatus(
          db, context, true, this.getUserId(context));
      newOpp.invalidateUserData(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Leads.do?command=Search");
      deleteRecentItem(context, newOpp);
      return ("OppDeleteOK");
    } else {
      processErrors(context, newOpp.getErrors());
      return (executeCommandSearch(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestoreOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-delete")) {
      return ("PermissionError");
    }
    //get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    boolean recordUpdated = false;
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOpp = new OpportunityHeader(
          db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", newOpp.getManager(), userId)) {
        return "PermissionError";
      }
      recordUpdated = newOpp.updateStatus(
          db, context, false, this.getUserId(context));
      newOpp.invalidateUserData(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "headerId", context.getRequest().getParameter("id"));
    return executeCommandDetailsOpp(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteComponent(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-delete")) {
      return ("PermissionError");
    }

    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    boolean recordDeleted = false;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      header = new OpportunityHeader(db, component.getHeaderId());
      if (!(hasViewpointAuthority(db, context, "pipeline", component.getOwner(), userId) ||
          hasViewpointAuthority(db, context, "pipeline", header.getManager(), userId))) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      deleteRecentItem(context, component);
      if (context.getRequest().getParameter("return") != null) {
        if (context.getRequest().getParameter("return").equals("list")) {
          context.getRequest().setAttribute(
              "refreshUrl", "Leads.do?command=Search");
          return ("ComponentDeleteOK");
        }
      }
      context.getRequest().setAttribute(
          "refreshUrl", "Leads.do?command=DetailsOpp&headerId=" + component.getHeaderId());
      return ("ComponentDeleteOK");
    } else {
      processErrors(context, component.getErrors());
      return (executeCommandSearch(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-delete")) {
      return ("PermissionError");
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl(
          "javascript:window.location.href='LeadsComponents.do?command=DeleteComponent&id=" + id + "&return=" + context.getRequest().getParameter(
              "return") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Prepare the action items
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "View Opportunities", "Opportunities");

    String componentId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, componentId);
      //Build the container item
      OpportunityHeader oppHeader = new OpportunityHeader(db, component.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
      if (!(hasViewpointAuthority(db, context, "pipeline", component.getOwner(), userId) ||
          hasViewpointAuthority(db, context, "pipeline", oppHeader.getManager(), userId))) {
        return "PermissionError";
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      //add access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ComponentDetails", component);
    addRecentItem(context, component);

    //Generate user list
    User thisRec = this.getUser(context, userId);
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userList = UserList.sortEnabledUsers(userList, new UserList());
    userList.setMyId(userId);
    userList.setMyValue(thisRec.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    if (context.getRequest().getParameter("popup") != null) {
      return ("ModifyPopupOK");
    } else {
      return executeCommandPrepare(context);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Opportunities", "Modify Opportunity");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    //Parameters
    int headerId = Integer.parseInt(
        context.getRequest().getParameter("headerId"));
    try {
      db = this.getConnection(context);
      //Generate the header
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.setBuildRecordDetails(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      addRecentItem(context, thisHeader);
      return getReturn(context, "ModifyOpp");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Search Opportunities
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    //Prepare the paged list
    PagedListInfo searchOppListInfo = this.getPagedListInfo(
        context, "SearchOppListInfo");
    searchOppListInfo.setLink("Leads.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Prepare viewpoints
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int vpUserId = viewpointInfo.getVpUserId(this.getUserId(context));
    int userId = this.getUserId(context);
    boolean fetchedList = false;
    boolean isValid = false;
    if (vpUserId != -1 && vpUserId != userId) {
      userId = vpUserId;
    }
    Connection db = null;
    OpportunityList oppList = new OpportunityList();
    try {
      db = this.getConnection(context);
      //Opportunity types drop-down menu
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
      typeSelect.addItem(0, systemStatus.getLabel("accounts.allTypes","All Types"));
      context.getRequest().setAttribute("TypeSelect", typeSelect);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      context.getRequest().setAttribute("systemStatus", systemStatus);
      //add access types
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      //The list of opportunities, according to drop-down filter
      oppList.setPagedListInfo(searchOppListInfo);
      searchOppListInfo.setIsValid(true);
      searchOppListInfo.setSearchCriteria(oppList, context);
      if (oppList.getSiteId() == Constants.INVALID_SITE) {
        oppList.setSiteId(this.getUser(context, vpUserId).getSiteId());
        oppList.setIncludeAllSites(true);
      } else {
        oppList.setIncludeAllSites(false);
      }
      if (AccessType.CONTROLLED_HIERARCHY == accessTypeList.getRuleId(oppList.getAccessType())) {
        if (searchOppListInfo.getFilterKey("listFilter2") != -1) {
          oppList.setControlledHierarchy(Constants.TRUE, this.getUserRange(context, searchOppListInfo.getFilterKey("listFilter2")));
        } else {
          if (!excludeHierarchy(context)) {
            oppList.setControlledHierarchy(Constants.TRUE, this.getUserRange(context, userId));
          }
        }
      } else if (AccessType.PUBLIC == accessTypeList.getRuleId(oppList.getAccessType())) {
        if (searchOppListInfo.getFilterKey("listFilter2") != -1) {
          oppList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context, searchOppListInfo.getFilterKey("listFilter2")));
        } else {
          if (!excludeHierarchy(context)) {
            oppList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context, userId));
          }
        }
      } else {
        oppList.setOwner(userId);
        oppList.setControlledHierarchyOnly(Constants.UNDEFINED);
      }
      if ("open".equals(searchOppListInfo.getListView())) {
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(searchOppListInfo.getListView())) {
        oppList.setQueryClosedOnly(true);
      }
      oppList.setTypeId(searchOppListInfo.getFilterKey("listFilter1"));
      isValid = searchOppListInfo.getIsValid();
      if (isValid) {
        fetchedList = oppList.buildList(db);
      }
      context.getRequest().setAttribute("OpportunityList", oppList);

      //Generate user list
      User thisRec = this.getUser(context, userId);
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(
          shortChildList, new UserList());
      userList = UserList.sortEnabledUsers(userList, new UserList());
      userList.setMyId(userId);
      userList.setMyValue(thisRec.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      context.getRequest().setAttribute("UserList", userList);
      addModuleBean(context, "View Opportunities", "Opportunities Add");
      if (fetchedList) {
        OrganizationList disabledOrgs = new OrganizationList();
        disabledOrgs.setIncludeEnabled(Constants.FALSE);
        disabledOrgs.buildList(db);
        context.getRequest().setAttribute("disabledOrgs", disabledOrgs);
        return ("OppListOK");
      } else {
        processErrors(context, oppList.getErrors());
        return executeCommandSearchForm(context,db);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    boolean isValid = false;
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    Connection db = null;
    int resultCount = 0;
    String headerId = context.getRequest().getParameter("headerId");
    String type = context.getRequest().getParameter("type");
    try {
      db = this.getConnection(context);
      OpportunityHeader oldHeader = new OpportunityHeader(db, headerId);
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      if (!hasViewpointAuthority(db, context, "pipeline", oppHeader.getManager(), userId)) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(
          context.getRequest().getParameter("description"));
      if (type.equals("contact")) {
        oppHeader.setContactLink(
            context.getRequest().getParameter("contactLink"));
        oppHeader.setAccountLink(-1);
      } else if (type.equals("org")) {
        oppHeader.setAccountLink(
            context.getRequest().getParameter("accountLink"));
        oppHeader.setContactLink(-1);
      }
      isValid = this.validateObject(context, db, oppHeader);
      if (isValid) {
        resultCount = oppHeader.update(db);
      }
      if (resultCount == 1) {
        oppHeader.checkResetActionStepAttachment(db, oldHeader);
        this.processUpdateHook(context, oldHeader, oppHeader);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandSearch(context));
      } else {
        return (executeCommandDetailsOpp(context));
      }
    } else {
      if (resultCount == -1 || !isValid) {
        return executeCommandModifyOpp(context);
      }
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Configure the action
    addModuleBean(context, "View Opportunities", "Component Details");
    String componentId = context.getRequest().getParameter("id");
    OpportunityComponent thisComponent = null;
    OpportunityHeader header = null;
    //Begin processing
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      //add access types
      AccessTypeList accessTypeList = null;
      accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      header = new OpportunityHeader(db, thisComponent.getHeaderId());
      if (!hasViewpointAuthority(db, context, "pipeline", thisComponent.getOwner(), userId) &&
          accessTypeList.getCode(AccessType.PUBLIC) != header.getAccessType()) {
        return "PermissionError";
      }
      thisComponent.checkEnabledOwnerAccount(db);
      //Build the container item
      context.getRequest().setAttribute("opportunityHeader", header);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("LeadsComponentDetails", thisComponent);
    addRecentItem(context, thisComponent);
    return ("DetailsComponentOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandComponentHistoryDetails(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    
    //Configure the action
    addModuleBean(context, "View Opportunities", "Component Details");
    
    String historyId = context.getRequest().getParameter("id");
    String componentId = context.getRequest().getParameter("componentId");
    String headerId = context.getRequest().getParameter("headerId");
    
    OpportunityComponentLog thisComponentLog = null;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    //Begin processing
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      thisComponentLog = new OpportunityComponentLog(db, Integer.parseInt(historyId));
      
      if (componentId != null && !"".equals(componentId.trim())) {
        if (Integer.parseInt(componentId) != -1) { 
          component = new OpportunityComponent(db, Integer.parseInt(componentId));
          context.getRequest().setAttribute("opportunityComponent", component);
        }
      }
      
      if (headerId != null && !"".equals(headerId.trim())) {
        header = new OpportunityHeader(db, Integer.parseInt(headerId));
        context.getRequest().setAttribute("opportunityHeader", header);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("componentLogDetails", thisComponentLog);
    return ("ComponentHistoryDetailsOK");
  }

  
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandComponentHistory(ActionContext context) {
	    if (!hasPermission(context, "pipeline-opportunities-view")) {
	      return ("PermissionError");
	    }
	    int headerId = -1;
	    int componentId = -1;
	    
      PagedListInfo componentHistoryListInfo = this.getPagedListInfo(
	        context, "componentHistoryListInfo");
	    
      OpportunityHeader thisHeader = null;
      OpportunityComponent thisComponent = null;
	    OpportunityComponentLogList componentLogList = null;
	    
      addModuleBean(context, "View Opportunities", "View Opportunity Details");
	    
      //Get Viewpoints if any
	    ViewpointInfo viewpointInfo = this.getViewpointInfo(
	        context, "PipelineViewpointInfo");
	    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
	    
      //Check parameters
	    if (context.getRequest().getParameter("headerId") != null) {
	    	headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
	    } else {
	    	headerId = Integer.parseInt(
	          (String) context.getRequest().getAttribute("headerId"));
	    }
	    
      if (context.getRequest().getParameter("id") != null) {
	    	componentId = Integer.parseInt(context.getRequest().getParameter("id"));
	    } else if (context.getRequest().getAttribute("id") != null) {
	    	componentId = Integer.parseInt(
	          (String) context.getRequest().getAttribute("id"));
	    }
      
	    Connection db = null;
	    //Configure the paged list info
	    if ("true".equals(context.getRequest().getParameter("reset"))) {
	      context.getSession().removeAttribute("componentHistoryListInfo");
	    }
	    componentHistoryListInfo.setLink(
	        "LeadsComponents.do?command=ComponentHistory" + 
            "&headerId="+ headerId + RequestUtils.addLinkParams(
	            context.getRequest(), "id|viewSource"));
	    try {
	      db = this.getConnection(context);
        //Generate the component
        if (componentId > -1) {
          thisComponent = new OpportunityComponent();
          thisComponent.queryRecord(db, componentId);
          context.getRequest().setAttribute("opportunityComponent", thisComponent);
        }
        //Generate the opportunity header
        thisHeader = new OpportunityHeader();
        thisHeader.setBuildComponentCount(true);
        thisHeader.queryRecord(db, headerId);
        context.getRequest().setAttribute("opportunityHeader", thisHeader);
	      //Generate the list of component log entries for this header
        componentLogList = new OpportunityComponentLogList();
        componentLogList.setPagedListInfo(componentHistoryListInfo);
	      componentLogList.setHeaderId(thisHeader.getId());
        if (componentId > -1) {
          //filters the component log list to a specific component
          componentLogList.setComponentId(componentId);
        }
	      componentLogList.buildList(db);
	      context.getRequest().setAttribute("componentHistoryList", componentLogList);
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return ("ComponentHistoryOK");
	  }


  
  /**
   * Description of the Method
   *
   * @param pertainsTo   Description of Parameter
   * @param oppList      Description of Parameter
   * @param usersToGraph Description of Parameter
   * @return Description of the Returned Value
   */
  private UserList prepareLines(ActionContext context, User pertainsTo, OpportunityComponentList oppList, UserList usersToGraph) {
    java.util.Date myDate = null;
    Calendar readDate = Calendar.getInstance();
    Calendar readDateAdjusted = Calendar.getInstance();

    int x = 0;

    Double ramrAddTerm = new Double(0.0);
    Double gmrAddTerm = new Double(0.0);
    Double cgmrAddTerm = new Double(0.0);
    Double cramrAddTerm = new Double(0.0);

    String valKey = "";

    Calendar rightNow = Calendar.getInstance();
    rightNow.set(Calendar.DAY_OF_MONTH, 1);

    Calendar rightNowAdjusted = Calendar.getInstance();
    rightNowAdjusted.set(Calendar.DAY_OF_MONTH, 1);
    rightNowAdjusted.add(Calendar.DATE, -1);

    //twelve months
    Calendar twelveMonths = Calendar.getInstance();
    twelveMonths.set(Calendar.DAY_OF_MONTH, 1);
    twelveMonths.add(Calendar.MONTH, +13);

    if (!pertainsTo.getIsValid()) {
      pertainsTo.doOpportunityLock();
      if (!pertainsTo.getIsValid()) {
        try {
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "Leads-> (RE)BUILDING DATA FOR " + pertainsTo.getId());
          }
          pertainsTo.setGmr(new GraphSummaryList());
          pertainsTo.setRamr(new GraphSummaryList());
          pertainsTo.setCgmr(new GraphSummaryList());
          pertainsTo.setCramr(new GraphSummaryList());

          Iterator oppIterator = oppList.iterator();
          while (oppIterator.hasNext()) {
            OpportunityComponent tempOpp = (OpportunityComponent) oppIterator.next();
            if (tempOpp.getOwner() == pertainsTo.getId()) {
              myDate = tempOpp.getCloseDate();
              readDate.setTime(myDate);
              readDateAdjusted.setTime(myDate);
              if (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null && getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null) {
                readDateAdjusted.add(
                    java.util.Calendar.MONTH, +(int) (java.lang.Math.round(
                    tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM")))));
              } else {
                readDateAdjusted.add(java.util.Calendar.MONTH, +(int) (java.lang.Math.round(tempOpp.getTermsInMonths())));
              }
              if (readDate.get(Calendar.DATE) >= 15) {
                readDate.add(java.util.Calendar.MONTH, +1);
              }

              valKey = String.valueOf(readDate.get(java.util.Calendar.YEAR)) +
                  String.valueOf(readDate.get(java.util.Calendar.MONTH));

              //get the individual graph values
              if (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null && getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null && getPref(context, "OPPORTUNITY.MULTIPLIER") != null) {
                gmrAddTerm = new Double(
                    (tempOpp.getGuess(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.MULTIPLIER")) / tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM"))));
              } else {
                gmrAddTerm = new Double(
                    (tempOpp.getGuess() / tempOpp.getTermsInMonths()));
              }
              if (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null && getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null && getPref(context, "OPPORTUNITY.MULTIPLIER") != null) {
                ramrAddTerm = new Double(
                    (tempOpp.getGuess(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.MULTIPLIER")) / tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM"))) * tempOpp.getCloseProb());
              } else {
                ramrAddTerm = new Double(
                    (tempOpp.getGuess() / tempOpp.getTermsInMonths()) * tempOpp.getCloseProb());
              }
              if (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null && getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null && getPref(context, "OPPORTUNITY.MULTIPLIER") != null) {
                cgmrAddTerm = new Double(
                    (tempOpp.getGuess(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.MULTIPLIER")) / tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM"))) * tempOpp.getCommission());
              } else {
                cgmrAddTerm = new Double(
                    (tempOpp.getGuess() / tempOpp.getTermsInMonths()) * tempOpp.getCommission());
              }
              if (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null && getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null && getPref(context, "OPPORTUNITY.MULTIPLIER") != null) {
                cramrAddTerm = new Double(
                    ((tempOpp.getGuess(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.MULTIPLIER")) / tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM"))) * tempOpp.getCloseProb() * tempOpp.getCommission()));
              } else {
                cramrAddTerm = new Double(
                    ((tempOpp.getGuess() / tempOpp.getTermsInMonths()) * tempOpp.getCloseProb() * tempOpp.getCommission()));
              }

              //case: close date within 0-6m range
              if (((rightNow.before(readDate) || rightNowAdjusted.before(
                  readDate)) && twelveMonths.after(readDate)) || rightNow.equals(
                  readDate) || twelveMonths.equals(readDate)) {
                pertainsTo.setGraphValues(
                    valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
              }
              //case: close date plus terms within 0-6m range, or greater than 6m
              else if (rightNowAdjusted.after(readDate) && ((rightNow.before(
                  readDateAdjusted) || rightNowAdjusted.before(
                  readDateAdjusted)))) {
                pertainsTo.setGraphValues(
                    valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
              }

              //more terms
              if ((java.lang.Math.round(
                  (getPref(context, "OPPORTUNITY.DEFAULT_UNITS") != null && getPref(context, "OPPORTUNITY.DEFAULT_TERM") != null ?
                      tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM")) :
                      tempOpp.getTermsInMonths())
              )) > 1) {
                for (x = 1; x < java.lang.Math.round(
                    tempOpp.getTermsInMonths(getPref(context, "OPPORTUNITY.DEFAULT_UNITS"), getPref(context, "OPPORTUNITY.DEFAULT_TERM"))); x++) {
                  readDate.add(java.util.Calendar.MONTH, +1);
                  if (((rightNow.before(readDate) || rightNowAdjusted.before(
                      readDate)) && twelveMonths.after(readDate)) || rightNow.equals(
                      readDate) || twelveMonths.equals(readDate)) {
                    valKey = String.valueOf(
                        readDate.get(java.util.Calendar.YEAR)) +
                        String.valueOf(readDate.get(java.util.Calendar.MONTH));
                    pertainsTo.setGraphValues(
                        valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
                  }
                }
              }
            }
          }
          pertainsTo.setIsValid(true, true);
        } catch (Exception e) {
          System.err.println(
              "Leads-> Unwanted exception occurred: " + e.toString());
        }
      }
      pertainsTo.doOpportunityUnlock();
    }
    usersToGraph.add(pertainsTo);
    if (oppList.size() == 0) {
      return new UserList();
    } else {
      return usersToGraph;
    }
  }


  /**
   * This method takes a userlist, then for each user the specified graph data
   * is pulled out by date range and put in an XYSeries, and then the XYSeries
   * is added to an XYSeriesCollection.
   *
   * @param linesToDraw Description of Parameter
   * @param whichGraph  Description of Parameter
   * @return Description of the Returned Value
   */
  private XYSeriesCollection createCategoryDataset(UserList linesToDraw, String whichGraph) {
    XYSeriesCollection xyDataset = new XYSeriesCollection();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Leads-> Lines to draw: " + linesToDraw.size());
    }
    if (linesToDraw.size() == 0) {
      return xyDataset;
    }
    Iterator users = linesToDraw.iterator();
    while (users.hasNext()) {
      User thisUser = (User) users.next();
      XYSeries dataSeries = new XYSeries("");
      String[] valKeys = thisUser.getGmr().getRange(12);
      Calendar iteratorDate = Calendar.getInstance();
      for (int count = 0; count < 12; count++) {
        java.util.Date dateValue = createDate(
            iteratorDate.get(Calendar.YEAR), iteratorDate.get(Calendar.MONTH), 1);
        Double itemValue = new Double(0);
        if (whichGraph.equals("gmr")) {
          itemValue = thisUser.getGmr().getValue(valKeys[count]);
        } else if (whichGraph.equals("ramr")) {
          itemValue = thisUser.getRamr().getValue(valKeys[count]);
        } else if (whichGraph.equals("cgmr")) {
          itemValue = thisUser.getCgmr().getValue(valKeys[count]);
        } else if (whichGraph.equals("cramr")) {
          itemValue = thisUser.getCramr().getValue(valKeys[count]);
        }
        iteratorDate.add(Calendar.MONTH, +1);
        dataSeries.add(dateValue.getTime(), itemValue);
      }
      xyDataset.addSeries(dataSeries);
    }
    return xyDataset;
  }


  /**
   * Create the x and y data for a chart for a single user, on top of another
   * list of user's data.. Each year and month combo for twelve months from the
   * beginning of this month are stored under a new single User object (hmph)
   * for drawing a line on the graph to show cumulative data.
   *
   * @param primaryNode  Description of Parameter
   * @param currentLines Description of Parameter
   * @return Description of the Returned Value
   */
  private UserList calculateLine(User primaryNode, UserList currentLines) {
    if (currentLines.size() == 0) {
      currentLines.add(primaryNode);
      return currentLines;
    }
    User thisLine = new User();
    String[] valKeys = thisLine.getGmr().getRange(12);
    Iterator x = currentLines.iterator();
    User addToMe = (User) x.next();
    for (int count = 0; count < 12; count++) {
      thisLine.getGmr().setValue(
          valKeys[count], new Double(
          primaryNode.getGmr().getValue(valKeys[count]).doubleValue() + (addToMe.getGmr().getValue(
              valKeys[count])).doubleValue()));
      thisLine.getRamr().setValue(
          valKeys[count], new Double(
          primaryNode.getRamr().getValue(valKeys[count]).doubleValue() + (addToMe.getRamr().getValue(
              valKeys[count])).doubleValue()));
      thisLine.getCgmr().setValue(
          valKeys[count], new Double(
          primaryNode.getCgmr().getValue(valKeys[count]).doubleValue() + (addToMe.getCgmr().getValue(
              valKeys[count])).doubleValue()));
      thisLine.getCramr().setValue(
          valKeys[count], new Double(
          primaryNode.getCramr().getValue(valKeys[count]).doubleValue() + (addToMe.getCramr().getValue(
              valKeys[count])).doubleValue()));
    }
    currentLines.add(thisLine);
    return currentLines;
  }


  /**
   * Create the x and y data for a chart for a list of users. Each year and
   * month combo for twelve months from the beginning of this month are added
   * together for each user and stored under a new single User object (hmph)
   * for drawing a line on the graph.
   *
   * @param toRollUp     Description of Parameter
   * @param currentLines Description of Parameter
   * @return Description of the Returned Value
   */
  private UserList calculateLine(UserList toRollUp, UserList currentLines) {
    if (toRollUp.size() == 0) {
      return new UserList();
    }
    User thisLine = new User();
    String[] valKeys = thisLine.getGmr().getRange(12);
    Iterator x = toRollUp.iterator();
    while (x.hasNext()) {
      User thisUser = (User) x.next();
      for (int count = 0; count < 12; count++) {
        thisLine.getGmr().setValue(
            valKeys[count], thisUser.getGmr().getValue(valKeys[count]));
        thisLine.getRamr().setValue(
            valKeys[count], thisUser.getRamr().getValue(valKeys[count]));
        thisLine.getCgmr().setValue(
            valKeys[count], thisUser.getCgmr().getValue(valKeys[count]));
        thisLine.getCramr().setValue(
            valKeys[count], thisUser.getCramr().getValue(valKeys[count]));
      }
    }
    currentLines.add(thisLine);
    return currentLines;
  }


  /**
   * Returns an access type list for repopulating a html select
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAccessTypeJSList(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccessTypeJSList");
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean allowMultiple(ActionContext context) {
    //get the preference for single/multiple components
    String multiple = this.getSystemPref(context, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
    return OpportunityComponent.allowMultiple(multiple);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean excludeHierarchy(ActionContext context) {
    //get the preference for single/multiple components
    String exclude = this.getSystemPref(context, OpportunityComponent.MULTPLE_CONFIG_NAME, "excludeHierarchy");
    return DatabaseUtils.parseBoolean(exclude);
  }

}
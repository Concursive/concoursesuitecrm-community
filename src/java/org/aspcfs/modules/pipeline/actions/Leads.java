package org.aspcfs.modules.pipeline.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.contacts.base.Contact;
import java.io.*;
import java.util.*;
import com.jrefinery.chart.*;
import com.jrefinery.chart.data.*;
import com.jrefinery.chart.ui.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.entity.StandardEntityCollection;
import com.jrefinery.chart.tooltips.TimeSeriesToolTipGenerator;
import java.awt.Color;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;

/**
 *  Core actions for Pipeline Management module
 *
 *@author     chris
 *@created    October 17, 2001
 *@version    $Id$
 */
public final class Leads extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  y  Description of Parameter
   *@param  m  Description of Parameter
   *@param  d  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static java.util.Date createDate(int y, int m, int d) {
    GregorianCalendar calendar = new GregorianCalendar(y, m, d, 0, 0, 0);
    return calendar.getTime();
  }


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
  public String executeCommandPrepare(ActionContext context) {
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Generate user list
    User thisRec = this.getUser(context, userId);
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(userId);
    userList.setMyValue(thisRec.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
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
      return "PrepareOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveComponent(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    int resultCount = 0;
    OpportunityHeader header = null;
    String componentId = context.getRequest().getParameter("id");
    String permission = "pipeline-opportunities-add";

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
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
      if (newComponent.getId() > 0) {
        OpportunityComponent oldComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
        if (!hasViewpointAuthority(db, context, "pipeline", oldComponent.getOwner(), userId)) {
          return "PermissionError";
        }
        newComponent.setModifiedBy(getUserId(context));
        resultCount = newComponent.update(db, context);
        if (resultCount == 1) {
          newComponent.queryRecord(db, newComponent.getId());
        }
      } else {
        recordInserted = newComponent.insert(db, context);
      }
      if (recordInserted) {
        addRecentItem(context, newComponent);
      } else if ("modify".equals(action) && resultCount == -1) {
        UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
        User thisRec = thisUser.getUserRecord();
        UserList shortChildList = thisRec.getShortChildList();
        UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
        userList.setMyId(getUserId(context));
        userList.setMyValue(thisUser.getContact().getNameLastFirst());
        userList.setIncludeMe(true);
        userList.setExcludeDisabledIfUnselected(true);
        context.getRequest().setAttribute("UserList", userList);
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newComponent.getTypeList());
      } else if ("insert".equals(action) && !recordInserted) {
        processErrors(context, newComponent.getErrors());
        //rebuild the form
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newComponent.getTypeList());
      }

      context.getRequest().setAttribute("ComponentDetails", newComponent);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if ("insert".equals(action)) {
        if (recordInserted) {
          return (executeCommandDetailsOpp(context));
        }
        return (executeCommandPrepare(context));
      } else {
        if (resultCount == -1) {
          processErrors(context, newComponent.getErrors());
          return (executeCommandPrepare(context));
        } else if (resultCount == 1) {
          if ("list".equals(context.getRequest().getParameter("return"))) {
            return (executeCommandSearch(context));
          } else if ("details".equals(context.getRequest().getParameter("return"))) {
            return (executeCommandDetailsComponent(context));
          } else if (context.getRequest().getParameter("popup") != null) {
            return "PopupCloseOK";
          }
          return ("DetailsComponentOK");
        }
      }
    }
    context.getRequest().setAttribute("Error", errorMessage);
    return ("SystemError");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    String association = context.getRequest().getParameter("opp_type");
    Contact linkedContact = null;

    //set types
    newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    if (newOpp.getHeader().getAccountLink() > -1) {
      newOpp.getHeader().setContactLink("-1");
    } else if (newOpp.getHeader().getContactLink() > -1) {
      newOpp.getHeader().setAccountLink("-1");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      //set the name for displaying on the form in case there are any errors
      if (newOpp.getHeader().getContactLink() > -1) {
        linkedContact = new Contact(db, newOpp.getHeader().getContactLink());
        newOpp.getHeader().setContactName(linkedContact.getNameLastFirst());
      }
      recordInserted = newOpp.insert(db, context);
      if (!recordInserted) {
        processErrors(context, newOpp.getHeader().getErrors());
        processErrors(context, newOpp.getComponent().getErrors());
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newOpp.getComponent().getTypeList());
        if (newOpp.getHeader().getAccountLink() > -1) {
          Organization thisOrg = new Organization(db, newOpp.getHeader().getAccountLink());
          newOpp.getHeader().setAccountName(thisOrg.getName());
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted) {
        addRecentItem(context, newOpp.getHeader());
        context.getRequest().setAttribute("headerId", String.valueOf(newOpp.getHeader().getId()));
        return (executeCommandDetailsOpp(context));
      } else {
        return (executeCommandPrepare(context));
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
  public String executeCommandDetailsOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    int headerId = -1;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;
    addModuleBean(context, "View Opportunities", "View Opportunity Details");
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Check parameters
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt((String) context.getRequest().getAttribute("headerId"));
    }
    Connection db = null;
    //Configure the paged list info
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("LeadsComponentListInfo");
    }
    PagedListInfo componentListInfo = this.getPagedListInfo(context, "LeadsComponentListInfo");
    componentListInfo.setLink("Leads.do?command=DetailsOpp&headerId=" + headerId + HTTPUtils.addLinkParams(context.getRequest(), "viewSource"));
    try {
      db = this.getConnection(context);
      //Generate the opportunity header
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      //Generate the list of components
      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context, userId));
      componentList.setHeaderId(thisHeader.getId());
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);
      addRecentItem(context, thisHeader);
      return ("OppDetailsOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    int errorCode = 0;
    Exception errorMessage = null;
    PagedListInfo searchOppListInfo = this.getPagedListInfo(context, "SearchOppListInfo");
    //Prepare viewpoints
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int vpUserId = viewpointInfo.getVpUserId(this.getUserId(context));
    int userId = this.getUserId(context);
    if (vpUserId != -1 && vpUserId != userId) {
      userId = vpUserId;
    }
    
    try {
      db = this.getConnection(context);

      //Opportunity types drop-down menu
      LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
      typeSelect.addItem(0, "All Types");
      context.getRequest().setAttribute("TypeSelect", typeSelect);

      //Generate user list
      User thisRec = this.getUser(context, userId);
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
      userList.setMyId(userId);
      userList.setMyValue(thisRec.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      context.getRequest().setAttribute("UserList", userList);

      //check if account/contact is already selected, if so build it
      if (!"".equals(searchOppListInfo.getSearchOptionValue("searchcodeOrgId")) && !"-1".equals(searchOppListInfo.getSearchOptionValue("searchcodeOrgId"))) {
        String orgId = searchOppListInfo.getSearchOptionValue("searchcodeOrgId");
        Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrg);
      }

      if (!"".equals(searchOppListInfo.getSearchOptionValue("searchcodeContactId")) && !"-1".equals(searchOppListInfo.getSearchOptionValue("searchcodeContactId"))) {
        String id = searchOppListInfo.getSearchOptionValue("searchcodeContactId");
        Contact thisContact = new Contact(db, Integer.parseInt(id));
        context.getRequest().setAttribute("ContactDetails", thisContact);
      }

      //stage
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);
    } catch (Exception e) {
      errorCode = 1;
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorCode == 0) {
      addModuleBean(context, "Search Opportunities", "Search Opportunities");
      return ("SearchOK");
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
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
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
      thisOpp = new OpportunityHeader(db, id);
      DependencyList dependencies = thisOpp.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      htmlDialog.setTitle("Dark Horse CRM: Confirm Delete");
      htmlDialog.setHeader("This object has the following dependencies within Dark Horse CRM:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='Leads.do?command=DeleteOpp&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "viewSource") + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action method to generate dashboard graphs, opportunities, and hierarchy
   *  gross pipeline.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "pipeline-dashboard-view")) {
      if (!hasPermission(context, "pipeline-opportunities-view")) {
        return ("PermissionError");
      }
      return (executeCommandSearch(context));
    }
    addModuleBean(context, "Dashboard", "Dashboard");
    String errorMessage = null;

    //Prepare the user id to base all data on
    int idToUse = 0;

    //Check to see if a Viewpoint is selected else default to user who's logged in
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    User thisRec = null;

    //Check if a specific user was selected
    int overrideId = StringUtils.parseInt(context.getRequest().getParameter("oid"), -1);

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
        overrideId = StringUtils.parseInt((String) context.getSession().getAttribute("leadsoverride"), -1);
      } else {
        overrideId = userId;
      }
    }
    String checkFileName = null;
    Connection db = null;

    UserList fullChildList = new UserList();
    UserList shortChildList = new UserList();
    OpportunityList realFullOppList = new OpportunityList();
    HtmlSelect graphTypeSelect = new HtmlSelect();

    //Determine the graph type to generate
    String graphString = null;
    if (context.getRequest().getParameter("whichGraph") != null) {
      graphString = context.getRequest().getParameter("whichGraph");
      context.getSession().setAttribute("pipelineGraph", graphString);
    } else if ((String) context.getRequest().getSession().getAttribute("pipelineGraph") != null) {
      graphString = (String) context.getRequest().getSession().getAttribute("pipelineGraph");
    } else {
      graphString = "gmr";
    }

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

      //Track the id in the request and the session
      if (context.getRequest().getParameter("oid") != null && !"true".equals((String) context.getRequest().getParameter("reset"))) {
        context.getRequest().setAttribute("override", String.valueOf(idToUse));
        context.getRequest().setAttribute("othername", thisRec.getContact().getNameFull());
        context.getRequest().setAttribute("previousId", String.valueOf(thisRec.getManagerId()));
        context.getSession().setAttribute("leadsoverride", String.valueOf(overrideId));
        context.getSession().setAttribute("leadsothername", thisRec.getContact().getNameFull());
        context.getSession().setAttribute("leadspreviousId", String.valueOf(thisRec.getManagerId()));
      }

      //Check the cache and see if the current graph exists and is valid
      if (thisRec.getIsValid() == true) {
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

      //Build the html graph combo box
      graphTypeSelect.setSelectName("whichGraph");
      graphTypeSelect.setJsEvent("onChange=\"document.forms[0].submit();\"");
      graphTypeSelect.addItem("gmr", "Gross Monthly Revenue");
      graphTypeSelect.addItem("ramr", "Risk Adjusted Monthly Revenue");
      graphTypeSelect.addItem("cgmr", "Commission Gross Monthly Revenue");
      graphTypeSelect.addItem("cramr", "Commission Risk Adj. Monthly Revenue");
      graphTypeSelect.setDefaultKey(graphString);

      //Generate the opportunities pagedList for the idToUse (builds component count and value of idToUse only), right of graph
      PagedListInfo dashboardListInfo = this.getPagedListInfo(context, "DashboardListInfo");
      dashboardListInfo.setLink("Leads.do?command=Dashboard");
      dashboardListInfo.setColumnToSortBy("x.description");
      OpportunityHeaderList headerList = new OpportunityHeaderList();
      headerList.setPagedListInfo(dashboardListInfo);
      headerList.setOwner(idToUse);
      headerList.setQueryOpenOnly(true);
      headerList.setComponentsOwnedByUser(idToUse);
      headerList.setBuildTotalValues(true);
      headerList.buildList(db);
      context.getRequest().setAttribute("oppList", headerList);

      //FullChildList is the complete user hierarchy for the selected user and
      //is needed for the graph
      if (checkFileName == null) {
        fullChildList = thisRec.getFullChildList(shortChildList, new UserList());
        String range = fullChildList.getUserListIds(idToUse);
        //All of the opportunities that make up this graph calculation
        //TODO: Set a max date for less records
        realFullOppList.setUnits("M");
        realFullOppList.setOwnerIdRange(range);
        realFullOppList.setExcludeClosedComponents(true);
        realFullOppList.buildList(db);
      }

      //ShortChildList is used for showing user list, under graph
      shortChildList.buildPipelineValues(db);
    } catch (Exception e) {
      errorMessage = e.toString();
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

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
      OpportunityList tempOppList = new OpportunityList();
      Iterator z = realFullOppList.iterator();
      while (z.hasNext()) {
        OpportunityBean tempOpp = (OpportunityBean) (z.next());
        if (tempOpp.getComponent().getOwner() == idToUse) {
          tempOppList.add(tempOpp);
        }
      }
      //add up all opportunities for children line on graph
      UserList tempUserList = new UserList();
      Iterator n = fullChildList.iterator();
      while (n.hasNext()) {
        User thisRecord = (User) n.next();
        tempUserList = prepareLines(thisRecord, realFullOppList, tempUserList);
      }
      UserList linesToDraw = new UserList();
      linesToDraw = calculateLine(tempUserList, linesToDraw);
      //set my own, on top of the children line
      tempUserList = prepareLines(thisRec, tempOppList, tempUserList);
      linesToDraw = calculateLine(thisRec, linesToDraw);
      //Store the data in the collection
      XYSeriesCollection categoryData = createCategoryDataset(linesToDraw, graphString);
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
      //TextTitle title = (TextTitle) chart.getTitle(0);
      //title.setText("Gross Monthly Revenue");
      chart.setBackgroundPaint(Color.white);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Leads-> Drawing the chart");
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
      } catch (IOException e) {
        e.printStackTrace(System.out);
      }
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ShortChildList", shortChildList);
      context.getRequest().setAttribute("GraphTypeList", graphTypeSelect);
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }

    //get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityHeader newOpp = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      newOpp = new OpportunityHeader(db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", newOpp.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      recordDeleted = newOpp.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Opportunities", "Delete an opportunity");
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "Leads.do?command=ViewOpp");
        deleteRecentItem(context, newOpp);
        return ("OppDeleteOK");
      } else {
        processErrors(context, newOpp.getErrors());
        return (executeCommandSearch(context));
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
  public String executeCommandDeleteComponent(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-delete")) {
      return ("PermissionError");
    }

    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", component.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, component);
        if (context.getRequest().getParameter("return") != null) {
          if (context.getRequest().getParameter("return").equals("list")) {
            context.getRequest().setAttribute("refreshUrl", "Leads.do?command=ViewOpp");
            return ("ComponentDeleteOK");
          }
        }
        context.getRequest().setAttribute("refreshUrl", "Leads.do?command=DetailsOpp&headerId=" + component.getHeaderId());
        return ("ComponentDeleteOK");
      } else {
        processErrors(context, component.getErrors());
        return (executeCommandSearch(context));
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
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, id);
      htmlDialog.setTitle("Dark Horse CRM: Pipeline Management");
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl("javascript:window.location.href='LeadsComponents.do?command=DeleteComponent&id=" + id + "&return=" + context.getRequest().getParameter("return") + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
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
  public String executeCommandModifyComponent(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Prepare the action items
    Exception errorMessage = null;
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
      if (!hasViewpointAuthority(db, context, "pipeline", component.getOwner(), userId)) {
        return "PermissionError";
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("ComponentDetails", component);
      addRecentItem(context, component);

      //Generate user list
      User thisRec = this.getUser(context, userId);
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
      userList.setMyId(userId);
      userList.setMyValue(thisRec.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      context.getRequest().setAttribute("UserList", userList);

      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupModifyOK");
      } else {
        return executeCommandPrepare(context);
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
  public String executeCommandModifyOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Opportunities", "Modify Opportunity");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    //Parameters
    int headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    try {
      db = this.getConnection(context);
      //Generate the header
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      addRecentItem(context, thisHeader);
      return ("ModifyOppOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Search Opportunities
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }

    //Prepare the paged list
    PagedListInfo searchOppListInfo = this.getPagedListInfo(context, "SearchOppListInfo");

    //Prepare viewpoints
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int vpUserId = viewpointInfo.getVpUserId(this.getUserId(context));
    int userId = this.getUserId(context);
    if (vpUserId != -1 && vpUserId != userId) {
      userId = vpUserId;
    }
    Connection db = null;
    OpportunityList oppList = new OpportunityList();
    try {
      db = this.getConnection(context);
      //Opportunity types drop-down menu
      LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
      typeSelect.addItem(0, "All Types");
      context.getRequest().setAttribute("TypeSelect", typeSelect);
      //The list of opportunities, according to drop-down filter
      oppList.setPagedListInfo(searchOppListInfo);
      searchOppListInfo.setSearchCriteria(oppList);
      if ("all".equals(searchOppListInfo.getListView())) {
        if (searchOppListInfo.getFilterKey("listFilter2") != -1) {
          oppList.setOwner(searchOppListInfo.getFilterKey("listFilter2"));
        } else {
          oppList.setOwnerIdRange(this.getUserRange(context, userId));
        }
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(searchOppListInfo.getListView())) {
        if (searchOppListInfo.getFilterKey("listFilter2") != -1) {
          oppList.setOwner(searchOppListInfo.getFilterKey("listFilter2"));
        } else {
          oppList.setOwnerIdRange(this.getUserRange(context, userId));
        }
        oppList.setQueryClosedOnly(true);
      } else {
        oppList.setOwner(userId);
        oppList.setQueryOpenOnly(true);
      }
      oppList.setTypeId(searchOppListInfo.getFilterKey("listFilter1"));
      oppList.buildList(db);
      context.getRequest().setAttribute("OpportunityList", oppList);

      //Generate user list
      User thisRec = this.getUser(context, userId);
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
      userList.setMyId(userId);
      userList.setMyValue(thisRec.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      context.getRequest().setAttribute("UserList", userList);
      addModuleBean(context, "View Opportunities", "Opportunities Add");
      return ("OppListOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandUpdateOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    String headerId = context.getRequest().getParameter("headerId");

    try {
      db = this.getConnection(context);
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      if (!hasViewpointAuthority(db, context, "pipeline", oppHeader.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      resultCount = oppHeader.update(db);
      if (resultCount == -1) {
        processErrors(context, oppHeader.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return executeCommandModifyOpp(context);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandSearch(context));
        } else {
          return (executeCommandDetailsOpp(context));
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    //Get viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Configure the action
    Exception errorMessage = null;
    addModuleBean(context, "View Opportunities", "Component Details");
    String componentId = context.getRequest().getParameter("id");
    OpportunityComponent thisComponent = null;
    //Begin processing
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      if (!hasViewpointAuthority(db, context, "pipeline", thisComponent.getOwner(), userId)) {
        return "PermissionError";
      }
      thisComponent.checkEnabledOwnerAccount(db);
      //Build the container item
      OpportunityHeader oppHeader = new OpportunityHeader(db, thisComponent.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("LeadsComponentDetails", thisComponent);
      addRecentItem(context, thisComponent);
      return ("DetailsComponentOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pertainsTo    Description of Parameter
   *@param  oppList       Description of Parameter
   *@param  usersToGraph  Description of Parameter
   *@return               Description of the Returned Value
   */
  private UserList prepareLines(User pertainsTo, OpportunityList oppList, UserList usersToGraph) {
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

    if (pertainsTo.getIsValid() == false) {
      pertainsTo.doOpportunityLock();
      if (pertainsTo.getIsValid() == false) {
        try {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Leads-> (RE)BUILDING DATA FOR " + pertainsTo.getId());
          }
          pertainsTo.setGmr(new GraphSummaryList());
          pertainsTo.setRamr(new GraphSummaryList());
          pertainsTo.setCgmr(new GraphSummaryList());
          pertainsTo.setCramr(new GraphSummaryList());

          Iterator oppIterator = oppList.iterator();
          while (oppIterator.hasNext()) {
            OpportunityBean tempOpp = (OpportunityBean) oppIterator.next();
            if (tempOpp.getComponent().getOwner() == pertainsTo.getId()) {
              myDate = tempOpp.getComponent().getCloseDate();
              readDate.setTime(myDate);
              readDateAdjusted.setTime(myDate);
              readDateAdjusted.add(java.util.Calendar.MONTH, +(int) (java.lang.Math.round(tempOpp.getComponent().getTerms())));
              if (readDate.get(Calendar.DATE) >= 15) {
                readDate.add(java.util.Calendar.MONTH, +1);
              }

              valKey = String.valueOf(readDate.get(java.util.Calendar.YEAR)) +
                  String.valueOf(readDate.get(java.util.Calendar.MONTH));

              //get the individual graph values
              gmrAddTerm = new Double((tempOpp.getComponent().getGuess() / tempOpp.getComponent().getTerms()));
              ramrAddTerm = new Double((tempOpp.getComponent().getGuess() / tempOpp.getComponent().getTerms()) * tempOpp.getComponent().getCloseProb());
              cgmrAddTerm = new Double((tempOpp.getComponent().getGuess() / tempOpp.getComponent().getTerms()) * tempOpp.getComponent().getCommission());
              cramrAddTerm = new Double(((tempOpp.getComponent().getGuess() / tempOpp.getComponent().getTerms()) * tempOpp.getComponent().getCloseProb() * tempOpp.getComponent().getCommission()));

              //case: close date within 0-6m range
              if (((rightNow.before(readDate) || rightNowAdjusted.before(readDate)) && twelveMonths.after(readDate)) || rightNow.equals(readDate) || twelveMonths.equals(readDate)) {
                pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
              }
              //case: close date plus terms within 0-6m range, or greater than 6m
              else if (rightNowAdjusted.after(readDate) && ((rightNow.before(readDateAdjusted) || rightNowAdjusted.before(readDateAdjusted)))) {
                pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
              }

              //more terms
              if ((java.lang.Math.round(tempOpp.getComponent().getTerms())) > 1) {
                for (x = 1; x < java.lang.Math.round(tempOpp.getComponent().getTerms()); x++) {
                  readDate.add(java.util.Calendar.MONTH, +1);
                  if (((rightNow.before(readDate) || rightNowAdjusted.before(readDate)) && twelveMonths.after(readDate)) || rightNow.equals(readDate) || twelveMonths.equals(readDate)) {
                    valKey = String.valueOf(readDate.get(java.util.Calendar.YEAR)) +
                        String.valueOf(readDate.get(java.util.Calendar.MONTH));
                    pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
                  }
                }
              }
            }
          }
          pertainsTo.setIsValid(true, true);
        } catch (Exception e) {
          System.err.println("Leads-> Unwanted exception occurred: " + e.toString());
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
      System.out.println("Leads-> Lines to draw: " + linesToDraw.size());
    }
    if (linesToDraw.size() == 0) {
      return xyDataset;
    }
    Iterator users = linesToDraw.iterator();
    while (users.hasNext()) {
      User thisUser = (User) users.next();
      XYSeries dataSeries = new XYSeries(null);
      String[] valKeys = thisUser.getGmr().getRange(12);
      Calendar iteratorDate = Calendar.getInstance();
      for (int count = 0; count < 12; count++) {
        java.util.Date dateValue = createDate(iteratorDate.get(Calendar.YEAR), iteratorDate.get(Calendar.MONTH), 1);
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
   *  Create the x and y data for a chart for a single user, on top of another
   *  list of user's data.. Each year and month combo for twelve months from the
   *  beginning of this month are stored under a new single User object (hmph)
   *  for drawing a line on the graph to show cumulative data.
   *
   *@param  primaryNode   Description of Parameter
   *@param  currentLines  Description of Parameter
   *@return               Description of the Returned Value
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
      thisLine.getGmr().setValue(valKeys[count], new Double(primaryNode.getGmr().getValue(valKeys[count]).doubleValue() + (addToMe.getGmr().getValue(valKeys[count])).doubleValue()));
      thisLine.getRamr().setValue(valKeys[count], new Double(primaryNode.getRamr().getValue(valKeys[count]).doubleValue() + (addToMe.getRamr().getValue(valKeys[count])).doubleValue()));
      thisLine.getCgmr().setValue(valKeys[count], new Double(primaryNode.getCgmr().getValue(valKeys[count]).doubleValue() + (addToMe.getCgmr().getValue(valKeys[count])).doubleValue()));
      thisLine.getCramr().setValue(valKeys[count], new Double(primaryNode.getCramr().getValue(valKeys[count]).doubleValue() + (addToMe.getCramr().getValue(valKeys[count])).doubleValue()));
    }
    currentLines.add(thisLine);
    return currentLines;
  }


  /**
   *  Create the x and y data for a chart for a list of users. Each year and
   *  month combo for twelve months from the beginning of this month are added
   *  together for each user and stored under a new single User object (hmph)
   *  for drawing a line on the graph.
   *
   *@param  toRollUp      Description of Parameter
   *@param  currentLines  Description of Parameter
   *@return               Description of the Returned Value
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
        thisLine.getGmr().setValue(valKeys[count], thisUser.getGmr().getValue(valKeys[count]));
        thisLine.getRamr().setValue(valKeys[count], thisUser.getRamr().getValue(valKeys[count]));
        thisLine.getCgmr().setValue(valKeys[count], thisUser.getCgmr().getValue(valKeys[count]));
        thisLine.getCramr().setValue(valKeys[count], thisUser.getCramr().getValue(valKeys[count]));
      }
    }
    currentLines.add(thisLine);
    return currentLines;
  }

}


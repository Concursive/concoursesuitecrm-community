package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.text.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import com.sun.image.codec.jpeg.*;

import com.jrefinery.chart.*;
import com.jrefinery.chart.data.*;
import com.jrefinery.chart.ui.*;
import com.jrefinery.data.*;

import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;

/**
 *  Description of the Class
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
  public String executeCommandAddOpp(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    OrganizationList orgList = new OrganizationList();
    Connection db = null;

    try {
      db = this.getConnection(context);
      buildFormElements(db, context);
      orgList.setMinerOnly(false);
      orgList.buildList(db);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OrgList", orgList);
      addModuleBean(context, "Add Opportunity", "Add Opportunity");
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
  public String executeCommandInsertOppComponent(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;

    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();

    //set types
    newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newComponent.setOwner(getUserId(context));
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      recordInserted = newComponent.insert(db, context);

      if (recordInserted) {
        context.getRequest().setAttribute("LeadsComponentDetails", newComponent);
        addRecentItem(context, newComponent);
      } else {
        processErrors(context, newComponent.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return (executeCommandDetailsOpp(context));
      } else {
        return (executeCommandAddOppComponent(context));
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
  public String executeCommandInsertOpp(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    Contact linkedContact = null;
    OpportunityHeader resultHeader = null;
    OpportunityComponentList componentList = null;
    Connection db = null;

    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    String association = context.getRequest().getParameter("opp_type");

    //set types
    newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.getComponent().setOwner(getUserId(context));
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    if (newOpp.getHeader().getAccountLink() > -1) {
      newOpp.getHeader().setContactLink("-1");
    } else if (newOpp.getHeader().getContactLink() > -1) {
      newOpp.getHeader().setAccountLink("-1");
    }

    try {
      db = this.getConnection(context);

      //set the name for displaying on the form in case there are any errors
      if (newOpp.getHeader().getContactLink() > -1) {
        linkedContact = new Contact(db, newOpp.getHeader().getContactLink());
        newOpp.getHeader().setContactName(linkedContact.getNameLastFirst());
      }

      recordInserted = newOpp.insert(db, context);
      if (recordInserted) {
        resultHeader = new OpportunityHeader(db, newOpp.getHeader().getOppId());
        context.getRequest().setAttribute("HeaderDetails", resultHeader);

        PagedListInfo componentListInfo = this.getPagedListInfo(context, "LeadsComponentListInfo");
        componentListInfo.setLink("Opportunities.do?command=DetailsOpp&oppId=" + resultHeader.getOppId());
        componentList = new OpportunityComponentList();
        componentList.setPagedListInfo(componentListInfo);
        componentList.setOppId(resultHeader.getOppId());
        componentList.buildList(db);
        context.getRequest().setAttribute("ComponentList", componentList);
      } else {
        processErrors(context, newOpp.getHeader().getErrors());
        processErrors(context, newOpp.getComponent().getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Add Opportunity", "Add Opportunity");
    if (errorMessage == null) {
      if (recordInserted) {
        addRecentItem(context, resultHeader);
        return ("OppDetailsOK");
      } else {
        return (executeCommandAddOpp(context));
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
    if (!(hasPermission(context, "pipeline-opportunities-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Opportunities", "View Opportunity Details");
    Exception errorMessage = null;
    int oppId = -1;
    Connection db = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;

    if (context.getRequest().getParameter("oppId") != null) {
      oppId = Integer.parseInt(context.getRequest().getParameter("oppId"));
    } else if (context.getRequest().getParameter("id") != null) {
      oppId = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    PagedListInfo componentListInfo = this.getPagedListInfo(context, "LeadsComponentListInfo");
    componentListInfo.setLink("Leads.do?command=DetailsOpp&oppId=" + oppId);
    try {
      db = this.getConnection(context);
      thisHeader = new OpportunityHeader(db, oppId);
      //check whether or not the owner is an active User
      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context));
      componentList.setOppId(thisHeader.getOppId());
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("HeaderDetails", thisHeader);
      addRecentItem(context, thisHeader);
      return ("OppDetailsOK");
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
  public String executeCommandAddOppComponent(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    String oppId = null;
    OpportunityHeader oppHeader = null;

    if (context.getRequest().getParameter("id") != null) {
      oppId = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      buildFormElements(db, context);

      //get the header info
      oppHeader = new OpportunityHeader(db, oppId);
      context.getRequest().setAttribute("OpportunityHeader", oppHeader);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Opportunities", "Add Opportunity Component");
      return ("AddOppComponentOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db       Description of the Parameter
   *@param  context  Description of the Parameter
   */
  public void buildFormElements(Connection db, ActionContext context) {
    Exception errorMessage = null;

    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    unitSelect.build();

    context.getRequest().setAttribute("BusTypeList", busTypeSelect);
    context.getRequest().setAttribute("UnitTypeList", unitSelect);

    try {
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);
    } catch (SQLException e) {
      errorMessage = e;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandSearchOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    int errorCode = 0;
    Exception errorMessage = null;
    try {
      db = this.getConnection(context);

      OrganizationList orgList = new OrganizationList();
      orgList.setMinerOnly(false);
      orgList.setShowMyCompany(true);
      orgList.buildList(db);
      context.getRequest().setAttribute("OrgList", orgList);

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
      htmlDialog.setTitle("CFS: Confirm Delete");
      htmlDialog.setHeader("This object has the following dependencies within CFS:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='Leads.do?command=DeleteOpp&id=" + id + "'");
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
      return (executeCommandViewOpp(context));
    }
    addModuleBean(context, "Dashboard", "Dashboard");
    int errorCode = 0;
    String errorMessage = "";

    //Prepare the user id to base all data on
    int idToUse = 0;
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = null;
    //Check if a specific user was selected
    int overrideId = StringUtils.parseInt(context.getRequest().getParameter("oid"), -1);
    //Check if the list is being reset
    if (context.getRequest().getParameter("reset") != null) {
      overrideId = -1;
      context.getSession().setAttribute("leadsoverride", null);
      context.getSession().setAttribute("leadsothername", null);
      context.getSession().setAttribute("leadspreviousId", null);
    }
    //Determine the user whose data is being shown, by default it's the current user
    if (overrideId > -1) {
      if (overrideId == getUserId(context)) {
        context.getSession().removeAttribute("leadsoverride");
        context.getSession().removeAttribute("leadsothername");
        context.getSession().removeAttribute("leadspreviousId");
      }
    } else if (context.getSession().getAttribute("leadsoverride") != null) {
      overrideId = StringUtils.parseInt((String) context.getSession().getAttribute("leadsoverride"), -1);
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
      context.getRequest().setAttribute("override", String.valueOf(idToUse));
      context.getRequest().setAttribute("othername", thisRec.getContact().getNameFull());
      context.getRequest().setAttribute("previousId", String.valueOf(thisRec.getManagerId()));
      context.getSession().setAttribute("leadsoverride", String.valueOf(overrideId));
      context.getSession().setAttribute("leadsothername", thisRec.getContact().getNameFull());
      context.getSession().setAttribute("leadspreviousId", String.valueOf(thisRec.getManagerId()));
    }

    //Determine the graph type to generate
    String graphString = null;
    if (context.getRequest().getParameter("whichGraph") != null) {
      graphString = context.getRequest().getParameter("whichGraph");
      context.getSession().setAttribute("whichGraph", graphString);
    } else if ((String) context.getRequest().getSession().getAttribute("whichGraph") != null) {
      graphString = (String) context.getRequest().getSession().getAttribute("whichGraph");
    } else {
      graphString = "gmr";
    }
    
    //Check the cache and see if the current graph exists and is valid
    String checkFileName = null;
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
    HtmlSelect graphTypeSelect = new HtmlSelect();
    graphTypeSelect.setSelectName("whichGraph");
    graphTypeSelect.setJsEvent("onChange=\"document.forms[0].submit();\"");
    graphTypeSelect.addItem("gmr", "Gross Monthly Revenue");
    graphTypeSelect.addItem("ramr", "Risk Adjusted Monthly Revenue");
    graphTypeSelect.addItem("cgmr", "Commission Gross Monthly Revenue");
    graphTypeSelect.addItem("cramr", "Commission Risk Adj. Monthly Revenue");
    graphTypeSelect.setDefaultKey(graphString);

    UserList fullChildList = new UserList();
    UserList shortChildList = new UserList();
    shortChildList = thisRec.getShortChildList();
    OpportunityList realFullOppList = new OpportunityList();
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      //Generate the opportunities pagedList for the idToUse, right of graph
      PagedListInfo dashboardListInfo = this.getPagedListInfo(context, "DashboardListInfo");
      dashboardListInfo.setLink("Leads.do?command=Dashboard");
      dashboardListInfo.setColumnToSortBy("x.description");
      OpportunityList oppList = new OpportunityList();
      oppList.setPagedListInfo(dashboardListInfo);
      oppList.setOwner(idToUse);
      oppList.setBuildComponentInfo(true);
      oppList.buildList(db);
      context.getRequest().setAttribute("OppList", oppList);

      //FullChildList is the complete user hierarchy for the selected user and
      //is needed for the graph
      if (checkFileName == null) {
        fullChildList = thisRec.getFullChildList(shortChildList, new UserList());
        String range = fullChildList.getUserListIds(idToUse);
  
        //All of the opportunities that make up this graph calculation
        realFullOppList.setUnits("M");
        realFullOppList.setOwnerIdRange(range);
        realFullOppList.buildList(db);
      }
      
      //ShortChildList is used for showing user list, under graph
      shortChildList.buildPipelineValues(db);
    } catch (Exception e) {
      errorCode = 1;
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
        Opportunity tempOpp = (Opportunity) (z.next());
        if (tempOpp.getOwner() == idToUse) {
          tempOppList.addElement(tempOpp);
        }
      }
      //add up all opportunities for children
      UserList tempUserList = new UserList();
      Iterator n = fullChildList.iterator();
      while (n.hasNext()) {
        User thisRecord = (User) n.next();
        tempUserList = prepareLines(thisRecord, realFullOppList, tempUserList);
      }
      UserList linesToDraw = new UserList();
      linesToDraw = calculateLine(tempUserList, linesToDraw);
      //set my own
      tempUserList = prepareLines(thisRec, tempOppList, tempUserList);
      //add me up -- keep this
      linesToDraw = calculateLine(thisRec, linesToDraw);
      XYDataset categoryData = createCategoryDataset(linesToDraw, graphString);
      //Prepare the chart
      JFreeChart chart = ChartFactory.createXYChart("", "", "", categoryData, false);
      chart.setBackgroundPaint(Color.white);
      XYPlot bPlot = chart.getXYPlot();
      //Vertical Axis characteristics
      VerticalNumberAxis vnAxis = (VerticalNumberAxis) bPlot.getVerticalAxis();
      vnAxis.setAutoRangeIncludesZero(true);
      vnAxis.setTickMarksVisible(true);
      bPlot.setRangeAxis(vnAxis);
      //Horizontal Axis characteristics
      HorizontalNumberAxis hnAxis = (HorizontalNumberAxis) bPlot.getHorizontalAxis();
      hnAxis.setAutoRangeIncludesZero(false);
      hnAxis.setAutoTickUnitSelection(false);
      hnAxis.setAutoRange(false);
      //Grid characteristics
      Stroke gridStroke = new BasicStroke(0.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0.0f, new float[]{2.0f, 2.0f}, 0.0f);
      Paint gridPaint = Color.gray;
      //Horizontal Axis labels
      Calendar cal = Calendar.getInstance();
      ValueAxis myHorizontalDateAxis = new HorizontalDateAxis(hnAxis.getLabel(), hnAxis.getLabelFont(),
          hnAxis.getLabelPaint(), hnAxis.getLabelInsets(), true, hnAxis.getTickLabelFont(),
          hnAxis.getTickLabelPaint(), hnAxis.getTickLabelInsets(), true, true, hnAxis.getTickMarkStroke(),
          true, new Integer(0),
          new Range(cal.get(Calendar.YEAR), (cal.get(Calendar.YEAR) + 1)), false,
          new DateUnit(Calendar.MONTH, 1),
          new SimpleDateFormat("MMM ' ' yy"), true, gridStroke, gridPaint, false, null, null, null);
      myHorizontalDateAxis.setTickMarksVisible(true);
      try {
        bPlot.setDomainAxis(myHorizontalDateAxis);
      } catch (AxisNotCompatibleException err1) {
        System.out.println("AxisNotCompatibleException error!");
      }
      //Draw the chart and save to file
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Leads-> Drawing the chart");
      }
      int width = 275;
      int height = 200;
      BufferedImage img = draw(chart, width, height);

      //Output the chart
      try {
        String realPath = context.getServletContext().getRealPath("/");
        String filePath = realPath + "graphs" + fs;

        java.util.Date testDate = new java.util.Date();
        String fileName = new String(idToUse + testDate.getTime() + context.getSession().getCreationTime() + ".jpg");
        FileOutputStream foutstream = new FileOutputStream(filePath + fileName);
        JPEGImageEncoder encoder =
            JPEGCodec.createJPEGEncoder(foutstream);
        JPEGEncodeParam param =
            encoder.getDefaultJPEGEncodeParam(img);
        param.setQuality(1.0f, true);
        encoder.encode(img, param);
        foutstream.close();
        
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

    if (errorCode == 0) {
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

    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityHeader newOpp = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      newOpp = new OpportunityHeader(db, context.getRequest().getParameter("id"));
      recordDeleted = newOpp.delete(db, context, this.getPath(context, "opportunities", newOpp.getId()));
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
        return (executeCommandViewOpp(context));
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
    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      recordDeleted = component.delete(db, context, this.getPath(context, "opportunities", component.getId()));
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
        context.getRequest().setAttribute("refreshUrl", "Leads.do?command=DetailsOpp&oppId=" + component.getOppId());
        return ("ComponentDeleteOK");
      } else {
        processErrors(context, component.getErrors());
        return (executeCommandViewOpp(context));
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
    Exception errorMessage = null;
    Connection db = null;
    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;

    if (!(hasPermission(context, "pipeline-opportunities-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("oppId") != null) {
      id = context.getRequest().getParameter("oppId");
    }

    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, id);
      htmlDialog.setTitle("CFS: Pipeline Management");
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
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "View Opportunities", "Opportunities");

    String passedId = context.getRequest().getParameter("id");
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      buildFormElements(db, context);
      component = new OpportunityComponent(db, passedId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {

      if (!hasAuthority(context, component.getOwner())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("LeadsComponentDetails", component);
      addRecentItem(context, component);
      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupModifyOK");
      } else {
        return ("ComponentModifyOK");
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
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    int oppId = -1;
    addModuleBean(context, "View Opportunities", "Modify Opportunity");

    if (context.getRequest().getParameter("oppId") != null) {
      oppId = Integer.parseInt(context.getRequest().getParameter("oppId"));
    } else if (context.getRequest().getParameter("id") != null) {
      oppId = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    Connection db = null;
    OpportunityHeader thisHeader = null;

    try {
      db = this.getConnection(context);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, oppId);

      //check whether or not the owner is an active User
      //thisHeader.checkEnabledOwnerAccount(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("HeaderDetails", thisHeader);
      addRecentItem(context, thisHeader);
      return ("ModifyOppOK");
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
   */
  public String executeCommandViewOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-view")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    PagedListInfo oppListInfo = this.getPagedListInfo(context, "OpportunityListInfo");
    oppListInfo.setLink("Leads.do?command=ViewOpp");

    Connection db = null;
    OpportunityList oppList = new OpportunityList();

    try {
      db = this.getConnection(context);

      LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
      typeSelect.addItem(0, "-- All --");
      context.getRequest().setAttribute("TypeSelect", typeSelect);

      oppList.setPagedListInfo(oppListInfo);
      oppListInfo.setSearchCriteria(oppList);

      if ("all".equals(oppListInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
      } else {
        oppList.setOwner(this.getUserId(context));
      }

      oppList.setTypeId(oppListInfo.getFilterKey("listFilter1"));
      oppList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OpportunityList", oppList);
      addModuleBean(context, "View Opportunities", "Opportunities Add");
      return ("OppListOK");
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
   */
  public String executeCommandGenerateForm(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;

    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);

      //-1 is the project ID for non-projects
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), -1);

      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(db, this.getPath(context, "lead-reports"));

        String filePath1 = this.getPath(context, "lead-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".csv";
        java.io.File fileToDelete1 = new java.io.File(filePath1);
        if (!fileToDelete1.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath1);
        }

        String filePath2 = this.getPath(context, "lead-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
        java.io.File fileToDelete2 = new java.io.File(filePath2);
        if (!fileToDelete2.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath2);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Reports", "Reports del");

    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteReportOK");
      } else {
        return ("DeleteReportERROR");
      }
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
   */
  public String executeCommandDownloadCSVReport(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    FileItem thisItem = null;

    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId), -1);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      FileItem itemToDownload = null;
      itemToDownload = thisItem;

      //itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "lead-reports") + getDatePath(itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";

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
      //User either cancelled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("-none-");
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
   */
  public String executeCommandShowReportHtml(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;

    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;

    try {
      db = getConnection(context);

      //TODO: -1 is the project ID for non-projects (and shouldn't be used anymore here)
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), -1);

      String filePath = this.getPath(context, "lead-reports") + getDatePath(thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = this.includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    return ("ReportHtmlOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandExportReport(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-add")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;
    String subject = context.getRequest().getParameter("subject");
    String ownerCriteria = context.getRequest().getParameter("criteria1");

    String filePath = this.getPath(context, "lead-reports");

    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    filePath += datePathToUse1 + fs + datePathToUse2 + fs;

    OpportunityReport oppReport = new OpportunityReport();
    oppReport.setCriteria(context.getRequest().getParameterValues("selectedList"));
    oppReport.setFilePath(filePath);
    oppReport.setSubject(subject);

    PagedListInfo thisInfo = new PagedListInfo();
    thisInfo.setColumnToSortBy(context.getRequest().getParameter("sort"));
    thisInfo.setItemsPerPage(50);
    oppReport.setPagedListInfo(thisInfo);

    if (ownerCriteria.equals("my")) {
      oppReport.setOwner(this.getUserId(context));
    } else if (ownerCriteria.equals("all")) {
      oppReport.setOwnerIdRange(this.getUserRange(context));
    }

    try {
      db = this.getConnection(context);
      oppReport.buildReportFull(db);
      oppReport.setEnteredBy(getUserId(context));
      oppReport.setModifiedBy(getUserId(context));
      oppReport.saveAndInsert(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return executeCommandReports(context);
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
   */
  public String executeCommandReports(ActionContext context) {
    if (!hasPermission(context, "pipeline-reports-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;

    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_LEADS_REPORTS);
    files.setLinkItemId(-1);

    PagedListInfo rptListInfo = this.getPagedListInfo(context, "LeadRptListInfo");
    rptListInfo.setLink("/Leads.do?command=Reports");

    try {
      db = this.getConnection(context);
      files.setPagedListInfo(rptListInfo);

      if ("all".equals(rptListInfo.getListView())) {
        files.setOwnerIdRange(this.getUserRange(context));
      } else {
        files.setOwner(this.getUserId(context));
      }

      files.buildList(db);

      Iterator i = files.iterator();
      while (i.hasNext()) {
        FileItem thisItem = (FileItem) i.next();
        Contact enteredBy = this.getUser(context, thisItem.getEnteredBy()).getContact();
        Contact modifiedBy = this.getUser(context, thisItem.getModifiedBy()).getContact();
        thisItem.setEnteredByString(enteredBy.getNameFirstLast());
        thisItem.setModifiedByString(modifiedBy.getNameFirstLast());
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "Reports", "ViewReports");
      context.getRequest().setAttribute("FileList", files);
      return ("ReportsOK");
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
   */
  public String executeCommandUpdateOpp(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-edit")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    OpportunityHeader oppHeader = null;
    String oppId = null;

    if (context.getRequest().getParameter("oppId") != null) {
      oppId = context.getRequest().getParameter("oppId");
    }

    try {
      db = this.getConnection(context);
      oppHeader = new OpportunityHeader(db, oppId);
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      resultCount = oppHeader.update(db);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, oppHeader.getErrors());
        return executeCommandModifyOpp(context);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandViewOpp(context));
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

    Exception errorMessage = null;
    addModuleBean(context, "View Opportunities", "Component Details");

    String componentId = context.getRequest().getParameter("id");

    Connection db = null;
    OpportunityComponent thisComponent = null;

    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      thisComponent.checkEnabledOwnerAccount(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {

      if (!hasAuthority(context, thisComponent.getOwner())) {
        return ("PermissionError");
      }

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
   *@param  chart   Description of Parameter
   *@param  width   Description of Parameter
   *@param  height  Description of Parameter
   *@return         Description of the Returned Value
   */
  protected BufferedImage draw(JFreeChart chart, int width, int height) {
    BufferedImage img =
        new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = img.createGraphics();
    chart.draw(g2, new Rectangle2D.Double((-21), 0, width + 21, height));
    g2.dispose();
    return img;
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
    java.util.Calendar readDate = java.util.Calendar.getInstance();
    java.util.Calendar readDateAdjusted = java.util.Calendar.getInstance();
    java.util.Date d = new java.util.Date();

    java.util.Calendar rightNow = java.util.Calendar.getInstance();
    java.util.Calendar rightNowAdjusted = java.util.Calendar.getInstance();
    java.util.Calendar twelveMonths = java.util.Calendar.getInstance();

    int passedDay = 0;
    int passedYear = 0;
    int passedMonth = 0;
    int roundedMonth = 0;
    int roundedYear = 0;
    int x = 0;

    Double ramrAddTerm = new Double(0.0);
    Double gmrAddTerm = new Double(0.0);
    Double cgmrAddTerm = new Double(0.0);
    Double cramrAddTerm = new Double(0.0);

    String valKey = "";
    boolean adjustTerms = false;

    d.setDate(1);

    rightNow.setTime(d);
    //rightNow.add(java.util.Calendar.MONTH, +1);

    rightNowAdjusted.setTime(d);
    //rightNowAdjusted.add(java.util.Calendar.MONTH, +1);
    rightNowAdjusted.add(java.util.Calendar.DATE, -1);

    //twelve months
    twelveMonths.setTime(d);
    twelveMonths.add(java.util.Calendar.MONTH, +13);

    if (pertainsTo.getIsValid() == false) {
      pertainsTo.doOpportunityLock();
      if (pertainsTo.getIsValid() == false) {
        try {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("(RE)BUILDING DATA FOR " + pertainsTo.getId());
          }

          pertainsTo.setGmr(new GraphSummaryList());
          pertainsTo.setRamr(new GraphSummaryList());
          pertainsTo.setCgmr(new GraphSummaryList());
          pertainsTo.setCramr(new GraphSummaryList());

          Iterator oppIterator = oppList.iterator();
          while (oppIterator.hasNext()) {
            Opportunity tempOpp = (Opportunity) oppIterator.next();
            if (tempOpp.getOwner() == pertainsTo.getId()) {
              myDate = tempOpp.getCloseDate();
              readDate.setTime(myDate);

              readDateAdjusted.setTime(myDate);
              readDateAdjusted.add(java.util.Calendar.MONTH, +(int) (java.lang.Math.round(tempOpp.getTerms())));

              passedDay = readDate.get(java.util.Calendar.DATE);
              passedYear = readDate.get(java.util.Calendar.YEAR);
              passedMonth = readDate.get(java.util.Calendar.MONTH);

              if (passedDay < 15) {
                roundedMonth = passedMonth;
                roundedYear = passedYear;
              } else if (passedDay >= 15) {
                if (passedMonth != 11) {
                  roundedMonth = (passedMonth + 1);
                  roundedYear = passedYear;
                } else {
                  roundedMonth = 0;
                  roundedYear = passedYear + 1;
                }
                adjustTerms = true;
              }
              valKey = String.valueOf(roundedYear) + String.valueOf(roundedMonth);

              //get the individual graph values
              gmrAddTerm = new Double((tempOpp.getGuess() / tempOpp.getTerms()));
              ramrAddTerm = new Double((tempOpp.getGuess() / tempOpp.getTerms()) * tempOpp.getCloseProb());
              cgmrAddTerm = new Double((tempOpp.getGuess() / tempOpp.getTerms()) * tempOpp.getCommission());
              cramrAddTerm = new Double(((tempOpp.getGuess() / tempOpp.getTerms()) * tempOpp.getCloseProb() * tempOpp.getCommission()));
              //done

              //case: close date within 0-6m range
              if (((rightNow.before(readDate) || rightNowAdjusted.before(readDate)) && twelveMonths.after(readDate)) || rightNow.equals(readDate) || twelveMonths.equals(readDate)) {
                pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
              }
              //case: close date plus terms within 0-6m range, or greater than 6m
              else if (rightNowAdjusted.after(readDate) && ((rightNow.before(readDateAdjusted) || rightNowAdjusted.before(readDateAdjusted)))) {
                pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
              }

              //more terms
              if ((java.lang.Math.round(tempOpp.getTerms())) > 1) {
                for (x = 1; x < (java.lang.Math.round(tempOpp.getTerms())); x++) {
                  readDate.add(java.util.Calendar.MONTH, +1);
                  if (((rightNow.before(readDate) || rightNowAdjusted.before(readDate)) && twelveMonths.after(readDate)) || rightNow.equals(readDate) || twelveMonths.equals(readDate)) {
                    valKey = String.valueOf(readDate.get(java.util.Calendar.YEAR)) +
                        String.valueOf(readDate.get(java.util.Calendar.MONTH));
                    if (!(adjustTerms)) {
                      pertainsTo.setGraphValues(valKey, gmrAddTerm, ramrAddTerm, cgmrAddTerm, cramrAddTerm);
                    }
                  }
                  adjustTerms = false;
                }
              }
            }
          }
          pertainsTo.setIsValid(true, true);
        } catch (Exception e) {
          System.err.println("Leads-> Unwanted exception occurred: " + e.toString());
        } finally {
          pertainsTo.doOpportunityUnlock();
        }
      } else {
        pertainsTo.doOpportunityUnlock();
      }
    }
    usersToGraph.addElement(pertainsTo);
    if (oppList.size() == 0) {
      return new UserList();
    } else {
      return usersToGraph;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  passedList  Description of Parameter
   *@param  whichGraph  Description of Parameter
   *@return             Description of the Returned Value
   */
  private XYDataset createCategoryDataset(UserList passedList, String whichGraph) {
    if (passedList.size() == 0) {
      return createEmptyCategoryDataset();
    }

    Object[][][] data;

    java.util.Date d = new java.util.Date();
    java.util.Calendar iteratorDate = java.util.Calendar.getInstance();

    data = new Object[passedList.size()][12][2];
    int count = 0;
    int x = 0;

    Iterator n = passedList.iterator();
    while (n.hasNext()) {
      User thisUser = (User) n.next();
      String[] valKeys = thisUser.getGmr().getRange(12);

      iteratorDate.setTime(d);
      for (count = 0; count < 12; count++) {
        data[x][count][0] = createDate(iteratorDate.get(java.util.Calendar.YEAR), iteratorDate.get(java.util.Calendar.MONTH), 0);

        if (whichGraph.equals("gmr")) {
          data[x][count][1] = thisUser.getGmr().getValue(valKeys[count]);
        } else if (whichGraph.equals("ramr")) {
          data[x][count][1] = thisUser.getRamr().getValue(valKeys[count]);
        } else if (whichGraph.equals("cgmr")) {
          data[x][count][1] = thisUser.getCgmr().getValue(valKeys[count]);
        } else if (whichGraph.equals("cramr")) {
          data[x][count][1] = thisUser.getCramr().getValue(valKeys[count]);
        }
        iteratorDate.add(java.util.Calendar.MONTH, +1);
      }
      x++;
    }
    return new DefaultXYDataset(data);
  }


  /**
   *  Description of the Method
   *
   *@param  primaryNode   Description of Parameter
   *@param  currentLines  Description of Parameter
   *@return               Description of the Returned Value
   */
  private UserList calculateLine(User primaryNode, UserList currentLines) {
    if (currentLines.size() == 0) {
      currentLines.addElement(primaryNode);
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
    currentLines.addElement(thisLine);
    return currentLines;
  }


  /**
   *  Description of the Method
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
    currentLines.addElement(thisLine);
    return currentLines;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateComponent(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Opportunities", "Update Opportunity Component");
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    OpportunityHeader header = null;

    OpportunityComponent component = (OpportunityComponent) context.getFormBean();
    component.setTypeList(context.getRequest().getParameterValues("selectedList"));

    try {
      db = this.getConnection(context);
      component.setModifiedBy(getUserId(context));
      resultCount = component.update(db, context);

      if (resultCount == 1) {
        component.queryRecord(db, component.getId());
        context.getRequest().setAttribute("OppComponentDetails", component);
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, component.getErrors());
        return executeCommandModifyComponent(context);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandViewOpp(context));
        } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("details")) {
          return (executeCommandDetailsOpp(context));
        } else if (context.getRequest().getParameter("popup") != null) {
          return "PopupCloseOK";
        } else {
          return ("DetailsComponentOK");
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
}


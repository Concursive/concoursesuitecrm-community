package com.darkhorseventures.autoguide.module;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.Organization;
import com.darkhorseventures.cfsmodule.CFSModule;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.autoguide.base.*;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    April 30, 2002
 *@version    $Id$
 */
public final class AutoGuide extends CFSModule {

  /**
   *  Default command --> goes to the List command
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandList(context);
  }


  /**
   *  Stand-alone view of the vehicle list
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandList(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    PagedListInfo autoGuideDirectoryInfo = this.getPagedListInfo(context, "AutoGuideDirectoryInfo");
    autoGuideDirectoryInfo.setLink("AutoGuide.do?command=List");

    Connection db = null;
    AccountInventoryList inventoryList = new AccountInventoryList();
    try {
      db = this.getConnection(context);
      //inventoryList.setPagedListInfo(autoGuideDirectoryInfo);
      inventoryList.setBuildOrganizationInfo(true);
      inventoryList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("InventoryList", inventoryList);
      if ("slides".equals(autoGuideDirectoryInfo.getListView())) {
        return ("List2OK");
      } else {
        return ("ListOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Stand-alone view of the vehicle details
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoGuide-> Details for ID: " + id);
      }
      db = this.getConnection(context);
      AccountInventory inventoryItem = new AccountInventory(db, id);
      context.getRequest().setAttribute("InventoryItem", inventoryItem);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "Details");
    if (errorMessage == null) {
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Stand-alone action to delete a vehicle and return to the list
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoGuide-> Delete ID: " + id);
      }
      db = this.getConnection(context);
      AccountInventory inventoryItem = new AccountInventory(db, id);
      inventoryItem.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "Delete");
    if (errorMessage == null) {
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Integrated view of the vehicle list under Accounts module
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAccountList(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    PagedListInfo autoGuideAccountInfo = this.getPagedListInfo(context, "AutoGuideAccountInfo");
    autoGuideAccountInfo.setLink("AccountsAutoGuide.do?command=AccountList");

    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    Connection db = null;
    AccountInventoryList inventoryList = new AccountInventoryList();
    try {
      db = this.getConnection(context);
      Organization org = new Organization(db, orgId);
      //inventoryList.setPagedListInfo(autoGuideAccountInfo);
      inventoryList.setOrgId(org.getOrgId());
      inventoryList.setBuildOrganizationInfo(false);
      inventoryList.buildList(db);
      context.getRequest().setAttribute("OrgDetails", org);
      context.getRequest().setAttribute("InventoryList", inventoryList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "List");
    if (errorMessage == null) {
      if ("slides".equals(autoGuideAccountInfo.getListView())) {
        return ("List2OK");
      } else {
        return ("ListOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAccountAdd(ActionContext context) {
    if (!(hasPermission(context, "autoguide-inventory-add"))) {
	    return ("PermissionError");
    }
    Exception errorMessage = null;

/*     HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();
 */
/*     HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    //unitSelect.addItem("D", "Days");
    //unitSelect.addItem("W", "Weeks");
    //unitSelect.addItem("Y", "Years");
    unitSelect.build();
 */
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    Connection db = null;
    try {
      db = this.getConnection(context);
      Organization thisOrganization = new Organization(db, orgId);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      
      ArrayList yearList = VehicleList.buildYearList(db); 
      HtmlSelect yearSelect = new HtmlSelect(yearList);
      yearSelect.addItem(-1, "--None--", 0);
      context.getRequest().setAttribute("YearSelect", yearSelect);
      
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Add Vehicle");
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}


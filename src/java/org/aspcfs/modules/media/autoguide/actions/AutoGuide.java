package com.darkhorseventures.autoguide.module;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.cfsmodule.CFSModule;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.autoguide.base.*;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    April 30, 2002
 *@version    $Id$
 */
public final class AutoGuide extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandList(context);
  }


  /**
   *  Description of the Method
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
  
  public String executeCommandDetails(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String)context.getRequest().getParameter("id"));
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
  
  public String executeCommandDelete(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String)context.getRequest().getParameter("id"));
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
}


/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.modules.actions.CFSModule;

import java.sql.Connection;

/**
 *  Actions for the Icelets module
 *
 *@author     kailash
 *@created    February 10, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public final class Icelets extends CFSModule {

  /**
   *  Default: not used
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

    return this.getReturn(context, "Default");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String excuteCommandList(ActionContext context) {

    Connection db = null;
    try {
      db = this.getConnection(context);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "List");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigure(ActionContext context) {

    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);

      IceletList iceletList = new IceletList();
      iceletList.setEmptyHtmlSelectRecord(systemStatus.getLabel("calendar.none.4dashes"));
      iceletList.buildList(db);

      RowColumnList rowColumnList = new RowColumnList();
      rowColumnList.buildList(db);

      context.getRequest().setAttribute("iceletList", iceletList);
      context.getRequest().setAttribute("rowColumnList", rowColumnList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Configure");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    String returnString = "View";
    Connection db = null;
    try {
      db = this.getConnection(context);

      int rowColumnId = Integer.parseInt(context.getRequest().getParameter("id"));
      RowColumn rowColumn = new RowColumn(db, rowColumnId);
      context.getRequest().setAttribute("rowColumn", rowColumn);

      int iceletId = Integer.parseInt(context.getRequest().getParameter(String.valueOf(rowColumnId)));

      Icelet icelet = new Icelet(db, iceletId);
      IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
      iceletPropertyMap.setIceletRowColumnId(rowColumnId);
      iceletPropertyMap.buildList(db);

      context.getRequest().setAttribute("iceletPropertyMap", iceletPropertyMap);

      //returnString = returnString + icelet.configureIcelet(context, iceletPropertyMap);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, returnString);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    String returnString = "Modify";
    try {
      db = this.getConnection(context);

      int rowColumnId = Integer.parseInt(context.getRequest().getParameter("id"));
      RowColumn rowColumn = new RowColumn(db, rowColumnId);

      int iceletId = Integer.parseInt(context.getRequest().getParameter(String.valueOf(rowColumnId)));
      if (rowColumn.getIceletId() != iceletId) {
        rowColumn.setIceletId(iceletId);
        rowColumn.setModifiedBy(getUserId(context));
        rowColumn.update(db);
      }

      Icelet icelet = new Icelet(db, iceletId);
      IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
      iceletPropertyMap.setIceletRowColumnId(rowColumnId);
      iceletPropertyMap.buildList(db);

      context.getRequest().setAttribute("rowColumn", rowColumn);
      context.getRequest().setAttribute("iceletPropertyMap", iceletPropertyMap);

      //returnString = returnString + icelet.configureIcelet(context, iceletPropertyMap);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, returnString);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    RowColumn rowColumn = (RowColumn) context.getFormBean();
    try {
      db = this.getConnection(context);

      IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
      iceletPropertyMap.setIceletRowColumnId(rowColumn.getId());
      iceletPropertyMap.buildList(db);
      iceletPropertyMap.delete(db);

      iceletPropertyMap = new IceletPropertyMap();
      iceletPropertyMap.setIceletRowColumnId(rowColumn.getId());
      iceletPropertyMap.setModifiedBy(getUserId(context));
      iceletPropertyMap.setEnteredBy(getUserId(context));
      iceletPropertyMap.setRequestItems(context);
      iceletPropertyMap.insert(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandConfigure(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ConfirmDelete");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Delete");
  }

/*
   //Prototype methods
  public String executeCommandPreview(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      RowColumnList rowColumnList = new RowColumnList();
      rowColumnList.setBuildIcelet(true);
      rowColumnList.setBuildIceletPropertyMap(true);
      rowColumnList.buildList(db);

      context.getRequest().setAttribute("rowColumnList", rowColumnList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Preview");
  }


  public String executeCommandSubmitForm(ActionContext context) {
    boolean returnValue = false;
    Connection db = null;
    try {
      db = this.getConnection(context);

      int rowColumnId = Integer.parseInt(context.getRequest().getParameter("id"));
      RowColumn newRowColumn = (RowColumn) context.getFormBean();
      newRowColumn.setBuildIcelet(true);
      newRowColumn.queryRecord(db, rowColumnId);
      
      IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
      iceletPropertyMap.setIceletRowColumnId(rowColumnId);
      iceletPropertyMap.setModifiedBy(getUserId(context));
      iceletPropertyMap.setEnteredBy(getUserId(context));
      iceletPropertyMap.setRequestItems(context);

      newRowColumn.setIceletPropertyMap(iceletPropertyMap);
      returnValue = newRowColumn.submitForm(context, this.getSystemStatus(context), db);

      RowColumnList rowColumnList = new RowColumnList();
      
      // Do not fetch the the icelet in the submitted row
      // inorder to preserve non-persistant icelet properties
      rowColumnList.setIgnoreRowColumnId(rowColumnId);
      
      rowColumnList.setBuildIcelet(true);
      rowColumnList.setBuildIceletPropertyMap(true);
      rowColumnList.buildList(db);
      
      //Add the icelet properties in the submitted row
      rowColumnList.add(newRowColumn);
      context.getRequest().setAttribute("rowColumnList", rowColumnList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Preview");
  }
  
*/
}


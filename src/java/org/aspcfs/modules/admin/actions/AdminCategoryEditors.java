package org.aspcfs.modules.admin.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.text.*;

/**
 *  Actions for managing the Category Editors.
 *
 *@author     akhi_m
 *@created    May 23, 2003
 *@version    $id: exp$
 */
public final class AdminCategoryEditors extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      context.getRequest().setAttribute("CategoryEditor", thisEditor);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "Editor");
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
    if (!(hasPermission(context, "admin-edit"))) {
      return ("PermissionError");
    }

    String categoryId = context.getRequest().getParameter("categoryId");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      TicketCategoryDraft thisCategory = (TicketCategoryDraft) thisEditor.getCategory(Integer.parseInt(categoryId));
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "EditCategory");
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
  public String executeCommandSave(ActionContext context) {
    return this.getReturn(context, "SaveCategory");
  }

}


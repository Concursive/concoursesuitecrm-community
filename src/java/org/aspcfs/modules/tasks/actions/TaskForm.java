package org.aspcfs.modules.tasks.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;

/**
 *  Basic Task object.<br>
 *  Processes and Builds necessary data for any Task object.
 *
 *@author     akhi_m
 *@created    May 23, 2003
 *@version    $Id$
 */
public final class TaskForm extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      //build loe types
      LookupList estimatedLOETypeList = new LookupList(db, "lookup_task_loe");
      context.getRequest().setAttribute("EstimatedLOETypeList", estimatedLOETypeList);
      //build task priority levels (descritions)
      LookupList priorityList = new LookupList(db, "lookup_task_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return this.getReturn(context, "PrepareTask");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}


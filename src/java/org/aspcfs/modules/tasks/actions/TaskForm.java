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
package org.aspcfs.modules.tasks.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;

/**
 * Basic Task object.<br>
 * Processes and Builds necessary data for any Task object.
 *
 * @author akhi_m
 * @version $Id$
 * @created May 23, 2003
 */
public final class TaskForm extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      //build loe types
      LookupList estimatedLOETypeList = new LookupList(db, "lookup_task_loe");
      context.getRequest().setAttribute(
          "EstimatedLOETypeList", estimatedLOETypeList);
      //build task priority levels (descritions)
      LookupList priorityList = new LookupList(db, "lookup_task_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    String returnAction = "PrepareTask";
    if (context.getRequest().getParameter("returnAction") != null) {
      returnAction = (String) context.getRequest().getParameter(
          "returnAction");
    }
    return getReturn(context, returnAction);
  }
}


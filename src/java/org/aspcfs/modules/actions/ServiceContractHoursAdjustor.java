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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.servicecontracts.base.ServiceContractHours;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.text.NumberFormat;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id$
 * @created February 16, 2004
 */
public final class ServiceContractHoursAdjustor extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdjustHours(ActionContext context) {
    Connection db = null;
    boolean adjustmentDone = false;
    ServiceContractHours sch = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      LookupList adjustmentReasonList = new LookupList(
          db, "lookup_hours_reason");
      adjustmentReasonList.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute(
          "adjustmentReasonList", adjustmentReasonList);

      if ("true".equals(
          (String) context.getRequest().getParameter("finalsubmit"))) {
        sch = new ServiceContractHours();
        NumberFormat nf = NumberFormat.getInstance(
            UserUtils.getUserLocale(context.getRequest()));
        sch.setAdjustmentHours(
            nf.parse(
                (String) context.getRequest().getParameter("adjustmentHours")).doubleValue());
        sch.setAdjustmentReason(
            (String) context.getRequest().getParameter("adjustmentReason"));
        sch.setAdjustmentNotes(
            (String) context.getRequest().getParameter("adjustmentNotes"));
        adjustmentDone = this.validateObject(context, db, sch);
      }

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (adjustmentDone) {
      context.getRequest().setAttribute("serviceContractHours", sch);
      context.getRequest().setAttribute("finalsubmit", "true");
    }
    return ("AdjustHoursOK");
  }
}

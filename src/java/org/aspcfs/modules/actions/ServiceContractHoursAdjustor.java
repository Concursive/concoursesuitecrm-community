package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.actions.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.servicecontracts.base.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    February 16, 2004
 *@version    $Id$
 */
public final class ServiceContractHoursAdjustor extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdjustHours(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    boolean adjustmentDone = false;
    ServiceContractHours sch = null;
    try{
      db = this.getConnection(context);
      LookupList adjustmentReasonList = new LookupList(db, "lookup_hours_reason");
      adjustmentReasonList.addItem(-1, "--None--");
      context.getRequest().setAttribute("adjustmentReasonList", adjustmentReasonList);

      if ("true".equals((String) context.getRequest().getParameter("finalsubmit"))) {
        sch = new ServiceContractHours(); 
        sch.setAdjustmentHours((String) context.getRequest().getParameter("adjustmentHours"));
        sch.setAdjustmentReason((String) context.getRequest().getParameter("adjustmentReason"));
        sch.setAdjustmentNotes((String) context.getRequest().getParameter("adjustmentNotes"));
        adjustmentDone = true;
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (adjustmentDone) {
        context.getRequest().setAttribute("serviceContractHours", sch);
        context.getRequest().setAttribute("finalsubmit", "true");
      }
      return ("AdjustHoursOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}


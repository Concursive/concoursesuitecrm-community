package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.*;

/**
 *  Help Module
 *
 *@author     mrajkowski
 *@created    January 21, 2002
 *@version    $Id$
 */
public final class Help extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    try {
      String module = context.getRequest().getParameter("module");
      String section = context.getRequest().getParameter("section");
      String subsection = context.getRequest().getParameter("sub");
      db = this.getConnection(context);
      HelpContents helpContents = new HelpContents(db);
      context.getRequest().setAttribute("HelpContents", helpContents);
      HelpItem thisItem = new HelpItem(db, module, section, subsection);
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    if (this.hasPermission(context, "help-edit")) {
      return ("ModifyOK");
    } else {
      return ("HelpOK");
    }
  }
  
  public String executeCommandProcess(ActionContext context) {
    if (this.hasPermission(context, "help-edit")) {
      Exception errorMessage = null;
      Connection db = null;
  
      try {
        HelpItem thisItem = (HelpItem)context.getFormBean();
        db = this.getConnection(context);
        thisItem.setEnteredBy(this.getUserId(context));
        thisItem.setModifiedBy(this.getUserId(context));
        thisItem.update(db);
      } catch (Exception e) {
        errorMessage = e;
        e.printStackTrace(System.out);
      } finally {
        this.freeConnection(context, db);
      }
  
      return ("ProcessOK");
    } else {
      return ("UserError");
    }
  }
}


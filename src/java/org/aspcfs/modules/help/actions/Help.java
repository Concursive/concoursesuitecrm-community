package org.aspcfs.modules.help.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.help.base.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupElement;
import com.isavvix.tools.*;
import java.sql.*;
import java.util.StringTokenizer;
import org.aspcfs.utils.HTTPUtils;

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
      HelpItem thisItem = new HelpItem();
      thisItem.setModule(module);
      thisItem.setSection(section);
      thisItem.setSubsection(subsection);
      
      thisItem.fetchRecord(db);
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcess(ActionContext context) {
    if (this.hasPermission(context, "help-edit")) {
      Exception errorMessage = null;
      Connection db = null;
      try {
        HelpItem thisItem = (HelpItem) context.getFormBean();
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewAll(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      HelpContents contents = new HelpContents();
      contents.build(db);
      context.getRequest().setAttribute("HelpContents", contents);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewAllOK");
  }

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewContext(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {

      String helpId = context.getRequest().getParameter("helpId");
      db = this.getConnection(context);
      HelpItem thisItem = new HelpItem();

      thisItem.setId(helpId);
      thisItem.queryRecord(db);
      context.getRequest().setAttribute("Help", thisItem);

    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    
    return ("ContextOK");

  }
  

  public String executeCommandViewModule(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {

      String moduleId = context.getRequest().getParameter("moduleId");
      db = this.getConnection(context);
      if (moduleId != null && !"-1".equals(moduleId)) {
        HelpModule thisModule = new HelpModule(db, Integer.parseInt(moduleId));
        context.getRequest().setAttribute("helpModule", thisModule);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    
    return ("ModuleOK");
  }
}

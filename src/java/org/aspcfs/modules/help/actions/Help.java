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
package org.aspcfs.modules.help.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.help.base.HelpContents;
import org.aspcfs.modules.help.base.HelpItem;
import org.aspcfs.modules.help.base.HelpModule;
import org.aspcfs.modules.help.base.HelpTOC;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;

/**
 *  Help Module
 *
 *@author     mrajkowski
 *@created    January 21, 2002
 *@version    $Id$
 */

public final class Help extends CFSModule {

  /**
   *  Fetches the help_id and module_id based on the action, section and
   *  sebsection
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
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
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
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
      Connection db = null;
      try {
        HelpItem thisItem = (HelpItem) context.getFormBean();
        db = this.getConnection(context);
        thisItem.setEnteredBy(this.getUserId(context));
        thisItem.setModifiedBy(this.getUserId(context));
        thisItem.update(db);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
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
    Connection db = null;
    try {
      db = this.getConnection(context);
      HelpContents contents = new HelpContents();
      contents.build(db);
      context.getRequest().setAttribute("HelpContents", contents);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewAllOK");
  }


  /**
   *  Fetches context sensitive (page related) help information
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewContext(ActionContext context) {
    Connection db = null;
    try {
      String helpId = context.getRequest().getParameter("helpId");
      db = this.getConnection(context);
      HelpItem thisItem = new HelpItem();
      thisItem.setId(helpId);
      if (!(helpId.equals("-1"))) {
        thisItem.queryRecord(db);
      }
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ContextOK");
  }


  /**
   *  Fetches module description of the module that the page belongs to
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */

  public String executeCommandViewModule(ActionContext context) {
    Connection db = null;
    try {
      String moduleId = context.getRequest().getParameter("moduleId");
      db = this.getConnection(context);
      HelpModule thisModule;
      if (moduleId.equals("-1")) {
        thisModule = new HelpModule();
        thisModule.setId(moduleId);
      } else {
        thisModule = new HelpModule(db, Integer.parseInt(moduleId));
      }
      context.getRequest().setAttribute("helpModule", thisModule);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ModuleOK");
  }


  /**
   *  Fetches the table of contents
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewTableOfContents(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      HelpTOC thisTOC = new HelpTOC(db);
      context.getRequest().setAttribute("helpTOC", thisTOC);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("TableOfContentsOK");
  }


  /**
   *  Provides an editable view of the module description
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewModuleDescription(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String module = context.getRequest().getParameter("module");
      HelpModule thisModule = new HelpModule(db, module);
      context.getRequest().setAttribute("helpModule", thisModule);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ModuleDescriptionOK");
  }


  /**
   *  Fetches the existing module descirption so that they can be modified
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyDescription(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String id = context.getRequest().getParameter("id");
      String action = context.getRequest().getParameter("action");
      HelpModule thisModule = new HelpModule(db, Integer.parseInt(id));
      thisModule.setRelatedAction(action);
      context.getRequest().setAttribute("helpModule", thisModule);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ModifyDescriptionOK");
  }


  /**
   *  Saves the updated module description
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveDescription(ActionContext context) {
    Connection db = null;
    int resultCount = -1;
    HelpModule thisModule = (HelpModule) context.getFormBean();
    try {
      db = this.getConnection(context);
      if (thisModule == null) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Help-> Help Module is NULL");
        }
      } else {
        resultCount = thisModule.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "Help.do?command=ViewModuleDescription&module=" + thisModule.getRelatedAction() + RequestUtils.addLinkParams(context.getRequest(), "popup"));
    return getReturn(context, "SaveDescription");
  }
}


package org.aspcfs.modules.admin.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.*;
import java.sql.*;
import org.aspcfs.modules.help.base.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Various QA tools for Admin
 *
 *@author     matt rajkowski
 *@created    July 25, 2003
 *@version    $Id$
 */
public class AdminQA extends CFSModule {

  /**
   *  Shows a report of the QA system
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReport(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      HelpItemList contents = new HelpItemList();
      PagedListInfo pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(0);
      pagedListInfo.setDefaultSort("module, section, subsection", null);
      contents.setPagedListInfo(pagedListInfo);
      contents.buildList(db);
      Iterator i = contents.iterator();
      while (i.hasNext()) {
        HelpItem item = (HelpItem) i.next();
        item.getNotes().setIgnoreDone(true);
        if ("true".equals(context.getRequest().getParameter("incompleteOnly"))) {
          item.getNotes().setIncompleteOnly(true);
        }
        item.buildNotes(db);
      }
      context.getRequest().setAttribute("contents", contents);
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return "QAReportOK";
  }
}


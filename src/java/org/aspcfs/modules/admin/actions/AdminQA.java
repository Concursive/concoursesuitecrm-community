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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.help.base.HelpItem;
import org.aspcfs.modules.help.base.HelpItemList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Various QA tools for Admin
 *
 * @author matt rajkowski
 * @version $Id$
 * @created July 25, 2003
 */
public class AdminQA extends CFSModule {

  /**
   * Shows a report of the QA system
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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


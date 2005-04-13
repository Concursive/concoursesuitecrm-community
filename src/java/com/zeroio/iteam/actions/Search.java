/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.beans.IteamSearchBean;
import com.zeroio.iteam.search.*;
import com.zeroio.iteam.base.*;
import com.zeroio.utils.*;
import java.sql.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.troubletickets.base.TicketIndexer;
import org.aspcfs.modules.tasks.base.TaskCategoryIndexer;
import org.aspcfs.modules.tasks.base.TaskIndexer;
import org.aspcfs.modules.documents.base.DocumentStoreIndexer;

/**
 *  Actions for working with the search page
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public final class Search extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public synchronized String executeCommandIndex(ActionContext context) {
    //setMaximized(context);
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Directory index = null;
    IndexWriter writer = null;
    try {
      index = getDirectory(context, true);
      // Create the index
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Search-> Creating index: " + index);
      }
      writer = new IndexWriter(index, new StandardAnalyzer(), true);
      // Add some data
      db = getConnection(context);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Search-> Adding items to index");
      }
      ProjectIndexer.add(writer, db, context);
      NewsArticleIndexer.add(writer, db, context);
      IssueCategoryIndexer.add(writer, db, context);
      IssueIndexer.add(writer, db, context);
      IssueReplyIndexer.add(writer, db, context);
      FileItemIndexer.add(writer, db, this.getPath(context, "projects"), context);
      TaskCategoryIndexer.add(writer, db, context);
      TaskIndexer.add(writer, db, context);
      TicketIndexer.add(writer, db, context);
      RequirementIndexer.add(writer, db, context);
      AssignmentFolderIndexer.add(writer, db, context);
      AssignmentIndexer.add(writer, db, context);
      AssignmentNoteIndexer.add(writer, db, context);
      DocumentStoreIndexer.add(writer, db, context);
      // Finish up
      writer.optimize();
      // Update the shared searcher
      IndexSearcher searcher = new IndexSearcher(index);
      context.getServletContext().setAttribute("indexSearcher", searcher);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
      // always close the index writer
      try {
        if (writer != null) {
          writer.close();
        }
        writer = null;
      } catch (Exception ie) {
      }
      try {
        if (index != null) {
          index.close();
        }
        index = null;
      } catch (Exception ie) {
      }
    }
    return "IndexOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandShowForm(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    return "SearchFormOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    //setMaximized(context);
    IteamSearchBean search = (IteamSearchBean) context.getFormBean();
    PagedListInfo searchBeanInfo = this.getPagedListInfo(context, "searchBeanInfo");
    searchBeanInfo.setLink("ProjectManagementSearch.do?command=Default");
    Connection db = null;
    try {
      search.parseQuery();
      if (!search.isValid()) {
        return "SearchResultsERROR";
      }
      // Get the shared searcher
      IndexSearcher searcher = SearchUtils.getSharedSearcher(context, this.getDirectory(context));
      db = getConnection(context);
      String queryString = null;
      if (search.getScope() != SearchBean.THIS) {
        search.setProjectId(-1);
      }
      // Check for project access and get acceptable query string
      if (search.getProjectId() > -1) {
        Project thisProject = loadProject(db, search.getProjectId(), context);
        context.getRequest().setAttribute("Project", thisProject);
        queryString = "(" + IteamSearchQuery.buildIteamSearchQuery(search, db, getUserId(context), search.getProjectId()) + ") AND (" + search.getParsedQuery() + ")";
      } else {
        queryString = "(" + IteamSearchQuery.buildIteamSearchQuery(search, db, getUserId(context), -1) + ") AND (" + search.getParsedQuery() + ")";
      }
      // Execute the query and build search results
      SearchUtils.buildSearchResults(context, queryString, searcher, searchBeanInfo);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SearchResultsERROR";
    } finally {
      freeConnection(context, db);
    }
    return "SearchResultsOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandTips(ActionContext context) {
    return "TipsOK";
  }
}


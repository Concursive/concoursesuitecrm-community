/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.beans.SearchBean;
import com.zeroio.iteam.base.*;
import java.sql.*;
import java.util.*;
import java.io.File;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.troubletickets.base.TicketIndexer;
import org.aspcfs.modules.tasks.base.TaskCategoryIndexer;
import org.aspcfs.modules.tasks.base.TaskIndexer;
import java.text.*;

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
  public String executeCommandIndex(ActionContext context) {
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
      System.out.println("Search-> Creating index: " + index);
      writer = new IndexWriter(index, new StandardAnalyzer(), true);
      // Add some data
      db = getConnection(context);
      System.out.println("Search-> Adding items to index");
      ProjectIndexer.add(writer, db);
      NewsArticleIndexer.add(writer, db);
      IssueCategoryIndexer.add(writer, db);
      IssueIndexer.add(writer, db);
      IssueReplyIndexer.add(writer, db);
      FileItemIndexer.add(writer, db, this.getPath(context, "projects"));
      TaskCategoryIndexer.add(writer, db);
      TaskIndexer.add(writer, db);
      TicketIndexer.add(writer, db);
      RequirementIndexer.add(writer, db);
      AssignmentFolderIndexer.add(writer, db);
      AssignmentIndexer.add(writer, db);
      // Finish up
      writer.optimize();
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    SearchBean search = (SearchBean) context.getFormBean();
    PagedListInfo searchBeanInfo = this.getPagedListInfo(context, "searchBeanInfo");
    searchBeanInfo.setLink("Search.do?command=Default");
    Connection db = null;
    try {
      search.parseQuery();
      if (!search.isValid()) {
        return "SearchResultsERROR";
      }
      // Get the shared searcher
      IndexSearcher searcher = (IndexSearcher) context.getServletContext().getAttribute("indexSearcher");
      if (searcher == null) {
        synchronized (this) {
          searcher = (IndexSearcher) context.getServletContext().getAttribute("indexSearcher");
          if (searcher == null) {
            Directory index = getDirectory(context);
            searcher = new IndexSearcher(index);
            context.getServletContext().setAttribute("indexSearcher", searcher);
          }
        }
      }
      db = getConnection(context);
      String queryString = null;
      if (search.getScope() != SearchBean.THIS) {
        search.setProjectId(-1);
      }
      // Check for project access and get acceptable query string
      if (search.getProjectId() > -1) {
        Project thisProject = new Project(db, search.getProjectId(), this.getUserRange(context));
        context.getRequest().setAttribute("Project", thisProject);
        queryString = "(" + buildProjectList(search, db, getUserId(context), search.getProjectId()) + ") AND (" + search.getParsedQuery() + ")";
      } else {
        queryString = "(" + buildProjectList(search, db, getUserId(context), -1) + ") AND (" + search.getParsedQuery() + ")";
      }
      // Execute the query
      long start = System.currentTimeMillis();
      Query query = QueryParser.parse(queryString, "contents", new StandardAnalyzer());
      Hits hits = searcher.search(query);
      //Sort sort = new Sort("type");
      //Hits hits = searcher.search(query, sort);
      long end = System.currentTimeMillis();
      context.getRequest().setAttribute("hits", hits);
      context.getRequest().setAttribute("duration", new Long(end - start));
      //System.out.println("Found " + hits.length() + " document(s) that matched query '" + queryString + "':");
      // configure the paged list info
      searchBeanInfo.setMaxRecords(hits.length());
      String tmpItemsPerPage = context.getRequest().getParameter("items");
      if (tmpItemsPerPage != null) {
        searchBeanInfo.setItemsPerPage(tmpItemsPerPage);
      }
      if ("true".equals(context.getRequest().getParameter("auto-populate"))) {
        searchBeanInfo.setCurrentOffset(0);
      }
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
   *@param  db                 Description of the Parameter
   *@param  userId             Description of the Parameter
   *@param  search             Description of the Parameter
   *@param  specificProjectId  Description of the Parameter
   *@return                    Description of the Return Value
   *@exception  SQLException   Description of the Exception
   */
  private String buildProjectList(SearchBean search, Connection db, int userId, int specificProjectId) throws SQLException {
    // get the projects for the user
    // get the project permissions for each project
    // if user has access to the data, then add to query
    HashMap projectList = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT project_id, userlevel " +
        "FROM project_team " +
        "WHERE user_id = ? " +
        "AND status IS NULL " +
        (specificProjectId > -1 ? "AND project_id = ? " : ""));
    pst.setInt(1, userId);
    if (specificProjectId > -1) {
      pst.setInt(2, specificProjectId);
    }
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      int projectId = rs.getInt("project_id");
      int roleId = rs.getInt("userlevel");
      projectList.put(new Integer(projectId), new Integer(roleId));
    }
    rs.close();
    pst.close();
    // build query string
    StringBuffer projectBuffer = new StringBuffer();
    // scan for permissions
    Iterator projects = projectList.keySet().iterator();
    while (projects.hasNext()) {
      StringBuffer permissionBuffer = new StringBuffer();
      Integer projectId = (Integer) projects.next();
      Integer roleId = (Integer) projectList.get(projectId);
      // for each project check the available user permissions
      PermissionList permissionList = new PermissionList();
      permissionList.setProjectId(projectId.intValue());
      permissionList.buildList(db);

      if (search.getSection() == SearchBean.DETAILS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for project permissions
        if (permissionList.getAccessLevel("project-details-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:project");
        }
      }
      if (search.getSection() == SearchBean.NEWS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for news permissions
        if (permissionList.getAccessLevel("project-news-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          // current, archived, unreleased
          // check for status permissions
          if (permissionList.getAccessLevel("project-news-view-unreleased") >= roleId.intValue()) {
            permissionBuffer.append("type:news");
          } else {
            // take into account a date range  [20030101 TO 20040101]
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            permissionBuffer.append("(type:news AND newsStatus:2 AND newsDate:[20030101 TO " + formatter.format(new java.util.Date()) + "])");
          }
        }
      }
      if (search.getSection() == SearchBean.DISCUSSION || search.getSection() == SearchBean.UNDEFINED) {
        // Check for issue category permissions
        if (permissionList.getAccessLevel("project-discussion-forums-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:issueCategory");
        }
        // Check for issue permissions
        if (permissionList.getAccessLevel("project-discussion-topics-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:issue");
        }
        // Check for issue reply permissions
        if (permissionList.getAccessLevel("project-discussion-messages-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:issueReply");
        }
      }
      if (search.getSection() == SearchBean.DOCUMENTS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for file item permissions
        if (permissionList.getAccessLevel("project-documents-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:file");
        }
      }
      if (search.getSection() == SearchBean.LISTS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for task category permissions
        if (permissionList.getAccessLevel("project-lists-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:listCategory");
          // Check for task permissions
          permissionBuffer.append(" OR ");
          permissionBuffer.append("type:list");
        }
      }
      if (search.getSection() == SearchBean.TICKETS || search.getSection() == SearchBean.UNDEFINED) {
        // Check for ticket permissions
        if (permissionList.getAccessLevel("project-tickets-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:ticket");
        }
      }
      if (search.getSection() == SearchBean.PLAN || search.getSection() == SearchBean.UNDEFINED) {
        // Check for requirement permissions
        if (permissionList.getAccessLevel("project-plan-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:outline");
          // Check for assignment folder permissions
          permissionBuffer.append(" OR ");
          permissionBuffer.append("type:activityFolder");
          // Check for assignment permissions
          permissionBuffer.append(" OR ");
          permissionBuffer.append("type:activity");
        }
      }
      // piece together
      if (permissionBuffer.length() > 0) {
        if (projectBuffer.length() > 0) {
          projectBuffer.append(" OR ");
        }
        projectBuffer.append("(projectId:" + projectId.intValue() + " AND (" + permissionBuffer.toString() + ")) ");
      }
      // debuging
      if (permissionBuffer.length() == 0) {
        System.out.println("NO PERMISSIONS FOR PROJECT: " + projectId.intValue());
      }
    }
    // user does not have any projects, so lock them into a non-existent project
    // for security
    if (projectBuffer.length() == 0) {
      return "projectId:-1";
    } else {
      return projectBuffer.toString();
    }
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


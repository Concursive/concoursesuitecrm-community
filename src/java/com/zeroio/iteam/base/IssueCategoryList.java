/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class IssueCategoryList extends Vector {

  private Project project = null;
  private int projectId = -1;


  /**
   *  Constructor for the IssueCategoryList object
   */
  public IssueCategoryList() { }


  /**
   *  Sets the projectId attribute of the IssueCategoryList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the project attribute of the IssueCategoryList object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql =
        "SELECT type_id, l.description, i.project_id " +
        "FROM lookup_project_issues l, project_issues i " +
        "WHERE l.code = i.type_id " +
        "AND i.project_id = ? " +
        "GROUP BY l.level, l.description, type_id, i.project_id ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, projectId);
    rs = pst.executeQuery();
    while (rs.next()) {
      IssueCategory thisCategory = new IssueCategory(rs);
      thisCategory.setProject(project);
      thisCategory.setProjectId(projectId);
      this.addElement(thisCategory);
    }
    rs.close();
    pst.close();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      IssueCategory thisIssueCategory = (IssueCategory) i.next();
      thisIssueCategory.buildCounts(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      IssueCategory thisIssueCategory = (IssueCategory) i.next();
      thisIssueCategory.delete(db);
    }
  }
}


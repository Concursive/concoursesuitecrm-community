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
package com.zeroio.iteam.base;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import java.sql.*;
import java.io.IOException;
import com.zeroio.utils.ContentUtils;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id: ProjectIndexer.java,v 1.2 2004/07/21 19:00:43 mrajkowski Exp
 *      $
 */
public class ProjectIndexer implements Indexer {

  /**
   *  Given a database and a Lucene writer, this method will add content to the
   *  searchable index
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT project_id, title, shortdescription, requestedby, requesteddept, modified " +
        "FROM projects " +
        "WHERE project_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      Project project = new Project();
      project.setId(rs.getInt("project_id"));
      project.setTitle(rs.getString("title"));
      project.setShortDescription(rs.getString("shortdescription"));
      project.setRequestedBy(rs.getString("requestedby"));
      project.setRequestedByDept(rs.getString("requesteddept"));
      project.setModified(rs.getTimestamp("modified"));
      // add the document
      ProjectIndexer.add(writer, project, false);
    }
    rs.close();
    pst.close();
    System.out.println("ProjectIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  project          Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Project project, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "project"));
    document.add(Field.Keyword("projectKeyId", String.valueOf(project.getId())));
    document.add(Field.Keyword("projectId", String.valueOf(project.getId())));
    document.add(Field.Text("title", project.getTitle()));
    document.add(Field.Text("contents",
        project.getTitle() + " " +
        project.getShortDescription() + " " +
        project.getRequestedBy() + " " +
        project.getRequestedByDept()));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(project.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("ProjectIndexer-> Added: " + project.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the ProjectIndexer class
   *
   *@param  project  Description of the Parameter
   *@return          The searchTerm value
   */
  public static Term getSearchTerm(Project project) {
    Term searchTerm = new Term("projectKeyId", String.valueOf(project.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the ProjectIndexer class
   *
   *@param  project  Description of the Parameter
   *@return          The deleteTerm value
   */
  public static Term getDeleteTerm(Project project) {
    Term searchTerm = new Term("projectId", String.valueOf(project.getId()));
    return searchTerm;
  }
}


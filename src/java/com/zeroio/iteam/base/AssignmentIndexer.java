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
 *@version    $Id: AssignmentIndexer.java,v 1.2 2004/07/21 19:00:43 mrajkowski
 *      Exp $
 */
public class AssignmentIndexer implements Indexer {

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
        "SELECT assignment_id, project_id, role, technology, requirement_id, modified " +
        "FROM project_assignments " +
        "WHERE assignment_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      Assignment assignment = new Assignment();
      assignment.setId(rs.getInt("assignment_id"));
      assignment.setProjectId(rs.getInt("project_id"));
      assignment.setRole(rs.getString("role"));
      assignment.setTechnology(rs.getString("technology"));
      assignment.setRequirementId(rs.getInt("requirement_id"));
      assignment.setModified(rs.getTimestamp("modified"));
      // add the document
      AssignmentIndexer.add(writer, assignment, false);
    }
    rs.close();
    pst.close();
    System.out.println("AssignmentIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  assignment       Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Assignment assignment, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "activity"));
    document.add(Field.Keyword("assignmentId", String.valueOf(assignment.getId())));
    document.add(Field.Keyword("requirementId", String.valueOf(assignment.getRequirementId())));
    document.add(Field.Keyword("projectId", String.valueOf(assignment.getProjectId())));
    document.add(Field.Text("title", assignment.getRole()));
    document.add(Field.Text("contents",
        assignment.getRole() + " " +
        assignment.getTechnology()));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(assignment.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("AssignmentIndexer-> Added: " + assignment.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the AssignmentIndexer class
   *
   *@param  assignment  Description of the Parameter
   *@return             The searchTerm value
   */
  public static Term getSearchTerm(Assignment assignment) {
    Term searchTerm = new Term("assignmentId", String.valueOf(assignment.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the AssignmentIndexer class
   *
   *@param  assignment  Description of the Parameter
   *@return             The deleteTerm value
   */
  public static Term getDeleteTerm(Assignment assignment) {
    Term searchTerm = new Term("assignmentId", String.valueOf(assignment.getId()));
    return searchTerm;
  }
}


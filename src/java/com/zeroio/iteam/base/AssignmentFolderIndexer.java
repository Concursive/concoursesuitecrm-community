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
 *@version    $Id: AssignmentFolderIndexer.java,v 1.2 2004/05/28 03:00:20 matt
 *      Exp $
 */
public class AssignmentFolderIndexer implements Indexer {

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
        "SELECT f.folder_id, r.project_id, f.name, f.description, f.requirement_id, f.modified " +
        "FROM project_assignments_folder f, " +
        "project_requirements r " +
        "WHERE r.requirement_id = f.requirement_id ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      AssignmentFolder thisAssignmentFolder = new AssignmentFolder();
      thisAssignmentFolder.setId(rs.getInt("folder_id"));
      thisAssignmentFolder.setProjectId(rs.getInt("project_id"));
      thisAssignmentFolder.setName(rs.getString("name"));
      thisAssignmentFolder.setDescription(rs.getString("description"));
      thisAssignmentFolder.setRequirementId(rs.getInt("requirement_id"));
      thisAssignmentFolder.setModified(rs.getTimestamp("modified"));
      // add to index
      AssignmentFolderIndexer.add(writer, thisAssignmentFolder, false);
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AssignmentFolderIndexer-> Finished: " + count);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  writer            Description of the Parameter
   *@param  assignmentFolder  Description of the Parameter
   *@param  modified          Description of the Parameter
   *@exception  IOException   Description of the Exception
   */
  public static void add(IndexWriter writer, AssignmentFolder assignmentFolder, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "activityFolder"));
    document.add(Field.Keyword("assignmentFolderId", String.valueOf(assignmentFolder.getId())));
    document.add(Field.Keyword("requirementId", String.valueOf(assignmentFolder.getRequirementId())));
    document.add(Field.Keyword("projectId", String.valueOf(assignmentFolder.getProjectId())));
    document.add(Field.Text("title", assignmentFolder.getName()));
    document.add(Field.Text("contents",
        assignmentFolder.getName() + " " +
        ContentUtils.toText(assignmentFolder.getDescription())));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(assignmentFolder.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("AssignmentFolderIndexer-> Added: " + assignmentFolder.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the AssignmentFolderIndexer class
   *
   *@param  assignmentFolder  Description of the Parameter
   *@return                   The searchTerm value
   */
  public static Term getSearchTerm(AssignmentFolder assignmentFolder) {
    Term searchTerm = new Term("assignmentFolderId", String.valueOf(assignmentFolder.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the AssignmentFolderIndexer class
   *
   *@param  assignmentFolder  Description of the Parameter
   *@return                   The deleteTerm value
   */
  public static Term getDeleteTerm(AssignmentFolder assignmentFolder) {
    Term searchTerm = new Term("assignmentFolderId", String.valueOf(assignmentFolder.getId()));
    return searchTerm;
  }
}


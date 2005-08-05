/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski and Team Elements
 */
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.utils.ContentUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.aspcfs.utils.DatabaseUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for working with the Lucene search engine
 *
 * @author matt rajkowski
 * @version $Id$
 * @created December 17, 2004
 */
public class AssignmentNoteIndexer implements Indexer {

  /**
   * Given a database and a Lucene writer, this method will add content to the
   * searchable index
   *
   * @param writer  Description of the Parameter
   * @param db      Description of the Parameter
   * @param context
   * @throws SQLException Description of the Exception
   * @throws IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db, ActionContext context) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT s.status_id, s.assignment_id, s.user_id, s.description, s.status_date, " +
        "a.project_id " +
        "FROM project_assignments_status s " +
        "LEFT JOIN project_assignments a ON s.assignment_id = a.assignment_id " +
        "WHERE s.status_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      AssignmentNote assignmentNote = new AssignmentNote();
      assignmentNote.setId(rs.getInt("status_id"));
      assignmentNote.setAssignmentId(rs.getInt("assignment_id"));
      assignmentNote.setUserId(rs.getInt("user_id"));
      assignmentNote.setDescription(rs.getString("description"));
      assignmentNote.setEntered(rs.getTimestamp("status_date"));
      assignmentNote.setProjectId(rs.getInt("project_id"));
      // add the document
      AssignmentNoteIndexer.add(writer, assignmentNote, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("AssignmentNoteIndexer-> Finished: " + count);
  }


  /**
   * Description of the Method
   *
   * @param writer         Description of the Parameter
   * @param assignmentNote Description of the Parameter
   * @param modified       Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public static void add(IndexWriter writer, AssignmentNote assignmentNote, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "activitynote"));
    document.add(
        Field.Keyword(
            "assignmentNoteId", String.valueOf(assignmentNote.getId())));
    document.add(
        Field.Keyword(
            "assignmentId", String.valueOf(assignmentNote.getAssignmentId())));
    document.add(
        Field.Keyword(
            "projectId", String.valueOf(assignmentNote.getProjectId())));
    document.add(
        Field.Text(
            "title", ContentUtils.toText(assignmentNote.getDescription())));
    document.add(
        Field.Text(
            "contents",
            ContentUtils.toText(assignmentNote.getDescription())));
    if (modified) {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(
                  assignmentNote.getEntered().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println(
          "AssignmentNoteIndexer-> Added: " + assignmentNote.getId());
    }
  }


  /**
   * Gets the searchTerm attribute of the AssignmentIndexer class
   *
   * @param assignmentNote Description of the Parameter
   * @return The searchTerm value
   */
  public static Term getSearchTerm(AssignmentNote assignmentNote) {
    Term searchTerm = new Term(
        "assignmentNoteId", String.valueOf(assignmentNote.getId()));
    return searchTerm;
  }


  /**
   * Gets the deleteTerm attribute of the AssignmentIndexer class
   *
   * @param assignmentNote Description of the Parameter
   * @return The deleteTerm value
   */
  public static Term getDeleteTerm(AssignmentNote assignmentNote) {
    Term searchTerm = new Term(
        "assignmentNoteId", String.valueOf(assignmentNote.getId()));
    return searchTerm;
  }
}


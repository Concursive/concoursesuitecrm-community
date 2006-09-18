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
 * @created May 27, 2004
 */
public class IssueIndexer implements Indexer {

  /**
   * Given a database and a Lucene writer, this method will add content to the
   * searchable index
   *
   * @param writer Description of the Parameter
   * @param db     Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db, ActionContext context) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT issue_id, project_id, category_id, subject, " + DatabaseUtils.addQuotes(db, "message")+ ", modified " +
        "FROM project_issues " +
        "WHERE project_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      Issue issue = new Issue();
      issue.setId(rs.getInt("issue_id"));
      issue.setProjectId(rs.getInt("project_id"));
      issue.setCategoryId(rs.getInt("category_id"));
      issue.setSubject(rs.getString("subject"));
      issue.setBody(rs.getString("message"));
      issue.setModified(rs.getTimestamp("modified"));
      // add the document
      IssueIndexer.add(writer, issue, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("IssueIndexer-> Finished: " + count);
  }


  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param issue    Description of the Parameter
   * @param modified Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public static void add(IndexWriter writer, Issue issue, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "issue"));
    document.add(Field.Keyword("issueKeyId", String.valueOf(issue.getId())));
    document.add(Field.Keyword("issueId", String.valueOf(issue.getId())));
    document.add(
        Field.Keyword(
            "issueCategoryId", String.valueOf(issue.getCategoryId())));
    document.add(
        Field.Keyword("projectId", String.valueOf(issue.getProjectId())));
    document.add(Field.Text("title", issue.getSubject()));
    document.add(
        Field.Text(
            "contents",
            issue.getSubject() + " " +
        ContentUtils.toText(issue.getBody())));
    if (modified) {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(issue.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("IssueIndexer-> Added: " + issue.getId());
    }
  }


  /**
   * Gets the searchTerm attribute of the IssueIndexer class
   *
   * @param issue Description of the Parameter
   * @return The searchTerm value
   */
  public static Term getSearchTerm(Issue issue) {
    Term searchTerm = new Term("issueKeyId", String.valueOf(issue.getId()));
    return searchTerm;
  }


  /**
   * Gets the deleteTerm attribute of the IssueIndexer class
   *
   * @param issue Description of the Parameter
   * @return The deleteTerm value
   */
  public static Term getDeleteTerm(Issue issue) {
    Term searchTerm = new Term("issueId", String.valueOf(issue.getId()));
    return searchTerm;
  }
}


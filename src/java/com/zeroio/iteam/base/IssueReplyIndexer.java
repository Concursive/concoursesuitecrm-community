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
 * @version $Id: IssueReplyIndexer.java,v 1.2 2004/07/21 19:00:43 mrajkowski
 *          Exp $
 * @created May 27, 2004
 */
public class IssueReplyIndexer implements Indexer {

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
        "SELECT r.reply_id, r.issue_id, i.project_id, i.category_id, r.subject, r.message, r.modified " +
        "FROM project_issue_replies r " +
        "LEFT JOIN project_issues i ON r.issue_id = i.issue_id " +
        "WHERE i.project_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      IssueReply issueReply = new IssueReply();
      issueReply.setId(rs.getInt("reply_id"));
      issueReply.setIssueId(rs.getInt("issue_id"));
      issueReply.setProjectId(rs.getInt("project_id"));
      issueReply.setCategoryId(rs.getInt("category_id"));
      issueReply.setSubject(rs.getString("subject"));
      issueReply.setBody(rs.getString("message"));
      issueReply.setModified(rs.getTimestamp("modified"));
      // add the document
      IssueReplyIndexer.add(writer, issueReply, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("IssueReplyIndexer-> Finished: " + count);
  }


  /**
   * Description of the Method
   *
   * @param writer     Description of the Parameter
   * @param issueReply Description of the Parameter
   * @param modified   Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public static void add(IndexWriter writer, IssueReply issueReply, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "issuereply"));
    document.add(
        Field.Keyword("issueReplyId", String.valueOf(issueReply.getId())));
    document.add(
        Field.Keyword("issueId", String.valueOf(issueReply.getIssueId())));
    document.add(
        Field.Keyword(
            "issueCategoryId", String.valueOf(issueReply.getCategoryId())));
    document.add(
        Field.Keyword("projectId", String.valueOf(issueReply.getProjectId())));
    document.add(Field.Text("title", issueReply.getSubject()));
    document.add(
        Field.Text(
            "contents",
            issueReply.getSubject() + " " +
        ContentUtils.toText(issueReply.getBody())));
    if (modified) {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(issueReply.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("IssueReplyIndexer-> Added: " + issueReply.getId());
    }
  }


  /**
   * Gets the searchTerm attribute of the IssueReplyIndexer class
   *
   * @param issueReply Description of the Parameter
   * @return The searchTerm value
   */
  public static Term getSearchTerm(IssueReply issueReply) {
    Term searchTerm = new Term(
        "issueReplyId", String.valueOf(issueReply.getId()));
    return searchTerm;
  }


  /**
   * Gets the deleteTerm attribute of the IssueReplyIndexer class
   *
   * @param issueReply Description of the Parameter
   * @return The deleteTerm value
   */
  public static Term getDeleteTerm(IssueReply issueReply) {
    Term searchTerm = new Term(
        "issueReplyId", String.valueOf(issueReply.getId()));
    return searchTerm;
  }
}


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
 *@version    $Id: IssueCategoryIndexer.java,v 1.4 2004/06/01 02:54:52 matt Exp
 *      $
 */
public class IssueCategoryIndexer implements Indexer {

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
        "SELECT category_id, project_id, subject, description, modified " +
        "FROM project_issues_categories " +
        "WHERE project_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      IssueCategory issueCategory = new IssueCategory();
      issueCategory.setId(rs.getInt("category_id"));
      issueCategory.setProjectId(rs.getInt("project_id"));
      issueCategory.setSubject(rs.getString("subject"));
      issueCategory.setDescription(rs.getString("description"));
      issueCategory.setModified(rs.getTimestamp("modified"));
      // add the document
      IssueCategoryIndexer.add(writer, issueCategory, false);
    }
    rs.close();
    pst.close();
    System.out.println("IssueCategoryIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  issueCategory    Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, IssueCategory issueCategory, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "issueCategory"));
    document.add(Field.Keyword("issueCategoryKeyId", String.valueOf(issueCategory.getId())));
    document.add(Field.Keyword("issueCategoryId", String.valueOf(issueCategory.getId())));
    document.add(Field.Keyword("projectId", String.valueOf(issueCategory.getProjectId())));
    document.add(Field.Text("title", issueCategory.getSubject()));
    document.add(Field.Text("contents",
        issueCategory.getSubject() + " " +
        ContentUtils.toText(issueCategory.getDescription())));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(issueCategory.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("IssueCategoryIndexer-> Added: " + issueCategory.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the IssueCategoryIndexer class
   *
   *@param  issueCategory  Description of the Parameter
   *@return                The searchTerm value
   */
  public static Term getSearchTerm(IssueCategory issueCategory) {
    Term searchTerm = new Term("issueCategoryKeyId", String.valueOf(issueCategory.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the IssueCategoryIndexer class
   *
   *@param  issueCategory  Description of the Parameter
   *@return                The deleteTerm value
   */
  public static Term getDeleteTerm(IssueCategory issueCategory) {
    Term searchTerm = new Term("issueCategoryId", String.valueOf(issueCategory.getId()));
    return searchTerm;
  }
}


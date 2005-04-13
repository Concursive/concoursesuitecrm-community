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
import org.aspcfs.utils.DatabaseUtils;
import java.text.*;
import com.zeroio.utils.ContentUtils;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id: NewsArticleIndexer.java,v 1.2 2004/07/21 19:00:43 mrajkowski
 *      Exp $
 */
public class NewsArticleIndexer implements Indexer {

  /**
   *  Given a database and a Lucene writer, this method will add content to the
   *  searchable index
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db, ActionContext context) throws SQLException, IOException {
    int count = 0;
    // TODO: Take into account the various status codes for news, as well as date ranges
    PreparedStatement pst = db.prepareStatement(
        "SELECT news_id, project_id, subject, intro, message, start_date, status " +
        "FROM project_news " +
        "WHERE project_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      NewsArticle newsArticle = new NewsArticle();
      newsArticle.setId(rs.getInt("news_id"));
      newsArticle.setProjectId(rs.getInt("project_id"));
      newsArticle.setSubject(rs.getString("subject"));
      newsArticle.setIntro(rs.getString("intro"));
      newsArticle.setMessage(rs.getString("message"));
      newsArticle.setStartDate(rs.getTimestamp("start_date"));
      newsArticle.setStatus(DatabaseUtils.getInt(rs, "status"));
      // add to index
      NewsArticleIndexer.add(writer, newsArticle, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("NewsArticleIndexer-> Finished: " + count);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  article          Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, NewsArticle article, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "news"));
    document.add(Field.Keyword("newsId", String.valueOf(article.getId())));
    document.add(Field.Keyword("projectId", String.valueOf(article.getProjectId())));
    document.add(Field.Text("title", article.getSubject()));
    document.add(Field.Text("contents",
        article.getSubject() + " " +
        ContentUtils.toText(ContentUtils.stripHTML(article.getIntro())) + " " +
        ContentUtils.toText(ContentUtils.stripHTML(article.getMessage()))));
    if (article.getStartDate() != null) {
      document.add(Field.Keyword("modified", String.valueOf(article.getStartDate().getTime())));
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
      document.add(Field.Keyword("newsDate", String.valueOf(formatter.format(article.getStartDate()))));
    }
    document.add(Field.Keyword("newsStatus", String.valueOf(article.getStatus())));
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("NewsArticleIndexer-> Added: " + article.getId());
    }
  }


  /**
   *  Gets the unique searchTerm attribute of the NewsArticleIndexer class
   *
   *@param  article  Description of the Parameter
   *@return          The searchTerm value
   */
  public static Term getSearchTerm(NewsArticle article) {
    Term searchTerm = new Term("newsId", String.valueOf(article.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the NewsArticleIndexer class
   *
   *@param  article  Description of the Parameter
   *@return          The deleteTerm value
   */
  public static Term getDeleteTerm(NewsArticle article) {
    Term searchTerm = new Term("newsId", String.valueOf(article.getId()));
    return searchTerm;
  }
}


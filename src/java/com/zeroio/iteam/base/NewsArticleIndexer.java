/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski and Team Elements
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

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
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
  public static void add(IndexWriter writer, Connection db) throws SQLException, IOException {
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


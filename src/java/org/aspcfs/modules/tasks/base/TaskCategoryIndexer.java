/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package org.aspcfs.modules.tasks.base;

import com.zeroio.iteam.base.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import java.sql.*;
import java.io.IOException;
import org.aspcfs.modules.tasks.base.TaskCategory;
import org.aspcfs.modules.base.Constants;
import com.zeroio.utils.ContentUtils;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public class TaskCategoryIndexer implements Indexer {

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
        "SELECT code, project_id, description " +
        "FROM lookup_task_category t, taskcategory_project c " +
        "WHERE t.code = c.category_id ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      TaskCategory taskCategory = new TaskCategory();
      taskCategory.setId(rs.getInt("code"));
      taskCategory.setLinkModuleId(Constants.TASK_CATEGORY_PROJECTS);
      taskCategory.setLinkItemId(rs.getInt("project_id"));
      taskCategory.setDescription(rs.getString("description"));
      // add the document
      TaskCategoryIndexer.add(writer, taskCategory, false);
    }
    rs.close();
    pst.close();
    System.out.println("TaskCategoryIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  taskCategory     Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, TaskCategory taskCategory, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "listCategory"));
    document.add(Field.Keyword("listCategoryKeyId", String.valueOf(taskCategory.getId())));
    document.add(Field.Keyword("listCategoryId", String.valueOf(taskCategory.getId())));
    document.add(Field.Keyword("projectId", String.valueOf(taskCategory.getLinkItemId())));
    document.add(Field.Text("title", taskCategory.getDescription()));
    document.add(Field.Text("contents",
        taskCategory.getDescription()));
    /*
     *  if (modified) {
     *  document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
     *  } else {
     *  document.add(Field.Keyword("modified", String.valueOf(taskCategory.getModified().getTime())));
     *  }
     */
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("TaskCategoryIndexer-> Added: " + taskCategory.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the TaskCategoryIndexer class
   *
   *@param  taskCategory  Description of the Parameter
   *@return               The searchTerm value
   */
  public static Term getSearchTerm(TaskCategory taskCategory) {
    Term searchTerm = new Term("listCategoryKeyId", String.valueOf(taskCategory.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the TaskCategoryIndexer class
   *
   *@param  taskCategory  Description of the Parameter
   *@return               The deleteTerm value
   */
  public static Term getDeleteTerm(TaskCategory taskCategory) {
    Term searchTerm = new Term("listCategoryId", String.valueOf(taskCategory.getId()));
    return searchTerm;
  }
}


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
package org.aspcfs.modules.tasks.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Indexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id: TaskCategoryIndexer.java,v 1.2 2004/07/21 19:00:44 mrajkowski
 *      Exp $
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
  public static void add(IndexWriter writer, Connection db, ActionContext context) throws SQLException, IOException {
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
      DatabaseUtils.renewConnection(context, db);
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


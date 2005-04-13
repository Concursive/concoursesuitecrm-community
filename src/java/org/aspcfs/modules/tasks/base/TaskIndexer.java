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
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public class TaskIndexer implements Indexer {

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
        "SELECT t.task_id, t.category_id, project_id, description, notes, modified " +
        "FROM task t, tasklink_project l " +
        "WHERE t.task_id = l.task_id ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      Task task = new Task();
      task.setId(rs.getInt("task_id"));
      task.setCategoryId(rs.getInt("category_id"));
      task.setProjectId(rs.getInt("project_id"));
      task.setDescription(rs.getString("description"));
      task.setNotes(rs.getString("notes"));
      task.setModified(rs.getTimestamp("modified"));
      // add the document
      TaskIndexer.add(writer, task, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("TaskIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  task             Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Task task, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "list"));
    document.add(Field.Keyword("listId", String.valueOf(task.getId())));
    document.add(Field.Keyword("listCategoryId", String.valueOf(task.getCategoryId())));
    document.add(Field.Keyword("projectId", String.valueOf(task.getProjectId())));
    document.add(Field.Text("title", task.getDescription()));
    document.add(Field.Text("contents",
        task.getDescription() + " " +
        ContentUtils.toText(task.getNotes())));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(task.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("TaskIndexer-> Added: " + task.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the TaskIndexer class
   *
   *@param  task  Description of the Parameter
   *@return       The searchTerm value
   */
  public static Term getSearchTerm(Task task) {
    Term searchTerm = new Term("listId", String.valueOf(task.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the TaskIndexer class
   *
   *@param  task  Description of the Parameter
   *@return       The deleteTerm value
   */
  public static Term getDeleteTerm(Task task) {
    Term searchTerm = new Term("listId", String.valueOf(task.getId()));
    return searchTerm;
  }
}


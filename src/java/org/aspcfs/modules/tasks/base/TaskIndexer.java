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
import org.aspcfs.modules.tasks.base.Task;
import com.zeroio.utils.ContentUtils;

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
  public static void add(IndexWriter writer, Connection db) throws SQLException, IOException {
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


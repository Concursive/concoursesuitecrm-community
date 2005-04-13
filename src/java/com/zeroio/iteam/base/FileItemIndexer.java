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
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id: FileItemIndexer.java,v 1.3 2004/09/13 19:01:28 mrajkowski Exp
 *      $
 */
public class FileItemIndexer implements Indexer {

  /**
   *  Given a database and a Lucene writer, this method will add content to the
   *  searchable index
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  path              Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db, String path, ActionContext context) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_id, folder_id, link_module_id, link_item_id, subject, client_filename, modified, size, filename " +
        "FROM project_files " +
        "WHERE link_module_id = ? " + 
        "OR link_module_id = ? ");
    pst.setInt(1, Constants.PROJECTS_FILES);
    pst.setInt(2, Constants.DOCUMENTS_DOCUMENTS );
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      FileItem fileItem = new FileItem();
      fileItem.setId(rs.getInt("item_id"));
      fileItem.setFolderId(rs.getInt("folder_id"));
      fileItem.setLinkModuleId(rs.getInt("link_module_id"));
      fileItem.setLinkItemId(rs.getInt("link_item_id"));
      fileItem.setSubject(rs.getString("subject"));
      fileItem.setClientFilename(rs.getString("client_filename"));
      fileItem.setModified(rs.getTimestamp("modified"));
      fileItem.setSize(rs.getInt("size"));
      fileItem.setFilename(rs.getString("filename"));
      fileItem.setDirectory(path);
      // add the document
      FileItemIndexer.add(writer, fileItem, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("FileItemIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  fileItem         Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, FileItem fileItem, boolean modified) throws IOException {
    String contents = ContentUtils.getText(fileItem);
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "file"));
    document.add(Field.Keyword("fileId", String.valueOf(fileItem.getId())));
    document.add(Field.Keyword("folderId", String.valueOf(fileItem.getFolderId())));
    if (fileItem.getLinkModuleId() == Constants.PROJECTS_FILES) {
      document.add(Field.Keyword("projectId", String.valueOf(fileItem.getLinkItemId())));
    } else if (fileItem.getLinkModuleId() == Constants.DOCUMENTS_DOCUMENTS) {
      document.add(Field.Keyword("documentStoreId", String.valueOf(fileItem.getLinkItemId())));
    }
    document.add(Field.Text("title", fileItem.getSubject() + " - " + fileItem.getClientFilename()));
    document.add(Field.Text("filename", fileItem.getClientFilename()));
    document.add(Field.Text("extension", fileItem.getExtension()));
    document.add(Field.Text("contents",
        fileItem.getSubject() + " " +
        fileItem.getClientFilename() + " " +
        ContentUtils.toText(contents)));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(fileItem.getModified().getTime())));
    }
    document.add(Field.Keyword("size", String.valueOf(fileItem.getSize())));
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("FileItemIndexer-> Added: " + fileItem.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the FileItemIndexer class
   *
   *@param  fileItem  Description of the Parameter
   *@return           The searchTerm value
   */
  public static Term getSearchTerm(FileItem fileItem) {
    Term searchTerm = new Term("fileId", String.valueOf(fileItem.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the FileItemIndexer class
   *
   *@param  fileItem  Description of the Parameter
   *@return           The deleteTerm value
   */
  public static Term getDeleteTerm(FileItem fileItem) {
    Term searchTerm = new Term("fileId", String.valueOf(fileItem.getId()));
    return searchTerm;
  }
}


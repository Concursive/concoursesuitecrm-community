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
import com.zeroio.utils.ContentUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public class FileItemIndexer implements Indexer {

  /**
   *  Given a database and a Lucene writer, this method will add content to the
   *  searchable index
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db, String path) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_id, folder_id, link_item_id, subject, client_filename, modified, size, filename " +
        "FROM project_files " +
        "WHERE link_module_id = ? ");
    pst.setInt(1, Constants.PROJECTS_FILES);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      FileItem fileItem = new FileItem();
      fileItem.setId(rs.getInt("item_id"));
      fileItem.setFolderId(rs.getInt("folder_id"));
      fileItem.setLinkItemId(rs.getInt("link_item_id"));
      fileItem.setSubject(rs.getString("subject"));
      fileItem.setClientFilename(rs.getString("client_filename"));
      fileItem.setModified(rs.getTimestamp("modified"));
      fileItem.setSize(rs.getInt("size"));
      fileItem.setFilename(rs.getString("filename"));
      fileItem.setDirectory(path);
      // add the document
      FileItemIndexer.add(writer, fileItem, false);
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
    document.add(Field.Keyword("projectId", String.valueOf(fileItem.getLinkItemId())));
    document.add(Field.Text("title", fileItem.getSubject() + " - " + fileItem.getClientFilename()));
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
  
  public static Term getDeleteTerm(FileItem fileItem) {
    Term searchTerm = new Term("fileId", String.valueOf(fileItem.getId()));
    return searchTerm;
  }
}


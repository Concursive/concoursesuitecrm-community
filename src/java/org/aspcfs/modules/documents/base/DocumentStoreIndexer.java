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
package org.aspcfs.modules.documents.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Indexer;
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
 * @author
 * @version $Id:  Exp
 *          $
 * @created
 */
public class DocumentStoreIndexer implements Indexer {

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
    PreparedStatement pst = db.prepareStatement(
        "SELECT document_store_id, title, shortdescription, requestedby, requesteddept, modified " +
        "FROM document_store " +
        "WHERE document_store_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      // read the record
      DocumentStore documentStore = new DocumentStore();
      documentStore.setId(rs.getInt("document_store_id"));
      documentStore.setTitle(rs.getString("title"));
      documentStore.setShortDescription(rs.getString("shortdescription"));
      documentStore.setRequestedBy(rs.getString("requestedby"));
      documentStore.setRequestedDept(rs.getString("requesteddept"));
      documentStore.setModified(rs.getTimestamp("modified"));
      // add the document
      DocumentStoreIndexer.add(writer, documentStore, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param writer        Description of the Parameter
   * @param documentStore Description of the Parameter
   * @param modified      Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public static void add(IndexWriter writer, DocumentStore documentStore, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "documentstoredetails"));
    document.add(
        Field.Keyword(
            "documentStoreKeyId", String.valueOf(documentStore.getId())));
    document.add(
        Field.Keyword(
            "documentStoreId", String.valueOf(documentStore.getId())));
    document.add(
        Field.Text(
            "trashed", ((documentStore.getTrashedDate() != null) ? "Trashed" : "")));
    document.add(Field.Text("title", documentStore.getTitle()));
    document.add(
        Field.Text(
            "contents",
            documentStore.getTitle() + " " +
        documentStore.getShortDescription() + " " +
        documentStore.getRequestedBy() + " " +
        documentStore.getRequestedDept()));
    if (modified) {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(
                  documentStore.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println(
          "DocumentStoreIndexer-> Added: " + documentStore.getId());
    }
  }


  /**
   * Gets the searchTerm attribute of the DocumentStoreIndexer class
   *
   * @param documentStore Description of the Parameter
   * @return The searchTerm value
   */
  public static Term getSearchTerm(DocumentStore documentStore) {
    Term searchTerm = new Term(
        "documentStoreKeyId", String.valueOf(documentStore.getId()));
    return searchTerm;
  }


  /**
   * Gets the deleteTerm attribute of the DocumentStoreIndexer class
   *
   * @param documentStore Description of the Parameter
   * @return The deleteTerm value
   */
  public static Term getDeleteTerm(DocumentStore documentStore) {
    Term searchTerm = new Term(
        "documentStoreId", String.valueOf(documentStore.getId()));
    return searchTerm;
  }
}


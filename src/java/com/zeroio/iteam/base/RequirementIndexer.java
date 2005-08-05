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

import com.darkhorseventures.framework.actions.ActionContext;
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
 * Class for working with the Lucene search engine
 *
 * @author matt rajkowski
 * @version $Id: RequirementIndexer.java,v 1.2 2004/07/21 19:00:43 mrajkowski
 *          Exp $
 * @created May 27, 2004
 */
public class RequirementIndexer implements Indexer {

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
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT requirement_id, project_id, shortdescription, description, submittedBy, departmentBy, modified " +
        "FROM project_requirements " +
        "WHERE requirement_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      Requirement requirement = new Requirement();
      requirement.setId(rs.getInt("requirement_id"));
      requirement.setProjectId(rs.getInt("project_id"));
      requirement.setShortDescription(rs.getString("shortdescription"));
      requirement.setDescription(rs.getString("description"));
      requirement.setSubmittedBy(rs.getString("submittedBy"));
      requirement.setDepartmentBy(rs.getString("departmentBy"));
      requirement.setModified(rs.getTimestamp("modified"));
      // add the document
      RequirementIndexer.add(writer, requirement, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("RequirementIndexer-> Finished: " + count);
  }


  /**
   * Description of the Method
   *
   * @param writer      Description of the Parameter
   * @param requirement Description of the Parameter
   * @param modified    Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public static void add(IndexWriter writer, Requirement requirement, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "outline"));
    document.add(
        Field.Keyword("requirementKeyId", String.valueOf(requirement.getId())));
    document.add(
        Field.Keyword("requirementId", String.valueOf(requirement.getId())));
    document.add(
        Field.Keyword("projectId", String.valueOf(requirement.getProjectId())));
    document.add(Field.Text("title", requirement.getShortDescription()));
    document.add(
        Field.Text(
            "contents",
            requirement.getShortDescription() + " " +
        ContentUtils.toText(requirement.getDescription()) + " " +
        requirement.getSubmittedBy() + " " +
        requirement.getDepartmentBy()));
    if (modified) {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(
          Field.Keyword(
              "modified", String.valueOf(requirement.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("RequirementIndexer-> Added: " + requirement.getId());
    }
  }


  /**
   * Gets the searchTerm attribute of the RequirementIndexer class
   *
   * @param requirement Description of the Parameter
   * @return The searchTerm value
   */
  public static Term getSearchTerm(Requirement requirement) {
    Term searchTerm = new Term(
        "requirementKeyId", String.valueOf(requirement.getId()));
    return searchTerm;
  }


  /**
   * Gets the deleteTerm attribute of the RequirementIndexer class
   *
   * @param requirement Description of the Parameter
   * @return The deleteTerm value
   */
  public static Term getDeleteTerm(Requirement requirement) {
    Term searchTerm = new Term(
        "requirementId", String.valueOf(requirement.getId()));
    return searchTerm;
  }
}


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

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public class RequirementIndexer implements Indexer {

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
    }
    rs.close();
    pst.close();
    System.out.println("RequirementIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  requirement      Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Requirement requirement, boolean modified) throws IOException {
    // add the document
    Document document = new Document();
    document.add(Field.Keyword("type", "outline"));
    document.add(Field.Keyword("requirementKeyId", String.valueOf(requirement.getId())));
    document.add(Field.Keyword("requirementId", String.valueOf(requirement.getId())));
    document.add(Field.Keyword("projectId", String.valueOf(requirement.getProjectId())));
    document.add(Field.Text("title", requirement.getShortDescription()));
    document.add(Field.Text("contents",
        requirement.getShortDescription() + " " +
        ContentUtils.toText(requirement.getDescription()) + " " +
        requirement.getSubmittedBy() + " " +
        requirement.getDepartmentBy()));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(requirement.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("RequirementIndexer-> Added: " + requirement.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the RequirementIndexer class
   *
   *@param  requirement  Description of the Parameter
   *@return              The searchTerm value
   */
  public static Term getSearchTerm(Requirement requirement) {
    Term searchTerm = new Term("requirementKeyId", String.valueOf(requirement.getId()));
    return searchTerm;
  }
  
  public static Term getDeleteTerm(Requirement requirement) {
    Term searchTerm = new Term("requirementId", String.valueOf(requirement.getId()));
    return searchTerm;
  }
}


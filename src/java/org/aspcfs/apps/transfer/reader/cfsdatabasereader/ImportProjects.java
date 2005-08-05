/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import com.zeroio.iteam.base.*;
import org.aspcfs.apps.transfer.DataWriter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class ImportProjects implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;


  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;
    writer.setAutoCommit(true);

    logger.info("ImportBaseData-> Inserting Project Lookups");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectActivity");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectIssues");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectLoe");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectPriority");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectStatus");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Projects");
    ProjectList projectList = new ProjectList();
    projectList.setGroupId(-1);
    projectList.buildList(db);
    processOK = mappings.saveList(writer, projectList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Requirements");
    RequirementList requirementList = new RequirementList();
    requirementList.buildList(db);
    processOK = mappings.saveList(writer, requirementList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Assignments");
    AssignmentList assignmentList = new AssignmentList();
    assignmentList.buildList(db);
    processOK = mappings.saveList(writer, assignmentList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Issues");
    IssueList issueList = new IssueList();
    issueList.buildList(db);
    processOK = mappings.saveList(writer, issueList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Issue Replies");
    IssueReplyList issueReplyList = new IssueReplyList();
    issueReplyList.buildList(db);
    processOK = mappings.saveList(writer, issueReplyList, "insert");

    logger.info("ImportBaseData-> Inserting Team Members");
    TeamMemberList teamMemberList = new TeamMemberList();
    teamMemberList.buildList(db);
    processOK = mappings.saveList(writer, teamMemberList, "insert");

    //Documents are inserting in bulk later
    return true;
  }

}


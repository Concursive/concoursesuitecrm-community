package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;
import java.util.*;

public class ImportProjects implements CFSDatabaseReaderImportModule {
  
  DataWriter writer = null;
  PropertyMapList mappings = null;
  
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer; 
    this.mappings = mappings;
    boolean processOK = true;
    writer.setAutoCommit(true);
    
    logger.info("ImportBaseData-> Inserting Project Lookups");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "lookupProjectActivity");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "lookupProjectIssues");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "lookupProjectLoe");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "lookupProjectPriority");
    if (!processOK) {
      return false;
    }
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "lookupProjectStatus");
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


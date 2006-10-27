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
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

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

    logger.info("ImportProjects-> Inserting Project Lookups");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectActivity");
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

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectLoe");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectRole");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupProjectCategory");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupNewsTemplate");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Project Permission Categories");
    PermissionCategoryLookupList categories = new PermissionCategoryLookupList();
    categories.buildList(db);
    processOK = mappings.saveList(writer, categories, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Project Permissions");
    PermissionLookupList permissions = new PermissionLookupList();
    permissions.buildList(db);
    processOK = mappings.saveList(writer, permissions, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Projects");
    ProjectList projectList = new ProjectList();
    projectList.setGroupId(-1);
    projectList.buildList(db);
    processOK = mappings.saveList(writer, projectList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Requirements");
    RequirementList requirementList = new RequirementList();
    requirementList.buildList(db);
    processOK = mappings.saveList(writer, requirementList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Assignments folders");
    AssignmentFolderList assignmentFolderList = new AssignmentFolderList();
    assignmentFolderList.buildList(db);
    processOK = this.saveAssignmentFolderList(db, assignmentFolderList);
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Assignments");
    AssignmentList assignmentList = new AssignmentList();
    assignmentList.buildList(db);
    processOK = this.saveAssignmentList(db, assignmentList);
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Assignments status");
    AssignmentNoteList assignmentNoteList = new AssignmentNoteList();
    assignmentNoteList.buildList(db);
    processOK = mappings.saveList(writer, assignmentNoteList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Issue Categories");
    IssueCategoryList issueCategoryList = new IssueCategoryList();
    issueCategoryList.buildList(db);
    processOK = mappings.saveList(writer, issueCategoryList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Issues");
    IssueList issueList = new IssueList();
    issueList.buildList(db);
    processOK = mappings.saveList(writer, issueList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProjects-> Inserting Issue Replies");
    IssueReplyList issueReplyList = new IssueReplyList();
    issueReplyList.buildList(db);
    processOK = mappings.saveList(writer, issueReplyList, "insert");

    logger.info("ImportProjects-> Inserting project folders");
    FileFolderList fileFolderList = new FileFolderList();
    fileFolderList.buildList(db);
    processOK = mappings.saveList(writer, fileFolderList, "insert");

    logger.info("ImportProjects-> Inserting project files");
    FileItemList fileItemList = new FileItemList();
    fileItemList.buildList(db);
    processOK = mappings.saveList(writer, fileItemList, "insert");

    logger.info("ImportProjects-> Inserting project file versions");
    FileItemVersionList fileItemVersionList = new FileItemVersionList();
    fileItemVersionList.buildList(db);
    processOK = mappings.saveList(writer, fileItemVersionList, "insert");

    logger.info("ImportProjects-> Inserting project file download log");
    FileDownloadLogList fileDownloadLogs = new FileDownloadLogList();
    fileDownloadLogs.buildList(db);
    processOK = mappings.saveList(writer, fileDownloadLogs, "insert");

    logger.info("ImportProjects-> Inserting project file thumbnails");
    processOK = mappings.saveList(writer,
        Thumbnail.recordList(db), "insert");

    logger.info("ImportProjects-> Inserting Team Members");
    processOK = mappings.saveList(writer, TeamMemberList.recordList(db), "insert");

    logger.info("ImportProjects-> Inserting news category");
    NewsArticleCategoryList newsArticleCategoryList = new NewsArticleCategoryList();
    newsArticleCategoryList.buildList(db);
    processOK = mappings.saveList(writer, newsArticleCategoryList, "insert");

    logger.info("ImportProjects-> Inserting news");
    NewsArticleList newsArticleList = new NewsArticleList();
    newsArticleList.buildList(db);
    processOK = mappings.saveList(writer, newsArticleList, "insert");

    logger.info("ImportProjects-> Inserting requirments map");
    RequirementMapList requirementMapList = new RequirementMapList();
    requirementMapList.buildList(db);
    processOK = mappings.saveList(writer, requirementMapList, "insert");

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "projectPermissions");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "projectAccounts");
    if (!processOK) {
      return false;
    }
    //Actual files will be imported later
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param assignments Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private boolean saveAssignmentFolderList(Connection db, AssignmentFolderList assignments) throws SQLException {
    Iterator i = assignments.iterator();
    while (i.hasNext()) {
      AssignmentFolder thisFolder = (AssignmentFolder) i.next();
      DataRecord thisRecord = mappings.createDataRecord(thisFolder, "insert");
      thisRecord.addField("recordMapItem", "false");
      writer.save(thisRecord);
    }

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param assignments Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private boolean saveAssignmentList(Connection db, AssignmentList assignments) throws SQLException {
    Iterator i = assignments.iterator();
    while (i.hasNext()) {
      Assignment thisAssignment = (Assignment) i.next();
      DataRecord thisRecord = mappings.createDataRecord(thisAssignment, "insert");
      thisRecord.addField("recordMapItem", "false");
      thisRecord.addField("recordNote", "false");
      writer.save(thisRecord);
    }

    return true;
  }
}


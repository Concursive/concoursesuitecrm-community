/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.documents.base.DocumentStoreList;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Module Importer for extracting data out of Document Stores.
 *
 * @author Ananth
 * @created September 11, 2006
 */
public class ImportDocumentStores implements CFSDatabaseReaderImportModule {
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

    logger.info("ImportDocumentStores-> Inserting Document Store Permission Category records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "documentStorePermissionCategory");
    if (!processOK) {
      return false;
    }

    logger.info("ImportDocumentStores-> Inserting Document Store Role records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "documentStoreRole");
    if (!processOK) {
      return false;
    }

    logger.info("ImportDocumentStores-> Inserting Document Store Role Permission records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "documentStorePermission");
    if (!processOK) {
      return false;
    }

    logger.info("ImportDocumentStores-> Inserting Document Store records");
    writer.setAutoCommit(false);
    DocumentStoreList documentStores = new DocumentStoreList();
    documentStores.buildList(db);
    mappings.saveList(writer, documentStores, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportDocumentStores-> Inserting Document Store Permission records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "documentStorePermissions");
    if (!processOK) {
      return false;
    }
    logger.info("ImportDocumentStores-> Inserting Document Store Team Member records");
    writer.setAutoCommit(false);
    DocumentStoreTeamMemberList teamMembers = new DocumentStoreTeamMemberList();
    teamMembers.setMemberType(DocumentStoreTeamMemberList.USER);
    teamMembers.buildList(db);
    mappings.saveList(writer, teamMembers, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportDocumentStores-> Inserting Document Store Role Member records");
    writer.setAutoCommit(false);
    DocumentStoreTeamMemberList roleMembers = new DocumentStoreTeamMemberList();
    roleMembers.setMemberType(DocumentStoreTeamMemberList.ROLE);
    roleMembers.buildList(db);
    this.saveMembers(db, roleMembers, "documentStoreRoleMember");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportDocumentStores-> Inserting Document Store Department Member records");
    writer.setAutoCommit(false);
    DocumentStoreTeamMemberList deptMembers = new DocumentStoreTeamMemberList();
    deptMembers.setMemberType(DocumentStoreTeamMemberList.DEPARTMENT);
    deptMembers.buildList(db);
    this.saveMembers(db, deptMembers, "documentStoreDepartmentMember");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param members Description of the Parameter
   * @param mapId   Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveMembers(Connection db, DocumentStoreTeamMemberList members, String mapId) throws SQLException {
    Iterator i = members.iterator();
    while (i.hasNext()) {
      DocumentStoreTeamMember member = (DocumentStoreTeamMember) i.next();

      DataRecord thisRecord = mappings.createDataRecord(member, "insert");
      thisRecord.removeField("itemId");

      if ("documentStoreRoleMember".equals(mapId)) {
        thisRecord.addField("itemId", member.getItemId(), "role", null);
      } else if ("documentStoreDepartmentMember".equals(mapId)) {
        thisRecord.addField("itemId", member.getItemId(), "lookupDepartment", null);
      }
      thisRecord.addField("teamMemberType", member.getTeamMemberType());

      writer.save(thisRecord);
    }
  }
}


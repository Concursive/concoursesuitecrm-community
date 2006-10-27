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

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.admin.base.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Reads all roles and permissions, and re-reads the user data for updating
 * them with roles.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 18, 2002
 */
public class ImportRoles implements CFSDatabaseReaderImportModule {

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

    logger.info("ImportRoles-> Inserting Roles");
    RoleList roles = new RoleList();
    roles.setEnabledState(-1);
    roles.buildList(db);
    processOK = mappings.saveList(writer, roles, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportRoles-> Inserting Permission Categories");
    PermissionCategoryList categories = new PermissionCategoryList();
    categories.buildList(db);
    processOK = mappings.saveList(writer, categories, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportRoles-> Inserting Permissions");
    PermissionList permissions = new PermissionList();
    permissions.buildList(db);
    processOK = mappings.saveList(writer, permissions, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportRoles-> Inserting Role Permissions");
    RolePermissionList rolePermissions = new RolePermissionList();
    rolePermissions.buildCombinedList(db);
    processOK = mappings.saveList(writer,
        new ArrayList(rolePermissions.values()),
        "insert");
    if (!processOK) {
      return false;
    }

    //Update the user records now that they all exist
    User firstUser = new User(db, "0");
    DataRecord firstRecord = mappings.createDataRecord(firstUser, "update");
    writer.save(firstRecord);

    //Update the user records now that they all exist
    UserList finalUserList = new UserList();
    finalUserList.buildList(db);
    processOK = mappings.saveList(writer, finalUserList, "update");
    if (!processOK) {
      return false;
    }

    return true;
  }
}


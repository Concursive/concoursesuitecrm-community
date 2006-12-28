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
package org.aspcfs.modules.setup.utils;

import bsh.EvalError;
import bsh.Interpreter;
import org.aspcfs.apps.help.ImportHelp;
import org.aspcfs.apps.icelets.ImportIcelets;
import org.aspcfs.apps.lookuplists.ImportLookupLists;
import org.aspcfs.apps.transfer.reader.cfs.InitPermissionsAndRoles;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.PermissionsAndRolesWriter;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.modules.website.base.IceletList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.service.utils.BackupUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * A utility Class for Setup methods
 *
 * @author matt rajkowski
 * @version $Id$
 * @created July 27, 2005
 */
public class SetupUtils {

  public final static String fs = System.getProperty("file.separator");

  public static void createDatabaseSchema(Connection db, String setupPath) throws Exception {
    String dbPath = null;
    switch (DatabaseUtils.getType(db)) {
      case DatabaseUtils.POSTGRESQL:
        dbPath = "postgresql";
        break;
      case DatabaseUtils.FIREBIRD:
        dbPath = "firebird";
        break;
      case DatabaseUtils.MSSQL:
        dbPath = "mssql";
        break;
      case DatabaseUtils.ORACLE:
        dbPath = "oracle";
        break;
      case DatabaseUtils.DB2:
        dbPath = "db2";
        break;
      case DatabaseUtils.DAFFODILDB:
        dbPath = "daffodildb";
        break;
      case DatabaseUtils.MYSQL:
        dbPath = "mysql";
        break;
      case DatabaseUtils.DERBY:
        dbPath = "derby";
        break;
      default:
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "Setup-> * Database could not be determined: " + DatabaseUtils.getType(
                  db));
        }
        break;
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SetupUtils-> Installing Schema: " + setupPath + dbPath);
    }
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_cdb.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_opportunity.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_project.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_product.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_service_contract.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_tms.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_quote.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_order.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_custom_field.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_campaign.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_help.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_sync.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_autoguide.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_revenue.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_task.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_documents.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_workflow.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_tms_append_fields.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_quote_adjustment.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_history.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_actionplan.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_knowledgebase.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_netapp.sql");
    DatabaseUtils.executeSQL(db, setupPath + dbPath + fs + "new_website.sql");
  }

  public static boolean insertDefaultData(Connection db, String dbFileLibraryPath, String setupPath, String locale, boolean syncOnly) throws Exception, EvalError, IOException {
    // TODO: Use a separate interpreter for each script since the same scope is being used here
    Interpreter script = new Interpreter();
    script.set("db", db);
    script.set("dbFileLibraryPath", dbFileLibraryPath);
    script.set("locale", locale);
    script.set("prefsPath", setupPath + "init" + fs);
    // Default database inserts
    script.source(setupPath + "init" + fs + "sync.bsh");
    script.source(setupPath + "init" + fs + "sync-mappings.bsh");
    if (syncOnly) {
      // No more base data is needed, so return
      return true;
    }
    // Setup a default user and organization
    script.source(setupPath + "init" + fs + "database.bsh");
    // Lookup Lists
    String lookupFile = "lookuplists_en_US.xml";
    File checkLookupFile = new File(
        setupPath + "init" + fs + "lookuplists_" + locale + ".xml");
    if (checkLookupFile.exists()) {
      lookupFile = "lookuplists_" + locale + ".xml";
    }
    ImportLookupLists lookups = new ImportLookupLists();
    lookups.importLookups(db, setupPath + "init" + fs + lookupFile);
    // Permissions
    String permissionsFile = "permissions_en_US.xml";
    File checkPermissionFile = new File(
        setupPath + "init" + fs + "permissions_" + locale + ".xml");
    if (checkPermissionFile.exists()) {
      permissionsFile = "permissions_" + locale + ".xml";
    }
    InitPermissionsAndRoles permissionsReader = new InitPermissionsAndRoles();
    permissionsReader.setProcessConfigFile(setupPath + "init" + fs + permissionsFile);
    PermissionsAndRolesWriter permissionsWriter = new PermissionsAndRolesWriter();
    permissionsWriter.setDb(db);
    permissionsReader.execute(permissionsWriter);
    // Workflow
    script.source(setupPath + "init" + fs + "workflow.bsh");
    // Icelets
    String filePath = setupPath + "../icelets/icelet_en_US.xml";
    HashMap iceletMap = IceletList.load(filePath);
    ImportIcelets.insertIceletList(db, iceletMap);
    // Help content
    ImportHelp help = new ImportHelp();
    help.buildHelpInformation(setupPath + "init" + fs + "help.xml");
    help.buildExistingPermissionCategories(db);
    help.insertHelpRecords(db);
    help.buildTableOfContents();
    help.insertTableOfContents(db);
    // Database version
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2004-06-15");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2004-08-30");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-01-14");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-03-30");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-05-02");
    // Version 3.1
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-07-08");
    // Version 3.2
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-08-24");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-08-30");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-10-17");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-10-24");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-11-02");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-11-03");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-11-14");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-12-14");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2005-12-19");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-01-25");
    // Version 4.0
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-04-12");
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-04-17");
    // Version 4.0.1
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-05-08");
    // Version 4.1 beta 1
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-06-01");
    // Version 4.1 beta 2
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-06-16");
    // Version 4.1 beta 3
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-06-30");
    // Version 4.1 beta 4
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-07-11");
    // Version 4.1 beta 7
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-09-05");
    // Version 4.1 beta 10
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-11-02");
    // Version 4.1 beta 11
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-11-16");
    // Version 4.1 beta 13
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-12-28");

    // Events (last because a check for these is made later)
    // NOTE: no longer used
    //script.source(setupPath + "events_gk.bsh");
    return true;
  }

  public static void restoreFromBackup(Connection db, Connection dbLookup, String backupFile) throws SQLException {
    // Create a temporary sync client
    SyncClient thisClient = new SyncClient();
    thisClient.setId(0);
    thisClient.setType("Restore client");
    thisClient.setEnabled(true);
    thisClient.setEnteredBy(0);
    thisClient.setModifiedBy(0);
    thisClient.insert(db);
    try {
      // Restore the data
      BackupUtils.restore(db, dbLookup, backupFile, thisClient);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      // Delete the temporary sync client
      thisClient.delete(db);
    }
  }
}


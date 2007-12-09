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
package org.aspcfs.modules.setup.utils;

import bsh.EvalError;
import bsh.Interpreter;

import org.apache.log4j.Logger;
import org.aspcfs.apps.help.ImportHelp;
import org.aspcfs.apps.lookuplists.ImportLookupLists;
import org.aspcfs.apps.transfer.reader.cfs.InitPermissionsAndRoles;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.PermissionsAndRolesWriter;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.service.utils.BackupUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

  static Logger log = Logger.getLogger(org.aspcfs.modules.setup.utils.SetupUtils.class);
  public final static String fs = System.getProperty("file.separator");

  public static void createDatabaseSchema(Connection db, String setupPath) throws Exception {
    SetupUtils.createDatabaseSchema(db, new File(setupPath).toURL());
  }

  public static void createDatabaseSchema(Connection db, URL setupURL) throws Exception {
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
    System.out.println("SetupUtils-> Installing Schema: " + setupURL + dbPath);
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_cdb.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_opportunity.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_project.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_product.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_service_contract.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_tms.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_quote.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_order.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_custom_field.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_campaign.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_help.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_sync.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_autoguide.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_revenue.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_task.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_documents.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_workflow.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_tms_append_fields.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_quote_adjustment.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_history.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_actionplan.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_knowledgebase.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_netapp.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_website.sql"));
    DatabaseUtils.executeSQL(db, new URL(setupURL + dbPath + "/new_graph.sql"));
  }

  public static boolean insertDefaultData(Connection db, String dbFileLibraryPath, String setupPath, String locale, boolean syncOnly) throws Exception, EvalError, IOException {
  	System.out.println("SetupUtils(old)-> Using URL: " + setupPath);
  	return SetupUtils.insertDefaultData(db, dbFileLibraryPath, (new File(setupPath)).toURL(), locale, syncOnly);
  }

  public static boolean insertDefaultData(Connection db, String dbFileLibraryPath, URL setupURL, String locale, boolean syncOnly) throws Exception, EvalError, IOException {
    // TODO: Use a separate interpreter for each script since the same scope is being used here
    System.out.println("SetupUtils-> Using URL: " + setupURL);
    Interpreter script = new Interpreter();
    script.set("db", db);
    script.set("dbFileLibraryPath", dbFileLibraryPath);
    script.set("locale", locale);
    script.set("prefsURL", null);
    script.set("libURL", new URL(setupURL + "../lib/"));
    // TODO: Fix downstream
    //script.set("prefsPath", setupPath + "init" + fs);
    // Default database inserts
    evalScript(script, new URL(setupURL + "init/sync.bsh"));
    // should reset the interpreter... script.clear();
    evalScript(script, new URL(setupURL + "init/sync-mappings.bsh"));
    if (syncOnly) {
      // No more base data is needed, so return
      return true;
    }
    // Setup a default user and organization
    evalScript(script, new URL(setupURL + "init/database.bsh"));

    // Lookup Lists
    String lookupFile = "lookuplists_en_US.xml";
    URL checkLookupURL = new URL(setupURL + "init/lookuplists_" + locale + ".xml");
    if ("file".equals(checkLookupURL.getProtocol())) {
      File file = new File(checkLookupURL.getPath());
      if (file.exists()) {
        lookupFile = "lookuplists_" + locale + ".xml";
      }
    } else {
      System.out.println("SetupUtils-> URL protocol: " + checkLookupURL.getProtocol());
    }
    ImportLookupLists lookups = new ImportLookupLists();
    lookups.importLookups(db, new URL(setupURL + "init/" + lookupFile));

    // Permissions
    String permissionsFile = "permissions_en_US.xml";
    URL checkPermissionURL = new URL(setupURL + "init/permissions_" + locale + ".xml");
    if ("file".equals(checkPermissionURL.getProtocol())) {
      File file = new File(checkPermissionURL.getPath());
      if (file.exists()) {
        permissionsFile = "permissions_" + locale + ".xml";
      }
    }
    InitPermissionsAndRoles permissionsReader = new InitPermissionsAndRoles();
    permissionsReader.setProcessConfigURL(new URL(setupURL + "init/" + permissionsFile));
    PermissionsAndRolesWriter permissionsWriter = new PermissionsAndRolesWriter();
    permissionsWriter.setDb(db);
    permissionsReader.execute(permissionsWriter);

    // Workflow
    evalScript(script, new URL(setupURL + "init/workflow.bsh"));

    // Help content
    ImportHelp help = new ImportHelp();
    help.buildHelpInformation(new URL(setupURL + "init/help.xml"));
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
    // Version 4.1 beta 14
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2007-01-16");
    // Version 4.1 rc 1
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2007-01-29");
    // Version 4.2 alpha 1
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2007-02-09");

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

  public static void evalScript(Interpreter script, URL source) throws Exception {
    System.out.println("SetupUtils-> evalScript: " + source);
    // Using source (supposed to accept a URL, but doesn't)
    //script.source(source);
    //script.source(source.getPath());

    //script.eval(
    //    "addClassPath(bsh.cwd + \"" + fsEval + "build" + fsEval + "lib" + fsEval + "aspcfs.jar\")");

    script.set("scriptURL", source);
    script.eval("source(scriptURL);");

    // Using eval
    /*
    BufferedReader executeReader = new BufferedReader(new InputStreamReader(source.openStream()));
    script.eval(executeReader);
    executeReader.close();
    */

  }
}


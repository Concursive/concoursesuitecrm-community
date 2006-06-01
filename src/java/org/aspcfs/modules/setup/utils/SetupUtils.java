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
import org.aspcfs.apps.lookuplists.ImportLookupLists;
import org.aspcfs.apps.transfer.reader.cfs.InitPermissionsAndRoles;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.PermissionsAndRolesWriter;
import org.aspcfs.apps.icelets.ImportIcelets;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.modules.website.base.IceletList;
import org.aspcfs.utils.DatabaseUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
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

  public static void insertDefaultData(Connection db, String dbFileLibraryPath, String setupPath, String locale) throws Exception, EvalError, IOException {
    // TODO: Use a separate interpreter for each script since the same scope is being used here
    Interpreter script = new Interpreter();
    script.set("db", db);
    script.set("dbFileLibraryPath", dbFileLibraryPath);
    script.set("locale", locale);
    // Default database inserts
    script.source(setupPath + "database.bsh");
    script.source(setupPath + "sync.bsh");
    // Lookup Lists
    String lookupFile = "lookuplists_en_US.xml";
    File checkLookupFile = new File(
        setupPath + "lookuplists_" + locale + ".xml");
    if (checkLookupFile.exists()) {
      lookupFile = "lookuplists_" + locale + ".xml";
    }
    ImportLookupLists lookups = new ImportLookupLists();
    lookups.importLookups(db, setupPath + lookupFile);
    // Permissions
    String permissionsFile = "permissions_en_US.xml";
    File checkPermissionFile = new File(
        setupPath + "permissions_" + locale + ".xml");
    if (checkPermissionFile.exists()) {
      permissionsFile = "permissions_" + locale + ".xml";
    }
    InitPermissionsAndRoles permissionsReader = new InitPermissionsAndRoles();
    permissionsReader.setProcessConfigFile(setupPath + permissionsFile);
    PermissionsAndRolesWriter permissionsWriter = new PermissionsAndRolesWriter();
    permissionsWriter.setDb(db);
    permissionsReader.execute(permissionsWriter);
    // Workflow
    script.source(setupPath + "workflow.bsh");
    // Icelets
    String filePath = setupPath + "../icelets/icelet_en_US.xml";
    HashMap iceletMap = IceletList.load(filePath);
    ImportIcelets.insertIceletList(db, iceletMap);
    // Help content
    ImportHelp help = new ImportHelp();
    help.buildHelpInformation(setupPath + "help.xml");
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
    // Version 4.1 beta
    DatabaseVersion.insertVersion(db, DatabaseUtils.getTypeName(db), "2006-06-01");
    // Events (last because a check for these is made later)
    script.source(setupPath + "events_gk.bsh");
  }
}


package org.aspcfs.apps.transfer.reader.cfsdatabasereader;


import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.help.base.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ImportHelp implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;

  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;

    logger.info("ImportHelp-> Inserting helpBusinessRuleList records");
    writer.setAutoCommit(false);
    HelpBusinessRuleList helpBusinessRuleList = new HelpBusinessRuleList();
    helpBusinessRuleList.buildList(db);
    mappings.saveList(writer, helpBusinessRuleList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportHelp-> Inserting helpFeatureList records");
    writer.setAutoCommit(false);
    HelpFeatureList helpFeatureList = new HelpFeatureList();
    helpFeatureList.buildList(db);
    mappings.saveList(writer, helpFeatureList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportHelp-> Inserting helpItemList records");
    writer.setAutoCommit(false);
    HelpItemList helpItemList = new HelpItemList();
    helpItemList.buildList(db);
    mappings.saveList(writer, helpItemList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportHelp-> Inserting helpModuleList records");
    writer.setAutoCommit(false);
    HelpModuleList helpModuleList = new HelpModuleList();
    helpModuleList.buildList(db);
    mappings.saveList(writer, helpModuleList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportHelp-> Inserting helpNoteList records");
    writer.setAutoCommit(false);
    HelpNoteList helpNoteList = new HelpNoteList();
    helpNoteList.buildList(db);
    mappings.saveList(writer, helpNoteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportHelp-> Inserting helpTipList records");
    writer.setAutoCommit(false);
    HelpTipList helpTipList = new HelpTipList();
    helpTipList.buildList(db);
    mappings.saveList(writer, helpTipList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}

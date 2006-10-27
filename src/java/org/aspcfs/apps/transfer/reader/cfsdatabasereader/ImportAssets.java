package org.aspcfs.apps.transfer.reader.cfsdatabasereader;


import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.assets.base.AssetMaterialList;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Processes Assets
 *
 * @author srini
 * @version $Id:  Exp
 *          $
 * @created December 10, 2005
 */
public class ImportAssets implements CFSDatabaseReaderImportModule {

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

    // import asset material
    logger.info("ImportAssets-> Inserting assets material");
    writer.setAutoCommit(false);
    AssetMaterialList assetMaterialList = new AssetMaterialList();
    assetMaterialList.buildList(db);
    mappings.saveList(writer, assetMaterialList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    // import asset
    logger.info("ImportAssets-> Inserting assets records");
    writer.setAutoCommit(false);
    AssetList assetList = new AssetList();
    assetList.buildList(db);
    mappings.saveList(writer, assetList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}

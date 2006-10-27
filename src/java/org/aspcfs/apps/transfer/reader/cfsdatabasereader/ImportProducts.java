/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.products.base.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created September 8, 2006
 */
public class ImportProducts implements CFSDatabaseReaderImportModule {
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

    logger.info("ImportProducts-> Inserting Product Category records");
    writer.setAutoCommit(false);
    ProductCategoryList categories = new ProductCategoryList();
    categories.buildList(db);
    mappings.saveList(writer, categories, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Category Map records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productCategoryMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Catalog records");
    writer.setAutoCommit(false);
    ProductCatalogList productCatalogList = new ProductCatalogList();
    productCatalogList.buildList(db);
    mappings.saveList(writer, productCatalogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Catalog pricing records");
    writer.setAutoCommit(false);
    ProductCatalogPricingList prodCatalogPricingList = new ProductCatalogPricingList();
    prodCatalogPricingList.buildList(db);
    mappings.saveList(writer, prodCatalogPricingList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting package records");
    writer.setAutoCommit(false);
    PackageList packageList = new PackageList();
    packageList.buildList(db);
    mappings.saveList(writer, packageList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Package Products Map records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "packageProductsMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Catalog Category Map records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productCatalogCategoryMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting ProductOptionConfiguratorList records");
    writer.setAutoCommit(false);
    ProductOptionConfiguratorList productOptionConfiguratorList = new ProductOptionConfiguratorList();
    productOptionConfiguratorList.buildList(db);
    mappings.saveList(writer, productOptionConfiguratorList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting ProductOptionList records");
    writer.setAutoCommit(false);
    ProductOptionList productOptionList = new ProductOptionList();
    productOptionList.buildList(db);
    mappings.saveList(writer, productOptionList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting ProductOptionValuesList records");
    writer.setAutoCommit(false);
    ProductOptionValuesList productOptionValuesList = new ProductOptionValuesList();
    productOptionValuesList.buildList(db);
    mappings.saveList(writer, productOptionValuesList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Option Map records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productOptionMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Option Boolean records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productOptionBoolean");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Option Float records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productOptionFloat");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Option Timestamp records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productOptionTimestamp");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Option Integer records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productOptionInteger");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Option Text records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productOptionText");
    if (!processOK) {
      return false;
    }

    logger.info("ImportProducts-> Inserting Product Keyword Map records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "productKeywordMap");
    if (!processOK) {
      return false;
    }

    return true;
  }

}


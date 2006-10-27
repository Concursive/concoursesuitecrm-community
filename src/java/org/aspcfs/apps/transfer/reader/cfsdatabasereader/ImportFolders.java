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
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Retrieves data in the appropriate order for reconstructing folders
 *
 * @author matt rajkowski
 * @version $Id: ImportFolders.java,v 1.11 2004/06/04 17:27:24 mrajkowski Exp
 *          $
 * @created 9/15/2002
 */
public class ImportFolders implements CFSDatabaseReaderImportModule {

  /**
   * Reads folder data and sends to a writer
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   * @deprecated The system_modules table has been replaced so this
   *             method needs to be updated
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    logger.info("ImportFolders-> Inserting folders");
    boolean processOK = true;

    writer.setAutoCommit(true);

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "moduleFieldCategoryLink");
    if (!processOK) {
      return false;
    }

    //For each linkModuleId... get the categories and definitions to insert
    ArrayList moduleList = CustomFieldCategory.getModules(db);
    Iterator modules = moduleList.iterator();
    while (modules.hasNext()) {
      String thisModule = (String) modules.next();
      int moduleId = Integer.parseInt(thisModule);
      //Insert the category
      CustomFieldCategoryList categoryList = new CustomFieldCategoryList();
      categoryList.setLinkModuleId(moduleId);
      categoryList.setBuildResources(true);
      categoryList.buildList(db);
      processOK = mappings.saveList(writer, categoryList, "insertCategory");
      if (!processOK) {
        return false;
      }

      //Insert the category groups
      Iterator categories = categoryList.iterator();
      while (categories.hasNext()) {
        CustomFieldCategory thisCategory = (CustomFieldCategory) categories.next();
        processOK = mappings.saveList(writer, thisCategory, "insertGroup");
        if (!processOK) {
          return false;
        }

        ArrayList fieldLookup = new ArrayList();

        //Insert the fields
        Iterator groups = thisCategory.iterator();
        while (groups.hasNext()) {
          CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
          processOK = mappings.saveList(writer, thisGroup, "insertField");
          if (!processOK) {
            return false;
          }

          //Insert the field lookup lists
          Iterator fields = thisGroup.iterator();
          while (fields.hasNext()) {
            CustomField thisField = (CustomField) fields.next();
            if (thisField.getType() == CustomField.SELECT) {

              //Stash this item id so we know it is a lookup for later
              fieldLookup.add(new Integer(thisField.getId()));

              thisField.buildElementData(db);
              Iterator lookupItems = ((LookupList) thisField.getElementData()).iterator();
              while (lookupItems.hasNext()) {
                LookupElement thisElement = (LookupElement) lookupItems.next();
                DataRecord thisRecord = new DataRecord();
                thisRecord.setName("customFieldLookup");
                thisRecord.setAction("insert");
                thisRecord.addField("tableName", "custom_field_lookup");
                thisRecord.addField("uniqueField", "code");
                thisRecord.addField("guid", String.valueOf(thisElement.getCode()));

                thisRecord.addField("field", "field_id");
                thisRecord.addField("data", "field_id=" + String.valueOf(thisField.getId()), "customField", null);
                thisRecord.addField("type", java.sql.Types.INTEGER);

                thisRecord.addField("field", "description");
                thisRecord.addField("data", thisElement.getDescription());
                thisRecord.addField("type", java.sql.Types.VARCHAR);

                thisRecord.addField("field", "default_item");
                thisRecord.addField("data", String.valueOf(thisElement.getDefaultItem()));
                thisRecord.addField("type", java.sql.Types.BOOLEAN);

                thisRecord.addField("field", "level");
                thisRecord.addField("level", String.valueOf(thisElement.getLevel()));
                thisRecord.addField("type", java.sql.Types.INTEGER);

                processOK = writer.save(thisRecord);
                if (!processOK) {
                  return false;
                }
              }
            }
            //NOTE: Could insert this field's data since the definition is needed,
            //but a record is needed
          }
        }

        //TODO: Test this with records...
        //Copy the actual data
        CustomFieldRecordList recordList = new CustomFieldRecordList();
        recordList.setLinkModuleId(moduleId);
        recordList.setCategoryId(thisCategory.getId());
        recordList.buildList(db);

        Iterator records = recordList.iterator();
        while (records.hasNext()) {
          CustomFieldRecord thisCFRecord = (CustomFieldRecord) records.next();
          DataRecord thisRecord = new DataRecord();
          thisRecord.setName("customFieldRecord");
          thisRecord.setAction("insert");
          thisRecord.addField(
              "linkModuleId", String.valueOf(thisCFRecord.getLinkModuleId()), "moduleFieldCategoryLink", null);
          switch (thisCFRecord.getLinkModuleId()) {
            case 1:
              thisRecord.addField(
                  "linkItemId", String.valueOf(thisCFRecord.getLinkItemId()), "account", null);
              break;
            case 2:
              thisRecord.addField(
                  "linkItemId", String.valueOf(thisCFRecord.getLinkItemId()), "contact", null);
              break;
            default:
              break;
          }
          thisRecord.addField(
              "categoryId", String.valueOf(thisCFRecord.getCategoryId()), "customFieldCategory", null);
          thisRecord.addField("guid", String.valueOf(thisCFRecord.getId()));
          thisRecord.addField(
              "entered", ObjectUtils.getParam(thisCFRecord, "entered"));
          thisRecord.addField(
              "enteredBy", String.valueOf(thisCFRecord.getEnteredBy()), "user", null);
          thisRecord.addField(
              "modified", ObjectUtils.getParam(thisCFRecord, "modified"));
          thisRecord.addField(
              "modifiedBy", String.valueOf(thisCFRecord.getModifiedBy()), "user", null);
          thisRecord.addField(
              "enabled", ObjectUtils.getParam(thisCFRecord, "enabled"));
          processOK = writer.save(thisRecord);
          if (!processOK) {
            return false;
          }

          //TODO:For each record, add the field data
          CustomFieldDataList fieldList = new CustomFieldDataList();
          fieldList.setRecordId(thisCFRecord.getId());
          fieldList.buildList(db);
          Iterator fieldItems = fieldList.iterator();
          while (fieldItems.hasNext()) {
            CustomFieldData thisData = (CustomFieldData) fieldItems.next();
            DataRecord thisFieldRecord = new DataRecord();
            thisFieldRecord.setName("customFieldData");
            thisFieldRecord.setAction("insert");
            thisFieldRecord.addField(
                "recordId", String.valueOf(thisData.getRecordId()), "customFieldRecord", null);
            thisFieldRecord.addField(
                "fieldId", String.valueOf(thisData.getFieldId()), "customField", null);
            if (fieldLookup.contains(new Integer(thisData.getFieldId()))) {
              thisFieldRecord.addField(
                  "selectedItemId", String.valueOf(
                  thisData.getSelectedItemId()), "customFieldLookup", null);
            } else {
              thisFieldRecord.addField(
                  "selectedItemId", String.valueOf(
                  thisData.getSelectedItemId()));
            }
            thisFieldRecord.addField(
                "enteredValue", String.valueOf(thisData.getEnteredValue()));
            thisFieldRecord.addField(
                "enteredNumber", String.valueOf(thisData.getEnteredNumber()));
            thisFieldRecord.addField(
                "enteredDouble", String.valueOf(thisData.getEnteredDouble()));
            processOK = writer.save(thisFieldRecord);
          }
        }
      }

      if (!processOK) {
        return false;
      }
    }
    return true;
  }
}


package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.*;

public class ImportFolders implements CFSDatabaseReaderImportModule {
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    logger.info("ImportBaseData-> Inserting folders");
    boolean processOK = true;
    
    writer.setAutoCommit(true);
    //For each linkModuleId... get the categories and definitions to insert
    LookupList moduleList = new LookupList(db, "system_modules");
    Iterator modules = moduleList.iterator();
    while (modules.hasNext()) {
      LookupElement thisModule = (LookupElement)modules.next();
      int moduleId = thisModule.getCode();
      
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
        CustomFieldCategory thisCategory = (CustomFieldCategory)categories.next();
        processOK = mappings.saveList(writer, thisCategory, "insertGroup");
        if (!processOK) {
          return false;
        }
        
        //Insert the fields
        Iterator groups = thisCategory.iterator();
        while (groups.hasNext()) {
          CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
          processOK = mappings.saveList(writer, thisGroup, "insertField");
          if (!processOK) {
            return false;
          }
          
          //Insert the field lookup lists
          Iterator fields = thisGroup.iterator();
          while (fields.hasNext()) {
            CustomField thisField = (CustomField)fields.next();
            if (thisField.getType() == CustomField.SELECT) {
              thisField.buildElementData(db);
              Iterator lookupItems = ((LookupList)thisField.getElementData()).iterator();
              while (lookupItems.hasNext()) {
                LookupElement thisElement = (LookupElement)lookupItems.next();
                DataRecord thisRecord = new DataRecord();
                thisRecord.setName("customFieldLookup");
                thisRecord.setAction("insert");
                thisRecord.addField("tableName", "custom_field_lookup");
                thisRecord.addField("fieldId", String.valueOf(thisField.getId()), "customField", null);
                thisRecord.addField("guid", String.valueOf(thisElement.getCode()));
                thisRecord.addField("description", thisElement.getDescription());
                thisRecord.addField("defaultItem", String.valueOf(thisElement.getDefaultItem()));
                thisRecord.addField("level", String.valueOf(thisElement.getLevel()));
                processOK = writer.save(thisRecord);
                if (!processOK) {
                  return false;
                }
              }
            }
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
          CustomFieldRecord thisCFRecord = (CustomFieldRecord)records.next();
          DataRecord thisRecord = new DataRecord();
          thisRecord.setName("customFieldRecord");
          thisRecord.setAction("insert");
          thisRecord.addField("linkModuleId", String.valueOf(thisCFRecord.getLinkModuleId()), "systemModules", null);
          switch (thisCFRecord.getLinkModuleId()) {
            case 1: 
              thisRecord.addField("linkItemId", String.valueOf(thisCFRecord.getLinkItemId()), "account", null);
              break;
            case 2:
              thisRecord.addField("linkItemId", String.valueOf(thisCFRecord.getLinkItemId()), "contact", null);
              break;
            default:
              break;
          }
          thisRecord.addField("categoryId", String.valueOf(thisCFRecord.getCategoryId()), "customFieldCategory", null);
          thisRecord.addField("guid", String.valueOf(thisCFRecord.getId()));
          thisRecord.addField("entered", ObjectUtils.getParam(thisCFRecord, "entered"));
          thisRecord.addField("enteredBy", String.valueOf(thisCFRecord.getEnteredBy()));
          thisRecord.addField("modified", ObjectUtils.getParam(thisCFRecord, "modified"));
          thisRecord.addField("modifiedBy", String.valueOf(thisCFRecord.getModifiedBy()));
          thisRecord.addField("enabled", ObjectUtils.getParam(thisCFRecord, "enabled"));
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
            CustomFieldData thisData = (CustomFieldData)fieldItems.next();
            DataRecord thisFieldRecord = new DataRecord();
            thisFieldRecord.setName("customFieldData");
            thisFieldRecord.setAction("insert");
            thisFieldRecord.addField("recordId", String.valueOf(thisData.getRecordId()), "customFieldRecord", null);
            thisFieldRecord.addField("fieldId", String.valueOf(thisData.getFieldId()), "customField", null);
            if (thisData.getType() == CustomField.SELECT) {
              thisFieldRecord.addField("selectedItemId", String.valueOf(thisData.getSelectedItemId()), "customFieldLookup", null);
            } else {
              thisFieldRecord.addField("selectedItemId", String.valueOf(thisData.getSelectedItemId()));
            }
            thisFieldRecord.addField("enteredValue", String.valueOf(thisData.getEnteredValue()));
            thisFieldRecord.addField("enteredNumber", String.valueOf(thisData.getEnteredNumber()));
            thisFieldRecord.addField("enteredDouble", String.valueOf(thisData.getEnteredDouble()));
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

package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

public class ImportFolders implements CFSDatabaseReaderImportModule {
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    logger.info("ImportBaseData-> Inserting folders");
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
      mappings.saveList(writer, categoryList, "insert");
      
      //Insert the category groups
      Iterator categories = categoryList.iterator();
      while (categories.hasNext()) {
        CustomFieldCategory thisCategory = (CustomFieldCategory)categories.next();
        mappings.saveList(writer, thisCategory, "insert");
        
        Iterator groups = thisCategory.iterator();
        while (groups.hasNext()) {
          CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
          mappings.saveList(writer, thisGroup, "insert");
        }
      }
      
    }
    //custom_field_category
    //custom_field_group
    //custom_field_info
    //custom_field_lookup
    
    //custom_field_record
    //custom_field_data
    return true;
  }
}

package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Processes the related lookup data.
 *
 *@author     matt rajkowski
 *@created    September 4, 2002
 *@version    $Id$
 */
public class ImportLookupTables implements CFSDatabaseReaderImportModule {
  Connection db = null;
  DataWriter writer = null;
  
  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@param  db      Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    logger.info("LookupTableImport-> Processing lookup lists");
    this.writer = writer;
    this.db = db;
    if (!saveLookupList("lookupIndustry", "lookup_industry")) {
      return false;
    }
    saveLookupList("systemModules", "system_modules");
    saveLookupList("lookupContactTypes", "lookup_contact_types");
    saveLookupList("lookupAccountTypes", "lookup_account_types");
    saveLookupList("lookupDepartment", "lookup_department");
    saveLookupList("lookupOrgAddressTypes", "lookup_orgaddress_types");
    saveLookupList("lookupOrgEmailTypes", "lookup_orgemail_types");
    saveLookupList("lookupOrgPhoneTypes", "lookup_orgphone_types");
    saveLookupList("lookupInstantMessengerTypes", "lookup_instantmessenger_types");
    saveLookupList("lookupEmploymentTypes", "lookup_employment_types");
    saveLookupList("lookupLocale", "lookup_locale");
    saveLookupList("lookupContactAddressTypes", "lookup_contactaddress_types");
    saveLookupList("lookupContactEmailTypes", "lookup_contactemail_types");
    saveLookupList("lookupContactPhoneTypes", "lookup_contactphone_types");
    saveLookupList("lookupStage", "lookup_stage");
    saveLookupList("lookupDeliveryOptions", "lookup_delivery_options");
    saveLookupList("lookupCallTypes", "lookup_call_types");
    return true;
  }
  
  
  public boolean saveLookupList(String uniqueId, String tableName) throws SQLException {
    writer.setAutoCommit(false);
    LookupList thisList = new LookupList(db, tableName);
    Iterator i = thisList.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement)i.next();
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName(uniqueId);
      thisRecord.setAction("insert");
      thisRecord.addField("tableName", tableName);
      thisRecord.addField("guid", String.valueOf(thisElement.getCode()));
      thisRecord.addField("description", thisElement.getDescription());
      thisRecord.addField("defaultItem", String.valueOf(thisElement.getDefaultItem()));
      thisRecord.addField("level", String.valueOf(thisElement.getLevel()));
      if (!writer.save(thisRecord)) {
        return false;
      }
    }
    return writer.commit();
  }
}


package org.aspcfs.apps.transfer.reader;

import org.aspcfs.apps.transfer.*;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import org.w3c.dom.*;

public class UnitTestReader implements DataReader {

  public static final String lf = System.getProperty("line.separator");
  
  /**
   *  Gets the version attribute of the CFSDatabaseReader object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSDatabaseReader object
   *
   *@return    The name value
   */
  public String getName() {
    return "Unit Test Reader";
  }


  /**
   *  Gets the description attribute of the CFSDatabaseReader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Coded test data";
  }


  /**
   *  Gets the configured attribute of the CFSDatabaseReader object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@return                  Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
/*    if (1==1) {
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName("accountList");
      thisRecord.setAction("select");
      thisRecord.addField("id", "");
      thisRecord.addField("name", "");
      writer.load(thisRecord);
      logger.info(writer.getLastResponse());
    }
    
    if (1==1) {
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName("contactList");
      thisRecord.setAction("select");
      thisRecord.addField("id", "");
      thisRecord.addField("nameFirst", "");
      thisRecord.addField("nameLast", "");
      thisRecord.addField("nameFull", "");
      thisRecord.addField("nameFirstLast", "");
      thisRecord.addField("nameLastFirst", "");
      writer.load(thisRecord);
      logger.info(writer.getLastResponse());
    }
    
    if (1==1) {
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName("userList");
      thisRecord.setAction("select");
      thisRecord.addField("id", "");
      thisRecord.addField("username", "");
      writer.load(thisRecord);
      logger.info(writer.getLastResponse());
    }
 */    
    if (1==1) {
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName("ticket");
      thisRecord.setAction("insert");
      thisRecord.addField("orgId", "1");
      thisRecord.addField("contactId", "4");
      thisRecord.addField("problem", "CFS Unit Test");
      thisRecord.addField("enteredBy", "2");
      thisRecord.addField("modifiedBy", "2");
      thisRecord.addField("severityCode", "1");
      thisRecord.addField("catCode", "3");
      thisRecord.addField("subCat1", "14");
      writer.save(thisRecord);
      logger.info(writer.getLastResponse());
    }
    
    if (1 == 1) {
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName("processLog");
      thisRecord.setAction("insert");
      thisRecord.addField("name", "Pilot Online Reader/FTP");
      thisRecord.addField("version", "1.0");
      thisRecord.addField("systemId", "5");
      thisRecord.addField("clientId", "4");
      thisRecord.addField("status", "0");
      thisRecord.addField("message", 
        "INFO: PilotOnlineReader-> Started Tue Oct 29 14:50:00 EST 2002" + lf +
        "INFO: Processing organizations/vehicles: 59 for Mon Oct 28 12:00:00 EST 2002 through Fri Nov 01 12:00:00 EST 2002" + lf +
        "INFO: Vehicles added-> 137" + lf +
        "INFO: Processing pictures (resize/copy): 134" + lf +
        "INFO: FTP Sending pictures" + lf +
        "INFO: FTP Sending data" + lf +
        "INFO: PilotOnlineReader-> Finished Tue Oct 29 14:55:28 EST 2002");
      writer.save(thisRecord);
      logger.info(writer.getLastResponse());
    }
    return true;
  }
}


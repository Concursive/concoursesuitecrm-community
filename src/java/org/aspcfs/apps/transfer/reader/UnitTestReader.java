package com.darkhorseventures.apps.dataimport.reader;

import com.darkhorseventures.apps.dataimport.*;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import org.w3c.dom.*;

public class UnitTestReader implements DataReader {

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
      thisRecord.addField("orgId", "4");
      thisRecord.addField("contactId", "28");
      thisRecord.addField("problem", "Some problem");
      thisRecord.addField("enteredBy", "2");
      thisRecord.addField("modifiedBy", "2");
      writer.save(thisRecord);
      logger.info(writer.getLastResponse());
    }
    return true;
  }
}


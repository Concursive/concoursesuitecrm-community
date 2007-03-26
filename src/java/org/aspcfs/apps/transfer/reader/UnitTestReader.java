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
package org.aspcfs.apps.transfer.reader;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class UnitTestReader implements DataReader {

  private static final Logger logger = Logger.getLogger(org.aspcfs.apps.transfer.reader.UnitTestReader.class);
  public final static String lf = System.getProperty("line.separator");


  /**
   * Gets the version attribute of the CFSDatabaseReader object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the CFSDatabaseReader object
   *
   * @return The name value
   */
  public String getName() {
    return "Unit Test Reader";
  }


  /**
   * Gets the description attribute of the CFSDatabaseReader object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Coded test data";
  }


  /**
   * Gets the configured attribute of the CFSDatabaseReader object
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    return true;
  }


  /**
   * Description of the Method
   *
   * @param writer Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    /*
     *  if (1==1) {
     *  DataRecord thisRecord = new DataRecord();
     *  thisRecord.setName("accountList");
     *  thisRecord.setAction("select");
     *  thisRecord.addField("id", "");
     *  thisRecord.addField("name", "");
     *  writer.load(thisRecord);
     *  logger.info(writer.getLastResponse());
     *  }
     *  if (1==1) {
     *  DataRecord thisRecord = new DataRecord();
     *  thisRecord.setName("contactList");
     *  thisRecord.setAction("select");
     *  thisRecord.addField("id", "");
     *  thisRecord.addField("nameFirst", "");
     *  thisRecord.addField("nameLast", "");
     *  thisRecord.addField("nameFull", "");
     *  thisRecord.addField("nameFirstLast", "");
     *  thisRecord.addField("nameLastFirst", "");
     *  writer.load(thisRecord);
     *  logger.info(writer.getLastResponse());
     *  }
     *  if (1==1) {
     *  DataRecord thisRecord = new DataRecord();
     *  thisRecord.setName("userList");
     *  thisRecord.setAction("select");
     *  thisRecord.addField("id", "");
     *  thisRecord.addField("username", "");
     *  writer.load(thisRecord);
     *  logger.info(writer.getLastResponse());
     *  }
     */
    if (1 == 1) {
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName("ticket");
      thisRecord.setAction("insert");
      thisRecord.addField("orgId", "0");
      thisRecord.addField("contactId", "1");
      thisRecord.addField("sourceCode", "1");
      thisRecord.addField(
          "problem", "This is a Centric CRM test ticket\r\nThis is line 2.");
      thisRecord.addField(
          "comment", "The following is set by this ticket:\r\n" +
          "The ticket is entered, modified by, and assigned to McClean using the Web Site source.\r\n" +
          "The ticket will be saved against the internal account (My Company).\r\n" +
          "The severity is normal, and the issue is categorized as:\r\n" +
          "Trouble,Technical Failure,E-Biz/Infrastructure");
      thisRecord.addField("enteredBy", "1");
      thisRecord.addField("modifiedBy", "1");
      thisRecord.addField("assignedTo", "1");
      thisRecord.addField("severityCode", "1");
      thisRecord.addField("catCode", "1");
      thisRecord.addField("subCat1", "2");
      thisRecord.addField("subCat2", "3");
      writer.save(thisRecord);
      logger.info(writer.getLastResponse());
    }
    /*
     *  if (1 == 1) {
     *  DataRecord thisRecord = new DataRecord();
     *  thisRecord.setName("processLog");
     *  thisRecord.setAction("insert");
     *  thisRecord.addField("name", "Pilot Online Reader/FTP");
     *  thisRecord.addField("version", "1.0");
     *  thisRecord.addField("systemId", "5");
     *  thisRecord.addField("clientId", "4");
     *  thisRecord.addField("status", "0");
     *  thisRecord.addField("message",
     *  "INFO: PilotOnlineReader-> Started Tue Oct 29 14:50:00 EST 2002" + lf +
     *  "INFO: Processing organizations/vehicles: 59 for Mon Oct 28 12:00:00 EST 2002 through Fri Nov 01 12:00:00 EST 2002" + lf +
     *  "INFO: Vehicles added-> 137" + lf +
     *  "INFO: Processing pictures (resize/copy): 134" + lf +
     *  "INFO: FTP Sending pictures" + lf +
     *  "INFO: FTP Sending data" + lf +
     *  "INFO: PilotOnlineReader-> Finished Tue Oct 29 14:55:28 EST 2002");
     *  writer.save(thisRecord);
     *  logger.info(writer.getLastResponse());
     *  }
     */
    return true;
  }
}


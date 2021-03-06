/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader.netdescisions;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.modules.contacts.base.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: NetDescAccountsReader.java,v 1.1.2.1 2003/01/22 23:10:49
 *          akhi_m Exp $
 * @created January 21, 2003
 */
public class NetDescAccountsReader implements DataReader {

  private static final Logger logger = Logger.getLogger(org.aspcfs.apps.transfer.reader.netdescisions.NetDescAccountsReader.class);

  private DataWriter writer = null;
  private PropertyMapList mappings = null;
  private String excelFile = "ActToCFS22Jan03.xls";
  private boolean ignoreRow1 = true;
  private String propertyFile = null;


  /**
   * Sets the excelFile attribute of the NetDescAccountsReader object
   *
   * @param tmp The new excelFile value
   */
  public void setExcelFile(String tmp) {
    this.excelFile = tmp;
  }


  /**
   * Sets the ignoreRow1 attribute of the NetDescAccountsReader object
   *
   * @param tmp The new ignoreRow1 value
   */
  public void setIgnoreRow1(boolean tmp) {
    this.ignoreRow1 = tmp;
  }


  /**
   * Sets the propertyFile attribute of the NetDescAccountsReader object
   *
   * @param tmp The new propertyFile value
   */
  public void setPropertyFile(String tmp) {
    this.propertyFile = tmp;
  }


  /**
   * Gets the excelFile attribute of the NetDescAccountsReader object
   *
   * @return The excelFile value
   */
  public String getExcelFile() {
    return excelFile;
  }


  /**
   * Gets the ignoreRow1 attribute of the NetDescAccountsReader object
   *
   * @return The ignoreRow1 value
   */
  public boolean getIgnoreRow1() {
    return ignoreRow1;
  }


  /**
   * Gets the version attribute of the NetDescAccountsReader object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the NetDescAccountsReader object
   *
   * @return The name value
   */
  public String getName() {
    return "Excel NetDescisions Accounts Transfer Reader";
  }


  /**
   * Gets the description attribute of the NetDescAccountsReader object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Reads data from an Excel file formatted with Account Contacts data";
  }


  /**
   * Gets the propertyFile attribute of the NetDescAccountsReader object
   *
   * @return The propertyFile value
   */
  public String getPropertyFile() {
    return propertyFile;
  }


  /**
   * Gets the configured attribute of the NetDescAccountsReader object
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    logger.info("Checking Reader Configuration");
    if (excelFile == null || "".equals(excelFile)) {
      logger.info("Reader Configuration FAILED -- Excel File not specified ");
      return false;
    }
    File testFile = new File(excelFile);
    if (!testFile.exists()) {
      logger.info("Reader Configuration FAILED -- Excel File does not exist ");
      return false;
    }
    try {
      mappings = new PropertyMapList(propertyFile, new ArrayList());
    } catch (Exception e) {
      logger.info(e.toString());
      return false;
    }
    return true;
  }


  /**
   * Processes the excel file & creates CFS mapping objects using
   * PropertyMapList.<br>
   * Also shoots of the CFSHttpXmlWriter to save the CFS objects
   *
   * @param writer Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    this.writer = writer;
    boolean processOK = true;

    logger.info("Processing excel file" + excelFile);
    try {
      //FileWriter logs = new FileWriter(new File("NetDescLog.txt"));
      //logs.write("Invalid Records\n");
      int userId = 1;
      DataRecord newUserRecord = new DataRecord();
      newUserRecord.setName("user");
      newUserRecord.setAction("insert");
      newUserRecord.addField("guid", userId);
      newUserRecord.addField("username", "Electronic Importer");
      newUserRecord.addField("contactId", 1030);
      newUserRecord.addField("encryptedPassword", "none");
      writer.save(newUserRecord);

      HashMap accounts = new HashMap();

      POIFSFileSystem fs =
          new POIFSFileSystem(new FileInputStream(excelFile));
      System.out.println("INFO: Created FileSystem");
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      System.out.println("INFO: Created WorkBook");
      HSSFSheet sheet = wb.getSheetAt(0);
      System.out.println("INFO: Got Sheet");
      int rows = sheet.getLastRowNum();
      int beginningRow = 0;
      if (ignoreRow1) {
        ++beginningRow;
      }
      for (int r = beginningRow; r < rows + 1; r++) {
        HSSFRow row = sheet.getRow(r);
        if (row != null) {
          logger.info("ROW " + row.getRowNum());
          short firstCellNum = (short) row.getFirstCellNum();
          logger.info(
              "Processing Account " + getCellValue(row.getCell(firstCellNum)));

          int orgId = r;

          //build the contact
          logger.info(
              "Adding Contact " + getCellValue(
                  row.getCell((short) (firstCellNum + 2))));
          Contact cfsContact = null;
          if (!"".equals(
              getCellValue(row.getCell((short) (firstCellNum + 2))))) {
            cfsContact = new Contact();
            cfsContact.setId(r);
            String contactInfo = getCellValue(
                row.getCell((short) (firstCellNum + 2)));
            if (!"".equals(contactInfo)) {
              StringTokenizer st = new StringTokenizer(contactInfo, ",");
              if (st.countTokens() > 1) {
                cfsContact.setNameLast(st.nextToken());
                cfsContact.setNameFirst(st.nextToken());
              } else {
                StringTokenizer st1 = new StringTokenizer(contactInfo, " ");
                cfsContact.setNameFirst(st1.nextToken());
                if (st1.hasMoreTokens()) {
                  String middleName = "";
                  while (st1.hasMoreTokens()) {
                    if (st1.countTokens() == 1) {
                      cfsContact.setNameMiddle(middleName);
                      //last word to be last name
                      cfsContact.setNameLast(st1.nextToken());
                    } else {
                      middleName += st1.nextToken();
                      if (st1.countTokens() > 1) {
                        middleName = middleName + " ";
                      }
                    }
                  }
                  //cfsContact.setNameLast(st1.nextToken());
                } else {
                  cfsContact.setNameLast(cfsContact.getNameFirst());
                  cfsContact.setNameFirst("");
                }
              }
            }
            cfsContact.setTitle(
                getCellValue(row.getCell((short) (firstCellNum + 13))));
            cfsContact.setNameSalutation(
                getCellValue(row.getCell((short) (firstCellNum + 12))));
            cfsContact.setEnteredBy(userId);
            cfsContact.setOwner(userId);
            cfsContact.setModifiedBy(userId);
            //cfsContact.setOrgId(orgId);

            //add the account name under contact
            cfsContact.setCompany(getCellValue(row.getCell(firstCellNum)));
          } else {
            logger.info(
                "INVALID RECORD : No Contact Name Associated with " + r);
            //logs.write(String.valueOf(r) + " --" +  getCellValue(row.getCell(firstCellNum)) + "\n");
          }

          //build the Contact's Address
          ContactAddress cfsAddress = null;

          if (!"".equals(
              getCellValue(row.getCell((short) (firstCellNum + 3))))) {
            logger.info("Adding Contact Address ");
            cfsAddress = new ContactAddress();

            //trim address line 1 depending on city
            String addressLine1 = getCellValue(
                row.getCell((short) (firstCellNum + 3)));
            String city = getCellValue(
                row.getCell((short) (firstCellNum + 4)));
            if (addressLine1 != null && city != null && !"".equals(
                addressLine1) && !"".equals(city) && addressLine1.indexOf(
                    city) != -1) {
              if (!"NA".equals(addressLine1) && !"NA".equals(city)) {
                addressLine1 = addressLine1.substring(
                    0, addressLine1.indexOf(city) - 1);
                if (addressLine1.endsWith(",")) {
                  addressLine1 = addressLine1.substring(
                      0, addressLine1.length() - 1);
                }
              }
            }
            cfsAddress.setStreetAddressLine1(addressLine1);
            cfsAddress.setType(1);
            cfsAddress.setCity(
                getCellValue(row.getCell((short) (firstCellNum + 4))));
            cfsAddress.setState(
                getCellValue(row.getCell((short) (firstCellNum + 5))).toUpperCase());
            cfsAddress.setZip(
                getCellValue(row.getCell((short) (firstCellNum + 6))));
            cfsAddress.setCountry(
                getCellValue(row.getCell((short) (firstCellNum + 7))));
            cfsAddress.setContactId(r);
            cfsAddress.setEnteredBy(userId);
            cfsAddress.setModifiedBy(userId);
          }

          //build Company's Phone Numbers

          ContactPhoneNumberList cfsContPhList = new ContactPhoneNumberList();
          String NetDescComPN = getCellValue(
              row.getCell((short) (firstCellNum + 9)));
          if (!"".equals(NetDescComPN)) {
            StringTokenizer st = new StringTokenizer(NetDescComPN, "/");
            while (st.hasMoreTokens()) {
              ContactPhoneNumber cfsPhNumber = new ContactPhoneNumber();
              cfsPhNumber.setType(1);
              cfsPhNumber.setNumber(st.nextToken());
              cfsPhNumber.setContactId(r);
              cfsPhNumber.setEnteredBy(userId);
              cfsPhNumber.setModifiedBy(userId);
              cfsContPhList.add(cfsPhNumber);
            }
          }

          //build Contact's Phone Numbers
          logger.info("Contact Phone Numbers ");
          String NetDescContPN = getCellValue(
              row.getCell((short) (firstCellNum + 8)));
          if (!"".equals(NetDescContPN)) {
            StringTokenizer st = new StringTokenizer(NetDescContPN, "/");
            while (st.hasMoreTokens()) {
              ContactPhoneNumber cfsPhNumber = new ContactPhoneNumber();
              cfsPhNumber.setType(1);
              StringTokenizer st1 = new StringTokenizer(st.nextToken(), "x");
              String number = st1.nextToken();
              logger.info("Primary -- " + number);
              cfsPhNumber.setNumber(number);
              if (st1.hasMoreTokens()) {
                cfsPhNumber.setExtension(st1.nextToken());
              }
              cfsPhNumber.setEnteredBy(userId);
              cfsPhNumber.setModifiedBy(userId);
              cfsPhNumber.setContactId(r);
              cfsContPhList.add(cfsPhNumber);
            }
          }
          //build Contact's Fax Number
          ContactPhoneNumber cfsFaxPhNumber = null;
          if (!"".equals(
              getCellValue(row.getCell((short) (firstCellNum + 10))))) {
            cfsFaxPhNumber = new ContactPhoneNumber();
            cfsFaxPhNumber.setType(3);
            cfsFaxPhNumber.setEnteredBy(userId);
            cfsFaxPhNumber.setModifiedBy(userId);
            String number = getCellValue(
                row.getCell((short) (firstCellNum + 10)));
            logger.info("Fax -- " + number);
            cfsFaxPhNumber.setNumber(number);
            cfsFaxPhNumber.setContactId(r);
            cfsContPhList.add(cfsFaxPhNumber);
          }

          //build Contact's Mobile Number

          ContactPhoneNumber cfsMobilePhNumber = null;
          if (!"".equals(
              getCellValue(row.getCell((short) (firstCellNum + 11))))) {
            cfsMobilePhNumber = new ContactPhoneNumber();
            cfsMobilePhNumber.setType(7);
            cfsMobilePhNumber.setEnteredBy(userId);
            cfsMobilePhNumber.setModifiedBy(userId);
            String number = getCellValue(
                row.getCell((short) (firstCellNum + 11)));
            logger.info("Mobile -- " + number);
            cfsMobilePhNumber.setNumber(number);
            cfsMobilePhNumber.setContactId(r);
            cfsContPhList.add(cfsMobilePhNumber);
          }

          //build Contact's Email Addresse's
          logger.info(
              "EmailAddress -- " + getCellValue(
                  row.getCell((short) (firstCellNum + 15))));
          ContactEmailAddress cfsEmailAddress = null;
          if (!"".equals(
              getCellValue(row.getCell((short) (firstCellNum + 15))))) {
            cfsEmailAddress = new ContactEmailAddress();
            cfsEmailAddress.setType(1);
            cfsEmailAddress.setEmail(
                getCellValue(row.getCell((short) (firstCellNum + 15))));
            cfsEmailAddress.setContactId(r);
            cfsEmailAddress.setEnteredBy(userId);
            cfsEmailAddress.setModifiedBy(userId);
          }

          logger.info("ImportAccounts --> Saving Contact ");

          //Save Contact Info inlcuding address, phone numbers only if contact exists.
          if (cfsContact != null) {
            DataRecord contRecord = mappings.createDataRecord(
                cfsContact, "insert");
            if (!writer.save(contRecord)) {
              processOK = false;
            }

            //Save Contact Address
            if (cfsAddress != null) {
              DataRecord addRecord = mappings.createDataRecord(
                  cfsAddress, "insert");
              if (!writer.save(addRecord)) {
                processOK = false;
              }
            }

            //Save Contact Fax, Mobile & Phone Numbers
            if (cfsContPhList != null) {
              processOK = mappings.saveList(writer, cfsContPhList, "insert");
            }

            //Save Contact EmailAddress
            if (cfsEmailAddress != null) {
              DataRecord emailRecord = mappings.createDataRecord(
                  cfsEmailAddress, "insert");
              if (!writer.save(emailRecord)) {
                processOK = false;
              }
            }
          }

          if (!processOK) {
            return false;
          }
        }
      }
    } catch (IOException io) {
      io.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   * Gets the Excel cell value depending on the Type of the cell.
   *
   * @param cell Description of the Parameter
   * @return The cellValue value
   */
  private String getCellValue(HSSFCell cell) {
    String value = null;
    switch (cell.getCellType()) {
      case HSSFCell.CELL_TYPE_BLANK:
        value = "";
        break;
      case HSSFCell.CELL_TYPE_BOOLEAN:
        value = Boolean.toString(cell.getBooleanCellValue());
        break;
      case HSSFCell.CELL_TYPE_NUMERIC:
        value = Double.toString(cell.getNumericCellValue()).trim();
        break;
      case HSSFCell.CELL_TYPE_STRING:
        value = cell.getStringCellValue().trim();
        break;
      default:
        value = "";
        break;
    }
    return value;
  }

}


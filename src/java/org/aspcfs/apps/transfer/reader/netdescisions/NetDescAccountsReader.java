package org.aspcfs.apps.transfer.reader.netdescisions;

import java.sql.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.admin.base.*;
import java.util.logging.*;
import java.util.*;
import java.io.*;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 21, 2003
 *@version    $Id: NetDescAccountsReader.java,v 1.1.2.1 2003/01/22 23:10:49
 *      akhi_m Exp $
 */
public class NetDescAccountsReader implements DataReader {

  private DataWriter writer = null;
  private PropertyMapList mappings = null;
  private String excelFile = "ActToCFS22Jan03.xls";
  private boolean ignoreRow1 = true;
  private String propertyFile = null;


  /**
   *  Sets the excelFile attribute of the NetDescAccountsReader object
   *
   *@param  tmp  The new excelFile value
   */
  public void setExcelFile(String tmp) {
    this.excelFile = tmp;
  }


  /**
   *  Sets the ignoreRow1 attribute of the NetDescAccountsReader object
   *
   *@param  tmp  The new ignoreRow1 value
   */
  public void setIgnoreRow1(boolean tmp) {
    this.ignoreRow1 = tmp;
  }


  /**
   *  Sets the propertyFile attribute of the NetDescAccountsReader object
   *
   *@param  tmp  The new propertyFile value
   */
  public void setPropertyFile(String tmp) {
    this.propertyFile = tmp;
  }


  /**
   *  Gets the excelFile attribute of the NetDescAccountsReader object
   *
   *@return    The excelFile value
   */
  public String getExcelFile() {
    return excelFile;
  }


  /**
   *  Gets the ignoreRow1 attribute of the NetDescAccountsReader object
   *
   *@return    The ignoreRow1 value
   */
  public boolean getIgnoreRow1() {
    return ignoreRow1;
  }


  /**
   *  Gets the version attribute of the NetDescAccountsReader object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the NetDescAccountsReader object
   *
   *@return    The name value
   */
  public String getName() {
    return "Excel NetDescisions Accounts Transfer Reader";
  }


  /**
   *  Gets the description attribute of the NetDescAccountsReader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Reads data from an Excel file formatted with Account Contacts data";
  }


  /**
   *  Gets the propertyFile attribute of the NetDescAccountsReader object
   *
   *@return    The propertyFile value
   */
  public String getPropertyFile() {
    return propertyFile;
  }


  /**
   *  Gets the configured attribute of the NetDescAccountsReader object
   *
   *@return    The configured value
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
   *  Processes the excel file & creates CFS mapping objects using
   *  PropertyMapList.<br>
   *  Also shoots of the CFSHttpXmlWriter to save the CFS objects
   *
   *@param  writer  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    this.writer = writer;
    boolean processOK = true;

    logger.info("Processing excel file" + excelFile);
    try {
      int userId = 1;
      DataRecord newUserRecord = new DataRecord();
      newUserRecord.setName("user");
      newUserRecord.setAction("insert");
      newUserRecord.addField("guid", userId);
      newUserRecord.addField("username", "Importer");
      newUserRecord.addField("contactId",  1030);
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
          logger.info("Processing Account " + getCellValue(row.getCell(firstCellNum)));

          int orgId = r;
          //Save Account if required
          if (!accounts.containsKey(getCellValue(row.getCell(firstCellNum)))) {
            //build the organization
            Organization thisOrg = new Organization();
            thisOrg.setOrgId(r);
            thisOrg.setName(getCellValue(row.getCell(firstCellNum)));
            thisOrg.setUrl(getCellValue(row.getCell((short) (firstCellNum + 14))));
            thisOrg.setOwner(userId);
            thisOrg.setEnteredBy(userId);
            thisOrg.setModifiedBy(userId);
            DataRecord thisRecord = mappings.createDataRecord(thisOrg, "insert");
            accounts.put(thisOrg.getName(), new Integer(r));
            if (!writer.save(thisRecord)) {
              processOK = false;
            }
          } else {
            orgId = ((Integer) accounts.get(getCellValue(row.getCell(firstCellNum)))).intValue();
          }

          //build the contact
          logger.info("Adding Contact " + getCellValue(row.getCell((short) (firstCellNum + 2))));
          Contact cfsContact = null;
          if (!"".equals(getCellValue(row.getCell((short) (firstCellNum + 2))))) {
            cfsContact = new Contact();
            cfsContact.setId(r);
            String contactInfo = getCellValue(row.getCell((short) (firstCellNum + 2)));
            if (!"".equals(contactInfo)) {
              StringTokenizer st = new StringTokenizer(contactInfo, ",");
              if (st.countTokens() > 1) {
                cfsContact.setNameLast(st.nextToken());
                cfsContact.setNameFirst(st.nextToken());
              } else {
                StringTokenizer st1 = new StringTokenizer(contactInfo, " ");
                cfsContact.setNameFirst(st1.nextToken());
                if (st1.hasMoreTokens()) {
                  cfsContact.setNameLast(st1.nextToken());
                } else {
                  cfsContact.setNameLast(cfsContact.getNameFirst());
                  cfsContact.setNameFirst("");
                }
              }
            }
            cfsContact.setTitle(getCellValue(row.getCell((short) (firstCellNum + 13))));
            cfsContact.setNameSalutation(getCellValue(row.getCell((short) (firstCellNum + 12))));
            cfsContact.setEnteredBy(userId);
            cfsContact.setModifiedBy(userId);
            cfsContact.setOrgId(orgId);
          }

          //build the Contact's Address
          ContactAddress cfsAddress = null;

          if (!"".equals(getCellValue(row.getCell((short) (firstCellNum + 3))))) {
            logger.info("Adding Contact Address ");
            cfsAddress = new ContactAddress();
            String address = getCellValue(row.getCell((short) (firstCellNum + 3)));
            StringTokenizer st = new StringTokenizer(address, ",");
            if (st.hasMoreTokens()) {
              cfsAddress.setStreetAddressLine1(st.nextToken());
              if (st.hasMoreTokens()) {
                cfsAddress.setStreetAddressLine2(st.nextToken());
              }
            }
            cfsAddress.setStreetAddressLine1(getCellValue(row.getCell((short) (firstCellNum + 3))));
            cfsAddress.setType(2);
            cfsAddress.setCity(getCellValue(row.getCell((short) (firstCellNum + 4))));
            cfsAddress.setState(getCellValue(row.getCell((short) (firstCellNum + 5))));
            cfsAddress.setZip(getCellValue(row.getCell((short) (firstCellNum + 6))));
            cfsAddress.setCountry(getCellValue(row.getCell((short) (firstCellNum + 7))));
            cfsAddress.setContactId(r);
            cfsAddress.setEnteredBy(userId);
            cfsAddress.setModifiedBy(userId);
          }

          //build Company's Phone Numbers

          OrganizationPhoneNumberList cfsCompPhList = null;
          String NetDescComPN = getCellValue(row.getCell((short) (firstCellNum + 9)));
          if (!"".equals(NetDescComPN)) {
            cfsCompPhList = new OrganizationPhoneNumberList();
            StringTokenizer st = new StringTokenizer(NetDescComPN, "/");
            while (st.hasMoreTokens()) {
              OrganizationPhoneNumber cfsPhNumber = new OrganizationPhoneNumber();
              cfsPhNumber.setType(1);
              cfsPhNumber.setNumber(st.nextToken());
              cfsPhNumber.setOrgId(orgId);
              cfsPhNumber.setEnteredBy(userId);
              cfsPhNumber.setModifiedBy(userId);
              cfsCompPhList.add(cfsPhNumber);
            }
          }

          //build Contact's Phone Numbers
          logger.info("Contact Phone Numbers ");
          ContactPhoneNumberList cfsContPhList = new ContactPhoneNumberList();
          String NetDescContPN = getCellValue(row.getCell((short) (firstCellNum + 8)));
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
          if (!"".equals(getCellValue(row.getCell((short) (firstCellNum + 10))))) {
            cfsFaxPhNumber = new ContactPhoneNumber();
            cfsFaxPhNumber.setType(3);
            cfsFaxPhNumber.setEnteredBy(userId);
            cfsFaxPhNumber.setModifiedBy(userId);
            String number = getCellValue(row.getCell((short) (firstCellNum + 10)));
            logger.info("Fax -- " + number);
            cfsFaxPhNumber.setNumber(number);
            cfsFaxPhNumber.setContactId(r);
            cfsContPhList.add(cfsFaxPhNumber);
          }

          //build Contact's Mobile Number

          ContactPhoneNumber cfsMobilePhNumber = null;
          if (!"".equals(getCellValue(row.getCell((short) (firstCellNum + 11))))) {
            cfsMobilePhNumber = new ContactPhoneNumber();
            cfsMobilePhNumber.setType(7);
            cfsMobilePhNumber.setEnteredBy(userId);
            cfsMobilePhNumber.setModifiedBy(userId);
            String number = getCellValue(row.getCell((short) (firstCellNum + 11)));
            logger.info("Mobile -- " + number);
            cfsMobilePhNumber.setNumber(number);
            cfsMobilePhNumber.setContactId(r);
            cfsContPhList.add(cfsMobilePhNumber);
          }

          //build Contact's Email Addresse's
          logger.info("EmailAddress -- " + getCellValue(row.getCell((short) (firstCellNum + 15))));
          ContactEmailAddress cfsEmailAddress = null;
          if (!"".equals(getCellValue(row.getCell((short) (firstCellNum + 15))))) {
            cfsEmailAddress = new ContactEmailAddress();
            cfsEmailAddress.setType(2);
            cfsEmailAddress.setEmail(getCellValue(row.getCell((short) (firstCellNum + 15))));
            cfsEmailAddress.setContactId(r);
            cfsEmailAddress.setEnteredBy(userId);
            cfsEmailAddress.setModifiedBy(userId);
          }

          logger.info("ImportAccounts --> Saving Contact ");

          //Save Contact Info inlcuding address, phone numbers only if contact exists.
          if (cfsContact != null) {
            DataRecord contRecord = mappings.createDataRecord(cfsContact, "insert");
            if (!writer.save(contRecord)) {
              processOK = false;
            }

            //Save Contact Address
            if (cfsAddress != null) {
              DataRecord addRecord = mappings.createDataRecord(cfsAddress, "insert");
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
              DataRecord emailRecord = mappings.createDataRecord(cfsEmailAddress, "insert");
              if (!writer.save(emailRecord)) {
                processOK = false;
              }
            }
          }

          //Save Company Phone Numbers
          if (cfsCompPhList != null) {
            processOK = mappings.saveList(writer, cfsCompPhList, "insert");
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
   *  Gets the Excel cell value depending on the Type of the cell.
   *
   *@param  cell  Description of the Parameter
   *@return       The cellValue value
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


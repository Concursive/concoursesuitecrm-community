package org.aspcfs.apps.transfer.reader.netdescisions;

import java.sql.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
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
  public boolean ignoreRow1 = true;


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
   *  Gets the configured attribute of the NetDescAccountsReader object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    if (excelFile == null || "".equals(excelFile)) {
      return false;
    }
    File testFile = new File(excelFile);
    if (!testFile.exists()) {
      return false;
    }
    mappings = new PropertyMapList(excelFile, new ArrayList());
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
          short firstCellNum = (short) (row.getFirstCellNum() + 1);

          logger.info("Processing Account " + row.getCell(firstCellNum).getStringCellValue());
          //build the organization
          Organization thisOrg = new Organization();
          thisOrg.setOrgId(r);
          thisOrg.setName(row.getCell(firstCellNum).getStringCellValue());
          thisOrg.setUrl(row.getCell((short) (firstCellNum + 14)).getStringCellValue());
          thisOrg.setEnteredBy(userId);
          thisOrg.setModifiedBy(userId);

          //build the contact
          logger.info("Adding Contact " + row.getCell((short) (firstCellNum + 2)).getStringCellValue());
          Contact cfsContact = new Contact();
          cfsContact.setId(r);
          String contactInfo = row.getCell((short) (firstCellNum + 2)).getStringCellValue();
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
              }
            }
          }
          cfsContact.setTitle(row.getCell((short) (firstCellNum + 13)).getStringCellValue());
          cfsContact.setEnteredBy(userId);
          cfsContact.setModifiedBy(userId);
          logger.info("Adding Contact Address ");
          //build the Contact's Address
          ContactAddress cfsAddress = new ContactAddress();
          cfsAddress.setStreetAddressLine1(row.getCell((short) (firstCellNum + 3)).getStringCellValue());
          cfsAddress.setType(2);
          cfsAddress.setCity(row.getCell((short) (firstCellNum + 4)).getStringCellValue());
          cfsAddress.setState(row.getCell((short) (firstCellNum + 5)).getStringCellValue());
          cfsAddress.setZip(row.getCell((short) (firstCellNum + 6)).getStringCellValue());
          cfsAddress.setCountry(row.getCell((short) (firstCellNum + 7)).getStringCellValue());
          cfsAddress.setContactId(r);
          cfsAddress.setEnteredBy(userId);
          cfsAddress.setModifiedBy(userId);

          //build Company's Phone Numbers
          String NetDescComPN = row.getCell((short) (firstCellNum + 9)).getStringCellValue();
          OrganizationPhoneNumberList cfsCompPhList = new OrganizationPhoneNumberList();
          if (!"".equals(NetDescComPN)) {
            StringTokenizer st = new StringTokenizer(NetDescComPN, "/");
            while (st.hasMoreTokens()) {
              OrganizationPhoneNumber cfsPhNumber = new OrganizationPhoneNumber();
              cfsPhNumber.setType(1);
              cfsPhNumber.setNumber(st.nextToken());
              cfsPhNumber.setOrgId(r);
              cfsCompPhList.add(cfsPhNumber);
            }
          }

          //build Contact's Phone Numbers
          String NetDescContPN = row.getCell((short) (firstCellNum + 8)).getStringCellValue();
          ContactPhoneNumberList cfsContPhList = new ContactPhoneNumberList();
          if (!"".equals(NetDescContPN)) {
            StringTokenizer st = new StringTokenizer(NetDescContPN, "/");
            while (st.hasMoreTokens()) {
              ContactPhoneNumber cfsPhNumber = new ContactPhoneNumber();
              cfsPhNumber.setType(1);
              StringTokenizer st1 = new StringTokenizer(st.nextToken(), "x");
              cfsPhNumber.setNumber(st1.nextToken());
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
          ContactPhoneNumber cfsFaxPhNumber = new ContactPhoneNumber();
          cfsFaxPhNumber.setType(3);
          cfsFaxPhNumber.setEnteredBy(userId);
          cfsFaxPhNumber.setModifiedBy(userId);
          cfsFaxPhNumber.setNumber(row.getCell((short) (firstCellNum + 10)).getStringCellValue());
          cfsFaxPhNumber.setContactId(r);
          cfsContPhList.add(cfsFaxPhNumber);

          //build Contact's Mobile Number
          ContactPhoneNumber cfsMobilePhNumber = new ContactPhoneNumber();
          cfsMobilePhNumber.setType(7);
          cfsMobilePhNumber.setEnteredBy(userId);
          cfsMobilePhNumber.setModifiedBy(userId);
          cfsMobilePhNumber.setNumber(row.getCell((short) (firstCellNum + 11)).getStringCellValue());
          cfsMobilePhNumber.setContactId(r);
          cfsContPhList.add(cfsFaxPhNumber);

          //build Contact's Email Addresse's
          ContactEmailAddress cfsEmailAddress = new ContactEmailAddress();
          cfsEmailAddress.setType(2);
          cfsEmailAddress.setEmail(row.getCell((short) (firstCellNum + 12)).getStringCellValue());
          cfsEmailAddress.setContactId(r);
          cfsEmailAddress.setEnteredBy(userId);
          cfsEmailAddress.setModifiedBy(userId);

          logger.info("ImportAccounts --> Saving Contact " + cfsContact.getId());

          //Save Account if required
          if (!accounts.containsKey(row.getCell(firstCellNum).getStringCellValue())) {
            DataRecord thisRecord = mappings.createDataRecord(thisOrg, "insert");
            accounts.put(thisOrg.getName(), new Integer(0));
            if (!writer.save(thisRecord)) {
              processOK = false;
            }
          } else {
            Integer count = (Integer) accounts.get(row.getCell(firstCellNum).getStringCellValue());
            accounts.remove(row.getCell(firstCellNum).getStringCellValue());
            accounts.put(row.getCell(firstCellNum).getStringCellValue(), new Integer(count.intValue() + 1));
          }

          //Save Contact Info
          DataRecord contRecord = mappings.createDataRecord(cfsContact, "insert");
          if (!writer.save(contRecord)) {
            processOK = false;
          }

          //Save Contact Address
          DataRecord addRecord = mappings.createDataRecord(cfsAddress, "insert");
          if (!writer.save(addRecord)) {
            processOK = false;
          }

          //Save Company Phone Numbers
          processOK = mappings.saveList(writer, cfsCompPhList, "insert");

          //Save Contact Fax, Mobile & Phone Numbers
          processOK = mappings.saveList(writer, cfsContPhList, "insert");

          //Save Contact EmailAddress
          DataRecord emailRecord = mappings.createDataRecord(cfsEmailAddress, "insert");
          if (!writer.save(emailRecord)) {
            processOK = false;
          }
          if (!processOK) {
            return false;
          }
        }
      }
    } catch (IOException io) {
      io.printStackTrace(System.out);
    }
    return true;
  }

}


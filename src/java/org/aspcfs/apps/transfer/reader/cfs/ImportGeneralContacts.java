package org.aspcfs.apps.transfer.reader.cfs;

import org.aspcfs.apps.transfer.reader.csvreader.CSVReader;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.apps.transfer.writer.cfshttpxmlwriter.CFSHttpXMLWriter;
import org.aspcfs.utils.*;
import org.aspcfs.utils.formatter.*;
import org.aspcfs.modules.contacts.base.*;

/**
 *  Imports a list of contacts into CFS General Contacts
 *
 *@author     matt rajkowski
 *@created    March 3, 2003
 *@version    $Id: ImportGeneralContacts.java,v 1.1.2.3 2003/03/05 21:19:14
 *      mrajkowski Exp $
 */
public class ImportGeneralContacts extends CSVReader {

  private int OWNER = 0;
  private int NAME_SALUTATION = 0;
  private int FIRST_NAME = 0;
  private int MIDDLE_NAME = 0;
  private int LAST_NAME = 0;
  private int SUFFIX = 0;
  private int COMPANY_NAME = 0;
  private int TITLE = 0;
  private int BUSINESS_ADDRESS_1 = 0;
  private int BUSINESS_ADDRESS_2 = 0;
  private int BUSINESS_ADDRESS_3 = 0;
  private int BUSINESS_CITY = 0;
  private int BUSINESS_STATE = 0;
  private int BUSINESS_ZIP = 0;
  private int BUSINESS_COUNTRY = 0;
  private int HOME_ADDRESS_1 = 0;
  private int HOME_ADDRESS_2 = 0;
  private int HOME_ADDRESS_3 = 0;
  private int HOME_CITY = 0;
  private int HOME_STATE = 0;
  private int HOME_ZIP = 0;
  private int HOME_COUNTRY = 0;
  private int BUSINESS_PHONE = 0;
  private int BUSINESS_2_PHONE = 0;
  private int BUSINESS_FAX = 0;
  private int HOME_PHONE = 0;
  private int HOME_2_PHONE = 0;
  private int HOME_FAX = 0;
  private int MOBILE_PHONE = 0;
  private int OTHER_PHONE = 0;
  private int PAGER = 0;
  private int BUSINESS_EMAIL = 0;
  private int PERSONAL_EMAIL = 0;
  private int OTHER_EMAIL = 0;
  private int NOTES = 0;


  /**
   *  Gets the version attribute of the ImportGeneralContacts object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the ImportGeneralContacts object
   *
   *@return    The name value
   */
  public String getName() {
    return "Dark Horse CRM General Contacts Importer";
  }


  /**
   *  Gets the description attribute of the ImportGeneralContacts object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Reads general contacts from a text file based on CFS specifications";
  }


  /**
   *  A required method which actually performs the processing of reading
   *  contact records.
   *
   *@param  writer  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    boolean processOK = true;

    try {
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("import.out", false)));

      //Create a new invisible user who will import these records (enterdBy) so
      //that records can be backed out if necessary
      //TODO: create user record
      int userId = 1;
      int contactId = 1;

      //Create the user, associated with the contact
      DataRecord newUserRecord = new DataRecord();
      newUserRecord.setName("user");
      newUserRecord.setAction("insert");
      newUserRecord.addField("guid", userId);
      newUserRecord.addField("username", "Electronic Import");
      //newUserRecord.addField("contactId", contactId, "contact", null);
      newUserRecord.addField("encryptedPassword", "none");
      newUserRecord.addField("enteredBy", userId);
      newUserRecord.addField("modifiedBy", userId);
      writer.save(newUserRecord);

      //Need a new contact
      DataRecord newContactRecord = new DataRecord();
      newContactRecord.setName("contact");
      newContactRecord.setAction("insert");
      newContactRecord.addField("guid", contactId);
      newContactRecord.addField("userId", userId, "user", null);
      newContactRecord.addField("nameFirst", "");
      newContactRecord.addField("nameLast", "Electronic Import");
      newContactRecord.addField("orgId", "0");
      newContactRecord.addField("enteredBy", userId);
      newContactRecord.addField("modifiedBy", userId);
      newContactRecord.addField("enabled", "false");
      writer.save(newContactRecord);
      /*
       *  XMLUtils responseXML = new XMLUtils(writer.getLastResponse(), true);
       *  System.out.println(XMLUtils.getNodeText(responseXML.getFirstElement("id")));
       *  /Update the user with the contact info
       *  DataRecord newUserRecord = new DataRecord();
       *  newUserRecord.setName("user");
       *  newUserRecord.setAction("insert");
       *  newUserRecord.addField("guid", userId);
       *  newUserRecord.addField("username", "Electronic Import");
       *  newUserRecord.addField("contactId", contactId, "contact", null);
       *  newUserRecord.addField("encryptedPassword", "none");
       *  newUserRecord.addField("enteredBy", userId);
       *  newUserRecord.addField("modifiedBy", userId);
       *  writer.save(newUserRecord);
       */
      //Prepare the formatters
      AddressFormatter addressFormatter = new AddressFormatter();
      EmailAddressFormatter emailFormatter = new EmailAddressFormatter();
      PhoneNumberFormatter phoneFormatter = new PhoneNumberFormatter();

      //Parse the text file
      BufferedReader in = new BufferedReader(new FileReader(csvFile));
      String line = null;
      int lineNumber = 0;
      while ((line = in.readLine()) != null) {
        ++lineNumber;
        //For each line use the parseExcelCSVLine method to get a record
        ArrayList thisRecord = new ArrayList(StringUtils.parseExcelCSVLine(line));
        if (lineNumber == 1) {
          //Process the column mappings
          OWNER = findColumn(thisRecord, "Owner");
          NAME_SALUTATION = findColumn(thisRecord, "Salutation");
          FIRST_NAME = findColumn(thisRecord, new String[]{"FirstName", "First Name"});
          MIDDLE_NAME = findColumn(thisRecord, "Middle Name");
          LAST_NAME = findColumn(thisRecord, new String[]{"LastName", "Last Name"});
          SUFFIX = findColumn(thisRecord, "Suffix");
          COMPANY_NAME = findColumn(thisRecord, new String[]{"Company", "Company Name"});
          TITLE = findColumn(thisRecord, "Title");
          BUSINESS_ADDRESS_1 = findColumn(thisRecord, "Business Address Line 1");
          BUSINESS_ADDRESS_2 = findColumn(thisRecord, "Business Address Line 2");
          BUSINESS_ADDRESS_3 = findColumn(thisRecord, "Business Address Line 3");
          BUSINESS_CITY = findColumn(thisRecord, new String[]{"BusinessCity", "Business City"});
          BUSINESS_STATE = findColumn(thisRecord, new String[]{"Business State", "Business State/Province"});
          BUSINESS_ZIP = findColumn(thisRecord, new String[]{"Business Zip", "Business Postal Code"});
          BUSINESS_COUNTRY = findColumn(thisRecord, "Business Country");
          HOME_ADDRESS_1 = findColumn(thisRecord, "Home Address Line 1");
          HOME_ADDRESS_2 = findColumn(thisRecord, "Home Address Line 2");
          HOME_ADDRESS_3 = findColumn(thisRecord, "Home Address Line 3");
          HOME_CITY = findColumn(thisRecord, new String[]{"HomeCity", "Home City"});
          HOME_STATE = findColumn(thisRecord, "Home State/Province");
          HOME_ZIP = findColumn(thisRecord, "Home Postal Code");
          HOME_COUNTRY = findColumn(thisRecord, "Home Country");
          BUSINESS_PHONE = findColumn(thisRecord, new String[]{"BusinessPhone", "Business Phone"});
          BUSINESS_2_PHONE = findColumn(thisRecord, "Business2 Phone");
          BUSINESS_FAX = findColumn(thisRecord, new String[]{"BusinessFax", "Business Fax"});
          HOME_PHONE = findColumn(thisRecord, "Home Phone");
          HOME_2_PHONE = findColumn(thisRecord, "Home2 Phone");
          HOME_FAX = findColumn(thisRecord, "Home Fax");
          MOBILE_PHONE = findColumn(thisRecord, "Mobile Phone");
          OTHER_PHONE = findColumn(thisRecord, "Other Phone");
          PAGER = findColumn(thisRecord, "Pager");
          BUSINESS_EMAIL = findColumn(thisRecord, "Business Email");
          PERSONAL_EMAIL = findColumn(thisRecord, new String[]{"Home Email", "Personal Email"});
          OTHER_EMAIL = findColumn(thisRecord, "Other Email");
          NOTES = findColumn(thisRecord, "Notes");
          continue;
        }
        ++contactId;

        //Contact Record Fields
        Contact thisContact = new Contact();
        ContactEmailAddressList emailAddressList = thisContact.getEmailAddressList();
        ContactPhoneNumberList phoneNumberList = thisContact.getPhoneNumberList();
        ContactAddressList addressList = thisContact.getAddressList();

        thisContact.setId(contactId);
        thisContact.setEnteredBy(userId);
        if (OWNER > 0) {
          thisContact.setOwner(getValue(thisRecord, OWNER));
        } else {
          thisContact.setOwner(userId);
        }
        thisContact.setModifiedBy(userId);
        //Contact Fields
        thisContact.setNameSalutation(getValue(thisRecord, NAME_SALUTATION));
        thisContact.setNameFirst(getValue(thisRecord, FIRST_NAME));
        thisContact.setNameMiddle(getValue(thisRecord, MIDDLE_NAME));
        thisContact.setNameLast(getValue(thisRecord, LAST_NAME));
        thisContact.setNameSuffix(getValue(thisRecord, SUFFIX));
        thisContact.setCompany(getValue(thisRecord, COMPANY_NAME));
        thisContact.setTitle(getValue(thisRecord, TITLE));

        //ContactAddress Record Fields
        ContactAddress businessAddress = new ContactAddress();
        businessAddress.setContactId(contactId);
        businessAddress.setType(1);
        businessAddress.setEnteredBy(userId);
        businessAddress.setModifiedBy(userId);
        //ContactAddress Fields
        businessAddress.setStreetAddressLine1(getValue(thisRecord, BUSINESS_ADDRESS_1));
        businessAddress.setStreetAddressLine2(getValue(thisRecord, BUSINESS_ADDRESS_2));
        businessAddress.setStreetAddressLine3(getValue(thisRecord, BUSINESS_ADDRESS_3));
        businessAddress.setCity(getValue(thisRecord, BUSINESS_CITY));
        businessAddress.setState(getValue(thisRecord, BUSINESS_STATE));
        businessAddress.setZip(getValue(thisRecord, BUSINESS_ZIP));
        businessAddress.setCountry(getValue(thisRecord, BUSINESS_COUNTRY));
        if (businessAddress.isValid()) {
          addressFormatter.format(businessAddress);
          addressList.add(businessAddress);
        }

        //ContactAddress Record Fields
        ContactAddress homeAddress = new ContactAddress();
        homeAddress.setContactId(contactId);
        homeAddress.setType(2);
        homeAddress.setEnteredBy(userId);
        homeAddress.setModifiedBy(userId);
        //ContactAddress Fields
        homeAddress.setStreetAddressLine1(getValue(thisRecord, HOME_ADDRESS_1));
        homeAddress.setStreetAddressLine2(getValue(thisRecord, HOME_ADDRESS_2));
        homeAddress.setStreetAddressLine3(getValue(thisRecord, HOME_ADDRESS_3));
        homeAddress.setCity(getValue(thisRecord, HOME_CITY));
        homeAddress.setState(getValue(thisRecord, HOME_STATE));
        homeAddress.setZip(getValue(thisRecord, HOME_ZIP));
        homeAddress.setCountry(getValue(thisRecord, HOME_COUNTRY));
        if (homeAddress.isValid()) {
          addressFormatter.format(homeAddress);
          addressList.add(homeAddress);
        }

        //Determine the locale to be used for formatting
        Locale thisLocale = businessAddress.getLocale();
        if (thisLocale == null) {
          thisLocale = homeAddress.getLocale();
        }

        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(userId);
        businessPhone.setModifiedBy(userId);
        businessPhone.setNumber(getValue(thisRecord, BUSINESS_PHONE));
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          phoneNumberList.add(businessPhone);
        }

        ContactPhoneNumber business2Phone = new ContactPhoneNumber();
        business2Phone.setContactId(contactId);
        business2Phone.setType(2);
        business2Phone.setEnteredBy(userId);
        business2Phone.setModifiedBy(userId);
        business2Phone.setNumber(getValue(thisRecord, BUSINESS_2_PHONE));
        if (business2Phone.isValid()) {
          phoneFormatter.format(business2Phone, thisLocale);
          phoneNumberList.add(business2Phone);
        }

        ContactPhoneNumber businessFax = new ContactPhoneNumber();
        businessFax.setContactId(contactId);
        businessFax.setType(3);
        businessFax.setEnteredBy(userId);
        businessFax.setModifiedBy(userId);
        businessFax.setNumber(getValue(thisRecord, BUSINESS_FAX));
        if (businessFax.isValid()) {
          phoneFormatter.format(businessFax, thisLocale);
          phoneNumberList.add(businessFax);
        }

        ContactPhoneNumber homePhone = new ContactPhoneNumber();
        homePhone.setContactId(contactId);
        homePhone.setType(4);
        homePhone.setEnteredBy(userId);
        homePhone.setModifiedBy(userId);
        homePhone.setNumber(getValue(thisRecord, HOME_PHONE));
        if (homePhone.isValid()) {
          phoneFormatter.format(homePhone, thisLocale);
          phoneNumberList.add(homePhone);
        }

        ContactPhoneNumber home2Phone = new ContactPhoneNumber();
        home2Phone.setContactId(contactId);
        home2Phone.setType(5);
        home2Phone.setEnteredBy(userId);
        home2Phone.setModifiedBy(userId);
        home2Phone.setNumber(getValue(thisRecord, HOME_2_PHONE));
        if (home2Phone.isValid()) {
          phoneFormatter.format(home2Phone, thisLocale);
          phoneNumberList.add(home2Phone);
        }

        ContactPhoneNumber homeFax = new ContactPhoneNumber();
        homeFax.setContactId(contactId);
        homeFax.setType(6);
        homeFax.setEnteredBy(userId);
        homeFax.setModifiedBy(userId);
        homeFax.setNumber(getValue(thisRecord, HOME_FAX));
        if (homeFax.isValid()) {
          phoneFormatter.format(homeFax, thisLocale);
          phoneNumberList.add(homeFax);
        }

        ContactPhoneNumber mobilePhone = new ContactPhoneNumber();
        mobilePhone.setContactId(contactId);
        mobilePhone.setType(7);
        mobilePhone.setEnteredBy(userId);
        mobilePhone.setModifiedBy(userId);
        mobilePhone.setNumber(getValue(thisRecord, MOBILE_PHONE));
        if (mobilePhone.isValid()) {
          phoneFormatter.format(mobilePhone, thisLocale);
          phoneNumberList.add(mobilePhone);
        }

        ContactPhoneNumber otherPhone = new ContactPhoneNumber();
        otherPhone.setContactId(contactId);
        otherPhone.setType(9);
        otherPhone.setEnteredBy(userId);
        otherPhone.setModifiedBy(userId);
        otherPhone.setNumber(getValue(thisRecord, OTHER_PHONE));
        if (otherPhone.isValid()) {
          phoneFormatter.format(otherPhone, thisLocale);
          phoneNumberList.add(otherPhone);
        }

        ContactPhoneNumber pager = new ContactPhoneNumber();
        pager.setContactId(contactId);
        pager.setType(8);
        pager.setEnteredBy(userId);
        pager.setModifiedBy(userId);
        pager.setNumber(getValue(thisRecord, PAGER));
        if (pager.isValid()) {
          phoneFormatter.format(pager, thisLocale);
          phoneNumberList.add(pager);
        }

        ContactEmailAddress businessEmail = new ContactEmailAddress();
        businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(userId);
        businessEmail.setModifiedBy(userId);
        businessEmail.setEmail(getValue(thisRecord, BUSINESS_EMAIL));
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          emailAddressList.add(businessEmail);
        }

        ContactEmailAddress personalEmail = new ContactEmailAddress();
        personalEmail.setContactId(contactId);
        personalEmail.setType(2);
        personalEmail.setEnteredBy(userId);
        personalEmail.setModifiedBy(userId);
        personalEmail.setEmail(getValue(thisRecord, PERSONAL_EMAIL));
        if (personalEmail.isValid()) {
          emailFormatter.format(personalEmail);
          emailAddressList.add(personalEmail);
        }

        ContactEmailAddress otherEmail = new ContactEmailAddress();
        otherEmail.setContactId(contactId);
        otherEmail.setType(3);
        otherEmail.setEnteredBy(userId);
        otherEmail.setModifiedBy(userId);
        otherEmail.setEmail(getValue(thisRecord, OTHER_EMAIL));
        if (otherEmail.isValid()) {
          emailFormatter.format(otherEmail);
          emailAddressList.add(otherEmail);
        }

        thisContact.setNotes(getValue(thisRecord, NOTES));

        processOK = writer.save(mappings.createDataRecord(thisContact, "insert"));
        if (processOK) {
          processOK = mappings.saveList(writer, addressList, "insert");
          processOK = mappings.saveList(writer, phoneNumberList, "insert");
          processOK = mappings.saveList(writer, emailAddressList, "insert");
        }
        writeln(out, thisRecord);
      }
      out.flush();
      out.close();
    } catch (Exception e) {
      logger.info("ImportGeneralContacts-> Error: " + e.toString());
      e.printStackTrace(System.out);
      processOK = false;
    }
    return processOK;
  }
}


package org.aspcfs.apps.axis;
import org.aspcfs.apps.axis.beans.WSUserBean;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumber;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldData;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.CRMConnection;
import org.aspcfs.utils.PasswordHash;
import org.aspcfs.utils.XMLUtils;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    March 27, 2006
 */
public class CentricService {
  private CRMConnection crm = new CRMConnection();


  /**
   *  DIVO will act as a Sync Client and access Centric's XML API. The
   *  authentication header should always have DIVO's sync client ID to validate
   *  it
   */
  public void initialize() {
    //private void initialize(String url, String id, int clientId, String code) {
    //crm.setUrl(url);
    //crm.setId(id);
    //crm.setClientId(clientId);
    //crm.setCode(code);

    //TODO: Remove these. To test the following values need to be provided
    crm.setUrl("http://127.0.0.1:8080/centric40/ProcessPacket.do");
    crm.setId("127.0.0.1:8080");
    crm.setSystemId(4);
    //crm.setClientId(4);
    crm.setUsername("ananth");
    crm.setCode("792fef441691aa41135a15c1478a5ee4");
  }


  /**
   * @param  username  Description of the Parameter
   * @param  password  Description of the Parameter
   * @return           Description of the Return Value
   */
  public int validateUser(String username, String password) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("id", "");
      crm.save(meta);

      DataRecord login = new DataRecord();
      login.setName("userList");
      login.setAction(DataRecord.SELECT);
      login.addField("username", username);
      login.addField("password", PasswordHash.encrypt(password));
      crm.save(login);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      return (crm.getRecordCount() == 1 ?
          Integer.parseInt(crm.getResponseValue("id")) : -1);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return -1;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public WSUserBean[] retrievePhoneUsers() {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("id", "");
      meta.addField("username", "");
      crm.save(meta);

      DataRecord login = new DataRecord();
      login.setName("userList");
      login.setAction(DataRecord.SELECT);
      login.addField("enabled", Constants.TRUE);
      crm.save(login);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.apps.axis.beans.WSUserBean").toArray();
      WSUserBean[] logins = new WSUserBean[objects.length];
      for (int i = 0; i < objects.length; i++) {
        WSUserBean thisUser = (WSUserBean) objects[i];
        Contact contact = getContact(thisUser.getId());
        if (contact != null) {
          thisUser.setNameFirst(contact.getNameFirst());
          thisUser.setNameLast(contact.getNameLast());
        }
        logins[i] = thisUser;
      }

      return logins;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Gets the contact attribute of the CentricService object
   *
   * @param  userId  Description of the Parameter
   * @return         The contact value
   */
  private Contact getContact(int userId) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("nameFirst", "");
      meta.addField("nameLast", "");
      crm.save(meta);

      DataRecord contact = new DataRecord();
      contact.setName("contactList");
      contact.setAction(DataRecord.SELECT);
      contact.addField("contactUserId", userId);
      crm.save(contact);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      ArrayList contacts = crm.getRecords("org.aspcfs.modules.contact.base.Contact");
      if (contacts.size() == 1) {
        return (Contact) contacts.get(0);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  userId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public Organization[] retrieveAccounts(int userId) {
    return retrieveAccounts(userId, "");
  }


  /**
   *  Description of the Method
   *
   * @param  userId  Description of the Parameter
   * @param  filter  Description of the Parameter
   * @return         Description of the Return Value
   */
  public Organization[] retrieveAccounts(int userId, String filter) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("name", "");
      meta.addField("url", "");
      meta.addField("notes", "");
      meta.addField("industryName", "");
      meta.addField("alertDate", "");
      meta.addField("alertText", "");
      meta.addField("revenue", "");
      meta.addField("ticker", "");
      meta.addField("accountNumber", "");
      meta.addField("potential", "");
      meta.addField("nameFirst", "");
      meta.addField("nameMiddle", "");
      meta.addField("nameLast", "");
      crm.save(meta);

      DataRecord account = new DataRecord();
      account.setName("accountList");
      account.setAction(DataRecord.SELECT);
      account.addField("ownerId", userId);
      if (filter != null && !"".equals(filter.trim())) {
        account.addField("accountName", "%" + filter);
      }
      crm.save(account);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.accounts.base.Organization").toArray();
      Organization[] accounts = new Organization[objects.length];
      for (int i = 0; i < objects.length; i++) {
        accounts[i] = (Organization) objects[i];
      }

      return accounts;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  accountId  Description of the Parameter
   * @return            Description of the Return Value
   */
  public OrganizationAddress[] retrieveAccountAddresses(int accountId) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("streetAddressLine1", "");
      meta.addField("streetAddressLine2", "");
      meta.addField("streetAddressLine3", "");
      meta.addField("streetAddressLine4", "");
      meta.addField("city", "");
      meta.addField("state", "");
      meta.addField("zip", "");
      meta.addField("country", "");
      meta.addField("typeName", "");
      meta.addField("primaryAddress", "");
      crm.save(meta);

      DataRecord account = new DataRecord();
      account.setName("organizationAddressList");
      account.setAction(DataRecord.SELECT);
      account.addField("orgId", accountId);
      crm.save(account);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.accounts.base.OrganizationAddress").toArray();
      OrganizationAddress[] addresses = new OrganizationAddress[objects.length];
      for (int i = 0; i < objects.length; i++) {
        addresses[i] = (OrganizationAddress) objects[i];
      }

      return addresses;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  accountId  Description of the Parameter
   * @return            Description of the Return Value
   */
  public OrganizationPhoneNumber[] retrieveAccountPhoneNumbers(int accountId) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("number", "");
      meta.addField("extension", "");
      meta.addField("typeName", "");
      meta.addField("primaryNumber", "");
      crm.save(meta);

      DataRecord account = new DataRecord();
      account.setName("organizationPhoneNumberList");
      account.setAction(DataRecord.SELECT);
      account.addField("orgId", accountId);
      crm.save(account);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.accounts.base.OrganizationPhoneNumber").toArray();
      OrganizationPhoneNumber[] phoneNumbers = new OrganizationPhoneNumber[objects.length];
      for (int i = 0; i < objects.length; i++) {
        phoneNumbers[i] = (OrganizationPhoneNumber) objects[i];
      }

      return phoneNumbers;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  accountId  Description of the Parameter
   * @return            Description of the Return Value
   */
  public CustomFieldCategory[] retrieveAccountFolders(int accountId) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("id", "");
      meta.addField("linkItemId", "");
      meta.addField("name", "");
      meta.addField("description", "");
      crm.save(meta);

      DataRecord category = new DataRecord();
      category.setName("customFieldCategoryList");
      category.setAction(DataRecord.SELECT);
      category.addField("linkModuleId", Constants.ACCOUNTS);
      category.addField("linkItemId", accountId);
      //TODO: Add other filters here in the future
      crm.save(category);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.base.CustomFieldCategory").toArray();
      CustomFieldCategory[] folders = new CustomFieldCategory[objects.length];
      for (int i = 0; i < objects.length; i++) {
        folders[i] = (CustomFieldCategory) objects[i];
      }

      return folders;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  folderId  Description of the Parameter
   * @return           Description of the Return Value
   */
  public CustomField[] retrieveCustomFields(int folderId) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("id", "");
      meta.addField("name", "");
      crm.save(meta);

      DataRecord category = new DataRecord();
      category.setName("customFieldList");
      category.setAction(DataRecord.SELECT);
      category.addField("categoryId", folderId);
      crm.save(category);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.base.CustomField").toArray();
      CustomField[] fields = new CustomField[objects.length];
      for (int i = 0; i < objects.length; i++) {
        fields[i] = (CustomField) objects[i];
      }

      return fields;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  accountId  Description of the Parameter
   * @param  folderId   Description of the Parameter
   * @return            Description of the Return Value
   */
  public CustomFieldData[] retrieveCustomData(int accountId, int folderId, int fieldId) {
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("enteredValue", "");
      crm.save(meta);

      DataRecord category = new DataRecord();
      category.setName("customFieldDataList");
      category.setAction(DataRecord.SELECT);
      category.addField("linkModuleId", Constants.ACCOUNTS);
      category.addField("linkItemId", accountId);
      category.addField("categoryId", folderId);
      category.addField("fieldId", fieldId);
      crm.save(category);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.base.CustomFieldData").toArray();
      CustomFieldData[] data = new CustomFieldData[objects.length];
      for (int i = 0; i < objects.length; i++) {
        data[i] = (CustomFieldData) objects[i];
      }

      return data;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Adds a feature to the CustomRecord attribute of the CentricService object
   *
   * @param  accountId  The feature to be added to the CustomRecord attribute
   * @param  folderId   The feature to be added to the CustomRecord attribute
   * @param  fieldId    The feature to be added to the CustomRecord attribute
   * @param  input      The feature to be added to the CustomRecord attribute
   * @param  userId     The feature to be added to the CustomRecord attribute
   * @return            Description of the Return Value
   */
  public boolean addCustomRecord(int userId, int folderId, int fieldId, int accountId, int input) {
    //insert a new record
    //insert custom field data
    try {
      initialize();
      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      DataRecord meta = new DataRecord();
      meta.addField("id", "");
      crm.save(meta);

      DataRecord customRecord = new DataRecord();
      customRecord.setName("customFieldRecord");
      customRecord.setAction(DataRecord.INSERT);
      customRecord.setShareKey(true);
      customRecord.addField("linkModuleId", Constants.ACCOUNTS);
      customRecord.addField("linkItemId", accountId);
      customRecord.addField("categoryId", folderId);
      customRecord.addField("enteredBy", userId);
      customRecord.addField("modifiedBy", userId);
      crm.save(customRecord);

      DataRecord customData = new DataRecord();
      customData.setName("customFieldData");
      customData.setAction(DataRecord.INSERT);
      customData.setShareKey(true);
      customData.addField("recordId", "$C{customFieldRecord.id}");
      customData.addField("fieldId", fieldId);
      customData.addField("enteredValue", input);
      customData.addField("enteredNumber", input);
      customData.addField("enteredDouble", input);
      crm.save(customData);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return (crm.getStatus() == 0);
  }
}


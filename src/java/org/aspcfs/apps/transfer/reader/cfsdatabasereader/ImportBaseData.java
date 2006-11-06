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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import com.zeroio.webdav.base.WebdavModuleList;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.ImportList;
import org.aspcfs.modules.base.NotificationMessageList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.mycfs.base.CFSNoteList;
import org.aspcfs.modules.system.base.DatabaseVersion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Processes Users, Accounts, and Contacts based on the user hierarchy that
 * created each item.
 *
 * @author matt rajkowski
 * @version $Id: ImportBaseData.java,v 1.33 2002/10/24 16:44:46 mrajkowski
 *          Exp $
 * @created September 4, 2002
 */
public class ImportBaseData implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;


  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;
    boolean contactOK = true;

    if (!processCustomLookups(writer, db, mappings)) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Users");
    writer.setAutoCommit(false);
    User baseUser = new User(db, "0");
    UserList userList = new UserList();
    userList.buildList(db);
    userList.add(0, baseUser);
    this.saveUserList(db, userList);
    processOK = writer.commit();
    userList = null;
    baseUser = null;
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Access Log records");
    writer.setAutoCommit(false);
    writer.initialize();
    AccessLogList accessLog = new AccessLogList();
    accessLog.buildList(db);
    mappings.saveList(writer, accessLog, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Usage records");
    writer.setAutoCommit(false);
    writer.initialize();
    UsageList usageList = new UsageList();
    usageList.buildList(db);
    mappings.saveList(writer, usageList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Contact Types");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupContactTypes");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Sub-Segment Lookup");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupSubSegment");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting State records");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "state");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Accounts");
    writer.setAutoCommit(false);
    writer.initialize();
    Organization myCompany = new Organization(db, 0);
    //Build a list of accounts
    OrganizationList accounts = new OrganizationList();
    accounts.setShowMyCompany(false);
    accounts.setIncludeEnabled(-1);
    accounts.setExcludeUnapprovedAccounts(false);
    accounts.buildList(db);

    //Build a list of trashed accounts
    OrganizationList trashedAccounts = new OrganizationList();
    trashedAccounts.setShowMyCompany(false);
    trashedAccounts.setIncludeEnabled(-1);
    trashedAccounts.setExcludeUnapprovedAccounts(false);
    trashedAccounts.setIncludeOnlyTrashed(true);
    trashedAccounts.buildList(db);

    accounts.add(0, myCompany);
    accounts.addAll(trashedAccounts);

    processOK = this.saveOrgList(db, accounts);
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Contacts");
    writer.setAutoCommit(false);
    writer.initialize();
    ContactList contacts = new ContactList();
    contacts.setIncludeAllSites(true);
    contacts.setIncludeEnabled(Constants.UNDEFINED);
    contacts.setExcludeUnapprovedContacts(false);
    contacts.setShowTrashedAndNormal(true);
    contacts.setPersonalId(ContactList.IGNORE_PERSONAL);
    contacts.buildList(db);
    processOK = this.saveContactList(db, contacts);
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Skipped Leads Information");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "contactLeadSkippedMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Leads Read Information");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "contactLeadReadMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Roles");
    RoleList roles = new RoleList();
    roles.setEnabledState(-1);
    roles.buildList(db);
    processOK = mappings.saveList(writer, roles, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Permission Categories");
    PermissionCategoryList categories = new PermissionCategoryList();
    categories.buildList(db);
    processOK = mappings.saveList(writer, categories, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Permissions");
    PermissionList permissions = new PermissionList();
    permissions.setEnabled(Constants.UNDEFINED);
    permissions.buildList(db);
    processOK = mappings.saveList(writer, permissions, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Role Permissions");
    processOK = mappings.saveList(writer,
        RolePermissionList.recordList(db),
        "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting News");
    writer.setAutoCommit(false);
    writer.initialize();
    NewsArticleList newsArticleList = new NewsArticleList();
    newsArticleList.buildList(db);
    mappings.saveList(writer, newsArticleList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Notifications");
    writer.setAutoCommit(false);
    writer.initialize();
    NotificationMessageList notifications = new NotificationMessageList();
    notifications.buildList(db);
    mappings.saveList(writer, notifications, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting CFS Notes");
    writer.setAutoCommit(false);
    writer.initialize();
    CFSNoteList noteList = new CFSNoteList();
    noteList.setBuildAll(true);
    noteList.buildList(db);
    mappings.saveList(writer, noteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting CFSNote Link records");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "cfsNoteLink");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Account Type Levels");
    writer.setAutoCommit(true);
    writer.initialize();
    mappings.saveList(writer, AccountTypeLevel.recordList(db), "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting contact Type Levels");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "contactTypeLevels");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Lookup Lists lookup");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupListsLookup");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Webdav");
    writer.setAutoCommit(false);
    writer.initialize();
    WebdavModuleList webdavModuleList = new WebdavModuleList();
    webdavModuleList.buildList(db);
    mappings.saveList(writer, new ArrayList(webdavModuleList.values()), "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting CategoryEditor records");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "categoryEditorLookup");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Viewpoint");
    writer.setAutoCommit(false);
    writer.initialize();
    ViewpointList viewpointList = new ViewpointList();
    viewpointList.buildList(db);
    mappings.saveList(writer, viewpointList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Viewpoint Permissions");
    writer.setAutoCommit(false);
    writer.initialize();
    mappings.saveList(writer, ViewpointPermissionList.recordList(db), "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Import");
    writer.setAutoCommit(false);
    writer.initialize();
    ImportList importList = new ImportList();
    importList.setIgnoreStatusId(-1);//fetches all records without filtering based on status
    importList.setBuildFileDetails(false);
    importList.buildList(db);
    mappings.saveList(writer, importList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    //Database Version
    logger.info("ImportBaseData-> Inserting Database Versions");
    writer.setAutoCommit(false);
    writer.initialize();
    mappings.saveList(writer, DatabaseVersion.recordList(db), "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    //Contact and Account History
    logger.info("ImportBaseData-> Inserting history");
    writer.setAutoCommit(false);
    writer.initialize();
    ContactHistoryList contactHistoryList = new ContactHistoryList();
    contactHistoryList.setDefaultFilters(true);
    contactHistoryList.setShowDisabledWithEnabled(true);
    contactHistoryList.buildList(db);
    mappings.saveList(writer, contactHistoryList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting User Groups");
    writer.setAutoCommit(false);
    writer.initialize();
    UserGroupList userGroupList = new UserGroupList();
    userGroupList.buildList(db);
    mappings.saveList(writer, userGroupList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting User Group Map");
    writer.setAutoCommit(true);
    writer.initialize();
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "userGroupMap");
    if (!processOK) {
      return false;
    }

    this.updateUserList(db);

    return true;
  }


  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private boolean processCustomLookups(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    writer.setAutoCommit(true);
    writer.initialize();
    boolean processOK = true;

    logger.info("ImportBaseData-> Inserting Access Type");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupAccessTypes");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Industry Types");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupIndustry");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Site IDs");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupSiteId");
    if (!processOK) {
      return false;
    }

    logger.info("ImportBaseData-> Inserting Stage Types");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupStage");
    if (!processOK) {
      return false;
    }

    return processOK;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param userList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveUserList(Connection db, UserList userList) throws SQLException {
    Iterator users = userList.iterator();
    while (users.hasNext()) {
      User thisUser = (User) users.next();
      DataRecord thisRecord = mappings.createDataRecord(thisUser, "insert");
      thisRecord.removeField("enteredBy");
      thisRecord.removeField("modifiedBy");
      thisRecord.removeField("contactId");
      thisRecord.removeField("roleId");
      thisRecord.removeField("managerId");
      thisRecord.removeField("assistant");
      thisRecord.removeField("alias");
      thisRecord.addField("addContact", "false");
      writer.save(thisRecord);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void updateUserList(Connection db) throws SQLException {
    HashMap userIdHash = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT contact_id, user_id " +
            "FROM contact c " +
            "WHERE c.user_id > 0 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      userIdHash.put(
          String.valueOf(rs.getInt("user_id")),
          String.valueOf(rs.getInt("contact_id"))
      );
    }
    rs.close();
    pst.close();

    //Update the user records now that they all exist
    User firstUser = new User(db, "0");

    UserList finalUserList = new UserList();
    finalUserList.buildList(db);
    finalUserList.add(firstUser);

    Iterator users = finalUserList.iterator();
    while (users.hasNext()) {
      User thisUser = (User) users.next();
      DataRecord thisRecord = mappings.createDataRecord(thisUser, "update");
      if (userIdHash.containsKey(String.valueOf(thisUser.getId()))) {
        thisRecord.removeField("contactId");
        thisRecord.addField("contactId",
            (String) userIdHash.get(String.valueOf(thisUser.getId())));
      }
      thisRecord.removeField("isXMLObject");
      thisRecord.addField("isXMLObject", "true");
      writer.save(thisRecord);
    }
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param contactList Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private boolean saveContactList(Connection db, ContactList contactList) throws SQLException {
    Iterator contacts = contactList.iterator();

    while (contacts.hasNext()) {
      Contact thisContact = (Contact) contacts.next();
      DataRecord thisRecord = mappings.createDataRecord(thisContact, "insert");
      writer.save(thisRecord);
      writer.commit();

      ContactEmailAddressList emailList = new ContactEmailAddressList();
      emailList.setContactId(thisContact.getId());
      emailList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + emailList.size() + " Contact emails");

      Iterator emails = emailList.iterator();
      while (emails.hasNext()) {
        ContactEmailAddress thisAddress = (ContactEmailAddress) emails.next();
        DataRecord anotherRecord = mappings.createDataRecord(
            thisAddress, "insert");
        writer.save(anotherRecord);
        writer.commit();
      }

      ContactAddressList addressList = new ContactAddressList();
      addressList.setContactId(thisContact.getId());
      addressList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + addressList.size() + " Contact addresses");

      Iterator addresses = addressList.iterator();
      while (addresses.hasNext()) {
        ContactAddress streetAddress = (ContactAddress) addresses.next();
        DataRecord addressRecord = mappings.createDataRecord(
            streetAddress, "insert");
        writer.save(addressRecord);
        writer.commit();
      }

      ContactPhoneNumberList phoneList = new ContactPhoneNumberList();
      phoneList.setContactId(thisContact.getId());
      phoneList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + phoneList.size() + " Contact phone numbers");

      Iterator phones = phoneList.iterator();
      while (phones.hasNext()) {
        ContactPhoneNumber phone = (ContactPhoneNumber) phones.next();
        DataRecord phoneRecord = mappings.createDataRecord(phone, "insert");
        writer.save(phoneRecord);
        writer.commit();
      }

      ContactInstantMessageAddressList contactInstantMessageAddressList = new ContactInstantMessageAddressList();
      contactInstantMessageAddressList.setContactId(thisContact.getId());
      contactInstantMessageAddressList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + contactInstantMessageAddressList.size() + " Contact IM Addresses");

      Iterator imAddresses = contactInstantMessageAddressList.iterator();
      while (imAddresses.hasNext()) {
        ContactInstantMessageAddress imAddress = (ContactInstantMessageAddress) imAddresses.next();
        DataRecord imAddrRecord = mappings.createDataRecord(imAddress, "insert");
        writer.save(imAddrRecord);
        writer.commit();
      }

      ContactTextMessageAddressList contactTextMessageAddressList = new ContactTextMessageAddressList();
      contactTextMessageAddressList.setContactId(thisContact.getId());
      contactTextMessageAddressList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + contactTextMessageAddressList.size() + " Contact Text Msg Addresses");

      Iterator textMsgAddrs = contactTextMessageAddressList.iterator();
      while (textMsgAddrs.hasNext()) {
        ContactTextMessageAddress textMsgAddr = (ContactTextMessageAddress) textMsgAddrs.next();
        DataRecord textMsgAddrRec = mappings.createDataRecord(textMsgAddr, "insert");
        writer.save(textMsgAddrRec);
        writer.commit();
      }
    }

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param orgList Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private boolean saveOrgList(Connection db, OrganizationList orgList) throws SQLException {
    Iterator orgs = orgList.iterator();

    while (orgs.hasNext()) {
      Organization thisOrg = (Organization) orgs.next();
      DataRecord thisRecord = mappings.createDataRecord(thisOrg, "insert");
      thisRecord.addField("insertPrimaryContact", "false");
      writer.save(thisRecord);
      writer.commit();

      OrganizationEmailAddressList emailList = new OrganizationEmailAddressList();
      emailList.setOrgId(thisOrg.getId());
      emailList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + emailList.size() + " Organization emails");

      Iterator emails = emailList.iterator();
      while (emails.hasNext()) {
        OrganizationEmailAddress thisAddress = (OrganizationEmailAddress) emails.next();
        DataRecord anotherRecord = mappings.createDataRecord(
            thisAddress, "insert");
        writer.save(anotherRecord);
        writer.commit();
      }

      OrganizationAddressList addressList = new OrganizationAddressList();
      addressList.setOrgId(thisOrg.getId());
      addressList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + addressList.size() + " Organization addresses");

      Iterator addresses = addressList.iterator();
      while (addresses.hasNext()) {
        OrganizationAddress streetAddress = (OrganizationAddress) addresses.next();
        DataRecord addressRecord = mappings.createDataRecord(
            streetAddress, "insert");
        writer.save(addressRecord);
        writer.commit();
      }

      OrganizationPhoneNumberList phoneList = new OrganizationPhoneNumberList();
      phoneList.setOrgId(thisOrg.getId());
      phoneList.buildList(db);

      logger.info(
          "ImportBaseData-> Inserting " + phoneList.size() + " Organization phone numbers");

      Iterator phones = phoneList.iterator();
      while (phones.hasNext()) {
        OrganizationPhoneNumber thisPhone = (OrganizationPhoneNumber) phones.next();
        DataRecord anotherRecord = mappings.createDataRecord(
            thisPhone, "insert");
        writer.save(anotherRecord);
        writer.commit();
      }
    }

    return true;
  }
}


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
package org.aspcfs.modules.accounts.webservices;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumber;
import org.aspcfs.modules.accounts.webservices.beans.AccountFilters;
import org.aspcfs.modules.actionplans.base.ActionItemWorkNote;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkNote;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.webservices.beans.WSTicketBean;
import org.aspcfs.utils.CRMConnection;
import org.aspcfs.utils.XMLUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Timestamp;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    July 21, 2006
 */
public class AccountServices {
  private CRMConnection crm = new CRMConnection();

  /**
   *  Sets the authenticationInfo attribute of the CentricServices object
   *
   * @param  auth  The new authenticationInfo value
   */
  public void setAuthenticationInfo(AuthenticationItem auth) {
    crm.setUrl(auth.getUrl());
    crm.setId(auth.getId());
    crm.setSystemId(auth.getSystemId());
    crm.setClientId(auth.getClientId());
    crm.setUsername(auth.getUsername());
    crm.setCode(auth.getCode());
  }

  /**
   *  Method returns a list of accounts owned by a specific user
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Owner
   * @return      List of accounts
   */
  public Organization[] retrieveAccounts(AuthenticationItem in0, int in1) {
    AccountFilters filters = new AccountFilters();
    filters.setOwnerId(in1);

    return retrieveAccounts(in0, filters);
  }

  
  /**
   *  Method returns a specific account that matches the filters specified
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Filters
   * @return      List of Accounts
   */
  public Organization retrieveAccount(AuthenticationItem in0, AccountFilters in1) {
    Organization[] accounts = retrieveAccounts(in0, in1);

    if (accounts != null && accounts.length > 0) {
      return (Organization) accounts[0];
    }
    return null;
  }
  

  /**
   *  Method returns a list of accounts, that match a set of filters
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Filters
   * @return      List of Accounts
   */
  public Organization[] retrieveAccounts(AuthenticationItem in0, AccountFilters in1) {
    try {
      
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("orgId");
      meta.add("name");
      meta.add("url");
      meta.add("notes");
      meta.add("industryName");
      meta.add("alertDate");
      meta.add("alertText");
      meta.add("revenue");
      meta.add("ticker");
      meta.add("accountNumber");
      meta.add("potential");
      meta.add("nameFirst");
      meta.add("nameMiddle");
      meta.add("nameLast");
      crm.setTransactionMeta(meta);

      DataRecord account = new DataRecord();
      account.setName("accountList");
      account.setAction(DataRecord.SELECT);
      
      if (in1.getOrgId() != -1) {
        account.addField("orgId", in1.getOrgId());
      }
      
      if (in1.getOwnerId() != -1) {
        account.addField("ownerId", in1.getOwnerId());
      }
      
      if (in1.getAccountName() != null && !"".equals(in1.getAccountName().trim())) {
        account.addField("accountName", "%" + in1.getAccountName() + "%");
      }
      
      if (in1.getSince() != null) {
        account.addField("enteredSince", in1.getSince());
      }
      
      if (in1.getTo() != null) {
        account.addField("enteredTo", in1.getTo());
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
   *  Method returns a list of addresses that belong to a specific account owned
   *  by a specific user
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Owner
   * @param  in2  Account Name
   * @return      Account Addresses
   */
  public OrganizationAddress[] retrieveAccountAddresses(AuthenticationItem in0, int in1, String in2) {
    try {
      //Fetch the account with name in2 owned by in1
      AccountFilters filters = new AccountFilters();
      filters.setOwnerId(in1);
      filters.setAccountName(in2);
      
      Organization account = retrieveAccount(in0, filters);

      if (account != null) {
        return retrieveAccountAddresses(in0, account.getOrgId());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return null;
  }


  /**
   *  Method returns a list of phone numbers that belong to a specific account
   *  owned by a specific user
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Owner
   * @param  in2  Account Name
   * @return      Account Phone Numbers
   */
  public OrganizationPhoneNumber[] retrieveAccountPhoneNumbers(AuthenticationItem in0, int in1, String in2) {
    try {
      //Fetch the account with name in2 owned by in1
      AccountFilters filters = new AccountFilters();
      filters.setOwnerId(in1);
      filters.setAccountName(in2);
      
      Organization account = retrieveAccount(in0, filters);

      if (account != null) {
        return retrieveAccountPhoneNumbers(in0, account.getOrgId());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return null;
  }


  /**
   *  Method returns a combined list of history notes and action plan notes
   *  associated with a specific account name owned by a specific user
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Owner
   * @param  in2  Account Name
   * @return      History & Action Plan Notes
   */
  public String[] getNotes(AuthenticationItem in0, int in1, String in2) {
    try {
      //Fetch the account with name in2 owned by in1
      AccountFilters filters = new AccountFilters();
      filters.setOwnerId(in1);
      filters.setAccountName(in2);
      
      Organization account = retrieveAccount(in0, filters);

      if (account != null) {
        return getNotes(in0, account.getOrgId());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return null;
  }


  /**
   *  Method returns a combined list of pending and completed activities
   *  associated with a specific account name owned by a specific user
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Owner
   * @param  in2  Account Name
   * @return      Pending & Completed Calls
   */
  public Call[] getActivities(AuthenticationItem in0, int in1, String in2) {
    try {
      //Fetch the account with name in2 owned by in1
      AccountFilters filters = new AccountFilters();
      filters.setOwnerId(in1);
      filters.setAccountName(in2);
      
      Organization account = retrieveAccount(in0, filters);

      if (account != null) {
        return getActivities(in0, account.getOrgId());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return null;
  }


  /**
   *  Method returns a list of help desk tickets associated with a specific
   *  account name owned by a specific user
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Owner
   * @param  in2  Account Name
   * @return      Tickets
   */
  public WSTicketBean[] getTickets(AuthenticationItem in0, int in1, String in2) {
    try {
      //Fetch the account with name in2 owned by in1
      AccountFilters filters = new AccountFilters();
      filters.setOwnerId(in1);
      filters.setAccountName(in2);
      
      Organization account = retrieveAccount(in0, filters);

      if (account != null) {
        return getTickets(in0, account.getOrgId());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return null;
  }


  /**
   *  Adds a phone number to an existing account
   *
   * @param  in0  Authentication Token
   * @param  in1  Account Name
   * @param  in2  Phone Number Details
   * @return      Status
   */
  public boolean addAccountPhoneNumber(AuthenticationItem in0, String in1, OrganizationPhoneNumber in2) {
    try {
      AccountFilters filters = new AccountFilters();
      filters.setAccountName(in1);
      
      Organization account = retrieveAccount(in0, filters);

      if (account != null) {
        in2.setOrgId(account.getOrgId());
        return addAccountPhoneNumber(in0, in2);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return false;
  }


  /**
   *  Adds a feature to the AccountPhoneNumber attribute of the AccountServices
   *  object
   *
   * @param  in0  Authentication Token
   * @param  in1  Phone Number Details
   * @return      Status
   */
  public boolean addAccountPhoneNumber(AuthenticationItem in0, OrganizationPhoneNumber in1) {
    try {
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("number");
      crm.setTransactionMeta(meta);
      
      //TODO: Phone number should be formatted before saving
      if (in1.getNumber() != null && !"".equals(in1.getNumber().trim())) {
        DataRecord phoneData = new DataRecord();
        phoneData.setName("organizationPhoneNumber");
        phoneData.setAction(DataRecord.INSERT);
        phoneData.addField("orgId", in1.getOrgId());
        phoneData.addField("type", in1.getType());
        phoneData.addField("number", in1.getNumber());
        phoneData.addField("extension", in1.getExtension());
        phoneData.addField("enteredBy", in1.getEnteredBy());
        phoneData.addField("modifiedBy", in1.getModifiedBy());
        crm.save(phoneData);
      }

      crm.commit();

      if (System.getProperty("DEBUG") != null) {
        System.out.println("Response-> " + crm.getLastResponse());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return (crm.getStatus() == 0);
  }


  /**
   *  This method returns a combined list of history notes and action plan notes
   *  associated with a specific account
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The notes value
   */
  private String[] getNotes(AuthenticationItem in0, int in1) {
    try {
      OrganizationHistory[] historyNotes = getHistoryNotes(in0, in1);
      ActionPlanWorkNote[] actionPlanNotes = getActionPlanNotes(in0, in1);
      ActionItemWorkNote[] actionItemNotes = getActionItemNotes(in0, in1);

      String[] notes = new String[historyNotes.length +
          actionPlanNotes.length +
          actionItemNotes.length];

      for (int i = 0; i < historyNotes.length; ++i) {
        notes[i] = ((OrganizationHistory) historyNotes[i]).getDescription();
      }

      for (int j = 0; j < actionPlanNotes.length; ++j) {
        notes[j + historyNotes.length] =
            ((ActionPlanWorkNote) actionPlanNotes[j]).getDescription();
      }

      for (int k = 0; k < actionItemNotes.length; ++k) {
        notes[(k + historyNotes.length + actionPlanNotes.length)] =
            ((ActionItemWorkNote) actionItemNotes[k]).getDescription();
      }

      return notes;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }



  /**
   *  Gets the historyNotes attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The historyNotes value
   */
  private OrganizationHistory[] getHistoryNotes(AuthenticationItem in0, int in1) {
    try {
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("description");
      crm.setTransactionMeta(meta);

      DataRecord notes = new DataRecord();
      notes.setName("accountHistoryList");
      notes.setAction(DataRecord.SELECT);
      notes.addField("orgId", in1);
      notes.addField("notes", true);
      notes.addField("activities", true);
      notes.addField("documents", true);
      notes.addField("quotes", true);
      notes.addField("opportunities", true);
      notes.addField("serviceContracts", true);
      notes.addField("tickets", true);
      notes.addField("tasks", true);
      notes.addField("relationships", true);
      notes.addField("assets", true);
      crm.save(notes);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.accounts.base.OrganizationHistory").toArray();
      OrganizationHistory[] history = new OrganizationHistory[objects.length];
      for (int i = 0; i < objects.length; i++) {
        history[i] = (OrganizationHistory) objects[i];
      }

      return history;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Gets the actionPlanNotes attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The actionPlanNotes value
   */
  private ActionPlanWorkNote[] getActionPlanNotes(AuthenticationItem in0, int in1) {
    try {
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("description");
      crm.setTransactionMeta(meta);

      DataRecord notes = new DataRecord();
      notes.setName("actionPlanWorkNoteList");
      notes.setAction(DataRecord.SELECT);
      notes.addField("orgId", in1);
      crm.save(notes);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.actionplans.base.ActionPlanWorkNote").toArray();
      ActionPlanWorkNote[] planNotes = new ActionPlanWorkNote[objects.length];
      for (int i = 0; i < objects.length; i++) {
        planNotes[i] = (ActionPlanWorkNote) objects[i];
      }

      return planNotes;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }



  /**
   *  Gets the actionItemNotes attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The actionItemNotes value
   */
  private ActionItemWorkNote[] getActionItemNotes(AuthenticationItem in0, int in1) {
    try {
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("description");
      crm.setTransactionMeta(meta);

      DataRecord notes = new DataRecord();
      notes.setName("actionItemWorkNoteList");
      notes.setAction(DataRecord.SELECT);
      notes.addField("orgId", in1);
      crm.save(notes);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.actionplans.base.ActionItemWorkNote").toArray();
      ActionItemWorkNote[] itemNotes = new ActionItemWorkNote[objects.length];
      for (int i = 0; i < objects.length; i++) {
        itemNotes[i] = (ActionItemWorkNote) objects[i];
      }

      return itemNotes;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Gets the activities attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The activities value
   */
  private Call[] getActivities(AuthenticationItem in0, int in1) {
    try {
      Call[] pendingCalls = getPendingActivities(in0, in1);
      Call[] completedCalls = getCompletedActivities(in0, in1);

      Call[] calls = new Call[pendingCalls.length + completedCalls.length];

      for (int i = 0; i < pendingCalls.length; ++i) {
        calls[i] = pendingCalls[i];
      }

      for (int j = 0; j < completedCalls.length; ++j) {
        calls[j + pendingCalls.length] = completedCalls[j];
      }

      return calls;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Gets the pendingActivities attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The pendingActivities value
   */
  private Call[] getPendingActivities(AuthenticationItem in0, int in1) {
    return getActivities(in0, in1,
        Call.COMPLETE_FOLLOWUP_PENDING);
  }


  /**
   *  Gets the completedActivities attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The completedActivities value
   */
  private Call[] getCompletedActivities(AuthenticationItem in0, int in1) {
    return getActivities(in0, in1,
        Call.COMPLETE);
  }


  /**
   *  Gets the activities for a specific account with pending/completed status
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @param  in2  Description of the Parameter
   * @return      The activities value
   */
  private Call[] getActivities(AuthenticationItem in0, int in1, int in2) {
    try {
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("id");
      meta.add("orgId");
      meta.add("length");
      meta.add("subject");
      meta.add("notes");
      meta.add("callType");
      meta.add("alertDate");
      meta.add("followupNotes");
      meta.add("priorityString");
      crm.setTransactionMeta(meta);

      DataRecord calls = new DataRecord();
      calls.setName("callList");
      calls.setAction(DataRecord.SELECT);
      calls.addField("orgId", in1);
      calls.addField("onlyPending", (Call.COMPLETE != in2));
      calls.addField("onlyCompleted", (Call.COMPLETE == in2));
      crm.save(calls);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.contacts.base.Call").toArray();
      Call[] callList = new Call[objects.length];
      for (int i = 0; i < objects.length; i++) {
        callList[i] = (Call) objects[i];
      }

      return callList;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Gets the tickets attribute of the AccountServices object
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      The tickets value
   */
  private WSTicketBean[] getTickets(AuthenticationItem in0, int in1) {
    try {
      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("id");
      meta.add("problem");
      meta.add("entered");
      meta.add("closed");
      meta.add("location");
      meta.add("cause");
      meta.add("estimatedResolutionDate");
      meta.add("resolutionDate");
      meta.add("companyName");
      meta.add("departmentName");
      meta.add("priorityName");
      meta.add("severityName");
      meta.add("categoryName");
      meta.add("sourceName");

      crm.setTransactionMeta(meta);

      DataRecord tickets = new DataRecord();
      tickets.setName("ticketList");
      tickets.setAction(DataRecord.SELECT);
      tickets.addField("orgId", in1);
      crm.save(tickets);

      boolean result = crm.commit();
      System.out.println("RESPONSE: " + crm.getLastResponse());

      Object[] objects = crm.getRecords("org.aspcfs.modules.troubletickets.webservices.beans.WSTicketBean").toArray();
      WSTicketBean[] ticketList = new WSTicketBean[objects.length];
      for (int i = 0; i < objects.length; i++) {
        ticketList[i] = (WSTicketBean) objects[i];
      }

      return ticketList;
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      Description of the Return Value
   */
  private OrganizationAddress[] retrieveAccountAddresses(AuthenticationItem in0, int in1) {
    try {
      int accountId = in1;

      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("streetAddressLine1");
      meta.add("streetAddressLine2");
      meta.add("streetAddressLine3");
      meta.add("streetAddressLine4");
      meta.add("city");
      meta.add("state");
      meta.add("zip");
      meta.add("country");
      meta.add("typeName");
      meta.add("primaryAddress");
      crm.setTransactionMeta(meta);

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
   * @param  in0  Description of the Parameter
   * @param  in1  Description of the Parameter
   * @return      Description of the Return Value
   */
  private OrganizationPhoneNumber[] retrieveAccountPhoneNumbers(AuthenticationItem in0, int in1) {
    try {
      int accountId = in1;

      //Authentication Info
      this.setAuthenticationInfo(in0);

      // Start a new transaction
      crm.setAutoCommit(false);

      //Add Meta Info
      ArrayList meta = new ArrayList();
      meta.add("orgId");
      meta.add("number");
      meta.add("extension");
      meta.add("typeName");
      meta.add("primaryNumber");
      crm.setTransactionMeta(meta);

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
}


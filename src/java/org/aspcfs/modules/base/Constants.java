package org.aspcfs.modules.base;

/**
 *  The Constants class provides several static variables that are used
 *  throughout ASPCFS. To maintain, users cannot reuse numbers within a specific
 *  category, like Documents or Folders. A formal request should be made before
 *  adding a new constant value.
 *
 *@author     matt rajkowski
 *@created    February 8, 2002
 *@version    $Id$
 */
public final class Constants {
  //Using int for boolean
  public final static int UNDEFINED = -1;
  public final static int TRUE = 1;
  public final static int FALSE = 0;

  //Basic Objects in CFS
  public final static int TICKET_OBJECT = 42420030;
  public final static int CALL_OBJECT = 42420031;
  public final static int OPPORTUNITY_OBJECT = 42420032;
  public final static int TASK_OBJECT = 42420033;
  public final static int ACCOUNT_OBJECT = 42420034;
  public final static int MESSAGE_OBJECT = 42420035;
  public final static int CAMPAIGN_OBJECT = 51320031;
  //Folders
  public final static int FOLDERS_ACCOUNTS = 1;
  public final static int FOLDERS_CONTACTS = 2;

  //Documents (Migrate the unsorted to this format)
  public final static int DOCUMENTS_ACCOUNTS = 1;
  public final static int DOCUMENTS_CONTACTS = 2;
  public final static int DOCUMENTS_OPPORTUNITIES = 3;
  public final static int DOCUMENTS_PROJECTS = 4;
  public final static int DOCUMENTS_COMMUNICATIONS_MAILMERGE = 5;
  public final static int DOCUMENTS_TICKETS = 6;
  public final static int DOCUMENTS_ACCOUNTS_REPORTS = 10;
  public final static int DOCUMENTS_CONTACTS_REPORTS = 11;
  public final static int DOCUMENTS_LEADS_REPORTS = 12;
  public final static int DOCUMENTS_TICKETS_REPORTS = 13;
  public final static int DOCUMENTS_AUTOGUIDE_PHOTOS = 14;
  public final static int DOCUMENTS_COMMUNICATIONS_FILE_ATTACHMENTS = 16;
  public final static int DOCUMENTS_COMMUNICATIONS = 17;

  //Unsorted -- used for folders, documents, and other
  //TODO: Cleanup this list once modules have been updated
  public final static int ACCOUNTS = 1;
  public final static int CONTACTS = 2;
  public final static int PIPELINE = 3;
  //public final static int PROJECTS = 4;
  public final static int COMMUNICATIONS = 5;
  //public final static int TICKETS = 6;
  public final static int ADMIN = 7;
  public final static int CONTACTS_CALLS = 8;
  public final static int CFSNOTE = 9;
  public final static int PIPELINE_CALLS = 10;
  //public final static int ACCOUNTS_REPORTS = 10;
  //public final static int CONTACTS_REPORTS = 11;
  //public final static int LEADS_REPORTS = 12;
  //public final static int TICKETS_REPORTS = 13;
  public final static int AUTOGUIDE = 14;
  public final static int TASKS = 15;
  public final static int COMMUNICATIONS_FILE_ATTACHMENTS = 16;
  public final static int COMMUNICATIONS_DOCUMENTS = 17;

  //Synchronization API
  public final static int NO_SYNC = -1;
  public final static int SYNC_INSERTS = 2;
  public final static int SYNC_UPDATES = 3;
  public final static int SYNC_DELETES = 4;
  public final static int SYNC_QUERY = 5;

  //Not sure
  public final static int CAMPAIGN_CONTACT_ID = 9;

  //Usage tracking
  public final static int USAGE_FILE_UPLOAD = 1;
  public final static int USAGE_FILE_DOWNLOAD = 2;
  public final static int USAGE_COMMUNICATIONS_EMAIL = 3;
  public final static int USAGE_COMMUNICATIONS_FAX = 4;

  //Task Categories
  public final static int TASK_CATEGORY_PROJECTS = 4;

  //TODO: Add lookup lists here...
  
  //Action Lists 
  public final static int ACTIONLISTS_CONTACTS = 2;
  
  
}


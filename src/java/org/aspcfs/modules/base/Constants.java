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
package org.aspcfs.modules.base;

/**
 * The Constants class provides several static variables that are used
 * throughout ASPCFS. To maintain, users cannot reuse numbers within a specific
 * category, like Documents or Folders. A formal request should be made before
 * adding a new constant value.<p>
 * <p/>
 * Constants should be in the following form (10 digits max!):<br>
 * two-digit month (no proceeding zero in an integer)<br>
 * two-digit day<br>
 * four-digit year<br>
 * two-digit hour of day (24 hour time)
 *
 * @author matt rajkowski
 * @version $Id$
 * @created February 8, 2002
 */
public final class Constants {
  public final static String fs = System.getProperty("file.separator");

  //Using int for boolean
  public final static int UNDEFINED = -1;
  public final static int TRUE = 1;
  public final static int FALSE = 0;
	public final static int ERROR  = 0;
	public final static int SUCCESS  = 1;
	public final static int DELETED  = 2;

  //Basic Objects
  public final static int TICKET_OBJECT = 42420030;
  public final static int CALL_OBJECT = 42420031;
  public final static int OPPORTUNITY_OBJECT = 42420032;
  public final static int OPPORTUNITY_COMPONENT_OBJECT = 1011200517;
  public final static int TASK_OBJECT = 42420033;
  public final static int ACCOUNT_OBJECT = 42420034;
  public final static int MESSAGE_OBJECT = 42420035;
  public final static int CAMPAIGN_OBJECT = 51320031;
  public final static int ACTION_ITEM_WORK_NOTE_OBJECT = 831200519;
  public final static int ACTION_ITEM_WORK_SELECTION_OBJECT = 831200520;
  //Folders
  public final static int FOLDERS_ACCOUNTS = 1;
  public final static int FOLDERS_CONTACTS = 2;
  public final static int FOLDERS_TICKETS = 11072003;
  public final static int FOLDERS_PRODUCT_CATALOG = 200403191;
  public final static int FOLDERS_PRODUCT_CATEGORY = 200403192;
  public final static int FOLDERS_PRODUCT_OPTION = 200403193;
  public final static int FOLDERS_EMPLOYEES = 120200513;
  public final static int FOLDERS_PIPELINE = 120200514;
  public final static int FOLDERS_GLOBALFOLDERS = 327071502;
  public final static int FOLDERS_LEADS = 724071525;  

  //Documents (Migrate the unsorted to this format)
  public final static int DOCUMENTS_ACCOUNTS = 1;
  public final static int DOCUMENTS_CONTACTS = 2;
  public final static int DOCUMENTS_OPPORTUNITIES = 3;
  // This item duplicated for source compatibility with project manager
  public final static int DOCUMENTS_PROJECTS = 4;
  public final static int PROJECTS_FILES = 4;
  public static final int NEWSARTICLE_FILES = 2004102113;
  public static final int DISCUSSION_FILES_TOPIC = 2005020616;
  public static final int DISCUSSION_FILES_REPLY = 20050201;
  public final static int DOCUMENTS_COMMUNICATIONS_MAILMERGE = 5;
  public final static int DOCUMENTS_TICKETS = 6;
  public final static int DOCUMENTS_ACCOUNTS_REPORTS = 10;
  public final static int DOCUMENTS_CONTACTS_REPORTS = 11;
  public final static int DOCUMENTS_LEADS_REPORTS = 12;
  public final static int DOCUMENTS_TICKETS_REPORTS = 13;
  public final static int DOCUMENTS_AUTOGUIDE_PHOTOS = 14;
  public final static int DOCUMENTS_COMMUNICATIONS_FILE_ATTACHMENTS = 16;
  public final static int DOCUMENTS_COMMUNICATIONS = 17;
  public final static int DOCUMENTS_PRODUCT_CATALOG = 200403194;
  public final static int DOCUMENTS_PRODUCT_CATEGORY = 200403195;
  public final static int DOCUMENTS_PRODUCT_OPTION = 200403196;
  public final static int DOCUMENTS_CUSTOMER_PRODUCT = 423200418;
  public final static int DOCUMENTS_DOCUMENTS = 20041083;
  public final static int DOCUMENTS_QUOTE_LOGO = 126200511;
  public final static int DOCUMENTS_KNOWLEDGEBASE = 104051616;
  public final static int DOCUMENTS_SALES_REPORTS = 2006031313;
  public final static int DOCUMENTS_PORTFOLIO_ITEM = 223061228;
  public final static int DOCUMENTS_WEBSITE = 2006051216;


  //Unsorted -- used for folders, documents, and other
  //TODO: Cleanup this list once modules have been updated
  public final static int ACCOUNTS = 1;
  public final static int CONTACTS = 2;
  public final static int QUOTES = 126200514;
  public final static int PIPELINE = 3;
  //public final static int PROJECTS = 4;
  public final static int COMMUNICATIONS = 5;
  //public final static int TICKETS = 6;
  public final static int ADMIN = 7;
  public final static int LEADS = 18;
  public final static int CONTACTS_CALLS = 8;
  public final static int CFSNOTE = 9;
  public final static int PIPELINE_CALLS = 10;
	public final static int EMPLOYEES = 25;
  //public final static int ACCOUNTS_REPORTS = 10;
  //public final static int CONTACTS_REPORTS = 11;
  //public final static int LEADS_REPORTS = 12;
  //public final static int TICKETS_REPORTS = 13;
  public final static int AUTOGUIDE = 14;
  public final static int TASKS = 15;
  public final static int COMMUNICATIONS_FILE_ATTACHMENTS = 16;
  public final static int COMMUNICATIONS_DOCUMENTS = 17;
  public static final int COMMUNICATIONS_MESSAGE_FILE_ATTACHMENTS = 508200600;
  public static final int MESSAGE_FILE_ATTACHMENTS =100120070;

  public final static int SERVICE_CONTRACTS = 209041109;
  public final static int ASSETS = 209041110;
  public final static int SYNC_PACKAGES = 1117200609;
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

  //User types
  //Users who use CRM with its complete license
  public final static int ROLETYPE_REGULAR = 0;

  // Restricted to access accounts of their organization
  public final static int ROLETYPE_CUSTOMER = 1;

  // Restricted to access AdsJet products and services
  public final static int ROLETYPE_PRODUCTS = 420041011;

  //Import
  public final static int IMPORT_CONTACTS = 33020041;
  public final static int IMPORT_LEADS = 33020042;
  public final static int IMPORT_ACCOUNT_CONTACTS = 33020043;
  public final static int IMPORT_SALES = 324200544;
  public final static int IMPORT_NETAPP_EXPIRATION = 917200409;
  public final static int IMPORT_PRODUCT_CATALOG = 33020045;

  //System cache names
  public static final String SYSTEM_PROJECT_NAME_LIST = "200401202226";
  public static final String SYSTEM_DOCUMENT_NAME_LIST = "200410081651";

  //Survey Types
  public final static int SURVEY_REGULAR = 1;
  public final static int SURVEY_ADDRESS_REQUEST = 2;

  //Error messages
  public final static String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";

  //invalid site id
  public final static int INVALID_SITE = -2;

  //Multiple search
  public final static String ALL = "ALL";
  public final static String ANY = "ANY";

  //Create Params Method
  public final static String STRING = "string";
  public final static String INT = "int";
}

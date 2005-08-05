Centric CRM Tools; build: @BUILD.NUMBER@; @BUILD.DATE@
$Id$

Centric CRM Tools 0.2

----------------------------------------------------------------------------
| LEGAL                                                                    |
----------------------------------------------------------------------------

This software is licensed under the Centric Public License (CPL).  
You should have received a copy of the CPL with this source code package in
the LICENSE file. If you did not receive a copy of the CPL, you may download
it from http://www.centriccrm.com. Compiling or using this software
signifies your acceptance of the  Centric Public License.

Copyright(c) 2004-2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
rights reserved. This material cannot be distributed without written
permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
this material for internal use is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies. DARK HORSE
VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
DAMAGES RELATING TO THE SOFTWARE.


----------------------------------------------------------------------------
| INTRODUCTION                                                             |
----------------------------------------------------------------------------

Centric CRM Tools includes multi-platform code to communicate with the
Centric CRM application using HTTP.

For example, you can capture leads or tickets from your existing web site
and send them straight into Centric CRM by using the "save" method.

You can also read data from Centric CRM by using the "load" method.

  Methods supported:
  
    DataRecord.INSERT
    DataRecord.SELECT
    DataRecord.UPDATE
    DataRecord.DELETE
    DataRecord.GET_DATETIME

More information can be found in the Centric CRM Community...
http://www.centriccrm.com/Portal.do?key=community


----------------------------------------------------------------------------
| CHANGELOG                                                                |
----------------------------------------------------------------------------

v0.2
  June 17, 2005
  Fix for sending data as UNICODE
  Includes import-mappings.xml which describes Class -> DataRecord mapping

v0.1
  April 26, 2005
  Initial version


----------------------------------------------------------------------------
| REQUIREMENTS                                                             |
----------------------------------------------------------------------------

The centric_crm_tools.jar can be used with any Java 1.4 or 1.5 application.
You will need to have the Java Servlet-API in your classpath.


In the Centric CRM database, a couple of manual operations currently need
to be performed (these will have Admin web config in a later version)

*  In the [sync_client] table, an arbitrary client should be inserted
   with a plain-text password in the [code] field.  This will be used in
   the client authentication code.


----------------------------------------------------------------------------
| TYPICAL USAGE                                                            |
----------------------------------------------------------------------------

import org.aspcfs.utils.CRMConnection;
import org.aspcfs.apps.transfer.DataRecord;

// Client ID must already exist in target CRM system
int clientId = 1;

// Establish connectivity information
CRMConnection crm = new CRMConnection();
crm.setUrl("http://www.yourorg.com/centric");
crm.setId("www.yourorg.com");
crm.setCode("password");
crm.setClientId(clientId);

// Start a new transaction
crm.setAutoCommit(false);

DataRecord contact = new DataRecord();
contact.setName("contact");
contact.setAction(DataRecord.INSERT);
contact.setShareKey(true);
contact.addField("nameFirst", bean.getNameFirst());
contact.addField("nameLast", bean.getNameLast());
contact.addField("company", bean.getCompanyName());
contact.addField("title", bean.getTitle());
contact.addField("source", bean.getSourceId());
contact.addField("isLead", "true");
contact.addField("accessType", 2);
contact.addField("leadStatus", 1);
contact.addField("enteredBy", 0);
contact.addField("modifiedBy", 0);
crm.save(contact);

// Transform the email
DataRecord email = new DataRecord();
email.setName("contactEmailAddress");
email.setAction(DataRecord.INSERT);
email.addField("email", bean.getEmail());
email.addField("contactId", "$C{contact.id}");
email.addField("type", 1);
email.addField("enteredBy", 0);
email.addField("modifiedBy", 0);
crm.save(email);

// Transform the phone
DataRecord phone = new DataRecord();
phone.setName("contactPhoneNumber");
phone.setAction(DataRecord.INSERT);
phone.addField("number", bean.getPhone());
phone.addField("contactId", "$C{contact.id}");
phone.addField("type", 1);
phone.addField("enteredBy", 0);
phone.addField("modifiedBy", 0);
crm.save(phone);

boolean result = crm.commit();

System.out.println(crm.getLastResponse());


----------------------------------------------------------------------------
| ADDITIONAL EXAMPLES                                                      |
----------------------------------------------------------------------------

1. Consider the following transaction that saves an Account, a Contact under
   that Account, the Contacts address information, a Ticket under the
   Account, and a Custom Record that inserts Custom Field Data into that
   Custom Record.

   account
     organizationPhoneNumber
     organizationEmailAddress
     organizationAddress
     contact
       contactPhoneNumber
       contactEmailAddress
       contactAddress
     ticket
       customFieldRecord
         customFieldData
         customFieldData
         customFieldData


2. Another example might be to retrieve information from Centric CRM first,
   if a record exists, then decide to create a new record or append to it.
   Consider the following:

   - Ask Centric CRM for a contact list in which the contact's email address
     is a match.
   - If a match is returned, then submit a ticket related to that contact

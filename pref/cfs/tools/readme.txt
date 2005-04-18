Centric CRM Tools; build: @BUILD.NUMBER@; @BUILD.DATE@
$Id$

Centric CRM Tools

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

Centric CRM Tools allow you to communicate with the Centric CRM application
using HTTP.  These tools are meant to be easy to maintain.

If you are going to be developing Centric CRM, please make sure to read the
developer information at http://www.centriccrm.com/Portal.do?key=community,
you can also find information about installing, configuring, and using
Centric CRM there.

----------------------------------------------------------------------------
| REQUIREMENTS                                                             |
----------------------------------------------------------------------------

The centric_crm-tools.jar can be used with any Java 1.4 or 1.5 application.

You will need to have the Java Servlet-API in your classpath.


----------------------------------------------------------------------------
| TYPICAL USAGE                                                            |
----------------------------------------------------------------------------

import org.aspcfs.utils.CRMConnection;
import org.aspcfs.apps.transfer.DataRecord;

int clientId = 5;

// Establish connectivity information
CRMConnection crm = new CRMConnection();
crm.setUrl("http://dhv.dhcrm.com");
crm.setId("dhv.dhcrm.com");
crm.setCode("password");
if (clientId == -1) {
  clientId = crm.retrieveNewClientId();
}
crm.setClientId(clientId);
crm.setAutoCommit(false);

// Write some data to the connection
DataRecord contact = new DataRecord();
contact.setName("contact");
contact.setAction(DataRecord.INSERT);
contact.addField("nameFirst", bean.getNameFirst());
contact.addField("nameLast", bean.getNameLast());
contact.addField("company", bean.getCompanyName());
contact.addField("title", bean.getTitle());
crm.save(contact);
// Transform the email
DataRecord email = new DataRecord();
email.setName("contactEmailAddress");
email.setAction(DataRecord.INSERT);
email.addField("email", bean.getEmail());
crm.save(email);
// Transform the phone
DataRecord phone = new DataRecord();
phone.setName("contactPhoneNumber");
phone.setAction(DataRecord.INSERT);
phone.addField("number", bean.getPhone() + (" " + StringUtils.toString(bean.getPhoneExt())).trim());
crm.save(phone);

boolean result = crm.commit();

System.out.println(crm.getLastResponse);



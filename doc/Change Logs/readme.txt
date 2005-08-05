----------------------------------------------------------------------------
Centric CRM 3.1
Copyright (C) 2005 Dark Horse Ventures
Change Log
August 4, 2005
----------------------------------------------------------------------------

Requirements:

  Apache Tomcat 5.5, 5.0, or 4

  PostgreSQL v8.0/v7.4 or Microsoft SQL Server 2000/DE

  Sun Java 1.5 (5.0) JRE when using Tomcat 5.5
  Sun Java 1.4 J2SE when using Tomcat 5.0 or 4

** BEFORE UPGRADING **
  You will need a Centric CRM user account configured with an
  administrator role in order to login and upgrade the system.  This means
  that you can be assigned the default "Administrator" role or any added
  role specifically called "Administrator" in your installation

Upgrade Notes:
  Follow the directions in the "CRM Installation and Setup.pdf"
  document.  When starting the application, an upgrade screen will
  appear and provide you with upgrade steps.


For additional information, support, or training, visit
http://www.centriccrm.com for details.

You can also submit bugs, issues, and feature suggestions, as well as find
product FAQs and announcements.

- Account History: a complete chronological record of account interaction
  in one integrated and efficient view
- Contact History: a complete chronological record of contact interaction
  in one integrated and efficient view
- Folders added to the Pipeline module (submitted by Louis Zezeran)
- In Pipeline, when you drill down to opportunities or components, the
  associated account and contact are shown
- Adds additional drop-downs for Pipeline, must be configured in Admin to
  appear
- Integrated the Quartz scheduler for optimal connection pooling, instance
  control, and immediate execution of background application maintenance jobs
- Reports now process immediately when added to queue due to Quartz scheduler
- Outbound email for any contact, with messages saved with contact record
- Smart Deleting: deleting of Accounts, Contacts and Users, with careful
  attention to related data
- Business process events for over 10 classes of data: Contacts, Activities,
  Emails, Communications, Tasks, Quotes, Opportunities, Tickets, Service
  Contracts, Assets, Documents, and Relationships
- By customizing workflow.xml you can alter how your Centric CRM behaves when
  objects are updated, inserted, or deleted, in real-time, by inspecting
  data contents, checking conditions, and performing actions
- Workflow.xml now includes Scheduler configuration for executing business
  processes at a recurring interval
- Admin capability to upload one or more custom workflow.xml files
- Improvements to the Leads module include an additional "Continue to next
  Lead" step when updating a lead and optimized duplication checking
- Updated supported languages
- Updated areas to use dictionary in a few places English was still being used
- Documents can now be copied into Accounts, Documents and Projects modules
  using WebDAV
- vCards can be exported from contact lists and details pages in Accounts,
  Contacts, and Employees modules
- vCards can be copied from WebDAV's Synchronization > Contacts folder
- vCards can be imported by saving and copying vcard files into WebDAV's
  Synchronization > Contacts folder
- Instant Messaging and Text Messaging contact fields have been added
- Additional contact fields have been added to map to vCard fields
- Accounts can be searched by Postal Code or Account Asset Serial #
- Importing contacts into Accounts module without a company name will now store
  the contact as an Individual
- iCalendar priority field added to tasks
- The complete set of scripts required to upgrade a system from v2.8, v2.9,
  and v3.0 have been included
- Daffodil DB/One$DB Embedded database now included which allows running
  Centric CRM without a database server
- New database abstraction features: supports sequences and reserved words
- New source code ant targets: "upgradedb" and "copy"
- Additional and updated translations included:
  - Deutsch, thanks to Reinhold Müller, menta AG
  - Español, thanks to Tamer Muyale
  - Portuguese, thanks to Emerson Cançado, Oriens Tecnologia

Fixes:

- All locales now have language and country specified
- Excel files with numeric and date cells now import into project plans
- HTTPUtils class correctly uses UTF8 for HTTP-XML API
- Some reports using addresses and phone numbers did not generate depending
  on data
- Includes missing workflow install/replace scripts
- Ticket history displayed "Department" changed instead of "Priority" or
  "Severity"
- Project ticket Id now displays on calendar correctly
- Activities started with an Opportunity stay linked with Opportunity when
  additional Activities are added; existing Activities are upgraded with
  this link


----------------------------------------------------------------------------
Centric CRM 3.0.1
Copyright (C) 2005 Dark Horse Ventures
Change Log
May 2, 2005
----------------------------------------------------------------------------

This is a maintenance release to address issues generated since 3.0.

- Under Admin, lookup list items can be renamed
- German translation expanded
- Quotes did not print with configured Currency
- A count of project tickets now shows under "My Items"
- Home Page calendar did not show project tickets
- Quote history log improvements
- Quote remarks with special characters could cause an error message
- Addresses always defaulted to United States, causing a problem
- Language encoding issue resolved
- HTML Message Editor improvement
- Fixed WebDAV calendar issue found under iCal 2.0
- Various changes as reported in the Centric CRM Community Site

----------------------------------------------------------------------------
Centric CRM 3.0
Copyright (C) 2005 Dark Horse Ventures
Change Log
April 15, 2005
----------------------------------------------------------------------------

This release includes over 75 new features!

Requirements:

  Apache Tomcat 5.5, 5.0, or 4

  PostgreSQL v8.0 or v7.4 or Microsoft SQL Server 2000

  Sun Java 1.5 (5.0) JRE when using Tomcat 5.5
  Sun Java 1.4 J2SE when using Tomcat 5.0 or 4

** BEFORE UPGRADING **
  You will need a Centric CRM user account configured with an
  administrator role in order to login and upgrade the system.  This means
  that you can be assigned the default "Administrator" role or any added
  role specifically called "Administrator" in your installation

Upgrade Notes:
  Follow the directions in the "CRM Installation and Setup.pdf"
  document.  When starting the application, an upgrade screen will
  appear and provide you with upgrade steps.


For additional information, support, or training, visit
http://www.centriccrm.com for details.

You can also submit bugs, issues, and feature suggestions, as well as find
product FAQs and announcements.


----------------------------------------------------------------------------
Centric CRM 2.9
Change Log
September 17, 2004
----------------------------------------------------------------------------

Summary:
- This release introduces several new features, including:
  - Project Management module
  - Contact activities
  - Account contact importing from CSV files
  - "Live" home page calendar that allows editing of data
  - Internationalization of dates, times, currencies, and reports
  - "Select" menu upgraded to new look
  - Document folders added in every module's "Documents" tab
  - HTML editor upgraded for Mozilla and IE 5.5+, a new HTML applet has been
    added for IE 5.0 and Safari
  - Account importer added (similar to Contact importer)
  - Assigned tickets with an estimated resolution date now show on calendar
  - More MIME-type graphics added

Fixes:
- This release also fixes several items that appeared, including:
  - The alphabet searching failed on several database configurations

Developer Changes:
- Refactored the following:
  - New formMessage tag for displaying action errors on a page


** BEFORE UPGRADING **
- You will need a Centric CRM user account configured with an
  administrator role in order to login and upgrade the system.  This means
  that you can be assigned the default "Administrator" role or any added
  role specifically called "Administrator" in your installation

  Upgrade Notes:
- Follow the directions in the "CRM Installation and Setup.pdf"
  document.  When starting the application, an upgrade screen will
  appear and provide you with upgrade steps.


For additional information, support, or training, visit
http://www.centriccrm.com for details. You can also find product FAQs and
announcements.
----------------------------------------------------------------------------

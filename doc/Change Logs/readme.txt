Centric CRM 2.9.1
Copyright 2004 Dark Horse Ventures

Change Log - November 3, 2004
----------------------------------------------------------------------------

This release fixes the following bug:

  - When adding a ticket from within a project, the operation could fail
    with an error message.  Projects have the capability to have tickets, 
    however they were not being associated to a contact correctly.
    Thanks to Justin L. Spies for the report and fix.


Change Log - October 29, 2004
----------------------------------------------------------------------------

This release introduces several new features since 2.8, including:

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
  - Changed product name to Centric CRM


There are a couple of changes for developers:

  - HTTP XML API has been activated
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

----------------------------------------------------------------------------
For additional information, support, or training, visit 
http://www.centriccrm.com for details. You can also find product FAQs and
announcements.
----------------------------------------------------------------------------


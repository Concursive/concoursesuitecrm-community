----------------------------------------------------------------------------
Centric CRM 3.0 Developer Test 2
Copyright 2004-2005 Dark Horse Ventures
Change Log
April 6, 2005
----------------------------------------------------------------------------

Summary:
- This release adds Microsoft SQL Server installation and upgrading

Fixes:
- This release fixes various bugs as reported
- Fixes international character input and calendar display

For additional information, support, or training, visit
http://www.centriccrm.com for details.

You can also submit bugs, issues, and feature suggestions, as well as find
product FAQs and announcements.


----------------------------------------------------------------------------
Centric CRM 3.0 Developer Test 1
Copyright 2004-2005 Dark Horse Ventures
Change Log
March 28, 2005
----------------------------------------------------------------------------

Summary:
- This release adds PostgreSQL installation and upgrading
- This release finalizes several new features, including:
  - Quote product and option selection
  - Leads Management module v1.0
  - Product Catalog admin editor v1.0
  - User interface update

Fixes:
- This release also fixes several items that appeared, including:
  - Various bugs as reported

Developer Changes:
  - All JSPs that use the <dhv:container /> tag have been updated.  The
    tag now bounds the body content.


----------------------------------------------------------------------------
Centric CRM 3.0 Developer Preview 3
Copyright 2004-2005 Dark Horse Ventures
Change Log
March 4, 2005
----------------------------------------------------------------------------

Summary:
- This release introduces several new features, including:
  - Leads Management module v1.0
  - Product Catalog admin editor v1.0
  - Project Management improvements v2.1
  - Accounts projects
  - Employee projects

Fixes:
- This release also fixes several items that appeared, including:
  - Various bugs as reported

Developer Changes:
  - All lookup list data is now read in from lookup_lists_en_US.xml

----------------------------------------------------------------------------
Centric CRM 3.0 Developer Preview 2
Copyright 2004-2005 Dark Horse Ventures
Change Log
February 10, 2005
----------------------------------------------------------------------------

Summary:
- This release introduces several new features, including:
  - Quote Management module
  - Product Catalog
  - Document Management module
  - Multiple languages
  - Internationalization of calendars and graphs
  - Relationship Management
  - Employee module custom forms and folders
  - Custom Forms and Folders usability improvements
  - Desktop Documents (WebDAV) module, allows desktop clients to map a drive
    to Centric CRM
  - Managers can view Action Lists of users in hierarchy
  - Managers can reassign Action Lists to other users
  - Contacts can be moved to other Accounts
  - Improvements to removing/deleting users
  - Improvements to removing/deleting lookup list items
  - Your own company information can now be modified and appears on quotes
  - Logo editor for Quotes module has been added, logo appears on quotes
  - Automated contact information request web form

Fixes:
- This release also fixes several items that appeared, including:
  - Various bugs as reported

Developer Changes:
- Additions
  - Compiles with J2SE 1.5
  - Works with Tomcat 5.5
  - Added a "SIDETABS" style to HTML Containers
  - Updated libraries:
      jasperreports-0.6.4.jar
      jcommon-0.9.6.jar
      jfreechart-0.9.21.jar
      jtds-1.0.1.jar
      postgresql-8.0-310.jdbc3.jar
- Changes:
  - Removed uses of document.forms[0] from all Javascript
  - Implemented text translations and customizations
    - All text now uses and requires <dhv:label/> tag in JSPs
    - All text now uses and requires getLabel function in JavaScript files
    - All text now uses and requires getLabel method in Java files


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
http://www.centriccrm.com for details. You can also find product FAQs,
announcements, developer documentation and videos.


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

Centric CRM Community Edition; build: @BUILD.NUMBER@; @BUILD.DATE@
$Id$

Centric CRM 4.1 Beta

----------------------------------------------------------------------------
| LEGAL                                                                    |
----------------------------------------------------------------------------

This software is licensed under the Centric Public License (CPL).  
You should have received a copy of the CPL with this source code package in
the LICENSE file. If you did not receive a copy of the CPL, you may download
it from http://www.centriccrm.com. Compiling or using this software
signifies your acceptance of the  Centric Public License.

Copyright(c) 2000-2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

Welcome to Centric CRM!

The installation and configuration of Centric CRM is intended to be as
simple as possible.

PLEASE READ ALL INSTRUCTIONS TO AVOID ANY PROBLEMS.

If you are going to be developing Centric CRM, please make sure to read the
developer information at http://www.centriccrm.com/Portal.do?key=community,
you can also find information about installing, configuring, and using
Centric CRM there.


----------------------------------------------------------------------------
| REQUIREMENTS                                                             |
----------------------------------------------------------------------------

The Centric CRM Source Code can be obtained from the Centric CRM Subversion
server.  You will need a Subversion client and a user account at
http://www.centriccrm.com to download source.

Latest Stable Release:

  https://svn.centricsuite.com/webapp/tags/rel-200604181700-40

Latest Unstable Developer Code (release + fixes):

  https://svn.centricsuite.com/webapp/branches/branch-40


The following software needs to be installed in order to compile Centric CRM:

  Sun J2SE SDK 1.4 or 5.0/1.5
  http://java.sun.com

  Apache Ant 1.6
  http://ant.apache.org

  Apache Tomcat 5.0 (J2SE 1.4) or 5.5 (J2RE 5.0/1.5)
  http://jakarta.apache.org/tomcat


You will also need a database server installed.  The following are supported:

  Postgresql 7.4, 8.0, 8.1
  http://www.postgresql.org

  Microsoft SQL Server 2000/MSDE
  http://www.microsoft.com/sql

  DaffodilDB / One$DB Embedded Server 4.0, 4.1 (included with Centric CRM)
  http://www.daffodildb.com

  Firebird SQL 1.5 +
  http://www.firebirdsql.org

  Oracle 10g Express
  http://www.oracle.com/technology/software/products/database/xe/index.html


Centric CRM includes and licenses many 3rd party libraries.  These are
distributed with Centric CRM.  See below for the specific license information.


----------------------------------------------------------------------------
| SYSTEM SETTINGS                                                          |
----------------------------------------------------------------------------

-> Linux, MacOSX, and Unix use "export VARIABLE=text"
-> Windows use the "My Computer-> Properties -> Environment Variables GUI"

* You must have the JAVA_HOME environment variable properly set to the
location of your Java SDK installation directory. On MacOSX, this path is:
/System/Library/Frameworks/JavaVM.framework/Home

  $ export JAVA_HOME=/usr/local/java/j2sdk1.4

* You must have Ant installed and ANT_HOME defined in your environment as
well as ANT_HOME/bin in your PATH.  You must also increase the JVM memory for
Ant by defining ANT_OPTS.

  $ export ANT_HOME=/usr/local/ant
  $ export ANT_OPTS="-Xmx192m -Xms192m"

* The properties: CATALINA_HOME, CENTRIC_HOME, CENTRIC_FILELIBRARY must exist
  as environment variables or as properties in a file called "home.properties"

* You must have Tomcat installed and CATALINA_HOME defined in your properties
which points to the tomcat directory.  Also specify the environment variable
CATALINA_OPTS=-Djava.awt.headless=true on Linux/Unix.  You should run Tomcat
with additional memory by setting this in Tomcat's startup script.

  $ export CATALINA_HOME=/path/to/apache-tomcat
  $ export CATALINA_OPTS=-Djava.awt.headless=true
  $ export JAVA_OPTS="-Xms256m -Xmx256m -XX:PermSize=64m -XX:MaxPermSize=128m"

  % Using System in Windows Control Panel set:
    CATALINA_HOME=c:\Program Files\Apache Software Foundation\Tomcat 5.5

* You must have CENTRIC_HOME defined in your properties which points to a
directory in which you want the exploded Centric CRM web application to be
deployed.  The ant script will allow you to install and upgrade this exploded
directory.

  $ export CENTRIC_HOME=/path/to/apache-tomcat/webapps/centric

  % Using System, in Windows Control Panel, set:
    CENTRIC_HOME=c:\Program Files\Apache Software Foundation\Tomcat 5.5\webapps

* You must have CENTRIC_FILELIBRARY defined in your properties which points to a
Centric CRM file library (initially empty) to be used by Ant and Centric CRM.

  $ export CENTRIC_FILELIBRARY=/var/lib/centric_crm/fileLibrary
  $ export CENTRIC_FILELIBRARY=/Library/Application\ Support/CentricCRM/fileLibrary

  % Using System in Windows Control Panel set:
    CENTRIC_FILELIBRARY=c:\CentricCRM\fileLibrary


----------------------------------------------------------------------------
| BUILD PROCESS                                                            |
----------------------------------------------------------------------------

Begin by executing "ant" from the command line.  You will be presented with
the available ant commands.

To verify that you have all settings in place, and to attempt to deploy
from the source code, execute:

  $ ant deploy
  
A new file will be created in the CENTRIC_HOME directory, which must be edited
with your custom settings.  Ant will notify you that you must make changes
to:

  $CENTRIC_HOME/WEB-INF/build.properties

Once changes have been made, you can try ant again:

  $ ant deploy
  
This time you should end up with with a compiled version of Centric CRM.

Before using Centric CRM, you must install the database using ant.  All of
the source SQL scripts have been included.  If you are using Daffodil DB then
you do not need an existing database.  If you are using a database server,
then you must create a centric_crm database before installing the database.

To create the database schema and install the default data, edit the
build.properties file by uncommenting the database of choice, then execute ant
with:

  $ ant installdb

Ant will ask for the database name, and use the URL and driver from the
build.properties file.  The database will be fully created.

Daffodil DB requires additional steps.

  $ ant installdb2
  $ ant installdb3


Now you can start Tomcat and begin the web-based configuration steps of
Centric CRM.


----------------------------------------------------------------------------
| UPGRADE PROCESS                                                          |
----------------------------------------------------------------------------

To keep up-to-date with Centric CRM, you will want to routinely update to
the latest stable or unstable source in the Subversion repository.

As developers commit code to the repository they often include SQL and BSH
upgrade scripts.  These scripts need to be executed diligently to maintain
a stable environment.  The unstable branches may not be complete in regards
to upgrade scripts for various databases.  The stable branches typically
contain a list of upgrade scripts that need to be run in order to upgrade
from one Centric CRM version to the next.

To upgrade from one stable release to the next stable release:

1. Upgrade to the latest source code stable release
2. Stop Tomcat
3. Backup your fileLibrary and database before running any scripts
4. Run "ant deploy" to compile and deploy any needed classes and files used
   by the upgrade process
5. Run the stable upgrade script, for example in bin/ there is an
   upgrade_31-40.sh and upgrade_31-40.bat file which will execute a
   corresponding upgrade_v31-32.txt which contains a list of SQL and BSH
   scripts required to run from one stable version to the next

Please report back your results.  It's possible that a script file might
be missing or incomplete.

To upgrade using the daily developer unstable code, you will need to run
scripts as they are committed to the repository.  Keep in mind that many
of these scripts have not been reviewed and may not be approved.  Approved
scripts get appended to an "upgrade_v*-*.txt" file for the stable releases.

Same directions as above, except instead of running the .bat or .sh script,
you can execute the scripts directory (in order by date):

  $ ant upgradedb

You will be prompted for the script to run.


----------------------------------------------------------------------------
| ABOUT THIS SOFTWARE                                                      |
----------------------------------------------------------------------------

Centric CRM licenses libraries and code from the following projects, some 
are proprietary in which Dark Horse Ventures has been granted a license
to redistribute, some are Open Source and are used according to the project
license:

Project Name                      License
--------------------------------  -----------------------------------------
Asterisk-Java                     Apache Software License
Batik                             Apache Software License
Bean Shell                        Sun Public License
Bouncy Castle Crypto API          Bouncy Castle Open Source License
Castor                            Apache Software License 2.0
DaffodilDB (One$DB Embedded)      LGPL
gnu.regexp                        LGPL
HTMLArea                          BSD style
HTTPMultiPartParser               iSavvix Public License
IBM DB2 JDBC Driver               IBM DB2 Developers Redistribution
iText                             LGPL
JFreeChart                        LGPL
Jakarta Commons                   Apache Software License
Jakarta Taglibs JSTL              Apache Software License
Jasper Reports                    LGPL
Java Activitation Framework       Sun License
Java Mail                         Sun License
Jaybird FirebirdSQL JDBC Driver   LGPL
Jcrontab                          LGPL
jTDS Microsoft SQL Server Driver  LGPL
Kafenio                           LGPL
Log4J                             Apache Software License
Lucene                            Apache Software License
NekoHTML                          Apache style
Oracle JDBC Driver                Oracle Technology Network Development and
                                  Distribution License
PDFBox                            BSD
Pluto                             Apache Software License
POI                               Apache Software License
PostgreSQL JDBC Driver            BSD
Quartz                            OpenSymphony Software License
Smack and Smackx                  Apache Software License
Team Elements Project Management  Centric Public License
TinyMCE                           LGPL
TMExtractors                      Apache style
Xerces                            Apache Software License
Ximian Icons                      LGPL
ypSlideOutMenu                    Creative Commons Attribution 2.0 license

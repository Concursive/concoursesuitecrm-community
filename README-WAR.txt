$Id$

Welcome to the binary distribution of Dark Horse CRM!

The installation and configuration of Dark Horse CRM is intended to be as simple
as possible.  However, this document assumes that you have a working knowledge
of the required applications and have them already installed.

--------------------------------------------------------------------------------
| REQUIREMENTS                                                                 |
--------------------------------------------------------------------------------

Operating System:

  Tested with Linux, Mac OSX and Microsoft Windows, should work with any 
  operating system with a Java VM.

Java VM:

 J2SE SDK 1.4.x (1.3.x will not work; JRE will not work)
 http://java.sun.com

Servlet Container:

 Tomcat 5.0.x (Tomcat 4.1.x still works with this release)
 http://jakarta.apache.org/tomcat

Database Server:

 PostgreSQL 7.4.x (PostgreSQL 7.3.x still works with this release)
 http://www.postgresql.org
 
 Microsoft SQL Server 2000
 http://www.microsoft.com/sql


NOTE: Dark Horse CRM works best with at least a 500 MHz processor and
      512 MB of memory.  Installation requires 20 MB of disk space, however 
      actual space requirements will be substantially more depending on the
      amount of data and documents added.
      
      The web application server and database server can be on the same machine.
      
      Tomcat should be configured with at least 256 MB of memory by setting the
      following environment variable:
      JAVA_OPTS="-Xms256m -Xmx256m"
      
      Under Linux, you will need to enable headless mode so that Dark Horse
      CRM can dynamically create graphics by setting the following environment
      variable:
      CATALINA_OPTS=-Djava.awt.headless=true
      


--------------------------------------------------------------------------------
| DEPLOYING DARK HORSE CRM                                                     |
--------------------------------------------------------------------------------

With a default installation of Tomcat, web applications are automatically
unpacked and deployed when a new .war file is placed into Tomcat's webapps
folder.

Copy the included darkhorse.war file into your <CATALINA_HOME>/webapps folder.

Tomcat will unpack and deploy the Dark Horse CRM application.

At this point you should be able to access Dark Horse CRM from your web browser.
For example, http://127.0.0.1/darkhorse or http://127.0.0.1:8080/darkhorse

NOTE: Until Dark Horse CRM is configured, you will be presented with steps
      that must be completed in order to use Dark Horse CRM.  All configuration
      is completed using the web browser, including the administrator account.


--------------------------------------------------------------------------------
| INSTALLING THE DATABASE                                                      |
--------------------------------------------------------------------------------

During the configuration process, Dark Horse CRM will prompt you for database
connection information.

NOTE: A database user and a database must be created so that Dark Horse CRM can
      connect and create the schema.  This is done outside of the Dark Horse CRM
      application.  Review database specific information below.


--------------------------------------------------------------------------------
| POSTGRESQL SPECIFIC CONFIGURATION                                            |
--------------------------------------------------------------------------------

Configure PostgreSQL so that it accepts TCP/IP connections, which it DOES NOT by
default.  Also add localhost, or if the web application is on another server,
the IP address to be trusted by PostgreSQL.

To create a user, you can use the PostgreSQL utility:

$ createuser -P darkhorse_crm

NOTE: This user does not need to be able to create other databases or users.

Once the user has been created, create a database using the following command:

$ createdb -E UNICODE -O darkhorse_crm darkhorse_crm

Now supply the connection information to Dark Horse CRM when asked:

User: darkhorse_crm
Password: <password>
Database: darkhorse_crm


--------------------------------------------------------------------------------
| MSSQL SPECIFIC CONFIGURATION                                                 |
--------------------------------------------------------------------------------

Using the Microsoft SQL Server Enterprise Manager, create a new database:

Name: darkhorse_crm

Once the database has been created, create a new Login:

General Tab
  Name: darkhorse_crm
  [X] SQL Server Authentication
  Password: <password>
Database Access Tab
  [X] darkhorse_crm database
  Role: db_owner

Now supply the connection information to Dark Horse CRM when asked:

User: darkhorse_crm
Password: <password>
Database: darkhorse_crm


--------------------------------------------------------------------------------
| RUNNING MULITPLE INSTANCES OF DARK HORSE CRM                                 |
--------------------------------------------------------------------------------

If the darkhorse.war is renamed, then a second instance of Dark Horse CRM can
exist on the same server.  The second instance, or any instance thereafter,
will require the user to perform the Dark Horse CRM configuration process.


--------------------------------------------------------------------------------
| QUESTIONS/PROBLEMS                                                           |
--------------------------------------------------------------------------------

If you are unable to see the Dark Horse CRM web site, review your Tomcat 
settings.  You may need to restart Tomcat so that it detects the .war.

If the Dark Horse CRM application comes up, but it reports a database connection
problem, review the database server settings.  The application must be able
to connect using TCP/IP.

If you still have problems or questions, please join the Dark Horse CRM
community site: http://community.darkhorsecrm.com

For support and sales information please visit the Dark Horse CRM
main site: http://www.darkhorsecrm.com


The Dark Horse CRM Team

$Id$

Welcome to the binary distribution of Dark Horse CRM!

The installation and configuration of Dark Horse CRM is intended to be as simple
as possible.  However, this document assumes that you have a working knowledge
of the required applications and have already installed them.

--------------------------------------------------------------------------------
| REQUIREMENTS                                                                 |
--------------------------------------------------------------------------------

J2SE SDK 1.4 or higher
http://java.sun.com

Tomcat 4.1.24 or higher, preferably Tomcat 5.0.16 or higher
http://jakarta.apache.org/tomcat

PostgreSQL 7.3.x or higher, preferably PostgreSQL 7.4.x or higher
http://www.postgresql.org

NOTE: Java applications work best with at least a 500 MHz processor and
      512 MB of memory.  Installation requires 20 MB of disk space, however 
      actual space requirements will be substantially more depending on the
      amount of data and documents added.


--------------------------------------------------------------------------------
| DEPLOYING DARK HORSE CRM                                                     |
--------------------------------------------------------------------------------

With a default installation of Tomcat, web applications are automatically
unpacked and deployed when a new .war file is placed into Tomcat's webapps
folder..

Copy the included darkhorse.war file into your <CATALINA_HOME>/webapps folder

Tomcat will unpack and deploy the Dark Horse CRM application.

At this point you should be able to access Dark Horse CRM from your web browser.
For example, http://127.0.0.1/darkhorse or http://127.0.0.1:8080/darkhorse

NOTE: Until Dark Horse CRM is configured, you will be presented with steps
      that must be completed in order to use Dark Horse CRM.  All configuration
      is completed using the web browser.


--------------------------------------------------------------------------------
| INSTALLING THE DATABASE                                                      |
--------------------------------------------------------------------------------

Dark Horse CRM will prompt you for database connection information.

A database user and a database must be created so that Dark Horse CRM can
connect and create the schema before continuing.  You must also have PostgreSQL
configured so that it accepts TCP/IP connections, which it does not by default.

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
| QUESTIONS/PROBLEMS                                                           |
--------------------------------------------------------------------------------

If you are unable to see the Dark Horse CRM web site, review your Tomcat 
settings.  You may need to restart Tomcat so that it detects the .war.

If the Dark Horse CRM application comes up, but it reports a database connection
problem, review the PostgreSQL settings.  Any changes made to postgresql.conf or
pg_hba.conf may require that PostgreSQL is restarted.

If you have still have problems or questions, please join the Dark Horse CRM
community site and post a detailed message describing your issue.

http://community.darkhorsecrm.com

Thanks for trying Dark Horse CRM,

The Dark Horse CRM Team
http://www.darkhorsecrm.com

$Id$

Welcome to CFS!

The installation and configuration of CFS is intended to be as simple as
possible.  Please refer to the installation and configuration guides in
the doc folder for specific information.

If you are doing development work with CFS, please make sure to read the
following document for guidelines and conventions:
  doc/Getting Started.sxw

To access the community development and collaboration site visit:
  http://www.aspcfs.org


-------------------------------------------------------------------------
| REQUIREMENTS                                                          |
-------------------------------------------------------------------------

Refer to "Getting Started.sxw" and "CFS Installation.sxw" for more
detailed information.

J2SE SDK 1.4 or higher 
http://java.sun.com

Ant 1.5.1 or higher
http://ant.apache.org

Tomcat 4.1.24 or higher
http://jakarta.apache.org/tomcat

Postgresql 7.3.x or higher
http://www.postgresql.org

You must have the JAVA_HOME environment variable properly set to be the
location of your SDK installation directory. On MacOSX, this path is:
/System/Library/Frameworks/JavaVM.framework/Home

You must have Ant installed and ANT_HOME defined in your environment as
well as ANT_HOME/bin in your PATH.

You must have Tomcat installed and CATALINA_HOME defined in your envionment
which points to the tomcat directory.  Also specify
$CATALINA_OPTS=-Djava.awt.headless=true on Linux

CFS_HOME must be defined as well, this is the target directory in which the
CFS application should be deployed. Typically ${CATALINA_HOME}/webapps/ROOT
or /some/dir/ROOT

For example:

    With sh/zsh/bash:
        export JAVA_HOME=/usr/java/j2se
        export ANT_HOME=/usr/java/ant
        export PATH=${PATH}:${JAVA_HOME}/bin:${ANT_HOME}/bin
        export CATALINA_HOME=/usr/java/tomcat
        export CATALINA_OPTS=-Djava.awt.headless=true
        export CFS_HOME=${CATALINA_HOME}/webapps/ROOT

    Note: To make these settings permanent put them in the appropriate place.
        Under Linux, in /etc/profile
        Under Windows, use the Environment Properties editor

All of the necessary .jar files for building and running CFS are
included in the /lib directory and the build system is setup to include
these into your classpath for you. Please do not add any jar files to
your CLASSPATH as it may cause compile errors.

By default, the web applications WEB-INF directory needs to have
permissions set so that the userid which the JVM is running under can
write into that directory.


-------------------------------------------------------------------------
| SETTINGS                                                              |
-------------------------------------------------------------------------

Refer to "Getting Started.sxw" for more detailed information.

The CFS build process begins by executing "ant deploy".  The provided Ant
script will review the system configuration and will abort if:

  1) Java is not found
  2) Ant is not found
  3) The CFS_HOME environment variable is not set
  4) CFS_HOME does not have write permissions
  
If the build script makes it past there, then Ant will copy the master
build properties file to $CFS_HOME/WEB-INF/build.properties for customization
on this sytem.

The CFS build process depends on having a few properties which must be
configured in this new $CFS_HOME/WEB-INF/build.properties file.
The settings are fairly well documented within the file, as well as
the "Getting Started" document, and include Database and Mail server settings.

These properties must be set accordingly *before* you build CFS.

NOTE: There is a line in $CFS_HOME/WEB-INF/build.properties that must
      be uncommented, this ensures that you have looked at the file and
      have made any necessary changes.

NOTE: If you need to modify this file after you have deployed the web
      application, do so, then you will need to re-run the ant
      script so that the servlet engine will reload the new preferences.
      
NOTE: If Tomcat is not configured to reload when it detects changes, then
      you will also have to restart Tomcat.


-------------------------------------------------------------------------
| SETTING THE FILE LIBRARY DIRECTORY                                    |
-------------------------------------------------------------------------

When the system generates files or users upload files, files are saved
to disk in the WEB-INF/fileLibrary directory.

This may or may not be an optimal place to store files potentially 
because of disk size issues. Therefore, there are two options to solve
this problem:

#1. On Linux, one can move the WEB-INF/fileLibrary directory to another
location and then create a symlink from that location to the
WEB-INF/fileLibrary.

#2. (NOT IMPLEMENTED) On Linux and other platforms (Win32), one can set the
cfs.fileLibrary.path property to point at another directory. By
default, this path is relative to the webapp directory and is set to
"WEB-INF/attachments". It is also possible to define an absolute path
such as "/bigdisk/cfs/fileLibrary".

NOTE: Please see the documentation above for instructions on how to set
      properties and rebuild CFS.


-------------------------------------------------------------------------
| DEPLOYING CFS                                                         |
-------------------------------------------------------------------------

To build CFS on your machine, you simply need to type the
following:

        ant deploy

This will compile and copy the CFS files into $CFS_HOME.

NOTE: Make sure that you have configured the system as specified under
      requirements and settings.


-------------------------------------------------------------------------
| INSTALLING THE DATABASE                                               |
-------------------------------------------------------------------------

Once CFS has been configured and deployed, ant can further be used to
generate the required database schema.  In WEB-INF/build.properties the
database connection information is defined.

An empty database must already exist as specified by the connection information,
CFS will create the tables, indexes and initial data.

The empty database is assumed to be installed and running with appropriately
configured access control setup.

Now, execute:
      ant installdb


-------------------------------------------------------------------------
| QUESTIONS/PROBLEMS                                                    |
-------------------------------------------------------------------------

If you have problems or questions, please join the CFS developer site 
and post a detailed message describing your issues.

http://www.aspcfs.org

/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.setup.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactEmailAddress;
import org.aspcfs.modules.setup.beans.DatabaseBean;
import org.aspcfs.modules.setup.beans.ServerBean;
import org.aspcfs.modules.setup.beans.UserSetupBean;
import org.aspcfs.modules.setup.utils.Prefs;
import org.aspcfs.modules.setup.utils.SetupUtils;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.RequestUtils;

import java.io.File;
import java.security.Key;
import java.sql.*;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Actions for setting up Centric CRM the first time
 *
 * @author matt rajkowski
 * @version $Id$
 * @created August 12, 2003
 */
public class Setup extends CFSModule {

  public final static String os = System.getProperty("os.name");


  /**
   * The user is going to the setup page
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Welcome");
    return "SetupOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandWelcome(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Welcome");
    String language = context.getRequest().getParameter("language");
    ApplicationPrefs prefs = getApplicationPrefs(context);
    prefs.add("SYSTEM.LANGUAGE", language);
    prefs.loadApplicationDictionary(context.getServletContext());
    CustomHook.populateSetup(context, getPath(context));
    return "WelcomeOK";
  }


  /**
   * The user has chosen if they need a registration key or they already have a
   * key
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRegister(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Register");
    return CustomHook.populateRegister(context, getPath(context));
  }


  /**
   * The user has filled out the registration form and it needs to be
   * transmitted to the Centric CRM server
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendReg(ActionContext context) {
    addModuleBean(context, null, "Register");
    return CustomHook.populateSendReg(context, getApplicationPrefs(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandValidate(ActionContext context) {
    addModuleBean(context, null, "Register");
    return CustomHook.populateValidate(context, getApplicationPrefs(context), getPath(context));
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureDirectoryCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Storage");
    //Check the file library pref, if it exists then add to request
    String path = context.getRequest().getParameter("fileLibrary");
    if (path == null) {
      path = getPref(context, "FILELIBRARY");
    }
    //Display a default recommended file path
    if (path == null) {
      // Use the .war name if there is one, otherwise default to centric
      String instanceName = context.getServletContext().getRealPath("/");
      if (instanceName != null) {
        File instance = new File(instanceName);
        instanceName = instance.getName();
      } else {
        instanceName = "centric";
      }
      if (os.startsWith("Windows")) {
        //Windows
        path = "c:\\CentricCRM\\fileLibrary\\" + instanceName + "\\";
      } else if (os.startsWith("Mac")) {
        //Mac OSX
        path = "/Library/Application Support/CentricCRM/fileLibrary/" + instanceName + "/";
      } else {
        File testDirectory = new File("/opt");
        if (testDirectory.exists()) {
          //Linux, Solaris, SunOS, OS/2, HP-UX, AIX, FreeBSD, etc
          path = "/opt/centric_crm/fileLibrary/" + instanceName + "/";
        } else {
          //Linux, Solaris, SunOS, OS/2, HP-UX, AIX, FreeBSD, etc
          path = "/var/lib/centric_crm/fileLibrary/" + instanceName + "/";
        }
      }
    }
    context.getRequest().setAttribute("fileLibrary", path);
    return "ConfigureDirectoryCheckOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureDirectory(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Storage");
    ApplicationPrefs prefs = getApplicationPrefs(context);
    String fileLibrary = context.getRequest().getParameter("fileLibrary");
    if (fileLibrary == null || fileLibrary.trim().length() == 0) {
      context.getRequest().setAttribute(
          "actionError", prefs.getLabel(
          "object.validation.incorrectTargetDirectoryName", prefs.get("SYSTEM.LANGUAGE")));
      return "ConfigureDirectoryERROR";
    }
    try {
      if (!fileLibrary.endsWith(fs)) {
        fileLibrary += fs;
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Setup-> ConfigureDirectory path = " + fileLibrary);
      }
      File targetDirectory = new File(fileLibrary);
      if (targetDirectory.exists()) {
        //See if the target directory already has a valid build.properties
        File propertiesFile = new File(fileLibrary + "build.properties");
        if (propertiesFile.exists()) {
          prefs = new ApplicationPrefs(fileLibrary + "build.properties");
          prefs.add("FILELIBRARY", fileLibrary);
          prefs.add("CONTROL", null);
          prefs.add("WEB-INF", null);
          context.getServletContext().setAttribute("applicationPrefs", prefs);
        } else {
          //Let's use the target directory
          prefs.setFilename(fileLibrary + "build.properties");
          prefs.add("FILELIBRARY", fileLibrary);
          prefs.save();
        }
        //Save the instance against the build.properties file for next time

        return "ConfigureDirectoryOK";
      } else {
        //Confirm with the user that the directory does not exist and it will be created
        return "ConfigureDirectoryConfirmOK";
      }
    } catch (Exception e) {
      return "ConfigureDirectoryConfirmOK";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureDirectoryMake(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Storage");
    try {
      // We have permission to make the directory, so go for it
      String path = context.getRequest().getParameter("fileLibrary");
      if (!path.endsWith(fs)) {
        path += fs;
      }
      File targetDirectory = new File(path);
      if (!targetDirectory.exists()) {
        targetDirectory.mkdirs();
      }
      ApplicationPrefs prefs = getApplicationPrefs(context);
      prefs.setFilename(path + "build.properties");
      prefs.add("FILELIBRARY", path);
      prefs.save();
      return "ConfigureDirectoryOK";
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "actionError",
          "An error occurred while trying to create the directory, the " +
              "following error was provided: " + e.getMessage());
      return "ConfigureDirectoryERROR";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureDirectoryFinalize(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Storage");
    ApplicationPrefs prefs = getApplicationPrefs(context);
    //Verify the path again that the user specified
    String userFileLibrary = getPref(context, "FILELIBRARY");
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "Setup-> ConfigureDirectoryFinalize path = " + userFileLibrary);
    }
    File userLibraryPath = new File(userFileLibrary);
    if (userFileLibrary == null || !userLibraryPath.isDirectory()) {
      context.getRequest().setAttribute(
          "actionError", prefs.getLabel(
          "object.validation.actionError.fileLibraryPathNotConfigured", prefs.get("SYSTEM.LANGUAGE")));
      return "ConfigureDirectoryERROR";
    }
    try {
      File destPath = new File(userFileLibrary + "init" + fs);
      destPath.mkdirs();
      if (!CustomHook.populateDirectory(context, prefs, userFileLibrary)) {
        return "ConfigureDirectoryERROR";
      }
      //Add fileLibrary pref to registry so that this page can be skipped in the future
      String dir = Prefs.retrieveContextPrefName(context.getServletContext());
      Prefs.savePref(dir, userFileLibrary);
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "actionError", prefs.getLabel(
          "object.validation.actionError.incorrectDirectoryRWPermissions", prefs.get("SYSTEM.LANGUAGE")));
      return "ConfigureDirectoryERROR";
    }
    return "ConfigureDirectoryCompleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureDatabaseCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Database");
    DatabaseBean bean = (DatabaseBean) context.getFormBean();
    if (bean.getConfigured() == -1) {
      //Check the database pref, if it exists then add to request for confirm
      try {
        String fileLibrary = getPref(context, "FILELIBRARY") + "init" + fs;
        File dbPref = new File(fileLibrary + "conn.sgml");
        if (dbPref.exists()) {
          String dbInfo = StringUtils.loadText(fileLibrary + "conn.sgml");
          // Use a key file for storing info
          Key key = null;
          synchronized (this) {
            //Get or make the key file
            String keyFile = fileLibrary + "zlib.jar";
            File thisFile = new File(keyFile);
            if (!thisFile.exists()) {
              key = PrivateString.generateKeyFile(keyFile);
            } else {
              key = PrivateString.loadKey(keyFile);
            }
          }
          bean.setConnection(PrivateString.decrypt(key, dbInfo));
        }
      } catch (Exception e) {
      }
    }
    return "ConfigureDatabaseCheckOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureDatabase(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Database");
    ApplicationPrefs prefs = getApplicationPrefs(context);
    //See if required info is filled in
    DatabaseBean bean = (DatabaseBean) context.getFormBean();
    CustomHook.populateDatabaseBean(bean);
    //See if the database connection works
    int timeout = DriverManager.getLoginTimeout();
    try {
      boolean isValid = false;
      isValid = this.validateObject(context, null, bean);
      if (!isValid) {
        return "ConfigureDatabaseERROR";
      }
      //Create the db directory to store database specific files
      String dbPath = getPref(context, "FILELIBRARY") + bean.getName() + fs;
      File dbDirectory = new File(dbPath);
      String setupPath =
          context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;
      //Create a connection
      Class.forName(bean.getDriver());
      DriverManager.setLoginTimeout(10);
      if (bean.isEmbedded()) {
        bean.setPath(dbPath);
        CustomHook.populateDatabase(bean, setupPath, dbPath);
      }
      Connection db = DatabaseUtils.getConnection(
          bean.getUrl(), bean.getUser(), bean.getPassword());
      //Save the conn info as encrypted text so it can be reloaded later
      String fileLibrary = getPref(context, "FILELIBRARY") + "init" + fs;
      String dbInfo = bean.getConnection();
      Key key = PrivateString.loadKey(fileLibrary + "zlib.jar");
      StringUtils.saveText(
          fileLibrary + "conn.sgml", PrivateString.encrypt(key, dbInfo));
      dbDirectory.mkdirs();
      //Copy setup files
      FileUtils.copyFile(
          new File(setupPath + "application.xml"), new File(
          dbPath + "application.xml"), true);
      FileUtils.copyFile(
          new File(setupPath + "system.xml"), new File(dbPath + "system.xml"), false);
      FileUtils.copyFile(
          new File(setupPath + "workflow_*.xml"), new File(dbPath), true);
      FileUtils.copyFile(
          new File(setupPath + "templates_*.xml"), new File(dbPath), true);
      //See if the database has been created
      boolean databaseExists = isDatabaseInstalled(db);
      //Finished testing
      db.close();
      //Append the database prefs to be saved when everything is complete
      prefs.add(
          "GATEKEEPER.APPCODE", (String) context.getServletContext().getAttribute(
          "SiteCode"));
      prefs.add("GATEKEEPER.DBTYPE", bean.getTypeValue());
      prefs.add("GATEKEEPER.DRIVER", bean.getDriver());
      prefs.add("GATEKEEPER.URL", bean.getUrl());
      prefs.add("GATEKEEPER.DATABASE", bean.getName());
      prefs.add("GATEKEEPER.USER", bean.getUser());
      prefs.add("GATEKEEPER.PASSWORD", bean.getPassword());
      prefs.save();
      //Skip the create db section
      if (databaseExists) {
        return "ConfigureDatabaseCreateOK";
      }
      return "ConfigureDatabaseOK";
    } catch (Exception e) {
      HashMap map = new HashMap();
      map.put("${error.message}", e.getMessage());
      context.getRequest().setAttribute(
          "actionError",
          getLabel(
              map, prefs.getLabel(
              "object.validation.actionError.databaseConnectionError", prefs.get("SYSTEM.LANGUAGE"))));
      return "ConfigureDatabaseERROR";
    } finally {
      DriverManager.setLoginTimeout(timeout);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public synchronized String executeCommandConfigureDatabaseData(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Database");
    ApplicationPrefs prefs = getApplicationPrefs(context);
    String locale = prefs.get("SYSTEM.LANGUAGE");
    String setupPath = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;
    String dbFileLibraryPath = prefs.get("FILELIBRARY") + prefs.get(
        "GATEKEEPER.DATABASE") + fs;
    //See if the database connection works
    Connection db = null;
    try {
      db = getDbConnection(context);
      if (!isDatabaseInstalled(db)) {
        try {
          // The database must already be created, this creates the schema and
          // inserts the default data
          switch (DatabaseUtils.getType(db)) {
            case DatabaseUtils.POSTGRESQL:
              if (System.getProperty("DEBUG") != null) {
                System.out.println("Setup-> Installing PostgreSQL Schema");
              }
              DatabaseUtils.executeSQL(db, setupPath + "postgresql.sql");
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.FIREBIRD:
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.MSSQL:
              if (System.getProperty("DEBUG") != null) {
                System.out.println("Setup-> Installing MSSQL Schema");
              }
              DatabaseUtils.executeSQL(db, setupPath + "mssql.sql");
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.ORACLE:
              if (System.getProperty("DEBUG") != null) {
                System.out.println("Setup-> Installing Oracle Schema");
              }
              DatabaseUtils.executeSQL(db, setupPath + "oracle.sql");
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.DB2:
              if (System.getProperty("DEBUG") != null) {
                System.out.println("Setup-> Installing DB2 Schema");
              }
              DatabaseUtils.executeSQL(db, setupPath + "db2.sql");
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.DAFFODILDB:
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.MYSQL:
              if (System.getProperty("DEBUG") != null) {
                System.out.println("Setup-> Installing MySQL Schema");
              }
              DatabaseUtils.executeSQL(db, setupPath + "mysql.sql");
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            case DatabaseUtils.DERBY:
              if (System.getProperty("DEBUG") != null) {
                System.out.println("Setup-> Installing Apache Derby Schema");
              }
              DatabaseUtils.executeSQL(db, setupPath + "derby.sql");
              SetupUtils.insertDefaultData(
                  db, dbFileLibraryPath, setupPath, locale);
              break;
            default:
              if (System.getProperty("DEBUG") != null) {
                System.out.println(
                    "Setup-> * Database could not be determined: " + DatabaseUtils.getType(
                        db));
              }
              break;
          }
        } catch (SQLException cre) {
          throw new SQLException(cre.getMessage());
        }
        if (db != null) {
          try {
            db.close();
          } catch (Exception cle) {
          }
        }
      }
      return "ConfigureDatabaseCreateOK";
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(e.getMessage());
        e.printStackTrace(System.out);
      }
      HashMap map = new HashMap();
      map.put("${error.message}", e.getMessage());
      context.getRequest().setAttribute(
          "actionError",
          getLabel(
              map, prefs.getLabel(
              "object.validation.actionError.databaseCreationError", prefs.get("SYSTEM.LANGUAGE"))));
      return "ConfigureDatabaseCreateERROR";
    } finally {
      if (db != null) {
        try {
          db.close();
        } catch (Exception e) {
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureServerCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Servers");
    ServerBean bean = (ServerBean) context.getFormBean();
    if (bean.getConfigured() == -1) {
      //Check the server pref, if it exists then add to request for confirmation
      ApplicationPrefs prefs = getApplicationPrefs(context);
      try {
        String fileLibrary = getPref(context, "FILELIBRARY") + "init" + fs;
        File serverPref = new File(fileLibrary + "srv1.sgml");
        if (serverPref.exists()) {
          String serverInfo = StringUtils.loadText(fileLibrary + "srv1.sgml");
          // Use a key file for storing info
          Key key = null;
          synchronized (this) {
            //Get or make the key file
            String keyFile = fileLibrary + "zlib.jar";
            File thisFile = new File(keyFile);
            if (!thisFile.exists()) {
              key = PrivateString.generateKeyFile(keyFile);
            } else {
              key = PrivateString.loadKey(keyFile);
            }
          }
          bean.setServerInfo(PrivateString.decrypt(key, serverInfo));
          bean.setServerInfo(prefs);
        }
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
      if (bean.getUrl() == null) {
        bean.setUrl(RequestUtils.getServerUrl(context.getRequest()));
      }
      if (bean.getTimeZone() == null) {
        bean.setTimeZone(TimeZone.getDefault().getID());
      }
      if (bean.getLanguage() == null) {
        bean.setLanguage(prefs.get("SYSTEM.LANGUAGE"));
      }
    }
    return "ConfigureServerCheckOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureServer(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "Servers");
    //See if required info is filled in
    ServerBean bean = (ServerBean) context.getFormBean();
    ApplicationPrefs prefs = getApplicationPrefs(context);
    try {
      boolean isValid = false;
      isValid = this.validateObject(context, null, bean);
      if (!isValid) {
        return "ConfigureServerERROR";
      }
      //Save the settings as encrypted text so it can be reloaded later
      String fileLibrary = getPref(context, "FILELIBRARY") + "init" + fs;
      Key key = null;
      synchronized (this) {
        //Get or make the key file
        String keyFile = fileLibrary + "zlib.jar";
        File thisFile = new File(keyFile);
        if (!thisFile.exists()) {
          key = PrivateString.generateKeyFile(keyFile);
        } else {
          key = PrivateString.loadKey(keyFile);
        }
      }
      String serverInfo = bean.getServerInfo();
      StringUtils.saveText(
          fileLibrary + "srv1.sgml", PrivateString.encrypt(key, serverInfo));
      CustomHook.populateServerInfo(context, getPath(context), key);
      //Save the known prefs
      prefs.add("WEBSERVER.URL", bean.getUrl());
      prefs.add("MAILSERVER", bean.getEmail());
      prefs.add("FAXSERVER", bean.getFax());
      if (bean.getFax() != null && !"".equals(bean.getFax().trim())) {
        prefs.add("FAXENABLED", "true");
      } else {
        prefs.add("FAXENABLED", "false");
      }
      prefs.add("EMAILADDRESS", bean.getEmailAddress());
      prefs.add("SYSTEM.TIMEZONE", bean.getTimeZone());
      prefs.add("SYSTEM.CURRENCY", bean.getCurrency());
      prefs.add("SYSTEM.LANGUAGE", bean.getLanguage());
      prefs.add("SYSTEM.COUNTRY", bean.getCountry());
      prefs.add("ASTERISK.OUTBOUND.ENABLED", StringUtils.valueOf(bean.getAsteriskOutbound()));
      prefs.add("ASTERISK.INBOUND.ENABLED", StringUtils.valueOf(bean.getAsteriskInbound()));
      prefs.add("ASTERISK.URL", bean.getAsteriskUrl());
      prefs.add("ASTERISK.USERNAME", bean.getAsteriskUsername());
      prefs.add("ASTERISK.PASSWORD", bean.getAsteriskPassword());
      prefs.add("ASTERISK.CONTEXT", bean.getAsteriskContext());
      prefs.add("XMPP.ENABLED", StringUtils.valueOf(bean.getXmppEnabled()));
      prefs.add("XMPP.CONNECTION.SSL", StringUtils.valueOf(bean.getXmppSSL()));
      prefs.add("XMPP.CONNECTION.URL", bean.getXmppUrl());
      prefs.add("XMPP.CONNECTION.PORT", String.valueOf(bean.getXmppPort()));
      prefs.add("XMPP.MANAGER.USERNAME", bean.getXmppUsername());
      prefs.add("XMPP.MANAGER.PASSWORD", bean.getXmppPassword());
      prefs.add("LDAP.ENABLED", StringUtils.valueOf(bean.getLdapEnabled()));
      prefs.add("LDAP.CENTRIC_CRM.FIELD", bean.getLdapCentricCRMField());
      prefs.add("LDAP.FACTORY", bean.getLdapFactory());
      prefs.add("LDAP.SERVER", bean.getLdapUrl());
      prefs.add("LDAP.SEARCH.BY_ATTRIBUTE", StringUtils.valueOf(bean.getLdapSearchByAttribute()));
      prefs.add("LDAP.SEARCH.USERNAME", bean.getLdapSearchUsername());
      prefs.add("LDAP.SEARCH.PASSWORD", bean.getLdapSearchPassword());
      prefs.add("LDAP.SEARCH.CONTAINER", bean.getLdapSearchContainer());
      prefs.add("LDAP.SEARCH.ORGPERSON", bean.getLdapSearchOrgPerson());
      prefs.add("LDAP.SEARCH.SUBTREE", StringUtils.valueOf(bean.getLdapSearchSubtree()));
      prefs.add("LDAP.SEARCH.ATTRIBUTE", bean.getLdapSearchAttribute());
      prefs.loadApplicationDictionary(context.getServletContext());
      prefs.save();
      return "ConfigureServerOK";
    } catch (Exception e) {
      HashMap map = new HashMap();
      map.put("${error.message}", e.getMessage());
      context.getRequest().setAttribute(
          "actionError",
          getLabel(
              map, prefs.getLabel(
              "object.validation.actionError.preferencesSaveError", prefs.get("SYSTEM.LANGUAGE"))));
      return "ConfigureServerERROR";
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTestEmail(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    ApplicationPrefs prefs = getApplicationPrefs(context);
    SMTPMessage message = new SMTPMessage();
    message.setHost(context.getRequest().getParameter("server"));
    message.setFrom(context.getRequest().getParameter("from"));
    message.setTo(context.getRequest().getParameter("to"));
    message.setSubject(prefs.getLabel("mail.subject.test", prefs.get("SYSTEM.LANGUAGE")));
    message.setSubject(prefs.getLabel("mail.body.congratulations", prefs.get("SYSTEM.LANGUAGE")));
    int result = message.send();
    if (result == 0) {
      return "SendMailOK";
    } else {
      context.getRequest().setAttribute("actionError", message.getErrorMsg());
      return "SendMailERROR";
    }
  }

  public String executeCommandTestLDAP(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    ApplicationPrefs prefs = getApplicationPrefs(context);
    String username = context.getRequest().getParameter("username");
    String password = context.getRequest().getParameter("password");
    int result = LDAPUtils.authenticateUser(prefs, username, password);
    if (result == LDAPUtils.RESULT_VALID) {
      return "LDAPLoginOK";
    } else {
      context.getRequest().setAttribute("actionError", "Additional information was written to the webserver's log file.");
      return "LDAPLoginERROR";
    }
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureUserCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "CRMSetup");
    //Check the database to see if an admin user already exists, user id 1
    UserSetupBean bean = (UserSetupBean) context.getFormBean();
    Connection db = null;
    ApplicationPrefs prefs = getApplicationPrefs(context);
    try {
      //Create a connection
      db = getDbConnection(context);
      if (!isDatabaseInstalled(db)) {
        return "ConfigureUserDatabaseERROR";
      }
      if (hasAdminUser(db)) {
        finalizePrefs(context);
        return "ConfigureUserOK";
      }
      CustomHook.populateDatabaseUser(context, getPath(context), bean);
      return "ConfigureUserCheckOK";
    } catch (Exception e) {
      HashMap map = new HashMap();
      map.put("${error.message}", e.getMessage());
      context.getRequest().setAttribute(
          "actionError",
          getLabel(
              map, prefs.getLabel(
              "object.validation.actionError.databaseVerificationError", prefs.get("SYSTEM.LANGUAGE"))));
      return "ConfigureUserDatabaseERROR";
    } finally {
      if (db != null) {
        try {
          db.close();
        } catch (Exception e) {
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigureUser(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    addModuleBean(context, null, "CRMSetup");
    //See if required info is filled in
    UserSetupBean bean = (UserSetupBean) context.getFormBean();
    Connection db = null;
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    try {
      //Get a database connection
      db = getDbConnection(context);
      boolean isValid = false;
      isValid = this.validateObject(context, db, bean);
      if (!isValid) {
        return "ConfigureUserERROR";
      }
      if (!isDatabaseInstalled(db)) {
        return "ConfigureUserDatabaseERROR";
      }
      //Create the administrator account
      if (!hasAdminUser(db)) {
        db.setAutoCommit(false);
        //Insert the contact record as an employee
        Contact thisContact = new Contact();
        thisContact.setOrgId(0);
        thisContact.setNameFirst(bean.getNameFirst());
        thisContact.setNameLast(bean.getNameLast());
        thisContact.setEmployee(true);
        thisContact.setEnteredBy(0);
        thisContact.setModifiedBy(0);
        thisContact.setAccessType(5);
        thisContact.insert(db);
        //Add the email
        ContactEmailAddress email = new ContactEmailAddress();
        email.setContactId(thisContact.getId());
        email.setType(1);
        email.setEmail(bean.getEmail());
        email.setEnteredBy(0);
        email.setModifiedBy(0);
        email.insert(db);
        //Update the organization name
        Organization.renameMyCompany(db, bean.getCompany());
        //Insert the user record
        User thisUser = new User();
        thisUser.setUsername(bean.getUsername());
        thisUser.setPassword1(bean.getPassword1());
        thisUser.setContactId(thisContact.getId());
        thisUser.setRoleId(1);
        thisUser.setEnteredBy(0);
        thisUser.setModifiedBy(0);
        thisUser.setTimeZone(prefs.get("SYSTEM.TIMEZONE"));
        thisUser.setCurrency(prefs.get("SYSTEM.CURRENCY"));
        thisUser.setLanguage(prefs.get("SYSTEM.LANGUAGE"));
        thisUser.insert(db);
        db.commit();
      }
      finalizePrefs(context);
      return "ConfigureUserOK";
    } catch (Exception e) {
      if (db != null) {
        try {
          db.rollback();
        } catch (Exception se) {
        }
      }
      HashMap map = new HashMap();
      map.put("${error.message}", e.getMessage());
      context.getRequest().setAttribute(
          "actionError",
          getLabel(
              map, prefs.getLabel(
              "object.validation.actionError.adminAddError", prefs.get("SYSTEM.LANGUAGE"))));
      return "ConfigureUserERROR";
    } finally {
      if (db != null) {
        try {
          db.setAutoCommit(true);
          db.close();
        } catch (Exception e) {
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean isDatabaseInstalled(Connection db) {
    //See if the database has been created
    boolean databaseExists = false;
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS nocols " +
              "FROM database_version ");
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int nocols = rs.getInt("nocols");
        if (nocols > 0) {
          databaseExists = true;
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException sqe) {
      //Table does not exist
    }
    return databaseExists;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean hasAdminUser(Connection db) {
    int count = 0;
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS record_count " +
              "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
              "WHERE user_id > 0 ");
      ResultSet rs = pst.executeQuery();
      rs.next();
      count = rs.getInt("record_count");
      rs.close();
      pst.close();
    } catch (SQLException sq) {
      //Table does not exist
    }
    return (count > 0);
  }


  /**
   * Gets the key attribute of the Setup object
   *
   * @param context Description of the Parameter
   * @return The key value
   */
  public static Key getKey(ActionContext context) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    String fileLibrary = prefs.get("FILELIBRARY") + "init" + fs;
    return PrivateString.loadKey(fileLibrary + "zlib.jar");
  }


  /**
   * Gets the connection attribute of the Setup object
   *
   * @param context Description of the Parameter
   * @return The connection value
   * @throws Exception Description of the Exception
   */
  private Connection getDbConnection(ActionContext context) throws Exception {
    //Retrieve the connection info from preferences
    Key key = getKey(context);
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    String fileLibrary = prefs.get("FILELIBRARY") + "init" + fs;
    DatabaseBean dbBean = new DatabaseBean();
    try {
      File dbPref = new File(fileLibrary + "conn.sgml");
      if (dbPref.exists()) {
        String dbInfo = StringUtils.loadText(fileLibrary + "conn.sgml");
        dbBean.setConnection(PrivateString.decrypt(key, dbInfo));
      }
    } catch (Exception e) {
    }
    //Create a connection
    Class.forName(dbBean.getDriver());
    return DatabaseUtils.getConnection(
        dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
  }


  /**
   * Return whether system is already setup
   *
   * @param context Description of the Parameter
   * @return The alreadySetup value
   */
  public static boolean isAlreadySetup(ActionContext context) {
    if (context.getServletContext().getAttribute("cfs.setup") != null) {
      return true;
    }
    return false;
  }


  /**
   * Gets the applicationPrefs attribute of the Setup object
   *
   * @param context Description of the Parameter
   * @return The applicationPrefs value
   */
  private ApplicationPrefs getApplicationPrefs(ActionContext context) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs == null) {
      prefs = new ApplicationPrefs();
      context.getServletContext().setAttribute("applicationPrefs", prefs);
    }
    return prefs;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  private void finalizePrefs(ActionContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Setup-> finalizePrefs - start");
    }
    //Update the final configuration file
    ApplicationPrefs prefs = getApplicationPrefs(context);
    prefs.add("CONTROL", "configured");
    prefs.add("CRON.ENABLED", "true");
    prefs.add("CONNECTION_POOL.DEBUG", "false");
    prefs.add("CONNECTION_POOL.TEST_CONNECTIONS", "false");
    prefs.add("CONNECTION_POOL.ALLOW_SHRINKING", "true");
    prefs.add("CONNECTION_POOL.MAX_CONNECTIONS", "10");
    prefs.add("CONNECTION_POOL.MAX_IDLE_TIME.SECONDS", "60");
    prefs.add("CONNECTION_POOL.MAX_DEAD_TIME.SECONDS", "300");
    prefs.add(
        "WEB-INF", context.getServletContext().getRealPath("/") + "WEB-INF" + fs);
    prefs.add("IMPORT_QUEUE_MAX", "1");
    prefs.save();
    prefs.populateContext(context.getServletContext());
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "Setup-> finalizePrefs - end (" + prefs.getPrefs().size() + ")");
    }
  }


  /**
   * Gets the label attribute of the Setup object
   *
   * @param map   Description of the Parameter
   * @param input Description of the Parameter
   * @return The label value
   */
  public static String getLabel(HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }

}

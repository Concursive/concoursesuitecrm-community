package org.aspcfs.modules.setup.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import org.aspcfs.modules.setup.beans.*;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.FileUtils;
import java.io.*;
import java.security.*;
import com.sun.crypto.provider.*;
import sun.misc.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.sql.*;
import org.aspcfs.modules.setup.utils.Prefs;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.ContactEmailAddress;
import org.aspcfs.modules.accounts.base.Organization;
import java.net.InetAddress;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.jcrontab.datasource.Event;

/**
 *  Actions for setting up Dark Horse CRM the first time
 *
 *@author     matt rajkowski
 *@created    August 12, 2003
 *@version    $Id$
 */
public class Setup extends CFSModule {

  /**
   *  The user is going to the setup page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //Check if license exists, if not force user to use a new license
    String path = getPath(context);
    if (path == null) {
      path = context.getServletContext().getRealPath("/") + "WEB-INF" + fs;
    }
    String keyFile = path + "init" + fs + "zlib.jar";
    File thisFile = new File(keyFile);
    if (thisFile.exists()) {
      context.getRequest().setAttribute("found", "true");
    }
    Prefs.loadPrefs(context.getServletContext());
    return "SetupOK";
  }


  /**
   *  The user has chosen if they need a registration key or they already have a
   *  key
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandRegister(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    String reg = context.getRequest().getParameter("doReg");
    if ("have".equals(reg)) {
      //If user says they want to continue, see if the file exists and that the
      //user validated it, otherwise show the reg screen
      if (isValidLicense(context)) {
        //move on to next action and see if they are there...
        return "ValidateOK";
      }
      return "SetupHaveRegOK";
    } else {
      RegistrationBean bean = (RegistrationBean) context.getFormBean();
      if (bean.getConfigured() == -1) {
        try {
          //Prepare the form... just add the server name
          InetAddress i = InetAddress.getLocalHost();
          //this returns the name
          bean.setProfile(i.getHostName());
        } catch (Exception e) {
        }
      }
      return "SetupNeedRegOK";
    }
  }


  /**
   *  The user has filled out the registration form and it needs to be
   *  transmitted to the Dark Horse CRM server
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSendReg(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    RegistrationBean bean = (RegistrationBean) context.getFormBean();
    if (context.getParameter("ssl") == null) {
      bean.setSsl(false);
    }
    try {
      if (!bean.isValid()) {
        processErrors(context, bean.getErrors());
        return "SendRegERROR";
      }
      String response = null;
      Key key = null;
      //Send the XML
      synchronized (this) {
        if ((Object) context.getServletContext().getAttribute("cfs.setup") == null) {
          //Make the graphs dir to store the graphs (must be done before using system)
          String graphPath =
              context.getServletContext().getRealPath("/") + "graphs" + fs;
          File graphDirectory = new File(graphPath);
          graphDirectory.mkdirs();
          //Make sure the temporary path is writeable (use WEB-INF)
          String keyFilePath = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs;
          File thisPath = new File(keyFilePath);
          thisPath.mkdirs();
          //Get or make the key file
          String keyFile = keyFilePath + "zlib.jar";
          File thisFile = new File(keyFile);
          if (!thisFile.exists()) {
            key = PrivateString.generateKeyFile(keyFile);
          } else {
            key = PrivateString.loadKey(keyFile);
          }
        }
      }
      //Erase the existing validation file since a new license is being requested
      try {
        File licenseFile = new File(context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "input.txt");
        if (licenseFile.exists()) {
          licenseFile.delete();
        }
      } catch (Exception io) {
      }
      //Encode the license for transmission
      BASE64Encoder encoder = new BASE64Encoder();
      bean.setZlib(encoder.encode(ObjectUtils.toByteArray(key)));
      bean.setText(PrivateString.encrypt(key, "5USERBINARY-1.0"));
      //Make sure the server received the key ok
      if (bean.getSsl()) {
        response = HTTPUtils.sendPacket("https://ds21.darkhorseventures.com/setup/LicenseServer.do?command=SubmitRegistration", bean.toXmlString());
      } else {
        response = HTTPUtils.sendPacket("http://ds21.darkhorseventures.com/setup/LicenseServer.do?command=SubmitRegistration", bean.toXmlString());
      }
      if (response == null) {
        context.getRequest().setAttribute("actionError",
            "Unspecified Error: Dark Horse CRM Server did not respond ");
        return "SendRegERROR";
      }
      XMLUtils responseXML = new XMLUtils(response);
      //System.out.println(responseXML.toString());
      Element responseNode = responseXML.getFirstChild("response");
      if (!"0".equals(XMLUtils.getNodeText(XMLUtils.getFirstChild(responseNode, "status")))) {
        context.getRequest().setAttribute("actionError",
            "Unspecified Error: Dark Horse CRM Server rejected registration " +
            XMLUtils.getNodeText(XMLUtils.getFirstChild(responseNode, "errorText")));
        return "SendRegERROR";
      }
    } catch (java.io.IOException errorMessage) {
      //Socket connection error
      context.getRequest().setAttribute("actionError", "Connection could not be made... Check your internet connection: " + errorMessage.getMessage());
      return "SendRegERROR";
    } catch (org.w3c.dom.DOMException xmlError) {
      //XML error
      context.getRequest().setAttribute("actionError", "The license server did not respond correctly, the following error occurred: " + xmlError.getMessage());
      return "SendRegERROR";
    } catch (Exception e) {
      //XML error
      context.getRequest().setAttribute("actionError", "An error occurred, the supplied error is: " + e.getMessage());
      return "SendRegERROR";
    }
    return "SubmitRegistrationOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandValidate(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    try {
      //Load the key
      String keyFile = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "zlib.jar";
      Key key = PrivateString.loadKey(keyFile);
      //Check the request
      String license = context.getRequest().getParameter("license");
      if (license == null) {
        context.getRequest().setAttribute("actionError", "The entered key did not validate, try entering it again.");
        return "ValidateRETRY";
      }
      //Decode the license into XML
      String licenseXml = license.substring(
          license.indexOf("<license>") + 9,
          license.lastIndexOf("</license>"));
      licenseXml = StringUtils.replace(licenseXml, " ", "\r\n");
      //Try decoding the license to make sure it's good
      XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
      //System.out.println(xml.toString());
      //The license is presumed good here so save the xml, it will be tested again
      String entered = XMLUtils.getNodeText(xml.getFirstChild("entered"));
      if (entered != null) {
        //save the license -- it came from the server
        StringUtils.saveText(context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs + "input.txt", licenseXml);
      }
      if (isValidLicense(context)) {
        return "ValidateOK";
      } else {
        throw new Exception("Invalid key for this system -- try again or request a new key");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError", "An error occurred in processing the license.  The following error was supplied: " + e.getMessage());
      return "ValidateRETRY";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureDirectoryCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //Check the file library pref, if it exists then add to request
    String path = context.getRequest().getParameter("fileLibrary");
    if (path == null) {
      path = (String) context.getServletContext().getAttribute("FileLibrary");
    }
    context.getRequest().setAttribute("fileLibrary", path);
    return "ConfigureDirectoryCheckOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureDirectory(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    String fileLibrary = context.getRequest().getParameter("fileLibrary");
    if (fileLibrary == null || fileLibrary.trim().length() == 0) {
      context.getRequest().setAttribute("actionError",
          "Target directory is a required field. Make sure to use valid " +
          "characters for a directory");
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
        //Let's use the target directory
        context.getServletContext().setAttribute("FileLibrary", fileLibrary);
        ApplicationPrefs prefs = getApplicationPrefs(context);
        prefs.setFilename(fileLibrary + "build.properties");
        prefs.add("FILELIBRARY", fileLibrary);
        prefs.save();
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureDirectoryMake(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    try {
      //We have permission to make the directory, so go for it
      String path = context.getRequest().getParameter("fileLibrary");
      File targetDirectory = new File(path);
      if (!targetDirectory.exists()) {
        targetDirectory.mkdirs();
      }
      if (!path.endsWith(fs)) {
        path += fs;
      }
      context.getServletContext().setAttribute("FileLibrary", path);
      ApplicationPrefs prefs = getApplicationPrefs(context);
      prefs.setFilename(path + "build.properties");
      prefs.add("FILELIBRARY", path);
      prefs.save();
      return "ConfigureDirectoryOK";
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("actionError",
          "An error occurred while trying to create the directory, the " +
          "following error was provided: " + e.getMessage());
      return "ConfigureDirectoryERROR";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureDirectoryFinalize(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //Verify the path again that the user specified
    String userFileLibrary = (String) context.getServletContext().getAttribute("FileLibrary");
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Setup-> ConfigureDirectoryFinalize path = " + userFileLibrary);
    }
    File userLibraryPath = new File(userFileLibrary);
    if (userFileLibrary == null || !userLibraryPath.isDirectory()) {
      context.getRequest().setAttribute("actionError",
          "The file library path has not been configured");
      return "ConfigureDirectoryERROR";
    }
    //Verify the existing files...
    String initPath = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "init" + fs;
    //Verify the source input.txt file
    File inputFile = new File(initPath + "input.txt");
    File inputFileDest = new File(userFileLibrary + "init" + fs + "input.txt");
    if (!inputFile.exists() && !inputFileDest.exists()) {
      context.getRequest().setAttribute("actionError",
          "The file library path is missing input.txt");
      return "ConfigureDirectoryERROR";
    }
    //Verify the source zlib.jar file
    File zlibFile = new File(initPath + "zlib.jar");
    File zlibFileDest = new File(userFileLibrary + "init" + fs + "zlib.jar");
    if (!zlibFile.exists() && !zlibFileDest.exists()) {
      context.getRequest().setAttribute("actionError",
          "The file library path is missing zlib.jar");
      return "ConfigureDirectoryERROR";
    }
    try {
      File destPath = new File(userFileLibrary + "init" + fs);
      destPath.mkdirs();
      if (!inputFileDest.exists()) {
        //Copy input.txt to target directory
        FileUtils.copyFile(inputFile, inputFileDest, true);
      }
      if (!zlibFileDest.exists()) {
        //Copy zlib.jar to target directory
        FileUtils.copyFile(zlibFile, zlibFileDest, true);
      }
      //Add fileLibrary pref to registry so that this page can be skipped in
      //the future
      Prefs.savePref("cfs.fileLibrary", userFileLibrary);
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError",
          "An error occurred while trying to use the directory, " +
          "please make sure the directory has read/write permissions");
      return "ConfigureDirectoryERROR";
    }
    return "ConfigureDirectoryCompleteOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureDatabaseCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    DatabaseBean bean = (DatabaseBean) context.getFormBean();
    if (bean.getConfigured() == -1) {
      //Check the database pref, if it exists then add to request for confirm
      try {
        String fileLibrary = (String) context.getServletContext().getAttribute("FileLibrary") + "init" + fs;
        File dbPref = new File(fileLibrary + "conn.sgml");
        if (dbPref.exists()) {
          String dbInfo = StringUtils.loadText(fileLibrary + "conn.sgml");
          Key key = PrivateString.loadKey(fileLibrary + "zlib.jar");
          bean.setConnection(PrivateString.decrypt(key, dbInfo));
        }
      } catch (Exception e) {
      }
    }
    return "ConfigureDatabaseCheckOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureDatabase(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //See if required info is filled in
    DatabaseBean bean = (DatabaseBean) context.getFormBean();
    if (!bean.isValid()) {
      processErrors(context, bean.getErrors());
      return "ConfigureDatabaseERROR";
    }
    //See if the database connection works
    int timeout = DriverManager.getLoginTimeout();
    try {
      //Create a connection
      Class.forName(bean.getDriver());
      DriverManager.setLoginTimeout(10);
      Connection db = DriverManager.getConnection(
          bean.getUrl(), bean.getUser(), bean.getPassword());
      //Save the conn info as encrypted text so it can be reloaded later
      String fileLibrary = (String) context.getServletContext().getAttribute("FileLibrary") + "init" + fs;
      File dbPref = new File(fileLibrary + "conn.sgml");
      String dbInfo = bean.getConnection();
      Key key = PrivateString.loadKey(fileLibrary + "zlib.jar");
      StringUtils.saveText(fileLibrary + "conn.sgml", PrivateString.encrypt(key, dbInfo));
      //Create the db directory to store database specific files
      String dbPath = (String) context.getServletContext().getAttribute("FileLibrary") + bean.getName() + fs;
      File dbDirectory = new File(dbPath);
      dbDirectory.mkdirs();
      //Copy setup/system.xml and setup/workflow.xml to new fileLib + dbname + /
      String setupPath =
          context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs;
      FileUtils.copyFile(new File(setupPath + "system.xml"), new File(dbPath + "system.xml"), false);
      FileUtils.copyFile(new File(setupPath + "workflow.xml"), new File(dbPath + "workflow.xml"), false);
      //See if the database has been created
      boolean databaseExists = isDatabaseInstalled(db);
      //Finished testing
      db.close();
      //Append the database prefs to be saved when everything is complete
      ApplicationPrefs prefs = getApplicationPrefs(context);
      prefs.add("GATEKEEPER.APPCODE", (String) context.getServletContext().getAttribute("SiteCode"));
      prefs.add("GATEKEEPER.DBTYPE", bean.getTypeValue());
      prefs.add("GATEKEEPER.DRIVER", bean.getDriver());
      prefs.add("GATEKEEPER.URL", bean.getUrl());
      prefs.add("GATEKEEPER.USER", bean.getUser());
      prefs.add("GATEKEEPER.PASSWORD", bean.getPassword());
      prefs.save();
      //Skip the create db section
      if (databaseExists) {
        return "ConfigureDatabaseCreateOK";
      }
      return "ConfigureDatabaseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError",
          "An error occurred while trying to connect to the database, the " +
          "following error was provided by the database driver: " + e.getMessage());
      return "ConfigureDatabaseERROR";
    } finally {
      DriverManager.setLoginTimeout(timeout);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public synchronized String executeCommandConfigureDatabaseData(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //See if the database connection works
    Connection db = null;
    try {
      db = getDbConnection(context);
      if (!isDatabaseInstalled(db)) {
        try {
          //Create the database and initial data, combine the following files
          //pg_dump -xOdR  cfs2gk > gatekeeper.sql
          //pg_dump -xOdR  cdb_cfs > postgresql.sql
          //Add BEGIN WORK; to beginning of file, COMMIT; to end of file
          String sql = StringUtils.loadText(
              context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "setup" + fs + "postgresql.sql");
          Statement st = db.createStatement();
          st.execute(sql);
          st.close();
        } catch (SQLException cre) {
          if (System.getProperty("DEBUG") != null) {
            System.out.println(cre.getMessage());
          }
        }
        if (db != null) {
          try {
            db.close();
          } catch (Exception cle) {
          }
        }
        db = getDbConnection(context);
        //Check the database for up to 90 seconds, the above action returns too soon
        long waitCount = System.currentTimeMillis() + (300 * 1000);
        while (!isDatabaseInstalled(db) && waitCount > System.currentTimeMillis()) {
          synchronized (this) {
            wait(2000);
          }
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Inserting the default tasks");
        }
      }
      return "ConfigureDatabaseCreateOK";
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(e.getMessage());
      }
      context.getRequest().setAttribute("actionError",
          "An error occurred while trying to create the database schema, the " +
          "following error was provided: " + e.getMessage());
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureServerCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    ServerBean bean = (ServerBean) context.getFormBean();
    if (bean.getConfigured() == -1) {
      //Check the server pref, if it exists then add to request for confirmation
      try {
        String fileLibrary = (String) context.getServletContext().getAttribute("FileLibrary") + "init" + fs;
        File serverPref = new File(fileLibrary + "srv1.sgml");
        if (serverPref.exists()) {
          String serverInfo = StringUtils.loadText(fileLibrary + "srv1.sgml");
          Key key = PrivateString.loadKey(fileLibrary + "zlib.jar");
          bean.setServerInfo(PrivateString.decrypt(key, serverInfo));
        }
      } catch (Exception e) {
      }
      if (bean.getUrl() == null) {
        bean.setUrl(HTTPUtils.getServerUrl(context.getRequest()));
      }
    }
    return "ConfigureServerCheckOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureServer(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //See if required info is filled in
    ServerBean bean = (ServerBean) context.getFormBean();
    if (!bean.isValid()) {
      processErrors(context, bean.getErrors());
      return "ConfigureServerERROR";
    }
    try {
      //Save the settings as encrypted text so it can be reloaded later
      String fileLibrary = (String) context.getServletContext().getAttribute("FileLibrary") + "init" + fs;
      Key key = PrivateString.loadKey(fileLibrary + "zlib.jar");
      String serverInfo = bean.getServerInfo();
      StringUtils.saveText(fileLibrary + "srv1.sgml", PrivateString.encrypt(key, serverInfo));
      //Access the license to get the email address
      String licenseFile = getPath(context) + "init" + fs + "input.txt";
      String licenseXml = StringUtils.loadText(licenseFile);
      XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
      String userAddress = XMLUtils.getNodeText(xml.getFirstChild("email"));
      context.getRequest().setAttribute("userAddress", userAddress);
      //Save the known prefs
      ApplicationPrefs prefs = getApplicationPrefs(context);
      prefs.add("WEBSERVER.URL", bean.getUrl());
      prefs.add("MAILSERVER", bean.getEmail());
      prefs.add("FAXSERVER", bean.getFax());
      prefs.add("EMAILADDRESS", bean.getEmailAddress());
      prefs.save();
      return "ConfigureServerOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError",
          "An error occurred while saving the preferences, the " +
          "following error was provided: " + e.getMessage());
      return "ConfigureServerERROR";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandTestEmail(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    SMTPMessage message = new SMTPMessage();
    message.setHost(context.getRequest().getParameter("server"));
    message.setFrom(context.getRequest().getParameter("from"));
    message.setTo(context.getRequest().getParameter("to"));
    message.setSubject("Test message from Dark Horse CRM");
    message.setBody("Congratulations, mail from Dark Horse CRM is working.");
    int result = message.send();
    if (result == 0) {
      return "SendMailOK";
    } else {
      context.getRequest().setAttribute("actionError", message.getErrorMsg());
      return "SendMailERROR";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureUserCheck(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //Check the database to see if an admin user already exists, user id 1
    UserSetupBean bean = (UserSetupBean) context.getFormBean();
    Connection db = null;
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
      if (bean.getConfigured() == -1) {
        //Populate the data from the license
        String licenseFile = getPath(context) + "init" + fs + "input.txt";
        String licenseXml = StringUtils.loadText(licenseFile);
        Key key = getKey(context);
        XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
        bean.setNameFirst(XMLUtils.getNodeText(xml.getFirstChild("nameFirst")));
        bean.setNameLast(XMLUtils.getNodeText(xml.getFirstChild("nameLast")));
        bean.setCompany(XMLUtils.getNodeText(xml.getFirstChild("company")));
        bean.setEmail(XMLUtils.getNodeText(xml.getFirstChild("email")));
        bean.setUsername(bean.getNameFirst().toLowerCase() + "." + bean.getNameLast().toLowerCase());
      }
      return "ConfigureUserCheckOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError",
          "An error occurred while trying verify the database schema, the " +
          "following error was provided: " + e.getMessage());
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfigureUser(ActionContext context) {
    if (isAlreadySetup(context)) {
      return "SetupCompleteError";
    }
    //See if required info is filled in
    UserSetupBean bean = (UserSetupBean) context.getFormBean();
    if (!bean.isValid()) {
      processErrors(context, bean.getErrors());
      return "ConfigureUserERROR";
    }
    Connection db = null;
    try {
      //Get a database connection
      db = getDbConnection(context);
      if (!isDatabaseInstalled(db)) {
        return "ConfigureUserDatabaseERROR";
      }
      //Create the administrator account
      if (!hasAdminUser(db)) {
        db.setAutoCommit(false);
        //Insert the contact record as an employee
        Contact thisContact = new Contact();
        thisContact.setOrgId(0);
        thisContact.addType(Contact.EMPLOYEE_TYPE);
        thisContact.setNameFirst(bean.getNameFirst());
        thisContact.setNameLast(bean.getNameLast());
        thisContact.setEnteredBy(0);
        thisContact.setModifiedBy(0);
        //AccessTypeList accessTypes = this.getSystemStatus(context).getAccessTypeList(db, AccessType.EMPLOYEES);
        //thisContact.setAccessType(accessTypes.getDefaultItem());
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
      context.getRequest().setAttribute("actionError",
          "An error occurred while adding the administrative user, the " +
          "following error was provided: " + e.getMessage());
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private boolean isDatabaseInstalled(Connection db) throws SQLException {
    //See if the database has been created
    boolean databaseExists = false;
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) " +
          "FROM events ");
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        databaseExists = true;
      }
      rs.close();
      pst.close();
    } catch (SQLException sqe) {
      //Table does not exist
    }
    return databaseExists;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private boolean hasAdminUser(Connection db) throws SQLException {
    int count = 0;
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS record_count " +
          "FROM access " +
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
   *  Gets the key attribute of the Setup object
   *
   *@param  context  Description of the Parameter
   *@return          The key value
   */
  private Key getKey(ActionContext context) {
    String fileLibrary = (String) context.getServletContext().getAttribute("FileLibrary") + "init" + fs;
    return PrivateString.loadKey(fileLibrary + "zlib.jar");
  }


  /**
   *  Gets the connection attribute of the Setup object
   *
   *@param  context        Description of the Parameter
   *@return                The connection value
   *@exception  Exception  Description of the Exception
   */
  private Connection getDbConnection(ActionContext context) throws Exception {
    //Retrieve the connection info from preferences
    Key key = getKey(context);
    String fileLibrary = (String) context.getServletContext().getAttribute("FileLibrary") + "init" + fs;
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
    return DriverManager.getConnection(dbBean.getUrl(), dbBean.getUser(), dbBean.getPassword());
  }


  /**
   *  Gets the validLicense attribute of the Setup object
   *
   *@param  context  Description of the Parameter
   *@return          The validLicense value
   */
  private boolean isValidLicense(ActionContext context) {
    try {
      //Load the key and license, make check then return true/false
      String path = getPath(context);
      if (path == null) {
        path = context.getServletContext().getRealPath("/") + "WEB-INF" + fs;
      }
      String keyFile = path + "init" + fs + "zlib.jar";
      File thisFile = new File(keyFile);
      if (!thisFile.exists()) {
        return false;
      }
      Key key = PrivateString.loadKey(keyFile);
      //Load the license
      String licenseFile = path + "init" + fs + "input.txt";
      String licenseXml = StringUtils.loadText(licenseFile);
      if (licenseXml == null) {
        return false;
      }
      XMLUtils xml = new XMLUtils(PrivateString.decrypt(key, licenseXml));
      String entered = XMLUtils.getNodeText(xml.getFirstChild("entered"));
      if (entered == null) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  /**
   *  Return whether system is already setup
   *
   *@param  context  Description of the Parameter
   *@return          The alreadySetup value
   */
  private boolean isAlreadySetup(ActionContext context) {
    if (context.getServletContext().getAttribute("cfs.setup") != null) {
      return true;
    }
    return false;
  }


  /**
   *  Gets the applicationPrefs attribute of the Setup object
   *
   *@param  context  Description of the Parameter
   *@return          The applicationPrefs value
   */
  private ApplicationPrefs getApplicationPrefs(ActionContext context) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("APPLICATION.PREFS");
    if (prefs == null) {
      prefs = new ApplicationPrefs();
      context.getServletContext().setAttribute("APPLICATION.PREFS", prefs);
    }
    return prefs;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  private void finalizePrefs(ActionContext context) {
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
    prefs.save();
    prefs.populateContext(context.getServletContext());
  }
}


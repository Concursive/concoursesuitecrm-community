package org.aspcfs.modules.service.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.PasswordHash;

import java.sql.*;

/**
 *  The information required for setting up and maintaining a demo account
 *
 *@author     matt rajkowski
 *@created    January 14, 2004
 *@version    $Id: DemoAccount.java 14508 2005-08-04 10:30:17 -0400 (Thu, 04 Aug 2005) mrajkowski $
 */
public class DemoAccount {
  public final static String fs = System.getProperty("file.separator");
  //email status
  public final static int STATUS_DEFAULT = -1;
  public final static int STATUS_INITIALIZED = 1;
  public final static int STATUS_EMAIL_SENT = 2;
  public final static int STATUS_EMAIL_ERROR = 3;
  //properties
  private int id = -1;
  private String nameFirst = null;
  private String nameLast = null;
  private String organization = null;
  private String title = null;
  private String phone = null;
  private String extension = null;
  private String email = null;
  private String ipAddress = null;
  private String browser = null;
  private String language = null;
  private String referer = null;
  private int status = STATUS_DEFAULT;
  private String password = null;
  private Timestamp processed = null;
  private int failedCount = 0;

  /**
   *  Description of the Method
   */
  public DemoAccount() { }

  public DemoAccount(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the DemoAccount object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the DemoAccount object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the nameFirst attribute of the DemoAccount object
   *
   *@param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameLast attribute of the DemoAccount object
   *
   *@param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the organization attribute of the DemoAccount object
   *
   *@param  tmp  The new organization value
   */
  public void setOrganization(String tmp) {
    this.organization = tmp;
  }


  /**
   *  Sets the title attribute of the DemoAccount object
   *
   *@param  tmp  The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Sets the phone attribute of the DemoAccount object
   *
   *@param  tmp  The new phone value
   */
  public void setPhone(String tmp) {
    this.phone = tmp;
  }


  /**
   *  Sets the ext attribute of the DemoAccount object
   *
   *@param  tmp  The new ext value
   */
  public void setExtension(String tmp) {
    this.extension = tmp;
  }


  /**
   *  Sets the email attribute of the DemoAccount object
   *
   *@param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the ipAddress attribute of the DemoAccount object
   *
   *@param  tmp  The new ipAddress value
   */
  public void setIpAddress(String tmp) {
    this.ipAddress = tmp;
  }


  /**
   *  Sets the browser attribute of the DemoAccount object
   *
   *@param  tmp  The new browser value
   */
  public void setBrowser(String tmp) {
    this.browser = tmp;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setReferer(String referer) {
    this.referer = referer;
  }

  /**
   *  Sets the status attribute of the DemoAccount object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the status attribute of the DemoAccount object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }

  public void setProcessed(Timestamp processed) {
    this.processed = processed;
  }

  /**
   *  Gets the id attribute of the DemoAccount object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the nameFirst attribute of the DemoAccount object
   *
   *@return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameLast attribute of the DemoAccount object
   *
   *@return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the organization attribute of the DemoAccount object
   *
   *@return    The organization value
   */
  public String getOrganization() {
    return organization;
  }


  /**
   *  Gets the title attribute of the DemoAccount object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the phone attribute of the DemoAccount object
   *
   *@return    The phone value
   */
  public String getPhone() {
    return phone;
  }


  /**
   *  Gets the extension attribute of the DemoAccount object
   *
   *@return    The extension value
   */
  public String getExtension() {
    return extension;
  }


  /**
   *  Gets the email attribute of the DemoAccount object
   *
   *@return    The email value
   */
  public String getEmail() {
    return email;
  }

  public String getEmailSubstring() {
    if (email != null && email.indexOf("@") > 0) {
      return email.substring(email.lastIndexOf("@") + 1);
    }
    return email;
  }

  /**
   *  Gets the ipAddress attribute of the DemoAccount object
   *
   *@return    The ipAddress value
   */
  public String getIpAddress() {
    return ipAddress;
  }


  /**
   *  Gets the browser attribute of the DemoAccount object
   *
   *@return    The browser value
   */
  public String getBrowser() {
    return browser;
  }

  public String getLanguage() {
    return language;
  }

  public String getReferer() {
    return referer;
  }

  /**
   *  Gets the status attribute of the DemoAccount object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }

  public Timestamp getProcessed() {
    return processed;
  }

  public int getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(int failedCount) {
    this.failedCount = failedCount;
  }

  public synchronized void addFailed() {
    this.failedCount += this.failedCount;
  }

  /**
   *  Logs the requested demo account in the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "demo_account_demo_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO demo_account (" + (id > -1 ? "demo_id, " : "") + "name_first, name_last, organization, title, " +
        "phone, extension, email, ip_address, browser, language, referer, status) VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, nameFirst);
    pst.setString(++i, nameLast);
    pst.setString(++i, organization);
    pst.setString(++i, title);
    pst.setString(++i, phone);
    pst.setString(++i, extension);
    pst.setString(++i, email);
    pst.setString(++i, ipAddress);
    pst.setString(++i, browser);
    pst.setString(++i, language);
    pst.setString(++i, referer);
    pst.setInt(++i, status);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "demo_account_demo_id_seq", id);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("DemoAccount-> Inserted new demo account: " + id);
    }
    return true;
  }


  /**
   *  Changes the installation status of the demo account as the process
   *  progresses
   *
   *@param  db                Description of the Parameter
   *@param  newStatus         Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, int newStatus) throws SQLException {
    status = newStatus;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE demo_account " +
        "SET status = ? " +
        "WHERE demo_id = ?");
    int i = 0;
    pst.setInt(++i, status);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  When the account is completely ready, set the processed field
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updateProcessed(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE demo_account " +
        "SET processed = CURRENT_TIMESTAMP " +
        "WHERE demo_id = ?");
    int i = 0;
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  Generates a database name based on the record id
   *
   *@return    The databaseName value
   */
  public String getDatabaseName() {
    return ("demo_" + id);
  }


  /**
   *  Gets the generatedPassword attribute of the DemoAccount object
   *
   *@return    The generatedPassword value
   */
  public String getGeneratedPassword() {
    if (password == null) {
      password = PasswordHash.getRandomString(6, 8);
    }
    return password;
  }


  /**
   *  Gets the loginName attribute of the DemoAccount object
   *
   *@return    The loginName value
   */
  public String getLoginName() {
    return email.trim();
  }


  /**
   *  Creates a database for the current record id
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean createDatabase(Connection db) throws SQLException {
    //Create the database
    PreparedStatement pst = db.prepareStatement(
        "CREATE DATABASE " + getDatabaseName() + " " +
        "WITH ENCODING = 'UNICODE'");
    pst.execute();
    pst.close();
    return true;
  }

  public boolean addToSites(Connection db) throws SQLException {
    //Add the new database entry to the sites table
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO sites (sitecode, vhost, dbhost, dbname, dbport, dbuser, dbpw, driver, enabled, language) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    int i = 0;
    pst.setString(++i, "cfs");
    pst.setString(++i, "account" + id + ".demo.centriccrm.com");
    pst.setString(++i, "jdbc:postgresql://127.0.0.1:5432/demo_" + id);
    pst.setString(++i, getDatabaseName());
    pst.setInt(++i, 5432);
    pst.setString(++i, "postgres");
    pst.setString(++i, "");
    pst.setString(++i, "org.postgresql.Driver");
    pst.setBoolean(++i, true);
    pst.setString(++i, language);
    pst.execute();
    pst.close();
    return true;
  }

  public boolean dropDatabase(Connection db) throws SQLException {
    //Create the database
    PreparedStatement pst = db.prepareStatement(
        "DROP DATABASE " + getDatabaseName() + " ");
    pst.execute();
    pst.close();
    //Remove the database entry from the sites table
    pst = db.prepareStatement(
        "DELETE FROM sites " +
        "WHERE dbname = ? ");
    int i = 0;
    pst.setString(++i, getDatabaseName());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                        Description of the Parameter
   *@return                           Description of the Return Value
   *@exception  SQLException          Description of the Exception
   *@exception  InterruptedException  Description of the Exception
   */
  public boolean insertSampleData(Connection db) throws SQLException, InterruptedException {
    // Check the database
    if (!isDatabaseInstalled(db)) {
      return false;
    }
    // Insert sample data
    return DemoData.install(db, this);
  }


  /**
   *  Description of the Method
   *
   *@param  db                        Description of the Parameter
   *@return                           Description of the Return Value
   *@exception  SQLException          Description of the Exception
   *@exception  InterruptedException  Description of the Exception
   */
  public boolean testAndFinalize(Connection db) throws SQLException, InterruptedException {
    // Check the database
    if (!isDatabaseInstalled(db)) {
      return false;
    }
    // The database will now include all of the demo defaults,
    // so update the first user's information with this user's information
    PreparedStatement pst = null;
    try {
      // Access table
      pst = db.prepareStatement(
          "UPDATE access " +
          "SET username = ?, " +
          "password = ? " +
          "WHERE user_id = 2");
      pst.setString(1, getLoginName());
      pst.setString(2, PasswordHash.encrypt(this.getGeneratedPassword()));
      pst.execute();
      pst.close();
      // Contact table
      pst = db.prepareStatement(
          "UPDATE contact " +
          "SET namefirst = ?, " +
          "namelast = ? " +
          "WHERE user_id = 2");
      pst.setString(1, nameFirst);
      pst.setString(2, nameLast);
      pst.execute();
      pst.close();
      // Email table
      pst = db.prepareStatement(
          "UPDATE contact_emailaddress " +
          "SET email = ? " +
          "WHERE contact_id IN (SELECT contact_id FROM contact WHERE user_id = 2)");
      pst.setString(1, email);
      pst.execute();
      pst.close();
      // Organization table
      pst = db.prepareStatement(
          "UPDATE organization " +
          "SET name = ? " +
          "WHERE org_id = 0");
      pst.setString(1, organization);
      pst.execute();
      pst.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    } finally {
      if (pst != null) {
        pst.close();
      }
    }
    return true;
  }


  /**
   *  Gets the databaseInstalled attribute of the DemoAccount object
   *
   *@param  db                Description of the Parameter
   *@return                   The databaseInstalled value
   */
  private boolean isDatabaseInstalled(Connection db) {
    //See if the database has been created
    boolean databaseExists = false;
    try {
      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT count(*) AS test " +
          "FROM access ");
      if (rs.next()) {
        databaseExists = true;
      }
      rs.close();
      st.close();
    } catch (SQLException sqe) {
      System.out.println("DemoAccount-> " + sqe.getMessage());
    }
    return databaseExists;
  }

  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("demo_id");
    nameFirst = rs.getString("name_first");
    nameLast = rs.getString("name_last");
    organization = rs.getString("organization");
    title = rs.getString("title");
    phone = rs.getString("phone");
    extension = rs.getString("extension");
    email = rs.getString("email");
    ipAddress = rs.getString("ip_address");
    browser = rs.getString("browser");
    status = DatabaseUtils.getInt(rs, "status");
    processed = rs.getTimestamp("processed");
    language = rs.getString("language");
    referer = rs.getString("referer");
  }
}



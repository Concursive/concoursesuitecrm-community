//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import java.sql.*;
import java.util.*;
import java.text.DateFormat;
import java.text.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.framework.servlets.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.controller.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.base.*;



/**
 *  Represents a user access record <p>
 *
 *  Password control is implemented with three fields: password is the database
 *  (stored) field, containing the encrypted string; password1 is the field that
 *  the user sees; password2 is the field used for verification.<p>
 *
 *  For an existing record, the password field is only included in the SQL
 *  command if the password1 field is left blank. Otherwise, it is compared to
 *  the password2 field (also filled in by the user), and if valid, is encrypted
 *  and included in the update.<p>
 *
 *  For a new record, the password 1 and 2 fields are required.
 *
 *@author     matt rajkowski
 *@created    September 17, 2001
 *@version    $Id$
 */
public class User extends GenericBean {
  private static Random rn = new Random();

  protected String errmsg = "";
  protected int id = -1;
  protected String username = null;
  protected String encryptedPassword = null;
  protected String password = null;
  protected String password1 = null;
  protected String password2 = null;
  protected int contactId = -1;
  protected int roleId = -1;
  protected String role = null;
  protected int managerId = -1;
  protected String manager = null;
  protected User managerUser = null;
  protected String ip = null;
  protected String timeZone = null;
  protected int startOfDay = -1;
  protected int endOfDay = -1;
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected boolean enabled = true;
  protected Contact contact = new Contact();
  protected UserList childUsers = null;
  protected double YTD = 0;

  protected double pipelineValue = 0;
  protected boolean pipelineValueIsValid = false;

  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;
  protected java.sql.Timestamp lastLogin = null;

  protected java.sql.Date expires = null;

  protected String aliasName = null;

  protected boolean buildContact = false;
  protected boolean buildHierarchy = false;

  protected String previousUsername = null;

  protected boolean opportunityLock = false;
  protected boolean revenueLock = false;
  protected GraphSummaryList gmr = new GraphSummaryList();
  protected GraphSummaryList ramr = new GraphSummaryList();
  protected GraphSummaryList cgmr = new GraphSummaryList();
  protected GraphSummaryList cramr = new GraphSummaryList();
  protected GraphSummaryList revenue = new GraphSummaryList();
  private int assistant = -1;
  private int alias = -1;

  //check to see if manager user is enabled
  private boolean managerUserEnabled = true;


  /**
   *  Constructor for the User object
   *
   *@since    1.1
   */
  public User() { }


  /**
   *  Constructor for the User object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public User(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public User(Connection db, String userId) throws SQLException {
    buildRecord(db, Integer.parseInt(userId));
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@param  doHierarchy       Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.26
   */
  public User(Connection db, String userId, boolean doHierarchy) throws SQLException {
    this.buildHierarchy = doHierarchy;
    buildRecord(db, Integer.parseInt(userId));
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.20
   */
  public User(Connection db, int userId) throws SQLException {
    buildRecord(db, userId);
    buildResources(db);
  }


  /**
   *  Gets the revenue attribute of the User object
   *
   *@return    The revenue value
   */
  public GraphSummaryList getRevenue() {
    return revenue;
  }


  /**
   *  Sets the revenue attribute of the User object
   *
   *@param  revenue  The new revenue value
   */
  public void setRevenue(GraphSummaryList revenue) {
    this.revenue = revenue;
  }


  /**
   *  Gets the yTD attribute of the User object
   *
   *@return    The yTD value
   */
  public double getYTD() {
    return YTD;
  }


  /**
   *  Sets the yTD attribute of the User object
   *
   *@param  YTD  The new yTD value
   */
  public void setYTD(double YTD) {
    this.YTD = YTD;
  }


  /**
   *  Gets the yTDValue attribute of the User object
   *
   *@return    The yTDValue value
   */
  public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }

    if (Integer.parseInt(toReturn) == 0) {
      toReturn = "";
    }

    return toReturn;
  }


  /**
   *  Gets the yTDCurrency attribute of the User object
   *
   *@return    The yTDCurrency value
   */
  public String getYTDCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(YTD);
    return amountOut;
  }


  /**
   *  Constructor for the User object
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@param  doHierarchy       Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.28
   */
  public User(Connection db, int userId, boolean doHierarchy) throws SQLException {
    this.buildHierarchy = doHierarchy;
    buildRecord(db, userId);
  }


  /**
   *  Sets the expires attribute of the User object
   *
   *@param  expires  The new expires value
   */
  public void setExpires(java.sql.Date expires) {
    this.expires = expires;
  }


  /**
   *  Sets the expires attribute of the User object
   *
   *@param  tmp  The new expires value
   */
  public void setExpires(String tmp) {
    this.expires = DateUtils.parseDateString(tmp);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int generateRandomPassword(Connection db, ActionContext context) throws SQLException {
    int resultCount = -1;
    User modUser = null;
    Contact targetContact = null;

    String newPassword = randomstring(6, 8);
    this.setPassword1(newPassword);
    this.setPassword2(newPassword);
    resultCount = this.newPassword(db, context);

    if (resultCount > -1) {
      modUser = new User();
      modUser.setBuildContact(true);
      modUser.buildRecord(db, modifiedBy);

      targetContact = new Contact(db, this.getContactId());

      //send email
      SMTPMessage mail = new SMTPMessage();
      mail.setHost((String) System.getProperty("MailServer"));
      mail.setFrom("cfs-root@darkhorseventures.com");
      mail.setType("text/html");
      mail.setTo(targetContact.getEmailAddress("Business"));
      mail.setSubject("CFS password changed");
      mail.setBody("Your CFS User account password has been changed by " + modUser.getUsername() + " (" + modUser.getContact().getNameLastFirst() + ").<br><br>" +
          " Your new CFS password is the following:<br>" + newPassword + "<br><br>" +
          "It is recomended that you change your password next time you login to CFS.");
      if (mail.send() == 2) {
        System.err.println(mail.getErrorMsg());
      }
    }

    return resultCount;
  }


  /**
   *  Sets the Assistant attribute of the User object
   *
   *@param  tmp  The new Assistant value
   *@since
   */
  public void setAssistant(int tmp) {
    this.assistant = tmp;
  }


  /**
   *  Gets the childUsers attribute of the User object
   *
   *@return    The childUsers value
   */
  public UserList getChildUsers() {
    return childUsers;
  }


  /**
   *  Sets the assistant attribute of the User object
   *
   *@param  tmp  The new assistant value
   */
  public void setAssistant(String tmp) {
    this.assistant = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Alias attribute of the User object
   *
   *@param  tmp  The new Alias value
   *@since
   */
  public void setAlias(int tmp) {
    alias = tmp;
  }


  /**
   *  Sets the alias attribute of the User object
   *
   *@param  tmp  The new alias value
   */
  public void setAlias(String tmp) {
    alias = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Gmr attribute of the User object
   *
   *@param  tmp  The new Gmr value
   *@since       1.18
   */
  public void setGmr(GraphSummaryList tmp) {
    this.gmr = tmp;
  }


  /**
   *  Sets the managerUserEnabled attribute of the User object
   *
   *@param  managerUserEnabled  The new managerUserEnabled value
   */
  public void setManagerUserEnabled(boolean managerUserEnabled) {
    this.managerUserEnabled = managerUserEnabled;
  }


  /**
   *  Gets the managerUserEnabled attribute of the User object
   *
   *@return    The managerUserEnabled value
   */
  public boolean getManagerUserEnabled() {
    return managerUserEnabled;
  }


  /**
   *  Gets the revenueLock attribute of the User object
   *
   *@return    The revenueLock value
   */
  public boolean getRevenueLock() {
    return revenueLock;
  }


  /**
   *  Sets the revenueLock attribute of the User object
   *
   *@param  revenueLock  The new revenueLock value
   */
  public void setRevenueLock(boolean revenueLock) {
    this.revenueLock = revenueLock;
  }


  /**
   *  Sets the Ramr attribute of the User object
   *
   *@param  tmp  The new Ramr value
   *@since       1.18
   */
  public void setRamr(GraphSummaryList tmp) {
    this.ramr = tmp;
  }


  /**
   *  Gets the pipelineValueIsValid attribute of the User object
   *
   *@return    The pipelineValueIsValid value
   */
  public boolean getPipelineValueIsValid() {
    return pipelineValueIsValid;
  }


  /**
   *  Sets the pipelineValueIsValid attribute of the User object
   *
   *@param  pipelineValueIsValid  The new pipelineValueIsValid value
   */
  public void setPipelineValueIsValid(boolean pipelineValueIsValid) {
    this.pipelineValueIsValid = pipelineValueIsValid;
  }


  /**
   *  Sets the IsValid attribute of the User object
   *
   *@param  isValid   The new IsValid value
   *@param  dataAlso  The new IsValid value
   *@since
   */
  public void setIsValid(boolean isValid, boolean dataAlso) {
    if (dataAlso) {
      this.gmr.setIsValid(isValid);
      this.ramr.setIsValid(isValid);
      this.cgmr.setIsValid(isValid);
      this.cramr.setIsValid(isValid);
    }

    if (!isValid) {
      this.gmr.setLastFileName(null);
      this.ramr.setLastFileName(null);
      this.cgmr.setLastFileName(null);
      this.cramr.setLastFileName(null);
    }

    if (managerUser != null) {
      managerUser.setIsValid(false, false);
    }
  }


  /**
   *  Sets the revenueIsValid attribute of the User object
   *
   *@param  isValid   The new revenueIsValid value
   *@param  dataAlso  The new revenueIsValid value
   */
  public void setRevenueIsValid(boolean isValid, boolean dataAlso) {
    if (dataAlso == true) {
      this.revenue.setIsValid(isValid);
    }
    if (isValid == false) {
      this.revenue.setLastFileName(null);
    }
    if (managerUser != null) {
      managerUser.setRevenueIsValid(false, false);
    }
  }


  /**
   *  Sets the aliasName attribute of the User object
   *
   *@param  aliasName  The new aliasName value
   */
  public void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }


  /**
   *  Sets the GraphValues attribute of the User object
   *
   *@param  key  The new GraphValues value
   *@param  v1   The new GraphValues value
   *@param  v2   The new GraphValues value
   *@param  v3   The new GraphValues value
   *@param  v4   The new GraphValues value
   *@since
   */
  public void setGraphValues(String key, Double v1, Double v2, Double v3, Double v4) {
    this.getGmr().setValue(key, v1);
    this.getRamr().setValue(key, v2);
    this.getCgmr().setValue(key, v3);
    this.getCramr().setValue(key, v4);
  }


  /**
   *  Sets the revenueGraphValues attribute of the User object
   *
   *@param  key  The new revenueGraphValues value
   *@param  v1   The new revenueGraphValues value
   */
  public void setRevenueGraphValues(String key, Double v1) {
    this.getRevenue().setValue(key, v1);
  }


  /**
   *  Sets the Cgmr attribute of the User object
   *
   *@param  tmp  The new Cgmr value
   *@since       1.18
   */
  public void setCgmr(GraphSummaryList tmp) {
    this.cgmr = tmp;
  }


  /**
   *  Sets the Cramr attribute of the User object
   *
   *@param  tmp  The new Cramr value
   *@since       1.18
   */
  public void setCramr(GraphSummaryList tmp) {
    this.cramr = tmp;
  }


  /**
   *  Sets the Id attribute of the User object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the User object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Username attribute of the User object
   *
   *@param  tmp  The new Username value
   *@since       1.1
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Sets the PreviousUsername attribute of the User object
   *
   *@param  tmp  The new PreviousUsername value
   *@since       1.28
   */
  public void setPreviousUsername(String tmp) {
    this.previousUsername = tmp;
  }


  /**
   *  Sets the encryptedPassword attribute of the User object
   *
   *@param  tmp  The new encryptedPassword value
   */
  public void setEncryptedPassword(String tmp) {
    this.encryptedPassword = tmp;
  }


  /**
   *  Sets the Password attribute of the User object
   *
   *@param  tmp  The new Password value
   *@since       1.1
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the Password1 attribute of the User object
   *
   *@param  tmp  The new Password1 value
   *@since       1.1
   */
  public void setPassword1(String tmp) {
    this.password1 = tmp;
  }


  /**
   *  Sets the Password2 attribute of the User object
   *
   *@param  tmp  The new Password2 value
   *@since       1.1
   */
  public void setPassword2(String tmp) {
    this.password2 = tmp;
  }


  /**
   *  Sets the entered attribute of the User object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the User object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the User object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the User object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the ContactId attribute of the User object
   *
   *@param  tmp  The new ContactId value
   *@since       1.1
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
    this.contact.setId(tmp);
  }


  /**
   *  Sets the ContactId attribute of the User object
   *
   *@param  tmp  The new ContactId value
   *@since       1.1
   */
  public void setContactId(String tmp) {
    if (tmp != null) {
      setContactId(Integer.parseInt(tmp));
    }
  }


  /**
   *  Sets the RoleId attribute of the User object
   *
   *@param  tmp  The new RoleId value
   *@since       1.1
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newManager        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean reassign(Connection db, int newManager) throws SQLException {
    int result = -1;
    this.setManagerId(newManager);
    result = this.update(db);

    if (result == -1) {
      return false;
    }

    return true;
  }


  /**
   *  Sets the RoleId attribute of the User object
   *
   *@param  tmp  The new RoleId value
   *@since       1.1
   */
  public void setRoleId(String tmp) {
    if (tmp != null) {
      this.roleId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the pipelineValue attribute of the User object
   *
   *@param  pipelineValue  The new pipelineValue value
   */
  public void setPipelineValue(double pipelineValue) {
    this.pipelineValue = pipelineValue;
  }


  /**
   *  Gets the pipelineValue attribute of the User object
   *
   *@return    The pipelineValue value
   */
  public double getPipelineValue() {
    return pipelineValue;
  }


  /**
   *  Sets the Role attribute of the User object
   *
   *@param  tmp  The new Role value
   *@since       1.11
   */
  public void setRole(String tmp) {
    this.role = tmp;
  }


  /**
   *  Sets the managerId attribute of the User object
   *
   *@param  tmp  The new managerId value
   *@since       1.1
   */
  public void setManagerId(int tmp) {
    this.managerId = (tmp);
  }


  /**
   *  Sets the Manager attribute of the User object
   *
   *@param  tmp  The new Manager value
   *@since       1.11
   */
  public void setManager(String tmp) {
    this.manager = tmp;
  }


  /**
   *  Sets the managerId attribute of the User object
   *
   *@param  tmp  The new managerId value
   *@since       1.1
   */
  public void setManagerId(String tmp) {
    if (tmp != null) {
      this.managerId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the EnteredBy attribute of the User object
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.1
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the User object
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.1
   */
  public void setEnteredBy(String tmp) {
    if (tmp != null) {
      this.enteredBy = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the ModifiedBy attribute of the User object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.1
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the User object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.1
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the User object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the User object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(String tmp) {
    if (tmp.toLowerCase().equals("false")) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
  }


  /**
   *  Sets the lastLogin attribute of the User object
   *
   *@param  tmp  The new lastLogin value
   */
  public void setLastLogin(java.sql.Timestamp tmp) {
    this.lastLogin = tmp;
  }


  /**
   *  Sets the lastLogin attribute of the User object
   *
   *@param  tmp  The new lastLogin value
   */
  public void setLastLogin(String tmp) {
    this.lastLogin = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Ip attribute of the User object
   *
   *@param  tmp  The new Ip value
   *@since
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   *  Sets the TimeZone attribute of the User object
   *
   *@param  tmp  The new TimeZone value
   *@since
   */
  public void setTimeZone(String tmp) {
    this.timeZone = tmp;
  }


  /**
   *  Sets the BuildContact attribute of the User object
   *
   *@param  tmp  The new BuildContact value
   *@since       1.29
   */
  public void setBuildContact(boolean tmp) {
    this.buildContact = tmp;
  }


  /**
   *  Sets the BuildHierarchy attribute of the User object
   *
   *@param  tmp  The new BuildHierarchy value
   *@since       1.23
   */
  public void setBuildHierarchy(boolean tmp) {
    this.buildHierarchy = tmp;
  }


  /**
   *  Sets the Contact attribute of the User object
   *
   *@param  tmp  The new Contact value
   *@since       1.1
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   *  Sets the ManagerUser attribute of the User object
   *
   *@param  tmp  The new ManagerUser value
   *@since       1.17
   */
  public void setManagerUser(User tmp) {
    this.managerUser = tmp;
  }


  /**
   *  Sets the ChildUsers attribute of the User object
   *
   *@param  tmp  The new ChildUsers value
   *@since
   */
  public void setChildUsers(UserList tmp) {
    this.childUsers = tmp;
  }


  /**
   *  Gets the expires attribute of the User object
   *
   *@return    The expires value
   */
  public java.sql.Date getExpires() {
    return expires;
  }


  /**
   *  Gets the expiresString attribute of the User object
   *
   *@return    The expiresString value
   */
  public String getExpiresString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(expires);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the aliasName attribute of the User object
   *
   *@return    The aliasName value
   */
  public String getAliasName() {
    return aliasName;
  }


  /**
   *  Gets the entered attribute of the User object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the User object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the User object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredString attribute of the User object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the lastLogin attribute of the User object
   *
   *@return    The lastLogin value
   */
  public java.sql.Timestamp getLastLogin() {
    return lastLogin;
  }


  /**
   *  Gets the lastLoginString attribute of the User object
   *
   *@return    The lastLoginString value
   */
  public String getLastLoginString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(lastLogin);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the Assistant attribute of the User object
   *
   *@return    The Assistant value
   *@since
   */
  public int getAssistant() {
    return assistant;
  }


  /**
   *  Gets the Alias attribute of the User object
   *
   *@return    The Alias value
   *@since
   */
  public int getAlias() {
    return alias;
  }


  /**
   *  Gets the IsValid attribute of the User object
   *
   *@return    The IsValid value
   *@since
   */
  public boolean getIsValid() {
    if (this.gmr.getIsValid() == true && this.ramr.getIsValid() == true &&
        this.cgmr.getIsValid() == true && this.cramr.getIsValid() == true) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Gets the revenueIsValid attribute of the User object
   *
   *@return    The revenueIsValid value
   */
  public boolean getRevenueIsValid() {
    if (this.revenue.getIsValid() == true) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Gets the Gmr attribute of the User object
   *
   *@return    The Gmr value
   *@since     1.18
   */
  public GraphSummaryList getGmr() {
    return gmr;
  }


  /**
   *  Gets the Ramr attribute of the User object
   *
   *@return    The Ramr value
   *@since     1.18
   */
  public GraphSummaryList getRamr() {
    return ramr;
  }


  /**
   *  Gets the Cgmr attribute of the User object
   *
   *@return    The Cgmr value
   *@since     1.18
   */
  public GraphSummaryList getCgmr() {
    return cgmr;
  }


  /**
   *  Gets the Cramr attribute of the User object
   *
   *@return    The Cramr value
   *@since     1.18
   */
  public GraphSummaryList getCramr() {
    return cramr;
  }



  /**
   *  Gets the Id attribute of the User object
   *
   *@return    The Id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Username attribute of the User object
   *
   *@return    The Username value
   *@since     1.1
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Gets the PreviousUsername attribute of the User object
   *
   *@return    The PreviousUsername value
   *@since     1.28
   */
  public String getPreviousUsername() {
    return previousUsername;
  }


  /**
   *  Gets the Password attribute of the User object
   *
   *@return    The Password value
   *@since     1.1
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the EncryptedPassword attribute of the User object
   *
   *@return    The EncryptedPassword value
   *@since
   */
  public String getEncryptedPassword() {
    if (encryptedPassword != null) {
      return encryptedPassword;
    } else {
      return this.encryptPassword(password);
    }
  }


  /**
   *  Gets the Password1 attribute of the User object
   *
   *@return    The Password1 value
   *@since     1.1
   */
  public String getPassword1() {
    return password1;
  }



  /**
   *  Gets the Password2 attribute of the User object
   *
   *@return    The Password2 value
   *@since     1.1
   */
  public String getPassword2() {
    return password2;
  }


  /**
   *  Gets the ContactId attribute of the User object
   *
   *@return    The ContactId value
   *@since     1.1
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the RoleId attribute of the User object
   *
   *@return    The RoleId value
   *@since     1.1
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   *  Gets the managerId attribute of the User object
   *
   *@return    The manager value
   *@since     1.1
   */
  public int getManagerId() {
    return managerId;
  }


  /**
   *  Gets the Manager attribute of the User object
   *
   *@return    The Manager value
   *@since     1.11
   */
  public String getManager() {
    return manager;
  }


  /**
   *  Gets the Role attribute of the User object
   *
   *@return    The Role value
   *@since     1.11
   */
  public String getRole() {
    return role;
  }


  /**
   *  Gets the EnteredBy attribute of the User object
   *
   *@return    The EnteredBy value
   *@since     1.1
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Ip attribute of the User object
   *
   *@return    The Ip value
   *@since
   */
  public String getIp() {
    return ip;
  }


  /**
   *  Gets the TimeZone attribute of the User object
   *
   *@return    The TimeZone value
   *@since
   */
  public String getTimeZone() {
    return timeZone;
  }


  /**
   *  Gets the FullChildList attribute of the User object
   *
   *@param  inList       Description of Parameter
   *@param  currentList  Description of Parameter
   *@return              The FullChildList value
   *@since               1.23
   */
  public UserList getFullChildList(UserList inList, UserList currentList) {
    if (inList != null) {
      Iterator j = inList.iterator();
      while (j.hasNext()) {
        User thisRec = (User) j.next();
        currentList.addElement(thisRec);
        UserList countList = thisRec.getShortChildList();
        if (countList != null && countList.getListSize() > 0) {
          currentList = thisRec.getFullChildList(countList, currentList);
        }
      }
    }
    return currentList;
  }


  /**
   *  Gets the ShortChildList attribute of the User object
   *
   *@return    The ShortChildList value
   *@since     1.20
   */
  public UserList getShortChildList() {
    return this.childUsers;
  }


  /**
   *  Gets the Child attribute of the User object by navigating through the
   *  children.
   *
   *@param  childId  Description of Parameter
   *@return          The Child value
   *@since           1.30
   */
  public User getChild(int childId) {
    UserList shortChildList = this.getShortChildList();
    if (shortChildList != null) {
      //System.out.println("User-> Child List Size: " + shortChildList.size());
      UserList fullChildList = this.getFullChildList(shortChildList, new UserList());
      Iterator i = fullChildList.iterator();
      while (i.hasNext()) {
        User childRecord = (User) i.next();
        if (childRecord.getId() == childId) {
          return childRecord;
        }
      }
    }
    return null;
  }


  /**
   *  Gets the ModifiedBy attribute of the User object
   *
   *@return    The ModifiedBy value
   *@since     1.1
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Enabled attribute of the User object
   *
   *@return    The Enabled value
   *@since     1.1
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Geentuhe Contact attribute of the User object
   *
   *@return    The Contact value
   *@since     1.1
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the ManagerUser attribute of the User object
   *
   *@return    The ManagerUser value
   *@since     1.17
   */
  public User getManagerUser() {
    return managerUser;
  }


  /**
   *  Returns whether this user is above the specified userId. If the specified
   *  userId is a child of this user, returns true.
   *
   *@param  userId  Description of Parameter
   *@return         The managerOf value
   */
  public boolean isManagerOf(int userId) {
    return (this.getChild(userId) != null);
  }


  /**
   *  Sets the opportunityLock to true, causing any other requests to this
   *  method to block until released.
   *
   *@since    1.19
   */
  public void doOpportunityLock() {
    while (opportunityLock == true) {
    }
    synchronized (this) {
      while (opportunityLock) {
      }
      this.opportunityLock = true;
    }
  }


  /**
   *  Description of the Method
   */
  public void doRevenueLock() {
    while (revenueLock == true) {
    }
    synchronized (this) {
      while (revenueLock) {
      }
      this.revenueLock = true;
    }
  }


  /**
   *  Sets the opportunityLock to false, allowing any waiting requests to try
   *  and lock it.
   *
   *@since    1.19
   */
  public void doOpportunityUnlock() {
    this.opportunityLock = false;
  }


  /**
   *  Description of the Method
   */
  public void doRevenueUnlock() {
    this.revenueLock = false;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@param  currPass          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int updatePassword(Connection db, ActionContext context, String currPass) throws SQLException {
    if (!isValidChangePass(context, currPass)) {
      return -1;
    } else {
      int resultCount = -1;

      if (this.getId() == -1) {
        throw new SQLException("User ID was not specified");
      }

      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE access " +
          "SET password = ? " +
          "WHERE user_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      pst.setString(++i, encryptPassword(password1));
      pst.setInt(++i, getId());
      resultCount = pst.executeUpdate();
      pst.close();

      return resultCount;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int select(Connection db) throws SQLException {
    buildRecord(db, this.id);
    if (expires != null && (new java.util.Date()).after(expires)) {
      //set error message
      return -1;
    } else {
      if (alias > 0) {
        return alias;
      } else {
        return this.id;
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
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);

      if (System.getProperty("DEBUG") != null) {
        System.out.println("User-> Beginning insert");
      }

      //new contact at the same time
      if (contactId < 1) {
        Contact newContact = this.getContact();
        newContact.setEnteredBy(enteredBy);
        newContact.setModifiedBy(modifiedBy);
        newContact.setOwner(enteredBy);
        newContact.insert(db);

        if (System.getProperty("DEBUG") != null) {
          System.out.println("User-> Inserting new Contact");
        }

        contactId = newContact.getId();

        if (System.getProperty("DEBUG") != null) {
          System.out.println("User-> New Contact ID: " + newContact.getId());
        }
      }

      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO access " +
          "(username, password, contact_id, alias, " +
          "manager_id, role_id, expires, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      if (lastLogin != null) {
        sql.append("last_login, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      if (lastLogin != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, getUsername());

      if (encryptedPassword != null) {
        pst.setString(++i, encryptedPassword);
      } else {
        pst.setString(++i, encryptPassword(password1));
      }
      pst.setInt(++i, contact.getId());
      pst.setInt(++i, getAlias());
      if (getAlias() > -1) {
        pst.setInt(++i, -1);
      } else {
        pst.setInt(++i, getManagerId());
      }
      pst.setInt(++i, getRoleId());

      if (expires == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setDate(++i, this.getExpires());
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      if (lastLogin != null) {
        pst.setTimestamp(++i, lastLogin);
      }

      pst.setInt(++i, getEnteredBy());
      pst.setInt(++i, getModifiedBy());
      pst.execute();
      pst.close();

      if (System.getProperty("DEBUG") != null) {
        System.out.println("User-> Getting interval value");
      }

      id = DatabaseUtils.getCurrVal(db, "access_user_id_seq");

      if (System.getProperty("DEBUG") != null) {
        System.out.println("User-> Updating contact");
      }

      pst = db.prepareStatement(
          "UPDATE contact " +
          "SET user_id = ? " +
          "WHERE contact_id = ? ");
      pst.setInt(1, id);
      pst.setInt(2, contact.getId());
      pst.executeUpdate();
      pst.close();
      db.commit();

      if (System.getProperty("DEBUG") != null) {
        System.out.println("User-> User inserted & contact record updated");
      }
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);
    return true;
  }


  /**
   *  Inserts the current user record into the database
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    if (!isValid(db, context)) {
      return false;
    }
    return this.insert(db);
  }


  /**
   *  Deletes the current user record
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified.");
    }

    int resultCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE access " +
        "SET enabled = ? " +
        "WHERE user_id = ? ");
    pst.setBoolean(1, false);
    pst.setInt(2, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 0) {
      errors.put("actionError", "User could not be disabled because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean enable(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified.");
    }

    int resultCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE access " +
        "SET enabled = ? " +
        "WHERE user_id = ? ");
    pst.setBoolean(1, true);
    pst.setInt(2, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 0) {
      errors.put("actionError", "User could not be enabled.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  This method builds are extra data for a user... Children Users, etc.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.12
   */
  public void buildResources(Connection db) throws SQLException {
    if (buildContact || buildHierarchy) {
      if (this.getContactId() > -1) {
        contact = new Contact(db, this.getContactId());
      }
    }
    if (buildHierarchy) {
      buildChildren(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.18
   */
  public void updateLogin(Connection db) throws SQLException {
    if (this.id > -1) {
      String sql =
          "UPDATE access " +
          "SET last_login = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
          "last_ip = ? " +
          "WHERE user_id = ? ";
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setString(1, this.ip);
      pst.setInt(2, this.id);
      pst.executeUpdate();
      pst.close();
    }

    insertLogRecord(db);

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void insertLogRecord(Connection db) throws SQLException {
    AccessLog thisLog = new AccessLog();
    thisLog.setUserId(this.getId());
    thisLog.setUsername(this.getUsername());
    thisLog.setIp(this.getIp());
    thisLog.insert(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  userId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.10
   */
  public void buildRecord(Connection db, int userId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT a.username, a.password, a.role_id, a.last_login, a.manager_id, " +
        "a.last_ip, a.timezone, a.startofday as access_startofday, " +
        "a.endofday as access_endofday, a.expires, a.alias, " +
        "a.contact_id as contact_id_link, a.user_id as access_user_id, " +
        "a.enabled as access_enabled, a.assistant, " +
        "a.entered as access_entered, a.enteredby as access_enteredby, " +
        "a.modified as access_modified, a.modifiedby as access_modifiedby, " +
        "r.role, " +
        "m.namefirst as mgr_namefirst, m.namelast as mgr_namelast, m_usr.enabled as mgr_enabled, " +
        "als.namefirst as als_namefirst, als.namelast as als_namelast, " +
        "c.* " +
        "FROM access a " +
        "LEFT JOIN contact c ON (a.contact_id = c.contact_id) " +
        "LEFT JOIN contact als ON (a.alias = als.user_id) " +
        "LEFT JOIN contact m ON (a.manager_id = m.user_id) " +
        "LEFT JOIN access m_usr ON (a.manager_id = m_usr.user_id) " +
        "LEFT JOIN role r ON (a.role_id = r.role_id) " +
        "WHERE a.user_id > -1 ");
    if (userId > -1) {
      sql.append("AND a.user_id = ? ");
    } else {
      sql.append(
          "AND lower(a.username) = ? " +
          "AND a.password = ? " +
          "AND a.enabled = ? ");
    }
    pst = db.prepareStatement(sql.toString());
    int i = 0;
    if (userId > -1) {
      pst.setInt(++i, userId);
    } else {
      pst.setString(++i, username);
      pst.setString(++i, encryptPassword(password));
      pst.setBoolean(++i, true);
    }
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("User record not found.");
    }
    rs.close();
    pst.close();
    buildResources(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  year              Description of the Parameter
   *@param  type              Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRevenueYTD(Connection db, int year, int type) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT sum(rv.amount) as s " +
        "FROM revenue rv " +
        "WHERE rv.owner IN (" + this.getIdRange() + ") AND rv.year = ? ");
    if (type > 0) {
      sql.append("AND rv.type = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, year);
    if (type > 0) {
      pst.setInt(++i, type);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setYTD(rs.getDouble("s"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the grossPipelineCurrency attribute of the User object
   *
   *@param  divisor  Description of the Parameter
   *@return          The grossPipelineCurrency value
   */
  public String getGrossPipelineCurrency(int divisor) {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    double tempValue = (java.lang.Math.round(pipelineValue) / divisor);
    String amountOut = "";

    if (tempValue < 1) {
      if (tempValue == 0.0) {
        amountOut = "0";
      } else {
        amountOut = "<1";
      }
    } else {
      amountOut = numberFormatter.format(tempValue);
    }

    return amountOut;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildGrossPipelineValue(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT SUM(oc.guessvalue) AS sum " +
        "FROM opportunity_component oc " +
        "WHERE oc.owner IN (" + this.getIdRange() + ") " +
        "AND oc.enabled = ? ");
    pst.setBoolean(1, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setPipelineValue(rs.getDouble("sum"));
    }
    rs.close();
    pst.close();
    this.setPipelineValueIsValid(true);
  }


  /**
   *  Method added in for SSS and other ASP customers newPassword: Updates the
   *  User's password with a new one. Does not verify the current one since this
   *  method is intended for those who have forgotten their password and need a
   *  new one.
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public int newPassword(Connection db, ActionContext context) throws SQLException {
    int resultCount = -1;

    if (this.getId() == -1) {
      throw new SQLException("User ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE access " +
        "SET password = ? ");
    if (modifiedBy > -1) {
      sql.append(", modifiedby = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " ");
    }
    sql.append(" WHERE user_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, encryptPassword(password1));
    if (modifiedBy > -1) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, getId());
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Gets the Valid attribute of the User object
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since                    1.12
   */
  protected boolean isValid(Connection db, ActionContext context) throws SQLException {
    isValidNoPass(db, context);

    if (contactId < 1 && (contact == null || !contact.isValid(db))) {
      errors.put("contactIdError", "Contact needs to be selected or newly created first");
    }

    if (password1 == null || password1.trim().equals("")) {
      errors.put("password1Error", "Password cannot be left blank");
    }

    if (!password1.equals(password2)) {
      errors.put("password2Error", "Verification password does not match");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Gets the ValidChangePass attribute of the User object
   *
   *@param  context      Description of Parameter
   *@param  currentPass  Description of Parameter
   *@return              The ValidChangePass value
   *@since
   */
  protected boolean isValidChangePass(ActionContext context, String currentPass) {

    if (!(this.getEncryptedPassword().equals(currentPass)) || password == null || password.trim().equals("")) {
      errors.put("passwordError", "Incorrect value for current password");
    }

    if (password1 == null || password1.trim().equals("")) {
      errors.put("password1Error", "Password cannot be left blank");
    }

    if (!password1.equals(password2)) {
      errors.put("password2Error", "Verification password does not match");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Gets the ValidNoPass attribute of the User object
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   The ValidNoPass value
   *@exception  SQLException  Description of Exception
   *@since                    1.27
   */
  protected boolean isValidNoPass(Connection db, ActionContext context) throws SQLException {
    errors.clear();

    if (username == null || username.trim().equals("")) {
      errors.put("usernameError", "Username cannot be left blank");
    } else {
      if (isDuplicate(db)) {
        errors.put("usernameError", "Username is already in use");
      }
    }

    if (roleId < 0) {
      errors.put("roleError", "Role needs to be selected");
    }

    //Check hierarchy context for circular references
    if (managerId > 0 && id > -1 && alias == -1) {
      if (managerId == id) {
        //Check 1: User cannot report to self
        errors.put("managerIdError", "User cannot report to itself");
      } else {
        //Check 2: User cannot report to someone already beneath them
        ConnectionElement ce = (ConnectionElement) context.getRequest().getSession().getAttribute("ConnectionElement");
        SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
        User updatedUser = systemStatus.getHierarchyList().getUser(id);
        User testChild = updatedUser.getChild(managerId);

        if (testChild != null) {
          //Since the new manager is a child of this user, display the hierarchy for the user
          //Start at the testChild and work up to current user
          Stack names = new Stack();
          int currentId = testChild.getId();
          while (currentId != id) {
            String childName = testChild.getContact().getNameFirstLast();
            names.push(childName);
            testChild = testChild.getManagerUser();
            currentId = testChild.getId();
          }
          names.push(updatedUser.getContact().getNameFirstLast());
          //Now work back down and show the hierarchy
          StringBuffer sb = new StringBuffer();
          sb.append("Cannot create a circular hierarchy, review current hierarchy:\r\n");
          while (!names.empty()) {
            sb.append((String) names.pop());
            if (!names.empty()) {
              sb.append(" < ");
            }
          }
          errors.put("managerIdError", sb.toString());
        }
      }
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Populates the current user record from a ResultSet
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setUsername(rs.getString("username"));
    String thisPassword = rs.getString("password");
    this.setPassword(thisPassword);
    this.setEncryptedPassword(thisPassword);
    this.setRoleId(rs.getInt("role_id"));
    lastLogin = rs.getTimestamp("last_login");
    this.setManagerId(rs.getInt("manager_id"));
    ip = rs.getString("last_ip");
    timeZone = rs.getString("timezone");
    startOfDay = rs.getInt("access_startofday");
    endOfDay = rs.getInt("access_endofday");
    expires = rs.getDate("expires");
    this.setAlias(rs.getInt("alias"));
    this.setContactId(rs.getInt("contact_id_link"));
    this.setId(rs.getInt("access_user_id"));
    enabled = rs.getBoolean("access_enabled");
    this.setAssistant(rs.getInt("assistant"));
    entered = rs.getTimestamp("access_entered");
    enteredBy = rs.getInt("access_enteredby");
    modified = rs.getTimestamp("access_modified");
    modifiedBy = rs.getInt("access_modifiedby");
    this.setRole(rs.getString("role"));

    String managerNameFirst = rs.getString("mgr_namefirst");
    String managerNameLast = rs.getString("mgr_namelast");

    //check to see if manager user is enabled
    if (managerId > -1) {
      managerUserEnabled = rs.getBoolean("mgr_enabled");
    }

    if (managerNameFirst != null || managerNameLast != null) {
      this.manager = Contact.getNameLastFirst(managerNameLast, managerNameFirst);
    }

    String aliasNameFirst = rs.getString("als_namefirst");
    String aliasNameLast = rs.getString("als_namelast");
    if (aliasNameFirst != null) {
      this.aliasName = Contact.getNameLastFirst(aliasNameLast, aliasNameFirst);
    }
  }


  /**
   *  Checks to see if the user is a duplicate in the database
   *
   *@param  db                Description of Parameter
   *@return                   The Duplicate value
   *@exception  SQLException  Description of Exception
   *@since                    1.11
   */
  private boolean isDuplicate(Connection db) throws SQLException {
    boolean duplicate = false;
    if (previousUsername != null && previousUsername.equals(username)) {
      return false;
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE lower(username) = lower(?) " +
        "AND enabled = ? ");
    pst.setString(1, getUsername());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      duplicate = true;
    }
    rs.close();
    pst.close();
    return duplicate;
  }


  /**
   *  Gets the contactId attribute of the User class
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@return                   The contactId value
   *@exception  SQLException  Description of the Exception
   */
  public static int getContactId(Connection db, int userId) throws SQLException {
    int contactId = -1;
    if (userId == -1) {
      throw new SQLException("User Id not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT contact_id " +
        "FROM access " +
        "WHERE user_id = ? ");
    pst.setInt(1, userId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      contactId = rs.getInt("contact_id");
    }
    rs.close();
    pst.close();
    return contactId;
  }


  /**
   *  Updates the list of users that this user manages.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.28
   */
  private void buildChildren(Connection db) throws SQLException {
    childUsers = new UserList(db, this, true);
  }


  /**
   *  Updates the current user record
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    int i = -1;
    i = this.update(db, context, false);
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    return update(db, null, true);
  }


  /**
   *  Updates the current user record, this method is used internally
   *
   *@param  db                Description of Parameter
   *@param  override          Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  private int update(Connection db, ActionContext context, boolean override) throws SQLException {
    int resultCount = 0;
    int defaultManager = -1;
    if (context != null && !isValidNoPass(db, context)) {
      return -1;
    }
    if (this.getId() == -1) {
      throw new SQLException("User ID was not specified");
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE access " +
        "SET username = ?, manager_id = ?, role_id = ?, expires = ?, ");
    if (password1 != null) {
      sql.append("password = ?, ");
    }
    if (enteredBy > -1) {
      sql.append("enteredby = ?, ");
    }
    if (modifiedBy > -1) {
      sql.append("modifiedby = ?, ");
    }
    if (contactId > -1) {
      sql.append("contact_id = ?, ");
    }
    if (assistant > -1) {
      sql.append("assistant = ?, ");
    }
    sql.append("alias = ? ");
    sql.append("WHERE user_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, username);
    if (this.getAlias() > -1) {
      pst.setInt(++i, defaultManager);
    } else {
      pst.setInt(++i, this.getManagerId());
    }
    pst.setInt(++i, roleId);
    if (expires == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getExpires());
    }
    if (password1 != null) {
      pst.setString(++i, encryptPassword(password1));
    }
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (modifiedBy > -1) {
      pst.setInt(++i, modifiedBy);
    }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (assistant > -1) {
      pst.setInt(++i, assistant);
    }
    pst.setInt(++i, alias);
    pst.setInt(++i, getId());
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Hashes a password... the resulting encrypted password cannot be decrypted
   *  since this is one-way.
   *
   *@param  tmp  Description of Parameter
   *@return      Description of the Returned Value
   *@since       1.1
   */
  private String encryptPassword(String tmp) {
    PasswordHash passwordHash = new PasswordHash();
    return passwordHash.encrypt(tmp);
  }


  /**
   *  The following functions are used to generate a random password
   *
   *@param  lo  Description of the Parameter
   *@param  hi  Description of the Parameter
   *@return     Description of the Return Value
   */
  public static int rand(int lo, int hi) {
    int n = hi - lo + 1;
    int i = rn.nextInt() % n;
    if (i < 0) {
      i = -i;
    }
    return lo + i;
  }


  /**
   *  Description of the Method
   *
   *@param  lo  Description of the Parameter
   *@param  hi  Description of the Parameter
   *@return     Description of the Return Value
   */
  public static String randomstring(int lo, int hi) {
    int n = rand(lo, hi);
    byte b[] = new byte[n];
    for (int i = 0; i < n; i++) {
      b[i] = (byte) rand('a', 'z');
    }
    return new String(b, 0);
  }


  /**
   *  Returns a comma delimited string of all users in this user's hierarchy for
   *  querying records.
   *
   *@return    The idRange value
   */
  public String getIdRange() {
    UserList shortChildList = this.getShortChildList();
    UserList fullChildList = this.getFullChildList(shortChildList, new UserList());
    return (fullChildList.getUserListIds(id));
  }
}


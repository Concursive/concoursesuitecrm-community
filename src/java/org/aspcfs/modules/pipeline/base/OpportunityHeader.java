//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.utils.DateUtils;
import com.zeroio.iteam.base.FileItemList;

/**
 *  OpportunityHeader: A header related to an Opportunity ( = OpportunityHeader
 *  + OpportunityComponent(s))
 *
 *@author     chris
 *@created    September 13, 2001
 *@version    $Id$
 */

public class OpportunityHeader extends GenericBean {
  protected int oppId = -1;
  protected String description = null;
  private int accountLink = -1;
  private int contactLink = -1;
  private String accountName = "";
  private String contactName = "";
  private String contactCompanyName = "";
  private String enteredByName = "";
  private String modifiedByName = "";
  private boolean accountEnabled = true;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;

  private int componentCount = 0;
  private boolean buildComponentCount = true;
  private double totalValue = 0;
  private FileItemList files = new FileItemList();


  /**
   *  Constructor for the OpportunityHeader object
   */
  public OpportunityHeader() { }


  /**
   *  Constructor for the OpportunityHeader object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OpportunityHeader(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the OpportunityHeader object
   *
   *@param  db                Description of the Parameter
   *@param  oppId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OpportunityHeader(Connection db, int oppId) throws SQLException {
    queryRecord(db, oppId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  oppId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int oppId) throws SQLException {
    if (oppId == -1) {
      throw new SQLException("Opportunity Header ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT oh.*, org.name as acct_name, org.enabled as accountenabled, " +
        "ct.namelast as last_name, ct.namefirst as first_name, " +
        "ct.company as ctcompany, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst " +
        "FROM opportunity_header oh " +
        "LEFT JOIN contact ct_eb ON (oh.enteredby = ct_eb.user_id) " +
        "LEFT JOIN organization org ON (oh.acctlink = org.org_id) " +
        "LEFT JOIN contact ct ON (oh.contactlink = ct.contact_id) " +
        "LEFT JOIN contact ct_mb ON (oh.modifiedby = ct_mb.user_id) " +
        "WHERE opp_id = ? ");
    pst.setInt(1, oppId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);

      if (buildComponentCount) {
        this.retrieveComponentCount(db);
      }
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Opportunity Header record not found.");
    }
    rs.close();
    pst.close();
    this.buildFiles(db);
  }


  /**
   *  Constructor for the OpportunityHeader object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OpportunityHeader(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *  Gets the componentCount attribute of the OpportunityHeader object
   *
   *@return    The componentCount value
   */
  public int getComponentCount() {
    return componentCount;
  }


  /**
   *  Sets the componentCount attribute of the OpportunityHeader object
   *
   *@param  componentCount  The new componentCount value
   */
  public void setComponentCount(int componentCount) {
    this.componentCount = componentCount;
  }


  /**
   *  Sets the componentCount attribute of the OpportunityHeader object
   *
   *@param  componentCount  The new componentCount value
   */
  public void setComponentCount(String componentCount) {
    this.componentCount = Integer.parseInt(componentCount);
  }


  /**
   *  Gets the totalValue attribute of the OpportunityHeader object
   *
   *@return    The totalValue value
   */
  public double getTotalValue() {
    return totalValue;
  }


  /**
   *  Sets the totalValue attribute of the OpportunityHeader object
   *
   *@param  totalValue  The new totalValue value
   */
  public void setTotalValue(double totalValue) {
    this.totalValue = totalValue;
  }


  /**
   *  Gets the shortDescription attribute of the OpportunityHeader object
   *
   *@return    The shortDescription value
   */
  public String getShortDescription() {
    if (description.length() <= 40) {
      return description;
    } else {
      return description.substring(0, 40) + "...";
    }
  }


  /**
   *  Gets the totalValueCurrency attribute of the OpportunityHeader object
   *
   *@return    The totalValueCurrency value
   */
  public String getTotalValueCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(totalValue);
    return amountOut;
  }


  /**
   *  Gets the oppId attribute of the OpportunityHeader object
   *
   *@return    The oppId value
   */
  public int getOppId() {
    return oppId;
  }


  /**
   *  Gets the id attribute of the OpportunityHeader object
   *
   *@return    The id value
   */
  public int getId() {
    return oppId;
  }


  /**
   *  Gets the description attribute of the OpportunityHeader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the accountLink attribute of the OpportunityHeader object
   *
   *@return    The accountLink value
   */
  public int getAccountLink() {
    return accountLink;
  }


  /**
   *  Gets the contactLink attribute of the OpportunityHeader object
   *
   *@return    The contactLink value
   */
  public int getContactLink() {
    return contactLink;
  }


  /**
   *  Gets the accountName attribute of the OpportunityHeader object
   *
   *@return    The accountName value
   */
  public String getAccountName() {
    if (accountName != null) {
      return accountName;
    } else if (contactName != null && !contactName.trim().equals("")) {
      return this.getContactName();
    } else {
      return this.getContactCompanyName();
    }
  }


  /**
   *  Gets the contactName attribute of the OpportunityHeader object
   *
   *@return    The contactName value
   */
  public String getContactName() {
    return contactName;
  }


  /**
   *  Gets the contactCompanyName attribute of the OpportunityHeader object
   *
   *@return    The contactCompanyName value
   */
  public String getContactCompanyName() {
    return contactCompanyName;
  }


  /**
   *  Gets the enteredByName attribute of the OpportunityHeader object
   *
   *@return    The enteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the modifiedByName attribute of the OpportunityHeader object
   *
   *@return    The modifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the accountEnabled attribute of the OpportunityHeader object
   *
   *@return    The accountEnabled value
   */
  public boolean getAccountEnabled() {
    return accountEnabled;
  }


  /**
   *  Gets the entered attribute of the OpportunityHeader object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the OpportunityHeader object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the oppId attribute of the OpportunityHeader object
   *
   *@param  tmp  The new oppId value
   */
  public void setOppId(int tmp) {
    this.oppId = tmp;
  }


  /**
   *  Sets the oppId attribute of the OpportunityHeader object
   *
   *@param  tmp  The new oppId value
   */
  public void setOppId(String tmp) {
    this.oppId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the OpportunityHeader object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the accountLink attribute of the OpportunityHeader object
   *
   *@param  tmp  The new accountLink value
   */
  public void setAccountLink(int tmp) {
    this.accountLink = tmp;
  }


  /**
   *  Sets the contactLink attribute of the OpportunityHeader object
   *
   *@param  tmp  The new contactLink value
   */
  public void setContactLink(int tmp) {
    this.contactLink = tmp;
  }


  /**
   *  Sets the accountLink attribute of the OpportunityHeader object
   *
   *@param  tmp  The new accountLink value
   */
  public void setAccountLink(String tmp) {
    this.accountLink = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactLink attribute of the OpportunityHeader object
   *
   *@param  tmp  The new contactLink value
   */
  public void setContactLink(String tmp) {
    this.contactLink = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildComponentCount attribute of the OpportunityHeader object
   *
   *@return    The buildComponentCount value
   */
  public boolean getBuildComponentCount() {
    return buildComponentCount;
  }


  /**
   *  Sets the buildComponentCount attribute of the OpportunityHeader object
   *
   *@param  buildComponentCount  The new buildComponentCount value
   */
  public void setBuildComponentCount(boolean buildComponentCount) {
    this.buildComponentCount = buildComponentCount;
  }


  /**
   *  Gets the enteredString attribute of the OpportunityHeader object
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
   *  Gets the modifiedString attribute of the OpportunityHeader object
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildFiles(Connection db) throws SQLException {
    files.clear();
    files.setLinkModuleId(Constants.PIPELINE);
    files.setLinkItemId(this.getId());
    files.buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasFiles() {
    return (files != null && files.size() > 0);
  }


  /**
   *  Sets the accountName attribute of the OpportunityHeader object
   *
   *@param  tmp  The new accountName value
   */
  public void setAccountName(String tmp) {
    this.accountName = tmp;
  }


  /**
   *  Sets the contactName attribute of the OpportunityHeader object
   *
   *@param  tmp  The new contactName value
   */
  public void setContactName(String tmp) {
    this.contactName = tmp;
  }


  /**
   *  Sets the contactCompanyName attribute of the OpportunityHeader object
   *
   *@param  tmp  The new contactCompanyName value
   */
  public void setContactCompanyName(String tmp) {
    this.contactCompanyName = tmp;
  }


  /**
   *  Sets the enteredByName attribute of the OpportunityHeader object
   *
   *@param  tmp  The new enteredByName value
   */
  public void setEnteredByName(String tmp) {
    this.enteredByName = tmp;
  }


  /**
   *  Sets the modifiedByName attribute of the OpportunityHeader object
   *
   *@param  tmp  The new modifiedByName value
   */
  public void setModifiedByName(String tmp) {
    this.modifiedByName = tmp;
  }


  /**
   *  Sets the accountEnabled attribute of the OpportunityHeader object
   *
   *@param  tmp  The new accountEnabled value
   */
  public void setAccountEnabled(boolean tmp) {
    this.accountEnabled = tmp;
  }


  /**
   *  Sets the entered attribute of the OpportunityHeader object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the OpportunityHeader object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the OpportunityHeader object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the OpportunityHeader object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the OpportunityHeader object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the OpportunityHeader object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the OpportunityHeader object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the OpportunityHeader object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the OpportunityHeader object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the OpportunityHeader object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the files attribute of the OpportunityHeader object
   *
   *@return    The files value
   */
  public FileItemList getFiles() {
    return files;
  }


  /**
   *  Sets the files attribute of the OpportunityHeader object
   *
   *@param  files  The new files value
   */
  public void setFiles(FileItemList files) {
    this.files = files;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    if (insert(db)) {
      //invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db, ActionContext context) throws SQLException {
    if (delete(db)) {
      //invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (delete(db, baseFilePath)) {
      //invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getOppId() == -1) {
      throw new SQLException("Opportunity Header ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE opportunity_component set enabled = " + DatabaseUtils.getFalse(db) + " " +
        "WHERE opp_id = ? ");

    //sql.append("AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, oppId);

    //pst.setTimestamp(++i, this.getModified());

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Gets the Valid attribute of the Opportunity object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean isValid(Connection db) throws SQLException {
    if (description == null || description.trim().equals("")) {
      errors.put("descriptionError", "Description cannot be left blank");
    }

    if (hasErrors()) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Opportunity Header-> Cannot insert: object is not valid");
      }
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
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    if (this.getAccountLink() == -1 && this.getContactLink() == -1) {
      throw new SQLException("You must associate an Opportunity Header with an account or contact.");
    }
    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO opportunity_header " +
          "(acctlink, contactlink, description, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (accountLink > -1) {
        pst.setInt(++i, this.getAccountLink());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (contactLink > -1) {
        pst.setInt(++i, this.getContactLink());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setString(++i, this.getDescription());
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();

      oppId = DatabaseUtils.getCurrVal(db, "opportunity_header_opp_id_seq");
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;

    if (this.getOppId() == -1) {
      throw new SQLException("Opportunity Header ID was not specified");
    }

    if (!isValid(db)) {
      return -1;
    }

    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    DependencyList dependencyList = new DependencyList();

    try {
      db.setAutoCommit(false);
      String sql = "SELECT COUNT(*) as callcount FROM call_log c WHERE c.opp_id = ? ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Calls");
        thisDependency.setCount(rs.getInt("callcount"));
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }

      sql = "SELECT COUNT(*) as documentcount FROM project_files pf WHERE pf.link_module_id = ? and pf.link_item_id = ? ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, Constants.DOCUMENTS_OPPORTUNITIES);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Documents");
        thisDependency.setCount(rs.getInt("documentcount"));
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }

      sql = "SELECT COUNT(*) as componentcount FROM opportunity_component oc WHERE oc.opp_id = ? ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Components");
        thisDependency.setCount(rs.getInt("componentcount"));
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }

      pst.close();
      db.commit();

    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean delete(Connection db) throws SQLException {
    if (this.getOppId() == -1) {
      throw new SQLException("The Opportunity Header could not be found.");
    }

    Statement st = null;

    try {
      db.setAutoCommit(false);

      st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM opportunity_header WHERE opp_id = " + this.getOppId());
      st.close();

      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (oppId == -1) {
      throw new SQLException("Opportunity ID not specified");
    }
    String sql = "DELETE FROM opportunity_component_levels WHERE opp_id in (SELECT id from opportunity_component oc where oc.opp_id = ?) ";
    System.out.println(sql.toString());
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("The Opportunity Record could not be found.");
    }

    Statement st = null;

    try {
      db.setAutoCommit(false);
      this.resetType(db);

      CallList callList = new CallList();
      callList.setOppId(this.getId());
      callList.buildList(db);
      callList.delete(db);
      callList = null;

      FileItemList fileList = new FileItemList();
      fileList.setLinkModuleId(Constants.PIPELINE);
      fileList.setLinkItemId(this.getId());
      fileList.buildList(db);
      fileList.delete(db, baseFilePath);
      fileList = null;

      //delete components
      st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM opportunity_component WHERE opp_id = " + this.getId());
      st.close();

      st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM opportunity_header WHERE opp_id = " + this.getId());
      st.close();

      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }



  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setOppId(rs.getInt("opp_id"));
    description = rs.getString("description");
    accountLink = rs.getInt("acctLink");
    if (rs.wasNull()) {
      accountLink = -1;
    }
    contactLink = rs.getInt("contactLink");
    if (rs.wasNull()) {
      contactLink = -1;
    }
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    //table
    accountName = rs.getString("acct_name");
    accountEnabled = rs.getBoolean("accountenabled");

    //contact table
    String contactNameLast = rs.getString("last_name");
    String contactNameFirst = rs.getString("first_name");
    contactName = Contact.getNameFirstLast(contactNameFirst, contactNameLast);
    contactCompanyName = rs.getString("ctcompany");
    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  override          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity Header-> Updating the opportunity header");
    }
    sql.append(
        "UPDATE opportunity_header " +
        "SET description = ?, acctlink = ?, contactlink = ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("modifiedby = ? ");


    sql.append("WHERE opp_id = ? ");

    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    if (accountLink > -1) {
      pst.setInt(++i, this.getAccountLink());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (contactLink > -1) {
      pst.setInt(++i, this.getContactLink());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getOppId());
    if (!override) {
      pst.setTimestamp(++i, modified);
    }

    resultCount = pst.executeUpdate();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity Header-> ResultCount: " + resultCount);
    }
    pst.close();

    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity Header-> Closing PreparedStatement");
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void retrieveComponentCount(Connection db) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as componentcount " +
        "FROM opportunity_component oc " +
        "WHERE id > 0 ");
    sql.append("AND oc.opp_id = ?");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, oppId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("componentcount");
    }
    rs.close();
    pst.close();
    this.setComponentCount(count);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildTotal(Connection db) throws SQLException {
    double total = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT sum(guessvalue) as total " +
        "FROM opportunity_component oc " +
        "WHERE id > 0 ");
    sql.append("AND oc.opp_id = ?");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, oppId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      total = rs.getDouble("total");
    }
    rs.close();
    pst.close();
    this.setTotalValue(total);
  }

}


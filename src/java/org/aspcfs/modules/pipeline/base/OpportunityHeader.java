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
 *  OpportunityHeader: A header related to an Opportunity ( = OpportunityHeader + OpportunityComponent(s))
 *
 *@author     chris
 *@created    September 13, 2001
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
  
  public OpportunityHeader() { }

  public OpportunityHeader(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public OpportunityHeader(Connection db, int oppId) throws SQLException {
    queryRecord(db, oppId);
  }
  
  public void queryRecord(Connection db, int oppId) throws SQLException {
    if (oppId == -1) {
      throw new SQLException("Opportunity Header ID not specified.");
    }
    PreparedStatement pst=db.prepareStatement(
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
  
  public OpportunityHeader(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }

  public int getComponentCount() {
    return componentCount;
  }
  public void setComponentCount(int componentCount) {
    this.componentCount = componentCount;
  }
  
  public void setComponentCount(String componentCount) {
    this.componentCount = Integer.parseInt(componentCount);
  }
  
  public double getTotalValue() {
    return totalValue;
  }
  public void setTotalValue(double totalValue) {
    this.totalValue = totalValue;
  }
  
  public String getShortDescription() {
    if (description.length() <= 40) {
      return description;
    } else {
      return description.substring(0, 40) + "...";
    }
  }  
  
  public String getTotalValueCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(totalValue);
    return amountOut;
  }  
  
  public int getOppId() { return oppId; }
  public int getId() { return oppId; }
  public String getDescription() { return description; }
  public int getAccountLink() { return accountLink; }
  public int getContactLink() { return contactLink; }
  
  public String getAccountName() {
    if (accountName != null) {
      return accountName;
    } else if (contactName != null && !contactName.trim().equals("")) {
      return this.getContactName();
    } else {
      return this.getContactCompanyName();
    }
  }  
  
  public String getContactName() { return contactName; }
  public String getContactCompanyName() { return contactCompanyName; }
  public String getEnteredByName() { return enteredByName; }
  public String getModifiedByName() { return modifiedByName; }
  public boolean getAccountEnabled() { return accountEnabled; }
  public java.sql.Timestamp getEntered() { return entered; }
  public java.sql.Timestamp getModified() { return modified; }
  
  public void setOppId(int tmp) { this.oppId = tmp; }
  public void setOppId(String tmp) { this.oppId = Integer.parseInt(tmp); }
  
  public void setDescription(String tmp) { this.description = tmp; }
  
  public void setAccountLink(int tmp) { this.accountLink = tmp; }
  public void setContactLink(int tmp) { this.contactLink = tmp; }
  public void setAccountLink(String tmp) { this.accountLink = Integer.parseInt(tmp); }
  public void setContactLink(String tmp) { this.contactLink = Integer.parseInt(tmp); }  
  
  public boolean getBuildComponentCount() {
    return buildComponentCount;
  }
  public void setBuildComponentCount(boolean buildComponentCount) {
    this.buildComponentCount = buildComponentCount;
  }

  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }

  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  
  public void buildFiles(Connection db) throws SQLException {
    files.clear();
    files.setLinkModuleId(Constants.PIPELINE);
    files.setLinkItemId(this.getId());
    files.buildList(db);
  }  
  
  public boolean hasFiles() {
    return (files != null && files.size() > 0);
  }   
  
  public void setAccountName(String tmp) { this.accountName = tmp; }
  public void setContactName(String tmp) { this.contactName = tmp; }
  public void setContactCompanyName(String tmp) { this.contactCompanyName = tmp; }
  public void setEnteredByName(String tmp) { this.enteredByName = tmp; }
  public void setModifiedByName(String tmp) { this.modifiedByName = tmp; }
  public void setAccountEnabled(boolean tmp) { this.accountEnabled = tmp; }
  
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }

  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }

  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }
  public void setModifiedBy(String tmp) { this.modifiedBy = Integer.parseInt(tmp); }  
  public int getEnteredBy() { return enteredBy; }
  public int getModifiedBy() { return modifiedBy; }

  public FileItemList getFiles() {
    return files;
  }
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
  
  public HashMap processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = "";
    HashMap dependencyList = new HashMap();

    try {
      db.setAutoCommit(false);
      sql = "SELECT COUNT(*) as callcount FROM call_log c WHERE c.opp_id = ? ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        dependencyList.put("Calls", new Integer(rs.getInt("callcount")));
      }

      sql = "SELECT COUNT(*) as documentcount FROM project_files pf WHERE pf.link_module_id = ? and pf.link_item_id = ? ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, Constants.PIPELINE);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        dependencyList.put("Documents", new Integer(rs.getInt("documentcount")));
      }
      
      sql = "SELECT COUNT(*) as componentcount FROM opportunity_component oc WHERE oc.opp_id = ? ";

      i = 0;
      pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        dependencyList.put("Components", new Integer(rs.getInt("componentcount")));
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
  
  public boolean resetType(Connection db) throws SQLException {
    if (oppId == -1) {
      throw new SQLException("Opportunity ID not specified");
    }
    String sql = "DELETE FROM opportunity_component_levels WHERE opp_id in (SELECT id from opportunity_component oc where oc.opp_id = ?) ";
    System.out.println(sql.toString());
    int i=0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }   
  
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

    /**
    if (this.getCloseIt() == true) {
      sql.append(
          ", closed = CURRENT_TIMESTAMP ");
    } else if (this.getOpenIt() == true) {
      sql.append(
          ", closed = ? ");
    }
    */
    
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


//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.pipeline.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.*;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.modules.base.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.contacts.base.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    September 13, 2001
 *@version    $Id$
 */
public class Opportunity extends OpportunityComponent {
  private int id = -1;
  private String description = "";
  private int accountLink = -1;
  private int contactLink = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;

  //private boolean enabled = true;

  private String accountName = "";
  private String contactName = "";
  private String contactCompanyName = "";
  private String enteredByName = "";
  private String modifiedByName = "";
  private boolean accountEnabled = true;
  private boolean callsDelete = false;
  private boolean documentDelete = false;
  private FileItemList files = new FileItemList();
  private int componentCount = 0;
  private boolean buildComponentCount = false;

  private String componentDescription = null;
  private int componentId = -1;
  private double totalValue = 0;


  /**
   *  Constructor for the Opportunity object
   *
   *@since    1.1
   */
  public Opportunity() { }


  /**
   *  Constructor for the Contact object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public Opportunity(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Opportunity object
   *
   *@param  db                Description of the Parameter
   *@param  oppId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Opportunity(Connection db, int oppId) throws SQLException {
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
      throw new SQLException("Valid opportunity ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT x.opp_id AS header_opp_id, " +
        "x.description AS header_description, " +
        "x.acctlink AS header_acctlink, " +
        "x.contactlink AS header_contactlink, " +
        "x.entered AS header_entered, " +
        "x.enteredby AS header_enteredby, " +
        "x.modified AS header_modified, " +
        "x.modifiedby AS header_modifiedby, " +
        "oc.*, y.description AS stagename, " +
        "ct_owner.namelast AS o_namelast, ct_owner.namefirst AS o_namefirst, " +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst, " +
        "ct_mb.namelast AS mb_namelast, ct_mb.namefirst AS mb_namefirst, " +
        "org.name AS acct_name, org.enabled AS accountenabled, " +
        "ct.namelast AS last_name, ct.namefirst AS first_name, " +
        "ct.company AS ctcompany, " +
        "oc.description AS comp_desc, oc.id AS comp_id " +
        "FROM opportunity_header x " +
        "LEFT JOIN opportunity_component oc ON (x.opp_id = oc.opp_id) " +
        "LEFT JOIN organization org ON (x.acctlink = org.org_id) " +
        "LEFT JOIN contact ct_eb ON (x.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (x.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_owner ON (oc.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct ON (x.contactlink = ct.contact_id), " +
        "lookup_stage y " +
        "WHERE y.code = oc.stage " +
        "AND x.opp_id = ? ");
    pst.setInt(1, oppId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
      buildTypes(db);
      if (buildComponentCount) {
        this.retrieveComponentCount(db);
      }
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Opportunity record not found.");
    }
    rs.close();
    pst.close();

    this.buildFiles(db);
  }


  /**
   *  Constructor for the Employee object, populates all attributes by
   *  performing a SQL query based on the employeeId parameter
   *
   *@param  db                Description of Parameter
   *@param  oppId             Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public Opportunity(Connection db, String oppId) throws SQLException {
    queryRecord(db, Integer.parseInt(oppId));
  }


  /**
   *  Sets the componentDescription attribute of the Opportunity object
   *
   *@param  tmp  The new componentDescription value
   */
  public void setComponentDescription(String tmp) {
    this.componentDescription = tmp;
  }


  /**
   *  Sets the componentId attribute of the Opportunity object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   *  Sets the componentId attribute of the Opportunity object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the componentDescription attribute of the Opportunity object
   *
   *@return    The componentDescription value
   */
  public String getComponentDescription() {
    return componentDescription;
  }


  /**
   *  Gets the componentId attribute of the Opportunity object
   *
   *@return    The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *  Sets the EnteredByName attribute of the Opportunity object
   *
   *@param  enteredByName  The new EnteredByName value
   *@since
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the typeList attribute of the Opportunity object
   *
   *@param  typeList  The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Sets the typeList attribute of the Opportunity object
   *
   *@param  criteriaString  The new typeList value
   */
  public void setTypeList(String[] criteriaString) {
    if (criteriaString != null) {
      String[] params = criteriaString;
      typeList = new ArrayList(Arrays.asList(params));
    } else {
      typeList = new ArrayList();
    }

    this.typeList = typeList;
  }


  /**
   *  Gets the typeList attribute of the Opportunity object
   *
   *@return    The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the alertText attribute of the Opportunity object
   *
   *@return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Sets the alertText attribute of the Opportunity object
   *
   *@param  alertText  The new alertText value
   */
  public void setAlertText(String alertText) {
    this.alertText = alertText;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Opportunity object
   *
   *@param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Opportunity object
   *
   *@return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Sets the OpenIt attribute of the Opportunity object
   *
   *@param  openIt  The new OpenIt value
   *@since
   */
  public void setOpenIt(boolean openIt) {
    this.openIt = openIt;
  }


  /**
   *  Sets the types attribute of the Opportunity object
   *
   *@param  types  The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Gets the types attribute of the Opportunity object
   *
   *@return    The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Sets the alertDate attribute of the Opportunity object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Date tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Opportunity object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Gets the notes attribute of the Opportunity object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Sets the notes attribute of the Opportunity object
   *
   *@param  notes  The new notes value
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }


  /**
   *  Gets the accountEnabled attribute of the Opportunity object
   *
   *@return    The accountEnabled value
   */
  public boolean getAccountEnabled() {
    return accountEnabled;
  }


  /**
   *  Sets the accountEnabled attribute of the Opportunity object
   *
   *@param  accountEnabled  The new accountEnabled value
   */
  public void setAccountEnabled(boolean accountEnabled) {
    this.accountEnabled = accountEnabled;
  }


  /**
   *  Sets the closeDate attribute of the Opportunity object
   *
   *@param  tmp  The new closeDate value
   */
  public void setCloseDate(java.sql.Date tmp) {
    this.closeDate = tmp;
  }


  /**
   *  Sets the stageDate attribute of the Opportunity object
   *
   *@param  tmp  The new stageDate value
   */
  public void setStageDate(java.sql.Date tmp) {
    this.stageDate = tmp;
  }


  /**
   *  Gets the callsDelete attribute of the Opportunity object
   *
   *@return    The callsDelete value
   */
  public boolean getCallsDelete() {
    return callsDelete;
  }


  /**
   *  Gets the totalValue attribute of the Opportunity object
   *
   *@return    The totalValue value
   */
  public double getTotalValue() {
    return totalValue;
  }


  /**
   *  Sets the totalValue attribute of the Opportunity object
   *
   *@param  totalValue  The new totalValue value
   */
  public void setTotalValue(double totalValue) {
    this.totalValue = totalValue;
  }


  /**
   *  Gets the totalValue attribute of the Opportunity object
   *
   *@param  divisor  Description of the Parameter
   *@return          The totalValue value
   */
  public String getTotalValue(int divisor) {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    double tempValue = (java.lang.Math.round(totalValue) / divisor);
    String amountOut = "";

    if (tempValue < 1) {
      amountOut = "<1";
    } else {
      amountOut = numberFormatter.format(tempValue);
    }

    return amountOut;
  }


  /**
   *  Gets the documentDelete attribute of the Opportunity object
   *
   *@return    The documentDelete value
   */
  public boolean getDocumentDelete() {
    return documentDelete;
  }


  /**
   *  Sets the callsDelete attribute of the Opportunity object
   *
   *@param  tmp  The new callsDelete value
   */
  public void setCallsDelete(boolean tmp) {
    this.callsDelete = tmp;
  }


  /**
   *  Sets the documentDelete attribute of the Opportunity object
   *
   *@param  tmp  The new documentDelete value
   */
  public void setDocumentDelete(boolean tmp) {
    this.documentDelete = tmp;
  }


  /**
   *  Sets the enabled attribute of the Opportunity object
   *
   *@param  enabled  The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   *  Sets the enabled attribute of the Opportunity object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the closeDate attribute of the Opportunity object
   *
   *@param  tmp  The new closeDate value
   */
  public void setCloseDate(String tmp) {
    this.closeDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the stageDate attribute of the Opportunity object
   *
   *@param  tmp  The new stageDate value
   */
  public void setStageDate(String tmp) {
    this.stageDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the accountName attribute of the Opportunity object
   *
   *@param  accountName  The new accountName value
   */
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }


  /**
   *  Sets the ContactCompanyName attribute of the Opportunity object
   *
   *@param  contactCompanyName  The new ContactCompanyName value
   *@since
   */
  public void setContactCompanyName(String contactCompanyName) {
    this.contactCompanyName = contactCompanyName;
  }


  /**
   *  Sets the Entered attribute of the Opportunity object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the Modified attribute of the Opportunity object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Opportunity object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Opportunity object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Owner attribute of the Opportunity object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the StageChange attribute of the Opportunity object
   *
   *@param  stageChange  The new StageChange value
   *@since
   */
  public void setStageChange(boolean stageChange) {
    this.stageChange = stageChange;
  }


  /**
   *  Sets the Closed attribute of the Opportunity object
   *
   *@param  closed  The new Closed value
   *@since
   */
  public void setClosed(String closed) {
    this.closed = closed;
  }


  /**
   *  Sets the Owner attribute of the Opportunity object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the ContactName attribute of the Opportunity object
   *
   *@param  contactName  The new ContactName value
   *@since
   */
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }


  /**
   *  Sets the Id attribute of the Opportunity object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the Opportunity object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the componentCount attribute of the Opportunity object
   *
   *@return    The componentCount value
   */
  public int getComponentCount() {
    return componentCount;
  }


  /**
   *  Gets the buildComponentCount attribute of the Opportunity object
   *
   *@return    The buildComponentCount value
   */
  public boolean getBuildComponentCount() {
    return buildComponentCount;
  }


  /**
   *  Sets the componentCount attribute of the Opportunity object
   *
   *@param  tmp  The new componentCount value
   */
  public void setComponentCount(int tmp) {
    this.componentCount = tmp;
  }


  /**
   *  Sets the buildComponentCount attribute of the Opportunity object
   *
   *@param  tmp  The new buildComponentCount value
   */
  public void setBuildComponentCount(boolean tmp) {
    this.buildComponentCount = tmp;
  }


  /**
   *  Sets the StageName attribute of the Opportunity object
   *
   *@param  stageName  The new StageName value
   *@since
   */
  public void setStageName(String stageName) {
    this.stageName = stageName;
  }


  /**
   *  Sets the Terms attribute of the Opportunity object
   *
   *@param  terms  The new Terms value
   *@since
   */
  public void setTerms(String terms) {
    try {
      this.terms = Double.parseDouble(terms);
    } catch (NumberFormatException ne) {
      errors.put("termsError", terms + " is invalid input for this field");
    }

  }


  /**
   *  Sets the ModifiedBy attribute of the Opportunity object
   *
   *@param  modifiedBy  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the Opportunity object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the EnteredBy attribute of the Opportunity object
   *
   *@param  enteredBy  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the Opportunity object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Description attribute of the Opportunity object
   *
   *@param  description  The new Description value
   *@since
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   *  Sets the Low attribute of the Opportunity object
   *
   *@param  low  The new Low value
   *@since
   */
  public void setLow(String low) {
    low = StringUtils.replace(low, ",", "");
    low = StringUtils.replace(low, "$", "");

    this.low = Double.parseDouble(low);
  }


  /**
   *  Sets the Guess attribute of the Opportunity object
   *
   *@param  guess  The new Guess value
   *@since
   */
  public void setGuess(String guess) {
    guess = StringUtils.replace(guess, ",", "");
    guess = StringUtils.replace(guess, "$", "");

    try {
      this.guess = Double.parseDouble(guess);
    } catch (NumberFormatException ne) {
      errors.put("guessError", guess + " is invalid input for this field");
    }
  }


  /**
   *  Sets the High attribute of the Opportunity object
   *
   *@param  high  The new High value
   *@since
   */
  public void setHigh(String high) {
    high = StringUtils.replace(high, ",", "");
    high = StringUtils.replace(high, "$", "");

    this.high = Double.parseDouble(high);
  }


  /**
   *  Sets the ModifiedByName attribute of the Opportunity object
   *
   *@param  modifiedByName  The new ModifiedByName value
   *@since
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the Type attribute of the Opportunity object
   *
   *@param  type  The new Type value
   *@since
   */
  public void setType(String type) {
    this.type = type;
  }


  /**
   *  Sets the Units attribute of the Opportunity object
   *
   *@param  units  The new Units value
   *@since
   */
  public void setUnits(String units) {
    this.units = units;
  }


  /**
   *  Sets the files attribute of the Opportunity object
   *
   *@param  files  The new files value
   */
  public void setFiles(FileItemList files) {
    this.files = files;
  }


  /**
   *  Gets the files attribute of the Opportunity object
   *
   *@return    The files value
   */
  public FileItemList getFiles() {
    return files;
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
   *  Sets the CloseProb attribute of the Opportunity object
   *
   *@param  closeProb  The new CloseProb value
   *@since
   */
  public void setCloseProb(String closeProb) {
    if (closeProb != null && closeProb.endsWith("%")) {
      closeProb = closeProb.substring(0, closeProb.length() - 1);
    }

    try {
      this.closeProb = ((Double.parseDouble(closeProb)) / 100);
    } catch (NumberFormatException ne) {
      errors.put("closeProbError", closeProb + " is invalid input for this field");
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity-> Close prob: " + closeProb);
    }
  }


  /**
   *  Sets the Commission attribute of the Opportunity object
   *
   *@param  commission  The new Commission value
   *@since
   */
  public void setCommission(String commission) {
    if (commission != null && commission.endsWith("%")) {
      commission = commission.substring(0, commission.length() - 1);
    }
    this.commission = ((Double.parseDouble(commission)) / 100);
  }


  /**
   *  Sets the ContactLink attribute of the Opportunity object
   *
   *@param  tmp  The new contactLink value
   *@since
   */
  public void setContactLink(String tmp) {
    this.contactLink = Integer.parseInt(tmp);
  }


  /**
   *  Sets the AccountLink attribute of the Opportunity object
   *
   *@param  tmp  The new accountLink value
   *@since
   */
  public void setAccountLink(String tmp) {
    this.accountLink = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Stage attribute of the Opportunity object
   *
   *@param  stage  The new Stage value
   *@since
   */
  public void setStage(String stage) {
    this.stage = Integer.parseInt(stage);
  }


  /**
   *  Sets the CloseIt attribute of the Opportunity object
   *
   *@param  closeIt  The new CloseIt value
   *@since
   */
  public void setCloseIt(boolean closeIt) {
    this.closeIt = closeIt;
  }


  /**
   *  Sets the closeNow attribute of the Opportunity object
   *
   *@param  tmp  The new closeNow value
   */
  public void setCloseNow(String tmp) {
    this.closeIt = ("ON".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the alertDate attribute of the Opportunity object
   *
   *@return    The alertDate value
   */
  public java.sql.Date getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the closeDate attribute of the Opportunity object
   *
   *@return    The closeDate value
   */
  public java.sql.Date getCloseDate() {
    return closeDate;
  }


  /**
   *  Gets the stageDate attribute of the Opportunity object
   *
   *@return    The stageDate value
   */
  public java.sql.Date getStageDate() {
    return stageDate;
  }


  /**
   *  Gets the alertDateString attribute of the Opportunity object
   *
   *@return    The alertDateString value
   */
  public String getAlertDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the Opportunity object
   *
   *@return    The alertDateStringLongYear value
   */
  public String getAlertDateStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the closeDateString attribute of the Opportunity object
   *
   *@return    The closeDateString value
   */
  public String getCloseDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(closeDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the stageDateString attribute of the Opportunity object
   *
   *@return    The stageDateString value
   */
  public String getStageDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(stageDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the OpenIt attribute of the Opportunity object
   *
   *@return    The OpenIt value
   *@since
   */
  public boolean getOpenIt() {
    return openIt;
  }


  /**
   *  Gets the ContactCompanyName attribute of the Opportunity object
   *
   *@return    The ContactCompanyName value
   *@since
   */
  public String getContactCompanyName() {
    return contactCompanyName;
  }


  /**
   *  Gets the Entered attribute of the Opportunity object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Opportunity object
   *
   *@return    The Modified value
   *@since
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Opportunity object
   *
   *@return    The ModifiedString value
   *@since
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
   *  Gets the EnteredString attribute of the Opportunity object
   *
   *@return    The EnteredString value
   *@since
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getOwner() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getOwner());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setHasEnabledOwnerAccount(true);
    } else {
      this.setHasEnabledOwnerAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the Closed attribute of the Opportunity object
   *
   *@return    The Closed value
   *@since
   */
  public String getClosed() {
    return closed;
  }


  /**
   *  Gets the StageChange attribute of the Opportunity object
   *
   *@return    The StageChange value
   *@since
   */
  public boolean getStageChange() {
    return stageChange;
  }


  /**
   *  Gets the ModifiedByName attribute of the Opportunity object
   *
   *@return    The ModifiedByName value
   *@since
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the EnteredByName attribute of the Opportunity object
   *
   *@return    The EnteredByName value
   *@since
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the OwnerName attribute of the Opportunity object
   *
   *@return    The OwnerName value
   *@since
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the ContactName attribute of the Opportunity object
   *
   *@return    The ContactName value
   *@since
   */
  public String getContactName() {
    return contactName;
  }


  /**
   *  Gets the Units attribute of the Opportunity object
   *
   *@return    The Units value
   *@since
   */
  public String getUnits() {
    return units;
  }


  /**
   *  Gets the Terms attribute of the Opportunity object
   *
   *@return    The Terms value
   *@since
   */
  public double getTerms() {
    return terms;
  }


  /**
   *  Gets the TermsString attribute of the Opportunity object
   *
   *@return    The TermsString value
   *@since
   */
  public String getTermsString() {
    Double tmp = new Double(round(terms, 2));
    String toReturn = "" + tmp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the StageName attribute of the Opportunity object
   *
   *@return    The StageName value
   *@since
   */
  public String getStageName() {
    if (this.getClosed() != null) {
      this.setStageName("Closed");
    }

    return stageName;
  }


  /**
   *  Gets the Type attribute of the Opportunity object
   *
   *@return    The Type value
   *@since
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the Id attribute of the Opportunity object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Owner attribute of the Opportunity object
   *
   *@return    The Owner value
   *@since
   */
  public int getOwner() {
    return owner;
  }



  /**
   *  Gets the Stage attribute of the Opportunity object
   *
   *@return    The Stage value
   *@since
   */
  public int getStage() {
    return stage;
  }


  /**
   *  Gets the ModifiedBy attribute of the Opportunity object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the CloseIt attribute of the Opportunity object
   *
   *@return    The CloseIt value
   *@since
   */
  public boolean getCloseIt() {
    return closeIt;
  }


  /**
   *  Gets the EnteredBy attribute of the Opportunity object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Description attribute of the Opportunity object
   *
   *@return    The Description value
   *@since
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Description attribute of the Opportunity object
   *
   *@return    The Description value
   */
  public String getShortDescription() {
    if (description.length() <= 40) {
      return description;
    } else {
      return description.substring(0, 40) + "...";
    }
  }


  /**
   *  Gets the Low attribute of the Opportunity object
   *
   *@return    The Low value
   */
  public double getLow() {
    return low;
  }


  /**
   *  Gets the LowAmount attribute of the Opportunity object
   *
   *@return    The LowAmount value
   *@since
   */
  public String getLowAmount() {
    Double thisAmount = new Double(round(low, 2));
    String toReturn = String.valueOf(thisAmount);
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the LowCurrency attribute of the Opportunity object
   *
   *@return    The LowCurrency value
   *@since
   */
  public String getLowCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(low);
    return amountOut;
  }


  /**
   *  Gets the Guess attribute of the Opportunity object
   *
   *@return    The Guess value
   *@since
   */
  public double getGuess() {
    return guess;
  }


  /**
   *  Gets the GuessAmount attribute of the Opportunity object
   *
   *@return    The GuessAmount value
   *@since
   */
  public String getGuessAmount() {
    Double thisAmount = new Double(round(guess, 2));
    String toReturn = String.valueOf(thisAmount);
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the GuessCurrency attribute of the Opportunity object
   *
   *@return    The GuessCurrency value
   *@since
   */
  public String getGuessCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(guess);
    return amountOut;
  }


  /**
   *  Gets the guessCurrency attribute of the Opportunity object
   *
   *@param  divisor  Description of the Parameter
   *@return          The guessCurrency value
   */
  public String getGuessCurrency(int divisor) {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    double tempValue = (java.lang.Math.round(guess) / divisor);
    String amountOut = "";
    if (tempValue < 1) {
      amountOut = "<1";
    } else {
      amountOut = numberFormatter.format(tempValue);
    }
    return amountOut;
  }


  /**
   *  Gets the High attribute of the Opportunity object
   *
   *@return    The High value
   *@since
   */
  public double getHigh() {
    return high;
  }


  /**
   *  Gets the HighAmount attribute of the Opportunity object
   *
   *@return    The HighAmount value
   *@since
   */
  public String getHighAmount() {
    Double thisAmount = new Double(round(high, 2));
    String toReturn = "" + thisAmount;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the HighCurrency attribute of the Opportunity object
   *
   *@return    The HighCurrency value
   *@since
   */
  public String getHighCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(high);
    return amountOut;
  }


  /**
   *  Gets the ContactLink attribute of the Opportunity object
   *
   *@return    The ContactLink value
   *@since
   */
  public int getContactLink() {
    return contactLink;
  }


  /**
   *  Gets the CloseProb attribute of the Opportunity object
   *
   *@return    The CloseProb value
   */
  public double getCloseProb() {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity-> Close Prob: " + closeProb);
    }
    return closeProb;
  }


  /**
   *  Gets the CloseProbString attribute of the Opportunity object
   *
   *@return    The CloseProbString value
   *@since
   */
  public String getCloseProbString() {
    //String stringOut = (new java.math.BigDecimal("" + closeProb)).toString();
    return "" + closeProb;
  }


  /**
   *  Gets the closeProbValue attribute of the Opportunity object
   *
   *@return    The closeProbValue value
   */
  public String getCloseProbValue() {
    double value_2dp = (double) Math.round(closeProb * 100.0 * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the CloseProbPercent attribute of the Opportunity object
   *
   *@return    The CloseProbPercent value
   *@since
   */
  public String getCloseProbPercent() {
    NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.US);
    String percentOut = percentFormatter.format(closeProb);
    return percentOut;
  }


  /**
   *  Gets the AccountLink attribute of the Opportunity object
   *
   *@return    The AccountLink value
   *@since
   */
  public int getAccountLink() {
    return accountLink;
  }


  /**
   *  Returns the actual name linked to this Opportunity, whether it is an
   *  Account or a Contact
   *
   *@return    The AccountName value
   *@since
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
   *  Gets the Commission attribute of the Opportunity object
   *
   *@return    The Commission value
   *@since
   */
  public double getCommission() {
    return commission;
  }


  /**
   *  Gets the CommissionString attribute of the Opportunity object
   *
   *@return    The CommissionString value
   *@since
   */
  public String getCommissionString() {
    String stringOut = (new java.math.BigDecimal("" + commission)).toString();
    return stringOut;
  }


  /**
   *  Gets the CommissionPercent attribute of the Opportunity object
   *
   *@return    The CommissionPercent value
   *@since
   */
  public String getCommissionPercent() {
    NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.US);
    String percentOut = percentFormatter.format(commission);
    return percentOut;
  }


  /**
   *  Gets the commissionValue attribute of the Opportunity object
   *
   *@return    The commissionValue value
   */
  public String getCommissionValue() {
    double value_2dp = (double) Math.round(commission * 100.0 * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.29
   */
  public boolean hasAccountLink() {
    return (accountLink > -1);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.29
   */
  public boolean hasContactLink() {
    return (contactLink > -1);
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
    boolean recordInserted = insert(db);
    if (recordInserted) {
      invalidateUserData(context);
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

  public int update(Connection db, ActionContext context) throws SQLException {
    int oldId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT owner " +
        "FROM opportunity_component " +
        "WHERE id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      oldId = rs.getInt("owner");
    }
    rs.close();
    pst.close();
    int result = update(db);
    if (result == 1) {
      invalidateUserData(context);
      if (oldId != this.getOwner()) {
        invalidateUserData(context, oldId);
      }
    }
    return result;
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
      invalidateUserData(context);
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
      invalidateUserData(context);
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
    if (this.getId() == -1) {
      throw new SQLException("Opportunity ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE opportunity set enabled = " + DatabaseUtils.getFalse(db) + " " +
        "WHERE opp_id = ? ");

    sql.append("AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, id);

    pst.setTimestamp(++i, this.getModified());

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public String toString() {
    StringBuffer out = new StringBuffer();

    out.append("===========================================\r\n");
    out.append("Id: " + id + "\r\n");
    out.append("Opportunity: " + description + "\r\n");
    out.append("Close Date: " + getCloseDateString() + "\r\n");
    out.append("Stage Date: " + getStageDateString() + "\r\n");
    out.append("Alert Date: " + getAlertDateString() + "\r\n");

    return out.toString();
  }


  /**
   *  Gets the Valid attribute of the Opportunity object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean isValid(Connection db) throws SQLException {
    //errors.clear();

    if (description == null || description.trim().equals("")) {
      errors.put("descriptionError", "Description cannot be left blank");
    }

    if (closeProb == 0 && !(errors.containsKey("closeProbError"))) {
      errors.put("closeProbError", "Close Probability cannot be left blank");
    } else {
      if (closeProb > 100) {
        errors.put("closeProbError", "Close Probability cannot be greater than 100%");
      } else if (closeProb < 0) {
        errors.put("closeProbError", "Close Probability cannot be less than 0%");
      }
    }

    if (closeDate == null || getCloseDateString().trim().equals("")) {
      errors.put("closeDateError", "Close Date cannot be left blank");
    }

    if (guess == 0 && !(errors.containsKey("guessError"))) {
      errors.put("guessError", "Amount needs to be entered");
    }

    if (terms == 0 && !(errors.containsKey("termsError"))) {
      errors.put("termsError", "Terms needs to be entered");
    } else {
      if (terms < 0) {
        errors.put("termsError", "Terms cannot be less than 0");
      }
    }

    if (hasErrors()) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Opportunity-> Cannot insert: object is not valid");
      }
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Inserts this object into the database, and populates this Id. For
   *  maintenance, only the required fields are inserted, then an update is
   *  executed to finish the record.
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    if (this.getAccountLink() == -1 && this.getContactLink() == -1) {
      throw new SQLException("You must associate an Opportunity with an account or contact.");
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

      id = DatabaseUtils.getCurrVal(db, "opportunity_header_opp_id_seq");
      this.insertComponent(db);
      this.update(db, true);
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
  public boolean insertAdditionalComponent(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    try {
      db.setAutoCommit(false);
      this.insertComponent(db);
      this.update(db, true);
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
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertAdditionalComponent(Connection db, ActionContext context) throws SQLException {
    boolean recordInserted = insertAdditionalComponent(db);
    if (recordInserted) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected int update(Connection db) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Opportunity ID was not specified");
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
   *  Delete the current object from the database
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("The Opportunity could not be found.");
    }

    Statement st = null;

    try {
      db.setAutoCommit(false);

      st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM opportunity_component_levels WHERE opp_id in (select id from opportunity_component oc where oc.opp_id = " + this.getId() + " ");
      st.close();

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
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("The Opportunity could not be found.");
    }

    Statement st = null;

    try {
      db.setAutoCommit(false);
      this.resetType(db);

      if (callsDelete) {
        CallList callList = new CallList();
        callList.setOppId(this.getId());
        callList.buildList(db);
        callList.delete(db);
        callList = null;
      }

      if (documentDelete) {
        FileItemList fileList = new FileItemList();
        fileList.setLinkModuleId(Constants.PIPELINE);
        fileList.setLinkItemId(this.getId());
        fileList.buildList(db);
        fileList.delete(db, baseFilePath);
        fileList = null;
      }

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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildTypes(Connection db) throws SQLException {
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT otl.type_id " +
        "FROM opportunity_component_levels otl " +
        "WHERE otl.opp_id = ? ORDER BY otl.level ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    rs = pst.executeQuery();
    while (rs.next()) {
      types.add(new LookupElement(db, rs.getInt("type_id"), "lookup_opportunity_types"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Populates this object from a result set
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //opportunity table
    this.setId(rs.getInt("header_opp_id"));
    description = rs.getString("header_description");
    accountLink = rs.getInt("header_acctLink");
    if (rs.wasNull()) {
      accountLink = -1;
    }
    contactLink = rs.getInt("header_contactLink");
    if (rs.wasNull()) {
      contactLink = -1;
    }
    entered = rs.getTimestamp("header_entered");
    enteredBy = rs.getInt("header_enteredby");
    modified = rs.getTimestamp("header_modified");
    modifiedBy = rs.getInt("header_modifiedby");
    
    //opportunity component table
    super.buildRecord(rs);

    //organization table
    accountName = rs.getString("acct_name");
    if (accountLink > -1) {
      accountEnabled = rs.getBoolean("accountenabled");
    }

    //contact table
    String contactNameLast = rs.getString("last_name");
    String contactNameFirst = rs.getString("first_name");
    contactName = Contact.getNameFirstLast(contactNameFirst, contactNameLast);
    contactCompanyName = rs.getString("ctcompany");
    
    //opportunity_component table (again?)
    componentDescription = rs.getString("comp_desc");
    componentId = rs.getInt("comp_id");
    
    enteredByName = super.getEnteredByName();
    modifiedByName = super.getModifiedByName();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertComponent(Connection db) throws SQLException {
    try {
      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO opportunity_component " +
          "(opp_id, description, owner, closedate, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getId());
      pst.setString(++i, super.getDescription());
      pst.setInt(++i, this.getEnteredBy());
      if (this.getCloseDate() == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setDate(++i, this.getCloseDate());
      }
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
      //id = DatabaseUtils.getCurrVal(db, "opportunity_component_id_seq");
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    return true;
  }


  /**
   *  Update the database with changes to this Opportunity
   *
   *@param  db                Opened database connection
   *@param  override          Set to true on an Insert only
   *@return                   The number of records updated
   *@exception  SQLException  update error
   *@since
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    if (!override) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Opportunity-> Retrieving values from previous Opportunity");
      }
      sql.append(
          "SELECT stage " +
          "FROM opportunity_component " +
          "WHERE id = ? ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int currentStage = rs.getInt("stage");
        if (currentStage != stage || this.getCloseIt()) {
          this.setStageChange(true);
        } else {
          this.setStageChange(false);
        }
      }
      rs.close();
      pst.close();
      sql.setLength(0);
    }

    sql.append(
        "UPDATE opportunity_component " +
        "SET lowvalue = ?, guessvalue = ?, highvalue = ?, closeprob = ?, " +
        "commission = ?, ");

    if ((this.getStageChange() == true && override == false)) {
      sql.append("stagedate = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("type = ?, stage = ?, description = ?, " +
        "alertdate = ?, alert = ?, terms = ?, units = ?, owner = ?, notes = ?, ");

    if (override == false) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("modifiedby = ? ");

    if (this.getCloseIt() == true) {
      sql.append(
          ", closed = CURRENT_TIMESTAMP ");
    } else if (this.getOpenIt() == true) {
      sql.append(
          ", closed = ? ");
    }

    sql.append("WHERE id = ? ");

    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setDouble(++i, this.getLow());
    pst.setDouble(++i, this.getGuess());
    pst.setDouble(++i, this.getHigh());
    pst.setDouble(++i, this.getCloseProb());
    pst.setDouble(++i, this.getCommission());
    pst.setString(++i, this.getType());
    pst.setInt(++i, this.getStage());
    pst.setString(++i, this.getDescription());

    if (this.getAlertDate() == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getAlertDate());
    }

    pst.setString(++i, this.getAlertText());
    pst.setDouble(++i, this.getTerms());
    pst.setString(++i, this.getUnits());
    pst.setInt(++i, this.getOwner());
    pst.setString(++i, this.getNotes());
    pst.setInt(++i, this.getModifiedBy());

    if (this.getOpenIt() == true) {
      pst.setNull(++i, java.sql.Types.DATE);
    }

    pst.setInt(++i, this.getId());

    if (!override) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity-> ResultCount: " + resultCount);
    }
    pst.close();

    //Remove all opp types, add new list
    if (this.getTypeList() != null) {
      resetType(db);
      int lvlcount = 0;
      for (int k = 0; k < this.getTypeList().size(); k++) {
        String val = (String) this.getTypeList().get(k);
        if (val != null && !(val.equals(""))) {
          int type_id = Integer.parseInt((String) this.getTypeList().get(k));
          lvlcount++;
          insertType(db, type_id, lvlcount);
        } else {
          lvlcount--;
        }
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity-> Closing PreparedStatement");
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID not specified");
    }
    String sql = "DELETE FROM opportunity_component_levels WHERE opp_id in (SELECT id from opportunity_component oc where oc.opp_id = ?) ";
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
   *@param  type_id           Description of the Parameter
   *@param  level             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID not specified");
    }
    String sql =
        "INSERT INTO opportunity_component_levels " +
        "(opp_id, type_id, level) " +
        "VALUES (?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.setInt(++i, type_id);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@since
   */
  public void invalidateUserData(ActionContext context) {
    invalidateUserData(context, this.getOwner());
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newOwner          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setOwner(newOwner);
    result = this.update(db);

    if (result == -1) {
      return false;
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@param  userId   Description of Parameter
   *@since
   */
  public void invalidateUserData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setIsValid(false, true);
    systemStatus.getHierarchyList().getUser(userId).setPipelineValueIsValid(false);
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
      pst.setInt(++i, Constants.PIPELINE);
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
   *@exception  SQLException  Description of the Exception
   */
  public void retrieveComponentCount(Connection db) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as componentcount " +
        "FROM opportunity_component oc " +
        "WHERE id > 0 " +
        "AND oc.opp_id = ? ");
    if (this.getOwner() > -1) {
      sql.append("AND oc.owner = ? ");
    }

    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    if (this.getOwner() > -1) {
      pst.setInt(2, owner);
    }
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
        "WHERE id > 0 " +
        "AND oc.opp_id = ? ");
    if (this.getOwner() > -1) {
      sql.append("AND oc.owner = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, oppId);
    if (this.getOwner() > -1) {
      pst.setInt(2, owner);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      total = rs.getDouble("total");
    }
    rs.close();
    pst.close();
    this.setTotalValue(total);
  }

}


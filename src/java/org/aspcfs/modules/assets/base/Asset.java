//Copyright 2004 Dark Horse Ventures

package org.aspcfs.modules.assets.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.troubletickets.base.TicketList;

/**
 *  Assets are h/w, s/w or systems that are serviced under the service contract.
 *
 *@author     kbhoopal
 *@created    March 16, 2004
 *@version    $Id$
 */
public class Asset extends GenericBean {

  private int id = -1;
  private int contractId = -1;
  private String serviceContractNumber = null;
  private int orgId = -1;
  private java.sql.Timestamp dateListed = null;
  private String assetTag = null;
  private int status = -1;
  private String location = null;
  private int level1 = -1;
  private int level2 = -1;
  private int level3 = -1;
  private String vendor = null;
  private String manufacturer = null;
  private String serialNumber = null;
  private String modelVersion = null;
  private String description = null;
  private java.sql.Timestamp expirationDate = null;
  private String inclusions = null;
  private String exclusions = null;
  private java.sql.Timestamp purchaseDate = null;
  private double purchaseCost = -1;
  private String poNumber = null;
  private String purchasedFrom = null;
  private int contactId = -1;
  private String notes = null;
  private int responseTime = -1;
  private int telephoneResponseModel = -1;
  private int onsiteResponseModel = -1;
  private int emailResponseModel = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private boolean override = false;


  /**
   *  Constructor for the Asset object
   */
  public Asset() {
    errors.clear();
  }


  /**
   *  Constructor for the Asset object
   *
   *@param  db                Description of the Parameter
   *@param  tmpId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Asset(Connection db, String tmpId) throws SQLException {
    errors.clear();
    queryRecord(db, Integer.parseInt(tmpId));
  }


  /**
   *  Constructor for the Asset object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Asset(ResultSet rs) throws SQLException {
    errors.clear();
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Asset object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Asset object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractId attribute of the Asset object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(int tmp) {
    this.contractId = tmp;
  }


  /**
   *  Sets the contractId attribute of the Asset object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(String tmp) {
    this.contractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractNumber attribute of the Asset object
   *
   *@param  tmp  The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   *  Sets the orgId attribute of the Asset object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the Asset object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the dateListed attribute of the Asset object
   *
   *@param  tmp  The new dateListed value
   */
  public void setDateListed(java.sql.Timestamp tmp) {
    this.dateListed = tmp;
  }


  /**
   *  Sets the dateListed attribute of the Asset object
   *
   *@param  tmp  The new dateListed value
   */
  public void setDateListed(String tmp) {
    this.dateListed = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the assetTag attribute of the Asset object
   *
   *@param  tmp  The new assetTag value
   */
  public void setAssetTag(String tmp) {
    this.assetTag = tmp;
  }


  /**
   *  Sets the status attribute of the Asset object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the status attribute of the Asset object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   *  Sets the location attribute of the Asset object
   *
   *@param  tmp  The new location value
   */
  public void setLocation(String tmp) {
    this.location = tmp;
  }


  /**
   *  Sets the level1 attribute of the Asset object
   *
   *@param  tmp  The new level1 value
   */
  public void setLevel1(int tmp) {
    this.level1 = tmp;
  }


  /**
   *  Sets the level1 attribute of the Asset object
   *
   *@param  tmp  The new level1 value
   */
  public void setLevel1(String tmp) {
    this.level1 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the level2 attribute of the Asset object
   *
   *@param  tmp  The new level2 value
   */
  public void setLevel2(int tmp) {
    this.level2 = tmp;
  }


  /**
   *  Sets the level2 attribute of the Asset object
   *
   *@param  tmp  The new level2 value
   */
  public void setLevel2(String tmp) {
    this.level2 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the level3 attribute of the Asset object
   *
   *@param  tmp  The new level3 value
   */
  public void setLevel3(int tmp) {
    this.level3 = tmp;
  }


  /**
   *  Sets the level3 attribute of the Asset object
   *
   *@param  tmp  The new level3 value
   */
  public void setLevel3(String tmp) {
    this.level3 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the vendor attribute of the Asset object
   *
   *@param  tmp  The new vendor value
   */
  public void setVendor(String tmp) {
    this.vendor = tmp;
  }


  /**
   *  Sets the manufacturer attribute of the Asset object
   *
   *@param  tmp  The new manufacturer value
   */
  public void setManufacturer(String tmp) {
    this.manufacturer = tmp;
  }


  /**
   *  Sets the serialNumber attribute of the Asset object
   *
   *@param  tmp  The new serialNumber value
   */
  public void setSerialNumber(String tmp) {
    this.serialNumber = tmp;
  }


  /**
   *  Sets the modelVersion attribute of the Asset object
   *
   *@param  tmp  The new modelVersion value
   */
  public void setModelVersion(String tmp) {
    this.modelVersion = tmp;
  }


  /**
   *  Sets the description attribute of the Asset object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the Asset object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(java.sql.Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the Asset object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the inclusions attribute of the Asset object
   *
   *@param  tmp  The new inclusions value
   */
  public void setInclusions(String tmp) {
    this.inclusions = tmp;
  }


  /**
   *  Sets the exclusions attribute of the Asset object
   *
   *@param  tmp  The new exclusions value
   */
  public void setExclusions(String tmp) {
    this.exclusions = tmp;
  }


  /**
   *  Sets the purchaseDate attribute of the Asset object
   *
   *@param  tmp  The new purchaseDate value
   */
  public void setPurchaseDate(java.sql.Timestamp tmp) {
    this.purchaseDate = tmp;
  }


  /**
   *  Sets the purchaseDate attribute of the Asset object
   *
   *@param  tmp  The new purchaseDate value
   */
  public void setPurchaseDate(String tmp) {
    this.purchaseDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the purchaseCost attribute of the Asset object
   *
   *@param  tmp  The new purchaseCost value
   */
  public void setPurchaseCost(String tmp) {
    this.purchaseCost = Double.parseDouble(tmp);
  }


  /**
   *  Sets the purchaseCost attribute of the Asset object
   *
   *@param  tmp  The new purchaseCost value
   */
  public void setPurchaseCost(double tmp) {
    this.purchaseCost = tmp;
  }


  /**
   *  Sets the poNumber attribute of the Asset object
   *
   *@param  tmp  The new poNumber value
   */
  public void setPoNumber(String tmp) {
    this.poNumber = tmp;
  }


  /**
   *  Sets the purchasedFrom attribute of the Asset object
   *
   *@param  tmp  The new purchasedFrom value
   */
  public void setPurchasedFrom(String tmp) {
    this.purchasedFrom = tmp;
  }


  /**
   *  Sets the contactId attribute of the Asset object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the Asset object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the Asset object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the responseTime attribute of the Asset object
   *
   *@param  tmp  The new responseTime value
   */
  public void setResponseTime(int tmp) {
    this.responseTime = tmp;
  }


  /**
   *  Sets the responseTime attribute of the Asset object
   *
   *@param  tmp  The new responseTime value
   */
  public void setResponseTime(String tmp) {
    this.responseTime = Integer.parseInt(tmp);
  }


  /**
   *  Sets the telephoneResponseModel attribute of the Asset object
   *
   *@param  tmp  The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(int tmp) {
    this.telephoneResponseModel = tmp;
  }


  /**
   *  Sets the telephoneResponseModel attribute of the Asset object
   *
   *@param  tmp  The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(String tmp) {
    this.telephoneResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the onsiteResponseModel attribute of the Asset object
   *
   *@param  tmp  The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(int tmp) {
    this.onsiteResponseModel = tmp;
  }


  /**
   *  Sets the onsiteResponseModel attribute of the Asset object
   *
   *@param  tmp  The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(String tmp) {
    this.onsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the emailResponseModel attribute of the Asset object
   *
   *@param  tmp  The new emailResponseModel value
   */
  public void setEmailResponseModel(int tmp) {
    this.emailResponseModel = tmp;
  }


  /**
   *  Sets the emailResponseModel attribute of the Asset object
   *
   *@param  tmp  The new emailResponseModel value
   */
  public void setEmailResponseModel(String tmp) {
    this.emailResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the Asset object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Asset object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Asset object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Asset object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Asset object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Asset object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Asset object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Asset object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Asset object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Asset object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the override attribute of the Asset object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the Asset object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the Asset object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the contractId attribute of the Asset object
   *
   *@return    The contractId value
   */
  public int getContractId() {
    return contractId;
  }


  /**
   *  Gets the serviceContractNumber attribute of the Asset object
   *
   *@return    The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   *  Gets the orgId attribute of the Asset object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the dateListed attribute of the Asset object
   *
   *@return    The dateListed value
   */
  public java.sql.Timestamp getDateListed() {
    return dateListed;
  }


  /**
   *  Gets the assetTag attribute of the Asset object
   *
   *@return    The assetTag value
   */
  public String getAssetTag() {
    return assetTag;
  }


  /**
   *  Gets the status attribute of the Asset object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the location attribute of the Asset object
   *
   *@return    The location value
   */
  public String getLocation() {
    return location;
  }


  /**
   *  Gets the level1 attribute of the Asset object
   *
   *@return    The level1 value
   */
  public int getLevel1() {
    return level1;
  }


  /**
   *  Gets the level2 attribute of the Asset object
   *
   *@return    The level2 value
   */
  public int getLevel2() {
    return level2;
  }


  /**
   *  Gets the level3 attribute of the Asset object
   *
   *@return    The level3 value
   */
  public int getLevel3() {
    return level3;
  }


  /**
   *  Gets the vendor attribute of the Asset object
   *
   *@return    The vendor value
   */
  public String getVendor() {
    return vendor;
  }


  /**
   *  Gets the manufacturer attribute of the Asset object
   *
   *@return    The manufacturer value
   */
  public String getManufacturer() {
    return manufacturer;
  }


  /**
   *  Gets the serialNumber attribute of the Asset object
   *
   *@return    The serialNumber value
   */
  public String getSerialNumber() {
    return serialNumber;
  }


  /**
   *  Gets the modelVersion attribute of the Asset object
   *
   *@return    The modelVersion value
   */
  public String getModelVersion() {
    return modelVersion;
  }


  /**
   *  Gets the description attribute of the Asset object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the expirationDate attribute of the Asset object
   *
   *@return    The expirationDate value
   */
  public java.sql.Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the inclusions attribute of the Asset object
   *
   *@return    The inclusions value
   */
  public String getInclusions() {
    return inclusions;
  }


  /**
   *  Gets the exclusions attribute of the Asset object
   *
   *@return    The exclusions value
   */
  public String getExclusions() {
    return exclusions;
  }


  /**
   *  Gets the purchaseDate attribute of the Asset object
   *
   *@return    The purchaseDate value
   */
  public java.sql.Timestamp getPurchaseDate() {
    return purchaseDate;
  }


  /**
   *  Gets the purchaseCost attribute of the Asset object
   *
   *@return    The purchaseCost value
   */
  public double getPurchaseCost() {
    return purchaseCost;
  }


  /**
   *  Gets the poNumber attribute of the Asset object
   *
   *@return    The poNumber value
   */
  public String getPoNumber() {
    return poNumber;
  }


  /**
   *  Gets the purchasedFrom attribute of the Asset object
   *
   *@return    The purchasedFrom value
   */
  public String getPurchasedFrom() {
    return purchasedFrom;
  }


  /**
   *  Gets the contactId attribute of the Asset object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the notes attribute of the Asset object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the responseTime attribute of the Asset object
   *
   *@return    The responseTime value
   */
  public int getResponseTime() {
    return responseTime;
  }


  /**
   *  Gets the telephoneResponseModel attribute of the Asset object
   *
   *@return    The telephoneResponseModel value
   */
  public int getTelephoneResponseModel() {
    return telephoneResponseModel;
  }


  /**
   *  Gets the onsiteResponseModel attribute of the Asset object
   *
   *@return    The onsiteResponseModel value
   */
  public int getOnsiteResponseModel() {
    return onsiteResponseModel;
  }


  /**
   *  Gets the emailResponseModel attribute of the Asset object
   *
   *@return    The emailResponseModel value
   */
  public int getEmailResponseModel() {
    return emailResponseModel;
  }


  /**
   *  Gets the entered attribute of the Asset object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Asset object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Asset object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Asset object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the Asset object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the override attribute of the Asset object
   *
   *@return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the properties that are TimeZone sensitive
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("dateListed");
    thisList.add("expirationDate");
    thisList.add("purchaseDate");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the Asset class
   *
   *@return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("purchaseCost");
    return thisList;
  }


  /**
   *  Gets the valid attribute of the Asset object
   *
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid() throws SQLException {

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpAssetId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int tmpAssetId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT a.*, sc.contract_number AS service_contract_number " +
        "FROM asset a LEFT JOIN service_contract sc ON (a.contract_id = sc.contract_id) " +
        "WHERE a.asset_id = ? ");
    pst.setInt(1, tmpAssetId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid() || hasErrors()) {
      return false;
    }

    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "INSERT INTO asset " +
        "(account_id," +
        "contract_id," +
        "date_listed," +
        "asset_tag," +
        "status," +
        "location," +
        "level1," +
        "level2," +
        "level3," +
        "vendor," +
        "manufacturer," +
        "serial_number," +
        "model_version," +
        "description," +
        "expiration_date," +
        "inclusions," +
        "exclusions," +
        "purchase_date," +
        "purchase_cost," +
        "po_number," +
        "purchased_from," +
        "contact_id," +
        "notes," +
        "response_time," +
        "telephone_service_model," +
        "onsite_service_model," +
        "email_service_model," +
        "enteredby," +
        "modifiedby)" +
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    int i = 0;
    pst.setInt(++i, orgId);
    DatabaseUtils.setInt(pst, ++i, contractId);
    DatabaseUtils.setTimestamp(pst, ++i, dateListed);
    pst.setString(++i, assetTag);
    DatabaseUtils.setInt(pst, ++i, status);
    pst.setString(++i, location);
    DatabaseUtils.setInt(pst, ++i, level1);
    DatabaseUtils.setInt(pst, ++i, level2);
    DatabaseUtils.setInt(pst, ++i, level3);
    pst.setString(++i, vendor);
    pst.setString(++i, manufacturer);
    pst.setString(++i, serialNumber);
    pst.setString(++i, modelVersion);
    pst.setString(++i, description);
    DatabaseUtils.setTimestamp(pst, ++i, expirationDate);
    pst.setString(++i, inclusions);
    pst.setString(++i, exclusions);
    DatabaseUtils.setTimestamp(pst, ++i, purchaseDate);
    pst.setDouble(++i, purchaseCost);
    pst.setString(++i, poNumber);
    pst.setString(++i, purchasedFrom);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, notes);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "asset_asset_id_seq");
    pst.close();

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
    int resultCount = -1;
    if (!isValid() || hasErrors()) {
      return resultCount;
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE asset SET " +
        "contract_id = ? , " +
        "date_listed = ? , " +
        "asset_tag = ? , " +
        "status = ? , " +
        "location = ? , " +
        "level1 = ? , " +
        "level2 = ? , " +
        "level3 = ? , " +
        "vendor = ? , " +
        "manufacturer = ? , " +
        "serial_number = ? , " +
        "model_version = ? , " +
        "description = ? , " +
        "expiration_date = ? , " +
        "inclusions = ? , " +
        "exclusions = ? , " +
        "purchase_date = ? , " +
        "purchase_cost = ? , " +
        "po_number = ? , " +
        "purchased_from = ? , " +
        "contact_id = ? , " +
        "notes = ? , " +
        "response_time = ? , " +
        "telephone_service_model = ? , " +
        "onsite_service_model = ? , " +
        "email_service_model = ? ");

    if (!override) {
      sql.append(" , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE asset_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, contractId);
    DatabaseUtils.setTimestamp(pst, ++i, dateListed);
    pst.setString(++i, assetTag);
    DatabaseUtils.setInt(pst, ++i, status);
    pst.setString(++i, location);
    DatabaseUtils.setInt(pst, ++i, level1);
    DatabaseUtils.setInt(pst, ++i, level2);
    DatabaseUtils.setInt(pst, ++i, level3);
    pst.setString(++i, vendor);
    pst.setString(++i, manufacturer);
    pst.setString(++i, serialNumber);
    pst.setString(++i, modelVersion);
    pst.setString(++i, description);
    DatabaseUtils.setTimestamp(pst, ++i, expirationDate);
    pst.setString(++i, inclusions);
    pst.setString(++i, exclusions);
    DatabaseUtils.setTimestamp(pst, ++i, purchaseDate);
    pst.setDouble(++i, purchaseCost);
    pst.setString(++i, poNumber);
    pst.setString(++i, purchasedFrom);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, notes);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
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
    DependencyList dependencyList = new DependencyList();
    try {

      Dependency ticDependency = new Dependency();
      ticDependency.setName("Tickets");
      ticDependency.setCount(TicketList.retrieveRecordCount(db, Constants.ASSETS, this.getId()));
      ticDependency.setCanDelete(false);
      dependencyList.add(ticDependency);

    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Seperate method to delete the dependencies and then the object. This
   *  seperation allows asset alone to be deleted when other objects are deleted
   *  (for e.g., tickets)
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      File path of documents related to a ticket
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteAll(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Asset Id not specified.");
    }
    try {
      db.setAutoCommit(false);
      //Assets have tickets related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setAssetId(this.getId());
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      delete(db);
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Deletes this object from the database
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "DELETE FROM asset " +
        "WHERE asset_id = ? ");
    pst.setInt(1, id);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  void buildRecord(ResultSet rs) throws SQLException {
    //asset table
    id = rs.getInt("asset_id");
    orgId = DatabaseUtils.getInt(rs, "account_id");
    contractId = DatabaseUtils.getInt(rs, "contract_id");
    dateListed = rs.getTimestamp("date_listed");
    assetTag = rs.getString("asset_tag");
    status = DatabaseUtils.getInt(rs, "status");
    location = rs.getString("location");
    level1 = DatabaseUtils.getInt(rs, "level1");
    level2 = DatabaseUtils.getInt(rs, "level2");
    level3 = DatabaseUtils.getInt(rs, "level3");
    vendor = rs.getString("vendor");
    manufacturer = rs.getString("manufacturer");
    serialNumber = rs.getString("serial_number");
    modelVersion = rs.getString("model_version");
    description = rs.getString("description");
    expirationDate = rs.getTimestamp("expiration_date");
    inclusions = rs.getString("inclusions");
    exclusions = rs.getString("exclusions");
    purchaseDate = rs.getTimestamp("purchase_date");
    poNumber = rs.getString("po_number");
    purchasedFrom = rs.getString("purchased_from");
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    notes = rs.getString("notes");
    responseTime = DatabaseUtils.getInt(rs, "response_time");
    telephoneResponseModel = DatabaseUtils.getInt(rs, "telephone_service_model");
    onsiteResponseModel = DatabaseUtils.getInt(rs, "onsite_service_model");
    emailResponseModel = DatabaseUtils.getInt(rs, "email_service_model");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    purchaseCost = rs.getDouble("purchase_cost");
    // service contract table
    serviceContractNumber = rs.getString("service_contract_number");
  }
}


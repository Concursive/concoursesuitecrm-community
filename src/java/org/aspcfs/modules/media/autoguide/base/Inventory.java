//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;
import com.darkhorseventures.utils.StringUtils;
import com.darkhorseventures.cfsbase.Organization;
import javax.servlet.http.*;
import com.darkhorseventures.cfsbase.Constants;
import com.zeroio.iteam.base.FileItem;

/**
 *  Represents a vehicle for the purpose of scheduling advertisements, including
 *  details, options and ad run dates
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class Inventory {

  private int id = -1;
  private int vehicleId = -1;
  private int accountId = -1;
  private String vin = null;
  private int mileage = -1;
  private boolean isNew = false;
  private String condition = null;
  private String comments = null;
  private String stockNo = null;
  private String exteriorColor = null;
  private String interiorColor = null;
  private double invoicePrice = -1;
  private double sellingPrice = -1;
  private boolean sold = false;
  private String status = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private Vehicle vehicle = new Vehicle();
  private Organization organization = null;
  private OptionList options = null;
  private AdRunList adRuns = null;
  private int pictureId = -1;
  private FileItem picture = null;

  private boolean showIncompleteAdRunsOnly = false;


  /**
   *  Constructor for the Inventory object
   */
  public Inventory() { }


  /**
   *  Constructor for the Inventory object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Inventory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Inventory object
   *
   *@param  db                Description of Parameter
   *@param  inventoryId       Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Inventory(Connection db, int inventoryId) throws SQLException {
    String sql =
        "SELECT i.inventory_id, i.vehicle_id AS inventory_vehicle_id, " +
        "i.account_id, vin, mileage, is_new, " +
        "condition, comments, stock_no, ext_color, int_color, invoice_price, " +
        "selling_price, sold, i.status, i.entered, i.enteredby, i.modified, i.modifiedby, " +
        "v.vehicle_id, v.year, v.make_id AS vehicle_make_id, " +
        "v.model_id AS vehicle_model_id, v.entered AS vehicle_entered, " +
        "v.enteredby AS vehicle_enteredby, v.modified AS vehicle_modified, " +
        "v.modifiedby AS vehicle_modifiedby, " +
        "model.model_id, model.make_id AS model_make_id, model.model_name, " +
        "model.entered, model.enteredby, " +
        "model.modified, model.modifiedby, " +
        "make.make_id, make.make_name, " +
        "make.entered AS make_entered, make.enteredby AS make_enteredby, " +
        "make.modified AS make_modified, make.modifiedby AS make_modifiedby " +
        "FROM autoguide_inventory i " +
        " LEFT JOIN autoguide_vehicle v ON i.vehicle_id = v.vehicle_id " +
        " LEFT JOIN autoguide_make make ON v.make_id = make.make_id " +
        " LEFT JOIN autoguide_model model ON v.model_id = model.model_id " +
        "WHERE i.inventory_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, inventoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      System.out.println("Inventory-> * RECORD NOT FOUND: " + inventoryId);
    }
    rs.close();
    pst.close();
    this.buildOrganizationInfo(db);
    this.buildOptions(db);
    this.buildAdRuns(db);
    this.buildPicture(db);
  }


  /**
   *  Sets the id attribute of the Inventory object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    id = tmp;
  }


  /**
   *  Sets the id attribute of the Inventory object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the vehicleId attribute of the Inventory object
   *
   *@param  tmp  The new vehicleId value
   */
  public void setVehicleId(int tmp) {
    this.vehicleId = tmp;
  }


  /**
   *  Sets the vehicleId attribute of the Inventory object
   *
   *@param  tmp  The new vehicleId value
   */
  public void setVehicleId(String tmp) {
    this.vehicleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the accountId attribute of the Inventory object
   *
   *@param  tmp  The new accountId value
   */
  public void setAccountId(int tmp) {
    this.accountId = tmp;
  }


  /**
   *  Sets the accountId attribute of the Inventory object
   *
   *@param  tmp  The new accountId value
   */
  public void setAccountId(String tmp) {
    this.accountId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the vin attribute of the Inventory object
   *
   *@param  tmp  The new vin value
   */
  public void setVin(String tmp) {
    this.vin = tmp;
  }


  /**
   *  Sets the mileage attribute of the Inventory object
   *
   *@param  tmp  The new mileage value
   */
  public void setMileage(int tmp) {
    this.mileage = tmp;
  }


  /**
   *  Sets the mileage attribute of the Inventory object
   *
   *@param  tmp  The new mileage value
   */
  public void setMileage(String tmp) {
    this.mileage = StringUtils.getIntegerNumber(tmp);
  }


  /**
   *  Sets the isNew attribute of the Inventory object
   *
   *@param  tmp  The new isNew value
   */
  public void setIsNew(boolean tmp) {
    this.isNew = tmp;
  }


  /**
   *  Sets the isNew attribute of the Inventory object
   *
   *@param  tmp  The new isNew value
   */
  public void setIsNew(String tmp) {
    this.isNew = ("1".equals(tmp) || "on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the condition attribute of the Inventory object
   *
   *@param  tmp  The new condition value
   */
  public void setCondition(String tmp) {
    this.condition = tmp;
  }


  /**
   *  Sets the comments attribute of the Inventory object
   *
   *@param  tmp  The new comments value
   */
  public void setComments(String tmp) {
    this.comments = tmp;
  }


  /**
   *  Sets the stockNo attribute of the Inventory object
   *
   *@param  tmp  The new stockNo value
   */
  public void setStockNo(String tmp) {
    this.stockNo = tmp;
  }


  /**
   *  Sets the exteriorColor attribute of the Inventory object
   *
   *@param  tmp  The new exteriorColor value
   */
  public void setExteriorColor(String tmp) {
    this.exteriorColor = tmp;
  }


  /**
   *  Sets the interiorColor attribute of the Inventory object
   *
   *@param  tmp  The new interiorColor value
   */
  public void setInteriorColor(String tmp) {
    this.interiorColor = tmp;
  }


  /**
   *  Sets the invoicePrice attribute of the Inventory object
   *
   *@param  tmp  The new invoicePrice value
   */
  public void setInvoicePrice(double tmp) {
    this.invoicePrice = tmp;
  }


  /**
   *  Sets the invoicePrice attribute of the Inventory object
   *
   *@param  tmp  The new invoicePrice value
   */
  public void setInvoicePrice(String tmp) {
    this.invoicePrice = StringUtils.getDoubleNumber(tmp);
  }


  /**
   *  Sets the sellingPrice attribute of the Inventory object
   *
   *@param  tmp  The new sellingPrice value
   */
  public void setSellingPrice(double tmp) {
    this.sellingPrice = tmp;
  }


  /**
   *  Sets the sellingPrice attribute of the Inventory object
   *
   *@param  tmp  The new sellingPrice value
   */
  public void setSellingPrice(String tmp) {
    sellingPrice = StringUtils.getDoubleNumber(tmp);
  }


  /**
   *  Sets the sold attribute of the Inventory object
   *
   *@param  tmp  The new sold value
   */
  public void setSold(boolean tmp) {
    this.sold = tmp;
  }


  /**
   *  Sets the sold attribute of the Inventory object
   *
   *@param  tmp  The new sold value
   */
  public void setSold(String tmp) {
    this.sold = ("1".equals(tmp) || "on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the status attribute of the Inventory object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the entered attribute of the Inventory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Inventory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Inventory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Inventory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Inventory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Inventory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Inventory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Inventory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the vehicle attribute of the Inventory object
   *
   *@param  tmp  The new vehicle value
   */
  public void setVehicle(Vehicle tmp) {
    this.vehicle = tmp;
  }


  /**
   *  Sets the organization attribute of the Inventory object
   *
   *@param  tmp  The new organization value
   */
  public void setOrganization(Organization tmp) {
    this.organization = tmp;
  }


  /**
   *  Sets the options attribute of the Inventory object
   *
   *@param  tmp  The new options value
   */
  public void setOptions(OptionList tmp) {
    this.options = tmp;
  }


  /**
   *  Sets the requestItems attribute of the Inventory object
   *
   *@param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    options = new OptionList(request);
    adRuns = new AdRunList(request);
  }


  /**
   *  Sets the adRuns attribute of the Inventory object
   *
   *@param  tmp  The new adRuns value
   */
  public void setAdRuns(AdRunList tmp) {
    this.adRuns = tmp;
  }


  /**
   *  Sets the pictureId attribute of the Inventory object
   *
   *@param  tmp  The new pictureId value
   */
  public void setPictureId(int tmp) {
    this.pictureId = tmp;
  }


  /**
   *  Sets the picture attribute of the Inventory object
   *
   *@param  tmp  The new picture value
   */
  public void setPicture(FileItem tmp) {
    this.picture = tmp;
  }


  /**
   *  Sets the showIncompleteAdRunsOnly attribute of the Inventory object
   *
   *@param  tmp  The new showIncompleteAdRunsOnly value
   */
  public void setShowIncompleteAdRunsOnly(boolean tmp) {
    this.showIncompleteAdRunsOnly = tmp;
  }


  /**
   *  Gets the id attribute of the Inventory object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the vehicleId attribute of the Inventory object
   *
   *@return    The vehicleId value
   */
  public int getVehicleId() {
    return vehicleId;
  }


  /**
   *  Gets the accountId attribute of the Inventory object
   *
   *@return    The accountId value
   */
  public int getAccountId() {
    return accountId;
  }


  /**
   *  Gets the vin attribute of the Inventory object
   *
   *@return    The vin value
   */
  public String getVin() {
    return vin;
  }


  /**
   *  Gets the mileage attribute of the Inventory object
   *
   *@return    The mileage value
   */
  public int getMileage() {
    return mileage;
  }


  /**
   *  Gets the mileageString attribute of the Inventory object
   *
   *@return    The mileageString value
   */
  public String getMileageString() {
    if (mileage > -1) {
      return String.valueOf(mileage);
    } else {
      return "";
    }
  }


  /**
   *  Gets the isNew attribute of the Inventory object
   *
   *@return    The isNew value
   */
  public boolean getIsNew() {
    return isNew;
  }


  /**
   *  Gets the condition attribute of the Inventory object
   *
   *@return    The condition value
   */
  public String getCondition() {
    return condition;
  }


  /**
   *  Gets the comments attribute of the Inventory object
   *
   *@return    The comments value
   */
  public String getComments() {
    return comments;
  }


  /**
   *  Gets the stockNo attribute of the Inventory object
   *
   *@return    The stockNo value
   */
  public String getStockNo() {
    return stockNo;
  }


  /**
   *  Gets the exteriorColor attribute of the Inventory object
   *
   *@return    The exteriorColor value
   */
  public String getExteriorColor() {
    return exteriorColor;
  }


  /**
   *  Gets the interiorColor attribute of the Inventory object
   *
   *@return    The interiorColor value
   */
  public String getInteriorColor() {
    return interiorColor;
  }


  /**
   *  Gets the invoicePrice attribute of the Inventory object
   *
   *@return    The invoicePrice value
   */
  public double getInvoicePrice() {
    return invoicePrice;
  }


  /**
   *  Gets the invoicePriceString attribute of the Inventory object
   *
   *@return    The invoicePriceString value
   */
  public String getInvoicePriceString() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    return ("$" + numberFormatter.format(invoicePrice));
  }


  /**
   *  Gets the sellingPrice attribute of the Inventory object
   *
   *@return    The sellingPrice value
   */
  public double getSellingPrice() {
    return sellingPrice;
  }


  /**
   *  Gets the sellingPriceString attribute of the Inventory object
   *
   *@return    The sellingPriceString value
   */
  public String getSellingPriceString() {
    if (sellingPrice > 0) {
      NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
      return ("$" + numberFormatter.format(sellingPrice));
    } else {
      return "";
    }
  }


  /**
   *  Gets the sold attribute of the Inventory object
   *
   *@return    The sold value
   */
  public boolean getSold() {
    return sold;
  }


  /**
   *  Gets the status attribute of the Inventory object
   *
   *@return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the entered attribute of the Inventory object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Inventory object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Inventory object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Inventory object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the vehicle attribute of the Inventory object
   *
   *@return    The vehicle value
   */
  public Vehicle getVehicle() {
    return vehicle;
  }


  /**
   *  Gets the organization attribute of the Inventory object
   *
   *@return    The organization value
   */
  public Organization getOrganization() {
    return organization;
  }


  /**
   *  Gets the account attribute of the Inventory object
   *
   *@return    The account value
   */
  public Organization getAccount() {
    return organization;
  }


  /**
   *  Gets the options attribute of the Inventory object
   *
   *@return    The options value
   */
  public OptionList getOptions() {
    return options;
  }


  /**
   *  Gets the adRuns attribute of the Inventory object
   *
   *@return    The adRuns value
   */
  public AdRunList getAdRuns() {
    return adRuns;
  }


  /**
   *  Gets the pictureId attribute of the Inventory object
   *
   *@return    The pictureId value
   */
  public int getPictureId() {
    return pictureId;
  }


  /**
   *  Gets the picture attribute of the Inventory object
   *
   *@return    The picture value
   */
  public FileItem getPicture() {
    return picture;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasOptions() {
    return (options != null && options.size() > 0);
  }


  /**
   *  Description of the Method
   *
   *@param  optionId  Description of Parameter
   *@return           Description of the Returned Value
   */
  public boolean hasOption(int optionId) {
    return (options != null && options.hasOption(optionId));
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasAdRuns() {
    return (adRuns != null && adRuns.size() > 0);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasPictureId() {
    return pictureId > -1;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasPicture() {
    return (picture != null);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO autoguide_inventory " +
        "(vehicle_id, account_id, vin, mileage, is_new, condition, comments, " +
        "stock_no, ext_color, int_color, invoice_price, selling_price, sold, " +
        "status, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, vehicleId);
    pst.setInt(++i, accountId);
    pst.setString(++i, vin);
    pst.setInt(++i, mileage);
    pst.setBoolean(++i, isNew);
    pst.setString(++i, condition);
    pst.setString(++i, comments);
    pst.setString(++i, stockNo);
    pst.setString(++i, exteriorColor);
    pst.setString(++i, interiorColor);
    pst.setDouble(++i, invoicePrice);
    pst.setDouble(++i, sellingPrice);
    pst.setBoolean(++i, sold);
    pst.setString(++i, status);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "autoguide_inve_inventory_id_seq");

    if (options != null) {
      options.setInventoryId(id);
      options.insert(db);
    }
    if (adRuns != null) {
      adRuns.setInventoryId(id);
      adRuns.insert(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    /*
     *  if (!isValid(db)) {
     *  return -1;
     *  }
     */
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();

      sql.append(
          "UPDATE autoguide_inventory " +
          "SET vehicle_id = ?, vin = ?, mileage = ?, is_new = ?, condition = ?, comments = ?, " +
          "stock_no = ?, ext_color = ?, int_color = ?, invoice_price = ?, selling_price = ?, sold = ?, status = ?, " +
          "modifiedby = ?, modified = CURRENT_TIMESTAMP ");
      sql.append("WHERE inventory_id = ? ");
      sql.append("AND modified = ? ");

      int i = 0;
      pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getVehicleId());
      pst.setString(++i, this.getVin());
      pst.setInt(++i, this.getMileage());
      pst.setBoolean(++i, this.getIsNew());
      pst.setString(++i, this.getCondition());
      pst.setString(++i, this.getComments());
      pst.setString(++i, this.getStockNo());
      pst.setString(++i, this.getExteriorColor());
      pst.setString(++i, this.getInteriorColor());
      pst.setDouble(++i, this.getInvoicePrice());
      pst.setDouble(++i, this.getSellingPrice());
      pst.setBoolean(++i, this.getSold());
      pst.setString(++i, this.getStatus());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      pst.setTimestamp(++i, this.getModified());
      resultCount = pst.executeUpdate();
      pst.close();

      if (resultCount == 1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Inventory-> Vehicle updated, updating options...");
        }
        options.setInventoryId(id);
        options.update(db);

        if (System.getProperty("DEBUG") != null) {
          System.out.println("Inventory-> Options updated, updating ad runs...");
        }
        adRuns.setInventoryId(id);
        adRuns.update(db);
      } else {
        System.out.println("Inventory-> Not updated...");
        System.out.println("Inventory-> Inventory Id: " + id);
        System.out.println("Inventory-> Inventory Modified: " + modified);
      }

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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID was not specified");
    }

    PreparedStatement pst = null;

    //Options
    if (options != null) {
      options.setInventoryId(id);
      options.delete(db);
    }

    //Ad Runs
    if (adRuns != null) {
      adRuns.setInventoryId(id);
      adRuns.delete(db);
    }

    //TODO: Pictures -- need filepath at this point
    /*
     *  if (pictureId > -1) {
     *  FileItemList previousFiles = new FileItemList();
     *  previousFiles.setLinkModuleId(Constants.AUTOGUIDE);
     *  previousFiles.setLinkItemId(id);
     *  previousFiles.buildList(db);
     *  previousFiles.delete(db, filePath);
     *  }
     */
    //Delete the record
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM autoguide_inventory " +
        "WHERE inventory_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      //errors.put("actionError", "Record could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildOrganizationInfo(Connection db) throws SQLException {
    if (accountId == -1) {
      accountId = 0;
    }
    organization = new Organization(db, accountId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildOptions(Connection db) throws SQLException {
    options = new OptionList();
    options.setInventoryId(id);
    options.buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildAdRuns(Connection db) throws SQLException {
    adRuns = new AdRunList();
    adRuns.setIncompleteOnly(showIncompleteAdRunsOnly);
    adRuns.setInventoryId(id);
    adRuns.buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void generateVehicleId(Connection db) throws SQLException {
    vehicleId = this.getVehicle().generateId(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildPictureId(Connection db) throws SQLException {
    FileItem fileItem = new FileItem(db, -1, id, Constants.AUTOGUIDE);
    pictureId = fileItem.getId();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildPicture(Connection db) throws SQLException {
    picture = new FileItem(db, -1, id, Constants.AUTOGUIDE);
    picture.buildVersionList(db);
    pictureId = picture.getId();
    if (pictureId == -1) {
      picture = null;
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String toString() {
    String lf = System.getProperty("line.separator");
    StringBuffer sb = new StringBuffer();
    sb.append("AUTO GUIDE VEHICLE OUTPUT: " + StringUtils.toDateTimeString(new java.util.Date()) + lf);
    sb.append(lf);
    sb.append(StringUtils.toString(organization.getName()));
    sb.append(" (" + StringUtils.toString(organization.getAccountNumber()) + ")" + lf);
    sb.append(organization.getPhoneNumber("Main") + lf);
    sb.append(lf);
    if (this.hasAdRuns()) {
      sb.append("Ad Runs:" + lf);
      int displayCount = 0;
      Iterator irun = adRuns.iterator();
      while (irun.hasNext()) {
        ++displayCount;
        AdRun thisAdRun = (AdRun) irun.next();
        sb.append(displayCount + ". ");
        if (adRuns.size() > 9 && displayCount < 10) {
          sb.append(" ");
        }
        sb.append(StringUtils.toDateString(thisAdRun.getRunDate()) + " ");
        sb.append(thisAdRun.getAdTypeName());
        sb.append(" - with");
        if (!thisAdRun.getIncludePhoto()) {
          sb.append("out");
        }
        sb.append(" photo");
        sb.append(lf);
      }
    } else {
      sb.append("Ad Runs: none specified" + lf);
    }
    sb.append(lf);
    sb.append("Make: " + StringUtils.toString(vehicle.getMake().getName()) + lf);
    sb.append("Model: " + StringUtils.toString(vehicle.getModel().getName()) + lf);
    sb.append("Year: " + vehicle.getYear() + lf);
    sb.append("Stock No: " + StringUtils.toString(stockNo) + lf);
    sb.append("Mileage: " + StringUtils.toString(this.getMileageString()) + lf);
    sb.append("VIN: " + StringUtils.toString(this.getVin()) + lf);
    sb.append("Selling Price: " + StringUtils.toString(this.getSellingPriceString()) + lf);
    sb.append("Color: " + StringUtils.toString(this.getExteriorColor()) + lf);
    sb.append("Condition: " + StringUtils.toString(condition) + lf);
    sb.append("Additional Text: " + StringUtils.toString(comments) + lf);
    sb.append("Options: ");
    if (this.hasOptions()) {
      Iterator options = this.getOptions().iterator();
      while (options.hasNext()) {
        Option thisOption = (Option) options.next();
        sb.append(thisOption.getName());
        if (options.hasNext()) {
          sb.append(", ");
        }
      }
    }
    sb.append(lf);

    return sb.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("inventory_id");
    vehicleId = rs.getInt("inventory_vehicle_id");
    accountId = rs.getInt("account_id");
    vin = rs.getString("vin");
    mileage = rs.getInt("mileage");
    isNew = rs.getBoolean("is_new");
    condition = rs.getString("condition");
    comments = rs.getString("comments");
    stockNo = rs.getString("stock_no");
    exteriorColor = rs.getString("ext_color");
    interiorColor = rs.getString("int_color");
    invoicePrice = rs.getDouble("invoice_price");
    sellingPrice = rs.getDouble("selling_price");
    sold = rs.getBoolean("sold");
    status = rs.getString("status");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    vehicle = new Vehicle(rs);
  }
}


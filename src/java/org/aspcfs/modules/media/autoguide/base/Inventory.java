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
  
  public Inventory() { }

  public Inventory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Inventory(Connection db, int inventoryId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(  
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
      " LEFT JOIN autoguide_model model ON v.model_id = model.model_id ");
    sql.append("WHERE i.inventory_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
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
    this.buildPictureId(db);
  }

  public void setId(int tmp) { id = tmp; }
  public void setId(String tmp) { id = Integer.parseInt(tmp); }
  public void setVehicleId(int tmp) { this.vehicleId = tmp; }
  public void setVehicleId(String tmp) { this.vehicleId = Integer.parseInt(tmp); }
  
  public void setAccountId(int tmp) { this.accountId = tmp; }
  public void setAccountId(String tmp) { this.accountId = Integer.parseInt(tmp); }
  public void setVin(String tmp) { this.vin = tmp; }
  public void setMileage(int tmp) { this.mileage = tmp; }
  public void setMileage(String tmp) { 
    this.mileage = StringUtils.getIntegerNumber(tmp);
  }
  public void setIsNew(boolean tmp) { this.isNew = tmp; }
  public void setIsNew(String tmp) { 
    this.isNew = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp)); 
  }
  public void setCondition(String tmp) { this.condition = tmp; }
  public void setComments(String tmp) { this.comments = tmp; }
  public void setStockNo(String tmp) { this.stockNo = tmp; }
  public void setExteriorColor(String tmp) { this.exteriorColor = tmp; }
  public void setInteriorColor(String tmp) { this.interiorColor = tmp; }
  public void setInvoicePrice(double tmp) { this.invoicePrice = tmp; }
  public void setInvoicePrice(String tmp) { 
    this.invoicePrice = StringUtils.getDoubleNumber(tmp);
  }
  public void setSellingPrice(double tmp) { this.sellingPrice = tmp; }
  public void setSellingPrice(String tmp) { 
    sellingPrice = StringUtils.getDoubleNumber(tmp);
  }
  public void setSold(boolean tmp) { this.sold = tmp; }
  public void setSold(String tmp) { 
    this.sold = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }
  public void setStatus(String tmp) { this.status = tmp; }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
  }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setModifiedBy(String tmp) { this.modifiedBy = Integer.parseInt(tmp); }
  public void setVehicle(Vehicle tmp) { this.vehicle = tmp; }
  public void setOrganization(Organization tmp) { this.organization = tmp; }
  public void setOptions(OptionList tmp) { this.options = tmp; }
  public void setRequestItems(HttpServletRequest request) {
    options = new OptionList(request);
    adRuns = new AdRunList(request);
  }
  public void setAdRuns(AdRunList tmp) { this.adRuns = tmp; }
  public void setPictureId(int tmp) { this.pictureId = tmp; }


  public int getId() { return id; }
  public int getVehicleId() { return vehicleId; }
  public int getAccountId() { return accountId; }
  public String getVin() { return vin; }
  public int getMileage() { return mileage; }
  public String getMileageString() { 
    if (mileage > -1) {
      return String.valueOf(mileage);
    } else {
      return "";
    }
  }
  public boolean getIsNew() { return isNew; }
  public String getCondition() { return condition; }
  public String getComments() { return comments; }
  public String getStockNo() { return stockNo; }
  public String getExteriorColor() { return exteriorColor; }
  public String getInteriorColor() { return interiorColor; }
  public double getInvoicePrice() { return invoicePrice; }
  public String getInvoicePriceString() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    return ("$" + numberFormatter.format(invoicePrice));
  }
  public double getSellingPrice() { return sellingPrice; }
  public String getSellingPriceString() {
    if (sellingPrice > 0) {
      NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
      return ("$" + numberFormatter.format(sellingPrice));
    } else {
      return "";
    }
  }
  public boolean getSold() { return sold; }
  public String getStatus() { return status; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }
  public String getGuid() {
    return ObjectUtils.generateGuid(entered, enteredBy, id);
  }
  public Vehicle getVehicle() { return vehicle; }
  public Organization getOrganization() { return organization; }
  public OptionList getOptions() { return options; }
  public boolean hasOptions() { 
    return (options != null && options.size() > 0); 
  }
  public boolean hasOption(int optionId) {
    return (options != null && options.hasOption(optionId));
  }
  public AdRunList getAdRuns() { return adRuns; }
  public boolean hasAdRuns() {
    return (adRuns != null && adRuns.size() > 0);
  }
  public int getPictureId() { return pictureId; }
  public boolean hasPictureId() { return pictureId > -1; }

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
  
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
/* 
    if (!isValid(db)) {
      return -1;
    }
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
      //sql.append("AND modified = ? ");
        
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
      //pst.setTimestamp(++i, this.getModified());
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


  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID was not specified");
    }

    PreparedStatement pst = null;
    //Delete related records (mappings)

    //Delete the record
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM autoguide_inventory " +
        "WHERE inventory_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();
    
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
/*     if (pictureId > -1) {
      FileItemList previousFiles = new FileItemList();
      previousFiles.setLinkModuleId(Constants.AUTOGUIDE);
      previousFiles.setLinkItemId(id);
      previousFiles.buildList(db);
      previousFiles.delete(db, filePath);
    }
 */
    if (recordCount == 0) {
      //errors.put("actionError", "Record could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


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

  public void buildOrganizationInfo(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Inventory-> Building org info: " + accountId);
    }
    if (accountId == -1) {
      accountId = 0;
    }
    organization = new Organization(db, accountId);
  }
  
  public void buildOptions(Connection db) throws SQLException {
    options = new OptionList();
    options.setInventoryId(id);
    options.buildList(db);
  }
  
  public void buildAdRuns(Connection db) throws SQLException {
    adRuns = new AdRunList();
    adRuns.setInventoryId(id);
    adRuns.buildList(db);
  }
  
  public void generateVehicleId(Connection db) throws SQLException {
    vehicleId = this.getVehicle().generateId(db);
  }
  
  public void buildPictureId(Connection db) throws SQLException {
    FileItem fileItem = new FileItem(db, -1, id, Constants.AUTOGUIDE);
    pictureId = fileItem.getId();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Inventory-> PictureID: " + pictureId);
    }
  }
}


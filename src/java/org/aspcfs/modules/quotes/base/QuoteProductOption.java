package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    March 24, 2004
 *@version    $Id: QuoteProductOption.java,v 1.1.2.3 2004/05/09 17:31:53 partha
 *      Exp $
 */
public class QuoteProductOption extends GenericBean {
  private int id = -1;
  private int itemId = -1;
  private int productOptionId = -1;
  private int quantity = 0;
  private int priceCurrency = -1;
  private double priceAmount = 0;
  private int recurringCurrency = -1;
  private double recurringAmount = 0;
  private int recurringType = -1;
  private double extendedPrice = 0;
  private double totalPrice = 0;
  private int statusId = -1;
  private boolean booleanValue = false;
  private double floatValue = 0.0;
  private Timestamp timestampValue = null;
  private int integerValue = -1;
  private String textValue = null;
  // product details
  private int productId = -1;


  /**
   *  Sets the id attribute of the QuoteProductOption object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteProductOption object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the itemId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the itemId attribute of the QuoteProductOption object g
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productOptionId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new productOptionId value
   */
  public void setProductOptionId(int tmp) {
    this.productOptionId = tmp;
  }


  /**
   *  Sets the productOptionId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new productOptionId value
   */
  public void setProductOptionId(String tmp) {
    this.productOptionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quantity attribute of the QuoteProductOption object
   *
   *@param  tmp  The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   *  Sets the quantity attribute of the QuoteProductOption object
   *
   *@param  tmp  The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceCurrency attribute of the QuoteProductOption object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   *  Sets the priceCurrency attribute of the QuoteProductOption object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceAmount attribute of the QuoteProductOption object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the QuoteProductOption object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the QuoteProductOption object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringAmount attribute of the QuoteProductOption object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the recurringType attribute of the QuoteProductOption object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   *  Sets the recurringType attribute of the QuoteProductOption object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the extendedPrice attribute of the QuoteProductOption object
   *
   *@param  tmp  The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   *  Sets the totalPrice attribute of the QuoteProductOption object
   *
   *@param  tmp  The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   *  Sets the statusId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the QuoteProductOption object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the booleanValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new booleanValue value
   */
  public void setBooleanValue(boolean tmp) {
    this.booleanValue = tmp;
  }


  /**
   *  Sets the booleanValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new booleanValue value
   */
  public void setBooleanValue(String tmp) {
    this.booleanValue = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the floatValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new floatValue value
   */
  public void setFloatValue(double tmp) {
    this.floatValue = tmp;
  }


  /**
   *  Sets the timestampValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new timestampValue value
   */
  public void setTimestampValue(Timestamp tmp) {
    this.timestampValue = tmp;
  }


  /**
   *  Sets the timestampValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new timestampValue value
   */
  public void setTimestampValue(String tmp) {
    this.timestampValue = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the integerValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new integerValue value
   */
  public void setIntegerValue(int tmp) {
    this.integerValue = tmp;
  }


  /**
   *  Sets the integerValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new integerValue value
   */
  public void setIntegerValue(String tmp) {
    this.integerValue = Integer.parseInt(tmp);
  }


  /**
   *  Sets the textValue attribute of the QuoteProductOption object
   *
   *@param  tmp  The new textValue value
   */
  public void setTextValue(String tmp) {
    this.textValue = tmp;
  }


  /**
   *  Gets the booleanValue attribute of the QuoteProductOption object
   *
   *@return    The booleanValue value
   */
  public boolean getBooleanValue() {
    return booleanValue;
  }


  /**
   *  Gets the floatValue attribute of the QuoteProductOption object
   *
   *@return    The floatValue value
   */
  public double getFloatValue() {
    return floatValue;
  }


  /**
   *  Gets the timestampValue attribute of the QuoteProductOption object
   *
   *@return    The timestampValue value
   */
  public Timestamp getTimestampValue() {
    return timestampValue;
  }


  /**
   *  Gets the integerValue attribute of the QuoteProductOption object
   *
   *@return    The integerValue value
   */
  public int getIntegerValue() {
    return integerValue;
  }


  /**
   *  Gets the textValue attribute of the QuoteProductOption object
   *
   *@return    The textValue value
   */
  public String getTextValue() {
    return textValue;
  }


  /**
   *  Gets the id attribute of the QuoteProductOption object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the itemId attribute of the QuoteProductOption object
   *
   *@return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the productOptionId attribute of the QuoteProductOption object
   *
   *@return    The productOptionId value
   */
  public int getProductOptionId() {
    return productOptionId;
  }


  /**
   *  Gets the quantity attribute of the QuoteProductOption object
   *
   *@return    The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   *  Gets the priceCurrency attribute of the QuoteProductOption object
   *
   *@return    The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   *  Gets the priceAmount attribute of the QuoteProductOption object
   *
   *@return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringCurrency attribute of the QuoteProductOption object
   *
   *@return    The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   *  Gets the recurringAmount attribute of the QuoteProductOption object
   *
   *@return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the recurringType attribute of the QuoteProductOption object
   *
   *@return    The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   *  Gets the extendedPrice attribute of the QuoteProductOption object
   *
   *@return    The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   *  Gets the totalPrice attribute of the QuoteProductOption object
   *
   *@return    The totalPrice value
   */
  public double getTotalPrice() {
    return totalPrice;
  }


  /**
   *  Gets the statusId attribute of the QuoteProductOption object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the productId attribute of the QuoteProductOption object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Constructor for the QuoteProductOption object
   */
  public QuoteProductOption() { }


  /**
   *  Constructor for the QuoteProductOption object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteProductOption(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the QuoteProductOption object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteProductOption(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote Product Option ID Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT opt.*, " +
        " bool.value AS boolean_value,  " +
        " float.value AS float_value, intr.value AS integer_value, " +
        " tst.value AS timestamp_value, txt.value AS text_value, " +
        " prod.product_id " +
        " FROM quote_product_options opt " +
        " LEFT JOIN quote_product_option_boolean bool " +
        " ON ( opt.quote_product_option_id = bool.quote_product_option_id ) " +
        " LEFT JOIN quote_product_option_float float " +
        " ON ( opt.quote_product_option_id = float.quote_product_option_id ) " +
        " LEFT JOIN quote_product_option_timestamp tst " +
        " ON ( opt.quote_product_option_id = tst.quote_product_option_id ) " +
        " LEFT JOIN quote_product_option_integer intr " +
        " ON ( opt.quote_product_option_id = intr.quote_product_option_id ) " +
        " LEFT JOIN quote_product_option_text txt " +
        " ON ( opt.quote_product_option_id = txt.quote_product_option_id ), " +
        " quote_product prod " +
        " WHERE opt.item_id = prod.item_id " +
        " AND opt.quote_product_option_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Quote Product Option not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // quote_product_options table
    this.setId(rs.getInt("quote_product_option_id"));
    itemId = rs.getInt("item_id");
    productOptionId = rs.getInt("product_option_id");
    quantity = DatabaseUtils.getInt(rs, "quantity");
    priceCurrency = DatabaseUtils.getInt(rs, "price_currency");
    priceAmount = DatabaseUtils.getDouble(rs, "price_amount");
    recurringCurrency = DatabaseUtils.getInt(rs, "recurring_currency");
    recurringAmount = DatabaseUtils.getDouble(rs, "recurring_amount");
    recurringType = DatabaseUtils.getInt(rs, "recurring_type");
    extendedPrice = DatabaseUtils.getDouble(rs, "extended_price");
    totalPrice = DatabaseUtils.getDouble(rs, "total_price");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    productId = DatabaseUtils.getInt(rs, "product_id");
    // quote_product_option_ values tables
    booleanValue = rs.getBoolean("boolean_value");
    floatValue = DatabaseUtils.getDouble(rs, "float_value");
    integerValue = DatabaseUtils.getInt(rs, "integer_value");
    timestampValue = rs.getTimestamp("timestamp_value");
    textValue = rs.getString("text_value");
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
    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      sql.append(
          " INSERT INTO quote_product_options(item_id, product_option_id, " +
          "   quantity, price_currency, price_amount, recurring_currency, " +
          "   recurring_amount, recurring_type, extended_price, total_price, status_id) "
          );
  
      sql.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getItemId());
      pst.setInt(++i, this.getProductOptionId());
      DatabaseUtils.setInt(pst, ++i, this.getQuantity());
      DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
      pst.setDouble(++i, this.getPriceAmount());
      DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
      pst.setDouble(++i, this.getRecurringAmount());
      DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
      pst.setDouble(++i, this.getExtendedPrice());
      pst.setDouble(++i, this.getTotalPrice());
      DatabaseUtils.setInt(pst, ++i, this.getStatusId());
      pst.execute();
      pst.close();
      this.setId(DatabaseUtils.getCurrVal(db, "quote_product_options_quote_product_option_id_seq"));
      
      if( this.getBooleanValue() != false ){
        sql = new StringBuffer("");
        sql.append(
          " INSERT INTO quote_product_option_boolean ( quote_product_option_id, value )"+
          " VALUES ( ? , ? ) "
        );
        pst = db.prepareStatement(sql.toString());
        pst.setInt(1, id);
        pst.setBoolean(2, this.getBooleanValue());
        pst.execute();
        pst.close();
      }
      if( ((int)this.getFloatValue()) > 0 ){
        sql = new StringBuffer("");
        sql.append(
          " INSERT INTO quote_product_option_float ( quote_product_option_id, value )"+
          " VALUES ( ? , ? ) "
        );
        pst = db.prepareStatement(sql.toString());
        pst.setInt(1, id);
        pst.setDouble(2, this.getFloatValue());
        pst.execute();
        pst.close();
      }
      if( this.getTimestampValue() != null ){
        sql = new StringBuffer("");
        sql.append(
          " INSERT INTO quote_product_option_timestamp ( quote_product_option_id, value )"+
          " VALUES ( ? , ? ) "
        );
        pst = db.prepareStatement(sql.toString());
        pst.setInt(1, id);
        pst.setTimestamp(2, this.getTimestampValue());
        pst.execute();
        pst.close();
      }
      if( this.getIntegerValue() != -1){
        sql = new StringBuffer("");
        sql.append(
          " INSERT INTO quote_product_option_integer ( quote_product_option_id, value )"+
          " VALUES ( ? , ? ) "
        );
        pst = db.prepareStatement(sql.toString());
        pst.setInt(1, id);
        DatabaseUtils.setInt(pst, 2, this.getIntegerValue());
        pst.execute();
        pst.close();
      }
      if(this.getTextValue()  != null){
        sql = new StringBuffer("");
        sql.append(
          " INSERT INTO quote_product_option_text ( quote_product_option_id, value )"+
          " VALUES ( ? , ? ) "
        );
        pst = db.prepareStatement(sql.toString());
        pst.setInt(1, id);
        pst.setString(2, this.getTextValue());
        pst.execute();
        pst.close();
      }
      db.commit();
    } catch(Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
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
  public boolean delete(Connection db) throws SQLException {
    boolean result = false;
    PreparedStatement pst = null;
    if (this.getId() == -1) {
      throw new SQLException("Quote Product Option ID not specified");
    }
    try {
    if( this.getBooleanValue() != false ){
      pst = db.prepareStatement(" DELETE FROM quote_product_option_boolean WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
    }
//  System.out.println("QuoteProductOption:");

    if( ((int)this.getFloatValue()) > 0 ){
      pst = db.prepareStatement(" DELETE FROM quote_product_option_float WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
    }
    if( this.getTimestampValue() != null ){
      pst = db.prepareStatement(" DELETE FROM quote_product_option_timestamp WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
    }
    if( this.getIntegerValue() != -1){
      pst = db.prepareStatement(" DELETE FROM quote_product_option_integer WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
    }
    if(this.getTextValue()  != null){
      pst = db.prepareStatement(" DELETE FROM quote_product_option_text WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
    }

    pst = db.prepareStatement(" DELETE FROM quote_product_options WHERE quote_product_option_id = ? ");
    pst.setInt(1, this.getId());
    result = pst.execute();
    pst.close();

    } catch (SQLException e) {
      db.rollback();
    } finally {
//      db.setAutoCommit(true);
    }
    return result;
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
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE quote_product_options " +
        " SET quantity = ?, " +
        "     price_currency = ?, " +
        "     price_amount = ?, " +
        "     recurring_currency = ?, " +
        "     recurring_amount = ?, " +
        "     recurring_type = ?, " +
        "     extended_price = ?, " +
        "     total_price = ?, " +
        "     status_id = ? ");
    sql.append(" WHERE quote_product_option_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getExtendedPrice());
    pst.setDouble(++i, this.getTotalPrice());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    int result1Count = -1;
    if( this.getBooleanValue() != false ){
      sql = new StringBuffer("");
      sql.append(
        " UPDATE quote_product_option_boolean "+
        " SET value = ? " +
        " WHERE quote_product_option_id = ? "
      );
      pst = db.prepareStatement(sql.toString());
      pst.setBoolean(1, this.getBooleanValue());
      DatabaseUtils.setInt(pst, 2, this.getId());
      result1Count = pst.executeUpdate();
      pst.close();
    }
    if( ((int)this.getFloatValue()) > 0 ){
      sql = new StringBuffer("");
      sql.append(
        " UPDATE quote_product_option_float "+
        " SET value = ? " +
        " WHERE quote_product_option_id = ? "
      );
      pst = db.prepareStatement(sql.toString());
      pst.setDouble(1, this.getFloatValue());
      DatabaseUtils.setInt(pst, 2, this.getId());
      result1Count = pst.executeUpdate();
      pst.close();
    }
    if( this.getTimestampValue() != null ){
      sql = new StringBuffer("");
      sql.append(
        " UPDATE quote_product_option_timestamp "+
        " SET value = ? " +
        " WHERE quote_product_option_id = ? "
      );
      pst = db.prepareStatement(sql.toString());
      pst.setTimestamp(1, this.getTimestampValue());
      DatabaseUtils.setInt(pst, 2, this.getId());
      result1Count = pst.executeUpdate();
      pst.close();
    }
    if( this.getIntegerValue() != -1){
      sql = new StringBuffer("");
      sql.append(
        " UPDATE quote_product_option_integer "+
        " SET value = ? " +
        " WHERE quote_product_option_id = ? "
      );
      pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, 1, this.getIntegerValue());
      DatabaseUtils.setInt(pst, 2, this.getId());
      result1Count = pst.executeUpdate();
      pst.close();
    
    }
    if(this.getTextValue()  != null){
      sql = new StringBuffer("");
      sql.append(
        " UPDATE quote_product_option_text "+
        " SET value = ? " +
        " WHERE quote_product_option_id = ? "
      );
      pst = db.prepareStatement(sql.toString());
      pst.setString(1, this.getTextValue());
      DatabaseUtils.setInt(pst, 2, this.getId());
      result1Count = pst.executeUpdate();
      pst.close();
    }
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the QuoteProductOption object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }
}


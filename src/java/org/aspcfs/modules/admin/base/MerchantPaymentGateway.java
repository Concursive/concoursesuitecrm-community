/**
 * 
 */
package org.aspcfs.modules.admin.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

/**
 * @author Olga.Kaptyug
 * 
 * @created Aug 22, 2006
 * 
 */
public class MerchantPaymentGateway extends GenericBean {
  private int id = -1;

  private int gatewayId = -1;
  
  private String gatewayName = null;

  private String merchantId = null;

  private String merchantCode = null;

  private Timestamp entered = null;

  private int enteredBy = -1;

  private Timestamp modified = null;

  private int modifiedBy = -1;

  /**
   * Gets the gatewayName attribute of the MerchantPaymentGateway object
   *
   * @return gatewayName The gatewayName value
   */
  public String getGatewayName() {
    return this.gatewayName;
  }

  /**
   * Sets the gatewayName attribute of the MerchantPaymentGateway object
   *
   * @param gatewayName The new gatewayName value
   */
  public void setGatewayName(String gatewayName) {
    this.gatewayName = gatewayName;
  }

  /**
   * Gets the entered attribute of the MerchantPaymentGateway object
   * 
   * @return entered The entered value
   */
  public Timestamp getEntered() {
    return this.entered;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Gets the enteredBy attribute of the MerchantPaymentGateway object
   * 
   * @return enteredBy The enteredBy value
   */
  public int getEnteredBy() {
    return this.enteredBy;
  }

  /**
   * Sets the enteredBy attribute of the MerchantPaymentGateway object
   * 
   * @param enteredBy
   *          The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }

  /**
   * Gets the modified attribute of the MerchantPaymentGateway object
   * 
   * @return modified The modified value
   */
  public Timestamp getModified() {
    return this.modified;
  }

  /**
   * Sets the modified attribute of the MerchantPaymentGateway object
   * 
   * @param modified
   *          The new modified value
   */
  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Gets the modifiedBy attribute of the MerchantPaymentGateway object
   * 
   * @return modifiedBy The modifiedBy value
   */
  public int getModifiedBy() {
    return this.modifiedBy;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the modifiedBy attribute of the MerchantPaymentGateway object
   * 
   * @param modifiedBy
   *          The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  /**
   * Gets the gatewayId attribute of the MerchantPaymentGateway object
   * 
   * @return gatewayId The gatewayId value
   */
  public int getGatewayId() {
    return this.gatewayId;
  }

  /**
   * Sets the gatewayId attribute of the MerchantPaymentGateway object
   * 
   * @param gatewayId
   *          The new gatewayId value
   */
  public void setGatewayId(int gatewayId) {
    this.gatewayId = gatewayId;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setGatewayId(String tmp) {
    this.gatewayId = Integer.parseInt(tmp);
  }

  /**
   * Gets the id attribute of the MerchantPaymentGateway object
   * 
   * @return id The id value
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the id attribute of the MerchantPaymentGateway object
   * 
   * @param id
   *          The new id value
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Sets the entered attribute of the MerchantPaymentGateway object
   * 
   * @param entered
   *          The new entered value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Gets the merchantCode attribute of the MerchantPaymentGateway object
   * 
   * @return merchantCode The merchantCode value
   */
  public String getMerchantCode() {
    return this.merchantCode;
  }

  /**
   * Sets the merchantCode attribute of the MerchantPaymentGateway object
   * 
   * @param merchantCode
   *          The new merchantCode value
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * Gets the merchantId attribute of the MerchantPaymentGateway object
   * 
   * @return merchantId The merchantId value
   */
  public String getMerchantId() {
    return this.merchantId;
  }

  /**
   * Sets the merchantId attribute of the MerchantPaymentGateway object
   * 
   * @param merchantId
   *          The new merchantId value
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * Constructor for the PaymentCreditCard object
   */
  public MerchantPaymentGateway() {
  }

  /**
   * Constructor for the PaymentCreditCard object
   * 
   * @param db
   *          Description of the Parameter
   * @param id
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public MerchantPaymentGateway(Connection db) throws SQLException {
    queryRecord(db);
  }

  /**
   * @param db
   * @param id2
   * @throws SQLException
   */
  private void queryRecord(Connection db) throws SQLException {
    PreparedStatement pst = db
        .prepareStatement("SELECT mpg.merchant_payment_gateway_id, lpg.description,"
            + " mpg.gateway_id, mpg.merchant_id, mpg.merchant_code,"
            + " mpg.entered, mpg.enteredby, mpg.modified, mpg.modifiedby"
            + " FROM merchant_payment_gateway mpg"
            + " LEFT JOIN lookup_payment_gateway lpg on (mpg.gateway_id=lpg.code)"
            );
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }

  /**
   * Constructor for the PaymentCreditCard object
   * 
   * @param rs
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public MerchantPaymentGateway(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * @param rs
   * @throws SQLException
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("merchant_payment_gateway_id"));
    gatewayId = DatabaseUtils.getInt(rs, "gateway_id");
    gatewayName = rs.getString("description");
    merchantId = rs.getString("merchant_id");
    merchantCode = rs.getString("merchant_code");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public int insert(Connection db) throws SQLException {
    int result = 0;
    id = DatabaseUtils.getNextSeq(db, "creditcard_creditcard_id_seq");
    StringBuffer sql = new StringBuffer();
    sql
        .append(" INSERT INTO merchant_payment_gateway(gateway_id, merchant_id, merchant_code,");
    if (id > -1) {
      sql.append("merchant_payment_gateway_id, ");
    }
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enteredby, ");
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("modifiedby )");
    sql.append("VALUES( ?, ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getGatewayId());
    pst.setString(++i, this.getMerchantId());
    pst.setString(++i, this.getMerchantCode());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setInt(++i, this.getEnteredBy());
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "merchant_payment_gateway_seq", id);
    result = 1;
    return result;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Merchant Payment Gateway ID not specified");
    }
    // delete the credit card info
    PreparedStatement pst = db
        .prepareStatement("DELETE FROM merchant_payment_gateway WHERE merchant_payment_gateway_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE merchant_payment_gateway " + "SET  gateway_id = ?, "
        + "     merchant_id = ?, "
        + "     merchant_code = ?, "
        + "     modified = "
        + DatabaseUtils.getCurrentTimestamp(db) + ", "
        + "     modifiedby = ? " + "WHERE merchant_payment_gateway_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getGatewayId());
    pst.setString(++i, this.getMerchantId());
    pst.setString(++i, this.getMerchantCode());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

}

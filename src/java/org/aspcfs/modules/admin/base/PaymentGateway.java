/**
 * 
 */
package org.aspcfs.modules.admin.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Olga.Kaptyug
 * 
 * @created Sep 28, 2006
 * 
 */
public class PaymentGateway {

  private int code = -1;
  private String description = "";
  private boolean defaultItem = false;
  private int level = 0;
  private boolean enabled = true;
  private int constantId = -1;

  public PaymentGateway() {

  }

  /**
   * Constructor 
   * 
   * @param db
   *          Description of the Parameter
   * @param id
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public PaymentGateway(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }

  /**
   * Constructor 
   * 
   * @param rs
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public PaymentGateway(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Gets the code attribute of the PaymentGateway object
   * 
   * @return code The code value
   */
  public int getCode() {
    return this.code;
  }

  /**
   * Sets the code attribute of the PaymentGateway object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Sets the code attribute of the PaymentGateway object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(String code) {
    this.code = Integer.parseInt(code);
  }

  /**
   * Gets the constant_id attribute of the PaymentGateway object
   * 
   * @return constant_id The constant_id value
   */
  public int getConstantId() {
    return this.constantId;
  }

  /**
   * Sets the constant_id attribute of the PaymentGateway object
   * 
   * @param constantId
   *          The new constant_id value
   */
  public void setConstantId(int constantId) {
    this.constantId = constantId;
  }

  /**
   * Sets the constant_id attribute of the PaymentGateway object
   * 
   * @param constantId
   *          The new constant_id value
   */
  public void setConstantId(String constantId) {
    this.constantId = Integer.parseInt(constantId);
  }

  /**
   * 
   * /** Gets the description attribute of the PaymentGateway object
   * 
   * @return description The description value
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description attribute of the PaymentGateway object
   * 
   * @param description
   *          The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the enabled attribute of the PaymentGateway object
   * 
   * @return enabled The enabled value
   */
  public boolean getEnabled() {
    return this.enabled;
  }

  /**
   * Sets the enabled attribute of the PaymentGateway object
   * 
   * @param enabled
   *          The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Sets the enabled attribute of the PaymentGateway object
   * 
   * @param enabled
   *          The new enabled value
   */
  public void setEnabled(String enabled) {
    this.enabled = DatabaseUtils.parseBoolean(enabled);
  }

  /**
   * Gets the level attribute of the PaymentGateway object
   * 
   * @return level The level value
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * Sets the level attribute of the PaymentGateway object
   * 
   * @param level
   *          The new level value
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Gets the defaultItem attribute of the PaymentGateway object
   * 
   * @return defaultItem The defaultItem value
   */
  public boolean getDefaultItem() {
    return this.defaultItem;
  }

  /**
   * Sets the defaultItem attribute of the PaymentGateway object
   * 
   * @param defaultItem
   *          The new defaultItem value
   */
  public void setDefaultItem(boolean defaultItem) {
    this.defaultItem = defaultItem;
  }

  /**
   * Sets the defaultItem attribute of the PaymentGateway object
   * 
   * @param defaultItem
   *          The new defaultItem value
   */
  public void setDefaultItem(String defaultItem) {
    this.defaultItem = DatabaseUtils.parseBoolean(defaultItem);
  }

  /**
   * @param db
   * @param code
   * @throws SQLException 
   */
  private void queryRecord(Connection db, int code) throws SQLException {
    if (code == -1) {
      throw new SQLException("Invalid ID");
    }

    String sql =
      "SELECT code, description, default_item, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, constant_id " +
      "FROM lookup_payment_gateway  " +
      "WHERE code = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new java.sql.SQLException("ID not found");
    }
    rs.close();
    pst.close();
  }

  /**
   * Description of the Method
   * 
   * @param rs
   *          Description of the Parameter
   * @throws java.sql.SQLException
   *           Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws java.sql.SQLException {
    code = rs.getInt("code");
    description = rs.getString("description");
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    constantId = rs.getInt("constant_id");
    if (!(this.getEnabled())) {
      description += " (X)";
    }

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
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    code = DatabaseUtils.getNextSeq(db, "lookup_payment_gateway_code_seq");
    sql.append("INSERT INTO lookup_payment_gateway (");
    if (code > -1) {
      sql.append("code, ");
    }
    sql.append(" description, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, " + " constant_id) "
        + "VALUES (");
    if (code > -1) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (code > -1) {
      pst.setInt(++i, this.getCode());
    }
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, this.getLevel());
    pst.setBoolean(++i, true);
    pst.setInt(++i, this.getConstantId());
    pst.execute();
    pst.close();
    code = DatabaseUtils
        .getCurrVal(db, "lookup_payment_gateway_code_seq", code);
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
    if (this.getCode() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE lookup_payment_gateway SET description = ?, "
        + " " + DatabaseUtils.addQuotes(db, "level") + " = ?, enabled = ?, constant_id = ?, modified = "
        + DatabaseUtils.getCurrentTimestamp(db) + " "
        + "WHERE code = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, this.getLevel());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getConstantId());
    pst.setInt(++i, this.getCode());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @exception SQLException
   *              Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM lookup_payment_gateway WHERE code = ? ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, code);
    pst.execute();
    pst.close();
  }

}
